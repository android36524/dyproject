<%@page import="org.apache.commons.lang.StringUtils"%>
<%@ page import="com.dayang.commons.util.DaYangCommonUtil" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@include file="/include-path.jsp"%>
<%
  String id = StringUtils.defaultIfEmpty(request.getParameter("id"), "");
%>
<script type="text/javascript">
  var _id = "<%=id%>";
  $("#stuParent_ID").val(_id);
  $("#schId").val(parent._schId);
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
  <form class="form-horizontal dy-dialog-form" id="manageStudent_form" method="post">

	<div class="cut-off-rule">快速关联孩子</div>
	 <div class="form-group">
      	请关联您的孩子（请至少关联一个孩子）:
    </div>
    <div class="space-2"></div>

    <div class="form-group">
      <label class="control-label col-xs-12 col-sm-5 no-padding-right" for="relaStu_name">请选择孩子:</label>
   	   <div class="input-group col-xs-12 col-sm-7 ">
	        <div class="clearfix">
	          <input type="hidden" name="id" id="stuParent_ID"  >
	          <input type="hidden" name="schId" id="schId"  >
	          <input type="text" name="name"  id="relaStu_name" class="col-xs-10 col-sm-12" readonly="readonly"/>
	        </div>
<!-- 	        <input type="hidden" name="id" id="relaStu_id"/> -->
	        <input type="hidden" name="relaStudents" id="rela_Stu" />
	        <span class="input-group-btn">
				<button type="button" id="searchButton" class="btn btn-purple btn-sm">
					<i class="icon-search icon-on-right bigger-110"></i>
				</button>
			</span>
       </div>
    </div>
    <div class="space-2"></div>
    
     <div class="form-group">
      <label class="control-label col-xs-12 col-sm-5 no-padding-right" for="relaStu_relationType">关系:</label>
      <div class="col-xs-12 col-sm-7">
        <div class="clearfix">
            <select class="col-xs-12 col-sm-12" id="relaStu_relationType" dyId="chooseId3" name="relaStu.relationType" style="width:150px" >
                <option value=''>--请选择--</option>
                <c:forEach  items="${relationList}" var="dic" >
                	<option value='${dic.value}'>${dic.name}</option>
                </c:forEach>
             </select>
        </div>
      </div>
    </div>
    <div class="space-2"></div>
    
    <div class="form-group">
      <label class="control-label col-xs-12 col-sm-5 no-padding-right" for="dept_deptName">是否监护人:</label>
      <div class="col-xs-12 col-sm-7">
        <div class="clearfix">
          <label style="margin-right: 20px">
		    <input type="radio" name="relaStu.guardian" id="relaStu_guardian1" value="1" checked="checked">
		    <span>是</span>
		  </label>
		  
		  <label>
		    <input type="radio" name="relaStu.guardian" id="relaStu_guardian2" value="0" >
		    <span>否</span>
		  </label>
        </div>
      </div>
    </div>
    <div class="space-2"></div>
    
    <div class="form-group">
   	   <div class="input-group col-xs-12 col-sm-7 ">
	        <div class="clearfix">
	             <input type="button" class="btn btn-primary dy-search-button " id="choose_studentBtn" value="关联孩子">
	        </div>
       </div>
    </div>
    <div class="space-2"></div>
    
   
     
     <div class="row">
       	<div class="cut-all-rule">已关联学生列表</div>
    	<div class="col-xs-12" style="overflow-x:hidden;overflow-y:hidden;height:150px;">
          <table id="manageStu-table" name="chooseTable"></table>
     	</div>
    </div>
    <div class="space-2"></div>
    
  </form>
</div>
<script type="text/javascript" src="js/manageStudent.js"></script>