package com.xyzcorp.ecommerce.thhs.rest;

import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.xyzcorp.ecommerce.thhs.model.Credentials;
import com.xyzcorp.ecommerce.thhs.model.GeneratedToken;
import com.xyzcorp.ecommerce.thhs.model.Message;
import com.xyzcorp.ecommerce.thhs.model.Token;
import com.xyzcorp.ecommerce.thhs.model.User;
import com.xyzcorp.ecommerce.thhs.util.CSVFileHandler;
import com.xyzcorp.ecommerce.thhs.util.TokenUtil;

@Controller
public class PortalUserController {

	// expose "/login" to authenticate users
	@PostMapping(path = "/login", produces = "application/json", consumes = "application/json")
	public ResponseEntity<?> authenticateuser(@RequestBody Credentials credentials, HttpServletResponse response) {

		try {
			String username = credentials.getUsername().trim();
			String password = credentials.getPassword().trim();

			if(username.isEmpty() || password.isEmpty()){
				Message message = new Message();
				message.setCode("Bad Request");
				return new ResponseEntity<Message>(message, HttpStatus.BAD_REQUEST);
			}
			
			CSVFileHandler csv = new CSVFileHandler();
			List<User> users = csv.readUsers();
			for (User u : users) {
				if (u.getUserName().equalsIgnoreCase(username)) {
					if (u.getPassword().equals(password)) {
						Token token = new Token();
						GeneratedToken generatedToken = TokenUtil.createToken(u);
						if(null != generatedToken){
							token.setUserToken(generatedToken.getToken());

							Cookie cookie = new Cookie("token", token.getUserToken());
							cookie.setMaxAge(generatedToken.getExpireTimestamp());
							response.addCookie(cookie);

							return new ResponseEntity<Token>(token, HttpStatus.OK);
						} else {
							Message message = new Message();
							message.setCode("Internal Server Error");
							return new ResponseEntity<Message>(message, HttpStatus.INTERNAL_SERVER_ERROR);
						}
						
					} else {
						Message message = new Message();
						message.setCode("Invalid Credentials");
						return new ResponseEntity<Message>(message, HttpStatus.UNAUTHORIZED);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		Message message = new Message();
		message.setCode("Bad Request");
		return new ResponseEntity<Message>(message, HttpStatus.BAD_REQUEST);
	}

	// expose "/register" to allow user to register
	@PostMapping("/register")
	public void registerUser() {
		System.out.println("Register user");

	}

}
