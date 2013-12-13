package edu.uwm.lamers;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.uwm.lamers.entities.*;

@SuppressWarnings("serial")
public class EmailServlet extends HttpServlet{
	
	String adminName;
	String adminEmail;
	
	@SuppressWarnings("unchecked")
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException
	{
		PersistenceManager pm = JDOHelper.getPersistenceManagerFactory("transactions-optional").getPersistenceManager();
		
		for(Admin a : (List<Admin>) pm.newQuery(Admin.class).execute()){
			adminName = a.getName();
			adminEmail = a.getEmail();
			break;
		}
		
		for(Student s : (List<Student>) pm.newQuery(Student.class).execute()){
			sendEmail(s);
			break;
		}
	}

	public void sendEmail(Student s){
		Properties props = new Properties();
		Session session = Session.getDefaultInstance(props, null);

		try {
			Message msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress(adminEmail, adminName));
			msg.addRecipient(Message.RecipientType.TO, new InternetAddress(s.getEmail(), s.getEmail()));
			msg.setSubject("Payment reminder");
			msg.setText(createMessage(s));
			Transport.send(msg);
		} catch (Exception e) {
			return;
		}
	}
	
	public String createMessage(Student s){
		String message = "Hello, " + s.getName() + ",\nThis is just a friendly reminder that your following payments are due:\n";
		
		for(PaymentPlan p : s.getPaymentPlans()){
			message += ("$" + p.getAmountDue() + "on " + p.getDueDate().toString() + "\n");
		}
		
		return message;
	}
}
