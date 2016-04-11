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
	<br><br><br><br><br><br>
	<table class="table table-striped table-hover ">
  <thead>
    <tr>
      <th>#</th>
      <th>Post</th>
      <th>Creator</th>
      <th>Date of creation</th>
    </tr>
  </thead>
  <tbody>
  <c:forEach begin="1" end="${fn:length(posts)}" varStatus="loop">
   <tr>
      <td>${loop.index}</td>
      <td>${posts[loop.index-1].post }</td>
      <td>${posts[loop.index-1].owner.firstName } ${posts[loop.index-1].owner.lastName }</td>
      <td>${posts[loop.index-1].date_created}</td>
      <td>
      	<form action="viewPost" method="get">
      	<input type="hidden" value="${loop.index-1}" name="postIndex">
      	<input type="submit" value="view">
      	</form>
      </td>
    </tr>
   </c:forEach>
  </tbody>
</table> 

<a href="createPost" class="btn btn-primary" style="float:right">Create post</a>


</body>
</html>