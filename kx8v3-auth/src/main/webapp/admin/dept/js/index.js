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
            var grid_selector = "#dept-table";
            var pager_selector = "#dept-pager";
            var colNamesAll;
            var colModelAll=[];
            var urlPath;
            
            if(_title=="公司"){
            	urlPath="/admin/dept/deptList?&orgFlag="+_orgFlag,
            	colNamesAll=['部门名称','组织类型','上级部门名称',  '负责人','负责人电话','办公电话','操作'],
            	colModelAll=[
                          {name:'deptName',index:'deptName',width:60, sortable:false},
                          {name:'',index:'', width:40, sortable:false,formatter : function(j){
                          	return '部门';
                          }},
                          {name:'parentName',index:'parentName', width:40, sortable:false},
                          {name:'charge',index:'charge', width:40, sortable:false},
                          {name:'chargePhone',index:'chargePhone', width:40, sortable:false},
                          {name:'telphone',index:'telphone', width:40, sortable:false},
                          {name:'id',index:'myop', width:100, sortable:false,formatter : function(j){
                          	if($("#company_name").val()==""){
                          		return "";
                          	}else{
                          		var editBtn = commonJs.createDefaultAuthBtn(commonJs.btn_modifycode,"modify-dept","修改",{deptId:j});
          	            		var detail =  '<button class="btn btn-xs btn-success tooltip-info" dyid="show_list" deptId="'+j+'" onclick="showCompanyEmpeList('+j+');"  data-rel="tooltip" data-placement="top" title="部门员工列表">' +
          	                     '部门员工列表</button>';
          	            		
          						var delBtn = commonJs.createDefaultAuthBtn(commonJs.btn_delcode,"del-dept","删除",{deptId:j});
          						return '<div class="visible-md visible-lg hidden-sm hidden-xs btn-group">' +editBtn+detail+delBtn+
          								'</div>';
                          	}	
                          }}
                      ]
            }else{
            	urlPath="/admin/dept/deptList?orgId="+_edbId+"&orgFlag="+_orgFlag,
            	colNamesAll=['部门名称',_title+'名称','上级部门名称',  '负责人','负责人电话','创建人','创建时间','操作'],
            	colModelAll=[
                          {name:'deptName',index:'deptName',width:60, sortable:false},
                          {name:'orgName',index:'orgName', width:40, sortable:false},
                          {name:'parentName',index:'parentName', width:40, sortable:false},
                          {name:'charge',index:'charge', width:40, sortable:false},
                          {name:'chargePhone',index:'chargePhone', width:40, sortable:false},
                          {name:'creatorName',index:'creatorName', width:40, sortable:false},
                          {name:'createTime',index:'createTime', width:60, sortable:false},
                          {name:'id',index:'myop', width:100, sortable:false,formatter : function(j){
                          		
                          		var editBtn = commonJs.createDefaultAuthBtn(commonJs.btn_modifycode,"modify-dept","修改",{deptId:j});
                          		var delBtn = commonJs.createDefaultAuthBtn(commonJs.btn_delcode,"del-dept","删除",{deptId:j});
                          		var detail =  '<button class="btn btn-xs btn-success tooltip-info" dyid="show_list" deptId="'+j+'" onclick="showEmpeList('+j+','+_edbId+');"  data-rel="tooltip" data-placement="top" title="部门员工列表">' +
                                   '部门员工列表</button>';
                          		var showInfo = '<button class="btn btn-xs btn-success tooltip-info" dyid="showDeptInfo" deptId="'+j+'"  deptName=" " data-rel="tooltip" data-placement="top" title="详情">' +'详情'+
                                '</button>' ;
      							
      							return '<div class="visible-md visible-lg hidden-sm hidden-xs btn-group">' +editBtn+delBtn+showInfo+detail+
      									'</div>';
                          }}
                      ]
            }
            
            
            jQuery(grid_selector).jqGrid({
                url : path + urlPath,
                datatype: "json",
                height: 450,
                colNames:colNamesAll,
                colModel:colModelAll,
                viewrecords : true,
                rowNum:30,
                rowList:[20,30,40,50],
                altRows: true,
                multiselect: false,
                multiboxonly: true,
                pager : pager_selector,
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
                beforeRequest : function(){
                    var postData = $(this).jqGrid('getGridParam',"postData");
                    if(postData.nodeid>0){
                        postData.deptName="";
                        $(this).jqGrid('setGridParam',{
                            datatype:'json',
                            postData:postData, //发送数据
                            page:1
                        });
                    }
                },
                loadComplete : function() {
                    var table = this;
                    setTimeout(function(){
                        dyjsCommon.enableTooltips(table);
                        dyjsCommon.updatePagerIcons(table);
                        $('button[deptid]').tooltip({container : 'body'});
                    }, 0);
                },
                caption: "<i class='icon-list'></i>"+_title+"部门列表",

                autowidth: true

            });
        },
        
        /**
         * 重新加载grid
         */
        reloadGrid : function(){
            $("#dept-table").trigger("reloadGrid");
        },
        /**
         * 查询条件参数
         * @returns
         */
        getParamObj : function(){
            var paramObj = {};
            paramObj.name =	$("#deptName").val();
            return paramObj;
        }
    };
})();

