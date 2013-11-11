package edu.uwm.lamers;

import java.io.IOException;
import java.util.List;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.uwm.lamers.entities.Award;
import edu.uwm.lamers.entities.Course;
import edu.uwm.lamers.entities.Demographic;
import edu.uwm.lamers.entities.Student;

public class ViewCoursesServlet extends HttpServlet {
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		
		PersistenceManager pm = getPersistenceManager();
		
		resp.setContentType("text/html");
		
		resp.getWriter().println("<head>");
		resp.getWriter().println("<link rel='stylesheet' type='text/css' href='styles/viewstudent.css'>");
		resp.getWriter().println("</head>");
		
		resp.getWriter().println("<body>");
		
		resp.getWriter().println("<table id='students'>");
		resp.getWriter().println("<caption>Courses</caption>");
		resp.getWriter().println("<tr>");
		resp.getWriter().println("<th>Course Title</th>");
		resp.getWriter().println("<th>Instructor Name</th>");
		resp.getWriter().println("<th>Size</th>");
		resp.getWriter().println("<th>Location</th>");
		resp.getWriter().println("<th>Meeting days</th>");
		resp.getWriter().println("<th>Meeting Time</th>");
		resp.getWriter().println("</tr>");
		
		for (Course c : (List<Course>) pm.newQuery(Course.class).execute()) {
			resp.getWriter().println("<tr>");
			
			resp.getWriter().println("<td>" + c.getTitle() + "</td>");
			resp.getWriter().println("<td>" + c.getInstructor() + "</td>");
			resp.getWriter().println("<td>" + c.size() + "</td>");
			resp.getWriter().println("<td>" + c.getLocation() + "</td>");
			
			resp.getWriter().println("<td>");
			resp.getWriter().println("<ul>");
			Boolean[] b = c.getDaysToMeet();
			if (b != null){
				if(b[0] != null && b[0] == true){ resp.getWriter().println("<li>Sunday<li>");}
				if(b[1] != null && b[1] == true){ resp.getWriter().println("<li>Monday<li>");}
				if(b[2] != null && b[2] == true){ resp.getWriter().println("<li>Tuesday<li>");}
				if(b[3] != null && b[3] == true){ resp.getWriter().println("<li>Wednesday<li>");}
				if(b[4] != null && b[4] == true){ resp.getWriter().println("<li>Thursday<li>");}
				if(b[5] != null && b[5] == true){ resp.getWriter().println("<li>Friday<li>");}
				if(b[6] != null && b[6] == true){ resp.getWriter().println("<li>Saturday<li>");}
			}
			resp.getWriter().println("</ul>");
			resp.getWriter().println("</td>");
			
			resp.getWriter().println("<td>" + c.getStartTime() + " - " + c.getEndTime() + "</td>");
			
			resp.getWriter().println("</tr>");
	    }
		
		resp.getWriter().println("</table>");
		
		resp.getWriter().println("<div id='create-link'>");
		resp.getWriter().println("<a class='enroll' href='/CreateCourse' target='content'>Create New Course</a>");
		resp.getWriter().println("</div>");
		
		resp.getWriter().println("</body>");

	}
	
	private PersistenceManager getPersistenceManager() {
		return JDOHelper.getPersistenceManagerFactory("transactions-optional").getPersistenceManager();
	}
}
