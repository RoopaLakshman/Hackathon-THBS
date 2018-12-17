$(document)
		.ready(
				function() {
					var securityCookie = $.cookie('token');
					if (typeof securityCookie === "undefined"
							|| null === securityCookie || securityCookie === '') {
						securityCookie = "";
						$("#loginIcons").css('display', 'show');
					} else {
						$("#loginIcons").css('display', 'none');
					}
					$
							.ajax({
								type : "GET",
								contentType : "application/json",
								url : "cart/view",
								dataType : 'json',
								cache : false,
								timeout : 600000,
								xhrFields : {
									withCredentials : true
								},
								beforeSend : function(xhr) {
									xhr.setRequestHeader('token',
											securityCookie);
								},
								success : function(data) {
									var json = '';
									if (data.length == 0) {
										json = '<tr class="rem"><td colspan="5">No items in cart</td></tr>';
									}
									for (i = 0; i < data.length; i++) {
										$(".submitCart").attr(
												'data-attr-cart-id',
												data[i].cartId);
										json = json
												+ '<tr class="rem'
												+ i
												+ '" id="rem'
												+ i
												+ '">'
												+ '<td class="invert-closeb">'
												+ '<div class="rem">'
												+ '<div class="close'
												+ i
												+ '" data-attr-cart-id="'
												+ data[i].cartId
												+ '" data-attr-quantity="'
												+ data[i].quantity
												+ '" data-attr-product-id="'
												+ data[i].productId
												+ '"> </div>'
												+ '</div>'
												+ '</td>'
												+ '<td class="invert-image"><a href="#"><img src="images/a'
												+ i
												+ '.jpg" alt=" " class="img-responsive" /></a></td>'
												+ '<td class="invert">'
												+ '<div class="quantity">'
												+ '<div class="quantity-select">'
												+ '<div class="entry value-minus" data-attr-cart-id="'
												+ data[i].cartId
												+ '" data-attr-quantity="'
												+ data[i].quantity
												+ '" data-attr-product-id="'
												+ data[i].productId
												+ '">&nbsp;</div>'
												+ '<div class="entry value"><span>'
												+ data[i].quantity
												+ '</span></div>'
												+ '<div class="entry value-plus active" data-attr-cart-id="'
												+ data[i].cartId
												+ '" data-attr-quantity="'
												+ data[i].quantity
												+ '" data-attr-product-id="'
												+ data[i].productId
												+ '">&nbsp;</div>' + '</div>'
												+ '</div>' + '</td>'
												+ '<td class="invert">'
												+ data[i].productId + '</td>'
												+ '<td class="invert">Price</td>'
												+ '</tr>'
									}
									$('#cartProducts tbody').append(json);
									callCartDetails();
									reloadElements(data);
									console.log("SUCCESS : ", data);
								},
								error : function(e) {
									if (e.status == "401" || e.status == "400") {
										window.location.replace("/thhs/api/");
									} else {
										var json = "<h4>Oops!! Something went wrong. Apologies!</h4><pre>"
												+ e.responseText + "</pre>";
									}
									$('#cartProducts tbody').append(json);
									console.log("ERROR : ", e);
								}
							});
				});

