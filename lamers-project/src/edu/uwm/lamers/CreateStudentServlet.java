package edu.uwm.lamers;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.uwm.lamers.entities.Course;
import edu.uwm.lamers.entities.Demographic;
import edu.uwm.lamers.entities.Student;



@SuppressWarnings("serial")
public class CreateStudentServlet extends HttpServlet
{
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
boolean privaledged = false;
		
		try {
			for (Cookie c : req.getCookies()){
				if (c.getName().equals("priv") && c.getValue().equals("admin"))
					privaledged = true;
				if (c.getName().equals("priv") && c.getValue().equals("instructor"))
					privaledged = true;
			}
		} catch (NullPointerException e){
			
		}
		
		if (!privaledged) {
			resp.setContentType("text/html");
			resp.getWriter().println("<h2>Error: Not authorized</h2>");
			return;
		}
		
		printForm(resp);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		String firstName = req.getParameter("firstname");
		String lastName = req.getParameter("lastname");
		String email = req.getParameter("email");
		String password = req.getParameter("password");
		String demographic = req.getParameter("demographic");
		
		String[] courses = req.getParameterValues("courses");
		
		PersistenceManager pm = getPersistenceManager();
		
		Student s = new Student(firstName, lastName, email);
		s.setPassword(password);
		
		boolean enrolled = false;
		boolean attemptedToEnroll = (courses != null);
		
		if (courses != null){
			for (int i = 0; i < courses.length; ++i){
				for (Course c : (List<Course>) pm.newQuery(Course.class).execute()) {		
					if(("" + c.getKey().getId()).equals(courses[i])){
						if(c.getRequirements().isEmpty() || c.getRequirements().equals(s.getAwards())){
							s.addCourse(c);
							c.addStudent(s);
							enrolled = true;
						}
					}
				} 
			}
		}
		
		for (Demographic d : (List<Demographic>) pm.newQuery(Demographic.class).execute()) {		
			if(demographic.equals(d.getKey().getId())){
				s.setDemo(d);
				d.addStudent(s);
			}
		} 
		
		try {
			pm.makePersistent(s);
		} finally {
			pm.close();
		}
		
		if(!attemptedToEnroll || (attemptedToEnroll && enrolled))
			resp.getWriter().println("<h2>Student created successfully!</h2>");
		else if(attemptedToEnroll && !enrolled)
			resp.getWriter().println("<h2>Student created successfully but didn't meet requirements for course</h2>");
		printForm(resp);
	}
	
	private PersistenceManager getPersistenceManager() {
		return JDOHelper.getPersistenceManagerFactory("transactions-optional").getPersistenceManager();
	}
	
	@SuppressWarnings("unchecked")
	private void printForm(HttpServletResponse resp) throws IOException {

		PersistenceManager pm = getPersistenceManager();
		
		resp.setContentType("text/html");

		resp.getWriter().println("<h2>Create " + getStudentTitle() + "</h2>");
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
			
		/*resp.getWriter().println("<tr>");
		resp.getWriter().println("<td>Demographic: </td>");
		resp.getWriter().println("<td><select id='demographic' name='demographic'>");
		for (Demographic demo : (List<Demographic>) pm.newQuery(Demographic.class).execute()) {
			resp.getWriter().println("<option value='" + demo.getKey().getId() + "'>" + demo.getTitle() + "</option>");
	    }
		resp.getWriter().println("</select></td>");
		resp.getWriter().println("</tr>");*/
		
		resp.getWriter().println("<tr>");
		resp.getWriter().println("<td>Courses: </td>");
		resp.getWriter().println("<td>");
		for (Course c : (List<Course>) pm.newQuery(Course.class).execute()) {
			resp.getWriter().println("<input type='checkbox' name='courses' value='" + c.getKey().getId() + "'>" + c.getTitle() + "<br>");
			
	    }
		resp.getWriter().println("</td>");
		resp.getWriter().println("</tr>");
		
		resp.getWriter().println("</table>");
		resp.getWriter().println("<input type='submit' value='Create " + getStudentTitle() + "'>");
		resp.getWriter().println("</form>");
		
	}
	
	private String getStudentTitle() throws IOException{
		String name;
		
		BufferedReader br = new BufferedReader(new FileReader("terms.txt"));
	    try {
	        br.readLine();
	        name = br.readLine();
	    } finally {
	        br.close();
	    }
	    return name;
	}
}
