var empType=2;
var empe_modify = (function () {
    return{
          //初始化岗位
          initEmpe:function(code){
        	  empe_modify.removeSlect("empe_jobsCareers");
        	  $.ajaxSetup({'async':false});
              $.post(path+'/admin/schoolEmpe/getEmpeTypeByCode',{code:code},function(empeTypeList){
                  if(empeTypeList){
                      for (var i = 0; i < empeTypeList.length; i++) {
                          var empeObj = empeTypeList[i];
                          $("#empe_jobsCareers").append("<option value='" + empeObj.value + "'>" + empeObj.name + "</option>");
                      }
                  }
              });
              $.ajaxSetup({'async':true});
          },
        //删除select item
          removeSlect : function(selectId){
              var selectObject = $("#" + selectId + " option");
              selectObject.each(function(){
                  if( $(this).val() !== ""){
                      $(this).remove();
                  }
              });
          }
    };
})();
$(document).ready(function () {

    //加载已有的学校员工数据
	var id =$("#empe_id").val();
    var orgId = $("#orgId").val();
    if(id){
        $.get(path + "/admin/schoolEmpe/queryById?id="+id,function(ret){
        	$("#empe_sex"+ ret['sex']).prop("checked",true);
        	var empTypeValue = ret['empType'];
        	if(empTypeValue){
        		empe_modify.initEmpe(empTypeValue);
        	}
            $.each(ret,function(k,v){                	
                $("#empe_form #empe_"+k).val(v);
                if(k=="empNo"){
            		$("#empe_empNo").attr("readonly",true);
            	}
            });
            if($("#empe_image").val() != ""){
                var image = $("#empe_image").val();
                $("#imageUrl").attr("src",_imgpath+"/"+image);
            }
        });
    }

    //时间选择
    $('#empe_birthDate,#empe_teacherWorkAge,#empe_workAge,#empe_toSchYear').datepicker({
        language:'zh-CN',
        autoclose:true,
        pickTime: false,
        todayBtn: true,
        format:'yyyy-mm-dd hh:ii:ss'
    }).prev().on(ace.click_event, function(){
        $(this).next().focus();
    });


    //上传 选择课程图片
    $("#imageUpload").dyuploadify({
        buttonText : "<div>图片上传</div>",
        fileTypeExts:'*.jpg;*.jpeg;*.png',
        width:120,
        onUploadSuccess : function(file,data,res){            
            data=$.parseJSON(data);
            $("#imageUrl").attr("src",data.access);
            $("#empe_image").val(data.fileName);
        },
        onError:function(event,queueId,fileObj,errorObj){
            $.gritter.add({
                title: '图片上传',
                text: "图片上传失败，"+errorObj.info,
                sticky: false,
                time: ''
            });
        }
    });

  //选择所属部门
    $("#btn_update_dept").on(ace.click_event, function() {
        var orgId = $("#orgId").val();
    	var _params = {
    		"orgId" : orgId
    	};
    	
    	commonJs.showDeptDialog(_params,function(dept){
    		$("#empe_deptId").val(dept.deptId);
			$("#empe_deptName").val(dept.deptName);
    	});
    });


    //实时生成员工名称拼音
    $('#empe_name').change(function(){
        var empeName = $('#empe_name').val();
        if(empeName != null && empeName !=""){
            $('#empe_nameSpell').val('');
            $.post(path + "/admin/empe/pinyinGen",{empeName:empeName},function(ret){
                if(ret){
                    $('#empe_nameSpell').val(ret);
                }
            });
        }
    });

 	//显示账户信息
    $("#chk").click(function(){
    	if(document.getElementById("chk").checked){
        	$("#accountArea").show();
        }else{
        	$("#accountArea").hide();
        }
    });

    
  //岗位类型改变事件
    $("#empe_empType").change(function () {
    	 var code = $('#empe_empType').val();
    	 empe_modify.initEmpe(code);
    });
    

//表单验证
    $("#empe_form").validate({
        errorElement: 'span',
        errorClass: 'help-inline',
        focusInvalid: false,
        rules : {
            "empe.name" : {
                required: true,
                chinese : true,
                rangelength:[2,10]
            },
            "empe.nameSpel" : {
                required: true,
                maxlength:20
            },
            "empe.empNo" : {
                required: true,
                rangelength:[2,10],
                engNum : true,
                remote : {
                    url:path+"/admin/schoolEmpe/vaidataEmpNo",
                    type:"post",
                    async:false,
                    dataType:"json",
                    data:{
                    	empNo:function(){
                            return $("#empe_empNo").val();
                        },
                        orgId:function(){
                            return $("#orgId").val();
                        },
                        empeId: function () {
                            return $("#empe_id").val();
                        },
                        empType: function(){
                        	return empType;
                        }
                    }
                }
                
            },
            "empe.deptId" : {
                required: true
            },
            "deptName" : {
                required: true
            },
            "empe.telphone" : {
                tel: true,
                maxlength:20
            },
            "empe.mobile" : {
                mobile: true,
                required: true,
                maxlength:20,
                remote : {
                    url:path+"/admin/schoolEmpe/validateEmpeMobile",
                    type:"post",
                    async:false,
                    dataType:"json",
                    data:{
                    	mobile:function(){
                            return $("#empe_mobile").val();
                        },
                        orgId:function(){
                            return $("#orgId").val();
                        },
                        empeId: function () {
                            return $("#empe_id").val();
                        }
                    }
                }
            },
            "empe.zipCode" : {
                zipCode: true,
                maxlength:10
            },
            "empe.qq" : {
                qq: true,
                maxlength:20
            },
            "empe.idCard" : {
                idCard: true,
                maxlength:18
            },
            "empe.hometown" : {
                maxlength:30
            },
            "empe.graduateSchool" : {
                maxlength:100
            },
            "edu.remark" : {
                maxlength:2500
            },
            "empe.email" : {
                email:true,
                maxlength:30
            },
            "empe.homeAddr" : {
                maxlength:100
            },
            "empe.addr" : {
                maxlength:100
            },
            "empe.empType":{
            	required:true
            },
            "empe.jobsCareers":{
            	required:true
            }
        },
        messages : {

            "empe.name" : {
                required : "请输入员工姓名"
            },
            "empe.nameSpel" : {
                required : "请输入员工姓名拼音简拼"
            },
            "empe.empNo" : {
                required : "请输入员工编号",
                remote : "员工不允许重复"
            },
            "empe.deptId" : {
                required : "请输入员工所属部门"
            },
            "deptName" : {
                required : "请输入员工所属部门"
            },
            "empe.telphone" :{
                tel : "输入的内容必须是电话号码(中国)格式."
            },
            "empe.mobile" :{
                mobile : "输入的内容必须是移动电话号码(中国)格式.",
                remote : "同一个学校部门员工手机号码不允许重复",
                required : "请输入员工手机号码"
            },
            "empe.zipCode" :{
                zipCode : "输入的内容必须是邮政编码(中国)格式."
            },
            "empe.qq" : {
                qq: "输入的内容必须是正确 QQ 号码格式."
            },
            "empe.idCard" : {
                idCard: "输入的内容必须是合法的身份证号码(中国)格式."
            },
            "empe.email":{
                email:"请输入正确的邮箱地址."
            },
            "empe.empType":{
            	required:"请选择岗位类型"
            },
            "empe.jobsCareers":{
            	required:"请选择岗位职业"
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

