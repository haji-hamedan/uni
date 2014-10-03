<%@page import="com.hajihamedan.loan.helper.JalaliCalendar"%>
<%@include file="header.jsp"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="UTF-8"%>
<div class="row">
	<div class="large-12 columns">
		<h1>نمایش وام</h1>
			<%
				Loan loan = (Loan) request.getAttribute("loan");
				JalaliCalendar jl = new JalaliCalendar();
				String firstPaymentDate = jl.GregorianToPersian(loan.getFirstPaymentDate());
			%>
			<p>مشخصه وام: <%=loan.getLoanId()%></p>
			<p>عنوان وام: <%=loan.getTitle()%></p>
			<p>مقدار وام: <%=loan.getAmount()%></p>
			<p>تعداد اقساط: <%= loan.getPaymentCount() %></p>
			<p>دوره اقساط: <%= Loan.getPaymentFrequencyName(loan.getPaymentFrequency()) %></p>
			<p>اولین قسط: <%= firstPaymentDate %></p>
			
			<table>
				<tr>
					<th>مشخصه</th>
					<th>مقدار</th>
					<th>تاریخ</th>
				</tr>
			<%
				Vector payments = (Vector) loan.getPayments();
				Enumeration allPayments = payments.elements();
				while (allPayments.hasMoreElements()) {
					Payment payment = (Payment) allPayments.nextElement();
					JalaliCalendar jl2 = new JalaliCalendar();
					String paymentDate = jl2.GregorianToPersian(payment.getPayDate());
			%>
				<tr>
					<td><%=payment.getPaymentId()%></td>
					<td><%=payment.getAmount()%></td>
					<td><%=paymentDate%></td>
				</tr>
			<%
				}
			%>
			</table>
	</div>
</div>

<%@include file="footer.jsp"%>