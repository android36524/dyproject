var _pOrgId;
var _prId;
var _level;

var dyCompany = (function(){
	
	return {
		
		/**
		 * 公司管理模块初始化
		 */
		init : function(){
			this.createGrid();
		},
		
		/**
		 * 创建Grid
		 */
		createGrid : function(){
			var grid_selector = "#company-table";
			var pager_selector = "#company-pager";
		
			jQuery(grid_selector).jqGrid({
				url : path + "/admin/companyDetail/listPage",
				datatype: "json",
				mtype :'POST',
				//postData:{'orgId':_pOrgId},
				height: 450,
				colNames:[ '公司名称','账号','组织类别','上级公司名称','负责人','负责人电话','办公电话','操作'],
				colModel:[
					{name:'orgName',index:'orgName', width:100,sortable:false},
					{name:'account_Id',index:'account_Id', width:50,sortable:false},
					{name:'orgFlag',index:'orgFlag',width:40, formatter : function(e){
						if(e == 2){
							return "公司";
						}
					}},
					{name:'parentOrg',index:'parentOrg',hidden:true, width:60},
					{name:'charge',index:'charge',width:60},
					{name:'chargePhone',index:'chargePhone', width:60},
					{name:'officeTel',index:'officeTel',width:60},
					{name:'id',index:'myop', width:50, sortable:false,formatter : function(e){
						
						var editBtn = commonJs.createDefaultAuthBtn(commonJs.btn_modifycode,"modify-company","修改",{companyId:e});
						var delBtn = commonJs.createDefaultAuthBtn(commonJs.btn_delcode,"del-company","删除",{companyId:e});
						return '<div class="visible-md visible-lg hidden-sm hidden-xs btn-group">' +editBtn+delBtn+
								'</div>';
						
					}}
				],
		
				viewrecords : true,
				rowNum:20,
				rowList:[20,30,40,50],
				pager : pager_selector,
				altRows: true,
				rownumbers:true,
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
				caption: "<i class='icon-list'></i> 公司基础信息",
				autowidth: true
		
			});
		},
		reloadGridByParams : function(prams){
			$("#company-table").jqGrid('setGridParam',{
				url : path + "/admin/companyDetail/listPage",
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
			$("#company-table").trigger("reloadGrid");
		}
	};
})();


$(function(){
	
//	$('#div_main').layout({
//		applyDefaultStyles: true,
//		west : {
//			header : "组织机构树",
//			size : 260
//		}
//	});
//	var defResultNode = _tree.initEdbTree(_edbType,{}, function () {
//		var nodes = _edbTreeObj.getSelectedNodes();
//		var tempNode = nodes[0];
//		_pOrgId = tempNode.id;
//		_prId = tempNode.pId;
//		_level = tempNode.level;
		//alert(_prId+"=="+_pOrgId+"=="+_level);
//		if(_tree.isEdbNode(tempNode)){
//			var params = {"orgId":_pOrgId};
//			dyCompany.reloadGridByParams(params);
//		}
//	});
//	_pOrgId = defResultNode.id;
	//alert("_pOrgId:"+_pOrgId);
	dyCompany.init();
	
	//新增公司信息
	$("#btn-addCompany").on(ace.click_event, function() {
		flageId = "0";//0表示新增公司
		//alert(flageId);
		$.post(path + "/admin/companyDetail/company-modify.jsp?flageId="+flageId,function(ret){
			bootbox.dialog({
				title : "新增公司信息",
				width: 2000,
				message : ret,
				buttons : {
					success : {
						label: "保存",
                        className: "btn-success",
                        callback :  function(result) {
							if(result) {
								if($("#company-form").valid()){
									$.ajaxSetup({async : false});
									$.post(path + "/admin/companyDetail/toAdd?flageId="+flageId,$("#company-form").formToObject(),function(addRet){
										$.gritter.add({
											title: '新增公司',
											text: addRet.code == "1" ? "添加成功" : "添加失败",
											sticky: false,
											time: '',
											class_name: "gritter-light"
										});
									});
									$.ajaxSetup({async : true});
									dyCompany.reloadGrid();
									//refreshTreeNode();
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
	
	//根据公司名称模糊查询
	$("#find_btn").click(function(){
		var companyName = $("#companyName").val();
			$("#company-table").jqGrid('setGridParam',{  
	        datatype:'json',  
	        mtype :'POST',
	        url : path + "/admin/companyDetail/queryByCompanyName",
	        postData:{'companyName':companyName}, //发送数据  
	        page:1  
	    }).trigger("reloadGrid"); 
	});
	
	//修改公司信息
	$("body").on(ace.click_event,"button[dyid='modify-company']", function() {
		var companyId = $(this).attr("companyId");
		flageId = "1";
		if(companyId==_pOrgId||companyId==2){
			if(companyId==2){
				bootbox.alert({ 
		            buttons: {  
		               ok: {  
		                    label: '确认',  
		                    className: 'btn-success'  
		                }  
		            },  
		            message: '此节点不允许修改！！',  
		            callback: function() {  
		                
		            },  
		            title: "提示信息", 
		        });
				return;
			}else{
				bootbox.alert({ 
		            buttons: {  
		               ok: {  
		                    label: '确认',  
		                    className: 'btn-success'  
		                }  
		            },  
		            message: '此节点不允许修改,请先点击上级节点再进行修改操作！！',  
		            callback: function() {  
		                
		            },  
		            title: "提示信息", 
		        });
				return;
			}
			
		}
		$.get(path + "/admin/companyDetail/company-modify.jsp?id="+companyId+"&flageId="+flageId,function(ret){
			bootbox.dialog({
				title : "更新公司基础信息",
				message : ret,
				buttons : {
					success : {
						label: "更新",
                        className: "btn-success",
                        callback :  function(result) {
							if(result) {
								if($("#company-form").valid()){
									$.ajaxSetup({async : false});
									$.post(path + "/admin/companyDetail/toAdd?flageId="+flageId,$("#company-form").formToObject(),function(addRet){
										$.gritter.add({
											title: '公司更新',
											text: addRet.code == "1" ? "更新成功" : "更新失败",
											sticky: false,
											time: ''
										});
										dyCompany.reloadGrid();
										//refreshTreeNode();
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
	
	//删除公司
	$("body").on(ace.click_event,"button[dyid='del-company']",function(){
		var companyId = $(this).attr("companyId");
		if(companyId==_pOrgId||companyId==2){
			if(companyId==2){
				bootbox.alert({ 
		            buttons: {  
		               ok: {  
		                    label: '确认',  
		                    className: 'btn-success'  
		                }  
		            },  
		            message: '此节点不允许删除！！',  
		            callback: function() {  
		                
		            },  
		            title: "提示信息", 
		        });
				return;
			}else{
				bootbox.alert({ 
		            buttons: {  
		               ok: {  
		                    label: '确认',  
		                    className: 'btn-success'  
		                }  
		            },  
		            message: '此节点不允许删除,请先点击上级节点再进行删除操作！！',  
		            callback: function() {  
		                
		            },  
		            title: "提示信息", 
		        });
				return;
			}
			
		}
		bootbox.confirm("确认删除该公司吗？",function(r){
			if(r){
				$.get(path + "/admin/companyDetail/del",{id : companyId},function(delRet){
					$.gritter.add({
						title: '公司删除',
						text: delRet.code == "1" ? "删除成功" : delRet.msg,
						sticky: false,
						time: ''
					});
					dyCompany.reloadGrid();
					//refreshTreeNode();
				});
			}
		});
	});
	
});


function refreshTreeNode(){
    _paramsObj.treeFlag = _treeFlag;
    $.ajaxSetup({async:false});
    $.post(path + "/admin/orgTree/list",_paramsObj,function(ret){
        _edbTreeObj = $.fn.zTree.init($("#edbTree"),treeSetting,ret);
    });
    _edbTreeObj.expandAll(true);
    $.ajaxSetup({async:true});
}