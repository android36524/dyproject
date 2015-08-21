<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="cn">
<head>
	<meta charset="utf-8" />
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<%@include file="/include-path.jsp"%>
	<%@include file="/common/include-bace.jsp"%>
	<link href="<%=path%>/admin/css/dy-admin-default.css" rel="stylesheet">
	<link href="css/index.css" rel="stylesheet" type="text/css">  
	<script type="text/javascript">
	</script>
	<style type="text/css">
		div#rMenu {
				    background-color:#555555;
				    text-align: left;
				    padding:2px;
				    width:100px;
				    position:absolute;visibility:hidden;
		}
		div#rMenu ul {
					    margin:1px 0;
					    padding:0 5px;
					    cursor: pointer;
					    list-style: none outside none;
					    background-color:#DFDFDF;
		}
		ul,li{
			list-style-type: none
		}
		div#rMenu ul li:hover {
		    margin:0;
		    padding:2px 0;
			color:#990020; /* 改变文字颜色*/ 
			font-size:15px;
		}
	</style>
	<title>知识点管理</title>
</head>
<body>
	<%@include file="/admin/common/common-navbar.jsp" %>
	<div class="main-container container-fluid">
		<%@include file="/admin/common/common-leftmenu.jsp" %>
		<div class="main-content">
			<%@include file="../common/common-breadcrumbs.jsp" %>
			<div class="page-content">
				<div class="container-fluid">
						<%--栅格系统布局 --%>
						<div class="row">
						  <div class="col-md-5">
							 <div id="query" style="position:static;float:none;clear:both;"> 
							 	<div class="col-xs-4">
							 	   <label>学阶：</label>
							       <select class="" id="stageId" dyId="selectId1" style="width: 100px">
									   <option value=''>--请选择--</option>
								   </select>
							 	</div>
							 
							 	<div class="col-xs-4">
							 	   <label>年级：</label>
							       <select class="" id="gradeId" dyId="selectId2"  style="width: 100px">
									   <option value=''>--请选择--</option>
								   </select>
							 	</div>
							     
							    <div class="col-xs-4">
							       <label>科目：</label>
								   <select class="" id="subjectId" dyId="selectId3" style="width: 100px" >
									   <option value=''>--请选择--</option>
								   </select>
							     </div>  
							   <div class="col-xs-12 col-sm-11" style="margin-top: 20px;margin-bottom: 20px">
								   <div class="input-group">
										<input type="text" class="form-control search-query" id="loreNameId" placeholder="输入知识点名称模糊查询" maxlength=15 />
										<span class="input-group-btn">
											<button type="button" id="searchButton" class="btn btn-purple btn-sm">
												<i class="icon-search icon-on-right bigger-110"></i>
											</button>
										</span>
								   </div>
							   </div>
						    </div>
						    <div class="" style="position:static;float:none;clear:both;">
							    <div class="col-xs-12"> 
								    <ul id="loreTree" class="ztree" style="width:100%">
								    </ul>
							    </div>
						    </div>
						    
						    <div id="rMenu">
								<ul>
									<li id="m_add" onclick="addTreeNode();">增加知识点</li>
									<li id="m_del" onclick="removeTreeNode();">删除知识点</li>
									<li id="m_edit" onclick="editTreeNode();">修改知识点</li>
									<li id="m_upward" onclick="moveUpwardTree();">向上移动</li>
									<li id="m_downmove" onclick="moveDownTree();">向下移动</li>
									<li id="m_goup" onclick="goUpTree();">升级</li>
									<li id="m_downlower" onclick="downLowerTree();" >降级</li>
								</ul>
							</div>
						    
						  </div>
						  <div>
						  	<font size="4px">
						  		新增/修改知识点&nbsp;&nbsp;&gt;&gt;&nbsp;&nbsp;<font color="red"><label id="stageName"></label></font>&nbsp;&nbsp;&gt;&gt;&nbsp;&nbsp;<font color="red"><label id="gradeName"></label></font>&nbsp;&nbsp;&gt;&gt;&nbsp;&nbsp;<font color="red"><label id="subjectName"></label></font>
						  	</font>
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
											 	<input type="text" name="lore.name"  id="lore_name" class="col-xs-12 col-sm-12" maxlength=15 />
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
										<label class="control-label col-xs-12 col-sm-5 no-padding-right" for="lore_remark">知识点说明:</label>
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






