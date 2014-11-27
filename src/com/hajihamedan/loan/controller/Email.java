package com.hajihamedan.loan.controller;

import java.util.Calendar;
import java.util.Enumeration;
import java.util.Properties;
import java.util.Vector;

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

import com.hajihamedan.loan.helper.JalaliCalendar;
import com.hajihamedan.loan.helper.NumberDelimiter;
import com.hajihamedan.loan.model.Domain;
import com.hajihamedan.loan.model.Loan;
import com.hajihamedan.loan.model.Payment;
import com.hajihamedan.loan.model.PaymentRepo;
import com.hajihamedan.loan.model.User;
import com.hajihamedan.loan.model.UserRepo;

public class Email extends Controller {

	public String sendEmail(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String statusMsg = "";
		String subject = "سررسید های این هفته ی شما";
		String emailContent;

		PaymentRepo paymentRepo = new PaymentRepo();

		UserRepo userRepo = new UserRepo();
		Vector<Domain> users = userRepo.loadAll();

		if (!users.isEmpty()) {
			Enumeration<Domain> allUsers = users.elements();
			while (allUsers.hasMoreElements()) {
				User user = (User) allUsers.nextElement();

				int days = 7;

				Calendar calendar = Calendar.getInstance();
				// java calendar start from 0 instead of 1!
				calendar.add(calendar.MONTH, 1);
				long today = calendar.getTimeInMillis();
				calendar.add(Calendar.DATE, days);
				long week = calendar.getTimeInMillis();

				Vector<Domain> payments = null;
				String condition = "userId = " + user.getUserId();
				condition += " and payDate >= " + today + " and payDate <= " + week;
				payments = paymentRepo.loadByCondition(condition, "payDate", "asc");

				if (payments.isEmpty()) {
					statusMsg += "<div style='color: black;font-size: 120%;line-height: 2em;'>" + "سررسید نزدیکی برای ارسال به " + user.getEmail()
							+ " موجود نبود." + "</div>";
				} else {

					emailContent = "";
					emailContent += "<div style='direction: rtl; text-align:right; font-family: tahoma;'>";
					emailContent += "<a href=" + request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
							+ request.getContextPath() + "><h1>سایت مدیریت اقساط و وام</h1></a>";
					emailContent += "<h2>سررسید های این هفته ی شما:</h2>";
					emailContent += "<div style='max-width: 800px; margin: 0; padding: 30px 0;font-family: tahoma;direction: rtl;'>";
					emailContent += "<table width='80%' border='1' cellpadding='0' cellspacing='0' dir='rtl'>";
					emailContent += "<tr>";
					emailContent += "<th style='padding: 1em;font-family: tahoma;text-align: center;'>وام</th>";
					emailContent += "<th style='padding: 1em;font-family: tahoma;text-align: center;'>مقدار</th>";
					emailContent += "<th style='padding: 1em;font-family: tahoma;text-align: center;'>تاریخ</th>";
					emailContent += "<th style='padding: 1em;font-family: tahoma;text-align: center;'>پرداخت شده؟</th>";
					emailContent += "</tr>";

					Enumeration<Domain> allPayments = payments.elements();
					while (allPayments.hasMoreElements()) {
						Payment payment = (Payment) allPayments.nextElement();
						String loanTitle = Loan.loadById(payment.getLoanId()).getTitle();

						JalaliCalendar jl3 = new JalaliCalendar();
						String paymentDate = jl3.GregorianToPersian(payment.getPayDate());

						String paidStatus = "پرداخت نشده است.";
						String paidColor = "red";
						if (payment.getIsPaid() == 1) {
							paidStatus = "پرداخت شده است.";
							paidColor = "green";
						}

						emailContent += "<tr style='color: white; background-color: " + paidColor + "'>";
						emailContent += "<td style='padding: 1em;font-family: tahoma;'>" + loanTitle + "</td>";
						emailContent += "<td style='padding: 1em;font-family: tahoma;'>" + NumberDelimiter.addDelimiter(payment.getAmount())
								+ " تومان</td>";
						emailContent += "<td style='padding: 1em;font-family: tahoma;text-align: center;'>" + paymentDate + "</td>";
						emailContent += "<td style='padding: 1em;font-family: tahoma;'>" + paidStatus + "</td>";
						emailContent += "</tr>";
					}
					emailContent += "</table></div></div>";

					// send email
					String to = user.getEmail();

					boolean isSend = this.sendingEmail(request, response, to, subject, emailContent);
					if (isSend) {
						statusMsg += "<div style='color: green;font-size: 120%;line-height: 2em;'>" + "ایمیل به " + to + " ارسال شد." + "</div>";
					} else {
						statusMsg += "<div style='color: red;font-size: 120%;line-height: 2em;'>" + "ایمیل به " + to + " ارسال نشد." + "</div>";
					}
				}
			}
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
			message.setHeader("Content-Type", "text/html; charset=utf-8");
			message.setContent(message, "text/html; charset=utf-8");
			message.setFrom(new InternetAddress(from));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
			message.setSubject(subject, "utf-8");
			message.setText(content, "utf-8", "html");
			Transport.send(message);

			return true;
		} catch (MessagingException mex) {
			mex.printStackTrace();

			return false;
		}

	}
}
