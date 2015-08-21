<%@page import="org.apache.commons.lang.StringUtils"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%-- 学阶添加&修改 --%>
<%
	String id = StringUtils.defaultIfEmpty(request.getParameter("id"), "");
%>
<script type="text/javascript">
var _id = "<%=id%>";
</script>
<div>
		<form class="form-horizontal dy-dialog-form" id="stage_form" method="post">
			<div class="form-group">
				<label class="control-label col-xs-12 col-sm-5 no-padding-right my-mustlabel"  for="stage_name">学阶名称:</label>
				<div class="col-xs-12 col-sm-7">
					<div class="clearfix">
						<input type="hidden" name="stage.id" id="stage_id"  >
						<input type="text" name="stage.name"  id="stage_name" class="col-xs-12 col-sm-12" />
					</div>
				</div>
			</div>
			<div class="space-2"></div>
		
			<div class="form-group">
				<label class="control-label col-xs-12 col-sm-5 no-padding-right" for="stage_code">学阶编码:</label>
				<div class="col-xs-12 col-sm-7">
					 <div class="clearfix">
					 	<input type="text" name="stage.code" readonly="readonly" id="stage_code" class="col-xs-12 col-sm-12" />
					 </div>
				</div>
			</div>
			<div class="space-2"></div>
			
			<div class="form-group">
				<label class="control-label col-xs-12 col-sm-5 no-padding-right" for="stage_seq">排序:</label>
				<div class="col-xs-12 col-sm-7">
					<div class="clearfix">
						<input  type="text" name="stage.seq" id="stage_seq" class="input-mini"/>
					</div>
				</div>
			</div>
			<div class="space-2"></div>
			
			<div class="form-group">
				<label class="control-label col-xs-12 col-sm-5 no-padding-right" for="stage_memo">备注:</label>
				<div class="col-xs-12 col-sm-7">
					<div class="clearfix">
						<textarea class="col-xs-12 col-sm-12" name="stage.memo" id="stage_memo"></textarea>
					</div>
				</div>
			</div>
			<div class="space-2"></div>
			
			
		</form>
</div>
<script type="text/javascript" src="js/stage-modify.js"></script>
<script type="text/javascript">
$('#stage_seq').ace_spinner({
	value:1,
	min:1,
	max:200,
	step:1, 
	btn_up_class:'btn-info' ,
	btn_down_class:'btn-info'
}).on('change', function(){
	
});
</script>
<script type="text/javascript" src="../js/commonDiyCss.js"></script>