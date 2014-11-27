<%@page import="com.hajihamedan.loan.helper.*"%>
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
			<p>مقدار وام: <%=NumberDelimiter.addDelimiter(loan.getAmount())%> تومان</p>
			<p>تعداد اقساط: <%=loan.getPaymentCount()) %></p>
			<p>دوره اقساط: <Loan.getPaymentFrequencyName(loan.getPaymentFrequency())()) %></p>
			<p>اولین قسط:firstPaymentDatetDate %></p>
			
			<table>
				<tr>
					<th>ردیف</th>
					<th>مقدار</th>
					<th>تاریخ</th>
					<th>پرداخت شده</th>
				</tr>
				Vector payments = (Vector) loan.getPayments();
					Enumeration allPayments = payments.elements();
					int i = 0;
					while (allPayments.hasMoreElements()) {
						i++;
						Payment payment = (Payment) allPayments.nextElement();
						JalaliCalendar jl2 = new JalaliCalendar();
						String paymentDate = jl2.GregorianToPersian(payment.getPayDate());
						
						String isPaidChecked = "";
						String isPaidClass = "unpaid";
						if(payment.getIsPaid() == 1){
							isPaidChecked = "checked=''";
							isPaidClass = "paid";
						}
			<tr class="<%= isPaidCisPaidClass					<td><%= i %><itd>
					<td><%=numbNumberDelimiter.addDelimiter(payment.getAmount())ومان</td>
					<td><%=paymentDate%></td>
					<td class='text-center'><input type="checkbox" name="is-paid" data-id="<%= payment.getPaymentId() %>" value="<%=payment.getIsPaid()%>" <%=isPaidChecked %> autocomplete="off" disabled="disabled"></td>
				</tr>
			<%
				}
			%>
			</table>
	</div>
</div>

<%@include file="footer.jsp"%>