/**
 * 学阶管理的JS
 */
 
var dySubject = (function(){
	
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
			var grid_selector = "#subject-table";
			var pager_selector = "#subject-pager";
			var arrColNames;
			var arrColModel=[];
			if(_flag==1){
				arrColNames = ['科目名称','科目编码','排序','创建人','创建时间','操作'];
			}else{
				arrColNames = ['学校','科目名称','科目编码','排序','创建人','创建时间','操作'];
				arrColModel[arrColModel.length]={name:'schId_showname',sortable:false,index:'schId',type:'text',width:120};
			}
			var arrCM1 = [
				{name:'name',index:'id',sortable:false,type:'text',width:80},
				{name:'code',index:'id',sortable:false,width:60, sorttype:"string"},
				{name:'seq',index:'seq',sortable:false,type:'text',width:50},
				{name:'creatorName',index:'creatorName',sortable:false,width:60},
				{name:'createTime',index:'createDate',width:80,sorttype:false},
				{name:'id',index:'id', width:80, sortable:false,formatter : function(e,options, rowObject){
					var editBtn = commonJs.createDefaultAuthBtn(commonJs.btn_modifycode,"modify-subject","修改",{subjectId:String(e)});
					var delBtn = commonJs.createDefaultAuthBtn(commonJs.btn_delcode,"del-subject","删除",{subjectId:String(e),subjectName:rowObject["name"],flag:rowObject["flag"],code:rowObject["code"]});
					return '<div class="visible-md visible-lg hidden-sm hidden-xs btn-group">' +editBtn+delBtn+
							'</div>';
					/*return '<div class="visible-md visible-lg hidden-sm hidden-xs btn-group">' +
								'<button class="btn btn-xs btn-info tooltip-info" dyid="modify-subject" subjectId="'+String(e)+'" data-rel="tooltip" data-placement="top" title="修改">' +
								'<i class="icon-edit bigger-120"></i></button>' +
								'<button class="btn btn-xs btn-danger tooltip-danger" dyid="del-subject" subjectId="'+String(e)+'" subjectName="'+rowObject["name"]+'" flag="'+rowObject["flag"]+'" code="'+rowObject["code"]+'"  data-rel="tooltip" data-placement="top" title="删除">' +
								'<i class="icon-trash bigger-120"></i></button>' +
							'</div>';*/
				}}
			];
			arrColModel = $.merge(arrColModel,arrCM1);		   
		     
		     jQuery(grid_selector).jqGrid({
					url : path + "/admin/subject/list?flag="+_flag+"&schId="+_schId,
					datatype: "json",
					height: 450,
					colNames:arrColNames,
					colModel:arrColModel, 
					viewrecords : true,
					rowNum:30,
					rowList:[20,30,40,50],
					pager : pager_selector,
					rownumbers:true,//显示行号
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
					caption: "<i class='icon-list'></i>科目列表",
					autowidth: true			
				});		  
		},
		
		/**
		 * 重新加载学阶数据
		 */
		reloadGrid : function(){
			$("#subject-table").trigger("reloadGrid");
		}
		
	};
})();
 
$(function(){
	if($('#div_main').layout){
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
			$("#subject-table").jqGrid('setGridParam',{
				url : path + "/admin/subject/list?flag="+_flag+"&schId="+_schId,
			    datatype:'json',  
			    mtype :'POST',
			    postData:{'name':$("#name").val()}, //发送数据  
			    page:1  
			}).trigger("reloadGrid"); 			
		});
		_schId=obj.id;
	}
	dySubject.init();	
	
	$("#btn-addSubject").on(ace.click_event,function(){
		$.post(path + "/admin/subject/toAdd?flag="+_flag+"&schId="+_schId,function(ret){
			if(ret.code=="-1"){
				$.gritter.add({
					title: '科目新增',
					text: ret.msg,
					sticky: false,
					time: '',
					class_name: "gritter-light"
				});
			 	return;
			}
			bootbox.dialog({
				title : "新增科目",
				message : ret,
				buttons : {
					success : {
						label: "保存",
                        className: "btn-success",
                        callback :  function(result) {
							if(result) {
								if($("#subject_form").valid()){
									$.ajaxSetup({async : false});								
									$.post(path + "/admin/subject/add",$("#subject_form").formToObject(),function(addRet){
										$.gritter.add({
											title: '科目新增',
											text: addRet.code == "1" ? "添加成功" : addRet.msg,
											sticky: false,
											time: '',
											class_name: "gritter-light"
										});
										dySubject.reloadGrid();
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
	$("body").on(ace.click_event,"button[dyid='modify-subject']", function() {
		var _subjectId = $(this).attr("subjectId");		
		$.get(path + "/admin/subject/toAdd?flag="+_flag+"&id="+_subjectId+"&schId="+_schId,function(ret){
			bootbox.dialog({
				title : "更新科目",
				message : ret,
				buttons : {
					success : {
						label: "更新",
                        className: "btn-success",
                        callback :  function(result) {
							if(result) {
								if($("#subject_form").valid()){
									
									$.ajaxSetup({async : false});
								
									$.post(path + "/admin/subject/add", $("#subject_form").formToObject(),function(addRet){
										$.gritter.add({
											title: '科目更新',
											text: addRet.code == "1" ? "更新成功" : addRet.msg,
											sticky: false,
											time: ''
										});
										dySubject.reloadGrid();
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
	
	//更新课时
	$("body").on(ace.click_event,"button[dyid='del-subject']", function() {
		var _subjectId = $(this).attr("subjectId");		
		var _subjectName = $(this).attr("subjectName");
		var _flag = $(this).attr("flag");
		var _code = $(this).attr("code");
		bootbox.confirm("您将删除 "+"<font color='red' size='2px'><b>"+_subjectName+"</b></font>"+" 科目信息，删除后信息将无法恢复，请确认此次操作？",function(r){
			if(r){
				$.get(path + "/admin/subject/del",{id : _subjectId,flag:_flag,code:_code},function(delRet){					
					$.gritter.add({
						title: '科目删除',
						text: delRet.code == "1" ? "删除成功" : delRet.msg,
						sticky: false,
						time: ''
					});
					dySubject.reloadGrid();
				});
			}
		});
	});
	
	//按条件查询菜单
	$("#find_btn").click(function(){		
		$("#subject-table").jqGrid('setGridParam',{  
	        datatype:'json',  
	        mtype :'POST',
	        postData:{'name':$.trim($("#name").val())}, //发送数据  
	        page:1  
	    }).trigger("reloadGrid"); 
	});  
	
});

