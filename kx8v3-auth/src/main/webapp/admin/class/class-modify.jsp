<%@page import="org.apache.commons.lang.StringUtils"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String id = StringUtils.defaultIfEmpty(request.getParameter("_classId"),"");
%>
<script type="text/javascript">
	var _id = "<%=id%>";
</script>
<%@include file="/include-path.jsp"%>

<style>
.tips{color: red; margin-right: 5px;}
</style>

<div>
	<form class="form-horizontal dy-dialog-form" id="class_form" 
		method="post">
		<input type="hidden" name="class.id" id="class_id"/>
		<input type="hidden" name="class.schId" id="class_schId" value="${schId}"/>

		<div class="row">
			<div class="col-xs-12">
				<div class="form-group">
					<label class="control-label col-xs-12 col-sm-12 no-padding-right"
						for="class_gradeId"><b class="tips">*</b>年级名称:</label>
					<div class="col-xs-12 col-sm-7">
						<div class="clearfix">
							<select name="class.gradeId" id="class_gradeId" class="col-xs-12 col-sm-7">
								<option value="">-请选择-</option>
								<c:forEach var="grade" items="${gradeList }">
									<option value="${grade.id }">${grade.name }</option>
								</c:forEach>
							</select>
						</div>
					</div>
				</div>
			</div>
		</div>

		<div class="row">
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-xs-12 col-sm-5 no-padding-right"
						for="class_name"><b class="tips">*</b>班级名称:</label>
					<div class="col-xs-12 col-sm-9">
						<div class="clearfix">
							<input type="text" name="class.name" id="class_name"
								class="col-xs-12 col-sm-9" />
						</div>
					</div>
				</div>
			</div>
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-xs-12 col-sm-5 no-padding-right"
						for="class_addr">上课地点:</label>
					<div class="col-xs-12 col-sm-9">
						<div class="clearfix">
							<input type="text" name="class.addr" id="class_addr" 
								class="col-xs-12 col-sm-9" />
						</div>
					</div>
				</div>
			</div>
		</div>

		<div class="row">
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-xs-12 col-sm-5 no-padding-right"
						for="class_buildDate"><b class="tips">*</b>建班年月:</label>
					<div class="col-xs-12 col-sm-7 input-group">
						<span class="input-group-addon"> <i
							class="icon-calendar bigger-110"></i>
						</span> <input type="text" name="class.buildDate"
							id="class_buildDate" class="form-control"
							readonly="readonly" />
					</div>
				</div>
			</div>
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-xs-12 col-sm-5 no-padding-right"
						for="class_headTeacherh">班主任:</label>
					<div class="col-xs-12 col-sm-8">
						<div class="clearfix">
							<input type="hidden" id="class_classHeadId" name="class.classHeadId" />
							<input type="text" name="headTeacher" readonly="readonly"
								id="class_headTeacher" class="col-xs-12 col-sm-7" />
							<span class="input-group-btn">
							<button class="btn btn-sm btn-primary" type="button"
								id="btn_select_hTeacher">
								<i class="icon-edit bigger-100"></i> 选择
							</button>
							</span>	
						</div>
						
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-xs-10">
				<div class="form-group">
					<label class="control-label col-xs-12 col-sm-5 no-padding-right"
						for="class_subjectType">文理类型:</label>
					<div class="col-xs-12 col-sm-9">
						<div class="clearfix">
							<select name="class.subjectType" id="class_subjectType" class="col-xs-12 col-sm-5">
								<option value="">-请选择-</option>
								<c:forEach var="dic" items="${subjectTypeList }">
									<option value="${dic.value }">${dic.name }</option>
								</c:forEach>
							</select>
						</div>
					</div>
				</div>
			</div>
		</div>
		
		<div class="row">
			<div class="col-xs-10">
				<div class="form-group">
					<label class="control-label col-xs-12 col-sm-5 no-padding-right"
						for="class_seq">排序:</label>
					<div class="col-xs-12 col-sm-7">
						<div class="clearfix">
							<input type="text" name="class.seq" id="class_seq" 
							class="col-xs-12 col-sm-12 spinner-input" />
						</div>
					</div>
				</div>
			</div>
		</div>

		<div class="row">
			<div class="col-xs-12">
				<div class="form-group">
					<label class="control-label col-xs-12 col-sm-5 no-padding-right"
						for="class_remark">备注:</label>
					<div class="col-xs-12 col-sm-7">
						<div class="clearfix">
							<textarea class="col-xs-12 col-sm-12" name="class.remark"
								id="class_remark"></textarea>
						</div>
					</div>
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
<script type="text/javascript" src="js/class-modify.js"></script>
