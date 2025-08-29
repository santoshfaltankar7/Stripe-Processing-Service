package com.practo.config;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.practo.dto.TransactionDto;
import com.practo.entity.TransactionEntity;
import com.practo.util.PaymentMethodEnumConverter;
import com.practo.util.PaymentTypeEnumConverter;
import com.practo.util.ProviderEnumConverter;
import com.practo.util.TransactionStatusEnumConverter;
import com.practo.util.idtostring.PaymentMethodEnumIdtoStingConverter;
import com.practo.util.idtostring.PaymentTypeEnumIdtoStingConverter;
import com.practo.util.idtostring.ProviderEnumIdtoStingConverter;
import com.practo.util.idtostring.TransactionStatusEnumIdtoStingConverter;

@Configuration
public class AppConfig 
{
	@Bean
	ModelMapper modelMapper()

	{

		ModelMapper mappers=new  ModelMapper();

		mappers.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		
		Converter<String, Integer>paymentMethodEnumConverter= new PaymentMethodEnumConverter();
		Converter<String, Integer>providerEnumConverter= new ProviderEnumConverter();
		Converter<String, Integer>paymentTypeEnumConverter= new PaymentTypeEnumConverter();
		Converter<String, Integer>transactionStatusEnumConverter= new TransactionStatusEnumConverter();

		//Converter<String, Integer>paymentMethodEnumConverter= new PaymentMethodEnumConverter();
		//Converter<String, Integer>providerEnumConverter= new ProviderEnumConverter();
		
		mappers.addMappings(new PropertyMap<TransactionDto, TransactionEntity>()
		{
			protected void configure()
			{
				using(paymentMethodEnumConverter).map(source.getPaymentMethod(),destination.getPaymentMethodId());
				using(providerEnumConverter).map(source.getProvider(),destination.getProviderId());
                
				using(paymentTypeEnumConverter).map(source.getPaymentType(),destination.getPaymentTypeId());
				using(transactionStatusEnumConverter).map(source.getTxnStatus(),destination.getTxnStatusId());

			}
			
		});
		
		mappers.addMappings(new PropertyMap<TransactionEntity, TransactionDto>()
		{
			protected void configure()
			{
				using(new PaymentMethodEnumIdtoStingConverter()).map(source.getPaymentMethodId(),destination.getPaymentMethod());
				using(new ProviderEnumIdtoStingConverter()).map(source.getProviderId(),destination.getProvider());
                
				using(new PaymentTypeEnumIdtoStingConverter()).map(source.getPaymentTypeId(),destination.getPaymentType());
				using(new TransactionStatusEnumIdtoStingConverter()).map(source.getTxnStatusId(),destination.getTxnStatus());

			}
			
		});
		return mappers;

	}

      
}
