/**
 * 两列表单的demo
 */
 
 $(function(){
 	
 	$("#btn-addMenu").click(function(){
 		$.get(path + "/demo/form/form-2col-form.jsp",{},function(ret){
			bootbox.dialog({
				title : "新增菜单",
				message : ret,
				className : "dialog-auto",
				buttons : {
					success : {
						label: "保存",
	                    className: "btn-success",
	                    callback :  function(result) {
							if(result) {
								if($("#menu-form").valid()){
									$.ajaxSetup({async : false});
									$.post(path + "/admin/menu/add",$("#menu-form").formToObject(),function(addRet){
										$.gritter.add({
											title: '菜单新增',
											text: addRet.code == "1" ? "添加成功" : "添加失败",
											sticky: false,
											time: '',
											class_name: "gritter-light"
										});
										dymenu.reloadGrid();
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
 	
 	
 	$("#btn_org").click(function(){
		commonAdmin.openOrgWin(); 		
 	});
 	
 });