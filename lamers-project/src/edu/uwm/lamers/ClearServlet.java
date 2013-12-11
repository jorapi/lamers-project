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
public class ClearServlet extends HttpServlet
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

	try {
		for (Student student : (List<Student>) pm.newQuery(Student.class).execute()) {
			pm.deletePersistent(student);
		}

		for (Course course : (List<Course>) pm.newQuery(Course.class).execute()) {
			pm.deletePersistent(course);
		}

		for (Instructor instructor : (List<Instructor>) pm.newQuery(Instructor.class).execute()) {
			pm.deletePersistent(instructor);
		}
		for (Admin admin : (List<Admin>) pm.newQuery(Admin.class).execute()) {
			pm.deletePersistent(admin);
		}
		
		for (Award a : (List<Award>) pm.newQuery(Award.class).execute()) {
			pm.deletePersistent(a);
		}
		
		for (Demographic a : (List<Demographic>) pm.newQuery(Demographic.class).execute()) {
			pm.deletePersistent(a);
		}
		
		for (Family a : (List<Family>) pm.newQuery(Family.class).execute()) {
			pm.deletePersistent(a);
		}
		
		for (Payment a : (List<Payment>) pm.newQuery(Payment.class).execute()) {
			pm.deletePersistent(a);
		}
		
		for (PaymentPlan a : (List<PaymentPlan>) pm.newQuery(PaymentPlan.class).execute()) {
			pm.deletePersistent(a);
		}

		resp.sendRedirect("/");
	} finally {
		pm.close();
	}
}

	private PersistenceManager getPersistenceManager()
	{
		return JDOHelper.getPersistenceManagerFactory("transactions-optional").getPersistenceManager();
	}
	private void printForm(HttpServletResponse resp) throws IOException {

		
		
		resp.setContentType("text/html");

		resp.getWriter().println("<h2>CLEAR DATA!!!</h2>");
		resp.getWriter().println("<form action='/clear' method='post'>");
		resp.getWriter().println("<table cellpadding='5'>");
		
		resp.getWriter().println("<input type='submit' value='Clear Servlet'>");
		resp.getWriter().println("</form>");
		
	}
}
