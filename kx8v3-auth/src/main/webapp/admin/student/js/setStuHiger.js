/**
 * 学生毕业升学设置
 */
var gdGradu = {};
var gradeList = {};
var ckid = 0;

$(document).ready(function(){
    var obj = _tree.initEdbTree("1",{}, function () {
        var nodes = _edbTreeObj.getSelectedNodes();
        $('#GraduationGrad').empty();
        $('#hightTime').empty();
        if(!_tree.isSchoolNode(nodes[0])){
            return;
        }
        _schId = nodes[0].id;
        getHightGrade(_schId);
        getHigGradeTime(_schId);
    });
    _schId=obj.id;
    getHightGrade(_schId);
    getHigGradeTime(_schId);
    
    
    function getHigGradeTime(_schId){
    	 $.get(path + "/admin/student/getHigGradeTime?schId="+_schId,function(ret){
    		 if(ret != null){
    			 var runTime = ret;
        		 $('#hightTime').val(runTime);
    		 }
    		
    	 });
    }
    

    function getHightGrade(_schId){
        $.get(path + "/admin/grade/setGradeHigerById?schId="+_schId,function(ret){
            gdGradu = ret.gradeGraduation;
            gradeList = ret.gradeList;
            $(gdGradu).each(function(k,v){
                var ck = "";
                if(k=='0'){
                    ck="checked=true";
                    ckid = v.id;
                }
                var r = "<input type=radio name='rdGru' "+ck+"  value='"+v.id+"' >"+ v.name;
                $('#GraduationGrad').append(r);
            });
            initGradeList();
            $("input[name=rdGru]").each(function(){
                $(this).change(function(){
                    ckid = $(this).attr("value");
                    initGradeList();
                })
            })
        });
    }

    function initGradeList(){
        var grade = "";
        $(gradeList).each(function(k,v){
            if(v.id!=ckid)
                grade += v.name+",";
        });
        $('#ProgressingGrad').val(grade);
    }


    //时间选择
    $('#hightTime').datepicker({
        language:'zh-CN',
        autoclose:true,
        pickTime: false,
        todayBtn: true,
        format:'yyyy-mm-dd hh:ii:ss'
    }).prev().on(ace.click_event, function(){
        $(this).next().focus();
    });

    //保存毕业升学设置
    $("#save_btn").click(function(){
        var hightTime = $('#hightTime').val();
        if(hightTime== null || hightTime == '')
        {
        	bootbox.alert("毕业时间不允许为空！");
            return;
        }
        $.get(path + "/admin/student/setGraduateStu?gradeId="+ckid+"&hightTime="+hightTime+"&schId="+_schId,function(delRet){
            $.gritter.add({
                title: '学生毕业升学设置',
                text: delRet.code == "1" ? "设置成功" : "设置失败："+delRet.msg,
                sticky: false,
                time: ''
            });
        });
    });
});
