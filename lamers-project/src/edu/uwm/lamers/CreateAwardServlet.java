package edu.uwm.lamers;
import java.io.IOException;
import java.util.*;
import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.uwm.lamers.entities.Award;



@SuppressWarnings("serial")
public class CreateAwardServlet extends HttpServlet
{
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
	
	@SuppressWarnings("unchecked")
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		//default award level should be 0 for all awards, if a given award doesn't have a level
		
		int awardLevel = 0;
		int awardcost = 0;
		String awardTitle = req.getParameter("award_title");
		String level = req.getParameter("award_level");
		String cost = req.getParameter("award_cost");
		if(level.equalsIgnoreCase(""))
		{
			resp.getWriter().println("<h2>Error: level empty</h2>");
			printForm(resp);
		}
		else if(awardTitle.equalsIgnoreCase(""))
		{
			resp.getWriter().println("<h2>Error: award title required </h2>");
			printForm(resp);
		}
		else if(cost.equalsIgnoreCase(""))
		{
			resp.getWriter().println("<h2>Error: cost required </h2>");
			printForm(resp);
		}
		else
		{
			boolean error = false;
			awardLevel = Integer.parseInt(level);
			awardcost = Integer.parseInt(cost);
		
			PersistenceManager pm = getPersistenceManager();
		
			Award a = new Award(awardTitle, awardLevel, awardcost);
			
			for (Award b : (List<Award>) pm.newQuery(Award.class).execute()) {
				//check to see if award exists already in the list 
				if(b.getAwardTitle().equalsIgnoreCase(a.getAwardTitle()) && b.getAwardLevel() == a.getAwardLevel())
				{
					error = true;
					resp.getWriter().println("<h2>Error: Award exists</h2>");
					printForm(resp);
					break;
				}
			}
			if(!error)
			{
				try {
					pm.makePersistent(a);
				} finally {
					pm.close();
				}
		
				resp.getWriter().println("<h2>Award created successfully!</h2>");
				printForm(resp);
			}
		}
	}
	
	private PersistenceManager getPersistenceManager() {
		return JDOHelper.getPersistenceManagerFactory("transactions-optional").getPersistenceManager();
	}
	
	private void printForm(HttpServletResponse resp) throws IOException {
		
		resp.setContentType("text/html");

		resp.getWriter().println("<h2>Create Award</h2>");
		resp.getWriter().println("<form action='/CreateAward' method='post'>");
		resp.getWriter().println("<table cellpadding='5'>");
		
		resp.getWriter().println("<tr>");
		resp.getWriter().println("<td>Award Title: </td>");
		resp.getWriter().println("<td><input type='text' name='award_title'></td>");
		resp.getWriter().println("</tr>");
			
		resp.getWriter().println("<tr>");
		resp.getWriter().println("<td>Award Level: </td>");
		resp.getWriter().println("<td><input type='number' name='award_level'></td>");
		resp.getWriter().println("</tr>");
		
		resp.getWriter().println("<tr>");
		resp.getWriter().println("<td>Award Cost: </td>");
		resp.getWriter().println("<td><input type='number' name='award_cost'></td>");
		resp.getWriter().println("</tr>");
		
		resp.getWriter().println("</table>");
		resp.getWriter().println("<input type='submit' value='Create Award'>");
		resp.getWriter().println("</form>");
		
	}
}