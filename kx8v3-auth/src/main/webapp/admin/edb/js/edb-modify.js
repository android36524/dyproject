var edb_modify = (function () {
    return{
          //初始化省
          initProvince:function(){
              $.post(path+'/admin/division/provinceList',function(retList){
                  if(retList){
                      for (var i = 0; i < retList.length; i++) {
                          var divisionObj = retList[i];
                          $("#edb_provinceCode").append("<option value='" + divisionObj.BM + "'>" + divisionObj.MC + "</option>");
                      }
                  }
              });
          },
          //根据省初始化市
          initCityOrAreaByBmId: function (bmId,id) {
              if(id == "edb_cityCode"){
                  edb_modify.removeSlect("edb_cityCode")
                  edb_modify.removeSlect("edb_areaCode")
              }else{
                  edb_modify.removeSlect(id);
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
            $("#edb_orgId").val(idData);
            $("#parentOrgName").val(nameData);
        }
    };
})();



$(document).ready(function () {
    
    
    $.ajaxSetup({async:false});
    //初始化所有省
    edb_modify.initProvince();
    //加载已有教育局基本信息
    if(_edbId){//修改
    	document.getElementById("accoutDiv").style.display='none';
    	document.getElementById("roleDiv").style.display='none';
    	document.getElementById("btn_select_manager").style.display='none';
        $.get(path + "/admin/edb/queryById?id="+_edbId,function(ret){
        	document.getElementById("edb_provinceCode").disabled=true;
        	document.getElementById("edb_cityCode").disabled=true;
        	document.getElementById("edb_areaCode").disabled=true;
            $("#parentOrgName").val(ret.parentOrg);
            $("#idid").val(ret.id);
            $("#areaCodeId").val(ret.areaCode);
            $("#edb_orgId1").val(ret.orgId);
            $("#btn_select_manager").removeAttr("disabled");
            edb_modify.initCityOrAreaByBmId(ret.provinceCode,"edb_cityCode");
            if(ret.cityCode){
                edb_modify.initCityOrAreaByBmId(ret.cityCode,"edb_areaCode");
            }
            $.each(ret,function(k,v){
                $("#edb-form #edb_"+k).val(v);
            });
            
            $("#edb_provinceCode_hidden").val(ret.provinceCode);
            $("#edb_cityCode_hidden").val(ret.cityCode);
            $("#edb_areaCode_hidden").val(ret.areaCode);
        });
    }else{//新增
        if(_orgLevel == _provinceLevel){
            document.getElementById("edb_provinceCode").disabled=true;
            document.getElementById("edb_areaCode").disabled=true;
        }else if(_orgLevel == _cityLevel){
            document.getElementById("edb_provinceCode").disabled=true;
            document.getElementById("edb_cityCode").disabled=true;
        }else if(_orgLevel == _arealLevel){
            document.getElementById("edb_areaCode").disabled=true;
            document.getElementById("edb_cityCode").disabled=true;
            document.getElementById("edb_provinceCode").disabled=true;
        }else{
            document.getElementById("edb_areaCode").disabled=true;
            document.getElementById("edb_cityCode").disabled=true;
        }
        if(_pOrgId != null){
            $.get(path + "/admin/edb/queryById?id="+_pOrgId,function(ret){
                $("#parentOrgName").val(ret.orgName);
                $("#edb_orgId").val(ret.id);
                $("#edb_provinceCode").val(ret.provinceCode);
                $("#edb_level").val(_orgLevel);
                edb_modify.initCityOrAreaByBmId(ret.provinceCode,"edb_cityCode");
                edb_modify.initCityOrAreaByBmId(ret.cityCode,"edb_areaCode");
                $("#edb_cityCode").val(ret.cityCode);
                $("#edb_areaCode").val(ret.areaCode);
            });
        }
    }
    $.ajaxSetup({async:true});
    
    //省改变事件
    $("#edb_provinceCode").change(function () {
        var provinceId = $(this).val();
        $("#edb_provinceCode_hidden").val(provinceId);
        if(provinceId != ""){
            $("#btn_select_manager").removeAttr("disabled");
        }else{
            $("#btn_select_manager").attr("disabled","disabled");
        }
        edb_modify.initCityOrAreaByBmId(provinceId,"edb_cityCode");
    });
    
    //市改变事件
    $("#edb_cityCode").change(function () {
       var cityId = $(this).val();
       $("#edb_cityCode_hidden").val(cityId);
        edb_modify.initCityOrAreaByBmId(cityId,"edb_areaCode");
    });
    //var areaCityId ;
    $("#edb_areaCode").change(function () {
    	var areaCityId = $(this).val();
         //alert("区："+cityId);
         $("#edb_areaCode_hidden").val(areaCityId);
     });
    
    //选择上级机构按钮单击事件
    $("#btn_select_manager").on(ace.click_event, function() {
        var params = {};
        var provinceCode = $("#edb_provinceCode").val();
        var itSelf = $("#edb_orgId1").val();
        var idid = $("#idid").val();
        //alert("====="+cityId);
        params.key = "province";
        params.value = provinceCode;
        var cityCode = $("#edb_cityCode").val();
        if(cityCode != ""){
            params.key = "city";
        }
        var areaCode = $("#edb_areaCode").val();
        if(areaCode != ""){
            params.key = "area";
            params.value = cityCode;
        }
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
            iframe: {src: path + "/admin/edb/edb-manager.jsp?flag=" +params.key + "&code=" + params.value+"&itSelf="+itSelf+"&idid="+idid},
            yes : function(index){
                var rowData = JSON.parse(layer.getChildFrame("#selected",index).val());
                $("#parentOrgName").val(rowData.orgName);
                $("#edb_orgId").val(rowData.id);
                //关闭弹窗
                layer.close(index);
            }
        });
    });
    
    //表单验证
    $("#edb-form").validate({
        errorElement: 'span',
        errorClass: 'help-inline',
        focusInvalid: false,
        rules : {
            "edb.orgName" : {
                required: true,
                chinese:true,
                minlength: 4,
                maxlength:50,
                remote : {
                    url:path+"/admin/edb/validateEdbName",
                    type:"post",
                    async:false,
                    dataType:"json",
                    data:{
                        edbName:function(){
                            return $("#edb_orgName").val();
                        },
                        edbId: function () {
                            return $("#edb_id").val();
                        }
                    }
                }
            },
            "edb_provinceCode" : {
                required : true
            },
            "edb.chargePhone" : {
                telOrMobile : true
            },
            "edb.zipCode" : {
                zipCode : true
            },
            "edb.parentOrgName" : {
                required : true
            },
            "edb.charge":{
            	maxlength:20
            },
            "edb.officeAddr":{
            	maxlength:150
            }
        },
        messages : {
            "edb.orgName" : {
                required : "请输入教育局名称",
                minlength : "教育局名称不能少于四个字",
                maxlength:"教育局名称不超过50字",
                remote : "教育局名称不允许重复"
            },
            "edb_provinceCode" : {
                required : "请选择教育局所属省"
            },
            "edb.parentOrgName" : {
                required : "请选择上级机构"
            },
            "edb.chargePhone" : {
                telOrMobile : "请输入正确的电话号码(中国)或移动电话号码(中国)"
            },
            "edb.charge":{
            	maxlength:"不超过20字符"
            },
            "edb.officeAddr":{
            	maxlength:"不超过150个字符"
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
