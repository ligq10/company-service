package com.ligq.shoe.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "orders")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Orders {

	@Id
	@Column(name="uuid",length=64)
	private String uuid;

	@Column(name = "company_id",length=64)
	private String companyId;

	@Column(name = "creater_id",length=64)
	private String createrId;

	@Column(name = "customer_id",length=64)
	private String customerId;

	@Column(name = "customer_name",length=256)
	private String customerName;

	@Column(name = "customer_tel",length=64)
	private String customerTel;
	
	@Column(name = "order_no",length=128)
	private String orderNo;

	@Column(name = "current_status",columnDefinition="text")
	private Integer currentStatus;

	@Column(name = "comment",columnDefinition="text")
	private String comment;

	@Column(name = "created_date")
	private Date createdDate;

	@Column(name = "service_time")
	private Date serviceTime;

	@Column(name = "expedite")
	private String expedite;
	
	@Column(name = "order_amount",precision=6)
    private Double orderAmount;
	
	@Column(name = "settlement_amount",precision=6)
    private Double settAmount;
	
	@Column(name = "service_content",columnDefinition="text")
    private String serviceContent;
	
	@Column(name = "service_employee_id",length=64)
    private String serviceEmployeeId;

	@Column(name = "shipping_address",columnDefinition="text")
	private String shippingAddress;

	@Column(name = "pay_status",length=64)
	private String payStatus;
	
	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getCreaterId() {
		return createrId;
	}

	public void setCreaterId(String createrId) {
		this.createrId = createrId;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public Integer getCurrentStatus() {
		return currentStatus;
	}

	public void setCurrentStatus(Integer currentStatus) {
		this.currentStatus = currentStatus;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getServiceTime() {
		return serviceTime;
	}

	public void setServiceTime(Date serviceTime) {
		this.serviceTime = serviceTime;
	}

	public String getExpedite() {
		return expedite;
	}

	public void setExpedite(String expedite) {
		this.expedite = expedite;
	}

	public Double getOrderAmount() {
		return orderAmount;
	}

	public void setOrderAmount(Double orderAmount) {
		this.orderAmount = orderAmount;
	}

	public Double getSettAmount() {
		return settAmount;
	}

	public void setSettAmount(Double settAmount) {
		this.settAmount = settAmount;
	}

	public String getServiceContent() {
		return serviceContent;
	}

	public void setServiceContent(String serviceContent) {
		this.serviceContent = serviceContent;
	}

	public String getServiceEmployeeId() {
		return serviceEmployeeId;
	}

	public void setServiceEmployeeId(String serviceEmployeeId) {
		this.serviceEmployeeId = serviceEmployeeId;
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

	public String getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(String payStatus) {
		this.payStatus = payStatus;
	}
	
}
