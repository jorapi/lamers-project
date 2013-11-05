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
		
		for(Student s : students){
			if ( s.getEmail().equals(username) && s.getPassword().equals(password)){
				Cookie c = new Cookie("username", req.getParameter("user_name"));

				resp.addCookie(c);
				resp.sendRedirect("/index.html");
				return;
			}
		}
		
		resp.setContentType("text/html");
		resp.getWriter().println("<h2>Login failed!</h2>");
		resp.getWriter().println("<a href='/login'> Try again </a>");
	}
	
	private PersistenceManager getPersistenceManager()
	{
		return JDOHelper.getPersistenceManagerFactory("transactions-optional").getPersistenceManager();
	}
	
	private void printForm(HttpServletResponse resp) throws IOException{
		resp.setContentType("text/html");

		resp.getWriter().println("<h2>Login Page</h2>");
		resp.getWriter().println("<form action='/login' method='POST'>");
		resp.getWriter().println("<table cellpadding='5'>");
		resp.getWriter().println("<tr>");
		resp.getWriter().println("<td>User Name: </td>");
		resp.getWriter().println("<td><input type='text' name='user_name'></td>");
		resp.getWriter().println("</tr>");
		resp.getWriter().println("<tr>");
		resp.getWriter().println("<td>Password: </td>");
		resp.getWriter().println("<td><input type='text' name='password'></td>");
		resp.getWriter().println("<tr>");		
		resp.getWriter().println("<select>");
		resp.getWriter().println("<option value='student'>Student</option>");
		resp.getWriter().println("<option value='instructor'>Instructor</option>");
		resp.getWriter().println("<option value='administrator'>Administrator</option>");	
		resp.getWriter().println("</select>");
		resp.getWriter().println("</tr>");		
		resp.getWriter().println("</table>");
		
		resp.getWriter().println("</table>");
		resp.getWriter().println("</br><input type='submit' value='login'>");
		resp.getWriter().println("<input type='submit' value='Clear'>");
		resp.getWriter().println("</form>");
	}
}
