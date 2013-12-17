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
	List<Course> courses = (List<Course>) pm.newQuery(Course.class).execute();
%>

<html>
	<body>
		<% if (request.getParameter("POST") != null) { %>
			<% if (request.getParameter("POST").equals("success")) { %>
				<h2><%= student %> enrolled successfully!</h2>
			<% } %>
			<% if (request.getParameter("POST").equals("failure")) { %>
				<h2><%= student %> not enrolled (did not meet requirements)</h2>
			<% } %>
		<% } %>
	
		<h2>Enroll <%= student %> </h2>
		<form action='/EnrollStudent' method='post'>
			<table cellpadding='5'>
				<tr>
					<td><%= student %>: </td>
					<td><select name='student'>
						<% for (Student s : students) { %>
							<option value='<%= s.getKey().getId() %>'><%= s.getName() %></option>
						<% } %>
					</select></td>
				</tr>
				
				<tr>
					<td><%= course %>(s): </td>
					<td><select multiple name='classes'>
						<% for (Course c : courses) { %>
							<option value='<%= c.getKey().getId() %>'><%= c.getTitle() %></option>
						<% } %>
					</select></td>
				</tr>
				
				<tr>
					<td><p>Payment Plan:<p></td>
					<td><input type="radio" name="plan" value="1"> Pay in Full<br></td>
					<td><input type="radio" name="plan" value="12"> Monthly<br></td>
					<td><input type="radio" name="plan" value="52"> Weekly<br></td>
					<td><input type="radio" name="plan" value="100"> Pay Per Session<br></td>
				
				</tr>
			</table>
			<input type='submit' value='Submit'>
		</form>
	</body>
</html>
		