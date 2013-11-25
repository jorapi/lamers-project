package edu.uwm.lamers;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.uwm.lamers.entities.Award;
import edu.uwm.lamers.entities.Course;
import edu.uwm.lamers.entities.Demographic;
import edu.uwm.lamers.entities.Student;

public class ViewStudentsServlet extends HttpServlet {
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
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
		 
		req.getRequestDispatcher("view_students.jsp").forward(req, resp);
		
		/*
		PersistenceManager pm = getPersistenceManager();
		
		resp.setContentType("text/html");
		
		resp.getWriter().println("<head>");
		resp.getWriter().println("<link rel='stylesheet' type='text/css' href='styles/viewstudent.css'>");
		resp.getWriter().println("</head>");
		
		resp.getWriter().println("<body>");
		
		resp.getWriter().println("<table id='students'>");
		resp.getWriter().println("<caption>" + title + " List</caption>");
		resp.getWriter().println("<tr>");
		resp.getWriter().println("<th>First Name</th>");
		resp.getWriter().println("<th>Last Name</th>");
		resp.getWriter().println("<th>Email</th>");
		resp.getWriter().println("<th>Classes Enrolled</th>");
		resp.getWriter().println("<th>Demographic</th>");
		resp.getWriter().println("<th>Balance Due</th>");
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
				resp.getWriter().println("<li>" + c.getTitle() + "</li>");
			}
			resp.getWriter().println("</ul>");
			resp.getWriter().println("</td>");
			
			if(s.getDemo() != null){
				resp.getWriter().println("<td>" + s.getDemo().getTitle() + "</td>");
			} else {
				resp.getWriter().println("<td></td>");
			}
			
			resp.getWriter().println("<td>" + s.getBalance() + "</td>");
			
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
		resp.getWriter().println("<a class='enroll' href='/CreateStudent' target='content'>Create New " + title + "</a>");
		resp.getWriter().println("</div>");
		
		resp.getWriter().println("</body>");*/

	}
	
	private PersistenceManager getPersistenceManager() {
		return JDOHelper.getPersistenceManagerFactory("transactions-optional").getPersistenceManager();
	}
}
