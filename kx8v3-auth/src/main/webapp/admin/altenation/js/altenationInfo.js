/**
 * 学阶管理的JS
 */
 
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
				url : path + "/admin/altenation/listAltenation?schId="+_schId,				
				datatype: "json",
				height: 450,
				multiselect:false,
				colNames:['教职工姓名','性别','异动类别','原属部门','转出学校名称','转入学校名称','异动日期','调往部门','联系电话','处理状态','处理人'],
				colModel:[							
						{name:'userName',index:'id',type:'text',width:60},
						{name:'sex_showname',index:'id',type:'text',width:40},
						{name:'changeType_showname',index:'id',width:60},
						{name:'deptName',index:'id',width:80},						
						{name:'schName',index:'id',width:80},
						{name:'toSchName',index:'id',width:80},						
						{name:'beginTime',index:'id',width:80,formatter:"date",formatoptions: {srcformat:'Y-m-d H:i:s',newformat:'Y-m-d'}},
						{name:'toDeptName',index:'id',width:80},
						{name:'telphone',index:'id',width:80},
						{name:'status_showname',index:'id', width:40, sortable:false},
						{name:'creatorName',index:'id',width:60}
				], 
				viewrecords : true,
				rowNum:30,
				rowList:[20,30,40,50],
				pager : pager_selector,				
				multiboxonly: false,
				rownumbers:true,//显示行号
				loadComplete : function() {
					var table = this;
					setTimeout(function(){
						dyjsCommon.enableTooltips(table);
						dyjsCommon.updatePagerIcons(table);
						$('button[menuid]').tooltip({container : 'body'});
					}, 0);
				},
				caption: "<i class='icon-list'></i>异动列表",
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
			url : path + "/admin/altenation/listAltenation?schId="+_schId,
			postData:getData(), //发送数据
	        datatype:'json',  
	        mtype :'POST',	        
	        page:1  
	    }).trigger("reloadGrid"); 			
	});
	_schId=obj.id;	
	//reloadDept();
	dyObj.init();	
	
	function reloadDept(){
		$("#qDept").empty();
		$.post(path + "/admin/dept/deptList",{orgId:_schId,page:1,rows:30,deptName:""},function(data){
			$("#qDept").append("<option value='-1'>请选择</option>");
			$(data.rows).each(function(k,d){
				$("#qDept").append("<option value='"+d.id+"'>"+d.deptName+"</option>");
			});
		});	
	}
	//时间选择
	$('#alter_startDate,#alter_endDate').datepicker({
		language:'zh-CN',
		autoclose:true,
		pickTime: false,
		todayBtn: true,
		format:'yyyy-mm-dd hh:ii:ss'
	}).prev().on(ace.click_event, function(){
		$(this).next().focus();			
	});
	$("#find_btn").click(function(){
		$("#altenation-table").jqGrid('setGridParam',{  
	        datatype:'json',  
	        mtype :'POST',
	        postData:getData(), //发送数据  
	        page:1  
	    }).trigger("reloadGrid");   
	});
	$("#reset_btn").click(function(){
		$("#alter_form")[0].reset();
	});
    //选择所属部门
    $("#btn_select_dept").on(ace.click_event, function() {
        var orgId = _schId;
        $.layer({
            type : 2,
            shadeClose: true,
            title: "部门选择",
            closeBtn: [0, true],
            shade: [0.8, '#000'],
            //btns : 2,
            border: [0],
            offset: ['20px',''],
            area: ['400px', '530px'],
            iframe: {src: path + "/common/showDeptTree.jsp?orgId="+orgId},
            yes : function(index){            	
                var rowData = JSON.parse(layer.getChildFrame("#selected",index).val());               
                $("#dept_parentName").val(rowData.deptName);
                $("#dept_pid").val(rowData.id);
                //关闭弹窗
                layer.close(index);
            }
        });
    });	
});

function setLayerDeptGroup(deptId,deptName){
 	$("#dept_pid").val(deptId);
	$("#dept_parentName").val(deptName);
}

function getData(){
	var data={};
	data['deptId']=$("#dept_pid").val();
	data['changeType']=$("#alterType").val();
	data['userName']=$.trim($("#name").val());
	data['status']=$("#status").val();
	data['beginTime']=$("#alter_startDate").val()?($("#alter_startDate").val()+" 00:00:00"):"";
	data['endTime']=$("#alter_endDate").val()?($("#alter_endDate").val()+" 23:59:59"):"";
	return data;
}
