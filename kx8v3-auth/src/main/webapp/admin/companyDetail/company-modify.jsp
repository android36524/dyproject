<%@page import="com.dayang.commons.enums.OrgLevelEnum"%>
<%@page import="org.apache.commons.lang.StringUtils"%>
<%@page import="com.dayang.commons.util.StaticData" %>
<%@page import="com.dayang.commons.util.AccountGenerator" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<%
  String companyId = StringUtils.defaultIfEmpty(request.getParameter("id"), "");
  String flageId = StringUtils.defaultIfEmpty(request.getParameter("flageId"), "");
  String roleName = StaticData.roleType.companyAdminName;
  long account = AccountGenerator.getAccount();
  String companyLevel = OrgLevelEnum.COMPANY.getValueStr();
%>
<script type="text/javascript">
    var companyId = "<%=companyId%>";
    var flageId = "<%=flageId%>";
    var roleName = "<%=roleName%>";
    var account = "<%=account%>";
    var _companyLevel = "<%=companyLevel%>";
    
    $("#account_account").val(account);
    $("#roleName").val(roleName);
</script>  

<style type="text/css">
.dy-dialog-form .control-label {
    width: 120px;
}
</style>       
<div>
  <form class="form-horizontal dy-dialog-form" id="company-form" method="post" >
    
    <div class="form-group" id="accoutDiv">
	      <label class="control-label col-xs-12 col-sm-5 no-padding-right" for="account_account">账号:</label>
	        <div class="col-xs-12 col-sm-7">
	          <div class="clearfix">
	            <input type="text" name="account.account" id="account_account"  class="col-xs-12 col-sm-12"  />
	          </div>
	        </div>  
      </div> 
      <div class="form-group" id="roleDiv">
	      	<label class="control-label col-xs-12 col-sm-5 no-padding-right" for="roleName">所属角色:</label>
	        <div class="col-xs-12 col-sm-7">
	          <div class="clearfix">
	            <input type="text" name="roleName" id="roleName"  class="col-xs-12 col-sm-12" readonly="readonly"/>
	            <input type="hidden" name="roleId" id="roleId" value="1" class="col-xs-12 col-sm-12"/>
	          </div>
	        </div> 
      </div>
    
      <div class="form-group">
        <label class="control-label col-xs-12 col-sm-3 no-padding-right my-mustlabel" for="company_name">公司名称:</label>
        <div class="col-xs-12 col-sm-9">
          <div class="clearfix">
            <input type="hidden" name="company.id" id="company_id"/>
            <input type="text" name="company.orgName" id="company_orgName" class="col-xs-12 col-sm-12" maxlength="50" onkeyup="value=value.replace(/[^\w\-\u4E00-\u9FA5]/g,'')" onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^\w\-\u4E00-\u9FA5]/g,''))"/>
          </div>
        </div>  
      </div>

      <div class="space-2"></div>
      <div class="form-group">
        <label class="control-label col-xs-12 col-sm-3 no-padding-right my-mustlabel" for="company_orgCode">组织类别:</label>
        <div class="col-xs-12 col-sm-9">
          <div class="clearfix">
            <input type="text"  id="company_flag" class="col-xs-12 col-sm-12" value="公司" readonly="readonly"/>
            <input type="hidden" name="company.orgFlag" id="company_orgFlag" class="col-xs-12 col-sm-12" value="2"/>
          </div>
        </div>
      </div>
    
      <div class="space-2"></div>
      <div class="form-group">
        <label class="control-label col-xs-12 col-sm-3 no-padding-right my-mustlabel" for="company_provinceCode">所属行政区划:</label>
        <div class="col-xs-12 col-sm-9">
          <div class="clearfix">
          	<!-- <select class="col-xs-3 col-sm-3 area" id="" name="">
              <option value="">全国</option>
            </select> -->
            <select class="col-xs-3 col-sm-3 area" id="company_provinceCode" name="company.provinceCode">
              <option value="">请选择</option>
            </select>
            <select class="col-xs-3 col-sm-3 area" id="company_cityCode" name="company.cityCode">
              <option value="">请选择</option>
            </select>
            <select class="col-xs-3 col-sm-3 area" id="company_areaCode" name="company.areaCode">
              <option value="">请选择</option>
            </select>
          </div>
        </div>
      </div>
      
      <div class="space-2"></div>
      <div class="form-group" style="display: none">
        <label class="control-label col-xs-12 col-sm-3 no-padding-right my-mustlabel" for="company_orgId">上级公司名称:</label>
          <div class="col-xs-12 col-sm-9">
              <div class="clearfix">
                  <div class="input-group">
						<span>
							<input type="text" name="parentOrgName" id="parentOrgName" class="col-xs-12 col-sm-10 spinner-input" disabled="disabled"/>
							<input type="hidden" name="company.orgId" id="company_orgId"/>
						</span>
						<span class="input-group-btn">
							<button class="btn btn-sm btn-primary" disabled="disabled" type="button" id="btn_select_manager">
                                <i class="icon-edit bigger-110"></i>
                                	选择上级机构部门
                            </button>
						</span>
                  </div>
              </div>
          </div>
      </div>
    
      <div class="space-2"></div>
      <div class="form-group">
        <label class="control-label col-xs-12 col-sm-3 no-padding-right" for="company_charge">负责人:</label>
        <div class="col-xs-12 col-sm-9">
          <div class="clearfix">
            <input type="text" name="company.charge" id="company_charge" class="col-xs-12 col-sm-12" />
          </div>
        </div>
      </div>
    
      <div class="space-2"></div>
      <div class="form-group">
        <label class="control-label col-xs-12 col-sm-3 no-padding-right" for="company_chargePhone">负责人电话:</label>
        <div class="col-xs-12 col-sm-9">
          <div class="clearfix">
            <input type="text" name="company.chargePhone" id="company_chargePhone" class="col-xs-12 col-sm-12" />
          </div>
        </div>
      </div>
    
      <div class="space-2"></div>
      <div class="form-group">
        <label class="control-label col-xs-12 col-sm-3 no-padding-right" for="company_officeTel">办公电话:</label>
        <div class="col-xs-12 col-sm-9">
          <div class="clearfix">
            <input type="text" name="company.officeTel" id="company_officeTel" class="col-xs-12 col-sm-12" />
          </div>
        </div>
      </div>
    
      <div class="space-2"></div>
      <div class="form-group">
        <label class="control-label col-xs-12 col-sm-3 no-padding-right" for="company_fax">传真:</label>
        <div class="col-xs-12 col-sm-9">
          <div class="clearfix">
            <input type="text" name="company.fax" id="company_fax" class="col-xs-12 col-sm-12" />
          </div>
        </div>
      </div>
      
      <div class="space-2"></div>
      <div class="form-group" style="display: none">
        <label class="control-label col-xs-12 col-sm-3 no-padding-right" for="company_level">层级:</label>
        <div class="col-xs-12 col-sm-9">
          <div class="clearfix">
            <input type="text" name="company.level" id="company_level" class="col-xs-12 col-sm-12" />
          </div>
        </div>
      </div>
  </form>
  
</div>
<script type="text/javascript" src="js/company-modify.js"></script>
<script type="text/javascript" src="../js/commonDiyCss.js"></script>