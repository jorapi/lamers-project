package edu.uwm.lamers;

import java.io.IOException;
import java.util.List;

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
public class LoginServlet extends HttpServlet
{
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException
	{
		printForm(resp);
	}
	
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException
	{
		String username = req.getParameter("user_name");
		String password = req.getParameter("password");
		
		PersistenceManager pm = getPersistenceManager();
		
		List<Student> students = (List<Student>) pm.newQuery(Student.class).execute();
		List<Instructor> instructors = (List<Instructor>) pm.newQuery(Instructor.class).execute();
		List<Admin> admins = (List<Admin>) pm.newQuery(Admin.class).execute();
		
		for(Student s : students){
			if ( s.getEmail().equals(username) && s.getPassword().equals(password)){
				Cookie c = new Cookie("priv", "student");
				Cookie c2 = new Cookie("email", s.getEmail());

				resp.addCookie(c);
				resp.addCookie(c2);
				resp.sendRedirect("/student-home.jsp");
				return;
			}
		}
		
		for(Instructor i : instructors){
			if ( i.getEmail().equals(username) && i.getPassword().equals(password)){
				Cookie c = new Cookie("priv", "instructor");
				Cookie c2 = new Cookie("email", i.getEmail());
				
				resp.addCookie(c);
				resp.sendRedirect("/instructor-home.jsp");
				return;
			}
		}
		
		for(Admin a : admins){
			if ( a.getEmail().equals(username) && a.getPassword().equals(password)){
				Cookie c = new Cookie("priv", "admin");

				resp.addCookie(c);
				resp.sendRedirect("/admin-home.jsp");
				return;
			}
		}
		
		
		resp.setContentType("text/html");
		resp.getWriter().println("<h2>Login failed!</h2>");
		resp.getWriter().println("<a href='/'> Try again </a>");
	}
	
	private PersistenceManager getPersistenceManager()
	{
		return JDOHelper.getPersistenceManagerFactory("transactions-optional").getPersistenceManager();
	}
	
	private void printForm(HttpServletResponse resp) throws IOException{
		resp.setContentType("text/html");

		resp.getWriter().println("<h2>Login Page</h2>");
		resp.getWriter().println("<form action='/login' method='POST' target='_top'>");
		resp.getWriter().println("<table cellpadding='5'>");
		resp.getWriter().println("<tr>");
		resp.getWriter().println("<td>Email Address: </td>");
		resp.getWriter().println("<td><input type='text' name='user_name'></td>");
		resp.getWriter().println("</tr>");
		resp.getWriter().println("<tr>");
		resp.getWriter().println("<td>Password: </td>");
		resp.getWriter().println("<td><input type='password' name='password'></td>");		
		resp.getWriter().println("</table>");
		
		resp.getWriter().println("</table>");
		resp.getWriter().println("</br><input type='submit' value='login'>");
		resp.getWriter().println("<input type='submit' value='Clear'>");
		resp.getWriter().println("</form>");
	}
}
