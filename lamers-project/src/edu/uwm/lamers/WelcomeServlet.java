package edu.uwm.lamers;

import java.io.IOException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class WelcomeServlet extends HttpServlet
{
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException
	{
		boolean privaledged = false;
		
		try {
			for (Cookie c : req.getCookies()){
				if (c.getName().equals("priv") && c.getValue().equals("admin")){
					resp.sendRedirect("/admin-home.html");
					privaledged = true;
				}
				if (c.getName().equals("priv") && c.getValue().equals("student")){
					resp.sendRedirect("/student-home.html");
					privaledged = true;
				}
				if (c.getName().equals("priv") && c.getValue().equals("instructor")){
					resp.sendRedirect("/instructor-home.html");
					privaledged = true;
				}
			}
		} catch (NullPointerException e){
			
		}
		
		if (!privaledged){
			resp.sendRedirect("/login.html");
		}
	}
}
