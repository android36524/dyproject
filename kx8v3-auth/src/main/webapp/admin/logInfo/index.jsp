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
	<title>Excel日志文件管理</title>
</head>
<body>
<%@include file="/admin/common/common-navbar.jsp" %>
<div class="main-container container-fluid">
	<%@include file="/admin/common/common-leftmenu.jsp" %>
	<div class="main-content">
		<%@include file="../common/common-breadcrumbs.jsp" %>
		<div class="page-content">
			<div class="row">

				<div class="form-group col-md-4" >
					<label>文件名称：</label><input type="text" class="input" id="fileName" />
					<input type="button" class="btn btn-primary dy-search-button" id="find_btn" value="查 询" />
				</div>

				<div class="col-xs-12">
					<table id="excel-table"></table>
					<div id="excel-pager"></div>
				</div>
			</div>
		</div>
	</div>
</div>
<%@include file="/admin/common/common-setting.jsp" %>
<script type="text/javascript" src="js/index.js"></script>
</body>
</html>