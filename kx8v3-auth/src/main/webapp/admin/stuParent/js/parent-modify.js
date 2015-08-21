/**
 * 学生家长管理修改或新增JS
 */
var dyChooseStu = (function(){

    return {

        /**
         * 创建Grid
         */
        createGrid : function(){
            var grid_selector = "#chooseStu-table";
            jQuery(grid_selector).jqGrid({
                datatype: "local",
                height: 150,
                colNames:['序号','学校ID','年级及班级','学生姓名', '关系', '是否监护人','操作','关系','学生ID','监护人val'],
                colModel:[
                    {name:'id',index:'id',width:150, sortable:false},
                    {name:'schId',index:'schId', hidden:true},
                    {name:'className',index:'className', width:160, sortable:false},
                    {name:'studentName',index:'studentName', width:160, sortable:false},
                    {name:'relationTypeName',index:'relationTypeName', width:160, sortable:false},
                    {name:'guardianName',index:'guardianName', width:150, sortable:false},
                    {name:'diyId',index:'diyId', width:160, sortable:false,formatter : function(j,options,rowObject){
                    	return '<div class="visible-md visible-lg hidden-sm hidden-xs btn-group">' +

                        '<button type="button" class="btn btn-xs btn-danger tooltip-error" dyid="managedel-student" rowId="'+j+'" studentId="'+rowObject.studentId+'" parentId="'+rowObject.parentId+'"  data-rel="tooltip" data-placement="top" title="移除">' +
                        '<i class="icon-trash bigger-120"></i></button>' +

                        '</div>';
                    }},
                    {name:'relationType',index:'relationType',hidden:true},
                    {name:'studentId',index:'studentId',hidden:true},
                    {name:'guardian',index:'guardian',hidden:true}
                    
                ],
                viewrecords : true,
				altRows: true,
				multiselect: false,
		        multiboxonly: true,
				loadComplete : function() {
					var table = this;
					setTimeout(function(){
						dyjsCommon.enableTooltips(table);
						dyjsCommon.updatePagerIcons(table);
						$('button[menuid]').tooltip({container : 'body'});
					}, 0);
				},
				autowidth: true

            });
        },

        /**
         * 重新加载grid
         */
        reloadGrid : function(){
            $("#stuParent-table").trigger("reloadGrid");
        },
        /**
         * 加载修改数据
         * @returns
         */
        asyncReload : function(){
        	
         	if(_id){
         		$("#account_info").hide();//隐藏账号信息
         		$.get(path + "/admin/stuParent/findStuParentById",{id:_id,schId:_schId},function(ret){
         			var _stuList = ret.studentList;
         			for(var i = 0;i < _stuList.length;i++){
                 		var _stuObj = _stuList[i];
                 		var rowObj = {
                 				id:i+1,
                 				schId:_stuObj.schId,
                 				className:_stuObj.className,
                 				studentName:_stuObj.name,
                 				relationTypeName:_stuObj.relationType_showname,
                 				guardianName:_stuObj.guardian_showname,
                 				relationType:_stuObj.relationType,
                 				studentId:_stuObj.studentId,
                 				parentId:_stuObj.parentId,
                 				guardian:_stuObj.guardian
                 				};
                 		jQuery("#chooseStu-table").jqGrid('addRowData', i + 1, rowObj);
         			}
         			$.each(ret,function(k,v){
        				var _val = v;
        				if(v == null){
        					_val = "";	
        				}
        				if(k == "sex"){
        					$("input[type='radio'][name='stuParent.sex'][value='"+_val+"']").attr("checked",true);  
        				}
        				$("#stuParent_form #stuParent_"+k).val(_val);
        			});
         		});	
         	}
        }
    };
})();

