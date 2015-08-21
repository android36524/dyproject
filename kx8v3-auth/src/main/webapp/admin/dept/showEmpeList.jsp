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
          <label>员工名称：</label><input type="text" class="input" id="name" />

          <label>手机号码：</label><input type="text" class="input" id="mobile" />

          <input type="button" class="btn btn-primary dy-search-button" id="find_btn" value="查 询"  onclick="query();"/>
        </div>
        <!-- 员工列表 -->
        <div class="col-xs-12" style="overflow-x:hidden">
          <table id="empe-table" style="width: 1000px"></table>
          <div id="empe-pager"></div>
        </div>
  </div>
<script type="text/javascript" src="js/showEmpeList.js"></script>
</body>
</html>