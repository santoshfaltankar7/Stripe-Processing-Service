package com.practo.service;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.practo.constant.TransactionStatusEnum;
import com.practo.service.impl.statusHandler.CreatedStatusHandler;
import com.practo.service.impl.statusHandler.FailedStatusHandler4;
import com.practo.service.impl.statusHandler.InitiatedStatusHandler2;
import com.practo.service.impl.statusHandler.PendingStatusHandler3;
import com.practo.service.impl.statusHandler.SuccessStatusHandler4;
import com.practo.service.interfaces.TxnStatusHandler;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class PaymentStatusFactory 
{
	private ApplicationContext contex;
	
	
	
    public PaymentStatusFactory(ApplicationContext contex) {
		
		this.contex = contex;
	}



	public TxnStatusHandler getHandler(TransactionStatusEnum statusEnum)
    {
    	
    	switch (statusEnum) {
		case CREATED: {
		   return contex.getBean(CreatedStatusHandler.class);
		}
		case INITIATED: {
			return contex.getBean(InitiatedStatusHandler2.class);
			}
		case PENDING: {
			return contex.getBean(PendingStatusHandler3.class);
			}
		case SUCCESS: {
			return contex.getBean(SuccessStatusHandler4.class);
			}
		case FAILED: {
			return contex.getBean(FailedStatusHandler4.class);
			}
    	}
    	log.error("No handler fournd for status",statusEnum);
    	return null;
    }
  }   
 
