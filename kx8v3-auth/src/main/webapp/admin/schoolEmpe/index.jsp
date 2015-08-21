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
  <title>学校员工管理</title>
</head>
<style type="text/css">
  .modal-dialog{
    width: 1000px;
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
        <dytags:authBtn name="新增" code="add" id="btn-addEmpe" btnClass="btn btn-success" lableClass="icon-pencil bigger-125" />
        <dytags:authBtn name="批量删除" code="del" id="btn-removeEmpe" btnClass="btn btn-success" lableClass="icon-pencil bigger-125" />
	    <dytags:authBtn name="导入员工" code="import" id="btn_importExcel" btnClass="btn btn-success" lableClass="icon-pencil bigger-125" />
		<dytags:authBtn name="导出员工" code="export" id="btn_downLoadExcel" btnClass="btn btn-success" lableClass="icon-pencil bigger-125" />
	   	<dytags:authBtn name="模板下载" code="download" id="btn_downTempExcel" btnClass="btn btn-success" lableClass="icon-pencil bigger-125" />
		<dytags:hasAuth />
        </p>
            
            <form name="form_search" id="form_search">
						<div class="panel panel-default">
							<div class="panel-body">
								 <div class="col-xs-12">
									<div class="form-group col-xs-1" align="right">   <label>部门：</label> </div>
									<div class="form-group col-xs-2" >										
										<div class="col-xs-12 col-sm-12 input-group">
											<input type="text" class="form-control" name="checkName" id="checkName"  readonly="readonly">
										    <span class="input-group-addon">
												<i class="icon-hand-left bigger-110" id="btn_select_dept">部门</i>													
											</span>
											<div style="display:none">		
												<input type="text"  id = "deptId" />
											</div>												
										</div>										
									</div>
								    <div class="form-group col-xs-1"  align="right">   <label>员工名称：</label> </div>
									<div class="form-group col-xs-2" >  <input type="text" class="input" id="name" /> 	</div>
					              </div>	
					              
					              <div class="col-xs-12">	 	
									<div class="form-group col-xs-1"  align="right">   <label>手机号码：</label> </div>
									<div class="form-group col-xs-2" >  <input type="text" maxlength="20" class="input" id="mobile" /> 	</div>	
									
									<div class="form-group col-xs-1"  align="right">   <label>教师状态：</label></div>
									<div class="form-group col-xs-2" >
						                <select class="" id="status" dyId="chooseId2" name="status">
						                  <option value="">--请选择--</option>
						                <c:forEach var="tea" items="${teaList}">
						                  <option value="${tea.value}">${tea.name}</option>
						                </c:forEach>
						              </select>
									</div>
									<div class="form-group col-xs-2"  align="center"> <input type="button" class="btn btn-primary dy-search-button" id="find_btn" value="查 询" /> 
										<button type="button" class="btn btn-primary dy-search-button" id="reset_btn" >重置</button> 
									</div>								  		      
								 </div>
					              
					              	
							</div>
						</div>
						</form> 
						
						
        <!-- 课时列表 -->
        <div class="col-xs-12" style="overflow-x:hidden">
          <table id="empe-table"></table>
          <div id="empe-pager"></div>
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