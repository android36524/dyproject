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
import com.dayang.commons.enums.EmpeTypeSch;
import com.dayang.commons.enums.MarryStatus;
import com.dayang.commons.enums.Sex;
import com.dayang.commons.pojo.AjaxRetPojo;
import com.dayang.commons.pojo.ExcelImportErrorPojo;
import com.dayang.commons.pojo.ImportPojo;
import com.dayang.commons.pojo.JQGridPagePojo;
import com.dayang.commons.pojo.SchEmplImportPojo;
import com.dayang.commons.thread.ExecuteSqlThread;
import com.dayang.commons.thread.ResThreadPoolUtil;
import com.dayang.commons.util.AccountGenerator;
import com.dayang.commons.util.CommonStaticData;
import com.dayang.commons.util.CommonStaticData.OrgTreeNodeType;
import com.dayang.commons.util.CommonUtil;
import com.dayang.commons.util.DaYangCommonUtil;
import com.dayang.commons.util.DaYangStaticData;
import com.dayang.commons.util.DictionaryUtil;
import com.dayang.commons.util.EnumAndDicDefine;
import com.dayang.commons.util.EnumUtil;
import com.dayang.commons.util.ExcelUtil;
import com.dayang.commons.util.FileUploadUtil;
import com.dayang.commons.util.IDKeyUtil;
import com.dayang.commons.util.StaticData;
import com.dayang.conf.interceptor.AuthInterceptor;
import com.dayang.conf.interceptor.FuncActionInterceptor;
import com.dayang.system.model.DeptModel;
import com.dayang.system.model.DictionaryModel;
import com.dayang.system.model.EmpeModel;
import com.dayang.system.model.LoginfoModel;
import com.dayang.system.model.TeachInfoModel;
import com.jfinal.aop.Before;
import com.jfinal.kit.PathKit;
import com.jfinal.plugin.activerecord.Page;

/**
 * 类描述：学校员工管理模块
 * <pre>
 * -------------History------------------
 *   DATE                 AUTHOR         VERSION        DESCRIPTION
 *  2015年5月18日             何意            V01.00.001		      新增内容
 * </pre>
 *
 * @author <a href="mailto:heyi@dayang.com">何意 </a>
 */
public class SchoolEmpeController extends AdminBaseController implements CURDController {

    /**
     * 教育局员工管理
     */
	@Before(AuthInterceptor.class)
    public void index(){
        this.setAttr("teaList", DictionaryUtil.getDicListByDicType(StaticData.TEASTATUS_INNERALTER));
        renderJsp("index.jsp");
    }

    /**
     * 初始化员工新增页面
     */
    @Override
    public void toAdd() {
        loadPublic();
        long orgId = getParaToLong("orgId",0l);
        this.setAttr("orgId",orgId);
        renderJsp("empe-modify.jsp");
    }

    /**
     * 新增/修改教育局员工
     */
    @FuncActionAnnotation(noIdAction="/admin/schoolEmpe/add",idAction="/admin/schoolEmpe/modify",idName = "empe.id")
    @Before({FuncActionInterceptor.class}) 
    @Override
    public void add() {
        EmpeModel dempeModel = getModel(EmpeModel.class, "empe");
        TeachInfoModel teachInfoModel = getModel(TeachInfoModel.class,"tea");
        
        if(dempeModel.getLong("id") != null){
            dempeModel.update();
            teachInfoModel.set("id",dempeModel.getLong("id"));
            teachInfoModel.update();
        }else {
        	int empType =  Integer.parseInt(dempeModel.get("empType").toString());
        	long empe_Id = IDKeyUtil.getIDKey();
            long accountId = IDKeyUtil.getIDKey();
            String account = String.valueOf(AccountGenerator.getAccount());
            if(empType == EmpeTypeSch.TEACHEREMP.getValue()){
            	AccountPojo accountPojo = new AccountPojo(accountId,account,CommonStaticData.AccountDicType.TeacherType,"",empe_Id);
                /**
                 * wangchong update by 2015-08-03 新增加入调用sns远程接口同步通讯录信息
                 */
                CommonUtil.saveAccountPoJo(accountPojo, StaticData.roleType.teacherRole);
            }
            dempeModel.set("id", empe_Id);
            dempeModel.set("creator", LoginInfoUtil.getAccountInfo().getId());
            dempeModel.set("createTime",new Date());
            dempeModel.set("accountId",accountId );
            dempeModel.save();
            teachInfoModel.set("id",dempeModel.get("id"));
            teachInfoModel.save();
        }
        renderJson(AjaxRetPojo.newInstance());
    }
    /**
     * 批量删除学校员工
     */
    @Override
    @Before({FuncActionInterceptor.class}) 
    public void del() {
        String empeId = getPara("num");
        if(CommonUtil.isNotEmptyString(empeId)){
            String[] id = empeId.split(",");
            EmpeModel.dao.insertAllSch(id);
            EmpeModel.dao.delAllSch(id);
            String accountIds = EmpeModel.dao.findAccountIds(empeId);
            DaYangCommonUtil.deleteAccountById(accountIds);
        }
        renderJson(AjaxRetPojo.newInstance());
    }


