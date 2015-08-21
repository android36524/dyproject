var schoolBookId;
var key;
var setting = {
	async: {
		enable: true,
		url:path+"/admin/section/findSectionList",
		otherParam:["schoolBookId",schoolBookId]
	},
	 view: {
         addHoverDom: addHoverDom,
         removeHoverDom: removeHoverDom,
         selectedMulti: true,
         dblClickExpand : true,
         fontCss: getFontCss
     },
     check: {
         enable: false
     },
     data: {
         simpleData: {
             enable: true
         }
     },
     edit: {
         //enable: true
         //showRemoveBtn: setRemoveBtn,
         //showRenameBtn: setRenameBtn,
         //renameTitle: "编辑节点名称",
         //removeTitle: "删除章节"
     },
     callback: {
    	beforeRemove: zTreeBeforeRemove,
    	beforeRename: zTreeBeforeRename,
    	onClick: zTreeOnClick,
    	onRightClick:OnRightClick,
    	onAsyncSuccess: zTreeOnAsyncSuccessSection
 	},
};

$(document).ready(function() {
	var newTree=$.fn.zTree.init($("#sectionTree"), setting);
});

function zTreeOnAsyncSuccessSection(event, treeId, treeNode, msg){
	var obj1 = $.fn.zTree.getZTreeObj(treeId);
	obj1.expandAll(true);
}

/**
 * 如果是父节点，不能删除
 * @param treeId
 * @param treeNode
 * @returns
 */
function setRemoveBtn(treeId, treeNode) {
	return !treeNode.isParent;
}

/**
 * 如果是父节点，不能修改名称
 * @param treeId
 * @param treeNode
 * @returns
 */
function setRenameBtn(treeId, treeNode) {
	
	var treeObj = $.fn.zTree.getZTreeObj("sectionTree");
	var node = treeObj.getNodeByTId(treeNode.tId);
	if(node.pId==null){
		return false;
	}
	return true;
}



var rMenu ;
var newCount = 1;
function addHoverDom(treeId, treeNode) {
	
	
   
};

function removeHoverDom(treeId, treeNode) {
    $("#addBtn_"+treeNode.tId).unbind().remove();
};

function zTreeOnNodeCreated(event, treeId, treeNode) {
    bootbox.alert({ 
        buttons: {  
           ok: {  
                label: '确认',  
                className: 'btn-success'  
            }  
        },  
        message: treeNode.tId + ", " + treeNode.name,  
        callback: function() {  
            
        },  
        title: "提示信息", 
    });  
};

//增加节点
function addZTreeNode(sectionId){
	 //新增
	hideRMenu();
    var  delFlag = false;
        var nodeName = "章节"+newCount++;
        $.ajaxSetup({async : false});
    	$.get(path + "/admin/section/save",{pId : sectionId,name : nodeName},function(saveRet){
    		$.gritter.add({
    			title: '章节',
    			text: saveRet.code == "1" ? "添加成功" : "添加失败："+saveRet.msg,
    			sticky: false,
    			time: ''
    		});
    		if(saveRet.code==1){
    			var treeObj = $.fn.zTree.getZTreeObj("sectionTree"); 
    			delFlag = true;
    			var newNode = {name:nodeName,id:saveRet.id,pId:saveRet.parentId};
    			treeObj.addNodes(treeObj.getSelectedNodes()[0],newNode);
    			var newTree = $.fn.zTree.init($("#sectionTree"), setting);
    			newTree.expandAll(true);
    		}
    	});
    	$.ajaxSetup({async : true});
        return delFlag;
}

//删除节点
function zTreeBeforeRemove(treeId, treeNode) {
	var  delFlag = false;
	$.ajaxSetup({async : false});
	$.get(path + "/admin/section/del",{id : treeNode.id},function(delRet){
		$.gritter.add({
			title: '章节',
			text: delRet.code == "1" ? "删除成功" : "删除失败："+delRet.msg,
			sticky: false,
			time: ''
		});
		if(delRet.code==1)delFlag = true;
	});
	$.ajaxSetup({async : true});
	return delFlag;
}

//编辑节点之前
function zTreeBeforeRename(treeId, treeNode, newName, isCancel) {
	var  delFlag = false;
	if(newName.length == 0){
		bootbox.alert({ 
		        buttons: {  
		           ok: {  
		                label: '确认',  
		                className: 'btn-success'  
		            }  
		        },  
		        message: "请输入章节名称！",  
		        callback: function() {  
		            
		        },  
		        title: "提示信息", 
		});  
		var treeObj = $.fn.zTree.getZTreeObj(treeId);
		treeObj.cancelEditName(treeNode.name);
		return delFlag;
	}
	if(newName.length > 20 ){
		bootbox.alert({ 
	        buttons: {  
	           ok: {  
	                label: '确认',  
	                className: 'btn-success'  
	            }  
	        },  
	        message: "章节名称请控制在20字以内！",  
	        callback: function() {  
	            
	        },  
	        title: "提示信息", 
		});  
		var treeObj = $.fn.zTree.getZTreeObj(treeId);
		treeObj.cancelEditName(treeNode.name);
		return delFlag;
	}
	if(newName==treeNode.name){
		var treeObj = $.fn.zTree.getZTreeObj(treeId);
		treeObj.cancelEditName();
	}else{
		$.ajaxSetup({async : false});
		$.get(path + "/admin/section/save",{id : treeNode.id,name:newName},function(delRet){
			$.gritter.add({
				title: '章节',
				text: delRet.code == "1" ? "修改成功" : "修改失败："+delRet.msg,
						sticky: false,
						time: ''
			});
			if(delRet.code==1)delFlag = true;
		});
	}
	$.ajaxSetup({async : false});
	return delFlag;
}

