
var dyLogInfo = (function(){
	
	return {
		
		init : function(){
			this.createGrid();
		},
		
		/**
		 * 创建Grid
		 */
		createGrid : function(){
			var grid_selector = "#excel-table";
			var pager_selector = "#excel-pager";
			
			jQuery(grid_selector).jqGrid({
				url : path + "/admin/logInfo/logInfoList",
				datatype: "json",
				height: 450,				
				colNames:['文件名称','导入时间','导入成功数量','导入失败数量','操作'],
				colModel:[							
						{name:'fileName',index:'id',type:'text',width:40},
						{name:'importTime',index:'id',width:120},
						{name:'succesCount',index:'id',width:120},
						{name:'errorCount',index:'id',width:120},{name:'id',index:'id', width:80, sortable:false,formatter : function(e,options, rowObject){
						var data=rowObject.downLoadUrl;
						if(data!=null && data != ""){
							return '<div class="visible-md visible-lg hidden-sm hidden-xs btn-group">' +
								'<button class="btn btn-xs btn-info tooltip-info" dyid="excel-downLoad" excelUrl="'+data+'" data-rel="tooltip" data-placement="top" title="下载">' +
								'<i class="icon-edit bigger-120"></i></button>' +
								'</div>';
						}else{
							return '';
						}
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
                caption: "<i class='icon-list'></i>Excel日志文件列表",
                autowidth: true
				
			});			
		},
		
		/**
		 * 重新加载学阶数据
		 */
		reloadGrid : function(){
			$("#altenation-table").trigger("reloadGrid");
		}
		
	};
})();
 
$(function() {

	dyLogInfo.init();
	$("#find_btn").click(function () {
		$("#altenation-table").jqGrid('setGridParam', {
			url: path + "/admin/logInfo/logInfoList",
			datatype: 'json',
			mtype: 'POST',
			postData: {'fileName':$("#fileName").val()}, //发送数据
			page: 1
		}).trigger("reloadGrid");
	});

	$("body").on(ace.click_event,"button[dyid='excel-downLoad']", function() {
		var downLoadUrl = $(this).attr("excelUrl");
		location.href = path +"/admin/logInfo/downExcel?downLoadUrl="+downLoadUrl;
	});
});

