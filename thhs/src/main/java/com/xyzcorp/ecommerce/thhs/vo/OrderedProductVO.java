package com.xyzcorp.ecommerce.thhs.vo;

import com.opencsv.bean.CsvBindByName;

public class OrderedProductVO {

	@CsvBindByName
	private String productId;

	@CsvBindByName
	private String name;

	@CsvBindByName
	private String description;

	@CsvBindByName
	private String cost;

	@CsvBindByName
	private String quantity;

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
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

	public String getCost() {
		return cost;
	}

	public void setCost(String cost) {
		this.cost = cost;
	}

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	@Override
	public String toString() {
		return "OrderedProductVO [productId=" + productId + ", name=" + name + ", description=" + description
				+ ", cost=" + cost + ", quantity=" + quantity + "]";
	}

}
