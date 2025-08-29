package com.practo.dto.stripe;

import lombok.Data;

@Data
public class StripeEventDto 
{
	private String id;
	private String type;
	private StripeDto data;
	

}
