<%@page import="org.apache.commons.lang.StringUtils"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<style>
.td-hover:hover {
	background-color: yellow;
	cursor: pointer;
}

.masthead {
	background-color: #337ab7;
}
</style>
<table class="table table-bordered" id="assign-table">
	<c:set var="sections"
		value="${fn:split('0,第一节,第二节,第三节,第四节,第五节,第六节,第七节,第八节,第九节,第十节,第十一节,第十二节', ',')}" />
	<c:set var="index" value="0" />
	<tr class="masthead">
		<td align="center" colspan="${fn:length(weekList)+1}"><strong>${classInfo.name
					}【班主任：${ classInfo.headTeacher}】 课程表</strong></td>
	</tr>
	<tr>
		<td align="center"><strong>节次/星期</strong></td>
		<c:forEach var="week" items="${weekList }">
			<td align="center" weekid='${week.id}'><strong>${week.name}</strong></td>
		</c:forEach>
	</tr>
	<c:forEach var="time" items="${timeList }">
		<c:set var="timeindex" value="0" />
			<c:forEach var="section" items="${sectionList }"
			varStatus="sectionIndex">
			<c:if
				test="${section.timeNode==time.value and ((section.flag==1 and section.flag_class!=2) or section.flag_class==1)}">
				<c:set var="index" value="${index+1}" />
				<c:set var="timeindex" value="${timeindex+1}" />
				<c:if test="${timeindex==1 }">
					<tr>
						<td colspan="${fn:length(weekList)+1}" align="center">${time.name}</td>
					</tr>
				</c:if>
				<tr>
					<td align="center" sectionId='${section.id}'>${sections[index]}</td>
					<c:forEach var="week" items="${weekList }">
						<c:set value="0" var="subjectId" />
						<c:set value="0" var="teacherId" />
						<c:set value="" var="teacherName" />
						<c:set value="" var="subjectName" />
						<c:if test="${not empty detailList }">
							<c:forEach var="detail" items="${detailList }">
								<c:if
									test="${ detail.weekId==week.id and detail.sectionId==section.id}">
									<c:set value="${detail.subjectId}" var="subjectId" />
									<c:set value="${detail.teacherId}" var="teacherId" />
									<c:set value="${detail.teaName}" var="teacherName" />
									<c:set value="${detail.subjectName}" var="subjectName" />
								</c:if>
							</c:forEach>
						</c:if>
						<td class="td-hover"
							<c:if test="${subjectId>0 }">subId="${subjectId }"</c:if>
							<c:if test="${teacherId>0 }">teaId="${ teacherId}"</c:if>
							align="center" weekid='${week.id}' sectionId='${section.id}'>
							<c:if test="${subjectId>0 }">${subjectName }<br />${teacherName }</c:if>
						</td>
					</c:forEach>
				</tr>
			</c:if>
		</c:forEach>
	</c:forEach>
</table>


