<%@page import="com.hajihamedan.loan.helper.JalaliCalendar"%>
<%@include file="header.jsp"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="UTF-8"%>
<div class="row">
	<div class="large-12 columns">
		<h1>نمایش اقساط و سررسید های نزدیک</h1>
		<%
			long startDate = Long.parseLong(request.getAttribute("startDate").toString());
			long endDate = Long.parseLong(request.getAttribute("endDate").toString());

			JalaliCalendar jl1 = new JalaliCalendar();
			String start = jl1.GregorianToPersian(startDate);

			JalaliCalendar jl2 = new JalaliCalendar();
			String end = jl2.GregorianToPersian(endDate);
		%>

		<h3>
			از تاریخ <span style="display: inline-block"><%=start%></span> تا
			تاریخ <span style="display: inline-block"><%=end%></span>
		</h3>

		<div class="row">
			<div class="large-3 columns">
				نمایش تا  <select id="show-interval">
					<option value="7">7 روز</option>
					<option value="10">10 روز</option>
					<option value="15">15 روز</option>
					<option value="30">1 روز</option>
					<option value="91">3 ماه</option>
					<option value="182">6 ماه</option>
					<option value="365">1 سال</option>
				</select> آینده
			</div>
		</div>

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
					Loan loan = Loan.loadById(payment.getLoanId());
					JalaliCalendar jl3 = new JalaliCalendar();
					String paymentDate = jl3.GregorianToPersian(payment.getPayDate());
			%>
			<tr>
				<td><%=payment.getPaymentId()%></td>
				<td><%=loan.getTitle()%></td>
				<td><%=payment.getAmount()%> تومان</td>
				<td><%=paymentDate%></td>
			</tr>
			<%
				}
			%>
		</table>
	</div>
</div>

<script>
$('#show-interval').on('change', function(){
	var value = $(this).val();
	window.location.href = "<%= request.getContextPath() %>/Payments.showNear?days="+value;
});
</script>
<%@include file="footer.jsp"%>