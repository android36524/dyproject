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
	<title>学生转入审批</title>
</head>
<body>
	<%@include file="/admin/common/common-navbar.jsp" %>
	<div class="main-container container-fluid">
		<%@include file="/admin/common/common-leftmenu.jsp" %>
		<div class="main-content">
			<%@include file="../common/common-breadcrumbs.jsp" %>
			<div class="page-content">
				<div style="width: 100%;height: 700px;" id="div_main">
					<div class="ui-layout-west jl_pane jl_ui-layout-pane" style="border: 1px solid #ccc;"> 
						<%@include file="/admin/common/orgTree/index.jsp" %>
					</div>
					<div class="ui-layout-center jl_ui-layout-pane">				
						<div >
						<div class="alert alert-info" role="alert">			
						<p>
转学申请说明：<br />
1、转学申请仅用于大洋软件技术有限公司合作学校之间的学生转学；本系统之间的学生转学仅用于学生数据的变更，不代表学生学籍的转出（转入）。<br />
2、转学流程：转出方学校填写转学申请 —— 接收方学校接收申请并给分配班级 —— 转学成功。

</p>	
						</div>
						    <div class="space-2"></div>		
							<form class="form-horizontal dy-dialog-form" id="alter_form" method="post">
								<dytags:hasAuth />
								<div class="col-xs-12">
									<div class="form-group col-md-1" align="right">   <label>处理状态：</label> </div>
									<div class="form-group col-md-2" > 
									    <div class="col-xs-12  input-group"> 
											<select id="status">
												<option value="">请选择</option>
												<c:forEach var="dic" items="${statusList }">
													<option value="${dic.value }">${dic.name }</option>
												</c:forEach>
											</select> 	
									    </div>
									 </div>  	  	
									<div class="form-group col-md-1" align="right">   <label>申请方名称：</label> </div>
									<div class="form-group col-md-2" > 
									    <div class="col-xs-12  input-group"> <input type="text" maxlength="30" class="input" id="schName" /> 	
									   </div>
									 </div>  								
									<div class="form-group col-md-1"  align="right">   <label>学生姓名：</label> </div>
									<div class="form-group col-md-2" > 
									    <div class="col-xs-12  input-group"> <input type="text" maxlength="20" class="input" id="teaName" /> 	
										</div>
									</div>	
									<div class="form-group col-md-2"  align="center"> 
										<input type="button" class="btn btn-primary dy-search-button" id="find_btn" value="查 询" />  
									</div>
								</div>	
							</form>	
						</div>			
							<div class="col-xs-12" style="overflow-x:hidden">
								<table id="altenation-table"></table>
								<div id="altenation-pager"></div>
							</div>
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