<%@ page language="java" contentType="text/html"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" 
           uri="http://java.sun.com/jsp/jstl/core" %>
           <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
<style>

</style>
<meta charset="UTF-8">
<title>talentsHub</title>
<link type="text/css" rel="stylesheet" href="css/style.css" />
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
			&nbsp; <br> <a href="changeProfile" class="btn btn-primary btn-xs">My profile</a><br>
		</form>
		</div>
		<form action="logout.htm" method="post">
		<a href="logout" class="submit1 btn btn-primary btn-xs">Logout</a>
		</form>
	</div>
	<div class="bodyx">
<br><br><br>
	<c:forEach begin="1" end="${fn:length(groups)}" varStatus="loop">
	<form action="viewGrp" method="post">
		<button class="btn btn-primary btn-lg2" value="${groups[loop.index-1].group_name}" type="submit">${groups[loop.index-1].group_name}</button>
		<input type="hidden" value ="${loop.index-1 }" name="grpIndex"/>
	</form>
	</c:forEach>
	<br>
	<form action="createGroup" method="get">
	<button class="btn btn-success" style="float:right">Create group</button></form>
</div>

</body>
</html>