package com.ligq.shoe.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="catalog")
public class CataLog {
	
	@Id
	@Column(name="uuid",length=64)
	private String uuid;
	
	@Column(name = "code",length=128)
	private String code;
	
	@Column(name = "sort",length=16)
	private Integer sort;
	
	@Column(name = "name",length=256)
	private String name;
	
	@Column(name = "description",length=512)
	private String description;
	
	@Column(name = "icon",length=64)
	private String icon;
	
	@Column(name = "created_by",length=256)
	private String createdBy;
	
	@Column(name = "created_timestamp")
	private Date createdTimestamp;
	
	@Column(name = "modified_by",length=256)
	private String modifiedBy;
	
	@Column(name = "modified_timestamp")
	private Date modifiedTimestamp;

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getCode() {
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}	
	
}
