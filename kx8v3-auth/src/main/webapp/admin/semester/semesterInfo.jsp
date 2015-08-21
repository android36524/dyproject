<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>	
<div>
		<form class="form-horizontal dy-dialog-form" id="semester_form" method="post">
			<div class="form-group">
				<label class="control-label col-xs-12 col-sm-5 no-padding-right" for="semester_schYear">学年:</label>
				<div class="col-xs-12 col-sm-7">
					<div class="clearfix">
						<select name="semester.schYear" id="semester_schYear">
						 <c:forEach var="dic" items="${schLists}">
						<option value="${dic.value}">${dic.name}</option>
						 </c:forEach>
						</select>
					</div>
				</div>
			</div>
			<div class="space-2"></div>
		
			<div class="form-group">
				<label class="control-label col-xs-12 col-sm-5 no-padding-right" for="semester_semester">学期:</label>
				<div class="col-xs-12 col-sm-7">
					 <div class="clearfix">
					 	<select name="semester.semester" id="semester_semester">
						 <c:forEach var="dic" items="${semLists}">
						<option value="${dic.value}">${dic.name}</option>
						 </c:forEach>
						</select>
					 </div>
				</div>
			</div>
			<div class="space-2"></div>
			<div class="form-group">
				<label class="control-label col-xs-12 col-sm-5 no-padding-right" for="semester_name">学期名称:</label>
				<div class="col-xs-12 col-sm-7">
					 <div class="clearfix">
					 	<input type="text" name="semester.name" readonly id="semester_name" class="col-xs-12 col-sm-12" />
					 </div>
				</div>
			</div>
			<div class="space-2"></div>
			<div class="form-group">
				<label class="control-label col-xs-12 col-sm-5 no-padding-right my-mustlabel" for="semester_startDate">学期时间:</label>
				<div class="col-xs-12 col-sm-7 input-group">
							
						<span class="input-group-addon">
							<i class="icon-calendar bigger-110"></i>
						</span><input class="form-control" type="text" name="semester.startDate" id="semester_startDate" />
					<input type="hidden" name="semester.id"  id="semester_id" class="col-xs-12 col-sm-12" />
					<input type="hidden" name="semester.schId"  id="semester_schId" class="col-xs-12 col-sm-12" />
					<input type="hidden" name="semester.isCur"  id="semester_isCur" class="col-xs-12 col-sm-12" />
				</div>
			</div>
			<div class="space-2"></div>			
			<div class="form-group">
				<label class="control-label col-xs-12 col-sm-5 no-padding-right my-mustlabel" for="semester_endDate">到:</label>
				<div class="col-xs-12 col-sm-7 input-group">
							
						<span class="input-group-addon">
							<i class="icon-calendar bigger-110"></i>
						</span><input class="form-control" type="text" name="semester.endDate" id="semester_endDate" />
					
				</div>
			</div>
			<div class="space-2"></div>	
			
		</form>
</div>
<script type="text/javascript">
	var _schId  =parent._schId;
	var _curid=${curId};
	var _notCurValue =${notCurSem.value};
	$(function(){
		
		//时间选择
		$('#semester_startDate,#semester_endDate').datepicker({
			language:'zh-CN',
			autoclose:true,
			pickTime: false,
			todayBtn: true,
			format:'yyyy-mm-dd hh:ii:ss'
		}).prev().on(ace.click_event, function(){
			$(this).next().focus();			
		});
		$("#semester_schId").val(_schId);
		$("#semester_schYear").change(function(){			
			setDefaultName();			
		});
		$("#semester_semester").change(function(){
			setDefaultName();			
		});
		setDefaultName();
		$("#semester_isCur").val(_notCurValue);
		if(_curid){
			$.get(path + "/admin/semester/queryById?id="+_curid,function(ret){
				if(ret){
					$.each(ret,function(k,v){
						$("#semester_form #semester_"+k).val(v);
					});
				}
			});			
		}
		$(".daterangepicker").css({zIndex:9999});
		
		//表单验证
		$("#semester_form").validate({
			errorElement: 'span',
			errorClass: 'help-inline',
			focusInvalid: false,
			rules : {				
				"semester.endDate":{
					required:true,
					DateGreaterTo:"#semester_startDate"
				},
				"semester.startDate":{
					required:true
				}
			},
			messages : {				
				"semester.endDate":{
					DateGreaterTo:"学期开始时间不可大于结束时间"
				},
				"semester.startDate":{
					required:"必须选择开始日期"
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
	
	function setDefaultName(){
		if($("#semester_schYear").val() && $("#semester_semester").val()){
			$("#semester_name").val($("#semester_schYear option:selected").text()+$("#semester_semester option:selected").text());
		}
	}
</script>
<script type="text/javascript" src="../js/commonDiyCss.js"></script>