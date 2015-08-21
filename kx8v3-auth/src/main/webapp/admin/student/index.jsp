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
	<title>学生管理</title>
	<style type="text/css">
		.modal-dialog{
			width: 900px !important;
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
							 <dytags:authBtn name="新增" code="add" id="btn-addStudent" btnClass="btn btn-success" lableClass="icon-pencil bigger-125" />
							 <dytags:authBtn name="批量删除" code="del" id="btn-removeStudent" btnClass="btn btn-success" lableClass="icon-pencil bigger-125" />
							 <dytags:authBtn name="毕业" code="graduate" id="btn-graduateStuParent" btnClass="btn btn-success" lableClass="icon-pencil bigger-125" />
							 <dytags:authBtn name="导入" code="import" id="btn_importExcel" btnClass="btn btn-success" lableClass="icon-pencil bigger-125" />
							 <dytags:authBtn name="导出" code="export" id="btn-exportStudent" btnClass="btn btn-success" lableClass="icon-pencil bigger-125" />
							 <dytags:authBtn name="模板下载" code="download" id="btn_downTempExcel" btnClass="btn btn-success" lableClass="icon-pencil bigger-125" />
							 <dytags:hasAuth />
							</p>
							 <div id="query">
				<form name="form-search" id="form-search">
			 	   <label>年级：</label>
			       <select class="" id="gradeId"   style="width: 90px">
			         	<option value="">-请选择-</option>
				   </select>
			 	
			 	   <label>班级：</label>
			       <select class="" id="classId" style="width: 90px">
					   <option value=''>--请选择--</option>
				   </select>
		          <label>学生姓名：</label><input type="text"  id="name" />
		          <label>手机号码：</label><input type="text"  id="tel" />
		          <input type="button" class="btn btn-primary dy-search-button" id="find_btn" value="查 询" />
		          </form>
	        		</div>		
							<!-- 学生列表 -->
							<div class="col-xs-12" style="padding-left: 0px !important; padding-right: 0px !important;">
								<table id="student-table"></table>
								<div id="student-pager"></div>
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