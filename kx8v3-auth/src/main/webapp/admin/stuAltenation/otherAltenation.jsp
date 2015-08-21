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
				<label class="control-label col-xs-12 col-sm-5 no-padding-right my-mustlabel" for="alterOtherType">异动类别:</label>
				<div class="col-xs-12 col-sm-7">
					 <div class="clearfix">
					 	<select name="alterOtherType" id="alterOtherType">
						 <c:forEach var="dic" items="${alterList}">
						<option value="${dic.value}">${dic.name}</option>
						 </c:forEach>
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
					<textarea rows="8px" cols="60px" maxlength="255"
						name="remark" id="remark"></textarea>
				</div>
			</div>
		</div>
		
		<div class="space-2">
		<input type="hidden" name="stuIds" id="stuIds">      
		<input type="hidden" name="shcId" id="schId">
		<input type="hidden" name="creator" id="creator">
		</div>
		</form>
</div>
<script type="text/javascript">
	
	function removeId(obj){
		$(obj).parents("tr").remove();		
	}
</script>
<script type="text/javascript" src="../js/commonDiyCss.js"></script>