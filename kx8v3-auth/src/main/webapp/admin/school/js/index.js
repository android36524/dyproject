/**
 * 学校基础信息管理的JS
 */
var _pOrgId;
var dySchool = (function(){
	
	return {
		
		/**
		 * 学校基础信息模块初始化
		 */
		init : function(){
			this.createGrid();
		},
		
		/**
		 * 创建Grid
		 */
		createGrid : function(){
			var grid_selector = "#school-table";
			var pager_selector = "#school-pager";
		
			jQuery(grid_selector).jqGrid({
				url : path + "/admin/school/listSchoolByPage",
				datatype: "json",
				height: 400,
				postData:{'orgId':_pOrgId},
				colNames:['学校名称','学校编码','账号','建校时间','所属教育局','校长姓名','学校联系电话','创建人','创建时间','状态','操作'],
				colModel:[
						{name:'orgName',index:'orgName',width:120, sortable:false,formatter : function(j,options,rowObject){
	                    	return '<a href="javascript:showDetail('+rowObject.id+');">'+rowObject.orgName+'</a>';
	                    }},
						{name:'orgCode',index:'orgCode',width:130, sorttype:"string"},
						{name:'account',index:'account',width:60},
						{name:'buildSchoolTime',index:'buildSchoolTime',width:80,sorttype:false},
						{name:'edbName',index:'edbName',type:'text',width:120},
						{name:'schoolLeaderName',index:'schoolLeaderName',type:'text',width:80},
						{name:'schoolPhoneNumber',index:'schoolPhoneNumber',type:'text',width:100},
						{name:'createName',index:'createName',width:80,sorttype:'string'},
						{name:'createTime',index:'createTime',width:125,sorttype:false},
						{name:'status',index:'status',width:60,formatter : function(e, options, rowObject){
							var status = rowObject.status;
							if(1 == status){
								return "<span style='color: #00AA00'>启用</span>";
							}else{
								return "<span style='color: #FF0000'>禁用</span>";
							}
						}},
						{name:'id',index:'id', width:120, sortable:false,formatter : function(e, options, rowObject){
							
							var editBtn = commonJs.createDefaultAuthBtn(commonJs.btn_modifycode,"modify-school","修改",{schoolId:e});
							var delBtn = commonJs.createDefaultAuthBtn(commonJs.btn_delcode,"del-school","删除",{schoolId:e, schoolName:rowObject.orgName});
							
							
							var s ='';
							var status = rowObject.status;
							if('9' == status){
//								s += '<button class="btn btn-xs btn-success tooltip-error" dyid="changeStatus-school" schoolId="'+String(e)+'"  data-rel="tooltip" data-placement="top" title="启用">' +'启用</button>' + '</div>';
								s += commonJs.createDefaultAuthBtn(commonJs.btn_name.enable,"changeStatus-school","启用",{schoolId:e});
							}else{
								s += commonJs.createDefaultAuthBtn(commonJs.btn_name.disable,"changeStatus-school","禁用",{schoolId:e});
//								s += '<button class="btn btn-xs btn-success tooltip-error" dyid="changeStatus-school" schoolId="'+String(e)+'"  data-rel="tooltip" data-placement="top" title="禁用">' +'禁用</button>' + '</div>';
							}
							
							return '<div class="visible-md visible-lg hidden-sm hidden-xs btn-group">' +editBtn+delBtn+s+
							'</div>';
						}}
				], 
				viewrecords : true,
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
				caption: "<i class='icon-list'></i>学校列表",
				autowidth: true
		
			});
		},
		
		/**
		 * 重新加载学校基础信息数据
		 */
		reloadGrid : function(){
			$("#school-table").trigger("reloadGrid");
		},
		reloadGridByParams : function(params){
			$("#school-table").jqGrid('setGridParam',{  
		        datatype:'json',  
		        mtype :'POST',
		        postData: params,
		        page:1  
		    }).trigger("reloadGrid"); 
		}
	};
})();

