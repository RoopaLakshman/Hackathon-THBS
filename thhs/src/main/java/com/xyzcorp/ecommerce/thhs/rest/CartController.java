package com.xyzcorp.ecommerce.thhs.rest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/cart")
public class CartController {

	// expose "/add" to add a new product into cart
	@RequestMapping("/add")
	public void addProductToCart() {
		System.out.println("In add product to cart");

	}

	// expose "/modify" to update products in cart
	@RequestMapping("/modify")
	public void modifyCart() {
		System.out.println("In modify cart method");

	}

	// expose "/delete" to delete a product in cart
	@RequestMapping("/delete")
	public void deleteProductFromCart() {
		System.out.println("In delete cart method");

	}

	// expose "/view" to view all products in cart or to view an empty cart
	@RequestMapping("/view")
	public void fetchProductsInCart() {
		System.out.println("In view cart details method");

	}
}
