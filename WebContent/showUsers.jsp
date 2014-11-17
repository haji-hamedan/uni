<%@include file="header.jsp"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="UTF-8"%>
<div class="row">
	<div class="large-12 columns">
		<h1>نمایش کاربران</h1>
		<div id="response-message" class="row" style="display: none"></div>
		<table>
			<tr>
				<th>مشخصه</th>
				<th>نام کاربری</th>
				<th>ایمیل</th>
				<th>نام</th>
				<th>نام خانوادگی</th>
				<th>موبایل</th>
				<th>مدیر بودن</th>
				<th>مشاهده</th>
				<th>ویرایش</th>
				<th>حذف</th>
			</tr>
			<%
				Vector users = (Vector) request.getAttribute("users");
				Enumeration allUsers = users.elements();
				while (allUsers.hasMoreElements()) {
					User user = (User) allUsers.nextElement();
					byte isAdmin = user.getIsAdmin();
					
					String isAdminCheckBox = "";
					if(isAdmin == 1){
						isAdminCheckBox = " checked ";
					}
			%>
			<tr data-user-id='<%=user.getUserId()%>'>
				<td><%=user.getUserId()%></td>
				<td><%=user.getUsername()%></td>
				<td><%=user.getEmail()%></td>
				<td><%=user.getFirstName()%></td>
				<td><%=user.getLastName()%></td>
				<td><%=user.getMobile()%></td>
				<td><input type="checkbox" <%= isAdminCheckBox %> readonly="readonly" disabled="disabled" ></td>
				<td><a
					href="<%=request.getContextPath()%>/users.show?userId=<%=user.getUserId()%>">مشاهده</a></td>
				<td><a
					href="<%=request.getContextPath()%>/users.edit?userId=<%=user.getUserId()%>">ویرایش</a></td>
				<td><button class='js-delete-user' type='button'>حذف</button></td>
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
			
		$('.js-delete-user').click(function() {
			var deleteConfirm = confirm("آیا مطمئن هستید؟");
			if (deleteConfirm == false) {
				return false;
			} 
			
			var tr = $(this).parent('td').parent('tr');
			var userId = tr.attr('data-user-id');
			
			response.html('لطفاً منتظر بمانید...').fadeIn();
			if (ajax_request) {
				ajax_request.abort();
			}
			
			ajax_request = $.ajax({
				url : "<%=request.getContextPath()%>/users.deleteUser",
				dataType : 'json',
				type : 'post',
				data : {
					userId : userId
				},
				success : function(data) {
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