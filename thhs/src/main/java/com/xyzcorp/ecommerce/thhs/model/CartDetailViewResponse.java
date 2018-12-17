package com.xyzcorp.ecommerce.thhs.model;

public class CartDetailViewResponse {

	private String productId;
	private Integer quantity;
	private String cartId;
	
	
	public String getProductId() {
		return productId;
	}


	public void setProductId(String productId) {
		this.productId = productId;
	}


	public Integer getQuantity() {
		return quantity;
	}


	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}


	public String getCartId() {
		return cartId;
	}


	public void setCartId(String cartId) {
		this.cartId = cartId;
	}


	@Override
	public String toString() {
		return "CartDetail [productId=" + productId + ", quantity=" + quantity + ", cartId=" + cartId + "]";
	}

}
