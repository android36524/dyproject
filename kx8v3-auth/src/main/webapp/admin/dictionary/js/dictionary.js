var usertypes=[{value:1,name:"普通用户"},{value:3,name:"bb"}];

var dydictype = (function(){
		
	return {
		
		/**
		 * 菜单管理模块初始化
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
				url : path + "/admin/dictionary/listDictionaryType",
				datatype: "json",
				height: 526,
				colNames:['字典类型名称','字典类型编码', '操作','描述'],
				colModel:[
									
					{name:'name',index:'name',width:120, sortable:false},
					{name:'code',index:'code',width:120, sortable:false},
				
					{name:'id',index:'myop', width:150, sortable:false,formatter : function(cellvalue,options, rowObject){
						return '<div class="visible-md visible-lg hidden-sm hidden-xs btn-group">' +
									'<button class="btn btn-xs btn-info tooltip-info" dyid="update-dictionarytype" dictionarytypeid="'+cellvalue+'"  dictionarytypename="'+rowObject["name"]+'" data-rel="tooltip" data-placement="top" title="修改字典类型">' +
									'<i class="icon-edit bigger-120"></i></button>' +
									'<button class="btn btn-xs btn-add tooltip-error" dyid="add-dictionary" dyrowid='+options["rowId"]+' dictionarytypeid="'+cellvalue+'" dictionarytypename="'+rowObject["name"]+'" data-rel="tooltip" data-placement="top" title="新增字典">' +
									'<i class="icon-plus bigger-120"></i></button>' +
								'</div>';
					}},
					{name:'desciption',index:'desciption',width:120, sorttype:"string"}
				], 
		
				altRows: true,
				
				multiselect: false,
		        multiboxonly: true,
		        mtype: "POST",      
		       	rowNum:15,
				rowList:[15,30,40,50],
				pager : pager_selector,		      
		         loadComplete : function() {
					var table = this;
					setTimeout(function(){
						dyjsCommon.enableTooltips(table);
						dyjsCommon.updatePagerIcons(table);						
					}, 0);
				},       
				subGrid: true,
				subGridRowExpanded: function(subgrid_id, row_id) {
					var subgrid_table_id;
					
					subgrid_table_id = subgrid_id+"_t";	
					
					$("#"+subgrid_id).html("<table id='"+subgrid_table_id+"' ></table>");
					var dydic = (function(){						
						return {							
							/**
							 * 菜单管理模块初始化
							 */
							init : function(){
								this.createGrid();
							},
							createGrid : function(){
								
								jQuery("#"+subgrid_table_id).jqGrid({
									url:path + "/admin/dictionary/query?typeid="+row_id,
									datatype: "json",
									colNames:['字典名称','字典编码', '字典值 ','上级字典类型','上级字典值','操作'],
									colModel:[
														
										{name:'name',index:'name',width:120, sorttype:"string"},
										{name:'code',index:'code',width:120, sorttype:"string"},
										{name:'value',index:'value',width:100, sorttype:"string"},
										{name:'pdictypeId',index:'pdictypeId',width:120, sorttype:"string",formatter : function(cellvalue){
											$.ajaxSetup({async : false});
											var name ='';											
											if(cellvalue > 0){												
												$.get(path + "/admin/dictionary/queryDictionaryTypeById?id="+cellvalue,function(ret){
													if(ret){		
														name = ret.name;													
													}
												});
											}
											return '<span class="label label-warning">'+name+'</span>';
											$.ajaxSetup({async : true});
										}},
										{name:'pdicValue',index:'pdicid',width:100, sorttype:"string",formatter : function(cellvalue,options, rowObject){
											$.ajaxSetup({async : false});
											var name ='';
											var pdictype= rowObject["pdictypeId"];
											if(cellvalue > 0 && pdictype>0){
												$.get(path + "/admin/dictionary/queryByPtypeAndValue?value="+cellvalue+"&typeid="+pdictype,function(ret){
													if(ret){		
														name = ret.name;													
													}
												});
											}
											return '<span class="label label-warning">'+name+'</span>';
											$.ajaxSetup({async : true});
										}},
										{name:'id',index:'myop', width:100, sortable:false,formatter : function(cellvalue,options, rowObject){
											return '<div class="visible-md visible-lg hidden-sm hidden-xs btn-group">' +
														'<button class="btn btn-xs btn-info tooltip-info" dyid="update-dictionary" dictionaryid="'+cellvalue+'"  dictionaryname="'+rowObject["name"]+'" subgridname='+subgrid_table_id+' data-rel="tooltip" data-placement="top" title="修改字典">' +
														'<i class="icon-edit bigger-120"></i></button>' 
													'</div>';
										}}
									], 
								   		   	
								   	sortname: 'name',
								    sortorder: "asc",
								     height:"90%",
								    altRows: true,
									rowNum:100,
								    multiselect: false,
		                            multiboxonly: true,
								    mtype: "POST",   
								    autowidth:true
								});
							},
							/**
							 * 重新加载grid
							 */
							reloadGrid : function(){
								$("#"+subgrid_table_id).trigger("reloadGrid");
							}
						};						
					})();
				
					dydic.init();
					
				},
				subGridRowColapsed: function(subgrid_id, row_id) {
					// this function is called before removing the data
					//var subgrid_table_id;
					//subgrid_table_id = subgrid_id+"_t";
					//jQuery("#"+subgrid_table_id).remove();
				},


				loadComplete : function() {
					var table = this;
					setTimeout(function(){
						dyjsCommon.enableTooltips(table);
						dyjsCommon.updatePagerIcons(table);
						//$('button[userid]').tooltip({container : 'body'});
					}, 0);
				},
				caption: "<i class='icon-list'></i> 字典类型列表",
		
				autowidth: true
		
			});
		},
		
		/**
		 * 重新加载grid
		 */
		reloadGrid : function(){
			$("#grid-table").trigger("reloadGrid");
		}
	};
})();


