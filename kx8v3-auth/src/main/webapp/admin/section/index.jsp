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
	<link rel="stylesheet" href="<%=path%>/css/metroStyle/metroStyle.css" type="text/css">
	<link rel="stylesheet" href="<%=path%>/css/demo.css" type="text/css">
	<script type="text/javascript">
	</script>
	<title>知识点管理</title>
</head>
<body>
	<%@include file="/admin/common/common-navbar.jsp" %>
	<div class="main-container container-fluid">
		<%@include file="/admin/common/common-leftmenu.jsp" %>
		<div class="main-content">
			<%@include file="../common/common-breadcrumbs.jsp" %>
			<div class="page-content">
				<div>
						<%--栅格系统布局 --%>
						<div class="row">
						  <div class="col-md-4">
							 <div id="query" style="position:static;float:none;clear:both;"> 
							 
							   <div class="col-xs-12 col-sm-11" style="margin-top: 20px;margin-bottom: 20px">
								   <div class="input-group">
										<input type="text" class="form-control search-query" id="loreNameId" placeholder="输入知识点名称模糊查询" />
										<span class="input-group-btn">
											<button type="button" id="searchButton" class="btn btn-purple btn-sm">
												<i class="icon-search icon-on-right bigger-110"></i>
											</button>
										</span>
								   </div>
							   </div>
						    </div>
						    <div class="content_wrap">
							    <div class="zTreeDemoBackground left" style="position:static;float:none;clear:both;margin-left:13px;"> 
								    <ul id="sectionTree" class="ztree">
								    </ul>
							    </div>
						    </div>
<!-- 						    <div id="rMenu"> -->
<!-- 								<ul> -->
<!-- 									<li id="m_add" onclick="addTreeNode();">增加节点</li> -->
<!-- 									<li id="m_del" onclick="removeTreeNode();">删除节点</li> -->
<!-- 									<li id="m_check" onclick="checkTreeNode(true);">Check节点</li> -->
<!-- 									<li id="m_unCheck" onclick="checkTreeNode(false);">unCheck节点</li> -->
<!-- 									<li id="m_reset" onclick="resetTree();">恢复zTree</li> -->
<!-- 								</ul> -->
<!-- 							</div> -->
						  </div>
						  <div class="col-md-5" style="border:solid;border-width: 1px; border-color:#ccc; ">
						  		<form class="form-horizontal dy-dialog-form" id="lore_form" method="post">
									<div class="form-group">
										<label class="control-label col-xs-12 col-sm-5 no-padding-right" for="lore_parentName">父级知识点:</label>
										<div class="col-xs-12 col-sm-7">
											<div class="clearfix">
												<input type="hidden" name="lore.parentId" id="lore_parentId"  >
												<input type="hidden" name="lore.stageId" id="lore_stageId"  >
												<input type="hidden" name="lore.gradeId" id="lore_gradeId" >
												<input type="hidden" name="lore.subjectId" id="lore_subjectId">
												<input type="hidden" name="lore.id" id="lore_id">
												<input type="text" name="parentName" readonly="readonly"  id="lore_parentName" class="col-xs-12 col-sm-12" />
											</div>
										</div>
									</div>
									<div class="space-2"></div>
								
									<div class="form-group">
										<label class="control-label col-xs-12 col-sm-5 no-padding-right" for="lore_name">知识点名称:</label>
										<div class="col-xs-12 col-sm-7">
											 <div class="clearfix">
											 	<input type="text" name="lore.name"  id="lore_name" class="col-xs-12 col-sm-12" />
											 </div>
										</div>
									</div>
									<div class="space-2"></div>
									
									<div class="form-group">
										<label class="control-label col-xs-12 col-sm-5 no-padding-right" for="lore_seq">排序:</label>
										<div class="col-xs-12 col-sm-7">
											<div class="clearfix">
												<input  type="text" name="lore.seq" id="lore_seq" class="input-mini"/>
											</div>
										</div>
									</div>
									<div class="space-2"></div>
									
									<div class="form-group">
										<label class="control-label col-xs-12 col-sm-5 no-padding-right" for="lore_remark">备注:</label>
										<div class="col-xs-12 col-sm-7">
											<div class="clearfix">
												<textarea class="col-xs-12 col-sm-12" name="lore.remark" id="lore_remark" style="height:264px"></textarea>
											</div>
										</div>
									</div>
									<div class="space-2"></div>
								</form>
								
								<p style="margin-left: 100px">
									<button class="btn btn-primary" id="btn-addLore">
										<i class="icon-ok bigger-125"></i>
										保存
									</button>
									<button class="btn btn-primary" id="btn-emptyLore">
										<i class="icon-reply bigger-125"></i>
										重置
									</button>
								</p>
						  </div>
						  <div class="col-md-2">
						  
						  </div>
						</div>
				</div>
			</div>
		</div>
	</div>
	<%@include file="/admin/common/common-setting.jsp" %>
	<script type="text/javascript" src="js/index.js"></script>
</body>
</html>






