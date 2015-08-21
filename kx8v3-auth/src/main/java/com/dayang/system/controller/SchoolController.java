package com.dayang.system.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;

import com.dayang.cas.pojo.AccountPojo;
import com.dayang.commons.annotation.FuncActionAnnotation;
import com.dayang.commons.controller.AdminBaseController;
import com.dayang.commons.enums.EnumAll;
import com.dayang.commons.enums.OrgFlag;
import com.dayang.commons.enums.SchoolType;
import com.dayang.commons.enums.Status;
import com.dayang.commons.pojo.AjaxRetPojo;
import com.dayang.commons.pojo.JQGridPagePojo;
import com.dayang.commons.util.AccountGenerator;
import com.dayang.commons.util.CommonStaticData;
import com.dayang.commons.util.CommonUtil;
import com.dayang.commons.util.DaYangCommonUtil;
import com.dayang.commons.util.DaYangStaticData;
import com.dayang.commons.util.DictionaryUtil;
import com.dayang.commons.util.EnumAndDicDefine;
import com.dayang.commons.util.EnumUtil;
import com.dayang.commons.util.IDKeyUtil;
import com.dayang.commons.util.StaticData;
import com.dayang.conf.interceptor.AuthInterceptor;
import com.dayang.conf.interceptor.FuncActionInterceptor;
import com.dayang.system.model.DivisionModel;
import com.dayang.system.model.OrgModel;
import com.dayang.system.model.SchoolExtModel;
import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.render.ContentType;

/**
 * 类描述：学校基础信息管理Controller
 * <pre>
 * -------------History------------------
 *   DATE                 AUTHOR         VERSION        DESCRIPTION
 *  2015年05月20日           张维      V01.00.001		   新增内容
 * </pre>
 *
 * @author <a href="mailto:zhangwei@dayanginfo.com">张维</a>
 */
public class SchoolController extends AdminBaseController{

    /**
     * 学校基础信息管理首页 
     */
	@Before(AuthInterceptor.class)
    public void index(){
        this.setAttr("fisrtMenu", "学校管理");
        this.setAttr("secendMenu", "学校基础信息管理");

        this.setAttr("statusList", EnumUtil.toEnumList(Status.ENABLE));
        renderJsp("index.jsp");
    }
    
    /**
     * 分页查询学校信息列表
     */
    public void listSchoolByPage(){
    	int pageNumber = getParaToInt("page");
        int pageSize = getParaToInt("rows", DaYangStaticData.PAGE_ROWS);
        
        //获取页面查询条件
        long orgId = getParaToLong("orgId", 0l);
        String schoolName = getPara("schoolName");
        String status = getPara("status");
		Page<OrgModel> schoolPage = OrgModel.dao.findSchoolByPage(pageNumber,
				pageSize, schoolName, status, orgId);
		CommonUtil.setShowValue2List(schoolPage, EnumAndDicDefine.SCHOOL_DEFINETABLE);
        renderJson(JQGridPagePojo.parsePageData(schoolPage));
    }
    
    /**
     * 初始化新增或者编辑页面
     */
    public void goAdd(){
    	
    	this.initPage();
    	this.setAttr("roleName", StaticData.roleType.schAdminName);
    	this.setAttr("account", AccountGenerator.getAccount() );
    	renderJsp("school-modify.jsp");
    }
    
    /**
     * 查看详情页面
     * */
    public void goView(){
    	
    	this.initPage();
    	renderJsp("school-view.jsp");
    }
    
    private void initPage(){
    	//初始化学校类别
    	this.setAttr("schTypeList", DictionaryUtil.getDicListByDicType(StaticData.SCHOOL_TYPE));
    	//初始化省份
    	this.setAttr("provinceList", DivisionModel.dao.provinceList());
    	//初始化学校性质
    	this.setAttr("schPropertyList", EnumUtil.toEnumList(EnumAll.SchProperty.SchPublic));
    	
    	long orgId = getParaToLong("orgId", 0l);
    	this.setAttr("orgId", orgId);
    	//如果不为空则是更新操作，传入到前台
    	long _schoolId = getParaToLong("_schoolId", 0l);
    	if(_schoolId != 0l){
    		this.setAttr("_schoolId", _schoolId);
    	}else{
    		OrgModel orgModel= OrgModel.dao.findFirst("select * from base_organization where 1=1 and id = ?", orgId);
    		this.setAttr("provinceCode", orgModel.get("provinceCode", ""));
    		this.setAttr("cityCode", orgModel.get("cityCode", ""));
    		this.setAttr("areaCode", orgModel.get("areaCode", ""));
    	}
    }

    /**
     * 切换省份
     */
    public void changeProvince(){
    	String provinceId = getPara("provinceId");
    	List<DivisionModel> cityList = DivisionModel.dao.findCityOrAreaByBmId(provinceId);
    	if(CollectionUtils.isNotEmpty(cityList)){
    		renderJson(cityList);
    	}
    }
    
    /**
     * 切换城市
     * */
    public void changeCity(){
    	String cityId = getPara("cityId");
    	List<DivisionModel> areaList = DivisionModel.dao.findCityOrAreaByBmId(cityId);
    	if(CollectionUtils.isNotEmpty(areaList)){
    		renderJson(areaList);
    	}
    }
    
    /**
     * 学校编码="上级教育局编码"+"_三位流水号"(获取学校最大记录的流水+1)
     */
    public void genSchoolCode(){
    	String orgId = getPara("orgId");
    	String schoolCode = OrgModel.dao.genSchCode(orgId);
    	renderText(schoolCode, ContentType.TEXT);
    }
    
