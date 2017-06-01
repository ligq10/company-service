package com.ligq.shoe.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "order_to_product_item")
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderToProductItem {

	@Id
	@Column(name="uuid",length=64)
	private String uuid;

	@Column(name = "order_id",length=64)
	private String orderId;

	@Column(name = "product_id",length=64)
	private String productId;

	@Column(name = "amount",precision=6)
	private Double amount;

	@Column(name = "name",length=512)
	private String name;

	@Column(name = "price",precision=6)
	private Double price;

	@Column(name = "subtotal",precision=6)
	private Double subtotal;

	@Column(name = "unit",length=64)
	private String unit;
	
	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Double getSubtotal() {
		return subtotal;
	}

	public void setSubtotal(Double subtotal) {
		this.subtotal = subtotal;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

}
