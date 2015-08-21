/**
 * 学生管理的JS
 */
var dyStudent = (function(){
	
	return {
		
		/**
    	 * 用于存储选择的家长数据
    	 */
    	choseRowData: null,
    	
		/**
		 * 学生模块初始化
		 * */
		init : function(){
			this.createGrid();
		},
		
		/**
		 * 创建Grid
		 * */
		createGrid : function(){
			var grid_selector = "#student-table";
			var pager_selector = "#student-pager";
			
			jQuery(grid_selector).jqGrid({
				url : path + "/admin/student/ListPage?schId="+_schId,
				datatype: "json",
				height: 450,
				colNames:['学校','年级及班级','学生ID','学生姓名','学号','性别','家长ID','家长','家长手机','账号','学生在校状态','操作'],
				colModel:[
				       {name:'schoolName',index:'schoolName',type:'text',width:120},
				       {name:'gradeNameAndSc',index:'gradeNameAndSc',type:'text', width:120},
				       {name:'id',index:'id',hidden:true},
				       {name:'name',index:'name',type:'text',width:120,formatter : function(j,options,rowObject){
	                    	return '<a href="javascript:showDetail('+rowObject.id+');">'+rowObject.name+'</a>';
	                    }},
				       {name:'studentNo',index:'studentNo',type:'text',width:120},
				       {name:'sex_showname',index:'sex_showname',type:'text',width:40},
				       {name:'parentIds',index:'parentIds',hidden:true},
				       {name:'parentName',index:'parentName',type:'text',width:120},
				       {name:'trelphone',index:'trelphone',type:'text',width:120},
				       {name:'account',index:'account',type:'text',width:120},
				       {name:'status_showname',index:'status',type:'text',width:120},
				       {name:'dlyId',index:'dlyId', width:120, sortable:false,formatter : function(e, options, rowObject){
				    	    var id=rowObject.id;
				    	    var name = rowObject.name;
	                    	var editBtn = commonJs.createDefaultAuthBtn(commonJs.btn_modifycode,"modify-student","修改",{studentId:id});
							var delBtn = commonJs.createDefaultAuthBtn(commonJs.btn_delcode,"del-student","删除",{studentId:id,studentName:name,parentIds:rowObject.parentIds});
							var manageBtn = commonJs.createDefaultAuthBtn(commonJs.btn_name.manage,"manage-parent","管理家长",{studentId:id});
							return '<div class="visible-md visible-lg hidden-sm hidden-xs btn-group">' +editBtn+delBtn+manageBtn+ '</div>';
				       }}
			],
			viewrecords : true,
			rowNum:30,
			rowList:[20,30,40,50],
			pager : pager_selector,
			altRows: true,
			multiselect: true,
	        multiboxonly: true,
			loadComplete : function() {
				var table = this;
				setTimeout(function(){
					dyjsCommon.enableTooltips(table);
					dyjsCommon.updatePagerIcons(table);
					$('button[menuid]').tooltip({container : 'body'});
				}, 0);
			},
			caption: "<i class='icon-list'></i>学生列表",
			autowidth: true
			});
	},
		
		/**
		 * 重新学生管理数据
		 * */
		reloadGrid : function(){
			$("#student-table").trigger("reloadGrid");
		},
		
		/**
         * 年级初始化
         */
        gradeInit : function(schId){
        	$.post(path+"/admin/grade/findGradeByTerm?schId="+schId,function(ret){
				if(ret){
					for(var i=0;i<ret.length;i++){
						var obj = ret[i];
						$("#gradeId").append("<option value="+obj.id+">"+obj.name+"</option>");
					}
				}
			});
        },
        
		/**
		 * 根据年级查找班级
		 */
		classInit : function(gradeId){
        	$.post(path+"/admin/class/findClassByGradeId?gradeId="+gradeId,function(ret){
				if(ret){
					for(var i=0;i<ret.length;i++){
						var obj = ret[i];
						$("#classId").append("<option value="+obj.id+">"+obj.name+"</option>");
					}
				}
			});
        },
	
	/**
     * 清除下拉选 数据
     */
    removeSelect : function(selectId){
		  var selectObject = $("#" + selectId + " option");
		  selectObject.each(function(){
			  if( $(this).val() !== ""){
				  $(this).remove();
			  }
		  });
	},
	/**
	 * 获取关联的家长列表信息
	 */
	getParentInfo : function(formId){
		var obj = $("#"+formId+" table[name='chooseTable']").jqGrid("getRowData");
	    if(obj!=""){
	    	var _stuVal = "[";
	    	jQuery(obj).each(function(i){
	    		_stuVal = _stuVal+JSON.stringify(obj[i])+",";
	    	});
	    	_stuVal = _stuVal.substring(0,_stuVal.length-1);
	    	_stuVal += "]";
	    	$("#"+formId+" input[id='rela_Stu']").val(_stuVal);
	    }
	},
	
	/**
	 * 关联家长 对相应的表单元素赋值
	 */
	assocChild : function(formId){
		var grid_selector = jQuery("#"+formId+" table[name='chooseTable']");
		var _relationType = $("#"+formId+" select[name='relaStu.relationType'] ").val();
		var _parentName = $("#"+formId+" input[name='name'] ").val();
		
		var stuId = $("#"+formId+" input[name='student.id'] ").val();
		var _parentId = $("#"+formId+" input[name='id'] ").val();
		//判断是否选择了家长 ，是否选择了对应的关系
		if(_parentName == '' || _parentName == null){
			bootbox.alert("请选择关联的家长");
			return;
		}
		
		if(_relationType == '' || _relationType == null){
			bootbox.alert("请选择你与家长的关系");
			return;
		}
		var obj = grid_selector.jqGrid("getRowData");
		var flag=false;
		jQuery(obj).each(function(i){
			if(_parentId==this.parentId){
				bootbox.alert(_parentName+"该家长已关联!!!");
				flag=true;
				return;
			}
		});		
		if(flag){
			return;
		}
		var parent=dyStudent.choseRowData;
		var _name = parent.name;
		var _tel=parent.telphone;
		var _studentId = stuId;
		var _relationTypeName = $("#"+formId+" select[name='relaStu.relationType'] option:selected").text();
		var _guardian =$("#"+formId+" input[name='relaStu.guardian']:checked").val();
		var _guardianName = $("#"+formId+" input[name='relaStu.guardian']:checked ~ span").text();
		var _id =grid_selector.jqGrid('getGridParam','records');
		_id += 1;
		var rowObj = {id:_id,schId:_schId,name:_name,telphone:_tel,relationTypeName:_relationTypeName,guardianName:_guardianName,relationType:_relationType,parentId:_parentId,guardian:_guardian};
		rows = [];
		rows.push(rowObj);
		for(var i = 0;i <= rows.length; i++){
			grid_selector.jqGrid('addRowData', _id, rows[i]);
		}
	},
	/**
	 * 查询条件参数
	 * @returns
	 */
	getParamObj : function(){
		var paramObj = {};
		paramObj.gradeId = jQuery("#gradeId").val(); 
		paramObj.classId = jQuery("#classId").val(); 
		paramObj.name =  $.trim($("#name").val());
		paramObj.tel =  $.trim($("#tel").val());
		return paramObj;
	}
	
	};
})();


