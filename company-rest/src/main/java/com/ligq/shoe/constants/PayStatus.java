package com.ligq.shoe.constants;

import org.springframework.util.StringUtils;


public enum PayStatus {

	YES("YES","已付款"),
	NO("NO","未付款");
	
	private String value;
	private String desc;
	
	private PayStatus(String value, String desc){
		this.value = value;
		this.desc = desc;
	}

	public String getValue() {
		return value;
	}

	public String getDesc() {
		return desc;
	}
	
	public static PayStatus getPayStatusByValue(String value){
		if(StringUtils.isEmpty(value)){
			return null;
		}
		
		for(PayStatus payStatus : PayStatus.values()){
			if(value.equals(payStatus.getValue())){
				return payStatus;
			}
		}
		return null;
	}
}
