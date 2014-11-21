<%@include file="header.jsp"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="UTF-8"%>
<div class="row">
	<div class="large-12 medium-12 small-12 columns">
		<h1>مدیریت اقساط و وام</h1>
		<p>به نرم افزار محاسبه اقساط و سررسید وام ها خوش آمدید.</p>

		<h4>امکانات این نرم افزار:</h4>
		<ol>
			<li>ایجاد حساب کاربری</li>
			<li>ثبت وام</li>
			<li>محاسبه اقساط و سررسید وام ها</li>
			<li>امکان حذف و ویرایش وام</li>
			<li>امکان مشاهده سررسید های نزدیک</li>
		</ol>

		<% User currenUser = (User) request.getAttribute("currentUser"); %>
		
		<% if (currentUser == null) { %>
		<p>
			<a href="<%=request.getContextPath()%>/Users.register"> <b>
					برای شروع ثبت نام کنید.</b></a>
		</p>
		<p>
			<a href="<%=request.getContextPath()%>/Users.login"> <b> یا
					وارد شوید.</b></a>
		</p>

		<% } else {	%>
		<p>
			<a href="<%=request.getContextPath()%>/Payments.showNear">
				<b>
					سررسید های نزدیک را ببینید.
				</b>
			</a>
		</p>
		<% } %>
	</div>
</div>

<%@include file="footer.jsp"%>