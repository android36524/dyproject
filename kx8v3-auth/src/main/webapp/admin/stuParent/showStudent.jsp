<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<div class="row">
	<div class="col-xs-12">
		<div id="query">
			<label>孩子所在年级：</label>
			<select class="" id="stu_gradeId"   style="width: 90px">
				<option value="">-请选择-</option>
			</select>

			<label>孩子所在班级：</label>
			<select class="" id="stu_classId" style="width: 90px">
   				<option value=''>--请选择--</option>
			</select>
			<label>姓名：</label><input type="text" class="input" id="name" />
			<input type="button" class="btn btn-primary dy-search-button" id="stufind_btn" value="查 询" />
			<input type="button" class="btn btn-primary dy-search-button " id="choose_saveBtn" value="选择带回">
		</div>			
	</div>

<!-- 学生列表 -->
	<div class="col-xs-12" style="overflow-x:hidden">
		<table id="student-tab"></table>
		<div id="student-pager"></div>
	</div>
</div>
<input type="hidden" id="selected">
<script type="text/javascript" src="js/showStudent.js"></script>
