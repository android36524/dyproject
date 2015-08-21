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
	<script src="<%=__BacePath%>/public/lib/jquery.layout/jquery.layout-1.4.js"></script>
	<link rel="stylesheet" href="<%=rootPath%>/public/lib/jquery.layout/jquery.layout-1.4.css" />
	
	<title>学期管理</title>
</head>
<script type="text/javascript">
	var _schId= "";

	</script>
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
						<div >
							<p>
								<dytags:authBtn name="新增" code="add" id="btn-addSemester" btnClass="btn btn-success" lableClass="icon-pencil bigger-125" />
								<dytags:hasAuth />
							</p>
							<div class="form-group col-md-4" >
								<form class="form-horizontal dy-dialog-form" id="form-search" method="post">
							   <label>学期名称：</label><input type="text" class="input" maxlength="20" id="name" /> 	
							   </form>
							     </from>					
						       <input type="button" class="btn btn-primary dy-search-button"  id="find_btn" value="查 询" /> 
					        </div>
				
							<!-- 课时列表 -->
							<div class="col-xs-12" style="overflow-x:hidden">
								<table id="semester-table"></table>
								<div id="semester-pager"></div>
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