package com.xyzcorp.ecommerce.thhs.vo;

import com.opencsv.bean.CsvBindByName;

public class ProductDetailVO {

	@CsvBindByName
	private String productId;
	
	@CsvBindByName
	private String name;
	
	@CsvBindByName
	private String description;
	
	@CsvBindByName
	private String cost;
	
	@CsvBindByName
	private String category;
	
	@CsvBindByName
	private String currency;
	
	@CsvBindByName
	private String qtyAvailable;

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

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getQtyAvailable() {
		return qtyAvailable;
	}

	public void setQtyAvailable(String qtyAvailable) {
		this.qtyAvailable = qtyAvailable;
	}

	@Override
	public String toString() {
		return "ProductDetail [productId=" + productId + ", name=" + name + ", description=" + description + ", cost="
				+ cost + ", category=" + category + ", currency=" + currency + ", qtyAvailable=" + qtyAvailable + "]";
	}

}
