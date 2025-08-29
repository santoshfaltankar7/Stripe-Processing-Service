package com.practo.service.interfaces;

import com.practo.dto.TransactionDto;

public interface TxnStatusHandler 
{
   public TransactionDto processStatus(TransactionDto transactionDto);
}
