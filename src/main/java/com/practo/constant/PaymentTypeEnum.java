package com.practo.constant;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Slf4j
public enum PaymentTypeEnum 
{
	SALE(1,"SALE");
	
	private int id;
	private String name;
	 PaymentTypeEnum(int id, String name) {
		this.id = id;
		this.name = name;
	}
	 
	 //Method: Get enum by name
	 public static PaymentTypeEnum fromName(String name) {
	        //if (name == null) return null;
	        for (PaymentTypeEnum status : PaymentTypeEnum.values()) {
	            if (status.getName().equalsIgnoreCase(name.trim())) {
	                return status;
	            }
	        }
	        log.error("no enum constant for name:"+name);
	        return null; 
	    }

	    // 2️⃣ Method: Get enum by id
	    public static PaymentTypeEnum fromId(int id) {
	        for (PaymentTypeEnum status : PaymentTypeEnum.values()) {
	            if (status.getId() == id) {
	                return status;
	            }
	        }
	        log.error("no enum constant for name:"+id);

	        return null; 
	    }
	

}

