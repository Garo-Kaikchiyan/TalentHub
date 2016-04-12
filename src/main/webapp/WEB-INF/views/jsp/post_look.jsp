<%@ page language="java" contentType="text/html"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" 
           uri="http://java.sun.com/jsp/jstl/core" %>
           <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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
	
	<br><br><br><br><br>
	<div id="content">
		<h1 align="center"><b> <c:out value="${group.group_name}"/></b></h1>
		<!-- POST HEADER -->
		<div class="post-header">
		</div>
		<div class="post">
			<!-- POST META -->
			<div class="post-meta">
				<div class="col30pc">
				<form action="getProfile" method="post">
					<span>
						<img src="http://forums.data.bg/public/style_images/Data.BG/user_off.png">
					</span>
					 <input type="hidden" value="${post.owner.email}" name="user">
					<input type="submit" value="${post.owner.firstName } ${post.owner.lastName }"/>
					</form>
				</div><div class="col70pc">
					<span>Публикувано: ${post.date_created }</span>
				</div>
			</div>

			<!-- POST MAIN -->
			<div class="post-main">
				<div class="col30pc post-leftbar">
					<div class="avatar">
						<img src="${post.owner.photo }">
					</div>
					<div>
						<strong>Група:</strong>
						<span>${group.group_name }</span>
					</div>
					<div>
						<strong></strong>
						<span></span>
					</div>
					<div>
						<strong>Регистриран:</strong>
						<span>20 may 2011</span>
					</div>
					<div>
						<strong></strong>
						<span></span>
					</div>
					<div>
						<strong>Рождена дата:</strong>
						<span>${post.owner.birth}</span>
					</div>
				</div>

				<div class="col70pc post-content">
					${post.post }
				</div>
			</div>
</div>
			<!-- POST FOOTER -->
			<div class="post-footer">
				<a href="#content"></a>
			</div>
		</div>
		<c:forEach begin="1" end="${fn:length(comments)}" varStatus="loop">
		
		<div class="post" style="width:77%; margin:auto">
			<!-- POST META -->
			<div class="post-meta">
				<div class="col30pc">
					
					<form action="getProfile" method="post">
					<span>
						<img src="http://forums.data.bg/public/style_images/Data.BG/user_off.png">
					</span>
					 <input type="hidden" value="${comments[loop.index-1].owner.email}" name="user">
					<input type="submit" value="${comments[loop.index-1].owner.firstName } ${comments[loop.index-1].owner.lastName }"/>
					</form>
				</div><div class="col70pc">
					<span>Публикувано: ${comments[loop.index-1].date_created }</span>
				</div>
			</div>

			<!-- POST MAIN -->
			<div class="post-main">
				<div class="col30pc post-leftbar">
					<div class="avatar">
						<img src="${comments[loop.index-1].owner.photo }">
					</div>
					<div>
						<strong>Група:</strong>
						<span>${group.group_name }</span>
					</div>
					<div>
						<strong></strong>
						<span></span>
					</div>
					<div>
						<strong>Регистриран:</strong>
						<span></span>
					</div>
					<div>
						<strong></strong>
						<span></span>
					</div>
					<div>
						<strong>Рождена дата:</strong>
						<span></span>
					</div>
				</div>

				<div class="col70pc post-content">
					${comments[loop.index-1].text }
				</div>
			</div>
</div>
			<!-- POST FOOTER -->
			<div class="post-footer" style="width:77%; margin:auto">
				<br><br>
			</div>
			</c:forEach>
			
		<form action="addComment" method ="post">
		<div class="form-group">
      <label for="textArea" class="col-lg-2 control-label"></label>
      <div class="col-lg-10" style="width:800px">
        <textarea class="form-control" rows="3" id="textArea" name="text"></textarea>
      </div>
      </div>
        <button type="submit" class="btn btn-primary" style="float:left">Post reply</button>
        <c:set var="curPost" scope="session" value="${post}" ></c:set>
		</form>
		<br>
</body>
</html>