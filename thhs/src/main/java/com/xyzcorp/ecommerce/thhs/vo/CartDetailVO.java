package com.xyzcorp.ecommerce.thhs.vo;

import com.opencsv.bean.CsvBindByName;

public class CartDetailVO {

	@CsvBindByName
	private String productId;

	@CsvBindByName
	private String quantity;

	@CsvBindByName
	private String cartId;

	@CsvBindByName
	private String userName;

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	public String getCartId() {
		return cartId;
	}

	public void setCartId(String cartId) {
		this.cartId = cartId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Override
	public String toString() {
		return "CartDetail [productId=" + productId + ", quantity=" + quantity + ", cartId=" + cartId + ", userName="
				+ userName + "]";
	}

}
