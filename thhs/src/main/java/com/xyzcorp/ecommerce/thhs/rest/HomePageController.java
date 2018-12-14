package com.xyzcorp.ecommerce.thhs.rest;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomePageController {

	@Value("${client.name}")
	private String clientName;

	// expose "/" to return to "main landing page"
	@RequestMapping("/")
	public void redirectToProductList(HttpServletResponse response) {
		try {
			response.sendRedirect("product/view");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
