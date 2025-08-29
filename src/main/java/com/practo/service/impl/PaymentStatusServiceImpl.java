package com.practo.service.impl;

import org.springframework.stereotype.Service;

import com.practo.constant.TransactionStatusEnum;
import com.practo.dto.TransactionDto;
import com.practo.service.PaymentStatusFactory;
import com.practo.service.interfaces.PaymentStatusService;
import com.practo.service.interfaces.TxnStatusHandler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class PaymentStatusServiceImpl implements PaymentStatusService
{
	
	private final PaymentStatusFactory paymentStatusFactory;

	@Override
	public TransactionDto processStatus(TransactionDto transactionDto) {
		log.info("*****Recieved TransactionDto"+transactionDto+"||paymentStatusFactory:"+paymentStatusFactory);
		
		TransactionStatusEnum statusEnum=TransactionStatusEnum.fromName(transactionDto.getTxnStatus());
		
		if(statusEnum==null)
		{
			log.error("Invalid Status recieved",transactionDto.getTxnStatus());
		}
		
		TxnStatusHandler statusHandler = paymentStatusFactory.getHandler(statusEnum);
		
		if(statusHandler == null)
		{
			//throw custome exception
		}
		
		TransactionDto responseDto= statusHandler.processStatus(transactionDto);
		log.info("responseDto"+responseDto);
		
		return responseDto;
	}

}
