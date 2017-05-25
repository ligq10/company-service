package com.ligq.shoe.constants;

import org.springframework.util.StringUtils;

public enum CompanyServiceStatus {

	SERVICING(1,"服务中"), PAUSE_SREVICE(2,"暂停服务"), STOP_SERVICE(3,"停止服务");
	
	private Integer value;
	private String desc;
	
	private CompanyServiceStatus(Integer value, String desc){
		this.value = value;
		this.desc = desc;
	}

	public Integer getValue() {
		return value;
	}

	public String getDesc() {
		return desc;
	}
	
	public static CompanyServiceStatus getCompanyServiceStatusByValue(Integer value){
		if(StringUtils.isEmpty(value)){
			return null;
		}
		
		for(CompanyServiceStatus companyServiceStatus : CompanyServiceStatus.values()){
			if(value == companyServiceStatus.getValue()){
				return companyServiceStatus;
			}
		}
		return null;
	}
	
	public static CompanyServiceStatus getCompanyServiceStatusByName(String name){
		if(StringUtils.isEmpty(name)){
			return null;
		}
		
	    if(name.equals(CompanyServiceStatus.SERVICING.name())){
			return CompanyServiceStatus.SERVICING;
		}else if(name.equals(CompanyServiceStatus.PAUSE_SREVICE.name())){
			return CompanyServiceStatus.PAUSE_SREVICE;			
		}else if(name.equals(CompanyServiceStatus.STOP_SERVICE.name())){
			return CompanyServiceStatus.STOP_SERVICE;						
		}		
		return null;
	}
}
