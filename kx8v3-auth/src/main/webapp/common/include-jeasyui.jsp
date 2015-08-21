<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String __EasyUIPath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
%>

<%-- jeasy UI 引用 --%>
<script type="text/javascript" src="<%=__EasyUIPath%>/public/jeasyui/easyloader.js"></script>
<script type="text/javascript" src="<%=__EasyUIPath%>/public/jeasyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=__EasyUIPath%>/public/jeasyui/locale/easyui-lang-zh_CN.js"></script>

<link id="easyuiTheme" rel="stylesheet" type="text/css" href="<%=__EasyUIPath%>/public/jeasyui/themes/bootstrap/easyui.css"/>
<link rel="stylesheet" type="text/css" href="<%=__EasyUIPath%>/public/jeasyui/themes/icon.css"/>

<link rel="stylesheet" type="text/css" href="<%=__EasyUIPath%>/public/jeasyui/icons/icon-all.css"/>
<script src="<%=__EasyUIPath%>/public/jeasyui/jquery.jdirk.js" type="text/javascript"></script>
<script src="<%=__EasyUIPath%>/public/jeasyui/jeasyui.icons.all.min.js" type="text/javascript"></script>

<link href="<%=__EasyUIPath%>/public/jeasyui/jeasyui-ext/jeasyui.extensions.css" rel="stylesheet" type="text/css" />
<script src="<%=__EasyUIPath%>/public/jeasyui/jeasyui-ext/jeasyui.extensions.js" type="text/javascript"></script>

<script src="<%=__EasyUIPath%>/public/jeasyui/jeasyui-ext/jeasyui.extensions.progressbar.js"></script>
<script src="<%=__EasyUIPath%>/public/jeasyui/jeasyui-ext/jeasyui.extensions.slider.js"></script>
<script src="<%=__EasyUIPath%>/public/jeasyui/jeasyui-ext/jeasyui.extensions.linkbutton.js" type="text/javascript"></script>
<script src="<%=__EasyUIPath%>/public/jeasyui/jeasyui-ext/jeasyui.extensions.form.js" type="text/javascript"></script>
<script src="<%=__EasyUIPath%>/public/jeasyui/jeasyui-ext/jeasyui.extensions.validatebox.js" type="text/javascript"></script>
<script src="<%=__EasyUIPath%>/public/jeasyui/jeasyui-ext/jeasyui.extensions.combo.js" type="text/javascript"></script>
<script src="<%=__EasyUIPath%>/public/jeasyui/jeasyui-ext/jeasyui.extensions.combobox.js" type="text/javascript"></script>
<script src="<%=__EasyUIPath%>/public/jeasyui/jeasyui-ext/jeasyui.extensions.menu.js" type="text/javascript"></script>
<script src="<%=__EasyUIPath%>/public/jeasyui/jeasyui-ext/jeasyui.extensions.searchbox.js" type="text/javascript"></script>
<script src="<%=__EasyUIPath%>/public/jeasyui/jeasyui-ext/jeasyui.extensions.panel.js" type="text/javascript"></script>
<script src="<%=__EasyUIPath%>/public/jeasyui/jeasyui-ext/jeasyui.extensions.window.js" type="text/javascript"></script>
<script src="<%=__EasyUIPath%>/public/jeasyui/jeasyui-ext/jeasyui.extensions.dialog.js" type="text/javascript"></script>
<script src="<%=__EasyUIPath%>/public/jeasyui/jeasyui-ext/jeasyui.extensions.layout.js" type="text/javascript"></script>
<script src="<%=__EasyUIPath%>/public/jeasyui/jeasyui-ext/jeasyui.extensions.tree.js" type="text/javascript"></script>
<script src="<%=__EasyUIPath%>/public/jeasyui/jeasyui-ext/jeasyui.extensions.datagrid.js" type="text/javascript"></script>
<script src="<%=__EasyUIPath%>/public/jeasyui/jeasyui-ext/jeasyui.extensions.treegrid.js" type="text/javascript"></script>
<script src="<%=__EasyUIPath%>/public/jeasyui/jeasyui-ext/jeasyui.extensions.combogrid.js" type="text/javascript"></script>
<script src="<%=__EasyUIPath%>/public/jeasyui/jeasyui-ext/jeasyui.extensions.combotree.js" type="text/javascript"></script>
<script src="<%=__EasyUIPath%>/public/jeasyui/jeasyui-ext/jeasyui.extensions.tabs.js" type="text/javascript"></script>
<script src="<%=__EasyUIPath%>/public/jeasyui/jeasyui-ext/jeasyui.extensions.theme.js" type="text/javascript"></script>
<script src="<%=__EasyUIPath%>/public/jeasyui/jeasyui-ext/jeasyui.extensions.icons.js" type="text/javascript"></script>

<script src="<%=__EasyUIPath%>/public/jeasyui/jeasyui-ext/jquery-easyui-toolbar/jquery.toolbar.js" type="text/javascript"></script>
<script src="<%=__EasyUIPath%>/public/jeasyui/jeasyui-ext/jquery-easyui-comboicons/jquery.comboicons.js" type="text/javascript"></script>
<script src="<%=__EasyUIPath%>/public/jeasyui/jeasyui-ext/jeasyui.extensions.gridselector.js" type="text/javascript"></script>
<script src="<%=__EasyUIPath%>/public/jeasyui/jeasyui-ext/jquery-easyui-comboselector/jquery.comboselector.js" type="text/javascript"></script>
<script src="<%=__EasyUIPath%>/public/jeasyui/jeasyui-ext/jquery-easyui-my97/jquery.my97.js" type="text/javascript"></script>


<!--include uploadFile  -->
<script src="<%=__EasyUIPath%>/public/jeasyui/jeasyui-ext/plugins/uploadify/jquery.uploadify.js" type="text/javascript"></script>
<link rel="stylesheet" type="text/css" href="<%=__EasyUIPath%>/public/jeasyui/jeasyui-ext/plugins/uploadify/uploadify.css"/>
<script src="<%=__EasyUIPath%>/public/jeasyui/jeasyui-ext/jquery-easyui-euploadify/jquery.euploadify.js"></script>