/**
 * 查看详情
 */
function showDetail(id){
     var schId = _schId;
     $.get(path + "/admin/student/toAdd?isDetail=true&id="+id+"&schId="+schId,function(ret){
         bootbox.dialog({
             title : "查看详情",
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
}

$(function(){
	
	//学校机构数初始化
	var obj = _tree.initEdbTree("1",{}, function () {
		$("#form-search")[0].reset();
		var nodes = _edbTreeObj.getSelectedNodes();
		if(!_tree.isSchoolNode(nodes[0])){
			return;
		}
		_schId = nodes[0].id;
		$("#student-table").jqGrid('setGridParam',{
			url : path + "/admin/student/ListPage?schId="+_schId,
			datatype:'json',
			mtype :'POST',
			postData:dyStudent.getParamObj(),
			page:1
		}).trigger("reloadGrid");
		$("#gradeId").empty();
		$("#gradeId").prepend("<option value=''>请选择</option>");
		dyStudent.gradeInit(_schId);
	});
	_schId=obj.id;
	   
	dyStudent.init();
	dyStudent.gradeInit(_schId);
	
	// 根据年级初始化班级
	  $("#gradeId").change(function () {
		  	$("#classId").empty();
			$("#classId").prepend("<option value=''>请选择</option>");
	        var gradeId = $(this).val();
	        if(gradeId != "") {
	            dyStudent.classInit(gradeId);
	        }
	    });
	
	//按条件查询
	$("#find_btn").click(function(){
		$("#student-table").jqGrid('setGridParam',{  
	        datatype:'json',  
	        mtype :'POST',
	        postData: dyStudent.getParamObj(), //发送数据  
	        page:1  
	    }).trigger("reloadGrid"); 
	});
	
	//增加学生
	$("#btn-addStudent").on(ace.click_event,function(){
		var schId = _schId;
		$.post(path + "/admin/student/toAdd?schId="+_schId,function(ret){
            bootbox.dialog({
                title : "新增学生信息",
                width:1000,
                message : ret,
                buttons : {
                    success : {
                        label: "保存",
                        className: "btn-success",
                        callback :  function(result) {
                            if(result) {
                                     if($("#student_form").valid()){
                                         $.ajaxSetup({async : false});
                                         $.post(path + "/admin/student/add",$("#student_form").formToObject(),function(addRet){
                                             $.gritter.add({
                                                 title: '学生新增',
                                                 text: addRet.code == "1" ? "新增学生成功！"+addRet.msg : "添加失败"+addRet.msg,
                                                 sticky: false,
                                                 time: '',
                                                 class_name: "gritter-light"
                                             });
                                             dyStudent.reloadGrid();
                                         });
                                         $.ajaxSetup({async : true});
                                     }else{
                                         return false;
                                     }
                            }
                        }
                    },
                    cancel : {
                        label : "取消",
                        "class" : "btn",
                        callback :  function(result) {
                            if(result) {

                            }
                        }
                    }
                }
            });
        });
	
	});
	
	 //更新学生信息
    $("body").on(ace.click_event,"button[dyid='modify-student']", function() {
        var studentId = $(this).attr("studentId");
        var schId = _schId;
        $.get(path + "/admin/student/toAdd?id="+studentId+"&schId="+schId,function(ret){
            bootbox.dialog({
                title : "更新学生信息",
                message : ret,
                buttons : {
                    success : {
                        label: "更新",
                        className: "btn-success",
                        callback :  function(result) {
                            if(result) {
                                if($("#student_form").valid()){
                                    $.ajaxSetup({async : false});
                                    $.post(path + "/admin/student/add",$("#student_form").formToObject(),function(addRet){
                                        bootbox.alert(addRet.code == "1" ? "更新成功" : "更新失败",function(r){
                                        	dyStudent.reloadGrid();
                                        });
                                    });
                                    $.ajaxSetup({async : true});
                                }else{
                                    return false;
                                }
                            }
                        }
                    },
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
    
    
    //管理家长
    $("body").on(ace.click_event,"button[dyid='manage-parent']", function() {
        var studentId = $(this).attr("studentId");
        var schId = _schId;
        $.get(path + "/admin/student/toManageParent?id="+studentId+"&schId="+schId,function(ret){
            bootbox.dialog({
                title : "管理关联家长信息",
                message : ret,
                buttons : {
                    success : {
                        label: "保存",
                        className: "btn-success",
                        callback :  function(result) {
                        	dyStudent.getParentInfo("parent_form");
                        	var rela_Stu=$("#parent_form input[id='rela_Stu']").val();
                            if(result && rela_Stu!="") {
	                            $.ajaxSetup({async : false});
	                            $.post(path + "/admin/student/saveRelaStu",$("#parent_form").formToObject(),function(addRet){
	                                bootbox.alert(addRet.code == "1" ? "关联家长更改成功！"+addRet.msg : "关联家长更改失败！"+addRet.msg,function(r){
	                                	 this.cancel;
	                                });
	                            });
	                            $.ajaxSetup({async : true});
                            }
                            dyStudent.reloadGrid();
                        }
                    },
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
    
    
	
	//单个删除学生
	$("body").on(ace.click_event,"button[dyid='del-student']", function() {
		var _studentId = $(this).attr("studentId");		
		var _studentName = $(this).attr("studentName");		
		var _parentIds = $(this).attr("parentIds");	 
		bootbox.confirm("您将删除" +_studentName+ "学生信息，删除后信息将无法恢复，请确认此次操作?",function(r){
			if(r){
				$.get(path + "/admin/student/del",{ids : _studentId,parentIds:_parentIds,schId:_schId},function(delRet){
					if(delRet.code=="-2"){
	          			bootbox.alert("该学生的家长只关联了这一个小孩，请先删除家长信息!");
	          			return false;
	          		}
					$.gritter.add({
						title: '学生删除',
						text: delRet.code == "1" ? "删除学生信息成功！"+delRet.msg : "删除家长信息失败！"+delRet.msg,
						sticky: false,
						time: ''
					});
					dyStudent.reloadGrid();
				});
			}
		});
	});
    
    
    //批量删除学生
    $("#btn-removeStudent").click(function(){
    	var s; 
    	s = jQuery("#student-table").jqGrid('getGridParam', 'selarrrow');
    	var rowData = [];
    	var _id = [];
    	var _parentIds = [];
    	var idArr;
    	var val = "";
		if(s.length == 0){
			bootbox.alert("请选择你要删除的学生！");
			return;
		}else if(s.length > 0){
			idArr = s.toString().split(",");
	    	for(var i = 0;i < idArr.length;i++){
	    		var row = $("#student-table").jqGrid('getRowData',idArr[i]);
	    		if(row.parentIds!=""){
	    			_parentIds.push(row.parentIds);
	    		}else{
	    			rowData.push(row);
		    		_id.push(row.id);
	    		}
	    	}
		}
		if(_parentIds.length > 0){
			bootbox.alert("你选中的学生中，有学生绑定了家长，请单个删除！");
			return;
		}
		if(rowData.length > 1){
			val = "这批";
		}else if(rowData.length == 1) {
			val = rowData[0].name;
		}
        bootbox.confirm("您将删除"+val+"学生信息，删除后信息将无法恢复，请确认此次操作？",function(r){
            if(r){
                $.get(path + "/admin/student/del",{ids:_id.toString()},function(delRet){
                    $.gritter.add({
                        title: '学生删除',
                        text: delRet.code == "1" ? "删除成功" : "删除失败："+delRet.msg,
                        sticky: false,
                        time: ''
                    });
                    dyStudent.reloadGrid();
                });
            }
        });

    });
	
	//毕业
	$("#btn-graduateStuParent").click(function(){
		var ids=$('#student-table').jqGrid('getGridParam','selarrrow');
		if(ids.length<1){
			$.gritter.add({
                title: '操作提示',
                text: "请选择你要毕业的学生",
                sticky: false,
                time: ''
            });
			return ;
		}
		var str = ids[0];
		for(var i=1;i<ids.length;i++){
			str = str+","+ids[i];
		}
		bootbox.confirm("确认毕业？",function(r){
			if(r){
				$.post(path+'/admin/student/stuGraduate',{id:str},function(ret){
					$.gritter.add({
						title: '学生毕业',
						text: ret.code == "1" ? "学生毕业成功！" : "学生毕业失败！",
						sticky: false,
						time: ''
					});
					dyStudent.reloadGrid();
				});
			}
		})
	});
	
	//上传Excel
    $("#btn_importExcel").on(ace.click_event, function() {
    //    var schId = _schId;
        $.get(path + "/admin/student/student-import.jsp",function(ret){
        	 bootbox.dialog({
                 title : "学生基本信息导入",
                 message : ret,
                 buttons : {
                     success : {
                         label: "保存",
                         className: "btn-success",
                         callback :  function(result) {
                        	 if(result) {
                                 if($("#excel_form").valid()){
                                     var fileName = $("#excelFileName").val();
                                     var excelName=$("#excelName").val();
                                     $.ajaxSetup({async : false});
                                     $.post(path + "/admin/student/importStuInfo",{schId:_schId,fileUrl:fileName,excelName:excelName},function(addRet){
                                         var promptMsg = "成功导入：" + addRet.code + " 条数据，导入失败：" + addRet.msg + " 条数据";
                                         $.gritter.add({
                                             title: '学生基本信息导入',
                                             text:  promptMsg,
                                             sticky: false,
                                             time: '',
                                             class_name: "gritter-light"
                                         });
                                         dyStudent.reloadGrid();
                                     });
                                     $.ajaxSetup({async : true});
                                 }else{
                                     return false;
                                 }
                             }
                         }
                     },
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
    $("#btn-exportStudent").bind('click',function(){
        var schId = _schId;
        var  gradeId = jQuery("#gradeId").val(); 
		var  classId = jQuery("#classId").val(); 
		var  name =	$("#name").val();
		var  tel = $("#tel").val();
        location.href = path +"/admin/student/downLoadExcel?schId="+schId+"&gradeId="+gradeId+"&classId="+classId+"&name="+name+"&tel="+tel;
    });
    
    //模板下载
    $("#btn_downTempExcel").bind('click',function(){
        location.href = path +"/admin/student/downExcel";
    });
});
 