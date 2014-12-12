<%@page import="com.hajihamedan.loan.helper.NumberDelimiter"%>
<%@page import="com.hajihamedan.loan.helper.JalaliCalendar"%>
<%@include file="header.jsp"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="UTF-8"%>
<div class="row">
	<div class="large-12 medium-12 small-12 columns">

		<% User currenUser = (User) request.getAttribute("currentUser"); %>
		
		<% if (currentUser == null) { %>
		<h1>مدیریت اقساط و وام</h1>
		<p>به نرم افزار محاسبه اقساط و سررسید وام ها خوش آمدید.</p>
		
		<h4>امکانات این نرم افزار:</h4>
		<ol>
			<li>ایجاد حساب کاربری</li>
			<li>ثبت وام</li>
			<li>محاسبه اقساط و سررسید وام ها</li>
			<li>امکان حذف و ویرایش وام</li>
			<li>امکان مشاهده سررسید های نزدیک</li>
			<li>ارسال سررسیدهای نزدیک به وسیله ایمیل</li>
		</ol>
		
		<p>
			<a href="<%=request.getContextPath()%>/Users.register"> <b>
					برای شروع ثبت نام کنید.</b></a>
		</p>
		<p>
			<a href="<%=request.getContextPath()%>/Users.login"> <b> یا
					وارد شوید.</b></a>
		</p>

		<% } else {	%>
			<%
				long startDate = Long.parseLong(request.getAttribute("startDate")
						.toString());
				long endDate = Long.parseLong(request.getAttribute("endDate")
						.toString());
	
				JalaliCalendar jl1 = new JalaliCalendar();
				String start = jl1.GregorianToPersian(startDate);
	
				JalaliCalendar jl2 = new JalaliCalendar();
				String end = jl2.GregorianToPersian(endDate);
			%>
	
			<h2>
			  سرسید های نزدیک شما تا هفته ی دیگر 
			</h2>
				از تاریخ <span style="display: inline-block"><%=start%></span> تا
				تاریخ <span style="display: inline-block"><%=end%></span>
			  <hr>
			<%
				Vector payments = (Vector) request.getAttribute("payments");
				Enumeration allPayments = payments.elements();
				if(allPayments.hasMoreElements()){
			%>
			
			<table>
				<tr>
					<th>مشخصه</th>
					<th>وام</th>
					<th>مقدار</th>
					<th>تاریخ</th>
					<th>پرداخت شده</th>
				</tr>
				<%
					int i = 0;
					while (allPayments.hasMoreElements()) {
						i++;
						Payment payment = (Payment) allPayments.nextElement();
						Loan loan = Loan.loadById(payment.getLoanId());
						JalaliCalendar jl3 = new JalaliCalendar();
						String paymentDate = jl3.GregorianToPersian(payment.getPayDate());
						
						String isPaidChecked = "";
						String isPaidClass = "unpaid";
						if(payment.getIsPaid() == 1){
							isPaidChecked = "checked=''";
							isPaidClass = "paid";
						}
				%>
				<tr class="<%= isPaidClass %>">
					<td><%=i%></td>
					<td><%=loan.getTitle() %></td>
					<td><%=NumberDelimiter.addDelimiter(payment.getAmount()) %> </td>
					<td><%=paymentDate%></td>
					<td class='text-center'><input type="checkbox" name="is-paid" data-id="<%= payment.getPaymentId() %>" value="<%=payment.getIsPaid()%>" <%=isPaidChecked %> autocomplete="off"></td>
				</tr>
				<%
					}
				%>
			</table>
			<%
				} else {
			%>
				<p style='color: green; font-size: 1.2em;'>
					خیالتان راحت باشد، تا هفته ی بعد سررسیدی ندارید!
				</p>
			<%
				}
			%>
			<hr>
			<p>
				<a href="<%=request.getContextPath()%>/Payments.showNear?days=15">
					<b>
						دیگر سررسید های نزدیک را ببینید.
					</b>
				</a>
			</p>
		<% } %>
	</div>
</div>

<%@include file="footer.jsp"%>