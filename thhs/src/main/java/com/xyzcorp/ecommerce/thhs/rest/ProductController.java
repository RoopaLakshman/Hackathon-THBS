package com.xyzcorp.ecommerce.thhs.rest;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xyzcorp.ecommerce.thhs.helper.CSVFileHelper;
import com.xyzcorp.ecommerce.thhs.model.Message;
import com.xyzcorp.ecommerce.thhs.model.ProductDetail;
import com.xyzcorp.ecommerce.thhs.model.ProductDetailView;
import com.xyzcorp.ecommerce.thhs.util.BeanMappingUtil;

@RestController
@RequestMapping("/product")
public class ProductController {

	List<ProductDetail> products;
	CSVFileHelper csvFileHelper = new CSVFileHelper();

	// expose "/view" to return list of products
	@GetMapping(path = "/view", produces = "application/json")
	public ResponseEntity<?> fetchProductList(@RequestHeader(value = "token", required = false) String token) {

		products = csvFileHelper.getProducts(true);
		List<ProductDetailView> response = BeanMappingUtil.mapProductDetailsToViewResponse(products);
		return new ResponseEntity<List<ProductDetailView>>(response, HttpStatus.OK);

	}

	// expose "/add" to return list of products
	@PostMapping(path = "/add", consumes = "application/json", produces = "application/json")
	public ResponseEntity<?> addNewProduct(@Valid @RequestBody(required = true) ProductDetailView reqObject,
			@RequestHeader(value = "token", required = false) String token) {
		ProductDetail productObject = BeanMappingUtil.mapProductRequestToViewObject(reqObject);
		boolean status = csvFileHelper.addProduct(productObject);
		if (status) {
			products = csvFileHelper.getProducts(false);
			List<ProductDetailView> response = BeanMappingUtil.mapProductDetailsToViewResponse(products);
			return new ResponseEntity<List<ProductDetailView>>(response, HttpStatus.OK);
		} else {
			Message message = new Message();
			message.setCode("Internal Server Error");
			return new ResponseEntity<Message>(message, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	// expose "/view" to return list of products
	@GetMapping(path = "/view/details", produces = "application/json")
	public ResponseEntity<?> fetchProductListForView(@RequestHeader(value = "token", required = false) String token) {

		products = csvFileHelper.getProducts(true);
		// List<ProductDetailView> response =
		// BeanMappingUtil.mapProductDetailsToViewResponse(products);
		return new ResponseEntity<List<ProductDetail>>(products, HttpStatus.OK);

	}
}