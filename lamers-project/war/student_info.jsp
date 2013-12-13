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


<% 	String studentId = request.getParameter("student"); 
	PersistenceManager pm = JDOHelper.getPersistenceManagerFactory("transactions-optional").getPersistenceManager();
	
	List<Student> students = (List<Student>) pm.newQuery(Student.class).execute();
	
	Student st = null;
	
	for (Student s : students) {
		if (s.getKey().getId() == Long.parseLong(studentId)){
			st = s;
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
				<h2>award removal failed!</h2>
			<% } %>
			<% if (request.getParameter("type").equals("REMSUCCESS")) { %>
				<h2>award removed successfully!</h2>
			<% } %>
		<% } %>
	
		<% if (st != null) { %>
		<h1><%= st.getName() %></h1>
		<h3>Email: <%= st.getEmail() %> </h3>
		<table id='students'>
			<caption>awards</caption>
			<tr>
				<th>Award   </th>
				<th>Level  </th>
				<th> </th>
			</tr>
		
			<% for (Award s : st.getAwards()) { %>
				<tr>
					<td><%= s.getAwardTitle() %></td>
					<td><%= s.getAwardLevel() %></td>
				
					<td>
						<form action = '/StudentInfo?type=REMOVE&student=<%= st.getKey().getId() %>&award=<%= s.getKey().getId() %>' method = 'post'>
        					<input type='submit' value='Remove' />
   						</form>
					</td>
				</tr>
			<% } %>
		
		</table>
		<br></br>
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
			
			<% for (Course c : st.getCourses()) { %>
				<tr>
					<%
						Long courseId = c.getKey().getId();
					%>
					<td><div id='create-link'><a class='list' href='/ClassList?course=<%= courseId %>' target='content'><%= c.getTitle() %></a></div></td>
					<td><%= c.getInstructor().getName() %></td>
					<td><%= c.size() %></td>
					<td><%= c.getLocation() %></td>
					<td><ul>
						<% for (String s : c.getDaysToMeet()) { %>
							<li><%= s %></li>
						<% } %>
					</ul></td>
					<td><%= c.getStartTime() %> - <%= c.getEndTime() %></td>
				</tr>
			<% } %>
		</table>
		<br></br>
		<h1>Balance due: <%= st.getBalance() %><h1>
		<% } %>
		</br>
		
	</body>
</html>
