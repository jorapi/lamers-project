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

import edu.uwm.lamers.entities.Award;
import edu.uwm.lamers.entities.Course;
import edu.uwm.lamers.entities.Demographic;
import edu.uwm.lamers.entities.Student;

@SuppressWarnings("serial")
public class AssignAwardServlet extends HttpServlet {

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
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
		
		req.getRequestDispatcher("assign_award.jsp").forward(req, resp);
	}
	
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		String awardID = req.getParameter("award");
		String[] studIDs = req.getParameterValues("students");
		
		PersistenceManager pm = getPersistenceManager();

		for (Award a : (List<Award>) pm.newQuery(Award.class).execute()) {		
			if(awardID.equals("" + a.getKey().getId())){
				for (int i = 0; i < studIDs.length; ++i){
					for (Student s : (List<Student>) pm.newQuery(Student.class).execute()) {		
						if(("" + s.getKey().getId()).equals(studIDs[i])){
							s.addAward(a);
							a.addStudent(s);
							s.setBalance(s.getBalance() + a.getCost());
						}
							
					}
				}
			}
		}
		
		cleanAwards();
		
		req.getRequestDispatcher("assign_award.jsp?POST=success").forward(req, resp);
	}
	
	private PersistenceManager getPersistenceManager() {
		return JDOHelper.getPersistenceManagerFactory("transactions-optional").getPersistenceManager();
	}
	
	/**
	 * Iterates through all students and awards to ensure that the awards only contain
	 * students who's award is set to that particular award
	 */
	private void cleanAwards() {
		PersistenceManager pm = getPersistenceManager();
		
		List<Award> awards = (List<Award>) pm.newQuery(Award.class).execute();
		
		for (Award a : awards){
			for (Student s : (List<Student>) pm.newQuery(Student.class).execute()){
				if(a.containsStudent(s)){
					if (!s.getAwards().contains(a)){
						a.removeStudent(s);
					}
				}
			}
		}
	}
}
