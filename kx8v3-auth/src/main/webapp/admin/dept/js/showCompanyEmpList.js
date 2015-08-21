/**
 * 公司部门员工管理的JS
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
                url : path + "/admin/companyEmp/empListPage?deptId="+deptId+"&orgId="+orgId,
                datatype: "json",
                height: 450,
                colNames:['姓名','账号','性别','手机号码','职务','员工类型','所属部门','公司'],
                colModel:[
                    {name:'name',index:'name',type:'text',width:60},
                    {name:'account_id',index:'account_id',type:'text',width:40},
                    {name:'sex_showname',index:'sex_showname',width:20, sorttype:"string"},
                    {name:'mobile',index:'mobile',width:50,sortable:false},
                    {name:'job',index:'job',width:100,sortable:false},
                    {name:'empType',index:'empType',width:50,sortable:false,formatter:function(j){
                    	if(j=="3"){
                    		return "员工";
                    	}else if(j=="5"){
                    		return "代理商";
                    	}
                    }},
                    {name:'deptName',index:'deptName',width:120,sortable:false},
                    {name:'orgName',index:'orgName',width:120,sortable:false}
                ],
                viewrecords : true,
                rowNum:30,
                rowList:[20,30,40,50],
                pager : pager_selector,
                altRows: true,
				rownumbers:true,
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

    //按条件查询
//    $("#find_btn").click(function(){
//        $("#empe-table").jqGrid('setGridParam',{
//            datatype:'json',
//            mtype :'POST',
//            postData:{'name':$("#name").val(),'deptId':$("#deptId").val(),'mobile':$("#mobile").val()}, //发送数据
//            page:1
//        }).trigger("reloadGrid");
//    });


});

function query(){
	$("#empe-table").jqGrid('setGridParam',{
      datatype:'json',
      mtype :'POST',
      postData:{'name':$("#name").val(),'deptId':$("#deptId").val(),'mobile':$("#mobile").val()}, //发送数据
      page:1
  }).trigger("reloadGrid");
}
