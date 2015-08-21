$(function(){
	$(".chosen-select").chosen();
	
	$('#grade_seq').ace_spinner({
		value:1,
		min:1,
		max:200,
		step:1, 
		btn_up_class:'btn-info' ,
		btn_down_class:'btn-info'
	}).on('change', function(){
		
	});
	//加载已有的学阶数据
	if(_id){
		$.get(path + "/admin/grade/findGradeById?id="+_id,function(ret){
			$.each(ret,function(k,v){
				$("#grade_form #grade_"+k).val(v);
			});
			$("#grade_code_hidden").val(ret.code);
			$("#grade_name_hidden").val(ret.name);
		});
		$("#grade_code").attr("readonly",true);
	}else{
		if($("#grade_gradeId")){
			$("#grade_gradeId").change(function(){				
				$("#grade_name").val($("#grade_gradeId option:selected").attr("stageName"));	
			});
			$("#grade_gradeId").change();
		}
	}
	
	//验证教材版本名称是否存在
	jQuery.validator.addMethod("checkChooseSubjectOrNot",function(){
		var subjectName = $("#selectSubjectId").val();
		//alert("科目ID："+subjectName);
		var result = false;
		if(subjectName!=""||subjectName!=null){
			result = true;
					$("#grade_form").closest('.form-group').removeClass('has-error').addClass('has-info');
		}
		
		return result;
	},'科目不能为空');
	
	//验证年级名称是否存在
	jQuery.validator.addMethod("checkGradeName",function(value,element){
		
		var grade_name_hidden = $("#grade_name_hidden").val();
		if(grade_name_hidden==value){
			return true;
		}else{
			var result = false;
			//设置为同步
			$.ajaxSetup({async:false});
			var param = {name:value,id:_id,schId:_schId};
			$.post(path+"/admin/grade/findGradeByName",param,function(data){
				result = (1 == data.code);
			});
			$.ajaxSetup({async:true});
			return result;
		}
	},'年级名称已存在');
	
	jQuery.validator.addMethod("checkGradeCode",function(value,element){
		var grade_code_hidden = $("#grade_code_hidden").val();
		if(grade_code_hidden==value){
			return true;
		}else{
			var result = false;
			//设置同步
			$.ajaxSetup({async:false});
			var param = {code:value,id:_id};
			$.post(path+"/admin/grade/findGradeByCode",param,function(data){
				result = (1 == data.code);
			});
			$.ajaxSetup({async:true});
			return result;
		}
	},'年级编码重复');
	
	//表单验证
	$("#grade_form").validate({
		errorElement: 'span',
		errorClass: 'help-inline',
		focusInvalid: false,
		rules : {
			"grade.name" : {
				required: true,
				chinese : true,
				rangelength : [2,8],
				checkGradeName : true
			},
			"grade.code" : {
				required: true,
				maxlength:10,
				checkGradeCode : true
			},
			"grade.seq" : {
				required: true,
				number:true,
				maxlength:11
			},
			"subjectId":{
				required:true,
				//checkChooseSubjectOrNot:true
			},
			"grade.remark" : {
				maxlength:200
			}
		},
		messages : {
			"grade.name" : {
				required : "请输入年级名称",
				maxlength:"请输入不超过20个字符",
				minlength:"请至少输入两个字符"
			},
			"grade.code" : {
				required : "请输入年级编码",
				maxlength:"请输入不超过10个字符"
			},
			"grade.seq" : {
				required : "请输入排序值",
				number:"请输入合法的数字"
			},
			"subjectId":{
				required:"科目不能为空"
			},
			"grade.remark" : {
				maxlength:"请输入不超过200个字符"
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