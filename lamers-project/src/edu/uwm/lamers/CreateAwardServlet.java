package edu.uwm.lamers;
import java.io.BufferedReader;
import java.io.FileReader;
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
import edu.uwm.lamers.entities.Student;
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
	
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		//default award level should be 0 for all awards, if a given award doesn't have a level
		
		String awardTitle = req.getParameter("award_title");
		int awardLevel = Integer.parseInt(req.getParameter("award_level"));
		
		PersistenceManager pm = getPersistenceManager();
		String[] awards = req.getParameterValues("awards");
		
		Award a = new Award(awardTitle, awardLevel);
		
		for(int i = 0; i < awards.length; ++i)
		{
			//check to see if award exists already in the list in form of "Sword1" "Belt1" "Belt2" etc
			if(awards[i] != awardTitle + awardLevel)
			{
				//once it gets to a spot in the array which is empty, the award is placed there
				if(awards[i].equals(""))
					awards[i] = awardTitle + awardLevel;
					int key = i;
			}
		}
		
		try {
			pm.makePersistent(a);
		} finally {
			pm.close();
		}
		
		resp.getWriter().println("<h2>Award created successfully!</h2>");
		printForm(resp);
	}
	
	private PersistenceManager getPersistenceManager() {
		return JDOHelper.getPersistenceManagerFactory("transactions-optional").getPersistenceManager();
	}
	
	private void printForm(HttpServletResponse resp) throws IOException {

		PersistenceManager pm = getPersistenceManager();
		
		resp.setContentType("text/html");

		resp.getWriter().println("<h2>Create " + getAwardTitle() + "</h2>");
		resp.getWriter().println("<form action='/CreateStudent' method='post'>");
		resp.getWriter().println("<table cellpadding='5'>");
		
		resp.getWriter().println("<tr>");
		resp.getWriter().println("<td>Award Title: </td>");
		resp.getWriter().println("<td><input type='text' name='award_title'></td>");
		resp.getWriter().println("</tr>");
			
		resp.getWriter().println("<tr>");
		resp.getWriter().println("<td>Award Level: </td>");
		resp.getWriter().println("<td><input type='text' name='award_level'></td>");
		resp.getWriter().println("</tr>");
		
		resp.getWriter().println("</table>");
		resp.getWriter().println("<input type='submit' value='Create " + getAwardTitle() + "'>");
		resp.getWriter().println("</form>");
		
	}
	
	private String getAwardTitle() throws IOException{
		String title;
		
		BufferedReader br = new BufferedReader(new FileReader("terms.txt"));
	    try {
	        br.readLine();
	        title = br.readLine();
	    } finally {
	        br.close();
	    }
	    return title;
	}
}