/**
 * 排课管理
 */
 
var max_showrow =8; 
var dyObj = (function(){
	
	return {
		/**
		 * 班级模块初始化
		 * */
		init : function(){
			this.createGrid();
		},
		
		/**
		 * 创建Grid
		 * */
		createGrid : function(){
			var grid_selector = "#class-table";
			var pager_selector = "#class-pager";
			
			jQuery(grid_selector).jqGrid({
				url : path + "/admin/class/listClassByPage?schId="+_schId,
				datatype: "json",
				height: 450,
				colNames:['学校','年级别名','班级名称','班主任','排序','操作',''],
				colModel:[
				       {name:'schoolName',index:'schoolName',type:'text',width:120},
				       {name:'gradeName',index:'gradeName',type:'text', width:50},
				       {name:'className',index:'className',type:'text',width:50},
				       {name:'headTeacher',index:'headTeacher',type:'text',width:80},
				       {name:'seq',index:'id',width:40},
				       {name:'id',index:'id', width:120, sortable:false,formatter : function(e, options, rowObject){
				    	   var arrangeBtn = commonJs.createDefaultAuthBtn(commonJs.btn_name.arrange,"arrange-schedult","排课",{classId:e});
				    	   var classInfo = "";
				       		if(rowObject["scheduleId"]>0){
				       			classInfo ='<button class="btn btn-xs btn-warning tooltip-danger" dyid="show-schedult" classId="'+String(e)+
								'" className="'+rowObject.className+'" data-rel="tooltip" data-placement="top" title="查看课表">' +
								'<i class="icon-list-alt bigger-120"></i></button>';
				       		}	
				    	   return '<div class="visible-md visible-lg hidden-sm hidden-xs btn-group">' +arrangeBtn+classInfo+
							'</div>';
				    	   
				       }},
				       {name:'id',hidden : true}
			],
			viewrecords : true,
			autowidth : true,
			rowNum:30,
			rowList:[20,30,40,50],
			pager : pager_selector,
			altRows: true,
			multiselect: true,
	        multiboxonly: true,
			loadComplete : function() {
				var table = this;
				setTimeout(function(){
					dyjsCommon.enableTooltips(table);
					dyjsCommon.updatePagerIcons(table);
					$('button[menuid]').tooltip({container : 'body'});
				}, 0);
			},
			caption: "<i class='icon-list'></i>班级列表",
			autowidth: true
			});
	},
		/**
	     * 年级初始化
	     */
	    gradeInit : function(schId){
	    	$.post(path+"/admin/grade/findGradeByTerm?schId="+schId,function(ret){
				if(ret){
					for(var i=0;i<ret.length;i++){
						var obj = ret[i];
						$("#gradeId").append("<option value="+obj.id+">"+obj.name+"</option>");
					}
					$("#gradeId").change();
				}
			});
	    },
	   classInit: function(){
			$("#className").empty();
			var _gradeId = $("#gradeId").val();
			$("#className").append("<option value=''>请选择</option>");
			if(!_gradeId) return;
			$.post(path + "/admin/class/listClassByPage",{gradeId:_gradeId,page:1,rows:30,className:""},function(data){	
				$(data.rows).each(function(k,d){
					$("#className").append("<option value='"+d.id+"'>"+d.name+"</option>");
				});
			});	
		},
		/**
		 * 重新加载班级数据
		 * */
		reloadGrid : function(){
			$("#class-table").trigger("reloadGrid");
		},
		/**
		 * 查询条件参数
		 * @returns
		 */
		getParamObj : function(){
			var paramObj = {};
			paramObj.gradeId = $('#gradeId').val();
			paramObj.classId = $("#className").val();
			return paramObj;
		}
	};
})();

$(function(){
	$("#gradeId").change(function(){
		dyObj.classInit();
	});
	var obj = _tree.initEdbTree("1",{}, function () {
		$("#form-search")[0].reset();
		var nodes = _edbTreeObj.getSelectedNodes();
		if(!_tree.isSchoolNode(nodes[0])){
			return;
		}
		_schId = nodes[0].id;
		$("#class-table").jqGrid('setGridParam',{
			url : path + "/admin/class/listClassByPage?schId="+_schId,
			datatype:'json',
			mtype :'POST',
			postData:dyObj.getParamObj(),
			page:1
		}).trigger("reloadGrid");
		$("#gradeId").empty();
		$("#gradeId").prepend("<option value=''>请选择</option>");
		dyObj.gradeInit(_schId);
	});
	_schId=obj.id;	
	dyObj.init();
	dyObj.gradeInit(_schId);
	//按条件查询
	$("#find_btn").click(function(){
		$("#class-table").jqGrid('setGridParam',{  
	        datatype:'json',  
	        mtype :'POST',
	        postData:dyObj.getParamObj(), //发送数据  
	        page:1  
	    }).trigger("reloadGrid"); 
	});
	//按条件查询
	$("#batchfind_btn").click(function(){
		var selectedIds = $("#class-table").jqGrid("getGridParam", "selarrrow");
		if(selectedIds.length<=0){
			$.gritter.add({
				title: '保存',
				text: "请选择至少一个班级",
				sticky: false,
				time: ''
			});
			return;
		}
		if(selectedIds.length>max_showrow){
			$.gritter.add({
				title: '保存',
				text: "请选择最多 "+max_showrow+" 个班级",
				sticky: false,
				time: ''
			});
			return;
		}
		$.post(path + "/admin/schedule/showMultiArrange.jsp",{"schId":_schId,ids:selectedIds.join(",")},function(ret){
			bootbox.dialog({
				title : "排课信息",
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
	});
	
	$("body").on(ace.click_event,"button[dyid='arrange-schedult']", function() {
		var _classId = $(this).attr("classId");			
		commonJs.toUrl("班级排课","保存",path + "/admin/schedule/toArrange?classId="+_classId+"&schId="+_schId,function(result) {
			if(result){
				var postData = {};
				var sublist=[];
				$("#assign-table").find("td[weekid][sectionId][teaId][subId]").each(function(){					
					var temp = {};
					temp.weekId = $(this).attr("weekId");
					temp.sectionId =$(this).attr("sectionId");
					temp.teacherId =$(this).attr("teaId") ;
					temp.subjectId=$(this).attr("subId");							
					sublist[sublist.length]=temp;					
				});
							
				var hdSection =[];
				var showSection =[];
				$("input[name='cksection']").each(function(){
					if($(this).is(':checked')){						
						hdSection[hdSection.length]=$(this).attr("sectionId");
					}else{
						showSection[showSection.length]=$(this).attr("sectionId");
					}
				});
				postData["classId"]=_classId;	
				postData["subjectList"]=JSON.stringify(sublist);	
				postData["hdSection"]=JSON.stringify(hdSection);
				postData["showSection"]=JSON.stringify(showSection);
				postData["semesterId"]=$("#semersterId").val();
				postData["scheduleId"]=$("#scheduleId").val();
				$.post(path + "/admin/schedule/saveArrange", postData,function(addRet){
					$.gritter.add({
						title: '保存',
						text: addRet.code == "1" ? "保存成功" : addRet.msg,
						sticky: false,
						time: ''
					});
					dyObj.reloadGrid();
				});
			}
		});
	});
	
	$("body").on(ace.click_event,"button[dyid='show-schedult']", function() {
		var _classId = $(this).attr("classId");	
		$.get(path + "/admin/schedule/toArrange?classId="+_classId+"&schId="+_schId+"&flag=1",function(ret){
			bootbox.dialog({
				title : "排课信息",
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
		
	});
});
 