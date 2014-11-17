package com.hajihamedan.loan.controller;

import java.io.IOException;
import java.lang.reflect.Method;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.hajihamedan.loan.model.DataHandler;
import com.hajihamedan.loan.model.User;

@WebServlet("/")
public class Front extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

		HttpSession session = request.getSession(true);
		int currentUserId = 0;
		if (session.getAttribute("userId") != null) {
			currentUserId = (int) session.getAttribute("userId");
		}

		User currentUser = null;
		try {
			currentUser = User.loadById(currentUserId);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		request.setAttribute("currentUserId", currentUserId);
		request.setAttribute("currentUser", currentUser);

		try {
			// remove '/' from beginning of url
			String path = request.getServletPath().substring(1, request.getServletPath().length());
			int pathLength = path.length();

			String className;
			String methodName;

			// main page of application
			if (pathLength == 0) {
				request.getRequestDispatcher("index.jsp").forward(request, response);
			} else {

				try {
					int dotPosition = path.indexOf(".");
					className = path.substring(0, dotPosition);

					if (dotPosition == pathLength - 1) {
						methodName = "index";
					} else {
						methodName = path.substring(dotPosition + 1, pathLength);
					}
				} catch (StringIndexOutOfBoundsException e) {
					className = path.substring(0, pathLength);
					methodName = "index";
				}

				className = className.substring(0, 1).toUpperCase() + className.substring(1);

				Class<?> ctrlClass = Class.forName("com.hajihamedan.loan.controller." + className);

				Method m = ctrlClass.getMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);

				String forward = (String) m.invoke(ctrlClass.newInstance(), request, response);

				if (forward != null) {
					request.getRequestDispatcher(forward).forward(request, response);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("message", e.toString());
			request.getRequestDispatcher("error.jsp").forward(request, response);
		}

		try {
			DataHandler dh = DataHandler.getInstance();
			dh.closeConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
