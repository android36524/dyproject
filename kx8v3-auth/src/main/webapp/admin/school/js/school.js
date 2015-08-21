$(function(){
	var schoolLevel = _schoolLevel;//层级编码 学校
	$("#school_level").val(schoolLevel);
	
	$('#schoolExt_buildSchoolTime').datepicker({
		language:'zh-CN',
		autoclose:true,
		pickTime: false,
		todayBtn: true,
		format:'yyyy-mm-dd hh:ii:ss'
	}).prev().on(ace.click_event, function(){
		$(this).next().focus();			
	});
	
	//切换省份，获取城市列表
	$('#school_provinceCode').change(function(){

		$('#school_cityCode').empty().append('<option value="">-请选择-</option>');
		$('#school_areaCode').empty().append('<option value="">-请选择-</option>');
		
		var provinceId = $(this).val();
		if(provinceId){
			$.ajaxSetup({async : false});  
			$.get(path + "/admin/school/changeProvince?provinceId="+provinceId, function(ret){
				if(ret){
					$.each(ret,function(name, value){
						var cityStr = "<option value="+value.BM+">"+value.MC+"</option>";
						$('#school_cityCode').append(cityStr);
					});
				}
			});
			$.ajaxSetup({async : true});
		}
	});
	
	//获取学校名称拼音简称
	$("#school_orgName").change(function(){
		$.post(path + "/common/getPinyin",{name:$("#school_orgName").val()},function(r){
			if(r){
				$("#school_orgNameSpell").val(r);
			}
		});			
	});	
	
	//切换城市，获取县区列表
	$('#school_cityCode').change(function(){
		
		$('#school_areaCode').empty().append('<option value="">-请选择-</option>');
		
		var cityId = $(this).val();
		if(cityId){
			$.ajaxSetup({async : false});
			$.get(path + "/admin/school/changeCity?cityId="+cityId, function(ret){
				if(ret){
					$.each(ret,function(name, value){
						var cityStr = "<option value="+value.BM+">"+value.MC+"</option>";
						$('#school_areaCode').append(cityStr);
					});
				}
			});
			$.ajaxSetup({async : true});
		}
	});
	
	//根据所属教育局机构，自动生成学校编码
		
	$('#show_orgCode').val('');
	$('#school_orgCode').val('');
	
	var orgId = $('#orgId').val();
	if(orgId){
		$.get(path + "/admin/school/genSchoolCode?orgId="+orgId, function(ret){
			if(ret){
				$('#show_orgCode').val(ret);
				$('#school_orgCode').val(ret);
			}
		});	
	}
			

	//表单验证
	$("#school_form").validate({
		errorElement: 'span',
		errorClass: 'help-inline',
		focusInvalid: false,
		rules : {
			"school.orgId" : {
				required: true
			},
			"school.orgName" : {
				required: true,
				chinese : true,
				minlength : 4,
				remote : {
					url:path+"/admin/school/validateSchoolName",
					type:"post",
					 async:false,
	                    dataType:"json",
	                    data:{
	                        schoolName:function(){
	                            return $("#school_orgName").val();
	                        },
	                        schoolId: function () {
	                            return $("#school_id").val();
	                        }
	                    }
				}
			},
			"schoolExt.schoolCategory" : {
				required: true
			},
			"school.officeAddr" : {
				required: true
			},
			"school.provinceCode" : {
				required: true
			},
			"school.cityCode" : {
				required: true
			},
			"school.areaCode" : {
				required: true
			},
			"schoolExt.schoolPhoneNumber" : {
                tel : true
            },
            "schoolExt.email" : {
            	email : true
            },
            "schoolExt.fax" : {
            	fax : true
            },
            "school.zipCode" : {
            	zipCode : true
            },
            "schoolExt.homeUrl" : {
            	url : true
            },
            "schoolExt.length" : {
            	digits : true,
            	min : 1
            },
            "schoolExt.schProperty" : {
            	required: true
            }
		},
		messages : {

			"school.orgId" : {
				required : "请选择上级主管行政机构"
			},
			"school.orgName" :{
				required : "请输入学校名",
				minlength : "学校名称不能少于四个字",
				remote : "学校名称不允许重复"
			},
			"schoolExt.schoolCategory" :{
				required : "请选择学校类别"
			},
			"school.officeAddr" :{
				required : "请输入学校地址"
			},
			"school.provinceCode" :{
				required : "请选择学校所属省"
			},
			"school.cityCode" :{
				required : "请选择学校所属市"
			},
			"school.areaCode" :{
				required : "请选择学校所属区"
			},
			"schoolExt.schoolPhoneNumber" : {
                tel : "请输入正确的电话号码(中国)"
            },
            "schoolExt.email" : {
            	email : "请输入正确的电子邮箱"
            },
            "schoolExt.fax" : {
            	fax : "请输入正确的传真"
            },
            "school.zipCode" : {
            	zipCode : "请输入正确的邮政编码"
            },
            "schoolExt.homeUrl" : {
            	url : "请输入正确的网址"
            },
            "schoolExt.length" : {
            	digits : "请输入不小于1的正整数",
            	min : "请输入不小于1的正整数"
            },
            "schoolExt.schProperty" : {
            	required: "请选择学校办别"
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


function initModifyPage(_schoolId, provinceCode, cityCode, areaCode){
	if(_schoolId){
		document.getElementById("accountDiv").style.display='none';
    	document.getElementById("roleDiv").style.display='none';
		$.get(path + "/admin/school/querySchoolById?_schoolId=" +_schoolId,function(ret){
			if(ret){
				var curCity = '';
				var curArea = '';
				$.each(ret,function(k,v){
					$("#school_form #school_"+k).val(v);
					
					if('cityCode' == k){
						curCity = v;
					}
					if('areaCode' == k){
						curArea = v;
					}
				});
				
				//给学校编码单独赋值
				$('#show_orgCode').val($('#school_orgCode').val());
				
				//触发省份和城市的change事件
				$('#school_provinceCode').change();
				$("#school_cityCode").val(curCity);
				
				$('#school_cityCode').change();
				$("#school_areaCode").val(curArea);
				
			}
		});
		
		$.get(path + "/admin/school/querySchoolExtById?_schoolId=" +_schoolId,function(ret){
			if(ret){
				$.each(ret,function(k,v){
					$("#school_form #schoolExt_"+k).val(v);
				});
			}
		});
	}else{
		//新增时初始化省市区
		if(provinceCode){
			$("#school_provinceCode").val(provinceCode);
			$("#provinceId").val(provinceCode);
			$("#school_provinceCode").change();
			$("#school_provinceCode").attr("disabled","disabled");
		}
		if(cityCode){
			$("#school_cityCode").val(cityCode);
			$("#cityId").val(cityCode);
			$("#school_cityCode").change();
			$("#school_cityCode").attr("disabled","disabled");
		}
		if(areaCode){
			$("#school_areaCode").val(areaCode);
			$("#areaId").val(areaCode);
			$("#school_areaCode").attr("disabled","disabled");
		}
	}
}