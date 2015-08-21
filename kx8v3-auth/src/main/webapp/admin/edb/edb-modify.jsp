<%@page import="org.apache.commons.lang.StringUtils"%>
<%@ page import="com.dayang.commons.enums.OrgLevelEnum" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%-- 菜单添加&修改 --%>
<%
    String edbId = StringUtils.defaultIfEmpty(request.getParameter("id"), "");
    String nationalLevel = OrgLevelEnum.NATIONAL.getValueStr();
    String provinceLevel = OrgLevelEnum.PROVINCE.getValueStr();
    String cityLevel = OrgLevelEnum.CITY.getValueStr();
    String arealLevel = OrgLevelEnum.AREA.getValueStr();
%>
<script type="text/javascript">
    var _edbId = "<%=edbId%>";
    var _nationalLevel = "<%=nationalLevel%>";
    var _provinceLevel = "<%=provinceLevel%>";
    var _cityLevel = "<%=cityLevel%>";
    var _arealLevel = "<%=arealLevel%>";
</script>
<div>
  <form class="form-horizontal dy-dialog-form" id="edb-form" method="post" >
    
      <div class="form-group" id="accoutDiv">
      <label class="control-label col-xs-12 col-sm-5 no-padding-right" for="account">账号:</label>
        <div class="col-xs-12 col-sm-7">
          <div class="clearfix">
            <input type="text" name="account" id="account" value="${account }" class="col-xs-12 col-sm-12"  />
          </div>
        </div>  
      </div> 
      <div class="form-group" id="roleDiv">
      	<label class="control-label col-xs-12 col-sm-5 no-padding-right" for="edb_orgName">所属角色:</label>
        <div class="col-xs-12 col-sm-7">
          <div class="clearfix">
            <input type="text" name="roleName" id="roleName" value="${roleName}"  class="col-xs-12 col-sm-12" readonly="readonly"/>
            <input type="hidden" name="roleId" id="roleId" value="1" class="col-xs-12 col-sm-12"/>
          </div>
        </div> 
      </div>
      <div class="form-group">
        <label class="control-label col-xs-12 col-sm-5 no-padding-right" for="edb_orgName"><span style="color: red"> *&nbsp;&nbsp;</span>教育局名称:</label>
        <div class="col-xs-12 col-sm-7">
          <div class="clearfix">
            <input type="hidden" name="edb.id" id="edb_id"/>
            <input type="text" name="edb.orgName" id="edb_orgName" class="col-xs-12 col-sm-12" />
            <input type="hidden" id="idid" />
          </div>
        </div>  
      </div>

      <div class="space-2"></div>
      <div class="form-group">
        <label class="control-label col-xs-12 col-sm-5 no-padding-right" for="edb_orgCode">教育局编号:</label>
        <div class="col-xs-12 col-sm-7">
          <div class="clearfix">
            <input type="text" name="edb.orgCode" id="edb_orgCode" class="col-xs-12 col-sm-12" disabled="disabled"/>
          </div>
        </div>
      </div>
    
      <div class="space-2"></div>
      <div class="form-group">
        <label class="control-label col-xs-12 col-sm-5 no-padding-right" for="edb_provinceCode"><span style="color: red"> *&nbsp;&nbsp;</span>省:</label>
        <div class="col-xs-12 col-sm-7">
          <div class="clearfix">
          <input type="hidden" id="edb_provinceCode_hidden" name="edb.provinceCode"/>
            <select class="col-xs-12 col-sm-12 area" id="edb_provinceCode" name="edb_provinceCode">
              <option value="">--请选择--</option>
            </select>
          </div>
        </div>
      </div>
    
      <div class="space-2"></div>
      <div class="form-group">
        <label class="control-label col-xs-12 col-sm-5 no-padding-right" for="edb_cityCode">市:</label>
        <div class="col-xs-12 col-sm-7">
          <div class="clearfix">
          <input type="hidden" id="edb_cityCode_hidden" name="edb.cityCode"/>
            <select class="col-xs-12 col-sm-12 area" id="edb_cityCode">
              <option value="">--请选择--</option>
            </select>
          </div>
        </div>
      </div>
    
      <div class="space-2"></div>
      <div class="form-group">
        <label class="control-label col-xs-12 col-sm-5 no-padding-right" for="edb_areaCode">区:</label>
        <div class="col-xs-12 col-sm-7">
          <div class="clearfix">
          <input type="hidden" id="edb_areaCode_hidden" name="edb.areaCode"/>
            <select class="col-xs-12 col-sm-12 area" id="edb_areaCode">
              <option value="">--请选择--</option>
            </select>
            <input Type="hidden" id="areaCodeId">
          </div>
        </div>
      </div>
    
      <div class="space-2"></div>
      <div class="form-group">
        <label class="control-label col-xs-12 col-sm-5 no-padding-right" for="edb_orgId">上级主管机构:</label>
          <div class="col-xs-12 col-sm-7">
              <div class="clearfix">
                  <div class="input-group">
						<span>
							<input type="text" name="parentOrgName" id="parentOrgName" class="col-xs-12 col-sm-10 spinner-input" disabled="disabled"/>
							<input type="hidden" name="edb.orgId" id="edb_orgId"/>
							<input type="hidden"  id="edb_orgId1"/>
						</span>
						<span class="input-group-btn">
							<button class="btn btn-sm btn-primary" disabled="disabled" type="button" id="btn_select_manager">
                                <i class="icon-edit bigger-110"></i>
                                选择上级机构
                            </button>
						</span>
                  </div>
              </div>
          </div>
      </div>
    
      <div class="space-2"></div>
      <div class="form-group">
        <label class="control-label col-xs-12 col-sm-5 no-padding-right" for="edb_charge">负责人:</label>
        <div class="col-xs-12 col-sm-7">
          <div class="clearfix">
            <input type="text" name="edb.charge" id="edb_charge" class="col-xs-12 col-sm-12" />
          </div>
        </div>
      </div>
    
      <div class="space-2"></div>
      <div class="form-group">
        <label class="control-label col-xs-12 col-sm-5 no-padding-right" for="edb_chargePhone">负责人电话:</label>
        <div class="col-xs-12 col-sm-7">
          <div class="clearfix">
            <input type="text" name="edb.chargePhone" id="edb_chargePhone" class="col-xs-12 col-sm-12" />
          </div>
        </div>
      </div>
    
      <div class="space-2"></div>
      <div class="form-group">
        <label class="control-label col-xs-12 col-sm-5 no-padding-right" for="edb_officeAddr">办公地址:</label>
        <div class="col-xs-12 col-sm-7">
          <div class="clearfix">
            <input type="text" name="edb.officeAddr" id="edb_officeAddr" class="col-xs-12 col-sm-12" />
          </div>
        </div>
      </div>
    
      <div class="space-2"></div>
      <div class="form-group">
        <label class="control-label col-xs-12 col-sm-5 no-padding-right" for="edb_zipCode">邮政编码:</label>
        <div class="col-xs-12 col-sm-7">
          <div class="clearfix">
            <input type="text" name="edb.zipCode" id="edb_zipCode" class="col-xs-12 col-sm-12" />
          </div>
        </div>
      </div>
      
      <div class="space-2"></div>
      <div class="form-group" style="display: none">
        <label class="control-label col-xs-12 col-sm-5 no-padding-right" for="edb_level">层级:</label>
        <div class="col-xs-12 col-sm-7">
          <div class="clearfix">
            <input type="text" name="edb.level" id="edb_level" class="col-xs-12 col-sm-12"  readonly="readonly"/>
          </div>
        </div>
      </div>
  </form>
  
</div>
<script type="text/javascript" src="js/edb-modify.js"></script>
