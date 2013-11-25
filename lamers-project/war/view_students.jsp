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
	List<Student> students = (List<Student>) pm.newQuery(Student.class).execute();
%>

<html>
	<head>
		<link rel='stylesheet' type='text/css' href='styles/viewstudent.css'>
	</head>
	<body>
		<table id='students'>
			<caption><%= student %> List</caption>
			<tr>	
				<th>First Name</th>
				<th>Last Name</th>
				<th>Email</th>
				<th><%= course %> List</th>
				<th>Demographic</th>
				<th>Balance Due</th>
				<th>Awards</th>
			</tr>
			
			<% for (Student s : students) { %>
				<tr>
					<td><%= s.getFirstName() %></a></td>
					<td><%= s.getLastName() %></td>
					<td><%= s.getEmail() %></td>
					
					<td><ul>
						<% for (Course c : s.getCourses()) { %>
							<li><%= c.getTitle() %></li>
						<% } %>
					</ul></td>
					
					<td>
						<c:if test="${not empty s.demographic}">
							<%= s.getDemo().getTitle() %>
						</c:if>
					</td>
					
					<td><%= s.getBalance() %></td>
					
					<td><ul>
						<% for (Award a : s.getAwards()) { %>
							<li><%= a.getAwardTitle() %></li>
						<% } %>
					</ul></td>
					
				</tr>
			<% } %>
		</table>
		
		<div id='create-link'>
			<a class='enroll' href='/CreateCourse' target='content'>Create New <%= student %></a>
		</div>
		
	</body>
</html>