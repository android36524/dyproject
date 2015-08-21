<%@page import="org.apache.commons.lang.StringUtils"%>
<%@ page import="com.dayang.commons.util.DaYangCommonUtil" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@include file="/include-path.jsp"%>
<%
  String id = StringUtils.defaultIfEmpty(request.getParameter("id"), "");
  String schId = StringUtils.defaultIfEmpty(request.getParameter("schId"), "");
%>
<style type="text/css">
		.modal-dialog{
			width: 900px !important;
		}
	</style>
<div>
  <form class="form-horizontal dy-dialog-form" id="stuParent_form" method="post">

	<div class="cut-off-rule" >家长基本信息</div>

    <div class="row">
      <div class="col-xs-6">
        <div class="form-group">
          <label class="control-label col-xs-12 col-sm-5 no-padding-right" for="stuParent_name"><span style="color: red"> *&nbsp;</span>家长姓名:</label>
         <div class="col-xs-12 col-sm-9">
            <div class="clearfix">
                <input type="hidden" name="isExist" id="isExist" value=""/>
               <input type="hidden" name="schId" id="schId" value="<%= schId%>"/>
              <input type="hidden" name="stuParent.ID" id="stuParent_ID" value="<%= id %>" >
              <input type="text" name="stuParent.name" id="stuParent_name" class="col-xs-12 col-sm-12 spinner-input" onchange="checkNameAndTel()" />
            </div>
          </div>
        </div>
      </div>
      
       <div class="col-xs-6">
        <div class="form-group">
          <label class="control-label col-xs-12 col-sm-5 no-padding-right" for="stuParent_nameSpell">姓名简拼:</label>
          <div class="col-xs-12 col-sm-9">
            <div class="clearfix">
              <input type="text" name="stuParent.nameSpell" id="stuParent_nameSpell" class="col-xs-12 col-sm-12 spinner-input" readonly="readonly" />
            </div>
          </div>
        </div>
      </div>
      
      
    </div>
    <div class="space-2"></div>
    
     <div class="row">
     <div class="col-xs-6">
        <div class="form-group">
          <label class="control-label col-xs-12 col-sm-5 no-padding-right" for="stuParent_telphone"><span style="color: red"> *&nbsp;</span>联系手机:</label>
          <div class="col-xs-12 col-sm-9">
            <div class="clearfix">
              <input type="text" name="stuParent.telphone" id="stuParent_telphone" class="col-xs-12 col-sm-12 spinner-input" onchange="checkNameAndTel()"/>
            </div>
          </div>
        </div>
      </div>
      <div class="col-xs-6">
        <div class="form-group">
          <label class="control-label col-xs-12 col-sm-5 no-padding-right" for="stuParent_sex1"><span style="color: red"> *&nbsp;</span>性别:</label>
			  <div class="col-xs-12 col-sm-9">
			   <label style="margin-right: 20px">
			    <input type="radio" name="stuParent.sex" id="stuParent_sex0"  value="${manVal}"  checked >
			     <span>${manName}</span>
			  </label>
			  <label>
			    <input type="radio" name="stuParent.sex" id="stuParent_sex1" value="${woManVal}" >
			   <span>${woManName}</span>
			  </label>
         	 </div>
        </div>
      </div>
    </div>
    <div class="space-2"></div>
    
    <div class="row">
       <div class="col-xs-6">
        <div class="form-group">
          <label class="control-label col-xs-12 col-sm-5 no-padding-right" for="stuParent_company">工作单位:</label>
          <div class="col-xs-12 col-sm-9">
            <div class="clearfix">
              <input type="text" name="stuParent.company" id="stuParent_company" class="col-xs-12 col-sm-12 spinner-input" />
            </div>
          </div>
        </div>
      </div>
      <div class="col-xs-6">
        <div class="form-group">
          <label class="control-label col-xs-12 col-sm-5 no-padding-right" for="stuParent_officePhone">单位电话:</label>
          <div class="col-xs-12 col-sm-9">
            <div class="clearfix">
             <input type="text" name="stuParent.officePhone" id="stuParent_officePhone" class="col-xs-12 col-sm-12 spinner-input" />
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="space-2"></div>
    
    <div class="row">
     <div class="col-xs-6">
        <div class="form-group">
          <label class="control-label col-xs-12 col-sm-5 no-padding-right" for="stuParent_officeAddr">单位地址:</label>
          <div class="col-xs-12 col-sm-9">
            <div class="clearfix">
              <input type="text" name="stuParent.officeAddr" id="stuParent_officeAddr" class="col-xs-12 col-sm-12 spinner-input" />
            </div>
          </div>
        </div>
      </div>
      <div class="col-xs-6">
        <div class="form-group">
          <label class="control-label col-xs-12 col-sm-5 no-padding-right" for="stuParent_duties">职务:</label>
          <div class="col-xs-12 col-sm-9">
            <div class="clearfix">
               <input type="text" name="stuParent.duties" id="stuParent_duties" class="col-xs-12 col-sm-12 spinner-input" />
            </div>
          </div>
        </div>
      </div>
     
    </div>
    <div class="space-2"></div>
    
    <div class="row">
       <div class="col-xs-6">
        <div class="form-group">
          <label class="control-label col-xs-12 col-sm-5 no-padding-right" for="stuParent_political">政治面貌:</label>
          <div class="col-xs-12 col-sm-9">
            <div class="clearfix">
              <select class="col-xs-12 col-sm-12" id="stuParent_political" dyId="chooseId2" name="stuParent.political">
                <option value=''>--请选择--</option>
                <c:forEach items="${politicalList}" var="dic">
                	<option value="${dic.value}">${dic.name}</option>
                </c:forEach>
              </select>
            </div>
          </div>
        </div>
      </div>
      <div class="col-xs-6">
        <div class="form-group">
          <label class="control-label col-xs-12 col-sm-5 no-padding-right" for="stuParent_zipCode">邮政编码:</label>
          <div class="col-xs-12 col-sm-9">
            <div class="clearfix">
               <input type="text" name="stuParent.zipCode" id="stuParent_zipCode" class="col-xs-12 col-sm-12 spinner-input" />
            </div>
          </div>
        </div>
      </div>
    
    </div>
    <div class="space-2"></div>
    
    <div class="row">
       <div class="col-xs-6">
        <div class="form-group">
          <label class="control-label col-xs-12 col-sm-5 no-padding-right" for="stuParent_email">电子邮箱:</label>
          <div class="col-xs-12 col-sm-9">
            <div class="clearfix">
              <input type="text" name="stuParent.email" id="stuParent_email" class="col-xs-12 col-sm-12 spinner-input" />
            </div>
          </div>
        </div>
      </div>
      <div class="col-xs-6">
        <div class="form-group">
          <label class="control-label col-xs-12 col-sm-5 no-padding-right" for="stuParent_addr">联系地址:</label>
          <div class="col-xs-12 col-sm-9">
            <div class="clearfix">
              <input type="text" name="stuParent.addr" id="stuParent_addr" class="col-xs-12 col-sm-12 spinner-input" />
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="space-2"></div>
    <div id="account_info">
    <div class="cut-off-rule">账户信息</div>
    <div class="form-group">
      <label class="control-label col-xs-12 col-sm-5 no-padding-right" for="stuParent_account">账号:</label>
      <div class="col-xs-12 col-sm-7">
        <div class="clearfix">
          <input type="text" name="account"  id="account" class="col-xs-12 col-sm-12" value="${account}"  readonly="readonly"/>
        </div>
      </div>
    </div>
    <div class="space-2"></div>
    
    <div class="form-group">
      <label class="control-label col-xs-12 col-sm-5 no-padding-right" for="dept_telphone">所属角色:</label>
      <div class="col-xs-12 col-sm-7">
            <div class="clearfix">  
              <input type="hidden" name="roleId"  id="roleId"  value="${roleId}" />
              <input type="text" name="roleName"  id="roleName" class="col-xs-12 col-sm-12" value="${roleName}"  readonly="readonly"/>
      		</div>
      </div>
    </div>
    <div class="space-2"></div>
	</div>
	<div class="cut-off-rule">快速关联孩子</div>
	 <div class="form-group">
      	请关联您的孩子（请至少关联一个孩子）:
    </div>
    <div class="space-2"></div>

    <div class="form-group">
      <label class="control-label col-xs-12 col-sm-5 no-padding-right" for="dept_deptName">请选择孩子:</label>
   	   <div class="input-group col-xs-12 col-sm-7 ">
	        <div class="clearfix">
	          <input type="text" name="name"  id="relaStu_name" class="col-xs-10 col-sm-12" readonly="readonly"/>
	        </div>
	        <input type="hidden" name="relaStu.id" id="relaStu_id"/>
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
		    <input type="radio" name="relaStu.guardian" id="relaStu_guardian1" value="${guarderVal}" checked="checked">
		    <span>${guarderName}</span>
		  </label>
		  
		  <label>
		    <input type="radio" name="relaStu.guardian" id="relaStu_guardian2" value="${notGuarderVal}" >
		    <span>${notGuarderName}</span>
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
          <table id="chooseStu-table" name="chooseTable" ></table>
     	</div>
    </div>
    <div class="space-2"></div>
    
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
<script type="text/javascript" src="js/parent-modify.js"></script>