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
import edu.uwm.lamers.entities.Demographic;
import edu.uwm.lamers.entities.Family;
import edu.uwm.lamers.entities.Student;

@SuppressWarnings("serial")
public class AddFamilyMemberServlet extends HttpServlet {

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		
		printForm(resp);
	}
	
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
	
		String fam = req.getParameter("family");
		String[] courseIDs = req.getParameterValues("students");
		
		
		PersistenceManager pm = getPersistenceManager();
		
		
		for (Family f : (List<Family>) pm.newQuery(Family.class).execute()) {		
			if(f.getHead().getKey().getId()==Long.parseLong(fam)){
				for (int i = 0; i < courseIDs.length; ++i){
					for (Student s : (List<Student>) pm.newQuery(Student.class).execute()){
						if(Long.parseLong(courseIDs[i])==(s.getKey().getId()) && !(courseIDs[i]).equals(fam) )
							f.getFamily().add(s);
					 }
			    }
				
			
		    }
			
		}
		
		
	}
			
	private PersistenceManager getPersistenceManager() {
		return JDOHelper.getPersistenceManagerFactory("transactions-optional").getPersistenceManager();
	}

	private void printForm(HttpServletResponse resp) throws IOException {
		
		PersistenceManager pm = getPersistenceManager();
	
		resp.setContentType("text/html");

		resp.getWriter().println("<h2>Add a Student To a Family</h2>");
		resp.getWriter().println("<form action='/AddFamilyMember' method='post'>");
		resp.getWriter().println("<table cellpadding='5'>");
		
		resp.getWriter().println("<tr>");		
		resp.getWriter().println("<td>Select Family(by Head of Family): </td>");
		resp.getWriter().println("<td><select name='family'>");
		for (Family s : (List<Family>) pm.newQuery(Family.class).execute()) {
			resp.getWriter().println("<option value='" + s.getHead().getKey().getId() + "'>" + s.getHead().getFirstName() + " " + s.getHead().getLastName() + "</option>");
	}
		resp.getWriter().println("</tr>");
		
		resp.getWriter().println("<tr>");
		resp.getWriter().println("<td><br>Select Students to Add to Family </td>");
		resp.getWriter().println("<td><select multiple name='students'>");
		for (Student s : (List<Student>) pm.newQuery(Student.class).execute()) {
			resp.getWriter().println("<option value='" + s.getKey().getId() + "'>" + s.getFirstName()+" " +s.getLastName() + "</option>");
		}
		resp.getWriter().println("</tr>");
		resp.getWriter().println("</td>");
		resp.getWriter().println("</tr>");
		
		resp.getWriter().println("</table>");
		resp.getWriter().println("<input type='submit' value='Add Students'>");
		resp.getWriter().println("</form>");
		
	}
}