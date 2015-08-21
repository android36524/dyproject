/**
 * 学阶管理的JS
 */
var flag_teacher='1';  // 老师
var flag_student='2';  // 学生
var status_apply = 1;  // 只有申请单可以审批
 
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
				url : path + "/admin/approveFlow/listPage?toSchId="+_schId+"&flag="+flag_student,				
				datatype: "json",
				height: 450,
				multiselect:false,
				colNames:['接受方学校','学生姓名','原学校','申请日期','处理人','处理状态','异动说明','操作','状态'],
				colModel:[
						{name:'toSchName',index:'id',type:'text',width:60},
						{name:'namelist',index:'id',type:'text',width:60},
						{name:'schName',index:'id',type:'text',width:60},											
						{name:'createTime',index:'id',width:80,formatter:"date",formatoptions: {srcformat:'Y-m-d H:i:s',newformat:'Y-m-d'}},
						{name:'creatorName',index:'id',width:60},		
						{name:'remark',index:'id',type:'text',width:180},	
						{name:'status_showname',index:'id', width:40, sortable:false},
						{name:'id',index:'myop', width:150, sortable:false,formatter : function(cellvalue,options, rowObject){
							var approveBtn = commonJs.createDefaultAuthBtn(commonJs.btn_name.approve,"op-approve","审批",{flowid:cellvalue});
							if(rowObject["status"]==status_apply){
//								return '<div class="visible-md visible-lg hidden-sm hidden-xs btn-group">' +
//										'<button class="btn btn-xs btn-info tooltip-info" dyid="op-approve" flowid="'+cellvalue+'" data-rel="tooltip" data-placement="top" title="审批">' +
//										'<i class="icon-edit bigger-120"></i></button>' 
//									'</div>';
								return '<div class="visible-md visible-lg hidden-sm hidden-xs btn-group">' +approveBtn+
								'</div>';
							}else{
								return '';
							}
						}},
						{name:'status',hidden : true}
				], 
				viewrecords : true,
				loadComplete : function() {
					var table = this;
					setTimeout(function(){
						dyjsCommon.enableTooltips(table);
						dyjsCommon.updatePagerIcons(table);
						$('button[menuid]').tooltip({container : 'body'});
					}, 0);
				},
				rowNum:30,
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
		//reloadDept();
		$("#altenation-table").jqGrid('setGridParam',{
			url : path + "/admin/approveFlow/listPage?toSchId="+_schId,
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
	$("body").on(ace.click_event,"button[dyid='op-approve']", function() {
		var id = $(this).attr("flowid");
		commonJs.toUrl("转入学生审批","提交",path + "/admin/approveFlowStu/toApprove?flag="+flag_student+"&flowid="+id+"&schId="+_schId,function(result){
			if(result) {
				var postData={};
				postData["flowid"]=id;
				postData["status"] = $("input[name='ck_approve']:checked").val();;
				postData["shcId"]=_schId;
				postData["remark"]=$("#remark")[0].value;
				var userlist=[];
				$("select[name^='toClass']").each(function(){					
					var user ={};
					user["userId"]=$(this).attr("userid");
					user["id"]=$(this).attr("alterchangeid");
					user["classId"]=$(this).val();
					user["toClassName"]=$(this).find("option:selected").text();
					var gradeId = $("select[name='toGrade'][userid='"+$(this).attr("userid")+"']").val();
					user["gradeId"]=gradeId;
					userlist[userlist.length]=user;					
				});
			
				postData["userlist"]=JSON.stringify(userlist);
				if(postData["status"]==3 || $("#alter_form").valid()){								
					$.ajaxSetup({async : false});
					$.post(path + "/admin/approveFlow/approve", postData,function(addRet){
						$.gritter.add({
							title: '审批',
							text: addRet.code == "1" ? "审批成功" : addRet.msg,
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

function getData(){
	var data={};
	data['schName']=$.trim($("#schName").val());
	data['teaName']=$.trim($("#teaName").val());
	data['status']=$("#status").val();
	data['flag']=flag_student;
	return data;
}