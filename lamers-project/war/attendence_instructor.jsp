<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<%@ page import="java.util.*" %>
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
	List<Instructor> ins = (List<Instructor>) pm.newQuery(Instructor.class).execute();
	
	Instructor thisIn = null;
	String inEmail = null;
	
	for (Cookie c : request.getCookies()){
		if (c.getName().equals("email"))
			inEmail = c.getValue();
			
	}
	
	for (Instructor i : ins){
		System.out.println(inEmail);
		if (i.getEmail().equals(inEmail)){
			thisIn = i;
			System.out.println(i.getEmail());
		}	
	}

%>

<html>
	<head>
		<link rel='stylesheet' type='text/css' href='styles/viewstudent.css'>
	</head>
	<body>
		<form action='/Attendance' method='post'>
		<% for (Course cs : thisIn.getCourses()) { %>
		
			<% Boolean[] days = cs.getDaysToMeet(); 
			   Set<Student> studentList = cs.getClasslist();
			%>
				
			<% for (Student st : studentList) { %>
				
				<h3><%= st.getName() %></h3>
				
				<% HashMap<Integer, Integer> missedMap = st.getDaysForCourse(cs); %>
			
				<table id='students'>
					<caption><%= cs.getTitle() %></caption>
			
					<tr>	
						<th></th>
						<% for (int i = 0; i < cs.getNumOfWeeks(); i++) { %>
							<th>Week <%= (i + 1) %></th>
						<% } %>
					</tr>
				
						<% if (days[0]) { %>
							<tr>
							<th>Sunday</th>
							<% for(int i = 0; i < cs.getNumOfWeeks(); i++) { %>
								<td>
									<input type="checkbox" 
									name="<%= st.getKey().getId() %>" 
									value="<%= i %>-0" 
									<% if (missedMap != null && missedMap.get(i) != null && missedMap.get(i) == 0) { %>
										checked
									<% } %>
									>Missed<br>
								</td>
							<% } %>
							</tr>
						<% } %>
						
						<% if (days[1]) { %>
							<tr>
							<th>Monday</th>
							<% for(int i = 0; i < cs.getNumOfWeeks(); i++) { %>
								<td>
									<input type="checkbox" 
									name="<%= st.getKey().getId() %>" 
									value="<%= i %>-1" 
									<% if (missedMap != null && missedMap.get(i) != null && missedMap.get(i) == 1) { %>
										checked
									<% } %>
									>Missed<br>
								</td>
							<% } %>
							</tr>
						<% } %>	
						
						<% if (days[2]) { %>
							<tr>
							<th>Tuesday</th>
							<% for(int i = 0; i < cs.getNumOfWeeks(); i++) { %>
								<td>
									<input type="checkbox" 
									name="<%= st.getKey().getId() %>" 
									value="<%= i %>-2" 
									<% if (missedMap != null && missedMap.get(i) != null && missedMap.get(i) == 2) { %>
										checked
									<% } %>
									>Missed<br>
								</td>
							<% } %>
							</tr>
						<% } %>	
						
						<% if (days[3]) { %>
							<tr>
							<th>Wednesday</th>
							<% for(int i = 0; i < cs.getNumOfWeeks(); i++) { %>
								<td>
									<input type="checkbox" 
									name="<%= st.getKey().getId() %>" 
									value="<%= i %>-3" 
									<% if (missedMap != null && missedMap.get(i) != null && missedMap.get(i) == 3) { %>
										checked
									<% } %>
									>Missed<br>
								</td>
							<% } %>
							</tr>
						<% } %>	
						
						<% if (days[4]) { %>
							<tr>
							<th>Thursday</th>
							<% for(int i = 0; i < cs.getNumOfWeeks(); i++) { %>
								<td>
									<input type="checkbox" 
									name="<%= st.getKey().getId() %>" 
									value="<%= i %>-4" 
									<% if (missedMap != null && missedMap.get(i) != null && missedMap.get(i) == 4) { %>
										checked
									<% } %>
									>Missed<br>
								</td>
							<% } %>
							</tr>
						<% } %>	
						
						<% if (days[5]) { %>
							<tr>
							<th>Friday</th>
							<% for(int i = 0; i < cs.getNumOfWeeks(); i++) { %>
								<td>
									<input type="checkbox" 
									name="<%= st.getKey().getId() %>" 
									value="<%= i %>-5" 
									<% if (missedMap != null && missedMap.get(i) != null && missedMap.get(i) == 5) { %>
										checked
									<% } %>
									>Missed<br>
								</td>
							<% } %>
							</tr>
						<% } %>	
						
						<% if (days[6]) { %>
							<tr>
							<th>Saturday</th>
							<% for(int i = 0; i < cs.getNumOfWeeks(); i++) { %>
								<td>
									<input type="checkbox" 
									name="<%= st.getKey().getId() %>" 
									value="<%= i %>-6" 
									<% if (missedMap != null && missedMap.get(i) != null && missedMap.get(i) == 6) { %>
										checked
									<% } %>
									>Missed<br>
								</td>
							<% } %>
							</tr>
						<% } %>		
				</table>
			<% } %>
		<% } %>
		<input type='submit' value='Update Attendance'>
	</form>
	</body>
</html>