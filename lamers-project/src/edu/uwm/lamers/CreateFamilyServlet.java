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
import edu.uwm.lamers.entities.Family;
import edu.uwm.lamers.entities.Instructor;
import edu.uwm.lamers.entities.Student;



@SuppressWarnings("serial")
public class CreateFamilyServlet extends HttpServlet
{
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

		printForm(resp);
	}
	
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		
		String head = req.getParameter("family");
		String[] courseIDs = req.getParameterValues("students");
	
		
		Family f = new Family();
		
		PersistenceManager pm = getPersistenceManager();
		

		for (Student s : (List<Student>) pm.newQuery(Student.class).execute()) {		
			if(s.getKey().getId()==Long.parseLong(head))
				f.addHead(s);
		}
				for (int i = 0; i < courseIDs.length; ++i){
					for (Student s : (List<Student>) pm.newQuery(Student.class).execute()){
						if(Long.parseLong(courseIDs[i])==(s.getKey().getId()) && !(courseIDs[i]).equals(head) )
							f.getFamily().add(s);
					}
					
				}
				
				
					pm.makePersistent(f);
				
					pm.close();
				
					
		resp.getWriter().println(f.getFamily().size()+1);
		for(Student s: f.getFamily())
			resp.getWriter().println(s.getFirstName());
		resp.getWriter().println("<h2>Family Created successfully!</h2>");
		printForm(resp);
	}
	
	
	
	private PersistenceManager getPersistenceManager()
	{
		return JDOHelper.getPersistenceManagerFactory("transactions-optional").getPersistenceManager();
	}
	
	
	
	
	private void printForm(HttpServletResponse resp) throws IOException {

		PersistenceManager pm = getPersistenceManager();
		
		resp.setContentType("text/html");

		resp.getWriter().println("<h2>Create a Family</h2>");
		resp.getWriter().println("<form action='/CreateFamily' method='post'>");
		resp.getWriter().println("<table cellpadding='5'>");
		
		resp.getWriter().println("<tr>");		
		resp.getWriter().println("<td>Select Head of Family: </td>");
		resp.getWriter().println("<td><select name='family'>");
		for (Student s : (List<Student>) pm.newQuery(Student.class).execute()) {
			resp.getWriter().println("<option value='" + s.getKey().getId() + "'>" + s.getFirstName() + " " + s.getLastName() + "</option>");
	}
		resp.getWriter().println("</tr>");
		
		resp.getWriter().println("<tr>");
		resp.getWriter().println("<td><br>Select Family Memebers: </td>");
		resp.getWriter().println("<td><select multiple name='students'>");
		for (Student s : (List<Student>) pm.newQuery(Student.class).execute()) {
			resp.getWriter().println("<option value='" + s.getKey().getId() + "'>" + s.getFirstName()+" " +s.getLastName() + "</option>");
		}
		resp.getWriter().println("</tr>");
		resp.getWriter().println("</td>");
		resp.getWriter().println("</tr>");
		
		resp.getWriter().println("</table>");
		resp.getWriter().println("<input type='submit' value='Create Family'>");
		resp.getWriter().println("</form>");
			
	}

}
