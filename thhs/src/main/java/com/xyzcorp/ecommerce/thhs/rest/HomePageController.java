package com.xyzcorp.ecommerce.thhs.rest;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomePageController {

	@Value("${client.name}")
	private String clientName;

	// expose "/" to return to "main landing page"
	@RequestMapping("/")
	public String redirectToProductList() {
		return "index";
	}

	// expose "/checkout" to return to "checkout page"
	@RequestMapping("/checkout")
	public String redirectToCheckOut() {
		return "checkout";
	}

}
