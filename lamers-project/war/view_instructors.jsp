<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<%@ page import="java.util.List" %>

<%@ page import="javax.jdo.JDOHelper" %>
<%@ page import="javax.jdo.PersistenceManager" %>
<%@ page import="java.io.BufferedReader" %>
<%@ page import="java.io.FileReader" %>

<%@ page import="edu.uwm.lamers.entities.*" %>

<% BufferedReader reader = new BufferedReader(new FileReader("terms.txt")); %>
<% String instructor = reader.readLine(); %>
<% String student = reader.readLine(); %>
<% String course = reader.readLine(); %>

<%
	PersistenceManager pm = JDOHelper.getPersistenceManagerFactory("transactions-optional").getPersistenceManager();
	List<Instructor> instructors = (List<Instructor>) pm.newQuery(Instructor.class).execute();
%>

<html>
	<head>
		<link rel='stylesheet' type='text/css' href='styles/viewstudent.css'>
	</head>
	<body>
		<table id='students'>
			<caption><%= instructor %> List</caption>
			<tr>	
				<th>First Name</th>
				<th>Last Name</th>
				<th>Email</th>
				<th><%= course %> List</th>
			</tr>
			
			<% for (Instructor i : instructors) { %>
				<tr>
					<td><%= i.getFirstName() %></a></td>
					<td><%= i.getLastName() %></td>
					<td><%= i.getEmail() %></td>
					
					<td><ul>
						<% for (Course c : i.getCourses()) { %>
							<li><%= c.getTitle() %></li>
						<% } %>
					</ul></td>
					
				</tr>
			<% } %>
		</table>
		
		<div id='create-link'>
			<a class='enroll' href='/CreateInstructor' target='content'>Create New <%= instructor %></a>
		</div>
		
	</body>
</html>