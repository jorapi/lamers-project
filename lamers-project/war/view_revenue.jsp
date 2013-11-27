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
			<caption>Revenue</caption>
			<tr>	
				<th>Instructor</th>
				<th>Course</th>
				<th>Revenue</th>
			</tr>
			
			<% for (Instructor in : instructors) { %>
				<tr>
					<td><%= in.getName() %></td>
					<td><ul>
						<% for (Course c : in.getCourses()) { %>
							<li><%= c.getTitle() %></li>
						<% } %>
					</ul></td>
					<td><ul>
						<% double total = 0.0; %>
						<% for (Course c : in.getCourses()) { %>
							<li>$<%= total += (c.getCost() * c.getClasslist().size()) %></li>
						<% } %>
					</ul>
						Total: $<%= total %>
					</td>
				</tr>
			<% } %>
		</table>
		
		<div id='create-link'>
			<a class='enroll' href='/CreateCourse' target='content'>Create New <%= course %></a>
		</div>
		
	</body>
</html>