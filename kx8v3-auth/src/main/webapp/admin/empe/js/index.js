/**
 * 学阶管理的JS
 */

var flag_dept = 0; //0 标识是查询界面  1 新增查询界面
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
            jQuery(grid_selector).jqGrid({
                url : path + "/admin/empe/empeList?orgId="+_edbId,
                datatype: "json",
                height: 400,
                colNames:['姓名','工号','性别','手机号码','职务','所属部门','所属教育局','创建时间','创建人','操作'],
                colModel:[
                    {name:'name',index:'name',width:120, sortable:false,formatter : function(j,options,rowObject){
                    	return '<a href="javascript:showDetail('+rowObject.id+');">'+rowObject.name+'</a>';
                    }},
                    {name:'empNo',index:'empNo',width:60, sorttype:"string"},
                    {name:'sex_showname',index:'sex_showname',width:40, sorttype:"string"},
                    {name:'mobile',index:'mobile',width:80,sortable:false},
                    {name:'job',index:'job',width:100,sortable:false},
                    {name:'deptName',index:'deptName',width:100,sortable:false},
                    {name:'orgName',index:'orgName',width:100,sortable:false},
                    {name:'createTime',index:'createTime',width:100,sortable:false},
                    {name:'creatorName',index:'creatorName',width:100,sortable:false},
                    {name:'id',index:'id', width:80, sortable:false,formatter : function(e){
                    	
                    	var editBtn = commonJs.createDefaultAuthBtn(commonJs.btn_modifycode,"modify-empe","修改",{empeId:e});
                    	var delBtn = commonJs.createDefaultAuthBtn(commonJs.btn_delcode,"del-empe","删除",{empeId:e});
                    	return '<div class="visible-md visible-lg hidden-sm hidden-xs btn-group">' +editBtn+delBtn+
						'</div>';
//                        return '<div class="visible-md visible-lg hidden-sm hidden-xs btn-group">' +
//                            '<button class="btn btn-xs btn-info tooltip-info" dyid="modify-empe" empeId="'+e+'" data-rel="tooltip" data-placement="top" title="修改">' +
//                            '<i class="icon-edit bigger-120"></i></button>' +
//
//                            '</div>';
                    }}
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
                caption: "<i class='icon-list'></i>教育局员工列表",
                autowidth: true

            });
        },

        /**
         * 重新加载学阶数据
         */
        reloadGrid : function(){
            $("#empe-table").trigger("reloadGrid");
        },
        /**
         * 查询条件参数
         * @returns
         */
        getParamObj : function(){
            var paramObj = {};
            paramObj.name =	$("#name").val();
            paramObj.deptId = $("#deptId").val();
            paramObj.mobile = $("#mobile").val();
            return paramObj;
        }
    };
})();

