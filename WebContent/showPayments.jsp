<%@page import="com.hajihamedan.loan.helper.JalaliCalendar"%>
<%@include file="header.jsp"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="UTF-8"%>
<div class="row">
	<div class="large-12 columns">
		<h1>نمایش اقساط و سررسید ها</h1>
		<div id="response-message" class="row" style="display: none"></div>
		<table>
			<tr>
				<th>مشخصه</th>
				<th>وام</th>
				<th>مقدار</th>
				<th>تاریخ</th>
			</tr>
			<%
				Vector payments = (Vector) request.getAttribute("payments");
				Enumeration allPayments = payments.elements();
				while (allPayments.hasMoreElements()) {
					Payment payment = (Payment) allPayments.nextElement();
					Loan loan =Loan.loadById(payment.getLoanId());
					JalaliCalendar jl2 = new JalaliCalendar();
					String paymentDate = jl2.GregorianToPersian(payment.getPayDate());
					
			%>
			<tr>
				<td><%=payment.getPaymentId()%></td>
				<td><%= loan.getTitle() %></td>
				<td><%=payment.getAmount()%> تومان</td>
				<td><%=paymentDate%></td>
			</tr>
			<%
				}
			%>
		</table>
	</div>
</div>
<%@include file="footer.jsp"%>