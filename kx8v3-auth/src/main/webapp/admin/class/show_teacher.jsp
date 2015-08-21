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
<%
	String schId = request.getParameter("schId");
%>
<script type="text/javascript">
	var _schId = "<%=schId%>";
</script>
<title>教师选择</title>
</head>
<body>
	<div style="margin-top: 10px;">
		<div class="form-group col-md-6">
			<label></label> <select id="search_id" name="searchBy"
				style="width: 150px;">
				<option value="">-请选择-</option>
				<option value="1">教师姓名</option>
				<option value="2">教师工号</option>
				<option value="3">手机号码</option>
			</select> <input type="text" class="input" id="searchText" /> <label>部门名称</label>
			<select id="dept_id" name="deptId" style="width: 150px;">
				<option value="">-请选择-</option>
			</select> <input type="button" class="btn btn-primary dy-search-button"
				id="find_btn" value="查 询" />
		</div>
	</div>
	<table id="empe-tab"></table>
	<div id="empe-pager"></div>
	<input type="hidden" id="selected">
	<script type="text/javascript" src="js/show_teacher.js"></script>
</body>
</html>