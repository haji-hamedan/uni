package com.hajihamedan.loan.controller;

import java.util.Calendar;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hajihamedan.loan.model.Domain;
import com.hajihamedan.loan.model.PaymentRepo;
import com.hajihamedan.loan.model.User;

public class Payments extends Controller {

	public String index(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return this.showAll(request, response);
	}

	public String showAll(HttpServletRequest request, HttpServletResponse response) throws Exception {

		PaymentRepo paymentRepo = new PaymentRepo();
		User currentUser = (User) request.getAttribute("currentUser");

		Vector<Domain> payments = null;
		if (currentUser.getIsAdmin() == 1) {
			payments = paymentRepo.loadAll("payDate", "asc");
		} else {
			String condition = "userId = " + currentUser.getUserId();
			payments = paymentRepo.loadByCondition(condition, "payDate", "asc");
		}

		request.setAttribute("pageTitle", "مشاهده اقساط و سررسیدها");
		request.setAttribute("payments", payments);
		return "showPayments.jsp";

	}

	public String showNear(HttpServletRequest request, HttpServletResponse response) throws Exception {
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
		calendar.add(calendar.MONTH, 1);

		long today = calendar.getTimeInMillis();

		calendar.add(Calendar.DATE, days);
		long week = calendar.getTimeInMillis();

		Vector<Domain> payments = null;

		String condition = "userId = " + currentUser.getUserId();
		condition += " and payDate >= " + today + " and payDate <= " + week;
		payments = paymentRepo.loadByCondition(condition, "payDate", "asc");

		request.setAttribute("pageTitle", "مشاهده اقساط و سررسیدهای نزدیک");
		request.setAttribute("payments", payments);
		request.setAttribute("startDate", today);
		request.setAttribute("endDate", week);
		return "showNearPayments.jsp";

	}

}
