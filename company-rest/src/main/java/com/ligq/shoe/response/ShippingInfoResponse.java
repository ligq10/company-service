package com.ligq.shoe.response;

import java.util.HashMap;
import java.util.Map;

import org.springframework.hateoas.ResourceSupport;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class ShippingInfoResponse extends ResourceSupport{
	
    private String uuid;

    private String status;
    
    private String statusDesc;
    
    private String comment;
    
    private String orderId;

    private Double settAmount;
	
    private String serviceContent;
	
    private String serviceEmployeeId;
    
    private String createdDate;

    private String waiterId;    
    

	public String getStatus() {
		if(StringUtils.isEmpty(status)){
			this.status = "";
		}
		return status;
	}

	public void setStatus(Integer status) {
		if(status==null){
			this.status="";
		}
		this.status = String.valueOf(status.intValue());
	}	
	
	public String getStatusDesc() {
		return statusDesc;
	}

	public void setStatusDesc(String statusDesc) {
		this.statusDesc = statusDesc;
	}

	public String getComment() {
		if(StringUtils.isEmpty(comment)){
			this.comment = "";
		}
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}


	public String getCreatedDate() {
		if(StringUtils.isEmpty(createdDate)){
			return "";
		}
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate =createdDate;
	}

	public String getWaiterId() {
		if(StringUtils.isEmpty(waiterId)){
			waiterId = "";
		}
		return waiterId;
	}

	public void setWaiterId(String waiterId) {
		this.waiterId = waiterId;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public Double getSettAmount() {
		if(null == settAmount){
			return 0d;
		}
		return settAmount;
	}

	public void setSettAmount(Double settAmount) {
		this.settAmount = settAmount;
	}

	public String getServiceContent() {
		if(StringUtils.isEmpty(serviceContent)){
			this.serviceContent = "";
		}
		return serviceContent;
	}

	public void setServiceContent(String serviceContent) {
		
		this.serviceContent = serviceContent;
	}

	public String getServiceEmployeeId() {
		if(StringUtils.isEmpty(serviceEmployeeId)){
			this.serviceEmployeeId = "";
		}
		return serviceEmployeeId;
	}

	public void setServiceEmployeeId(String serviceEmployeeId) {
		this.serviceEmployeeId = serviceEmployeeId;
	}

	public void setStatus(String status) {
		this.status = status;
	}
    
}
