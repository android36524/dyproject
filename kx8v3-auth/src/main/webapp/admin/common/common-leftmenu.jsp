<%@page import="com.dayang.commons.enums.CommonEnumAll.MenuType"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<a class="menu-toggler" id="menu-toggler" href="#"> <span class="menu-text"></span>
</a>
<div class="sidebar" id="sidebar">
	<script type="text/javascript">
		try{ace.settings.check('sidebar' , 'fixed')}catch(e){}
	</script>
	<div class="sidebar-shortcuts" id="sidebar-shortcuts">
		<!-- <div class="sidebar-shortcuts-large" id="sidebar-shortcuts-large">
			<button class="btn btn-success">
				<i class="icon-signal"></i>
			</button>

			<button class="btn btn-info">
				<i class="icon-pencil"></i>
			</button>

			<button class="btn btn-warning">
				<i class="icon-group"></i>
			</button>

			<button class="btn btn-danger">
				<i class="icon-cogs"></i>
			</button>
		</div> -->

		<div class="sidebar-shortcuts-mini" id="sidebar-shortcuts-mini">
			<span class="btn btn-success"></span> <span class="btn btn-info"></span> <span class="btn btn-warning"></span> <span
				class="btn btn-danger"></span>
		</div>
	</div>
	<!--#sidebar-shortcuts-->
	<script type="text/javascript">
	var frameMenu="<%=MenuType.frameMenu.getValueStr()%>";
	$(function(){
		//加载当前选择的菜单
		var curPath = window.location.href;
		var _currentPath = curPath.split(path?path:curPath.split(path+"/")[2]+"/")[1]||"";
		if(_currentPath.indexOf("?")){
			_currentPath = _currentPath.substring(0,_currentPath.indexOf("?"));
		}
		if(_currentPath.endsWith("/")){
			_currentPath = _currentPath.substring(0,_currentPath.length-1);
		}
		if(!_currentPath.startsWith("/")){
			_currentPath="/"+_currentPath;
		}
		if(_currentPath){
			var patter = _currentPath.indexOf("?")>0?"url*='"+_currentPath+"'":"url='"+_currentPath+"'";
			var _menuItem = $("a["+patter+"]");
			var _subMenuName = _menuItem.attr("title");
			$('[dyid="li_secendMenu"]').html(_subMenuName);
			var _parentMenu = $(_menuItem).parents("ul.submenu");					
			_menuItem.parent().addClass("active");		
			if(_parentMenu){
				var num=_parentMenu.length>0?_parentMenu.length-1:0;			
				var obj = num>0?_parentMenu[num]:_parentMenu;				
				var _parentMenuName = $(obj).prev().attr("title") || _subMenuName;
				$('[dyid="li_firstMenu"]').html(_parentMenuName);
				_parentMenu.addClass("active open");
				_parentMenu.show();	
			}		
		}		
	});

	</script>
	${S_MENU }
	<!--/.nav-list-->

	<div class="sidebar-collapse" id="sidebar-collapse">
		<i class="icon-double-angle-left" data-icon1="icon-double-angle-left" data-icon2="icon-double-angle-right"></i>
	</div>
	
	<script type="text/javascript">
		try{ace.settings.check('sidebar' , 'collapsed')}catch(e){}
	</script>
</div>