package com.dayang.system.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.dayang.cas.pojo.AccountPojo;
import com.dayang.commons.annotation.FuncActionAnnotation;
import com.dayang.commons.controller.AdminBaseController;
import com.dayang.commons.enums.OrgFlag;
import com.dayang.commons.enums.Status;
import com.dayang.commons.pojo.AjaxRetPojo;
import com.dayang.commons.pojo.JQGridPagePojo;
import com.dayang.commons.util.CommonStaticData;
import com.dayang.commons.util.CommonUtil;
import com.dayang.commons.util.DaYangCommonUtil;
import com.dayang.commons.util.DaYangStaticData;
import com.dayang.commons.util.IDKeyUtil;
import com.dayang.commons.util.StaticData;
import com.dayang.conf.interceptor.AuthInterceptor;
import com.dayang.conf.interceptor.FuncActionInterceptor;
import com.dayang.system.model.HisorgModel;
import com.dayang.system.model.OrgModel;
import com.dayang.system.validator.CompanyDelValidator;
import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.Page;

/**
 * 
 * 类描述：公司基础信息管理
 * <pre>
 * -------------History------------------
 *   DATE       AUTHOR       VERSION        DESCRIPTION
 *  2015-6-23      李勇                V01.00.001		      新增内容   
 * </pre>
 * 
 * @author <a href="mailto:liyong@dayanginfo.com">李勇</a>
 */
public class CompanyController extends AdminBaseController{
	
	/**
	 * 公司管理 组织机构管理
	 */
	@Before(AuthInterceptor.class)
	public void index(){
		this.setAttr("fisrtMenu", "公司管理");
		this.setAttr("secendMenu", "公司基础信息管理");
		renderJsp("index.jsp");
	}
	
	/**
	 * 查询所有公司
	 */
	@SuppressWarnings("unused")
	public void listPage(){
		int pageNumber = getParaToInt("page");
		int pageSize = getParaToInt("rows", DaYangStaticData.PAGE_ROWS);
		String orgFlag = OrgFlag.COMPANYFLAG.getValueStr();
		//long orgId = getParaToLong("orgId");
		String status = getPara("status","");
        Map<String,Object> paramsMap = new HashMap<>();
        paramsMap.put("status",status);
		//Page<OrgModel> edbModelPage = OrgModel.dao.findCompanyByPage(pageNumber,pageSize,orgId,orgFlag,paramsMap);
		Page<OrgModel> edbModelPage = OrgModel.dao.findCompanyByPage(pageNumber,pageSize,orgFlag,paramsMap);
		renderJson(JQGridPagePojo.parsePageData(edbModelPage));
	}
	
	/**
	 * 新增公司或者修改公司
	 */
	@FuncActionAnnotation(noIdAction="/admin/companyDetail/add",idAction="/admin/companyDetail/modify",idName="company.id")
	@Before(FuncActionInterceptor.class)
	public void toAdd(){
		
		String account = getPara("account.account");
    	long edb_Id = IDKeyUtil.getIDKey();
    	long accountId = IDKeyUtil.getIDKey();
    	
    	
		//companyModel.setEdbCode();
        Long flageId = getParaToLong("flageId");
        OrgModel companyModel = getModel(OrgModel.class, "company");
        if (flageId == 0){//新增
        	
        	AccountPojo accountPojo = new AccountPojo(accountId,account,CommonStaticData.AccountDicType.AdminType,"",edb_Id);
        	/**
        	 * wangchong update by 2015-08-03 新增加入调用sns远程接口同步通讯录信息
        	 */
        	CommonUtil.saveAccountPoJo(accountPojo, StaticData.roleType.companyAdmin);
        	companyModel.set("id",edb_Id).set("status", Status.ENABLE.getValueStr())
                    //.set("orgFlag",OrgFlag.EDBFLAG.getValueStr())  后台直接传枚举类型2过来
        			.set("orgCode", "")
                    .set("creator",getLoginUserId())//获取用户信息
                    .set("createTime",new Date()).set("accountId", accountId);
        	companyModel.save();
        }else {//修改
        	companyModel.update();
        }
        renderJson(AjaxRetPojo.newInstance());
	}
	
	
	/**
	 * 根据ID删除公司
	 */
	//@FuncActionAnnotation(idAction="/admin/companyDetail/del",idName="company.id")
	@Before({CompanyDelValidator.class, FuncActionInterceptor.class})
	public void del(){
		 String companyId = getPara("id");
	        OrgModel companyModel = OrgModel.dao.findById(companyId);
	        String accountId = String.valueOf(companyModel.get("accountId"));
	        HisorgModel hisorgModel = new HisorgModel();
	        if (companyModel != null){
	            Set<Map.Entry<String,Object>> set = companyModel.getAttrsEntrySet();
	            for (Map.Entry<String,Object> obj : set){
	                hisorgModel.set(obj.getKey(),obj.getValue());
	            }
	            hisorgModel.save();
	            companyModel.delete();
	            DaYangCommonUtil.deleteAccountById(accountId);
	        }
	        renderJson(AjaxRetPojo.newInstance());
	    }
	
	/**
	 * 根据ID查询公司基本信息
	 * 因为SQL与教育局一样的，同样findByEdbId此方法
	 */
    @SuppressWarnings("unused")
    public void queryById(){
        OrgModel companyModel = OrgModel.dao.findByEdbId(getPara("id"));
        renderJson(companyModel);
    }
    
    /**
     * 根据公司名称模糊查询
     */
    public void queryByCompanyName(){
    	int pageNumber = getParaToInt("page");
		int pageSize = getParaToInt("rows", DaYangStaticData.PAGE_ROWS);
		String orgFlag = OrgFlag.COMPANYFLAG.getValueStr();
		String companyName = getPara("companyName");
		Page<OrgModel> edbModelPage = OrgModel.dao.queryCompanyByName(pageNumber,pageSize,companyName,orgFlag);
		renderJson(JQGridPagePojo.parsePageData(edbModelPage));
    }
    
    /**
     * 校验公司名称是否唯一 
     */
    @SuppressWarnings("unused")
    public void validateCompanyName(){
        boolean flag = true;
        String companyName = getPara("companyName");
        long companyId = getParaToLong("companyId",0l);
        String comFlag = OrgFlag.COMPANYFLAG.getValueStr();
        OrgModel companyModel = OrgModel.dao.validateCompanyName(companyName,companyId,comFlag);
        if (companyModel != null){
            flag = false;
        }
        renderJson(flag);
    }
    
    /**
     * 根据本级公司ID查询所有上级公司
     */
    @SuppressWarnings("unused")
    public  void queryParentOrg(){
        String itSelf =getPara("itSelf");
        Long companyOrgId =getParaToLong("companyOrgId");
        String provinceId =getPara("provinceId");
        int pageNumber = getParaToInt("page");
        int pageSize = getParaToInt("rows", DaYangStaticData.PAGE_ROWS);
        String comFlag = OrgFlag.COMPANYFLAG.getValueStr();
        Page<OrgModel> edbModelPage = OrgModel.dao.findFNodeByItSelfPage(pageNumber,pageSize,comFlag,itSelf,companyOrgId,provinceId);
        renderJson(JQGridPagePojo.parsePageData(edbModelPage));
    }
    
    /**
     * 根据省市区来查询公司的下拉框
     */
    public void findCompanyByProvinceOr(){
    	String bmId = getPara("bmId");
    	String cityCode = getPara("cityCode");
    	String areaCode = getPara("areaCode");
    	renderJson(OrgModel.dao.findCompanyByProvinceOrCityOrArea(bmId,cityCode,areaCode));
    }
   
}
