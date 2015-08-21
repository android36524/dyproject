$(function(){
	//上传excel
	$("#courseUpload").dyuploadify({
		buttonText : "<div>选择excel文件</div>",
		fileTypeExts:'*.xlsx',
		width:120,
		onUploadSuccess : function(file,data,res){
			data = $.parseJSON(data);
			$("#excelName").val(data.originalFileName);
			$("#excelFileName").val(data.fileName);
		},
		onError:function(event,queueId,fileObj,errorObj){
			$.gritter.add({
				title: '文件上传',
				text: "文件上传失败，"+errorObj.info,
				sticky: false,
				time: ''
			});
		}
	});

	//表单验证
	$("#excel_form").validate({
		errorElement: 'span',
		errorClass: 'help-inline',
		focusInvalid: false,
		rules : {
			"excelName":{
				required:true
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