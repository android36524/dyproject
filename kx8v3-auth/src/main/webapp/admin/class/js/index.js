/**
 * 学校班级管理的JS
 */
var dyClass = (function(){
	
	return {
		/**
		 * 班级模块初始化
		 * */
		init : function(){
			this.createGrid();
		},
		
		/**
		 * 创建Grid
		 * */
		createGrid : function(){
			var grid_selector = "#class-table";
			var pager_selector = "#class-pager";
			
			jQuery(grid_selector).jqGrid({
				url : path + "/admin/class/listClassByPage?schId="+_schId,
				datatype: "json",
				height: 400,
				colNames:['学校','年级别名','班级名称','班主任','排序','操作'],
				colModel:[
				       {name:'schoolName',index:'schoolName',type:'text',width:40},
				       {name:'gradeName',index:'gradeName',type:'text', width:30},
				       {name:'className',index:'className',type:'text',width:30},
				       {name:'headTeacher',index:'headTeacher',type:'text',width:30},
				       {name:'seq',index:'seq',width:20},
				       {name:'id',index:'id', width:50, sortable:false,formatter : function(e, options, rowObject){
	                    	var name = rowObject.className;
	                    	var editBtn = commonJs.createDefaultAuthBtn(commonJs.btn_modifycode,"modify-class","修改",{classId:e});
							var delBtn = commonJs.createDefaultAuthBtn(commonJs.btn_delcode,"del-class","删除",{classId:e,className:name});
							var setBtn = commonJs.createDefaultAuthBtn(commonJs.btn_name.set,"set-hTeacher","班主任设置",{classId:e,className:name});
//							var setBtn= '<button class="btn btn-xs btn-success tooltip-danger" dyid="set-hTeacher" classId="'+String(e)+
//								'" className="'+rowObject.className+'" data-rel="tooltip" data-placement="top" title="班主任设置">' +
//								'班主任设置</button>'; ;
							return '<div class="visible-md visible-lg hidden-sm hidden-xs btn-group">' +editBtn+delBtn+setBtn+
									'</div>';
				       }}
			],
			viewrecords : true,
			autowidth : true,
			rowNum:30,
			rowList:[20,30,40,50],
			pager : pager_selector,
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
			caption: "<i class='icon-list'></i>班级列表",
			autowidth: true
			});
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
		 * 重新加载班级数据
		 * */
		reloadGrid : function(){
			$("#class-table").trigger("reloadGrid");
		},
		/**
		 * 查询条件参数
		 * @returns
		 */
		getParamObj : function(){
			var paramObj = {};
			paramObj.gradeId = $('#gradeId').val();
			paramObj.className = $.trim($("#className").val());
			return paramObj;
		}
	};
})();

$(function(){
//	dyClass.init();
	
	var obj = _tree.initEdbTree("1",{}, function () {
		$("#form-search")[0].reset();
		var nodes = _edbTreeObj.getSelectedNodes();
		if(!_tree.isSchoolNode(nodes[0])){
			return;
		}
		_schId = nodes[0].id;
		$("#class-table").jqGrid('setGridParam',{
			url : path + "/admin/class/listClassByPage?schId="+_schId,
			datatype:'json',
			mtype :'POST',
			postData:dyClass.getParamObj(),
			page:1
		}).trigger("reloadGrid");
		$("#gradeId").empty();
		$("#gradeId").prepend("<option value=''>请选择</option>");
		dyClass.gradeInit(_schId);
	});
	_schId=obj.id;
	dyClass.init();
	dyClass.gradeInit(_schId);
	
	//按条件查询
	$("#find_btn").click(function(){
		$("#class-table").jqGrid('setGridParam',{  
	        datatype:'json',  
	        mtype :'POST',
	        postData:dyClass.getParamObj(), //发送数据  
	        page:1  
	    }).trigger("reloadGrid"); 
	});
	
	//增加班级
	$("#btn-addClass").on(ace.click_event,function(){
		$.post(path + "/admin/class/goAdd?schId="+_schId,function(ret){
			bootbox.dialog({
				title : "新增/修改班级信息",
				message : ret,
				buttons : {
					success : {
						label: "保存",
                        className: "btn-success",
                        callback :  function(result) {
							if(result) {
								if($("#class_form").valid()){
									$.ajaxSetup({async : false});
									$.post(path + "/admin/class/add",$("#class_form").formToObject(),function(addRet){
										$.gritter.add({
											title: '班级',
											text: addRet.code == "1" ? "新增班级信息成功!" : "新增班级信息失败!",
											sticky: false,
											time: '',
											class_name: "gritter-light"
										});
										dyClass.reloadGrid();
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
	
	//编辑班级
	$("body").on(ace.click_event,"button[dyid='modify-class']", function() {
		var _classId = $(this).attr("classId");		
		$.get(path + "/admin/class/goAdd?schId="+_schId+"&_classId="+_classId,function(ret){
			bootbox.dialog({
				title : "新增/修改班级信息",
				message : ret,
				buttons : {
					success : {
						label: "更新",
                        className: "btn-success",
                        callback :  function(result) {
							if(result) {
								if($("#class_form").valid()){
									
									$.ajaxSetup({async : false});
									$.post(path + "/admin/class/add", $("#class_form").formToObject(),function(addRet){
										$.gritter.add({
											title: '新增/修改班级信息',
											text: addRet.code == "1" ? "修改班级信息成功!" : addRet.msg,
											sticky: false,
											time: ''
										});
										dyClass.reloadGrid();
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
	
	
	//删除班级
	$("body").on(ace.click_event,"button[dyid='del-class']", function() {
		var _classId = $(this).attr("classId");		
		var _className = $(this).attr("className");		
		
		bootbox.confirm("您将删除" +_className+ "班级信息，删除后信息将无法恢复，请确认此次操作?",function(r){
			if(r){
				$.get(path + "/admin/class/del",{_classId : _classId},function(delRet){
					
					$.gritter.add({
						title: '班级删除',
						text: delRet.code == "1" ? "删除班级信息成功!" : delRet.msg,
						sticky: false,
						time: ''
					});
					dyClass.reloadGrid();
				});
			}
		});
	});
	
	//班主任设置
	$("body").on(ace.click_event,"button[dyid='set-hTeacher']", function() {
		var _classId = $(this).attr("classId");
		$.get(path + "/admin/common/teacherChoose/show_teacher.jsp?schId="+_schId,function(ret){
            bootbox.dialog({
                title : "班主任选择",
                message : ret,
                buttons : {
                    success : {
                        label: "选择带回",
                        className: "btn-success",
                        callback :  function(result) {
                        	var rowData = JSON.parse($("#selected").val());
                        	//关闭弹窗
            				commonJs.closedialog("empe-tab"); //关闭当前弹窗
            	            
            	            $.get(path + "/admin/class/setHteacher", 
            	            		{_classId : _classId, classHeadId : rowData.id},
            	             function(setRet){
            					$.gritter.add({
            						title: '班主任设置',
            						text: setRet.code == "1" ? "班主任设置成功!" : setRet.msg,
            						sticky: false,
            						time: ''
            					});
            					dyClass.reloadGrid();
            				});
            				
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
	
});
 