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
import edu.uwm.lamers.entities.Demographic;
import edu.uwm.lamers.entities.Instructor;
import edu.uwm.lamers.entities.Student;



@SuppressWarnings("serial")
public class CreateStudentServlet extends HttpServlet
{
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		printForm(resp);
	}
	
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		String firstName = req.getParameter("firstname");
		String lastName = req.getParameter("lastname");
		String email = req.getParameter("email");
		String password = req.getParameter("password");
		
		String[] courses = req.getParameterValues("courses");
		
		PersistenceManager pm = getPersistenceManager();
		
		Student s = new Student(firstName, lastName, email);
		s.setPassword(password);
		
		
		if (courses != null){
			for (int i = 0; i < courses.length; ++i){
				for (Course c : (List<Course>) pm.newQuery(Course.class).execute()) {		
					if(c.getTitle().equals(courses[i]))
						s.addCourse(c);
						c.addStudent(s);
				} 
			}
		}
		
		try {
			pm.makePersistent(s);
		} finally {
			pm.close();
		}
		
		resp.getWriter().println("<h2>Student created successfully!</h2>");
		printForm(resp);
	}
	
	private PersistenceManager getPersistenceManager() {
		return JDOHelper.getPersistenceManagerFactory("transactions-optional").getPersistenceManager();
	}
	
	private void printForm(HttpServletResponse resp) throws IOException {

		PersistenceManager pm = getPersistenceManager();
		
		resp.setContentType("text/html");

		resp.getWriter().println("<h2>Create Student</h2>");
		resp.getWriter().println("<form action='/CreateStudent' method='post'>");
		resp.getWriter().println("<table cellpadding='5'>");
		
		resp.getWriter().println("<tr>");
		resp.getWriter().println("<td>First name: </td>");
		resp.getWriter().println("<td><input type='text' name='firstname'></td>");
		resp.getWriter().println("</tr>");
			
		resp.getWriter().println("<tr>");
		resp.getWriter().println("<td>Last name: </td>");
		resp.getWriter().println("<td><input type='text' name='lastname'></td>");
		resp.getWriter().println("</tr>");
			
		resp.getWriter().println("<tr>");
		resp.getWriter().println("<td>Email: </td>");
		resp.getWriter().println("<td><input type='email' name='email'></td>");
		resp.getWriter().println("</tr>");

		resp.getWriter().println("<tr>");
		resp.getWriter().println("<td>Password: </td>");
		resp.getWriter().println("<td><input type='password' name='password'></td>");
		resp.getWriter().println("</tr>");
			
		resp.getWriter().println("<tr>");
		resp.getWriter().println("<td>Demographic: </td>");
		for (Demographic demo : (List<Demographic>) pm.newQuery(Demographic.class).execute()) {
			resp.getWriter().println("<option value='" + demo.getKey().getId() + "'>" + demo.getTitle() + "</option>");
	    }
		resp.getWriter().println("</select></td>");
		resp.getWriter().println("</tr>");
		
		resp.getWriter().println("<tr>");
		resp.getWriter().println("<td>Courses: </td>");
		resp.getWriter().println("<td>");
		for (Course c : (List<Course>) pm.newQuery(Course.class).execute()) {
			resp.getWriter().println("<input type='checkbox' name='courses' value='" + c.getKey().getId() + "'>" + c.getTitle() + "<br>");
			
	    }
		resp.getWriter().println("</td>");
		resp.getWriter().println("</tr>");
		
		resp.getWriter().println("</table>");
		resp.getWriter().println("<input type='submit' value='Create Student'>");
		resp.getWriter().println("</form>");
		
	}
}
