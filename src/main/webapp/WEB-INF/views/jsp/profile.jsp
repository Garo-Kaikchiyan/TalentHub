<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
     <%@ taglib prefix="c" 
           uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="UTF-8">
<title>talentsHub</title>
<link type="text/css" rel="stylesheet" href="css/style.css" />
<link href="https://fontastic.s3.amazonaws.com/koZpYWicqpccnhbmDcBiTG/icons.css" rel="stylesheet">
 <link rel="stylesheet" href="css/normalize.css"> 
  <link rel="stylesheet" href="css/style1.css"> 
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
	
<section class="card">
  <figure class="panel meta">
    <picture>
      <img class="avatar" src="${user.photo }" width="128" height="128"/>
      <img class="company-logo" src="img/star_icon.png" alt="BigglesCodes" width="40" height="40"/>
    </picture>
    <figcaption>

      <h1 class="name">${user.firstName} ${user.lastName}</h1>
      <h3 class="title">IT Talent</h3>
    </figcaption>
  </figure>
  
  <div class="panel info">
    <dl class="skillz">
      <dt>Java/EE</dt>
      <dd>0</dd>
      <dt>Java/Android</dt>
      <dd>0</dd>
      <dt>Javascript</dt>
      <dd>0</dd>
      <dt>PHP</dt>
      <dd>0</dd>
    </dl>
    
    <ul class="social">
      <li><a class="icon-social-twitter" href="https://twitter.com/bigglesrocks">Twitter</a></li>
      <li><a class="icon-social-github" href="https://github.com/jlegosama">Github</a></li>
      <li><a class="icon-social-stack-overflow" href="http://stackoverflow.com/users/664904/jlego">StackOverflow</a></li>
    </ul>
    
  </div>
</section>
     <script src='http://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.3/jquery.min.js'></script> 

        <script src="js/index.js"></script>
		

</body>
</html>