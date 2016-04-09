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
			<a href="index"><img src="img/logo.png" /></a>
		</div>
		<div id="form2" class="header">
			&nbsp; <br> <a href="changeProfile">My Profile</a><br>
		</div>
		<input type="submit" class="submit1" value="Logout" />
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
  <c:forEach begin="${startIndex}" end="${endIndex}" varStatus="loop">
   <tr>
      <td>${loop.index}</td>
      <td>${questions[loop.index].question_title }</td>
      <td>${questions[loop.index].author_name }</td>
      <td>${questions[loop.index].date_created}</td>
      <td>
      	<form action="thread" method="get">
      	<input type="hidden" value="${questions[loop.index].question_title}" name="question">
      	<input type="submit" value="view">
      	</form>
      </td>
    </tr>
   </c:forEach>
  </tbody>
</table> 

<a href="#" class="btn btn-primary" style="float:right">Create thread</a>
<textarea placeholder="Remember, be nice!" cols="60" rows="5" style="float:center"></textarea>


</body>
</html>