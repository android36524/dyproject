<%@ page import="org.apache.commons.lang.StringUtils" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="cn">
<head>
	<meta charset="utf-8" />
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<%@include file="/include-path.jsp"%>
	<%@include file="/common/include-bace.jsp"%>
	<link href="<%=path%>/admin/css/dy-admin-default.css" rel="stylesheet">
	<title>上级机构选择</title>
	<%
		String flag = StringUtils.defaultIfEmpty(request.getParameter("flag"), "");
		String code = StringUtils.defaultIfEmpty(request.getParameter("code"), "");
		String itSelf = StringUtils.defaultIfEmpty(request.getParameter("itSelf"),"");
		String idid = StringUtils.defaultIfEmpty(request.getParameter("idid"),"");
	%>
	<script type="text/javascript">
		var flag = "<%=flag%>";
		var code = "<%=code%>";
		var itSelf = "<%=itSelf%>";
		var idid = "<%=idid%>";
	</script>
</head>
<body>
	<table id="grid-table-1"></table>
    <div id="grid-pager-1"></div>
    <input type="hidden" id="selected">
	<script type="text/javascript" src="js/edb-manager.js"></script>
</body>
</html>