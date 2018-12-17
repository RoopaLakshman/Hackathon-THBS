package com.xyzcorp.ecommerce.thhs.model;

public class ProductDetailView {

	private String name;
	private String description;
	private String cost;
	private String category;
	private String currency;
	private Integer qtyAvailable;

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

	public Integer getQtyAvailable() {
		return qtyAvailable;
	}

	public void setQtyAvailable(Integer qtyAvailable) {
		this.qtyAvailable = qtyAvailable;
	}

	@Override
	public String toString() {
		return "ProductDetail [ name=" + name + ", description=" + description + ", cost="
				+ cost + ", category=" + category + ", currency=" + currency + ", qtyAvailable=" + qtyAvailable + "]";
	}

}
