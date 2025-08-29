package com.practo.util.idtostring;

import org.modelmapper.AbstractConverter;

import com.practo.constant.ProviderEnum;

public class ProviderEnumIdtoStingConverter extends AbstractConverter<Integer, String> 
{
	@Override
     protected String convert(Integer source)
     {
    	 return ProviderEnum.fromId(source).getName();
     }
}
