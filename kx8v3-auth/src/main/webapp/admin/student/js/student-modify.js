/**
 * 学生管理修改或新增JS
 */
$(document).ready(function(){
	   var id =_id;
	    if(id){
	    	//如果为修改页面，则隐藏账号信息
	    	$("#account_info").hide();
	    	//加载修改页面表单数据
	        $.get(path + "/admin/student/queryById?id="+id,function(ret){
	        	$("#student_sex"+ ret['sex']).prop("checked",true);
	        	$("#student_isReside"+ ret['isReside']).prop("checked",true);
	        	$("#student_migrant"+ ret['migrant']).prop("checked",true);
	        	$("#student_soldier"+ ret['soldier']).prop("checked",true);
	        	$("#student_teacher"+ ret['teacher']).prop("checked",true);
	        	$("#student_singleParent"+ ret['singleParent']).prop("checked",true);
	        	$("#student_onlySon"+ ret['onlySon']).prop("checked",true);
	        	$("#student_poor"+ ret['poor']).prop("checked",true);
	                $.each(ret,function(k,v){
                    $("#student_form #student_"+k).val(v);
                    
	             });
	            if($("#student_image").val() != ""){
	                var image = $("#student_image").val();
	                $("#imageUrl").attr("src",_imgpath+"/"+image);
	            }
	        });
	    }
    // 获取姓名简拼
	$("#student_name").change(function(){
		$.post(path + "/common/getPinyin",{name:$("#student_name").val()},function(r){
			if(r){
				$("#student_nameSpell").val(r);
			}
		});			
	});	
	
    
  //表单验证
    $("#student_form").validate({
        errorElement: 'span',
        errorClass: 'help-inline',
        focusInvalid: false,
        rules : {
            "student.name" : {
            	required : true,
				chinese : true,
				rangelength:[2,10]
            },
            "student.nameSpell" : {
                required: true,
                maxlength:20
            },
            "student.gradeId" : {
                required: true
            },
            "student.classId" : {
                required: true
            },
            "student.studentNo" : {
                required: true,
                maxlength:100
            },
            "student.status" : {
                required: true
            },
            "student.birthDate" : {
                required: true,
                date:true
            },
            "student.sex" : {
                required: true
            },
            "stuInfo.ziplCode" : {
            	 zipCode: true,
                 maxlength:10
            },
            "student.telephone" : {
                telOrMobile: true,
                maxlength:20
            },
            "student.idCard" : {
                idCard: true,
                maxlength:18
            },
            "student.hometown" :{
            	maxlength:30
            },
            "student.birthPlace" :{
            	maxlength:200
            },
            "student.originalSchool" :{
            	maxlength:100
            },
            "student.enrolScore" :{
            	numeric : true,
            	maxlength:100
            },
            "student.rollCode" :{
            	maxlength:100
            },
            "student.homeAddr" :{
            	maxlength:100
            },
            "stuInfo.postalAddr" :{
            	maxlength:200
            },
            "stuInfo.householdLocation" :{
            	maxlength:200
            }
        },
        messages : {
            "student.name" : {
                required : "请输入学生姓名"
            },
            "student.nameSpell" : {
                required : "请输入学生姓名拼音简拼"
            },
            "student.gradeId" : {
                required : "请选择年级"
            },
            "student.classId" : {
                required : "请选择班级"
            },
            "student.studentNo" : {
                required : "请输入学号",
                maxlength : "学号长度不能超过100"
            },
            "student.status" : {
                required : "学生状态不能为空"
            },
            "student.birthDate" : {
                required : "请输入合法的日期"
            },
            "student.sex" : {
                required : "请选择性别"
            },
            "stuInfo.ziplCode" : {
            	ziplCode : "输入的内容必须是邮政编码(中国)格式."
            },
            "student.telephone" :{
                telOrMobile : "请输入正确的电话号码(中国)或移动电话号码(中国)"
            },
            "student.idCard" : {
                idCard: "输入的内容必须是合法的身份证号码(中国)格式."
            },
            "student.hometown" :{
            	maxlength:"籍贯长度不能超过30"
            },
            "student.birthPlace" :{
            	maxlength:"出生地长度不能超过200"
            },
            "student.originalSchool" :{
            	maxlength:"来源校(园)长度不能超过100"
            },
            "student.enrolScore" :{
            	numeric : "入学成绩只能是数字",
            	maxlength:"入学成绩长度不能超过100"
            },
            "student.rollCode" :{
            	maxlength:"学籍号长度不能超过100"
            },
            "student.homeAddr" :{
            	maxlength:"家庭住址长度不能超过100"
            },
            "stuInfo.postalAddr" :{
            	maxlength:"通信地址长度不能超过200"
            },
            "stuInfo.householdLocation" :{
            	maxlength:"户口所在地长度不能超过200"
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

//根据年级初始化班级
$("#student_gradeId").change(function () {
	$("#student_classId").empty();
	$("#student_classId").prepend("<option value=''>请选择</option>");
      var gradeId = $(this).val();
      if(gradeId != "") {
          	$.post(path+"/admin/class/findClassByGradeId?gradeId="+gradeId,function(ret){
  				if(ret){
  					for(var i=0;i<ret.length;i++){
  						var obj = ret[i];
  						$("#student_classId").append("<option value="+obj.id+">"+obj.name+"</option>");
  					}
  				}
  			});
      }
  });

//时间选择
$('#student_birthDate,#student_enrolTime').datepicker({
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
    fileTypeExts:'*.jpg;*.jpeg;*.png;*.gif',
    width:120,
    onUploadSuccess : function(file,data,res){
        data=$.parseJSON(data);
        $("#imageUrl").attr("src",data.access);
        $("#student_image").val(data.fileName);
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

/**
 * 入学成绩校验
 * @param obj
 */
function clearNoNumOfAlert(obj){   // 值允许输入一个小数点和数字
	  obj.value = obj.value.replace(/[^\d.]/g,"");     //先把非数字的都替换掉，除了数字和.
	  obj.value = obj.value.replace(/^\./g,"");         //必须保证第一个为数字而不是.
	  obj.value = obj.value.replace(/\.{2,}/g,".");   //保证只有出现一个.而没有多个.
	  obj.value = obj.value.replace(".","$#$").replace(/\./g,"").replace("$#$",".");    //保证.只出现一次，而不能出现两次以上
	}

