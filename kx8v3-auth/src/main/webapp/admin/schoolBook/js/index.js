/**
 * 年级管理的JS
 */
 
var dySchoolBook = (function(){
	
	return {
		
		/**
		 * 学阶管理模块初始化
		 */
		init : function(){
			this.createGrid();
		},
		
		/**
		 * 初始化学阶
		 */
		initStage:function(){
			$.post(path+"/admin/stage/findStageAll",function(stageList){
                if(stageList){
                    for (var i = 0; i < stageList.length; i++) {
                        var stageObj = stageList[i];
                        $("#schoolBookVer_stage").append("<option value='" + stageObj.id + "'>" + stageObj.name + "</option>");
                        
                    }
                }
            });
		},
		
		/**
		 * 删除select选项
		 */
		deleteSelectItem : function(selectId){
			var deleteSelectItemObj = $("#"+selectId+" option");
			deleteSelectItemObj.each(function(){
				if($(this).val()!==""){
					$(this).remove();
				}
			});
		},
		
		/**
		 * 创建Grid
		 */
		createGrid : function(){
			var grid_selector = "#schoolBook-table";
			var pager_selector = "#schoolBook-pager";
		
			jQuery(grid_selector).jqGrid({
				url : path + "/admin/schoolBook/listPage",
				datatype: "json",
				height: 450,
				colNames:['教材名称','学阶ID','所属学阶','年级ID','所属年级','科目ID','所属科目','上下册','出版社','出版日期','创建人','创建时间','操作'],
				colModel:[
						{name:'schbookverName',schbookverName:'name',type:'text',width:100},
						{name:'stageId',index:'stageId',width:60,hidden:true},
						{name:'stageName',schbookverName:'stageName',type:'text',hidden:true,width:100},
						{name:'gradeId',index:'gradeId',width:60,hidden:true},
						{name:'gradeName',index:'gradeName',width:60, sorttype:"string"},
						{name:'subjectId',index:'subjectId',width:60,hidden:true},
						{name:'subjectName',index:'subjectName',width:100,sorttype:'string'},
						{name:'volume',index:'volume',width:50,sorttyoe:'string',formatter:function(e){
							if(e=="100"){
								return '<lable>不分册</lable>';
							}else if(e=="101"){
								return '<lable>上册</lable>';
							}else{
								return '<lable>下册</lable>';
							}
						}},
						{name:'pressName',index:'pressName',width:100,sorttype:'string'},
						{name:'pressTime',index:'pressTime',width:100,sorttype:'string'},
						{name:'creatorName',index:'creatorName',width:100,sorttype:'string'},
						{name:'createTime',index:'createTime',width:100,sorttype:false},
						{name:'id',index:'id', width:80, sortable:false,formatter : function(e){
							
							var editBtn = commonJs.createDefaultAuthBtn(commonJs.btn_modifycode,"modify-schoolBook","修改",{schoolBookId:e});
							var delBtn = commonJs.createDefaultAuthBtn(commonJs.btn_delcode,"del-schoolBook","删除",{schoolBookId:e});
							return '<div class="visible-md visible-lg hidden-sm hidden-xs btn-group">' +editBtn+delBtn+
							'<button class="btn btn-xs btn-warning tooltip-info" dyid="open-schoolBookChapter" schoolBookId="'+e+'"  data-rel="tooltip" data-placement="top" title="章节管理">' +
							'章节管理</button>' +
						'</div>';
							
							/*return '<div class="visible-md visible-lg hidden-sm hidden-xs btn-group">' +
										'<button class="btn btn-xs btn-info tooltip-info" dyid="modify-schoolBook" schoolBookId="'+e+'" data-rel="tooltip" data-placement="top" title="修改">' +
										'<i class="icon-edit bigger-120"></i></button>' +
										'<button class="btn btn-xs btn-danger tooltip-error" dyid="del-schoolBook" schoolBookId="'+e+'"  data-rel="tooltip" data-placement="top" title="删除">' +
										'<i class="icon-trash bigger-120"></i></button>' +
										'<button class="btn btn-xs btn-warning tooltip-info" dyid="open-schoolBookChapter" schoolBookId="'+e+'"  data-rel="tooltip" data-placement="top" title="章节管理">' +
										'章节管理</button>' +
									'</div>';*/
						}}
				], 
				viewrecords : true,
				rowNum:30,
				rowList:[20,30,40,50],
				pager : pager_selector,
				rownumbers:true,
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
				caption: "<i class='icon-list'></i>教材管理列表",
				autowidth: true
		
			});
		},
		
		/**
		 * 重新加载学阶数据
		 */
		reloadGrid : function(){
			$("#schoolBook-table").trigger("reloadGrid");
		}
		
	};
})();

