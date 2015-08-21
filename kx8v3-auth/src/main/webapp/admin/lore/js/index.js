/**
 * 知识点的JS
 */
 
var dyLore = (function(){
	
	return {
		
		/**
		 * 学阶下拉初始化
		 * @returns
		 */
		stageInit : function(){
			$.post(path+"/admin/stage/findStageAll",function(ret){
				if(ret){
					for(var i=0;i<ret.length;i++){
						var obj = ret[i];
						$("#stageId").append("<option value="+obj.id+">"+obj.name+"</option>");
					}
				}
			});
		},
		
		gradeInit : function(){
			$.post(path+"/admin/grade/findGradeAll",function(ret){
				if(ret){
					for(var i = 0;i<ret.length;i++){
						var gradeObj = ret[i];
						$("#gradeId").append("<option value="+gradeObj.id+">"+gradeObj.name+"</option>");
					}
				}
			});
		},
		
		//删除select item
		removeSelect : function(selectId){
			  var selectObject = $("#" + selectId + " option");
			  selectObject.each(function(){
				  if( $(this).val() !== ""){
					  $(this).remove();
				  }
			  });
		},
		
		//禁用下拉选框
		optionDisable : function(flag){
			if(flag){
				$("#stageId,#gradeId,#subjectId").attr("disabled","true");
			}else{
				$("#stageId,#gradeId,#subjectId").removeAttr("disabled");
			}

		},
		
		//保存与重置按钮展现或隐藏
		buttonShow : function(flag){
			if(flag){
				$("#btn-emptyLore").show();
				$("#btn-addLore").show();
			}else{
				$("#btn-emptyLore").hide();
				$("#btn-addLore").hide();
			}
		}
		
	};
})();

var setting1 = {
		async: {
			enable: true,
			url:path+"/admin/lore/searchLore",
			autoParam:["id", "subjectId","stageId","gradeId"]
		},
		view: {
			showIcon: showIconForTree
		},
		data: {
			simpleData: {
				enable: true
			}
		},
		callback: {
			onRightClick: OnRightClick,
			onClick:zTreeOnClick
		}
};

function filter(treeId, parentNode, childNodes) {
	if (!childNodes) return null;
	for (var i=0, l=childNodes.length; i<l; i++) {
		childNodes[i].name = childNodes[i].name.replace(/\.n/g, '.');
	}
	return childNodes;
}

function showIconForTree(treeId, treeNode) {
	return !treeNode.isParent;
};

function OnRightClick(event, treeId, treeNode) {
	if (!treeNode && event.target.tagName.toLowerCase() != "button" && $(event.target).parents("a").length == 0) {
		zTree.cancelSelectedNode();
		showRMenu("root", event.clientX, event.clientY);
	} else if (treeNode && !treeNode.noR) {
		zTree.selectNode(treeNode);
		showRMenu("node", event.clientX, event.clientY);
	}
}

function showRMenu(type, x, y) {
	$("#rMenu ul").show();
	if (type=="root") {
		$("#m_del").hide();
		$("#m_edit").hide();
		$("#m_upward").hide();
		$("#m_downmove").hide();
		$("#m_goup").hide();
		$("#m_downlower").hide();
	} else {
		$("#m_del").show();
		$("#m_edit").show();
		$("#m_upward").show();
		$("#m_downmove").show();
		$("#m_goup").show();
		$("#m_downlower").show();
	}
	$("#rMenu").css({top:y-150+"px", left:x-195+"px", "visibility":"visible"});
	$("body").bind("mousedown", onBodyMouseDown);
}

function hideRMenu() {
	if (rMenu) rMenu.css({"visibility": "hidden"});
	$("body").unbind("mousedown", onBodyMouseDown);
}

function onBodyMouseDown(event){
	if (!(event.target.id == "rMenu" || $(event.target).parents("#rMenu").length>0)) {
		rMenu.css({"visibility" : "hidden"});
	}
}

var addCount = 1;
var zTree ;
var rMenu ;

/**
 * 添加知识点
 */
