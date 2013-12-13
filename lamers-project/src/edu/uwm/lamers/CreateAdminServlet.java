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
import edu.uwm.lamers.services.CreateAdminService;



@SuppressWarnings("serial")
public class CreateAdminServlet extends HttpServlet
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
		
		CreateAdminService cas = new CreateAdminService();
		
		Set<String> errors = cas.createAdmin(firstName, lastName, email, password);
		
		if(errors.isEmpty()){
			resp.getWriter().println("<h2>Admin created successfully!</h2>");
		} else {
			for(String e : errors){
				resp.getWriter().println(e);
			}
		}
		printForm(resp);
	}
	
	private PersistenceManager getPersistenceManager() {
		return JDOHelper.getPersistenceManagerFactory("transactions-optional").getPersistenceManager();
	}
	
	private void printForm(HttpServletResponse resp) throws IOException {

		PersistenceManager pm = getPersistenceManager();
		
		resp.setContentType("text/html");

		resp.getWriter().println("<h2>Create Admin</h2>");
		resp.getWriter().println("<form action='/SuperSecretCreateAdmin' method='post'>");
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
		resp.getWriter().println("<input type='submit' value='Create Student'>");
		resp.getWriter().println("</form>");
		
	}
}
