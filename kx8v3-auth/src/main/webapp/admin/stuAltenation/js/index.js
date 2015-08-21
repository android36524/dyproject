/**
 * 学阶管理的JS
 */
 
var flag_innerType= 101;   // 转班
var flag_repeatType= 102;   // 复学
var flag_outType  =103;    // 转学
var flag_otherType = 104;     // 其他异动 
var _schId = 0;
var max_alter = 20;
var max_alterOut = 5;
var title_repeat = "复学";
var title_inner = "调班";
var title_other ="其他异动";
var title_outer ="转学";

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
				url : path + "/admin/student/ListPage?schId="+_schId,
				datatype: "json",
				height: 450,
				colNames:['学校','年级及班级','学生姓名','学号','性别','家长','家长手机','账号','学生在校状态','状态'],
				colModel:[
				       {name:'schoolName',index:'schoolName',type:'text',width:120},
				       {name:'gradeNameAndSc',index:'gradeNameAndSc',type:'text', width:120},
				       {name:'name',index:'name',type:'text',width:120},
				       {name:'rollCode',index:'rollCode',type:'text',width:120},
				       {name:'sex_showname',index:'sex',type:'text',width:40},
				       {name:'parentName',index:'parentName',type:'text',width:120},
				       {name:'trelphone',index:'trelphone',type:'text',width:120},
				       {name:'accountId',index:'accountId',type:'text',width:120},
				       {name:'status_showname',index:'status',type:'text',width:120},
				       {name:'status',hidden : true}				       
				],
				viewrecords : true,
				rowNum:20,
				rowList:[10,20],
				pager : pager_selector,
				altRows: true,
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
				caption: "<i class='icon-list'></i>学生列表",
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
		reloadGrade();
		$("#altenation-table").jqGrid('setGridParam',{
			url : path + "/admin/student/ListPage?schId="+_schId,
			postData:getData(), //发送数据  
	        datatype:'json',  
	        mtype :'POST',	        
	        page:1  
	    }).trigger("reloadGrid"); 			
	});
	_schId=obj.id;	
	reloadGrade();
	$("#qGrade").change(function(){
		reloadClass();
	});
	
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
			datatype:'json',  
		    mtype :'POST',
		    postData:getData(), //发送数据  
		    page:1  
	    }).trigger("reloadGrid"); 
	});
	
	$("#inneralter_btn").click(function(){
		var selectedIds = $("#altenation-table").jqGrid("getGridParam", "selarrrow");
		if(!checkAlter(selectedIds,flag_innerType,max_alter,title_inner)){
			return;
		}		
		var sIds = selectedIds.join(",");	
		commonJs.toUrl(title_inner,title_inner,path + "/admin/stuAltenation/toAltenation?flag="+flag_innerType+"&ids="+sIds+"&schId="+_schId,function(result) {
			if(result) {
				if($("#alterlisttable tbody").children("tr").length<=0){
					alert("没有选择任何人员");
					return;
				}
				var arrid = [];
				$("#alterlisttable tbody").children().each(function(){
					arrid[arrid.length]=$(this).attr("id");
				});	
				$("#stuIds").val(arrid.join(","));
				$("#shcId").val(_schId);				
				if($("#alter_form").valid()){									
					$.ajaxSetup({async : false});
					$.post(path + "/admin/stuAltenation/innerAlter", $("#alter_form").formToObject(),function(addRet){
						$.gritter.add({
							title: title_inner,
							text: addRet.code == "1" ? "调班成功" : addRet.msg,
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
		commonJs.toUrl(title_other,title_other,path + "/admin/stuAltenation/toAltenation?flag="+flag_otherType+"&ids="+sIds+"&schId="+_schId,function(result) {
			if(result) {
				if($("#alterlisttable tbody").children("tr").length<=0){
					alert("没有选择任何人员");
					return;
				}
				var arrid = [];
				$("#alterlisttable tbody").children().each(function(){
					arrid[arrid.length]=$(this).attr("id");
				});								
				$("#stuIds").val(arrid.join(","));
				$("#shcId").val(_schId);
				//$("#creator").val(_creator);								
				if($("#alter_form").valid()){									
					$.ajaxSetup({async : false});
					$.post(path + "/admin/stuAltenation/otherAlter", $("#alter_form").formToObject(),function(addRet){
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
	
	$("#repeatAlter_btn").click(function(){
		var selectedIds = $("#altenation-table").jqGrid("getGridParam", "selarrrow");
		if(!checkAlter(selectedIds,flag_repeatType,max_alter,title_repeat)){
			return;
		}
		
		var sIds = selectedIds.join(",");
		commonJs.toUrl(title_repeat,title_repeat,path + "/admin/stuAltenation/toAltenation?flag="+flag_repeatType+"&ids="+sIds+"&schId="+_schId,function(result) {
			if(result) {
				if($("#alterlisttable tbody").children("tr").length<=0){
					alert("没有选择任何人员");
					return;
				}
				var arrid = [];
				$("#alterlisttable tbody").children().each(function(){
					arrid[arrid.length]=$(this).attr("id");
				});								
				$("#stuIds").val(arrid.join(","));
				$("#shcId").val(_schId);
				//$("#creator").val(_creator);								
				if($("#alter_form").valid()){									
					$.ajaxSetup({async : false});
					$.post(path + "/admin/stuAltenation/repeatAlter", $("#alter_form").formToObject(),function(addRet){
						$.gritter.add({
							title:title_repeat,
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
	
	$("#outerAlter_btn").click(function(){
		var selectedIds = $("#altenation-table").jqGrid("getGridParam", "selarrrow");
		if(!checkAlter(selectedIds,flag_outType,max_alterOut,title_outer)){
			return;
		}		
		var sIds = selectedIds.join(",");
		commonJs.toUrl("转学申请异动","提交",path + "/admin/stuAltenation/toAltenation?flag="+flag_outType+"&ids="+sIds+"&schId="+_schId,function(result) {
			if(result) {
				if($("#alterlisttable tbody").children("tr").length<=0){
					alert("没有选择任何人员");
					return false;
				}
				if(_schId==$("#o_school").val()){
					alert("请选择其他学校校");
					return false;
				}
				var arrid = [];
				$("#alterlisttable tbody").children().each(function(){
					arrid[arrid.length]=$(this).attr("id");
				});								
				
				$("#empIds").val(arrid.join(","));
				$("#shcId").val(_schId);
				$("#creator").val(1);
				
				if($("#alter_form").valid()){									
					$.ajaxSetup({async : false});
					$.post(path + "/admin/approveFlow/apply", $("#alter_form").formToObject(),function(addRet){
						$.gritter.add({
							title: '转学申请',
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
});

function reloadGrade(){
	$("#qGrade").empty();
	$.post(path + "/admin/grade/listPage",{schId:_schId,page:1,rows:30,flag:2,gradeName:""},function(data){				
		$("#qGrade").append("<option value=''>请选择</option>");
		$(data.rows).each(function(k,d){
			$("#qGrade").append("<option value='"+d.id+"'>"+d.name+"("+d.stageName+")</option>");
		});
		$("#qGrade").change();
	});			
}

function reloadClass(){
	$("#qClass").empty();
	var _gradeId = $("#qGrade").val();
	if(!_gradeId) return;
		$.post(path + "/admin/class/listClassByPage",{gradeId:_gradeId,page:1,rows:30,className:""},function(data){				
		$("#qClass").append("<option value=''>请选择</option>");
		$(data.rows).each(function(k,d){
			$("#qClass").append("<option value='"+d.id+"'>"+d.name+"</option>");
		});
	});	
}

// 判断是否能做次操作
function isCanAlter(type,statuses){
	var bl=true;
	$.ajaxSetup({async:false});
	$.get(path + "/admin/stuAltenation/isCanAlter?alterStatus="+statuses+"&alterType="+type,function(ret){
		if(ret.code!=1){
			bl=false;
		}
	});
	$.ajaxSetup({async:true});
	return bl;
}

// 判断是否能做次操作
function isCanAlterIds(ids,type){
	var bl = true;
	var arrStatus = [];
	for(var i=0;i<ids.length;i++){
		var rowData = $("#altenation-table").jqGrid("getRowData",ids[i]);
		var status = rowData.status;
		if(!arrStatus.contains(status)){
			arrStatus[arrStatus.length]=status;
		}							
	}
	var statuses = arrStatus.join(",");	
	if(!isCanAlter(type,arrStatus)){
		bl=false;
	}
	return bl;
}

//拼查询参数
function getData(){		
	var data={};
	data['name']=$.trim($("#name").val());
	data['gradeId']=$("#qGrade").val();
	data['classId']=$("#qClass").val();
	data['tel']=$.trim($("#telphone").val());
	data['status']=$("#status").val();
	return data;
}

function checkAlter(selectedIds,flag,maxRow,title){
	if(selectedIds.length<=0){
		$.gritter.add({
			title: title,
			text: "您未选择学生信息，无法进行该操作！",
			sticky: false,
			time: ''
		});
		return false;
	}
	if(isMax(selectedIds,maxRow)){
		$.gritter.add({
			title: title,
			text: "您选择学生太多了，最多只能选 "+maxRow +" 个",
			sticky: false,
			time: ''
		});
		return false;
	}
	if(!isCanAlterIds(selectedIds,flag)){
		$.gritter.add({
			title: title,
			text: "你选择的学生不能做此操作",
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