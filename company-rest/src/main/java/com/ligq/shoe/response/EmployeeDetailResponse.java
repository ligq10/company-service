package com.ligq.shoe.response;

import org.springframework.hateoas.ResourceSupport;
import org.springframework.util.StringUtils;

public class EmployeeDetailResponse extends ResourceSupport{

	private String uuid;

	private String identification;

	private String defaultpsd;

	private String gender;

	private String birthday;

	private Integer status;

	private String name;

	private Integer age;

	private String address;

	private Integer level;

	private String description;

	private String companyId;

	private String loginId;	

	private String departmentCode;

	private String title;

	private Double credit;

	private String homeAddress;

	private String landlineNo;

	private String faxNo;

	private String mobileNo;

	private String email;

	private String isAdmin;

	private String profileUrl;

	private String marketingUrl;

	private String feedbackUrl;

	private String supportInfo;

	private String createdBy;

	private String createdTimestamp;

	private String modifiedBy;

	private String modifiedTimestamp;

	private String isdeleted;	

	private Double grade;
	
	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getIdentification() {
		if (StringUtils.isEmpty(identification)) {
			return "";
		}
		return identification;
	}

	public String getDefaultpsd() {
		if (StringUtils.isEmpty(defaultpsd)) {
			return "";
		}
		return defaultpsd;
	}

	public String getGender() {
		if (StringUtils.isEmpty(gender)) {
			return "";
		}
		return gender;
	}

	public String getBirthday() {
		if (StringUtils.isEmpty(birthday)) {
			return "";
		}
		return birthday;
	}

	public Integer getStatus() {
		return status;
	}

	public String getName() {
		if (StringUtils.isEmpty(name)) {
			return "";
		}
		return name;
	}

	public Integer getAge() {
		return age;
	}

	public String getAddress() {
		if (StringUtils.isEmpty(address)) {
			return "";
		}
		return address;
	}

	public Integer getLevel() {
		return level;
	}

	public String getDescription() {
		if (StringUtils.isEmpty(description)) {
			return "";
		}
		return description;
	}

	public String getCompanyId() {
		if (StringUtils.isEmpty(companyId)) {
			return "";
		}
		return companyId;
	}

	public String getLoginId() {
		if (StringUtils.isEmpty(loginId)) {
			return "";
		}
		return loginId;
	}

	public String getDepartmentCode() {
		if (StringUtils.isEmpty(departmentCode)) {
			return "";
		}
		return departmentCode;
	}

	public String getTitle() {
		if (StringUtils.isEmpty(title)) {
			return "";
		}
		return title;
	}

	public Double getCredit() {
		if (null == credit) {
			return credit = 0.0;
		}
		return credit;
	}

	public String getHomeAddress() {
		if (StringUtils.isEmpty(homeAddress)) {
			return "";
		}
		return homeAddress;
	}

	public String getLandlineNo() {
		if (StringUtils.isEmpty(landlineNo)) {
			return "";
		}
		return landlineNo;
	}

	public String getFaxNo() {
		if (StringUtils.isEmpty(faxNo)) {
			return "";
		}
		return faxNo;
	}

	public String getMobileNo() {
		if (StringUtils.isEmpty(mobileNo)) {
			return "";
		}
		return mobileNo;
	}

	public String getEmail() {
		if (StringUtils.isEmpty(email)) {
			return "";
		}
		return email;
	}

	public String getIsAdmin() {
		if (StringUtils.isEmpty(isAdmin)) {
			return "";
		}
		return isAdmin;
	}

	public String getCreatedBy() {
		if (StringUtils.isEmpty(createdBy)) {
			return "";
		}
		return createdBy;
	}

	public String getCreatedTimestamp() {
		if (StringUtils.isEmpty(createdTimestamp)) {
			return "";
		}
		return createdTimestamp;
	}

	public String getModifiedBy() {
		if (StringUtils.isEmpty(modifiedBy)) {
			return "";
		}
		return modifiedBy;
	}

	public String getModifiedTimestamp() {
		if (StringUtils.isEmpty(modifiedTimestamp)) {
			return "";
		}
		return modifiedTimestamp;
	}

	public String getIsdeleted() {
		if (StringUtils.isEmpty(isdeleted)) {
			return "";
		}
		return isdeleted;
	}

	public String getProfileUrl() {
		if (StringUtils.isEmpty(profileUrl)) {
			return "";
		}
		return profileUrl;
	}

	public String getMarketingUrl() {
		if (StringUtils.isEmpty(marketingUrl)) {
			return "";
		}
		return marketingUrl;
	}

	public String getFeedbackUrl() {
		if (StringUtils.isEmpty(feedbackUrl)) {
			return "";
		}
		return feedbackUrl;
	}

	public String getSupportInfo() {
		if (StringUtils.isEmpty(supportInfo)) {
			return "";
		}
		return supportInfo;
	}

	public void setIdentification(String identification) {
		this.identification = identification;
	}

	public void setDefaultpsd(String defaultpsd) {
		this.defaultpsd = defaultpsd;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public void setDepartmentCode(String departmentCode) {
		this.departmentCode = departmentCode;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setCredit(Double credit) {
		this.credit = credit;
	}

	public void setHomeAddress(String homeAddress) {
		this.homeAddress = homeAddress;
	}

	public void setLandlineNo(String landlineNo) {
		this.landlineNo = landlineNo;
	}

	public void setFaxNo(String faxNo) {
		this.faxNo = faxNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setIsAdmin(String isAdmin) {
		this.isAdmin = isAdmin;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public void setCreatedTimestamp(String createdTimestamp) {
		this.createdTimestamp = createdTimestamp;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public void setModifiedTimestamp(String modifiedTimestamp) {
		this.modifiedTimestamp = modifiedTimestamp;
	}

	public void setIsdeleted(String isdeleted) {
		this.isdeleted = isdeleted;
	}

	public void setProfileUrl(String profileUrl) {
		this.profileUrl = profileUrl;
	}

	public void setMarketingUrl(String marketingUrl) {
		this.marketingUrl = marketingUrl;
	}

	public void setFeedbackUrl(String feedbackUrl) {
		this.feedbackUrl = feedbackUrl;
	}

	public void setSupportInfo(String supportInfo) {
		this.supportInfo = supportInfo;
	}

	public Double getGrade() {
		if(null == grade){
			grade = 0d;
		}
		return grade;
	}

	public void setGrade(Double grade) {
		this.grade = grade;
	}

}
