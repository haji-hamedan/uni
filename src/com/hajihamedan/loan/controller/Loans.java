package com.hajihamedan.loan.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hajihamedan.loan.helper.Security;
import com.hajihamedan.loan.helper.Validation;
import com.hajihamedan.loan.model.Loan;
import com.hajihamedan.loan.model.LoanRepo;

public class Loans {

	public String index(HttpServletRequest request, HttpServletResponse response) {
		return this.showAll(request, response);
	}

	public String add(HttpServletRequest request, HttpServletResponse response) {

		request.setAttribute("pageTitle", "ثبت وام جدید");
		return "addLoan.jsp";
	}

	public String submit(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		String status = "success";
		String message = "";
		int userId = 0;

		String inputTitle = Security.clean(request.getParameter("title"));
		String inputAmount = Security.clean(request.getParameter("amount"));
		String inputInterestRate = Security.clean(request
				.getParameter("interestRate"));
		String inputPaymentCount = Security.clean(request
				.getParameter("paymentCount"));
		String inputPaymentFrequency = Security.clean(request
				.getParameter("paymentFrequency"));
		String inputFirstPaymentDate = Security.clean(request
				.getParameter("firstPaymentDate"));

		String[] titleRules = { "required" };
		String[] amountRules = { "required", "long" };
		String[] interestRateRules = { "required", "byte" };
		String[] paymentCountRules = { "required", "short" };
		String[] paymentFrequencyRules = { "required", "byte" };
		String[] firstPaymentDateRules = { "required", "date" };

		Validation validator = new Validation();
		validator.setItem("عنوان وام", inputTitle, titleRules);
		validator.setItem("مقدار وام", inputAmount, amountRules);
		validator.setItem("نرخ بهره", inputInterestRate, interestRateRules);
		validator.setItem("تعداد اقساط", inputPaymentCount, paymentCountRules);
		validator.setItem("بازه پرداخت اقساط", inputPaymentFrequency,
				paymentFrequencyRules);
		validator.setItem("اولین سررسید", inputFirstPaymentDate,
				firstPaymentDateRules);

		if (!validator.isValid()) {
			status = "error";
			message = validator.getMessage();
		} else {
			String title = inputTitle;
			long amount = Long.parseLong(inputAmount);
			byte interestRate = Byte.parseByte(inputInterestRate);
			short paymentCount = Short.parseShort(inputPaymentCount);
			byte paymentFrequency = Byte.parseByte(inputPaymentFrequency);
			String firstPaymentDate = inputFirstPaymentDate;

			String today = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

			Loan newLoan = new Loan();
			newLoan.setTitle(title);
			newLoan.setAmount(amount);
			newLoan.setInterestRate(interestRate);
			newLoan.setPaymentCount(paymentCount);
			newLoan.setPaymentFrequency(paymentFrequency);
			newLoan.setFirstPaymentDate(today);
			newLoan.setUserId(userId);
			newLoan.setCreateDate(today);

			LoanRepo loanRepo = new LoanRepo();

			try {
				loanRepo.persist(newLoan);
			} catch (Exception e) {
				status = "error";
				message = e.getMessage();
			}

			if (status == "success") {
				message = "ثبت وام با موفقیت انجام شد.";
			}
		}
		this.ajaxResponse(response, status, message);
		return null;
	}

	public String showAll(HttpServletRequest request, HttpServletResponse response) {

		request.setAttribute("pageTitle", "نمایش وام ها");
		request.setAttribute("message", "سلام ");
		return "showLoans.jsp";

	}

	private void ajaxResponse(HttpServletResponse response, String status,
			String message) throws IOException {
		String reponse_msg = "{\"status\":\"" + status + "\",\"msg\":\""
				+ message.trim() + "\"}";
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(reponse_msg);
	}
}
