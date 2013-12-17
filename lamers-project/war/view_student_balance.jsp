<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<%@ page import="java.util.List" %>
<%@ page import="java.lang.Math" %>
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
	double totalDue=0;
	
	for (Cookie c : request.getCookies()){
		if (c.getName().equals("email"))
			studEmail = c.getValue();
	}
	
	for (Student s : students){
		if (s.getEmail() != null){
			if (s.getEmail().equals(studEmail))
				thisStudent = s;
		}
	}
%>

<html>
	<head>
		<link rel='stylesheet' type='text/css' href='styles/viewstudent.css'>
	</head>
	<body>
		<table id='students'>
			<caption>Account Summary</caption>
			<tr>
				<th>Class ID</th>	
				<th>Amount Due This Period</th>
				<th>Date Due</th>
				<th>Outstanding balance</th>
			</tr>
			
			<% for(PaymentPlan p: thisStudent.getPaymentPlans()){ %>
			<%= totalDue+=Math.ceil(p.getAmountDue()) %></td>
			<tr>
				<td><%= p.getCourseID() %></td>
				<td><%= Math.ceil(p.getAmountDue()) %></td>
				<td><%= p.getDueDate() %></td>
				<td><%= Math.ceil(p.getAmount()) %></td>
					
				</td>
			</tr>
			<%}%>
			<tr>
				<th> Total Money Owed </th>
				<th> Total Money Owed this Period </th>
			</tr>
			
				<td><%= Math.ceil(thisStudent.getBalance())%></td>
				<td><%= totalDue%></td>
				
		</table>
		
		<div id='create-link'>
			<a class='enroll' href='/MakePayment' target='content'>Make Payment</a>
		</div>
		
	</body>
</html>