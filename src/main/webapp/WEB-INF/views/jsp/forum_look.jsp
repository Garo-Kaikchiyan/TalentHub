<%@ page language="java" contentType="text/html"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" 
           uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<style>

</style>
<meta charset="UTF-8">
<title>talentsHub</title><link type="text/css" rel="stylesheet" href="css/style.css" />
<link rel="stylesheet" href="css/normalize.css">
<link rel="stylesheet" href="css/style1.css">
<link rel="stylesheet" href="css/post.css">
<link type="text/css" rel="stylesheet" href="css/bootstrap.css" />
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
	
	<br><br><br>
	<div id="content">
		<h1>Форум</h1>
		<!-- POST HEADER -->
		<div class="post-header">
			<h3>${question.question_title}</h3>
		</div>
		<div class="post">
			<!-- POST META -->
			<div class="post-meta">
				<div class="col30pc">
					<span>
						<img src="http://forums.data.bg/public/style_images/Data.BG/user_off.png">
					</span>
					<span>${question.user_name }</span>
				</div><div class="col70pc">
					<span>Публикувано: ${question.date_created }</span>
				</div>
			</div>

			<!-- POST MAIN -->
			<div class="post-main">
				<div class="col30pc post-leftbar">
					<div class="avatar">
						<img src="http://flatter.cloudtub.com/img/avatar.png">
					</div>
					<div>
						<strong>Група:</strong>
						<span>JavaEE</span>
					</div>
					<div>
						<strong>Мнения:</strong>
						<span>35</span>
					</div>
					<div>
						<strong>Регистриран:</strong>
						<span>20 may 2011</span>
					</div>
					<div>
						<strong>Ранг:</strong>
						<span>Master</span>
					</div>
					<div>
						<strong>Рождена дата:</strong>
						<span>02.02.02</span>
					</div>
				</div>

				<div class="col70pc post-content">
					${question.question_text }
				</div>
			</div>
</div>
			<!-- POST FOOTER -->
			<div class="post-footer">
				<a href="#content">Kym nachaloto</a>
			</div>
		</div>

</body>
</html>