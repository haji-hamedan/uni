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