package com.hajihamedan.loan.controller;

import java.util.Calendar;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hajihamedan.loan.model.Domain;
import com.hajihamedan.loan.model.PaymentRepo;
import com.hajihamedan.loan.model.User;

public class Index extends Controller {

	public String index(HttpServletRequest request, HttpServletResponse response) throws Exception {
		PaymentRepo paymentRepo = new PaymentRepo();
		User currentUser = (User) request.getAttribute("currentUser");

		int days = 7;

		try {
			int inputDays = Integer.parseInt(request.getParameter("days"));
			days = inputDays;
		} catch (Exception e) {
		}

		Calendar calendar = Calendar.getInstance();
		// java calendar start from 0 instead of 1!
		calendar.add(Calendar.MONTH, 1);

		long today = calendar.getTimeInMillis();

		calendar.add(Calendar.DATE, days);
		long week = calendar.getTimeInMillis();

		Vector<Domain> payments = null;

		if (currentUser != null) {
			String condition = "userId = " + currentUser.getUserId();
			condition += " and payDate >= " + today + " and payDate <= " + week;
			payments = paymentRepo.loadByCondition(condition, "payDate", "asc");
		}

		request.setAttribute("payments", payments);
		request.setAttribute("days", days);
		request.setAttribute("startDate", today);
		request.setAttribute("endDate", week);
		return "index.jsp";
	}

}
