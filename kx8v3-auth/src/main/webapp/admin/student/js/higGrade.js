$(document).ready(function () {

    //加载已有的学校员工数据
    var id =_id;
    if(id){
        $.get(path + "/admin/student/findHigGradeInfoById?id="+id,function(ret){
                $.each(ret,function(k,v){
                	if(k=='sex' && v=='1'){
                		v='女';
                	}
                	else if(k=='sex' && v=='0'){
                		v='男';
                	}
                    $("#student_form #"+k).val(v);
                })
        });
    }
})