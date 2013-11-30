package edu.uwm.lamers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AttendanceServlet extends HttpServlet {
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		boolean privaledged = false;
		
		try {
			for (Cookie c : req.getCookies()){
				if (c.getName().equals("priv")){
					if (c.getValue().equals("instructor")){
						privaledged = true;
						req.getRequestDispatcher("attendence_instructor.jsp").forward(req, resp);
					} else if (c.getValue().equals("student")){
						privaledged = true;
						req.getRequestDispatcher("attendence_student.jsp").forward(req, resp);
					}
					
				}
					
			}
		} catch (NullPointerException e){
			
		}
		
		if (!privaledged) {
			resp.setContentType("text/html");
			resp.getWriter().println("<h2>Error: Not authorized</h2>");
			return;
		}

	}
	
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		

	}
}
