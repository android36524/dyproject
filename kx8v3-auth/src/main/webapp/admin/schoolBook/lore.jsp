<%@page import="org.apache.commons.lang.StringUtils"%>
<%@ page import="com.dayang.commons.util.DaYangCommonUtil" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>


<div id="query" style="position: static; float: none; clear: both;">
				<h6>知识点树</h6>
				<div class="col-xs-12 col-sm-11"
					style="margin-top: 20px; margin-bottom: 20px">
					<div class="input-group">
						<input type="text" class="form-control search-query"
							id="loreNameId" placeholder="输入知识点名称模糊查询" /> <span
							class="input-group-btn">
							<button type="button" id="searchButton" onclick="searchNodeByLoreName();"
								class="btn btn-purple btn-sm">
								<i class="icon-search icon-on-right bigger-110"></i>
							</button>
						</span>
					</div>
				</div>
			</div>
			
			<div class="" style="position:static;float:none;clear:both;">
				<div class="col-xs-12">
					<ul id="sectionPointTree" class="ztree" style="width:100%">
					</ul>
					<div id="loreDiv" style="display: none" align="right">
						<span><font color="red"><b>此章节暂时没有关联知识点！</b></font></span>
					</div>
				</div>
			</div>
			<input type="hidden" id="loreId" name="loreId"/>
			<input type="hidden" id="loreId2" name="loreId2"/>
			<input type="hidden" id="biaozhi" name="biaozhi"/>
			
			<script type="text/javascript" src="js/lore.js"></script>
			
			