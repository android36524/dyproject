<%@page import="org.apache.commons.lang.StringUtils"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String id = StringUtils.defaultIfEmpty(request.getParameter("id"),
			"");
%>
<script type="text/javascript">
	var _id = "<%=id%>";
	
</script>
<style>
.phcolor{ color:#999;}
</style>
<%@include file="/include-path.jsp"%>
<div>
	<form class="form-horizontal dy-dialog-form" id="grade_form"
		method="post">
		<input type="hidden" name="grade.id" id="grade_id" />
		<input type="hidden" name="hd_subjectIds" id="hd_subjectIds" />
		
		<c:if test="${param['flag']==1}">
		<div class="form-group">	
			<label class="control-label col-xs-12 col-sm-5 no-padding-right my-mustlabel"
				for="grade_stageId">请选择学阶:</label>
			<div class="col-xs-12 col-sm-7">
				<div class="clearfix">
					<select class="col-xs-12 col-sm-12" id="grade_stageId" name="grade.stageId">
					<c:forEach var="stage" items="${stageList }">											
						<option value="${stage.id}">${stage.name}</option>					
					</c:forEach>
					</select>
				</div>
			</div>
		</div>	
		<div class="space-2"></div>
		<div class="form-group">
		<input type="hidden" id="grade_name_hidden">
			<label class="control-label col-xs-12 col-sm-5 no-padding-right my-mustlabel"
				for="grade_name">年级名称:</label>
			<div class="col-xs-12 col-sm-7">
				<div class="clearfix">
					<input type="text" name="grade.name" id="grade_name"
						class="col-xs-20 col-sm-12" />
				</div>
			</div>
		</div>
		<div class="space-2"></div>
		<div class="form-group">
		<input type="hidden" id="grade_code_hidden">
			<label class="control-label col-xs-12 col-sm-5 no-padding-right my-mustlabel"
				for="grade_code">年级编码:</label>
			<div class="col-xs-12 col-sm-7">
				<div class="clearfix">
					<input type="text" name="grade.code" id="grade_code"
						class="col-xs-20 col-sm-12" />
				</div>
			</div>
		</div>
			</c:if>
			<c:if test="${param['flag']==2}">
		<div class="form-group">	
			<label class="control-label col-xs-12 col-sm-5 no-padding-right my-mustlabel"
				for="grade_gradeId">年级名称:</label>
			<div class="col-xs-12 col-sm-7">
				<div class="clearfix">
					<select class="col-xs-12 col-sm-12" id="grade_gradeId" name="grade.gradeId">
					<c:forEach var="grade" items="${gradeList }">											
						<option value="${grade.id}" stageName="${grade.name }" codeid="${grade.code}" stageId="${grade.stageId}">${grade.name}(${grade.stageName})</option>					
					</c:forEach>
					</select>
					<input type="hidden" id="grade_stageId" name="grade.stageId">
					<input type="hidden" id="grade_code" name="grade.code">			
				</div>
			</div>
		</div>
		<div class="space-2"></div>
		<div class="form-group">
			<label class="control-label col-xs-12 col-sm-4 no-padding-right my-mustlabel"
				for="grade_name">年级别名:</label>
			<div class="col-xs-12 col-sm-3">
				<div class="clearfix">
					<input type="text" name="grade.name" id="grade_name" class="col-xs-12 col-sm-12" />				       
				</div>
			</div>
			<label class="col-xs-12 col-sm-5"><small>(年级别名,每个学校可自行设置)</small></label>
		</div>
		</c:if>
		<input type="hidden" id="grade_schId" name="grade.schId">
		<input type="hidden" id="grade_flag" name="grade.flag" value="${param['flag']}">
		<div class="space-2"></div>
		<div class="form-group" id="div_">
			<label class="control-label col-xs-12 col-sm-5 no-padding-right my-mustlabel"
				for="subjectId">科目:</label>
			<div class="col-xs-12 col-sm-7">
				<div class="clearfix">
				<select multiple="" class="chosen-select tag-input-style col-xs-12 col-sm-12"  id="subjectId" name="subjectId" data-placeholder="请选择科目...">
					<option value="-1"></option>
					<c:forEach var="subject" items="${subjectList }">											
						<option id="selectSubjectId" value="${subject.id}" <c:if test="${not empty gradeSubject}">
						 <c:forEach  var="gradeSu" items="${gradeSubject }">					
							<c:if test="${gradeSu.subjectId==subject.id}">
   	 							<c:out value="selected='selected'"></c:out>
							</c:if>
					   </c:forEach>
					   </c:if>
					   >${subject.name}</option>					
					</c:forEach>
				</select>
				</div>
			</div>
		</div>

		<div class="space-2"></div>
		<div class="form-group">
			<label class="control-label col-xs-12 col-sm-5 no-padding-right"
				for="grade_seq">排序:</label>
			<div class="col-xs-12 col-sm-7">
				<div class="clearfix">
					<input type="text" name="grade.seq" id="grade_seq"	class="input-mini" />
				</div>
			</div>
		</div>
		<div class="space-2"></div>

		<div class="form-group">
			<label class="control-label col-xs-12 col-sm-5 no-padding-right"
				for="grade_remark">备注:</label>
			<div class="col-xs-12 col-sm-7">
				<div class="clearfix">
					<textarea class="col-xs-12 col-sm-12" name="grade.remark"
						id="grade_remark"></textarea>
				</div>
			</div>
		</div>
		<div class="space-2"></div>
	</form>
	<div class="col-xs-12">
		<table id="grid-table"></table>
		<div id="grid-pager"></div>
	</div>
</div>
<script type="text/javascript">
var _flag='${param['flag']}';

</script>
<script type="text/javascript" src="js/grade-modify.js"></script>
<script type="text/javascript" src="../js/commonDiyCss.js"></script>