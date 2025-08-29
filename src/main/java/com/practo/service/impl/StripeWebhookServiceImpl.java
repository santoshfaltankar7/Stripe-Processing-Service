package com.practo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.practo.constant.TransactionStatusEnum;
import com.practo.dao.interfaces.TransactionDao;
import com.practo.dto.TransactionDto;
import com.practo.dto.stripe.CheckOutSessionCompletedData;
import com.practo.dto.stripe.StripeEventDto;
import com.practo.service.interfaces.PaymentStatusService;
import com.practo.service.interfaces.StripeWebhookService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class StripeWebhookServiceImpl implements StripeWebhookService 
{
	
	@Autowired
	private final Gson gson;
	
	@Autowired
	private PaymentStatusService paymentStatusService;
	@Autowired
	private TransactionDao dao;
	
	 

	public StripeWebhookServiceImpl(Gson gson) {
		super();
		this.gson = gson;
	}

	private static final String CHECKOUT_SESSION_ASYNC_PAYMENT_FAILED = "checkout.session.async_payment_failed";
	private static final String CHECKOUT_SESSION_ASYNC_PAYMENT_SUCCEEDED = "checkout.session.async_payment_succeeded";
	private static final String CHECKOUT_SESSION_COMPLETED = "checkout.session.completed";

	@Override
	public void processEvent(StripeEventDto eventDto) 
	{
		if(CHECKOUT_SESSION_COMPLETED.equals(eventDto.getType()))
		{
			log.info("Checkout Session Completed Event recieved");
			CheckOutSessionCompletedData objData=gson.fromJson(eventDto.getData().getObject(), CheckOutSessionCompletedData.class);
			
			log.info("Coveret StripeEventDto to CheckOutSessio Completed Data");
			if("complete".equals(objData.getStatus())&& "paid".equals(objData.getPaymentStatus())) {
				log.info("Payment Success");
				
				TransactionDto txnDto=dao.getTransactionByProviderReference(objData.getId());
				
				if(txnDto==null) 
				{
					log.error("no transaxtion found for provider reference ");
				}
				txnDto.setTxnStatus(TransactionStatusEnum.SUCCESS.getName());
				paymentStatusService.processStatus(txnDto);
			}
		}
		
		
		
		if(CHECKOUT_SESSION_ASYNC_PAYMENT_SUCCEEDED.equals(eventDto.getType()))
		{
			log.info("Checkout Session async payment succeeded Event recieved");
		}
		
		if(CHECKOUT_SESSION_ASYNC_PAYMENT_FAILED.equals(eventDto.getType()))
		{
			log.info("Checkout Session async payment failed Event recieved");
		}

	}

}
