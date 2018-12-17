package com.xyzcorp.ecommerce.thhs.model;

public class CartDetailResponse {

	private String cartId;

	public String getCartId() {
		return cartId;
	}

	public void setCartId(String cartId) {
		this.cartId = cartId;
	}

	@Override
	public String toString() {
		return "CartDetail [ cartId=" + cartId + "]";
	}

}
