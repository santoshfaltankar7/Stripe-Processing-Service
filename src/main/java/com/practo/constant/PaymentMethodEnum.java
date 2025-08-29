package com.practo.constant;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Slf4j
public enum PaymentMethodEnum 
{
	APM(1,"APM");
	
	
	private int id;
	private String name;
	 PaymentMethodEnum(int id, String name) {
		this.id = id;
		this.name = name;
	}
	 
	 //Method: Get enum by name
	 public static PaymentMethodEnum fromName(String name) {
//	        if (name == null) return null;
	        for (PaymentMethodEnum status : PaymentMethodEnum.values()) {
	            if (status.getName().equalsIgnoreCase(name.trim())) {
	                return status;
	            }
	        }
	        log.error("no enum constant for name:"+name);
	        return null; 
	    }

	    // 2️⃣ Method: Get enum by id
	    public static PaymentMethodEnum fromId(int id) {
	        for (PaymentMethodEnum status : PaymentMethodEnum.values()) {
	            if (status.getId() == id) {
	                return status;
	            }
	        }
	        log.error("no enum constant for name:"+id);

	        return null; 
	    }
	

}

