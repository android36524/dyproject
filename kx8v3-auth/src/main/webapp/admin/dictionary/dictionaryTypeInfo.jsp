<%@page import="org.apache.commons.lang.StringUtils"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%-- 字典类型添加或修改 --%>
<%
	String dictionaryTypeId = StringUtils.defaultIfEmpty(request.getParameter("id"), "");
%>
<div>
	<!-- <h3 class="header smaller lighter purple">
		字典添加或修改
	</h3> -->
	<form class="form-horizontal dy-dialog-form" id="dictionarytype-form" method="post" >
	   
	    <div class="form-group">
			<label class="control-label col-xs-12 col-sm-5 no-padding-right" for="dictionarytype_name">字典类型名称:</label>
			<div class="col-xs-12 col-sm-7">
				<div class="clearfix">
					 <input type="hidden" name="dictionarytype.id" id="dictionarytype_id" />
	    			
					<input type="text" name="dictionarytype.name" id="dictionarytype_name" class="col-xs-12 col-sm-12" />
				</div>
			</div>
		</div>
		<div class="space-2"></div>
	   <div class="form-group">
			<label class="control-label col-xs-12 col-sm-5 no-padding-right" for="dictionarytype_code">字典类型编码:</label>
			<div class="col-xs-12 col-sm-7">
				<div class="clearfix">				
					<input type="text" name="dictionarytype.code" id="dictionarytype_code" class="col-xs-12 col-sm-12" />
				</div>
			</div>
		</div>
		<div class="space-2"></div>			
	</form>
</div>
<script type="text/javascript">
	$(function(){
		//加载已有的菜单数据
		var _dictionaryTypeId = "<%=dictionaryTypeId%>";
		
		if(_dictionaryTypeId){
			$.get(path + "/admin/dictionary/queryDictionaryTypeById?id="+_dictionaryTypeId,function(ret){
				$.each(ret,function(k,v){
					
					$("#dictionarytype-form #dictionarytype_"+k).val(v);
				});
				
			});
			
		}
		
		//表单验证
		$("#dictionarytype-form").validate({
			errorElement: 'span',
			errorClass: 'help-inline',
			focusInvalid: false,
			rules : {
				"dictionarytype.name" : {
					required: true
				},
				"dictionarytype.code" : {
					required: true
				}
			},
			messages : {
				"dictionarytype.name" : {
					required : "请输入字典类型名称"
				},
				"dictionarytype.code" : {
					required : "请输入字典类型编码"
				}
			},
			invalidHandler: function (event, validator) { //display error alert on form submit   
				$('.alert-error', $('.login-form')).show();
			},
	
			highlight: function (e) {
				$(e).closest('.control-group').removeClass('info').addClass('error');
			},
	
			success: function (e) {
				$(e).closest('.control-group').removeClass('error').addClass('info');
				$(e).remove();
			},
	
			errorPlacement: function (error, element) {
				if(element.is(':checkbox') || element.is(':radio')) {
					var controls = element.closest('.controls');
					if(controls.find(':checkbox,:radio').length > 1) controls.append(error);
					else error.insertAfter(element.nextAll('.lbl:eq(0)').eq(0));
				}
				else if(element.is('.select2')) {
					error.insertAfter(element.siblings('[class*="select2-container"]:eq(0)'));
				}
				else if(element.is('.chzn-select')) {
					error.insertAfter(element.siblings('[class*="chzn-container"]:eq(0)'));
				}
				else error.insertAfter(element);
			},
	
			submitHandler: function (form) {
			},
			invalidHandler: function (form) {
			}
		});
	});
</script>