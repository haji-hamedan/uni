package com.hajihamedan.loan.controller;

import java.io.IOException;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hajihamedan.loan.helper.JalaliCalendar;
import com.hajihamedan.loan.helper.Security;
import com.hajihamedan.loan.helper.Validation;
import com.hajihamedan.loan.model.Domain;
import com.hajihamedan.loan.model.Loan;
import com.hajihamedan.loan.model.LoanRepo;
import com.hajihamedan.loan.model.Payment;
import com.hajihamedan.loan.model.PaymentFrequency;
import com.hajihamedan.loan.model.PaymentRepo;
import com.hajihamedan.loan.model.User;
import com.sun.jmx.snmp.Timestamp;

public class Loans extends Controller {

	public String index(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return this.showAll(request, response);
	}

	public String add(HttpServletRequest request, HttpServletResponse response) {
		request.setAttribute("pageTitle", "ثبت وام جدید");
		return "addLoan.jsp";
	}

	public String submit(HttpServletRequest request, HttpServletResponse response) throws IOException, Exception {
		String status = "success";
		String message = "";
		int userId = (int) request.getAttribute("currentUserId");

		String inputTitle = Security.clean(request.getParameter("title"));
		String inputAmount = Security.clean(request.getParameter("amount"));
		String inputInterestRate = Security.clean(request.getParameter("interestRate"));
		String inputPaymentCount = Security.clean(request.getParameter("paymentCount"));
		String inputPaymentFrequency = Security.clean(request.getParameter("paymentFrequency"));
		String inputFirstPaymentDate = Security.clean(request.getParameter("firstPaymentDate"));

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
		validator.setItem("بازه پرداخت اقساط", inputPaymentFrequency, paymentFrequencyRules);
		validator.setItem("اولین سررسید", inputFirstPaymentDate, firstPaymentDateRules);

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

			long today = Calendar.getInstance().getTimeInMillis();

			JalaliCalendar jalaliCalendar = new JalaliCalendar();
			long firstPaymentTimestamp = jalaliCalendar.PersianToMillis(firstPaymentDate);

			Loan newLoan = new Loan();
			newLoan.setTitle(title);
			newLoan.setAmount(amount);
			newLoan.setInterestRate(interestRate);
			newLoan.setPaymentCount(paymentCount);
			newLoan.setPaymentFrequency(paymentFrequency);
			newLoan.setFirstPaymentDate(firstPaymentTimestamp);
			newLoan.setUserId(userId);
			newLoan.setCreateDate(today);

			try {
				newLoan = (Loan) newLoan.persist();
				this.createPayments(newLoan);

			} catch (Exception e) {
				status = "error";
				message = e.getMessage();
				e.printStackTrace();
			}

			if (status == "success") {
				message = "ثبت وام با موفقیت انجام شد.";
			}
		}
		this.ajaxResponse(response, status, message);
		return null;
	}

	public String showAll(HttpServletRequest request, HttpServletResponse response) throws Exception {

		LoanRepo loanRepo = new LoanRepo();
		User currentUser = (User) request.getAttribute("currentUser");

		Vector<Domain> loans = null;
		if (currentUser.getIsAdmin() == 1) {
			loans = loanRepo.loadAll();
		} else {
			String condition = "userId = " + currentUser.getUserId();
			loans = loanRepo.loadByCondition(condition);
		}

		request.setAttribute("pageTitle", "مشاهده وام ها");
		request.setAttribute("loans", loans);
		return "showLoans.jsp";

	}

	public String show(HttpServletRequest request, HttpServletResponse response) throws Exception {

		int loanId = Integer.parseInt(request.getParameter("loanId"));
		Loan loan = Loan.loadById(loanId);

		request.setAttribute("pageTitle", "مشاهده وام " + loanId);
		request.setAttribute("loan", loan);
		return "showLoan.jsp";

	}

	/**
	 * Create payments by the standard formula.
	 * 
	 * @param loan
	 * @throws Exception
	 */
	private void createPayments(Loan loan) throws Exception {
		int loanId = loan.getLoanId();
		short paymentCount = loan.getPaymentCount();
		byte paymentFrequency = loan.getPaymentFrequency();
		byte interestRate = loan.getInterestRate();
		long loanAmount = loan.getAmount();
		int userId = loan.getUserId();

		long interestAmount = 0;
		short monthInterval = 0;
		long paymentAmount = 0;

		if (paymentFrequency == PaymentFrequency.MONTHLY_PAYMENT) {
			monthInterval = 1;
		} else if (paymentFrequency == PaymentFrequency.YEARLY_PAYMENT) {
			monthInterval = 12;
		}

		interestAmount = ((paymentCount + monthInterval) * interestAmount * loanAmount) / 2400;
		paymentAmount = (loanAmount + interestRate) / paymentCount;

		long today = new Timestamp().getDateTime();
		long paymentDate = loan.getFirstPaymentDate();
		int monthDiff = 0;

		Calendar cl = Calendar.getInstance();
		cl.setTimeInMillis(paymentDate);

		if (PaymentFrequency.MONTHLY_PAYMENT == loan.getPaymentFrequency()) {
			monthDiff = 1;
		} else if (PaymentFrequency.YEARLY_PAYMENT == loan.getPaymentFrequency()) {
			monthDiff = 12;
		}

		for (int i = 0; i < paymentCount; i++) {
			Payment payment = new Payment();
			payment.setLoanId(loanId);
			payment.setAmount(paymentAmount);
			payment.setPayDate(paymentDate);
			payment.setCreateDate(today);
			payment.setUserId(userId);
			payment.setIsPaid((byte) 0);
			payment.persist();

			cl.add(Calendar.MONTH, monthDiff);
			paymentDate = cl.getTimeInMillis();
		}

	}

	public String deleteLoan(HttpServletRequest request, HttpServletResponse response) throws IOException, Exception {

		String status = "success";
		String message = "";
		User currentUser = (User) request.getAttribute("currentUser");

		String inputLoanId = Security.clean(request.getParameter("loanId"));

		String[] loanIdRules = { "required", "int" };

		Validation validator = new Validation();
		validator.setItem("مشخصه وام", inputLoanId, loanIdRules);

		if (!validator.isValid()) {
			status = "error";
			message = validator.getMessage();
		} else {
			int loanId = Integer.parseInt(inputLoanId);
			Loan loan = Loan.loadById(loanId);

			if (loan.getUserId() != currentUser.getUserId() && currentUser.getIsAdmin() == 0) {
				status = "error";
				message = "شما اجازه ی این کار را ندارید.";
				this.ajaxResponse(response, status, message);
				return null;
			}

			LoanRepo loanRepo = new LoanRepo();
			loanRepo.deleteById(loanId);

			PaymentRepo paymentRepo = new PaymentRepo();
			paymentRepo.deleteByLoanId(loanId);

			if (status == "success") {
				message = "حذف وام با موفقیت انجام شد.";
			}
		}
		this.ajaxResponse(response, status, message);
		return null;
	}

	public String edit(HttpServletRequest request, HttpServletResponse response) throws Exception {

		int loanId = Integer.parseInt(request.getParameter("loanId"));
		Loan loan = Loan.loadById(loanId);
		User currentUser = (User) request.getAttribute("currentUser");

		if (loan.getUserId() != currentUser.getUserId() && currentUser.getIsAdmin() == 0) {
			request.setAttribute("message", "شما اجازه ی این کار را ندارید.");
			return "error.jsp";
		}

		request.setAttribute("loan", loan);
		request.setAttribute("pageTitle", "ویرایش وام " + loanId);
		return "editLoan.jsp";
	}

	public String submitEdit(HttpServletRequest request, HttpServletResponse response) throws IOException, Exception {
		String status = "success";
		String message = "";
		int loanId = Integer.parseInt(request.getParameter("loanId"));
		Loan loan = Loan.loadById(loanId);
		User currentUser = (User) request.getAttribute("currentUser");

		if (loan.getUserId() != currentUser.getUserId() && currentUser.getIsAdmin() == 0) {
			status = "error";
			message = "شما اجازه ی این کار را ندارید.";
			this.ajaxResponse(response, status, message);
			return null;
		}

		String inputTitle = Security.clean(request.getParameter("title"));
		String inputAmount = Security.clean(request.getParameter("amount"));
		String inputInterestRate = Security.clean(request.getParameter("interestRate"));
		String inputPaymentCount = Security.clean(request.getParameter("paymentCount"));
		String inputPaymentFrequency = Security.clean(request.getParameter("paymentFrequency"));
		String inputFirstPaymentDate = Security.clean(request.getParameter("firstPaymentDate"));

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
		validator.setItem("بازه پرداخت اقساط", inputPaymentFrequency, paymentFrequencyRules);
		validator.setItem("اولین سررسید", inputFirstPaymentDate, firstPaymentDateRules);

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

			long today = Calendar.getInstance().getTimeInMillis();

			JalaliCalendar jalaliCalendar = new JalaliCalendar();
			long firstPaymentTimestamp = jalaliCalendar.PersianToMillis(firstPaymentDate);

			loan.setTitle(title);
			loan.setAmount(amount);
			loan.setInterestRate(interestRate);
			loan.setPaymentCount(paymentCount);
			loan.setPaymentFrequency(paymentFrequency);
			loan.setFirstPaymentDate(firstPaymentTimestamp);
			loan.setCreateDate(today);

			try {
				loan = (Loan) loan.persist();
				this.deletePayments(loan);
				this.createPayments(loan);

			} catch (Exception e) {
				status = "error";
				message = e.getMessage();
				e.printStackTrace();
			}

			if (status == "success") {
				message = "ویرایش وام با موفقیت انجام شد.";
			}
		}
		this.ajaxResponse(response, status, message);
		return null;
	}

	private void deletePayments(Loan loan) throws Exception {
		PaymentRepo paymentRepo = new PaymentRepo();

		Vector<Domain> payments = loan.getPayments();
		Enumeration<Domain> allPayments = payments.elements();
		while (allPayments.hasMoreElements()) {
			Payment payment = (Payment) allPayments.nextElement();
			paymentRepo.deleteById(payment.getPaymentId());
		}
	}

}
