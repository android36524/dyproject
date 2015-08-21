<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/include-path.jsp"%>
<%@include file="/common/include-bace.jsp"%>
<title>组织机构树选择</title>
</head>
<body>
	<%@include file="/admin/common/orgTree/index.jsp" %>
	<script type="text/javascript">
		$("[f=dl_del]").remove();
		$("#div_query_group").css({"right" : "0px"});
	</script>
</body>
</html>