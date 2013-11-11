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
import edu.uwm.lamers.entities.Instructor;
import edu.uwm.lamers.entities.Student;

public class ViewInstructorsServlet extends HttpServlet {
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		
		PersistenceManager pm = getPersistenceManager();
		
		resp.setContentType("text/html");
		
		resp.getWriter().println("<head>");
		resp.getWriter().println("<link rel='stylesheet' type='text/css' href='styles/viewstudent.css'>");
		resp.getWriter().println("</head>");
		
		resp.getWriter().println("<body>");
		
		resp.getWriter().println("<table id='instructors'>");
		resp.getWriter().println("<caption>Instructors</caption>");
		resp.getWriter().println("<tr>");
		resp.getWriter().println("<th>First Name</th>");
		resp.getWriter().println("<th>Last Name</th>");
		resp.getWriter().println("<th>Email</th>");
		resp.getWriter().println("<th>Classes Teaching</th>");
		resp.getWriter().println("</tr>");
		
		for (Instructor in : (List<Instructor>) pm.newQuery(Instructor.class).execute()) {
			resp.getWriter().println("<tr>");
			
			resp.getWriter().println("<td>" + in.getFirstName() + "</td>");
			resp.getWriter().println("<td>" + in.getLastName() + "</td>");
			resp.getWriter().println("<td>" + in.getEmail() + "</td>");
			
			resp.getWriter().println("<td>");
			resp.getWriter().println("<ul>");
			for(Course c : in.getCourses()){
				resp.getWriter().println("<li>" + c.getTitle() + "</li>");
			}
			resp.getWriter().println("</ul>");
			resp.getWriter().println("</td>");
			
			resp.getWriter().println("</tr>");
	    }
		
		resp.getWriter().println("</table>");
		
		resp.getWriter().println("<div id='enroll-link'>");
		resp.getWriter().println("<a class='enroll' href='/CreateInstructor' target='content'>Create New Instructor</a>");
		resp.getWriter().println("</div>");
		
		resp.getWriter().println("</body>");

	}
	
	private PersistenceManager getPersistenceManager() {
		return JDOHelper.getPersistenceManagerFactory("transactions-optional").getPersistenceManager();
	}
}
