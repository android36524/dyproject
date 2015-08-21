/**
 * 部门管理的JS
 */

var commondept = (function(){
 	var grid_selector = "#common_dept_grid";
    var pager_selector = "#common_dept_grid_pager";
    
    var rowId;
    var rowData;
    
    return {

        /**
         * 组织机构树初始化
         */
        init : function(){
            this.createGrid();
        },

        /**
         * 创建组织机构的TreeGrid
         */
        createGrid : function(){
            var _layer = $(".xubox_layer");
            
            var _params = {
            	"orgId" : __common_dept_orgId,
            	"deptId" : __common_dept_deptId
            };
            
            jQuery(grid_selector).jqGrid({
                url : path + "/admin/dept/getDeptTree?"+$.param(_params),
                datatype: "json",
                height: (_layer.height() || 530)-120,
                width : 400,
                colNames:['ID','部门名称'],
                colModel:[
                    {name:'id',index:'id', sortable:false},
                    {name:'deptName',index:'deptName', sortable:false}
                ],

                viewrecords : true,
                rowNum:30,
                rowList:[20,30,40,50],
                altRows: true,
                multiselect: false,
                multiboxonly: true,

                ExpandColumn : 'name',
                ExpandColClick: true,
                treeGrid: true,
                treeGridModel: 'adjacency', //treeGrid模式，跟json元数据有关
                treeReader : {
                    level_field: "level",
                    parent_id_field: "pid",
                    leaf_field: "isLeaf",
                    expanded_field: "expanded"
                },
                loadComplete : function() {
                    var table = this;
                    setTimeout(function(){
                        dyjsCommon.enableTooltips(table);
                        dyjsCommon.updatePagerIcons(table);
                        $('button[menuid]').tooltip({container : 'body'});
                    }, 0);
                },
                onSelectRow : function(rowid,status){
                	rowId = rowid;
                    rowData = $(grid_selector).jqGrid('getRowData',rowid);
                }
            });
        },

        /**
         * 重新加载grid
         */
        reloadGrid : function(){
            $("#dept-table").trigger("reloadGrid");
        },
        
        /**
         * 获取选中的数据
         */
        getSelected : function(callback){
        	var _ret = 0;
        	if(rowData){
        		if(callback && typeof(callback) == "function"){
        			callback({
        				deptId : rowId,
        				deptName : rowData.deptName
        			});
        			_ret = 1;
        		}
        	}else{
        		layer.alert("请先选择组织机构");
        	}
        	return _ret;
        }
    };
})();

$(function(){
    commondept.init();
});
