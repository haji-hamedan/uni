package com.hajihamedan.loan.controller;

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

}
