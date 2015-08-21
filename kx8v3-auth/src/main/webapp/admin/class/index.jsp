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
	
	<script src="<%=__BacePath%>/public/lib/jquery.layout/jquery.layout-1.4.js"></script>
	<link rel="stylesheet" href="<%=rootPath%>/public/lib/jquery.layout/jquery.layout-1.4.css" />
	
	<link href="<%=path%>/admin/css/dy-admin-default.css" rel="stylesheet">
	<title>学校班级管理</title>
	<style type="text/css">
		.modal-dialog{
			width: 900px;
		}
		
	</style>
	<script type="text/javascript">
	$(document).ready(function () {
		$('#div_main').layout({ 
			applyDefaultStyles: true,
			west : {
				header : "组织机构树",
				size : 260
			}
		});
	});
	var _schId= "";
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
					<div class="ui-layout-west jl_pane jl_ui-layout-pane" style="border: 1px solid #ccc;"> 
						<%@include file="/admin/common/orgTree/index.jsp" %>
					</div>
					<div class="ui-layout-center jl_ui-layout-pane">
						<div>
							<p>
							 <dytags:authBtn name="新增" code="add" id="btn-addClass" btnClass="btn btn-success" lableClass="icon-pencil bigger-125" />
							 <dytags:hasAuth />
							</p>
							<form name="form-search" id="form-search">
							<div class="form-group col-md-6" >
							   <label>年级别名：</label>
							   <select id="gradeId" style="width: 150px;">
							   		<option value="">-请选择-</option>
							   		<c:forEach var="grade" items="${gradeList }">
							   			<option value="${grade.id}">${grade.name}</option>
							   		</c:forEach>
							   </select>						
						       
						       <label>班级名称</label>
						       <input type="text" class="input" id="className" name="className" maxlength=20  />
						       
						       <input type="button" class="btn btn-primary dy-search-button" 
						       id="find_btn" value="查 询" /> 
						       
					        </div>
					        </form>
							<!-- 班级列表 -->
							<div class="col-xs-12" style="padding-left: 0px !important; padding-right: 0px !important;">
								<table id="class-table"></table>
								<div id="class-pager"></div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<%@include file="/admin/common/common-setting.jsp" %>
	<script type="text/javascript" src="js/index.js"></script>
</body>
</html>