$(document).ready(function() {
$("#register").click(function() {
$("div.errorDiv").hide();
$("div.errorDiv").empty();
var name = $("#name").val();
var lName = $("#lname").val();
var email = $("#email").val();
var password = $("#password").val();
var cpassword = $("#cpassword").val();
var isError=false;
if (name == '' || email == '' || password == '' || cpassword == '') {
	$("div.errorDiv").append( "<li>All fields are mandatory</li>" );
	isError =true;
} else {

if ((password.length) < 8) {
	$("div.errorDiv").append("Password should atleast 8 character in length.");
	isError =true;
} else if (!(password).match(cpassword)) {
	$("div.errorDiv").text("Password doesn't match");
	isError =true;
} 
}
if (isError == true) {
   	$("div.errorDiv").show();
	$("div.errorDiv").removeClass("success");
		$("div.errorDiv").addClass("error");
} else {
var payload = {"firstName": name , "lastName":lName,"email": email ,"password": password};
	$.ajax({
  method: "POST",
   beforeSend: function (request)
            {
                request.setRequestHeader("SECRET_KEY" ,"0IGpk3GTkNBEbO8v8ei35J1vRY9v7WR1");
            },
   dataType: "json",
   contentType: "application/json",
  url: "ws/ckt/register",
  data: JSON.stringify(payload)
})
  .done(function( msg ) {
  if (msg.errors.length > 0) {
   	alert(msg.errors);
	} else {
		$("div.errorDiv").empty();
		$("div.errorDiv").removeClass("error");
		$("div.errorDiv").addClass("success");
		$("div.errorDiv").append("You have been registered successfuly with QSports!!. ");
		$("div.errorDiv").show();
		$("#form").clearForm();
	}
});
}
});

$.fn.clearForm = function() {
  return this.each(function() {
    var type = this.type, tag = this.tagName.toLowerCase();
    if (tag == 'form')
      return $(':input',this).clearForm();
    if (type == 'text' || type == 'password' || tag == 'textarea')
      this.value = '';
    else if (type == 'checkbox' || type == 'radio')
      this.checked = false;
    else if (tag == 'select')
      this.selectedIndex = -1;
  });
};


$("#sendpwd").click(function() {
$("div.errorDiv").hide();
$("div.errorDiv").empty();
var email = $("#email").val();
var isError=false;
if (email == '') {
	$("div.errorDiv").append( "<li>Please provide email Id</li>" );
	isError =true;
} 
if (isError == true) {
   	$("div.errorDiv").show();
	$("div.errorDiv").removeClass("success");
		$("div.errorDiv").addClass("error");
} else {
	$.ajax({
  method: "GET",
 
  url: "forgotpwd?email=" + email,
})
  .done(function( msg ) {
  
		$("div.errorDiv").empty();
		$("div.errorDiv").removeClass("error");
		$("div.errorDiv").addClass("success");
		$("div.errorDiv").append("Password has been sent to registered email with QSports!!. ");
		$("div.errorDiv").show();
		$("#form").clearForm();
	
});
}
});

$.fn.clearForm = function() {
  return this.each(function() {
    var type = this.type, tag = this.tagName.toLowerCase();
    if (tag == 'form')
      return $(':input',this).clearForm();
    if (type == 'text' || type == 'password' || tag == 'textarea')
      this.value = '';
    else if (type == 'checkbox' || type == 'radio')
      this.checked = false;
    else if (tag == 'select')
      this.selectedIndex = -1;
  });
};
});