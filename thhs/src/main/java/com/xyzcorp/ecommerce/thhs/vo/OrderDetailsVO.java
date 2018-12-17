package com.xyzcorp.ecommerce.thhs.vo;

import java.util.ArrayList;
import java.util.List;

import com.opencsv.bean.CsvBindAndSplitByName;
import com.opencsv.bean.CsvBindByName;

public class OrderDetailsVO {

	@CsvBindByName
	private String orderId;

	@CsvBindByName
	private String cartId;

	@CsvBindByName
	private String userName;

	@CsvBindAndSplitByName(elementType = OrderedProductVO.class)
	private List<OrderedProductVO> orderedProducts;

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
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

	public List<OrderedProductVO> getOrderedProducts() {

		if (orderedProducts == null) {
			orderedProducts = new ArrayList<OrderedProductVO>();
		}

		return orderedProducts;
	}

	public void setOrderedProducts(List<OrderedProductVO> orderedProducts) {
		this.orderedProducts = orderedProducts;
	}

	@Override
	public String toString() {
		return "OrderdetailsVO [orderId=" + orderId + ", cartId=" + cartId + ", userName=" + userName
				+ ", orderedProducts=" + orderedProducts + "]";
	}

}
