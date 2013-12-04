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


<% 	String courseId = request.getParameter("course"); 
	PersistenceManager pm = JDOHelper.getPersistenceManagerFactory("transactions-optional").getPersistenceManager();
	
	List<Course> courses = (List<Course>) pm.newQuery(Course.class).execute();
	
	Course crse = null;
	
	for (Course c : courses) {
		if (c.getKey().getId() == Long.parseLong(courseId)){
			crse = c;
		}
	}
%>

<html>
	<head>
		<link rel='stylesheet' type='text/css' href='styles/viewstudent.css'>
	</head>
	<body>
		<% if (request.getParameter("type") != null) { %>
			<% if (request.getParameter("type").equals("REMFAIL")) { %>
				<h2><%= student %> removal failed!</h2>
			<% } %>
			<% if (request.getParameter("type").equals("REMSUCCESS")) { %>
				<h2><%= student %> removed successfully!</h2>
			<% } %>
		<% } %>
	
		<% if (crse != null) { %>
		<table id='students'>
		<h1><%= crse.getTitle() %></h1>
		<h3><%= instructor %>: <%= crse.getInstructor().getName()%></h3>
		
		<tr>
			<th>Name</th>
			<th>Email</th>
			<th>Classes Enrolled</th>
			<th>Demographic</th>
			<th>Balance Due</th>
			<th>Awards</th>
			<th> </th>
		</tr>
		
		<% for (Student s : crse.getClasslist()) { %>
			<tr>
				<td><%= s.getName() %></td>
				<td><%= s.getEmail() %></td>
				
				<td><ul>
				<% for (Course c1 : s.getCourses()) { %>
					<li><%= c1.getTitle() %></li>
				<% } %>
				</ul></td>
				
				<td>
				<% if (s.getDemo() != null) { %>
					s.getDemo().getTitle();
				<% } %>
				</td>
				
				<td><%= s.getBalance() %></td>
				
				<td><ul>
					<% for (Award a : s.getAwards()) { %>
						<li><%= a.getAwardTitle() %></li>
					<% } %>
				</ul></td>
				
				<td>
					<form action = '/ClassList?type=REMOVE&course=<%= crse.getKey().getId() %>&student=<%= s.getKey().getId() %>' method = 'post'>
        				<input type='submit' value='Remove' />
   					</form>
				</td>
			</tr>
		<% } %>
		
		</table>
		
		<% } %>
	</body>
</html>
