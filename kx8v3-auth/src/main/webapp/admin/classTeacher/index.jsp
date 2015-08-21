<%@ page import="com.dayang.commons.enums.OrgFlag" %>
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
	<link href="<%=path%>/admin/css/dy-admin-default.css" rel="stylesheet">
	<script src="<%=__BacePath%>/public/lib/jquery.layout/jquery.layout-1.4.js"></script>
	<link rel="stylesheet" href="<%=rootPath%>/public/lib/jquery.layout/jquery.layout-1.4.css" />
	<title>菜单管理</title>
	<%
		String schType = OrgFlag.SCHOOLFLAG.getValueStr();
	%>
	<script type="text/javascript">
		var _schType = "<%=schType%>"
	</script>
</head>
<body>
	<%@include file="/admin/common/common-navbar.jsp" %>
	<div class="main-container container-fluid">
		<%@include file="/admin/common/common-leftmenu.jsp" %>
		<div class="main-content">
			<%@include file="../common/common-breadcrumbs.jsp" %>
			<div class="page-content">
				<div style="width: 100%;height: 690px;" id="div_main">
					<dytags:hasAuth />
					<div class="ui-layout-west jl_pane jl_ui-layout-pane" style="border: 1px solid #ccc;">
						<%@include file="/admin/common/orgTree/index.jsp" %>
					</div>
					<!-- 班级列表 -->
					<div class="ui-layout-center jl_ui-layout-pane">
						<p>
							<div class="form-group col-md-12" >
								<label>当前学期：</label>
								<select name="currentSemester" id="currentSemester" disabled="disabled">
								</select>
								<label>年级：</label>
								<select name="grade" id="grade">
									<option value="">全部</option>
								</select>
								<label>班级：</label>
								<select name="class" id="class">
									<option value="">全部</option>
								</select>
								<input type="button" class="btn btn-primary dy-search-button" id="find_btn" value="查 询" />
							</div>
						</p>
						<div class="col-xs-12">
							<table id="grid-table"></table>
							<div id="grid-pager"></div>
						</div>
					</div>
				</div>
			</div>
		<%@include file="../common/common-setting.jsp" %>
		</div>
	</div>
	<script type="text/javascript" src="js/index.js"></script>
</body>
</html>