$(function(){
	
	$('#schBookVer_pressTime').datepicker({
		language : 'zh-CN',
		pickTime : false,
		todayBtn : true,
		autoclose : true,
		minView : '2',
		forceParse : false,
		format : "yyyy-mm-dd"
	}).next().on(ace.click_event, function(){
        $(this).prev().focus();
    });
	
	$.ajaxSetup({async:false}); 
	$.get(path + "/admin/press/findPressAll",function(pressList){
		if(pressList){
			var press_dom = $("#schBookVer_pressId");
			press_dom.empty();
			for (var i = 0; i < pressList.length; i++) {
				var pressObj = pressList[i];
				press_dom.append("<option value='"+pressObj.id+"'>"+pressObj.name+"</option>");
			}
		}
	});
	$.ajaxSetup({async:true}); 
	
	//加载已有的学阶数据
	if(_id){
		$.get(path + "/admin/schBookVer/findSchBookVerById?id="+_id,function(ret){
			$.each(ret,function(k,v){
				$("#schBookVer_form #schBookVer_"+k).val(v);
			});
		});
	}

	$('#schBookVer_seq').ace_spinner({
		value:1,
		min:1,
		max:200,
		step:1, 
		btn_up_class:'btn-info' ,
		btn_down_class:'btn-info'
	}).on('change', function(){
		
	});
	
	//验证教材版本名称是否存在
	jQuery.validator.addMethod("checkSchoolBookVerName",function(value,element){
		var _id = $("#schBookVer_id").val();//获取id的值 判断是新增还是修改
		var result = false;
		//设置为同步
		$.ajaxSetup({async:false});
		var param;
		if(_id == null || _id == ""){//新增
			param = {name:value};
		}else{//修改
			param = {name:value,id:_id};
		}
		$.post(path+"/admin/schBookVer/findSchoolBookVerByName",param,function(data){
			result = (1 == data.code);
		});
		$.ajaxSetup({async:true});
		return result;
	},'教材版本名称已存在');
	
	
	//表单验证
	$("#schBookVer_form").validate({
		errorElement: 'span',
		errorClass: 'help-inline',
		focusInvalid: false,
		rules : {
			"schBookVer.name" : {
				required: true,
				checkSchoolBookVerName:true,
				chsEngNum : true,
				rangelength : [3,10]
				
			},
			"schBookVer.seq" : {
				required: true,
				number:true,
				maxlength:11
			}
		},
		messages : {
			"schBookVer.name" : {
				required : "请输入出版社名称"
			},
			"schBookVer.seq" : {
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