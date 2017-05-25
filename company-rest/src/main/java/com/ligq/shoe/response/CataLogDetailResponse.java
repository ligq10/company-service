package com.ligq.shoe.response;

import org.springframework.hateoas.ResourceSupport;
import org.springframework.util.StringUtils;



public class CataLogDetailResponse extends ResourceSupport {

	private String uuid;
	
	private String code;
	
	private String name;
	
	private Integer sort;
	
	private String description;
	
	private String icon;
		
	private String createdBy;
	
	private String createdTimestamp;
	
	private String modifiedBy;
	
	private String modifiedTimestamp;

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}	
	
	public String getCode() {
		if(StringUtils.isEmpty(code)){
			code = "";
		}
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public String getDescription() {
		if(this.description==null){
			return "";
		}
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getIcon() {
		if(this.icon==null){
			return "";
		}
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
		
	}


	public String getCreatedBy() {
		if(this.createdBy==null){
			return "";
		}
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getCreatedTimestamp() {
		if(this.createdTimestamp==null){
			return "";
		}
		return createdTimestamp;
	}

	public void setCreatedTimestamp(String createdTimestamp) {
		this.createdTimestamp = createdTimestamp;
	}

	public String getModifiedBy() {
		if(this.modifiedBy==null){
			return "";
		}
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public String getModifiedTimestamp() {
		if(this.modifiedTimestamp==null){
			return "";
		}
		return modifiedTimestamp;
	}

	public void setModifiedTimestamp(String modifiedTimestamp) {
		this.modifiedTimestamp = modifiedTimestamp;
	}
}
