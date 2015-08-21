
/**
 * 教育局管理的JS
 */
var _pOrgId;
var _prId;
var _level;
var flageId;
var _orgLevel;//机构层级
var dyEdb = (function(){
	
	return {
		
		/**
		 * 教育局管理模块初始化
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
				url : path + "/admin/edb/listPage",
				datatype: "json",
				mtype :'POST',
				postData:{'orgId':_pOrgId},
				height: 400,
				colNames:[ '教育局名称','教育局编码','账号','负责人','负责人电话','上级主管机构','状态','创建人','创建时间','操作'],
				colModel:[
					{name:'orgName',index:'orgName', width:60,sortable:false,formatter : function(j,options,rowObject){
                    	return '<a href="javascript:showDetail('+rowObject.id+');">'+rowObject.orgName+'</a>';
                    }},
					{name:'orgCode',index:'orgCode',width:60},
					{name:'account',index:'account',width:60},
					{name:'charge',index:'charge',width:60},
					{name:'chargePhone',index:'chargePhone', width:60},
					{name:'parentOrg',index:'parentOrg', width:60},
					{name:'status',index:'status',width:40, formatter : function(e,options, rowObject){
						var status = rowObject.status;
						if(1 == status){
							return "<span style='color: #00AA00'>启用</span>";
						}else{
							return "<span style='color: red'>禁用</span>";
						}
					}},
					{name:'creatorName',index:'creatorName', width:40},
					{name:'createTime',index:'createTime', width:60},
					{name:'id',index:'myop', width:150, sortable:false,formatter : function(e,options, rowObject){
						var editBtn = commonJs.createDefaultAuthBtn(commonJs.btn_modifycode,"modify-edb","修改",{edbId:e});
						var delBtn = commonJs.createDefaultAuthBtn(commonJs.btn_delcode,"del-edb","删除",{edbId:e});
						
						var s = '<div class="visible-md visible-lg hidden-sm hidden-xs btn-group">' +editBtn+delBtn;
						if('9' == rowObject.status){
							var enableBtn = commonJs.createDefaultAuthBtn(commonJs.btn_name.enable,"enable-edb","启用",{edbId:e});
							s += enableBtn;
						}else if('1'==rowObject.status){
							var disableBtn = commonJs.createDefaultAuthBtn(commonJs.btn_name.disable,"disabled-edb","禁用",{edbId:e});
							s += disableBtn;
						}
						s+='</div>';
						return s;
						
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
				caption: "<i class='icon-list'></i> 教育局",
				autowidth: true
		
			});
		},
		reloadGridByParams : function(prams){
			$("#grid-table").jqGrid('setGridParam',{
				url : path + "/admin/edb/listPage",
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
		}
	};
})();
 
function getStatus(e){
	var rowData = $("#grid-table").jqGrid("getRowData",e);
	alert($.toJSON(rowData));
}

$(function(){
	$('#div_main').layout({
		applyDefaultStyles: true,
		west : {
			header : "组织机构树",
			size : 260
		}
	});
	var defResultNode = _tree.initEdbTree(_edbType,{}, function () {
		var nodes = _edbTreeObj.getSelectedNodes();
		var tempNode = nodes[0];
		_pOrgId = tempNode.id;
		_prId = tempNode.pId;
		_level = tempNode.level;
		_orgLevel = tempNode.orgLevel;
		if(_tree.isEdbNode(tempNode)){
			var params = {"orgId":_pOrgId};
			dyEdb.reloadGridByParams(params);
		}
	});
	var level_onload = defResultNode.level;
	_pOrgId = defResultNode.id;
	_orgLevel = defResultNode.orgLevel;
	dyEdb.init();
	//新增教育局
	$("#btn-addEdb").on(ace.click_event, function() {
		/*var provenceLevel = "8601";//层级编码 省
		 var cityLevel = "8602";//层级编码 市
		 var areaLevel = "8603";//层级编码 区





		 var levelFlag;
		 flageId = "0";
		 if(_level==0){
		 levelFlag=provenceLevel;
		 }
		 if(_level==1){
		 levelFlag=cityLevel;
		 }
		 if(_level==2){
		 levelFlag=areaLevel;
		 }
		 if(_level==3||_level==undefined&&_prId==undefined){
		 if(level_onload==3){
		 bootbox.alert({
		 buttons: {
		 ok: {
		 label: '确认',
		 className: 'btn-success'
		 }
		 },
		 message: '已经是区县级别教育局了，不能添加教育局了！！',
		 callback: function() {

		 },
		 title: "提示信息",
		 });
		 return ;
		 }
		 }
		 */
		$.post(path + "/admin/edb/toAdd", function(ret){
			bootbox.dialog({
				title : "新增教育局",
				message : ret,
				buttons : {
					success : {
						label: "保存",
                        className: "btn-success",
                        callback :  function(result) {
							if(result) {
								if($("#edb-form").valid()){
									var account = $("#account").val();
									//$.ajaxSetup({async : false});
									var params = $("#edb-form").formToObject();
									params["edb.provinceCode"] = $("#edb_provinceCode").val();
									params["edb.cityCode"] = $("#edb_cityCode").val();
									params["edb.areaCode"] = $("#edb_areaCode").val();
									$.post(path + "/admin/edb/add",params,function(addRet){
										$.gritter.add({
											title: '教育局新增',
											text: addRet.code == "1" ? "添加成功" : addRet.msg,
											sticky: false,
											time: '',
											class_name: "gritter-light"
										});
										dyEdb.reloadGrid();
										refreshTreeNode();
										/**/
									});
									//$.ajaxSetup({async : true});
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
	
	//更新教育局
	$("body").on(ace.click_event,"button[dyid='modify-edb']", function() {
		var edbId = $(this).attr("edbId");
		if(edbId==1){
			bootbox.alert({ 
	            buttons: {  
	               ok: {  
	                    label: '确认',  
	                    className: 'btn-success'  
	                }  
	            },  
	            message: '顶级教育局不允许修改！！',  
	            callback: function() {  
	                
	            },  
	            title: "提示信息", 
	        });
			return;
		}
		flageId = "1";
		$.get(path + "/admin/edb/edb-modify.jsp?id="+edbId+"&flageId="+flageId,function(ret){
			bootbox.dialog({
				title : "更新教育局基本信息",
				message : ret,
				buttons : {
					success : {
						label: "更新",
                        className: "btn-success",
                        callback :  function(result) {
							if(result) {
								if($("#edb-form").valid()){
									$.ajaxSetup({async : false});
									$.post(path + "/admin/edb/add",$("#edb-form").formToObject(),function(addRet){
										$.gritter.add({
											title: '教师更新',
											text: addRet.code == "1" ? "更新成功" : "更新失败",
											sticky: false,
											time: ''
										});
										dyEdb.reloadGrid();
										refreshTreeNode();
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
	
	//删除教育局
	$("body").on(ace.click_event,"button[dyid='del-edb']",function(){
		var edbId = $(this).attr("edbId");
		bootbox.confirm("确认删除该教育局吗？",function(r){
			if(r){
				$.get(path + "/admin/edb/del",{id : edbId},function(delRet){
					$.gritter.add({
						title: '教育局删除',
						text: delRet.code == "1" ? "删除成功" : delRet.msg,
						sticky: false,
						time: ''
					});
					dyEdb.reloadGrid();
					refreshTreeNode();
				});
			}
		});
	});
	
	//禁用教育局
	$("body").on(ace.click_event,"button[dyid='disabled-edb']",function(){
		var edbId = $(this).attr("edbId");
		bootbox.confirm("确认禁用该教育局吗?",function(r){
			if(r){
				$.get(path + "/admin/edb/updateStatus",{id : edbId,flag:"disabled"},function(Ret){
					$.gritter.add({
						title: '禁用教育局',
						text: Ret.code == "1" ? "禁用成功" : "禁用失败",
						sticky: false,
						time: ''
					});
					dyEdb.reloadGrid();
				});
			}
		});
	});
	
	//启用教育局
	$("body").on(ace.click_event,"button[dyid='enable-edb']",function(){
		var edbId = $(this).attr("edbId");
		bootbox.confirm("确认启用该教育局吗?",function(r){
			if(r){
				$.get(path + "/admin/edb/updateStatus",{id : edbId,flag:"enabled"},function(Ret){
					$.gritter.add({
						title: '启用教育局',
						text: Ret.code == "1" ? "启用成功" : "启用失败",
						sticky: false,
						time: ''
					});
					dyEdb.reloadGrid();
				});
			}
		});
	});
	
	//按条件查询菜单
	$("#find_btn").click(function(){		
		var params = {"orgId":_pOrgId};
		params.status = $("select[name='status']").val();
		dyEdb.reloadGridByParams(params);
	});
});

/**
 * 查看详情
 */
function showDetail(id){
	 var edbId = id;
     $.get(path + "/admin/edb/edb-showInfo.jsp?id="+edbId,function(ret){
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


function refreshTreeNode(){
    _paramsObj.treeFlag = _treeFlag;
    $.ajaxSetup({async:false});
    $.post(path + "/admin/orgTree/list",_paramsObj,function(ret){
        _edbTreeObj = $.fn.zTree.init($("#edbTree"),treeSetting,ret);
    });
    _edbTreeObj.expandAll(true);
    $.ajaxSetup({async:true});
}
