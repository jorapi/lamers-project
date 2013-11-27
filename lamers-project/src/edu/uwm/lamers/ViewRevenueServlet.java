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

import edu.uwm.lamers.entities.Award;
import edu.uwm.lamers.entities.Course;
import edu.uwm.lamers.entities.Demographic;
import edu.uwm.lamers.entities.Instructor;
import edu.uwm.lamers.entities.Student;

public class ViewRevenueServlet extends HttpServlet {
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
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
		
		req.getRequestDispatcher("view_revenue.jsp").forward(req, resp);

	}
	
	private PersistenceManager getPersistenceManager() {
		return JDOHelper.getPersistenceManagerFactory("transactions-optional").getPersistenceManager();
	}
}
