package com.ligq.shoe.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "company")
public class Company {

	@Id
	@Column(name="uuid",length=64)
	private String uuid;
	
	//区域ID
	@Column(name="area_uuid",length=64)
	private String areaUuid;
	
	//分类ID
	@Column(name="category_id",length=64)
	private String categoryId;
	
	//子分类ID
	@Column(name="sub_category_id",length=64)
	private String subCategoryId;
	
	//分类名称
	@Column(name="company_class",length=128)
	private String companyClass;

	//子分类名称
	@Column(name="company_sub_class",length=128)
	private String companySubClass;
	
	//医院名称
	@Column(name="name",length=128)
	private String name;

	//医院管理员账号
	@Column(name="account_no",length=128)
	private String accountNo;
	
	//医院管理员密码
	@Column(name="account_password",length=128)
	private String accountPassword;

	//医院管理员姓名
	@Column(name="boss_name",length=128)
	private String bossName;

	//医院管理员电话号码
	@Column(name="boss_phone",length=128)
	private String bossPhone;

	//营业时间
	@Column(name="business_hours",length=512)
	private String businessHours;

	
	@Column(name="address",length=512)
	private String address;

	
	@Column(name="created_by",length=128)
	private String createdBy;

	
	@Column(name="created_timestamp")
	private Date createdTimestamp;

	
	@Column(name="description",length=512)
	private String description;

	
	@Column(name="email",length=128)
	private String email;

	
	@Column(name="fax_no",length=128)
	private String fax_no;

	
	@Column(name="group_id",length=64)
	private String groupId;

	
	@Column(name="group_name",length=512)
	private String groupName;
	
	@Column(name="icon",length=64)
	public String icon;
	
	
	@Column(name="isdeleted",length=16)
	private String isdeleted;

	
	@Column(name="landline_no",length=128)
	private String landlineNo;

	
	@Column(name="modified_by",length=128)
	private String modifiedBy;

	
	@Column(name="modified_timestamp")
	private Date modifiedTimestamp;

	
	@Column(name="note",length=128)
	private String note;
	
	
	@Column(name="open_time",length=128)
	private String openTime;
	
	
	@Column(name="pageurl",length=128)
	private String pageurl;

	
	@Column(name="service_phone",length=128)
	private String servicePhone;

	
	@Column(name="service_status",length=128)
	private String serviceStatus;

	
	@Column(name="short_name",length=128)
	private String shortName;
		
	
	@Column(name="lon",precision=6)
	private double lon;
	
	
	@Column(name="lat",precision=6)
	private double lat;
	
	@Column(name="pause_service_time")
	private Date pauseServiceTime;
	
	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getAreaUuid() {
		return areaUuid;
	}

	public void setAreaUuid(String areaUuid) {
		this.areaUuid = areaUuid;
	}

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public String getSubCategoryId() {
		return subCategoryId;
	}

	public void setSubCategoryId(String subCategoryId) {
		this.subCategoryId = subCategoryId;
	}

	public String getCompanyClass() {
		return companyClass;
	}

	public void setCompanyClass(String companyClass) {
		this.companyClass = companyClass;
	}

	public String getCompanySubClass() {
		return companySubClass;
	}

	public void setCompanySubClass(String companySubClass) {
		this.companySubClass = companySubClass;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public String getAccountPassword() {
		return accountPassword;
	}

	public void setAccountPassword(String accountPassword) {
		this.accountPassword = accountPassword;
	}

	public String getBossName() {
		return bossName;
	}

	public void setBossName(String bossName) {
		this.bossName = bossName;
	}

	public String getBossPhone() {
		return bossPhone;
	}

	public void setBossPhone(String bossPhone) {
		this.bossPhone = bossPhone;
	}

	public String getBusinessHours() {
		return businessHours;
	}

	public void setBusinessHours(String businessHours) {
		this.businessHours = businessHours;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedTimestamp() {
		return createdTimestamp;
	}

	public void setCreatedTimestamp(Date createdTimestamp) {
		this.createdTimestamp = createdTimestamp;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFax_no() {
		return fax_no;
	}

	public void setFax_no(String fax_no) {
		this.fax_no = fax_no;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getIsdeleted() {
		return isdeleted;
	}

	public void setIsdeleted(String isdeleted) {
		this.isdeleted = isdeleted;
	}

	public String getLandlineNo() {
		return landlineNo;
	}

	public void setLandlineNo(String landlineNo) {
		this.landlineNo = landlineNo;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Date getModifiedTimestamp() {
		return modifiedTimestamp;
	}

	public void setModifiedTimestamp(Date modifiedTimestamp) {
		this.modifiedTimestamp = modifiedTimestamp;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getOpenTime() {
		return openTime;
	}

	public void setOpenTime(String openTime) {
		this.openTime = openTime;
	}

	public String getPageurl() {
		return pageurl;
	}

	public void setPageurl(String pageurl) {
		this.pageurl = pageurl;
	}

	public String getServicePhone() {
		return servicePhone;
	}

	public void setServicePhone(String servicePhone) {
		this.servicePhone = servicePhone;
	}

	public String getServiceStatus() {
		return serviceStatus;
	}

	public void setServiceStatus(String serviceStatus) {
		this.serviceStatus = serviceStatus;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public double getLon() {
		return lon;
	}

	public void setLon(double lon) {
		this.lon = lon;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public Date getPauseServiceTime() {
		return pauseServiceTime;
	}

	public void setPauseServiceTime(Date pauseServiceTime) {
		this.pauseServiceTime = pauseServiceTime;
	}

}
