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
	if(request.getParameter("course") == null) System.out.println("NULL");
	String courseId = request.getParameter("course"); 
	PersistenceManager pm = JDOHelper.getPersistenceManagerFactory("transactions-optional").getPersistenceManager();
	
	List<Course> courses = (List<Course>) pm.newQuery(Course.class).execute();
	
	Course crse = null;
	
	for (Course c : courses) {
		if (c.getKey().getId() == Long.parseLong(courseId)){
			crse = c;
		}
	}
%>

<html>
  <body>
	
	<h2>Edit <%= course %></h2>
	<form action='/EditCourse?course=<%= courseId %>' method='post'>
	  <table cellpadding='5'>
		<tr>
		  <td><%= course %> name: </td>
		  <td><input type='text' name='class_name' value='<%= crse.getTitle() %>'></td>
	    </tr>		
		<tr>
		  <td><%= instructor %>: </td>
		  <td><select id='instructor' name='instructor'>
			<% for (Instructor i : (List<Instructor>) pm.newQuery(Instructor.class).execute()) { %>
			    <option value='<%= i.getKey().getId() %>' <% if (i.getKey().getId() == crse.getInstructor().getKey().getId()) { %> selected="selected" <% } %>><%= i.getName() %></option>
			<% } %>
		  </select></td>
		  </tr>		
		  <tr>
			<td>Location: </td>
			<td><input type='text' name='location' value='<%= crse.getLocation() %>'></td>
		  </tr>
				
		  <tr><table id='times'>
		  	<% Boolean days[] = crse.getDaysToMeet(); %>
		    <caption>Meeting Time</caption>
			<tr>
			  <td><input type='checkbox' name='Monday' value='Monday' <% if (days[1]) { %>checked<% } %>>Monday</td>
			</tr>
			<tr>
			  <td><input type='checkbox' name='Tuesday' value='Tuesday' <% if (days[2]) { %>checked<% } %>>Tuesday </td>
			</tr>
			<tr>
			  <td><input type='checkbox' name='Wednesday' value='Wednesday' <% if (days[3]) { %>checked<% } %>>Wednesday</td>
			</tr>
			<tr>
			  <td><input type='checkbox' name='Thursday' value='Thursday' <% if (days[4]) { %>checked<% } %>>Thursday</td>
			</tr>
			<tr>
			  <td><input type='checkbox' name='Friday' value='Friday' <% if (days[5]) { %>checked<% } %>>Friday</td>
			</tr>
			<tr>
			  <td><input type='checkbox' name='Saturday' value='Saturday' <% if (days[6]) { %>checked<% } %>>Saturday</td>
			</tr>
			<tr>
			  <td><input type='checkbox' name='Sunday' value='Sunday' <% if (days[0]) { %>checked<% } %>>Sunday</td>
			</tr>
			<tr>
			  <td>Start Time:</td>
			  <td><input type='time' name='start' value='<%= crse.getStartTime() %>'></td>
			</tr>
			<tr>
			  <td>End Time:</td>
			  <td><input type='time' name='end' value='<%= crse.getEndTime() %>'></td>
			</tr>
			<tr>
			  <td>Length (number of weeks to meet):</td>
			  <td><input type='number' name='weeks' value='<%= crse.getNumOfWeeks() %>'></td>
			</tr>
		  </table></tr>
	  </table>

	  <div id="payment_options">
	  	 <h3>Payment Options</h3>
	  	 <label>Standard Cost: </label>
	  	 <input type="number" name="standard_cost" value="<%= crse.getStandardCost() %>"><br>
	  	 <label>Family Plan Cost: </label>
	  	 <input type="number" name="family_plan_cost" value="<%= crse.getFamilyPlanCost() %>"><br>
	  	 <label>Billing Cycle: </label>
	  	 <select>
	  	   <option type="text" value="flat_rate">Flat Rate</option>
	  	   <option type="text" value="montly">Monthly</option>
	  	   <option type="text" value="weekly">Weekly</option>
	  	   <option type="text" value="per_session">Per Session</option>
	  	 </select>
	  </div>

	  <br><input type='submit' value='Submit'>
    </form>
  </body>
</html>