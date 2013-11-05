package edu.uwm.lamers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class LoginServlet extends HttpServlet
{
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException
	{
		resp.setContentType("text/html");

		resp.getWriter().println("<h2>Login Page</h2>");
		resp.getWriter().println("<form action='/login-page' method='POST'>");
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
	
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException
	{
		Cookie c = new Cookie("username", req.getParameter("user_name"));

		resp.addCookie(c);
		req.getRequestDispatcher("index.html").include(req, resp);
	}
}
