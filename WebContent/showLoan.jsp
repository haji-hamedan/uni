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
			<p>مقدار وام: <%=	NumberDelimiter.addDelimiter(loan.getAmount())%> تومان</p>
			<p>تعداد اقساط: <%=loan.getPaymentCount() %></p>
			<p>دوره اقساط: <%= Loan.getPaymentFrequencyName(loan.getPaymentFrequency()) %></p>
			<p>اولین قسط:<%= firstPaymentDate %></p>
			
			<table>
				<tr>
					<th>ردیف</th>
					<th>مقدار</th>
					<th>تاریخ</th>
					<th>پرداخت شده</th>
				</tr><%
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
%>
			<tr class="<%= isPaidClass %>" >
					<td><%= i %></td>
					<td><%=NumberDelimiter.addDelimiter(payment.getAmount()) %>تومان </td>
					<td><%=paymentDate%></td>
					<td class='text-center'><input type="checkbox" name="is-paid" data-id="<%= payment.getPaymentId() %>" value="<%=payment.getIsPaid()%>" <%=isPaidChecked %> autocomplete="off"></td>
				</tr>
			<%
				}
			%>
			</table>
	</div>
</div>

<script>
$(document).ready(function() {
	var ajax_request = false;
	var response = $('#response-message');
		
	$('input[name="is-paid"]').change(function(){
		var isPaidInput = $(this);
		var isChecked = this.checked;
		var paymentId = isPaidInput.attr('data-id');
	
		if (ajax_request) {
			ajax_request.abort();
		}
		
		ajax_request = $.ajax({
			url : "<%=request.getContextPath()%>/Payments.paidChange",
			dataType : 'json',
			type : 'post',
			data : {
				paymentId : paymentId,
				isChecked: isChecked
			},
			success : function(data) {
				if(data.status != "success"){
					response.addClass("error");	
					response.stop(true).fadeOut(function() {
						response.html(data.msg);
						response.fadeIn();
					});
				} else {
					response.removeClass("error");
					response.stop(true).fadeOut();
					if(isChecked === true){
						isPaidInput.parent('td').parent('tr').removeClass("unpaid").addClass("paid");
					} else {
						isPaidInput.parent('td').parent('tr').removeClass("paid").addClass("unpaid");
					}
				}
			},
			error : function() {
				response.stop(true).fadeOut(function() {
					response.html("اشکالی  به وجود آمد، لطفاً مجدداً تلاش نمایید.");
					response.fadeIn();
				});
			}

		});
	});
});
</script>
<%@include file="footer.jsp"%>