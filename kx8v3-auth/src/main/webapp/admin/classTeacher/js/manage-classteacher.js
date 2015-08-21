var _deleteIds = [];
//初始化任课老师列表
function initClassTeacherGrid(){
	var grid_selector = "#classTeacher-table";
	jQuery(grid_selector).jqGrid({
		datatype: "local",
		height: 150,
		colNames:['id','科目','任课老师','操作','科目ID','老师ID','学期id','班级id'],
		colModel:[
			{name:'id',index:'id',hidden:true},
			{name:'subjectName',index:'subjectName', width:180, sortable:false},
			{name:'teacherName',index:'teacherName', width:180, sortable:false},
			{name:'diyId',index:'diyId', width:160, sortable:false,formatter : function(j,options,rowObject){
				return '<div class="visible-md visible-lg hidden-sm hidden-xs btn-group">' +

					'<button type="button" class="btn btn-xs btn-danger tooltip-error" dyid="del-classTeacher" data-rel="tooltip" data-placement="top" title="移除">' +
					'<i class="icon-trash bigger-120"></i></button>' +

					'</div>';
			}},
			{name:'subjectId',index:'subjectId',hidden:true},
			{name:'teacherId',index:'teacherId',hidden:true},
			{name:'semesterId',index:'semesterId',hidden:true},
			{name:'classId',index:'classId',hidden:true}

		],
		viewrecords : true,
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
		autowidth: true

	});
}

//初始化年级班级学期
function initSelect(){  
	$.post(path+"/admin/classTeacher/initSelect",{classId:_classId,semesterId:_semesterId}, function (ret) {
		$("#semester").append("<option value='" + ret.semesterObj.id + "'>" + ret.semesterObj.name + "</option>");
		$("#class_grade").append("<option value='" + ret.classObj.gradeId + "'>" + ret.classObj.gradeName + "</option>");
		$("#class_class").append("<option value='" + ret.classObj.classId + "'>" + ret.classObj.className + "</option>");
		var subjectList = ret.subjectList;
		if(subjectList != null){
			$.each(subjectList,function(index,ele){
				$("#subject").append("<option value='" + ele.id + "'>" + ele.name + "</option>");
			});
		}
		$("#class_schId").val(ret.schId);
	});
}

//根据班级id获取该班级科目任课老师
function findClassTeacherByClassId(){
	$.post(path+"/admin/classTeacher/findClassTeacherByClassId",{classId:_classId}, function (ret) {
		$.each(ret, function (index,ele) {
			$("#classTeacher-table").jqGrid("addRowData",index + 1,ele);
		});
	});
}

$(function(){
	initClassTeacherGrid();
	initSelect();
	findClassTeacherByClassId();
	//选择任课老师
	$("#btn_teacher").on(ace.click_event, function() {
		var schId = $("#class_schId").val();
		$.get(path + "/admin/common/teacherChoose/show_teacher.jsp?schId="+schId,function(ret){
            bootbox.dialog({
                title : "任课老师选择",
                message : ret,
                buttons : {
                    success : {
                        label: "选择带回",
                        className: "btn-success",
                        callback :  function(result) {
                        	var rowData = JSON.parse($("#selected").val());
            				$("#class_teacher").val(rowData.name);
            				$("#classTeacherId").val(rowData.id);
            				//关闭弹窗
            				commonJs.closedialog("empe-tab"); //关闭当前弹窗
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

	//确认按钮单击事件
	$("#confirmBtn").on(ace.click_event, function() {
		var data = {};
		data.subjectName = $("#subject").find("option:selected").text();
		data.subjectId = $("#subject").val();
		data.teacherName = $("#class_teacher").val();
		data.teacherId = $("#classTeacherId").val();
		data.semesterId = $("#semester").val();
		data.classId = $("#class_class").val();
		if(data.subjectName != "" && data.teacherName != ""){
			var subjectIdArray = $("#classTeacher-table").jqGrid('getCol','subjectId',false);
			if(jQuery.inArray(data.subjectId,subjectIdArray) == -1){
				var rowNumber =$("#classTeacher-table").jqGrid('getGridParam','records');
				$("#classTeacher-table").jqGrid("addRowData",rowNumber + 1,data);
				$("#class_teacher").val("");
				$("#classTeacherId").val("");
			}else{
				bootbox.alert("该科目已设置任课老师！", function () {
				});
			}
		}
	});

	//移除按钮单击事件
	$("body").on(ace.click_event,"button[dyid='del-classTeacher']",function(){
		var rowNumber = $("#classTeacher-table").jqGrid("getGridParam", "selrow");
		var rowData = $("#classTeacher-table").jqGrid("getRowData",rowNumber);
		if(rowData.id != null && rowData.id != ""){
			_deleteIds.push(rowData.id);
		}
		$("#classTeacher-table").jqGrid('delRowData', rowNumber);

	});
});

