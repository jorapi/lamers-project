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
		String Location = req.getParameter("location");
		String mon_start = req.getParameter("mon_start");
		String mon_end = req.getParameter("mon_end");
		String tues_start = req.getParameter("tues_start");
		String tues_end = req.getParameter("tues_end");
		String wed_start = req.getParameter("wed_start");
		String wed_end = req.getParameter("wed_end");
		String thurs_start = req.getParameter("thurs_start");
		String thurs_end = req.getParameter("thurs_end");
		String fri_start = req.getParameter("fri_start");
		String fri_end = req.getParameter("fri_end");
		String sat_start = req.getParameter("sat_start");
		String sat_end = req.getParameter("sat_end");
		String sun_start = req.getParameter("sun_start");
		String sun_end = req.getParameter("sun_end");
		
		PersistenceManager pm = getPersistenceManager();
		
		//create course and make persistent
		
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
		for (Instructor instructor : (List<Instructor>) pm.newQuery(Instructor.class).execute()) {
			resp.getWriter().println("<option value='" + instructor.getKey().getId() + "'>" + instructor.getFirstName() + " " + instructor.getLastName() + "</option>");
		}
		resp.getWriter().println("</tr>");
			
		resp.getWriter().println("<tr>");
		resp.getWriter().println("<td>Location: </td>");
		resp.getWriter().println("<td><input type='text' name='location'></td>");
		resp.getWriter().println("</tr>");
			
		resp.getWriter().println("<table id='students'>");
		resp.getWriter().println("<caption>Meeting Time</caption>");
		resp.getWriter().println("<tr>");
		resp.getWriter().println("<th></th>");
		resp.getWriter().println("<th>Start Time</th>");
		resp.getWriter().println("<th>End Time</th>");
		resp.getWriter().println("</tr>");
		resp.getWriter().println("<tr>");
		resp.getWriter().println("<td>Monday:</td>");
		resp.getWriter().println("<td><input type='time' name='mon_start'></td>");
		resp.getWriter().println("<td><input type='time' name='mon_end'></td>");
		resp.getWriter().println("</tr>");
		resp.getWriter().println("<tr>");
		resp.getWriter().println("<td>Tuesday:</td>");
		resp.getWriter().println("<td><input type='time' name='tues_start'></td>");
		resp.getWriter().println("<td><input type='time' name='tues_end'></td>");
		resp.getWriter().println("</tr>");
		resp.getWriter().println("<tr>");
		resp.getWriter().println("<td>Wednesday:</td>");
		resp.getWriter().println("<td><input type='time' name='wed_start'></td>");
		resp.getWriter().println("<td><input type='time' name='wed_end'></td>");
		resp.getWriter().println("</tr>");
		resp.getWriter().println("<tr>");
		resp.getWriter().println("<td>Thursday:</td>");
		resp.getWriter().println("<td><input type='time' name='thurs_start'></td>");
		resp.getWriter().println("<td><input type='time' name='thurs_end'></td>");
		resp.getWriter().println("</tr>");
		resp.getWriter().println("<tr>");
		resp.getWriter().println("<td>Friday:</td>");
		resp.getWriter().println("<td><input type='time' name='fri_start'></td>");
		resp.getWriter().println("<td><input type='time' name='fri_end'></td>");
		resp.getWriter().println("</tr>");
		resp.getWriter().println("<tr>");
		resp.getWriter().println("<td>Saturday:</td>");
		resp.getWriter().println("<td><input type='time' name='sat_start'></td>");
		resp.getWriter().println("<td><input type='time' name='sat_end'></td>");
		resp.getWriter().println("</tr>");
		resp.getWriter().println("<tr>");
		resp.getWriter().println("<td>Sunday:</td>");
		resp.getWriter().println("<td><input type='time' name='sun_start'></td>");
		resp.getWriter().println("<td><input type='time' name='sun_end'></td>");
		resp.getWriter().println("</tr>");
		resp.getWriter().println("</table>");
			
		resp.getWriter().println("</table>");
		resp.getWriter().println("</br><input type='submit' value='Add Students'>");
		resp.getWriter().println("<input type='submit' value='Create Class'>");
		resp.getWriter().println("</form>");
	}
}
