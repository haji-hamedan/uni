<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="UTF-8"%>

<%@include file="header.jsp"%>

<div class="row">
	<div class="large-12 medium-12 columns">
		<h1>ثبت وام جدید</h1>
	</div>
</div>

<div id="response-message" class="row" style="display: none"></div>

<div class="row">
	<div class="large-12 medium-12 columns">
		<form action="<%=request.getContextPath()%>/Loans.submit" method="post">
			<div class="row">
				<div class="large-2 medium-2 small-4 columns">
					<label for="title">عنوان وام: </label>
				</div>
				<div class="large-5 medium-5 small-8 columns">
					<input id="title" name="title" type="text" />
				</div>
				<div class="columns"></div>
			</div>
		</form>
	</div>
</div>

<script>
$(document).ready(function(){
	var ajax_request = false;
	$('form').on("submit",function(e){
		e.preventDefault();
		
		var form = $(this);
		var action = form.attr('action');
		var response = $('#response-message');
		
		response.html('لطفاً منتظر بمانید...').fadeIn();
		if(ajax_request){
			ajax_request.abort();
		}
		ajax_request = $.ajax({
			url: action,
			dataType: 'json',
			type: 'post',
			data: form.serialize(),
			success:function(data){
				response.stop(true).fadeOut(function(){
					response.html(data.msg);
					response.fadeIn();
				});
			},
			error: function(){
				response.stop(true).fadeOut(function(){
					response.html("اشکالی در ثبت به وجود آمد، لطفاً مجدداً تلاش نمایید.");
					response.fadeIn();
				});
			}
			
		});
	});
});
</script>
<%@include file="footer.jsp"%>