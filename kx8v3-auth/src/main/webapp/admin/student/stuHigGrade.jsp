<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="cn">
<head>
	<meta charset="utf-8" />
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<%@include file="/include-path.jsp"%>
	<%@include file="/common/include-bace.jsp"%>
	<link rel="stylesheet" href="<%=rootPath%>/public/bootstrap/ace/css/chosen.css" />
	<script src="<%=__BacePath%>/public/bootstrap/ace/js/chosen.jquery.min.js"></script>
	
	<script src="<%=__BacePath%>/public/lib/jquery.layout/jquery.layout-1.4.js"></script>
	<link rel="stylesheet" href="<%=rootPath%>/public/lib/jquery.layout/jquery.layout-1.4.css" />
	
	<link href="<%=path%>/admin/css/dy-admin-default.css" rel="stylesheet">
	<title>学生升学查询</title>
	<style type="text/css">
		.modal-dialog{
			width: 900px;
		}
		
	</style>
	<script type="text/javascript">
	$(document).ready(function () {
		$('#div_main').layout({ 
			applyDefaultStyles: true,
			west : {
				header : "组织机构树",
				size : 260
			}
		});
	});
	var _schId= "";
	</script>
</head>
<body>
	<%@include file="/admin/common/common-navbar.jsp" %>
	<div class="main-container container-fluid">
		<%@include file="/admin/common/common-leftmenu.jsp" %>
		<div class="main-content">
			<%@include file="../common/common-breadcrumbs.jsp" %>
			<div class="page-content">
				<div style="width: 100%;height: 690px;" id="div_main">
					<div class="ui-layout-west jl_pane jl_ui-layout-pane" style="border: 1px solid #ccc;"> 
						<%@include file="/admin/common/orgTree/index.jsp" %>
					</div>
					<div class="ui-layout-center jl_ui-layout-pane">
						<div>
							<form name="form-search" id="form-search">
								
								<div class="panel panel-default">
									<div class="panel-body">
										<div class="col-xs-12">
											<div class="form-group col-xs-1" align="right">
												<label>原年级：</label>
											</div>
											<div class="form-group col-xs-2">
												<select class="" id="gradeId" dyId="selectId1" style="width: 90px">
													<option value=''>--请选择--</option>
												</select>
											</div>
											<div class="form-group col-xs-1" align="right">
												<label>学生姓名：</label>
											</div>
											<div class="form-group col-xs-2">
												<input type="text" class="input" id="name" maxlength="20"  />
											</div>
											<div class="form-group col-xs-1" align="right">
												<label>学号：</label>
											</div>
											<div class="form-group col-xs-2">
												<input type="text" class="input" id="studentNo" maxlength="12" onkeyup="value=value.replace(/\D/g,'')"/>
											</div>
										</div>
										<div class="col-xs-12">
											<div class="form-group col-xs-1" align="right">
												<label>家长手机：</label>
											</div>
											<div class="form-group col-xs-2">
												<input type="text" class="input" id="tel" maxlength="11" onkeyup="value=value.replace(/\D/g,'')"/>
											</div>
											<div class="form-group col-xs-1" align="right">
												<label>升学时间：</label>
											</div>
											<div class="form-group col-xs-2">
												<input  type="text" name="startTime" id="startTime" /> ~ 
											</div>
											<div class="form-group col-xs-2">
												<input  type="text" name="endTime" id="endTime" />
											</div>
											<div class="form-group col-xs-2" align="left">
												<input type="button" class="btn btn-primary dy-search-button" id="find_btn" value="查 询" />
											</div>
										</div>
									</div>
								</div>
								
								
							</form>
							<!-- 班级列表 -->
							<div class="col-xs-12" style="padding-left: 0px !important; padding-right: 0px !important;">
								<table id="stuEntrance-table"></table>
								<div id="stuEntrance-pager"></div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<%@include file="/admin/common/common-setting.jsp" %>
	<script type="text/javascript" src="js/stuHigGrade.js"></script>
</body>
</html>
<script type="text/javascript">
$('#startTime').datepicker({
    language:'zh-CN',
    autoclose:true,
    pickTime: false,
    todayBtn: true,
    format:'yyyy-mm-dd hh:ii:ss'
    
}).on('changeDate',function(ev){
	  var startTime = $('#startTime').val();
	  var endTime = $('#endTime').val();
	  if( endTime !=''){
		  if(startTime>endTime){
			   bootbox.alert({ 
		            buttons: {  
		               ok: {  
		                    label: '确认',  
		                    className: 'btn-success'  
		                }  
		            },  
		            message: '开始时间 不能大于结束时间  ！',  
		            callback: function() {  
		                
		            },  
		            title: "提示信息", 
		        });  
			   $("#endTime").focus();
			   $('#startTime').val('');
			   return ;
			  }
	  }
});
    $('#endTime').datepicker({
        language:'zh-CN',
        autoclose:true,
        pickTime: false,
        todayBtn: true,
        format:'yyyy-mm-dd hh:ii:ss'
    }).on('changeDate',function(ev){
    	  var endTime = $('#endTime').val();
    	  var startTime = $('#startTime').val();
    	  if(startTime != ''){
    		  if(startTime>endTime){
    	    	   bootbox.alert({ 
   		            buttons: {  
   		               ok: {  
   		                    label: '确认',  
   		                    className: 'btn-success'  
   		                }  
   		            },  
   		            message: '结束时间 不能小于开始时间  ！',  
   		            callback: function() {  
   		                
   		            },  
   		            title: "提示信息", 
   		        });  
    	    	   $("#startTime").focus();
    	    	   $('#endTime').val('');
    	    	   return ;
    	    	  }
    	  }
    	 });
</script>