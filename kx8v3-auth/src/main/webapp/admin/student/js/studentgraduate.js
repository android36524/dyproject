/**
 * 学生升学毕业管理的JS
 */
var dyStuEntrance = (function(){
	
	return {
		
		/**
		 * 学生升学毕业管理
		 * */
		init : function(){
			this.createGrid();
		},
		
		/**
		 * 创建Grid
		 * */
		createGrid : function(){
			var grid_selector = "#stuEntrance-table";
			var pager_selector = "#stuEntrance-pager";
			
			jQuery(grid_selector).jqGrid({
				url : path + "/admin/student/ListPage?schId="+_schId,
				datatype: "json",
				height: 450,
				colNames:['年级及班级','学生姓名','学号','性别','家长','家长手机','班级毕业日期','账号','学生在校状态','ID'],
				colModel:[
				       {name:'gradeNameAndSc',index:'gradeNameAndSc',type:'text', width:120},
				       {name:'name',index:'name',type:'text',width:120},
				       {name:'rollCode',index:'rollCode',type:'text',width:120},
				       {name:'sex_showname',index:'sex_showname',type:'text',width:40},
				       {name:'parentName',index:'parentName',type:'text',width:120},
				       {name:'trelphone',index:'trelphone',type:'text',width:120},
				       {name:'',index:'',type:'text',width:120},
				       {name:'accountId',index:'accountId',type:'text',width:120},
				       {name:'rollStatus',index:'rollStatus',type:'text',width:120},
				       {name:'id',index:'id',hidden:true}
			],
			viewrecords : true,
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
			caption: "<i class='icon-list'></i>学生列表",
			autowidth: true
			});
		},
		
		/**
		 * 重新学生管理数据
		 * */
		reloadGrid : function(){
			$("#stuEntrance-table").trigger("reloadGrid");
		},
		
		 /**
         * 年级初始化
         */
        gradeInit : function(schId){
        	$.post(path+"/admin/grade/findGradeByTerm?schId="+schId,function(ret){
				if(ret){
					dyStuEntrance.removeSelect("gradeId");
					for(var i=0;i<ret.length;i++){
						var obj = ret[i];
						$("#gradeId").append("<option value="+obj.id+">"+obj.name+"</option>");
					}
				}
			});
       },
		/**
		 * 清除下拉选 数据
		 */
		removeSelect : function(selectId){
			var selectObject = $("#" + selectId + " option");
			selectObject.each(function(){
				if( $(this).val() !== ""){
					$(this).remove();
				}
			});
		}
	};
})();


var grid_selector = "#stuEntrance-table";
$(function(){
	var obj = _tree.initEdbTree("1",{}, function () {

		$("#form-search")[0].reset();
		var nodes = _edbTreeObj.getSelectedNodes();
		if(!_tree.isSchoolNode(nodes[0])){
			return;
		}
		_schId = nodes[0].id;
		$("#stuEntrance-table").jqGrid('setGridParam',{
			url : path + "/admin/student/ListPage?orgId="+_schId,
			datatype:'json',
			mtype :'POST',
			postData:{'name':$("#name").val()}, //发送数据
			page:1
		}).trigger("reloadGrid");
		dyStuEntrance.gradeInit(_schId);
	});
	_schId=obj.id;
	dyStuEntrance.gradeInit(_schId);
	dyStuEntrance.init();
	
	//按条件查询
	$("#find_btn").click(function(){
		$("#stuEntrance-table").jqGrid('setGridParam',{
	        datatype:'json',  
	        mtype :'POST',
	        postData:{
	        	'gradeId':$("#gradeId").val(),
	        	'classId':$("#classId").val(),
	        	'name':$("#name").val(),
	        	'tel':$("#tel").val(),
				'studentNo':$("#studentNo").val()
	        }, //发送数据  
	        page:1  
	    }).trigger("reloadGrid"); 
	});
	
	//年级选择
	$("#gradeId").change(function(){
		var gradeId = $("#gradeId").val();
		$.post(path+'/admin/stuParent/findClassByGrade',{gradeId:gradeId},function(retList){
			if(retList){
				dyStuEntrance.removeSelect("classId");
				for (var i = 0; i < retList.length; i++) {
					var gradeObj = retList[i];
					$("#classId").append("<option value='"+gradeObj.id+"'>"+gradeObj.name+"</option>");
				}
			}
		});
	});
	
	//毕业
	$("#btn-graduateStuParent").click(function(){
		s = jQuery(grid_selector).jqGrid('getGridParam', 'selarrrow');
		if(s.length > 0){
			var idArr = s.toString().split(",");
			var _id = [];
	    	for(var i = 0;i < idArr.length;i++){
	    		var row = $(grid_selector).jqGrid('getRowData',idArr[i]);
	    		_id.push(row.id);
	    	}
	    	$.post(path+'/admin/student/stuGraduate',{id:_id.toString()},function(ret){
	    		$.gritter.add({
					title: '学生毕业',
					text: ret.code == "1" ? "学生毕业成功！" : "学生毕业失败！",
					sticky: false,
					time: ''
				});
	    		dyStuEntrance.reloadGrid();
	    	});
		}else{
			bootbox.alert("请选择需要毕业的学生");
			return;
		}
	});
	
	//增加学生
	$("#btn-addstudent").on(ace.click_event,function(){
		$.post(path + "/admin/student/goAdd",function(ret){
			bootbox.dialog({
				title : "新增/修改班级信息",
				message : ret,
				buttons : {
					success : {
						label: "保存",
                        className: "btn-success",
                        callback :  function(result) {
							if(result) {
								if($("#student_form").valid()){
									$.ajaxSetup({async : false});
									$.post(path + "/admin/student/add",$("#student_form").formToObject(),function(addRet){
										$.gritter.add({
											title: '班级',
											text: addRet.code == "1" ? "新增班级信息成功!" : "新增班级信息失败!",
											sticky: false,
											time: '',
											class_name: "gritter-light"
										});
										dyStudent.reloadGrid();
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
});
 