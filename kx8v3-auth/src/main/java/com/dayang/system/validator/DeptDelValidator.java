package com.dayang.system.validator;

import com.dayang.commons.pojo.AjaxRetPojo;
import com.dayang.commons.util.CommonUtil;
import com.dayang.system.model.DeptModel;
import com.dayang.system.model.EmpeModel;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.validate.Validator;

import java.util.List;

/**
 * 类描述：删除教育局/学校部门之前先判断是否有下属部门
 * <pre>
 * -------------History------------------
 *   DATE                 AUTHOR         VERSION        DESCRIPTION
 *  2015年05月29日            何意      V01.00.001		   新增内容
 * </pre>
 *
 * @author <a href="mailto:liujy@dayanginfo.com">何意 </a>
 */
public class DeptDelValidator extends Validator {
    
    public static final String key = "hasChild";
    
    @Override
    protected void validate(Controller c) {
        c.removeAttr(key);
        String deptId = c.getPara("id");
        DeptModel deptModel = DeptModel.dao.findFirst("select count(1) as subcount from base_department  where pid=?",deptId);
        List<EmpeModel> empeList =EmpeModel.dao.find("select * from base_employee t where t.deptId = ?",deptId);
        if(deptModel != null && deptModel.getLong("subcount") > 0){
            addError(key, "当前部门下存在子部门，不允许删除！");
        }
        else if (empeList.size()>0){
            addError(key, "当前部门下已存在员工，不允许删除！");
        }
    }

    @Override
    protected void handleError(Controller c) {
        AjaxRetPojo ajaxRetPojo = AjaxRetPojo.newInstance();
        ajaxRetPojo.setCode(AjaxRetPojo.CODE_FAIL);
        if(c.getAttr(key) != null){
            ajaxRetPojo.setMsg(c.getAttr(key).toString());
        }
        c.renderJson(ajaxRetPojo);
    }
}
