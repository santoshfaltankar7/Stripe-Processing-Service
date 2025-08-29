package com.practo.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.practo.dto.InitiatePaymentDto;
import com.practo.dto.TransactionDto;
import com.practo.pojo.CreatePaymentRequest;
import com.practo.pojo.CreatePaymentResponse;
import com.practo.pojo.InitPaymentResponse;
import com.practo.pojo.InitiatePaymentReq;
import com.practo.service.interfaces.PaymentService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("v1/payment")
public class PaymentController 
{
	@Autowired
	private PaymentService paymentService; 

	@Autowired
	private ModelMapper modelMapper;




	@PostMapping
	public ResponseEntity<CreatePaymentResponse> createPayment(@RequestBody CreatePaymentRequest createPaymentRequest)
	{
		log.info("Payment create success: CreatePaymentRequest"+ createPaymentRequest);

		TransactionDto transactionDto= modelMapper.map(createPaymentRequest, TransactionDto.class);
		log.info("transactionDto creates sucessfully : transactionDto"+transactionDto);

		TransactionDto response=paymentService.createPayment(transactionDto);

		CreatePaymentResponse createPaymentResponse=new CreatePaymentResponse();
		createPaymentResponse.setTxnreference(response.getTxnReference());  
		createPaymentResponse.setTxnStatus(response.getTxnStatus());

		log.info("Returning createPaymentResponse",createPaymentResponse);

		return new ResponseEntity<>(createPaymentResponse, HttpStatus.CREATED);
	}

	@PostMapping("/{txnreference}/initiate")
	public ResponseEntity<InitPaymentResponse> initiatePayment(@PathVariable String txnreference,
			@RequestBody InitiatePaymentReq initiatePaymentReq)
	{
		log.info("initiate payment || InitiatePaymentReq"+txnreference, initiatePaymentReq);

		InitiatePaymentDto reqDto=modelMapper.map(initiatePaymentReq, InitiatePaymentDto.class);

		log.info("response from service TranactionDTO:"+reqDto);

		TransactionDto responseDto =paymentService.initiatePayment(txnreference, reqDto);


		InitPaymentResponse response= new InitPaymentResponse();
		response.setTxnReference(responseDto.getTxnReference());
		response.setTxnStatus(responseDto.getTxnStatus());
		response.setUrl(responseDto.getUrl());

		System.out.println("Returning Response:"+response);


		return ResponseEntity.ok(response);

	}
}
