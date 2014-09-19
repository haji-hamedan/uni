<%@page import="com.sun.jmx.snmp.Timestamp"%>
<%@page import="com.hajihamedan.loan.helper.JalaliCalendar"%>
<%@page import="java.sql.ResultSet"%>
<%@include file="header.jsp"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="UTF-8"%>
<div class="row">
	<div class="large-12 columns">
		<h1>نمایش وام ها</h1>
		<table>
			<tr>
				<th>مشخصه</th>	
				<th>عنوان</th>	
				<th>مقدار</th>	
				<th>نرخ بهره</th>	
				<th>تعداد اقساط</th>	
				<th>بازپرداخت</th>	
				<th>اولین باز پرداخت</th>	
				<th>مشاهده</th>
				<th>ویرایش</th>
				<th>حذف</th>
			</tr>
			<%
			Vector loans = (Vector)request.getAttribute("loans");
			Enumeration allLoans = loans.elements();
			while(allLoans.hasMoreElements()) {
				Loan loan = (Loan)allLoans.nextElement();
				JalaliCalendar jl = new JalaliCalendar();
				String firstPaymentDate = jl.GregorianToPersian(loan.getFirstPaymentDate());
				%>
				<tr>
				 <td><%= loan.getLoanId() %></td>
				 <td><%= loan.getTitle() %></td>
				 <td><%= loan.getAmount() %></td>
				 <td><%= loan.getInterestRate() %></td>
				 <td><%= loan.getPaymentCount() %></td>
				 <td><%= loan.PAYMENTS[loan.getPaymentFrequency()] %></td>
				 <td><%= firstPaymentDate %></td>
				 <td><a href="<%= request.getContextPath() %>/Loans.show?loanId=<%= loan.getLoanId() %>" >مشاهده</a></td>
				 <td><a href="<%= request.getContextPath() %>/Loans.edit?loanId=<%= loan.getLoanId() %>" >ویرایش</a></td>
				 <td><a href="#" >حذف</a></td>
				</tr>
			<% } %>
		</table>
	</div>
</div>

<%@include file="footer.jsp"%>