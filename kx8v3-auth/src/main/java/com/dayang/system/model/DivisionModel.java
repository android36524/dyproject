package com.dayang.system.model;



import com.jfinal.plugin.activerecord.Model;

import java.util.List;

/**
 * 类描述：行政区划表Model
 * <pre>
 * -------------History------------------
 *   DATE                 AUTHOR         VERSION        DESCRIPTION
 *  2015年05月18日            刘建宇      V01.00.001		   新增内容
 * </pre>
 *
 * @author <a href="mailto:liujy@dayanginfo.com">刘建宇</a>
 */
public class DivisionModel extends Model<DivisionModel> {
    public static final DivisionModel dao = new DivisionModel();

    /**
     * 获取所有省 
     * @return List
     */
    public List<DivisionModel> provinceList(){
        return DivisionModel.dao.find("select * from base_division t where t.bmid is null");
    }

    /**
     * 根据bmId获取市或者区
     * @param bmId
     * @return List
     */
    public List<DivisionModel> findCityOrAreaByBmId(String bmId){
        return DivisionModel.dao.find("select * from base_division t where t.bmid = ?",bmId);
    }
}
