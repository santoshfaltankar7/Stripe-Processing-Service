package com.practo.entity;

import java.time.LocalDate;

import lombok.Data;


@Data
public class TransactionEntity
{
	
	private Integer id;
    private Integer userId;
    private Integer paymentMethodId;
    private Integer providerId;
    private Integer paymentTypeId;
    private Integer txnStatusId;
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
}
