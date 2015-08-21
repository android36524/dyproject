<%@page import="org.apache.commons.lang.StringUtils"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String id = StringUtils.defaultIfEmpty(request.getParameter("id"),"");
%>
<script type="text/javascript">
	var _id = "<%=id%>";
</script>
<%@include file="/include-path.jsp"%>

<style>
.tips{color: red; margin-right: 5px;}
</style>

<div>
	<form class="form-horizontal dy-dialog-form" id="school_form" 
		method="post">
		<input type="hidden" name="school.id" id="school_id"/>
		<div class="form-group" id="accountDiv">
      <label class="control-label col-xs-12 col-sm-5 no-padding-right" for="account">账号:</label>
        <div class="col-xs-12 col-sm-7">
          <div class="clearfix">
            <input type="text" name="account" id="account" class="col-xs-12 col-sm-12" value="${account }" readonly="readonly" />
          </div>
        </div>  
      </div> 
      <div class="form-group" id="roleDiv">
      	<label class="control-label col-xs-12 col-sm-5 no-padding-right" for="edb_orgName">所属角色:</label>
        <div class="col-xs-12 col-sm-7">
          <div class="clearfix">
            <input type="text" name="roleNo" id="roleNo" class="col-xs-12 col-sm-12" value="${roleName }" readonly="readonly"/>
          </div>
        </div> 
      </div>
		<div class="row">
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-xs-12 col-sm-5 no-padding-right"
						for="school_orgName"><b class="tips">*</b>学校名称:</label>
					<div class="col-xs-12 col-sm-9">
						<div class="clearfix">
							<input type="text" name="school.orgName" id="school_orgName"
								class="col-xs-12 col-sm-9" />
						</div>
					</div>
				</div>
			</div>
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-xs-12 col-sm-5 no-padding-right"
						for="show_orgCode"><b class="tips">*</b>学校编码:</label>
					<div class="col-xs-12 col-sm-9">
						<div class="clearfix">
							<input type="text" id="show_orgCode" disabled="disabled"
								class="col-xs-12 col-sm-9" /> <input type="hidden"
								name="school.orgCode" id="school_orgCode" />
						</div>
					</div>
				</div>
			</div>
		</div>

		<div class="row">
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-xs-12 col-sm-5 no-padding-right"
						for="schoolExt_schoolCategory"><b class="tips">*</b>学校类别:</label>
					<div class="col-xs-12 col-sm-9">
						<div class="clearfix">
							<select name="schoolExt.schoolCategory" class="col-xs-12 col-sm-9"
								id="schoolExt_schoolCategory">
								<option value="">-请选择-</option>
								<c:forEach items="${schTypeList }" var="item">
									<option value="${item.value }">${item.name }</option>
								</c:forEach>
							</select>
						</div>
					</div>
				</div>
			</div>
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-xs-12 col-sm-5 no-padding-right"
						for="schoolExt_buildSchoolTime">建校时间:</label>
					<div class="col-xs-12 col-sm-7 input-group">
						<span class="input-group-addon"> <i
							class="icon-calendar bigger-110"></i>
						</span> <input type="text" name="schoolExt.buildSchoolTime"
							id="schoolExt_buildSchoolTime" class="form-control"
							readonly="readonly" />
					</div>
				</div>
			</div>
		</div>

		<div class="row">
			<div class="col-xs-12">
				<div class="form-group">
					<label class="control-label col-xs-12 col-sm-5 no-padding-right"
						for="school_officeAddr"><b class="tips">*</b>学校地址:</label>
					<div class="col-xs-12 col-sm-9">
						<div class="clearfix">
							<input type="text" name="school.officeAddr"
								id="school_officeAddr" class="col-xs-12 col-sm-12" />
						</div>
					</div>
				</div>
			</div>
		</div>
			
		<div class="row">
			<div class="col-xs-10">
				<div class="form-group">
					<label class="control-label col-xs-12 col-sm-5 no-padding-right"
						for="school_provinceCode"><b class="tips">*</b>所属行政区划:</label>
					<div class="col-xs-12 col-sm-9">
						<div class="clearfix">
							<select name="school.provinceCode" id="school_provinceCode" 
							class="col-sm-3" style="margin-right: 5px;">
								<option value="">-请选择-</option>
								<c:forEach items="${provinceList }" var="item">
									<option value="${item.BM }">${item.MC }</option>
								</c:forEach>
							</select> <select name="school.cityCode" id="school_cityCode" 
							class="col-sm-3" style="margin-right: 5px;">
								<option value="">-请选择-</option>
							</select> <select name="school.areaCode" id="school_areaCode" 
							class="col-sm-3">
								<option value="">-请选择-</option>
							</select>
						</div>
					</div>
				</div>
			</div>
		</div>

		<div class="row">
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-xs-12 col-sm-5 no-padding-right"
						for="schoolExt_schoolLeaderName">校长姓名:</label>
					<div class="col-xs-12 col-sm-9">
						<div class="clearfix">
							<input type="text" name="schoolExt.schoolLeaderName"
								id="schoolExt_schoolLeaderName" class="col-xs-12 col-sm-9" />
						</div>
					</div>
				</div>
			</div>
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-xs-12 col-sm-5 no-padding-right"
						for="schoolExt_diplomaNo">法定证书号:</label>
					<div class="col-xs-12 col-sm-9">
						<div class="clearfix">
							<input type="text" name="schoolExt.diplomaNo"
								id="schoolExt_diplomaNo" class="col-xs-12 col-sm-9" />
						</div>
					</div>
				</div>
			</div>
		</div>

		<div class="row">
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-xs-12 col-sm-5 no-padding-right"
						for="school_partyLeader">党委负责人:</label>
					<div class="col-xs-12 col-sm-9">
						<div class="clearfix">
							<input type="text" name="schoolExt.partyLeader"
								id="schoolExt_partyLeader" class="col-xs-12 col-sm-9" />
						</div>
					</div>
				</div>
			</div>
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-xs-12 col-sm-5 no-padding-right"
						for="schoolExt_schoolPhoneNumber">学校联系电话:</label>
					<div class="col-xs-12 col-sm-9">
						<div class="clearfix">
							<input type="text" name="schoolExt.schoolPhoneNumber"
								id="schoolExt_schoolPhoneNumber" class="col-xs-12 col-sm-9" />
						</div>
					</div>
				</div>
			</div>
		</div>
		
		<div class="row">
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-xs-12 col-sm-5 no-padding-right"
						for="schoolExt_legalPerson">法定代表人:</label>
					<div class="col-xs-12 col-sm-9">
						<div class="clearfix">
							<input type="text" name="schoolExt.legalPerson" id="schoolExt_legalPerson"
								class="col-xs-12 col-sm-9" />
						</div>
					</div>
				</div>
			</div>
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-xs-12 col-sm-5 no-padding-right"
						for="schoolExt_email">电子邮箱:</label>
					<div class="col-xs-12 col-sm-9">
						<div class="clearfix">
							<input type="text" name="schoolExt.email" id="schoolExt_email"
								class="col-xs-12 col-sm-9" />
						</div>
					</div>
				</div>
			</div>
		</div>

		<div class="row">
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-xs-12 col-sm-5 no-padding-right"
						for="school_zipCode">邮政编码:</label>
					<div class="col-xs-12 col-sm-9">
						<div class="clearfix">
							<input type="text" name="school.zipCode" id="school_zipCode"
								class="col-xs-12 col-sm-9" />
						</div>
					</div>
				</div>
			</div>
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-xs-12 col-sm-5 no-padding-right"
						for="schoolExt_fax">传真:</label>
					<div class="col-xs-12 col-sm-9">
						<div class="clearfix">
							<input type="text" name="schoolExt.fax" id="schoolExt_fax"
								class="col-xs-12 col-sm-9" />
						</div>
					</div>
				</div>
			</div>
		</div>

		<div class="row">
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-xs-12 col-sm-5 no-padding-right"
						for="schoolExt_length">学制:</label>
					<div class="col-xs-12 col-sm-9">
						<div class="clearfix">
							<input type="text" name="schoolExt.length" id="schoolExt_length"
								class="col-xs-12 col-sm-9" />
						</div>
					</div>
				</div>
			</div>
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-xs-12 col-sm-5 no-padding-right"
						for="schoolExt_homeUrl">主页地址:</label>
					<div class="col-xs-12 col-sm-9">
						<div class="clearfix">
							<input type="text" name="schoolExt.homeUrl"
								id="schoolExt_homeUrl" class="col-xs-12 col-sm-9" />
						</div>
					</div>
				</div>
			</div>
		</div>

		<div class="row">
			<div class="col-xs-12">
				<div class="form-group">
					<label class="control-label col-xs-12 col-sm-5 no-padding-right"
						for="schoolExt_schoolHis">历史沿革:</label>
					<div class="col-xs-12 col-sm-10s">
						<div class="clearfix">
							<textarea class="col-xs-12 col-sm-12" name="schoolExt.schoolHis"
								id="schoolExt_schoolHis"></textarea>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="space-2"></div>
	</form>
	<div class="col-xs-12">
		<table id="grid-table"></table>
		<div id="grid-pager"></div>
	</div>
</div>
<script type="text/javascript" src="js/school.js"></script>
<script type="text/javascript">
var _schoolId = '${_schoolId}';
var provinceCode = '${provinceCode}';
var cityCode = '${cityCode}';
var areaCode = '${areaCode}';
initModifyPage(_schoolId, provinceCode, cityCode, areaCode);

$(function(){
	//禁用表单元素
	$("form[id='school_form'] :text").attr("disabled","disabled");  
    $("form[id='school_form'] textarea").attr("disabled","disabled");  
    $("form[id='school_form'] select").attr("disabled","disabled");  
});
</script>
