var edb_modify = (function () {
    return{
          //初始化学阶
          initStage:function(){
              $.post(path+'/admin/stage/findStageAll',function(stageList){
                  if(stageList){
                      for (var i = 0; i < stageList.length; i++) {
                          var stageObj = stageList[i];
                          $("#schoolBook_stageId").append("<option value='" + stageObj.id + "'>" + stageObj.name + "</option>");
                          
                      }
                  }
              });
          },
          //根据学阶查询年级
          initGrade: function (stageId) {
        	  
        	  edb_modify.removeSlect("schoolBook_gradeId");
        	  edb_modify.removeSlect("schoolBook_subjectId");
        	  
              $.post(path+'/admin/grade/findGradeByStage',{stageId:stageId},function(gradeList){
                  if(gradeList){
                	  var grade_dom = $("#schoolBook_gradeId");
                      for (var i = 0; i < gradeList.length; i++) {
                          var gradeObj = gradeList[i];
                          grade_dom.append("<option value='" + gradeObj.id + "'>" + gradeObj.name + "</option>");
                      }
                  }
              }); 
          },
          
          //根据年级查询科目
          initSubject: function (gradeId) {
        	  edb_modify.removeSlect("schoolBook_subjectId");
        	  
        	  $.post(path+'/admin/subject/findSubjectByGrade',{gradeId:gradeId},function(subjectList){
        		  if(subjectList){
        			  var subject_dom = $("#schoolBook_subjectId");
        			  for (var i = 0; i < subjectList.length; i++) {
        				  var subjectObj = subjectList[i];
        				  subject_dom.append("<option value='" + subjectObj.id + "'>" + subjectObj.name + "</option>");
        			  }
        		  }
        	  }); 
          },
        //删除select item
        removeSlect : function(selectId){
            var selectObject = $("#" + selectId + " option");
            selectObject.each(function(){
                if( $(this).val() !== ""){
                    $(this).remove();
                }
            });
        },
    };
})();


$(document).ready(function(){
	
	$.ajaxSetup({async:false}); 
	//初始化所学阶
    edb_modify.initStage();
	
	$.get(path + "/admin/schBookVer/findSchBookVerAll",function(pressList){
		if(pressList){
			var press_dom = $("#schoolBook_schBookVerId");
			press_dom.empty();
			for (var i = 0; i < pressList.length; i++) {
				var pressObj = pressList[i];
				press_dom.append("<option value='"+pressObj.id+"'>"+pressObj.name+"</option>");
			}
		}
	});
	
	 $.ajaxSetup({async:true});
	 
 	 //学阶改变事件
     $("#schoolBook_stageId").change(function () {
        var stageId = $(this).val();
        if(stageId==""){
        	 $("#schoolBook_gradeId option[index!='0']").remove();
	   		 $("#schoolBook_subjectId option[index!='0']").remove();
	   		 $("#schoolBook_gradeId").prepend("<option selected='selected' value=''>---请选择---</option>");
	   		 $("#schoolBook_subjectId").prepend("<option selected='selected' value=''>---请选择---</option>");
        }else{
        	 edb_modify.initGrade(stageId,"schoolBook_subjectId");
        }
     });
    
     //年级改变事件
     $("#schoolBook_gradeId").change(function () {
       var gradeId = $(this).val();
       if(gradeId==""){
			$("#schoolBook_subjectId option[index!='0']").remove();
			$("#schoolBook_subjectId").prepend("<option selected='selected' value=''>---请选择---</option>");
       }else{
    	    edb_modify.initSubject(gradeId,"schoolBook_subjectId");
       }
     });
	
	//加载教材
	if(_id){
		$.get(path + "/admin/schoolBook/findSchoolBookById?id="+_id,function(ret){
			edb_modify.initGrade(ret.stageId,"schoolBook_gradeId");
			edb_modify.initSubject(ret.gradeId,"schoolBook_subjectId");
			$.each(ret,function(k,v){
				$("#schoolBook_form #schoolBook_"+k).val(v);
			});
			if($("#schoolBook_image").val() != ""){
				var image = $("#schoolBook_image").val();
                $("#imageUrl").attr("src",_imgpath+"/"+image);
            }
		});
	}
	$.ajaxSetup({async:false});
	
	//上传 选择课程图片
    $("#imageUpload").dyuploadify({
        buttonText : "<div>图片上传</div>",
        fileTypeExts:'*.jpg;*.jpeg;*.png',
        width:120,
        onUploadSuccess : function(file,data,res){
            data=$.parseJSON(data);           
            $("#imageUrl").attr("src",data.access);
            $("#schoolBook_image").val(data.fileName);
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
	
	//表单验证
	$("#schoolBook_form").validate({
		errorElement: 'span',
		errorClass: 'help-inline',
		focusInvalid: false,
		rules : {
			"schoolBook.schBookVerId" : {
				required: true,
				
			},
			"schoolBook.stageId" : {
				required: true,
			},
			"schoolBook.gradeId" : {
				required: true,
				
			},
			"schoolBook.subjectId" : {
				required: true,
				
			}
		},
		messages : {
			"schoolBook.schBookVerId" : {
				required: "请选择教材版本",
				
			},
			"schoolBook.stageId" : {
				required: "请选择所属学阶",
			},
			"schoolBook.gradeId" : {
				required: "请选择所属年级",
				
			},
			"schoolBook.subjectId" : {
				required: "请选择所属科目",
				
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