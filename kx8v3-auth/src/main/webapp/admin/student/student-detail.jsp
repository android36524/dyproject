<%@page import="org.apache.commons.lang.StringUtils"%>
<%@ page import="com.dayang.commons.util.DaYangCommonUtil" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@include file="/include-path.jsp"%>
<%
	String id = StringUtils.defaultIfEmpty(request.getParameter("id"), "");
  String _img = DaYangCommonUtil.getAppProperties("ftp.access.base");
  String schId = StringUtils.defaultIfEmpty(request.getParameter("schId"), "");
  // String yes= EnumAll.IsYesOrNot.IsYes.getValueStr();
%>
<script type="text/javascript">
  var _id = "<%=id%>";
  var _imgpath = "<%=_img%>";
</script>
<style>
.cut-off-rule{
	border-bottom-style:solid;
	border-bottom-width:1px;
	border-bottom-color:#ccc;
	margin-bottom: 20px;
	font-weight:bold;
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
  <form class="form-horizontal dy-dialog-form" id="student_form" method="post">
    
 
	<div class="cut-off-rule" >学生基本信息</div>

    <div class="row">
      <div class="col-xs-6">
        <div class="form-group">
          <label class="control-label col-xs-12 col-sm-5 no-padding-right" for="student_name"><span style="color: red"> *&nbsp;</span>学生姓名:</label>
         <div class="col-xs-12 col-sm-9">
            <div class="clearfix">
              <input type="hidden" name="student.schId" id="schId" value="<%= schId%>"/>
               
              <input type="hidden" name="student.id" id="student_id"  >
              <input type="text" name="student.name" id="student_name" class="col-xs-12 col-sm-12 spinner-input" readonly="readonly"  />
            </div>
          </div>
        </div>
      </div>
      <div class="col-xs-6">
        <div class="form-group">
          <label class="control-label col-xs-12 col-sm-5 no-padding-right" for="student_nameSpell">姓名简拼:</label>
          <div class="col-xs-12 col-sm-9">
            <div class="clearfix">
              <input type="text" name="student.nameSpell" id="student_nameSpell"  readonly="readonly" class="col-xs-12 col-sm-12 spinner-input" />
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="space-2"></div>
    
    <div class="row">
      <div class="col-xs-6">
        <div class="form-group">
          <label class="control-label col-xs-12 col-sm-5 no-padding-right" for="student_gradeId"><span style="color: red"> *&nbsp;&nbsp;</span>年级:</label>
         <div class="col-xs-12 col-sm-9">
            <div class="clearfix">
              <select class="col-xs-12 col-sm-12" id="student_gradeId" dyId="chooseId2" name="student.gradeId" disabled="disabled">
                <option value=''>--请选择--</option>
                <c:forEach items="${gradeList}" var="dic">
                	<option value="${dic.id}">${dic.name}</option>
                </c:forEach>
              </select>
            </div>
          </div>
        </div>
      </div>
      <div class="col-xs-6">
        <div class="form-group">
          <label class="control-label col-xs-12 col-sm-5 no-padding-right" for="student_classId"><span style="color: red"> *&nbsp;&nbsp;</span>班级:</label>
          <div class="col-xs-12 col-sm-9">
            <div class="clearfix">
              <select class="col-xs-12 col-sm-12" id="student_classId" dyId="chooseId2" name="student.classId" disabled="disabled">
                <option value=''>--请选择--</option>
                <c:if test="${not empty classList}">
	                <c:forEach items="${classList}" var="dic">
	                	<option value="${dic.id}">${dic.name}</option>
	                </c:forEach>
                 </c:if>
              </select>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="space-2"></div>
    
     <div class="row">
      <div class="col-xs-6">
        <div class="form-group">
          <label class="control-label col-xs-12 col-sm-5 no-padding-right" for="student_sex1"><span style="color: red"> *&nbsp;&nbsp;</span>性别:</label>
          <div class="col-xs-12 col-sm-9">
			   <label style="margin-right: 20px">
			    <input type="radio" name="student.sex" id="student_sex0"  value="${manVal}"  checked disabled>
			     <span>${manName}<span>
			  </label>
			  
			  <label>
			    <input type="radio" name="student.sex" id="student_sex1" value="${woManVal}" disabled>
			   <span>${woManName}<span>
			  </label>
          </div>
        </div>
      </div>
      <div class="col-xs-6">
        <div class="form-group">
          <label class="control-label col-xs-5 no-padding-right" for="student_birthDate"><span style="color: red"> *&nbsp;&nbsp;</span>出生日期:</label>
          
          <div class="col-xs-7 input-group">
            <span class="input-group-addon">
                <i class="icon-calendar bigger-110"></i>
            </span><input class="form-control" type="text" name="student.birthDate" id="student_birthDate" readonly="readonly" />
          </div>
        </div>
      </div>
    </div>
    <div class="space-2"></div>
    
    <div class="row">
      <div class="col-xs-6">
        <div class="form-group">
          <label class="control-label col-xs-12 col-sm-5 no-padding-right" for="student_studentNo"><span style="color: red"> *&nbsp;&nbsp;</span>学号:</label>
          <div class="col-xs-12 col-sm-9">
            <div class="clearfix">
             <input type="text" name="student.studentNo" id="student_studentNo" class="col-xs-12 col-sm-12 spinner-input" readonly="readonly" />
            </div>
          </div>
        </div>
      </div>
      <div class="col-xs-6">
        <div class="form-group">
          <label class="control-label col-xs-12 col-sm-5 no-padding-right" for="student_hometown">籍贯:</label>
          <div class="col-xs-12 col-sm-9">
            <div class="clearfix">
              <input type="text" name="student.hometown" id="student_hometown" class="col-xs-12 col-sm-12 spinner-input" readonly="readonly" />
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="space-2"></div>
    
    
    <div class="row">
      <div class="col-xs-6">
        <div class="form-group">
          <label class="control-label col-xs-12 col-sm-5 no-padding-right" for="student_birthPlace">出生地:</label>
          <div class="col-xs-12 col-sm-9">
            <div class="clearfix">
             <input type="text" name="student.birthPlace" id="student_birthPlace" class="col-xs-12 col-sm-12 spinner-input" readonly="readonly"  />
            </div>
          </div>
        </div>
      </div>
      <div class="col-xs-6">
        <div class="form-group">
          <label class="control-label col-xs-12 col-sm-5 no-padding-right" for="student_enrolTime">入校时间:</label>
           <div class="col-xs-7 input-group">
            <span class="input-group-addon">
                <i class="icon-calendar bigger-110"></i>
            </span><input class="form-control" type="text" name="student.enrolTime" id="student_enrolTime" readonly="readonly"  />
          </div>
        </div>
      </div>
    </div>
    <div class="space-2"></div>
    
    <div class="row">
      <div class="col-xs-6">
        <div class="form-group">
          <label class="control-label col-xs-12 col-sm-5 no-padding-right" for="student_originalSchool">来源校（园）:</label>
          <div class="col-xs-12 col-sm-9">
            <div class="clearfix">
             <input type="text" name="student.originalSchool" id="student_originalSchool" class="col-xs-12 col-sm-12 spinner-input" readonly="readonly"  />
            </div>
          </div>
        </div>
      </div>
      <div class="col-xs-6">
        <div class="form-group">
          <label class="control-label col-xs-12 col-sm-5 no-padding-right" for="student_enrolScore">入学成绩:</label>
          <div class="col-xs-12 col-sm-9">
            <div class="clearfix">
              <input type="text" name="student.enrolScore" id="student_enrolScore" class="col-xs-12 col-sm-12 spinner-input" readonly="readonly"  />
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="space-2"></div>
    
    <div class="row">
      <div class="col-xs-6">
        <div class="form-group">
          <label class="control-label col-xs-12 col-sm-5 no-padding-right" for="student_idCard">身份证号:</label>
          <div class="col-xs-12 col-sm-9">
            <div class="clearfix">
               <input type="text" name="student.idCard" id="student_idCard" class="col-xs-12 col-sm-12 spinner-input" readonly="readonly"  />
            </div>
          </div>
        </div>
      </div>
      <div class="col-xs-6">
        <div class="form-group">
          <label class="control-label col-xs-12 col-sm-5 no-padding-right" for="student_status">学生当前状态:</label>
          <div class="col-xs-12 col-sm-9">
            <div class="clearfix">
               <input type="hidden" name="student.status" id="student_status" value="${status_id}" />
               <input type="text"  value="${status_name}" class="col-xs-12 col-sm-12 spinner-input" readonly="readonly" />
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="space-2"></div>
    
    <div class="row">
      <div class="col-xs-6">
        <div class="form-group">
          <label class="control-label col-xs-12 col-sm-5 no-padding-right" for="student_nation">民族:</label>
          <div class="col-xs-12 col-sm-9">
            <div class="clearfix">
               <select class="col-xs-12 col-sm-12" id="student_nation" dyId="chooseId2" name="student.nation" disabled="disabled">
                <option value=''>--请选择--</option>
                <c:forEach var="dic" items="${nationList }">											
						<option value="${dic.value}" 
							<c:if test="${dic.name=='汉族'}">
   	 							<c:out value="selected='selected'"></c:out>
							</c:if>
					   >${dic.name}</option>					
					</c:forEach>
              </select>
               
            </div>
          </div>
        </div>
      </div>
      <div class="col-xs-6">
        <div class="form-group">
          <label class="control-label col-xs-12 col-sm-5 no-padding-right" for="stuInfo_political">政治面貌:</label>
          <div class="col-xs-12 col-sm-9">
             <div class="clearfix">
              <select class="col-xs-12 col-sm-12" id="student_political" dyId="chooseId2" name="stuInfo.political" disabled="disabled">
                <option value=''>--请选择--</option>
                <c:forEach items="${politicalList}" var="dic">
                	<option value="${dic.value}">${dic.name}</option>
                </c:forEach>
              </select>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="space-2"></div>
    
    
    <div class="row">
      <div class="col-xs-6">
        <div class="form-group">
          <label class="control-label col-xs-12 col-sm-5 no-padding-right" for="stuInfo_health">健康状况:</label>
          <div class="col-xs-12 col-sm-9">
            <div class="clearfix">
                <select class="col-xs-12 col-sm-12" id="student_health" dyId="chooseId2" name="stuInfo.health" disabled="disabled">
                <option value=''>--请选择--</option>
                <c:forEach items="${healthList}" var="dic">
                	<option value="${dic.value}">${dic.name}</option>
                </c:forEach>
              </select>
            </div>
          </div>
        </div>
      </div>
      <div class="col-xs-6">
        <div class="form-group">
          <label class="control-label col-xs-12 col-sm-5 no-padding-right" for="student_fromWhere">学生来源:</label>
          <div class="col-xs-12 col-sm-9">
             <div class="clearfix">
              <select class="col-xs-12 col-sm-12" id="student_fromWhere" dyId="chooseId2" name="student.fromWhere" disabled="disabled">
                <option value=''>--请选择--</option>
                <c:forEach items="${fromWhereList}" var="dic">
                	<option value="${dic.value}">${dic.name}</option>
                </c:forEach>
              </select>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="space-2"></div>
    
    <div class="row">
  
      <div class="col-xs-6">
        <div class="form-group">
          <label class="control-label col-xs-12 col-sm-5 no-padding-right" for="student_rollCode">学籍号:</label>
          <div class="col-xs-12 col-sm-9">
            <div class="clearfix">
              <input type="text" name="student.rollCode" id="student_rollCode" class="col-xs-12 col-sm-12 spinner-input"  readonly="readonly" />
            </div>
          </div>
        </div>
      </div>
     
       <div class="col-xs-6">
        <div class="form-group">
          <label class="control-label col-xs-12 col-sm-5 no-padding-right" for="student_isReside">就读方式：</label>
          <div class="col-xs-12 col-sm-9">
             <div class="clearfix">
			   <label style="margin-right: 20px">
			    <input type="radio" name="student.isReside" id="student_isReside1"  value="${goReadVal}"  checked  disabled>
			     <span>${goReadName}<span>
			  </label>
			  
			  <label>
			    <input type="radio" name="student.isReside" id="student_isReside2" value="${ResidenceVal}" disabled >
			   <span>${ResidenceName}<span>
			  </label>
            </div>
          </div>
        </div>
    </div>
    <div class="space-2"></div>
    
    
    <div class="row">
      <div class="col-xs-6">
        <div class="form-group">
          <label class="control-label col-xs-12 col-sm-5 no-padding-right" for="student_image">照片:</label>
          <div class="col-xs-12 col-sm-9">
            <div class="clearfix">
                <img style="height: 138px;width: 120px;border:1px solid #ccc;" id="imageUrl" src="<%=path%>/css/img/defaultpic.gif"/>
                <input type="hidden" name="student.image" id="student_image" />
             </div>
          </div>
        </div>
      </div>
    </div>
    <div class="space-2"></div>
    
    <div class="cut-off-rule">联系方式</div>
    <div class="row">
      <div class="col-xs-6">
        <div class="form-group">
          <label class="control-label col-xs-12 col-sm-5 no-padding-right" for="student_telephone">联系电话:</label>
         <div class="col-xs-12 col-sm-9">
            <div class="clearfix">
              <input type="text" name="student.telephone" id="student_telephone" class="col-xs-12 col-sm-12 spinner-input" readonly="readonly"  />
            </div>
          </div>
        </div>
      </div>
      <div class="col-xs-6">
        <div class="form-group">
          <label class="control-label col-xs-12 col-sm-5 no-padding-right" for="stuInfo_ziplCode">邮编:</label>
          <div class="col-xs-12 col-sm-9">
            <div class="clearfix">
              <input type="text" name="stuInfo.ziplCode" id="student_ziplCode" class="col-xs-12 col-sm-12 spinner-input" readonly="readonly" />
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="space-2"></div>
    
    
    <div class="row">
      <div class="col-xs-12">
        <div class="form-group">
          <label class="control-label col-xs-12 col-sm-5 no-padding-right" for="student_homeAddr">家庭住址:</label>
         <div class="col-xs-12 col-sm-9">
             <div class="clearfix">
        	   <input type="text" name="student.homeAddr" id="student_homeAddr" class="col-xs-10 col-sm-12 spinner-input" readonly="readonly" />
        	</div>
          </div>
        </div>
      </div>
    </div>
    <div class="space-2"></div>
    
    <div class="row">
      <div class="col-xs-12">
        <div class="form-group">
          <label class="control-label col-xs-12 col-sm-5 no-padding-right" for="stuInfo_postalAddr">通信地址:</label>
         <div class="col-xs-12 col-sm-9">
           <div class="clearfix">
        	  <input type="text" name="stuInfo.postalAddr"  id="student_postalAddr" class="col-xs-12 col-sm-12" readonly="readonly"  />
        	</div>
          </div>
        </div>
      </div>
    </div>
    <div class="space-2"></div>
    
     <div class="cut-off-rule">其他</div>
    <div class="row">
      <div class="col-xs-6">
        <div class="form-group">
          <label class="control-label col-xs-12 col-sm-5 no-padding-right" for="stuInfo_householdLocation">户口所在地:</label>
         <div class="col-xs-12 col-sm-9">
            <div class="clearfix">
              <input type="text" name="stuInfo.householdLocation" id="student_householdLocation" class="col-xs-12 col-sm-12 spinner-input" readonly="readonly"  />
            </div>
          </div>
        </div>
      </div>
      <div class="col-xs-6">
        <div class="form-group">
          <label class="control-label col-xs-12 col-sm-5 no-padding-right" for="stuInfo_householdNature">户口性质:</label>
          <div class="col-xs-12 col-sm-9">
            <div class="clearfix">
              <select class="col-xs-12 col-sm-12" id="student_householdNature" dyId="chooseId2" name="stuInfo.householdNature" disabled="disabled">
                <option value=''>--请选择--</option>
                <c:forEach items="${householdNatureList}" var="dic">
                	<option value="${dic.value}">${dic.name}</option>
                </c:forEach>
              </select>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="space-2"></div>
    
    <div class="row">
      <div class="col-xs-6">
        <div class="form-group">
          <label class="control-label col-xs-12 col-sm-5 no-padding-right" for="stuInfo_disabilityType">残疾类型:</label>
         <div class="col-xs-12 col-sm-9">
           <div class="clearfix">
              <select class="col-xs-12 col-sm-12" id="student_disabilityType" dyId="chooseId2" name="stuInfo.disabilityType" disabled="disabled">
                <option value=''>--请选择--</option>
                <c:forEach items="${disabilityList}" var="dic">
                	<option value="${dic.value}">${dic.name}</option>
                </c:forEach>
              </select>
            </div>
          </div>
        </div>
      </div>
      <div class="col-xs-6">
        <div class="form-group">
          <label class="control-label col-xs-12 col-sm-5 no-padding-right" for="stuInfo_stayList">留守儿童:</label>
          <div class="col-xs-12 col-sm-9">
            <div class="clearfix">
              <select class="col-xs-12 col-sm-12" id="student_stay" dyId="chooseId2" name="stuInfo.stay" disabled="disabled">
                <option value=''>--请选择--</option>
                <c:forEach items="${stayList}" var="dic">
                	<option value="${dic.value}">${dic.name}</option>
                </c:forEach>
              </select>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="space-2"></div>
    
    
    <div class="row">
      <div class="col-xs-6">
        <div class="form-group">
          <label class="control-label col-xs-12 col-sm-5 no-padding-right" for="stuInfo_migrant">农民工子女:</label>
         <div class="col-xs-12 col-sm-9">
           <label style="margin-right: 20px">
		    <input type="radio" name="stuInfo.migrant" id="student_migrant1" value="${radioYesVal}" disabled>
		    <span>${radioYesName}<span>
		  </label>
		  
		  <label>
		    <input type="radio" name="stuInfo.migrant" id="student_migrant2" value="${radioNotVal}" disabled>
		    <span>${radioNotName}<span>
		  </label>
			  
          </div>
        </div>
      </div>
      <div class="col-xs-6">
        <div class="form-group">
          <label class="control-label col-xs-12 col-sm-5 no-padding-right" for="stuInfo_soldier">军人子女:</label>
          <div class="col-xs-12 col-sm-9">
            <label style="margin-right: 20px">
			    <input type="radio" name="stuInfo.soldier" id="student_soldier1"  value="${radioYesVal}" disabled  >
			     <span>${radioYesName}<span>
			  </label>
			  
			  <label>
			    <input type="radio" name="stuInfo.soldier" id="student_soldier2" value="${radioNotVal}" disabled >
			   <span>${radioNotName}<span>
			  </label>
          </div>
        </div>
      </div>
    </div>
    <div class="space-2"></div>
    
    
    
    <div class="row">
      <div class="col-xs-6">
        <div class="form-group">
          <label class="control-label col-xs-12 col-sm-5 no-padding-right" for="stuInfo_teacher">教师子女:</label>
         <div class="col-xs-12 col-sm-9">
           <label style="margin-right: 20px">
			    <input type="radio" name="stuInfo.teacher" id="student_teacher1" value="${radioYesVal}" disabled >
			      <span>${radioYesName}<span>
			  </label>
			  
			  <label>
			    <input type="radio" name="stuInfo.teacher" id="student_teacher2" value="${radioNotVal}" disabled >
			    <span>${radioNotName}<span>
			  </label>
          </div>
        </div>
      </div>
      <div class="col-xs-6">
        <div class="form-group">
          <label class="control-label col-xs-12 col-sm-5 no-padding-right" for="stuInfo_singleParent">单亲家庭:</label>
          <div class="col-xs-12 col-sm-9">
            <label style="margin-right: 20px">
			    <input type="radio" name="stuInfo.singleParent" id="student_singleParent1" value="${radioYesVal}" disabled  >
			    <span>${radioYesName}<span>
			  </label>
			  
			  <label>
			    <input type="radio" name="stuInfo.singleParent" id="student_singleParent2" value="${radioNotVal}" disabled >
			     <span>${radioNotName}<span>
			  </label>
          </div>
        </div>
      </div>
    </div>
    <div class="space-2"></div>
    
    
      <div class="row">
      <div class="col-xs-6">
        <div class="form-group">
          <label class="control-label col-xs-12 col-sm-5 no-padding-right" for="stuInfo_onlySon">独生子女:</label>
         <div class="col-xs-12 col-sm-9">
           <label style="margin-right: 20px">
			    <input type="radio" name="stuInfo.onlySon" id="student_onlySon1" value="${radioYesVal}" disabled>
			    <span>${radioYesName}<span>
			  </label>
			  
			  <label>
			    <input type="radio" name="stuInfo.onlySon" id="student_onlySon2" value="${radioNotVal}" disabled >
			     <span>${radioNotName}<span>
			  </label>
          </div>
        </div>
      </div>
      <div class="col-xs-6">
        <div class="form-group">
          <label class="control-label col-xs-12 col-sm-5 no-padding-right" for="stuInfo_poor">贫困生:</label>
          <div class="col-xs-12 col-sm-9">
            <label style="margin-right: 20px">
			    <input type="radio" name="stuInfo.poor" id="student_poor1" value="${radioYesVal}" disabled>
			     <span>${radioYesName}<span>
			  </label>
			  
			  <label>
			    <input type="radio" name="stuInfo.poor" id="student_poor2" value="${radioNotVal}" disabled >
			    <span>${radioNotName}<span>
			  </label>
          </div>
        </div>
      </div>
    </div>
    <div class="space-2"></div>
        <div id="account_info">
    <div class="cut-off-rule">账户信息</div>
    <div class="form-group">
      <label class="control-label col-xs-12 col-sm-5 no-padding-right" for="student_account">账号:</label>
      <div class="col-xs-12 col-sm-7">
        <input type="hidden" name="user.id"  id="user.id"  />
        <div class="clearfix">
          <input type="text" name="user.account"  id="student_account" class="col-xs-12 col-sm-12" readonly="readonly"/>
        </div>
      </div>
    </div>
    <div class="space-2"></div>

    <div class="form-group">
      <label class="control-label col-xs-12 col-sm-5 no-padding-right" for="student_telephone">绑定手机:</label>
   	  <div class="col-xs-12 col-sm-7">
        <div class="clearfix">
          <input type="text" name="user.telephone"  id="student_telephone" class="col-xs-12 col-sm-12" readonly="readonly"  />
        </div>
      </div>
    </div>
    <div class="space-2"></div>

    <div class="form-group">
      <label class="control-label col-xs-12 col-sm-5 no-padding-right" for="student_email">绑定邮箱:</label><span>(用于系统登录和密码找回)</span>
      <div class="col-xs-12 col-sm-7">
        <div class="clearfix">
          <input type="text" name="user.email"  id="student_email" class="col-xs-12 col-sm-12" readonly="readonly"  />
        </div>
      </div>
    </div>
    <div class="space-2"></div>

    <div class="form-group">
      <label class="control-label col-xs-12 col-sm-5 no-padding-right" for="student_password">密码:</label><span>(初始密码为：888888)</span>
      <div class="col-xs-12 col-sm-7">
        <div class="clearfix">
          <input type="password" name="user.password"  id="student_password" value="" class="col-xs-12 col-sm-12"  readonly="readonly"  />
        </div>
      </div>
    </div>
    <div class="space-2"></div>
    
    <div class="form-group">
      <label class="control-label col-xs-12 col-sm-5 no-padding-right" for="dept_telphone">所属角色:</label>
       <label class="control-label col-xs-12 col-sm-5 no-padding-left" for="dept_telphone"> 学生</label>       	
    </div>
    <div class="space-2"></div>
   </div>
  </form>
</div>
<script type="text/javascript" src="js/student-modify.js"></script>