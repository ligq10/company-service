package com.ligq.shoe.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class ShippingInfoAddRequest {
   
    private Integer status;
    
    private String comment;

    private String waiterId;
    
    private Double settAmount;//结算金额
    
    private String serviceEmployeeId;//接单员工
    
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}	
	
	public String getWaiterId() {
		return waiterId;
	}

	public void setWaiterId(String waiterId) {
		this.waiterId = waiterId;
	}


	public Double getSettAmount() {
		if(null == settAmount){
			settAmount = 0d;
		}
		return settAmount;
	}

	public void setSettAmount(Double settAmount) {
		this.settAmount = settAmount;
	}

	public String getServiceEmployeeId() {
		return serviceEmployeeId;
	}

	public void setServiceEmployeeId(String serviceEmployeeId) {
		this.serviceEmployeeId = serviceEmployeeId;
	}
    
}
