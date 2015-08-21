$(function() {
	//表单验证
	$("#class_form").validate(
					{
						errorElement : 'span',
						errorClass : 'help-inline',
						focusInvalid : false,
						rules : {
							"class.gradeId" : {
								required : true
							},
							"class.name" : {
								required : true,
								chsEngNum:true,
								minlength : 3
							},
							"class.buildDate" : {
								required : true
							},
							"class.remark" : {
								maxlength : 200
							}

						},
						messages : {
							"class.gradeId" : {
								required : "请选择年级名称"
							},
							"class.name" : {
								required : "请输入班级名称",
								minlength : "班级名称不能少于三个字"
							},
							"class.buildDate" : {
								required : "请选择建班年月"
							},
							"class.remark" : {
								maxlength : "已超过200个字符"
							}
						},
						invalidHandler : function(event, validator) { //display error alert on form submit
							$('.alert-danger', $('.login-form')).show();
						},

						highlight : function(e) {
							$(e).closest('.form-group').removeClass('has-info')
									.addClass('has-error');
						},

						success : function(e) {
							$(e).closest('.form-group')
									.removeClass('has-error').addClass(
											'has-info');
							$(e).remove();
						},

						errorPlacement : function(error, element) {
							if (element.is(':checkbox') || element.is(':radio')) {
								var controls = element
										.closest('div[class*="col-"]');
								if (controls.find(':checkbox,:radio').length > 1)
									controls.append(error);
								else
									error.insertAfter(element.nextAll(
											'.lbl:eq(0)').eq(0));
							} else if (element.is('.select2')) {
								error
										.insertAfter(element
												.siblings('[class*="select2-container"]:eq(0)'));
							} else if (element.is('.chosen-select')) {
								error
										.insertAfter(element
												.siblings('[class*="chosen-container"]:eq(0)'));
							} else
								error.insertAfter(element.parent());
						},

						submitHandler : function(form) {
						},
						invalidHandler : function(form) {
						}
		});

	$('#class_buildDate').datepicker({
		language : 'zh-CN',
		autoclose : true,
		pickTime : false,
		todayBtn : true,
		format : 'yyyy-mm-dd hh:ii:ss'
	}).prev().on(ace.click_event, function() {
		$(this).next().focus();
	});

	$('#class_seq').ace_spinner({
		value : 1,
		min : 1,
		max : 999,
		step : 1,
		icon_up : 'icon-plus',
		icon_down : 'icon-minus',
		btn_up_class : 'btn-success',
		btn_down_class : 'btn-danger'
	});
});

//选择班主任
$("#btn_select_hTeacher").on(
		ace.click_event,
		function() {
			$.layer({
				type : 1,
				title : "班主任选择",
				closeBtn : [ 0, true ],
				shade : [ 0.8, '#000' ],
				btns : 2,
				btn : [ '选择带回', '取消' ],
				border : [ 0 ],
				area : [ '800px', '500px' ],
				 page: {
			        	url: path + "/admin/common/teacherChoose/show_teacher.jsp?schId="+_schId, 
			        	ok: function(datas){}
		        },
				yes : function(index) {
					var rowData = JSON.parse($("#selected").val());
					$("#class_headTeacher").val(rowData.name);
					$("#class_classHeadId").val(rowData.id);
					layer.close(index);
				}
			});
		});

var _classId = _id;
initModifyPage(_classId);
//初始化班级列表。
function initModifyPage(_classId) {
	if (_classId) {
		$.get(path + "/admin/class/queryClassById?_classId=" + _classId,
				function(ret) {
					if (ret) {
						$.each(ret, function(k, v) {
							$("#class_form #class_" + k).val(v);
						});
					}
				});
		$("#class_form #class_gradeId").attr("disabled", "disabled");
	}
}