////删除之后
//function zTreeOnRemove(event, treeId, treeNode) {
//	alert("删除节点之后"+treeNode.tId + ", " + treeNode.name);
//}

//编辑之后
//function zTreeOnRename(event, treeId, treeNode, isCancel) {
//	alert("编辑节点之后的名字"+treeNode.tId + ", " + treeNode.name);
//	alert("编辑之后");
//}


/**
 * 查询
 */
var lastValue = "", nodeList = [], fontCss = {};

function searchNode() {
	var zTree = $.fn.zTree.getZTreeObj("sectionTree");
		key = $("#key");
		if($("#key").val()!=""){
			var value = $.trim(key.get(0).value);
			var keyType = "name";
			updateNodes(false);
			nodeList = zTree.getNodesByParamFuzzy(keyType, value);
			updateNodes(false);
			updateNodes(true);
			$.fn.zTree.getZTreeObj("sectionTree").expandAll(true);
		}else{
			bootbox.alert({ 
		        buttons: {  
		           ok: {  
		                label: '确认',  
		                className: 'btn-success'  
		            }  
		        },  
		        message: "请输入章节名称模糊查询！",  
		        callback: function() {  
		            
		        },  
		        title: "提示信息", 
			});
			updateNodes(false);
		}
}
function updateNodes(highlight) {
	var zTree = $.fn.zTree.getZTreeObj("sectionTree");
	for( var i=0, l=nodeList.length; i<l; i++) {
		nodeList[i].highlight = highlight;
		zTree.updateNode(nodeList[i]);
	}
}
function getFontCss(treeId, treeNode) {
	return (!!treeNode.highlight) ? {color:"#A60000", "font-weight":"bold"} : {color:"#333", "font-weight":"normal"};
}


function zTreeOnClick(event,treeId,treeNode){
	
	
	
	$("#loreId").val("");
	var treeObj = $.fn.zTree.getZTreeObj("sectionTree");
	var node = treeObj.getNodeByTId(treeNode.tId);
	//获取父节点(章节)
	var sectionPId = node.pId;
	//获取节点ID(章节)
	var sectionId = node.id;
	
	$("#sectionId").val(sectionId);
	if(sectionPId!=null){//如果不是父节点，点击的时候，先查询出此章节相关的知识点，然后显示添加知识点按钮，点击添加知识点按钮以后，查询此学阶年级科目下的所有知识点，以便于继续添加知识点
		
		var param = {sectionId:sectionId,subjectId:subjectId,gradeId:gradeId,stageId:stageId,flag:"1"};
		setting1.async.otherParam = param;
		var obj = $.fn.zTree.init($("#sectionPointTree"),setting1);
		obj.expandAll(true);
		$.get(path + "/admin/lore/searchSectionLoreID",{sectionId:sectionId,subjectId:subjectId,gradeId:gradeId,stageId:stageId,flag:"1"},function(delRet){
			if(delRet.length==0){
				document.getElementById("loreDiv").style.display="block";
			}else{
				document.getElementById("loreDiv").style.display="none";
			}
			$("#selectLoreId").val(delRet);
			$("#loreId2").val(delRet);
		});
		
		return false;
	}else{
		var param = {subjectId:subjectId,gradeId:gradeId,stageId:stageId,flag:"1"};
		setting1.async.otherParam = param;
		$.fn.zTree.init($("#sectionPointTree"),setting1);
		document.getElementById("loreDiv").style.display="none";
	}
}


//以右击菜单形式操作树
//右击菜单
function OnRightClick(event, treeId, treeNode) {
	var zTree1 = $.fn.zTree.getZTreeObj(treeId);
	
	var nodes = zTree1.getSelectedNodes();
	if(nodes.length == 0){
	}else{
		var node = nodes[0];
		var sectionPId = node.pId;
		var sectionName =node.name;
		var sectionId = node.id;
		if(sectionPId==null){
			$("#m_add").val(sectionId);
			zTree1.cancelSelectedNode();
			showRMenu("root", event.clientX, event.clientY);
		}else{
			$("#m_add").val(sectionId);
			zTree1.selectNode(treeNode);
			showRMenu("node", event.clientX, event.clientY);
		}
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
		$("#m_addLore").hide();
	} else {
		$("#m_del").show();
		$("#m_addLore").show();
		$("#m_edit").show();
		$("#m_upward").show();
		$("#m_downmove").show();
		$("#m_goup").show();
		$("#m_downlower").show();
	}
	$("#rMenu").css({top:y-100+"px", left:x-450+"px", "visibility":"visible"});
	$("body").bind("mousedown", onBodyMouseDown);
}

