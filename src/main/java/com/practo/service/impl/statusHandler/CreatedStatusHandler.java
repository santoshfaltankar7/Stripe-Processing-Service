package com.practo.service.impl.statusHandler;

import org.springframework.stereotype.Service;

import com.practo.dao.interfaces.TransactionDao;
import com.practo.dto.TransactionDto;
import com.practo.service.interfaces.TxnStatusHandler;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CreatedStatusHandler implements TxnStatusHandler 
{
	
	private final TransactionDao transactionDao;
	public CreatedStatusHandler(TransactionDao transactionDao) {
		
		this.transactionDao = transactionDao;
	}


	@Override
	public TransactionDto processStatus(TransactionDto transactionDto) 
	{
		log.info("Proccessing CREATED status||transactionDto-"+transactionDto);
		
		transactionDto = transactionDao.createTransaction(transactionDto);
	  log.info("Created txn in DB || transactionDto"+transactionDto);
		return transactionDto;	
	}

}
