package edu.uwm.lamers;
import java.io.BufferedReader;
import java.io.FileReader;
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
public class CreateInstructorServlet extends HttpServlet
{
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
		
		Instructor in = new Instructor(firstName, lastName, email);
		in.setPassword(password);

		try {
			pm.makePersistent(in);
		} finally {
			pm.close();
		}
		
		resp.getWriter().println("<h2>Instructor created successfully!</h2>");
		printForm(resp);
	}
	
	private PersistenceManager getPersistenceManager() {
		return JDOHelper.getPersistenceManagerFactory("transactions-optional").getPersistenceManager();
	}
	
	private void printForm(HttpServletResponse resp) throws IOException {

		PersistenceManager pm = getPersistenceManager();
		
		String title = getInstructorTitle();
		
		resp.setContentType("text/html");

		resp.getWriter().println("<h2>Create " + title + "</h2>");
		resp.getWriter().println("<form action='/CreateInstructor' method='post'>");
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
		
		resp.getWriter().println("</table>");
		resp.getWriter().println("<input type='submit' value='Create " + title + "'>");
		resp.getWriter().println("</form>");
		
	}
	
	private String getInstructorTitle() throws IOException{
		String name;
		
		BufferedReader br = new BufferedReader(new FileReader("terms.txt"));
	    try {
	        name = br.readLine();
	    } finally {
	        br.close();
	    }
	    return name;
	}
}
