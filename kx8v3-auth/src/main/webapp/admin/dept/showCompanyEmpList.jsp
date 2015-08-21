<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@page import="org.apache.commons.lang.StringUtils"%>
<%
    String id = StringUtils.defaultIfEmpty(request.getParameter("id"), "");
    String orgId = StringUtils.defaultIfEmpty(request.getParameter("orgId"), "");
%>
<script type="text/javascript">
</script>

<body>
      <div class="row">
          <div class="col-xs-12" style="overflow-x:hidden">
              <input type="hidden" class="input" id="deptId" name="deptId" value="<%= id %>"/>
              <input type="hidden" class="input" id="orgId" name="orgId" value="<%= orgId %>"/>
          <label>员工名称：</label><input type="text" class="input" id="name" maxlength="20" onkeyup="value=value.replace(/[^\d\a-zA-Z\u4E00-\u9FA5]/g,'')" onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^\d\a-zA-Z\u4E00-\u9FA5]/g,''))"/>

          <label>手机号码：</label><input type="text" class="input" id="mobile" maxlength="11" onkeyup="value=value.replace(/[^\d]/g,'')" onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^\d]/g,''))"/>

          <input type="button" class="btn btn-primary dy-search-button" id="find_btn" value="查 询" onclick="query();"/>
        </div>
        <!-- 员工列表 -->
        <div class="col-xs-12" style="overflow-x:hidden">
          <table id="empe-table" style="width: 1000px"></table>
          <div id="empe-pager"></div>
        </div>
  </div>
<script type="text/javascript" src="js/showCompanyEmpList.js"></script>
</body>
</html>