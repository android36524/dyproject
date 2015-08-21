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
	<script type="text/javascript" src="<%=path%>/admin/js/common-admin.js"></script>
	<title>表单两列的demo</title>
	<style type="text/css">
		.modal-dialog{
			width: 1000px;
		}
	</style>
</head>
<body>
	<button class="btn btn-success" id="btn-addMenu">
		<i class="icon-pencil bigger-125"></i>
		添加菜单
	</button>
	
	<button class="btn btn-success" id="btn_org">
		<i class="icon-pencil bigger-125"></i>
		机构选择
	</button>
	<input type="text" id="txt_org" placeholder="组织机构和ID">
	
	
	<script type="text/javascript" src="js/form-2col.js"></script>
</body>
</html>