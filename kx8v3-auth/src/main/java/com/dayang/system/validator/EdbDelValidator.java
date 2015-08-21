package com.dayang.system.validator;

import com.dayang.commons.pojo.AjaxRetPojo;
import com.dayang.commons.util.CommonUtil;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.validate.Validator;

import java.util.List;

/**
 * 类描述：删除教育局之前先判断是否有下属机构
 * <pre>
 * -------------History------------------
 *   DATE                 AUTHOR         VERSION        DESCRIPTION
 *  2015年05月20日            刘建宇      V01.00.001		   新增内容
 * </pre>
 *
 * @author <a href="mailto:liujy@dayanginfo.com">刘建宇</a>
 */
public class EdbDelValidator extends Validator {
    
    public static final String key = "hasChild";
    
    @Override
    protected void validate(Controller c) {
        c.removeAttr(key);
        String edbId = c.getPara("id");
        List<Record> list  = Db.find("select * from base_organization t where t.orgId = ?", edbId);
        if (!CommonUtil.isEmptyCollection(list)){
            addError(key,"该教育局有下属机构,不允许删除");
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
