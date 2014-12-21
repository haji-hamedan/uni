package com.hajihamedan.loan.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

public abstract class Controller {
	protected void ajaxResponse(HttpServletResponse response, String status, String message) throws IOException {
		String reponse_msg = "{\"status\":\"" + status + "\",\"msg\":\"" + message.trim() + "\"}";
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(reponse_msg);
	}
}
