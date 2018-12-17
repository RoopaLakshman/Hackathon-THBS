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
								url : "product/view/details",
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
									for (i = 0; i < data.length; i++) {
										json = json
												+ '<div class="col-md-3 product-men">'
												+ '<div class="men-pro-item simpleCart_shelfItem">'
												+ '<div class="men-thumb-item">'
												+ '<img id="productImage'
												+ i
												+ '" src="images/a'
												+ i
												+ '.jpg" th:src="@{images/a'
												+ i
												+ '.jpg}" alt="" class="pro-image-front">'
												+ '<img id="productImage'
												+ i
												+ '" src="images/a'
												+ i
												+ '.jpg" th:src="@{images/a'
												+ i
												+ '.jpg}" alt="" class="pro-image-back">'
												+ '<span class="product-new-top">New</span>'
												+ '</div>'
												+ '<div class="item-info-product ">'
												+ '<h4>'
												+ '<a href="#">'
												+ data[i].name
												+ '</a>'
												+ '</h4>'
												+ '<div class="info-product-price">'
												+ '<span class="item_price">'
												+ data[i].cost
												+ ' '
												+ data[i].currency
												+ '</span>'
												+ '<del>'
												+ (parseInt(data[i].cost) + 10)
												+ ' '
												+ data[i].currency
												+ '</del>'
												+ '</div>'
												+ '<a href="#" onclick="addToCart('
												+ data[i].productId
												+ ')" class="item_add single-item hvr-outline-out button2">Add to cart</a>'
												+ '</div>' + '</div>'
												+ '</div>'
									}
									callCartDetails();
									$('#products').html(json);
									console.log("SUCCESS : ", data);
								},
								error : function(e) {

									if (e.status == "401" || e.status == "400") {
										var json = "<h4>Please login</h4>";
										$('#myModal4').modal('show');
									} else {
										var json = "<h4>Oops!! Something went wrong. Apologies!</h4><pre>"
												+ e.status + "</pre>";
									}
									$('#products').html(json);
									console.log("ERROR : ", e);
								}
							});
				});

function addToCart(productId) {
	var urlCall = "cart/add";
	var methodType = "POST";
	var cart;
	var quan = 1;
	$("#cartProductsHidden option").each(function() {
		cart = $(this).attr('data-attr-cartId');
		if ($(this).val() == productId) {
			urlCall = "cart/modify";
			methodType = "PUT";
			quan = $(this).attr('data-attr-quantity');
			quan = (parseInt(quan) + 1);
		}
	});

	var securityCookie = $.cookie('token');
	if (typeof securityCookie === "undefined" || null === securityCookie
			|| securityCookie === '') {
		securityCookie = "";
	}
	var prodId = productId;
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
				type : methodType,
				contentType : "application/json",
				url : urlCall,
				data : inputdata,
				xhrFields : {
					withCredentials : true
				},
				beforeSend : function(xhr) {
					xhr.setRequestHeader('token', securityCookie);
				},
				cache : false,
				success : function(response) {
					callCartDetails();
					window.location.replace("/thhs/api/");
					alert("Successfully added to cart");
				},
				error : function(e) {
					if (e.status == "401" || e.status == "400") {
						window.location.replace("/thhs/api/");
					} else {
						var json = "<h4>Oops!! Something went wrong. Apologies!</h4><pre>"
								+ e.responseText + "</pre>";
					}
					console.log("ERROR : ", e);
				}
			});
}
