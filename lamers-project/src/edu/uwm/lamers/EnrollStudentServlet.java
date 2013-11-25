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
public class EnrollStudentServlet extends HttpServlet {

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
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
		
		printForm(resp);
	}
	
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		String studentID = req.getParameter("student");
		String[] courseIDs = req.getParameterValues("classes");
		
		PersistenceManager pm = getPersistenceManager();

		for (Student s : (List<Student>) pm.newQuery(Student.class).execute()) {		
			if(studentID.equals(s.getKey().getId()));
				for (int i = 0; i < courseIDs.length; ++i){
					for (Course c : (List<Course>) pm.newQuery(Course.class).execute()) {		
						if(("" + c.getKey().getId()).equals(courseIDs[i]))
							s.addCourse(c);
							c.addStudent(s);
					}
				}
				
				try {
					pm.makePersistent(s);
				} finally {
					pm.close();
				}
				break;
		}
		
		resp.getWriter().println("<h2>Student enrolled successfully!</h2>");
		printForm(resp);
	}
	
	private PersistenceManager getPersistenceManager() {
		return JDOHelper.getPersistenceManagerFactory("transactions-optional").getPersistenceManager();
	}

	private void printForm(HttpServletResponse resp) throws IOException {
		
		PersistenceManager pm = getPersistenceManager();
		
		resp.setContentType("text/html");

		resp.getWriter().println("<h2>Enroll + getStudentTitle() + </h2>");
		resp.getWriter().println("<form action='/EnrollStudent' method='post'>");
		resp.getWriter().println("<table cellpadding='5'>");
		resp.getWriter().println("<tr>");
		resp.getWriter().println("<td>Student: </td>");
					
		resp.getWriter().println("<td><select name='student'>");
		for (Student s : (List<Student>) pm.newQuery(Student.class).execute()) {
			resp.getWriter().println("<option value='" + s.getKey().getId() + "'>" + s.getFirstName() + " " + s.getLastName() + "</option>");
		}

		resp.getWriter().println("</select></td>");
		resp.getWriter().println("</tr>");
				
		resp.getWriter().println("<tr>");
		resp.getWriter().println("<td>Classes: </td>");
		
		resp.getWriter().println("<td><select multiple name='classes'>");
		for (Course c : (List<Course>) pm.newQuery(Course.class).execute()) {
			resp.getWriter().println("<option value='" + c.getKey().getId() + "'>" + c.getTitle() + "</option>");
		}

		resp.getWriter().println("</select></td>");
		resp.getWriter().println("</tr>");
		resp.getWriter().println("</table>");
		resp.getWriter().println("<input type='submit' value='Submit'>");
		resp.getWriter().println("</form>");		
	}
	
	private String getStudentTitle() throws IOException{
		String name;
		
		BufferedReader br = new BufferedReader(new FileReader("terms.txt"));
	    try {
	        br.readLine();
	        name = br.readLine();
	    } finally {
	        br.close();
	    }
	    return name;
	}
}
