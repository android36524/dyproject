<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.dysoft.top/kxbv3/tags" prefix="dytags" %>  

<%
	/* 路径相关的变量 */
	String path = request.getContextPath();
	String rootPath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();//recommend
	String basePath = rootPath + path; //recommend 
%>
<script type="text/javascript">
	var path = "<%=path%>";
	var basePath = "<%=basePath%>";
	var rootPath = "<%=rootPath%>";
</script>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="renderer" content="webkit">
