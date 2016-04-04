<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Forgotten Password</title>
<link type="text/css" rel="stylesheet" href="css/style.css" />
</head>
<body><div class="headerx">

<div class="header">
	<div id="logo" class="header"><a href="index"><img src="img/logo.png"/></a> </div>
		<div id="form1" class="header">Email<br>
		<input placeholder="Email" type="email" name="mail" /><br>
		<input type="checkbox" /> Keep me logged in</div>
		<div id="form2" class="header">Password<br>
		<input placeholder="Password" type="password" name="password"/><br> <a href="forgottenPassword">
		Forgotten my password</a></div> 
</div>
<input type="submit" class="submit1" value="login"/>
	<div class="bodyx">
		<form action="/passwordReset" method="post">
			<div id="form4" class="bodyx">
			Enter your email and you will receive your password.
				<input placeholder="Email" type="text" id="mailbox" name="email" /><br>
				<input type="submit" value="Reset Password"/>
			<label style="font-size:medium; color: red" >${errMsg}</label>
			</div>
		</form>
	</div>
</div>
</body>
</html>