$(function(){
	dydictype.init();
	//新增字典类型
	$("#btn-addDictionarytype").on(ace.click_event, function() {		
		$.get(path + "/admin/dictionary/dictionaryTypeInfo.jsp",function(ret){
			bootbox.dialog({
				title : "新增字典类型",
				message : ret,
				buttons : {
					success : {
						label: "保存",
                        className: "btn-success",
                        callback :  function(result) {
                        	
							if(result) {
								if($("#dictionarytype-form").valid()){
									$.ajaxSetup({async : false});
									$.post(path + "/admin/dictionary/addDictionaryType",$("#dictionarytype-form").formToObject(),function(addRet){
										$.gritter.add({
											title: '字典类型新增',
											text: addRet.code == "1" ? "添加成功" : addRet.msg,
											sticky: false,
											time: '',
											class_name: "gritter-light"
										});
										dydictype.reloadGrid();
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
	
	//修改字典类型
	$("body").on(ace.click_event,"button[dyid='update-dictionarytype']", function() {
		var typeid = $(this).attr("dictionarytypeid");
		$.get(path + "/admin/dictionary/dictionaryTypeInfo.jsp?id="+typeid,function(ret){
			bootbox.dialog({
				title : "更新字典类型",
				message : ret,
				buttons : {
					success : {
						label: "更新",
                        className: "btn-success",
                        callback :  function(result) {
                        
							if(result) {
								
								if($("#dictionarytype-form").valid()){
									$.ajaxSetup({async : false});
									$.post(path + "/admin/dictionary/addDictionaryType",$("#dictionarytype-form").formToObject(),function(addRet){
										
										$.gritter.add({
											title: '字典类型更新',
											text: addRet.code == "1" ? "更新成功" : addRet.msg,
											sticky: false,
											time: '',
											class_name: "gritter-light"
										});
										dydictype.reloadGrid();
										
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
	
	//新增字典
	$("body").on(ace.click_event,"button[dyid='add-dictionary']", function() {
		var typeid = $(this).attr("dictionarytypeid");
		
		var subgrid_id = "grid-table_" +$(this).attr("dyrowid")+"_t";
		
		$.get(path + "/admin/dictionary/dictionaryInfo.jsp?typeid="+typeid,function(ret){
			bootbox.dialog({
				title : "新增字典",
				message : ret,
				buttons : {
					success : {
						label: "保存",
                        className: "btn-success",
                        callback :  function(result) {
							if(result) {
								if($("#dictionary-form").valid()){
									$.ajaxSetup({async : false});
									$.post(path + "/admin/dictionary/add",$("#dictionary-form").formToObject(),function(addRet){
										$.gritter.add({
											title: '字典新增',
											text: addRet.code == "1" ? "新增成功" : addRet.msg,
											sticky: false,
											time: '',
											class_name: "gritter-light"
										});
										//dydictype.reloadGrid();
										if($("#"+subgrid_id)){
											$("#"+subgrid_id).trigger("reloadGrid");
										}
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
	
	//修改字典
	$("body").on(ace.click_event,"button[dyid='update-dictionary']", function() {
		var id = $(this).attr("dictionaryid");
		var subgridname = $(this).attr("subgridname");
		$.get(path + "/admin/dictionary/dictionaryInfo.jsp?id="+id,function(ret){
			bootbox.dialog({
				title : "更新字典",
				message : ret,
				buttons : {
					success : {
						label: "更新",
                        className: "btn-success",
                        callback :  function(result) {
							if(result) {
								if($("#dictionary-form").valid()){
									$.ajaxSetup({async : false});
									$.post(path + "/admin/dictionary/add",$("#dictionary-form").formToObject(),function(addRet){
										$.gritter.add({
											title: '字典更新',
											text: addRet.code == "1" ? "更新成功" : addRet.msg,
											sticky: false,
											time: '',
											class_name: "gritter-light"
										});
										//dydictype.reloadGrid();
										$("#"+subgridname).trigger("reloadGrid");
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
	
    //删除字典
	$("body").on(ace.click_event,"button[dyid='del-dictionary']",function(){
		var dictionaryid = $(this).attr("dictionaryid");
		var dictionaryname = $(this).attr("dictionaryname");
		
		bootbox.confirm("确认删除“"+dictionaryname+"”吗？",function(r){
			if(r){
				$.get(path + "/admin/dictionary/del",{id : dictionaryid},function(delRet){
					$.gritter.add({
						title: '字典删除',
						text: delRet.code == "1" ? "删除成功" : "删除失败",
						sticky: false,
						time: ''
					});
					dydictype.reloadGrid();
				});
			}
		});
	});
	
});

