package edu.uwm.lamers;



import java.io.IOException;
import java.util.List;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.uwm.lamers.entities.*;

@SuppressWarnings("serial")
public class ClassListServlet extends HttpServlet
{
	@SuppressWarnings("unchecked")
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException
	{
		printForm(resp);
	}
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException
	{
		 PersistenceManager pm = getPersistenceManager();
		
		 String c = req.getParameter("course");
		 Course course = null;
		 
		 for (Course cse : (List<Course>) pm.newQuery(Course.class).execute()) {		
				if(cse.getTitle().equals(c)){
					course = cse;
				}
		 }
		 
		req.setAttribute("course", course);
		req.setAttribute("students", course.getClasslist());
		req.getRequestDispatcher("class_list.jsp").forward(req, resp);
		 /*PersistenceManager pm = getPersistenceManager();
			
			resp.setContentType("text/html");
			
			resp.getWriter().println("<head>");
			resp.getWriter().println("<link rel='stylesheet' type='text/css' href='styles/viewstudent.css'>");
			resp.getWriter().println("</head>");
			
			resp.getWriter().println("<body>");
			
			resp.getWriter().println("<table id='students'>");
			resp.getWriter().println("<h1>"+c+"</h1>");
			resp.getWriter().println("<tr>");
			resp.getWriter().println("<th>First Name</th>");
			resp.getWriter().println("<th>Last Name</th>");
			resp.getWriter().println("<th>Email</th>");
			resp.getWriter().println("<th>Classes Enrolled</th>");
			resp.getWriter().println("<th>Demographic</th>");
			resp.getWriter().println("<th>Balance Due</th>");
			resp.getWriter().println("<th>Awards</th>");
			resp.getWriter().println("</tr>");
		
		

		for (Course s : (List<Course>) pm.newQuery(Course.class).execute()) {		
			if(s.getTitle().equals(c)){
				resp.getWriter().println(s.getInstructor().getLastName());
				for(Student stud :s.getClasslist()){
					resp.getWriter().println("<tr>");
					
					resp.getWriter().println("<td>" + stud.getFirstName() + "</td>");
					resp.getWriter().println("<td>" + stud.getLastName() + "</td>");
					resp.getWriter().println("<td>" + stud.getEmail() + "</td>");
					
					resp.getWriter().println("<td>");
					resp.getWriter().println("<ul>");
					for(Course f : stud.getCourses()){
						resp.getWriter().println("<li>" + f.getTitle() + "</li>");
					}
					resp.getWriter().println("</ul>");
					resp.getWriter().println("</td>");
					if(stud.getDemo() != null){
						resp.getWriter().println("<td>" + stud.getDemo().getTitle() + "</td>");
					} else {
						resp.getWriter().println("<td></td>");
					}
					
					resp.getWriter().println("<td>" + stud.getBalance() + "</td>");
					
					resp.getWriter().println("<td>");
					resp.getWriter().println("<ul>");
					for(Award a : stud.getAwards()){
						resp.getWriter().println("<li>" + a.getAwardTitle() + "<li>");
					}
					resp.getWriter().println("</ul>");
					resp.getWriter().println("</td>");
					
					resp.getWriter().println("</tr>");
			    }
				}
			}*/
		}
					
	
	private PersistenceManager getPersistenceManager()
	{
		return JDOHelper.getPersistenceManagerFactory("transactions-optional").getPersistenceManager();
	}
	
	
	
	
	
	private void printForm(HttpServletResponse resp) throws IOException {

		PersistenceManager pm = getPersistenceManager();
		
		resp.setContentType("text/html");

		resp.getWriter().println("<h2></h2>");
		resp.getWriter().println("<form action='/classlist' method='post'>");
		resp.getWriter().println("<table cellpadding='5'>");
		
		resp.getWriter().println("<td><select name='course'>");
		for (Course c : (List<Course>) pm.newQuery(Course.class).execute()) {
			resp.getWriter().println("<option value='" + c.getTitle() + "'>" + c.getTitle()+"  ("+c.getInstructor().getLastName()+")" + "</option>");
		}
		
	
	
	resp.getWriter().println("<input type='submit' value='Submit'>");
	resp.getWriter().println("</form>");
		
		
	}

}
