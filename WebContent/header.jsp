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
	<div data-magellan-expedition="fixed">
		<dl class="sub-nav">
			<dd>
				<a href="<%=request.getContextPath()%>">خانه</a>
			</dd>
			<dd>
				<a href="<%=request.getContextPath()%>/Loans.add">ثبت وام</a>
			</dd>
			<dd>
				<a href="<%=request.getContextPath()%>/Loans.showAll">مشاهده وام ها</a>
			</dd>
			<% if(currentUser == null){ %>
			<dd>
				<a href="<%=request.getContextPath()%>/Users.login">ورود</a>
			</dd>
			<dd>
				<a href="<%=request.getContextPath()%>/Users.register">ثبت نام</a>
			</dd>
			<% } else {%>
			<dd>
				<a href="<%=request.getContextPath()%>/Users.showAll">لیست کاربران</a>
			</dd>
			<dd>
				<a href="<%=request.getContextPath()%>/Users.logout">خروج</a>
			</dd>
			<% } %>
		</dl>
	</div>
