/**
 * 学阶管理的JS
 */

var dyEmpe = (function(){

    return {

        /**
         * 学阶管理模块初始化
         */
        init : function(){
            this.createGrid();
        },

        /**
         * 创建Grid
         */
        createGrid : function(){
            var grid_selector = "#empe-table";
            var pager_selector = "#empe-pager";
            var deptId = $("#deptId").val();
            var orgId = $("#orgId").val();
            jQuery(grid_selector).jqGrid({
                url : path + "/admin/schoolEmpe/empeList?deptId="+deptId+"&orgId="+orgId,
                datatype: "json",
                height: 450,
                colNames:['姓名','性别','手机号码','职务','所属部门','所属学校','创建时间','创建人'],
                colModel:[
                    {name:'name',index:'name',type:'text',width:40},
                    {name:'sex_showname',index:'sex_showname',width:120, sorttype:"string"},
                    {name:'mobile',index:'mobile',width:100,sortable:false},
                    {name:'job',index:'job',width:100,sortable:false},
                    {name:'deptName',index:'deptName',width:100,sortable:false},
                    {name:'orgName',index:'orgName',width:100,sortable:false},
                    {name:'createTime',index:'createTime',width:100,sortable:false},
                    {name:'creatorName',index:'creatorName',width:100,sortable:false}
                ],
                viewrecords : true,
                rowNum:30,
                rowList:[20,30,40,50],
                pager : pager_selector,
                altRows: true,
                multiselect: true,
                multiboxonly: true,
                loadComplete : function() {
                    var table = this;
                    setTimeout(function(){
                        dyjsCommon.enableTooltips(table);
                        dyjsCommon.updatePagerIcons(table);
                        $('button[menuid]').tooltip({container : 'body'});
                    }, 0);
                },
                caption: "<i class='icon-list'></i>部门员工列表",
                autowidth: true

            });
        },

        /**
         * 重新加载学阶数据
         */
        reloadGrid : function(){
            $("#empe-table").trigger("reloadGrid");
        }

    };
})();

$(function(){
    dyEmpe.init();

});

function query(){
	$("#empe-table").jqGrid('setGridParam',{
        datatype:'json',
        mtype :'POST',
        postData:{'name':$("#name").val(),'deptId':$("#deptId").val(),'mobile':$("#mobile").val()}, //发送数据
        page:1
    }).trigger("reloadGrid");
}