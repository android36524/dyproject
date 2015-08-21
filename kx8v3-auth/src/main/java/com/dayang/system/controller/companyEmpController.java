package com.dayang.system.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.dayang.commons.annotation.FuncActionAnnotation;
import com.dayang.commons.controller.AdminBaseController;
import com.dayang.commons.controller.CURDController;
import com.dayang.commons.enums.EmpeStatus;
import com.dayang.commons.enums.MarryStatus;
import com.dayang.commons.enums.Sex;
import com.dayang.commons.pojo.AjaxRetPojo;
import com.dayang.commons.pojo.JQGridPagePojo;
import com.dayang.commons.util.CommonUtil;
import com.dayang.commons.util.DaYangCommonUtil;
import com.dayang.commons.util.DaYangStaticData;
import com.dayang.commons.util.DictionaryUtil;
import com.dayang.commons.util.EnumAndDicDefine;
import com.dayang.commons.util.EnumUtil;
import com.dayang.commons.util.IDKeyUtil;
import com.dayang.commons.util.StaticData;
import com.dayang.commons.util.CommonStaticData.OrgTreeNodeType;
import com.dayang.conf.interceptor.AuthInterceptor;
import com.dayang.conf.interceptor.FuncActionInterceptor;
import com.dayang.system.model.EmpeModel;
import com.dayang.system.model.HisEmpeModel;
import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.Page;

public class companyEmpController extends AdminBaseController implements CURDController{

	/**
	 * 公司员工管理
	 */
	@Override
	@Before(AuthInterceptor.class)
	public void index() {
		renderJsp("index.jsp");
	}
	
	/**
	 * 查询公司或者部门下面的所有员工
	 */
	public void empListPage(){
		int pageNumber = getParaToInt("page");
        int pageSize = getParaToInt("rows", DaYangStaticData.PAGE_ROWS);
        long deptId = getParaToLong("deptId", 0l);
        long orgId = getParaToLong("orgId", 0l);
        int status = getParaToInt("status", 0);
        String name = getPara("name");
        String mobile = getPara("mobile");
        String SpecialFlage = getPara("SpecialFlage");
        Map<String,Object> map= new HashMap<String,Object>();
        map.put("deptId",deptId);
        map.put("orgId",orgId);
        map.put("status",status);
        map.put("name",name);
        map.put("mobile",mobile);
        map.put("orgType",OrgTreeNodeType.COMPANYFLAG);
        //map.put("empType",EmpeType.COMPANYEMP.getValueStr());//此处是查询公司员工
        Page p = EmpeModel.dao.findEmpeModelPage(pageNumber, pageSize, map);
        CommonUtil.setShowValue2List(p, EnumAndDicDefine.EMPE_DEFINETABLE);
        renderJson(JQGridPagePojo.parsePageData(p));
	}
	
	/**
	 * 根据条件查询员工信息
	 */
	public void queryByConditions(){
		int pageNumber = getParaToInt("page");
        int pageSize = getParaToInt("rows", DaYangStaticData.PAGE_ROWS);
        long orgId = getParaToLong("orgId", 0l);
        String phoneNo = getPara("phoneNo");
        String accountId = getPara("accountId");
        String nameOrPy = getPara("nameOrPy");
        String empType = getPara("empType");
        String company_id=getPara("company_id");
        Map<String,Object> map= new HashMap<String,Object>();
        map.put("orgId", orgId);
        
        
        map.put("empType",empType);
        map.put("orgType",OrgTreeNodeType.COMPANYFLAG);
		Page p = EmpeModel.dao.queryByConditions(pageNumber, pageSize,nameOrPy,phoneNo,accountId,company_id,map);
        CommonUtil.setShowValue2List(p, EnumAndDicDefine.EMPE_DEFINETABLE);
        renderJson(JQGridPagePojo.parsePageData(p));
	}

	/**
	 * 跳转到新增公司员工页面
	 */
	@Override
	public void toAdd() {
		loadPublic();
		long orgId = getParaToLong("orgId",0l);
		String selectOrgId = getPara("selectOrgId");
		String name = getPara("name");
        this.setAttr("orgId",orgId);
        this.setAttr("selectOrgId", selectOrgId);
        this.setAttr("name", name);
        renderJsp("companyEmp-modify.jsp");
	}

	/**
	 * 新增或者修改公司员工信息
	 */
	@Override
	@FuncActionAnnotation(noIdAction="/admin/companyEmp/add",idAction="/admin/companyEmp/modify",idName="companyEmp.id")
	@Before(FuncActionInterceptor.class)
	public void add() {
		Long flag = getParaToLong("flageId");
		EmpeModel dempeModel = getModel(EmpeModel.class, "companyEmp");
        if(flag==1){
            dempeModel.update();
        }else {
            dempeModel.set("id", IDKeyUtil.getIDKey());
            dempeModel.set("status", EmpeStatus.ENABLE.getValueStr());
            //dempeModel.set("empType",EmpeType.COMPANYEMP.getValueStr());//公司员工默认为3
            dempeModel.set("creator", getLoginUserId());
            dempeModel.set("createTime",new Date());
            dempeModel.save();
        }
        renderJson(AjaxRetPojo.newInstance());
	}

	/**
	 * 根据公司员工ID删除员工，并保存到历史表
	 */
	@Override
	@Before(FuncActionInterceptor.class)
	public void del(){
        String companyEmpId = getPara("id");
        EmpeModel empeModel = EmpeModel.dao.findById(companyEmpId);
        String accountId = String.valueOf(empeModel.get("accountId"));
        HisEmpeModel hisEmpModel = new HisEmpeModel();
        if (empeModel != null){
            Set<Map.Entry<String,Object>> set = empeModel.getAttrsEntrySet();
            for (Map.Entry<String,Object> obj : set){
            	hisEmpModel.set(obj.getKey(),obj.getValue());
            }
            hisEmpModel.save();
            empeModel.delete();
            DaYangCommonUtil.deleteAccountById(accountId);
        }
        renderJson(AjaxRetPojo.newInstance());
    }
	
	/**
     * 页面公共需要加载的数据
     */
    public void loadPublic(){
        this.setAttr("nationList", DictionaryUtil.getDicListByDicType(StaticData.NATION_dictionaryType));//民族
        this.setAttr("politicalList", DictionaryUtil.getDicListByDicType(StaticData.POLITICAL_dictionaryType));//政治面貌
        this.setAttr("educationList", DictionaryUtil.getDicListByDicType(StaticData.EDUCATION_dictionaryType));//学历
        this.setAttr("jobList", DictionaryUtil.getDicListByDicType(StaticData.JOB_dictionaryType));//职务
        this.setAttr("marryLists", EnumUtil.toEnumList(MarryStatus.ISMARRY));
        this.setAttr("manName", Sex.MAN.getName()); //性别
		this.setAttr("manVal", Sex.MAN.getValueStr());
		this.setAttr("woManName", Sex.WOMAN.getName());
		this.setAttr("woManVal", Sex.WOMAN.getValueStr());
        long id = getParaToLong("id",0l);
        this.setAttr("id", id);
    }
    
    /**
     * 根据公司员工ID查询员工信息
     */
    public void queryById(){
    	long empeId = getParaToLong("id");
        EmpeModel empeModel = EmpeModel.dao.findCompanyByEmpeId(empeId);
        renderJson(empeModel);
    }

}
