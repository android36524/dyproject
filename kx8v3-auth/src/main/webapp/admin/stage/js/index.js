/**
 * 学阶管理的JS
 */
 
var dyStage = (function(){
	
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
			var grid_selector = "#stage-table";
			var pager_selector = "#stage-pager";
		
			jQuery(grid_selector).jqGrid({
				url : path + "/admin/stage/findStageByPage",
				datatype: "json",
				height: 450,
				colNames:['学阶名称','学阶代码','排序',"创建人","创建时间",'操作'],
				colModel:[
						{name:'name',index:'id',type:'text',width:40},
						{name:'code',index:'courseName',width:120, sorttype:"string"},
						{name:'seq',index:'title',width:100,sorttype:'string'},
						{name:'createName',index:'createName',width:100,sorttype:'string'},
						{name:'createTime',index:'createTime',width:100,sorttype:false},
						{name:'id',index:'id', width:80, sortable:false,formatter : function(e){
							var editBtn = commonJs.createDefaultAuthBtn(commonJs.btn_modifycode,"modify-stage","修改",{stageId:String(e)});
							var delBtn = commonJs.createDefaultAuthBtn(commonJs.btn_delcode,"del-stage","删除",{stageId:e});
							return '<div class="visible-md visible-lg hidden-sm hidden-xs btn-group">' +editBtn+delBtn+
									'</div>';
						}}
				], 
				viewrecords : true,
				rowNum:30,
				rownumbers:true,
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
				caption: "<i class='icon-list'></i>学阶列表",
				autowidth: true
		
			});
		},
		
		/**
		 * 重新加载学阶数据
		 */
		reloadGrid : function(){
			$("#stage-table").trigger("reloadGrid");
		}
		
	};
})();
 
$(function(){
	dyStage.init();
	
	$("#btn-removeStage").click(function(){
		var id = $("#stage-table").jqGrid('getGridParam','selrow');
		if(id == null){
			bootbox.alert({ 
		        buttons: {  
		           ok: {  
		                label: '确认',  
		                className: 'btn-success'  
		            }  
		        },  
		        message: "请选择你要删除的学阶！",  
		        callback: function() {  
		            
		        },  
		        title: "提示信息", 
			});
		}else{
			var rowData = $("#stage-table").jqGrid('getRowData',id);
			var stageName = rowData.name;
			bootbox.confirm("您将删除 "+"<font color='red' size='2px'><b>"+stageName+"</b></font>"+" 学阶信息，删除后信息将无法恢复，请确认此次操作？",function(r){
				if(r){
					$.post(path+"/admin/stage/deleStageInfo",{id:id},function(ret){
						$.gritter.add({
							title:'学阶删除',
							text:ret.code == "1" ? "删除学阶信息成功！"+ret.msg : "删除学阶信息失败！"+ret.msg,
							sticky: false,
							time: '' 
						});
						dyStage.reloadGrid();
					});
				}
			});
		}
	});
	
	$("#btn-addStage").on(ace.click_event,function(){
		$.post(path + "/admin/stage/stage-modify.jsp",function(ret){
			bootbox.dialog({
				title : "新增学阶",
				message : ret,
				buttons : {
					success : {
						label: "保存",
                        className: "btn-success",
                        callback :  function(result) {
							if(result) {
								if($("#stage_form").valid()){
									$.ajaxSetup({async : false});
									$.post(path + "/admin/stage/addStage",$("#stage_form").formToObject(),function(addRet){
										$.gritter.add({
											title: '学阶新增',
											text: addRet.code == "1" ? "新增学阶成功！" : "新增学阶失败！",
											sticky: false,
											time: '',
											class_name: "gritter-light"
										});
										dyStage.reloadGrid();
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
	
	//更新学阶
	$("body").on(ace.click_event,"button[dyid='modify-stage']", function() {
		var _stageId = $(this).attr("stageId");
		$.get(path + "/admin/stage/stage-modify.jsp?id="+_stageId,function(ret){
			bootbox.dialog({
				title : "更新学阶",
				message : ret,
				buttons : {
					success : {
						label: "更新",
                        className: "btn-success",
                        callback :  function(result) {
							if(result) {
								if($("#stage_form").valid()){
									$.ajaxSetup({async : false});
									$.post(path + "/admin/stage/addStage", $("#stage_form").formToObject(),function(addRet){
										$.gritter.add({
											title: '学阶更新',
											text: addRet.code == "1" ? "修改学阶信息成功！" : "修改学阶信息失败！",
											sticky: false,
											time: ''
										});
										dyStage.reloadGrid();
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
	
	//删除学阶
	$("body").on(ace.click_event,"button[dyid='del-stage']", function() {
		var stageId = $(this).attr("stageId");
		
		var deleteId = $("#stage-table").jqGrid("getGridParam","selrow");
		var rowData = $("#stage-table").jqGrid("getRowData",deleteId);
		var stageName = rowData.name;
		
		bootbox.confirm("您将删除 "+"<font color='red' size='2px'><b>"+stageName+"</b></font>"+" 学阶信息，删除后信息将无法恢复，请确认此次操作？",function(r){
			if(r){
				$.get(path +"/admin/stage/deleStageInfo",{id:stageId},function(delRet){
				//$.post(path+"/admin/stage/deleStageInfo",{id:id},function(ret){
					$.gritter.add({
						title: '学阶',
						text: delRet.code == "1" ? "删除成功" : "删除失败："+delRet.msg,
						sticky: false,
						time: ''
					});
					dyStage.reloadGrid();
				});
			}
		});
	});
	
	//按条件查询
	$("#find_btn").click(function(){
		$("#stage-table").jqGrid('setGridParam',{  
	        datatype:'json',  
	        mtype :'POST',
	        postData:{'name':$("#stageName").val()}, //发送数据  
	        page:1  
	    }).trigger("reloadGrid"); 
	});
	
});

