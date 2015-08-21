var sectionId=undefined;
var setting1={
		async:{
			enable:true,
			url:path+"/admin/lore/searchLore",
			otherParam:["sectionId",sectionId,"flag","1","subjectId",subjectId,"stageId",stageId,"gradeId",gradeId]
		},
		view:{
			//addHoverDom:addHoverDom,
			//removeHoverDom:removeHoverDom,
			selectedMulti: true,
			dblClickExpand:true,
			fontCss: getFontCss1
		},
		edit:{
			enable:true,
			showRemoveBtn:false,
			showRenameBtn:false
		},
		check:{
			enable:false,
			chkStyle:"checkbox",
			chkboxType:{"Y":"p","N":"s"},
			autoCheckTrigger:true
			
		},
		data:{
			simpleData: {
	             enable: true
	         }
		},
		callback: {
	    	//beforeRemove: zTreeBeforeRemove,
	    	//beforeRename: zTreeBeforeRename,
			onAsyncSuccess: zTreeOnAsyncSuccessLore1,
			onCheck: onClick
	 	},
};

$(function($){
	$.fn.zTree.init($("#sectionPointTree"),setting1);
});

function zTreeOnAsyncSuccessLore1(event, treeId, treeNode, msg){
	var obj = $.fn.zTree.getZTreeObj(treeId);
	obj.expandAll(true);
}

/**
 * 查询
 */
var nodeList1 = [], fontCss = {};

function searchNodeByLoreName() {
	var zTree = $.fn.zTree.getZTreeObj("sectionPointTree");
		key = $("#loreNameId");
		if($("#loreNameId").val()!=""){
			var valueData = $.trim(key.get(0).value);
			var keyTypeData = "name";
			updateNodes1(false);
			nodeList1 = zTree.getNodesByParamFuzzy(keyTypeData, valueData);
			updateNodes1(false);
			updateNodes1(true);
			$.fn.zTree.getZTreeObj("sectionPointTree").expandAll(true);
		}else{
			bootbox.alert({ 
	            buttons: {  
	               ok: {  
	                    label: '确认',  
	                    className: 'btn-success'  
	                }  
	            },  
	            message: '请输入知识点名称模糊查询！！',  
	            callback: function() {  
	                
	            },  
	            title: "提示信息", 
	        });  
			updateNodes1(false);
		}
}
function updateNodes1(highlight) {
	var zTree = $.fn.zTree.getZTreeObj("sectionPointTree");
	for( var i=0, l=nodeList1.length; i<l; i++) {
		nodeList1[i].highlight = highlight;
		zTree.updateNode(nodeList1[i]);
	}
}
function getFontCss1(treeId, treeNode) {
	return (!!treeNode.highlight) ? {color:"#A60000", "font-weight":"bold"} : {color:"#333", "font-weight":"normal"};
}

function selectLore(enent,treeId,treeNode){
	var obj = $.fn.zTree.getZtreeObj("sectionPointTree");
	$.fn.zTree.getZTreeObj("sectionTree");
}


var settingBySectionId={
		async:{
			enable:true,
			url:path+"/admin/lore/searchLoreBySectionId",
			otherParam:["sectionId",sectionId,"flag","1","subjectId",subjectId,"stageId",stageId,"gradeId",gradeId]
		},
		view:{
			//addHoverDom:addHoverDom,
			//removeHoverDom:removeHoverDom,
			selectedMulti: true,
			dblClickExpand:true,
			fontCss: getFontCss1
		},
		edit:{
			enable:true,
			showRemoveBtn:false,
			showRenameBtn:false
		},
		check:{
			enable:false
		},
		data:{
			simpleData: {
	             enable: true
	         }
		},
		callback: {
	    	//beforeRemove: zTreeBeforeRemove,
	    	//beforeRename: zTreeBeforeRename,
			onAsyncSuccess: zTreeOnAsyncSuccessBySectionId,
			onCheck: onClick
	 	},
};
var ids=[];
function onClick(enent,treeId,treeNode){
	$("#biaozhi").val(1);//设置知识点是不是勾选过，如果这个不是1，那么就说明知识点树没有点过，如果是1，就说明知识点被点过。
	var treeObj = $.fn.zTree.getZTreeObj("sectionPointTree");
	var node = treeObj.getNodeByTId(treeNode.tId);
	
	//获取父节点
	var sectionPId = node.pId;
	//获取节点ID
	var sectionId = node.id;
	
	var obj =  $.fn.zTree.getZTreeObj("sectionPointTree").getCheckedNodes(true);
	if(obj.length>0){
		
		var idlore=[];
		for(var i=0;i<obj.length;i++){
			idlore+=obj[i].id+",";
		}
	}
	$("#loreId").val(idlore);
}

function getChildren(ids,treeNode){

	ids.push(treeNode.id);

	if (treeNode.isParent){
		for(var obj in treeNode.children){
			getChildren(ids,treeNode.children[obj]);
		}
	}
	 return ids;
}

$("#dianjie").click(function(){
	var idLore = $("#loreId").val();
});
