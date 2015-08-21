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
	<title>学生升学时间设置</title>
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
							<form name="form-setHiger" id="form-setHiger">
								<div class="row">
									<div class="col-xs-6">
										<div class="form-group">
											<label class="control-label col-xs-3 col-sm-5 no-padding-right" for="grade1">请选择毕业年级:</label>
											<div class="col-xs-12 col-sm-9">
												<div class="clearfix" id="GraduationGrad">

												</div>
											</div>
										</div>
									</div>
								</div>
								<div class="space-2"></div>

								<div class="row">
									<div class="col-xs-12">
										<div class="form-group">
											<label class="control-label col-xs-3 col-sm-5 no-padding-right" for="ProgressingGrad">升学年级:</label>
											<div class="col-xs-12 col-sm-12">
												<div class="clearfix" >
													<input type="text" id="ProgressingGrad" style="width:600px" name="ProgressingGrad" value=""/>
												</div>
											</div>
										</div>
									</div>
								</div>
								<div class="space-2"></div>

								<div class="row">
									<div class="col-xs-12">
										<div class="form-group">
											<label class="control-label col-xs-12  no-padding-right" for="hightTime">毕业升学时间:</label>
											<div class="col-xs-2 ">
												<div class="input-group">
													<span class="input-group-addon">
														<i class="icon-calendar bigger-110"></i>
													</span>
													<input  type="text" class="form-control" name="hightTime" id="hightTime" />
												</div>
											</div>
										</div>
									</div>
								</div>
								<div class="space-2"></div>
								<input type="button" class="btn btn-primary dy-search-button" id="save_btn" value="保 存" />
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<%@include file="/admin/common/common-setting.jsp" %>
</body>
</html><script type="text/javascript" src="js/setStuHiger.js"></script>