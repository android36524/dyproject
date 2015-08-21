<%@page import="org.apache.velocity.runtime.directive.Include"%>
<%@page import="org.apache.commons.lang.StringUtils"%>
<%@ page import="com.dayang.commons.util.DaYangCommonUtil" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/include-path.jsp"%>

<link href="css/index.css" rel="stylesheet" type="text/css">

<%
  	String schoolBookId = StringUtils.defaultIfEmpty(request.getParameter("schoolBookId"), "");

	String subjectId = StringUtils.defaultIfEmpty(request.getParameter("subjectId"), "");
	String gradeId = StringUtils.defaultIfEmpty(request.getParameter("gradeId"), "");
	String stageId = StringUtils.defaultIfEmpty(request.getParameter("stageId"), "");
%>
<script type="text/javascript">
  var schoolBookId = "<%=schoolBookId%>";
  var subjectId = "<%=subjectId%>";
  var gradeId = "<%=gradeId%>";
  var stageId = "<%=stageId%>";
  
  
  //alert("教材版本："+schoolBookId+"科目ID="+subjectId+"||年级ID="+gradeId+"||学阶ID="+stageId);
</script>
<style type="text/css">
.modal-dialog {
	width: 1000px !important;
}
</style>
<div>
	<%--栅格系统布局 --%>
	<form class="form-horizontal dy-dialog-form" id="section_form" method="post">
	<div class="row">
		<div class="col-xs-6" style="border: solid; border-width: 0px; border-color: #ccc;height: 510px;border-right-width: 1px">
			<%@include file="/admin/schoolBook/section.jsp" %>
		</div>
		
		<div class="col-xs-6" style="border: solid; border-width: 0px; border-color: #ccc;height: 510px;">
			<%@include file="/admin/schoolBook/lore.jsp" %>
		</div>
	</div>
	</form>
</div>
