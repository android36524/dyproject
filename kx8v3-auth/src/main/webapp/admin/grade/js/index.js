/**
 * 年级管理的JS
 */
 
var dyGrade = (function(){
	
	return {
		
		/**
		 * 学阶管理模块初始化
		 */
		init : function(){
			this.createGrid();
		},
		
		/**
		 * 创建Grid
		 */
		createGrid : function(){
			var grid_selector = "#grade-table";
			var pager_selector = "#grade-pager";
			
			var arrColNames;
			var arrColModel=[];
			if(_flag && _flag==2){
				arrColNames = ['学校','学阶','年级名称','年级编码','科目','排序','创建人','创建时间','操作'];
				arrColModel[arrColModel.length]={name:'schId_showname',sortable:false,index:'schId',type:'text',width:120};
			}else{
				arrColNames = ['学阶','年级名称','年级编码','科目','排序','创建人','创建时间','操作'];				
			}
			var arrCM1 = [
				        {name:'stageName',index:'stageName',type:'text',width:80},
						{name:'name',index:'name',type:'text',width:80},
						{name:'code',index:'code',width:60, sorttype:"string"},
						{name:'km',index:'km',width:120, sorttype:"string"},
						{name:'seq',index:'sqe',width:40,sorttype:'string'},
						{name:'creatorName',index:'creatorName',width:80,sorttype:'string'},
						{name:'createTime',index:'createDate',width:80,sorttype:false},
						{name:'id',index:'id', width:80, sortable:false,formatter : function(e,options, rowObject){
							var editBtn = commonJs.createDefaultAuthBtn(commonJs.btn_modifycode,"modify-grade","修改",{gradeId:e});
							var delBtn = commonJs.createDefaultAuthBtn(commonJs.btn_delcode,"del-grade","删除",{gradeId:e,gradeName:rowObject["name"],flag:rowObject["flag"],code:rowObject["code"]});
							return '<div class="visible-md visible-lg hidden-sm hidden-xs btn-group">' +editBtn+delBtn+
									'</div>';
						}}
				];
			arrColModel = $.merge(arrColModel,arrCM1);		
		
			jQuery(grid_selector).jqGrid({
				url : path + "/admin/grade/listPage?schId="+_schId+"&flag="+_flag,
				datatype: "json",
				height: 450,
				colNames:arrColNames,
				colModel:arrColModel, 
				viewrecords : true,
				rowNum:30,
				rowList:[20,30,40,50],
				pager : pager_selector,
				rownumbers:true,//显示行号
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
				caption: "<i class='icon-list'></i>年级列表",
				autowidth: true
		
			});
		},
		
		/**
		 * 重新加载学阶数据
		 */
		reloadGrid : function(){
			$("#grade-table").trigger("reloadGrid");
		}
		
	};
})();


/**
 * 提交时课程值
 */
function setsubjectIdValue(){
	var subjectIds="";
	$("#subjectId option:selected").each(function(){
		subjectIds+=$(this).attr("value")+",";										
	});
	if(subjectIds.length>0){										
		subjectIds = subjectIds.substring(0,subjectIds.length-1);
		$("#hd_subjectIds").val(subjectIds);
	}
}

/**
 * 提交时年阶的值
 */
function setStageValue(){
	var stageId="";
	$("#grade_gradeId option:selected").each(function(){
		$("#grade_stageId").val($(this).attr("stageId"));
		$("#grade_code").val($(this).attr("codeId"));
	});	
}

/**
 * 设置需要提交的值
 */
function setDefualtValue(){
	setsubjectIdValue();
	setStageValue();
	$("#grade_schId").val(_schId);
}
 
$(function(){
	if($('#div_main').layout){		
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
			$("#grade-table").jqGrid('setGridParam',{
				url : path + "/admin/grade/listPage?schId="+_schId+"&flag="+_flag,
			    datatype:'json',  
			    mtype :'POST',
			    postData:{'name':$("#name").val()}, //发送数据  
			    page:1  
			}).trigger("reloadGrid"); 			
		});
		_schId=obj.id;
	}
	
	dyGrade.init();
	
	$("#btn-addgrade").on(ace.click_event,function(){
		$.post(path + "/admin/grade/toAdd?flag="+_flag+"&schId="+_schId,function(ret){
			bootbox.dialog({
				title : "新增年级",
				message : ret,
				buttons : {
					success : {
						label: "保存",
                        className: "btn-success",
                        callback :  function(result) {
							if(result) {
								if($("#grade_form").valid()){
									$.ajaxSetup({async : false});
									setDefualtValue();
									$.post(path + "/admin/grade/save",$("#grade_form").formToObject(),function(addRet){
										$.gritter.add({
											title: '年级新增',
											text: addRet.code == "1" ? "添加成功" :addRet.msg,
											sticky: false,
											time: '',
											class_name: "gritter-light"
										});
										dyGrade.reloadGrid();
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
	
	//更新年级
	$("body").on(ace.click_event,"button[dyid='modify-grade']", function() {
		var _id = $(this).attr("gradeId");
		$.get(path + "/admin/grade/toAdd?id="+_id+"&flag="+_flag+"&schId="+_schId,function(ret){
			bootbox.dialog({
				title : "更新年级",
				message : ret,
				buttons : {
					success : {
						label: "更新",
                        className: "btn-success",
                        callback :  function(result) {
							if(result) {
								if($("#grade_form").valid()){
									$.ajaxSetup({async : false});
									setDefualtValue();
									$.post(path + "/admin/grade/save", $("#grade_form").formToObject(),function(addRet){
										$.gritter.add({
											title: '年级更新',
											text: addRet.code == "1" ? "更新成功" : addRet.msg,
											sticky: false,
											time: ''
										});
										dyGrade.reloadGrid();
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
	
	// 删除年级
	$("body").on(ace.click_event,"button[dyid='del-grade']", function() {
		var _gradeId = $(this).attr("gradeId");		
		var _gradeName = $(this).attr("gradeName");
		var _flag = $(this).attr("flag");
		var _code = $(this).attr("code");
		bootbox.confirm("您将删除 "+"<font color='red' size='2px'><b>"+_gradeName+"</b></font>"+" 年级信息，删除后信息将无法恢复，请确认此次操作？",function(r){
			if(r){
				$.get(path + "/admin/grade/del",{id : _gradeId,flag:_flag,code:_code},function(delRet){					
					$.gritter.add({
						title: '年级删除',
						text: delRet.code == "1" ? "删除成功" : delRet.msg,
						sticky: false,
						time: ''
					});
					dyGrade.reloadGrid();
				});
			}
		});
	});
	
	//按条件查询菜单
	$("#find_btn").click(function(){
			$("#grade-table").jqGrid('setGridParam',{  
	        datatype:'json',  
	        mtype :'POST',
	        postData:{'gradeName':$("#gradeName").val()}, //发送数据  
	        page:1  
	    }).trigger("reloadGrid"); 
		});	
});

