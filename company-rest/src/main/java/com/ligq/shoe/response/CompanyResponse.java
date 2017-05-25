package com.ligq.shoe.response;

import java.util.List;
import java.util.Map;

import org.springframework.hateoas.ResourceSupport;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
@JsonIgnoreProperties(ignoreUnknown=true)
public class CompanyResponse extends ResourceSupport{

	private String uuid;

	private String name;

	private String shortName;

    private String categoryId;
    
	private String subCategoryId;
	
	private String address;

	private String email;
	
	private String servicePhone;
	
	private String bossName;
	
	private String serviceStatus;

	private String icon;
	
	private Map<String,Double> location;

    private List<String> businessHours;
	
	private Double grade;

	private Map<String,String> detail;
	
	private Double distance;

	private String pauseServiceTime;

	public Map<String, String> getDetail() {
		return detail;
	}

	public void setDetail(Map<String, String> detail) {
		this.detail = detail;
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
	
	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getName() {
		if (StringUtils.isEmpty(name)) {
			return "";
		}
		return name;
	}

	public String getShortName() {
		if (StringUtils.isEmpty(shortName)) {
			return "";
		}
		return shortName;
	}

	public String getAddress() {
		if (StringUtils.isEmpty(address)) {
			return "";
		}
		return address;
	}

	public String getEmail() {
		if (StringUtils.isEmpty(email)) {
			return "";
		}
		return email;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getServicePhone() {
		if (StringUtils.isEmpty(servicePhone)) {
			return "";
		}
		return servicePhone;
	}

	public void setServicePhone(String servicePhone) {
		this.servicePhone = servicePhone;
	}

	public String getBossName() {
		if (StringUtils.isEmpty(bossName)) {
			return "";
		}
		return bossName;
	}

	public void setBossName(String bossName) {
		this.bossName = bossName;
	}

	public String getServiceStatus() {
		if (StringUtils.isEmpty(serviceStatus)) {
			return "";
		}
		return serviceStatus;
	}

	public void setServiceStatus(String serviceStatus) {
		this.serviceStatus = serviceStatus;
	}

	public String getIcon() {
		if (StringUtils.isEmpty(icon)) {
			return "";
		}
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public Map<String,Double> getLocation() {
		return location;
	}

	public void setLocation(Map<String,Double> location) {
		this.location = location;
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

    public Double getDistance() {
        if (distance == null) {
            distance = 0d;
        }
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

	public List<String> getBusinessHours() {
		return businessHours;
	}

	public void setBusinessHours(List<String> businessHours) {
		this.businessHours = businessHours;
	}

	public String getPauseServiceTime() {
		return pauseServiceTime;
	}

	public void setPauseServiceTime(String pauseServiceTime) {
		this.pauseServiceTime = pauseServiceTime;
	}	
	
}
