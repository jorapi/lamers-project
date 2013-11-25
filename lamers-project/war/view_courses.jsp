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
	List<Course> courses = (List<Course>) pm.newQuery(Course.class).execute();
%>

<html>
	<head>
		<link rel='stylesheet' type='text/css' href='styles/viewstudent.css'>
	</head>
	<body>
		<table id='students'>
			<caption><%= course %> List</caption>
			<tr>	
				<th>Course Title</th>
				<th>Instructor Name</th>
				<th>Size</th>
				<th>Location</th>
				<th>Meeting days</th>
				<th>Meeting Time</th>
			</tr>
			
			<% for (Course c : courses) { %>
				<tr>
					<td><%= c.getTitle() %></td>
					<td><%= c.getInstructor().getName() %></td>
					<td><%= c.size() %></td>
					<td><%= c.getLocation() %></td>
					<td><ul>
						<% for (String s : c.getMeetingDays()) { %>
							<li><%= s %></li>
						<% } %>
					</ul></td>
					<td><%= c.getStartTime() %> - <%= c.getEndTime() %></td>
				</tr>
			<% } %>
		</table>
	</body>
</html>