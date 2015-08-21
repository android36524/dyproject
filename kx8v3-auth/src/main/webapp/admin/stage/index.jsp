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
	<title>学阶管理</title>
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
							<dytags:authBtn name="新增" code="add" id="btn-addStage" btnClass="btn btn-success" lableClass="icon-pencil bigger-125" />
							<dytags:hasAuth />
						</p>
						<div class="form-group col-md-4" >
						   <label>学阶名称：</label>
						   <input type="text" class="input chinese" id="stageName" maxlength="5" onkeyup="value=value.replace(/[^\u4e00-\u9fa5]/g,'')" onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^\u4e00-\u9fa5]/g,''))"/> 						
					       <input type="button" class="btn btn-primary dy-search-button" id="find_btn" value="查 询" /> 
				        </div>
					<!-- 课时列表 -->
					<div class="col-xs-12" style="overflow-x:hidden">
						<table id="stage-table"></table>
						<div id="stage-pager"></div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<%@include file="/admin/common/common-setting.jsp" %>
	<script type="text/javascript" src="js/index.js"></script>
</body>
</html>