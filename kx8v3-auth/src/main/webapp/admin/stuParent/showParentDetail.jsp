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
</script>
<script type="text/javascript" src="js/showParentDetail.js"></script>

<div>
  <form class="form-horizontal dy-dialog-form" id="stuParentDetail_form" method="post">

    <div class="cut-off-rule">账户信息</div>
    <div class="form-group">
      <label class="control-label col-xs-12 col-sm-5 no-padding-right" for="user_account">账号:</label>
      <div class="col-xs-12 col-sm-7">
        <input type="hidden" name="user.id"  id="user_id"  />
        <div class="clearfix">
          <label id="stuParent_account" class="col-xs-12 col-sm-12"></label>
        </div>
      </div>
    </div>
    <div class="space-2"></div>

    <div class="form-group">
      <label class="control-label col-xs-12 col-sm-5 no-padding-right" for="user_telephone">绑定手机:</label>
   	  <div class="col-xs-12 col-sm-7">
        <div class="clearfix">
          <label id="stuParent_telephone" class="col-xs-12 col-sm-12"></label>
        </div>
      </div>
    </div>
    <div class="space-2"></div>

    <div class="form-group">
      <label class="control-label col-xs-12 col-sm-5 no-padding-right" for="user_email">绑定邮箱:</label><span>(用于系统登录和密码找回)</span>
      <div class="col-xs-12 col-sm-7">
        <div class="clearfix">
          <label id="stuParent_email" class="col-xs-12 col-sm-12"></label>
        </div>
      </div>
    </div>
    <div class="space-2"></div>

    <div class="form-group">
      <label class="control-label col-xs-12 col-sm-5 no-padding-right" for="user_password">密码:</label><span>(初始密码为：888888)</span>
      <div class="col-xs-12 col-sm-7">
        <div class="clearfix">
          <label id="stuParent_password" class="col-xs-12 col-sm-12"></label>
        </div>
      </div>
    </div>
    <div class="space-2"></div>
    
    <div class="form-group">
      <label class="control-label col-xs-12 col-sm-5 no-padding-right" for="dept_telphone">所属角色:</label>
      <div class="col-xs-12 col-sm-7">
	      <div class="clearfix">
	      	<label id="" class="col-xs-12 col-sm-12"> 家长</label>       	
	      </div>
      </div>
    </div>
    <div class="space-2"></div>

	<div class="cut-off-rule" >家长基本信息</div>

    <div class="row">
      <div class="col-xs-6">
        <div class="form-group">
          <label class="control-label col-xs-12 col-sm-5 no-padding-right" for="stuParent_name">家长姓名:</label>
         <div class="col-xs-12 col-sm-9">
            <div class="clearfix">
              <label id="stuParent_name" class="col-xs-12 col-sm-12"></label>
            </div>
          </div>
        </div>
      </div>
      <div class="col-xs-6">
        <div class="form-group">
          <label class="control-label col-xs-12 col-sm-5 no-padding-right" for="stuParent_telphone">联系手机:</label>
          <div class="col-xs-12 col-sm-9">
            <div class="clearfix">
              <label id="stuParent_telphone" class="col-xs-12 col-sm-12"></label>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="space-2"></div>
    
     <div class="row">
      <div class="col-xs-6">
        <div class="form-group">
          <label class="control-label col-xs-12 col-sm-5 no-padding-right" for="stuParent_sex1">性别:</label>
          <div class="col-xs-12 col-sm-9">
               <label id="stuParent_sex_showname" class="col-xs-12 col-sm-12"></label>
          </div>
        </div>
      </div>
      <div class="col-xs-6">
        <div class="form-group">
          <label class="control-label col-xs-12 col-sm-5 no-padding-right" for="stuParent_company">工作单位:</label>
          <div class="col-xs-12 col-sm-9">
            <div class="clearfix">
               <label id="stuParent_company" class="col-xs-12 col-sm-12"></label>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="space-2"></div>
    
    <div class="row">
      <div class="col-xs-6">
        <div class="form-group">
          <label class="control-label col-xs-12 col-sm-5 no-padding-right" for="stuParent_officePhone">单位电话:</label>
          <div class="col-xs-12 col-sm-9">
            <div class="clearfix">
             <label id="stuParent_officePhone" class="col-xs-12 col-sm-12"></label>
            </div>
          </div>
        </div>
      </div>
      <div class="col-xs-6">
        <div class="form-group">
          <label class="control-label col-xs-12 col-sm-5 no-padding-right" for="stuParent_officeAddr">单位地址:</label>
          <div class="col-xs-12 col-sm-9">
            <div class="clearfix">
              <label id="stuParent_officeAddr" class="col-xs-12 col-sm-12"></label>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="space-2"></div>
    
    <div class="row">
      <div class="col-xs-6">
        <div class="form-group">
          <label class="control-label col-xs-12 col-sm-5 no-padding-right" for="stuParent_duties">职务:</label>
          <div class="col-xs-12 col-sm-9">
            <div class="clearfix">
               <label id="stuParent_duties" class="col-xs-12 col-sm-12"></label>
            </div>
          </div>
        </div>
      </div>
      <div class="col-xs-6">
        <div class="form-group">
          <label class="control-label col-xs-12 col-sm-5 no-padding-right" for="stuParent_political">政治面貌:</label>
          <div class="col-xs-12 col-sm-9">
            <div class="clearfix">
               <label id="stuParent_political_showname" class="col-xs-12 col-sm-12"></label>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="space-2"></div>
    
    <div class="row">
      <div class="col-xs-6">
        <div class="form-group">
          <label class="control-label col-xs-12 col-sm-5 no-padding-right" for="stuParent_zipCode">邮政编码:</label>
          <div class="col-xs-12 col-sm-9">
            <div class="clearfix">
               <label id="stuParent_zipCode" class="col-xs-12 col-sm-12"></label>
            </div>
          </div>
        </div>
      </div>
      <div class="col-xs-6">
        <div class="form-group">
          <label class="control-label col-xs-12 col-sm-5 no-padding-right" for="stuParent_email">电子邮箱:</label>
          <div class="col-xs-12 col-sm-9">
            <div class="clearfix">
               <label id="stuParent_email" class="col-xs-12 col-sm-12"></label>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="space-2"></div>
    
    <div class="row">
      <div class="col-xs-6">
        <div class="form-group">
          <label class="control-label col-xs-12 col-sm-5 no-padding-right" for="stuParent_addr">联系地址:</label>
          <div class="col-xs-12 col-sm-9">
            <div class="clearfix">
               <label id="stuParent_addr" class="col-xs-12 col-sm-12"></label>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="space-2"></div>

     <div class="row">
       	<div class="cut-all-rule">已关联学生列表</div>
    	<div class="col-xs-12" style="overflow-x:hidden;overflow-y:auto;height:150px;">
          <table id="chooseStuDetail-table"></table>
     	</div>
    </div>
    <div class="space-2"></div>
    
  </form>
</div>
