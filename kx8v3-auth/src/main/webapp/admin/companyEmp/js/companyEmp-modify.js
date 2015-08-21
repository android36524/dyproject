var empType = 3;
$(document).ready(function () {
	$.ajaxSetup({async:false});
	
	if(flageId==0){//0新增  1修改
		$("#btn_select_dept").removeAttr("disabled");
	   	if(_pOrgId!=null){
	       	$.get(path + "/admin/companyDetail/queryById?id="+_pOrgId,function(ret){
	               $("#companyName").val(ret.orgName);
	               $("#companyEmp_orgId").val(ret.id);
	           });
	       }
	   }else if(flageId==1){
		   if(empeId){
			   $.get(path + "/admin/companyEmp/queryById?id="+empeId,function(ret){
				   $("#companyEmp_sex"+ ret['sex']).prop("checked",true);
		            $("#companyName").val(ret.orgName);
		            $("#deptName").val(ret.deptName);
		            $("#btn_select_dept").removeAttr("disabled");
		            
		            $.each(ret,function(k,v){
		                $("#companyEmp-form #companyEmp_"+k).val(v);
		                if(k=="empNo"){
	                		$("#companyEmp_empNo").attr("readonly",true);
	                	}
		            });
		        });
		   }
	   }
	
	$.ajaxSetup({async:true});
	
	
	//选择所属部门
    $("#btn_select_dept").on(ace.click_event, function() {
        var orgId = $("#companyEmp_orgId").val();
    	var _params = {
    		"orgId" : orgId
    	};
    	
    	commonJs.showDeptDialog(_params,function(dept){
    		$("#companyEmp_deptId").val(dept.deptId);
			$("#deptName").val(dept.deptName);
    	});
        
    });
	
	//实时生成员工编号格式DY0001开始 和 姓名简拼
	   if(flageId==0){//0新增  新增时工号系统生成
	    	$('#companyEmp_name').bind('blur',function(){
	            var empeName = $('#companyEmp_name').val();
	            if(empeName!="" && empeName != null){
	                /*$('#companyEmp_empNo').val('');
	                $.ajaxSetup({async : false});
	                $.get(path + "/admin/empe/genEmpeCode",{empFlag:empFlag}, function(ret){
	                    if(ret){
	                        $('#companyEmp_empNo').val(ret);
	                    }
	                });*/
	                $('#companyEmp_nameSpell').val('');
	                $.post(path + "/admin/empe/pinyinGen",{empeName:empeName}, function(ret){
	                    if(ret){
	                        $('#companyEmp_nameSpell').val(ret);
	                    }
	                });
	                $.ajaxSetup({async : true});
	            }else{
	            	$('#companyEmp_nameSpell').val('');
	            	$('#companyEmp_empNo').val('');
	            }
	        });
	    }else{ //1修改 修改时工号不变
	    	$('#companyEmp_name').bind('blur',function(){
	            var empeName = $('#companyEmp_name').val();
	            if(empeName!="" && empeName != null){
	                /*$.ajaxSetup({async : false});
	                $('#companyEmp_nameSpell').val('');
	                $.post(path + "/admin/empe/pinyinGen",{empeName:empeName}, function(ret){
	                    if(ret){
	                        $('#companyEmp_nameSpell').val(ret);
	                    }
	                });
	                $.ajaxSetup({async : true});*/
	            }else{
	            	$('#companyEmp_nameSpell').val('');
	            	$('#companyEmp_empNo').val('');
	            }
	        });
	    }  
    
    
	   jQuery.validator.addMethod("checkEmpNo",function(value,element){
			var empNo = $("#companyEmp_empNo").val();//获取输入的工号
			var result = false;
			//设置为同步
			$.ajaxSetup({async:false});
			/*if(empNo.length<=2){
				result = false;
			}else{
				var ss = empNo.substr(0, 2);
				var result = /^[D][Y]*$/.test( ss );
				if(result==true){
					var aa = empNo.substr(2,empNo.length);
					if(aa.length>0){
						var result = /^[0-9]*$/.test( aa );
					}else{
						result = true; 
					}
				}
			}*/
			//var result = /^[D]{1}[Y]{1}[0-9]+$/.test(empNo);
			var result = /^[0-9a-zA-Z]{2,10}$/.test(empNo);
			$.ajaxSetup({async:true});
			return result;
		},'工号规则：英文或数字，长度2~10位之间；');



//表单验证
    $("#companyEmp-form").validate({
        errorElement: 'span',
        errorClass: 'help-inline',
        focusInvalid: false,
        rules : {
            "companyEmp.name" : {
                required: true,
                rangelength:[1,20]
            },
            "companyEmp.empNo":{
            	required: true,
            	checkEmpNo:true,
            	remote:{
                    url:path+"/admin/empe/vaidataEmpNo",
                    type:"post",
                    async:false,
                    dataType:"json",
                    data:{
                    	empNo:function(){
                            return $("#companyEmp_empNo").val();
                        },
                        orgId:function(){
                            return $("#companyEmp_orgId").val();
                        },
                        empType: function () {
                            return empType;
                        },
                        empeId:function(){
                        	return $("#companyEmp_id").val();
                        }
                    }
                }
            },
            "deptName" : {
                required: true
            },
            "companyEmp.deptId":{
            	required: true
            },
            "companyEmp.mobile" : {
                mobile: true
            },
            "companyEmp.job":{
            	rangelength:[1,20]
            }
        },
        messages : {

            "companyEmp.name" : {
                required : "请输入员工姓名"
            },
            "deptName" : {
                required : "请选择员工所属部门"
            },
            "companyEmp.deptId":{
            	required: "请选择员工所属部门"
            },
            "companyEmp.empNo":{
            	required: "请输入工号",
            	remote:"工号不允许重复"
            },
            "companyEmp.job" :{
                mobile : "输入的内容必须是移动电话号码(中国)格式."
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
