<%@page import="org.apache.commons.lang.StringUtils"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%-- 学阶添加&修改 --%>
<%
	String id = StringUtils.defaultIfEmpty(request.getParameter("id"), "");
%>
<script type="text/javascript">
var _id = "<%=id%>";
</script>
<div>
		<form class="form-horizontal dy-dialog-form" id="subject_form" method="post">
			<div class="form-group">
				<label class="control-label col-xs-12 col-sm-5 no-padding-right my-mustlabel" for="subject_subjectId">科目名称:</label>
				<div class="col-xs-12 col-sm-7">
					<div class="clearfix">
					<select name="subject.subjectId"  id="subject_subjectId">
					 <c:forEach var="om" items="${otherModels}">
						<option value="${om.id}" code="${om.code }" >${om.name}</option>
					</c:forEach>
					</select>
						
						<input type="hidden" name="subject.code"  id="subject_code" class="col-xs-12 col-sm-12" />
						<input type="hidden" name="subject.flag"  id="subject_flag" class="col-xs-12 col-sm-12" />
						<input type="hidden" name="subject.schId"  id="subject_schId" class="col-xs-12 col-sm-12" />
						<input type="hidden" name="subject.creator"  id="subject_creator" class="col-xs-12 col-sm-12" />
						<input type="hidden" name="subject.id"  id="subject_id" class="col-xs-12 col-sm-12" />
					</div>
				</div>
			</div>
			<div class="space-2"></div>
		
			<div class="form-group">
				<label class="control-label col-xs-12 col-sm-4 no-padding-right my-mustlabel" for="subject_name">科目别名:</label>
				<div class="col-xs-12 col-sm-3">
					 <div class="clearfix">
					 	<input type="text" name="subject.name" id="subject_name" class="col-xs-12 col-sm-12" />
					 </div>
				</div>
				<label class="col-xs-12 col-sm-5"><small>(科目别名,每个学校可自行设置)</small></label>
			</div>
			<div class="space-2"></div>
			
			<div class="form-group">
				<label class="control-label col-xs-12 col-sm-5 no-padding-right" for="subject_seq">排序:</label>
				<div class="col-xs-12 col-sm-7">
					<div class="clearfix">
						<input type="text" name="subject.seq" id="subject_seq" class="col-xs-12 col-sm-12 spinner-input" />
					</div>
				</div>
			</div>
			<div class="space-2"></div>
			
			
		</form>
</div>
<script type="text/javascript">
	var _flag = ${flag};
	var _curid=${curId};

	$(function(){

	
		$('#subject_seq').ace_spinner({
			value:1,
			min:1,
			max:200,
			step:1, 
			btn_up_class:'btn-info' ,
			btn_down_class:'btn-info'
		}).on('change', function(){
			
		});
		var _schId = parent._schId;
		var _userId = 1;	
		var _schFlagValue = ${schEnum.value};
		
		$("#subject_flag").val(_schFlagValue);
		$("#subject_schId").val(_schId);
		$("#subject_creator").val(_userId);
		
		
		if(_curid){
			$.get(path + "/admin/subject/queryById?id="+_curid,function(ret){
				if(ret){
					$.each(ret,function(k,v){
						$("#subject_form #subject_"+k).val(v);
					});
				}
			});	
			$("#subject_subjectId").attr("disabled","disabled");	
		}else{
			$("#subject_subjectId").change(function(){			
				$("#subject_code").val($("#subject_subjectId option:selected").attr("code"));	
				$("#subject_name").val($("#subject_subjectId option:selected").text());	
			})
			$("#subject_subjectId").change();
		}
		
		//表单验证
		$("#subject_form").validate({
			errorElement: 'span',
			errorClass: 'help-inline',
			focusInvalid: false,
			rules : {
				"subject.name" : {
					required: true,
					maxlength:20,
					minlength:2
				}
			},
			messages : {
				"subject.name" : {
					required : "请输入科目名称",
					maxlength:"请最多输入20个字符",
					minlength:"请至少输入两个字符"
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
		
		
	});
	
</script>
<script type="text/javascript" src="../js/commonDiyCss.js"></script>