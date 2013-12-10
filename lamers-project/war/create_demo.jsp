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
%>

<html>
  <body>
    <% if (request.getParameter("POST") != null) { %>
	  <h2>Demographic Created successfully!</h2>
	<% } %>
	
	<h2>Create <%= course %></h2>
	<form action='/CreateDemo' method='post'>
	  <table cellpadding='5'>
		<tr>
		  <td>Demographic name: </td>
		  <td><input type='text' name='demo_name'></td>
	    </tr>		
	  </table>
	  <br><input type='submit' value='Create Demographic'>
    </form>
  </body>
</html>