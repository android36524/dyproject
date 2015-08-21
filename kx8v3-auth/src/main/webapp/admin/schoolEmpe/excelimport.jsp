<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<html>
<body>
<div>
		<form class="form-horizontal dy-dialog-form" id="excel_form" method="post">
		<div class="form-group">
			<label class="control-label col-xs-12 col-sm-5 no-padding-right" for="excelName">excel文件:</label>
			<div class="col-xs-12 col-sm-7">
					<div class="input-group">
						<span class="input-icon">
							<div class="clearfix">
								<input class="col-xs-12 col-sm-10 spinner-input" type="text" id="excelName" name="excelName" readonly="readonly" value=''/>
							</div>
							<input type="hidden" name="excelFileName" id="excelFileName" />
						</span>
						<div class="input-group-btn">
							<div id="courseUpload" >
							</div>
						</div> 
					</div>
			</div>
		</div>
		<div class="space-2"></div>
		</form>
</div>
<script type="text/javascript" src="js/excelimport.js"></script>
</body>
</html>
