<%@page import="org.apache.commons.lang.StringUtils"%>
<%@ page import="com.dayang.commons.util.DaYangCommonUtil" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@include file="/include-path.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%
  String id = StringUtils.defaultIfEmpty(request.getParameter("id"), "");
%>

<script type="text/javascript">
  var _id = "<%=id%>";
</script>

<style>
.cut-off-rule{
	border-bottom-style:solid;
	border-bottom-width:1px;
	border-bottom-color:#ccc;
	margin-bottom: 20px;
	font-weight:bold;
}

.modal-dialog{
	width: 1000px;
}

.cut-all-rule{
	border-style:solid;
	border-width:1.5px;
	border-color:#ccc;
	font-weight:bold;
	background-color: #ccc;
	margin-bottom:20px
}
</style>
<div>
  <form class="form-horizontal dy-dialog-form" id="stuParentDetail_form" method="post">
<c:forEach var="fieldmodel" items="${showField}" varStatus="cur" step="3">
 	<div class="row"> 	
      <div class="col-sm-4">
        <div class="form-group">
          <label class="control-label col-sm-4 no-padding-right" for="alterstu_userName">${ fieldmodel.name}:</label>
         <div class="col-sm-8">
            <div class="clearfix">
              <div class="clearfix">
              	<label id="alterstu_userName"  class="col-sm-8 col-sm-12">
              		<c:set var="colvalue" value="${fieldmodel.fieldName}" />  
              		<c:choose>
			       		<c:when test="${fieldmodel.dataType==2}">             
			            	<fmt:formatDate value="${changeModel[colvalue]}" type="both" pattern="yyyy/MM/dd HH:mm:ss" /> 
			            </c:when>
			            <c:otherwise>
			            	${changeModel[colvalue]}
			            </c:otherwise>
		            </c:choose>
		          </label>  
              	</label>
            	</div>       
            </div>
          </div>
        </div>
      </div>     
     <c:if test="${ not empty showField[cur.index+1]}">
     <div class="col-sm-4">
        <div class="form-group">
          <label class="control-label col-sm-12 col-sm-4 no-padding-right" for="alterstu_sex_showname">${showField[cur.index+1].name}:</label>
         <div class="col-sm-8">
            <div class="clearfix">
              <label id="alterstu_sex_showname"  class="col-sm-8 col-sm-12">
              	<c:set var="colvalue" value="${showField[cur.index+1].fieldName}" />
               	<c:choose>
			       	<c:when test="${showField[cur.index+1].dataType==2}">             
			           	<fmt:formatDate value="${changeModel[colvalue]}" type="both" pattern="yyyy/MM/dd HH:mm:ss" /> 
			        </c:when>
			        <c:otherwise>
			          	${changeModel[colvalue]}
			        </c:otherwise>
		      	</c:choose>
		      </label>	
            </div>
          </div>
        </div>
      </div>
     </c:if>
      
    <c:if test="${ not empty showField[cur.index+2]}">
     <div class="col-sm-4">
        <div class="form-group">
          <label class="control-label col-sm-12 col-sm-4 no-padding-right" for="alterstu_sex_showname">${showField[cur.index+2].name}:</label>
         <div class="col-sm-8">
            <div class="clearfix">
              <label id="alterstu_sex_showname"  class="col-sm-8 col-sm-12">
             <c:set var="colvalue" value="${showField[cur.index+2].fieldName}" />  
              	<c:choose>
			       	<c:when test="${showField[cur.index+2].dataType==2}">             
			           	<fmt:formatDate value="${changeModel[colvalue]}" type="both" pattern="yyyy/MM/dd HH:mm:ss" /> 
			        </c:when>
			        <c:otherwise>
			          	${changeModel[colvalue]}
			        </c:otherwise>
		      	</c:choose>
		      </label>
            </div>
          </div>
        </div>
      </div>
     </c:if>
    <div class="space-2"></div>
    </div>
  </c:forEach>
   
    
  </form>
</div>
