<%@include file="header.jsp"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="UTF-8"%>

<div class="row">
	<div class="large-12 columns">
		<h1>مشکلی به وجود آمده است.</h1>
		<p>
			<%=request.getAttribute("message")%>
		</p>
	</div>
</div>


<%@include file="footer.jsp"%>