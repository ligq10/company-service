package com.ligq.shoe.response;

import org.apache.commons.lang.StringUtils;
import org.springframework.hateoas.ResourceSupport;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class HomePageResources extends ResourceSupport {

	private String uuid;
	private String shortName;
	private String pageUrl;
	
	
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public String getShortName() {
		return shortName;
	}
	public void setShortName(String shortName) {
		this.shortName = shortName;
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
}
