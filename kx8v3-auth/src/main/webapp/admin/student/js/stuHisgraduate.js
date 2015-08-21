/**
 * 学生管理的JS
 */
var dyStudent = (function(){

	return {
		/**
		 * 学生模块初始化
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
				url : path + "/admin/student/findGraduateStu?schId="+_schId,
				datatype: "json",
				height: 400,
				colNames:['学校','年级及班级','学生姓名','学号','性别','毕业时间','家长手机','账号','学生状态'],
				colModel:[
					{name:'schoolName',index:'schoolName',type:'text',width:60},
					{name:'gradeNameAndSc',index:'gradeNameAndSc',type:'text', width:60},
					{name:'name',index:'name',type:'text',width:60},
					{name:'studentNo',index:'studentNo',type:'text',width:60},
					{name:'sex_showname',index:'sex_showname',type:'text',width:40},
					{name:'graduateTime',index:'graduateTime',type:'text',width:80},
					{name:'trelphone',index:'trelphone',type:'text',width:80},
					{name:'accountId',index:'accountId',type:'text',width:60},
					{name:'status_showname',index:'status',type:'text',width:60}
				],
				viewrecords : true,
				rowNum:30,
				rowList:[20,30,40,50],
				pager : pager_selector,
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
				caption: "<i class='icon-list'></i>毕业学生列表",
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
					for(var i=0;i<ret.length;i++){
						var obj = ret[i];
						$("#gradeId").append("<option value="+obj.id+">"+obj.name+"</option>");
					}
				}
			});
		},

		/**
		 * 根据年级查找班级
		 */
//		classInit : function(gradeId){
//			$.post(path+"/admin/class/findHisClassByGradeId?gradeId="+gradeId,function(ret){
//				if(ret){
//					for(var i=0;i<ret.length;i++){
//						var obj = ret[i];
//						$("#classId").append("<option value="+obj.id+">"+obj.name+"</option>");
//					}
//				}
//			});
//		},

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
		},
		/**
		 * 查询条件参数
		 * @returns
		 */
		getParamObj : function(){
			var paramObj = {};
			paramObj.gradeId = $('#gradeId').val();
//			paramObj.classId = $("#classId").val();
			paramObj.name =	$("#name").val();
			paramObj.tel = $("#tel").val();
			
			return paramObj;
		}

	};
})();


$(function(){
	var obj = _tree.initEdbTree("1",{}, function () {
		$("#form-search")[0].reset();
		var nodes = _edbTreeObj.getSelectedNodes();
		if(!_tree.isSchoolNode(nodes[0])){
			return;
		}
		_schId = nodes[0].id;
		$("#stuEntrance-table").jqGrid('setGridParam',{
			url : path + "/admin/student/findGraduateStu?schId="+_schId,
			datatype:'json',
			mtype :'POST',
			postData:dyStudent.getParamObj,
			page:1
		}).trigger("reloadGrid");
		$("#gradeId").empty();
		$("#gradeId").prepend("<option value=''>请选择</option>");
		dyStudent.gradeInit(_schId);
	});
	_schId=obj.id;

	dyStudent.init();
	dyStudent.gradeInit(_schId);

	// 根据年级初始化班级
	$("#gradeId").change(function () {
		$("#classId").empty();
		$("#classId").prepend("<option value=''>请选择</option>");
		var gradeId = $(this).val();
		if(gradeId != "") {
			dyStudent.classInit(gradeId);
		}
	});

	//按条件查询
	$("#find_btn").click(function(){
		$("#stuEntrance-table").jqGrid('setGridParam',{
			datatype:'json',
			mtype :'POST',
			postData:{
				'gradeId':$('#gradeId').val(),
//				'classId':$("#classId").val(),
				'name':$("#name").val(),
				'studentNo':$("#studentNo").val(),
				'startTime':$("#startTime").val(),
				'endTime':$("#endTime").val(),
				'tel':$("#tel").val()
			}, //发送数据
			page:1
		}).trigger("reloadGrid");
	});
});
 