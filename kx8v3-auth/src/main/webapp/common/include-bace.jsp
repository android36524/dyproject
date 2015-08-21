<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String __BacePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
%>
<!--basic styles-->

<link href="<%=__BacePath%>/public/css/animate.css" rel="stylesheet" />
<link href="<%=__BacePath%>/public/bootstrap/ace/css/bootstrap.min.css" rel="stylesheet" />
<link rel="stylesheet" href="<%=__BacePath%>/public/bootstrap/ace/css/font-awesome.min.css" />

<!--[if IE 7]>
  <link rel="stylesheet" href="<%=__BacePath%>/public/bootstrap/ace/css/font-awesome-ie7.min.css" />
<![endif]-->

<!--page specific plugin styles-->

<!--fonts-->

<!-- <link rel="stylesheet" href="<%=__BacePath%>/public/google/font.css" />
 -->

<!--basic scripts-->
<!--[if IE]>
<script type="text/javascript">
 window.jQuery || document.write("<script src='<%=__BacePath%>/public/bootstrap/ace/js/jquery-1.10.2.min.js'>"+"<"+"/script>");
</script>
<![endif]-->

<!--[if !IE]>-->
<script type="text/javascript">
	window.jQuery || document.write("<script src='<%=__BacePath%>/public/bootstrap/ace/js/jquery-2.0.3.min.js'>" + "<"+"/script>");
</script>
<!--<![endif]-->


<script type="text/javascript">
	if ("ontouchend" in document)
		document.write("<script src='<%=__BacePath%>/public/bootstrap/ace/js/jquery.mobile.custom.min.js'>"
				+ "<"+"/script>");
</script>
<script src="<%=__BacePath%>/public/bootstrap/ace/js/bootstrap.min.js"></script>
<script src="<%=__BacePath%>/public/bootstrap/ace/js/typeahead-bs2.min.js"></script>

<!--page specific plugin scripts-->
<script src="<%=__BacePath%>/public/bootstrap/ace/js/jquery.dataTables.min.js"></script>
<script src="<%=__BacePath%>/public/bootstrap/ace/js/jquery.dataTables.bootstrap.js"></script>

<!--ace scripts-->
<script src="<%=__BacePath%>/public/bootstrap/ace/js/ace-elements.min.js"></script>
<script src="<%=__BacePath%>/public/bootstrap/ace/js/ace.min.js"></script>
<script src="<%=__BacePath%>/public/bootstrap/ace/js/ace-extra.min.js"></script>


<!--[if lte IE 8]>
<script type="text/javascript" src="<%=__BacePath%>/public/bootstrap/ace/js/html5shiv.js"></script>
<link rel="stylesheet" href="<%=__BacePath%>/public/bootstrap/ace/css/ace-ie.min.css" />
<![endif]-->

<!--[if lte IE 8]>
  <script src="<%=__BacePath%>/public/bootstrap/ace/js/excanvas.min.js"></script>
<![endif]-->

<script src="<%=__BacePath%>/public/bootstrap/ace/js/bootbox.min.js"></script>
<script src="<%=__BacePath%>/public/bootstrap/ace/js/fuelux/fuelux.spinner.min.js"></script>

<!-- jquery validate-->
<script src="<%=__BacePath%>/public/bootstrap/ace/js/jquery.validate.min.js"></script>
<script src="<%=__BacePath%>/public/lib/jquery.jdirk.js"></script>
<script src="<%=__BacePath%>/public/lib/validate/additional-methods.js"></script>
<script src="<%=__BacePath%>/public/lib/validate/messages_cn.js"></script>

<script src="<%=__BacePath%>/public/lib/jquery.form.js"></script>

<script type="text/javascript" src="<%=rootPath%>/public/lib/jquery.loadJSON.js"></script>

<script src="<%=__BacePath%>/public/bootstrap/ace/js/jquery-ui-1.10.3.custom.min.js"></script>
<script src="<%=__BacePath%>/public/bootstrap/ace/js/jquery.ui.touch-punch.min.js"></script>
<script src="<%=__BacePath%>/public/bootstrap/ace/js/jquery.slimscroll.min.js"></script>
<script src="<%=__BacePath%>/public/bootstrap/ace/js/jquery.easy-pie-chart.min.js"></script>
<!-- 线状图，需要时单独引用 -->
<%-- <script src="<%=__BacePath%>/public/bootstrap/ace/js/jquery.sparkline.min.js"></script> --%>

<script src="<%=__BacePath%>/public/bootstrap/ace/js/flot/jquery.flot.min.js"></script>
<script src="<%=__BacePath%>/public/bootstrap/ace/js/flot/jquery.flot.pie.min.js"></script>
<script src="<%=__BacePath%>/public/bootstrap/ace/js/flot/jquery.flot.resize.min.js"></script>
<script src="<%=__BacePath%>/public/lib/jquery.cookie.js"></script>

<script src="<%=__BacePath%>/public/bootstrap/ace/js/date-time/bootstrap-datepicker.min.js"></script>

