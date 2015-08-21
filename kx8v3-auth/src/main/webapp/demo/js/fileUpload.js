/**
 * 文件上传的Demo
 */
 
var uploadDemo = (function(){
	
	return {
		/**
		 * 初始化方法
		 */
		init : function(){
			this.eventInit();
		},
		
		/**
		 * 事件初始化方法
		 */
		eventInit : function(){
			//单文件上传
			iTide.util.initEuploadifySingleDialog("btn_singleUpload",{},function(data){
				alert("上传的文件信息如下：\n"+$.toJSON(data));
			});
		}
	};
})();


$(function(){
	uploadDemo.init();
});