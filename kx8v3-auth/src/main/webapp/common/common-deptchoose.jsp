<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@page import="org.apache.commons.lang.StringUtils"%>
<%@include file="/include-path.jsp"%>
<%
  String orgId = StringUtils.defaultIfEmpty(request.getParameter("orgId"), "");
  String deptId = StringUtils.defaultIfEmpty(request.getParameter("deptId"), "");
%>
<script type="text/javascript">
	var __common_dept_orgId = "<%=orgId%>";
	var __common_dept_deptId = "<%=deptId%>";
</script>
<table id="common_dept_grid" style="width: 100%;height: 100%;"></table>
<div id="common_dept_grid_pager"></div>
<script type="text/javascript" src="<%=path %>/common/js/common-deptchoose.js"></script>
