<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/include-path.jsp" %>
<%
	String schId = request.getParameter("schId");
%>
<script type="text/javascript">
	var _schId = "<%=schId%>";
</script>
<div style="width: 100%;">
	<div style="margin-top: 10px;">
		<div class="form-group">
			<label></label> <select id="search_id" name="searchBy"
				style="width: 150px;">
					<option value="1">教师姓名</option>
					<option value="2">教师工号</option>
					<option value="3">手机号码</option>
					<option value="4">部门名称</option>
				</select>
				<input type="text" class="input" id="searchText" />
				<input type="button" class="btn btn-primary dy-search-button"
					id="find_btn" value="查 询" onclick="javascript:findByParams();"/>
			</div>
	</div>
	<table id="empe-tab" style="width: 100%;"></table>
	<div id="empe-pager"></div>
	<input type="hidden" id="selected">
	<script type="text/javascript" src="<%=path %>/admin/common/teacherChoose/js/show_teacher.js"></script>
	
</div>