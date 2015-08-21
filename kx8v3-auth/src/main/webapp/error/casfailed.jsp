<%@page import="org.springframework.security.core.AuthenticationException"%>
<%@page import="org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/include-core.jsp" %>
<title>CAS登录失败</title>
</head>
<body>
	<h3>CAS登录失败</h3>
	<br/>
	<%
     Exception error = ((AuthenticationException) session.getAttribute(AbstractAuthenticationProcessingFilter.SPRING_SECURITY_LAST_EXCEPTION_KEY));
	     if(error != null) {
	 %>
	 <%= error.getMessage() %>
	 <%
	 }
	 %>
</body>
</html>