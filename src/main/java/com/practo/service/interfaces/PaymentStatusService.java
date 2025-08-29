package com.practo.service.interfaces;

import com.practo.dto.TransactionDto;

public interface PaymentStatusService 
{
	public TransactionDto processStatus(TransactionDto transactionDto);
	
}