function initGrade(){
	$.post(path+"/admin/grade/findGradeByStage",function(gradeList){
		if(gradeList){
		dySchoolBook.deleteSelectItem("schoolBookVer_Grade");
		dySchoolBook.deleteSelectItem("schoolBookVer_subject");
      	  var grade_dom = $("#schoolBookVer_Grade");
            for (var i = 0; i < gradeList.length; i++) {
                var gradeObj = gradeList[i];
                grade_dom.append("<option value='" + gradeObj.id + "'>" + gradeObj.name + "</option>");
            }
        }
	});
}
 
$(function(){
	dySchoolBook.init();
	dySchoolBook.initStage();
	initGrade();
	//学阶改变事件
	$("#schoolBookVer_stage").change(function(){
		var stageId = $(this).val();
		if(stageId==""){
			
			 $("#schoolBookVer_Grade option[index!='0']").remove();
			 $("#schoolBookVer_subject option[index!='0']").remove();
			 $("#schoolBookVer_Grade").prepend("<option selected='selected' value=''>---请选择---</option>");
			 $("#schoolBookVer_subject").prepend("<option selected='selected' value=''>---请选择---</option>");
			 
		}else{
		$.post(path+"/admin/grade/findGradeByStage",{stageId:stageId},function(gradeList){
			if(gradeList){
			dySchoolBook.deleteSelectItem("schoolBookVer_Grade");
			dySchoolBook.deleteSelectItem("schoolBookVer_subject");
          	  var grade_dom = $("#schoolBookVer_Grade");
                for (var i = 0; i < gradeList.length; i++) {
                    var gradeObj = gradeList[i];
                    grade_dom.append("<option value='" + gradeObj.id + "'>" + gradeObj.name + "</option>");
                }
            }
		});
	  }
	});
	
	//年级 改变事件
	$("#schoolBookVer_Grade").change(function(){
		
		var gradeId =$(this).val();
		if(gradeId==""){
			$("#schoolBookVer_subject option[index!='0']").remove();
			$("#schoolBookVer_subject").prepend("<option selected='selected' value=''>---请选择---</option>");
		}else{
			$.post(path+"/admin/subject/findSubjectByGrade",{gradeId:gradeId},function(gradeList){
				if(gradeList){
				 dySchoolBook.deleteSelectItem("schoolBookVer_subject");
	          	  var grade_dom = $("#schoolBookVer_subject");
	                for (var i = 0; i < gradeList.length; i++) {
	                    var gradeObj = gradeList[i];
	                    grade_dom.append("<option value='" + gradeObj.id + "'>" + gradeObj.name + "</option>");
	                }
	            }
			});
		}
		
	});
	
	$("#btn-addschoolBook").on(ace.click_event,function(){
		$.post(path + "/admin/schoolBook/schoolBook-modify.jsp",function(ret){
			bootbox.dialog({
				title : "新增教材",
				message : ret,
				buttons : {
					success : {
						label: "保存",
                        className: "btn-success",
                        callback :  function(result) {
							if(result) {
								if($("#schoolBook_form").valid()){
									$.ajaxSetup({async : false});
									$.post(path + "/admin/schoolBook/save",$("#schoolBook_form").formToObject(),function(addRet){
										$.gritter.add({
											title: '教材',
											text: addRet.code == "1" ? "添加成功" : "添加失败",
											sticky: false,
											time: '',
											class_name: "gritter-light"
										});
										dySchoolBook.reloadGrid();
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
	
	//更新教材
	$("body").on(ace.click_event,"button[dyid='modify-schoolBook']", function() {
		var _id = $(this).attr("schoolBookId");
		$.get(path + "/admin/schoolBook/schoolBook-modify.jsp?id="+_id,function(ret){
			bootbox.dialog({
				title : "更新教材",
				message : ret,
				buttons : {
					success : {
						label: "更新",
                        className: "btn-success",
                        callback :  function(result) {
							if(result) {
								if($("#schoolBook_form").valid()){
									$.ajaxSetup({async : false});
									$.post(path + "/admin/schoolBook/save", $("#schoolBook_form").formToObject(),function(addRet){
										$.gritter.add({
											title: '教材',
											text: addRet.code == "1" ? "更新成功" : "更新失败",
											sticky: false,
											time: ''
										});
										dySchoolBook.reloadGrid();
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
	
	//删除教材
	$("body").on(ace.click_event,"button[dyid='del-schoolBook']",function(){
		var schoolBookId = $(this).attr("schoolBookId");
		
		var id=$("#schoolBook-table").jqGrid("getGridParam","selrow");
		var rowData = $("#schoolBook-table").jqGrid("getRowData",id);
		var schbookverName = rowData.schbookverName;
		
		bootbox.confirm("您将删除 "+"<font color='red' size='2px'><b>"+schbookverName+"</b></font>"+" 教材信息，删除后信息将无法恢复，请确认此次操作？",function(r){
			if(r){
				$.get(path + "/admin/schoolBook/del",{id : schoolBookId},function(delRet){
					$.gritter.add({
						title: '教材',
						text: delRet.code == "1" ? "删除成功" : "删除失败："+delRet.msg,
						sticky: false,
						time: ''
					});
					dySchoolBook.reloadGrid();
				});
			}
		});
	});
	
	//章节管理
	$("body").on(ace.click_event,"button[dyid='open-schoolBookChapter']",function(){
		var schoolBookId = $(this).attr("schoolBookId");
		
		var id=$("#schoolBook-table").jqGrid("getGridParam","selrow");
		var rowData = $("#schoolBook-table").jqGrid("getRowData",id);
		var stageName = rowData.stageName;
		var gradeName = rowData.gradeName;
		var subjectId = rowData.subjectId;
		var subjectName = rowData.subjectName;
		var schbookverName=rowData.schbookverName;
		
		var stageId =rowData.stageId;
		var gradeId = rowData.gradeId;
		
		$.post(path + "/admin/schoolBook/mainBL.jsp",{schoolBookId:schoolBookId,subjectId:subjectId,stageId:stageId,gradeId:gradeId},function(ret){
			bootbox.dialog({
				title:"章节管理  >>"+stageName+"  >>"+gradeName+"  >>"+schbookverName+"  >>"+subjectName,
				message : ret,
				buttons : {
					success : {
						label: "保存",
                        className: "btn-success",
                        callback :  function(result) {
							if(result) {
								if($("#section_form").valid()){
									$.ajaxSetup({async : false});
									var save_SectionId = $("#sectionId").val();
									var save_loreId = $("#loreId").val();
									var resultSelectLoreId = $("#selectLoreId").val();
									var loreId2 = $("#loreId2").val();//这个是保存此章节关联的知识点ID
									
									var flageSelect = $("#biaozhi").val();
									if(flageSelect==null||flageSelect==""){
										//alert("知识点未修改!!");
										bootbox.alert({ 
								            buttons: {  
								               ok: {  
								                    label: '确认',  
								                    className: 'btn-success'  
								                }  
								            },  
								            message: '知识点未修改！！',  
								            callback: function() {  
								                
								            },  
								            title: "提示信息", 
								        });  
										return false;
									}
									
									$.post(path + "/admin/section/saveSectionAndLore", $("#section_form").formToObject(),function(addRet){
										$.gritter.add({
											title: '章节与知识点',
											text: addRet.code == "1" ? "更新成功" : "更新失败",
											sticky: false,
											time: ''
										});
										dySchoolBook.reloadGrid();
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
			dySchoolBook.reloadGrid();
		});
	});
	
	//按条件查询菜单
	$("#find_btn").click(function(){
		var schoolBookName = $("#schoolBookName").val();
		var stageId =$("#schoolBookVer_stage").val();
		var gradeId =$("#schoolBookVer_Grade").val();
		var subject =$("#schoolBookVer_subject").val();
			$("#schoolBook-table").jqGrid('setGridParam',{  
	        datatype:'json',  
	        mtype :'POST',
	        postData:{'schoolBookName':schoolBookName,'stageId':stageId,'gradeId':gradeId,'subject':subject}, //发送数据  
	        page:1  
	    }).trigger("reloadGrid"); 
		});
	
});