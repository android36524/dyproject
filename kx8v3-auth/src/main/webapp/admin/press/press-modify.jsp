<%@page import="org.apache.commons.lang.StringUtils"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String id = StringUtils.defaultIfEmpty(request.getParameter("id"),"");
%>
<script type="text/javascript">
	var _id = "<%=id%>";
</script>
<%@include file="/include-path.jsp"%>
<div>
	<form class="form-horizontal dy-dialog-form" id="press_form"
		method="post">
		<input type="hidden" name="press.id" id="press_id"
			class="col-xs-20 col-sm-12" />

		<div class="space-2"></div>
		<div class="form-group">
			<label class="control-label col-xs-12 col-sm-5 no-padding-right my-mustlabel"
				for="press_name">出版社名称:</label>
			<div class="col-xs-12 col-sm-7">
				<div class="clearfix">
					<input type="text" name="press.name" id="press_name"
						class="col-xs-20 col-sm-12" />
				</div>
			</div>
		</div>

		<div class="space-2"></div>
		<div class="form-group">
			<label class="control-label col-xs-12 col-sm-5 no-padding-right"
				for="press_seq">排序:</label>
			<div class="col-xs-12 col-sm-7">
				<div class="clearfix">
					<input type="text" name="press.seq" id="press_seq" onKeyUp="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')"
						class="input-mini" />
				</div>
			</div>
		</div>
		<div class="space-2"></div>

		<div class="form-group">
			<label class="control-label col-xs-12 col-sm-5 no-padding-right"
				for="press_remark">备注:</label>
			<div class="col-xs-12 col-sm-7">
				<div class="clearfix">
					<textarea class="col-xs-12 col-sm-12" name="press.remark"
						id="press_remark"></textarea>
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
<script type="text/javascript" src="js/press-modify.js"></script>
<script type="text/javascript" src="../js/commonDiyCss.js"></script>