package com.ligq.shoe.request;


import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ligq.shoe.model.ProductImage;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UpdateProductPriceRequest {
	
	private String productName;

	private String categoryId;
	
	private Double price;
	
	private String unit;
	
	private String description;
	
	private String icon;
		
	private String status;
	
	private List<ProductImage> images;


	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}
	
	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<ProductImage> getImages() {
		return images;
	}

	public void setImages(List<ProductImage> images) {
		this.images = images;
	}
	
}
