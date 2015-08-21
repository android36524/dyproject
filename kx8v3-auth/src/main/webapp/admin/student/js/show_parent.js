/**
 * 选择家长列表JS
 */

var dyParent = (function(){

    return {

        /**
         * 初始化表格
         */
        init : function(){
            this.createGrid();
        },

        /**
         * 创建Grid
         */
        createGrid : function(){
            var grid_selector = "#parent-tab";
            var pager_selector = "#parent-pager";
            var parentIds=parent.$("#parentIds").val();
            var PostData={schId:parent._schId,isManageParent:"true",studentId:parent._id,parentIds:parentIds};
            jQuery(grid_selector).jqGrid({
                url : path + "/admin/stuParent/findParentByPage",
                datatype: "json",
                postData:PostData,
                height: 200,
                colNames:['家长ID','家长姓名','性别','手机号码','职务'],
                colModel:[
                    {name:'ID',index:'ID',width:200, hidden:true},
                    {name:'name',index:'name',width:200, sortable:false},
                    {name:'sex_showname',index:'sex',width:200, sortable:false},
                    {name:'telphone',index:'telphone',width:200,sortable:false},
                    {name:'duties',index:'duties',width:200,sortable:false}
                    
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
				caption: "<i class='icon-list'></i>家长列表",
				autowidth: true
            });
        },

        /**
         * 重新加载grid
         */
        reloadGrid : function(){
            $("#parent-tab").trigger("reloadGrid");
        }
        
    };
})();

$(function(){
	dyParent.init();
	//选择带回
	$("#choose_saveBtn").click(function(){
		var grid_selector = "#parent-tab";
		var id = jQuery(grid_selector).jqGrid('getGridParam','selrow');
		var rowData = $(grid_selector).jqGrid('getRowData',id);
		parent.choseRowData = "";
		parent.choseRowData = rowData;
		parent.setLayerStu(id,rowData.ID,rowData.name);
		closebootbox();
	});
	
	 //按条件查询
    $("#parfind_btn").click(function(){
    	var _name = $("#par_name").val();
    	var _tel=$("#par_phone").val();
    	var params = {name:_name,tel:_tel, schId:parent._schId};
        $("#parent-tab").jqGrid('setGridParam',{
            datatype:'json',
            mtype :'POST',
            postData:params, //发送数据
            page:1
        }).trigger("reloadGrid");
    });
	
	
});

/**
 * 关闭当前弹窗
 */
var closebootbox = function(){
	commonJs.closedialog("parent-tab");
};
