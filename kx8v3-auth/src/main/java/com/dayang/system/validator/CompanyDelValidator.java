package com.dayang.system.validator;

import java.util.List;

import com.dayang.commons.pojo.AjaxRetPojo;
import com.dayang.commons.util.CommonUtil;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.validate.Validator;

public class CompanyDelValidator extends Validator{
public static final String key = "hasChild";
    
    @Override
    protected void validate(Controller c) {
        c.removeAttr(key);
        String edbId = c.getPara("id");
        List<Record> list  = Db.find("select * from base_organization t where t.orgId = ?", edbId);
        if (!CommonUtil.isEmptyCollection(list)){
            addError(key,"该公司有下属机构,不允许删除");
        }
        List<Record> listDept  = Db.find("select * from base_department t where t.orgId = ?", edbId);
        if (!CommonUtil.isEmptyCollection(listDept)){
            addError(key,"该公司有部门,不允许删除");
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
