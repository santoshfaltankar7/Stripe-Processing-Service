package com.practo.util.idtostring;

import org.modelmapper.AbstractConverter;

import com.practo.constant.PaymentTypeEnum;

public class PaymentTypeEnumIdtoStingConverter extends AbstractConverter<Integer, String> 
{
	@Override
     protected String convert(Integer source)
     {
    	 return PaymentTypeEnum.fromId(source).getName();
     }
}

