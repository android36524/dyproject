
var company_modify = (function () {
    return{
          //初始化省
          initProvince:function(){
              $.post(path+'/admin/division/provinceList',function(retList){
                  if(retList){
                      for (var i = 0; i < retList.length; i++) {
                          var divisionObj = retList[i];
                          $("#company_provinceCode").append("<option value='" + divisionObj.BM + "'>" + divisionObj.MC + "</option>");
                      }
                  }
              });
          },
          //根据省初始化市
          initCityOrAreaByBmId: function (bmId,id) {
              if(id == "company_cityCode"){
            	  company_modify.removeSlect("company_cityCode")
                  company_modify.removeSlect("company_areaCode")
              }else{
            	  company_modify.removeSlect(id);
              }
              $.post(path+'/admin/division/findCityOrAreaByBmId',{bmId:bmId},function(retList){
                  if(retList){
                      var selectObj = $("#" + id);
                      for (var i = 0; i < retList.length; i++) {
                          var divisionObj = retList[i];
                          selectObj.append("<option value='" + divisionObj.BM + "'>" + divisionObj.MC + "</option>");
                      }
                  }
              }); 
          },
        //删除select item
        removeSlect : function(selectId){
            var selectObject = $("#" + selectId + " option");
            selectObject.each(function(){
                if( $(this).val() !== ""){
                    $(this).remove();
                }
            });
        },
        setLayerTeacherGroup : function (idData,nameData){
            $("#company_orgId").val(idData);
            $("#parentOrgName").val(nameData);
        }
    };
})();

