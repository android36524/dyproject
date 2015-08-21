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
	<title>字典管理</title>
</head>
<body>
	<%@include file="/admin/common/common-navbar.jsp" %>
	<div class="main-container container-fluid">
		<%@include file="/admin/common/common-leftmenu.jsp" %>
		<div class="main-content">
			<%@include file="/admin/common/common-breadcrumbs.jsp" %>
			<div class="page-content">
				<div class="row">
					<!-- 菜单列表 -->
					<div class="col-xs-12">
						<p>
							<button class="btn btn-success" id="btn-addDictionarytype">
								<i class="icon-pencil bigger-125"></i>
								添加字典类型
							</button>
							
						</p>
					</div>
					
					<div class="col-xs-12">
						<table id="grid-table"></table>
						<div id="grid-pager"></div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<%@include file="/admin/common/common-setting.jsp" %>
	<script type="text/javascript" src="js/dictionary.js"></script>
</body>
</html>