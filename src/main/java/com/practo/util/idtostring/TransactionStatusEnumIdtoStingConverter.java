package com.practo.util.idtostring;

import org.modelmapper.AbstractConverter;

import com.practo.constant.TransactionStatusEnum;

public class TransactionStatusEnumIdtoStingConverter extends AbstractConverter<Integer, String> 
{
	@Override
     protected String convert(Integer source)
     {
    	 return TransactionStatusEnum.fromId(source).getName();
     }
}
