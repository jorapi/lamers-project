package edu.uwm.lamers;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.uwm.lamers.entities.Course;
import edu.uwm.lamers.entities.Demographic;
import edu.uwm.lamers.entities.Student;

@SuppressWarnings("serial")
public class AssignDemoServlet extends HttpServlet {

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
		
		req.getRequestDispatcher("assign_demo.jsp").forward(req, resp);
	}
	
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		String demoID = req.getParameter("demo");
		String[] studIDs = req.getParameterValues("students");
		
		PersistenceManager pm = getPersistenceManager();

		for (Demographic d : (List<Demographic>) pm.newQuery(Demographic.class).execute()) {		
			if(demoID.equals("" + d.getKey().getId())){
				for (int i = 0; i < studIDs.length; ++i){
					for (Student s : (List<Student>) pm.newQuery(Student.class).execute()) {		
						if(("" + s.getKey().getId()).equals(studIDs[i])){
							s.setDemo(d);
							d.addStudent(s);
						}
							
					}
				}
			}
		}
		
		cleanDemos();
		
		req.getRequestDispatcher("assign_demo.jsp?POST=success").forward(req, resp);
	}
	
	private PersistenceManager getPersistenceManager() {
		return JDOHelper.getPersistenceManagerFactory("transactions-optional").getPersistenceManager();
	}
	
	/**
	 * Iterates through all students and demos to ensure that the demos only contain
	 * students who's demo is set to that particular demo
	 */
	private void cleanDemos() {
		PersistenceManager pm = getPersistenceManager();
		
		List<Demographic> demos = (List<Demographic>) pm.newQuery(Demographic.class).execute();
		
		for (Demographic d : demos){
			for (Student s : (List<Student>) pm.newQuery(Student.class).execute()){
				if(d.containsStudent(s)){
					if (s.getDemo().getKey().getId() != d.getKey().getId()){
						d.removeStudent(s);
					}
				}
			}
		}
	}
}
