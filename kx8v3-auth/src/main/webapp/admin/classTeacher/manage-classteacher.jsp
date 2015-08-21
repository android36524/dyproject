<%@page import="org.apache.commons.lang.StringUtils"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@include file="/include-path.jsp"%>
<%
  String classId = StringUtils.defaultIfEmpty(request.getParameter("classId"), "");
  String semesterId = StringUtils.defaultIfEmpty(request.getParameter("semesterId"), "");
%>
<script type="text/javascript">
  var _classId = "<%=classId%>";
  var _semesterId="<%=semesterId%>";
</script>
<style>
.cut-off-rule{
	border-bottom-style:solid;
	border-bottom-width:1px;
	border-bottom-color:#ccc;
	margin-bottom: 20px;
	font-weight:bold;
}

.modal-dialog{
	width: 900px;
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
  <form class="form-horizontal dy-dialog-form" id="parent_form" method="post">
    <input type="hidden" id="class_schId"/>
	<div class="cut-off-rule" >设置任课老师</div>
    <div class="form-group">
          <label class="control-label col-xs-12 col-sm-5 no-padding-right" for="semester">学期:</label>
          <div class="col-xs-12 col-sm-7">
              <div class="clearfix">
                  <select id="semester" name="semester" disabled="disabled">

                  </select>
              </div>
          </div>
    </div>
  <div class="space-2"></div>
    <div class="form-group">
        <label class="control-label col-xs-12 col-sm-5 no-padding-right" for="class_grade">年级:</label>
        <div class="col-xs-12 col-sm-7">
            <div class="clearfix">
                <select id="class_grade" name="class.grade" disabled="disabled">

                </select>
            </div>
        </div>
    </div>
    <div class="space-2"></div>
    
    <div class="form-group">
        <label class="control-label col-xs-12 col-sm-5 no-padding-right" for="class_class">班级:</label>
        <div class="col-xs-12 col-sm-7">
            <div class="clearfix">
                <select id="class_class" name="class.class" disabled="disabled">

                </select>
            </div>
        </div>
    </div>
    <div class="space-2"></div>
    <div class="form-group">
        <label class="control-label col-xs-12 col-sm-5 no-padding-right" for="subject">科目:</label>
        <div class="col-xs-12 col-sm-7">
          <div class="clearfix">
              <select id="subject" name="subject">

              </select>
          </div>
        </div>
    </div>
    <div class="form-group">
        <label class="control-label col-xs-12 col-sm-5 no-padding-right" for="class_teacher">任课老师:</label>
        <div class="col-xs-12 col-sm-7">
            <div class="clearfix">
                <div class="input-group">
						<span>
							<input type="text" name="class.teacher" id="class_teacher" class="col-xs-12 col-sm-8" disabled="disabled"/>
							<input type="hidden" name="classTeacherId" id="classTeacherId"/>
						</span>
						<span class="input-group-btn">
							<button class="btn btn-sm btn-primary" type="button" id="btn_teacher">
                                <i class="icon-edit bigger-110"></i>
                                选择任课老师
                            </button>
						</span>
                </div>
            </div>
        </div>
    </div>
    <div class="space-2"></div>
    <div class="form-group">
   	   <div class="input-group col-xs-12 col-sm-7 ">
	        <div class="clearfix">
	             <input type="button" class="btn btn-primary dy-search-button " id="confirmBtn" value="确认">
	        </div>
       </div>
    </div>
    <div class="space-2"></div>
    <div class="row">
       	<div class="cut-all-rule">已设置任课老师列表</div>
    	<div class="col-xs-12" style="overflow-x:hidden;overflow-y:hidden;height:200px;">
          <table id="classTeacher-table"></table>
     	</div>
    </div>
    <div class="space-2"></div>
  </form>
</div>
<script type="text/javascript" src="js/manage-classteacher.js"></script>