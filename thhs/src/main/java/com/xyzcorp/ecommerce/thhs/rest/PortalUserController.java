package com.xyzcorp.ecommerce.thhs.rest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PortalUserController {

	// expose "/login" to authenticate users
	@RequestMapping("/login")
	public void authenticateuser() {
		System.out.println("Authneticate user");

	}

	// expose "/register" to allow user to register
	@RequestMapping("/register")
	public void registerUser() {
		System.out.println("Register user");

	}

}
