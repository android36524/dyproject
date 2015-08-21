package com.dayang.commons.controller;

import com.dayang.system.model.OrgModel;

import java.util.List;

/**
 * 类描述：教育局树Controller
 * <pre>
 * -------------History------------------
 *   DATE                 AUTHOR         VERSION        DESCRIPTION
 *  2015年05月21日            刘建宇      V01.00.001		   新增内容
 * </pre>
 *
 * @author <a href="mailto:liujy@dayanginfo.com">刘建宇</a>
 */
public class OrgTreeController extends AdminBaseController{

    public void index(){
        renderJsp("/admin/common/orgTree/index.jsp");
    }


    /**
     * 获取机构树
     */
    public void list(){
        String provinceId = getPara("provinceCode","");
        String cityId = getPara("cityCode","");
        String areaId = getPara("areaCode","");
        String orgName = getPara("searchName","");
        String treeFlag = getPara("treeFlag");
        boolean isDataAuth = getParaToBoolean("isDataAuth",true);
        List<OrgModel> edbModelList = OrgModel.dao.findOrgTree(provinceId, cityId, areaId,
                orgName,treeFlag,isDataAuth);
        renderJson(edbModelList);
    }
}
