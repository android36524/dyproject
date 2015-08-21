  var rows = [];
  $(document).ready(function(){
     	var grid_selector = "#chooseStuDetail-table";
        jQuery(grid_selector).jqGrid({
            datatype: "local",
            height: 150,
            colNames:['序号','班级','学生姓名', '关系', '是否监护人','关系','学生ID'],
            colModel:[
                {name:'id',index:'id',width:150, sortable:false},
                {name:'className',index:'className', width:200, sortable:false},
                {name:'studentName',index:'studentName', width:150, sortable:false},
                {name:'relationTypeName',index:'relationTypeName', width:150, sortable:false},
                {name:'guardian',index:'guardian', width:150, sortable:false,formatter:function(j,options,rowObject){
                	var formatStr = '';
                	if(j == 1){
                		formatStr += '<p style="text-align:center">是</p>';
                	}else{
                		formatStr += '<p style="text-align:center">否</p>';
                	}
                	return formatStr;
                }},
                {name:'relationType',index:'relationType',hidden:true},
                {name:'studentId',index:'studentId',hidden:true}
                
            ],
            viewrecords : true,
			altRows: true,
			multiselect: false,
	        multiboxonly: true,
			loadComplete : function() {
				var table = this;
				setTimeout(function(){
					dyjsCommon.enableTooltips(table);
					dyjsCommon.updatePagerIcons(table);
					$('button[menuid]').tooltip({container : 'body'});
				}, 0);
			},
			autowidth: true

      });
        
        $.ajaxSetup({async:false});
    	if(_id){
     		$.get(path + "/admin/stuParent/findStuParentById",{id:_id,schId:_schId},function(ret){
    			var _stuList = ret.studentList;
    			for(var i = 0;i < _stuList.length;i++){
            		var _stuObj = _stuList[i];
            		var rowObj = {
            				id:i+1,
            				className:_stuObj.className,
            				studentName:_stuObj.name,
            				relationTypeName:_stuObj.relationType_showname,
            				guardian:_stuObj.guardian,
            				relationType:_stuObj.relationType,
            				studentId:_stuObj.studentId
            				};
            		jQuery("#chooseStuDetail-table").jqGrid('addRowData', i + 1, rowObj);
    			}
    			$.each(ret,function(k,v){
    				var _val = v;
    				if(v == null){
    					_val = "";	
    				}
    				$("#stuParentDetail_form #stuParent_"+k).text(_val);
    			});
    		});
    	}
      $.ajaxSetup({async:true});
  });