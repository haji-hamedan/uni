<%@page import="com.hajihamedan.loan.helper.numberDelimiter"%>
<%@page import="com.hajihamedan.loan.helper.JalaliCalendar"%>
<%@include file="header.jsp"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="UTF-8"%>
<div class="row">
	<div class="large-12 columns">
		<h1>نمایش اقساط و سررسید های نزدیک</h1>
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

		<h3>
			از تاریخ <span style="display: inline-block"><%=start%></span> تا
			تاریخ <span style="display: inline-block"><%=end%></span>
		</h3>

		<%
			int days = Integer
					.parseInt(request.getAttribute("days").toString());
		%>
		<div class="row">
			<div class="large-3 columns">
				نمایش تا <select id="show-interval">
					<option value="7" <%if (days == 7) {%> selected <%}%>>7	روز</option>
					<option value="10" <%if (days == 10) {%> selected <%}%>>10 روز</option>
					<option value="15" <%if (days == 15) {%> selected <%}%>>15 روز</option>
					<option value="30" <%if (days == 30) {%> selected <%}%>>1 ماه</option>
					<option value="91" <%if (days == 91) {%> selected <%}%>>3 ماه</option>
					<option value="182" <%if (days == 182) {%> selected <%}%>>6 ماه</option>
					<option value="365" <%if (days == 365) {%> selected <%}%>>1 سال</option>
				</select> آینده
			</div>
		</div>

		<div class="row">
			<div id="response-message"  class="small-12 columns" style="display: none"></div>
		</div>
		
		<table>
			<tr>
				<th>مشخصه</th>
				<th>وام</th>
				<th>مقدار</th>
				<th>تاریخ</th>
				<th>پرداخت شده</th>
			</tr>
			<%
				Vector payments = (Vector) request.getAttribute("payments");
				Enumeration allPayments = payments.elements();
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
				<td><%=loan.getTitle()%></td>
				<td><%=numberDelimiter.addDelimiter(payment.getAmount())%> تومان</td>
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
	$('#show-interval').on('change', function(){
		var value = $(this).val();
		window.location.href = "<%=request.getContextPath()%>/Payments.showNear?days="+ value;
	});

	var ajax_request = false;
	var response = $('#response-message');
		
	$('input[name="is-paid"]').change(function(){
		var isPaidInput = $(this);
		var isChecked = this.checked;
		var paymentId = isPaidInput.attr('data-id');
	
		response.html('لطفاً منتظر بمانید...').fadeIn();
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
				if(data.status == "success"){
					response.removeClass("error");
				} else {
					response.addClass("error");	
				}
				
				response.stop(true).fadeOut(function() {
					response.html(data.msg);
					response.fadeIn();
				});
				
				if (data.status == "success") {
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