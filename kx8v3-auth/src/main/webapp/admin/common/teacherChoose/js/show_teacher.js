/**
 * 教师列表
 */
var dyTeacher = (function() {

	return {
		init : function(schId) {
			//this.initDept(schId);
			this.createGrid(schId);
		},

		initDept : function(schId) {
			$.get(path + "/admin/class/initDept?schId=" + schId, function(ret) {
				if (ret) {
					$.each(ret, function(name, value) {
						var deptStr = "<option value=" + value.id + ">"
								+ value.deptName + "</option>";
						$('#dept_id').append(deptStr);
					});
				}
			});
		},

		/**
		 * 创建Grid
		 */
		createGrid : function(schId) {
			var grid_selector = "#empe-tab";
			var pager_selector = "#empe-pager";

			jQuery(grid_selector).jqGrid({
				url : path + "/admin/class/getTeacherList?schId=" + schId,
				datatype : "json",
				height : 200,
				width : 830,
				colNames : [ '序号','老师ID', '工号', '教师姓名', '性别', '所属部门', '手机号码' ],
				colModel : [{
					name : 'rowno',
					index : 'rowno',
					width : 80,
					sortable : false
				}, {
					name : 'id',
					index : 'id',
					width : 80,
					sortable : false,
					hidden: true
				}, {
					name : 'empNo',
					index : 'empNo',
					width : 80,
					sortable : false
				}, {
					name : 'name',
					index : 'name',
					width : 80,
					sortable : false
				}, {
					name : 'sex_showname',
					index : 'sex',
					width : 80,
					sortable : false
				}, {
					name : 'deptName',
					index : 'deptName',
					width : 80,
					sortable : false
				}, {
					name : 'mobile',
					index : 'mobile',
					width : 80,
					sortable : false
				} ],

				viewrecords : true,
				rowNum : 30,
				rowList : [ 20, 30, 40, 50 ],
				pager : pager_selector,
				altRows : true,
				multiselect : false,
				multiboxonly : true,
				loadComplete : function() {
					var table = this;
					setTimeout(function() {
						dyjsCommon.enableTooltips(table);
						dyjsCommon.updatePagerIcons(table);
						$('button[menuid]').tooltip({
							container : 'body'
						});
					}, 0);
				},
				onSelectRow : function(rowid, status) {
					var rowData = $(grid_selector).jqGrid('getRowData', rowid);
					$("#selected").val($.toJSON(rowData));
				},
				caption : "<i class='icon-list'></i>教师列表"
			});
		},

		/**
		 * 重新加载grid
		 */
		reloadGrid : function() {
			$("#empe-tab").trigger("reloadGrid");
		},
		reloadGridByParams : function(params){
			$("#empe-tab").jqGrid('setGridParam',{  
		        datatype:'json',  
		        mtype :'POST',
		        postData: params,
		        page:1  
		    }).trigger("reloadGrid"); 
		}
	};
})();

$(function() {
	dyTeacher.init(_schId);
	
});

function findByParams(){
	var params = {};
	params.searchBy = $("#search_id").val();
	params.searchText = $("#searchText").val();
	params.schId = _schId;
	dyTeacher.reloadGridByParams(params);
}

var closeLayer = function() {
	var index = parent.layer.getFrameIndex(window.name);
	parent.layer.close(index);
};