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
				url : path + "/admin/stuAltenation/listAltenation?schId="+_schId,				
				datatype: "json",
				height: 450,
				multiselect:false,
				colNames:['所属学校','学生姓名','账号','性别','异动类别','异动日期','联系电话','处理状态','处理人'],
				colModel:[
						{name:'schName',index:'id',type:'text',width:60},						
						{name:'userName',index:'name',width:40, sortable:false,formatter : function(j,options,rowObject){
	                    	return '<a href="javascript:showDetail('+rowObject.id+',\''+rowObject.changeType_showname+'\');">'+rowObject.userName+'</a>';
	                    }},
	                    {name:'account',index:'id',type:'text',width:60},	
						{name:'sex_showname',index:'id',type:'text',width:40},
						{name:'changeType_showname',index:'id',width:60},						
						{name:'beginTime',index:'id',width:80,formatter:"date",formatoptions: {srcformat:'Y-m-d H:i:s',newformat:'Y-m-d'}},						
						{name:'telphone',index:'id',width:80},
						{name:'status_showname',index:'id', width:40, sortable:false},
						{name:'creatorName',index:'id',width:60}
				], 
				loadComplete : function() {
					var table = this;
					setTimeout(function(){
						dyjsCommon.enableTooltips(table);
						dyjsCommon.updatePagerIcons(table);
						$('button[menuid]').tooltip({container : 'body'});
					}, 0);
				},
				viewrecords : true,
				rowNum:30,
				rowList:[20,30,40,50],
				pager : pager_selector,				
				multiboxonly: false,
				rownumbers:true,//显示行号
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
			url : path + "/admin/stuAltenation/listAltenation?schId="+_schId,
			postData:getData(), //发送数据
	        datatype:'json',  
	        mtype :'POST',	        
	        page:1  
	    }).trigger("reloadGrid"); 			
	});
	_schId=obj.id;		
	dyObj.init();	
	
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
});

function getData(){		
	var data={};
	
	data['changeType']=$("#alterType").val();
	data['userName']=$("#name").val();
	data['status']=$("#status").val();
	data['beginTime']=$("#alter_startDate").val()?($("#alter_startDate").val()+" 00:00:00"):"";
	data['endTime']=$("#alter_endDate").val()?($("#alter_endDate").val()+" 23:59:59"):"";
	return data;
}

/**
 * 查看详情
 */
function showDetail(id,title){
	 var id = id;
     $.get(path + "/admin/stuAltenation/toShowDetail?id="+id,function(ret){
         bootbox.dialog({
             title : title+"详情",
             message : ret,
             buttons : {
                 cancel : {
                     "label" : "关闭",
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

