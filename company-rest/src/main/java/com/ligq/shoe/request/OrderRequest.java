package com.ligq.shoe.request;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class OrderRequest {
	
	private List<OrderItemRequest> items;    
	private String serviceTime;
	private String shippingAddress;
	private String comment;
	private String customerName;
	private String customerTel;
	private String expedite;
	private String payStatus;

	public List<OrderItemRequest> getItems() {
		return items;
	}
	public void setItems(List<OrderItemRequest> items) {
		this.items = items;
	}
	public String getServiceTime() {
		return serviceTime;
	}
	public void setServiceTime(String serviceTime) {
		this.serviceTime = serviceTime;
	}
	public String getShippingAddress() {
		return shippingAddress;
	}
	public void setShippingAddress(String shippingAddress) {
		this.shippingAddress = shippingAddress;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getCustomerTel() {
		return customerTel;
	}
	public void setCustomerTel(String customerTel) {
		this.customerTel = customerTel;
	}
	public String getExpedite() {
		return expedite;
	}
	public void setExpedite(String expedite) {
		this.expedite = expedite;
	}
	public String getPayStatus() {
		return payStatus;
	}
	public void setPayStatus(String payStatus) {
		this.payStatus = payStatus;
	}

}
