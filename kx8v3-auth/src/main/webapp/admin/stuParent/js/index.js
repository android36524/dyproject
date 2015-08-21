/**
 * 学生家长管理的JS
 */

var dyStuParent = (function(){

    return {
    	
    	/**
    	 * 用于存储选择的孩子数据
    	 */
    	choseRowData: null,
    	
        /**
         * 学生家长模块初始化
         */
        init : function(){
            this.createGrid();
        },

        /**
         * 创建Grid
         */
        createGrid : function(){
            var grid_selector = "#stuParent-table";
            var pager_selector = "#stuParent-pager";

            jQuery(grid_selector).jqGrid({
                url : path + "/admin/stuParent/findParentByPage?schId="+_schId,
                datatype: "json",
                height: 450,
                colNames:['家长姓名','孩子班级/姓名','是否监护人', '关系', '手机号码','联系地址','账号','操作','ID'],
                colModel:[
                    {name:'name',index:'name',width:40, sortable:false,formatter : function(j,options,rowObject){
                    	return '<a href="javascript:showDetail('+rowObject.ID+');">'+rowObject.name+'</a>';
                    }},
                    {name:'className',index:'className', width:60, sortable:false,formatter : function(j,options,rowObject){
                    	var _stuList = rowObject.stuList;
                    	var formatStr = '';
                    	for(var i = 0;i < _stuList.length;i++){
                    		var _stuObj = _stuList[i];
                    		if(i == _stuList.length-1 ){
                    			formatStr += '<p style="text-align:center;">'+_stuObj.className+'/'+_stuObj.name+'</p>';
                    		}else{
                    			formatStr += '<p class="stu-pcut-rule">'+_stuObj.className+'/'+_stuObj.name+'</p>';
                    		}
                    	}
                    	return formatStr;
                    }},
                    {name:'guardian',index:'guardian', width:30, sortable:false,formatter : function(j,options,rowObject){
                    	var _stuList = rowObject.stuList;
                    	var formatStr = '';
                    	for(var i = 0;i < _stuList.length;i++){
                    		var _stuObj = _stuList[i];
                    		if( i == _stuList.length-1){
	                    		if(_stuObj.guardian == 1){
	                    			formatStr += '<p style="text-align:center">是</p>';
	                    		}else{
	                    			formatStr += '<p style="text-align:center">否</p>';
	                    		}
                    		}else{
                    			if(_stuObj.guardian == 1){
	                    			formatStr += '<p class="stu-pcut-rule">是</p>';
	                    		}else{
	                    			formatStr += '<p class="stu-pcut-rule">否</p>';
	                    		}
                    		}
                    	}
                    	return formatStr;
                    }},
                    {name:'relationType_showname',index:'relationType_showname', width:40, sortable:false,formatter:function(j,options,rowObject){
                    	var _stuList = rowObject.stuList;
                    	var formatStr = '';
                    	for(var i = 0;i < _stuList.length;i++){
                    		var _stuObj = _stuList[i];
                    		if(i == _stuList.length-1){
                    			formatStr += '<p style="text-align:center;">'+_stuObj.relationType_showname+'</p>';
                    		}else{
                    			formatStr += '<p class="stu-pcut-rule">'+_stuObj.relationType_showname+'</p>';
                    		}
                    	}
                    	return formatStr; 
                    	    
                    }},
                    {name:'telphone',index:'telphone', width:80, sortable:false},
                    {name:'addr',index:'chargePhone', width:80, sortable:false},
                    {name:'account',index:'account', width:70, sortable:false},
                    {name:'diyID',index:'diyID', width:100, sortable:false,formatter : function(j,options,rowObject){
                    	var id = rowObject.ID;
                    	var name = rowObject.name;
                    	var editBtn = commonJs.createDefaultAuthBtn(commonJs.btn_modifycode,"modify-stuParent","修改",{stuParentId:id});
						var delBtn = commonJs.createDefaultAuthBtn(commonJs.btn_delcode,"remove_stuParent","删除",{stuParentId:id,name:name,onclick:"removeParent('"+id+"','"+name+"')"});
						var manageBtn= commonJs.createDefaultAuthBtn(commonJs.btn_name.manage,"manage-stu","管理孩子",{stuParentId:id});
						return '<div class="visible-md visible-lg hidden-sm hidden-xs btn-group">' +editBtn+delBtn+manageBtn+
								'</div>';
                    }},
                    {name:'ID',index:'ID',hidden:true}
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
				caption: "<i class='icon-list'></i>家长列表",
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
		 * 获取关联的学生列表信息
		 */
		getStudentInfo : function(formId){
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
		 * 关联孩子 对相应的表单元素赋值
		 */
		assocChild : function(formId){
			var grid_selector = jQuery("#"+formId+" table[name='chooseTable']");
			var _relationType = $("#"+formId+" select[name='relaStu.relationType'] ").val();
			var _studentName = $("#"+formId+" input[name='name'] ").val();
			//判断是否选择了孩子 ，是否选择了对应的关系
			if(_studentName == '' || _studentName == null){
				bootbox.alert("请选择关联的孩子");
				return;
			}
			
			if(_relationType == '' || _relationType == null){
				bootbox.alert("请选择你与孩子的关系");
				return;
			}
			
			var obj = grid_selector.jqGrid("getRowData");
			var _studentId = dyStuParent.choseRowData.id;
			var _name = dyStuParent.choseRowData.name;
			var flag=false;
			jQuery(obj).each(function(i){
				if(_studentId==this.studentId){
					bootbox.alert(_name+"该学生已关联!!!");
					flag=true;
					return;
				}
			});		
			if(flag){
				return;
			}
			var _className = dyStuParent.choseRowData.gradeNameAndSc;
			var _relationTypeName = $("#"+formId+" select[name='relaStu.relationType'] option:selected").text();
			var _guardian =$("#"+formId+" input[name='relaStu.guardian']:checked").val();
			var _guardianName = $("#"+formId+" input[name='relaStu.guardian']:checked ~ span").text();
			var _id =grid_selector.jqGrid('getGridParam','records');
			_id += 1;
			var rowObj = {id:_id,schId:_schId,className:_className,studentName:_name,relationTypeName:_relationTypeName,guardianName:_guardianName,relationType:_relationType,studentId:_studentId,guardian:_guardian};
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
			paramObj.gradeId = $('#gradeId').val();
			paramObj.classId = $("#classId").val();
			paramObj.name =	$("#par_name").val();
			paramObj.tel = $("#par_phone").val();
			return paramObj;
		}
		
    };
})();

var _schId = "";

$(function(){
    
    //组织机构树
    $('#div_main').layout({ 
		applyDefaultStyles: true,
		west : {
			header : "组织机构树",
			size : 260
		}
	});
    
    
    
    var obj = _tree.initEdbTree("1",{}, function () {

        $("#form-search")[0].reset();
        var nodes = _edbTreeObj.getSelectedNodes();
        if(!_tree.isSchoolNode(nodes[0])){
            return;
        }
        _schId = nodes[0].id;
        $("#stuParent-table").jqGrid('setGridParam',{
			url : path + "/admin/stuParent/findParentByPage?schId="+_schId,
			datatype:'json',
			mtype :'POST',
			postData:dyStuParent.getParamObj(),
			page:1
		}).trigger("reloadGrid");
        $("#gradeId").empty();
		$("#gradeId").prepend("<option value=''>请选择</option>");
        dyGradeChoose.gradeInit(_schId);
    });
    
    _schId = obj.id;
    dyStuParent.init();
    dyGradeChoose.gradeInit(_schId);

    //批量删除
    $("#btn-removeStuParent").on('click',function(){
    	removeParent();
    });
    /**
     * 新增学生家长
     */
    $("#btn-addStuParent").on('click',function(){
        $.post(path + "/admin/stuParent/toModifyPage?schId="+_schId,function(ret){
            bootbox.dialog({
                title : "新增学生家长",
                message : ret,
                buttons : {
                    success : {
                        label: "保存",
                        className: "btn-success",
                        callback :  function(result) {
                            if(result) {
                                if($("#stuParent_form").valid()){
                                	var name=$("#stuParent_name").val();
                                	var telphone=$("#stuParent_telphone").val();
                                	dyStuParent.getStudentInfo("stuParent_form");
                                	var obj = $("#stuParent_form table[name='chooseTable']").jqGrid("getRowData");
                                	if(obj==""){
                                		bootbox.alert("请至少关联一个孩子!");
                                		return false;
                                	}
                                	var flag=false;
                                    $.post(path + "/admin/stuParent/validateParent",{schId:_schId,name:name,tel:telphone},function(ret){
                                            if(ret.code == "1"){
                                            	bootbox.alert(name+"家长已在该学校存在记录!");
                                            	flag=true;
                                            }
                                    });
                                    if(!flag){
                                    		$.ajaxSetup({async : false});
                                            $.post(path + "/admin/stuParent/saveStuParent",$("#stuParent_form").formToObject(),function(addRet){
                                                $.gritter.add({
                                                    title: '家长新增',
                                                    text: addRet.code == "1" ? "新增家长成功！"+addRet.msg : "添加失败"+addRet.msg,
                                                    sticky: false,
                                                    time: '',
                                                    class_name: "gritter-light"
                                                });
                                                dyStuParent.reloadGrid();
                                            });
                                            $.ajaxSetup({async : true});
                                    }else{
                                    	return false;
                                    }
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

    //更新学生家长
    $("body").on(ace.click_event,"button[dyid='modify-stuParent']", function() {
        var id = $(this).attr("stuParentId");
        $.get(path + "/admin/stuParent/toModifyPage?id="+id+"&schId="+_schId,function(ret){
            bootbox.dialog({
                title : "更新家长",
                message : ret,
                buttons : {
                    success : {
                        label: "更新",
                        className: "btn-success",
                        callback :  function(result) {
                            if(result) {
                                if($("#stuParent_form").valid()){
                                	dyStuParent.getStudentInfo("stuParent_form");
                                	var obj = $("#stuParent_form table[name='chooseTable']").jqGrid("getRowData");
                                	if(obj==""){
                                		bootbox.alert("请至少关联一个孩子!");
                                		return false;
                                	}else{
                                		$.ajaxSetup({async : false});
                                        $.post(path + "/admin/stuParent/saveStuParent",$("#stuParent_form").formToObject(),function(addRet){
                                            bootbox.alert(addRet.code == "1" ? "修改家长信息成功！"+addRet.msg : "修改家长信息成功！"+addRet.msg,function(r){
                                            	 dyStuParent.reloadGrid();
                                            });
                                        });
                                        $.ajaxSetup({async : true});
                                	}
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
    
    /**
     * 管理孩子
     */
    $("body").on(ace.click_event,"button[dyid='manage-stu']",function(){
    	var id = $(this).attr("stuParentId");
    	$.get(path + "/admin/stuParent/toManageStuPage?id="+id,function(ret){
            bootbox.dialog({
                title : "管理孩子",
                message : ret,
                buttons : {
                    success : {
                        label: "保存",
                        className: "btn-success",
                        callback :  function(result) {
                        	dyStuParent.getStudentInfo("manageStudent_form");
                        	var obj = $("#manageStudent_form table[name='chooseTable']").jqGrid("getRowData");
                        	if(obj==""){
                        		bootbox.alert("请至少关联一个孩子!");
                        		return false;
                        	}
                            if(result) {
	                            $.ajaxSetup({async : false});
	                            $.post(path + "/admin/stuParent/saveRelaStu",$("#manageStudent_form").formToObject(),function(addRet){
	                                bootbox.alert(addRet.code == "1" ? "关联孩子更改成功！"+addRet.msg : "关联孩子更改失败！"+addRet.msg,function(r){
	                                	 this.cancel;
	                                });
	                            });
	                            $.ajaxSetup({async : true});
                            }
                            dyStuParent.reloadGrid();
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
    
    // 导出学生家长信息
    $("#btn-exportStuParent").on('click',function(){
        var schId = _schId;
        var  gradeId = jQuery("#gradeId").val(); 
		var  classId = jQuery("#classId").val(); 
		var  name =	$("#par_name").val();
		var  tel = $("#par_phone").val();
        location.href = path +"/admin/stuParent/downLoadExcel?schId="+schId+"&gradeId="+gradeId+"&classId="+classId+"&name="+name+"&tel="+tel;
    });
    
    //按条件查询
    $("#find_btn").click(function(){
    	var _gradeId = $("#gradeId option:selected").val();
    	var _classId = $("#classId option:selected").val();
    	var _name = $("#par_name").val();
    	var _phone = $("#par_phone").val();
    	var params = {gradeId:_gradeId,classId:_classId,name:_name,tel:_phone,schId:_schId};
        $("#stuParent-table").jqGrid('setGridParam',{
            datatype:'json',
            mtype :'POST',
            postData:params, //发送数据
            page:1
        }).trigger("reloadGrid");
    });

});

/**
 * 查看详情
 */
function showDetail(id){
	 var id = id;
     $.get(path + "/admin/stuParent/showParentDetail.jsp?id="+id,function(ret){
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

/**
 * 删除家长信息
 */
function removeParent(id,name){
	var s; 
	s = jQuery("#stuParent-table").jqGrid('getGridParam', 'selarrrow');
	var rowData = [];
	var _id = [];
	var idArr;
	var val = "";
	if(typeof(name) !== 'undefined'){
		_id.push(id);
		val = name;
	}else{
		if(s.length == 0){
			bootbox.alert("请选择你要删除的家长！");
			return;
		}else if(s.length > 0){
			idArr = s.toString().split(",");
	    	for(var i = 0;i < idArr.length;i++){
	    		var row = $("#stuParent-table").jqGrid('getRowData',idArr[i]);
	    		rowData.push(row);
	    		_id.push(row.ID);
	    	}
		}
		if(rowData.length > 1){
			val = "这批";
		}else if(rowData.length == 1) {
			val = rowData[0].name;
		}
	}
	
	bootbox.confirm("您将删除"+val+"家长信息，删除后信息将无法恢复，请确认此次操作？",function(r){
		if(r){
			$.post(path+"/admin/stuParent/deleStuParentInfo",{id:_id.toString(),schId:_schId},function(ret){
				$.gritter.add({
					title:'家长信息删除',
					text:ret.code == "1" ? "删除家长信息成功！"+ret.msg : "删除家长信息失败！"+ret.msg,
					sticky: false,
					time: '' 
				});
				dyStuParent.reloadGrid();
			});
		}
	});
}