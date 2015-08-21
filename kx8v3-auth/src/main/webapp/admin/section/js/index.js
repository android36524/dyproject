var setting = {
	async: {
		enable: true,
		url:"/auth/admin/section/findSectionList",
	},
	 view: {
         addHoverDom: addHoverDom,
         removeHoverDom: removeHoverDom,
         selectedMulti: false
     },
     check: {
         enable: true
     },
     data: {
         simpleData: {
             enable: true
         }
     },
     edit: {
         enable: true
     },
     callback: {
    	beforeRemove: zTreeBeforeRemove,
    	beforeRename: zTreeBeforeRename,
 	},
};

$(document).ready(function() {
	$.fn.zTree.init($("#sectionTree"), setting);
});

var newCount = 1;
function addHoverDom(treeId, treeNode) {
    var sObj = $("#" + treeNode.tId + "_span");
    if (treeNode.editNameFlag || $("#addBtn_"+treeNode.tId).length>0) return;
    var addStr = "<span class='button add' id='addBtn_" + treeNode.tId
        + "' title='add node' onfocus='this.blur();'></span>";
    sObj.after(addStr);
    //新增
    var btn = $("#addBtn_"+treeNode.tId);
    if (btn) btn.bind("click", function(){
        var zTree = $.fn.zTree.getZTreeObj("sectionTree");
        var nodeName = "章节"+newCount++;
        alert(nodeName);
        alert("pId"+treeNode.id);
        $.ajaxSetup({async : false});
    	$.get(path + "/admin/section/save",{pId : treeNode.id,name : nodeName},function(saveRet){
    		$.gritter.add({
    			title: '章节',
    			text: saveRet.code == "1" ? "添加成功" : "添加失败："+saveRet.msg,
    			sticky: false,
    			time: ''
    		});
    		if(saveRet.code==1)delFlag = true;
    	});
    	$.ajaxSetup({async : true});
//        zTree.addNodes(treeNode, {id:(100 + newCount), pId:treeNode.id, name:"章节" + (newCount++)});
        return true;
    });
};

function removeHoverDom(treeId, treeNode) {
    $("#addBtn_"+treeNode.tId).unbind().remove();
};

function zTreeOnNodeCreated(event, treeId, treeNode) {
    alert(treeNode.tId + ", " + treeNode.name);
};

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
		alert("请输入章节名称！");
		var treeObj = $.fn.zTree.getZTreeObj(treeId);
		treeObj.cancelEditName(treeNode.name);
		return delFlag;
	}
	if(newName.length > 20 ){
		alert("章节名称请控制在20字以内！");
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