$(document).ready(function () {
	var companyLevel = _companyLevel;//层级编码 公司
	$("#company_level").val(companyLevel);
	
	$.ajaxSetup({async:false});
    //初始化所有省
	company_modify.initProvince();
	
	if(flageId==0){//0新增  1修改
   	 document.getElementById("btn_select_manager").style.display='none';
   	document.getElementById("roleDiv").style.display='none';
   	if(_pOrgId!=null){
       	$.get(path + "/admin/companyDetail/queryById?id="+_pOrgId,function(ret){
               $("#parentOrgName").val(ret.orgName);
               $("#company_orgId").val(ret.id);
           });
       }
   }else if(flageId==1){
	   //alert(companyId);
	   //alert("===="+_pOrgId);
	   if(companyId){
		   document.getElementById("accoutDiv").style.display='none';
	    	document.getElementById("roleDiv").style.display='none';
		   $.get(path + "/admin/companyDetail/queryById?id="+companyId,function(ret){
	            $("#parentOrgName").val(ret.parentOrg);
	            document.getElementById("btn_select_manager").style.display='none';
	            company_modify.initCityOrAreaByBmId(ret.provinceCode,"company_cityCode");
	            if(ret.cityCode){
	            	company_modify.initCityOrAreaByBmId(ret.cityCode,"company_areaCode");
	            }
	            $.each(ret,function(k,v){
	                $("#company-form #company_"+k).val(v);
	            });
	        });
	   }
   }
	
    
    $.ajaxSetup({async:true});
    
  //省改变事件
    var provinceId;
    $("#company_provinceCode").change(function () {
    	 provinceId = $(this).val();
        if(provinceId != ""){
        	document.getElementById("btn_select_manager").style.display='none';
        }else{
        	document.getElementById("btn_select_manager").style.display='none';
        }
        company_modify.initCityOrAreaByBmId(provinceId,"company_cityCode");
    });
    
    //市改变事件
    $("#company_cityCode").change(function () {
       var cityId = $(this).val();
       company_modify.initCityOrAreaByBmId(cityId,"company_areaCode");
    });
    var areaCode ;
    $("#company_areaCode").change(function () {
    	areaCode = $(this).val();
     });
    
    //选择上级机构
    $("#btn_select_manager").on(ace.click_event, function() {
        var itSelf = $("#company_id").val();//修改的时候根据本级节点查询原有上级节点的上级节点
        var companyOrgId = $("#company_orgId").val();//要修改的公司的父节点
        //alert(provinceId);
        $.layer({
            type : 2,
            shadeClose: true,
            title: "上级机构选择",
            closeBtn: [0, true],
            shade: [0.8, '#000'],
            btns : 2,
            border: [0],
            offset: ['20px',''],
            area: ['500px', '430px'],
            iframe: {src: path + "/admin/companyDetail/company-manager.jsp?itSelf="+itSelf+"&companyOrgId="+companyOrgId+"&provinceId="+provinceId},
            yes : function(index){
                var rowData = JSON.parse(layer.getChildFrame("#selected",index).val());
                $("#parentOrgName").val(rowData.orgName);
                $("#company_orgId").val(rowData.id);
                //关闭弹窗
                layer.close(index);
            }
        });
    });
    
    //表单验证
    $("#company-form").validate({
        errorElement: 'span',
        errorClass: 'help-inline',
        focusInvalid: false,
        rules : {
            "company.orgName" : {
                required: true,
                maxlength:50,
                remote : {
                    url:path+"/admin/companyDetail/validateCompanyName",
                    type:"post",
                    async:false,
                    dataType:"json",
                    data:{
                        companyName:function(){
                            return $("#company_orgName").val();
                        },
                        companyId: function () {
                            return $("#company_id").val();
                        }
                    }
                }
            },
//            "company.provinceCode" : {
//                required : true
//            },
//            "company.cityCode" : {
//                required : true
//            },
            "company.areaCode" : {
                required : true
            },
            "company.chargePhone" : {
                telOrMobile : true
            },
            "company.parentOrgName" : {
                required : true
            },
            "company.charge":{
            	maxlength:20
            },
            "company.officeTel":{
            	telOrMobile : true
            },
            "company.fax":{
            	fax : true
            }
            
        },
        messages : {
            "company.orgName" : {
                required : "请输入公司名称",
                maxlength:"公司名称不超过50字",
                remote : "公司名称不允许重复"
            },
            "company.provinceCode" : {
                required : "请选择公司所属省"
            },
            "company.cityCode" : {
                required : "请选择公司所属市"
            },
            "company.areaCode" : {
                required : "请选择公司所属区/县"
            },
            "company.parentOrgName" : {
                required : "请选择上级机构"
            },
            "company.chargePhone" : {
                telOrMobile : "请输入正确的电话号码(中国)或移动电话号码(中国)"
            },
            "company.charge":{
            	maxlength:"不超过20字符"
            },
            "company.officeTel":{
            	telOrMobile : "请输入正确的电话号码(中国)或移动电话号码(中国)"
            },
            "company.fax":{
            	fax:"请输入正确的传真号码(中国)"
            }
        },
        invalidHandler: function (event, validator) { //display error alert on form submit
            $('.alert-danger', $('.login-form')).show();
        },

        highlight: function (e) {
            $(e).closest('.form-group').removeClass('has-info').addClass('has-error');
        },

        success: function (e) {
            $(e).closest('.form-group').removeClass('has-error').addClass('has-info');
            $(e).remove();
        },

        errorPlacement: function (error, element) {
            if(element.is(':checkbox') || element.is(':radio')) {
                var controls = element.closest('div[class*="col-"]');
                if(controls.find(':checkbox,:radio').length > 1) controls.append(error);
                else error.insertAfter(element.nextAll('.lbl:eq(0)').eq(0));
            }
            else if(element.is('.select2')) {
                error.insertAfter(element.siblings('[class*="select2-container"]:eq(0)'));
            }
            else if(element.is('.chosen-select')) {
                error.insertAfter(element.siblings('[class*="chosen-container"]:eq(0)'));
            }
            else error.insertAfter(element.parent());
        },

        submitHandler: function (form) {
        },
        invalidHandler: function (form) {
        }
    });
    
});