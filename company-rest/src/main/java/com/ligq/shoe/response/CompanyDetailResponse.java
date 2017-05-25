package com.ligq.shoe.response;


import java.util.List;
import java.util.Map;

import org.springframework.hateoas.ResourceSupport;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class CompanyDetailResponse extends ResourceSupport{
	
	private String uuid;
	
	private String account;

	private String pageUrl;

	private String name;

	private String shortName;

	private String companyClass;

	private String companySubClass;

	private String address;

	private String landlineNo;

	private String faxNo;

	private String email;

	private String openTime;

	private String bossName;

	private String bossPhone;

	private String note;
	
	private Map<String,Double> location;

	private String isdeleted;	
	
    private String categoryId;
	
	private String subCategoryId;
	
	private String accountNo;
	
	private String servicePhone;
	
	private String description;
	
    private String serviceStatus;
    
    private String groupId;
    
    private List<String> businessHours;
    
    private String icon;
    
    private Map<String,String> detail;
	
	private String createdBy;

	private String createdTimestamp;

	private String modifiedBy;

	private String modifiedTimestamp;

	private Double grade;

	private String pauseServiceTime;
	
	public String getUuid() {
		if(StringUtils.isEmpty(this.uuid)){
			return "";
		}
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getAccount() {
		if(StringUtils.isEmpty(this.account)){
			return "";
		}
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPageUrl() {
		if(StringUtils.isEmpty(this.pageUrl)){
			return "";
		}
		return pageUrl;
	}

	public void setPageUrl(String pageUrl) {
		this.pageUrl = pageUrl;
	}

	public String getName() {
		if(StringUtils.isEmpty(this.name)){
			return "";
		}
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getShortName() {
		if(StringUtils.isEmpty(this.shortName)){
			return "";
		}
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public String getCompanyClass() {
		if(StringUtils.isEmpty(this.companyClass)){
			return "";
		}
		return companyClass;
	}

	public void setCompanyClass(String companyClass) {
		this.companyClass = companyClass;
	}

	public String getCompanySubClass() {
		if(StringUtils.isEmpty(this.companySubClass)){
			return "";
		}
		return companySubClass;
	}

	public void setCompanySubClass(String companySubClass) {
		this.companySubClass = companySubClass;
	}

	public String getAddress() {
		if(StringUtils.isEmpty(this.address)){
			return "";
		}
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getLandlineNo() {
		if(StringUtils.isEmpty(this.landlineNo)){
			return "";
		}
		return landlineNo;
	}

	public void setLandlineNo(String landlineNo) {
		this.landlineNo = landlineNo;
	}

	public String getFaxNo() {
		if(StringUtils.isEmpty(this.faxNo)){
			return "";
		}
		return faxNo;
	}

	public void setFaxNo(String faxNo) {
		this.faxNo = faxNo;
	}

	public String getEmail() {
		if(StringUtils.isEmpty(this.email)){
			return "";
		}
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getOpenTime() {
		if(StringUtils.isEmpty(this.openTime)){
			return "";
		}
		return openTime;
	}

	public void setOpenTime(String openTime) {
		this.openTime = openTime;
	}

	public String getBossName() {
		if(StringUtils.isEmpty(this.bossName)){
			return "";
		}
		return bossName;
	}

	public void setBossName(String bossName) {
		this.bossName = bossName;
	}

	public String getBossPhone() {
		if(StringUtils.isEmpty(this.bossPhone)){
			return "";
		}
		return bossPhone;
	}

	public void setBossPhone(String bossPhone) {
		this.bossPhone = bossPhone;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Map<String, Double> getLocation() {
		return location;
	}

	public void setLocation(Map<String, Double> location) {
		this.location = location;
	}

	public String getIsdeleted() {
		return isdeleted;
	}

	public void setIsdeleted(String isdeleted) {
		this.isdeleted = isdeleted;
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

	public String getAccountNo() {
		if(StringUtils.isEmpty(this.accountNo)){
			return "";
		}
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public String getServicePhone() {
		if(StringUtils.isEmpty(this.servicePhone)){
			return "";
		}
		return servicePhone;
	}

	public void setServicePhone(String servicePhone) {
		this.servicePhone = servicePhone;
	}

	public String getDescription() {
		if(StringUtils.isEmpty(this.description)){
			return "";
		}
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getServiceStatus() {
		if(StringUtils.isEmpty(this.serviceStatus)){
			return "";
		}
		return serviceStatus;
	}

	public void setServiceStatus(String serviceStatus) {
		this.serviceStatus = serviceStatus;
	}

	public String getGroupId() {
		if(StringUtils.isEmpty(this.groupId)){
			return "";
		}
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public List<String> getBusinessHours() {
		return businessHours;
	}

	public void setBusinessHours(List<String> businessHours) {
		this.businessHours = businessHours;
	}

	public String getIcon() {
		if(StringUtils.isEmpty(this.icon)){
			return "";
		}
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public Map<String, String> getDetail() {
		return detail;
	}

	public void setDetail(Map<String, String> detail) {
		this.detail = detail;
	}

	public String getCreatedBy() {
		if(StringUtils.isEmpty(this.createdBy)){
			return "";
		}
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getCreatedTimestamp() {
		if(StringUtils.isEmpty(this.createdTimestamp)){
			return "";
		}
		return createdTimestamp;
	}

	public void setCreatedTimestamp(String createdTimestamp) {
		this.createdTimestamp = createdTimestamp;
	}

	public String getModifiedBy() {
		if(StringUtils.isEmpty(this.modifiedBy)){
			return "";
		}
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public String getModifiedTimestamp() {
		if(StringUtils.isEmpty(this.modifiedTimestamp)){
			return "";
		}
		return modifiedTimestamp;
	}

	public void setModifiedTimestamp(String modifiedTimestamp) {
		this.modifiedTimestamp = modifiedTimestamp;
	}

	public Double getGrade() {
		if(null == this.grade){
			return 0d;
		}
		return grade;
	}

	public void setGrade(Double grade) {
		this.grade = grade;
	}

	public String getPauseServiceTime() {
		if(StringUtils.isEmpty(pauseServiceTime)){
			return "";
		}
		return pauseServiceTime;
	}

	public void setPauseServiceTime(String pauseServiceTime) {
		this.pauseServiceTime = pauseServiceTime;
	}
	
}
