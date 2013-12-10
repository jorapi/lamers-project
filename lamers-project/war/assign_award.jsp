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
	List<Award> awards = (List<Award>) pm.newQuery(Award.class).execute();
%>

<html>
	<body>
		<% if (request.getParameter("POST") != null) { %>
			<h2>Award assigned successfully!</h2>
		<% } %>
	
		<h2>Assign Award </h2>
		<form action='/AssignAward' method='post'>
			<table cellpadding='5'>
				<tr>
					<td>Award: </td>
					<td><select name='award'>
						<% for (Award a : awards) { %>
							<option value='<%= a.getKey().getId() %>'><%= a.getAwardTitle() + " LV." + a.getAwardLevel() %></option>
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
		