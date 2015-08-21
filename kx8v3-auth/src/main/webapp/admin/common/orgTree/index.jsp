<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<div class="jl_header widget-header" f="dl_del"><h4 class="smaller">组织机构树</h4></div>
<div id="div_common_org" class="row" style="margin-right: 0px !important;text-align: center;">		
	<div class="form-group col-xs-12">
		<label class="control-label col-xs-3 no-padding-right" for="tree_provinceId">省：</label>
		<select  class="col-xs-9 no-padding-left" id="tree_provinceId" >
				<option value=''>--请选择--</option>
		</select>
	</div>
	<div class="space-2"></div>
	<div class="form-group col-xs-12">
		<label class="control-label col-xs-3 no-padding-right" for="tree_cityId">市：</label>
		<select class="col-xs-9 no-padding-left" id="tree_cityId" >
				<option value=''>--请选择--</option>
		</select>
	</div>
	<div class="space-2"></div>
	<div class="form-group col-xs-12">
		<label class="control-label col-xs-3 no-padding-right" for="tree_areaId">区：</label>
		<select class="col-xs-9 no-padding-left" id="tree_areaId">
			<option value=''>--请选择--</option>
		</select>
	</div>
	<div class="space-2"></div>		
	<div class="form-group col-xs-12">
		<!-- <label class="col-xs-1"></label>
		<input type="text" class="input col-xs-7 no-padding-right" id="searchName" placeholder="机构名称或编码"/>
		<label class="col-xs-1"></label>
		<input type="submit" class="btn btn-primary btn-xs col-xs-2 no-padding-left" id="tree_find_btn" value="查 询" />	 -->
		
		<label class="col-xs-1"></label>
		<div id="div_query_group" class="input-group col-xs-11" style="padding-right: 0px;right:-16px; padding-left: 0px;">
			<input id="searchName" class="form-control" type="text" placeholder="机构名称或编码" maxlength="50">
			<span class="input-group-btn">
				<button class="btn btn-sm btn-default" type="button" id="tree_find_btn" >
					<i class="icon-search bigger-110"></i>
					查 询
				</button>
			</span>
		</div>
	</div>
	<div class="space-2"></div>	
	<div class="col-xs-12">
	<ul id="edbTree" class="ztree">
	</ul>
</div>	
</div>
<script type="text/javascript" src="<%=path %>/admin/common/orgTree/js/index.js"></script>