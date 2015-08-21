/**
 * 学阶管理的JS
 */

var flag_innerType= 1;   // 校内变动
var flag_outType= 2;   // 其他变动
var flag_otherType= 3;   // 其他变动
var flag_repeat = 4;     // 返聘
var max_alter = 20;
var max_alterOut = 5;
var title_repeat = "返聘";
var title_inner = "校内调岗";
var title_other ="其他异动";
var title_outer ="校外调岗";

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
				url : path + "/admin/schoolEmpe/empeList?orgId="+_schId,				
				datatype: "json",
				height: 450,				
				colNames:['姓名','手机号码','民族','性别','岗位职业','所属学校','所属部门','教师状态','状态'],
				colModel:[							
						{name:'name',index:'id',type:'text',width:20},
						{name:'mobile',index:'id',type:'text',width:20},
						{name:'nation_showname',index:'id',width:20},
						{name:'sex_showname',index:'id',width:20},
						{name:'jobsCareers_showname',index:'id',width:40},
						{name:'orgName',index:'id',width:40},
						{name:'deptName',index:'id',width:40},
						{name:'status_showname',index:'id', width:20, sortable:false},
						{name:'status',hidden : true}
				], 
				viewrecords : true,
				rowNum:20,
				rowList:[10,20],				
				pager : pager_selector,	
				multiselect:true,
				multiboxonly: true,
				loadComplete : function() {
					var table = this;
					setTimeout(function(){
						dyjsCommon.enableTooltips(table);
						dyjsCommon.updatePagerIcons(table);
						$('button[menuid]').tooltip({container : 'body'});
					}, 0);
				},
				caption: "<i class='icon-list'></i>教师列表",
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
		$("#form-search")[0].reset();
		var nodes = _edbTreeObj.getSelectedNodes();	
		if(!_tree.isSchoolNode(nodes[0])){
			return;
		}
		_schId = nodes[0].id;	
		reloadDept();		
		$("#altenation-table").jqGrid('setGridParam',{
			url : path + "/admin/schoolEmpe/empeList?orgId="+_schId,	
	        datatype:'json',  
	        mtype :'POST',	
	        postData:getData(),
	        page:1  
	    }).trigger("reloadGrid"); 			
	});
	_schId=obj.id;	
	reloadDept();
	dyObj.init();
	$("#form-search").validate({
		rules:{
			"name":{ maxlength:20,integer:false},
			"telphone":{ maxlength:11,integer:true}
		},
		messages:{			
			"telphone":{ maxlength:$.validator.format("请输入一个长度最多是 {0} 的数字!")}
		}
	}); 
	$("#find_btn").click(function(){
		if(!$("#form-search").valid()){
			return;
		}
		$("#altenation-table").jqGrid('setGridParam',{  
			url : path + "/admin/schoolEmpe/empeList?orgId="+_schId,	
	        datatype:'json',  
	        mtype :'POST',
	        postData:getData(), //发送数据  
	        page:1  
	    }).trigger("reloadGrid"); 
	});
	
	function getData(){
		var data={};
		data['name']=$.trim($("#name").val());
		data['deptId']=$("#dept_pid").val();
		data['mobile']=$.trim($("#telphone").val());
		data['status']=$("#status").val();
		return data;
	}
	
	function reloadDept(){
		$("#qDept").empty();
		$.post(path + "/admin/dept/deptList",{orgId:_schId,page:1,rows:30,deptName:""},function(data){				
			$("#qDept").append("<option value='-1'>请选择</option>");
			$(data.rows).each(function(k,d){
				$("#qDept").append("<option value='"+d.id+"'>"+d.deptName+"</option>");
			});
		});	
	}	
	
	$("#inneralter_btn").click(function(){
		var selectedIds = $("#altenation-table").jqGrid("getGridParam", "selarrrow");
		if(!checkAlter(selectedIds,flag_innerType,max_alter,title_inner)){
			return;
		}		
		var sIds = selectedIds.join(",");
		commonJs.toUrl(title_inner,"执行调岗",path + "/admin/altenation/toAlternation?flag="+flag_innerType+"&ids="+sIds+"&schId="+_schId,function(result) {
			if(result) {
				if($("#alterlisttable tbody").children("tr").length<=0){
					$.gritter.add({
						title: title_inner,
						text: "没有选择任何人员",
						sticky: false,
						time: ''
					});
					return;
				}
				var arrid = [];
				$("#alterlisttable tbody").children().each(function(){
					arrid[arrid.length]=$(this).attr("id");
				});								
				$("#empIds").val(arrid.join(","));
				$("#shcId").val(_schId);
				$("#creator").val(_creator);								
				if($("#alter_form").valid()){									
					$.ajaxSetup({async : false});
					$.post(path + "/admin/altenation/innerAlter", $("#alter_form").formToObject(),function(addRet){
						$.gritter.add({
							title: title_inner,
							text: addRet.code == "1" ? "调岗成功" : addRet.msg,
							sticky: false,
							time: ''
						});
						dyObj.reloadGrid();
					});
					$.ajaxSetup({async : true});
				}else{
					return false;
				}
			}
		});
	});
	
	$("#otherAlter_btn").click(function(){
		var selectedIds = $("#altenation-table").jqGrid("getGridParam", "selarrrow");
		if(!checkAlter(selectedIds,flag_otherType,max_alter,title_other)){
			return;
		}
		var sIds = selectedIds.join(",");
		commonJs.toUrl(title_other,title_other,path + "/admin/altenation/toAlternation?flag="+flag_otherType+"&ids="+sIds+"&schId="+_schId,function(result) {
			if(result) {
				if($("#alterlisttable tbody").children("tr").length<=0){
					$.gritter.add({
						title: title_other,
						text: "没有选择任何人员",
						sticky: false,
						time: ''
					});
					return;
				}
				var arrid = [];
				$("#alterlisttable tbody").children().each(function(){
					arrid[arrid.length]=$(this).attr("id");
				});								
				$("#empIds").val(arrid.join(","));
				$("#shcId").val(_schId);
				$("#creator").val(_creator);								
				if($("#alter_form").valid()){									
					$.ajaxSetup({async : false});
					$.post(path + "/admin/altenation/otherAlter", $("#alter_form").formToObject(),function(addRet){
						$.gritter.add({
							title: title_other,
							text: addRet.code == "1" ? "变动成功" : addRet.msg,
							sticky: false,
							time: ''
						});
						dyObj.reloadGrid();
					});
					$.ajaxSetup({async : true});
				}else{
					return false;
				}
			}
		});
	});
	
	$("#outerAlter_btn").click(function(){
		var selectedIds = $("#altenation-table").jqGrid("getGridParam", "selarrrow");
		if(!checkAlter(selectedIds,flag_outType,max_alterOut,title_outer)){
			return;
		}
		var sIds = selectedIds.join(",");
		commonJs.toUrl("校外调动申请异动","提交",path + "/admin/altenation/toAlternation?flag="+flag_outType+"&ids="+sIds+"&schId="+_schId,function(result) {
			if(result) {
				if($("#alterlisttable tbody").children("tr").length<=0){
					$.gritter.add({
						title: title_outer,
						text: "没有选择任何人员",
						sticky: false,
						time: ''
					});
					return;
				}
				if(_schId==$("#o_school").val()){
					$.gritter.add({
						title:title_outer,
						text: "请选择其他学校",
						sticky: false,
						time: ''
					});
					return false;
				}
				var arrid = [];
				$("#alterlisttable tbody").children().each(function(){
					arrid[arrid.length]=$(this).attr("id");
				});								
				$("#empIds").val(arrid.join(","));
				$("#shcId").val(_schId);
				$("#creator").val(_creator);								
				if($("#alter_form").valid()){									
					$.ajaxSetup({async : false});
					$.post(path + "/admin/approveFlow/apply", $("#alter_form").formToObject(),function(addRet){
						$.gritter.add({
							title: '校外调动',
							text: addRet.code == "1" ? "变动成功" : addRet.msg,
							sticky: false,
							time: ''
						});
						dyObj.reloadGrid();
					});
					$.ajaxSetup({async : true});
				}else{
					return false;
				}
			}
		});
	});
	
	
	$("#repeatAlter_btn").click(function(){
		var selectedIds = $("#altenation-table").jqGrid("getGridParam", "selarrrow");
		if(!checkAlter(selectedIds,flag_repeat,max_alter,title_repeat)){
			return;
		}
		var sIds = selectedIds.join(",");
		commonJs.toUrl(title_repeat,title_repeat,path + "/admin/altenation/toAlternation?flag="+flag_repeat+"&ids="+sIds+"&schId="+_schId,function(result){
			if(result) {
				if($("#alterlisttable tbody").children("tr").length<=0){
					$.gritter.add({
						title: title_repeat,
						text: "没有选择任何人员",
						sticky: false,
						time: ''
					});
					return;
				}
				var arrid = [];
				$("#alterlisttable tbody").children().each(function(){
					arrid[arrid.length]=$(this).attr("id");
				});								
				$("#empIds").val(arrid.join(","));
				$("#shcId").val(_schId);
				$("#creator").val(_creator);								
				if($("#alter_form").valid()){									
					$.ajaxSetup({async : false});
					$.post(path + "/admin/altenation/repeatAlter", $("#alter_form").formToObject(),function(addRet){
						$.gritter.add({
							title: title_repeat,
							text: addRet.code == "1" ? title_repeat+"成功" : addRet.msg,
							sticky: false,
							time: ''
						});
						dyObj.reloadGrid();
					});
					$.ajaxSetup({async : true});
				}else{
					return false;
				}
			}						
		});
	});
	
    
  //选择所属部门
    $("#btn_select_dept").on(ace.click_event, function() {
    	
    	var orgId = _schId;
    	var _params = {
    		"orgId" : orgId,
    	};
    	
    	commonJs.showDeptDialog(_params,function(dept){
    		$("#dept_pid").val(dept.deptId);
			$("#dept_parentName").val(dept.deptName);
    	});
    });
    
    $("#reset_btn").click(function(){
		$("#form-search")[0].reset();
	});
});

