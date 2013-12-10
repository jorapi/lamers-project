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
public class CreateDemoServlet extends HttpServlet {
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		boolean privaledged = false;
		
		try {
			for (Cookie c : req.getCookies()) {
				if (c.getName().equals("priv") && c.getValue().equals("admin"))
					privaledged = true;
			}
		} catch (NullPointerException e) {
			
		}
		
		if (!privaledged) {
			resp.setContentType("text/html");
			resp.getWriter().println("<h2>Error: Not authorized</h2>");
			return;
		}
		
		PersistenceManager pm = getPersistenceManager();
		
		resp.sendRedirect("/create_demo.jsp");
	}
	
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException
	{
		String demoName = req.getParameter("demo_name");
		
		PersistenceManager pm = getPersistenceManager();
		
		Demographic demo = new Demographic(demoName);
		
		try {
			pm.makePersistent(demo);
		} finally {
			pm.close();
		}
		
		req.getRequestDispatcher("create_demo.jsp?POST=success").forward(req, resp);
	}
	
	private PersistenceManager getPersistenceManager()
	{
		return JDOHelper.getPersistenceManagerFactory("transactions-optional").getPersistenceManager();
	}
}
