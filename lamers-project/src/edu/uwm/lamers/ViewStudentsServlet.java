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

public class ViewStudentsServlet extends HttpServlet {
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		
		PersistenceManager pm = getPersistenceManager();
		
		resp.setContentType("text/html");
		
		resp.getWriter().println("<head>");
		resp.getWriter().println("<link rel='stylesheet' type='text/css' href='styles/viewstudent.css'>");
		resp.getWriter().println("</head>");
		
		resp.getWriter().println("<body>");
		
		resp.getWriter().println("<table id='students'>");
		resp.getWriter().println("<caption>Enrolled Students</caption>");
		resp.getWriter().println("<tr>");
		resp.getWriter().println("<th>First Name</th>");
		resp.getWriter().println("<th>Last Name</th>");
		resp.getWriter().println("<th>Email</th>");
		resp.getWriter().println("<th>Classes Enrolled</th>");
		resp.getWriter().println("<th>Demographic</th>");
		resp.getWriter().println("<th>Awards</th>");
		resp.getWriter().println("</tr>");
		
		for (Student s : (List<Student>) pm.newQuery(Student.class).execute()) {
			resp.getWriter().println("<tr>");
			
			resp.getWriter().println("<td>" + s.getFirstName() + "</td>");
			resp.getWriter().println("<td>" + s.getLastName() + "</td>");
			resp.getWriter().println("<td>" + s.getEmail() + "</td>");
			
			resp.getWriter().println("<td>");
			resp.getWriter().println("<ul>");
			for(Course c : s.getCourses()){
				resp.getWriter().println("<li>" + c.getTitle() + "<li>");
			}
			resp.getWriter().println("</ul>");
			resp.getWriter().println("</td>");
			
			if(s.getDemo() != null){
				resp.getWriter().println("<td>" + s.getDemo().getTitle() + "</td>");
			} else {
				resp.getWriter().println("<td></td>");
			}
			
			
			resp.getWriter().println("<td>");
			resp.getWriter().println("<ul>");
			for(Award a : s.getAwards()){
				resp.getWriter().println("<li>" + a.getAwardTitle() + "<li>");
			}
			resp.getWriter().println("</ul>");
			resp.getWriter().println("</td>");
			
			resp.getWriter().println("</tr>");
	    }
		
		resp.getWriter().println("</table>");
		
		resp.getWriter().println("<div id='enroll-link'>");
		resp.getWriter().println("<a class='enroll' href='/CreateStudent' target='content'>Create New Student</a>");
		resp.getWriter().println("</div>");
		
		resp.getWriter().println("</body>");

	}
	
	private PersistenceManager getPersistenceManager() {
		return JDOHelper.getPersistenceManagerFactory("transactions-optional").getPersistenceManager();
	}
}
