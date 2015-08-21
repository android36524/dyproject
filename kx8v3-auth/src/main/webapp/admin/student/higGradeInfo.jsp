<%@page import="org.apache.commons.lang.StringUtils"%>
<%@ page import="com.dayang.commons.util.DaYangCommonUtil" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@include file="/include-path.jsp"%>
<%
  String id = StringUtils.defaultIfEmpty(request.getParameter("id"), "");
%>
<script type="text/javascript">
  var _id = "<%=id%>";
</script>
<style>
.cut-off-rule{
	border-bottom-style:solid;
	border-bottom-width:1px;
	border-bottom-color:#ccc;
	margin-bottom: 20px;
	font-weight:bold;
}


.cut-all-rule{
	border-style:solid;
	border-width:1.5px;
	border-color:#ccc;
	font-weight:bold;
	background-color: #ccc;
	margin-bottom:20px
}
</style>
<div>
  <form class="form-horizontal dy-dialog-form" id="student_form" method="post">

	<div class="cut-off-rule" >升学学生基本信息</div>

    <div class="row">
      <div class="col-xs-6">
        <div class="form-group">
          <label class="control-label col-xs-12 col-sm-5 no-padding-right" for="stuName"><span style="color: red"> *&nbsp;</span>学生姓名:</label>
         <div class="col-xs-12 col-sm-9">
            <div class="clearfix">
              <input type="hidden" name="id" id="id" value="${id} "/> 
              <input type="text" name="stuName" id="stuName" disabled="disabled" class="col-xs-12 col-sm-12 spinner-input" />
            </div>
          </div>
        </div>
      </div>
      <div class="col-xs-6">
        <div class="form-group">
          <label class="control-label col-xs-12 col-sm-5 no-padding-right" for="sex">性别:</label>
          <div class="col-xs-12 col-sm-9">
            <div class="clearfix">
              <input type="text" name="sex" id="sex" disabled="disabled" class="col-xs-12 col-sm-12 spinner-input" />
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="space-2"></div>
    
    <div class="row">
      <div class="col-xs-6">
        <div class="form-group">
          <label class="control-label col-xs-12 col-sm-5 no-padding-right" for="birthDate"><span style="color: red"> *&nbsp;&nbsp;</span>出生日期:</label>
         <div class="col-xs-12 col-sm-9">
            <div class="clearfix">
            <input type="text" name="birthDate" id="birthDate" disabled="disabled" class="col-xs-12 col-sm-12 spinner-input" />
            </div>
          </div>
        </div>
      </div>
      <div class="col-xs-6">
        <div class="form-group">
          <label class="control-label col-xs-12 col-sm-5 no-padding-right" for="studentNo"><span style="color: red"> *&nbsp;&nbsp;</span>学号:</label>
          <div class="col-xs-12 col-sm-9">
            <div class="clearfix">
              <input type="text" name="studentNo" id="studentNo" disabled="disabled" class="col-xs-12 col-sm-12 spinner-input" />
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="space-2"></div>
    
     <div class="row">
      <div class="col-xs-6">
        <div class="form-group">
          <label class="control-label col-xs-12 col-sm-5 no-padding-right" for="befGrade"><span style="color: red"> *&nbsp;&nbsp;</span>原年级:</label>
          <div class="col-xs-12 col-sm-9">
            <div class="clearfix">
              <input type="text" name="befGrade" id="befGrade" disabled="disabled" class="col-xs-12 col-sm-12 spinner-input" />
            </div>
          </div>
        </div>
      </div>
      <div class="col-xs-6">
        <div class="form-group">
          <label class="control-label col-xs-5 no-padding-right" for="afeGrade"><span style="color: red"> *&nbsp;&nbsp;</span>现年级:</label>
          
          <div class="col-xs-7 input-group">
            <div class="clearfix">
              <input type="text" name="afeGrade" id="afeGrade" disabled="disabled" class="col-xs-12 col-sm-12 spinner-input" />
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="space-2"></div>
    
    <div class="row">
      <div class="col-xs-6">
        <div class="form-group">
          <label class="control-label col-xs-12 col-sm-5 no-padding-right" for="className"><span style="color: red"> *&nbsp;&nbsp;</span>班级:</label>
          <div class="col-xs-12 col-sm-9">
            <div class="clearfix">
             <input type="text" name="className" id="className" disabled="disabled" class="col-xs-12 col-sm-12 spinner-input" />
            </div>
          </div>
        </div>
      </div>
      <div class="col-xs-6">
        <div class="form-group">
          <label class="control-label col-xs-12 col-sm-5 no-padding-right" for=""createTime"">升学时间:</label>
          <div class="col-xs-12 col-sm-9">
            <div class="clearfix">
              <input type="text" name="createTime" id="createTime" disabled="disabled" class="col-xs-12 col-sm-12 spinner-input" />
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="space-2"></div>

  </form>
</div>
<script type="text/javascript" src="js/higGrade.js"></script>