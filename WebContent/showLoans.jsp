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
				<th>مشاهده</th>
				<th>ویرایش</th>
				<th>حذف</th>
			</tr>
			<%
			Vector loans = (Vector)request.getAttribute("loans");
			Enumeration allLoans = loans.elements();
			while(allLoans.hasMoreElements()) {
				Loan loan = (Loan)allLoans.nextElement();
				%>
				<tr>
				 <td><%= loan.getLoanId() %></td>
				 <td><%= loan.getTitle() %></td>
				 <td><%= loan.getAmount() %></td>
				 <td><%= loan.getInterestRate() %></td>
				 <td><%= loan.getPaymentCount() %></td>
				 <td><%= loan.getPaymentFrequency() %></td>
				 <td><a href="<%= request.getContextPath() %>/Loans.show?loanId=<%= loan.getLoanId() %>" >مشاهده</a></td>
				 <td><a href="<%= request.getContextPath() %>/Loans.edit?loanId=<%= loan.getLoanId() %>" >ویرایش</a></td>
				 <td><a href="#" >حذف</a></td>
				</tr>
			<% } %>
		</table>
	</div>
</div>

<%@include file="footer.jsp"%>