function addTreeNode() {
	var subjectId = $("#subjectId").val();
	if(subjectId==""){
		bootbox.alert({ 
	        buttons: {  
	           ok: {  
	                label: '确认',  
	                className: 'btn-success'  
	            }  
	        },  
	        message: "请先选择科目！",  
	        callback: function() {  
	            
	        },  
	        title: "提示信息", 
		});
		dyLore.buttonShow(false);
		return;
	}
	hideRMenu();
	dyLore.optionDisable(true);
	dyLore.buttonShow(true);
	$('body input,textarea').attr("readonly",false);//去除input元素的readonly属性
	$("#lore_parentName").attr("readonly",true);//父节点永远都不能输入
	var nodes = zTree.getSelectedNodes();
	$("body input[id != 'lore_seq'],textarea").val("");
	$("#lore_stageId").val($("#stageId").find("option:selected").val());
	$("#lore_gradeId").val($("#gradeId").find("option:selected").val());
	$("#lore_subjectId").val($("#subjectId").find("option:selected").val());
	if(nodes.length == 0){
		$("#lore_name").focus();
	}else{
		var node = nodes[0];
		$("#lore_parentName").val(node.name);
		$("#lore_parentId").val(node.id);
	}
}

/**
 * 删除知识点
 */
function removeTreeNode() {
	dyLore.buttonShow(false);
	$('body input,textarea').attr("readonly",true);//去除input元素的readonly属性
	//$("#lore_parentName").attr("readonly",true);//父节点永远都不能输入
	hideRMenu();
	var nodes = zTree.getSelectedNodes();
	if(typeof(nodes[0].children) == 'undefined'){
		bootbox.confirm("您将删除'"+nodes[0].name+"'知识点信息，删除后信息将无法恢复，请确认此次操作？",function(r){
			if(r){
				$.post(path+"/admin/lore/deleLoreById",{id:nodes[0].id},function(ret){
					$.gritter.add({
						title:'知识点删除',
						text:ret.code == "1" ? "删除成功"+ret.msg : "删除失败"+ret.msg,
						sticky: false,
						time: '' 
					});
					if(ret.code == "1"){
						zTree.removeNode(nodes[0]);
						$('body input,textarea').val("");
					}
				});
			}
		});
		
	}
	else if(nodes[0].children.length > 0){
		var msg = "你删除的知识点下存在知识点，不允许删除，请先删除下级知识点！";
		bootbox.alert({ 
	        buttons: {  
	           ok: {  
	                label: '确认',  
	                className: 'btn-success'  
	            }  
	        },  
	        message: msg,  
	        callback: function() {  
	            
	        },  
	        title: "提示信息"
		});
	}else{
		bootbox.confirm("您将删除'"+nodes[0].name+"'知识点信息，删除后信息将无法恢复，请确认此次操作？",function(r){
			if(r){
				$.post(path+"/admin/lore/deleLoreById",{id:nodes[0].id},function(ret){
					$.gritter.add({
						title:'知识点删除',
						text:ret.code == "1" ? "删除成功"+ret.msg : "删除失败"+ret.msg,
						sticky: false,
						time: '' 
					});
					if(ret.code == "1"){
						zTree.removeNode(nodes[0]);
						$('body input,textarea').val("");
					}
				});
			}
		});
	}
}

/*
 向上移动知识点
*/
function moveUpwardTree(){
	dyLore.buttonShow(false);
	$('body input,textarea').attr("readonly",true);//去除input元素的readonly属性
	//$("#lore_parentName").attr("readonly",true);//父节点永远都不能输入
	zTree = $.fn.zTree.getZTreeObj("loreTree");
	var nodes = zTree.getSelectedNodes();
	if(nodes.length > 0){
		var node = nodes[0];
		var preNode = nodes[0].getPreNode();
		if(preNode == null){
			$.gritter.add({
				title:'知识点移动',
				text:"移动失败，已为同级第一个节点",
				sticky:false,
				time: ''
			});
			return;
		}
		zTree.moveNode(preNode,node,"prev");
		var params = {nodeId:node.id,moveNodeId:preNode.id};
		$.post(path+"/admin/lore/moveLore",params,function(ret){
			$.gritter.add({
				title:'知识点移动',
				text:ret.code == "1" ? "移动成功"+ret.msg : "移动失败"+ret.msg,
				sticky:false,
				time: ''
			});
		});
	}
}

