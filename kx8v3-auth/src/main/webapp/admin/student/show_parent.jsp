<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<div class="row">
	<div class="col-xs-12">
		<div id="query">
			  <label>家长姓名：</label><input type="text" class="input" id="par_name" />
			  <label>家长手机：</label><input type="text" class="input" id="par_phone" />
			  <input type="button" class="btn btn-primary dy-search-button" id="parfind_btn" value="查 询" />
			  <input type="button" class="btn btn-primary dy-search-button " id="choose_saveBtn" value="选择带回">
		</div>			
	</div>
	
	<!-- 家长列表 -->
	<div class="col-xs-12" style="overflow-x:hidden">
		<table id="parent-tab"></table>
			<div id="parent-pager"></div>
	</div>
</div>
<input type="hidden" id="selected"/>
<script type="text/javascript" src="js/show_parent.js"></script>
</body>
</html>