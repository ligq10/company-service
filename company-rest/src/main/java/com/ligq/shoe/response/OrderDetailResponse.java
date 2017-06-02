package com.ligq.shoe.response;

import java.util.List;

import org.springframework.hateoas.ResourceSupport;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ligq.shoe.entity.OrderToProductItem;

@JsonIgnoreProperties(ignoreUnknown=true)
public class OrderDetailResponse extends ResourceSupport {
	
	private String uuid;
	private String comment;
	private Integer currentStatus;
	private String currentStatusDesc;
	private String orderNo;
	private String companyId;
	private String createdDate;
	private String serviceTime;
	private String serviceContent;
	private Double orderAmount;
	private Double settAmount;
	private String serviceEmployeeId;
	private String customerName;
	private String customerTel;	
	private String shippingAddress;
	private String expedite;
	private String payStatus;
	private String payStatusDesc;
	private List<ShippingInfoResponse> shippingInfos;
	private List<OrderToProductItem> orderItems;
	
	public String getExpedite() {
		return expedite;
	}

	public void setExpedite(String expedite) {
		this.expedite = expedite;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getComment() {
		if (StringUtils.isEmpty(comment)) {
			return "";
		}
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Integer getCurrentStatus() {
		if (StringUtils.isEmpty(currentStatus)) {
			return 0;
		}
		return currentStatus;
	}

	public void setCurrentStatus(Integer currentStatus) {
		this.currentStatus = currentStatus;
	}

	public String getCurrentStatusDesc() {
		return currentStatusDesc;
	}

	public void setCurrentStatusDesc(String currentStatusDesc) {
		this.currentStatusDesc = currentStatusDesc;
	}

	public String getOrderNo() {
		if (StringUtils.isEmpty(orderNo)) {
			return "";
		}
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public String getServiceTime() {
		return serviceTime;
	}

	public void setServiceTime(String serviceTime) {
		this.serviceTime = serviceTime;
	}

	public String getServiceContent() {
		if(StringUtils.isEmpty(this.serviceContent)){
			return "";
		}
		return serviceContent;
	}

	public void setServiceContent(String serviceContent) {
		this.serviceContent = serviceContent;
	}

	public Double getOrderAmount() {
		if(null == orderAmount){
			orderAmount = 0d;
		}
		return orderAmount;
	}

	public void setOrderAmount(Double orderAmount) {
		this.orderAmount = orderAmount;
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
		if(StringUtils.isEmpty(this.serviceEmployeeId)){
			return "";
		}
		return serviceEmployeeId;
	}

	public void setServiceEmployeeId(String serviceEmployeeId) {
		this.serviceEmployeeId = serviceEmployeeId;	
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
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

	public String getShippingAddress() {
		return shippingAddress;
	}

	public void setShippingAddress(String shippingAddress) {
		this.shippingAddress = shippingAddress;
	}

	public List<ShippingInfoResponse> getShippingInfos() {
		return shippingInfos;
	}

	public void setShippingInfos(List<ShippingInfoResponse> shippingInfos) {
		this.shippingInfos = shippingInfos;
	}

	public List<OrderToProductItem> getOrderItems() {
		return orderItems;
	}

	public void setOrderItems(List<OrderToProductItem> orderItems) {
		this.orderItems = orderItems;
	}

	public String getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(String payStatus) {
		this.payStatus = payStatus;
	}

	public String getPayStatusDesc() {
		return payStatusDesc;
	}

	public void setPayStatusDesc(String payStatusDesc) {
		this.payStatusDesc = payStatusDesc;
	}

}
