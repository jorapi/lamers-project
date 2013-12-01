<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page import="java.io.BufferedReader" %>
<%@ page import="java.io.FileReader" %>

<% BufferedReader reader = new BufferedReader(new FileReader("terms.txt")); %>
<% String instructor = reader.readLine(); %>
<% String student = reader.readLine(); %>
<% String course = reader.readLine(); %>


<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Administrator Navigation</title>
	
	<style type="text/css">

	body{
		/* IE10 Consumer Preview */ 
		background-image: -ms-linear-gradient(left, #DFDFDF 80%, #C1C1C1 100%);

		/* Mozilla Firefox */ 
		background-image: -moz-linear-gradient(left, #DFDFDF 80%, #C1C1C1 100%);

		/* Opera */ 
		background-image: -o-linear-gradient(left, #DFDFDF 80%, #C1C1C1 100%);

		/* Webkit (Safari/Chrome 10) */ 
		background-image: -webkit-gradient(linear, left top, right top, color-stop(.8, #DFDFDF), color-stop(1, #C1C1C1));

		/* Webkit (Chrome 11+) */ 
		background-image: -webkit-linear-gradient(left, #DFDFDF 80%, #C1C1C1 100%);

		/* W3C Markup, IE10 Release Preview */ 
		background-image: linear-gradient(to right, #DFDFDF 80%, #C1C1C1 100%);
	}
	
	 a.navlink:link{
	 	text-decoration: none;
	 	color: black;
	 	font-family: Helvetica, Arial, sans-serif;
	 	font-weight: bold;
	 }

	 a.navlink:visited{
	 	text-decoration: none;
	 	color: black;
	 }

	 a.navlink:hover{
	 	color:#717171;
	 }

	 </style>
	 
</head>

<body>
	<div id="navbar">
		<br>
		<a class="navlink" href="/ViewCourses" target="content"><%= course %> List</a>
		<br><br>
		<a class="navlink" href="/ViewStudents" target="content"><%= student %> List</a>
		<br><br>
		<a class="navlink" href="/CreateStudent" target="content">Create <%= student %></a>
		<br><br>
		<!--<a class="navlink" href="/UpdateAwards" target="content">Update Awards</a>-->
		<!--<br><br>-->
		<a class="navlink" href="/Attendance" target="content">View Attendance</a>
		<br><br>
	</div>
</body>
</html>