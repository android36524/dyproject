<%@ page import="org.apache.commons.lang.StringUtils" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
	<%
		String classId = StringUtils.defaultIfEmpty(request.getParameter("classId"), "");
	%>
	<script type="text/javascript">
		var _classId = "<%=classId%>";
	</script>
<table id="grid-table-2" ></table>
<script type="text/javascript" src="js/classTeacherDetails.js"></script>
