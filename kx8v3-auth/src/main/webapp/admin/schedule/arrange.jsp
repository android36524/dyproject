<%@page import="org.apache.commons.lang.StringUtils"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 
<style>
.td-hover:hover{
background-color:yellow;
cursor:pointer;
}
.masthead {
  background-color: #337ab7;
}
</style>
<table class="table table-bordered" id="assign-table">
<tr class="masthead"><td align="center" colspan="${fn:length(weekList)+2}"><strong>${classInfo.name }【班主任：${ classInfo.headTeacher}】  课程表</strong></td></tr>
<tr>
<td align="center"><strong>节次/星期</strong></td>
<c:forEach var="week" items="${weekList }">	
 <td align="center" weekid='${week.id}'><strong>${week.name}</strong></td>
</c:forEach>
<td></td>
</tr>
<c:forEach var="time" items="${timeList }">
<tr>
<td colspan="${fn:length(weekList)+2}" align="center">${time.name}</td>
</tr>

<c:forEach var="section" items="${sectionList }">
<c:if test="${section.timeNode==time.value }">
	<tr><td align="center" sectionId='${section.id}'>${section.name}</td>
		<c:forEach var="week" items="${weekList }">
			<c:set value="0" var="subjectId" />
			<c:set value="0" var="teacherId" />	
			<c:set value="" var="teacherName" />	
			<c:set value="" var="subjectName" />	
			<c:if test="${not empty detailList }">
				<c:forEach var="detail" items="${detailList }">
					<c:if test="${ detail.weekId==week.id and detail.sectionId==section.id}">
						
						<c:set value="${detail.subjectId}" var="subjectId" />
						<c:set value="${detail.teacherId}" var="teacherId" />
						<c:set value="${detail.teaName}" var="teacherName" />
						<c:set value="${detail.subjectName}" var="subjectName" />
					</c:if>
				</c:forEach>		
			</c:if>			
			<td title="点击排课" class="td-hover" <c:if test="${subjectId>0 }">subId="${subjectId }"</c:if> <c:if test="${teacherId>0 }">teaId="${ teacherId}"</c:if>  align="center" weekid='${week.id}' sectionId='${section.id}' onclick="setSubjectAndTea('${week.id}','${section.id}','${week.name}','${section.name }')">			
				<c:if test="${subjectId>0 }">${subjectName }<br/>${teacherName }</c:if>
			</td>
		</c:forEach>
		<td><input sectionId='${section.id}' type="checkbox" <c:if test="${section.flag_class==2 or (section.flag==2 and section.flag_class!=1) }">checked </c:if> name="cksection">隐藏</td>
	</tr>
</c:if>	
</c:forEach>
</c:forEach>
</table>
<input type="hidden" id="semersterId" value="${curSemester.id}">
<input type="hidden" id="scheduleId" value="${scheduleId}">

<script type="text/javascript">
var _classId = '${param['classId']}';
var _scheduleId = '${scheduleId}';

function setSubjectAndTea(weekId,sectionId,weekName,sectionName){
	commonJs.toPostUrl("选择科目","保存",path + "/admin/schedule/toSelectSubject?classId="+_classId+"&weekId="+weekId+"&sectionId="+sectionId,{'weekName':weekName,'sectionName':sectionName},function(result) {
		if(result){
			var sub = $("#subjectId").find("option:selected");
			var teaId= sub.attr("teaId");
			if(!sub.val()){
				$.gritter.add({
                    title: '排课提示',
                    text: "请选择科目，如果没有没有科目，请给该班设置任课老师",
                    sticky: false,
                    time: '',
                    class_name: "gritter-light"
                });
				return;
			}
			var teaName= sub.attr("teaName");
			var subId = sub.val();			
			var ctd = $("#assign-table").find("td[weekid='"+weekId+"'][sectionId='"+sectionId+"']");
			// 验证是否已经教过
			$.get(path + "/admin/schedule/validatorTeaIsAssigned?scheduleId="+_scheduleId+"&teaId="+teaId+"&weekId="+weekId+"&sectionId="+sectionId,function(ret){
				if(ret.code!=1){
					$.gritter.add({
                        title: '排课提示',
                        text: ret.code == "1" ? "添加成功" : ret.msg,
                        sticky: false,
                        time: '',
                        class_name: "gritter-light"
                    });
				}else{
					ctd.empty();
					ctd.append("<span>"+sub.text()+"<br/>"+teaName+"</span>");
					ctd.attr("teaId",teaId);
					ctd.attr("subId",subId);
				}
			});
						
		}
	});	
}
</script>