function loginUser(e){
	var user = $("#loginUsername").val();
	var pwd = $("#loginPassword").val();
	
	var credentials = {
			username : user, 
			password: pwd
	};
	var inputdata = JSON.stringify(credentials);
	$
	.ajax({
		type : "POST",
		contentType : "application/json",
		url : "login",
		data : inputdata,
		cache : false,
		success : function(response) {
			window.location.replace("/thhs/api/");
			console.log("SUCCESS : ", response);
		},
		error : function(e) {
			if(e.status == "401" || e.status == "400") {
				window.location.replace("/thhs/api/");
			} else {
				var json = "<h4>Oops!! Something went wrong. Apologies!</h4><pre>"
						+ e.responseText + "</pre>";
			}
			console.log("ERROR : ", e);
		}
	});
	
}