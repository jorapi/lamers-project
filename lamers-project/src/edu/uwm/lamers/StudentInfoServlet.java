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

import edu.uwm.lamers.entities.*;

@SuppressWarnings({"serial", "unchecked"})
public class StudentInfoServlet extends HttpServlet
{
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException
	{
		boolean privaledged = false;
		
		try {
			for (Cookie c : req.getCookies()){
				if (c.getName().equals("priv") && c.getValue().equals("admin"))
					privaledged = true;
				if (c.getName().equals("priv") && c.getValue().equals("instructor"))
					privaledged = true;
			}
		} catch (NullPointerException e){
			
		}
		
		if (!privaledged) {
			resp.setContentType("text/html");
			resp.getWriter().println("<h2>Error: Not authorized</h2>");
			return;
		}
		
		String student = req.getParameter("student");
		
		req.getRequestDispatcher("student_info.jsp?student=" + student).forward(req, resp);
	}
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException
	{
		 PersistenceManager pm = getPersistenceManager();
		
		 String awardId = req.getParameter("award");
		 String studentId = req.getParameter("student");
		
		 
		 if (req.getParameter("type") != null && req.getParameter("type").equals("REMOVE")){
			 Student s = null;
			 Award c = null;
			 
			 for (Student stud : (List<Student>) pm.newQuery(Student.class).execute()) {		
					if(studentId.equals("" + stud.getKey().getId())){
						s = stud;
					}
			 }
			 for (Award cour : (List<Award>) pm.newQuery(Award.class).execute()) {		
					if(awardId.equals("" + cour.getKey().getId())){
						c = cour;
					}
			 }
			 
			 if ((s != null) && (c != null)){
				 if (s.removeAward(c) && c.removeStudent(s)){
					 req.getRequestDispatcher("student_info.jsp?student=" + studentId + "&type=REMSUCCESS").forward(req, resp);
					 return; 
				 }
			 }
		 }
		 
		 req.getRequestDispatcher("student_info.jsp?student=" + studentId + "&type=REMFAIL").forward(req, resp);
	}
					
	
	private PersistenceManager getPersistenceManager()
	{
		return JDOHelper.getPersistenceManagerFactory("transactions-optional").getPersistenceManager();
	}

}
