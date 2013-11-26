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
import edu.uwm.lamers.entities.Instructor;
import edu.uwm.lamers.entities.Student;



@SuppressWarnings("serial")
public class CreateCourseServlet extends HttpServlet
{
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException
	{
		
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
		
		PersistenceManager pm = getPersistenceManager();
		
		if(((List<Instructor>) pm.newQuery(Instructor.class).execute()).size() != 0){
			resp.sendRedirect("/create_course.jsp");
		} else {
			resp.setContentType("text/html");

			resp.getWriter().println("<h2>Error: At least one Instructor required to create Course</h2>");
		}
	}
	
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException
	{
		String CourseName = req.getParameter("class_name");
		String instructorID = req.getParameter("instructor");
		String Location = req.getParameter("location");
		String start = req.getParameter("start");
		String end = req.getParameter("end");
		int weeks = Integer.parseInt(req.getParameter("weeks"));
		double cost = Double.parseDouble(req.getParameter("cost"));
		Boolean[] days = new Boolean[7];
		
		for(int i = 0; i < 7; i++){
			days[i] = false;
		}
		
		if(req.getParameter("Monday") != null)
			days[1] = true;
		if(req.getParameter("Tuesday") != null)
			days[2] = true;
		if(req.getParameter("Wednesday") != null)
			days[3] = true;
		if(req.getParameter("Thursday") != null)
			days[4] = true;
		if(req.getParameter("Friday") != null)
			days[5] = true;
		if(req.getParameter("Saturday") != null)
			days[6] = true;
		if(req.getParameter("Sunday") != null)
			days[0] = true;
		
		PersistenceManager pm = getPersistenceManager();
		
		Course course = new Course(CourseName, Location, start, end);
		
		course.setMeetingDays(days);
		course.setCost(cost);
		course.setNumOfWeeks(weeks);
		
		for (Instructor instructor : (List<Instructor>) pm.newQuery(Instructor.class).execute()) {		
			if(instructorID.equals("" + instructor.getKey().getId())){
				course.setInstructor(instructor);
				instructor.addCourse(course);
				break;
			}
		} 
		
		try {
			pm.makePersistent(course);
		} finally {
			pm.close();
		}
		
		req.getRequestDispatcher("create_course.jsp?POST=success").forward(req, resp);
	}
	
	private PersistenceManager getPersistenceManager()
	{
		return JDOHelper.getPersistenceManagerFactory("transactions-optional").getPersistenceManager();
	}
}
