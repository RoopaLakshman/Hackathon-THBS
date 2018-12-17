package com.xyzcorp.ecommerce.thhs.helper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.xyzcorp.ecommerce.thhs.model.CartDetail;
import com.xyzcorp.ecommerce.thhs.model.ProductDetail;
import com.xyzcorp.ecommerce.thhs.model.User;
import com.xyzcorp.ecommerce.thhs.util.BeanMappingUtil;
import com.xyzcorp.ecommerce.thhs.util.CSVFileHandler;
import com.xyzcorp.ecommerce.thhs.vo.CartDetailVO;
import com.xyzcorp.ecommerce.thhs.vo.OrderDetailsVO;
import com.xyzcorp.ecommerce.thhs.vo.OrderedProductVO;
import com.xyzcorp.ecommerce.thhs.vo.ProductDetailVO;
import com.xyzcorp.ecommerce.thhs.vo.TokenVO;

public class CSVFileHelper {
	private final CSVFileHandler csvFileHandler = new CSVFileHandler();
	private final BeanMappingUtil beanMapingUtil = new BeanMappingUtil();

	public boolean separateDataFile(String fileName) {
		return csvFileHandler.separateDataFile(fileName);
	}

	public List<User> getUsers() {

		List<User> response = csvFileHandler.readUsers();
		List<User> users = new ArrayList<User>();

		for (User user : response) {
			if (!user.getUserId().trim().equalsIgnoreCase("USERID")) {
				users.add(user);
			}
		}

		return users;
	}

	public List<CartDetail> getCarts() {

		List<CartDetailVO> response = csvFileHandler.readCarts();
		List<CartDetailVO> carts = new ArrayList<CartDetailVO>();

		for (CartDetailVO cart : response) {
			if (!cart.getCartId().trim().equalsIgnoreCase("CARTID")) {
				carts.add(cart);
			}
		}
		return beanMapingUtil.mapCartDetailVOsToCartDetails(carts);
	}

	public List<String> getAllTokens() {

		List<TokenVO> response = csvFileHandler.readTokens();
		List<String> tokens = new ArrayList<String>();

		for (TokenVO token : response) {
			if (!token.getToken().trim().equalsIgnoreCase("TOKEN") && !token.getToken().trim().equalsIgnoreCase("")) {
				tokens.add(token.getToken());
			}
		}
		return tokens;
	}

	public List<ProductDetail> getProducts(boolean onlyAvailable) {

		List<ProductDetailVO> response = csvFileHandler.readProducts();
		List<ProductDetailVO> products = new ArrayList<ProductDetailVO>();

		for (ProductDetailVO product : response) {
			if (!product.getProductId().trim().equalsIgnoreCase("PRODUCTID")) {
				if (onlyAvailable && Integer.parseInt(product.getQtyAvailable()) <= 0) {
					continue;
				}

				products.add(product);
			}
		}
		return beanMapingUtil.mapProductDetailVOsToProductDetails(products);
	}

	public boolean removeCartItem(CartDetail cart, boolean isSubmit) {

		Map<String, List<CartDetail>> cartItems = getCartDetailsMap();

		if (cartItems.get(cart.getCartId()) == null) {
			System.out.println("No item in cart with CartId: " + cart.getCartId() + " in cartDetails.csv");

		} else {
			for (CartDetail loopCartObj : cartItems.get(cart.getCartId())) {

				System.out.println(loopCartObj.getProductId() + ":" + cart.getProductId());
				if (loopCartObj.getProductId().equals(cart.getProductId())) {
					if (isSubmit) {
						System.out.println("Removing: " + loopCartObj.toString());
						cartItems.get(cart.getCartId()).remove(loopCartObj);
						break;

					} else {
						loopCartObj.setQuantity(loopCartObj.getQuantity() - cart.getQuantity());
						break;
					}
				}
			}
		}

		List<CartDetail> cartWriteList = new ArrayList<CartDetail>();
		for (List<CartDetail> cartList : cartItems.values()) {
			cartWriteList.addAll(cartList);
		}

		return writeCarts(cartWriteList);

	}

	public boolean removeCartItems(List<CartDetail> carts, boolean isSubmit) {
		boolean anyFailed = false;

		for (CartDetail cart : carts) {
			if (removeCartItem(cart, isSubmit)) {

			} else {
				anyFailed = true;
			}
		}

		return !anyFailed;
	}

