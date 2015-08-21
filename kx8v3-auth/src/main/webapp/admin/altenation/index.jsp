<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="cn">
<head>
	<meta charset="utf-8" />
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<%@include file="/include-path.jsp"%>
	<%@include file="/common/include-bace.jsp"%>
	<link href="<%=path%>/admin/css/dy-admin-default.css" rel="stylesheet">
	<script src="<%=__BacePath%>/public/lib/jquery.layout/jquery.layout-1.4.js"></script>
	<link rel="stylesheet" href="<%=rootPath%>/public/lib/jquery.layout/jquery.layout-1.4.css" />
	<title>教师异动管理</title>
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
					
					    <div class="alert alert-info" role="alert">			
							<p>
		                                         教师调岗说明：<br />
		                     1、教师调岗仅用于大洋软件技术有限公司合作学校之间的调岗；本系统之间的教师调岗仅用于教师数据的变更，不代表教师编制的调动。<br/>
		                     2、校外调岗流程：转出方学校填写调岗申请 —— 接收方学校接收申请并给分配部门 —— 调岗成功。</p>	
						</div>
						<form name="form-search" id="form-search">
						<div class="panel panel-default">
							<div class="panel-heading">
								<h4 class="panel-title"><b>请输入查询条件:</b></h3>
							</div>									
							<div class="panel-body">
								 <div class="col-xs-12">
									<div class="form-group col-md-1" align="right">   <label>部门：</label> </div>
									<div class="form-group col-md-2" >										
										<div class="col-xs-12 col-sm-12 input-group">
											<input type="text" class="form-control" name="parentName" id="dept_parentName"  readonly="readonly">
										    <span class="input-group-addon">
												<i class="icon-hand-left bigger-110" id="btn_select_dept">部门</i>													
											</span>
											<div style="display:none">		
												<input type="text"  id = "dept_pid" />
											</div>												
										</div>										
									</div>
								    <div class="form-group col-md-1"  align="right">   <label>账号：</label> </div>
									<div class="form-group col-md-2" >  <input type="text" class="input" id="account" /> 	</div>
									<div class="form-group col-md-1"  align="right">   <label>姓名：</label> </div>
									<div class="form-group col-md-2" >  <input type="text" maxlength="20" class="input" name="name" id="name" /> 	</div>				      
					              </div>		
					              <div class="col-xs-12">	 	
									<div class="form-group col-md-1"  align="right">   <label>手机号码：</label> </div>
									<div class="form-group col-md-2" >  <input type="text" class="input" name="telphone" id="telphone" /> 	</div>
								    <div class="form-group col-md-1"  align="right">   <label>教师状态：</label> </div>
									<div class="form-group col-md-2" >  
										<select id="status" style="width:155px">
										<option value="">请选择</option>
										<c:forEach var="dic" items="${empStatus }">
											<option value="${dic.value }">${dic.name }</option>
										</c:forEach>
										</select> 	
									</div>
									<div class="form-group col-md-2"  align="center"> <input type="button" class="btn btn-primary dy-search-button" id="find_btn" value="查 询" /> 
										<button type="button" class="btn btn-primary dy-search-button" id="reset_btn" >重置</button> 
									</div>								  		      
								 </div> 
							</div>
						</div>
						</form>    	
						<div class="form-group col-md-6" >
							<dytags:authBtn name="校内调岗" code="innerAlter" id="inneralter_btn" btnClass="btn btn-info" lableClass="icon-pencil bigger-125" />
							<dytags:authBtn name="返聘" code="repeat" id="repeatAlter_btn" btnClass="btn btn-repeat" lableClass="icon-pencil bigger-125" />
							<dytags:authBtn name="其他异动" code="otherAlter" id="otherAlter_btn" btnClass="btn btn-warning" lableClass="icon-pencil bigger-125" />
							<dytags:authBtn name="校外调岗" code="outerAlter" id="outerAlter_btn" btnClass="btn btn-danger" lableClass="icon-pencil bigger-125" />
							 
						</div>
						
						<!-- 课时列表 -->
						<div class="col-xs-12" style="overflow-x:hidden">
							<table id="altenation-table"></table>
							<div id="altenation-pager"></div>
						</div>
					
					</div>
					</div>
			</div>
		</div>
	</div>
	<%@include file="/admin/common/common-setting.jsp" %>
	<script type="text/javascript">
	var _creator=1;
	var _schId=0;
	</script>
	<script type="text/javascript" src="js/index.js"></script>
	
</body>
</html>