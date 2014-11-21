package com.hajihamedan.loan.controller;

import java.io.IOException;
import java.util.Calendar;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hajihamedan.loan.helper.Security;
import com.hajihamedan.loan.helper.Validation;
import com.hajihamedan.loan.model.Domain;
import com.hajihamedan.loan.model.Payment;
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
		request.setAttribute("days", days);
		request.setAttribute("startDate", today);
		request.setAttribute("endDate", week);
		return "showNearPayments.jsp";
	}

	public String paidChange(HttpServletRequest request, HttpServletResponse response) throws IOException, Exception {

		String status = "success";
		String message = "";
		User currentUser = (User) request.getAttribute("currentUser");

		String inputPaymentId = Security.clean(request.getParameter("paymentId"));
		String inputIsChecked = Security.clean(request.getParameter("isChecked"));

		String[] paymentIdRules = { "required", "int" };
		String[] isCheckedRules = { "required" };

		Validation validator = new Validation();
		validator.setItem("مشخصه قسط", inputPaymentId, paymentIdRules);
		validator.setItem("پرداخت شده یا نشده", inputIsChecked, isCheckedRules);

		if (!validator.isValid()) {
			status = "error";
			message = validator.getMessage();
		} else {
			int paymentId = Integer.parseInt(inputPaymentId);
			Payment payment = Payment.loadById(paymentId);

			System.out.println(payment.getUserId());
			System.out.println(currentUser.getUserId());

			if (payment.getUserId() != currentUser.getUserId() && currentUser.getIsAdmin() == 0) {
				status = "error";
				message = "شما اجازه ی این کار را ندارید.";
				this.ajaxResponse(response, status, message);
				return null;
			}

			if (inputIsChecked.equals("true")) {
				payment.setIsPaid((byte) 1);
			} else {
				payment.setIsPaid((byte) 0);
			}
			payment.persist();

			if (status == "success") {
				message = " عملیات با موفقیت انجام شد.";
			}
		}
		this.ajaxResponse(response, status, message);
		return null;
	}

}
