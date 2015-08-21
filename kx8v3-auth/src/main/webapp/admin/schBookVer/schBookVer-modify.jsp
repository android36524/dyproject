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

<style type="text/css">
.dy-dialog-form .control-label {
    width: 120px;
}
</style>  
<%@include file="/include-path.jsp"%>
<div>
	<form class="form-horizontal dy-dialog-form" id="schBookVer_form"
		method="post">
		<input type="hidden" name="schBookVer.id" id="schBookVer_id"
			class="col-xs-12 col-sm-12" />

		<div class="space-2"></div>
		<input type="hidden" name="schBookVer.id" id="schBookVer_id">
		<div class="form-group">
			<label class="control-label col-xs-12 col-sm-5 no-padding-right my-mustlabel"
				for="schBookVer_name">教材版本名称:</label>
			<div class="col-xs-12 col-sm-7">
				<div class="clearfix">
					<input type="text" name="schBookVer.name" id="schBookVer_name"
						class="col-xs-12 col-sm-12" />
				</div>
			</div>
		</div>

		<div class="space-2"></div>
		<div class="form-group ">
			<label class="control-label col-xs-12 col-sm-5 no-padding-right" for="schBookVer_pressTime">出版日期:</label>
			<div class="col-xs-12 col-sm-7 input-group bootstrap-timepicker">
				<input name="classHour.id" type="hidden" id="classHour_id" /> 
				<input name="schBookVer.pressTime" id="schBookVer_pressTime" type="text"
					class="form-control date-picker" data-date-format="yyyy-mm-dd">
				<span class="input-group-addon"> <i
					class="icon-calendar bigger-110"></i>
				</span>
			</div>
			
		</div>

		<div class="space-2"></div>
		<div class="form-group">
			<label class="control-label col-xs-12 col-sm-5 no-padding-right"
				for="schBookVer_remark">出版社:</label>
			<div class="col-xs-12 col-sm-7">
				<div class="clearfix">
					<select class="col-xs-12 col-sm-12" id="schBookVer_pressId" name="schBookVer.pressId">
					</select>
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
<script type="text/javascript" src="js/schBookVer-modify.js"></script>
<script type="text/javascript" src="../js/commonDiyCss.js"></script>