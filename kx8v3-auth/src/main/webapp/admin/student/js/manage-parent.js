
/**
 * 学生家长管理
 */
var dyChooseStuParent = (function(){

    return {

        /**
         * 创建Grid
         */
        createGrid : function(){
            var grid_selector = "#chooseStuParent-table";
            jQuery(grid_selector).jqGrid({
                datatype: "local",
                height: 165,
                colNames:['序号','学校ID','家长姓名','手机号码', '关系', '是否监护人','操作','关系','学生ID','家长ID','监护人val'],
                colModel:[
                    {name:'id',index:'id',width:80, sortable:false},
                    {name:'schId',index:'schId',hidden:true},
                    {name:'name',index:'name', width:130, sortable:false},
                    {name:'telphone',index:'telphone', width:130, sortable:false},
                    {name:'relationTypeName',index:'relationTypeName', width:130, sortable:false},
                    {name:'guardianName',index:'guardianName', width:130, sortable:false},
                    {name:'diyId',index:'diyId', width:130, sortable:false,formatter : function(j,options,rowObject){
                        return '<div class="visible-md visible-lg hidden-sm hidden-xs btn-group">' +

                        '<button type="button" class="btn btn-xs btn-danger tooltip-error" dyid="del-parent" studentId="'+rowObject.studentId+'" parentId="'+rowObject.parentId+'"  data-rel="tooltip" data-placement="top" title="移除">' +
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
				scroll:true,
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

        /**
         * 重新加载grid
         */
        reloadGrid : function(){
            $("#chooseStuParent-table").trigger("reloadGrid");
        },
        /**
         * 重新加载grid
         */
        asyncReload : function(){
         	if(_id){
         		$.get(path + "/admin/student/findStuParentById?id="+_id,function(ret){
         			var _parentList = ret;
         			for(var i = 0;i < _parentList.length;i++){
                 		var _parentObj = _parentList[i];
                 		var rowObj = {
                 				id:i+1,
                 				schId:_parentObj.schId,
                 				name:_parentObj.name,
                 				telphone:_parentObj.telphone,
                 				relationTypeName:_parentObj.relationType_showname,
                 				guardianName:_parentObj.guardian_showname,
                 				relationType:_parentObj.relationType,
                 				studentId:_parentObj.studentId,
                 				parentId:_parentObj.parentId,
                 				guardian:_parentObj.guardian
                 				};
                 		jQuery("#chooseStuParent-table").jqGrid('addRowData', i + 1, rowObj);
         			}
         		});
         	}
        }
    };
})();

var rows = [];
var choseRowData;

$(document).ready(function(){
	dyChooseStuParent.createGrid();
	dyChooseStuParent.asyncReload();
	
    /**
     * 选择家长
     */
    $("#searchButton").on(ace.click_event, function() {
    	  //获取所有行数据，获取已关联的家长ID
    	  var obj  = $("#chooseStuParent-table").jqGrid("getRowData");
    	  var parentIds="";
    	  jQuery(obj).each(function(){
    	        parentIds+=this.parentId+",";
    	    });
    	  parentIds=parentIds.substring(0,parentIds.length-1);
    	  $("#parentIds").val(parentIds);
          $.get(path + "/admin/student/show_parent.jsp",function(ret){
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
     * 移除管理的家长。
     */
    $("body").off(ace.click_event,"button[dyid='del-parent']").on(ace.click_event,"button[dyid='del-parent']",function(){	
        var _studentId = $(this).attr("studentId");
        var _parentId = $(this).attr("parentId");
        var selectedId = $("#chooseStuParent-table").jqGrid("getGridParam", "selrow");  
        if (_studentId == "undefined" || _studentId==null) { 
     	   $("#chooseStuParent-table").jqGrid('delRowData', selectedId); 
     	   dyChooseStuParent.reloadGrid();
     	   return;
        }
        
        bootbox.confirm("确认移除此家长？",function(r){
            if(r){
            	 $.get(path + "/admin/stuParent/deleStudent",{studentId : _studentId,parentId:_parentId,schId:_schId},function(delRet){
            		 if(delRet.code=="-2"){
              			bootbox.alert("该家长只关联了这一个小孩，不能删除关联关系!");
              			return false;
              		}
            		 $.gritter.add({
                        title: '关联学生移除',
                        text: delRet.code == "1" ? "删除成功" : "删除失败："+delRet.msg,
                        sticky: false,
                        time: ''
                    });
                    $("#chooseStuParent-table").jqGrid('delRowData', selectedId); 
                    dyChooseStuParent.reloadGrid();
                });
            }
        });
    });

    
});

/**
 * 设置关联的家长信息
 * @param id
 * @param parent_Id
 * @param parent_Name
 */
function setLayerStu(id,parent_Id,parent_Name){
	$("#relaStu_id").val(id);
	$("#relaStu_name").val(parent_Name);
	$("#parent_id").val(parent_Id);
}


/**
 * 关联家长
 */
$("#choose_parentBtn").on('click',function(){
	dyStudent.choseRowData = choseRowData;
	dyStudent.assocChild("parent_form");
});