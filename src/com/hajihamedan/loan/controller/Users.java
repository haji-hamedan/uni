package com.hajihamedan.loan.controller;

import java.io.IOException;
import java.security.MessageDigest;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.hajihamedan.loan.helper.Security;
import com.hajihamedan.loan.helper.Validation;
import com.hajihamedan.loan.model.Domain;
import com.hajihamedan.loan.model.User;
import com.hajihamedan.loan.model.UserRepo;

public class Users extends Controller {

	public String showAll(HttpServletRequest request, HttpServletResponse response) throws Exception {

		UserRepo userRepo = new UserRepo();
		Vector<Domain> users = userRepo.loadAll();

		request.setAttribute("pageTitle", "مشاهده کاربران");
		request.setAttribute("users", users);
		return "showUsers.jsp";

	}

	public String register(HttpServletRequest request, HttpServletResponse response) {
		request.setAttribute("pageTitle", "ثبت نام");
		return "register.jsp";

	}

	public String submit(HttpServletRequest request, HttpServletResponse response) throws IOException, Exception {
		String status = "success";
		String message = "";

		String inputUserName = Security.clean(request.getParameter("username"));
		String inputEmail = Security.clean(request.getParameter("email"));
		String inputPassword = Security.clean(request.getParameter("password"));
		String inputRePassword = Security.clean(request.getParameter("re_password"));
		String inputFirstName = Security.clean(request.getParameter("first_name"));
		String inputLastName = Security.clean(request.getParameter("last_name"));
		String inputMobile = Security.clean(request.getParameter("mobile"));

		String[] userNameRules = { "required" };
		String[] emailRules = { "required", "email" };
		String[] passwordRules = { "required" };
		String[] rePasswordRules = { "required" };
		String[] firstNameRules = { "required" };
		String[] lastNameRules = { "required" };
		String[] mobileRules = { "required", "long" };

		Validation validator = new Validation();
		validator.setItem("نام کاربری", inputUserName, userNameRules);
		validator.setItem("ایمیل", inputEmail, emailRules);
		validator.setItem("رمز عبور", inputPassword, passwordRules);
		validator.setItem("تکرار رمز عبور", inputRePassword, rePasswordRules);
		validator.setItem("نام", inputFirstName, firstNameRules);
		validator.setItem("نام خانوادگی", inputLastName, lastNameRules);
		validator.setItem("موبایل", inputMobile, mobileRules);

		if (!validator.isValid()) {
			status = "error";
			message = validator.getMessage();
		} else {
			String userName = inputUserName;
			String email = inputEmail;
			String password = inputPassword;
			String rePassword = inputRePassword;
			String firstName = inputFirstName;
			String lastName = inputLastName;
			String mobile = inputMobile;

			if (!password.equals(rePassword)) {
				status = "error";
				message = "رمز عبور و تکرار رمز عبور هم خوانی ندارند.";
				this.ajaxResponse(response, status, message);
				return null;
			}

			String encryptedPassword = this.encryptPassword(password);

			User newUser = new User();
			newUser.setUsername(userName);
			newUser.setEmail(email);
			newUser.setPassword(encryptedPassword);
			newUser.setFirstName(firstName);
			newUser.setLastName(lastName);
			newUser.setMobile(mobile);
			newUser.setIsAdmin((byte) 0);

			try {
				newUser = (User) newUser.persist();
			} catch (Exception e) {
				status = "error";
				message = e.getMessage();
				e.printStackTrace();
			}

			if (status == "success") {
				message = "ثبت نام با موفقیت انجام شد.";
			}
		}
		this.ajaxResponse(response, status, message);
		return null;
	}

	public String login(HttpServletRequest request, HttpServletResponse response) {
		request.setAttribute("pageTitle", "ورود");
		return "login.jsp";

	}

	public String submitLogin(HttpServletRequest request, HttpServletResponse response) throws IOException, Exception {
		String status = "success";
		String message = "";

		String inputUsername = Security.clean(request.getParameter("username"));
		String inputPassword = Security.clean(request.getParameter("password"));

		String[] usernameRules = { "required" };
		String[] passwordRules = { "required" };

		Validation validator = new Validation();
		validator.setItem("نام کاربری", inputUsername, usernameRules);
		validator.setItem("رمز عبور", inputPassword, passwordRules);

		if (!validator.isValid()) {
			status = "error";
			message = validator.getMessage();
		} else {
			String username = inputUsername;
			String password = inputPassword;

			User user = null;
			if (Security.isEmail(username)) {
				user = User.loadByEmail(username);
			} else {
				user = User.loadByUsername(username);
			}

			if (user == null) {
				status = "error";
				message = "کاربری با این مشخصات یافت نشد.";
				this.ajaxResponse(response, status, message);
				return null;
			}

			String userPassword = user.getPassword();

			String encryptedPassword = this.encryptPassword(password);

			if (!userPassword.equals(encryptedPassword)) {
				status = "error";
				message = "رمز عبور اشتباه است.";
				this.ajaxResponse(response, status, message);
				return null;
			}

			HttpSession session = request.getSession(true);
			session.removeAttribute("userId");
			session.setAttribute("userId", user.getUserId());

			if (status == "success") {
				message = "شما وارد شدید.";
			}
		}
		this.ajaxResponse(response, status, message);
		return null;
	}

