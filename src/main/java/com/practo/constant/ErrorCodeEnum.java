package com.practo.constant;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public enum ErrorCodeEnum 
{

	GENERIC_ERROR("2000","Unable to process the request,plz try again later"),
	UNABLE_TO_CONNECT_TO_STRIPE_PS("2001","unable to connect to stripe provider"),
	ERROR_AT_STRIPE_PSP("2002","failed process at stripe psp.plz try again later"),
	INVAILD_TXN_REFERENCE("2003","Invaild txnRef. mo transaction found"),
	INVAILD_PAYMENT_STATUS("2004","invaild payment status. no configuration found"),
	NO_STATUS_HANDLE_FOUND("2005","transaction status handler not found");
	
	private String errorCode;
	private String errorMessage;
	ErrorCodeEnum(String errorCode, String errorMessage) {
		
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}
	

}