$(function(){
	
	$('#div_main').layout({
		applyDefaultStyles: true,
		west : {
			header : "组织机构树",
			size : 260
		}
	});
	
	var defResultNode = _tree.initEdbTree("0",{}, function () {
		var nodes = _edbTreeObj.getSelectedNodes();
		var tempNode = nodes[0];
		_pOrgId = tempNode.id;
		if(_tree.isEdbNode(tempNode)){
			var params = {};
			params.orgId = _pOrgId;
			dySchool.reloadGridByParams(params);
		}
	});
	_pOrgId = defResultNode.id;
	
	dySchool.init();
	
	//按条件查询
	$("#find_btn").click(function(){
			
		var params = {};
		params.schoolName = $("#schoolName").val();
		params.status = $("#status").val();
		params.orgId = _pOrgId;
		
		dySchool.reloadGridByParams(params);
	});
	
	
	//增加学校
	$("#btn-addSchool").on(ace.click_event,function(){
		$.post(path + "/admin/school/goAdd?orgId="+_pOrgId,function(ret){
			bootbox.dialog({
				title : "新增/修改学校基本信息",
				message : ret,
				buttons : {
					success : {
						label: "保存",
                        className: "btn-success",
                        callback :  function(result) {
							if(result) {
								if($("#school_form").valid()){
									$.ajaxSetup({async : false});
									var account = $("#account").val();
									$.post(path + "/admin/school/add?account="+account,$("#school_form").formToObject(),function(addRet){
										$.gritter.add({
											title: '学校',
											text: addRet.code == "1" ? "新增学校信息成功!" : "新增学校信息失败!",
											sticky: false,
											time: '',
											class_name: "gritter-light"
										});
										dySchool.reloadGrid();
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
	
	//编辑学校
	$("body").on(ace.click_event,"button[dyid='modify-school']", function() {
		var _schoolId = $(this).attr("schoolId");		
		$.get(path + "/admin/school/goAdd?orgId="+_pOrgId+"&_schoolId="+_schoolId,function(ret){
			bootbox.dialog({
				title : "新增/修改学校基本信息",
				message : ret,
				buttons : {
					success : {
						label: "更新",
                        className: "btn-success",
                        callback :  function(result) {
							if(result) {
								if($("#school_form").valid()){
									
									$.ajaxSetup({async : false});
									$.post(path + "/admin/school/add", $("#school_form").formToObject(),function(addRet){
										$.gritter.add({
											title: '新增/修改学校基本信息',
											text: addRet.code == "1" ? "修改学校信息成功!" : addRet.msg,
											sticky: false,
											time: ''
										});
										dySchool.reloadGrid();
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
	
	//查看学校
	$("body").on(ace.click_event,"button[dyid='view-school']", function() {
		var _schoolId = $(this).attr("schoolId");
		$.get(path + "/admin/school/goView?&_schoolId="+_schoolId, function(ret){
			bootbox.dialog({
				title : "查看学校详情信息",
				message : ret
			});
		});
	});
	
	//启用/禁用学校
	$("body").on(ace.click_event,"button[dyid='changeStatus-school']", function() {
		var _schoolId = $(this).attr("schoolId");		
		var status = $(this).attr("status");
		var parame = {};
		parame._schoolId = _schoolId;
		if(status == "9"){
			parame.flag = "enable";
		}else{
			parame.flag = "disabled";
		}
		bootbox.confirm("确认执行该操作吗？",function(r){
			if(r){
				$.get(path + "/admin/school/changeStatus",parame,function(changeRet){
					$.gritter.add({
						title: '学校启用/禁用',
						text: changeRet.code == "1" ? "操作成功" : changeRet.msg,
						sticky: false,
						time: ''
					});
					dySchool.reloadGrid();
				});
			}
		});
	});
	
	//删除学校
	$("body").on(ace.click_event,"button[dyid='del-school']", function() {
		var _schoolId = $(this).attr("schoolId");		
		var _schoolName = $(this).attr("schoolName");		
		
		bootbox.confirm("您将删除" +_schoolName+ "学校信息，删除后信息将无法恢复，请确认此次操作?",function(r){
			if(r){
				$.get(path + "/admin/school/del",{_schoolId : _schoolId},function(delRet){
					
					$.gritter.add({
						title: '学校删除',
						text: delRet.code == "1" ? "删除学校信息成功!" : delRet.msg,
						sticky: false,
						time: ''
					});
					dySchool.reloadGrid();
				});
			}
		});
	});
	
});


/**
 * 查看详情
 */
function showDetail(id){
	 var _schoolId = id;
     $.get(path + "/admin/school/goView?_schoolId="+_schoolId,function(ret){
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