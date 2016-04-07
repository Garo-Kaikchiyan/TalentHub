<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" 
           uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>My Profile</title>
<link type="text/css" rel="stylesheet" href="css/style.css" />
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
		<div id="form4" class="bodyx">
		<form action=changeProfile method="post">
			Change your: <input placeholder="Password" type="text"
				id="mailbox" name="password" value="${password}"/><br>
				  <input
				placeholder="Twitter account" type="text" id="mailbox"
				name="twitter_account" /><br> <input
				placeholder="Github account" type="text" id="mailbox"
				name="github_account" /><br> <input
				placeholder="Stackoverflow account" type="text" id="mailbox"
				name="stackoverflow_account" /><br>
				<input type="submit" value="Submit">
			</form>
			<p>Choose your profile picture</p>
			<form action="forum.html">
				<input type="file" name="pic" accept="image/*"> <input
					type="submit">
			</form>
		</div>
	</div>
</body>
</html>