/**
 * 员工列表展现
 */
function showEmpeList(j,orgId){
    var deptId = j;
    var orgId = orgId;
if(_title=="教育局"){
    $.layer({
        type : 1,
        shadeClose: true,
        title: "员工信息列表",
        closeBtn: [0, true],
        shade: [0.8, '#000'],
        border: [0],
        offset: ['20px',''],
        area: ['1000px', '730px'],
        //iframe: {src: path + "/admin/dept/showEmpeList.jsp?id="+deptId+"&orgId="+orgId},
        page : {
        	  url : path + "/admin/dept/showEmpeList.jsp?id="+deptId+"&orgId="+orgId
          },
        yes : function(index){
        }
    });
}
    else if(_title=="学校"){
    $.layer({
        type : 1,
        shadeClose: true,
        title: "员工信息列表",
        closeBtn: [0, true],
        shade: [0.8, '#000'],
        border: [0],
        offset: ['20px',''],
        area: ['1000px', '730px'],
        //iframe: {src: path + "/admin/dept/showSchoolEmpeList.jsp?id="+deptId+"&orgId="+orgId},
        page : {
      	  url : path + "/admin/dept/showSchoolEmpeList.jsp?id="+deptId+"&orgId="+orgId
        },
        yes : function(index){
        }
    });
}}

function showCompanyEmpeList(j){
	var deptId = j;
	$.layer({
        type : 1,
        shadeClose: true,
        title: "员工信息列表",
        closeBtn: [0, true],
        shade: [0.8, '#000'],
        border: [0],
        offset: ['20px',''],
        area: ['1000px', '730px'],
        //iframe: {src: path + "/admin/dept/showCompanyEmpList.jsp?id="+deptId+"&orgId="+orgId},
        page : {
      	  url : path + "/admin/dept/showCompanyEmpList.jsp?id="+deptId
        },
        yes : function(index){
        }
    });
}

function commFunc(){
	 $("#form-search")[0].reset();
     var nodes = _edbTreeObj.getSelectedNodes();
     
     if(_title == "教育局"){
 		if(!_tree.isEdbNode(nodes[0])){
 	         return;
 	     }
 	}else if(_title=="学校"){
 		if(!_tree.isSchoolNode(nodes[0])){
 	         return;
 	     }
 	}else if(_title=="公司"){
 		
 	}
     
     _edbId = nodes[0].id;
     $("#dept-table").jqGrid('setGridParam',{
         url : path + "/admin/dept/deptList?orgId="+_edbId+"&orgFlag="+_orgFlag,
         datatype:'json',
         mtype :'POST',
         postData:dydept.getParamObj,
         page:1
     }).trigger("reloadGrid");
}

