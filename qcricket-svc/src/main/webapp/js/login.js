$(document).ready(function() {
localStorage.removeItem('userObj');
localStorage.removeItem('profileObj');
	$("#btnsignin").click(function() {
	
	$("div.errorDiv").hide();
	$("div.errorDiv").empty();
	var username = $("#username").val();
	var password = $("#password").val();

	if (username == '' || password == '') {
		$("div.errorDiv").append("<li>All fields are mandatory</li>" );
		isError =true;
	} else {
		var payload = {"email": username , "registrationType":"INTERNAL","password": password};
		$.ajax({
		method: "POST",
		beforeSend: function (request)
           {
               request.setRequestHeader("SECRET_KEY" ,"0IGpk3GTkNBEbO8v8ei35J1vRY9v7WR1");
           },
		dataType: "json",
		contentType: "application/json",
		url: "ws/ckt/login",
		data: JSON.stringify(payload)
		})
		.done(function( msg ) {
			if (msg.errors.length > 0) {
				alert(msg.errors);
			} else {
				callProfileServices();	
			}
		});
	}
});
});