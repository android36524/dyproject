<%@page import="org.apache.commons.lang.StringUtils"%>
<%@ page import="com.dayang.commons.util.DaYangCommonUtil" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/include-path.jsp"%>
<%
	String id = StringUtils.defaultIfEmpty(request.getParameter("id"),"");
	String _img = DaYangCommonUtil.getAppProperties("ftp.access.base");
	//System.out.println("------------------========"+id);
%>
<script type="text/javascript">
	var _id = "<%=id%>";
	var _imgpath = "<%=_img%>";
</script>
<style type="text/css">
	.modal-dialog {
		width: 600px !important;
	}
</style>
<div>
	<form class="form-horizontal dy-dialog-form" id="schoolBook_form"
		method="post">
		<input type="hidden" name="schoolBook.id" id="schoolBook_id"
			class="col-xs-20 col-sm-12" />

		<div class="space-2"></div>
		<div class="form-group">
			<label class="control-label col-xs-12 col-sm-5 no-padding-right my-mustlabel"
				for="schoolBook_name">教材版本:</label>
			<div class="col-xs-12 col-sm-7">
				<div class="clearfix">
					<select class="col-xs-12 col-sm-12" id="schoolBook_schBookVerId"
						name="schoolBook.schBookVerId">
					</select>
				</div>
			</div>
		</div>

		<div class="space-2"></div>
		<div class="form-group">
			<label class="control-label col-xs-12 col-sm-5 no-padding-right my-mustlabel"
				for="schoolBook_name">所属学阶:</label>
			<div class="col-xs-12 col-sm-7">
				<div class="clearfix">
					<select class="col-xs-12 col-sm-12" id="schoolBook_stageId"
						name="schoolBook.stageId">
						<option value=''>---请选择---</option>
					</select>
				</div>
			</div>
		</div>

		<div class="space-2"></div>
		<div class="form-group">
			<label class="control-label col-xs-12 col-sm-5 no-padding-right my-mustlabel"
				for="schoolBook_seq">所属年级:</label>
			<div class="col-xs-12 col-sm-7">
				<div class="clearfix">
					<select class="col-xs-12 col-sm-12" id="schoolBook_gradeId"
						name="schoolBook.gradeId">
						<option value=''>---请选择---</option>
					</select>
				</div>
			</div>
		</div>

		<div class="space-2"></div>
		<div class="form-group">
			<label class="control-label col-xs-12 col-sm-5 no-padding-right my-mustlabel"
				for="schoolBook_remark">所属科目:</label>
			<div class="col-xs-12 col-sm-7">
				<div class="clearfix">
					<select class="col-xs-12 col-sm-12" id="schoolBook_subjectId"
						name="schoolBook.subjectId">
						<option value=''>---请选择---</option>
					</select>
				</div>
			</div>
		</div>
		
		<div class="space-2"></div>
		<div class="form-group">
			<label class="control-label col-xs-12 col-sm-5 no-padding-right my-mustlabel"
				for="schoolBook_volume">上下册:</label>
			<div class="col-xs-12 col-sm-7">
				<div class="clearfix">
					<select class="col-xs-12 col-sm-12" id="schoolBook_volume"
						name="schoolBook.volume">
						<option value='100'>不分册</option>
						<option value='101'>上册</option>
						<option value='102'>下册</option>
					</select>
				</div>
			</div>
		</div>

		<div class="space-2"></div>
		<div class="form-group">
			<label class="control-label col-xs-12 col-sm-5 no-padding-right"
				for="schoolBook_remark">封面图片</label>
			<div class="col-xs-12 col-sm-7">
				<div class="col-xs-2">
					<div class="row" style="height: 140px;">
						<img style="height: 138px; width: 120px; border: 1px solid #ccc;"
							id="imageUrl" src="<%=path%>/css/img/defaultpic.gif" />
					</div>
					<div class="row">
						<div class="col-xs-12" style="padding-left: 0px;">
							<input type="hidden" name="schoolBook.image" id="schoolBook_image" />
							<div class="input-group-btn">
								<div id="imageUpload"></div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</form>
	<div class="col-xs-12">
		<table id="grid-table"></table>
		<div id="grid-pager"></div>
	</div>
</div>
<script type="text/javascript" src="js/schoolBook-modify.js"></script>
<script type="text/javascript" src="../js/commonDiyCss.js"></script>