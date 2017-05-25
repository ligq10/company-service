package com.ligq.shoe.constants;

import org.springframework.util.StringUtils;

public enum ProductPriceStatus {

	ONLINE(1,"已上架"), OFFLINE(2,"已下架");
	private int status;
	private String description;

	private ProductPriceStatus(int status,String description) {
		this.description = description;
		this.status=status;
	}
	
	public int getStatus() {
		return status;
	}
	
	public String getDescription() {
		return description;
	}
	
	public static ProductPriceStatus getProductPriceStatusByStatus(String status){
		if(StringUtils.isEmpty(status)){
			return null;
		}
		
		for(ProductPriceStatus productPriceStatus : ProductPriceStatus.values()){
			if(status.equals(productPriceStatus.getStatus())){
				return productPriceStatus;
			}
		}
		return null;
	}

	public static ProductPriceStatus getProductPriceStatusByName(String name){
		if(StringUtils.isEmpty(name)){
			return null;
		}
		
		if(ProductPriceStatus.OFFLINE.name().equalsIgnoreCase(name)){
			return ProductPriceStatus.OFFLINE;
		}else if(ProductPriceStatus.ONLINE.name().equalsIgnoreCase(name)){
			return ProductPriceStatus.ONLINE;
		}else{
			return null;
		}
	}
}
