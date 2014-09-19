<%@page import="java.sql.ResultSet"%>
<%@include file="header.jsp"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="UTF-8"%>
<div class="row">
	<div class="large-12 columns">
		<h1>نمایش وام ها</h1>
		<p>
		<%
		Loan loan = (Loan)request.getAttribute("loan");
			%>
			 <%= loan.getLoanId() %>
			 <%= loan.getTitle() %>
			 <%= loan.getAmount() %>
			 <%
			 Vector payments = (Vector)loan.getPayments();
			 Enumeration allPayments = payments.elements();
				while(allPayments.hasMoreElements()) {
					Payment payment = (Payment)allPayments.nextElement();
					%>
					 <%= payment.getPaymentId() %>
					 <%= payment.getLoanId() %>
					 <%= payment.getAmount() %>
			 	<% } %>
			 <br>
		</p>
	</div>
</div>

<%@include file="footer.jsp"%>