/*
 向下移动知识点
*/
function moveDownTree(){
	dyLore.buttonShow(false);
	$('body input,textarea').attr("readonly",true);//去除input元素的readonly属性
	//$("#lore_parentName").attr("readonly",true);//父节点永远都不能输入
	zTree = $.fn.zTree.getZTreeObj("loreTree");
	var nodes = zTree.getSelectedNodes();
	if(nodes.length > 0){
		var node = nodes[0];
		var nextNode = nodes[0].getNextNode();
		if(nextNode == null){
			$.gritter.add({
				title:'知识点移动',
				text:"移动失败，已为同级最后节点",
				sticky:false,
				time: ''
			});
			return;
		}
		zTree.moveNode(node,nextNode,"prev");
		var params = {nodeId:node.id,moveNodeId:nextNode.id};
		$.post(path+"/admin/lore/moveLore",params,function(ret){
			$.gritter.add({
				title:'知识点移动',
				text:ret.code == "1" ? "移动成功"+ret.msg : "移动失败"+ret.msg,
				sticky:false,
				time: ''
			});
		});
	}
}

/*
 升级
*/
function goUpTree(){
	hideRMenu();
	dyLore.buttonShow(false);
	$('body input,textarea').attr("readonly",true);//去除input元素的readonly属性
	//$("#lore_parentName").attr("readonly",true);//父节点永远都不能输入
	zTree = $.fn.zTree.getZTreeObj("loreTree");
	var nodes = zTree.getSelectedNodes();
	if(nodes.length > 0){
		var node = nodes[0];
		var parNode = node.getParentNode();
		var pId;
		if(parNode == null){
			$.gritter.add({
				title:'知识点升级',
				text:"升级失败，该知识点已为根节点",
				sticky:false,
				time: ''
			});
			return;
		}else{
			pId = parNode.pId;
			var id = node.id;
			var params = {id:id,pId:pId};
			zTree.moveNode(parNode,node,"prev");
			$.post(path+"/admin/lore/loreChange",params,function(ret){
				$.gritter.add({
					title:'知识点升级',
					text:ret.code == "1" ? "升级成功"+ret.msg : "升级失败"+ret.msg,
					sticky:false,
					time: ''
				});
			});
		}
	}
}

/*
 降级	
*/
function downLowerTree(){
	hideRMenu();
	dyLore.buttonShow(false);
	$('body input,textarea').attr("readonly",true);//去除input元素的readonly属性
	//$("#lore_parentName").attr("readonly",true);//父节点永远都不能输入
	zTree = $.fn.zTree.getZTreeObj("loreTree");
	var nodes = zTree.getSelectedNodes();
	if(nodes.length > 0){
		var node = nodes[0];
		var preNode = nodes[0].getPreNode();
		var nextNode = nodes[0].getNextNode();
		var pId;
		var id = node.id;
		if(preNode == null && nextNode == null){
			$.gritter.add({
				title:'知识点降级',
				text:"降级失败,该节点已经最底层节点",
				sticky:false,
				time: ''
			});
			return;
		}else if(preNode != null){
			pId = preNode.id;
			var params = {id:id,pId:pId};
			$.post(path+"/admin/lore/loreChange",params,function(ret){
				$.gritter.add({
					title:'知识点降级',
					text:ret.code == "1" ? "降级成功"+ret.msg : "降级失败"+ret.msg,
					sticky:false,
					time: ''
				});
				
				if(ret.code == "1"){
					zTree.moveNode(preNode,node,"inner");
				}
			});
			
		}else{
			pId = nextNode.id;
			var params = {id:id,pId:pId};
			$.post(path+"/admin/lore/loreChange",params,function(ret){
				$.gritter.add({
					title:'知识点降级',
					text:ret.code == "1" ? "降级成功"+ret.msg : "降级失败"+ret.msg,
					sticky:false,
					time: ''
				});
				
				if(ret.code == "1"){
					zTree.moveNode(nextNode,node,"inner");
				}
			});
			
		}
		
	}
}

/*
	 修改知识点
*/
function editTreeNode(){
	dyLore.optionDisable(true);
	dyLore.buttonShow(true);
	$('body input,textarea').attr("readonly",false);//去除input元素的readonly属性
	$("#lore_parentName").attr("readonly",true);//父节点永远都不能输入
	hideRMenu();
	var nodes = zTree.getSelectedNodes();
	var node = nodes[0];
//	$("#lore_parentId").val(node.pId);
	$.post(path + "/admin/lore/searchLoreById?id="+node.id,function(ret){
		$.each(ret,function(k,v){
			$("#lore_form #lore_"+k).val(v);
		});
	});
	$("#lore_stageId").val($("#stageId").find("option:selected").val());
	$("#lore_gradeId").val($("#gradeId").find("option:selected").val());
	$("#lore_subjectId").val($("#subjectId").find("option:selected").val());
	$("#lore_remark").val(node.remark);
}

