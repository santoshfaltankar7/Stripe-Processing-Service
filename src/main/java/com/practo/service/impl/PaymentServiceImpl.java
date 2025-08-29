package com.practo.service.impl;

import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.practo.constant.ErrorCodeEnum;
import com.practo.constant.TransactionStatusEnum;
import com.practo.dao.interfaces.TransactionDao;
import com.practo.dto.InitiatePaymentDto;
import com.practo.dto.TransactionDto;
import com.practo.exception.ProcessingException;
import com.practo.exception.SPErrorResponse;
import com.practo.http.HttpRequest;
import com.practo.http.HttpServiceEngine;
import com.practo.service.interfaces.PaymentService;
import com.practo.service.interfaces.PaymentStatusService;
import com.practo.stripeProvider.CreatePaymentReq;
import com.practo.stripeProvider.PaymentRes;
import com.practo.stripeProvider.PaymentResDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService
{
	private final PaymentStatusService paymentStatusService;
	
	private final HttpServiceEngine httpServiceEngine;
	
	private final TransactionDao transactionDao;
	 
	private final ModelMapper modelMapper;
	
	@Value("${stripe.provider.create.payment.url}")
	private String createSessionUrl ;
	
	private final Gson gson;

	
	@Override
	public TransactionDto createPayment(TransactionDto transactionDto) {
		log.info("Transaction Dto "+transactionDto);
		
		transactionDto.setTxnStatus(TransactionStatusEnum.CREATED.getName());
		transactionDto.setTxnReference(generetetxnReference());
		
		transactionDto=paymentStatusService.processStatus(transactionDto);
		log.info("Create payment in DB transactionDto:"+transactionDto);
		
		
		return transactionDto;
	}

	
	private String generetetxnReference() {
		String txnreference = UUID.randomUUID().toString();
		log.info("Generated txnReference"+txnreference);
		return txnreference;
	}

	@Override
	public TransactionDto initiatePayment(String txnReference, InitiatePaymentDto reqDto) 
	{
		log.info("Initiate payment txnReference");    		
		TransactionDto txnDtos=transactionDao.getTransactionByTxnReference(txnReference);
		
		log.info("in paymentserviceImpl get dto transactionDao from DB:"+txnDtos);
		//TransactionDto txnDto=new TransactionDto();
		
		
		txnDtos.setTxnStatus(TransactionStatusEnum.INITIATED.getName());		
		paymentStatusService.processStatus(txnDtos);
		
		
		HttpRequest httpRequest = prepareHttpReq(reqDto);
				
		try {
			
			ResponseEntity<String> stripeResponse =httpServiceEngine.makeHttpCall(httpRequest);
			log.info("response from stripeResponse:"+stripeResponse);
			
			PaymentResDto paymentResDto=processResponse(stripeResponse);
			
			txnDtos.setTxnStatus(TransactionStatusEnum.PENDING.getName());
			txnDtos.setProviderReference(paymentResDto.getId());
			txnDtos.setUrl(paymentResDto.getUrl());
			paymentStatusService.processStatus(txnDtos);
			
			
	
			return txnDtos;
			
		} catch(ProcessingException e){
			txnDtos.setTxnStatus(TransactionStatusEnum.FAILED.getName());
			txnDtos.setErrorCode(e.getErrorCode());
			txnDtos.setErrorMessage(e.getErrorMessage());
			paymentStatusService.processStatus(txnDtos);
			
			log.info("Successfully ErrorCode  & ErrorMessage updated in DB:"+txnDtos);
			if(HttpStatus.BAD_REQUEST.equals(e.getHttpStatus())) {
				throw new ProcessingException(ErrorCodeEnum.ERROR_AT_STRIPE_PSP.getErrorCode(),
						ErrorCodeEnum.ERROR_AT_STRIPE_PSP.getErrorMessage(), e.getHttpStatus());
			}
			throw e;
			
		}

		
		
	}
	
	private HttpRequest prepareHttpReq(InitiatePaymentDto reqDto) 
	{
		log.info("prepareHttpReq:"+reqDto);
		
		HttpHeaders httpHeaders=new HttpHeaders();
		httpHeaders.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE );

		
		CreatePaymentReq createPaymentReq=modelMapper.map(reqDto, CreatePaymentReq.class);
		
		log.info("create payment "+createPaymentReq);
		
		HttpRequest httpRequest = HttpRequest.builder()
			    .method(HttpMethod.POST)
			    .url(createSessionUrl)
			    .headers(httpHeaders)
			    .requestBody(gson.toJson(createPaymentReq))
			    .build();
		
		return httpRequest;
	}
	
	private PaymentResDto processResponse(ResponseEntity<String> httpRes) {
		if(httpRes.getStatusCode().isSameCodeAs(HttpStatus.CREATED)) {
			PaymentRes spPaymentRes=gson.fromJson(httpRes.getBody(), PaymentRes.class);//change gson to gsonUtils
			log.info("In processResponse Converted httpRes to PaymentRes:"+spPaymentRes);
			
			
			if(spPaymentRes != null && spPaymentRes.getUrl() !=null) {
				PaymentResDto paymentResDto=modelMapper.map(spPaymentRes, PaymentResDto.class);
				log.info("In processResponse Converted spPaymentRes to PaymentResDto:"+paymentResDto);
				return paymentResDto;
			}
			log.info("Got 201 but no url in response");
		}
		
		SPErrorResponse errorRes=gson.fromJson(httpRes.getBody(),SPErrorResponse.class);
log.info("Convert HttpRes Body to SPErrorResponse");
		if(errorRes!=null && errorRes.getErrorCode()!=null) {
			throw new ProcessingException(errorRes.getErrorCode(), errorRes.getErrorMessage(), 
					HttpStatus.valueOf(httpRes.getStatusCode().value()));
			
		}
		
		throw new ProcessingException(ErrorCodeEnum.GENERIC_ERROR.getErrorCode(), 
				ErrorCodeEnum.GENERIC_ERROR.getErrorMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		
	}


}