	public boolean addCartItem(CartDetail cart, boolean isModify) {

		Map<String, List<CartDetail>> cartItems = getCartDetailsMap();

		if (cartItems.get(cart.getCartId()) == null) {

			List<CartDetail> tempCart = new ArrayList<CartDetail>();
			tempCart.add(cart);

			cartItems.put(cart.getCartId(), tempCart);

		} else {

			boolean productExists = false;

			for (CartDetail loopCartObj : cartItems.get(cart.getCartId())) {
				if (loopCartObj.getProductId().equals(cart.getProductId())) {

					if (isModify) {
						loopCartObj.setQuantity(cart.getQuantity());
					} else {
						loopCartObj.setQuantity(loopCartObj.getQuantity() + cart.getQuantity());
					}

					productExists = true;
					break;
				}
			}

			if (!productExists) {
				cartItems.get(cart.getCartId()).add(cart);

			}
		}

		List<CartDetail> cartWriteList = new ArrayList<CartDetail>();
		for (List<CartDetail> cartList : cartItems.values()) {
			cartWriteList.addAll(cartList);
		}

		return writeCarts(cartWriteList);
	}

	public boolean addCartItems(List<CartDetail> carts, String tokenString, boolean isModify) {
		boolean anyFailed = false;

		if (tokenString != null && !tokenString.trim().isEmpty()) {

			String userName = getUserNameFromToken(tokenString);

			for (CartDetail cart : carts) {
				cart.setUserName(userName);

				if(cart.getQuantity()==0){
					if(removeCartItem(cart, true)){
						
					}else{
						anyFailed = true;
					}
				}else{
					if (addCartItem(cart, isModify)) {

					} else {
						anyFailed = true;
					}
	
				}							
			}
		} else {
			for (CartDetail cart : carts) {
				if(cart.getQuantity()==0){
					if(removeCartItem(cart, true)){
						
					}else{
						anyFailed = true;
					}
				}else{
					if (addCartItem(cart, isModify)) {

					} else {
						anyFailed = true;
					}
	
				}
			}
		}

		return !anyFailed;
	}

	public boolean addProduct(ProductDetail product) {
		
		Map<String, ProductDetail> productItems = getProductDetailsMap(false);

		if (null != productItems.get(product.getName())) {
		
			System.out.println("Modifing product: " + product.toString());
			
			// ProductDetail productDetail =
			// productItems.get(product.getName());
			ProductDetail tempProduct = productItems.get(product.getName());
			// tempProduct.setProductId(productDetail.getProductId());
			tempProduct.setName(product.getName());
			tempProduct.setCost(product.getCost());
			tempProduct.setCurrency(product.getCurrency());
			tempProduct.setCategory(product.getCategory());
			tempProduct.setDescription(product.getDescription());
			tempProduct.setQtyAvailable(product.getQtyAvailable());

		} else {
			int id = productItems.size() + 1;
			for (ProductDetail p : productItems.values()) {
				if (p.getProductId().equals(String.valueOf(id))) {
					id++;
				} else {
					break;
				}
			}
			product.setProductId(String.valueOf(id));
			productItems.put(product.getName(), product);

		}

		List<ProductDetail> writeProductItems = new ArrayList<ProductDetail>(productItems.values());
		return writeProducts(writeProductItems);
	}

	public ProductDetail getProductByProductId(String productId) {
		List<ProductDetail> allProducts = getProducts(false);

		for (ProductDetail product : allProducts) {
			if (product.getProductId().equals(productId)) {
				return product;
			}
		}

		return null;
	}

	public boolean addToken(String tokenString) {
		boolean status = false;
		boolean rewriteAll = false;
		String[] tokenParts = tokenString.split(":");
		
		List<String> allTokens = getAllTokens();
		List<String> removeTokens = new ArrayList<String>();
		for (String token : allTokens) {
			String[] parts = token.split(":");
			if (parts[0].equalsIgnoreCase(tokenParts[0])) {
				removeTokens.add(token);
				rewriteAll = true;
			}
		}
		if (rewriteAll) {
			allTokens.removeAll(removeTokens);
			List<TokenVO> validTokens = new ArrayList<TokenVO>();
			TokenVO newToken = new TokenVO();
			newToken.setToken(tokenString);
			validTokens.add(newToken);
			for (String token : allTokens) {
				TokenVO vo = new TokenVO();
				vo.setToken(token);
				validTokens.add(vo);
			}
			status = csvFileHandler.writeTokens(validTokens, true);
		} else {
			TokenVO token = new TokenVO();
			token.setToken(tokenString);
			status = csvFileHandler.writeToken(token, true);
		}
		return status;
	}

	public Map<String, List<CartDetail>> getCartDetailsMap() {
		List<CartDetail> existingCarts = getCarts();
		Map<String, List<CartDetail>> cartItems = new HashMap<String, List<CartDetail>>();

		for (CartDetail loopCartObj : existingCarts) {
			if (cartItems.get(loopCartObj.getCartId()) == null) {

				List<CartDetail> tempCart = new ArrayList<CartDetail>();
				tempCart.add(loopCartObj);

				cartItems.put(loopCartObj.getCartId(), tempCart);

			} else {
				cartItems.get(loopCartObj.getCartId()).add(loopCartObj);

			}
		}

		return cartItems;
	}

