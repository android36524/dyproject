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

  <title>员工信息管理</title>
  
  <script type="text/javascript">
		var _edbType = 2;
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
		<%-- <div class="ui-layout-west jl_pane jl_ui-layout-pane" style="border: 1px solid #ccc;">
			<%@include file="/admin/common/orgTree/index.jsp" %>
		</div> --%>
        <div class="ui-layout-center jl_ui-layout-pane">
          <div>
        <p id="addCompanyEmp" style="display: none">
						<dytags:authBtn name="新增" code="add" id="btn-addCompanyEmp" btnClass="btn btn-success" lableClass="icon-pencil bigger-125" />
						<dytags:hasAuth />
		</p>
            <form name="form-search" id="form-search">
              <div class="form-group" >
              
              <div class="col-xs-12 col-md-12">
              		<label class="col-xs-1" style="text-align: right"><font color="red">*</font>省：</label>
	              	<select class="col-xs-1" id="company_provinceCode" name="companyProvinceCode">
		              <option value="">请选择</option>
		            </select>
	              	<label class="col-xs-1" style="text-align: right"><font color="red">*</font>市：</label>
	              	<select class="col-xs-1" id="company_cityCode" name="companyCityCode">
		              <option value="">请选择</option>
		            </select>
	              	<label class="col-xs-1" style="text-align: right"><font color="red">*</font>区/县：</label>
	              	<select class="col-xs-1" id="company_areaCode" name="companyAreaCode">
		              <option value="">请选择</option>
		            </select>
		            <label class="col-xs-1" style="text-align: right"><font color="red">*</font>公司名称：</label>
		            <select class="col-xs-1" id="company_name" name="companyName">
		              <option value="">请选择</option>
		            </select>
		            </div>
		            <div class="col-xs-12 col-md-12"><p/></div>
                <div class="col-xs-12 col-md-12">
                <label class="col-xs-1" style="text-align: right">员工类型：</label>
                <select class="col-xs-1" id="empType">
                	<option value="">全部</option>
                	<option value="3">员工</option>
                	<option value="5">代理商</option>
                </select>
                
                <label class="col-xs-1" style="text-align: right">姓名或简拼：</label>
                <input id="nameOrPy" type="text"  name="nameOrPy" class="input col-xs-1" maxlength="20" onkeyup="value=value.replace(/[^\d\a-zA-Z\u4E00-\u9FA5]/g,'')" />
                <label class="col-xs-1" style="text-align: right">账号：</label>
                <input id="accountId" type="text" name="accountId" class="input col-xs-1" maxlength="20" onkeyup="value=value.replace(/[^\d]/g,'')" />
                <label class="col-xs-1" style="text-align: right">手机号码：</label>
                <input id="phoneNo" type="text"  name="phoneNo" class="input col-xs-1" maxlength="11" onkeyup="value=value.replace(/[^\d]/g,'')" />
                <input type="button" class="btn  btn-primary  dy-search-button col-xs-1" id="find_btn" value="查 询" />
              </div>
              </div>
              </form>
        <!-- 教育部门列表 -->
        <div class="col-xs-12" style="overflow-x:hidden">
          <table id="companyEmp-table"></table>
          <div id="companyEmp-pager"></div>
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