	public void logout(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		session.setAttribute("userId", 0);
		session.invalidate();
		request.setAttribute("currentUserId", 0);
		request.setAttribute("currentUser", null);
		response.sendRedirect("");
	}

	private String encryptPassword(String password) throws Exception {
		String generatedPassword = null;
		MessageDigest md = MessageDigest.getInstance("MD5");
		md.update(password.getBytes());
		byte[] bytes = md.digest();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < bytes.length; i++) {
			sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
		}
		generatedPassword = sb.toString();
		return generatedPassword;
	}

	public String show(HttpServletRequest request, HttpServletResponse response) throws Exception {

		int userId = Integer.parseInt(request.getParameter("userId"));
		User user = User.loadById(userId);

		request.setAttribute("pageTitle", "مشاهده کاربر " + userId);
		request.setAttribute("user", user);
		return "showUser.jsp";
	}

	public String edit(HttpServletRequest request, HttpServletResponse response) throws Exception {

		int userId = Integer.parseInt(request.getParameter("userId"));
		User user = User.loadById(userId);
		User currentUser = (User) request.getAttribute("currentUser");

		if (user.getUserId() != currentUser.getUserId() && currentUser.getIsAdmin() == 0) {
			request.setAttribute("message", "شما اجازه ی این کار را ندارید.");
			return "error.jsp";
		}

		request.setAttribute("user", user);
		request.setAttribute("pageTitle", "ویرایش کاربر " + userId);
		return "editUser.jsp";
	}

	public String submitEdit(HttpServletRequest request, HttpServletResponse response) throws IOException, Exception {
		String status = "success";
		String message = "";

		int userId = Integer.parseInt(request.getParameter("userId"));

		User user = User.loadById(userId);
		User currentUser = (User) request.getAttribute("currentUser");

		if (user.getUserId() != currentUser.getUserId() && currentUser.getIsAdmin() == 0) {
			status = "error";
			message = "شما اجازه ی این کار را ندارید.";
			this.ajaxResponse(response, status, message);
			return null;
		}

		String inputUserName = Security.clean(request.getParameter("username"));
		String inputEmail = Security.clean(request.getParameter("email"));
		String inputFirstName = Security.clean(request.getParameter("first_name"));
		String inputLastName = Security.clean(request.getParameter("last_name"));
		String inputMobile = Security.clean(request.getParameter("mobile"));

		String inputIsAdmin = "off";
		if (currentUser.getIsAdmin() == 1) {
			try {
				inputIsAdmin = Security.clean(request.getParameter("isAdmin"));
			} catch (Exception e) {
			}
		}

		String[] userNameRules = { "required" };
		String[] emailRules = { "required", "email" };
		String[] firstNameRules = { "required" };
		String[] lastNameRules = { "required" };
		String[] mobileRules = { "required", "long" };
		// String[] isAdminRules = {};

		Validation validator = new Validation();
		validator.setItem("نام کاربری", inputUserName, userNameRules);
		validator.setItem("ایمیل", inputEmail, emailRules);
		validator.setItem("نام", inputFirstName, firstNameRules);
		validator.setItem("نام خانوادگی", inputLastName, lastNameRules);
		validator.setItem("موبایل", inputMobile, mobileRules);
		// validator.setItem("مدیر بودن", inputIsAdmin, isAdminRules);

		if (!validator.isValid()) {
			status = "error";
			message = validator.getMessage();
		} else {
			String userName = inputUserName;
			String email = inputEmail;
			String firstName = inputFirstName;
			String lastName = inputLastName;
			String mobile = inputMobile;
			String isAdmin = inputIsAdmin;

			user.setUsername(userName);
			user.setEmail(email);
			user.setFirstName(firstName);
			user.setLastName(lastName);
			user.setMobile(mobile);

			if (currentUser.getIsAdmin() == 1) {
				if (isAdmin.equals("on")) {
					user.setIsAdmin((byte) 1);
				} else {
					user.setIsAdmin((byte) 0);
				}
			}

			try {
				user = (User) user.persist();
			} catch (Exception e) {
				status = "error";
				message = e.getMessage();
				e.printStackTrace();
			}

			if (status == "success") {
				message = " ویرایش با موفقیت انجام شد.";
			}
		}
		this.ajaxResponse(response, status, message);
		return null;
	}

}
