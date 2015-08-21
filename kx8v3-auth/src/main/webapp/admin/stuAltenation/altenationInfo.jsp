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
<script	src="<%=__BacePath%>/public/lib/jquery.layout/jquery.layout-1.4.js"></script>
<link rel="stylesheet"	href="<%=rootPath%>/public/lib/jquery.layout/jquery.layout-1.4.css" />
<title>学生异动记录</title>
</head>
<body>
	<%@include file="/admin/common/common-navbar.jsp"%>
	<div class="main-container container-fluid">
		<%@include file="/admin/common/common-leftmenu.jsp"%>
		<div class="main-content">
			<%@include file="../common/common-breadcrumbs.jsp"%>
			<div class="page-content">
				<div style="width: 100%; height: 700px;" id="div_main">
					<div class="ui-layout-west jl_pane jl_ui-layout-pane"
						style="border: 1px solid #ccc;">
						<%@include file="/admin/common/orgTree/index.jsp"%>
					</div>
					<div class="ui-layout-center jl_ui-layout-pane">
						<div>
							<div class="space-2"></div>
							<form class="form-horizontal dy-dialog-form" id="alter_form"
								method="post">
								<div class="panel panel-default">
									<div class="panel-heading">
										<h4 class="panel-title">
											<b>请输入查询条件:</b>
											</h3>
									</div>
									<div class="panel-body">
										<div class="col-xs-12">
											<div class="form-group col-md-1" align="right">
												<label>异动类别：</label>
											</div>
											<div class="form-group col-md-2">
												<div class="col-xs-12  input-group">
													<select id="alterType" style="width: 160px">
														<option value="">请选择</option>
														<c:forEach var="alterType" items="${alterList }">
															<option value="${alterType.value }">${alterType.name
																}</option>
														</c:forEach>
													</select>
												</div>
											</div>
											<div class="form-group col-md-1" align="right">
												<label>学生姓名：</label>
											</div>
											<div class="form-group col-md-2">
												<div class="col-xs-12  input-group">
													<input type="text" maxlength="20" class="input" id="name" />
												</div>
											</div>
										</div>
										<div class="col-xs-12">
											<div class="form-group col-md-1" align="right">
												<label>处理状态：</label>
											</div>
											<div class="form-group col-md-2">
												<div class="col-xs-12  input-group">
													<select id="status" style="width: 160px">
														<option value="">请选择</option>
														<c:forEach var="alterStatus" items="${alterStatus }">
															<option value="${alterStatus.value }">${alterStatus.name
																}</option>
														</c:forEach>
													</select>
												</div>
											</div>
											<div class="form-group col-md-1" align="right">
												<label>异动时间：</label>
											</div>
											<div class="form-group col-md-2">
												<div class="col-xs-12  input-group">
													<span class="input-group-addon"> <i
														class="icon-calendar bigger-110"></i>
													</span> <input class="form-control" type="text" name="startDate"
														id="alter_startDate" />
												</div>
											</div>
											<div class="form-group col-md-1" align="right">
												<label>到：</label>
											</div>
											<div class="form-group col-md-2">
												<div class="col-xs-12 col-sm-12 input-group">
													<span class="input-group-addon"> <i
														class="icon-calendar bigger-110"></i>
													</span> <input class="form-control" type="text" name="endDate"
														id="alter_endDate" />
												</div>
											</div>
											<div class="form-group col-md-2" align="center">
												<input type="button"
													class="btn btn-primary dy-search-button" id="find_btn"
													value="查 询" />
											</div>
										</div>
									</div>
								</div>
							</form>
						
							<div class="col-xs-12" style="overflow-x: hidden">
								<table id="altenation-table"></table>
								<div id="altenation-pager"></div>
							</div>
						</div>	
					</div>
				</div>
			</div>
		</div>
	</div>
	<%@include file="/admin/common/common-setting.jsp"%>
	<script type="text/javascript">
		var _creator = 1;
		var _schId = 1;
	</script>
	<script type="text/javascript" src="js/altenationInfo.js"></script>

</body>
</html>