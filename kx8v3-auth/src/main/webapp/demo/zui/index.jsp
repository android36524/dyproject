<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/include-core.jsp"%>
<%@include file="/common/include-zui.jsp"%>

<link href="<%=path%>/css/zuidoc.css" type="text/css" rel="stylesheet">

<title>大洋基础平台</title>
</head>
<body class="doc-basic with-navbar">
	<header data-tab="basic"> <nav role="navigation"
		class="navbar navbar-inverse navbar-fixed-left navbar-collapsed"
		id="navbar">
	<div class="navbar-header">
		<button data-target="#navbar-collapse" data-toggle="collapse"
			class="navbar-toggle" type="button">
			<span class="sr-only">Toggle navigation</span> <span class="icon-bar"></span>
			<span class="icon-bar"></span> <span class="icon-bar"></span>
		</button>
		<a href="/" class="navbar-brand">ZUI &nbsp; <small
			class="text-muted version-current"></small></a>
	</div>
	<div id="navbar-collapse" class="collapse navbar-collapse">
		<ul data-tab="start" class="nav navbar-nav collapsed">
			<li class="nav-heading">开始</li>
			<li><a href="/docs/start.html#intro" data-target="#intro">说明</a></li>
			<li><a href="/docs/start.html#about" data-target="#about">约定</a></li>
			<li><a href="/docs/start.html#files" data-target="#files">文件目录结构</a></li>
			<li><a href="/docs/start.html#edition" data-target="#edition">选择版本</a></li>
			<li><a href="/docs/start.html#grunt" data-target="#grunt">编译及定制</a></li>
			<li><a href="/docs/start.html#browsers" data-target="#browsers">受支持的平台</a></li>
		</ul>
		<ul data-tab="basic" class="nav navbar-nav">
			<li class="nav-heading">基础</li>
			<li class=""><a href="/docs/basic.html#hello"
				data-target="#hello">Hello world</a></li>
			<li class="active"><a href="/docs/basic.html#global"
				data-target="#global">全局样式表</a></li>
			<li><a href="/docs/basic.html#ie" data-target="#ie">兼容IE浏览器</a></li>
			<li><a href="/docs/basic.html#responsive"
				data-target="#responsive">响应式设计</a></li>
			<li><a href="/docs/basic.html#grid" data-target="#grid">栅格系统</a></li>
			<li><a href="/docs/basic.html#typography"
				data-target="#typography">文字排版</a></li>
			<li><a href="/docs/basic.html#utilities"
				data-target="#utilities">辅助类</a></li>
			<li><a href="/docs/basic.html#scrollbars"
				data-target="#scrollbars">滚动条</a></li>
			<li><a href="/docs/basic.html#colorset" data-target="#colorset">配色</a></li>
			<li><a href="/docs/basic.html#themes" data-target="#themes">主题</a></li>
		</ul>
		<ul data-tab="controls" class="nav navbar-nav collapsed">
			<li class="nav-heading">控件</li>
			<li><a href="/docs/controls.html#icons" data-target="#icons">图标</a></li>
			<li><a href="/docs/controls.html#button" data-target="#button">按钮</a></li>
			<li><a href="/docs/controls.html#progressbar"
				data-target="#progressbar">进度条</a></li>
			<li><a href="/docs/controls.html#labels" data-target="#labels">标签</a></li>
			<li><a href="/docs/controls.html#textbox" data-target="#textbox">文本框</a></li>
			<li><a href="/docs/controls.html#breadcrumb"
				data-target="#breadcrumb">面包屑</a></li>
			<li><a href="/docs/controls.html#images" data-target="#images">图片</a></li>
			<li><a href="/docs/controls.html#headers" data-target="#headers">标题</a></li>
			<li><a href="/docs/controls.html#dividers"
				data-target="#dividers">分隔</a></li>
		</ul>
		<ul data-tab="components" class="nav navbar-nav collapsed">
			<li class="nav-heading">组件</li>
			<li><a href="/docs/components.html#alerts" data-target="#alerts">消息框</a></li>
			<li><a href="/docs/components.html#code" data-target="#code">代码</a></li>
			<li><a href="/docs/components.html#inputgroup"
				data-target="#inputgroup">输入组</a></li>
			<li><a href="/docs/components.html#listgroup"
				data-target="#listgroup">列表组</a></li>
			<li><a href="/docs/components.html#navs" data-target="#navs">导航</a></li>
			<li><a href="/docs/components.html#navbars"
				data-target="#navbars">导航条</a></li>
			<li><a href="/docs/components.html#menu" data-target="#menu">垂直菜单</a></li>
			<li><a href="/docs/components.html#pagers" data-target="#pagers">分页器</a></li>
			<li><a href="/docs/components.html#panels" data-target="#panels">面板</a></li>
			<li><a href="/docs/components.html#tables" data-target="#tables">表格</a></li>
			<li><a href="/docs/components.html#buttongroup"
				data-target="#buttongroup">按钮组</a></li>
		</ul>
		<ul data-tab="javascript" class="nav navbar-nav collapsed">
			<li class="nav-heading">JS插件</li>
			<li><a href="/docs/javascript.html#store" data-target="#store">本地存储</a></li>
			<li><a href="/docs/javascript.html#modals" data-target="#modals">模态框</a></li>
			<li><a href="/docs/javascript.html#modaltrigger"
				data-target="#modaltrigger">模态框触发器</a></li>
			<li><a href="/docs/javascript.html#dropdowns"
				data-target="#dropdowns">下拉菜单</a></li>
			<li><a href="/docs/javascript.html#tabs" data-target="#tabs">标签页</a></li>
			<li><a href="/docs/javascript.html#messager"
				data-target="#messager">漂浮消息</a></li>
			<li><a href="/docs/javascript.html#tooltips"
				data-target="#tooltips">提示消息</a></li>
			<li><a href="/docs/javascript.html#popovers"
				data-target="#popovers">弹出框</a></li>
			<li><a href="/docs/javascript.html#collapse"
				data-target="#collapse">折叠</a></li>
			<li><a href="/docs/javascript.html#carousel"
				data-target="#carousel">轮播</a></li>
			<li><a href="/docs/javascript.html#datetimepicker"
				data-target="#datetimepicker">日期选择</a></li>
			<li><a href="/docs/javascript.html#chosen" data-target="#chosen">Chosen</a></li>
			<li><a href="/docs/javascript.html#kindeditor"
				data-target="#kindeditor">富文本编辑器</a></li>
			<li><a href="/docs/javascript.html#droppable"
				data-target="#droppable">拖放</a></li>
			<li><a href="/docs/javascript.html#autotrigger"
				data-target="#autotrigger">自动触发器</a></li>
		</ul>
		<ul data-tab="views" class="nav navbar-nav collapsed">
			<li class="nav-heading">视图</li>
			<li><a href="/docs/views.html#datatable"
				data-target="#datatable">数据表格</a></li>
			<li><a href="/docs/views.html#calendar" data-target="#calendar">日历</a></li>
			<li><a href="/docs/views.html#forms" data-target="#forms">表单</a></li>
			<li><a href="/docs/views.html#article" data-target="#article">文章</a></li>
			<li><a href="/docs/views.html#card" data-target="#card">卡片</a></li>
			<li><a href="/docs/views.html#list" data-target="#list">列表</a></li>
			<li><a href="/docs/views.html#comment" data-target="#comment">评论</a></li>
			<li><a href="/docs/views.html#board" data-target="#board">看板</a></li>
			<li><a href="/docs/views.html#dashboard"
				data-target="#dashboard">仪表盘</a></li>
		</ul>
		<ul data-tab="explore" class="nav navbar-nav collapsed">
			<li class="nav-heading">探索</li>
			<li><a href="/docs/explore.html#sourcecode"
				data-target="#sourcecode">下载源码</a></li>
			<li><a href="/docs/explore.html#cases" data-target="#cases">这些网站在使用</a></li>
		</ul>
	</div>
	</nav> 
	</header>

	<div id="main" style="min-height: 943px;">
		<div class="alert alert-info">
			测试弹窗
		</div>
	</div>
</body>
</html>