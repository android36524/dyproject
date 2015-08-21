/**
 * 年级管理的JS
 */
 
var dyPress = (function(){
	
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
			var grid_selector = "#press-table";
			var pager_selector = "#press-pager";
		
			jQuery(grid_selector).jqGrid({
				url : path + "/admin/press/listPage",
				datatype: "json",
				height: 450,
				colNames:['出版社','编码','创建人','创建时间','操作'],
				colModel:[
						{name:'name',index:'name',type:'text',width:100},
						{name:'code',index:'code',width:60, sorttype:"string"},
						//{name:'seq',index:'sqe',width:100,sorttype:'string'},
						{name:'creatorName',index:'creatorName',width:100,sorttype:'string'},
						{name:'createTime',index:'createTime',width:100,sorttype:false},
						{name:'id',index:'id', width:80, sortable:false,formatter : function(e,options, rowObject){
							var editBtn = commonJs.createDefaultAuthBtn(commonJs.btn_modifycode,"modify-press","修改",{pressId:e});
							var delBtn = commonJs.createDefaultAuthBtn(commonJs.btn_delcode,"del-press","删除",{pressId:e});
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
				caption: "<i class='icon-list'></i>出版社管理列表",
				autowidth: true
		
			});
		},
		
		/**
		 * 重新加载学阶数据
		 */
		reloadGrid : function(){
			$("#press-table").trigger("reloadGrid");
		}
		
	};
})();
 
$(function(){
	dyPress.init();
	
	$("#btn-addpress").on(ace.click_event,function(){
		$.post(path + "/admin/press/press-modify.jsp",function(ret){
			bootbox.dialog({
				title : "新增出版社",
				message : ret,
				buttons : {
					success : {
						label: "保存",
                        className: "btn-success",
                        callback :  function(result) {
							if(result) {
								if($("#press_form").valid()){
									$.ajaxSetup({async : false});
									$.post(path + "/admin/press/save",$("#press_form").formToObject(),function(addRet){
										$.gritter.add({
											title: '出版社',
											text: addRet.code == "1" ? "添加成功" : "添加失败:"+addRet.msg,
											sticky: false,
											time: '',
											class_name: "gritter-light"
										});
										dyPress.reloadGrid();
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
	$("body").on(ace.click_event,"button[dyid='modify-press']", function() {
		var _id = $(this).attr("pressId");
		$.get(path + "/admin/press/press-modify.jsp?id="+_id,function(ret){
			bootbox.dialog({
				title : "更新出版社",
				message : ret,
				buttons : {
					success : {
						label: "更新",
                        className: "btn-success",
                        callback :  function(result) {
							if(result) {
								if($("#press_form").valid()){
									$.ajaxSetup({async : false});
									$.post(path + "/admin/press/save", $("#press_form").formToObject(),function(addRet){
										$.gritter.add({
											title: '出版社',
											text: addRet.code == "1" ? "更新成功" : "更新失败:"+addRet.msg,
											sticky: false,
											time: ''
										});
										dyPress.reloadGrid();
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
	
	//删除出版社
	$("body").on(ace.click_event,"button[dyid='del-press']",function(){
		var pressId = $(this).attr("pressId");
		
		var deleteId = $("#press-table").jqGrid("getGridParam","selrow");
		var rowData = $("#press-table").jqGrid("getRowData",deleteId);
		var pressName = rowData.name;
		
		bootbox.confirm("您将删除 "+"<font color='red' size='2px'><b>"+pressName+"</b></font>"+" 出版社信息，删除后信息将无法恢复，请确认此次操作？",function(r){
			if(r){
				$.get(path + "/admin/press/del",{id : pressId},function(delRet){
					$.gritter.add({
						title: '出版社',
						text: delRet.code == "1" ? "删除成功" : "删除失败:"+delRet.msg,
						sticky: false,
						time: ''
					});
					dyPress.reloadGrid();
				});
			}
		});
	});
	
	//按条件查询菜单
	$("#find_btn").click(function(){
			$("#press-table").jqGrid('setGridParam',{  
	        datatype:'json',  
	        mtype :'POST',
	        postData:{'pressName':$("#pressName").val()}, //发送数据  
	        page:1  
	    }).trigger("reloadGrid"); 
		});
	
});

