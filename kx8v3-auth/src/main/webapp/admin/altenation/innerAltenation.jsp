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
<h4>已选教职工列表：</h4>
<table id="alterlisttable" class="table table-bordered">
<thead>
<tr><td>序号</td><td>教职工姓名</td><td>所在部门</td><td>教师状态</td><td>操作</td></tr>
</thead>
<tbody>
 <c:forEach var="emp"  varStatus="status" items="${empList}">
<tr id="${emp.id}"><td>${status.index+1}</td><td>${emp.name }</td><td>${emp.deptName }</td><td>${emp.status_showname }</td><td><input type="button"  class="btn btn-default btn-xs" value="移出" onclick="removeId(this)" /></td></tr>
</c:forEach>

</tbody>
</table>
		<form class="form-horizontal dy-dialog-form" id="alter_form" method="post">
			<div class="form-group">
				<label class="control-label col-xs-12 col-sm-5 no-padding-right my-mustlabel" for="todept">调往部门:</label>
				<div class="col-xs-12 col-sm-7">
					
					<div class="col-xs-12 col-sm-12 input-group">				
						<input type="text" class="form-control" name="deptName" id="deptName"  readonly="readonly">
						<span class="input-group-addon">
							<i class="icon-hand-left bigger-110" id="btn_selectDeptName">部门</i>													
						</span>
							
							<input type="hidden" name="todept" id ="todept" />
						
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
		<input type="hidden" name="empIds" id="empIds">
		<input type="hidden" name="shcId" id="schId">
		<input type="hidden" name="creator" id="creator">
		</div>
		</form>
</div>
<script type="text/javascript">
	
	$(function(){		
		
		//表单验证
		$("#alter_form").validate({
			errorElement: 'span',
			errorClass: 'help-inline',
			focusInvalid: false,
			rules : {				
				"todept" : {
	                required : true
	            },
			},
			messages : {				
				"todept" : {
	                required : "请选择部门"
	            },
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
		$("#btn_selectDeptName").on(ace.click_event, function() {
		  	var orgId = _schId;
		  	var _params = {
		   		"orgId" : orgId,
		   	};		  	
		   	commonJs.showDeptDialog(_params,function(dept){
		   		$("#todept").val(dept.deptId);
				$("#deptName").val(dept.deptName);
		   	});
		});
	});
	
	function removeId(obj){
		$(obj).parents("tr").remove();		
	}
</script>
<script type="text/javascript" src="../js/commonDiyCss.js"></script>