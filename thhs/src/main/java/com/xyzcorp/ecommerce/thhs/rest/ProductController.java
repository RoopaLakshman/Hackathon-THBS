package com.xyzcorp.ecommerce.thhs.rest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/product")
public class ProductController {

	// expose "/view" to return list of products
	@RequestMapping("/view")
	public String fetchProductList() {
		System.out.println("In view product");
		return "index";
	}

	// expose "/add" to return list of products
	@RequestMapping("/add")
	public void addNewProduct() {
		System.out.println("In add new product");

	}
}
