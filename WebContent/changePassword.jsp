<%@page import="com.hajihamedan.loan.helper.JalaliCalendar"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="UTF-8"%>

<%@include file="header.jsp"%>

<div class="row">
	<div class="large-12 medium-12 columns">
		<h1>تغییر رمز عبور</h1>
	</div>
</div>

<%
	User user = (User) request.getAttribute("user");
%>

<div class="row">
	<div id="response-message"  class="small-12 columns" style="display: none"></div>
</div>

<div class="row">
	<div class="large-12 medium-12 columns">
		<form action="<%=request.getContextPath()%>/Users.submitChangePassword?userId=<%=user.getUserId()%>"
			method="post" autocomplete="off">
			
			<div class="row">
				<div class="large-2 medium-2 small-4 columns">
					<label for="old-password">رمز عبور فعلی</label>
				</div>
				<div class="large-5 medium-5 small-8 columns">
					<input id="old-password" name="old-password" type="password" autofocus="autofocus" value="" />
				</div>
				<div class="columns"></div>
			</div>
			
			<div class="row">
				<div class="large-2 medium-2 small-4 columns">
					<label for="new-password">رمز عبور جدید</label>
				</div>
				<div class="large-5 medium-5 small-8 columns">
					<input id="new-password" name="new-password" type="password" value="" />
				</div>
				<div class="columns"></div>
			</div>
			
			<div class="row">
				<div class="large-2 medium-2 small-4 columns">
					<label for="confirm-new-password">تکرار رمز عبور جدید</label>
				</div>
				<div class="large-5 medium-5 small-8 columns">
					<input id="confirm-new-password" name="confirm-new-password" type="password" value="" />
				</div>
				<div class="columns"></div>
			</div>
			
			<div class="row">
				<div
					class="large-5 large-offset-2 medium-5 medium-offset-2 small-8 small-offset-4 columns">
					<input id="submit" class="button" type="submit" value="تغییر رمز عبور" />
				</div>
				<div class="columns"></div>
			</div>
		</form>
	</div>
</div>

<script>
	$(document).ready(function() {
		var ajax_request = false;
		$('form').on("submit",function(e) {
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
							window.setTimeout(function(){
								window.location.reload();
							}, 2000);
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