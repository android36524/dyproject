<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%-- 面包屑导航 --%>
<script type="text/javascript" src="<%=path%>/admin/js/common-admin.js"></script>
<div class="breadcrumbs" id="breadcrumbs">
	<script type="text/javascript">
		try{ace.settings.check('breadcrumbs' , 'fixed')}catch(e){}
	</script>
	<ul class="breadcrumb">
		<li>
			<i class="icon-home home-icon"></i>
			<a href="<%=path%>/admin">首页</a>
		</li>
		<li class="active" dyid="li_firstMenu"></li>
		<li class="active" dyid="li_secendMenu"></li>
	</ul><!--.breadcrumb-->

	<div class="nav-search" id="nav-search">
		<form class="form-search" />
			<span class="input-icon">
				<input type="text" placeholder="搜索 ..." class="nav-search-input" id="nav-search-input" autocomplete="off" />
				<i class="icon-search nav-search-icon"></i>
			</span>
		</form>
	</div><!--#nav-search-->
</div>
