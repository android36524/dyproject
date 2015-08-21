
/**
 * 教育局管理的JS
 */
var _pOrgId;
var dyClassTeacher = (function(){
	
	return {
		
		/**
		 * 班级列表初始化
		 */
		init : function(){
			this.createGrid();
		},
		
		/**
		 * 创建Grid
		 */
		createGrid : function(){
			var grid_selector = "#grid-table";
			var pager_selector = "#grid-pager";
		
			jQuery(grid_selector).jqGrid({
				url : path + "/admin/classTeacher/listPage",
				datatype: "json",
				mtype :'POST',
				postData:{'orgId':_pOrgId},
				height: 450,
				colNames:[ '班级','年级',"年级id",'班主任','操作'],
				colModel:[
					{name:'className',index:'className', width:60},
					{name:'gradeName',index:'gradeName',width:60},
					{name:'gradeId',index:'gradeId',hidden:true,width:60},
					{name:'classHeadName',index:'classHeadName',width:60},
					{name:'id',index:'myop', width:150, sortable:false,formatter : function(e){
						
						var setTea = commonJs.createDefaultAuthBtn(commonJs.btn_name.set,"addClassTeacher","设置任课老师",{classId:e});
						var teaInfo = '<button class="btn btn-xs btn-info tooltip-info" dyid="classTeacherDetail" classId="'+e+'" data-rel="tooltip" data-placement="top" title="查看任课老师详情">' +
						'查看任课老师详情</button>';
						return '<div class="visible-md visible-lg hidden-sm hidden-xs btn-group">' +setTea+teaInfo+
						'</div>';
					}}
				],
		
				viewrecords : true,
				rowNum:20,
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
				caption: "<i class='icon-list'></i> 任课老师",
				autowidth: true
		
			});
		},
		reloadGridByParams : function(prams){
			$("#grid-table").jqGrid('setGridParam',{
				url : path + "/admin/classTeacher/listPage",
				datatype:'json',
				mtype :'POST',
				postData:prams,
				page:1
			}).trigger("reloadGrid");
		},
		/**
		 * 重新加载grid
		 */
		reloadGrid : function(){
			$("#grid-table").trigger("reloadGrid");
		},
		/**
		 *获取当前学期
		 */
		initSemester : function(){
			dyClassTeacher.removeSlect("currentSemester");
			$.post(path + "/admin/classTeacher/findCurSemester",{schId:_pOrgId}, function (retList) {
				if(retList != null){
					for (var i = 0; i < retList.length; i++) {
						var semester = retList[i];
						if(semester == null){
							bootbox.alert({ 
					            buttons: {  
					               ok: {  
					                    label: '确认',  
					                    className: 'btn-success'  
					                }  
					            },  
					            message: '请先设置学校的当前学期！',  
					            callback: function() {  
					                
					            },  
					            title: "提示信息", 
					        });
							return;
						}
						$("select[name='currentSemester']").append("<option value='" + semester.id + "'>" + semester.name + "</option>");
					}
				}
			});
		},
		/**
		 * 初始化年级
		 */
		initGrade : function(){
			dyClassTeacher.removeSlect("grade");
			$.post(path + "/admin/classTeacher/findGrade",{schId:_pOrgId}, function (retList) {
				if(retList != null){
					for (var i = 0; i < retList.length; i++) {
						var grade = retList[i];
						$("select[name='grade']").append("<option value='" + grade.id + "'>" + grade.name + "</option>");
					}
				}
			});
		},
		/**
		 * 根据年级获取班级
		 */
		findClassByGradeId : function(gradeId){
			dyClassTeacher.removeSlect("class");
			if(gradeId == ""){
				return;
			}
			$.post(path + "/admin/classTeacher/findClassByGradeId",{schId:_pOrgId,gradeId:gradeId}, function (retList) {
				if(retList != null){
					for (var i = 0; i < retList.length; i++) {
						var classObj = retList[i];
						$("#class").append("<option value='" + classObj.id + "'>" + classObj.name + "</option>");
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
		/**
		 * 获取查询条件
		 */
		getParamsObj : function(){
			var paramsObj = {};
			paramsObj.gradeId = $("#grade").val();
			paramsObj.classId = $("#class").val();
			paramsObj.orgId = _pOrgId;
			return paramsObj;
		}
	};
})();

$(function(){
	//首页初始化
	$('#div_main').layout({
		applyDefaultStyles: true,
		west : {
			header : "组织机构树",
			size : 260
		}
	});
	var defResultNode = _tree.initEdbTree(_schType,{}, function () {
		var nodes = _edbTreeObj.getSelectedNodes();
		var tempNode = nodes[0];
		_pOrgId = tempNode.id;
		if(_tree.isSchoolNode(tempNode)){
			$.ajaxSetup({async:false});
			dyClassTeacher.initSemester();
			dyClassTeacher.initGrade();
			dyClassTeacher.removeSlect("class");
			var params = dyClassTeacher.getParamsObj();
			dyClassTeacher.reloadGridByParams(params);
			$.ajaxSetup({async:true});
		}
	});
	_pOrgId = defResultNode.id;
	dyClassTeacher.initSemester();
	dyClassTeacher.initGrade();
	dyClassTeacher.init();

	//年级改变事件
	$("select[name='grade']").change(function () {
		var gradeId = $(this).val();
		dyClassTeacher.findClassByGradeId(gradeId);
	});

	//查询按钮单击事件
	$("#find_btn").click(function(){
		var params = {"orgId":_pOrgId};
		params.gradeId = $("select[name='grade']").val();
		params.classId = $("#class").val();
		dyClassTeacher.reloadGridByParams(params);
	});

	//设置任课老师
	$("body").on(ace.click_event,"button[dyid='addClassTeacher']", function() {
		var classId = $(this).attr("classId");
		var semesterId = $("select[name='currentSemester']").val();
		if(semesterId == null || semesterId == ""){
			bootbox.alert({ 
	            buttons: {  
	               ok: {  
	                    label: '确认',  
	                    className: 'btn-success'  
	                }  
	            },  
	            message: '请先设置学校的当前学期！',  
	            callback: function() {  
	                
	            },  
	            title: "提示信息", 
	        });
			return;
		}
		$.get(path + "/admin/classTeacher/manage-classteacher.jsp?classId="+classId +"&semesterId=" + semesterId,function(ret){
			bootbox.dialog({
				title : "设置任课老师",
				message : ret,
				buttons : {
					success : {
						label: "保存",
						className: "btn-success",
						callback :  function(result) {
							if(result) {
								var dataArray = $("#classTeacher-table").jqGrid("getRowData");
								var addDataArray = [];
								$.each(dataArray, function (index,ele) {
									if(ele.id == null || ele.id == "" || ele.id == undefined){
										addDataArray.push(ele);
									}
								});
								var dataStr = JSON.stringify(addDataArray);
								var delIds = _deleteIds.join(",");
								$.post(path+"/admin/classTeacher/add",{dataStr:dataStr,delIds:delIds}, function (addRet) {
									$.gritter.add({
										title: '设置任课老师',
										text: addRet.code == "1" ? "设置成功" : addRet.msg,
										sticky: false,
										time: ''
									});
								});
								dyClassTeacher.reloadGrid();
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

	//查看任课老师详情
	$("body").on(ace.click_event,"button[dyid='classTeacherDetail']", function() {
		var classId = $(this).attr("classId");
		$.layer({
	        type : 1,
	        title: "班主任选择",
	        closeBtn: [0, true],
	        shade: [0.8, '#000'],
	        btns : 2,
	        border: [0],
	        area: ['800px', '500px'],
	        page: {
	        	url: path + "/admin/classTeacher/classTeacherDetails.jsp?classId="+classId, 
	        	ok: function(datas){}
        	},
        	yes : function(index){
        		//关闭弹窗
				layer.close(index);
	        }
	    });
	});
});

