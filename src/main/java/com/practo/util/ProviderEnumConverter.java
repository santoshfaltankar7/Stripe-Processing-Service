package com.practo.util;

import org.modelmapper.AbstractConverter;

import com.practo.constant.ProviderEnum;

public class ProviderEnumConverter extends AbstractConverter<String, Integer> 
{
     protected Integer convert(String source)
     {
    	 return ProviderEnum.fromName(source).getId();
     }
}
