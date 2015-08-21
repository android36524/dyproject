var _pOrgId;
var _prId;
var _level;

var dyCompanyEmp = (function(){
	
	return {
		
		/**
		 * 公司管理模块初始化
		 */
		init : function(){
			this.createGrid();
		},
		
		/**
		 * 创建Grid
		 */
		createGrid : function(){
			var grid_selector = "#companyEmp-table";
			var pager_selector = "#companyEmp-pager";
		
			jQuery(grid_selector).jqGrid({
				url : path + "/admin/companyEmp/empListPage",
				datatype: "json",
				mtype :'POST',
				postData:{'orgId':_pOrgId},
				height: 450,
				colNames:['姓名','工号','账号','性别','手机号码','职务','员工类型','所属部门','公司','操作'],
                colModel:[
                    {name:'name',index:'name',type:'text',width:60},
                    {name:'empNo',index:'empNo',type:'text',width:60},
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
                    {name:'orgName',index:'orgName',width:120,sortable:false},
                    {name:'id',index:'id', width:80, sortable:false,formatter : function(e){
                    	if($("#company_name").val()==""){
                    		return "";
                    	}else{
                    		var editBtn = commonJs.createDefaultAuthBtn(commonJs.btn_modifycode,"modify-empe","修改",{empeId:e});
    						var delBtn = commonJs.createDefaultAuthBtn(commonJs.btn_delcode,"del-empe","删除",{empeId:e});
    						return '<div class="visible-md visible-lg hidden-sm hidden-xs btn-group">' +editBtn+delBtn+
    								'</div>';
                    	}
                    	
                    }}
                ],
		
				viewrecords : true,
				rowNum:20,
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
				caption: "<i class='icon-list'></i> 员工信息",
				autowidth: true
		
			});
		},
		reloadGridByParams : function(prams){
			$("#companyEmp-table").jqGrid('setGridParam',{
				url : path + "/admin/companyEmp/empListPage",
				datatype:'json',
				mtype :'POST',
				postData:prams,
				page:1
			}).trigger("reloadGrid");
		},
		/**
		 * 重新加载grid
		 */
		reloadGrid : function(){
			$("#companyEmp-table").trigger("reloadGrid");
		}
	};
})();

