<%@page import="com.hajihamedan.loan.helper.NumberDelimiter"%>
<%@page import="com.hajihamedan.loan.helper.JalaliCalendar"%>
<%@include file="header.jsp"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="UTF-8"%>
<div class="row">
	<div class="large-12 columns">
		<h1>نمایش وام ها</h1>
		
		<div class="row">
			<div id="response-message"  class="small-12 columns" style="display: none"></div>
		</div>
		
		<table>
			<tr>
				<th>ردیف</th>
				<th>عنوان</th>
				<th>مقدار</th>
				<th>نرخ بهره (درصد)</th>
				<th>تعداد اقساط</th>
				<th>بازپرداخت</th>
				<th>اولین باز پرداخت</th>
				<th>مشاهده</th>
				<th>ویرایش</th>
				<th>حذف</th>
			</tr>
			<%
				Vector loans = (Vector) request.getAttribute("loans");
					Enumeration allLoans = loans.elements();
					JalaliCalendar jl = new JalaliCalendar();
					int i = 0;
					while (allLoans.hasMoreElements()) {
						i++;
						Loan loan = (Loan) allLoans.nextElement();
						String firstPaymentDate = jl.GregorianToPersian(loan
								.getFirstPaymentDate());
						%>
			<tr data-loan-id='<%=loan.getLoanId() %>' >
				<td><%=i%></td>
				<td><%=loan.getTitle() %> </td>
				<td><%=NumberDelimiter.addDelimiter(loan.getAmount()) %></td>
				<td><%=loan.getInterestRate()%></td>
				<td><%=loan.getPaymentCount()%></td>
				<td><%=loan.PAYMENTS[loan.getPaymentFrequency()]%></td>
				<td><%=firstPaymentDate%></td>
				<td><a
					href="<%=request.getContextPath()%>/Loans.show?loanId=<%=loan.getLoanId()%>">مشاهده</a></td>
				<td><a
					href="<%=request.getContextPath()%>/Loans.edit?loanId=<%=loan.getLoanId()%>">ویرایش</a></td>
				<td><button class='js-delete-loan' type='button'>حذف</button></td>
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
			
		$('.js-delete-loan').click(function() {
			var deleteConfirm = confirm("آیا مطمئن هستید؟");
			if (deleteConfirm == false) {
				return false;
			} 
			
			var tr = $(this).parent('td').parent('tr');
			var loanId = tr.attr('data-loan-id');
			
			response.html('لطفاً منتظر بمانید...').fadeIn();
			if (ajax_request) {
				ajax_request.abort();
			}
			
			ajax_request = $.ajax({
				url : "<%=request.getContextPath()%>/Loans.deleteLoan",
				dataType : 'json',
				type : 'post',
				data : {
					loanId : loanId
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
						tr.slideUp('slow');
					}
				},
				error : function() {
					response.stop(true).fadeOut(function() {
						response.html("اشکالی در حذف به وجود آمد، لطفاً مجدداً تلاش نمایید.");
						response.fadeIn();
					});
				}

			});
		});
	});
</script>
<%@include file="footer.jsp"%>