$(function(){		
	function initProv(){
		$.post(path+'/admin/division/provinceList',function(retList){
			if(retList){
				var selectObj = $("#o_provinceId");
				for (var i = 0; i < retList.length; i++) {
					var divisionObj = retList[i];
					selectObj.append("<option value='" + divisionObj.BM + "'>" + divisionObj.MC + "</option>");
				}
				$("#o_provinceId").change();
			}
		});
	}
	function initCity(provinceId){			
		$.post(path+'/admin/division/findCityOrAreaByBmId',{bmId:provinceId},function(retList){
			if(retList){
				var selectObj = $("#o_cityId");	            	
				for (var i = 0; i < retList.length; i++) {
					var divisionObj = retList[i];
					selectObj.append("<option value='" + divisionObj.BM + "'>" + divisionObj.MC + "</option>");
				}
			}
			$("#o_cityId").change();
		});
	}
	function initArea(cityId){	    	
		$.post(path+'/admin/division/findCityOrAreaByBmId',{bmId:cityId},function(retList){
			if(retList){
				var selectObj = $("#o_areaId");		           	
				for (var i = 0; i < retList.length; i++) {
					var divisionObj = retList[i];
					selectObj.append("<option value='" + divisionObj.BM + "'>" + divisionObj.MC + "</option>");
				}
			}
			$("#o_areaId").change();
		});
	}

	function initSchool(){	    	
		var params={};	    	
		params["provinceCode"]=$("#o_provinceId").val();
		params["cityCode"]=$("#o_cityId").val();
		params["areaCode"]=$("#o_areaId").val();
		params["treeFlag"]=1;
		params["isDataAuth"]= false;
		$.post(path + "/admin/orgTree/list",params,function(retList){                
			if(retList){
				$("#o_school").empty();	
				$("#o_school").append("<option value=''>--请选择--</option>");
				var selectObj = $("#o_school");		           
				for (var i = 0; i < retList.length; i++) {
					var divisionObj = retList[i];
					if(divisionObj.flag==1){
						selectObj.append("<option value='" + divisionObj.id + "'>" + divisionObj.name + "</option>");
					}
				}
			}
		});
	}
	//省改变事件
	$("#o_provinceId").change(function () {
		var provinceId = $(this).val();
		$("#o_cityId").empty();
		$("#o_areaId").empty();	
		$("#o_school").empty();	
		$("#o_cityId").append("<option value=''>--请选择--</option>");
		$("#o_areaId").append("<option value=''>--请选择--</option>");
		$("#o_school").append("<option value=''>--请选择--</option>");
		if(provinceId != "") {
			initCity(provinceId);
		}
	});

	//市改变事件
	$("#o_cityId").change(function () {
		var cityId = $(this).val();
		$("#o_areaId").empty();
		$("#o_school").empty();	
		$("#o_areaId").append("<option value=''>--请选择--</option>");
		$("#o_school").append("<option value=''>--请选择--</option>");
		if(cityId != "") {
			initArea(cityId);
			initSchool();
		}	        
	});

	//市改变事件
	$("#o_areaId").change(function () {	    	
		var areaId = $(this).val();	
		$("#o_school").empty();	
		$("#o_school").append("<option value=''>--请选择--</option>");
		if(areaId!="" || $("#o_cityId").val()!=""){
			initSchool();
		}
	});
	$("#o_school").change(function () {
		var schName = $(this).find("option:selected").text();	      
		$("#toSchName").val(schName); 
	});
	$("#schId").val(parent._schId);	
	initProv();	   
});
