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

import org.apache.log4j.Logger;

import com.dayang.cas.util.LoginInfoUtil;
import com.dayang.commons.annotation.FuncActionAnnotation;
import com.dayang.commons.controller.AdminBaseController;
import com.dayang.commons.controller.CURDController;
import com.dayang.commons.enums.EmpeStatus;
import com.dayang.commons.enums.EmpeType;
import com.dayang.commons.enums.MarryStatus;
import com.dayang.commons.enums.Sex;
import com.dayang.commons.pojo.AjaxRetPojo;
import com.dayang.commons.pojo.EdbEmplImportPojo;
import com.dayang.commons.pojo.ExcelImportErrorPojo;
import com.dayang.commons.pojo.ImportPojo;
import com.dayang.commons.pojo.JQGridPagePojo;
import com.dayang.commons.thread.ExecuteSqlThread;
import com.dayang.commons.thread.ResThreadPoolUtil;
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
import com.dayang.system.model.EduInfoModel;
import com.dayang.system.model.EmpeModel;
import com.dayang.system.model.LoginfoModel;
import com.jfinal.aop.Before;
import com.jfinal.kit.PathKit;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.render.ContentType;
/**
 * 类描述：教育局员工管理模块
 * <pre>
 * -------------History------------------
 *   DATE                 AUTHOR         VERSION        DESCRIPTION
 *  2015年5月18日             何意            V01.00.001		      新增内容
 * </pre>
 *
 * @author <a href="mailto:heyi@dayang.com">何意 </a>
 */
public class EmpeEdbController extends AdminBaseController implements CURDController {

    /**
     * 教育局员工管理
     */
    @Override
    @Before(AuthInterceptor.class)
    public void index() {
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
     * 拼音简写生成
     */
    public void pinyinGen(){
        String empeName = getPara("empeName");
        String nameSpell = EmpeModel.dao.pinYinByName(empeName);
        renderText(nameSpell, ContentType.TEXT);
    }

    /**
     * 新增/修改教育局员工
     */
    @Override
    @FuncActionAnnotation(noIdAction="/admin/empe/add",idAction="/admin/empe/modify",idName = "empe.id")
    @Before(FuncActionInterceptor.class)
    public void add() {
        EmpeModel dempeModel = getModel(EmpeModel.class, "empe");
        EduInfoModel eduInfoModel = getModel(EduInfoModel.class,"edu");
        if(dempeModel.getLong("id") != null){
            dempeModel.update();
            eduInfoModel.set("id",dempeModel.getLong("id"));
            eduInfoModel.update();
        }else {
            dempeModel.set("id", IDKeyUtil.getIDKey());
            dempeModel.set("status", EmpeStatus.ENABLE.getValueStr());
            dempeModel.set("empType",EmpeType.EDBEMPE.getValueStr());
            dempeModel.set("createTime",new Date());
            dempeModel.set("creator",LoginInfoUtil.getAccountInfo().getId());
            dempeModel.save();
            eduInfoModel.set("id",dempeModel.get("id"));
            eduInfoModel.save();
        }
        renderJson(AjaxRetPojo.newInstance());
    }

    /**
     * 批量删除教育局员工
     */
    @Override
    @Before(FuncActionInterceptor.class)
    public void del() {
        String empeId = getPara("num");
        if(CommonUtil.isNotEmptyString(empeId)){
            String[] id = empeId.split(",");
            EmpeModel.dao.insertAllEdb(id);
            EmpeModel.dao.delAllEdb(id);
            String accountIds = EmpeModel.dao.findAccountIds(empeId);
            DaYangCommonUtil.deleteAccountById(accountIds);
        }
        renderJson(AjaxRetPojo.newInstance());
    }

    private static Logger logger = Logger.getLogger(EmpeModel.class);
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
        map.put("empType",EmpeType.EDBEMPE.getValueStr());
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
//        this.setAttr("jobList", CacheUtil.getDicListByDicType(StaticData.JOB_dictionaryType));//职务
        this.setAttr("marryLists", EnumUtil.toEnumList(MarryStatus.ISMARRY));
        this.setAttr("manName", Sex.MAN.getName()); //性别
		this.setAttr("manVal", Sex.MAN.getValueStr());
		this.setAttr("woManName", Sex.WOMAN.getName());
		this.setAttr("woManVal", Sex.WOMAN.getValueStr());
		//this.setAttr("empTypeList", EnumUtil.toEnumList(EmpeTypeSch.SCHEMP));
		
        long id = getParaToLong("id",0l);
        this.setAttr("id", id);
    }

