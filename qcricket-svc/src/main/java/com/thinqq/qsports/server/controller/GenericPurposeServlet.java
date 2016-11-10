package com.thinqq.qsports.server.controller;

import java.util.Date;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.thinqq.qsports.persistence.conf.SessionManager;
import com.thinqq.qsports.persistence.dao.UserDAO;
import com.thinqq.qsports.persistence.dataobjects.User;

public class GenericPurposeServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8892830523880606237L;
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) {
		doGet(request, response);
	}

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) {
		String email  = request.getParameter("email");
	    org.hibernate.Session sessionDao = null;
	    try {
	    	sessionDao = SessionManager.getSessionFactory().openSession();
	    	UserDAO userDao = new UserDAO();
	    	User user = userDao.getByEmail(sessionDao, email);
			if (user != null) {
				final String username = "qsportsthinqq@gmail.com";
				final String password = "password-123";
		 
				Properties props = new Properties();
				props.put("mail.smtp.auth", "true");
				props.put("mail.smtp.starttls.enable", "true");
				props.put("mail.smtp.host", "smtp.gmail.com");
				props.put("mail.smtp.port", "587");
				props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
		 
				Session session = Session.getInstance(props,
				 null);
		 
		 
					Message message = new MimeMessage(session);
					message.setFrom(new InternetAddress(username));
					message.setRecipients(Message.RecipientType.TO,
						InternetAddress.parse(user.getEmail()));
					message.setSubject("QSports Password");
					message.setText("Hello " + user.getFirstName() + "\n\n\n Your password registered with QSports is " + user.getPassword() + "\n\n Thanks QSports Admin\n Have a great Day!!");
		 
					Transport transport = session.getTransport("smtp");
					transport.connect("smtp.gmail.com", username, password);
					transport.sendMessage(message, message.getAllRecipients());
					transport.close();
					response.getWriter().append("Success");
			}
	    } catch (MessagingException mex) {
	        System.out.println("send failed, exception: " + mex);
	    } catch (Exception  mex) {
	        System.out.println("send failed, exception: " + mex);
	    }
	
	}
	
	public static void main(String args[]) {
		String email  = "binoy@thinqq.com";
	    org.hibernate.Session sessionDao = null;
	    try {
	    	sessionDao = SessionManager.getSessionFactory().openSession();
	    	UserDAO userDao = new UserDAO();
	    	User user = userDao.getByEmail(sessionDao, email);
			if (user != null) {
				Properties props = new Properties();
			    props.put("mail.smtp.host", "smtp.gmail.com");
			    props.put("mail.smtp.starttls.enable", "true");
				 Session session = Session.getInstance(props, null);
				MimeMessage msg = new MimeMessage(session);
				Address fromAdress = new InternetAddress(
						"qsportsthinqq@gmail.com");
				msg.setFrom(fromAdress);
				msg.setRecipients(Message.RecipientType.TO, "email");
				msg.setSubject("QSports Password");
				msg.setSentDate(new Date());
				msg.setText("Hello " + user.getFirstName() + "!\n Your password registered with QSports is " + user.getPassword() + "!\n!\n Thanks QSports Admin\n Have a great Day!!");
				//Transport transpot = session.getTransport();
				//transpot.connect("qsportsthinqq@gmail.com", "password-123");
				Transport.send(msg);
			}
	    } catch (MessagingException mex) {
	        System.out.println("send failed, exception: " + mex);
	    }
	}
}
