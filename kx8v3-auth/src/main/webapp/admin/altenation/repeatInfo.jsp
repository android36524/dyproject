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
			<div class="space-2"></div>
			<div class="panel panel-default">
				<div class="panel-heading">
   				 	<h3 class="panel-title">请填写异动类别详情:</h3>
 				</div>
			    <div class="panel-body">
				   <div class="form-group">
					<label class="control-label col-xs-12 col-sm-5 no-padding-right" for="alterOtherType">异动类别:</label>
					<div class="col-xs-12 col-md-7">
						 <div class="clearfix">
						 	<select name="alterOtherType" id="alterOtherType">
						
							<option value="${empStatus.value}">${empStatus.name}</option>
							
							</select>
						 </div>
					</div>
				    </div>
				    <div class="form-group">
					<label class="control-label col-xs-12 col-sm-5 no-padding-right" for="schoolExt_schoolHis">异动说明:</label>
					<div class="col-xs-12 col-md-4">
						<div class="clearfix">
							<textarea rows="8px" cols="50px" name="remark" id="remark"></textarea>
						</div>
					</div>
			  	</div>
			</div>			
		</div>
		<div class="space-2"></div>
		<div class="panel panel-default">
			<div class="panel-heading">
   			 	<h3 class="panel-title">请分配教职工部门职务信息:</h3>
 			</div>
		    <div class="panel-body">
			   <div class="form-group">
					<label class="control-label col-xs-12 col-sm-5 no-padding-right" for="todept">调往部门:</label>
					<div class="col-xs-12 col-sm-7">
						<div class="clearfix">
							<select name="todept" id="todept">
							 <c:forEach var="dept" items="${deptList}">
								<option value="${dept.id}">${dept.deptName}</option>
							 </c:forEach>
							</select>
						</div>
					</div>
				</div>
				<div class="form-group">
					<label class="control-label col-xs-12 col-sm-5 no-padding-right" for="todept">岗位职业:</label>
					<div class="col-xs-12 col-sm-7">
						<div class="clearfix">
							 <select class="col-xs-12 col-sm-12" id="empe_jobsCareers" dyId="chooseId2" name="jobsCareers">
				                <c:forEach var="dic" items="${jobList}">
				                  <option value="${dic.value}">${dic.name}</option>
				                </c:forEach>
				              </select>
						</div>
					</div>
				</div>
				<div class="form-group">
					<label class="control-label col-xs-12 col-sm-5 no-padding-right" for="todept">分配角色:</label>
					<div class="col-xs-12 col-sm-7">
						<div class="clearfix">
							
						</div>
					</div>
				</div>					   
			</div>			
		</div>
		
		<div class="space-2">
		<input type="hidden" name="empIds" id="empIds"> 
		<input type="hidden" name="deptName" id="deptName">
		<input type="hidden" name="shcId" id="schId">
		<input type="hidden" name="creator" id="creator">
		</div>
		</form>
</div>
<script type="text/javascript">
$(function(){
	$("#todept").change(function(){			
		$("#deptName").val($("#todept option:selected").text());	
	});
	$("#todept").change();
	function removeId(obj){
		$(obj).parents("tr").remove();		
	}
})	
</script>
