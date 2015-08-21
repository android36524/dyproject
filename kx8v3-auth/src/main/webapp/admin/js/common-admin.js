/**
 * 后台管理的公共类
 * @author zhangcs
 */
var commonAdmin = (function(){
	
	return {
		/**
		 * 打开组织机构选择
		 */
		openOrgWin : function(){
			$.layer({
			    type: 2,
			    title: '组织机构选择',
			    shadeClose: true,
			    btns: 2,
			    btn: ['确定', '取消'],
			    shade: [0.5, '#000'],
			    area: ['300px', '550px'],
			    iframe: {src : path + '/admin/common/orgTree/orgtree-dialog.jsp'},
			    yes : function(ind){
			    	alert("您选择了节点");
//			    	$.layer.close(ind);
			    }
			}); 

		}
	};
})();