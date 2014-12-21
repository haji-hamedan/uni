<%@page import="com.hajihamedan.loan.helper.JalaliCalendar"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="UTF-8"%>

<%@include file="header.jsp"%>

<div class="row">
	<div class="large-12 medium-12 columns">
		<h1>ویرایش وام</h1>
	</div>
</div>

<%
	Loan loan = (Loan) request.getAttribute("loan");
	JalaliCalendar jl = new JalaliCalendar();
	String firstPaymentDate = jl.GregorianToPersian(loan
			.getFirstPaymentDate());
%>


<div class="row">
	<div id="response-message"  class="small-12 columns" style="display: none"></div>
</div>

<div class="row">
	<div class="large-12 medium-12 columns">
		<form
			action="<%=request.getContextPath()%>/Loans.submitEdit?loanId=<%=loan.getLoanId()%>"
			method="post" autocomplete="off">
			<div class="row">
				<div class="large-2 medium-2 small-4 columns">
					<label for="title" class="required">عنوان وام</label>
				</div>
				<div class="large-5 medium-5 small-8 columns">
					<input id="title" name="title" type="text" autofocus="autofocus"
						value="<%=loan.getTitle()%>" />
				</div>
				<div class="columns"></div>
			</div>
			<div class="row">
				<div class="large-2 medium-2 small-4 columns">
					<label for="amount" class="required">مقدار وام</label>
				</div>
				<div class="large-5 medium-5 small-8 columns">
					<div class="row collapse">
						<div class="large-9 medium-9 small-9 columns">
							<input id="amount" name="amount" type="text"
								value="<%=loan.getAmount()%>" />
						</div>
						<div class="large-3 medium-3 small-3 columns">
							<span class="postfix">تومان</span>
						</div>
						<div class="columns"></div>
					</div>
				</div>
				<div class="columns"></div>
			</div>
			<div class="row">
				<div class="large-2 medium-2 small-4 columns">
					<label for="interestRate" class="required">نرخ بهره</label>
				</div>
				<div class="large-5 medium-5 small-8 columns">
					<div class="row collapse">
						<div class="large-9 medium-9 small-9 columns">
							<input id="interestRate" name="interestRate" type="text"
								value="<%=loan.getInterestRate()%>" />
						</div>
						<div class="large-3 medium-3 small-3 columns">
							<span class="postfix">%</span>
						</div>
					</div>
				</div>
				<div class="columns"></div>
			</div>
			<div class="row">
				<div class="large-2 medium-2 small-4 columns">
					<label for="paymentCount" class="required">تعداد اقساط</label>
				</div>
				<div class="large-5 medium-5 small-8 columns">
					<input id="paymentCount" name="paymentCount" type="text"
						value="<%=loan.getPaymentCount()%>" />
				</div>
				<div class="columns"></div>
			</div>
			<div class="row">
				<div class="large-2 medium-2 small-4 columns">
					<label for="paymentFrequency" class="required">بازه پرداخت اقساط</label>
				</div>
				<div class="large-5 medium-5 small-8 columns">
					<select id="paymentFrequency" name="paymentFrequency">
						<option value="<%=PaymentFrequency.MONTHLY_PAYMENT%>"
							<%if (loan.getPaymentFrequency() == PaymentFrequency.MONTHLY_PAYMENT) {%>
							selected="selected" <%}%>><%=PaymentFrequency
					.getPaymentName(PaymentFrequency.MONTHLY_PAYMENT)%></option>
						<option value="<%=PaymentFrequency.YEARLY_PAYMENT%>"
							<%if (loan.getPaymentFrequency() == PaymentFrequency.YEARLY_PAYMENT) {%>
							selected="selected" <%}%>><%=PaymentFrequency
					.getPaymentName(PaymentFrequency.YEARLY_PAYMENT)%></option>
					</select>
				</div>
				<div class="columns"></div>
			</div>
			<div class="row">
				<div class="large-2 medium-2 small-4 columns">
					<label for="firstPaymentDate" class="required">اولین سررسید</label>
				</div>
				<div class="large-5 medium-5 small-8 columns">
					<input id="firstPaymentDate" name="firstPaymentDate" type="text"
						readonly="readonly" value="<%=firstPaymentDate%>"
						onclick="displayDatePicker('firstPaymentDate', this);" />
				</div>
				<div class="columns"></div>
			</div>
			<div class="row">
				<div
					class="large-5 large-offset-2 medium-5 medium-offset-2 small-8 small-offset-4 columns">
					<input id="submit" class="button" type="submit" value="ثبت" />
				</div>
				<div class="columns"></div>
			</div>
		</form>
	</div>
</div>

<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/calendar.css">
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/calendar.js"></script>
<script>
	$(document).ready(function() {
		var ajax_request = false;
		$('form').on("submit", function(e) {
			e.preventDefault();

			var form = $(this);
			var action = form.attr('action');
			var response = $('#response-message');

			response.html('لطفاً منتظر بمانید...').fadeIn();
			if (ajax_request) {
				ajax_request.abort();
			}
			ajax_request = $.ajax({
				url : action,
				dataType : 'json',
				type : 'post',
				data : form.serialize(),
				success : function(data) {
					if(data.status == "success"){
						response.removeClass("error");
					} else {
						response.addClass("error");	
					}
					
					response.stop(true).fadeOut(function() {
						response.html(data.msg);
						response.fadeIn(function() {
							if (data.status == 'success') {
								window.location.reload();
							}
						});
					});
				},
				error : function() {
					response.stop(true).fadeOut(function() {
						response.html("اشکالی در ثبت به وجود آمد، لطفاً مجدداً تلاش نمایید.");
						response.fadeIn();
					});
				}

			});
		});
	});
</script>
<%@include file="footer.jsp"%>