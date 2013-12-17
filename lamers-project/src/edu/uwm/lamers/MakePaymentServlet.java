package edu.uwm.lamers;
import java.io.IOException;
import java.util.*;
import java.sql.Time;
import java.text.*;
import java.lang.Math;

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
import edu.uwm.lamers.entities.Instructor;
import edu.uwm.lamers.entities.PaymentPlan;
import edu.uwm.lamers.entities.Student;
import edu.uwm.lamers.services.CreateAdminService;



@SuppressWarnings("serial")
public class MakePaymentServlet extends HttpServlet
{
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		printForm(resp);
	}
	
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		
		
		PersistenceManager pm = JDOHelper.getPersistenceManagerFactory("transactions-optional").getPersistenceManager();
		List<Student> students = (List<Student>) pm.newQuery(Student.class).execute();
		
		Student thisStudent = null;
		String studEmail = null;
		double amount=0;
		double temp;
		
		for (Cookie c : req.getCookies()){
			if (c.getName().equals("email"))
				studEmail = c.getValue();
		}
		
		for (Student s : students){
			if (s.getEmail() != null){
				if (s.getEmail().equals(studEmail))
					thisStudent = s;
			}
		}
		
		try{
			amount = Double.parseDouble(req.getParameter("amount"));	
			}
		
		catch(NumberFormatException e){
			resp.getWriter().println("<h2>Amount Incorrect Please enter a different amount</h2>");
			printForm(resp);
		}
		
		thisStudent.setBalance(thisStudent.getBalance()-amount);
		for(PaymentPlan p: thisStudent.getPaymentPlans()){
			if (amount>p.getAmountDue()){

				p.setAmount(p.getAmount()-p.getAmountDue());
				amount=amount-p.getAmountDue();
				
				p.setAmountDue(0);
				
		}
			else{
				p.setAmountDue(p.getAmountDue()-amount);
				p.setAmount(p.getAmount()-amount);
				
				amount=0;
			}
		}
		
		
		resp.getWriter().println("payment successful!");
		printForm(resp);
		
		
	}
	
	
	private PersistenceManager getPersistenceManager() {
		return JDOHelper.getPersistenceManagerFactory("transactions-optional").getPersistenceManager();
	}
	
	private void printForm(HttpServletResponse resp) throws IOException {

		PersistenceManager pm = getPersistenceManager();
		
		resp.setContentType("text/html");

		resp.getWriter().println("<h2>Make a Payment</h2>");
		resp.getWriter().println("<form action='/MakePayment' method='post'>");
		resp.getWriter().println("<table cellpadding='5'>");
		
		resp.getWriter().println("<tr>");
		resp.getWriter().println("<td>Amount </td>");
		resp.getWriter().println("<td><input type='text' name='amount'></td>");
		resp.getWriter().println("</tr>");
			
		
		resp.getWriter().println("</table>");
		resp.getWriter().println("<input type='submit' value='Submit Payment'>");
		resp.getWriter().println("</form>");
		
	}
}
