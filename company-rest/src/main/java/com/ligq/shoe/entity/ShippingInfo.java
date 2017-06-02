package com.ligq.shoe.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="shipping_info")
public class ShippingInfo implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = -1131660566346356298L;
	
	@Id
	@Column(name="uuid",length=64)
	private String uuid;

	@Column(name = "order_id",length=64)
	private String orderId;

	@Column(name = "created_date",length=64)
    private Long createdDate;
    
    @Column(name = "waiter_id")
    private String waiterId;    
    
    @Column(name = "status")
    private Integer status;
    
	@Column(name = "settlement_amount")
    private Double settAmount;
	
	@Column(name = "service_content")
    private String serviceContent;
	
	@Column(name = "service_employee_id")
    private String serviceEmployeeId;
    
	@Column(name = "pay_status",length=64)
	private String payStatus;
	
    @Column(name = "comment")
    private String comment;

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

	public Long getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Long createdDate) {
		this.createdDate = createdDate;
	}

	public String getWaiterId() {
		return waiterId;
	}

	public void setWaiterId(String waiterId) {
		this.waiterId = waiterId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
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

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(String payStatus) {
		this.payStatus = payStatus;
	}

	
}
