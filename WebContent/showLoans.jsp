<%@page import="java.sql.ResultSet"%>
<%@include file="header.jsp"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="UTF-8"%>
<div class="row">
	<div class="large-12 columns">
		<h1>نمایش وام ها</h1>
		<p>
		<%
		Vector loans = (Vector)request.getAttribute("loans");
		Enumeration allLoans = loans.elements();
		while(allLoans.hasMoreElements()) {
			Loan loan = (Loan)allLoans.nextElement();
			%>
			 <%= loan.getIdProperty() %>
			 <%= loan.getTitle() %>
			 <br>
		<% } %>
		</p>
	</div>
</div>

<%@include file="footer.jsp"%>