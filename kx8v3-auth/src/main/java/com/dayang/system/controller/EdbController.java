package com.dayang.system.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import com.dayang.cas.pojo.AccountPojo;
import com.dayang.cas.util.LoginInfoUtil;
import com.dayang.commons.annotation.FuncActionAnnotation;
import com.dayang.commons.controller.AdminBaseController;
import com.dayang.commons.controller.CURDController;
import com.dayang.commons.enums.OrgFlag;
import com.dayang.commons.enums.Status;
import com.dayang.commons.pojo.AjaxRetPojo;
import com.dayang.commons.pojo.EdbEmplImportPojo;
import com.dayang.commons.pojo.ExcelImportErrorPojo;
import com.dayang.commons.pojo.ImportPojo;
import com.dayang.commons.pojo.JQGridPagePojo;
import com.dayang.commons.thread.ExecuteSqlThread;
import com.dayang.commons.thread.ResThreadPoolUtil;
import com.dayang.commons.util.AccountGenerator;
import com.dayang.commons.util.CommonStaticData;
import com.dayang.commons.util.CommonUtil;
import com.dayang.commons.util.DaYangCommonUtil;
import com.dayang.commons.util.DaYangStaticData;
import com.dayang.commons.util.ExcelUtil;
import com.dayang.commons.util.IDKeyUtil;
import com.dayang.commons.util.StaticData;
import com.dayang.conf.interceptor.AuthInterceptor;
import com.dayang.conf.interceptor.FuncActionInterceptor;
import com.dayang.system.model.HisorgModel;
import com.dayang.system.model.LoginfoModel;
import com.dayang.system.model.OrgModel;
import com.dayang.system.validator.EdbDelValidator;
import com.jfinal.aop.Before;
import com.jfinal.kit.PathKit;
import com.jfinal.plugin.activerecord.Page;

/**
 * 类描述：教育局基础信息管理Controller
 * <pre>
 * -------------History------------------
 *   DATE                 AUTHOR         VERSION        DESCRIPTION
 *  2015年05月18日            刘建宇      V01.00.001		   新增内容
 * </pre>
 *
 * @author <a href="mailto:liujy@dayanginfo.com">刘建宇</a>
 */
public class EdbController extends AdminBaseController implements CURDController{


    @Override
    public void toAdd() {
    	this.setAttr("roleName", StaticData.roleType.edbAdminName);
    	this.setAttr("account", AccountGenerator.getAccount());
    	renderJsp("edb-modify.jsp");
    }
    

    /**
     * 教育局基础信息管理首页 
     */
    @Before(AuthInterceptor.class)
    public void index(){
        this.setAttr("fisrtMenu", "教育局管理");
        this.setAttr("secendMenu", "教育局基础信息管理");
        renderJsp("index.jsp");
    }

    /**
     * 查询所有教育局 
     */
    public void listPage(){
        int pageNumber = getParaToInt("page");
        int pageSize = getParaToInt("rows", DaYangStaticData.PAGE_ROWS);
        long orgId = getParaToLong("orgId");
        String status = getPara("status","");
        Map<String,Object> paramsMap = new HashMap<>();
        paramsMap.put("status",status);
        Page<OrgModel> edbModelPage = OrgModel.dao.findByPage(pageNumber,pageSize,orgId,paramsMap);
        renderJson(JQGridPagePojo.parsePageData(edbModelPage));
    }

    /**
     * 新增或者修改教育局信息 
     */
    @FuncActionAnnotation(noIdAction="/admin/edb/add",idAction="/admin/edb/modify",idName = "edb.id")
    @Before(FuncActionInterceptor.class)
    public void add(){
        OrgModel edbModel = getModel(OrgModel.class, "edb");
        Long edbId = edbModel.getLong("id");
        if (edbId == null){//新增
            String account = getPara("account");
            long edb_Id = IDKeyUtil.getIDKey();
            long accountId = IDKeyUtil.getIDKey();
            AccountPojo accountPojo = new AccountPojo(accountId,account,CommonStaticData.AccountDicType.AdminType,"",edb_Id);
            /**
             * wangchong update by 2015-08-03 新增加入调用sns远程接口同步通讯录信息
             */
            CommonUtil.saveAccountPoJo(accountPojo, StaticData.roleType.edbAdmin);
            edbModel.setEdbCode();
            edbModel.set("id",edb_Id).set("status", Status.ENABLE.getValueStr())
                    .set("orgFlag",OrgFlag.EDBFLAG.getValueStr())
                    .set("creator",LoginInfoUtil.getAccountInfo().getId())
                    .set("createTime", new Date())
                    .set("accountId",accountId );
            edbModel.save();
        }else {//修改
            edbModel.update();
        }
        renderJson(AjaxRetPojo.newInstance());
    }

    /**
     * 校验教育局名称是否唯一 
     */
    public void validateEdbName(){
        boolean flag = true;
        String edbName = getPara("edbName");
        long edbId = getParaToLong("edbId",0l);
        OrgModel edbModel = OrgModel.dao.validateEdbName(edbName,edbId);
        if (edbModel != null){
            flag = false;
        }
        renderJson(flag);
    }

    /**
     * 根据所选行政区划查询上级教育局 
     */
    @SuppressWarnings("unused")
    public  void queryParentOrg(){
        String flag = getPara("flag");
        String code = getPara("code");
        String itSelf =getPara("itSelf");
        String idid = getPara("idid");
        int pageNumber = getParaToInt("page");
        int pageSize = getParaToInt("rows", DaYangStaticData.PAGE_ROWS);
        Page<OrgModel> edbModelPage = OrgModel.dao.findByPage(pageNumber,pageSize,flag,code,idid);
        renderJson(JQGridPagePojo.parsePageData(edbModelPage));
    }

    /**
     * 根据id查询教育局基本信息 
     */
    public void queryById(){
        OrgModel edbModel = OrgModel.dao.findByEdbId(getPara("id"));
        renderJson(edbModel);
    }

    /**
     * 根据id启用或者禁用教育局 
     */
    @FuncActionAnnotation(diyAction="flag^disabled:/admin/edb/disable,enabled:/admin/edb/enable")
    @Before(FuncActionInterceptor.class)
    public void updateStatus(){
        OrgModel edbModel = OrgModel.dao.findById(getPara("id"));
        String flag = getPara("flag");
        if ("disabled".equals(flag)){
            edbModel.set("status",Status.DISABLED.getValueStr()).update();
        }else {
            edbModel.set("status",Status.ENABLE.getValueStr()).update();
        }
        renderJson(AjaxRetPojo.newInstance());
    }

    /**
     * 根据id删除教育局 
     */
    @Before({EdbDelValidator.class,FuncActionInterceptor.class})
    public void del(){
        String edbId = getPara("id");
        OrgModel edbModel = OrgModel.dao.findById(edbId);
        String accountId = String.valueOf(edbModel.get("accountId"));
        HisorgModel hisorgModel = new HisorgModel();
        if (edbModel != null){
            Set<Map.Entry<String,Object>> set = edbModel.getAttrsEntrySet();
            for (Map.Entry<String,Object> obj : set){
                hisorgModel.set(obj.getKey(),obj.getValue());
            }
            hisorgModel.save();
            edbModel.delete();
            DaYangCommonUtil.deleteAccountById(accountId);
        }
        renderJson(AjaxRetPojo.newInstance());
    }

}
