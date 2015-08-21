package com.dayang.system.controller;

import com.dayang.commons.controller.AdminBaseController;
import com.dayang.system.model.DivisionModel;

/**
 * 类描述：行政区划Controller
 * <pre>
 * -------------History------------------
 *   DATE                 AUTHOR         VERSION        DESCRIPTION
 *  2015年05月18日            刘建宇      V01.00.001		   新增内容
 * </pre>
 *
 * @author <a href="mailto:liujy@dayanginfo.com">刘建宇</a>
 */
public class DivisionController extends AdminBaseController{

    /**
     * 获取所有省 
     */
    @SuppressWarnings("unused")
    public void provinceList(){
        renderJson(DivisionModel.dao.provinceList());    
    }

    /**
     * 获取市或者区 
     */
    @SuppressWarnings("unused")
    public void findCityOrAreaByBmId(){
        String bmId = getPara("bmId");
        renderJson(DivisionModel.dao.findCityOrAreaByBmId(bmId));
    }
}
