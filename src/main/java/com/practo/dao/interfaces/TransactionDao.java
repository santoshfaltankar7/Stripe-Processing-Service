package com.practo.dao.interfaces;

import com.practo.dto.TransactionDto;

public interface TransactionDao 
{
     public TransactionDto createTransaction(TransactionDto transactionDto);
     
     public TransactionDto getTransactionByTxnReference(String txnRefs);

	public TransactionDto updateTransactionStatusDetails(TransactionDto txnDto);
}
