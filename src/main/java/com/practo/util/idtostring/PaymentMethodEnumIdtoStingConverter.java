package com.practo.util.idtostring;

import org.modelmapper.AbstractConverter;

import com.practo.constant.PaymentMethodEnum;

public class PaymentMethodEnumIdtoStingConverter extends AbstractConverter<Integer, String> 
{
	@Override
     protected String convert(Integer source)
     {
    	 return PaymentMethodEnum.fromId(source).getName();
     }
}
