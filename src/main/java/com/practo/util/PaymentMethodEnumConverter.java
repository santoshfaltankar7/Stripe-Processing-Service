package com.practo.util;

import org.modelmapper.AbstractConverter;

import com.practo.constant.PaymentMethodEnum;

public class PaymentMethodEnumConverter extends AbstractConverter<String, Integer> 
{
     protected Integer convert(String source)
     {
    	 return PaymentMethodEnum.fromName(source).getId();
     }
}
