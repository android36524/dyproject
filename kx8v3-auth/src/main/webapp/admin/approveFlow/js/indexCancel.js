/**
 * 学阶管理的JS
 */
var flag_teacher='1';  // 老师
var flag_student='2';  // 学生
var status_apply = 1;  // 只有申请单可以审批
var status_cancel = 4;  // 只有申请单可以审批
 
var dyObj = (function(){
	
	return {
		
		/**
		 * 异动管理模块初始化
		 */
		init : function(){
			this.createGrid();
		},
		
		/**
		 * 创建Grid
		 */
		createGrid : function(){
			var grid_selector = "#altenation-table";
			var pager_selector = "#altenation-pager";
			
			jQuery(grid_selector).jqGrid({
				url : path + "/admin/approveFlow/listPage?schId="+_schId,				
				datatype: "json",
				height: 450,
				multiselect:false,
				colNames:['教职工姓名','转出学校名称','申请日期','处理人','处理状态','异动说明','不通过原因','操作','状态'],
				colModel:[							
						{name:'namelist',index:'id',type:'text',width:60},
						{name:'name',index:'id',type:'text',width:60},											
						{name:'createTime',index:'id',width:80,formatter:"date",formatoptions: {srcformat:'Y-m-d H:i:s',newformat:'Y-m-d'}},
						{name:'approveName',index:'id',width:60},					
						{name:'status_showname',index:'id', width:40, sortable:false},
						{name:'remark',index:'id',type:'text',width:120},	
						{name:'approveRemark',index:'id', width:120, sortable:false},
						{name:'id',index:'myop', width:150, sortable:false,formatter : function(cellvalue,options, rowObject){
							if(rowObject["status"]==status_apply){
								var revoke = commonJs.createDefaultAuthBtn(commonJs.btn_name.revoke,"op-cancel","撤销",{flowid:cellvalue});
								return '<div class="visible-md visible-lg hidden-sm hidden-xs btn-group">' + revoke+										
									'</div>';
							}else{
								return '';
							}
						}},
						{name:'status',hidden : true}
				], 
				viewrecords : true,
				rowNum:30,
				loadComplete : function() {
					var table = this;
					setTimeout(function(){
						dyjsCommon.enableTooltips(table);
						dyjsCommon.updatePagerIcons(table);
						$('button[menuid]').tooltip({container : 'body'});
					}, 0);
				},
				rowList:[20,30,40,50],
				pager : pager_selector,				
				multiboxonly: false,
				rownumbers:true,//显示行号
				caption: "<i class='icon-list'></i>申请单列表",
				autowidth: true		
			});
			
		},
		
		/**
		 * 重新加载学阶数据
		 */
		reloadGrid : function(){
			$("#altenation-table").trigger("reloadGrid");
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
	var obj = _tree.initEdbTree("1",{}, function () {		
		$("#alter_form")[0].reset();
		var nodes = _edbTreeObj.getSelectedNodes();	
		if(!_tree.isSchoolNode(nodes[0])){
			return;
		}
		_schId = nodes[0].id;
		$("#altenation-table").jqGrid('setGridParam',{
			url : path + "/admin/approveFlow/listPage?schId="+_schId,
			postData:getData(), //发送数据
	        datatype:'json',  
	        mtype :'POST',	        
	        page:1  
	    }).trigger("reloadGrid"); 			
	});
	_schId=obj.id;	
	//reloadDept();
	dyObj.init();	
	
	$("#find_btn").click(function(){
		$("#altenation-table").jqGrid('setGridParam',{  
	        datatype:'json',  
	        mtype :'POST',
	        postData:getData(), //发送数据  
	        page:1  
	    }).trigger("reloadGrid");   
	});
	
	//审批
	$("body").on(ace.click_event,"button[dyid='op-cancel']", function() {
		
		var id = $(this).attr("flowid");
		var postData={};
		postData["flowid"]=id;
		postData["status"] = status_cancel;
		postData["shcId"]=_schId;
		postData["userlist"]="";	
		$.ajaxSetup({async : false});
		bootbox.confirm("确认撤销该申请单吗？",function(r){
            if(r){
				$.post(path + "/admin/approveFlow/approve", postData,function(addRet){
					$.gritter.add({
						title: '撤销',
						text: addRet.code == "1" ? "撤销成功" : addRet.msg,
						sticky: false,
						time: ''
					});
					dyObj.reloadGrid();
					$.ajaxSetup({async : true});
				});
            }
		});
	});    
});

function getData(){
	var data={};
	data['status']=$("#status").val();
	data['teaName']=$.trim($("#teaName").val());
	return data;
}