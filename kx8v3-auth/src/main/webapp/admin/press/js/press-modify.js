$(function(){
	//加载已有的学阶数据
	if(_id){
		$.get(path + "/admin/press/findPressById?id="+_id,function(ret){
			$.each(ret,function(k,v){
				$("#press_form #press_"+k).val(v);
			});
		});
	}
	
	
	jQuery.validator.addMethod("checkPressName",function(value,element){
		var result = false;
		//设置为同步
		$.ajaxSetup({async:false});
		var param;
		if(!_id){//新增
			param = {name:value};
		}else{//修改
			param = {name:value,id:_id};
		}
		$.post(path+"/admin/press/findPressByName",param,function(data){
			result = (1 == data.code);
		});
		$.ajaxSetup({async:true});
		return result;
	},'出版社名称已存在');
	
	
	$('#press_seq').ace_spinner({
		value:1,
		min:1,
		max:200,
		step:1, 
		btn_up_class:'btn-info' ,
		btn_down_class:'btn-info'
	}).on('change', function(){
		
	});
	
	//表单验证
	$("#press_form").validate({
		errorElement: 'span',
		errorClass: 'help-inline',
		focusInvalid: false,
		rules : {
			"press.name" : {
				required: true,
				chinese: true,
				rangelength:[2,12],
				checkPressName:true
				
			},
			"press.seq" : {
				required: true,
				number:true,
				maxlength:11
			},
			"press.remark":{
				rangelength:[0,255]
			}
		},
		messages : {
			"press.name" : {
				required : "请输入出版社名称",
				checkPressName:"出版社名称已存在"
			},
			"press.seq" : {
				required : "请输入值",
				number:"请输入合法的数字"
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