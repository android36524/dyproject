/**
 * Created by ljy on 2015/6/16.
 */
function createDetailsGrid(){
    var grid_selector = "#grid-table-2";
    jQuery(grid_selector).jqGrid({
    	url : path + "/admin/classTeacher/details?classId="+_classId,
        datatype: "json",
        height : 200,
		width : 800,
        colNames:['编号','科目','任课老师'],
        colModel:[
            {name:'id',index:'id', width:200},
            {name:'subjectName',index:'subjectName',width:180},
            {name:'teacherName',index:'teacherName', width:180},
        ],
        viewrecords : true,
        altRows: true,
        multiselect: false,
        caption: "<i class='icon-list'></i> 任课老师详情",
    });
}
createDetailsGrid();

/**
 * 关闭当前弹窗
 */
var closeLayer = function(){
    var index = parent.layer.getFrameIndex(window.name);
    parent.layer.close(index);
};
