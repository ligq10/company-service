package com.ligq.shoe.constants;

import org.springframework.util.StringUtils;

public enum EmployeeType {
	ADMIN("admin","超级管理员"),
	EMPLOYEE("employee","员工"),
	MANAGER("manager","管理员");
	
	private String value;
	private String desc;
	
	private EmployeeType(String value, String desc){
		this.value = value;
		this.desc = desc;
	}

	public String getValue() {
		return value;
	}

	public String getDesc() {
		return desc;
	}
	
	public static EmployeeType getUserTypeByValue(String value){
		if(StringUtils.isEmpty(value)){
			return null;
		}
		
		for(EmployeeType employeeType : EmployeeType.values()){
			if(value.equals(employeeType.getValue())){
				return employeeType;
			}
		}
		
		return null;
	}
}
