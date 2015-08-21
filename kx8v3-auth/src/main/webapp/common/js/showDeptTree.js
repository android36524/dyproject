/**
 * 部门管理的JS
 */

var dydept = (function(){

    return {

        /**
         * 部门管理模块初始化
         */
        init : function(){
            this.createGrid();
        },

        /**
         * 创建Grid
         */
        createGrid : function(){
            var grid_selector = "#empe-tab";
            var pager_selector = "#empe-pager";
            var orgId = $("#orgId").val();
            var deptId = $("#deptId").val();
            jQuery(grid_selector).jqGrid({
                url : path + "/admin/dept/getDeptTree?orgId="+orgId+"&deptId="+deptId,
                datatype: "json",
                height: 200,
                colNames:['ID','部门名称'],
                colModel:[
                    {name:'id',index:'id',width:80, sortable:false},
                    {name:'deptName',index:'deptName',width:80, sortable:false}
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
                    var rowData = $(grid_selector).jqGrid('getRowData',rowid);
                    $("#selected").val($.toJSON(rowData));
                },
                ondblClickRow : function(rowid){
                    var rowData = $(grid_selector).jqGrid('getRowData',rowid);
                    parent.setLayerDeptGroup(rowid,rowData.deptName);
                    closeLayer();
                },
                caption: "<i class='icon-list'></i> ",

                autowidth: true

            });
        },

        /**
         * 重新加载grid
         */
        reloadGrid : function(){
            $("#dept-table").trigger("reloadGrid");
        }
    };
})();

$(function(){
    dydept.init();
});
/**
 * 关闭当前弹窗
 */
var closeLayer = function(){
    var index = parent.layer.getFrameIndex(window.name);
    parent.layer.close(index);
};
//
//$("#btn_confirm").click(function(){
//    closeLayer();
//});
//
//$("#btn_cancel").click(function(){
//    closeLayer();
//});