	/**
	 * 新增或编辑学校
	 */
    @FuncActionAnnotation(noIdAction="/admin/school/add",idAction="/admin/school/modify",idName = "school.id")
    @Before(FuncActionInterceptor.class)
	public void add(){
		OrgModel m = getModel(OrgModel.class, "school");
		SchoolExtModel schExt = getModel(SchoolExtModel.class, "schoolExt");
		if(m.getLong("id") != null){
			m.update();
			
			schExt.set("id", m.getLong("id"));
			schExt.update();
		}else{
			
			String account = getPara("account");
	    	long sch_Id = IDKeyUtil.getIDKey();
	    	long accountId = IDKeyUtil.getIDKey();
	    	
	    	AccountPojo accountPojo = new AccountPojo(accountId,account,CommonStaticData.AccountDicType.AdminType,"",sch_Id);
	    	/**
	    	 * wangchong update by 2015-08-03 新增加入调用sns远程接口同步通讯录信息
	    	 */
	    	CommonUtil.saveAccountPoJo(accountPojo, StaticData.roleType.schAdmin);
	    	
			long idKey = sch_Id;
			m.set("id", idKey);
			m.set("status", Status.ENABLE.getValueStr());
			m.set("orgFlag", OrgFlag.SCHOOLFLAG.getValueStr());
			//创建人，获取当前登录用户
			m.set("creator", this.getLoginUserId());
			m.set("createTime", new Date());
			m.set("accountId",accountId );
			if(CommonUtil.isEmpty(m.get("provinceCode"))){
				m.set("provinceCode", this.getPara("provinceId"));
			}
			if(CommonUtil.isEmpty(m.get("cityCode"))){
				m.set("cityCode", this.getPara("cityId"));
			}
			if(CommonUtil.isEmpty(m.get("areaCode"))){
				m.set("areaCode", this.getPara("areaId"));
			}
			m.save();
			
			String schoolCategory = schExt.get("schoolCategory").toString();
			schExt.set("id", idKey);
			schExt.save();
			
			//TODO 新增学校时，后台默认将该学校下的年级、科目信息一并进行初始化
			initSchoolInfo(idKey, schoolCategory);
		}
		renderJson(AjaxRetPojo.newInstance());
	}
	
	/**
	 * 初始化学校信息
	 * @param schId 学校ID
	 * @param schoolCategory 学校类别
	 */
	@SuppressWarnings("rawtypes")
	private void initSchoolInfo(long schId, String schoolCategory){
		if(CommonUtil.isEmpty(schoolCategory)){
			return ;
		}
		if(SchoolType.SCHTYPE_DICT.containsKey(schoolCategory)){
			Map<String,String> map = SchoolType.SCHTYPE_DICT.get(schoolCategory);
			
			StringBuffer codeBuffer = new StringBuffer();
			Iterator it = map.keySet().iterator();
			while(it.hasNext()){
				String key = (String) it.next();
				codeBuffer.append("'").append(map.get(key)).append("'").append(",");
			}
			String codeArray = codeBuffer.substring(0, codeBuffer.length() - 1);
			
			Map<String, List<Long>> subRecodMap = new HashMap<String, List<Long>>();
			long userId = this.getLoginUserId();
			OrgModel.dao.initSubject(schId, codeArray, subRecodMap, userId);
			OrgModel.dao.initGrade(schId, codeArray, userId);
			OrgModel.dao.initRGradeSub(schId, subRecodMap);
		}
	}
	
	public void querySchoolById(){
		renderJson(OrgModel.dao.findById(getParaToLong("_schoolId")));
	}
	
	public void querySchoolExtById(){
		renderJson(SchoolExtModel.dao.findById(getParaToLong("_schoolId")));
	}
	
	/**
	 * 启用或禁用当前学校
	 */
	@FuncActionAnnotation(diyAction="flag^disabled:/admin/school/disable,enabled:/admin/school/enable")
    @Before(FuncActionInterceptor.class)
	public void changeStatus(){
		String _schoolId = getPara("_schoolId");
		OrgModel.dao.changeSchStatus(_schoolId);
		
		renderJson(AjaxRetPojo.newInstance());
	}
	
	/**
	 * 删除当前学校
	 */
	@Before(FuncActionInterceptor.class)
	public void del(){
		String _schoolId = getPara("_schoolId");
		OrgModel orgModel = OrgModel.dao.findById(_schoolId);
		String accountId =  String.valueOf(orgModel.get("accountId"));
		String result = OrgModel.dao.checkBeforeDel(_schoolId);
		if(CommonUtil.isNotEmptyString(result)){
			sendResult(result);
			return ;
		}
		
		OrgModel.dao.delSchool(_schoolId);
		DaYangCommonUtil.deleteAccountById(accountId);
		renderJson(AjaxRetPojo.newInstance());
	}

	private void sendResult(String msg){
		AjaxRetPojo ret = AjaxRetPojo.newInstance();
		ret.setCode(AjaxRetPojo.CODE_FAIL);
		ret.setMsg(msg);
		renderJson(ret);
	}
	
	/**
     * 校验学校名称是否唯一 
     */
    public void validateSchoolName(){
        boolean flag = true;
        String schoolName = getPara("schoolName");
        long schoolId = getParaToLong("schoolId", 0l);
        OrgModel edbModel = OrgModel.dao.validateSchoolName(schoolName,schoolId);
        if (edbModel != null){
            flag = false;
        }
        renderJson(flag);
    }
}
