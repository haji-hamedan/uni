<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="com.hajihamedan.loan.controller.*"%>
<%@page import="com.hajihamedan.loan.model.*"%>
<%@page import="java.util.*"%>

<html lang="fa">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">

<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/normalize.css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/foundation.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/loan.css">

<script src="<%=request.getContextPath()%>/js/jquery.js"></script>

<%
	if (request.getAttribute("pageTitle") == null) {
		request.setAttribute("pageTitle", "مدیریت اقساط و وام");
	}
%>
<title><%=request.getAttribute("pageTitle")%></title>

</head>
<body>
	<%
		User currentUser = (User) request.getAttribute("currentUser");
	%>
	<div class='row main-menu'>
		<div class="small-12 columns">
			<div class='main-menu-item'>
				<a href="<%=request.getContextPath()%>">خانه</a>
			</div>
			<%
				if (currentUser != null) {
			%>
			
			<%
				if (currentUser.getIsAdmin() == 1) {
			%>
			<div class='main-menu-item'>
				<a href="<%=request.getContextPath()%>/Loans.showAll">وام های
					ثبت شده در سیستم</a>
			</div>
			<div class='main-menu-item'>
				<a href="<%=request.getContextPath()%>/Payments.showAll">اقساط
					ثبت شده در سیستم</a>
			</div>
			<div class='main-menu-item'>
				<a href="<%=request.getContextPath()%>/Users.showAll">لیست
					کاربران</a>
			</div>
			<%
				} else {
			%>
			<div class='main-menu-item'>
				<a href="<%=request.getContextPath()%>/Loans.add">ثبت وام</a>
			</div>
			<div class='main-menu-item'>
				<a href="<%=request.getContextPath()%>/Loans.showAll">وام های من</a>
			</div>
			<div class='main-menu-item'>
				<a href="<%=request.getContextPath()%>/Payments.showNear">
				سررسید های نزدیک
				</a>
			</div>
			<div class='main-menu-item'>
				<a href="<%=request.getContextPath()%>/Payments.showAll">اقساط
					من</a>
			</div>
			<div class='main-menu-item'>
				<a href="<%=request.getContextPath()%>/Users.edit?userId=<%=request.getAttribute("currentUserId")%>">
					ویرایش اطلاعات کاربری</a>
			</div>
			<div class='main-menu-item'>
				<a href="<%=request.getContextPath()%>/Users.changePassword?userId=<%=request.getAttribute("currentUserId")%>">
					تغییر رمز عبور
				</a>
			</div>
			<%
				}
			%>
			<div class='main-menu-item'>
				<a href="<%=request.getContextPath()%>/Users.logout">خروج</a>
			</div>
			<%
				} else {
			%>
			<div class='main-menu-item'>
				<a href="<%=request.getContextPath()%>/Users.login">ورود</a>
			</div>
			<div class='main-menu-item'>
				<a href="<%=request.getContextPath()%>/Users.register">ثبت نام</a>
			</div>
			<%
				}
			%>
		</div>
	</div>