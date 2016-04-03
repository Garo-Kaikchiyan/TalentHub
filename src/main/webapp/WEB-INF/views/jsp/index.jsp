<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" 
           uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<script>function validateEmail(emailField, buttonName){
	    var reg = /^([A-Za-z0-9_\-\.])+\@([A-Za-z0-9_\-\.])+\.([A-Za-z]{2,4})$/;
	
	    if (reg.test(emailField.value) == false) 
	    {
	        document.getElementsByName(buttonName)[0].disabled = true;
	        alert("Please enter a valid e-mail")
	        return false;
	    }
	    document.getElementsByName(buttonName)[0].disabled = false;
	
	}</script>
<title>talentsHub</title>
<link type="text/css" rel="stylesheet" href="css/style.css" />
</head>
<body><div class="headerx">
</div>
	<form action="login" method="post">
		<div class="header">
			<div id="logo" class="header"><a href="index.html"><img src="img/logo.png"/></a> </div>
				<div id="form1" class="header">
					Email<br>
					<c:if test=""></c:if>
					<input placeholder="Email" type="email" name="email" value ="${loginEmail}" onblur="validateEmail(this,'loginButton');"/><br>
					<input type="checkbox" /> 
					Keep me logged in
					<br><label style="color: red;"> ${invalidUser}</label>
				</div>
				<div id="form2" class="header">
					Password<br>
					<input placeholder="Password" type="password" name="password"/>
					 <a href="/TalentHub/forgottenPassword"><br>Forgotten my password</a>
				</div> 
		</div>
		<input type="submit" class="submit1" value="login" name ="loginButton"/>
	</form>
<div class="bodyx">
	<form action="register" method="post">
		<div id="intro1" class="bodyx">TalentHub -<br> Share your ideas!</div>
		<div id="intro2" class="bodyx">Create an account</div> 
		<div id="img2" class="bodyx"><img src = "img/community.png"/></div>
		<div id="form3" class="bodyx">
			<input placeholder="Firstname" type="text" id="namebox" name="firstName" value="${firstName}"/>
			<input placeholder="Lastname" type="text" id="namebox" name="lastName" value="${lastName}"/>
			<input placeholder="Email" type="text" id="mailbox" name="email" value="${regEmail}" onblur="validateEmail(this,'regButton')"/><br>
			<input placeholder="Re-enter email" type="text" id="mailbox" name="email2" /><br>
			<input placeholder="Password" type="password" id="mailbox" name="pass" /><br>
			<input type="date" id="namebox"/><br><br>
			<input type="radio" name="sex" value="male"/> Male
			<input type="radio" name="sex" value="female"/> Female<br>
			<label style="font-size:medium; color: red" >${errMsg}</label><br>
			<span>You will create an account.</span>

		</div>
		
		<input type="submit" class="button2" value="Go!" name ="regButton"/>
	</form>
</div>
</body>
</html>