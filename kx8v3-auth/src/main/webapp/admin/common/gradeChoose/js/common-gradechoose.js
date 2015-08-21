
/**
 * 年级与班级选择
 */
var dyGradeChoose = (function (){
	return {
		gradeInit : function(_schId){
			$.post(path+"/admin/grade/findGradeByTerm?schId="+_schId,function(ret){
				if(ret){
					for(var i=0;i<ret.length;i++){
						var obj = ret[i];
						$("select[id=gradeId]").append("<option value="+obj.id+">"+obj.name+"</option>");
					}
				}
			});
		},
		removeSelect : function(selectId){
			  var selectObject = $("#" + selectId + " option");
			  selectObject.each(function(){
				  if( $(this).val() !== ""){
					  $(this).remove();
				  }
			  });
		}
	};
})();

$(document).ready(function(){
	  $("select[id=gradeId]").change(function(){
		  	var gradeId = $(this).find("option:selected").val();
			$.post(path+'/admin/stuParent/findClassByGrade',{gradeId:gradeId},function(retList){
				if(retList){
					dyGradeChoose.removeSelect("classId");
					for (var i = 0; i < retList.length; i++) {
						var gradeObj = retList[i];
						$("select[id=classId]").append("<option value='"+gradeObj.id+"'>"+gradeObj.name+"</option>");
					}
				}
			});
		});
});