function checkTreeNode(checked) {
	var nodes = zTree.getSelectedNodes();
	if (nodes && nodes.length>0) {
		zTree.checkNode(nodes[0], checked, true);
	}
	hideRMenu();
}

//点击知识点，显示详情
function zTreeOnClick(event,treeId,treeNode){
	//hideRMenu();
	
	dyLore.optionDisable(false);
	dyLore.buttonShow(false);
	$('body input,textarea').attr("readonly",true);//去除input元素的readonly属性
	$('#loreNameId').attr("readonly",false);
	var nodes = zTree.getSelectedNodes();
	var node = nodes[0];
	$.post(path + "/admin/lore/searchLoreById?id="+node.id,function(ret){
		$.each(ret,function(k,v){
			$("#lore_form #lore_"+k).val(v);
		});
	});
	$("#lore_stageId").val($("#stageId").find("option:selected").val());
	$("#lore_gradeId").val($("#gradeId").find("option:selected").val());
	$("#lore_subjectId").val($("#subjectId").find("option:selected").val());
}

//按键F5 清空页面所有的输入框数据
$(window).keypress(function(event){
	var keyCode = event.keyCode;
	if(keyCode == 116){
		event.preventDefault();
		window.location.href = basePath+'/admin/lore';
	}
});
 
