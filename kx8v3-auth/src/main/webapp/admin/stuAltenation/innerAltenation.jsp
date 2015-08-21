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
<tr><td>序号</td><td>姓名</td><td>所属班级</td><td>状态</td><td>操作</td></tr>
</thead>
<tbody>
 <c:forEach var="stu"  varStatus="status" items="${stuList}">
<tr id="${stu.id}"><td>${status.index+1}</td><td>${stu.name }</td><td>${stu.gradeNameAndSc }</td><td>${stu.status_showname }</td><td><input type="button"  class="btn btn-default btn-xs" value="移出" onclick="removeId(this)" /></td></tr>
</c:forEach>

</tbody>
</table>
		<form class="form-horizontal dy-dialog-form" id="alter_form" method="post">
			<div class="form-group">
				<label class="control-label col-xs-12 col-sm-5 no-padding-right my-mustlabel" for="toClass">调往班级:</label>
				<div class="col-xs-12 col-sm-7">
					<div class="clearfix">
						<select name="toGrade" id="toGrade">
						
						</select>
						<select name="toClass" id="toClass">
						
						</select>
					</div>
				</div>
			</div>
			<div class="space-2"></div>		
	
			<div class="form-group">
			<label class="control-label col-xs-12 col-sm-5 no-padding-right"
				for="schoolExt_schoolHis">异动说明:</label>
			<div class="col-xs-12 col-sm-7">
				<div class="clearfix">
					<textarea rows="8px" cols="60px" 
						name="remark" id="remark"></textarea>
				</div>
			</div>
		</div>
		
		<div class="space-2">
		<input type="hidden" name="stuIds" id="stuIds">
        <input type="hidden" name="className" id="className">
		<input type="hidden" name="shcId" id="schId">
		<input type="hidden" name="creator" id="creator">
		</div>
		</form>
</div>
<script type="text/javascript">
	var _schId = parent._schId;
	$(function(){
		reloadGrade();
		$("#toGrade").change(function(){
			reloadClass();
		});
		$("#toGrade").change();
		function reloadGrade(){
			$("#toGrade").empty();
			$.post(path + "/admin/grade/listPage",{schId:_schId,page:1,rows:30,flag:2,gradeName:""},function(data){				
				$("#toGrade").append("<option value=''>请选择</option>");
				$(data.rows).each(function(k,d){
					$("#toGrade").append("<option value='"+d.id+"'>"+d.name+"("+d.stageName+")</option>");
				});
				$("#toGrade").change();
			});			
		}
		function reloadClass(){
			$("#toClass").empty();
			var _gradeId = $("#toGrade").val();
			if(!_gradeId) return;
			$.post(path + "/admin/class/listClassByPage",{gradeId:_gradeId,page:1,rows:30,className:""},function(data){				
				$("#toClass").append("<option value=''>请选择</option>");
				$(data.rows).each(function(k,d){
					$("#toClass").append("<option value='"+d.id+"'>"+d.name+"</option>");
				});
			});	
		}
		
		$("#toClass").change(function(){			
			$("#className").val($("#toClass option:selected").text());	
		});
		
		//表单验证
		$("#alter_form").validate({
			errorElement: 'span',
			errorClass: 'help-inline',
			focusInvalid: false,
			rules : {
				
				"toClass":{
					required:true
				},
	            "remark" : {
					maxlength : 255
				}
			},
			messages : {
				
				"toClass":{
					required:"请选择班级"
				},
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