function checkAlter(selectedIds,flag,maxRow,title){
	if(selectedIds.length<=0){
		$.gritter.add({
			title: title,
			text: "您未选择教职工信息，无法进行该操作！",
			sticky: false,
			time: ''
		});
		return false;
	}
	if(isMax(selectedIds,maxRow)){
		$.gritter.add({
			title: title,
			text: "您选择的员工太多了，最多只能选 "+maxRow +" 个",
			sticky: false,
			time: ''
		});
		return false;
	}
	if(!isCanAlterIds(selectedIds,flag)){
		$.gritter.add({
			title: title,
			text: "你选择的员工不能做此操作",
			sticky: false,
			time: ''
		});
		return false;
	}	
	return true;
}

function isMax(ids,max){
	if(!ids) return false;
	return ids.length>max;
}



function isCanAlter(type,statuses){
	var bl=true;
	$.ajaxSetup({async:false});
	$.get(path + "/admin/altenation/isCanAlter?alterStatus="+statuses+"&alterType="+type,function(ret){
		if(ret.code!=1){
			bl=false;
		}
	});
	$.ajaxSetup({async:true});
	return bl;
}

function isCanAlterIds(ids,type){
	var bl = true;
	var arrStatus = [];
	for(var i=0;i<ids.length;i++){
		var rowData = $("#altenation-table").jqGrid("getRowData",ids[i]);
		var status = rowData.status;
		if(!status){
			bl=false;
		}
		if(!arrStatus.contains(status)){
			arrStatus[arrStatus.length]=status;
		}							
	}
	
	if(bl&&!isCanAlter(type,arrStatus)){
		bl=false;
	}
	return bl;
}