<link rel="stylesheet" href="<%=__BacePath%>/public/bootstrap/ace/css/jquery.gritter.css" />
<script src="<%=__BacePath%>/public/bootstrap/ace/js/jquery.gritter.min.js"></script>

<!-- add by alex -->
<link rel="stylesheet" href="<%=__BacePath%>/public/bootstrap/ace/css/datepicker.css" />
<link rel="stylesheet" href="<%=__BacePath%>/public/bootstrap/ace/css/bootstrap-timepicker.css" />
<link rel="stylesheet" href="<%=__BacePath%>/public/bootstrap/ace/css/daterangepicker.css" />
<script src="<%=__BacePath%>/public/bootstrap/ace/js/date-time/bootstrap-timepicker.min.js"></script>
<script src="<%=__BacePath%>/public/bootstrap/ace/js/date-time/bootstrap-datepicker.min.js"></script>
<script src="<%=__BacePath%>/public/bootstrap/ace/js/date-time/daterangepicker.min.js"></script>
<script src="<%=__BacePath%>/public/bootstrap/ace/js/date-time/moment.min.js"></script>

<!-- 引入datetimepicker -->
<link rel="stylesheet" href="<%=__BacePath%>/public/bootstrap/datetimepicker/css/bootstrap-datetimepicker.min.css" />
<script src="<%=__BacePath%>/public/bootstrap/datetimepicker/js/bootstrap-datetimepicker.min.js"></script>
<script src="<%=__BacePath%>/public/bootstrap/datetimepicker/js/locales/bootstrap-datetimepicker.zh-CN.js"></script>

<!--file upload-->
<script src="<%=__BacePath%>/public/bootstrap/ace/js/dropzone.min.js"></script>
<link rel="stylesheet" href="<%=__BacePath%>/public/bootstrap/ace/css/dropzone.css" />


<link rel="stylesheet" href="<%=__BacePath%>/public/css/jqui/lightness/jqui.icons.css" />
<link rel="stylesheet" href="<%=__BacePath%>/public/bootstrap/ace/css/ui.jqgrid.css" />
<script src="<%=__BacePath%>/public/bootstrap/ace/js/jqGrid/jquery.jqGrid.min.js"></script>
<script src="<%=__BacePath%>/public/bootstrap/ace/js/jqGrid/i18n/grid.locale-cn.js"></script>

<!--ace styles-->
<link rel="stylesheet" href="<%=__BacePath%>/public/bootstrap/ace/css/ace.min.css" />
<link rel="stylesheet" href="<%=__BacePath%>/public/bootstrap/ace/css/ace-rtl.min.css" />
<link rel="stylesheet" href="<%=__BacePath%>/public/bootstrap/ace/css/ace-skins.min.css" />

<!--[if lte IE 8]>
  <script type="text/javascript" src="<%=__BacePath%>/public/bootstrap/ace/js/respond.min.js"></script>
<![endif]-->


<!-- uploadify -->
<script src="<%=__BacePath%>/public/lib/uploadify/jquery.uploadify.min.js"></script>
<link rel="stylesheet" href="<%=__BacePath%>/public/lib/uploadify/uploadify.css" />
<script src="<%=__BacePath%>/public/lib/uploadify/dy.uploadify.js"></script>

<!-- layer -->
<script src="<%=__BacePath%>/public/lib/layer/layer.min.js"></script>
<link rel="stylesheet" href="<%=__BacePath%>/public/lib/uploadify/uploadify.css" />

<!-- jquery json -->
<script type="text/javascript" src="<%=rootPath%>/public/lib/jquery.json-2.4.js"></script>

<!-- md5 -->
<script type="text/javascript" src="<%=rootPath%>/public/lib/crypto/crypto-md5.js"></script>

<!-- dy script -->
<script src="<%=__BacePath%>/public/dyjs/dyjs-common.js"></script>
<link rel="stylesheet" href="<%=__BacePath%>/public/lib/layer/skin/layer.css" />
<link rel="stylesheet" href="<%=__BacePath%>/public/lib/layer/skin/layer.ext.css" />

<!-- ztree -->
<link rel="stylesheet" href="<%=__BacePath%>/public/lib/ztree/css/zTreeStyle/zTreeStyle.css" type="text/css">
<script type="text/javascript" src="<%=__BacePath%>/public/lib/ztree/js/jquery.ztree.core-3.5.js"></script>
<script type="text/javascript" src="<%=__BacePath%>/public/lib/ztree/js/jquery.ztree.excheck-3.5.js"></script>
<script type="text/javascript" src="<%=__BacePath%>/public/lib/ztree/js/jquery.ztree.exedit.js"></script>

<!-- ie8,ie9等过时浏览器 支持placeholder -->
<script type="text/javascript" src="<%=rootPath%>/public/lib/include-pholderIE8.js"></script>
<script src="<%=path%>/js/commonJS.js"></script>
<script type="text/javascript">
	$(function(){
		bootbox.setDefaults({locale : "zh_CN"});
		$('[data-rel=tooltip]').tooltip();
	});
</script>