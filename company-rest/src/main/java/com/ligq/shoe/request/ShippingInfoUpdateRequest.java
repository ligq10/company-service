package com.ligq.shoe.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class ShippingInfoUpdateRequest {

    private Integer status;
    
    private String comment;

    private String waiterId;
    

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

}
