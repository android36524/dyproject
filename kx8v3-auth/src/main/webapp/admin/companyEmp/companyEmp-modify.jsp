<%@page import="org.apache.commons.lang.StringUtils"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@include file="/include-path.jsp"%>
<%
  String empeId = StringUtils.defaultIfEmpty(request.getParameter("id"), "");
  String flageId = StringUtils.defaultIfEmpty(request.getParameter("flageId"), "");
  String selectOrgId = StringUtils.defaultIfEmpty(request.getParameter("selectOrgId"), "");
%>
<script type="text/javascript">
    var empeId = "<%=empeId%>";
    var flageId = <%=flageId%>;
</script>         
<div>
  <form class="form-horizontal dy-dialog-form" id="companyEmp-form" method="post" >
    
      <div class="form-group">
        <label class="control-label col-xs-12 col-sm-3 no-padding-right my-mustlabel" for="companyEmp_name">姓名:</label>
        <div class="col-xs-12 col-sm-9">
          <div class="clearfix">
            <input type="hidden" name="companyEmp.id" id="companyEmp_id"/>
            <input type="text" name="companyEmp.name" id="companyEmp_name" class="col-xs-12 col-sm-12" maxlength="50" onkeyup="value=value.replace(/[^a-zA-Z0-9\u4E00-\u9FA5]/g,'')"/>
          </div>
        </div>  
      </div>

      <div class="space-2"></div>
      <div class="form-group">
        <label class="control-label col-xs-12 col-sm-3 no-padding-right my-mustlabel" for="companyEmp_sex">性别:</label>
        <div class="col-xs-12 col-sm-9">
          <div class="clearfix">
            <label style="margin-right: 20px">
			    <input type="radio" name="companyEmp.sex" id="companyEmp_sex0"  value="${manVal}"  checked >
			     <span>${manName}</span>
			  </label>
			  
			  <label>
			    <input type="radio" name="companyEmp.sex" id="companyEmp_sex1" value="${woManVal}" >
			   <span>${woManName}</span>
			  </label>
          </div>
        </div>
      </div>
    
      <div class="form-group">
        <label class="control-label col-xs-12 col-sm-3 no-padding-right my-mustlabel" for="companyEmp_empNo">工号:</label>
        <div class="col-xs-12 col-sm-9">
          <div class="clearfix">
            <input type="text" name="companyEmp.empNo" id="companyEmp_empNo" class="col-xs-12 col-sm-12"  maxlength="20"/>
          </div>
        </div>  
      </div>
      
      <div class="space-2"></div>
      <div class="form-group">
        <label class="control-label col-xs-12 col-sm-3 no-padding-right my-mustlabel" for="companyEmp_orgId">公司名称:</label>
          <div class="col-xs-12 col-sm-9">
              <div class="clearfix">
							<input type="hidden" name="companyEmp.orgId" id="companyEmp_orgId" value="<%=selectOrgId %>" class="col-xs-12 col-sm-12" readonly="readonly"/>
							<input type="text" name="companyName" id="companyName"  value="${name}" class="col-xs-12 col-sm-12" readonly="readonly"/>
              </div>
          </div>
      </div>
      
      <div class="space-2"></div>
      <div class="form-group">
        <label class="control-label col-xs-12 col-sm-3 no-padding-right my-mustlabel" for="companyEmp_deptId">部门名称:</label>
          <div class="col-xs-12 col-sm-9">
              <div class="clearfix">
                  <div class="input-group">
						<span>
							<input type="text" name="deptName" id="deptName"  readonly="readonly" />
							<input type="hidden" name="companyEmp.deptId" id="companyEmp_deptId"/>
							<button class="btn btn-sm btn-primary" disabled="disabled" type="button" id="btn_select_dept">
                                <i class="icon-search icon-on-right bigger-110"></i>
                                	选择部门
                            </button>
						</span>
                  </div>
              </div>
          </div>
      </div>
      
      <div class="space-2"></div>
      <div class="form-group">
      	<label class="control-label col-xs-12 col-sm-3 no-padding-right my-mustlabel" for="companyEmp_empTypeFlag">员工类型:</label>
      		<div class="col-xs-12 col-sm-9">
	      		<div class="clearfix">
	                <select class="col-xs-12 col-sm-12" id="companyEmp_empType" name="companyEmp.empType">
	                	<option value="3">员工</option>
	                	<option value="5">代理商</option>
	                </select>
	              </div>
              </div>
    	</div>
    
      <div class="space-2"></div>
      <div class="form-group">
        <label class="control-label col-xs-12 col-sm-3 no-padding-right" for="companyEmp_mobile">手机号码:</label>
        <div class="col-xs-12 col-sm-9">
          <div class="clearfix">
            <input type="text" name="companyEmp.mobile" id="companyEmp_mobile" class="col-xs-12 col-sm-12" />
          </div>
        </div>
      </div>
    
      <div class="space-2"></div>
      <div class="form-group">
        <label class="control-label col-xs-12 col-sm-3 no-padding-right" for="companyEmp_job">职务:</label>
        <div class="col-xs-12 col-sm-9">
          <div class="clearfix">
            <input type="text" name="companyEmp.job" id="companyEmp_job" class="col-xs-12 col-sm-12" />
          </div>
        </div>
      </div>
    
      <div class="space-2"></div>
      <div class="form-group">
        <label class="control-label col-xs-12 col-sm-3 no-padding-right" for="companyEmp_nameSpell">姓名简拼:</label>
        <div class="col-xs-12 col-sm-9">
          <div class="clearfix">
            <input type="text" name="companyEmp.nameSpell" id="companyEmp_nameSpell" class="col-xs-12 col-sm-12" readonly="readonly"/>
          </div>
        </div>
      </div>
    
      
  </form>
  
</div>
<script type="text/javascript" src="js/companyEmp-modify.js"></script>
<script type="text/javascript" src="../js/commonDiyCss.js"></script>