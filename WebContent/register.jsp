<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="UTF-8"%>

<%@include file="header.jsp"%>

<div class="row">
	<div class="large-12 medium-12 columns">
		<h1>ثبت نام</h1>
	</div>
</div>

<div id="response-message" class="row" style="display: none"></div>

<div class="row">
	<div class="large-12 medium-12 columns">
		<form action="<%=request.getContextPath()%>/Users.submit"
			method="post">
			<div class="row">
				<div class="large-2 medium-2 small-4 columns">
					<label for="username">نام کاربری</label>
				</div>
				<div class="large-5 medium-5 small-8 columns">
					<input id="username" name="username" type="text" autofocus="autofocus" />
				</div>
				<div class="columns"></div>
			</div>
			<div class="row">
				<div class="large-2 medium-2 small-4 columns">
					<label for="email">ایمیل</label>
				</div>
				<div class="large-5 medium-5 small-8 columns">
					<input id="email" name="email" type="text" />
				</div>
				<div class="columns"></div>
			</div>
			<div class="row">
				<div class="large-2 medium-2 small-4 columns">
					<label for="password">رمز عبور</label>
				</div>
				<div class="large-5 medium-5 small-8 columns">
					<input id="password" name="password" type="password" />
				</div>
				<div class="columns"></div>
			</div>
			<div class="row">
				<div class="large-2 medium-2 small-4 columns">
					<label for="re_password">تکرار رمز عبور</label>
				</div>
				<div class="large-5 medium-5 small-8 columns">
					<input id="re_password" name="re_password" type="password"/>
				</div>
				<div class="columns"></div>
			</div>
			<div class="row">
				<div class="large-2 medium-2 small-4 columns">
					<label for="first_name">نام</label>
				</div>
				<div class="large-5 medium-5 small-8 columns">
					<input id="first_name" name="first_name" type="text" />
				</div>
				<div class="columns"></div>
			</div>
			<div class="row">
				<div class="large-2 medium-2 small-4 columns">
					<label for="last_name">نام خانوادگی</label>
				</div>
				<div class="large-5 medium-5 small-8 columns">
					<input id="last_name" name="last_name" type="text" />
				</div>
				<div class="columns"></div>
			</div>
			<div class="row">
				<div class="large-2 medium-2 small-4 columns">
					<label for="mobile">موبایل</label>
				</div>
				<div class="large-5 medium-5 small-8 columns">
					<input id="mobile" name="mobile" type="text" />
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
					response.stop(true).fadeOut(function() {
						response.html(data.msg);
						response.fadeIn(function(){
							if(data.status == 'success'){
								setTimeout(function(){
									window.location.href = '<%=request.getContextPath()%>';
								}, 1000);
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