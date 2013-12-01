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

import edu.uwm.lamers.entities.Course;
import edu.uwm.lamers.entities.Instructor;
import edu.uwm.lamers.entities.Student;

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
	
	@SuppressWarnings("unchecked")
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		PersistenceManager pm = JDOHelper.getPersistenceManagerFactory("transactions-optional").getPersistenceManager();
		List<Instructor> ins = (List<Instructor>) pm.newQuery(Instructor.class).execute();
		
		Instructor thisIn = null;
		String inEmail = null;
		
		for (Cookie c : req.getCookies()){
			if (c.getName().equals("email"))
				inEmail = c.getValue();
				
		}
		
		for (Instructor i : ins){
			System.out.println(inEmail);
			if (i.getEmail().equals(inEmail)){
				thisIn = i;
				System.out.println(i.getEmail());
			}	
		}
		
		for (Course c : thisIn.getCourses()){
			for(Student s : c.getClasslist()){
				
				if(s.getDaysMissed().get(c) != null){
					s.clearCourseDaysMissed(c); //reset the days missed
				}

				
				for(int week = 0; week < c.getNumOfWeeks(); week++){
					for (String value : req.getParameterValues("" + s.getKey().getId())){
						String[] strings = value.split("-");
						
						if(strings[0].equals("" + week)){
							s.addDayMissed(c, week, Integer.parseInt(strings[1]));
						}
					}
				}
			}
		}
		
		req.getRequestDispatcher("attendence_instructor.jsp").forward(req, resp);
	}
}
