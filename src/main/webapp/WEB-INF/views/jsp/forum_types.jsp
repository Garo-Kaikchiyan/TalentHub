<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<style>

</style>
<meta charset="UTF-8">
<title>talentsHub</title>
<link type="text/css" rel="stylesheet" href="css/buttons.css" />
<link type="text/css" rel="stylesheet" href="css/style.css" />
<link
	href="https://fontastic.s3.amazonaws.com/koZpYWicqpccnhbmDcBiTG/icons.css"
	rel="stylesheet">
<link rel="stylesheet" href="css/normalize.css">
<link rel="stylesheet" href="css/style1.css">
<link type="text/css" rel="stylesheet" href="css/bootstrap.css" />
</head>
<body>
	<div class="headerx"></div>
	<div class="header">
		<div id="logo" class="header">
			<a href="main.htm"><img src="img/logo.png" /></a>
		</div>
		<div id="form2" class="header">
			<form action="myProfile.htm" method="get">
			&nbsp; <br> <a href="myProfile.htm" class="btn btn-primary btn-xs">My profile</a><br>
			</form>
		</div>
		<form action="logout.htm" method="post">
		<a href="logout.htm" class="submit1 btn btn-primary btn-xs">Logout</a>
		</form>
	</div>
	<div class="bodyx">
	
		<div class="button-container">
			<form action="javaForum" method="get">
				<button id="btn1" class="css_button"></button>
			</form>
			<form action="androidForum" method="get">
				<button id="btn2" class="css_button"></button>
			</form>
		</div>
		<div class="button-container">
			<form action="phpForum" method="get">
				<button id="btn3" class="css_button"></button>
			</form>
			<form action="jsForum" method="get">
				<button id="btn4" class="css_button"></button>
			</form>
		</div>
	</div>

</body>
</html>