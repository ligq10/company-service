package com.ligq.shoe.constants;

import org.springframework.util.StringUtils;

public enum LogicStatus {
	YES("YES","是"),
	NO("NO","否");
	
	private String value;
	private String desc;
	
	private LogicStatus(String value, String desc){
		this.value = value;
		this.desc = desc;
	}

	public String getValue() {
		return value;
	}

	public String getDesc() {
		return desc;
	}
	
	public static LogicStatus getLogicStatusByValue(String value){
		if(StringUtils.isEmpty(value)){
			return null;
		}
		
		for(LogicStatus logicStatus : LogicStatus.values()){
			if(value.equalsIgnoreCase(logicStatus.getValue())){
				return logicStatus;
			}
		}
		
		return null;
	}
}
