package com.ligq.shoe.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="product_price")
public class ProductPrice {

	@Id
	@Column(name="uuid",length=64)
	private String uuid;
	
	@Column(name = "company_id",length=64)
	private String companyId;
	
	@Column(name = "product_name",length=256)
	private String productName;

	@Column(name = "price",precision=2)
	private Double price;
	
	@Column(name = "unit",length=256)
	private String unit;
	
	@Column(name = "description",length=512)
	private String description;
	
	@Column(name = "icon",length=64)
    private String icon;
		
	@Column(name = "isdeleted",length=32)
	private String isdeleted;
	
	@Column(name = "status",length=32)
	private String status;
	
	@Column(name = "status_value",length=16)
	private Integer statusValue;
	
	@Column(name = "created_time")
	private Date createdTime;
	
	@Column(name = "created_by",length=256)
	private String createdBy;
	
	@Column(name = "modified_time")
	private Date modifiedTime;
	
	@Column(name = "modified_by",length=256)
	private String modifiedBy;
	
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}
	
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getIcon() {
		return icon;
	}
	
	public void setIcon(String icon) {
		this.icon = icon;
	}
	
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	public int getStatusValue() {
		return statusValue;
	}
	
	public void setStatusValue(Integer statusValue) {
		this.statusValue = statusValue;
	}
	
	public Date getCreatedTime() {
		return createdTime;
	}
	
	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}
	
	public String getCreatedBy() {
		return createdBy;
	}
	
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	
	public Date getModifiedTime() {
		return modifiedTime;
	}
	
	public void setModifiedTime(Date modifiedTime) {
		this.modifiedTime = modifiedTime;
	}
	
	public String getModifiedBy() {
		return modifiedBy;
	}
	
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	
	public String getIsdeleted() {
		return isdeleted;
	}
	
	public void setIsdeleted(String isdeleted) {
		this.isdeleted = isdeleted;
	}
	
	public String getCompanyId() {
		return companyId;
	}
	
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
}