$(function(){


    var obj = _tree.initEdbTree("0",{}, function () {

        $("#form-search")[0].reset();
        var nodes = _edbTreeObj.getSelectedNodes();        
        if(!_tree.isEdbNode(nodes[0])){
            return;
        }
        _edbId = nodes[0].id;
        $("#empe-table").jqGrid('setGridParam',{
            url : path + "/admin/empe/empeList?orgId="+_edbId,
            datatype:'json',
            mtype :'POST',
            postData:dyEmpe.getParamObj(), //发送数据
            page:1
        }).trigger("reloadGrid");
    });
    _edbId=obj.id;
    dyEmpe.init();

    $("#btn-addEmpe").on(ace.click_event,function(){
        var orgId = _edbId;
        $.post(path + "/admin/empe/toAdd?orgId="+orgId,function(ret){
            bootbox.dialog({
                title : "新增员工",
                message : ret,
                buttons : {
                    success : {
                        label: "保存",
                        className: "btn-success",
                        callback :  function(result) {
                            if(result) {
                                var isValidate = $("#empe_form").valid();
                                if(isValidate){
                                    $.ajaxSetup({async : false});
                                    $.post(path + "/admin/empe/add",$("#empe_form").formToObject(),function(addRet){
                                        $.gritter.add({
                                            title: '员工新增',
                                            text: addRet.code == "1" ? "添加成功" : addRet.msg,
                                            sticky: false,
                                            time: '',
                                            class_name: "gritter-light"
                                        });
                                    });
                                    $.ajaxSetup({async : true});
                                    dyEmpe.reloadGrid();
                                }else{
                                    return false;
                               }
                            }
                        }
                    },
                    cancel : {
                        label : "取消",
                        "class" : "btn",
                        callback :  function(result) {
                            if(result) {
                            	
                            }
                        }
                    }
                }
            });
        });
    });


    //更新员工
    $("body").on(ace.click_event,"button[dyid='modify-empe']", function() {
        var empeId = $(this).attr("empeId");
        var orgId = _edbId;
        $.get(path + "/admin/empe/toAdd?id="+empeId+"&orgId="+orgId,function(ret){
            bootbox.dialog({
                title : "更新员工",
                message : ret,
                buttons : {
                    success : {
                        label: "更新",
                        className: "btn-success",
                        callback :  function(result) {
                            if(result) {
                                if($("#empe_form").valid()){
                                    $.ajaxSetup({async : false});
                                    $.post(path + "/admin/empe/add",$("#empe_form").formToObject(),function(addRet){
                                        bootbox.alert(addRet.code == "1" ? "更新成功" : addRet.msg,function(r){
                                            dyEmpe.reloadGrid();
                                        });
                                    });
                                    $.ajaxSetup({async : true});
                                }else{
                                    return false;
                                }
                            }
                        }
                    },
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


//员工详情

    $("body").on(ace.click_event,"button[dyid='showEmpeInfo']", function() {
        var empeId = $(this).attr("empeId");
        $.get(path + "/admin/empe/empeInfo?id="+empeId,function(ret){
            bootbox.dialog({
                title : "员工详情",
                message : ret

            });
        });
    });
    
    
  //单个删除员工
	$("body").on(ace.click_event,"button[dyid='del-empe']", function() {
		var empeId = $(this).attr("empeId");	
		bootbox.confirm("您将删除教育局员工信息，删除后信息将无法恢复，请确认此次操作?",function(r){
			if(r){
				$.get(path + "/admin/empe/del",{num : empeId},function(delRet){
					$.gritter.add({
						title: '教育局员工删除',
						text: delRet.code == "1" ? "删除信息成功！" : delRet.msg,
						sticky: false,
						time: ''
					});
					dyEmpe.reloadGrid();
				});
			}
		});
	});
    
    
    
    
//批量删除员工
    $("#btn-removeEmpe").click(function(){
        var ids=$('#empe-table').jqGrid('getGridParam','selarrrow');
        if(ids.length<1){
            return ;
        }
        var str = ids[0];
        for(var i=1;i<ids.length;i++){
            str = str+","+ids[i];
        }

        bootbox.confirm("确认删除吗？",function(r){
            if(r){
                $.get(path + "/admin/empe/del",{num:str},function(delRet){
                    $.gritter.add({
                        title: '教育部门员工删除',
                        text: delRet.code == "1" ? "删除成功" : "删除失败："+delRet.msg,
                        sticky: false,
                        time: ''
                    });
                    dyEmpe.reloadGrid();
                });
            }
        });

    });

    //按条件查询
    $("#find_btn").click(function(){
        $("#empe-table").jqGrid('setGridParam',{
            datatype:'json',
            mtype :'POST',
            postData:dyEmpe.getParamObj(), //发送数据
            page:1
        }).trigger("reloadGrid");
    });


    //选择所属部门
    $("#btn_select_dept").on(ace.click_event, function() {
    	var _params = {
    		"orgId" : _edbId
    	};
    	
    	commonJs.showDeptDialog(_params,function(dept){
    		$("#deptId").val(dept.deptId);
			$("#checkName").val(dept.deptName);
    	});
    });
    
    
    $("#reset_btn").click(function(){
		$("#form-search")[0].reset();
	});
	
    //清空所选择的部门
    $("#btn_clean_dept").on(ace.click_event, function() {
        $("#deptId").val('');
        $("#checkName").val('');
    });

    //上传Excel
    $("#btn_importExcel").on(ace.click_event, function() {
        var orgId = _edbId;
        $.get(path + "/admin/empe/excelimport.jsp",function(ret){
            bootbox.dialog({
                title : "教育局员工信息导入",
                message : ret,
                buttons : {
                    success : {
                        label: "保存",
                        className: "btn-success",
                        callback :  function(result) {
                            if(result) {
                                if($("#excel_form").valid()){
                                    var fileName = $("#excelFileName").val();
                                    $.ajaxSetup({async : false});
                                    $.post(path + "/admin/empe/importEdbInfo",{orgId:orgId,fileUrl:fileName},function(addRet){
                                        var promptMsg = "成功导入：" + addRet.code + " 条数据，导入失败：" + addRet.msg + " 条数据";
                                        $.gritter.add({
                                            title: '教育局员工信息导入',
                                            text:  promptMsg,
                                            sticky: false,
                                            time: '',
                                            class_name: "gritter-light"
                                        });
                                        dyEmpe.reloadGrid();
                                    });
                                    $.ajaxSetup({async : true});
                                }else{
                                    return false;
                                }
                            }
                        }
                    },
                    cancel : {
                        label : "取消",
                        "class" : "btn",
                        callback :  function(result) {
                            if(result) {
                            	
                            }
                        }
                    }
                }
            });
        });
    });

    $("#btn_downTempExcel").bind('click',function(){
        location.href = path +"/admin/empe/downExcel";
    });
    $("#btn_downLoadExcel").bind('click',function(){
        var name = $("#name").val();
        var deptId = $("#deptId").val();
        var mobile = $("#mobile").val();
        var orgId = _edbId;
        location.href = path +"/admin/empe/downLoadExcel?name="+name+"&deptId="+deptId+"&mobile="+mobile+"&orgId="+orgId;
    });
    
});
/**
 * 查看详情
 */
function showDetail(id){
	 var empeId = id;
     $.get(path + "/admin/empe/empeInfo?id="+empeId,function(ret){
         bootbox.dialog({
             title : "查看详情",
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
}   

