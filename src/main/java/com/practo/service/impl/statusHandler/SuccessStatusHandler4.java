package com.practo.service.impl.statusHandler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.practo.dao.interfaces.TransactionDao;
import com.practo.dto.TransactionDto;
import com.practo.service.interfaces.TxnStatusHandler;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SuccessStatusHandler4 implements TxnStatusHandler 
{
	@Autowired
    private  TransactionDao transactionDao; 
	
	@Override
	public TransactionDto processStatus(TransactionDto txnDto) 
	{

		log.info("Processing SUCCESS States:"+txnDto);
		
		transactionDao.updateTransactionStatusDetails(txnDto);
		
		log.info("Update Txn in DB"+txnDto);
		
		return txnDto;
		
	}

}
