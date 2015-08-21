<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="cn">
<head>
	<meta charset="utf-8" />
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<%@include file="/include-path.jsp"%>
	<%@include file="/common/include-bace.jsp"%>
	<link rel="stylesheet" href="<%=rootPath%>/public/bootstrap/ace/css/chosen.css" />
	<script src="<%=__BacePath%>/public/bootstrap/ace/js/chosen.jquery.min.js"></script>
	
	<link href="<%=path%>/admin/css/dy-admin-default.css" rel="stylesheet">
	<title>年级管理</title>
</head>
<body>
	<%@include file="/admin/common/common-navbar.jsp" %>
	<div class="main-container container-fluid">
		<%@include file="/admin/common/common-leftmenu.jsp" %>
		<div class="main-content">
			<%@include file="../common/common-breadcrumbs.jsp" %>
			<div class="page-content">
				<div class="row">
						<p>
						<dytags:authBtn name="新增" code="add" id="btn-addgrade" btnClass="btn btn-success" lableClass="icon-pencil bigger-125" />
						<dytags:hasAuth />
						</p>
						<div class="form-group col-md-4" >
						   <label>年级名称：</label><input type="text" class="input" id="gradeName" maxlength="8" onkeyup="value=value.replace(/[^\u4e00-\u9fa5]/g,'')" onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^\u4e00-\u9fa5]/g,''))"/> 						
					       <input type="button" class="btn btn-primary dy-search-button" id="find_btn" value="查 询" /> 
				        </div>
					<!-- 课时列表 -->
					<div class="col-xs-12" >
						<table id="grade-table"></table>
						<div id="grade-pager"></div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<%@include file="/admin/common/common-setting.jsp" %>
	<script type="text/javascript">
		var _flag=${flag};
		var _schId = 0;
	</script>
	<script type="text/javascript" src="js/index.js"></script>
</body>
</html>