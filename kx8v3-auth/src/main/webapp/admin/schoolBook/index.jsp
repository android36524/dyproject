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
	<link href="css/index.css" rel="stylesheet" type="text/css">
	<title>教材管理</title>
	<style type="text/css">
.modal-dialog {
	width: 1000px !important;
}
.phcolor{ color:#999;}

</style>
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
							<dytags:authBtn name="新增" code="add" id="btn-addschoolBook" btnClass="btn btn-success" lableClass="icon-pencil bigger-125" />
							<dytags:hasAuth />
						</p>
						<div class="form-group col-md-7" >
						   <label>&nbsp;&nbsp;教材名称:</label>
						   <input type="text" class="input" id="schoolBookName" name="schoolBookName" maxlength=10 />
						   <!-- <label>&nbsp;&nbsp;所属学阶:</label>
						   <select id="schoolBookVer_stage" name="schoolBookVer_stage" dyId="selectId1">
						   		<option value="">---请选择---</option>
						   </select> -->
						   <label>&nbsp;&nbsp;所属年级:</label>
						   <select id="schoolBookVer_Grade" name="schoolBookVer_Grade" dyId="selectId2">
						   		<option value="">---请选择---</option>
						   </select>
						   <label>&nbsp;&nbsp;科目:</label>
						   <select id="schoolBookVer_subject" name="schoolBookVer_subject" dyId="selectId3">
						   		<option value="">---请选择---</option>
						   </select>					
					       <input type="button" class="btn btn-primary dy-search-button" id="find_btn" value="查 询" />
				        </div>
					<!-- 课时列表 -->
					<div class="col-xs-12" style="overflow-x:hidden">
						<table id="schoolBook-table"></table>
						<div id="schoolBook-pager"></div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<%@include file="/admin/common/common-setting.jsp" %>
	<script type="text/javascript" src="js/index.js"></script>
</body>
</html>