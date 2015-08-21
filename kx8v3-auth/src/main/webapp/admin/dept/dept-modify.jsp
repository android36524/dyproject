<%@page import="org.apache.commons.lang.StringUtils"%>
<%@ page import="com.dayang.commons.util.DaYangCommonUtil" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@include file="/include-path.jsp"%>
<%
  String id = StringUtils.defaultIfEmpty(request.getParameter("id"), "");
  String orgId = StringUtils.defaultIfEmpty(request.getParameter("orgId"), "");
%>
<script type="text/javascript">
  var _id = "<%=id%>";
  
  var _title ="${title}";
  var name = "${name}";
</script>
<div>
  <form class="form-horizontal dy-dialog-form" id="dept_form" method="post">

	<c:if test="${title=='教育局'||title=='学校' }">
	<div class="form-group">
      <label class="control-label col-xs-12 col-sm-5 no-padding-right" for="orgName">所属${title}:</label>
      <div class="col-xs-12 col-sm-7">
        <div class="clearfix">
          <input type="text" name="orgName"  id="orgName" class="col-xs-12 col-sm-12" value="${orgName} " readonly="readonly" />
        </div>
      </div>
    </div>
    <div class="space-2"></div>


    <div class="form-group">
      <label class="control-label col-xs-12 col-sm-5 no-padding-right" for="dept_deptName"><span style="color: red"> *&nbsp;&nbsp;</span>部门名称:</label>
      <div class="col-xs-12 col-sm-7">
        <input type="hidden" name="dept.id"  id="dept_id"  />
        <input type="hidden" class="input" id="dept_orgId" name="dept.orgId" value="<%= orgId%>" />
        <div class="clearfix">
          <input type="text" name="dept.deptName"  id="dept_deptName" class="col-xs-12 col-sm-12" maxlength="20" />
        </div>
      </div>
    </div>
    <div class="space-2"></div>

    <div class="form-group">
      <label class="control-label col-xs-12 col-sm-5 no-padding-right" for="dept_deptName">所属部门:</label>
      <div class="col-xs-12 col-sm-7">
        <div class="input-group">
              <span>
                  <div class="clearfix">
                    <input type="text" name="parentName" id="dept_parentName"  class="col-xs-12 col-sm-10 spinner-input" readonly="readonly"/>
                  </div>
                  <input type="hidden" name="dept.pid" id="dept_pid"/>
              </span>
              <span class="input-group-btn">
                  <button class="btn btn-sm btn-primary" type="button" id="btn_select_dept">
                    <i class="icon-edit bigger-110"></i>
                    所属部门
                  </button>
              </span>
        </div>
      </div>
    </div>
    <div class="space-2"></div>

    <div class="form-group">
      <label class="control-label col-xs-12 col-sm-5 no-padding-right" for="dept_deptCode">部门编号:</label>
      <div class="col-xs-12 col-sm-7">
        <div class="clearfix">
          <input type="text" name="dept.deptCode"  id="dept_deptCode" class="col-xs-12 col-sm-12"  readonly="readonly"/>
        </div>
      </div>
    </div>
    <div class="space-2"></div>

    <div class="form-group">
      <label class="control-label col-xs-12 col-sm-5 no-padding-right" for="dept_telphone">部门联系电话:</label>
      <div class="col-xs-12 col-sm-7">
        <div class="clearfix">
          <input type="text" name="dept.telphone"  id="dept_telphone" class="col-xs-12 col-sm-12" />
        </div>
      </div>
    </div>
    <div class="space-2"></div>

    <div class="form-group">
      <label class="control-label col-xs-12 col-sm-5 no-padding-right" for="dept_charge">部门负责人:</label>
      <div class="col-xs-12 col-sm-7">
        <div class="clearfix">
          <input type="text" name="dept.charge"  id="dept_charge" class="col-xs-12 col-sm-12" />
        </div>
      </div>
    </div>
    <div class="space-2"></div>

    <div class="form-group">
      <label class="control-label col-xs-12 col-sm-5 no-padding-right" for="dept_chargePhone">负责人电话:</label>
      <div class="col-xs-12 col-sm-7">
        <div class="clearfix">
          <input type="text" name="dept.chargePhone"  id="dept_chargePhone" class="col-xs-12 col-sm-12" />
        </div>
      </div>
    </div>
    <div class="space-2"></div>




    <!--  <div class="form-group">
      <label class="control-label col-xs-12 col-sm-5 no-padding-right" for="dept_remark">备注:</label>
      <div class="col-xs-12 col-sm-7">
        <div class="clearfix">
          <textarea class="col-xs-12 col-sm-12" name="dept.remark" id="dept_remark"></textarea>
        </div>
      </div>
    </div>
    <div class="space-2"></div>-->

    <div class="form-group">
      <label class="control-label col-xs-12 col-sm-5 no-padding-right" for="dept_seq">排序:</label>
      <div class="col-xs-12 col-sm-7">
        <div class="clearfix">
          <input  type="text" name="dept.seq" id="dept_seq" class="input-mini"/>
        </div>
      </div>
    </div>
    <div class="space-2"></div>
	</c:if>
	
	<c:if test="${title=='公司' }">
      
      
      <div class="form-group" id="companyNameDiv">
        <label class="control-label col-xs-12 col-sm-3 no-padding-right my-mustlabel" for="company_name">公司名称:</label>
        <div class="col-xs-12 col-sm-9">
          <div class="clearfix">
            <input type="hidden" name="dept.orgId" id="dept_orgId" class="col-xs-12 col-sm-12" value=<%= orgId%> readonly="readonly"/>
            <input type="text" name="dept_orgName" id="dept_orgName" class="col-xs-12 col-sm-12" value="${name}" readonly="readonly"/>
          </div>
        </div>  
      </div>
      
      <div class="form-group">
        <label class="control-label col-xs-12 col-sm-3 no-padding-right my-mustlabel" for="company_name">部门名称:</label>
        <div class="col-xs-12 col-sm-9">
	        <input type="hidden" name="dept.id"  id="dept_id"  />
          <div class="clearfix">
            <input type="text" name="dept.deptName" id="dept_deptName" class="col-xs-12 col-sm-12" maxlength="50" onkeyup="value=value.replace(/[^\w\-\u4E00-\u9FA5]/g,'')" onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^\w\-\u4E00-\u9FA5]/g,''))"/>
          </div>
        </div>  
      </div>

      <div class="space-2"></div>
      <div class="form-group">
        <label class="control-label col-xs-12 col-sm-3 no-padding-right my-mustlabel" for="company_orgCode">组织类别:</label>
        <div class="col-xs-12 col-sm-9">
          <div class="clearfix">
            <input type="text"  id="company_flag" class="col-xs-12 col-sm-12" value="部门" readonly="readonly"/>
            <input type="hidden" name="company.orgFlag" id="company_orgFlag" class="col-xs-12 col-sm-12" value="2"/>
          </div>
        </div>
      </div>
    
      <div class="space-2"></div>
      <div class="form-group">
        <label class="control-label col-xs-12 col-sm-3 no-padding-right" for="dept_deptName">所属部门:</label>
          <div class="col-xs-12 col-sm-9">
              <div class="clearfix">
                  <div class="input-group">
						<span>
							<input type="text" name="parentName" id="dept_parentName" class="col-xs-12 col-sm-10 spinner-input" readonly="readonly"/>
							<input type="hidden" name="dept.pid" id="dept_pid"/>
						</span>
						<span class="input-group-btn">
							<button class="btn btn-sm btn-primary"  type="button" id="btn_select_dept">
                                <i class="icon-edit bigger-110"></i>
                                	选择所属部门
                            </button>
						</span>
                  </div>
              </div>
          </div>
      </div>
      
      <div class="form-group" style="display: none">
      <label class="control-label col-xs-12 col-sm-5 no-padding-right" for="dept_deptCode">部门编号:</label>
      <div class="col-xs-12 col-sm-7">
        <div class="clearfix">
          <input type="text" name="dept.deptCode"  id="dept_deptCode" class="col-xs-12 col-sm-12"  readonly="readonly"/>
        </div>
      </div>
    </div>
      
      <div class="space-2"></div>
      <div class="form-group">
        <label class="control-label col-xs-12 col-sm-3 no-padding-right" for="dept_charge">负责人:</label>
        <div class="col-xs-12 col-sm-9">
          <div class="clearfix">
            <input type="text" name="dept.charge" id="dept_charge" class="col-xs-12 col-sm-12" />
          </div>
        </div>
      </div>
    
      <div class="space-2"></div>
      <div class="form-group">
        <label class="control-label col-xs-12 col-sm-3 no-padding-right" for="dept_chargePhone">负责人电话:</label>
        <div class="col-xs-12 col-sm-9">
          <div class="clearfix">
            <input type="text" name="dept.chargePhone" id="dept_chargePhone" class="col-xs-12 col-sm-12" />
          </div>
        </div>
      </div>
    
      <div class="space-2"></div>
      <div class="form-group">
        <label class="control-label col-xs-12 col-sm-3 no-padding-right" for="dept_telphone">办公电话:</label>
        <div class="col-xs-12 col-sm-9">
          <div class="clearfix">
            <input type="text" name="dept.telphone" id="dept_telphone" class="col-xs-12 col-sm-12" />
          </div>
        </div>
      </div>
    
      <div class="space-2"></div>
      <div class="form-group">
        <label class="control-label col-xs-12 col-sm-3 no-padding-right" for="dept_fax">传真:</label>
        <div class="col-xs-12 col-sm-9">
          <div class="clearfix">
            <input type="text" name="dept.fax" id="dept_fax" class="col-xs-12 col-sm-12" />
          </div>
        </div>
      </div>

    </c:if>

  </form>
</div>
<script type="text/javascript">
  $('#dept_seq').ace_spinner({
    value:1,
    min:1,
    max:200,
    step:1,
    btn_up_class:'btn-info' ,
    btn_down_class:'btn-info'
  }).on('change', function(){

  });
</script>
<script type="text/javascript" src="js/dept-modify.js"></script>
<script type="text/javascript" src="../js/commonDiyCss.js"></script>