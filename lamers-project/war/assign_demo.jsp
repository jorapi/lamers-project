<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page import="java.io.BufferedReader" %>
<%@ page import="java.io.FileReader" %>
<%@ page import="java.util.List" %>

<%@ page import="javax.jdo.JDOHelper" %>
<%@ page import="javax.jdo.PersistenceManager" %>

<%@ page import="edu.uwm.lamers.entities.*" %>

<% BufferedReader reader = new BufferedReader(new FileReader("terms.txt")); %>
<% String instructor = reader.readLine(); %>
<% String student = reader.readLine(); %>
<% String course = reader.readLine(); %>

<%
	PersistenceManager pm = JDOHelper.getPersistenceManagerFactory("transactions-optional").getPersistenceManager();
	List<Student> students = (List<Student>) pm.newQuery(Student.class).execute();
	List<Demographic> demos = (List<Demographic>) pm.newQuery(Demographic.class).execute();
%>

<html>
	<body>
		<% if (request.getParameter("POST") != null) { %>
			<h2>Demographic assigned successfully!</h2>
		<% } %>
	
		<h2>Enroll <%= student %> </h2>
		<form action='/AssignDemo' method='post'>
			<table cellpadding='5'>
				<tr>
					<td>Demographic: </td>
					<td><select name='demo'>
						<% for (Demographic d : demos) { %>
							<option value='<%= d.getKey().getId() %>'><%= d.getTitle() %></option>
						<% } %>
					</select></td>
				</tr>
				
				<tr>
					<td><%= student %>(s): </td>
					<td><select multiple name='students'>
						<% for (Student s : students) { %>
							<option value='<%= s.getKey().getId() %>'><%= s.getName() %></option>
						<% } %>
					</select></td>
				</tr>
			</table>
			<input type='submit' value='Submit'>
		</form>
	</body>
</html>
		