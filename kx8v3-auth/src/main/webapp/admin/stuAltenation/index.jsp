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
<script
	src="<%=__BacePath%>/public/lib/jquery.layout/jquery.layout-1.4.js"></script>
<link rel="stylesheet"
	href="<%=rootPath%>/public/lib/jquery.layout/jquery.layout-1.4.css" />
<title>学生异动管理</title>
</head>
<body>
	<%@include file="/admin/common/common-navbar.jsp"%>
	<div class="main-container container-fluid">
		<%@include file="/admin/common/common-leftmenu.jsp"%>
		<div class="main-content">
			<%@include file="../common/common-breadcrumbs.jsp"%>
			<div class="page-content">
				<div style="width: 100%; height: 720px;" id="div_main">
					<div class="ui-layout-west jl_pane jl_ui-layout-pane"
						style="border: 1px solid #ccc;">
						<%@include file="/admin/common/orgTree/index.jsp"%>
					</div>
					<div class="ui-layout-center jl_ui-layout-pane">
						<div>
							<form name="form-search" id="form-search">
								<div class="alert alert-info" role="alert">
									<p>
										转学申请说明：<br />
										1、转学申请仅用于大洋软件技术有限公司合作学校之间的学生转学；本系统之间的学生转学仅用于学生数据的变更，不代表学生学籍的转出（转入）。<br />
										2、转学流程：转出方学校填写转学申请 —— 接收方学校接收申请并给分配班级 —— 转学成功。
									</p>
								</div>
								<div class="panel panel-default">
									<div class="panel-body">
										<div class="col-xs-12">
											<div class="form-group col-md-1" align="right">
												<label>年级：</label>
											</div>
											<div class="form-group col-md-2">
												<select id="qGrade" style="width: 155px"></select>
											</div>
											<div class="form-group col-md-1" align="right">
												<label>班级：</label>
											</div>
											<div class="form-group col-md-2">
												<select id="qClass" style="width: 155px"></select>
											</div>
											<div class="form-group col-md-1" align="right">
												<label>学生姓名：</label>
											</div>
											<div class="form-group col-md-2">
												<input type="text" class="input" name="name" id="name" />
											</div>
										</div>
										<div class="col-xs-12">
											<div class="form-group col-md-1" align="right">
												<label>家长号码：</label>
											</div>
											<div class="form-group col-md-2">
												<input type="text" class="input" name="telphone" id="telphone" />
											</div>
											<div class="form-group col-md-1" align="right">
												<label>学生状态：</label>
											</div>
											<div class="form-group col-md-2">
												<select id="status" style="width: 155px">
													<option value="">请选择</option>
													<c:forEach var="dic" items="${empStatus }">
														<option value="${dic.value }">${dic.name }</option>
													</c:forEach>
												</select>
											</div>
											<div class="form-group col-md-2" align="left">
												<input type="button"
													class="btn btn-primary dy-search-button" id="find_btn"
													value="查 询" />
											</div>
										</div>
									</div>
								</div>
							</form>
								<div class="form-group col-md-6">
<!-- 									<input type="button" class="btn btn-info" id="inneralter_btn" value="转班" />  -->
<!-- 										<input type="button" class="btn btn-danger" id="repeatAlter_btn" value="复学" />  -->
<!-- 										<input type="button" class="btn btn-warning" id="otherAlter_btn" value="其他异动" />  -->
<!-- 										<input type="button" class="btn btn-warning" id="outerAlter_btn" value="创建转学申请单" /> -->
										
										<dytags:authBtn name="转班" code="innerAlter" id="inneralter_btn" btnClass="btn btn-info" lableClass="icon-pencil bigger-125" />
										<dytags:authBtn name="复学" code="repeat" id="repeatAlter_btn" btnClass="btn btn-danger" lableClass="icon-pencil bigger-125" />
										<dytags:authBtn name="其他异动" code="otherAlter" id="otherAlter_btn" btnClass="btn btn-warning" lableClass="icon-pencil bigger-125" />
										<dytags:authBtn name="创建转学申请单" code="outerAlter" id="outerAlter_btn" btnClass="btn btn-warning" lableClass="icon-pencil bigger-125" />
										<dytags:hasAuth />
								</div>


							<!-- 课时列表 -->
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

	<script type="text/javascript" src="js/index.js"></script>

</body>
</html>