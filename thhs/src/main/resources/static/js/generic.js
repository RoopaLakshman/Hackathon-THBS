function callCartDetails() {
	var securityCookie = $.cookie('token');
	if (typeof securityCookie === "undefined" || null === securityCookie
			|| securityCookie === '') {
		securityCookie = "";
	}
	$
			.ajax({
				type : "GET",
				url : "cart/details",
				dataType : 'json',
				cache : false,
				xhrFields : {
					withCredentials : true
				},
				beforeSend : function(xhr) {
					xhr.setRequestHeader('token', securityCookie);
				},
				success : function(response) {
					var totalCost = 0;
					var totalProducts = 0;
					for (i = 0; i < response.length; i++) {
						totalCost = totalCost + parseInt(response[i].cost);
						totalProducts = totalProducts + parseInt(response[i].quantity);
						$("#simpleCart_totalNew").text("$" + totalCost);
						$("#simpleCart_quantityNew").text(
								totalProducts + " items");
						
						 $('#cartProductsHidden')
				         .append($("<option data-attr-cartId="+response[i].cartId+" data-attr-quantity="+response[i].quantity+"></option>")
				                    .attr("value",response[i].productId)
				                    .text(response[i].productId)); 
					}
				},
				error : function(e) {
					if (e.status == "401" || e.status == "400") {
						if (e.status == "401" || e.status == "400") {
							var json = "<h4>Please login</h4><pre>"
									+ e.responseText + "</pre>";
							$('#myModal4').modal('show');
						} else {
							var json = "<h4>Oops!! Something went wrong. Apologies!</h4><pre>"
									+ e.responseText + "</pre>";
						}
						console.log("ERROR : ", e);
					}
				}
			});

}