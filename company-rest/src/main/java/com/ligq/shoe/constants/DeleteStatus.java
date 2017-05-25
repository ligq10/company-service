package com.ligq.shoe.constants;

import org.springframework.util.StringUtils;


public enum DeleteStatus {

	YES("YES","已删除"),
	NO("NO","未删除");
	
	private String value;
	private String desc;
	
	private DeleteStatus(String value, String desc){
		this.value = value;
		this.desc = desc;
	}

	public String getValue() {
		return value;
	}

	public String getDesc() {
		return desc;
	}
	
	public static DeleteStatus getDeleteStatusByValue(String value){
		if(StringUtils.isEmpty(value)){
			return null;
		}
		
		for(DeleteStatus deleteStatus : DeleteStatus.values()){
			if(value.equals(deleteStatus.getValue())){
				return deleteStatus;
			}
		}
		return null;
	}
}
