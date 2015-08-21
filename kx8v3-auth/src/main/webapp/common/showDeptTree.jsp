<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@page import="org.apache.commons.lang.StringUtils"%>
<%
  String orgId = StringUtils.defaultIfEmpty(request.getParameter("orgId"), "");
  String deptId = StringUtils.defaultIfEmpty(request.getParameter("deptId"), "");
%>
<!DOCTYPE html>
<html lang="cn">
<head>
  <meta charset="utf-8" />
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <%@include file="/include-path.jsp"%>
  <%@include file="/common/include-bace.jsp"%>
  <link href="<%=path%>/admin/css/dy-admin-default.css" rel="stylesheet">
  <title>教育部门选择管理</title>
</head>
<body>
<table id="empe-tab"></table>
<div id="empe-pager"></div>
<input type="hidden" id="selected">
<input type="hidden" class="input" id="orgId" name="orgId" value="<%= orgId%>" />
<input type="hidden" class="input" id="deptId" name="deptId" value="<%= deptId%>" />
<script type="text/javascript" src="js/showDeptTree.js"></script>
</body>
</html>