<%@page import="org.apache.commons.lang.StringUtils"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String sectionName = StringUtils.defaultIfEmpty(request.getParameter("sectionName"),"");
	String sectionId = StringUtils.defaultIfEmpty(request.getParameter("sectionId"),"");
%>
<script type="text/javascript">
	var sectionId = "<%=sectionId%>";
	var sectionName = "<%=sectionName%>";
	
	$("#section_id").val(sectionId);
	$("#section_name").val(sectionName);
</script>
<%@include file="/include-path.jsp"%>
<div>
	<form class="form-horizontal dy-dialog-form" id="editSectionName_form"
		method="post">
		

		<div class="space-2"></div>
		<div class="form-group">
			<label class="control-label col-xs-12 col-sm-5 no-padding-right"
				for="section_id">章节ID:</label>
			<div class="col-xs-12 col-sm-7">
				<div class="clearfix">
					<input type="text" name="section_id" id="section_id" readonly="readonly"
						class="col-xs-20 col-sm-12" />
				</div>
			</div>
		</div>
		
		<div class="space-2"></div>
		<div class="form-group">
			<label class="control-label col-xs-12 col-sm-5 no-padding-right my-mustlabel"
				for="section_name">章节名称:</label>
			<div class="col-xs-12 col-sm-7">
				<div class="clearfix">
					<input type="text" name="section_name" id="section_name"
						class="col-xs-20 col-sm-12" />
				</div>
			</div>
		</div>
		
	</form>
	<div class="col-xs-12">
		<table id="grid-table"></table>
		<div id="grid-pager"></div>
	</div>
</div>
<script type="text/javascript" src="../js/commonDiyCss.js"></script>

<script type="text/javascript">
//表单验证
$("#editSectionName_form").validate({
	errorElement: 'span',
	errorClass: 'help-inline',
	focusInvalid: false,
	rules : {
		"section_name" : {
			required: true,
			rangelength : [1,20]
			
		}
	},
	messages : {
		"section_name" : {
			required: "请输入章节名称！",
			
		}
	},
	invalidHandler: function (event, validator) { //display error alert on form submit
		$('.alert-danger', $('.login-form')).show();
	},

	highlight: function (e) {
		$(e).closest('.form-group').removeClass('has-info').addClass('has-error');
	},

	success: function (e) {
		$(e).closest('.form-group').removeClass('has-error').addClass('has-info');
		$(e).remove();
	},

	errorPlacement: function (error, element) {
		if(element.is(':checkbox') || element.is(':radio')) {
			var controls = element.closest('div[class*="col-"]');
			if(controls.find(':checkbox,:radio').length > 1) controls.append(error);
			else error.insertAfter(element.nextAll('.lbl:eq(0)').eq(0));
		}
		else if(element.is('.select2')) {
			error.insertAfter(element.siblings('[class*="select2-container"]:eq(0)'));
		}
		else if(element.is('.chosen-select')) {
			error.insertAfter(element.siblings('[class*="chosen-container"]:eq(0)'));
		}
		else error.insertAfter(element.parent());
	},

	submitHandler: function (form) {
	},
	invalidHandler: function (form) {
	}
});
</script>