    /**
     * 查询部门员工列表信息
     */
    public void empeList(){
        int pageNumber = getParaToInt("page");
        int pageSize = getParaToInt("rows", DaYangStaticData.PAGE_ROWS);
        long deptId = getParaToLong("deptId", 0l);
        long orgId = getParaToLong("orgId", 0l);
        int status = getParaToInt("status", 0);
        String name = getPara("name");
        String mobile = getPara("mobile");
        Map<String,Object> map= new HashMap<String,Object>();
        map.put("deptId",deptId);
        map.put("orgId",orgId);
        map.put("status",status);
        map.put("name",name);
        map.put("mobile",mobile);
        map.put("empTypeE", EmpeTypeSch.SCHEMP.getValueStr());
        map.put("empTypeT", EmpeTypeSch.TEACHEREMP.getValue());
        map.put("orgType",OrgTreeNodeType.SCHFLAG);
        Page p = EmpeModel.dao.findEmpeModelPage(pageNumber, pageSize, map);
        CommonUtil.setShowValue2List(p, EnumAndDicDefine.EMPE_DEFINETABLE);
        renderJson(JQGridPagePojo.parsePageData(p));
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
        this.setAttr("empTypeList", EnumUtil.toEnumList(EmpeTypeSch.SCHEMP));//岗位类型
        this.setAttr("healthList",DictionaryUtil.getDicListByDicType(StaticData.TEASTATUS_HEALTH));//健康状况
       
        long id = getParaToLong("id",0l);
        EmpeModel empeModel = EmpeModel.dao.findById(id);
        DictionaryModel dictionaryModel = null;
        if(id>0){
        	dictionaryModel = DictionaryModel.dao.findDictiByValue(StaticData.TEASTATUS_INNERALTER,String.valueOf(empeModel.get("status")));
        }
        else{
        	dictionaryModel= DictionaryModel.dao.findDictiByValue(StaticData.TEASTATUS_INNERALTER,String.valueOf(StaticData.TeacherStatus.inUsed));
        }
        this.setAttr("value", dictionaryModel.get("value"));
        this.setAttr("name", dictionaryModel.get("name"));
        this.setAttr("id", id);
        this.setAttr("manName", Sex.MAN.getName()); //性别
		this.setAttr("manVal", Sex.MAN.getValueStr());
		this.setAttr("woManName", Sex.WOMAN.getName());
		this.setAttr("woManVal", Sex.WOMAN.getValueStr());
    }

    /**
    * 初始化员工详情页面
    */
    public void empeInfo(){
        loadPublic();
        renderJsp("showEmpeInfo.jsp");
    }

    /**
     * 根据id获取该员工详细信息
     */
    public void queryById(){
        long empeId = getParaToLong("id");
        renderJson(EmpeModel.dao.findSchByEmpeId(empeId));
    }

    /**
     * 验证学校员工是否唯一
     */
    public void vaidataEmpNo(){
    	 boolean flag = true;
    	//公司员工和代理商：3  教育局 ： 1  学校 2或者是4
    	String empType = getPara("empType"); 
    	String empNo = getPara("empNo");
    	long orgId = getParaToLong("orgId");
    	long empId = getParaToLong("empeId",0l);
        EmpeModel empeModel = EmpeModel.dao.valdateEmpNo(empNo,orgId,empId,empType);
        if (empeModel != null){
            flag = false;
        }
        renderJson(flag);
    }

