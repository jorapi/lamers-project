<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<%@ page import="java.util.List" %>

<%@ page import="javax.jdo.JDOHelper" %>
<%@ page import="javax.jdo.PersistenceManager" %>

<%@ page import="edu.uwm.lamers.entities.*" %>

<html>
	<head>
		<link rel='stylesheet' type='text/css' href='styles/viewstudent.css'>
	</head>
	<body>
		<table id='students'>
		<h1>${course.courseTitle}</h1>
		
		<tr>
			<th>First Name</th>
			<th>Last Name</th>
			<th>Email</th>
			<th>Classes Enrolled</th>
			<th>Demographic</th>
			<th>Balance Due</th>
			<th>Awards</th>
		</tr>
		
		<c:forEach items="${students}" var="student">
      		<tr>
      			<td>${student.firstName}</td>
      			<td>${student.lastName}</td>
      			<td>${student.email}</td>
      			<td>
      				<ul>
      					<c:forEach items="${student.coursesEnrolled}" car="stucourse">
      						<li>${stucourse.courseTitle}</li>
      					</c:forEach>
      				</ul>
      			</td>
      		</tr>
    	</c:forEach>
	</body>
</html>
