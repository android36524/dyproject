
/**
 * 管理关联学生JS
 */
var dyManageStu = (function(){

    return {

        /**
         * 创建Grid
         */
        createGrid : function(){
            var grid_selector = "#manageStu-table";
            jQuery(grid_selector).jqGrid({
                datatype: "local",
                height: 150,
                colNames:['序号','学校ID','年级及班级','学生姓名', '关系', '是否监护人','操作','关系','学生ID','家长ID','监护人val'],
                colModel:[
                    {name:'id',index:'id',width:150, sortable:false},
                    {name:'schId',index:'schId',hidden:true},
                    {name:'className',index:'className', width:160, sortable:false},
                    {name:'studentName',index:'studentName', width:160, sortable:false},
                    {name:'relationTypeName',index:'relationTypeName', width:160, sortable:false},
                    {name:'guardianName',index:'guardianName', width:150, sortable:false},
                    {name:'diyId',index:'diyId', width:160, sortable:false,formatter : function(j,options,rowObject){
                        return '<div class="visible-md visible-lg hidden-sm hidden-xs btn-group">' +

                            '<button type="button" class="btn btn-xs btn-danger tooltip-error" dyid="managedel-student" studentId="'+rowObject.studentId+'" parentId="'+rowObject.parentId+'"  data-rel="tooltip" data-placement="top" title="移除">' +
                            '<i class="icon-trash bigger-120"></i></button>' +

                            '</div>';
                    }},
                    {name:'relationType',index:'relationType',hidden:true},
                    {name:'studentId',index:'studentId',hidden:true},
                    {name:'parentId',index:'parentId',hidden:true},
                    {name:'guardian',index:'guardian',hidden:true}
                    
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
        },
        reloadGrid : function(){
            $("#manageStu-table").trigger("reloadGrid");
        },

        /**
         * 重新加载grid
         */
        asyncReload : function(){
         	if(_id){
         		$.get(path + "/admin/stuParent/findStuParentById",{id:_id,schId:_schId},function(ret){
         			var _stuList = ret.studentList;
         			for(var i = 0;i < _stuList.length;i++){
                 		var _stuObj = _stuList[i];
                 		var rowObj = {
                 				id:i+1,
                 				schId:_stuObj.schId,
                 				className:_stuObj.className,
                 				studentName:_stuObj.name,
                 				relationTypeName:_stuObj.relationType_showname,
                 				guardianName:_stuObj.guardian_showname,
                 				relationType:_stuObj.relationType,
                 				studentId:_stuObj.studentId,
                 				parentId:_stuObj.parentId,
                 				guardian:_stuObj.guardian
                 				};
                 		jQuery("#manageStu-table").jqGrid('addRowData', i + 1, rowObj);
         			}
         		});
         	}
        }
    };
})();

var rows = [];
var choseRowData;

function setLayerStu(stuId,stuName){
	$("#relaStu_id").val(stuId);
	$("#relaStu_name").val(stuName);
}

$(document).ready(function(){
	dyManageStu.createGrid();
	dyManageStu.asyncReload();
	
    //选择孩子
    $("#searchButton").on(ace.click_event, function() {
    	$.get(path + "/admin/stuParent/showStudent.jsp",function(ret){
            bootbox.dialog({
                title : "家长选择",
                message : ret,
                buttons : {
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
    
    /**
     * 移除关联记录
     */
    $("body").off(ace.click_event,"button[dyid='managedel-student']").on(ace.click_event,"button[dyid='managedel-student']",function(){	
        var _studentId = $(this).attr("studentId");
        var _parentId = $(this).attr("parentId");
        var selectedId = $("#manageStu-table").jqGrid("getGridParam", "selrow");  
       if (_parentId == "undefined" || _parentId==null) { 
    	   $("#manageStu-table").jqGrid('delRowData', selectedId); 
    	   dyManageStu.reloadGrid();
    	   return;
       }
        bootbox.confirm("确认移除此学生？",function(r){
            if(r){
                $.get(path + "/admin/stuParent/deleStudent",{studentId : _studentId,parentId:_parentId,schId:_schId},function(delRet){
             		if(delRet.code=="-2"){
             			bootbox.alert("不能删除，请至少关联一个孩子!");
             			return false;
             		}
                	$.gritter.add({
                        title: '关联学生移除',
                        text: delRet.code == "1" ? "删除成功" : "删除失败："+delRet.msg,
                        sticky: false,
                        time: ''
                    });
                    $("#manageStu-table").jqGrid('delRowData', selectedId); 
                	dyManageStu.reloadGrid();
                	parent.dyStuParent.reloadGrid();
                });
            }
        });
    });
    
});

/**
 * 关联孩子
 */
$("#choose_studentBtn").on('click',function(){
	parent.dyStuParent.choseRowData = choseRowData;
	parent.dyStuParent.assocChild("manageStudent_form");
});