    /**
     * 导入学校部门员工信息
     */
    @FuncActionAnnotation(action="/admin/schoolEmpe/import")
	@Before(FuncActionInterceptor.class) 
    public void importSchInfo(){
        long  orgId = getParaToLong("orgId");
        SchEmplImportPojo.orgId = orgId;
        String fileUrl  = getPara("fileUrl");
        File file = FileUploadUtil.getServerPath(fileUrl);
        List<ImportPojo> list = ExcelUtil.parseXlsx(file, SchEmplImportPojo.class.getName());
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        List<String> listDept = DeptModel.dao.findDeptNameListByOrgId(String.valueOf(orgId));
     
        List<ExcelImportErrorPojo> errorMsgList = new ArrayList<>();
        List<SchEmplImportPojo> validationList = new ArrayList<>();
        String errorExcelUrl = null;
        for (ImportPojo importPojo : list){
            if (importPojo instanceof SchEmplImportPojo){
                SchEmplImportPojo temp = (SchEmplImportPojo) importPojo;
                temp.setDeptNames(listDept);
                Set<ConstraintViolation<SchEmplImportPojo>> constraintViolations = validator.validate(temp);
                ExcelImportErrorPojo errorPojo = new ExcelImportErrorPojo();
                if(constraintViolations.size()!=0){
                    errorPojo.setRowNumber(temp.getRowNumber());
                    for (ConstraintViolation constraintViolation : constraintViolations){
                        errorPojo.getErrorMsg().add(constraintViolation.getMessage());
                    }
                    errorMsgList.add(errorPojo);
                }
                else{
                    validationList.add(temp);
                }
            }
        }
        if(errorMsgList.size()>0){
            //错误信息写入文件
            String errorPath = CommonUtil.getAppProperties("errorExcelPath");
            errorExcelUrl = PathKit.getWebRootPath() + errorPath+"/"+ CommonUtil.getUUID()+".xls";
            File errorFile = new File(errorExcelUrl);
            ExcelUtil.writeError2Excel(errorFile, errorMsgList);
        }

        //将生成的错误Excel写入到imp_loginfo表中
        LoginfoModel loginfoModel = new LoginfoModel();
        loginfoModel.set("id", IDKeyUtil.getIDKey()).set("fileName",fileUrl)
                .set("downLoadUrl",errorExcelUrl)
                .set("importTime",new Date()).set("succesCount",validationList.size())
                .set("errorCount",errorMsgList.size()).save();

        //执行验证通过后的pojo插入数据库
        ResThreadPoolUtil.threadPool.execute(new ExecuteSqlThread(validationList));
        AjaxRetPojo ret = new AjaxRetPojo();
        ret.setMsg(String.valueOf(errorMsgList.size()));
        ret.setCode(String.valueOf(validationList.size()));
        renderJson(ret);
    }
    /**
     * 下载学校员工Excel模板
     */
    @FuncActionAnnotation(action="/admin/schoolEmpe/download")
 	@Before(FuncActionInterceptor.class) 
    public void downExcel(){
        renderFile(CommonUtil.getAppProperties("teaEmplExcelPath"));
    }

    /**
     * 导出学校员工信息Excel数据
     */
    @FuncActionAnnotation(action="/admin/schoolEmpe/export")
  	@Before(FuncActionInterceptor.class) 
    public void downLoadExcel(){
        long deptId = getParaToLong("deptId", 0l);
        long orgId = getParaToLong("orgId", 0l);
        String name = getPara("name");
        String mobile = getPara("mobile");
        int status = getParaToInt("status",0);
        List<EmpeModel> dataSource = EmpeModel.dao.teaEmplSql(orgId, deptId, name, mobile,status);
        CommonUtil.setShowValue2List(dataSource, EnumAndDicDefine.EMPE_DEFINETABLE);
        String downExcel = CommonUtil.getAppProperties("downExcel");
        String file = PathKit.getWebRootPath()+downExcel+"/"+ CommonUtil.getUUID()+".xls";
        File targetFile = new File(file);
        SchEmplImportPojo schEmplImportPojo = new SchEmplImportPojo();
        ExcelUtil.exportExcel(dataSource, targetFile, schEmplImportPojo);
        renderFile(targetFile);
    }
    
    /**
     * 校验当前学校部门员工手机号码是否唯一
     */
    public void validateEmpeMobile(){
        boolean flag = true;
        String mobile = getPara("mobile");
        long orgId = getParaToLong("orgId",0l);
        long empeId = getParaToLong("empeId",0l);
        EmpeModel empeModel = EmpeModel.dao.validateEmpeMobile(mobile, orgId, empeId);
        if (empeModel != null){
            flag = false;
        }
        renderJson(flag);
    }
    /**
     * 根据岗位类型获取岗位职业
     */
    public void getEmpeTypeByCode(){
    	int dictionarytype = StaticData.JOBSCAREERS_DICTIONARYTYPE;
    	String  code = getPara("code");
    	Map<String,Object> map = new HashMap<>();
    	map.put("code", code);
    	map.put("dictionaryType", dictionarytype);
    	List<DictionaryModel>  dicList = DictionaryModel.dao.findDictionaryByParams(map);
    	renderJson(dicList);
    	
    }
}