function hideRMenu() {
	if ($("#rMenu")) $("#rMenu").css({"visibility": "hidden"});
	$("body").unbind("mousedown", onBodyMouseDown);
}

function onBodyMouseDown(event){
	if (!(event.target.id == "rMenu" || $(event.target).parents("#rMenu").length>0)) {
		$("#rMenu").css({"visibility" : "hidden"});
	}
}

//增加章节
function addTreeNode(){
	
	var zTree_add = $.fn.zTree.getZTreeObj("sectionTree");
	var nodes = zTree_add.getSelectedNodes();
	if(nodes.length == 0){
		var sectionId = $("#m_add").val();
		addZTreeNode(sectionId);
	}else{
		var node = nodes[0];
		var sectionPId = node.pId;
		var sectionName =node.name;
		var sectionId = node.id;
		addZTreeNode(sectionId);
	}

}

//修改章节
function editTreeNode(){
	hideRMenu();
	var zTree_modify = $.fn.zTree.getZTreeObj("sectionTree");
	
	var nodes = zTree_modify.getSelectedNodes();
	var node = nodes[0];
	var sectionName =node.name;
	var sectionId = node.id;
	
	//$.post(path + "/admin/schoolBook/editSectionName.jsp?sectionName="+sectionName+"&sectionId="+sectionId,function(ret){
	$.post(path + "/admin/schoolBook/editSectionName.jsp",{sectionName :sectionName,sectionId:sectionId},function(ret){
		bootbox.dialog({
			title : "修改章节名称",
			message : ret,
			buttons : {
				success : {
					label: "保存",
                    className: "btn-success",
                    callback :  function(result) {
						if(result) {
							if($("#editSectionName_form").valid()){
								//{id :sectionId,name:newName}
								var newName =$("#editSectionName_form #section_name").val();
								$.ajaxSetup({async : false});
								$.post(path + "/admin/section/save",{id :sectionId,name:newName},function(addRet){
									$.gritter.add({
										title: '章节名称',
										text: addRet.code == "1" ? "修改成功" : "修改失败",
										sticky: false,
										time: '',
										class_name: "gritter-light"
									});
									if(addRet.code==1){
						    			var treeObj = $.fn.zTree.getZTreeObj("sectionTree");
						    			var nodes = treeObj.getSelectedNodes();
						    			if (nodes.length>0) {
						    				nodes[0].name = addRet.name;
						    				treeObj.updateNode(nodes[0]);
						    			}
						    		}
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
	
}

//删除章节
function removeTreeNode(){
	var zTree_del = $.fn.zTree.getZTreeObj("sectionTree");
	var nodes = zTree_del.getSelectedNodes();
	var node = nodes[0];
	var sectionName =node.name;
	var sectionId = node.id;
	
	hideRMenu();
	//var nodes = zTree_del.getSelectedNodes();
	if(typeof(node.children) == 'undefined'){
		bootbox.confirm("您将删除 "+"<font color='red' size='2px'><b>"+sectionName+"</b></font>"+" 章节信息，删除后信息将无法恢复，请确认此次操作？",function(r){
			if(r){
				$.post(path+"/admin/section/del",{id : sectionId},function(ret){
					$.gritter.add({
						title:'章节删除',
						text:ret.code == "1" ? ret.msg :ret.msg,
						sticky: false,
						time: '' 
					});
					if(ret.code == "1"){
						zTree_del.removeNode(node);
					}
				});
			}
		});
		
	}
	else if(node.children.length > 0){
		var msg = "您删除的章节下存在子章节，不允许删除，请先删除子章节！";
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
	        title: "提示信息", 
		});
	}else{
		bootbox.confirm("您将删除 "+"<font color='red' size='2px'><b>"+sectionName+"</b></font>"+" 章节信息，删除后信息将无法恢复，请确认此次操作？",function(r){
			if(r){
				$.post(path+"/admin/section/del",{id : sectionId},function(ret){
					$.gritter.add({
						title:'章节删除',
						text:ret.code == "1" ? ret.msg : ret.msg,
						sticky: false,
						time: '' 
					});
					if(ret.code == "1"){
						zTree_del.removeNode(node);
					}
				});
			}
		});
	}
	
}

//添加知识点
function addLoreTreeNode(){
	hideRMenu();
	var sectionId = $("#sectionId").val();
	settingBySectionId.check.enable=true;
    var param1 = {sectionId:sectionId,subjectId:subjectId,gradeId:gradeId,stageId:stageId,flag:"1"};
    settingBySectionId.async.otherParam = param1;
	var obj = $.fn.zTree.init($("#sectionPointTree"),settingBySectionId);
}

function zTreeOnAsyncSuccessBySectionId(event, treeId, treeNode, msg){
	var obj2 = $.fn.zTree.getZTreeObj(treeId);
	obj2.expandAll(true);
}
