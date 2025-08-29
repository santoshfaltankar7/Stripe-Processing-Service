package com.practo.pojo;

import lombok.Data;

@Data
public class CreatePaymentRequest 
{
    private Integer userId;
    private String paymentMethod;
    private String provider;
    private String paymentType;  
    private Long amount;
    private String currency;
    private String merchantTxnReference;
}
