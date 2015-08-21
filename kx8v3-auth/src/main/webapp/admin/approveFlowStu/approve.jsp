<%@page import="org.apache.commons.lang.StringUtils"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
	String id = StringUtils.defaultIfEmpty(request.getParameter("id"), "");
%>
<script type="text/javascript">
var _id = "<%=id%>";
</script>
<form class="form-horizontal dy-dialog-form" id="approve_form" method="post">	
	<div>			
		<div class="space-2"></div>
	   	<div class="panel panel-default">
			<div class="panel-heading">
	   		 	<h3 class="panel-title">审批信息:</h3>
	 		</div>
			<div class="panel-body">
			  	<div class="form-group">
					<label class="control-label col-xs-12 col-sm-5 no-padding-right" for="alterOtherType">审批状态:</label>
					<div class="col-xs-12 col-md-7">
						<div class="clearfix">
							<input type="radio"  value="2" name="ck_approve" checked="true"> 通过
							<input type="radio"  value="3" name="ck_approve" > 不通过
						</div>
					</div>
				</div>				    
			</div>
		</div>			
	</div>
	<div class="space-2"></div>
	<div class="panel panel-default" id="passcheck">
		<div class="panel-heading">
	   		<h3 class="panel-title">请分配学生班级:</h3>
	 	</div>
		<div class="panel-body">
		   	<c:forEach var="user" items="${userList}" varStatus="uind">
				<div class="form-group">
					<label class="control-label col-xs-12 col-sm-3 no-padding-right" for="todept">学生姓名:</label>
					<div class="col-xs-12 col-sm-9">
						<div class="clearfix">
							<label> ${user.userName} (原就读班级：${user.gradeNameAndSc })</label>
							<input type="hidden" value="${user.userId }">
						</div>
					</div>
				</div>
				<div class="form-group">
					<label class="control-label col-xs-12 col-sm-3 no-padding-right my-mustlabel" for="todept">分配班级:</label>
					<div class="col-xs-12 col-sm-9">
						<div class="clearfix">
							<select userid="${user.userId }" alterchangeid="${user.id }" name="toGrade"  style="width:100px" id="toGrade">
								<c:forEach var="grade" items="${gradeList}">
									<option value="${grade.id}">${grade.name}</option>
								</c:forEach>
							</select>
							<select userid="${user.userId }" alterchangeid="${user.id }" toclass="1" name="toClass_${uind.index }" style="width:100px" id="toClass_${uind.index }">
								
							</select>
						</div>
					</div>
				</div>				
				<div class="form-group">
					<label class="control-label col-xs-12 col-sm-3 no-padding-right" for="todept">分配角色:</label>
					<div class="col-xs-12 col-sm-7">
						<div class="clearfix">
								
						</div>
					</div>
				</div>							   
			</c:forEach>
		</div>			
	</div>
	<div class="panel panel-default" id="nopasscheck">
		<div class="panel-heading">
	   		<h3 class="panel-title">填写不通过原因:</h3>
	 	</div>
	 	<div class="panel-body">
	 		<div class="form-group">
				<label class="control-label col-xs-12 col-sm-3 no-padding-right" for="todept">不通过原因:</label>
				<div class="col-xs-12 col-sm-9">					
					<div class="row">
						<textarea rows="6px" cols="50px" 
							name="remark" id="remark"></textarea>
					</div>					
				</div>
			</div>	
	 	</div>
	</div>
		
	<div class="space-2">		
		<input type="hidden" name="shcId" id="schId">
		<input type="hidden" name="creator" id="creator">
	</div>
</form>

<script type="text/javascript">
$(function(){
	$("select[name='toGrade']").each(function(){
		$(this).change(function(){
			reloadClass($(this).val(),$(this).attr("userid"));
		});
		$(this).change();
	});
	function reloadClass(gradeId,userId){
		var obj = $("select[name^='toClass'][userid='"+userId+"']");
		obj.empty();		
		var _gradeId = gradeId;
		if(!_gradeId) return;
		$.post(path + "/admin/class/listClassByPage",{gradeId:_gradeId,page:1,rows:30,className:""},function(data){				
			obj.append("<option value=''>请选择</option>");
			$(data.rows).each(function(k,d){
				obj.append("<option value='"+d.id+"'>"+d.name+"</option>");
			});
		});	
	}
	$("input[name='ck_approve']").each(function(){
		$(this).change(function(){
			if($(this).val()==2){
				$("#passcheck").show();
				$("#nopasscheck").hide();
			}else{
				$("#passcheck").hide();
				$("#nopasscheck").show();
			}
		});
	});
	$("#passcheck").show();
	$("#nopasscheck").hide();
	
	var clsRule = {};
	var clsMsg = {};
	$("select[name^='toClass']").each(function(){
		var _name = $(this).attr("name");
		clsRule[_name] = {"required" : true};
		clsMsg[_name] = {"required" : "请选择一个班级"};
	});	
	//表单验证
	$("#approve_form").validate({
		errorElement: 'span',
		errorClass: 'help-inline',
		focusInvalid: false,
		rules : clsRule,
		messages : clsMsg,
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
})	
</script>
<script type="text/javascript" src="../js/commonDiyCss.js"></script>