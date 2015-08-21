/**
 * 学阶管理的JS
 */
 
var dyObj = (function(){
	
	return {
		
		/**
		 * 学阶管理模块初始化
		 */
		init : function(){
			this.createGrid();
		},
		
		/**
		 * 创建Grid
		 */
		createGrid : function(){
			var grid_selector = "#semester-table";
			var pager_selector = "#semester-pager";
			
			jQuery(grid_selector).jqGrid({
				url : path + "/admin/semester/list?schId="+_schId,
				datatype: "json",
				height: 450,
				colNames:['学年','学期名称','是否当前学期','学校名称','创建人','创建时间','操作'],
				colModel:[					
						{name:'schYear_showname',index:'id',type:'text',width:40},
						{name:'name',index:'id',width:120},
						{name:'isCur_showname',index:'id',width:120},
						{name:'schId_showname',index:'id',width:120},
						{name:'account',index:'id',width:100},
						{name:'createTime',index:'createDate',width:80,sorttype:false},
						{name:'id',index:'id', width:80, sortable:false,formatter : function(e,options, rowObject){
							var editBtn = commonJs.createDefaultAuthBtn(commonJs.btn_modifycode,"modify-semester","修改",{semesterId:e});
							var delBtn = commonJs.createDefaultAuthBtn(commonJs.btn_delcode,"del-semester","删除",{semesterId:e,semesterName:rowObject["name"],schId:rowObject["schId"]});
							var setBtn = "";
							if(rowObject["isCur"]==2){
								setBtn =  commonJs.createDefaultAuthBtn(commonJs.btn_name.set,"set-semester","设置为当前学期",{semesterId:String(e),schId:rowObject["schId"]});
							}
							return '<div class="visible-md visible-lg hidden-sm hidden-xs btn-group">' +editBtn+delBtn+setBtn+
									'</div>';
						}}
				], 
				viewrecords : true,
				rownumbers:true,//显示行号
				rowNum:30,
				rowList:[20,30,40,50],
				pager : pager_selector,
				loadComplete : function() {
					var table = this;
					setTimeout(function(){
						dyjsCommon.enableTooltips(table);
						dyjsCommon.updatePagerIcons(table);
						$('button[menuid]').tooltip({container : 'body'});
					}, 0);
				},
				multiselect: false,
				multiboxonly: true,
				
				caption: "<i class='icon-list'></i>学期列表",
				autowidth: true
		
			});
			
		},
		
		/**
		 * 重新加载学阶数据
		 */
		reloadGrid : function(){
			$("#semester-table").trigger("reloadGrid");
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
	
	//var v_org = ${key_currentOrg_json};

	var obj = _tree.initEdbTree("1",{}, function () {		
		$("#form-search")[0].reset();
		var nodes = _edbTreeObj.getSelectedNodes();
		if(!_tree.isSchoolNode(nodes[0])){
			return;
		}
		_schId = nodes[0].id;			
		$("#semester-table").jqGrid('setGridParam',{
			url : path + "/admin/semester/list?schId="+_schId,
	        datatype:'json',  
	        mtype :'POST',
	        postData:{'name':$("#name").val()}, //发送数据  
	        page:1  
	    }).trigger("reloadGrid"); 			
	});
	_schId=obj.id;	
	dyObj.init();
	
	$("#btn-addSemester").on(ace.click_event,function(){
		$.get(path + "/admin/semester/toAdd",function(ret){
			bootbox.dialog({
				title : "新增学期",
				message : ret,
				buttons : {
					success : {
						label: "保存",
                        className: "btn-success",
                        callback :  function(result) {
							if(result) {
								if($("#semester_form").valid()){
									$.ajaxSetup({async : false});
									$.post(path + "/admin/semester/add",$("#semester_form").formToObject(),function(addRet){
										$.gritter.add({
											title: '学期新增',
											text: addRet.code == "1" ? "添加成功" : addRet.msg,
											sticky: false,
											time: '',
											class_name: "gritter-light"
										});
										dyObj.reloadGrid();
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
	
	//更新课时
	$("body").on(ace.click_event,"button[dyid='modify-semester']", function() {		
		var _semester = $(this).attr("semesterId");	
		
		$.get(path + "/admin/semester/toAdd?&id="+_semester,function(ret){
			bootbox.dialog({
				title : "更新学期",
				message : ret,
				buttons : {
					success : {
						label: "更新",
                        className: "btn-success",
                        callback :  function(result) {
							if(result) {
								if($("#semester_form").valid()){									
									$.ajaxSetup({async : false});
									$.post(path + "/admin/semester/add", $("#semester_form").formToObject(),function(addRet){
										$.gritter.add({
											title: '学期更新',
											text: addRet.code == "1" ? "更新成功" : addRet.msg,
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
	
	//删除学期
	$("body").on(ace.click_event,"button[dyid='del-semester']", function() {
		var _semesterId = $(this).attr("semesterId");
		var _name = $(this).attr("semesterName");
		bootbox.confirm("您将删除 "+_name+" 学期信息，删除后信息将无法恢复，请确认此次操作？",function(r){
			if(r){
				$.get(path + "/admin/semester/del",{id : _semesterId},function(delRet){
					
					$.gritter.add({
						title: '学期删除',
						text: delRet.code == "1" ? "删除成功" : delRet.msg,
						sticky: false,
						time: ''
					});
					dyObj.reloadGrid();
				});
			}
		});
	});
	
	//设置当前学期
	$("body").on(ace.click_event,"button[dyid='set-semester']", function() {
		var _semesterId = $(this).attr("semesterId");		
		var _schId = $(this).attr("schId");	
		bootbox.confirm("确认设置为当前学期吗？",function(r){
			if(r){
				$.get(path + "/admin/semester/set",{id : _semesterId,schId:_schId},function(delRet){
					
					$.gritter.add({
						title: '设置当前学期',
						text: delRet.code == "1" ? "设置成功" : delRet.msg,
						sticky: false,
						time: ''
					});
					dyObj.reloadGrid();
				});
			}
		});
	});
	
	//按条件查询菜单
	$("#find_btn").click(function(){		
		$("#semester-table").jqGrid('setGridParam',{  
	        datatype:'json',  
	        mtype :'POST',
	        postData:{'name':$.trim($("#name").val())}, //发送数据  
	        page:1  
	    }).trigger("reloadGrid"); 
		});
	
});

