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
	List<Instructor> instructors = (List<Instructor>) pm.newQuery(Instructor.class).execute();
%>

<html>
	<body>
		<% if (request.getParameter("POST") != null) { %>
			<h2><%= course %> Created successfully!</h2>
		<% } %>
	
		<h2>Create <%= course %></h2>
		<form action='/CreateCourse' method='post'>
			<table cellpadding='5'>
				<tr>
					<td><%= course %> name: </td>
					<td><input type='text' name='class_name'></td>
				</tr>
				
				<tr>
					<td><%= instructor %>: </td>
					<td><select id='instructor' name='instructor'>
						<% for (Instructor i : (List<Instructor>) pm.newQuery(Instructor.class).execute()) { %>
							<option value='<%= i.getKey().getId() %>'><%= i.getName() %></option>
						<% } %>
					</select></td>
				</tr>
				
				<tr>
					<td>Location: </td>
					<td><input type='text' name='location'></td>
				</tr>
				
				<tr><table id='times'>
					<caption>Meeting Time</caption>
						<tr>
							<td><input type='checkbox' name='Monday' value='Monday'>Monday</td>
						</tr>
						<tr>
							<td><input type='checkbox' name='Tuesday' value='Tuesday'>Tuesday </td>
						</tr>
						<tr>
							<td><input type='checkbox' name='Wednesday' value='Wednesday'>Wednesday</td>
						</tr>
						<tr>
							<td><input type='checkbox' name='Thursday' value='Thursday'>Thursday</td>
						</tr>
						<tr>
							<td><input type='checkbox' name='Friday' value='Friday'>Friday</td>
						</tr>
						<tr>
							<td><input type='checkbox' name='Saturday' value='Saturday'>Saturday</td>
						</tr>
						<tr>
							<td><input type='checkbox' name='Sunday' value='Sunday'>Sunday</td>
						</tr>
						<tr>
							<td>Start Time:</td>
							<td><input type='time' name='start'></td>
						</tr>
						<tr>
							<td>End Time:</td>
							<td><input type='time' name='end'></td>
						</tr>
						<tr>
							<td>Length (number of weeks to meet):</td>
							<td><input type='number' name='weeks'></td>
						</tr>
						</table></tr>

				<tr>
					<td>Cost: </td>
					<td><input type='number' name='cost'></td>
				</tr>
	
			</table>

			<br><input type='submit' value='Create <%= course %>'>
		</form>
	</body>
</html>