
$(function(){
		if(!_id){
			$.post(path+"/admin/stage/getMaxCode",function(ret){
				$("#stage_code").val(ret.code);
			});
		}
		
		//validate expand
		jQuery.validator.addMethod("checkStageName",function(value,element){
			var result = false;
			//设置为同步
			$.ajaxSetup({async:false});
			var param;
			if(!_id){//新增
				param = {name:value};
			}else{//修改
				param = {name:value,id:_id};
			}
			$.post(path+"/admin/stage/findStageByName",param,function(data){
				result = (1 == data.code);
			});
			$.ajaxSetup({async:true});
			return result;
		},'学阶名称已存在');
	
		//修改时加载课程信息
		$.ajaxSetup({async:false});
		if(_id){
			$.get(path + "/admin/stage/findStageInfo?id="+_id,function(ret){
				$.each(ret,function(k,v){
					$("#stage_form #stage_"+k).val(v);
				});
			});
		}
		$.ajaxSetup({async:true});
		
		//表单验证
		$("#stage_form").validate({
			errorElement: 'span',
			errorClass: 'help-inline',
			focusInvalid: false,
			rules : {
				"stage.name" : {
					required: true,
					chinese: true,
					rangelength:[2,5],
					checkStageName:true
				},
				"stage.memo" : {
					rangelength:[0,85]
				},
				"stage.seq" : {
					required : true,
					notIsInteger:true
				}
			},
			messages : {
				"stage.seq":{
					required:"请输入值"
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