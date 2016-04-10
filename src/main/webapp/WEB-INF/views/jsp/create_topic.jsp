<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<style>
</style>
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
		
<br><br><br><br><br><br><br>
	<form class="form-horizontal" action="createQuestion"  method="post">
  <fieldset>
    <legend>Post new thread</legend>
    <div class="form-group">
      <label for="textArea" class="col-lg-2 control-label">Subject</label>
      <div class="col-lg-10" style="width:800px">
        <textarea class="form-control" rows="1" id="textArea" name="subject"></textarea>
      </div>
    </div>
    <div class="form-group">
      <label for="textArea" class="col-lg-2 control-label">Textarea</label>
      <div class="col-lg-10" style="width:800px">
        <textarea class="form-control" rows="3" id="textArea" name="text"></textarea>
        <span class="help-block">Remember to check if the topic exists, before posting it.</span>
      </div>
    </div>
    <div class="form-group">
      <div class="col-lg-10 col-lg-offset-2">
        <button type="submit" class="btn btn-primary" style="float:right">Submit</button>
      </div>
    </div>
    </fieldset>
    </form>

</body>
</html>