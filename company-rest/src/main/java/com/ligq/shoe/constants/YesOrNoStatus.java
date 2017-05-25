package com.ligq.shoe.constants;

import org.springframework.util.StringUtils;


public enum YesOrNoStatus {

	YES("yes","是"),
	NO("no","否");
	
	private String value;
	private String desc;
	
	private YesOrNoStatus(String value, String desc){
		this.value = value;
		this.desc = desc;
	}

	public String getValue() {
		return value;
	}

	public String getDesc() {
		return desc;
	}
	
	public static YesOrNoStatus getYesOrNoStatusByValue(String value){
		if(StringUtils.isEmpty(value)){
			return null;
		}
		
		for(YesOrNoStatus yesOrNoStatus : YesOrNoStatus.values()){
			if(value.equals(yesOrNoStatus.getValue())){
				return yesOrNoStatus;
			}
		}
		return null;
	}
}
