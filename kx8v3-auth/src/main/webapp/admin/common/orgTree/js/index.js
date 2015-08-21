//教育局树对象
var _edbTreeObj;
var _treeFlag;
var _paramsObj;
var _tree = {
        initProvince : function(provinceId){
            $.post(path+'/admin/division/provinceList',function(retList){
                if(retList){
                    var selectObj = $("#tree_provinceId");
                    for (var i = 0; i < retList.length; i++) {
                        var divisionObj = retList[i];
                        selectObj.append("<option value='" + divisionObj.BM + "'>" + divisionObj.MC + "</option>");
                    }
                }
            });
        },
        initCity : function (provinceId){
            _tree.removeSlect("tree_cityId");
            _tree.removeSlect("tree_areaId");
            $.post(path+'/admin/division/findCityOrAreaByBmId',{bmId:provinceId},function(retList){
                if(retList){
                    var selectObj = $("#tree_cityId");
                    for (var i = 0; i < retList.length; i++) {
                        var divisionObj = retList[i];
                        selectObj.append("<option value='" + divisionObj.BM + "'>" + divisionObj.MC + "</option>");
                    }
                }
            });
        },
        initArea : function(cityId){
            _tree.removeSlect("tree_areaId");
            $.post(path+'/admin/division/findCityOrAreaByBmId',{bmId:cityId},function(retList){
                if(retList){
                    var selectObj = $("#tree_areaId");
                    for (var i = 0; i < retList.length; i++) {
                        var divisionObj = retList[i];
                        selectObj.append("<option value='" + divisionObj.BM + "'>" + divisionObj.MC + "</option>");
                    }
                }
            });
        },
        //删除select item
        removeSlect : function(selectId){
            var selectObject = $("#" + selectId + " option");
            selectObject.each(function(){
                if( $(this).val() !== ""){
                    $(this).remove();
                }
            });
        },
        //初始化教育局树
        initEdbTree : function(treeFlag,orgObj,clickNodeCallBack,searchName){
            $.ajaxSetup({async:false});
            _paramsObj = orgObj;
            _treeFlag = treeFlag;
            var params = {treeFlag:treeFlag,searchName:searchName};
            for(i in orgObj){
                params[i] = orgObj[i];
            }
            treeSetting.callback.onClick = clickNodeCallBack;
            $.post(path + "/admin/orgTree/list",params,function(ret){
                _edbTreeObj = $.fn.zTree.init($("#edbTree"),treeSetting,ret);
            });
            _edbTreeObj.expandAll(true);
            _tree.initProvince();
            _tree.initCity(_paramsObj.provinceCode);
            _tree.initArea(_paramsObj.cityCode);
            _tree.setSelectStatus(_paramsObj);
            var nodes = _edbTreeObj.transformToArray(_edbTreeObj.getNodes());
            var _lastNode = undefined;
            $.each(nodes, function (index,ele) {
                if(!ele.isParent){
                    _lastNode = ele;
                    _edbTreeObj.selectNode(ele);
                    return false;
                }
            });
            return _lastNode;
            $.ajaxSetup({async:true});
        },
        //判断节点是否为教育局节点
        isEdbNode : function(node){
            var result = true;
            if(node.flag == "1"){
                result = false;
            }
            return result;
        },
        //判断节点是否为学校节点
        isSchoolNode : function(node){
            return !_tree.isEdbNode(node);
        },
        //获取选中教育局节点对象
        getSelectNodeObj : function(){
            var nodes = _edbTreeObj.getSelectedNodes();
            return nodes[0]
        },
        setSelectStatus : function(_paramsObj){
            if(_paramsObj.provinceCode != null && _paramsObj.provinceCode !== ""){
                $("#tree_provinceId").val(_paramsObj.provinceCode);
                $("#tree_provinceId").attr("disabled","disabled");
            }
            if(_paramsObj.cityCode != null && _paramsObj.cityCode !== ""){
                $("#tree_cityId").val(_paramsObj.cityCode);
                $("#tree_cityId").attr("disabled","disabled");
            }
            if(_paramsObj.areaCode != null && _paramsObj.areaCode !== ""){
                $("#tree_areaId").val(_paramsObj.areaCode);
                $("#tree_areaId").attr("disabled","disabled");
            }
        }
}
//教育局树属性设置
var treeSetting = {
    data: {
        simpleData: {
            enable: true
        }
    },
    callback : {
        onClick : _tree.getSelectNodeObj
    }
};
$(function () {
    var orgObj = {id:100055,provinceCode:43,cityCode:4301,areaCode:'',orgFlag:'0'};
    //初始化省
    //_tree.initProvince();

    //初始化教育局树
    //_tree.initEdbTree('0',orgObj);

    //省改变事件
    $("#tree_provinceId").change(function () {
        var provinceId = $(this).val();
        if(provinceId != "") {
            _tree.initCity(provinceId);
        }else{
    		$("#tree_cityId option[index!='0']").remove();
			$("#tree_areaId option[index!='0']").remove();
			$("#tree_cityId").prepend("<option selected='selected' value=''>--请选择--</option>");
			$("#tree_areaId").prepend("<option selected='selected' value=''>--请选择--</option>");
        }
    });

    //市改变事件
    $("#tree_cityId").change(function () {
        var cityId = $(this).val();
        if(cityId != "") {
            _tree.initArea(cityId);
        }else{
			$("#tree_areaId option[index!='0']").remove();
			$("#tree_areaId").prepend("<option selected='selected' value=''>--请选择--</option>");
        }
    });

    //查询按钮单击事件
    $("#tree_find_btn").on(ace.click_event, function() {
        var provinceId = $("#tree_provinceId").val();
        var cityId = $("#tree_cityId").val();
        var areaId = $("#tree_areaId").val();
        var searchName = $("#searchName").val();
        _paramsObj.provinceCode = provinceId;
        _paramsObj.cityCode = cityId;
        _paramsObj.areaCode = areaId;
        _paramsObj.treeFlag = _treeFlag;
        _paramsObj.searchName = searchName;
        $.ajaxSetup({async:false});
        $.post(path + "/admin/orgTree/list",_paramsObj,function(ret){
            _edbTreeObj = $.fn.zTree.init($("#edbTree"),treeSetting,ret);
        });
        _edbTreeObj.expandAll(true);
        $.ajaxSetup({async:true});
    });
});
