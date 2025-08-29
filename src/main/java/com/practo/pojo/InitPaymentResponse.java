package com.practo.pojo;

import lombok.Data;

@Data
public class InitPaymentResponse 
{
	private String txnReference;
	private String txnStatus;
	private String url;

}
