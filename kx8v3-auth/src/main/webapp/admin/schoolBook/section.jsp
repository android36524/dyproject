<%@page import="org.apache.commons.lang.StringUtils"%>
<%@ page import="com.dayang.commons.util.DaYangCommonUtil" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<link href="css/index.css" rel="stylesheet" type="text/css">

<style type="text/css">

/* .ztree li span.button.add {margin-left:2px; margin-right: -1px; background-position:-144px 0; vertical-align:top; *vertical-align:middle}
.ztree li span.button.newAdd {margin-right:2px; background-position:-128px -48px; vertical-align:top; *vertical-align:middle}
 */
div#rMenu {
				    background-color:#555555;
				    text-align: left;
				    padding:2px;
				    width:150px;
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
<div id="query" style="position: static; float: none; clear: both;">
				<h6>章节目录树</h6>
				<div class="col-xs-12 col-sm-11"
					style="margin-top: 20px; margin-bottom: 20px">
					<div class="input-group">
						<input type="text" class="form-control search-query"
							id="key" placeholder="输入章节模糊查询" /> <span
							class="input-group-btn">
							<button type="button" id="searchTreeButton" onclick="searchNode();"
								class="btn btn-purple btn-sm">
								<i class="icon-search icon-on-right bigger-110"></i>
							</button>
						</span>
					</div>
				</div>
			</div>
			
			<div class="" style="position:static;float:none;clear:both;">
				<div class="col-xs-12">
					<ul id="sectionTree" class="ztree" style="width:100%">
					</ul>
				</div>
			</div>
			
			<div id="rMenu">
				<ul>
					<li id="m_add" onclick="addTreeNode();">增加章节</li>
					<li id="m_del" onclick="removeTreeNode();">删除章节</li>
					<li id="m_edit" onclick="editTreeNode();">修改章节名称</li>
					<li id="m_addLore" onclick="addLoreTreeNode();">增加/修改知识点</li>
				</ul>
			</div>
			
			<input type="hidden" id="sectionId" name="sectionId" />
			<input type="hidden" id="selectLoreId" name="selectLoreId" />
<script type="text/javascript" src="js/section.js"></script>
