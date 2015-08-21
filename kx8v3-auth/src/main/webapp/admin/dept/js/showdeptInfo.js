
$(document).ready(function () {
//加载已有的教育部门数据

    var id =_id;
    if(id){
        $.get(path + "/admin/dept/queryById?id="+id,function(ret){
            $.each(ret,function(k,v){
                $("#dept_form #dept_"+k).val(v);
            });
        });
    }
});


    