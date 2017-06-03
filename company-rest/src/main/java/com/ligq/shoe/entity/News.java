package com.ligq.shoe.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="news")
public class News {

	@Id
	@Column(name="uuid",length=64)
	private String uuid;

	@Column(name = "title",length=512)
	private String title;

	@Column(name = "icon",length=64)
	private String icon;

	@Column(name = "intro",columnDefinition="text")
	private String intro;

	@Column(name = "content",columnDefinition="text")
	private String content;

	@Column(name = "description",columnDefinition="text")
	private String description;

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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getIntro() {
		return intro;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

}