$(function(){
    //dydept.init();


if(_title == "教育局"){
    var obj = _tree.initEdbTree("0",{}, function () {
    	commFunc();
    });
}
  else if(_title=="学校"){
    var obj = _tree.initEdbTree("1",{}, function () {
    	commFunc();
    });
}

if(_title == "教育局"||_title == "学校"){
	_edbId=obj.id;
}
    dydept.init();
    
    company_dept.initProvince();
    
    //省改变事件
    var provinceId;
    $("#company_provinceCode").change(function () {
    	 provinceId = $(this).val();
        company_dept.initCityOrAreaByBmId(provinceId,"company_cityCode");
        //company_dept.initCompanyByProvince(provinceId,"company_name");
        if(provinceId.length==0){
			  document.getElementById("cmonDiv").style.display='none';
		  }
        $("#dept-table").trigger("reloadGrid");
    });
    
    //市改变事件
    $("#company_cityCode").change(function () {
       var cityId = $(this).val();
       company_dept.initCityOrAreaByBmId(cityId,"company_areaCode");
       $("#dept-table").trigger("reloadGrid");
    });
    var areaCode ;
    $("#company_areaCode").change(function () {
    	areaCode = $(this).val();
    	company_dept.initCompanyByProvince(areaCode,"company_name");
    	$("#dept-table").trigger("reloadGrid");
     });
    
    var company_name;
    $("#company_name").change(function(){
    	selectCompany();
        company_name = $(this).val();
	    if(company_name!=null||company_name!=""){
	    	document.getElementById("cmonDiv").style.display='block';
	    	$("#dept-table").trigger("reloadGrid");
	    }
	    if(company_name.length==0){
	    	document.getElementById("cmonDiv").style.display='none';
	    }
    });
  


    /**
     * 新增部门
     */
    $("#btn-addDept").on(ace.click_event,function(){
    	var selectOrgId;
    	var name;
    	if(_title == "教育局"||_title == "学校"){
    		selectOrgId=_edbId;
		}else if(_title == "公司"){
			selectOrgId=company_name;
			name = $('#company_name option:selected').text();
		}
        var orgId = _edbId;
        $.post(path + "/admin/dept/toAdd?orgId="+selectOrgId,{title:_title,name:name},function(ret){
            bootbox.dialog({
                title : "新增"+_title+"部门",
                message : ret,
                buttons : {
                    success : {
                        label: "保存",
                        className: "btn-success",
                        callback :  function(result) {
                            if(result) {
                                if($("#dept_form").valid()){
                                    $.ajaxSetup({async : false});
                                    $.post(path + "/admin/dept/add",$("#dept_form").formToObject(),function(addRet){
                                        $.gritter.add({
                                            title: _title+"部门新增",
                                            text: addRet.code == "1" ? "添加成功" : addRet.msg,
                                            sticky: false,
                                            time: '',
                                            class_name: "gritter-light"
                                        });
                                    });
                                    $.ajaxSetup({async : true});
                                    dydept.reloadGrid();
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



    //更新部门
    $("body").on(ace.click_event,"button[dyid='modify-dept']", function() {
        var deptId = $(this).attr("deptId");
        
		
		var id=$("#dept-table").jqGrid("getGridParam","selrow");
		var selectId;
		if(_title == "教育局"||_title == "学校"){
			selectId=_edbId;
		}else if(_title == "公司"){
			selectId=id;
		}
		
        $.post(path + "/admin/dept/toAdd?id="+deptId,{orgId:selectId,title:_title},function(ret){
            bootbox.dialog({
                title : "更新"+_title+"部门",
                message : ret,
                buttons : {
                    success : {
                        label: "更新",
                        className: "btn-success",
                        callback :  function(result) {
                            if(result) {
                                if($("#dept_form").valid()){
                                    $.ajaxSetup({async : false});
                                    $.post(path + "/admin/dept/add",$("#dept_form").formToObject(),function(addRet){
                                        bootbox.alert(addRet.code == "1" ? "更新成功" : addRet.msg,function(r){
                                            dydept.reloadGrid();
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


//显示部门详情
    $("body").on(ace.click_event,"button[dyid='showDeptInfo']", function() {
        var deptId = $(this).attr("deptId");
        $.post(path + "/admin/dept/showdeptInfo?id="+deptId,{orgId:_edbId,title:_title},function(ret){
            bootbox.dialog({
                title : "部门详情",
                message : ret
            });
        });
    });




    /**
     * 删除部门
     */
    $("body").on(ace.click_event,"button[dyid='del-dept']",function(){
        var deptid = $(this).attr("deptId");
        var deptName = $(this).attr("deptName");

        bootbox.confirm("确认删除该部门吗？",function(r){
            if(r){
                $.get(path + "/admin/dept/del",{id : deptid},function(delRet){
                    $.gritter.add({
                        title: _title+'部门删除',
                        text: delRet.code == "1" ? "删除成功" : "删除失败："+delRet.msg,
                        sticky: false,
                        time: ''
                    });
                    dydept.reloadGrid();
                });
            }
        });
    });


//按条件查询菜单
    $("#find_btn").click(function(){
        $("#dept-table").jqGrid('setGridParam',{
            datatype:'json',
            mtype :'POST',
            postData:{'deptName':$.trim($("#deptName").val())}, //发送数据
            page:1
        }).trigger("reloadGrid");
    });
    
  //按条件查询公司部门
    $("#find_btn_company").click(function(){
    	selectCompany();
    });    

});

//公司一选择就出发查询部门
function selectCompany(){
	var company_id = $("#company_name").val();
	
	if(company_id==""){
		bootbox.alert({ 
            buttons: {  
               ok: {  
                    label: '确认',  
                    className: 'btn-success'  
                }  
            },  
            message: "<font color='red' size='2px'><b>公司名称必选！！</b></font>",  
            callback: function() {  
                
            },  
            title: "提示信息", 
        });
		return;
	}else{
		$("#dept-table").jqGrid('setGridParam',{
	    	url : path + "/admin/dept/deptList",
	        datatype:'json',
	        mtype :'POST',
	        postData:{'deptName':$.trim($("#deptName").val()),"orgId":company_id,"orgFlag":2}, //发送数据
	        page:1
	    }).trigger("reloadGrid");
	}	
}


var company_dept = (function () {
    return{
          //初始化省
          initProvince:function(){
              $.post(path+'/admin/division/provinceList',function(retList){
                  if(retList){
                      for (var i = 0; i < retList.length; i++) {
                          var divisionObj = retList[i];
                          $("#company_provinceCode").append("<option value='" + divisionObj.BM + "'>" + divisionObj.MC + "</option>");
                      }
                  }
              });
          },
          //根据省初始化市
          initCityOrAreaByBmId: function (bmId,id) {
              if(id == "company_cityCode"){
            	  company_dept.removeSlect("company_cityCode");
                  company_dept.removeSlect("company_areaCode");
                  company_dept.removeSlect("company_name");
                  document.getElementById("cmonDiv").style.display='none';
              }else{
            	  company_dept.removeSlect(id);
            	  company_dept.removeSlect("company_name");
            	  document.getElementById("cmonDiv").style.display='none';
              }
			  
              $.post(path+'/admin/division/findCityOrAreaByBmId',{bmId:bmId},function(retList){
                  if(retList){
                      var selectObj = $("#" + id);
                      for (var i = 0; i < retList.length; i++) {
                          var divisionObj = retList[i];
                          selectObj.append("<option value='" + divisionObj.BM + "'>" + divisionObj.MC + "</option>");
                      }
                  }
              }); 
          },
          initCompanyByProvince:function(areaCode,id){
        	  //根据省市区来查公司
        	  if(id=="company_name"){
        		  company_dept.removeSlect("company_name");
        		  document.getElementById("cmonDiv").style.display='none';
        	  }
        	  var provinceCode = $("#company_provinceCode").val();
        	  var cityCode = $("#company_cityCode").val();
        	  $.post(path+'/admin/companyDetail/findCompanyByProvinceOr',{bmId:provinceCode,cityCode:cityCode,areaCode:areaCode},function(retList){
                  if(retList){
                      var selectObj = $("#" + id);
                      for (var i = 0; i < retList.length; i++) {
                          var companyObj = retList[i];
                          selectObj.append("<option value='" + companyObj.id + "'>" + companyObj.orgName + "</option>");
                      }
                  }
                  if(retList.length==0){
                	  document.getElementById("cmonDiv").style.display='none';
                  }
              });
          },
        //删除select item
        removeSlect : function(selectId){
            var selectObject = $("#" + selectId + " option");
            selectObject.each(function(){
                if( $(this).val() !== ""){
                    $(this).remove();
                }
            });
        },
        setLayerTeacherGroup : function (idData,nameData){
            $("#company_orgId").val(idData);
            $("#parentOrgName").val(nameData);
        }
    };
})();