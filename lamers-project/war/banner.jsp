<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page import="java.io.BufferedReader" %>
<%@ page import="java.io.FileReader" %>

<% BufferedReader reader = new BufferedReader(new FileReader("terms.txt")); %>
<% String instructor = reader.readLine(); %>
<% String student = reader.readLine(); %>
<% String course = reader.readLine(); %>
<% String businessTitle = reader.readLine(); %>

<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Banner</title>
	
	<style type="text/css">
	
	body{
		background-color: #404040;
		color: white;
		font-family: Helvetica, Arial, sans-serif;
	}
	
	#logout {
		position: absolute;
    	z-index: 2;
    	top: 5px;
    	left: 95%;
    	font-family: Helvetica, Arial, sans-serif;
	}
	
	a:link{
	 	text-decoration: none;
	 	color: white;
	 	font-family: Helvetica, Arial, sans-serif;
	 }

	 a:visited{
	 	text-decoration: none;
	 	color: white;
	 }

	 a:hover{
	 	color:gray;
	 }
	
	</style>
</head>

<body>
	<h1><%= businessTitle %></h1>
	
	<div id="logout">
		<a href="logout" target="_top"> Logout </a>
	</div>
</body>
</html>