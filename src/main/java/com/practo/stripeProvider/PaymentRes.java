package com.practo.stripeProvider;

import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class PaymentRes 
{
	private String id;
	private String url;
	private String status;
	
	@SerializedName("payment_status")
	private String paymentStatus;


}
