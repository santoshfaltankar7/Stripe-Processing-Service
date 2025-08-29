package com.practo.util;

import org.modelmapper.AbstractConverter;

import com.practo.constant.TransactionStatusEnum;

public class TransactionStatusEnumConverter extends AbstractConverter<String, Integer> 
{
     protected Integer convert(String source)
     {
    	 return TransactionStatusEnum.fromName(source).getId();
     }
}
