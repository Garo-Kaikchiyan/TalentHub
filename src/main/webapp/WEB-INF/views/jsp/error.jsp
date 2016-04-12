<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<style>
</style>
<meta charset="UTF-8">
<title>talentsHub</title>
<link type="text/css" rel="stylesheet" href="../css/style.css" />
<link rel="stylesheet" href="../css/normalize.css">
<link rel="stylesheet" href="../css/style1.css">
<link type="text/css" rel="stylesheet" href="../css/bootstrap.css" />
</head>
<body>
	<div class="headerx"></div>
	<div class="header">
		<div id="logo" class="header">
			<a href="index"><img src="img/logo.png" /></a>
		</div>
		<div id="form2" class="header">
		<form action="myProfile.htm" method="get">
			&nbsp; <br> <a href="changeProfile" class="btn btn-primary btn-xs">My profile</a><br>
		</form>
		</div>
		<form action="logout.htm" method="post">
		<a href="logout" class="submit1 btn btn-primary btn-xs">Logout</a>
		</form>
	</div>
<div class="alert alert-dismissible alert-danger" id="outPopUp">
  <strong>Oops!</strong> Something went wrong! You can go to <a href="/main" class="alert-link">main page</a> and try again.
  </div>
</body>
</html>