<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/include-core.jsp" %>
<title>大洋基础平台登录</title>
</head>
<body>
	<h3>大洋基础平台登录</h3>
	<form method="POST" action="<%=path %>/demo/login" name="f">
		<table>
			<tbody>
				<tr>
					<td>用户名:</td>
					<td><input type="text" value="${name }" name="name">${nameMsg }</td>
				</tr>
				<tr>
					<td>密码:</td>
					<td><input type="password" name="pwd" value="${pwd }">${pwdMsg }</td>
				</tr>
				<tr>
					<td colspan="2"><input type="submit" value="登录"
						name="submit"></td>
				</tr>
			</tbody>
		</table>
	</form>
</body>
</html>