<%@page import="org.apache.commons.lang.StringUtils"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%-- 字典类型添加或修改 --%>
<%
	String dictionaryId = StringUtils.defaultIfEmpty(request.getParameter("id"), "");
	String typeId = StringUtils.defaultIfEmpty(request.getParameter("typeid"), "");
%>
<div>
	<!-- <h3 class="header smaller lighter purple">
		字典添加或修改
	</h3> -->
	<form class="form-horizontal dy-dialog-form" id="dictionary-form" method="post" >
	    
	     <div class="form-group">
			<label class="control-label col-xs-12 col-sm-5 no-padding-right" for="dictionary_name">字典名称:</label>
			<div class="col-xs-12 col-sm-7">
				<div class="clearfix">
					<input type="hidden" name="dictionary.id" id="dictionary_id" />
	    			 <input type="hidden" name="dictionary.dictionaryType" id="dictionary_dictionaryType" />
					<input type="text" name="dictionary.name" id="dictionary_name" class="col-xs-12 col-sm-12" />
				</div>
			</div>
		</div>
		<div class="space-2"></div>
		<div class="form-group">
			<label class="control-label col-xs-12 col-sm-5 no-padding-right" for="dictionary_code">字典编码:</label>
			<div class="col-xs-12 col-sm-7">
				<div class="clearfix">				
					<input type="text" name="dictionary.code" id="dictionary_code" class="col-xs-12 col-sm-12" />
				</div>
			</div>
		</div>
		<div class="space-2"></div>		
		<div class="form-group">
			<label class="control-label col-xs-12 col-sm-5 no-padding-right" for="dictionary_value">字典值:</label>
			<div class="col-xs-12 col-sm-7">
				<div class="clearfix">				
					<input type="text" name="dictionary.value" id="dictionary_value" class="col-xs-12 col-sm-12" />
				</div>
			</div>
		</div>
		<div class="space-2"></div>	
		<div class="form-group">
			<label class="control-label col-xs-12 col-sm-5 no-padding-right" for="dictionary_pdictypeId">上级字典类型:</label>
			<div class="col-xs-12 col-sm-7">
				<div class="clearfix">
					<select class="col-xs-12 col-sm-12" id="dictionary_pdictypeId" name="dictionary.pdictypeId">
					</select>
				</div>
			</div>
		</div>
		<div class="space-2"></div>
		<div class="form-group">
			<label class="control-label col-xs-12 col-sm-5 no-padding-right" for="dictionary_pdicValue">上级字典值:</label>
			<div class="col-xs-12 col-sm-7">
				<div class="clearfix">
					<select class="col-xs-12 col-sm-12" id="dictionary_pdicValue" name="dictionary.pdicValue">
					</select>
				</div>
			</div>
		</div>
		<div class="space-2"></div>	
		<div class="form-group">
			<label class="control-label col-xs-12 col-sm-5 no-padding-right" for="dictionary_code">序号:</label>
			<div class="col-xs-12 col-sm-7">
				<div class="clearfix">				
					<input type="text" name="dictionary.seq" id="dictionary_seq" class="col-xs-12 col-sm-12" />
				</div>
			</div>
		</div>
		<div class="space-2"></div>	
	</form>
</div>
<script type="text/javascript">
	/**
	* 根据字典类型加载字典
	*/
	
	function loadDicByType(){
		var typeid  = $("#dictionary_pdictypeId").val();
		$.ajaxSetup({async : false});
		if(typeid && typeid>=0){						
			$.get(path + "/admin/dictionary/query",{"typeid":typeid},function(dicList){
				if(dicList){
					var type_parent_dom = $("#dictionary_pdicValue");
					type_parent_dom.empty();
					type_parent_dom.append("<option value='-1'>--请选择--</option>");
					for (var i = 0; i < dicList.length; i++) {
						var typeObj = dicList[i];
						type_parent_dom.append("<option value='"+typeObj.value+"'>"+typeObj.name+"</option>");
					}
				}
			});
		}
		$.ajaxSetup({async : true});
	}

	$(function(){
		
		//加载上级字典信息
		$.ajaxSetup({async : false});
		$.get(path + "/admin/dictionary/listDicType",function(typeList){
			if(typeList){
				var type_parent_dom = $("#dictionary_pdictypeId");
				type_parent_dom.empty();
				type_parent_dom.append("<option value='-1'>--请选择--</option>");
				for (var i = 0; i < typeList.length; i++) {
					var typeObj = typeList[i];
					type_parent_dom.append("<option value='"+typeObj.id+"'>"+typeObj.name+"</option>");
				}				
				
				$("#dictionary_pdictypeId").on("change", function() {
					loadDicByType();
				});				
			}
		});
		
		//加载已有的字典数据
		var _dictionaryId = "<%=dictionaryId%>";
		if(_dictionaryId){
			$.get(path + "/admin/dictionary/queryById?id="+_dictionaryId,function(ret){
				$.each(ret,function(k,v){
					$("#dictionary-form #dictionary_"+k).val(v);
				});	
				loadDicByType();
				
				$("#dictionary-form #dictionary_pdicValue").val(ret["pdicValue"]);
			});			
		}
		$.ajaxSetup({async : true});
		var _typeId = "<%=typeId%>";
		if(_typeId){			
			$("#dictionary-form #dictionary_dictionaryType").val(_typeId);
		}
		
		//表单验证
		$("#dictionary-form").validate({
			errorElement: 'span',
			errorClass: 'help-inline',
			focusInvalid: false,
			rules : {
				"dictionary.name" : {
					required: true
				},
				"dictionary.code" : {
					required: true
				},
				"dictionary.value" : {
					required: true,
					integer:true
				}
			},
			messages : {
				"dictionary.name" : {
					required : "请输入字典类型名称"
				},
				"dictionary.code" : {
					required : "请输入字典类型编码"
				},
				"dictionary.value" : {
					required:  "请输入字典值",
					integer:"请输入数字"
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