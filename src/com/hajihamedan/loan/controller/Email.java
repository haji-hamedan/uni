package com.hajihamedan.loan.controller;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Email extends Controller {

	public String sendEmail(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String statusMsg = "";

		String to = "47scorpion@gmail.com";
		String subject = "salam";
		String content = "in yek maile testist";

		boolean isSend = this.sendingEmail(request, response, to, subject, content);
		if (isSend) {
			statusMsg += "<span style='color: green;'>" + "ایمیل به " + to + " ارسال شد." + "</span>";
		} else {
			statusMsg += "<span style='color: red;'>" + "ایمیل به " + to + " ارسال نشد." + "</span>";
		}

		request.setAttribute("pageTitle", "ارسال ایمیل");
		request.setAttribute("statusMsg", statusMsg);
		return "sendEmail.jsp";
	}

	private boolean sendingEmail(HttpServletRequest request, HttpServletResponse response, String to, String subject, String content)
			throws Exception {
		String from = "loanmanagement873@gmail.com";
		String host = "smtp.gmail.com";
		String port = "25";
		final String userName = "loanmanagement873@gmail.com";
		final String password = "j3kj2hk4jh234";

		// Get system properties
		Properties properties = System.getProperties();

		// Setup mail server
		properties.put("mail.smtp.host", host);
		properties.put("mail.smtp.port", port);
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true");

		// creates a new session with an authenticator
		Authenticator auth = new Authenticator() {
			@Override
			public PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(userName, password);
			}
		};

		Session session = Session.getInstance(properties, auth);

		try {
			// Create a default MimeMessage object.
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(from));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
			message.setSubject(subject);
			message.setText(content);
			Transport.send(message);

			return true;
		} catch (MessagingException mex) {
			mex.printStackTrace();

			return false;
		}

	}
}
