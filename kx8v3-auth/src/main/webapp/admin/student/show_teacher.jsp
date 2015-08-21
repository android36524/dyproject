<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="cn">
<head>
  <meta charset="utf-8" />
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <%@include file="/include-path.jsp"%>
  <%@include file="/common/include-bace.jsp"%>
  <link href="<%=path%>/admin/css/dy-admin-default.css" rel="stylesheet">
  <title>教师选择</title>
</head>
<body>
<table id="empe-tab"></table>
<div id="empe-pager"></div>
<input type="hidden" id="selected">
<script type="text/javascript">
/**
 * 教师列表
 */
var dyTeacher = (function(){

    return {
        init : function(){
            this.createGrid();
        },

        /**
         * 创建Grid
         */
        createGrid : function(){
            var grid_selector = "#empe-tab";
            var pager_selector = "#empe-pager";

            jQuery(grid_selector).jqGrid({
                url : path + "/admin/class/getTeacherList",
                datatype: "json",
                height: 200,
                colNames:['序号','教师姓名','性别','所属部门','手机号码'],
                colModel:[
                    {name:'id',index:'id',width:80, sortable:false},
                    {name:'name',index:'name',width:80, sortable:false},
                    {name:'sex_showname',index:'sex',width:80, sortable:false},
                    {name:'deptName',index:'deptName',width:80, sortable:false},
                    {name:'mobile',index:'mobile',width:80, sortable:false}
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
                onSelectRow : function(rowid,status){
                    var rowData = $(grid_selector).jqGrid('getRowData',rowid);
                    $("#selected").val($.toJSON(rowData));
                },
                caption: "<i class='icon-list'></i>教师列表",
                autowidth: true

            });
        },

        /**
         * 重新加载grid
         */
        reloadGrid : function(){
            $("#empe-tab").trigger("reloadGrid");
        }
    };
})();

$(function(){
	dyTeacher.init();
});


var closeLayer = function(){
    var index = parent.layer.getFrameIndex(window.name);
    parent.layer.close(index);
};
</script>
</body>
</html>