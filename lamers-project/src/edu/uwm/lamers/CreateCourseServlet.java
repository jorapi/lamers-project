package edu.uwm.lamers;
import java.io.IOException;
import java.util.*;
import java.sql.Time;
import java.text.*;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.uwm.lamers.entities.Admin;
import edu.uwm.lamers.entities.Course;
import edu.uwm.lamers.entities.Instructor;
import edu.uwm.lamers.entities.Student;



@SuppressWarnings("serial")
public class CreateCourseServlet extends HttpServlet
{
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException
	{
		printForm(resp);
	}
	
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException
	{
		String CourseName = req.getParameter("class_name");
		String InstructorName = req.getParameter("instructor");
		Instructor in = null;
		String Location = req.getParameter("location");
		String start = req.getParameter("start");
		String end = req.getParameter("end");
		Boolean[] days = new Boolean[7];
		if(req.getParameter("Monday") != null)
			days[1] = true;
		if(req.getParameter("Tuesday") != null)
			days[2] = true;
		if(req.getParameter("Wednesday") != null)
			days[3] = true;
		if(req.getParameter("Thursday") != null)
			days[4] = true;
		if(req.getParameter("Friday") != null)
			days[5] = true;
		if(req.getParameter("Saturday") != null)
			days[6] = true;
		if(req.getParameter("Sunday") != null)
			days[0] = true;
		
		PersistenceManager pm = getPersistenceManager();
		
		for (Instructor instructor : (List<Instructor>) pm.newQuery(Instructor.class).execute()) {		
			if(InstructorName.equals(instructor.getFirstName() + " " + instructor.getLastName()))
				in = instructor;
		} 
		
		Course course = new Course(CourseName, in, Location, start, end);
		
		course.setMeetingDays(days);
		
		try {
			pm.makePersistent(course);
		} finally {
			pm.close();
		}
	}
	
	private PersistenceManager getPersistenceManager()
	{
		return JDOHelper.getPersistenceManagerFactory("transactions-optional").getPersistenceManager();
	}
	
	private void printForm(HttpServletResponse resp) throws IOException{
		PersistenceManager pm = getPersistenceManager();

		resp.setContentType("text/html");

		resp.getWriter().println("<h2>Create Program</h2>");
		resp.getWriter().println("<form action='' method='post'>");
		resp.getWriter().println("<table cellpadding='5'>");
		resp.getWriter().println("<tr>");
		resp.getWriter().println("<td>Class name: </td>");
		resp.getWriter().println("<td><input type='text' name='class_name'></td>");
		resp.getWriter().println("</tr>");
			
		resp.getWriter().println("<tr>");
		resp.getWriter().println("<td>Instructor name: </td>");
		resp.getWriter().println("<td><select id='instructor' name='instructor'>");
		for (Instructor instructor : (List<Instructor>) pm.newQuery(Instructor.class).execute()) {
			resp.getWriter().println("<option value=' " + instructor.getKey().getId() + "'>" + instructor.getLastName() + "</option>");
	    }
		resp.getWriter().println("</select></td>");
		resp.getWriter().println("</tr>");
			
		resp.getWriter().println("<tr>");
		resp.getWriter().println("<td>Location: </td>");
		resp.getWriter().println("<td><input type='text' name='location'></td>");
		resp.getWriter().println("</tr>");
			
		resp.getWriter().println("<table id='times'>");
		resp.getWriter().println("<caption>Meeting Time</caption>");
		resp.getWriter().println("<tr>");
		resp.getWriter().println("<td><input type='checkbox' name='Monday' value='Monday'>Monday</td>");
		resp.getWriter().println("</tr>");
		resp.getWriter().println("<tr>");
		resp.getWriter().println("<td><input type='checkbox' name='Tuesday' value='Tuesday'>Tuesday </td>");
		resp.getWriter().println("</tr>");
		resp.getWriter().println("<tr>");
		resp.getWriter().println("<td><input type='checkbox' name='Wednesday' value='Wednesday'>Wednesday</td>");
		resp.getWriter().println("</tr>");
		resp.getWriter().println("<tr>");
		resp.getWriter().println("<td><input type='checkbox' name='Thursday' value='Thursday'>Thursday</td>");
		resp.getWriter().println("</tr>");
		resp.getWriter().println("<tr>");
		resp.getWriter().println("<td><input type='checkbox' name='Friday' value='Friday'>Friday</td>");
		resp.getWriter().println("</tr>");
		resp.getWriter().println("<tr>");
		resp.getWriter().println("<td><input type='checkbox' name='Saturday' value='Saturday'>Saturday</td>");
		resp.getWriter().println("</tr>");
		resp.getWriter().println("<tr>");
		resp.getWriter().println("<td><input type='checkbox' name='Sunday' value='Sunday'>Sunday</td>");
		resp.getWriter().println("</tr>");
		resp.getWriter().println("<tr>");
		resp.getWriter().println("<td>Start Time:</td>");
		resp.getWriter().println("<td><input type='time' name='start'></td>");
		resp.getWriter().println("</tr>");
		resp.getWriter().println("<tr>");
		resp.getWriter().println("<td>End Time:</td>");
		resp.getWriter().println("<td><input type='time' name='end'></td>");
		resp.getWriter().println("</tr>");
		resp.getWriter().println("</table>");
			
		resp.getWriter().println("</table>");
		resp.getWriter().println("<input type='submit' value='Create Class'>");
		resp.getWriter().println("</form>");
	}
}
