package com.ligq.shoe.entity;

import java.io.Serializable;
import java.util.Date;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;




@Entity
@Table(name="category")
public class Category implements Serializable {
	private static final long serialVersionUID = 6843882685715521278L;

	@Id
	@Column(name="uuid",length=64)
	private String uuid;

	@Column(name = "parent_id",length=64)
	private String parentId;

	@Column(name = "code",length=128)
	private String code;

	@Column(name = "name",length=256)
	private String name;
	
	@Column(name = "sort",length=16)
	private Integer sort;
	
	@Column(name = "created_time")
	private Date createdTime;

	@Column(name = "update_time")
	private Date updateTime;
	
	@Column(name = "description",length=512)
	private String description;

	@Column(name = "level",length=16)
	private int level;
	
	@Column(name = "catalog_id",length=64)
	private String catalogId;	
	
	@Column(name = "icon",length=64)
	private String icon;
 

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}
	
	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getCode() {
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

	public String getCatalogId() {
		return catalogId;
	}

	public void setCatalogId(String catalogId) {
		this.catalogId = catalogId;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}
	
}
