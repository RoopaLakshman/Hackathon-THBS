package com.xyzcorp.ecommerce.thhs.rest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class OrdersController {

	// expose "/submit" to submit order Details
	@RequestMapping("/submit")
	public void submitCartDetails() {
		System.out.println("in submit cart details");

	}
}
