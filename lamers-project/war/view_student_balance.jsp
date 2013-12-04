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
	List<Student> students = (List<Student>) pm.newQuery(Student.class).execute();
	
	Student thisStudent = null;
	String studEmail = null;
	
	for (Cookie c : request.getCookies()){
		if (c.getName().equals("studentemail"))
			studEmail = c.getName();
	}
	
	for (Student s : students){
		if (s.getEmail().equals(studEmail))
			thisStudent = s;
	}
%>

<html>
	<head>
		<link rel='stylesheet' type='text/css' href='styles/viewstudent.css'>
	</head>
	<body>
		<table id='students'>
			<caption>Your Balance</caption>
			<tr>	
				<th>Amount Due</th>
				<th>Date Due</th>
				<th>Total outstanding balance</th>
			</tr>
			
			<tr>
				<td><%-- Min payment --%></td>
				<td><%-- Due date --%></td>
				<td>
					<% if (thisStudent != null) { %>
						<%= thisStudent.getBalance() %>
					<% } %>
				</td>
			</tr>
		</table>
		
		<div id='create-link'>
			<a class='enroll' href='/MakePayment' target='content'>Make Payment</a>
		</div>
		
	</body>
</html>