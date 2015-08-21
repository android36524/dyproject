
$(document).ready(function () {
//加载已有的教育部门数据

    var id =_id;
    if(id){
    	if(_title=="公司"){
    		document.getElementById("companyNameDiv").style.display='none';
    	}
        $.get(path + "/admin/dept/queryById?id="+id,function(ret){
            $.each(ret,function(k,v){
                $("#dept_form #dept_"+k).val(v);
            });
        });
    }

    if(id){
    	if(_title=="公司"){
    		document.getElementById("companyNameDiv").style.display='none';
    	}
        $.get(path + "/admin/dept/queryById?id="+id,function(ret){
            $.each(ret,function(k,v){
                var _val = v;
                if(v == null){
                    _val = "";
                }
                $("#deptSch_form #dept_"+k).text(_val);
            });
        });
    }


    //实时生成部门编号
    $('#dept_deptName').bind('blur',function(){

        $('#dept_deptCode').val('');

        var orgId = $("#dept_orgId").val();
        if(orgId){
            $.get(path + "/admin/dept/genDeptCode?orgId="+orgId, function(ret){
                if(ret){
                    $('#dept_deptCode').val(ret);
                }
            });
        }

    });
    


    //选择所属部门
    $("#btn_select_dept").on(ace.click_event, function() {
    	
    	var orgId = $("#dept_orgId").val();
        var deptId = $("#dept_id").val();
    	var _params = {
    		"orgId" : orgId,
    		"deptId" : deptId
    	};
    	
    	commonJs.showDeptDialog(_params,function(dept){
    		$("#dept_pid").val(dept.deptId);
			$("#dept_parentName").val(dept.deptName);
    	});
    });


//表单验证
    $("#dept_form").validate({
        errorElement: 'span',
        errorClass: 'help-inline',
        focusInvalid: false,
        rules : {
            "dept.deptName" : {
                required: true,
                chinese : function(){
                	if(_title!="公司"){
                		return true;
                	}
                },
                rangelength:[2,10],
                remote : {
                    url:path+"/admin/dept/validateDeptName",
                    type:"post",
                    async:false,
                    dataType:"json",
                    data:{
                        deptName:function(){
                            return $("#dept_deptName").val();
                        },
                        orgId:function(){
                            return $("#dept_orgId").val();
                        },
                        deptId: function () {
                            return $("#dept_id").val();
                        }
                    }
                }
            },
            "dept.telphone" : {
                telOrMobile: true,
                maxlength:20
            },
            "dept.chargePhone" : {
                telOrMobile: true,
                maxlength:20
            },
            "dept.charge" : {
                maxlength:50
            }
            ,
            "dept.seq":{
                notIsInteger:true
            },
            "dept.fax":{
            	fax:true
            }

        },
        messages : {

            "dept.deptName" : {
                required : "请输入部门名称",
                remote : "部门名称不允许重复"
            },
            "dept.telphone" :{
                telOrMobile : "请输入正确的电话号码(中国)或移动电话号码(中国)"
            },
            "dept.chargePhone" :{
                telOrMobile : "请输入正确的电话号码(中国)或移动电话号码(中国)"
            },
            "dept.seq":{
                notIsInteger:"不允许输入小于0和小数的数字."
            }
        },
        invalidHandler: function (event, validator) { //display error alert on form submit
            $('.alert-danger', $('.login-form')).show();
        },

        highlight: function (e) {
            $(e).closest('.form-group').removeClass('has-info').addClass('has-error');
        },

        success: function (e) {
            $(e).closest('.form-group').removeClass('has-error').addClass('has-info');
            $(e).remove();
        },

        errorPlacement: function (error, element) {
            if(element.is(':checkbox') || element.is(':radio')) {
                var controls = element.closest('div[class*="col-"]');
                if(controls.find(':checkbox,:radio').length > 1) controls.append(error);
                else error.insertAfter(element.nextAll('.lbl:eq(0)').eq(0));
            }
            else if(element.is('.select2')) {
                error.insertAfter(element.siblings('[class*="select2-container"]:eq(0)'));
            }
            else if(element.is('.chosen-select')) {
                error.insertAfter(element.siblings('[class*="chosen-container"]:eq(0)'));
            }
            else error.insertAfter(element.parent());
        },

        submitHandler: function (form) {
        },
        invalidHandler: function (form) {
        }
    });
});
function setLayerDeptGroup(deptId,deptName){

    $("#dept_pid").val(deptId);
    $("#dept_parentName").val(deptName);
}