<%@page import="org.apache.commons.lang.StringUtils"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib prefix="h" uri="http://java.sun.com/jsf/html" %>
<%
  String id = StringUtils.defaultIfEmpty(request.getParameter("id"), "");
%>
<script type="text/javascript">
  var _id = "<%=id%>";
</script>
<div>
  <form class="form-horizontal dy-dialog-form" id="dept_form" method="post">

<div class="form-group">
      <label class="control-label col-xs-12 col-sm-5 no-padding-right" for="orgName">所属${title}:</label>
      <div class="col-xs-12 col-sm-7">
        <div class="clearfix">
          <input type="text" name="orgName"  id="orgName" class="col-xs-12 col-sm-12" value="${orgName} " readonly="readonly" />
        </div>
      </div>
    </div>
    <div class="space-2"></div>

    <div class="form-group">
      <label class="control-label col-xs-12 col-sm-5 no-padding-right" for="dept_deptName">部门名称:</label>
      <div class="col-xs-12 col-sm-7">
        <input type="hidden" name="dept.id"  id="dept_id"  />
        <div class="clearfix">
          <input type="text" name="dept.deptName"  id="dept_deptName" class="col-xs-12 col-sm-12"  readonly="readonly" />
        </div>
      </div>
    </div>
    <div class="space-2"></div>

    <div class="form-group">
      <label class="control-label col-xs-12 col-sm-5 no-padding-right" for="dept_deptName">所属部门:</label>
      <div class="col-xs-12 col-sm-7">
        <div class="input-group">
              <span>
                  <div class="clearfix">
                    <input type="text" name="dept.parentName"  id="dept_parentName" class="col-xs-12 col-sm-12"  readonly="readonly" />
                  </div>
                  <input type="hidden" name="dept.pid" id="dept_pid"/>
              </span>
        </div>
      </div>
    </div>
    <div class="space-2"></div>

    <div class="form-group">
      <label class="control-label col-xs-12 col-sm-5 no-padding-right" for="dept_deptCode">部门编号:</label>
      <div class="col-xs-12 col-sm-7">
        <div class="clearfix">
          <input type="text" name="dept.deptCode"  id="dept_deptCode" class="col-xs-12 col-sm-12"  readonly="readonly" />
        </div>
      </div>
    </div>
    <div class="space-2"></div>

    <div class="form-group">
      <label class="control-label col-xs-12 col-sm-5 no-padding-right" for="dept_telphone">部门联系电话:</label>
      <div class="col-xs-12 col-sm-7">
        <div class="clearfix">
          <input type="text" name="dept.telphone"  id="dept_telphone" class="col-xs-12 col-sm-12"  readonly="readonly" />
        </div>
      </div>
    </div>
    <div class="space-2"></div>

    <div class="form-group">
      <label class="control-label col-xs-12 col-sm-5 no-padding-right" for="dept_charge">部门负责人:</label>
      <div class="col-xs-12 col-sm-7">
        <div class="clearfix">
          <input type="text" name="dept.charge"  id="dept_charge" class="col-xs-12 col-sm-12"  readonly="readonly" />
        </div>
      </div>
    </div>
    <div class="space-2"></div>

    <div class="form-group">
      <label class="control-label col-xs-12 col-sm-5 no-padding-right" for="dept_chargePhone">负责人电话:</label>
      <div class="col-xs-12 col-sm-7">
        <div class="clearfix">
          <input type="text" name="dept.chargePhone"  id="dept_chargePhone" class="col-xs-12 col-sm-12"  readonly="readonly" />
        </div>
      </div>
    </div>
    <div class="space-2"></div>




    <!--  <div class="form-group">
      <label class="control-label col-xs-12 col-sm-5 no-padding-right" for="dept_remark">备注:</label>
      <div class="col-xs-12 col-sm-7">
        <div class="clearfix">
          <textarea class="col-xs-12 col-sm-12" name="dept.remark" id="dept_remark" readonly></textarea>
        </div>
      </div>
    </div>
    <div class="space-2"></div>-->

   <div class="form-group">
      <label class="control-label col-xs-12 col-sm-5 no-padding-right" for="dept_se">排序:</label>
      <div class="col-xs-12 col-sm-7">
        <div class="clearfix">
          <input type="text" name="dept.seq"  id="dept_seq" class="col-xs-12 col-sm-12"  readonly="readonly" />
        </div>
      </div>
    </div>
    <div class="space-2"></div>


  </form>
</div>
<script type="text/javascript">
  
</script>
<script type="text/javascript" src="js/showdeptInfo.js"></script>