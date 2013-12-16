<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page import="java.io.BufferedReader" %>
<%@ page import="java.io.FileReader" %>

<% BufferedReader reader = new BufferedReader(new FileReader("terms.txt")); %>
<% String instructor = reader.readLine(); %>
<% String student = reader.readLine(); %>
<% String course = reader.readLine(); %>
<% String title = reader.readLine(); %>

<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title><%= title %></title>
</head>
	<frameset rows="65,*" framespacing="0" frameborder="NO">
		<FRAME name="banner" src="banner.jsp" scrolling=no>
		<frameset cols="150,*">
			<FRAME name="navbar" src="admin-nav.jsp">
			<FRAME name="content" src="/ViewStudents">
		</frameset>
	</frameset>
</html>