package com.practo.service.interfaces;

import com.practo.dto.InitiatePaymentDto;
import com.practo.dto.TransactionDto;

public interface PaymentService 
{
    public TransactionDto createPayment(TransactionDto transactionDto);
    
    public TransactionDto initiatePayment(String txnReference, InitiatePaymentDto reqDto);	

}
