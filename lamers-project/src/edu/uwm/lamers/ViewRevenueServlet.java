package edu.uwm.lamers;

import java.io.IOException;
import java.util.List;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.uwm.lamers.entities.Award;
import edu.uwm.lamers.entities.Course;
import edu.uwm.lamers.entities.Demographic;
import edu.uwm.lamers.entities.Instructor;
import edu.uwm.lamers.entities.Student;

public class ViewRevenueServlet extends HttpServlet {
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		boolean privaledged = false;
		
		try {
			for (Cookie c : req.getCookies()){
				if (c.getName().equals("priv") && c.getValue().equals("admin"))
					privaledged = true;
			}
		} catch (NullPointerException e){
			
		}
		
		if (!privaledged) {
			resp.setContentType("text/html");
			resp.getWriter().println("<h2>Error: Not authorized</h2>");
			return;
		}
		
		
		PersistenceManager pm = getPersistenceManager();
		
		resp.setContentType("text/html");
		
		resp.getWriter().println("<head>");
		resp.getWriter().println("<link rel='stylesheet' type='text/css' href='styles/viewstudent.css'>");
		resp.getWriter().println("</head>");
		
		resp.getWriter().println("<body>");
		
		resp.getWriter().println("<table id='students'>");
		resp.getWriter().println("<caption>Revenue</caption>");
		resp.getWriter().println("<tr>");
		resp.getWriter().println("<th>Instructor</th>");
		resp.getWriter().println("<th>Courses</th>");
		resp.getWriter().println("<th>Revenue</th>");
		resp.getWriter().println("</tr>");
		
		for (Instructor in : (List<Instructor>) pm.newQuery(Instructor.class).execute()) {
			resp.getWriter().println("<tr>");
			
			resp.getWriter().println("<td>" + in.getFirstName() + " " + in.getFirstName() + "</td>");
			
			resp.getWriter().println("<td>");
			resp.getWriter().println("<ul>");
			for(Course c : in.getCourses()){
				resp.getWriter().println("<li>" + c.getTitle() + "</li>");
			}
			resp.getWriter().println("</ul>");
			resp.getWriter().println("</td>");
			
			
			resp.getWriter().println("<td>" + getInstructorRev(in) + "</td>");
			
			resp.getWriter().println("</tr>");
	    }
		
		resp.getWriter().println("</table>");
		
		resp.getWriter().println("</body>");

	}
	
	private PersistenceManager getPersistenceManager() {
		return JDOHelper.getPersistenceManagerFactory("transactions-optional").getPersistenceManager();
	}
	
	private double getInstructorRev(Instructor i){
		double total = 0;
		
		for(Course c : i.getCourses()){
			total += (c.getCost() * c.size());
		}
		
		return total;
	}
}
