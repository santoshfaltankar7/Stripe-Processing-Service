package com.practo.stripeProvider;

import java.util.List;

import lombok.Data;

@Data
public class CreatePaymentReq 
{ 
    private String successUrl;
    private String cancelUrl;
    private List<LineItem>lineItem;
}