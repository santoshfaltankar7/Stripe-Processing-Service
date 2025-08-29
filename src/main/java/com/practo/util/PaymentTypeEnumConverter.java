package com.practo.util;

import org.modelmapper.AbstractConverter;

import com.practo.constant.PaymentTypeEnum;

public class PaymentTypeEnumConverter extends AbstractConverter<String, Integer> 
{
     protected Integer convert(String source)
     {
    	 return PaymentTypeEnum.fromName(source).getId();
     }
}
