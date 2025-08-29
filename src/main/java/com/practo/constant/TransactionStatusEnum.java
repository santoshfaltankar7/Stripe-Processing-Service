package com.practo.constant;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Slf4j
public enum TransactionStatusEnum 
{
	CREATED(1,"CREATED"),
	INITIATED(2,"INITIATED"),
	PENDING(3,"PENDING"),
	SUCCESS(4,"SUCCESS"),
	FAILED(5,"FAILED");
	
	private int id;
	private String name;
	 TransactionStatusEnum(int id, String name) {
		this.id = id;
		this.name = name;
	}
	 
	 //Method: Get enum by name
	 public static TransactionStatusEnum fromName(String name) {
	       // if (name == null) return null;
	        for (TransactionStatusEnum status : TransactionStatusEnum.values()) {
	            if (status.getName().equalsIgnoreCase(name.trim())) {
	                return status;
	            }
	        }
	        log.error("no enum constant for name:"+name);
	        return null; 
	    }

	    // 2️⃣ Method: Get enum by id
	    public static TransactionStatusEnum fromId(int id) {
	        for (TransactionStatusEnum status : TransactionStatusEnum.values()) {
	            if (status.getId() == id) {
	                return status;
	            }
	        }
	        log.error("no enum constant for name:"+id);

	        return null; 
	    }
	

}

