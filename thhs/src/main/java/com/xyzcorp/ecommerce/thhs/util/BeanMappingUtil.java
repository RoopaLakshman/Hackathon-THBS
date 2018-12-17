package com.xyzcorp.ecommerce.thhs.util;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import com.xyzcorp.ecommerce.thhs.model.CartDetail;
import com.xyzcorp.ecommerce.thhs.model.CartDetailViewResponse;
import com.xyzcorp.ecommerce.thhs.model.ProductDetail;
import com.xyzcorp.ecommerce.thhs.model.ProductDetailView;
import com.xyzcorp.ecommerce.thhs.model.User;
import com.xyzcorp.ecommerce.thhs.vo.CartDetailVO;
import com.xyzcorp.ecommerce.thhs.vo.ProductDetailVO;
import com.xyzcorp.ecommerce.thhs.vo.UserVO;

public class BeanMappingUtil {

	public User mapUserVOToUser(UserVO userVO) {
		User user = new User();

		user.setUserId(userVO.getUserId());
		user.setUserName(userVO.getUserName());
		user.setPassword(userVO.getPassword());
		user.setName(userVO.getName());
		user.setPhoneNumber(userVO.getPhoneNumber());
		user.setAddress(userVO.getAddress());
		user.setPreference(userVO.getPreference());

		return user;
	}

	public List<User> mapUserVOsToUsers(List<UserVO> userVOs) {
		List<User> users = new ArrayList<User>();

		for (UserVO userVO : userVOs) {
			users.add(mapUserVOToUser(userVO));
		}

		return users;
	}

	public UserVO mapUserToUserVO(User user) {
		UserVO userVO = new UserVO();

		userVO.setUserId(user.getUserId());
		userVO.setUserName(user.getUserName());
		userVO.setPassword(user.getPassword());
		userVO.setName(user.getName());
		userVO.setPhoneNumber(user.getPhoneNumber());
		userVO.setAddress(user.getAddress());
		userVO.setPreference(user.getPreference());

		return userVO;
	}

	public List<UserVO> mapUsersToUserVOs(List<User> users) {
		List<UserVO> userVOs = new ArrayList<UserVO>();

		for (User user : users) {
			userVOs.add(mapUserToUserVO(user));
		}

		return userVOs;
	}

	public CartDetail mapCartDetailVOToCartDetail(CartDetailVO cartVO) {
		CartDetail cart = new CartDetail();

		cart.setCartId(cartVO.getCartId());
		cart.setProductId(cartVO.getProductId());
		cart.setQuantity(Integer.parseInt(cartVO.getQuantity()));
		cart.setUserName(cartVO.getUserName());

		return cart;
	}

	public List<CartDetail> mapCartDetailVOsToCartDetails(List<CartDetailVO> cartsVO) {
		List<CartDetail> carts = new ArrayList<CartDetail>();

		for (CartDetailVO cart : cartsVO) {
			carts.add(mapCartDetailVOToCartDetail(cart));
		}

		return carts;
	}

	public CartDetailVO mapCartDetailToCartDetailVO(CartDetail cart) {
		CartDetailVO cartVO = new CartDetailVO();

		cartVO.setCartId(cart.getCartId());
		cartVO.setProductId(cart.getProductId());
		cartVO.setQuantity(String.valueOf(cart.getQuantity()));
		cartVO.setUserName(cart.getUserName());

		return cartVO;
	}

	public List<CartDetailVO> mapCartDetailsToCartDetailVOs(List<CartDetail> carts) {
		List<CartDetailVO> cartsVO = new ArrayList<CartDetailVO>();

		for (CartDetail cart : carts) {
			cartsVO.add(mapCartDetailToCartDetailVO(cart));
		}

		return cartsVO;
	}

	public ProductDetailVO mapProductDetailToProductDetailVO(ProductDetail product) {
		ProductDetailVO productVO = new ProductDetailVO();

		productVO.setProductId(product.getProductId());
		productVO.setName(product.getName());
		productVO.setDescription(product.getDescription());
		productVO.setCost(product.getCost());
		productVO.setCurrency(product.getCurrency());
		productVO.setQtyAvailable(String.valueOf(product.getQtyAvailable()));
		productVO.setCategory(product.getCategory());

		return productVO;
	}

	public List<ProductDetailVO> mapProductDetailsToProductDetailVOs(List<ProductDetail> products) {
		List<ProductDetailVO> productsVO = new ArrayList<ProductDetailVO>();

		for (ProductDetail product : products) {
			productsVO.add(mapProductDetailToProductDetailVO(product));
		}

		return productsVO;
	}

	public ProductDetail mapProductDetailVOToProductDetail(ProductDetailVO productVO) {
		ProductDetail product = new ProductDetail();

		product.setProductId(productVO.getProductId());
		product.setName(productVO.getName());
		product.setDescription(productVO.getDescription());
		product.setCost(productVO.getCost());
		product.setCurrency(productVO.getCurrency());
		product.setQtyAvailable(Integer.parseInt(productVO.getQtyAvailable()));
		product.setCategory(productVO.getCategory());

		return product;
	}

	public List<ProductDetail> mapProductDetailVOsToProductDetails(List<ProductDetailVO> productsVO) {
		List<ProductDetail> products = new ArrayList<ProductDetail>();

		for (ProductDetailVO productVO : productsVO) {
			products.add(mapProductDetailVOToProductDetail(productVO));
		}

		return products;
	}

	public CartDetailViewResponse mapCartDetailToCartDetailViewResponse(CartDetail cartDetails) {
		CartDetailViewResponse cdvs = new CartDetailViewResponse();

		cdvs.setCartId(cartDetails.getCartId());
		cdvs.setProductId(cartDetails.getProductId());
		cdvs.setQuantity(cartDetails.getQuantity());

		return cdvs;
	}

	public List<CartDetailViewResponse> mapCartDetailsToCartDetailsViewResponse(List<CartDetail> cartDetails) {
		List<CartDetailViewResponse> cdViews = new ArrayList<CartDetailViewResponse>();

		for (CartDetail cartDetail : cartDetails) {
			cdViews.add(mapCartDetailToCartDetailViewResponse(cartDetail));
		}

		return cdViews;
	}

	public static List<ProductDetailView> mapProductDetailsToViewResponse(List<ProductDetail> productdetails) {
		List<ProductDetailView> productDetailsResponse = new ArrayList<ProductDetailView>();

		for (ProductDetail product : productdetails) {
			productDetailsResponse.add(mapProductToProduct(product));
		}

		return productDetailsResponse;
	}

	public static ProductDetailView mapProductToProduct(ProductDetail product) {
		ProductDetailView productResp = new ProductDetailView();

		productResp.setName(product.getName());
		productResp.setDescription(product.getDescription());
		productResp.setCost(product.getCost());
		productResp.setCategory(product.getCategory());
		productResp.setCurrency(product.getCurrency());
		productResp.setQtyAvailable(product.getQtyAvailable());

		return productResp;
	}

	public static ProductDetail mapProductRequestToViewObject(@Valid ProductDetailView reqObject) {
		ProductDetail product = new ProductDetail();

		product.setName(reqObject.getName());
		product.setDescription(reqObject.getDescription());
		product.setCost(reqObject.getCost());
		product.setCategory(reqObject.getCategory());
		product.setCurrency(reqObject.getCurrency());
		product.setQtyAvailable(reqObject.getQtyAvailable());

		return product;
	}
}
