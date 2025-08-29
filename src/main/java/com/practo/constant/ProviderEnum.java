package com.practo.constant;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Slf4j
public enum ProviderEnum 
{
	STRIPE(1,"STRIPE");
	
	private int id;
	private String name;
	 ProviderEnum(int id, String name) {
		this.id = id;
		this.name = name;
	}
	 
	 //Method: Get enum by name
	 public static ProviderEnum fromName(String name) {
	       // if (name == null) return null;
	        for (ProviderEnum status : values()) {
	            if (status.name.equalsIgnoreCase(name.trim())) {
	                return status;
	            }
	        }
	        log.error("no enum constant for name:"+name);
	        return null; 
	    }

	    // 2️⃣ Method: Get enum by id
	    public static ProviderEnum fromId(int id) {
	        for (ProviderEnum status : values()) {
	            if (status.id == id) {
	                return status;
	            }
	        }
	        log.error("no enum constant for id:"+id);

	        return null; 
	    }
	

}