var rows = [];
var choseRowData;
var _id =  $("#stuParent_ID").val();
$(document).ready(function(){
	dyChooseStu.createGrid();
	dyChooseStu.asyncReload();
	
	$("#stuParent_name").change(function(){
		$.post(path + "/common/getPinyin",{name:$("#stuParent_name").val()},function(r){
			if(r){
				$("#stuParent_nameSpell").val(r);
			}
		});			
	});
	
	//validate expand
	jQuery.validator.addMethod("checkTelphone",function(value,element){
		var result = true;
		var name=$("#stuParent_name").val();
		var telphone=$("#stuParent_telphone").val();
		//设置为同步
		$.ajaxSetup({async:false});
		var param;
		if(!_id){//新增
			param = {tel:telphone,schId:parent._schId,name:name};
		}else{//修改
			param = {tel:telphone,schId:parent._schId,id:_id,name:name};
		}
		if(name!="" && telphone!=""){
			$.post(path+"/admin/stuParent/findTelphone",param,function(data){
				result = (1 == data.code);
			});
		}
		$.ajaxSetup({async:true});
		return result;
	},'用户已有相同姓名和手机号码');
	
    //选择孩子
    $("#searchButton").on(ace.click_event, function() {
    	$.get(path + "/admin/stuParent/showStudent.jsp",function(ret){
            bootbox.dialog({
                title : "选择孩子",
                message : ret,
                buttons : {
                    cancel : {
                        "label" : "取消",
                        "class" : "btn",
                        "callback" :  function(result) {
                            if(result) {

                            }
                        }
                    }
                }
            });
        });
    });
    
	//表单验证
	$("#stuParent_form").validate({
		errorElement: 'span',
		errorClass: 'help-inline',
		focusInvalid: false,
		rules : {
			"stuParent.name" : {
				required : true,
				chinese : true,
				rangelength:[2,8]
			},
			"stuParent.telphone" : {
				required : true,
				mobile : true
				//checkTelphone:true
			},
			"stuParent.officePhone":{
				 telOrMobile: true
			},
			"stuParent.email" : {
				email : true
			},
			"stuParent.zipCode" : {
				zipCode : true
			},
			"stuParent.company" : {
				 maxlength:50
			},
			"stuParent.officeAddr" : {
				 maxlength:255
			},
			"stuParent.duties" : {
				 maxlength:50
			},
			"stuParent.addr" : {
				maxlength:50
			}
			
		},
		messages : {
			"stuParent.officePhone":{
                telOrMobile : "请输入正确的电话号码(中国)或移动电话号码(中国)"
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
	
	//联系手机 跟 邮箱 绑定
	$("#stuParent_telphone").blur(function(){
		$("#stuParent_telephone").val($("#stuParent_telphone").val());
	});
	
	$("#stuParent_email").blur(function(){
		$("input[name='user.email']").val($("#stuParent_email").val());
	});
	
	/**
	 * 移除关联的学生
	 */
	 $("body").off(ace.click_event,"button[dyid='managedel-student']").on(ace.click_event,"button[dyid='managedel-student']",function(){
	        var _studentId = $(this).attr("studentId");
	        var _parentId = $(this).attr("parentId");
	        var selectedId = $("#chooseStu-table").jqGrid("getGridParam", "selrow");  
	       if (_parentId == "undefined" || _parentId==null) { 
	    	   $("#chooseStu-table").jqGrid('delRowData', selectedId); 
	    	   dyChooseStu.reloadGrid();
	    	   return;
	       }
	        bootbox.confirm("确认移除此学生？",function(r){
		            if(r){
			                $.get(path + "/admin/stuParent/deleStudent",{studentId : _studentId,parentId:_parentId,schId:_schId},function(delRet){
			                	if(delRet.code=="-2"){
			             			bootbox.alert("不能删除，请至少关联一个孩子!");
			             			return false;
			             		}
			                	$.gritter.add({
			                        title: '关联学生移除',
			                        text: delRet.code == "1" ? "删除成功" : "删除失败："+delRet.msg,
			                        sticky: false,
			                        time: ''
			                    });
			                    $("#chooseStu-table").jqGrid('delRowData', selectedId); 
			                    dyChooseStu.reloadGrid();
			                	parent.dyStuParent.reloadGrid();
			                });
		            }
	        });
	    });
	   
});

function setLayerStu(stuId,stuName){
	$("#relaStu_id").val(stuId);
	$("#relaStu_name").val(stuName);
}

/**
 * 关联孩子
 */
$("#choose_studentBtn").on('click',function(){
	dyStuParent.choseRowData = choseRowData;
	dyStuParent.assocChild("stuParent_form");
});

function checkNameAndTel(){
	var result = false;
	var name=$("#stuParent_name").val();
	var telphone=$("#stuParent_telphone").val();
	//设置为同步
	$.ajaxSetup({async:false});
	var param;
	if(!_id){//新增
		param = {tel:telphone,schId:parent._schId,name:name};
	}else{//修改
		param = {tel:telphone,schId:parent._schId,id:_id,name:name};
	}
	if(name!="" && telphone!=""){
		$.post(path+"/admin/stuParent/findNameAndTel",param,function(data){
			_id=data.ID;
			result = (-1 != data.code);
		});
	}
	if(result){
		bootbox.confirm(""+name+"已有相同记录，是否直接沿用？",function(r){
			$("#isExist").val("true"); //已有相同数据
        	$("#account_info").hide();//隐藏账号信息
			if(r){
	         		$.get(path + "/admin/stuParent/findStuParentById",{id:_id,schId:_schId},function(ret){
	         			var _stuList = ret.studentList;
	         			for(var i = 0;i < _stuList.length;i++){
	                 		var _stuObj = _stuList[i];
	                 		var rowObj = {
	                 				id:i+1,
	                 				schId:_stuObj.schId,
	                 				className:_stuObj.className,
	                 				studentName:_stuObj.name,
	                 				relationTypeName:_stuObj.relationType_showname,
	                 				guardianName:_stuObj.guardian_showname,
	                 				relationType:_stuObj.relationType,
	                 				studentId:_stuObj.studentId,
	                 				parentId:_stuObj.parentId,
	                 				guardian:_stuObj.guardian
	                 				};
	                 		jQuery("#chooseStu-table").jqGrid('addRowData', i + 1, rowObj);
	         			}
	         			$.each(ret,function(k,v){
	        				var _val = v;
	        				if(v == null){
	        					_val = "";	
	        				}
	        				if(k == "sex"){
	        					$("input[type='radio'][name='stuParent.sex'][value='"+_val+"']").attr("checked",true); 
	        					$("input[type='radio'][name='stuParent.sex'][value='"+_val+"']").attr("disabled",true);
	        				}
	        				$("#stuParent_form #stuParent_"+k).val(_val);
	        				$("#stuParent_form #stuParent_"+k).attr("readonly","readonly");
	        				if(k == "political"){
	        					$("#stuParent_form #stuParent_"+k).attr("disabled",true);
	        				}
	        			});
	         		});	
	         }
		});
	}
	$.ajaxSetup({async:true});
}