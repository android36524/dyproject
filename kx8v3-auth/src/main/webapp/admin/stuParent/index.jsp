<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="cn">
<head>
  <meta charset="utf-8" />
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <%@include file="/include-path.jsp"%>
  <%@include file="/common/include-bace.jsp"%>
   <style type="text/css">
		.modal-dialog{
			width: 900px !important;
		}
	</style>
  <link rel="stylesheet" href="<%=rootPath%>/public/bootstrap/ace/css/chosen.css" />
  <script src="<%=__BacePath%>/public/bootstrap/ace/js/chosen.jquery.min.js"></script>
	
  <script src="<%=__BacePath%>/public/lib/jquery.layout/jquery.layout-1.4.js"></script>
  <link rel="stylesheet" href="<%=rootPath%>/public/lib/jquery.layout/jquery.layout-1.4.css" />
  
  <link href="<%=path%>/admin/css/dy-admin-default.css" rel="stylesheet">
  <link href="css/index.css" rel="stylesheet">
  <title>学生家长管理</title>

</head>
<body>
<%@include file="/admin/common/common-navbar.jsp" %>
<div class="main-container container-fluid">
  <%@include file="/admin/common/common-leftmenu.jsp" %>
  <div class="main-content">
    <%@include file="../common/common-breadcrumbs.jsp" %>
    <div class="page-content">
      <div style="width: 100%;height: 690px" id="div_main">
      		<div class="ui-layout-west jl_pane jl_ui-layout-pane" style="border: 1px solid #ccc;"> 
						<%@include file="/admin/common/orgTree/index.jsp" %>
			</div>
			<div class="ui-layout-center jl_ui-layout-pane">
			    <p>
			    <dytags:authBtn name="新增" code="add" id="btn-addStuParent" btnClass="btn btn-success" lableClass="icon-pencil bigger-125" />
		         <dytags:authBtn name="删除" code="del" id="btn-removeStuParent" btnClass="btn btn-success" lableClass="icon-pencil bigger-125" />
		           <dytags:authBtn name="导出" code="export" id="btn-exportStuParent" btnClass="btn btn-success" lableClass="icon-pencil bigger-125" />
				<dytags:hasAuth />
		        </p>
		        <form name="form-search" id="form-search">
			        <div id="query">
			        	  <%@include file="/admin/common/gradeChoose/common-gradechoose.jsp" %>
				          <label>家长姓名：</label><input type="text" class="input" id="par_name" />
				          <label>手机号码：</label><input type="text" class="input" id="par_phone" />
				          <input type="button" class="btn btn-primary dy-search-button" id="find_btn" value="查 询" />
			        </div>			
	      		</form>
		        <!-- 学生家长列表 -->
		        <div class="col-xs-12" style="overflow-x:hidden">
		          <table id="stuParent-table"></table>
		          <div id="stuParent-pager"></div>
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