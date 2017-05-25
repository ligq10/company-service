package com.ligq.shoe.response;



import org.springframework.hateoas.ResourceSupport;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@JsonIgnoreProperties(ignoreUnknown=true)
public class CategoryDetailResponse extends ResourceSupport {
	
	private String uuid;
	private String parentId;
	private String code;
	private String name;
	private Integer sort;
	private String createdTime;
	private String description;
	private int level;
	private String catalogId;	
	private String icon;
	
	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(String createdTime) {
		this.createdTime = createdTime;
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

	public String getName() {
		if (StringUtils.isEmpty(name)) {
			name = "";
		}
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getParentId() {
		if (StringUtils.isEmpty(parentId)) {
			parentId = "";
		}
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public String getCatalogId() {
		if(StringUtils.isEmpty(catalogId)){
			catalogId = "";
		}
		return catalogId;
	}

	public void setCatalogId(String catalogId) {
		this.catalogId = catalogId;
	}

	public String getIcon() {
		if(StringUtils.isEmpty(icon)){
			icon = "";
		}
		return icon;
	}

	public void setIcon(String icon) {

		this.icon = icon;
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

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

}
