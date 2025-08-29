package com.practo.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class TransactionDto 
{
	private Integer id;
    private Integer userId;
    private String paymentMethod;
    private String provider;
    private String paymentType; 
    private String txnStatus;
    private Long amount;
    private String currency;
    private String merchantTxnReference;
    private String txnReference;
    private String providerReference;
    private String errorCode;
    private String errorMessage;
    private LocalDate creationDate;
    private LocalDate updatedDate;
    private Integer retryCount;
    private String url;
	
}
