<%@ page language="java" contentType="text/html"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" 
           uri="http://java.sun.com/jsp/jstl/core" %>
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
	<br><br><br><br><br><br>
	<table class="table table-striped table-hover ">
  <thead>
    <tr>
      <th>#</th>
      <th>Theme</th>
      <th>Creator</th>
      <th>Date of creation</th>
    </tr>
  </thead>
  <tbody>
  <c:forEach begin="${startIndex + 1}" end="${endIndex}" varStatus="loop">
   <tr>
      <td>${loop.index}</td>
      <td>${questions[loop.index-1].question_title }</td>
      <td>${questions[loop.index-1].user_name }</td>
      <td>${questions[loop.index-1].date_created}</td>
      <td>
      	<form action="thread" method="get">
      	<input type="hidden" value="${loop.index-1}" name="questionIndex">
      	<input type="submit" value="view">
      	</form>
      </td>
    </tr>
   </c:forEach>
  </tbody>
</table> 

<a href="createTopic" class="btn btn-primary" style="float:right">Create thread</a>


</body>
</html>