$(function(){
	dyLore.stageInit();
	
	dyLore.optionDisable(false);
	dyLore.buttonShow(false);
	
	//查询
	$("#searchButton").on('click',function(e){
		var _subId = $("#subjectId").find("option:selected").val();
		if(_subId == null || _subId == ""){
			bootbox.alert({ 
		        buttons: {  
		           ok: {  
		                label: '确认',  
		                className: 'btn-success'  
		            }  
		        },  
		        message: "必须选择科目！",  
		        callback: function() {  
		            
		        },  
		        title: "提示信息" 
			});
			return;
		}
		var _loreName = $("#loreNameId").val();
		var _stageId = $("#stageId").val();
		var _gradeId = $("#gradeId").val();
		var param = {subjectId:_subId,name:_loreName,flag:'1',stageId:_stageId,gradeId:_gradeId};
		setting1.async.otherParam = param;
		$.fn.zTree.init($("#loreTree"),setting1);
		zTree = $.fn.zTree.getZTreeObj("loreTree");
		rMenu = $("#rMenu");
		
	});
	
	//新增保存
	$("#btn-addLore").on('click',function(e){
		if($("#lore_form").valid()){
			$.ajaxSetup({async : false});
			$.post(path + "/admin/lore/addLore",$("#lore_form").formToObject(),function(addRet){
				var _id = $("#lore_id").val();
				if(_id == null || _id == ""){
					addCallBack(addRet);
				}else{
					$.gritter.add({
						title: '知识点更新',
						text: addRet.code == "1" ? "修改知识点信息成功！" : "修改知识点信息失败！",
						sticky: false,
						time: '',
						class_name: "gritter-light"
					});
					zTree = $.fn.zTree.getZTreeObj("loreTree");
					var nodes = zTree.getSelectedNodes();
					if (nodes.length>0) {
						nodes[0].name = $("#lore_name").val();
						nodes[0].remark = $("#lore_remark").val();
						zTree.updateNode(nodes[0]);
					}
				}
			});
			$.ajaxSetup({async : true});
		}
	});
	
	//新增成功执行的方法
	function addCallBack(addRet){
		$.gritter.add({
			title: '知识点新增',
			text:  addRet.code == "1" ? "新增知识点成功！" : "新增知识点失败！",
			sticky: false,
			time: '',
			class_name: "gritter-light"
		});
		if(addRet.code == "1"){
			var newNode = {name:addRet.name,id:addRet.id,pId:addRet.parentId};
			zTree.addNodes(zTree.getSelectedNodes()[0],newNode);
		}
		dyLore.optionDisable(false);
	}
	
	//重置
	$("#btn-emptyLore").on('click',function(e){
		//$("body input,textarea").val("");
		$("#lore_name").val("");
		$("#lore_seq").val("");
		$("#lore_remark").val("");
		dyLore.optionDisable(false);
	});
	
	
	$("#stageId").on("change",function(){
		
		var stageName = $("#stageId").find("option:selected").text();
		if(stageName=="--请选择--"){
			$("#subjectName").text("  ");
			$("#gradeName").text("  ");
			$("#stageName").text("  ");
			var txt = "--请选择--";
			$("#gradeId option[index!='0']").remove();
			$("#gradeId").prepend("<option selected='selected' value=''>--请选择--</option>");
			$("#subjectId option[index!='0']").remove();
			$("#subjectId").prepend("<option selected='selected' value=''>--请选择--</option>");
		}else{
			$("#stageName").text(stageName);
			$("#subjectName").text("  ");
			$("#gradeName").text("  ");
			var stageId = $(this).find("option:selected").val();
			$.post(path+'/admin/grade/findGradeByStage',{stageId:stageId},function(ret){
				if(ret){
					dyLore.removeSelect("gradeId");
					for(var i=0;i<ret.length;i++){
						var tempObj = ret[i];
						$("#gradeId").append("<option value='"+tempObj.id+"'>"+tempObj.name+"</option>");
					}
					$("#subjectId option[index!='0']").remove();
					$("#subjectId").prepend("<option selected='selected' value=''>--请选择--</option>");
				}
			});
		}
	});
	
	$("#gradeId").change(function(){
		var gradeId = $(this).find("option:selected").val();
		$.post(path+'/admin/subject/findSubByGrade',{gradeId:gradeId},function(retList){
			if(retList){
				dyLore.removeSelect("subjectId");
				for (var i = 0; i < retList.length; i++) {
					var gradeObj = retList[i];
					$("#subjectId").append("<option value='"+gradeObj.id+"'>"+gradeObj.name+"</option>");
				}
			}
		});
		var gradeName=$("#gradeId").find("option:selected").text();
		if(gradeName=="--请选择--"){
			$("#subjectName").text("  ");
			$("#gradeName").text("  ");
		}else{
			$("#gradeName").text(gradeName);
			$("#subjectName").text("  ");
		}
	});
	
	$("#subjectId").change(function(){
		var subjectName=$("#subjectId").find("option:selected").text();
		if(subjectName=="--请选择--"){
			$("#subjectName").text("  ");
		}else{
			$("#subjectName").text(subjectName);
		}
	});
	
	
	//validate expand
	jQuery.validator.addMethod("checkLoreName",function(value,element){
		var _id = $("#lore_id").val();//获取id的值 判断是新增还是修改
		var _subjectId = $("#subjectId").find("option:selected").val();
		var result = false;
		//设置为同步
		$.ajaxSetup({async:false});
		var param = {name:value,subjectId:_subjectId,id:_id};
		$.post(path+"/admin/lore/findLoreByName",param,function(data){
			result = (1 == data.code);
		});
		$.ajaxSetup({async:true});
		return result;
	},'知识点名称已存在');
	
	//表单验证
	$("#lore_form").validate({
		errorElement: 'span',
		errorClass: 'help-inline',
		focusInvalid: false,
		rules : {
			"lore.name" : {
				required: true,
				chsEngNum : true,
				rangelength:[2,15],
				checkLoreName : true
			},
			"lore.remark":{
				rangelength:[0,255]
			}
		},
		invalidHandler: function (event, validator) { //display error alert on form submit   
			$('.alert-danger', $('.login-form')).show();
		},

		highlight: function (e) {
			$(e).closest('.form-group').removeClass('has-info').addClass('has-error');
		},

		success: function (e) {
			$(e).closest('.form-group').removeClass('has-error').addClass('has-info');
			$(e).remove();
		},

		errorPlacement: function (error, element) {
			if(element.is(':checkbox') || element.is(':radio')) {
				var controls = element.closest('div[class*="col-"]');
				if(controls.find(':checkbox,:radio').length > 1) controls.append(error);
				else error.insertAfter(element.nextAll('.lbl:eq(0)').eq(0));
			}
			else if(element.is('.select2')) {
				error.insertAfter(element.siblings('[class*="select2-container"]:eq(0)'));
			}
			else if(element.is('.chosen-select')) {
				error.insertAfter(element.siblings('[class*="chosen-container"]:eq(0)'));
			}
			else error.insertAfter(element.parent());
		},

		submitHandler: function (form) {
		},
		invalidHandler: function (form) {
		}
	});
	
	$('#lore_seq').ace_spinner({
		value:1,
		min:1,
		max:2000,
		step:1, 
		btn_up_class:'btn-info' ,
		btn_down_class:'btn-info'
	});
	
});

