<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<%@include file="/include-path.jsp"%>
	<%@include file="/common/include-bace.jsp"%>
	<title>ACE 文件上传DEMO</title>
</head>
<body>
	<div id="courseFile">
		
	</div>
	
	<script type="text/javascript">
		$(function(){
			$("#courseFile").dyuploadify({
				buttonText : "选择课程图片",
				onUploadSuccess : function(file,data,res){
					console.log(file);
					console.log(data);
					console.log(res);
				}
			});
		});
	</script>
</body>
</html>