    /**
     * 初始化员工详情页面
     */
    public void empeInfo(){
        loadPublic();
        renderJsp("showempeInfo.jsp");
    }

    /**
     * 根据id获取该员工详细信息
     */
    public void queryById(){
        long empeId = getParaToLong("id");
        EmpeModel empeModel = EmpeModel.dao.findEdbByEmpeId(empeId);
        renderJson(empeModel);
    }

    /**
     * 验证教育局员工是否唯一
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
     * 导入教育局员工信息
     */
    @FuncActionAnnotation(action="/admin/empe/import")
    @Before(FuncActionInterceptor.class)
    public void importEdbInfo(){
        long  orgId = getParaToLong("orgId");
        EdbEmplImportPojo.orgId = orgId;
        String fileUrl  = getPara("fileUrl");
        File file = FileUploadUtil.getServerPath(fileUrl);
        List<ImportPojo> list = ExcelUtil.parseXlsx(file, EdbEmplImportPojo.class.getName());
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        List<String> listDept = DeptModel.dao.findDeptNameListByOrgId(String.valueOf(orgId));
        List<ExcelImportErrorPojo> errorMsgList = new ArrayList<>();
        List<EdbEmplImportPojo> validationList = new ArrayList<>();
        String errorExcelUrl = null;
        for (ImportPojo importPojo : list){
            if (importPojo instanceof EdbEmplImportPojo){
                EdbEmplImportPojo temp = (EdbEmplImportPojo) importPojo;
                temp.setDeptNames(listDept);
                Set<ConstraintViolation<EdbEmplImportPojo>> constraintViolations = validator.validate(temp);
                if(constraintViolations.size()!=0){
                    ExcelImportErrorPojo errorPojo = new ExcelImportErrorPojo();
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
            errorExcelUrl = PathKit.getWebRootPath() + errorPath+"/"+CommonUtil.getUUID()+".xls";
            File errorFile = new File(errorExcelUrl);
            ExcelUtil.writeError2Excel(errorFile,errorMsgList);
        }

        //将生成的错误Excel写入到imp_loginfo表中
        LoginfoModel loginfoModel = new LoginfoModel();
        loginfoModel.set("id",IDKeyUtil.getIDKey()).set("fileName",fileUrl)
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
     * 下载教育局员工Excel模板
     */
    @FuncActionAnnotation(action="/admin/empe/download")
    @Before(FuncActionInterceptor.class)
    public void downExcel(){
        renderFile(CommonUtil.getAppProperties("eudEmplExcelPath"));
    }
    /**
     * 导出教育局员工信息Excel数据
     */
    @FuncActionAnnotation(action="/admin/empe/export")
    @Before(FuncActionInterceptor.class)
    public void downLoadExcel(){
        long deptId = getParaToLong("deptId", 0l);
        long orgId = getParaToLong("orgId", 0l);
        String name = getPara("name");
        String mobile = getPara("mobile");
        List<EmpeModel> dataSource = EmpeModel.dao.eduEmplSql(orgId, deptId, name, mobile);
        CommonUtil.setShowValue2List(dataSource, EnumAndDicDefine.EMPE_DEFINETABLE);
        String downExcel = CommonUtil.getAppProperties("downExcel");
        String file = PathKit.getWebRootPath()+downExcel+"/"+CommonUtil.getUUID()+".xls";
        File targetFile = new File(file);
        EdbEmplImportPojo edbEmplImportPojo = new EdbEmplImportPojo();
        ExcelUtil.exportExcel(dataSource,targetFile,edbEmplImportPojo);
        renderFile(targetFile);
    }
    
    /**
     * 校验当前教育局部门员工手机号码是否唯一
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
}
