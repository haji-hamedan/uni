package com.hajihamedan.loan.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
		String msg = "ثبت با موفقیت انجام شد.";

		String reponse_msg = "{\"status\":\"" + status + "\",\"msg\":\"" + msg
				+ "\"}";
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(reponse_msg);
		return "";
	}

	public String showAll(HttpServletRequest request, HttpServletResponse response) {

		request.setAttribute("pageTitle", "نمایش وام ها");
		request.setAttribute("message", "سلام ");
		return "showLoans.jsp";

	}
}
