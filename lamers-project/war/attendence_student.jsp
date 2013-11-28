<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<%@ page import="java.util.List" %>
<%@ page import="java.util.HashMap" %>

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
	
	Student thisStudent = null;
	String studEmail = null;
	
	for (Cookie c : request.getCookies()){
		if (c.getName().equals("email"))
			studEmail = c.getValue();
			
	}
	
	for (Student s : students){
		System.out.println(studEmail);
		if (s.getEmail().equals(studEmail))
			thisStudent = s;
			
	}

%>

<html>
	<head>
		<link rel='stylesheet' type='text/css' href='styles/viewstudent.css'>
	</head>
	<body>
	
		<% for (Course cs : thisStudent.getCourses()) { %>
			<table id='students'>
				<caption><%= cs.getTitle() %></caption>
			
				<tr>	
					<th></th>
					<% for (int i = 0; i < cs.getNumOfWeeks(); i++) { %>
						<th>Week <%= (i + 1) %></th>
					<% } %>
				</tr>
				
				<% Boolean[] days = cs.getDaysToMeet(); 
				   HashMap<Integer, Integer> missedMap = thisStudent.getDaysForCourse(cs);
				%>
				
				<% if (days[0]) { %>
					<tr>
					<th>Sunday</th>
					<% for(int i = 0; i < cs.getNumOfWeeks(); i++) { %>
						<td>
						<% if(missedMap != null && missedMap.get(0) == i) { %>
							Missed
						<% } else { %>
							-
						<% } %>
						</td>
					<% } %>
					</tr>
				<% } %>
				
				<% if (days[1]) { %>
					<tr>
					<th>Monday</th>
					<% for(int i = 0; i < cs.getNumOfWeeks(); i++) { %>
						<td>
						<% if(missedMap != null && missedMap.get(1) == i) { %>
							Missed
						<% } else { %>
							-
						<% } %>
						</td>
					<% } %>
					</tr>
				<% } %>
				
				<% if (days[2]) { %>
					<tr>
					<th>Tuesday</th>
					<% for(int i = 0; i < cs.getNumOfWeeks(); i++) { %>
						<td>
						<% if(missedMap != null && missedMap.get(2) == i) { %>
							Missed
						<% } else { %>
							-
						<% } %>
						</td>
					<% } %>
					</tr>
				<% } %>
				
				<% if (days[3]) { %>
					<tr>
					<th>Wednesday</th>
					<% for(int i = 0; i < cs.getNumOfWeeks(); i++) { %>
						<td>
						<% if(missedMap != null && missedMap.get(3) == i) { %>
							Missed
						<% } else { %>
							-
						<% } %>
						</td>
					<% } %>
					</tr>
				<% } %>
				
				<% if (days[4]) { %>
					<tr>
					<th>Thursday</th>
					<% for(int i = 0; i < cs.getNumOfWeeks(); i++) { %>
						<td>
						<% if(missedMap != null && missedMap.get(4) == i) { %>
							Missed
						<% } else { %>
							-
						<% } %>
						</td>
					<% } %>
					</tr>
				<% } %>
				
				<% if (days[5]) { %>
					<tr>
					<th>Friday</th>
					<% for(int i = 0; i < cs.getNumOfWeeks(); i++) { %>
						<td>
						<% if(missedMap != null && missedMap.get(5) == i) { %>
							Missed
						<% } else { %>
							-
						<% } %>
						</td>
					<% } %>
					</tr>
				<% } %>
				
				<% if (days[6]) { %>
					<tr>
					<th>Saturday</th>
					<% for(int i = 0; i < cs.getNumOfWeeks(); i++) { %>
						<td>
						<% if(missedMap != null && missedMap.get(6) == i) { %>
							Missed
						<% } else { %>
							-
						<% } %>
						</td>
					<% } %>
					</tr>
				<% } %>
				
			</table>
		<% } %>
		
	</body>
</html>