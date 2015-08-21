<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="cn">
<head>
	<meta charset="utf-8" />
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<%@include file="/include-path.jsp"%>
	<%@include file="/common/include-bace.jsp"%>
	<title>大洋塔尖名师-服务器发生内部错误</title>
</head>
<body>
	<div class="error-container">
			<div class="well">
				<h1 class="grey lighter smaller">
					<span class="blue bigger-125">
						<i class="icon-random"></i>
						500
					</span>
					服务器内部错误
				</h1>

				<hr />
				<h3 class="lighter smaller">
					我们正在努力修复
					<i class="icon-wrench icon-animated-wrench bigger-125"></i>
					它！
				</h3>

				<div class="space"></div>

				<div>
					<h4 class="lighter smaller">你可以进行以下操作:</h4>

					<ul class="list-unstyled spaced inline bigger-110 margin-15">
						<li>
							<i class="icon-hand-right blue"></i>
							阅读使用指南
						</li>

						<li>
							<i class="icon-hand-right blue"></i>
							联系我们反馈错误！
						</li>
					</ul>
				</div>

				<hr />
				<div class="space"></div>

				<div class="center">
					<a href="javascript:window.history.back()" class="btn btn-grey">
						<i class="icon-arrow-left"></i>
						返回
					</a>

					<a href="<%=path %>" class="btn btn-primary">
						<i class="icon-dashboard"></i>
						主页
					</a>
				</div>
			</div>
		</div>
</div>
</body>
</html>