$(function(){
	
//	$('#div_main').layout({
//		applyDefaultStyles: true,
//		west : {
//			header : "组织机构树",
//			size : 260
//		}
//	});
//	var defResultNode = _tree.initEdbTree(_edbType,{}, function () {
//		var nodes = _edbTreeObj.getSelectedNodes();
//		var tempNode = nodes[0];
//		_pOrgId = tempNode.id;
//		_prId = tempNode.pId;
//		_level = tempNode.level;
//		//alert(_prId+"=="+_pOrgId+"=="+_level);
//		if(_tree.isEdbNode(tempNode)){
//			//alert("节点："+_pOrgId);
//			var params = {"orgId":_pOrgId};
//			dyCompanyEmp.reloadGridByParams(params);
//		}
//	});
//	_pOrgId = defResultNode.id;
	//alert("_pOrgId:"+_pOrgId);
	dyCompanyEmp.init();
	
company_deptEmp.initProvince();
    
    //省改变事件
    var provinceId;
    $("#company_provinceCode").change(function () {
    	 provinceId = $(this).val();
        company_deptEmp.initCityOrAreaByBmId(provinceId,"company_cityCode");
        //company_deptEmp.initCompanyByProvince(provinceId,"company_name");
        if(provinceId.length==0){
			  document.getElementById("addCompanyEmp").style.display='none';
		  }
        $("#companyEmp-table").trigger("reloadGrid");
    });
    
    //市改变事件
    $("#company_cityCode").change(function () {
       var cityId = $(this).val();
       company_deptEmp.initCityOrAreaByBmId(cityId,"company_areaCode");
       $("#companyEmp-table").trigger("reloadGrid");
    });
    var areaCode ;
    $("#company_areaCode").change(function () {
    	areaCode = $(this).val();
    	company_deptEmp.initCompanyByProvince(areaCode,"company_name");
    	$("#companyEmp-table").trigger("reloadGrid");
     });
    
    var company_name;
    $("#company_name").change(function(){
    	selectCompanyEmp();
        company_name = $(this).val();
	    if(company_name!=null||company_name!=""){
	    	document.getElementById("addCompanyEmp").style.display='block';
	    	$("#companyEmp-table").trigger("reloadGrid");
	    }
	    if(company_name.length==0){
	    	document.getElementById("addCompanyEmp").style.display='none';
	    }
    });
	
	
	//新增公司员工信息
	$("#btn-addCompanyEmp").on(ace.click_event, function() {
		flageId = "0";//0表示新增公司员工
		//alert(flageId);
		var selectOrgId = company_name;
		var name = $('#company_name option:selected').text();
		$.post(path + "/admin/companyEmp/toAdd?flageId="+flageId,{selectOrgId:selectOrgId,name:name},function(ret){
			bootbox.dialog({
				title : "新增员工信息",
				width: 2000,
				message : ret,
				buttons : {
					success : {
						label: "保存",
                        className: "btn-success",
                        callback :  function(result) {
							if(result) {
								if($("#companyEmp-form").valid()){
									$.ajaxSetup({async : false});
									$.post(path + "/admin/companyEmp/add?flageId="+flageId,$("#companyEmp-form").formToObject(),function(addRet){
										$.gritter.add({
											title: '新增员工',
											text: addRet.code == "1" ? "添加成功" : "添加失败",
											sticky: false,
											time: '',
											class_name: "gritter-light"
										});
									});
									$.ajaxSetup({async : true});
									dyCompanyEmp.reloadGrid();
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
	
	//修改公司员工信息
	$("body").on(ace.click_event,"button[dyid='modify-empe']", function() {
		var empeId = $(this).attr("empeId");
		flageId = "1";
		$.get(path + "/admin/companyEmp/toAdd?id="+empeId+"&flageId="+flageId,function(ret){
			bootbox.dialog({
				title : "更新员工基本信息",
				message : ret,
				buttons : {
					success : {
						label: "更新",
                        className: "btn-success",
                        callback :  function(result) {
							if(result) {
								if($("#companyEmp-form").valid()){
									$.ajaxSetup({async : false});
									$.post(path + "/admin/companyEmp/add?flageId="+flageId,$("#companyEmp-form").formToObject(),function(addRet){
										$.gritter.add({
											title: '员工更新',
											text: addRet.code == "1" ? "更新成功" : "更新失败",
											sticky: false,
											time: ''
										});
										dyCompanyEmp.reloadGrid();
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
	
	//删除公司员工
	$("body").on(ace.click_event,"button[dyid='del-empe']",function(){
		var empeId = $(this).attr("empeId");
		
		bootbox.confirm("确认删除该员工吗？",function(r){
			if(r){
				$.get(path + "/admin/companyEmp/del",{id : empeId},function(delRet){
					$.gritter.add({
						title: '员工删除',
						text: delRet.code == "1" ? "删除成功" : delRet.msg,
						sticky: false,
						time: ''
					});
					dyCompanyEmp.reloadGrid();
				});
			}
		});
	});
	
	//根据条件模糊查询
	$("#find_btn").click(function(){
		selectCompanyEmp();
	});
});


var company_deptEmp = (function () {
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
            	  company_deptEmp.removeSlect("company_cityCode");
                  company_deptEmp.removeSlect("company_areaCode");
                  company_deptEmp.removeSlect("company_name");
                  document.getElementById("addCompanyEmp").style.display='none';
              }else{
            	  company_deptEmp.removeSlect(id);
            	  company_deptEmp.removeSlect("company_name");
            	  document.getElementById("addCompanyEmp").style.display='none';
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
        		  company_deptEmp.removeSlect("company_name");
        		  document.getElementById("addCompanyEmp").style.display='none';
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

//根据公司名称查询出公司的员工
function selectCompanyEmp(){
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
		var nameOrPy = $("#nameOrPy").val();
		var accountId = $("#accountId").val();
		var phoneNo = $("#phoneNo").val();
		var empType = $("#empType").val();
			$("#companyEmp-table").jqGrid('setGridParam',{  
	        datatype:'json',  
	        mtype :'POST',
	        url : path + "/admin/companyEmp/queryByConditions",
	        postData:{'nameOrPy':nameOrPy,"accountId":accountId,"phoneNo":phoneNo,"empType":empType,"company_id":company_id}, //发送数据  
	        page:1  
	    }).trigger("reloadGrid"); 
	}
	
}
