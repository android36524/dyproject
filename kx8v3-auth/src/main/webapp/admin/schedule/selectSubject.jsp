<%@page import="org.apache.commons.lang.StringUtils"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div class="row">
<label class="form-group col-md-3"  align="right">当前设置课次：</label>
<label class="form-group col-md-9"><strong>${param['weekName']} ${param['sectionName']}</strong></label>
</div>
<div class="row">
<label class="form-group col-md-3" align="right">年级科目：</label>
<select class="form-group col-md-9" id="subjectId" style="width: 150px;">
<c:forEach var="sub" items="${subList }">
	<option value="${sub.subjectId}" teaId="${sub.teacherId}" teaName="${sub.teaName }">${sub.subjectName}</option>
</c:forEach>
</select>	
</div>
<br/>

