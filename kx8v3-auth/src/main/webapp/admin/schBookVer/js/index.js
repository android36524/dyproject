/**
 * 年级管理的JS
 */
 
var dySchBookVer = (function(){
	
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
			var grid_selector = "#schBookVer-table";
			var pager_selector = "#schBookVer-pager";
		
			jQuery(grid_selector).jqGrid({
				url : path + "/admin/schBookVer/listPage",
				datatype: "json",
				height: 450,
				colNames:['教材版本名称','出版社','出版日期','创建人','创建时间','操作'],
				colModel:[
						{name:'name',index:'name',type:'text',width:100},
						{name:'pressName',index:'pressName',width:60, sorttype:"string"},
						{name:'pressTime',index:'pressTime',width:100,sorttype:false},
						{name:'creatorName',index:'creatorName',width:100,sorttype:'string'},
						{name:'createTime',index:'createTime',width:100,sorttype:false},
						{name:'id',index:'id', width:80, sortable:false,formatter : function(e,options, rowObject){
							var editBtn = commonJs.createDefaultAuthBtn(commonJs.btn_modifycode,"modify-schBookVer","修改",{schBookVerId:e});
							var delBtn = commonJs.createDefaultAuthBtn(commonJs.btn_delcode,"del-schBookVer","删除",{schBookVerId:e});
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
						$('button[schBookVerid]').tooltip({container : 'body'});
					}, 0);
				},
				caption: "<i class='icon-list'></i>教材版本列表",
				autowidth: true
		
			});
		},
		
		/**
		 * 重新加载出版社版本数据
		 */
		reloadGrid : function(){
			$("#schBookVer-table").trigger("reloadGrid");
		}
		
	};
})();
 
$(function(){
	dySchBookVer.init();
	
	$("#btn-addschBookVer").on(ace.click_event,function(){
		$.post(path + "/admin/schBookVer/schBookVer-modify.jsp",function(ret){
			bootbox.dialog({
				title : "新增教材版本",
				message : ret,
				buttons : {
					success : {
						label: "保存",
                        className: "btn-success",
                        callback :  function(result) {
							if(result) {
								if($("#schBookVer_form").valid()){
									$.ajaxSetup({async : false});
									$.post(path + "/admin/schBookVer/save",$("#schBookVer_form").formToObject(),function(addRet){
										$.gritter.add({
											title: '教材版本',
											text: addRet.code == "1" ? "添加成功" : "添加失败:"+addRet.msg,
											sticky: false,
											time: '',
											class_name: "gritter-light"
										});
										dySchBookVer.reloadGrid();
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
	
	//更新出版社版本
	$("body").on(ace.click_event,"button[dyid='modify-schBookVer']", function() {
		var _id = $(this).attr("schBookVerId");
		$.get(path + "/admin/schBookVer/schBookVer-modify.jsp?id="+_id,function(ret){
			bootbox.dialog({
				title : "更新教材版本",
				message : ret,
				buttons : {
					success : {
						label: "更新",
                        className: "btn-success",
                        callback :  function(result) {
							if(result) {
								if($("#schBookVer_form").valid()){
									$.ajaxSetup({async : false});
									$.post(path + "/admin/schBookVer/save", $("#schBookVer_form").formToObject(),function(addRet){
										$.gritter.add({
											title: '教材版本',
											text: addRet.code == "1" ? "更新成功" : "更新失败:"+addRet.msg,
											sticky: false,
											time: ''
										});
										dySchBookVer.reloadGrid();
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
	$("body").on(ace.click_event,"button[dyid='del-schBookVer']",function(){
		var schBookVerId = $(this).attr("schBookVerId");
		
		var id=$("#schBookVer-table").jqGrid("getGridParam","selrow");
		var rowData = $("#schBookVer-table").jqGrid("getRowData",id);
		var schoolBookVerName = rowData.name;
		
		bootbox.confirm("您将删除 "+"<font color='red' size='2px'><b>"+schoolBookVerName+"</b></font>"+" 教材版本信息，删除后信息将无法恢复，请确认此次操作？",function(r){
			if(r){
				$.get(path + "/admin/schBookVer/del",{id : schBookVerId},function(delRet){
					$.gritter.add({
						title: '教材版本',
						text: delRet.code == "1" ? "删除成功" : "删除失败:"+delRet.msg,
						sticky: false,
						time: ''
					});
					dySchBookVer.reloadGrid();
				});
			}
		});
	});
	
	//按条件查询菜单
	$("#find_btn").click(function(){
			$("#schBookVer-table").jqGrid('setGridParam',{  
	        datatype:'json',  
	        mtype :'POST',
	        postData:{'schBookVerName':$("#schBookVerName").val()}, //发送数据  
	        page:1  
	    }).trigger("reloadGrid"); 
		});
	
});

