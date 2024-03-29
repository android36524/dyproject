<%@page import="org.apache.commons.lang.StringUtils"%>
<%@ page import="com.dayang.commons.util.DaYangCommonUtil" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@include file="/include-path.jsp"%>
<%-- 教育局员工添加&修改 --%>
<%
	String empeId = StringUtils.defaultIfEmpty(request.getParameter("id"), "");
  String _img = DaYangCommonUtil.getAppProperties("ftp.access.base");
  String orgId = StringUtils.defaultIfEmpty(request.getParameter("orgId"), "");
%>
<script type="text/javascript">
  var _imgpath = "<%=_img%>";
  var _orgId  ="<%=orgId%>"
</script>
<div>

  <form class="form-horizontal dy-dialog-form" id="empe_form" method="post">
    <div class="row">
      <div class="col-xs-6">
        <%--员工姓名--%>
        <div class="form-group">
          <label class="control-label col-xs-12 col-sm-5 no-padding-right" for="empe_name">员工姓名:</label>
          <div class="col-xs-12 col-sm-9">
            <div class="clearfix">
              <input type="hidden" name="empe.id" id="empe_id" value="<%=empeId%>"/>
              <input type="hidden" name="empe.orgId" id="orgId" value="<%= orgId%>"/>
              <input type="text" name="empe.name" id="empe_name" class="col-xs-12 col-sm-12" disabled="disabled"/>
            </div>
          </div>
        </div>

        <div class="form-group">
          <label class="control-label col-xs-12 col-sm-5 no-padding-right" for="empe_sex">性别:</label>
          <div class="col-xs-7 input-group">
			   <label style="margin-right: 20px">
			    <input type="radio" name="empe.sex" id="empe_sex0"  value="${manVal}"  checked disabled="disabled">
			     <span>${manName}<span>
			  </label>
			  
			  <label>
			    <input type="radio" name="empe.sex" id="empe_sex1" value="${woManVal}" disabled="disabled">
			   <span>${woManName}<span>
			  </label>
          </div>
        </div>

        <div class="form-group">
          <label class="control-label col-xs-5 no-padding-right" for="empe_birthDate">出生日期:</label>
          <div class="col-xs-7 input-group">

            <span class="input-group-addon">
                <i class="icon-calendar bigger-110"></i>
            </span><input class="form-control" type="text" name="empe.birthDate" id="empe_birthDate" disabled="disabled"/>
          </div>
        </div>

        <div class="form-group">
          <label class="control-label col-xs-12 col-sm-5 no-padding-right" for="empe_nation">民族:</label>
          <div class="col-xs-12 col-sm-9">
            <div class="clearfix">
              <select class="col-xs-12 col-sm-12" id="empe_nation" dyId="chooseId2" name="empe.nation" disabled="disabled">
                <c:forEach var="dic" items="${nationList}">
                  <option value="${dic.value}">${dic.name}</option>
                </c:forEach>
              </select>
            </div>
          </div>
        </div>


      </div>
      <div class="col-xs-4">
        <div class="form-group">
          <label class="control-label col-xs-5 no-padding-right" for="empe_empNo">工号:</label>
          <div class="col-xs-7">
            <div class="clearfix">
              <input type="text" name="empe.empNo" id="empe_empNo" class="col-xs-12"  disabled="disabled"/>
            </div>
          </div>
        </div>

        <div class="form-group">
          <label class="control-label col-xs-5 no-padding-right" for="empe_nameSpell">姓名拼音简拼:</label>
          <div class="col-xs-7 input-group">
            <div class="clearfix">
              <input type="text" name="empe.nameSpell" id="empe_nameSpell" class="col-xs-12 spinner-input" disabled="disabled"/>
            </div>
          </div>
        </div>

        <div class="form-group">
          <label class="control-label col-xs-5 no-padding-right" for="empe_hometown">籍贯:</label>
          <div class="col-xs-7">
            <div class="clearfix">
              <input type="text" name="empe.hometown" id="empe_hometown" class="col-xs-12 spinner-input" disabled="disabled"/>
            </div>
          </div>
        </div>

        <div class="form-group">
          <label class="control-label col-xs-5 no-padding-right" for="empe_maritalStatus">婚姻状况:</label>
          <div class="col-xs-7">
            <div class="clearfix">
              <select class="col-xs-12" id="empe_maritalStatus" name="empe.maritalStatus" disabled="disabled">
                <c:forEach var="dic" items="${marryLists}">
                  <option value="${dic.value}">${dic.name}</option>
                </c:forEach>
              </select>
            </div>
          </div>

        </div>

      </div>

      <div class="col-xs-2">
        <div class="row" style="height: 140px;">
          <img style="height: 138px;width: 120px;border:1px solid #ccc;" id="imageUrl" src="<%=path%>/css/img/defaultpic.gif"/>
        </div>
        <div class="row">
          <div class="col-xs-12" style="padding-left: 0px;">
            <input type="hidden" name="empe.image" id="empe_image" />
            <div class="input-group-btn">
              <!-- <div id="imageUpload" >
              </div>-->
            </div>
          </div>
        </div>
      </div>
    </div>

    <div class="space-2"></div>


    <div class="row">
      <div class="col-xs-6">
        <div class="form-group">
          <label class="control-label col-xs-12 col-sm-5 no-padding-right" for="empe_political">政治面貌:</label>
          <div class="col-xs-12 col-sm-9">
            <div class="clearfix">
              <select class="col-xs-12 col-sm-12" id="empe_political" dyId="chooseId2" name="empe.political" disabled="disabled">
                <c:forEach var="dic" items="${politicalList}">
                  <option value="${dic.value}">${dic.name}</option>
                </c:forEach>
              </select>
            </div>
          </div>
        </div>
      </div>

      <div class="col-xs-6">
        <div class="form-group">
          <label class="control-label col-xs-12 col-sm-5 no-padding-right" for="empe_health">健康状况:</label>
          <div class="col-xs-12 col-sm-9">
            <div class="clearfix">
              <select class="col-xs-12 col-sm-12" id="empe_health" dyId="chooseId2" name="tea.health" disabled="disabled">
                <c:forEach var="dic" items="${healthList}">
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
          <label class="control-label col-xs-12 col-sm-5 no-padding-right" for="empe_education">学历:</label>
          <div class="col-xs-12 col-sm-9">
            <div class="clearfix">
              <select class="col-xs-12 col-sm-12" id="empe_education" dyId="chooseId2" name="empe.education" disabled="disabled">
                <c:forEach var="dic" items="${educationList}">
                  <option value="${dic.value}">${dic.name}</option>
                </c:forEach>
              </select>
            </div>
          </div>
        </div>
      </div>
      <div class="col-xs-6">
        <div class="form-group">
          <label class="control-label col-xs-12 col-sm-5 no-padding-right" for="empe_idCard">身份证号码:</label>
          <div class="col-xs-12 col-sm-9">
            <div class="clearfix">
              <input type="text" name="empe.idCard" id="empe_idCard" class="col-xs-12 col-sm-12 spinner-input" disabled="disabled" />
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="space-2"></div>


    <div class="row">
      <div class="col-xs-6">
        <div class="form-group">
          <label class="control-label col-xs-12 col-sm-5 no-padding-right" for="empe_jobsCareers">岗位职业:</label>
          <div class="col-xs-12 col-sm-9">
            <div class="clearfix">
              <select class="col-xs-12 col-sm-12" id="empe_empType" dyId="chooseId2" name="empe.empType" disabled="disabled">
                <c:forEach var="dic" items="${empTypeList}">
                  <option value="${dic.value}">${dic.name}</option>
                </c:forEach>
              </select>
            </div>
          </div>
        </div>
      </div>
      <div class="col-xs-6">
        <div class="form-group">
          <label class="control-label col-xs-12 col-sm-5 no-padding-right" for="empe_toSchYear">来校年月:</label>
          <div class="col-xs-12 col-sm-7 input-group">
            <span class="input-group-addon">
                <i class="icon-calendar bigger-110"></i>
            </span><input class="form-control" type="text" name="tea.toSchYear" id="empe_toSchYear" disabled="disabled"/>
          </div>
        </div>
      </div>
    </div>
    <div class="space-2"></div>



    <div class="row">
      <div class="col-xs-6">
        <div class="form-group">
          <label class="control-label col-xs-12 col-sm-5 no-padding-right" for="empe_deptName">所在部门:</label>
          <div class="col-xs-12 col-sm-9">
            <div class="input-group">
                <span>
                    <div class="clearfix">
                      <input type="text" name="deptName" id="empe_deptName"  class="col-xs-12 col-sm-10 spinner-input" disabled="disabled"/>
                    </div>
                    <input type="hidden" name="empe.deptId" id="empe_deptId"/>
                </span>
            </div>
          </div>
        </div>
      </div>
      <div class="col-xs-6">
        <div class="form-group">
          <label class="control-label col-xs-12 col-sm-5 no-padding-right" for="empe_job">职务:</label>
          <div class="col-xs-12 col-sm-9">
            <div class="clearfix">
              <input type="text" name="empe.job" id="empe_job" class="col-xs-12 col-sm-12 spinner-input" disabled="disabled"/>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="space-2"></div>





    <div class="row">
      <div class="col-xs-6">
        <div class="form-group">
          <label class="control-label col-xs-12 col-sm-5 no-padding-right" for="empe_workAge">参加工作年月:</label>
          <div class="col-xs-12 col-sm-7 input-group">
            <span class="input-group-addon">
                <i class="icon-calendar bigger-110"></i>
            </span><input class="form-control" type="text" name="tea.workAge" id="empe_workAge" disabled="disabled"/>
          </div>
        </div>
      </div>
      <div class="col-xs-6">
        <div class="form-group">
          <label class="control-label col-xs-12 col-sm-5 no-padding-right" for="empe_teacherWorkAge">从教年月:</label>
          <div class="col-xs-12 col-sm-7 input-group">
               <span class="input-group-addon">
                <i class="icon-calendar bigger-110"></i>
            </span><input class="form-control" type="text" name="tea.teacherWorkAge" id="empe_teacherWorkAge" disabled="disabled"/>
          </div>
        </div>
      </div>
    </div>
    <div class="space-2"></div>

    <div class="row">
      <div class="col-xs-6">
        <div class="form-group">
          <label class="control-label col-xs-12 col-sm-5 no-padding-right" for="empe_jobName">职称:</label>
          <div class="col-xs-12 col-sm-9">
            <div class="clearfix">
              <input type="text" name="tea.jobName" id="empe_jobName" class="col-xs-12 col-sm-12 spinner-input" disabled="disabled"/>
            </div>
          </div>
        </div>
      </div>
      <div class="col-xs-6">
        <div class="form-group">
          <label class="control-label col-xs-12 col-sm-5 no-padding-right" for="empe_isWork">教师当前状态:</label>
          <div class="col-xs-12 col-sm-7 input-group">
            <div class="clearfix">
             <input type="text" name="showName" id="showName" value="${name}" class="col-xs-12 col-sm-12 spinner-input" disabled="disabled" />
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="space-2"></div>



    <div class="row">
      <div class="col-xs-6">
        <div class="form-group">
          <label class="control-label col-xs-12 col-sm-5 no-padding-right" for="empe_mobile">手机号码:</label>
          <div class="col-xs-12 col-sm-9">
            <div class="clearfix">
              <input type="text" name="empe.mobile" id="empe_mobile" class="col-xs-12 col-sm-12 spinner-input" disabled="disabled"/>
            </div>
          </div>
        </div>
      </div>
      <div class="col-xs-6">
        <div class="form-group">
          <label class="control-label col-xs-12 col-sm-5 no-padding-right" for="empe_telphone">办公电话:</label>
          <div class="col-xs-12 col-sm-9">
            <div class="clearfix">
              <input type="text" name="empe.telphone" id="empe_telphone" class="col-xs-12 col-sm-12 spinner-input" disabled="disabled"/>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="space-2"></div>

    <div class="row">
      <div class="col-xs-6">
        <div class="form-group">
          <label class="control-label col-xs-12 col-sm-5 no-padding-right" for="empe_qq">qq:</label>
          <div class="col-xs-12 col-sm-9">
            <div class="clearfix">
              <input type="text" name="empe.qq" id="empe_qq" class="col-xs-12 col-sm-12 spinner-input" disabled="disabled"/>
            </div>
          </div>
        </div>
      </div>
      <div class="col-xs-6">
        <div class="form-group">
          <label class="control-label col-xs-12 col-sm-5 no-padding-right" for="empe_email">email:</label>
          <div class="col-xs-12 col-sm-9">
            <div class="clearfix">
              <input type="text" name="empe.email" id="empe_email" class="col-xs-12 col-sm-12 spinner-input" disabled="disabled"/>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="space-2"></div>

    <div class="row">
      <div class="col-xs-12">
        <div class="form-group">
          <label class="control-label col-xs-12 col-sm-5 no-padding-right" for="empe_zipCode">邮政编码:</label>
          <div class="col-xs-12 col-sm-10">
            <div class="clearfix">
              <input type="text" name="empe.zipCode" id="empe_zipCode" class="col-xs-12 col-sm-12 spinner-input" disabled="disabled"/>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="space-2"></div>

    <div class="row">
      <div class="col-xs-12">
        <div class="form-group">
          <label class="control-label col-xs-12 col-sm-5 no-padding-right" for="empe_homeAddr">家庭住址:</label>
          <div class="col-xs-12 col-sm-10">
            <div class="clearfix">
              <input type="text" name="empe.homeAddr" id="empe_homeAddr" class="col-xs-12 col-sm-12 spinner-input" disabled="disabled"/>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="space-2"></div>

    <div class="row">
      <div class="col-xs-12">
        <div class="form-group">
          <label class="control-label col-xs-12 col-sm-5 no-padding-right" for="empe_addr">现住址:</label>
          <div class="col-xs-12 col-sm-10">
            <div class="clearfix">
              <input type="text" name="empe.addr" id="empe_addr" class="col-xs-10 col-sm-12 spinner-input" disabled="disabled"/>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="space-2"></div>

    <div class="row">
      <div class="col-xs-12">
        <div class="form-group">
          <label class="control-label col-xs-12 col-sm-5 no-padding-right" for="empe_societyNexus">社会关系:</label>
          <div class="col-xs-12 col-sm-10">
            <div class="clearfix">
              <input type="text" name="tea.societyNexus" id="empe_societyNexus" class="col-xs-10 col-sm-12 spinner-input" disabled="disabled"/>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="space-2"></div>


    <div class="row">
      <div class="col-xs-12">
        <div class="form-group">
          <label class="control-label col-xs-12 col-sm-5 no-padding-right" for="empe_workExperience">工作经历:</label>
          <div class="col-xs-12 col-sm-10">
            <div class="clearfix">
              <input type="text" name="tea.workExperience" id="empe_workExperience" class="col-xs-10 col-sm-12 spinner-input" disabled="disabled"/>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="space-2"></div>

    <div class="row">
      <div class="col-xs-12">
        <div class="form-group">
          <label class="control-label col-xs-12 col-sm-5 no-padding-right" for="empe_studyExperrience">学习经历:</label>
          <div class="col-xs-12 col-sm-10">
            <div class="clearfix">
              <input type="text" name="tea.studyExperrience" id="empe_studyExperrience" class="col-xs-10 col-sm-12 spinner-input" disabled="disabled"/>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="space-2"></div>

    <div class="row">
      <div class="col-xs-12">
        <div class="form-group">
          <label class="control-label col-xs-12 col-sm-5 no-padding-right" for="empe_remark">备注:</label>
          <div class="col-xs-12 col-sm-10">
            <div class="clearfix">
              <textarea class="col-xs-12 col-sm-12" name="tea.remark" id="empe_remark" disabled="disabled"></textarea>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="space-2"></div>
  </form>
</div>
<script type="text/javascript" src="js/empe-modify.js"></script>