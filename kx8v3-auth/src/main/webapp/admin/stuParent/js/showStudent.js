/**
 * 选择学生列表JS
 */

var dyStudent = (function(){

    return {

        /**
         * 
         */
        init : function(){
            this.createGrid();
        },

        /**
         * 创建Grid
         */
        createGrid : function(){
            var grid_selector = "#student-tab";
            var pager_selector = "#student-pager";
            jQuery(grid_selector).jqGrid({
                url : path + "/admin/student/ListPage?schId="+parent._schId,
                datatype: "json",
                height: 200,
                colNames:['ID','学生姓名','所在年级和班级','出生日期'],
                colModel:[
                    {name:'id',index:'id',width:200, sortable:false},
                    {name:'name',index:'name',width:200, sortable:false},
                    {name:'gradeNameAndSc',index:'gradeNameAndSc',width:200,sortable:false},
                    {name:'birthDate',index:'birthDate',width:200,sortable:false}
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
				caption: "<i class='icon-list'></i>学阶列表",
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
						$("#stu_gradeId").append("<option value="+obj.id+">"+obj.name+"</option>");
					}
				}
			});
        },
        
		/**
		 * 根据年级查找班级
		 */
		classInit : function(gradeId){
        	$.post(path+"/admin/class/findClassByGradeId?gradeId="+gradeId,function(ret){
				if(ret){
					for(var i=0;i<ret.length;i++){
						var obj = ret[i];
						$("#stu_classId").append("<option value="+obj.id+">"+obj.name+"</option>");
					}
				}
			});
        },

        /**
         * 重新加载grid
         */
        reloadGrid : function(){
            $("#student-tab").trigger("reloadGrid");
        }
        
    };
})();

$(function(){
	dyStudent.init();
	dyStudent.gradeInit(parent._schId);
	
	// 根据年级初始化班级
	  $("#stu_gradeId").change(function () {
		  	$("#stu_classId").empty();
			$("#stu_classId").prepend("<option value=''>请选择</option>");
	        var gradeId = $(this).val();
	        if(gradeId != "") {
	            dyStudent.classInit(gradeId);
	        }
	    });
	//选择孩子
	$("#choose_saveBtn").click(function(){
		var grid_selector = "#student-tab";
		var id = jQuery(grid_selector).jqGrid('getGridParam','selrow');
		var rowData = $(grid_selector).jqGrid('getRowData',id);
		parent.choseRowData = "";
		parent.choseRowData = rowData;
		parent.setLayerStu(id,rowData.name);
		commonJs.closedialog("student-tab"); //关闭当前弹窗
	});
	
	 //按条件查询
    $("#stufind_btn").click(function(){
    	var _gradeId = $("#stu_gradeId option:selected").val();
    	var _classId = $("#stu_classId option:selected").val();
    	var _name = $("#name").val();
    	var params = {gradeId:_gradeId,classId:_classId,name:_name, schId:parent._schId};
        $("#student-tab").jqGrid('setGridParam',{
            datatype:'json',
            mtype :'POST',
            postData:params, //发送数据
            page:1
        }).trigger("reloadGrid");
    });
	
});
