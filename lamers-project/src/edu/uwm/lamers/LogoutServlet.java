package edu.uwm.lamers;

import java.io.IOException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class LogoutServlet extends HttpServlet
{
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException
	{
		Cookie c = new Cookie("priv", null);
		c.setMaxAge(0);
		
		resp.addCookie(c);
		
		c = new Cookie("email", null);
		c.setMaxAge(0);
		
		resp.addCookie(c);
		resp.sendRedirect("/login.html");
	}
}
