<%@page import="com.hajihamedan.loan.helper.JalaliCalendar"%>
<%@include file="header.jsp"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="UTF-8"%>
<div class="row">
	<div class="large-12 columns">
		<h1>نمایش کاربر</h1>
		<%
			User user = (User) request.getAttribute("user");
			byte isAdmin = user.getIsAdmin();

			String isAdminCheckBox = "";
			if (isAdmin == 1) {
				isAdminCheckBox = " checked ";
			}
		%>
		<p>شناسه کاربر: <%=user.getUserId()%></p>
		<p>نام کاربری: <%=user.getUsername()%></p>
		<p>ایمیل: <%=user.getEmail()%></p>
		<p>نام: <%=user.getFirstName()%></p>
		<p>نام خانوادگی: <%=user.getLastName()%></p>
		<p>موبایل: <%=user.getMobile()%></p>
		<p>مدبر بودن: <input type="checkbox" <%=isAdminCheckBox%>
			readonly="readonly" disabled="disabled"></p>

	</div>
</div>

<%@include file="footer.jsp"%>