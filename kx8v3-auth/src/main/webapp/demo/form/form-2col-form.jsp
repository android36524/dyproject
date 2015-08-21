<%@page import="org.apache.commons.lang.StringUtils"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%-- 菜单添加&修改 --%>
<%
	String menuId = StringUtils.defaultIfEmpty(request.getParameter("id"), "");
	String pmenuid = StringUtils.defaultIfEmpty(request.getParameter("pmenuid"), "");
%>
<div>
	<!-- <h3 class="header smaller lighter purple">
		菜单新增
	</h3> -->
	<form class="form-horizontal dy-dialog-form" id="menu-form" method="post" >
		<div class="row">
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-xs-12 col-sm-5 no-padding-right" for="menu_name">菜单名称:</label>
					<div class="col-xs-12 col-sm-9">
						<div class="clearfix">
							<input type="hidden" name="menu.id" id="menu_id"/>
							<input type="text" name="menu.name" id="menu_name" class="col-xs-12 col-sm-12" />
						</div>
					</div>
				</div>
			</div>
			
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-xs-12 col-sm-5 no-padding-right" for="menu_icon">菜单图标:</label>
					<div class="col-xs-12 col-sm-9">
						<div class="clearfix">
							<div class="input-group">
								<span class="input-icon">
									<input class="form-control" name="menu.icon" type="text" id="menu_icon" value=''/>
									<i id="i_menu_icon" class="icon-leaf blue"></i>
								</span>
								<span class="input-group-btn">
									<button class="btn btn-sm btn-primary" type="button" id="btn_select_icon">
										<i class="icon-edit bigger-110"></i>
										选择图标
									</button>
								</span>
							</div>
						</div>
					</div>
				</div>
				
			</div>
		</div>
		<div class="space-2"></div>
		
		<div class="row">
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-xs-12 col-sm-5 no-padding-right" for="menu_url">菜单链接:</label>
					<div class="col-xs-12 col-sm-9">
						<div class="clearfix">
							<input type="text" name="menu.url" id="menu_url" class="col-xs-12 col-sm-12"/>
						</div>
					</div>
				</div>
			</div>
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-xs-12 col-sm-5 no-padding-right" for="menu_parentId">父级菜单:</label>
					<div class="col-xs-12 col-sm-9">
						<div class="clearfix">
							<select class="col-xs-12 col-sm-12" id="menu_parentId" name="menu.parentId">
							</select>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="space-2"></div>
		
		<div class="row">
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-xs-12 col-sm-5 no-padding-right" for="menu_seq">菜单排序:</label>
					<div class="col-xs-12 col-sm-9">
						<div class="clearfix">
							<input type="text" name="menu.seq" id="menu_seq" class="col-xs-12 col-sm-12 spinner-input" />
						</div>
					</div>
				</div>
			</div>
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-xs-12 col-sm-5 no-padding-right" for="menu_isHidden">是否隐藏:</label>
					<div class="col-xs-12 col-sm-9">
						<div class="clearfix">
							<select class="col-xs-12 col-sm-12" id="menu_isHidden" name="menu.isHidden">
								<option value="0">隐藏</option>
								<option value="1" selected="selected" >显示</option>
							</select>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="space-2"></div>
		
		<div class="row">
			<div class="col-xs-12">
				<div class="form-group">
					<label class="control-label col-xs-12 col-sm-5 no-padding-right" for="menu_description">菜单描述:</label>
					<div class="col-xs-12 col-sm-10s">
						<div class="clearfix">
							<textarea class="col-xs-12 col-sm-12" name="menu.description" id="menu_description"></textarea>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="space-2"></div>
	</form>
</div>
<script type="text/javascript">
	$(function(){
		$('#menu_seq').ace_spinner({value:1,min:0,max:999,step:1, icon_up:'icon-plus', icon_down:'icon-minus', btn_up_class:'btn-success' , btn_down_class:'btn-danger'});
		
		//加载父级菜单信息
		$.ajaxSetup({async : false});
		$.get(path + "/admin/menu/list",function(menuList){
			if(menuList){
				var menu_parent_dom = $("#menu_parentId");
				menu_parent_dom.empty();
				menu_parent_dom.append("<option value='0'>--顶级菜单--</option>");
				for (var i = 0; i < menuList.length; i++) {
					var menuObj = menuList[i];
					menu_parent_dom.append("<option value='"+menuObj.id+"'>"+menuObj.name+"</option>");
				}
			}
		});
		$.ajaxSetup({async : true});
		
		//加载已有的菜单数据
		var _menuId = "<%=menuId%>";
		var _pmenuId = "<%=pmenuid%>";
		if(_menuId){
			$.get(path + "/admin/menu/queryById?id="+_menuId,function(ret){
				$.each(ret,function(k,v){
					$("#menu-form #menu_"+k).val(v);
				});
			});
		}
		if(_pmenuId && _pmenuId > 0){
			$("#menu_parentId").val(_pmenuId);
		}		
		//选择图标
		$("#btn_select_icon").click(function(){
			commonJs.openIcon(function(icon){
				$("#menu_icon").val(icon);
				$("#i_menu_icon").attr("class",icon + " blue");
			});
		});
		
		//表单验证
		$("#menu-form").validate({
			errorElement: 'span',
			errorClass: 'help-inline',
			focusInvalid: false,
			rules : {
				"menu.name" : {
					required: true
				},
				"menu.url" : {
					required: true
				},
				"menu.parent_id" : {
					required: true
				}
			},
			messages : {
				"menu.name" : {
					required : "请输入菜单名称"
				},
				"menu.url" : {
					required : "请输入菜单链接"
				},
				"menu.parent_id" : {
					required : "请选择父级菜单"
				}
			},
			invalidHandler: function (event, validator) { //display error alert on form submit   
				$('.alert-danger', $('.login-form')).show();
			},
	
			highlight: function (e) {
				$(e).closest('.form-group').removeClass('has-info').addClass('has-error');
			},
	
			success: function (e) {
				$(e).closest('.form-group').removeClass('has-error').addClass('has-info');
				$(e).remove();
			},
	
			errorPlacement: function (error, element) {
				if(element.is(':checkbox') || element.is(':radio')) {
					var controls = element.closest('div[class*="col-"]');
					if(controls.find(':checkbox,:radio').length > 1) controls.append(error);
					else error.insertAfter(element.nextAll('.lbl:eq(0)').eq(0));
				}
				else if(element.is('.select2')) {
					error.insertAfter(element.siblings('[class*="select2-container"]:eq(0)'));
				}
				else if(element.is('.chosen-select')) {
					error.insertAfter(element.siblings('[class*="chosen-container"]:eq(0)'));
				}
				else error.insertAfter(element.parent());
			},
	
			submitHandler: function (form) {
			},
			invalidHandler: function (form) {
			}
		});
	});
</script>