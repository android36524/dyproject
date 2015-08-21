function createGrid(){
    var grid_selector = "#grid-table-1";
    var pager_selector = "#grid-pager-1";

    jQuery(grid_selector).jqGrid({
        url : path + "/admin/edb/queryParentOrg?flag=" + flag +"&code=" + code+"&itSelf="+itSelf+"&idid="+idid,
        datatype: "json",
        height: 210,
        colNames:['','教育局名称','教育局编码','状态'],
        colModel:[
            {name:'id',index:'id', width:20, sorttype:"int",hidden:true},
            {name:'orgName',index:'orgName', width:60, sorttype:"string"},
            {name:'orgCode',index:'orgCode',width:60, sorttype:"string"},
            {name:'status',index:'status',width:40,sorttype:"string", formatter : function(e){
                if(e == 1){
                    return "启用";
                }else if(e == 9){
                    return "禁用";
                }
            }},
        ],
        viewrecords : true,
        rowNum:20,
        rowList:[20,30,40,50],
        pager : pager_selector,
        altRows: true,
        multiselect: false,
        loadComplete : function() {
            var table = this;
            setTimeout(function(){
                dyjsCommon.enableTooltips(table);
                dyjsCommon.updatePagerIcons(table);
                $('button[menuid]').tooltip({container : 'body'});
            }, 0);
        },
        onSelectRow : function(rowid){
        	var rowData = $(grid_selector).jqGrid('getRowData',rowid);
        	$("#selected").val($.toJSON(rowData));
        },
        ondblClickRow : function(rowid){
            var rowData = $(grid_selector).jqGrid('getRowData',rowid);
            parent.edb_modify.setLayerTeacherGroup(rowData.id,rowData.orgName);
            closeLayer();
        },
        caption: "<i class='icon-list'></i> 上级列表",
        autowidth: true

    });
    
}
createGrid();

/**
 * 关闭当前弹窗
 */
var closeLayer = function(){
    var index = parent.layer.getFrameIndex(window.name);
    parent.layer.close(index);
};