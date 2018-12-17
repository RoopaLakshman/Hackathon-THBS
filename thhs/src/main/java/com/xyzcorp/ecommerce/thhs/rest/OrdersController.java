package com.xyzcorp.ecommerce.thhs.rest;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.xyzcorp.ecommerce.thhs.helper.CSVFileHelper;
import com.xyzcorp.ecommerce.thhs.model.Message;
import com.xyzcorp.ecommerce.thhs.model.OrderBody;
import com.xyzcorp.ecommerce.thhs.model.OrderId;

@RestController
public class OrdersController {

	CSVFileHelper csvFileHelper = new CSVFileHelper();

	// expose "/submit" to submit order Details
	@PostMapping(path = "/submit", produces = "application/json")
	public ResponseEntity<?> submitCartDetails(@Valid @RequestBody(required = true) OrderBody body,
			@RequestHeader(value = "token", required = false) String token) {

		String cardId = body.getCartId();
		if (cardId == null || cardId.equals("")) {
			Message message = new Message();
			message.setCode("Bad Request");
			return new ResponseEntity<Message>(message, HttpStatus.BAD_REQUEST);
		}

		OrderId order = new OrderId();
		String reponse = csvFileHelper.orderSubmited(cardId, token);
		
		try {
			order.setOrderId(Integer.parseInt(reponse));
			
		} catch (Exception e) {
			System.out.println(e);
			System.out.println("Failed to place order");
			
			Message message = new Message();
			
			if(reponse.equals("CART_NOT_FOUND")){
				message.setCode("Cart not found");
				return new ResponseEntity<Message>(message, HttpStatus.NOT_FOUND);
				
			}else if(reponse.equals("QTY_NOT_AVAILABLE")){
				message.setCode("One or more item is not in stock");
				return new ResponseEntity<Message>(message, HttpStatus.OK);	
				
			}else if(reponse.equals("PRODUCT_NOT_FOUND")){
				message.setCode("Product not found");
				return new ResponseEntity<Message>(message, HttpStatus.NOT_ACCEPTABLE);
				
			}

			
			message.setCode(HttpStatus.INTERNAL_SERVER_ERROR.toString());
			return new ResponseEntity<Message>(message, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		System.out.println("In submit cart details");
		return new ResponseEntity<OrderId>(order, HttpStatus.OK);

	}
}
