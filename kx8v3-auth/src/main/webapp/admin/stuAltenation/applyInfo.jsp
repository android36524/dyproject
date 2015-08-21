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
<h4>已选学生列表：</h4>
<table id="alterlisttable" class="table table-bordered">
<thead>
<tr><td>序号</td><td>学生姓名</td><td>所属班级</td><td>学生状态</td><td>操作</td></tr>
</thead>
<tbody>
 <c:forEach var="emp"  varStatus="status" items="${stuList}">
<tr id="${emp.id}"><td>${status.index+1}</td><td>${emp.name }</td><td>${emp.gradeNameAndSc }</td><td>${emp.status_showname }</td><td><input type="button"  class="btn btn-default btn-xs" value="移出" onclick="removeId(this)" /></td></tr>
</c:forEach>
</tbody>
</table>
		<form class="form-horizontal dy-dialog-form" id="alter_form" method="post">
			<div class="form-group">
				<label class="control-label col-xs-12 col-md-5 no-padding-right my-mustlabel" for="todept">拟转入学校:</label>
				<div class="col-xs-12 col-md-7">
					<div class="row">						
						<select  class="col-md-9 no-padding-left" id="o_provinceId" >
							<option value=''>--请选择--</option>
						</select>
					</div>					
					<div class="space-4"></div>	
					<div class="row">						
						<select  class="col-md-9 no-padding-left" id="o_cityId" >
							<option value=''>--请选择--</option>
						</select>
					</div>	
					<div class="space-2"></div>	
					<div class="row">		
						<select  class="col-md-9 no-padding-left"  id="o_areaId">
							<option value=''>--请选择--</option>
						</select>
					</div>	
					<div class="space-2"></div>	
					<div class="row">		
						<select class="col-md-9 no-padding-left" name="o_school" id="o_school">
							<option value=''>--请选择--</option>
						</select>
					</div>								
				</div>
			</div>
			<div class="space-2"></div>		
			<div class="form-group">
			<label class="control-label col-xs-12 col-md-5 no-padding-right"
				for="schoolExt_schoolHis">转学说明:</label>
			<div class="col-xs-12 col-md-7">			
				<div class="row">
					<textarea rows="6px" cols="60px" 
						name="remark" id="remark"></textarea>
				</div>
			</div>
		</div>		
		<div class="space-2">
		<input type="hidden" name="empIds" id="empIds">
		<input type="hidden" name="schId" id="schId">
		<input type="hidden" name="toSchName" id="toSchName">
		<input type="hidden" name="creator" id="creator">
		<input type="hidden" name="flag" id="flag" value="2">
		<input type="hidden" name="changeType" id="changeType" value = "103">
		</div>
		</form>
</div>
<script type="text/javascript" src="../js/selSchool.js"></script>
<script type="text/javascript">
	
	$(function(){			
		//表单验证
		$("#alter_form").validate({
			errorElement: 'span',
			errorClass: 'help-inline',
			focusInvalid: false,
			rules : {				
				"o_school" : {
	                required : true
	            },
	            "remark" : {
					maxlength : 255
				}
			},
			messages : {				
				"o_school" :"请选择一个学校",
				"remark" : {
					maxlength : "已超过255个字符"
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
	
	function removeId(obj){
		$(obj).parents("tr").remove();		
	}
</script>
<script type="text/javascript" src="../js/commonDiyCss.js"></script>