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
    var _edbId= "";
  </script>

  <title>部门管理</title>
</head>
<body>
<%@include file="/admin/common/common-navbar.jsp" %>
<div class="main-container container-fluid">
  <%@include file="/admin/common/common-leftmenu.jsp" %>
  <div class="main-content">
    <%@include file="../common/common-breadcrumbs.jsp" %>
    <div class="page-content">
      <div style="width: 100%;height: 690px;" id="div_main">
      <c:if test="${title=='教育局'||title=='学校' }">
        <div class="ui-layout-west jl_pane jl_ui-layout-pane" style="border: 1px solid #ccc;">
          <%@include file="/admin/common/orgTree/index.jsp" %>
        </div>
        </c:if>
        <div class="ui-layout-center jl_ui-layout-pane">
          <div>
        <c:if test="${title=='教育局'||title=='学校' }">  
        <p>
		<dytags:authBtn name="新增" code="add" id="btn-addDept" btnClass="btn btn-success" lableClass="icon-pencil bigger-125" />
		<dytags:hasAuth />
		</p>
		</c:if>
		<c:if test="${title=='公司' }">
			<p style="display: none" id="cmonDiv">
          <button class="btn btn-success" id="btn-addDept" >
            <i class="icon-pencil bigger-125"></i>
           		 新增公司部门
          </button>
          <dytags:hasAuth />
        </p>
		</c:if>
            <form name="form-search" id="form-search">
            <c:if test="${title=='教育局'||title=='学校' }">
              <div class="form-group col-md-4" >
                <label>部门名称：</label><input type="text" class="input" id="deptName" maxlength="20" onkeyup="value=value.replace(/^ +| +$/g,'')" onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/^ +| +$/g,''))"/>
                <%--<input type="hidden" class="input" id="orgId" name="orgId" value="1" />--%>
                <input type="button" class="btn btn-primary dy-search-button" id="find_btn" value="查 询" />
              </div>
              </c:if>
              <c:if test="${title=='公司' }">
	              <div class="form-group col-xs-12 col-md-12" >
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
	                <label class="col-xs-1" style="text-align: right">公司部门名称：</label>
	                <input type="text" class="input" id="deptName" maxlength="20" onkeyup="value=value.replace(/^ +| +$/g,'')" />
	                <%--<input type="hidden" class="input" id="orgId" name="orgId" value="1" />--%>
	                <input type="button" class="btn btn-primary dy-search-button" id="find_btn_company" value="查 询" />
	              </div>
              </c:if>
              </form>
        <!-- 教育部门列表 -->
        <div class="col-xs-12" style="overflow-x:hidden">
          <table id="dept-table"></table>
          <div id="dept-pager"></div>
        </div>
      </div>
    </div>
  </div>
</div>
  </div>
</div>

<%@include file="/admin/common/common-setting.jsp" %>
<script type="text/javascript">
  var _title = '${title}';
  var _orgFlag = '${orgFlag}';
</script>
<script type="text/javascript" src="js/index.js"></script>
</body>
</html>