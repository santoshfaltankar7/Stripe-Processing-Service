package com.practo.service.impl.statusHandler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.practo.dao.interfaces.TransactionDao;
import com.practo.dto.TransactionDto;
import com.practo.service.interfaces.TxnStatusHandler;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class InitiatedStatusHandler2 implements TxnStatusHandler
{
	@Autowired
	private TransactionDao transactionDao;	
	@Override
	public TransactionDto processStatus(TransactionDto txnDto) 
	{
		log.info("Processing Initiated States:"+txnDto);
		
		transactionDao.updateTransactionStatusDetails(txnDto);
		
		log.info("Update Txn in DB"+txnDto);
		
		return txnDto;
		
		
	}

}