	public Map<String, ProductDetail> getProductDetailsMap(boolean onlyAvailable) {

		List<ProductDetail> existingProducts = getProducts(onlyAvailable);
		Map<String, ProductDetail> productItems = new HashMap<String, ProductDetail>();

		for (ProductDetail loopCartObj : existingProducts) {
			productItems.put(loopCartObj.getName(), loopCartObj);
		}
		return productItems;
	}

	public Map<String, ProductDetail> getProductDetailsMapByProductId(boolean onlyAvailable) {

		List<ProductDetail> existingProducts = getProducts(onlyAvailable);
		Map<String, ProductDetail> productItems = new HashMap<String, ProductDetail>();
		//System.out.println(existingProducts.toString());

		for (ProductDetail loopCartObj : existingProducts) {
			productItems.put(loopCartObj.getProductId(), loopCartObj);
		}
		return productItems;
	}

	public List<CartDetail> getCartsByUserName(String userName) {

		List<CartDetail> carts = new ArrayList<CartDetail>();
		List<CartDetail> allCarts = getCarts();

		for (CartDetail cart : allCarts) {
			System.out.println("Cart details -- " + cart);
			if (cart.getUserName().equals(userName)) {
				carts.add(cart);
			}
		}

		return carts;
	}

	public List<CartDetail> getCartsByCartId(String cartId) {

		List<CartDetail> carts = new ArrayList<CartDetail>();
		List<CartDetail> allCarts = getCarts();

		for (CartDetail cart : allCarts) {
			if (cart.getCartId().equals(cartId)) {
				carts.add(cart);
			}
		}

		return carts;
	}

	public String getUserNameFromToken(String tokenString) {

		List<String> allTokens = getAllTokens();
		String userName = "";

		for (String token : allTokens) {
			String[] tokenArray = token.split(":");

			if (tokenArray[2].equals(tokenString)) {
				userName = tokenArray[0];
				break;
			}
		}

		return userName;
	}

	public String orderSubmited(String cartId, String tokenString) {
		boolean anyFailed = false;

		List<String[]> allOrders = csvFileHandler.readOrderDetails();

		Integer orderId = (allOrders.size() / 2)+1;
		System.out.println("orderId: " + orderId);

		OrderDetailsVO orderDetailsVO = new OrderDetailsVO();
		List<OrderedProductVO> orderedProductVOList = orderDetailsVO.getOrderedProducts();

		// Removing items from cartDetails.csv for cartId
		Map<String, List<CartDetail>> allCarts = getCartDetailsMap();

		// Updating quantity in productDetails.csv
		Map<String, ProductDetail> allProducts = getProductDetailsMapByProductId(false);
		System.out.println(allProducts.toString());

		if (allCarts.get(cartId) != null) {

			orderDetailsVO.setOrderId(String.valueOf(orderId));
			orderDetailsVO.setCartId(cartId);
			orderDetailsVO.setUserName(getUserNameFromToken(tokenString));

			List<CartDetail> listOfItemsInCart = getCartsByCartId(cartId);

			for (CartDetail cart : listOfItemsInCart) {
				if (removeCartItem(cart, true)) {

				} else {
					return "CART_UPDATE_FAILEDE";
				}

				System.out.println("cart.getProductId(): " + cart.getProductId());
				ProductDetail tempProduct = allProducts.get(cart.getProductId());

				if (tempProduct == null) {
					return "PRODUCT_NOT_FOUND";
				}

				if ((tempProduct.getQtyAvailable() - cart.getQuantity()) >= 0) {
					tempProduct.setQtyAvailable(tempProduct.getQtyAvailable() - cart.getQuantity());

				} else {
					return "QTY_NOT_AVAILABLE";
				}

				if (addProduct(tempProduct)) {
					OrderedProductVO orderedProductVO = new OrderedProductVO();

					orderedProductVO.setProductId(tempProduct.getProductId());
					orderedProductVO.setName(tempProduct.getName());
					orderedProductVO.setDescription(tempProduct.getDescription());
					orderedProductVO.setCost(tempProduct.getCost());
					orderedProductVO.setQuantity(String.valueOf(tempProduct.getQtyAvailable()));

					orderedProductVOList.add(orderedProductVO);
				} else {
					return "PRODUCT_UPDATE_FAILED";
				}
			}

			if (csvFileHandler.writeOrderDetails(orderDetailsVO, true)) {
				return orderDetailsVO.getOrderId();

			} else {
				return "WRITING_TO_ORDER_DETAILS_FAILED";

			}

		} else {
			return "CART_NOT_FOUND";

		}

	}

	public boolean writeCarts(List<CartDetail> carts) {
		return csvFileHandler.writeCarts(beanMapingUtil.mapCartDetailsToCartDetailVOs(carts));
	}

	public boolean writeProducts(List<ProductDetail> products) {
		return csvFileHandler.writeProducts(beanMapingUtil.mapProductDetailsToProductDetailVOs(products));
	}

}