function reloadElements(data) {
	var listElements = '';
	if (data.length == 0) {
		listElements = '<li>No items in cart</li>';
	}
	for (i = 0; i < data.length; i++) {
		listElements = listElements + '<li data-product-val="'
				+ data[i].productId + '" id="product' + data[i].productId
				+ '">' + data[i].productId
				+ ' <i>-</i> <span>Price</span></li>';
	}
	$('#summaryBasket').html(listElements);
	for (var i = 0; i < data.length; i++) {
		$('body')
				.on(
						'click',
						'div.close' + i,
						function(c) {
							var securityCookie = $.cookie('token');
							if (typeof securityCookie === "undefined"
									|| null === securityCookie
									|| securityCookie === '') {
								securityCookie = "";
							}

							$(this).parent().parent().parent().fadeOut('slow',
									function(c) {
										$('#rem' + i).remove();
									});

							var prodId = $(this).attr('data-attr-product-id');
							var cart = $(this).attr('data-attr-cart-id');
							var quan = $(this).attr('data-attr-quantity');
							var cartList = [];
							var cartDetails = {
								productId : prodId,
								quantity : quan,
								cartId : cart
							};
							cartList.push(cartDetails);
							var inputdata = JSON.stringify(cartList);
							$
									.ajax({
										type : "DELETE",
										contentType : "application/json",
										url : "cart/delete",
										data : inputdata,
										async : false,
										xhrFields : {
											withCredentials : true
										},
										beforeSend : function(xhr) {
											xhr.setRequestHeader('token',
													securityCookie);
										},
										cache : false,
										success : function(response) {
											callCartDetails();
											alert("Product deleted from cart");
											$('#summaryBasket li')
													.each(
															function(i) {
																if ($(this)
																		.attr(
																				'data-product-val') === prodId) {
																	$(this)
																			.remove();
																}
															});
										},
										error : function(e) {
											if (e.status == "401"
													|| e.status == "400") {
												window.location
														.replace("/thhs/api/");
											} else {
												var json = "<h4>Oops!! Something went wrong. Apologies!</h4><pre>"
														+ e.responseText
														+ "</pre>";
											}
											console.log("ERROR : ", e);
										}
									});
						});
	}

	$('body')
			.on(
					'click',
					'div.value-plus',
					function() {
						var divUpd = $(this).parent().find('.value'), newVal = parseInt(
								divUpd.text(), 10) + 1;
						divUpd.text(newVal);
						var securityCookie = $.cookie('token');
						if (typeof securityCookie === "undefined"
								|| null === securityCookie
								|| securityCookie === '') {
							securityCookie = "";
						}
						var prodId = $(this).attr('data-attr-product-id');
						var cart = $(this).attr('data-attr-cart-id');
						var quan = newVal.toString();
						var cartList = [];
						var cartDetails = {
							productId : prodId,
							quantity : quan,
							cartId : cart
						};
						cartList.push(cartDetails);
						var inputdata = JSON.stringify(cartList);
						$
								.ajax({
									type : "PUT",
									contentType : "application/json",
									url : "cart/modify",
									data : inputdata,
									xhrFields : {
										withCredentials : true
									},
									beforeSend : function(xhr) {
										xhr.setRequestHeader('token',
												securityCookie);
									},
									cache : false,
									success : function(response) {
										callCartDetails();
										alert("Successfully modified in cart");
									},
									error : function(e) {
										if (e.status == "401"
												|| e.status == "400") {
											window.location
													.replace("/thhs/api/");
										} else {
											var json = "<h4>Oops!! Something went wrong. Apologies!</h4><pre>"
													+ e.responseText + "</pre>";
										}
										console.log("ERROR : ", e);
									}
								});
					});

	$('body')
			.on(
					'click',
					'div.value-minus',
					function() {
						var divUpd = $(this).parent().find('.value'), newVal = parseInt(
								divUpd.text(), 10) - 1;
						if (newVal >= 1)
							divUpd.text(newVal);
						var securityCookie = $.cookie('token');
						if (typeof securityCookie === "undefined"
								|| null === securityCookie
								|| securityCookie === '') {
							securityCookie = "";
						}
						var prodId = $(this).attr('data-attr-product-id');
						var cart = $(this).attr('data-attr-cart-id');
						var quan = newVal.toString();
						var cartList = [];
						var cartDetails = {
							productId : prodId,
							quantity : quan,
							cartId : cart
						};
						cartList.push(cartDetails);
						var inputdata = JSON.stringify(cartList);
						$
								.ajax({
									type : "PUT",
									contentType : "application/json",
									url : "cart/modify",
									data : inputdata,
									xhrFields : {
										withCredentials : true
									},
									beforeSend : function(xhr) {
										xhr.setRequestHeader('token',
												securityCookie);
									},
									cache : false,
									success : function(response) {
										callCartDetails();
										alert("Successfully modified in cart");
									},
									error : function(e) {
										if (e.status == "401"
												|| e.status == "400") {
											window.location
													.replace("/thhs/api/");
										} else {
											var json = "<h4>Oops!! Something went wrong. Apologies!</h4><pre>"
													+ e.responseText + "</pre>";
										}
										console.log("ERROR : ", e);
									}
								});
					});

	$('body')
			.on(
					'click',
					'input.submitCart',
					function(c) {

						var securityCookie = $.cookie('token');
						if (typeof securityCookie === "undefined"
								|| null === securityCookie
								|| securityCookie === '') {
							securityCookie = "";
						}
						var cartId = $(this).attr('data-attr-cart-id');

						var cartDetails = {
							cartId : cartId
						}
						var inputdata = JSON.stringify(cartDetails);
						$
								.ajax({
									type : "POST",
									contentType : "application/json",
									url : "submit",
									data : inputdata,
									xhrFields : {
										withCredentials : true
									},
									beforeSend : function(xhr) {
										xhr.setRequestHeader('token',
												securityCookie);
									},
									cache : false,
									success : function(response) {
										window.location
												.replace("/thhs/api/checkout");
									},
									error : function(e) {
										if (e.status == "401"
												|| e.status == "400") {
											window.location
													.replace("/thhs/api/");
										} else {
											var json = "<h4>Oops!! Something went wrong. Apologies!</h4><pre>"
													+ e.responseText + "</pre>";
										}
										console.log("ERROR : ", e);
									}
								});
					});
}
