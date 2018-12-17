package com.xyzcorp.ecommerce.thhs.rest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xyzcorp.ecommerce.thhs.helper.CSVFileHelper;
import com.xyzcorp.ecommerce.thhs.model.CartDetail;
import com.xyzcorp.ecommerce.thhs.model.CartDetailResponse;
import com.xyzcorp.ecommerce.thhs.model.CartDetailViewResponse;
import com.xyzcorp.ecommerce.thhs.model.CartDetailsResponse;
import com.xyzcorp.ecommerce.thhs.model.Message;
import com.xyzcorp.ecommerce.thhs.model.ProductDetail;
import com.xyzcorp.ecommerce.thhs.util.BeanMappingUtil;

@RestController
@RequestMapping("/cart")
public class CartController {

	CSVFileHelper csvFileHelper = new CSVFileHelper();
	BeanMappingUtil beanMappingUtil = new BeanMappingUtil();
	String username = null;
	Message message = new Message();

	// expose "/add" to add a new product into cart
	@PostMapping(path = "/add", produces = "application/json")
	public ResponseEntity<?> addProductToCart(@Valid @RequestBody(required = true) List<CartDetail> reqBody,
			@RequestHeader(value = "token", required = false) String token) {
		if (!checkAuthorization(token, reqBody)) {
			Message m = new Message();
			m.setCode("Unauthorized");
			return new ResponseEntity<Message>(m, HttpStatus.UNAUTHORIZED);
		}
		if (!checkQuantityAvailablity(reqBody)) {
			Message m = new Message();
			m.setCode("Insufficient products");
			return new ResponseEntity<Message>(m, HttpStatus.BAD_REQUEST);
		}
		boolean added = csvFileHelper.addCartItems(reqBody, token, true);
		CartDetailResponse cdResp = new CartDetailResponse();
		if (added) {
			cdResp.setCartId(reqBody.get(0).getCartId());
			return new ResponseEntity<CartDetailResponse>(cdResp, HttpStatus.OK);
		} else {
			return new ResponseEntity<CartDetailResponse>(cdResp, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// expose "/modify" to update products in cart
	@PutMapping(path = "/modify", produces = "application/json")
	public ResponseEntity<?> modifyCart(@Valid @RequestBody(required = true) List<CartDetail> reqBody,
			@RequestHeader(value = "token", required = false) String token) {
		if (!checkAuthorization(token, reqBody)) {
			Message m = new Message();
			m.setCode("Unauthorized");
			return new ResponseEntity<Message>(m, HttpStatus.UNAUTHORIZED);
		}
		if (!checkQuantityAvailablity(reqBody)) {
			Message m = new Message();
			m.setCode("Insufficient products");
			return new ResponseEntity<Message>(m, HttpStatus.BAD_REQUEST);
		}
		List<CartDetail> cartDetails = new ArrayList<>();
		boolean modified = csvFileHelper.addCartItems(reqBody, token, true);

		if (modified) {
			username = csvFileHelper.getUserNameFromToken(token);
			cartDetails = csvFileHelper.getCartsByUserName(username);
			return new ResponseEntity<List<CartDetail>>(cartDetails, HttpStatus.OK);
		} else {
			return new ResponseEntity<List<CartDetail>>(cartDetails, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// expose "/delete" to delete a product in cart
	@DeleteMapping(path = "/delete", produces = "application/json")
	public ResponseEntity<?> deleteProductFromCart(@Valid @RequestBody(required = true) List<CartDetail> reqBody,
			@RequestHeader(value = "token", required = false) String token) {

		Map<String, CartDetail> cartSummedUp = new HashMap<String, CartDetail>();
		for (CartDetail c : reqBody) {
			if (null != cartSummedUp.get(c.getProductId())) {
				CartDetail cartDetail = cartSummedUp.get(c.getProductId());
				int totalQuantity = cartDetail.getQuantity() + c.getQuantity();
				cartDetail.setQuantity(totalQuantity);
				cartSummedUp.put(c.getProductId(), cartDetail);
			} else {
				cartSummedUp.put(c.getProductId(), c);
			}
			
		}
		reqBody.clear();
		reqBody.addAll(cartSummedUp.values());

		String userName = csvFileHelper.getUserNameFromToken(token);
		List<CartDetail> cartsDetails = csvFileHelper.getCartsByUserName(userName);

		if (cartsDetails.size() > 0) {
			if (!checkAuthorization(token, reqBody)) {
				Message m = new Message();
				m.setCode("Unauthorized");
				return new ResponseEntity<Message>(m, HttpStatus.UNAUTHORIZED);
			} else if (!checkProductExists(token, reqBody)) {
				Message m = new Message();
				m.setCode("Product doesn't exist in cart");
				return new ResponseEntity<Message>(m, HttpStatus.BAD_REQUEST);
			} else if (!checkProductQuantity(token, reqBody)) {
				Message m = new Message();
				m.setCode("Invalid Quantity");
				return new ResponseEntity<Message>(m, HttpStatus.BAD_REQUEST);
			}

			boolean removed = csvFileHelper.removeCartItems(reqBody, true);
			if (removed) {
				Message cdResp = new Message();
				cdResp.setCode("successful operation");
				return new ResponseEntity<Message>(cdResp, HttpStatus.OK);
			} else {
				Message error = new Message();
				error.setCode("Internal server error");
				return new ResponseEntity<Message>(error, HttpStatus.OK);
			}
		} else {
			Message cdResp = new Message();
			cdResp.setCode("No items in cart");
			return new ResponseEntity<Message>(cdResp, HttpStatus.NOT_FOUND);
		}

	}

	// expose "/view" to view all products in cart or to view an empty cart
	@GetMapping(path = "/view", produces = "application/json")
	public ResponseEntity<?> fetchProductsInCart(@RequestHeader(value = "token", required = false) String token) {
		{
			username = csvFileHelper.getUserNameFromToken(token);
			List<CartDetail> cartDetails = new ArrayList<CartDetail>();
			cartDetails = csvFileHelper.getCartsByUserName(username);
			List<CartDetailViewResponse> CDVresponse = beanMappingUtil
					.mapCartDetailsToCartDetailsViewResponse(cartDetails);

			return new ResponseEntity<List<CartDetailViewResponse>>(CDVresponse, HttpStatus.OK);
		}
	}

	private boolean checkAuthorization(String token, List<CartDetail> cartdetails) {
		String userName = csvFileHelper.getUserNameFromToken(token);
		List<CartDetail> cartsDetails = csvFileHelper.getCartsByUserName(userName);
		if (cartsDetails.size() > 0) {
			String cartId = cartsDetails.get(0).getCartId();
			for (CartDetail c : cartdetails) {

				if (!c.getCartId().equals(cartId)) {

					return false;
				}

			}

		}

		return true;
	}

	private boolean checkProductExists(String token, List<CartDetail> cartdetails) {
		boolean matched = true;
		String userName = csvFileHelper.getUserNameFromToken(token);
		List<CartDetail> cartDetailsFromRepo = csvFileHelper.getCartsByUserName(userName);
		String productsAvailableInCart[] = new String[cartDetailsFromRepo.size()];
		for (int i = 0; i < cartDetailsFromRepo.size(); i++) {
			productsAvailableInCart[i] = cartDetailsFromRepo.get(i).getProductId();
		}
		for (CartDetail c : cartdetails) {
			if (!(Arrays.asList(productsAvailableInCart).contains(c.getProductId()))) {
				matched = false;
			}
		}
		return matched;
	}

// expose "/details" to get products details in cart
	@GetMapping(path = "/details", produces = "application/json")
	public ResponseEntity<?> getDetails(@RequestHeader(value = "token", required = false) String token) {
		{
			username = csvFileHelper.getUserNameFromToken(token);
			List<CartDetail> cartDetails = new ArrayList<CartDetail>();
			List<CartDetailsResponse> cartDetailResponseList = new ArrayList<CartDetailsResponse>();
			
			cartDetails = csvFileHelper.getCartsByUserName(username);
			
			for(CartDetail cart:cartDetails){
				ProductDetail product = csvFileHelper.getProductByProductId(cart.getProductId());
				CartDetailsResponse tempCartDetailResponse = new CartDetailsResponse();
				tempCartDetailResponse.setCartId(cart.getCartId());
				tempCartDetailResponse.setQuantity(cart.getQuantity());
				tempCartDetailResponse.setProductId(cart.getProductId());
				tempCartDetailResponse.setCurrency(product.getCurrency());
				tempCartDetailResponse.setCategory(product.getCategory());
				tempCartDetailResponse.setDescription(product.getDescription());
				tempCartDetailResponse.setCost(tempCartDetailResponse.getQuantity()*Integer.parseInt(product.getCost()));
				
				cartDetailResponseList.add(tempCartDetailResponse);
			}

			return new ResponseEntity<List<CartDetailsResponse>>(cartDetailResponseList, HttpStatus.OK);
		}
	}

	private boolean checkProductQuantity(String token, List<CartDetail> cartdetails) {

		String userName = csvFileHelper.getUserNameFromToken(token);
		List<CartDetail> cartDetailsFromRepo = csvFileHelper.getCartsByUserName(userName);
		Map<String, Integer> productsQuantityAvailableInCart = new HashMap<String, Integer>();
		for (int i = 0; i < cartDetailsFromRepo.size(); i++) {
			productsQuantityAvailableInCart.put(cartDetailsFromRepo.get(i).getProductId(),
					cartDetailsFromRepo.get(i).getQuantity());
		}
		for (CartDetail c : cartdetails) {
			if (c.getQuantity() > productsQuantityAvailableInCart.get(c.getProductId())) {
				return false;
			}
		}
		return true;
	}
	
	private boolean checkQuantityAvailablity(@Valid List<CartDetail> reqBody) {
		List<ProductDetail> products = csvFileHelper.getProducts(true);
		Map<String, Integer> availaibility = new HashMap<String, Integer>();

		for (int i = 0; i < products.size(); i++) {
			availaibility.put(products.get(i).getProductId(), products.get(i).getQtyAvailable());
		}

		for (CartDetail c : reqBody) {
			int quanAvailable = availaibility.get(c.getProductId());
			if (quanAvailable < c.getQuantity()) {
				return false;
			}
		}
		return true;
	}

}
