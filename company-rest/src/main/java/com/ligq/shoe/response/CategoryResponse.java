package com.ligq.shoe.response;


import org.springframework.hateoas.ResourceSupport;
import org.springframework.util.StringUtils;



/**
 * 
 * @author xiongtao
 *
 */
public class CategoryResponse extends ResourceSupport {

	private String uuid;
	private String parentId;
	private String code;
	private String name;
	private Integer sort;
	private String createdTime;
	private String description;
	private String icon;
	
	public String getUuid() {
		if(StringUtils.isEmpty(uuid)){
			this.uuid = "";
		}
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getCreatedTime() {
		if(StringUtils.isEmpty(createdTime)){
			this.createdTime = "";
		}
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

	public String getIcon() {
		if(StringUtils.isEmpty(this.icon)){
			return "";
		}
		return icon;
	}

	public void setIcon(String icon) {
		if(StringUtils.isEmpty(icon)){
			return;
		}
		this.icon = icon.toString();
	}

	public String getParentId() {
		if(StringUtils.isEmpty(parentId)){
			return "";
		}
		return parentId;
	}

	public void setParentId(String parentId) {
		if(StringUtils.isEmpty(parentId)){
			return ;
		}
		this.parentId = parentId.toString();
	}

	public String getCode() {
		if(StringUtils.isEmpty(code)){
			this.code = "";
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
