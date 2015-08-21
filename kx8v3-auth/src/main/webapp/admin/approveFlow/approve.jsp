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
<form class="form-horizontal dy-dialog-form" id="alter_form" method="post">	
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
	<div class="panel panel-default"  id="passcheck">
		<div class="panel-heading">
	   		<h3 class="panel-title">请分配教职工部门职务信息:</h3>
	 	</div>
		<div class="panel-body">
		   	<c:forEach var="user" items="${userList}">
				<div class="form-group">
					<label class="control-label col-xs-12 col-sm-3 no-padding-right" for="todept">教职工姓名:</label>
					<div class="col-xs-12 col-sm-9">
						<div class="clearfix">
							<label> ${user.userName} </label>
							<input type="hidden" value="${user.userId }">
						</div>
					</div>
				</div>
				<div class="form-group">
					<label class="control-label col-xs-12 col-sm-3 no-padding-right" for="todept">调往部门:</label>
					<div class="col-xs-12 col-sm-9">
						<div class="clearfix">
							<select userid="${user.userId }" alterchangeid="${user.id }" name="todept" style="width:100px" id="todept">
								<c:forEach var="dept" items="${deptList}">
									<option value="${dept.id}">${dept.deptName}</option>
								</c:forEach>
							</select>
						</div>
					</div>
				</div>
				<div class="form-group">
					<label class="control-label col-xs-12 col-sm-3 no-padding-right" for="todept">岗位职业:</label>
					<div class="col-xs-12 col-sm-9">
						<div class="clearfix">
							<select userid="${user.userId }" class="col-xs-12 col-sm-12" style="width:100px" id="empe_jobsCareers" dyId="chooseId2" name="jobsCareers">
					        	<c:forEach var="dic" items="${jobList}">
					            	<option value="${dic.value}">${dic.name}</option>
					         	</c:forEach>
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
})	
</script>
