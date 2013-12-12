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
public class CreateCourseServlet extends HttpServlet {
	@SuppressWarnings("unchecked")
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
		
		if(((List<Instructor>) pm.newQuery(Instructor.class).execute()).size() != 0){
			resp.sendRedirect("/create_course.jsp");
		} else {
			resp.setContentType("text/html");

			resp.getWriter().println("<h2>Error: At least one Instructor required to create Course</h2>");
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException
	{
		Map<String, String> errors = new HashMap<String, String>();
		double standardCost = 0.0;
		double familyCost = 0.0;
		
		Boolean[] days = new Boolean[7];
		
		for(int i = 0; i < 7; i++) {
			days[i] = false;
		}
		
		String title = req.getParameter("title");
		String instructorID = req.getParameter("instructor");
		String location = req.getParameter("location");
		String startDate_str = req.getParameter("start_date");
		String endDate_str = req.getParameter("end_date");
		String startTime = req.getParameter("start_time");
		String endTime = req.getParameter("end_time");
		String standardCost_str = req.getParameter("standard_cost");
		String familyCost_str = req.getParameter("family_cost");
		String billingCycle = req.getParameter("billing_cycle");
		
		if (title.isEmpty()) {
			errors.put("title", "Title is required");
		}
		if (location.isEmpty()) {
			errors.put("location", "Location is required");
		}
		if (startDate_str.isEmpty()) {
			errors.put("start_date", "Start date is required");
		}
		if (endDate_str.isEmpty()) {
			errors.put("end_date", "End date is required");
		}
		if (startTime.isEmpty()) {
			errors.put("start_time", "Start time is required");
		}
		if (endTime.isEmpty()) {
			errors.put("end_time", "End time is required");
		}
		
		if (standardCost_str.isEmpty()) {
			errors.put("standard_cost", "Standard cost is required");
		} else {
			standardCost = Double.parseDouble(req.getParameter("standard_cost"));
		}
		
		if (familyCost_str.isEmpty()) {
			errors.put("family_cost", "Family cost is required");
		} else {
			familyCost = Double.parseDouble(req.getParameter("family_cost"));
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
		
		boolean checked = false;
		for (int i=0; i < days.length-1; i++) {
			if (days[i] == true) {
				checked = true;
				break;
			}
		}
		if (checked == false) {
			errors.put("days_to_meet", "Must select meeting day(s)");
		}
		
		DateFormat df = new SimpleDateFormat("mm/dd/yyyy");
		Date startDate = null;
		Date endDate = null;
		
		try {
			startDate = df.parse(startDate_str);
			endDate = df.parse(endDate_str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		PersistenceManager pm = getPersistenceManager();
		Instructor instructor = null;
		
		for (Instructor i : (List<Instructor>) pm.newQuery(Instructor.class).execute()) {		
			if(instructorID.equals("" + i.getKey().getId())) {
				instructor = i;
				break;
			}
		} 
		
		Course course;
		
		try {
			if (!errors.isEmpty()) {
				req.setAttribute("errors", errors);
				req.setAttribute("title", req.getParameter("title"));
				req.setAttribute("instructor", req.getParameter("instructor"));
				req.setAttribute("location", req.getParameter("location"));
				req.setAttribute("start_date", req.getParameter("start_date"));
				req.setAttribute("end_date", req.getParameter("end_date"));
				req.setAttribute("start_time", req.getParameter("start_time"));
				req.setAttribute("amount", req.getParameter("amount"));
				req.setAttribute("standard_cost", req.getParameter("standard_cost"));
				req.setAttribute("family_cost", req.getParameter("family_cost"));
				req.setAttribute("billing_cycle", req.getParameter("nilling_cycle"));
				
				req.getRequestDispatcher("create_course.jsp").forward(req, resp);
			} else {
				course = new Course(title, location, startDate, endDate, startTime, 
						endTime, days, standardCost, familyCost, billingCycle, instructor);
				pm.makePersistent(course);
				req.getRequestDispatcher("create_course.jsp?POST=success").forward(req, resp);
			}
		} finally {
			pm.close();
		}
	}
	
	private PersistenceManager getPersistenceManager() {
		return JDOHelper.getPersistenceManagerFactory("transactions-optional").getPersistenceManager();
	}
}