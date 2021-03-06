<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<%@ page import="java.util.List" %>

<%@ page import="javax.jdo.JDOHelper" %>
<%@ page import="javax.jdo.PersistenceManager" %>
<%@ page import="java.io.BufferedReader" %>
<%@ page import="java.io.FileReader" %>
<%@ page import="java.text.*" %>

<%@ page import="edu.uwm.lamers.entities.*" %>

<% BufferedReader reader = new BufferedReader(new FileReader("terms.txt")); %>
<% String instructor = reader.readLine(); %>
<% String student = reader.readLine(); %>
<% String course = reader.readLine(); %>

<%
	PersistenceManager pm = JDOHelper.getPersistenceManagerFactory("transactions-optional").getPersistenceManager();
	List<Course> courses = (List<Course>) pm.newQuery(Course.class).execute();
	DateFormat df = new SimpleDateFormat("mm/dd/yyyy");
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
				<th>Requirements</th>
				<th>Size</th>
				<th>Location</th>
				<th>Meeting days</th>
				<th>Meeting Time</th>
				<th>Start Date - End Date</th>
			</tr>
			
			<% for (Course c : courses) { %>
				<tr>
					<%
						Long courseId = c.getKey().getId();
					%>
					<td><div id='create-link'><a class='list' href='/ClassList?course=<%= courseId %>' target='content'><%= c.getTitle() %></a></div></td>
					<td><%= c.getInstructor().getName() %></td>
					<td><ul>
						<% for (Award s : c.getRequirements()) { %>
							<li><%= s.getAwardTitle() + " Lv." + s.getAwardLevel() %></li>
						<% } %>
					</ul></td>
					<td><%= c.size() %></td>
					<td><%= c.getLocation() %></td>
					<td><ul>
						<% for (String s : c.getDaysToMeet()) { %>
							<li><%= s %></li>
						<% } %>
					</ul></td>
					<td><%= c.getStartTime() %> - <%= c.getEndTime() %></td>
					<td><%= df.format(c.getStartDate()) %> - <%= df.format(c.getEndDate()) %></td>
				</tr>
			<% } %>
		</table>
		
		<div id='create-link'>
			<a class='enroll' href='/CreateCourse' target='content'>Create New <%= course %></a>
		</div>
		
	</body>
</html>