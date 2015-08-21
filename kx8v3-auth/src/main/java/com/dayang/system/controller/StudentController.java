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

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dayang.commons.annotation.FuncActionAnnotation;
import com.dayang.commons.controller.AdminBaseController;
import com.dayang.commons.controller.CURDController;
import com.dayang.commons.enums.EnumAll;
import com.dayang.commons.enums.Sex;
import com.dayang.commons.pojo.AjaxRetPojo;
import com.dayang.commons.pojo.ExcelImportErrorPojo;
import com.dayang.commons.pojo.ImportPojo;
import com.dayang.commons.pojo.JQGridPagePojo;
import com.dayang.commons.pojo.StudentImportPojo;
import com.dayang.commons.thread.ExecuteSqlThread;
import com.dayang.commons.thread.ResThreadPoolUtil;
import com.dayang.commons.util.AccountGenerator;
import com.dayang.commons.util.CommonUtil;
import com.dayang.commons.util.DaYangCommonUtil;
import com.dayang.commons.util.DaYangStaticData;
import com.dayang.commons.util.DictionaryUtil;
import com.dayang.commons.util.EnumAndDicDefine;
import com.dayang.commons.util.ExcelUtil;
import com.dayang.commons.util.FileUploadUtil;
import com.dayang.commons.util.IDKeyUtil;
import com.dayang.commons.util.StaticData;
import com.dayang.conf.interceptor.AuthInterceptor;
import com.dayang.conf.interceptor.FuncActionInterceptor;
import com.dayang.system.model.ClassModel;
import com.dayang.system.model.DictionaryModel;
import com.dayang.system.model.GradeModel;
import com.dayang.system.model.HisStudentModel;
import com.dayang.system.model.LogGradHigModel;
import com.dayang.system.model.LoginfoModel;
import com.dayang.system.model.RstuAndParentModel;
import com.dayang.system.model.SchUpgradeModel;
import com.dayang.system.model.StuParentModel;
import com.dayang.system.model.StudentInfoModel;
import com.dayang.system.model.StudentModel;
import com.jfinal.aop.Before;
import com.jfinal.kit.PathKit;
import com.jfinal.plugin.activerecord.Page;

/**
 * 类描述：学生管理模块Controller
 * <pre>
 * -------------History------------------
 *   DATE                 AUTHOR         VERSION        DESCRIPTION
 *  2015年5月23日下午3:03:52        吴杰东              		 V01.00.001		 新增内容   
 * </pre>
 * 
 * @author <a href="wujd@dayanginfo.com">吴杰东  </a>
 */
public class StudentController extends AdminBaseController implements CURDController {
	
	private static AjaxRetPojo pojo = new AjaxRetPojo();
	
	@Before(AuthInterceptor.class)
	public void index(){
		renderJsp("index.jsp");
	}
	
	public void toStuHiger(){
		renderJsp("setStuHiger.jsp");
	}

	public void toQueryHisStudent(){
		renderJsp("stuHisgraduate.jsp");
	}
	
	public void init(){
		long schId = getParaToLong("_schId");
		System.out.println("schId:"+schId);
	}

	/**
	 * 根据条件分页查询学生家长信息 
	 */
	public void findStuPerentByPage(){
		int pageNumber = getParaToInt("page");
		int pageSize = getParaToInt("rows", DaYangStaticData.PAGE_ROWS);
		String name = getTrimParamValue("name");//家长姓名
		String telphone = getPara("telphone");//手机号码
		renderJson(JQGridPagePojo.parsePageData(StudentModel.findStuParentByPage(pageNumber, pageSize, name, telphone)));
	}
	
	/**
	 * 学生管理根据条件分页查询学生信息
	 */
	public void ListPage(){
		int pageNumber = getParaToInt("page");
		int pageSize = getParaToInt("rows", DaYangStaticData.PAGE_ROWS);
		Map<String,Object> map=queryParams();
		Page<StudentModel> stuPage = StudentModel.dao.findStudentPage(pageNumber, pageSize, map);
        CommonUtil.setShowValue2List(stuPage, EnumAndDicDefine.STUDENT_DEFINETABLE);
        renderJson(JQGridPagePojo.parsePageData(stuPage));
	}
	
	/**
	 * 查询参数
	 * @return
	 */
	private Map<String,Object> queryParams(){
		Long schId = getParaToLong("schId");
		Long gradeId = getParaToLong("gradeId");//年级
		Long classId = getParaToLong("classId");//班级
		String name = getTrimParamValue("name");//学生姓名
		String telphone = getTrimParamValue("tel"); //家长号码
		String studentNo = getPara("studentNo");//学号
		String status = getPara("status","");  //学生状态
		
		Map<String,Object> map= new HashMap<String,Object>();
        map.put("schId",schId);
        map.put("gradeId",gradeId);
        map.put("classId",classId);
        map.put("name",name);
        map.put("telphone",telphone);
        map.put("studentNo",studentNo);
        map.put("status", status);
        return map;
	}
	
	/**
	 * 根据学生ID查询家长信息
	 */
	public void findStuParentById(){
		Long id = getParaToLong("id");
		List<StuParentModel> list = StudentModel.findParentByStuId(id);
		CommonUtil.setShowValue2List(list, EnumAndDicDefine.RELA_STUDENT);
		renderJson(list);
	}

	/**
	 * 查询已毕业的学生信息（手动毕业）
	 */
	public void findGraduateStu(){
		int pageNumber = getParaToInt("page");
		int pageSize = getParaToInt("rows", DaYangStaticData.PAGE_ROWS);
		Long schId = getParaToLong("schId");
		Long gradeId = getParaToLong("gradeId");//年级
		Long classId = getParaToLong("classId");//班级
		String name = getTrimParamValue("name");//学生姓名
		String telphone = getPara("tel"); //家长号码
		String studentNo = getPara("studentNo");//学号
		String startTime = getPara("startTime");
		String endTime = getPara("endTime");
		int status = StaticData.StudentStatus.gradution;
		Map<String,Object> map= new HashMap<String,Object>();
		map.put("schId",schId);
		map.put("gradeId",gradeId);
		map.put("classId",classId);
		map.put("name",name);
		map.put("telphone",telphone);
		map.put("studentNo",studentNo);
		map.put("status",status);
		map.put("startTime",startTime);
		map.put("endTime",endTime);
		Page<HisStudentModel> p = HisStudentModel.findGraduateStuPage(pageNumber, pageSize, map);
		CommonUtil.setShowValue2List(p, EnumAndDicDefine.STUDENT_DEFINETABLE);
		renderJson(JQGridPagePojo.parsePageData(p));
	}
	/**
	 * 毕业管理
	 */
	@FuncActionAnnotation(action="/admin/student/graduate")
    @Before(FuncActionInterceptor.class)
	public void stuGraduate(){
		String id = getPara("id");
		if(CommonUtil.isNotEmptyString(id)){
			String [] idArr = id.split(",");
			StudentModel.dao.insertAllStuGraduate(idArr);
			StudentModel.dao.delAllStu(idArr);
		}
		renderJson(AjaxRetPojo.newInstance());
	}
	
	/**
	 * 管理家长
	 */
	@FuncActionAnnotation(action="/admin/student/manage")
    @Before(FuncActionInterceptor.class)
	public void toManageParent(){
		long id = getParaToLong("id",0l);
		long schId = getParaToLong("schId");
		this.setAttr("relationList", DictionaryUtil.getDicListByDicType(StaticData.RELATION_DICTIONARYTYPE));//家属关系
	    this.setAttr("id", id);
	    this.setAttr("schId", schId);
	    setRadioYesOrNo();
		renderJsp("manage-parent.jsp");
	}

    /**
	 * 加载修改或新增页面
	 */
	@Override
	public void toAdd() {
		this.setAttr("nationList", DictionaryUtil.getDicListByDicType(StaticData.NATION_dictionaryType));//民族
		this.setAttr("politicalList", DictionaryUtil.getDicListByDicType(StaticData.POLITICAL_dictionaryType));//政治面貌
		this.setAttr("healthList", DictionaryUtil.getDicListByDicType(StaticData.HEALTH_DICTIONARYTYPE));//健康状况
		this.setAttr("fromWhereList", DictionaryUtil.getDicListByDicType(StaticData.FROMWHERE_DICTIONARYTYPE));//健康状况
		this.setAttr("householdNatureList", DictionaryUtil.getDicListByDicType(StaticData.HOUSEHOLDNATURE_DICTIONARYTYPE));//户口性质
		this.setAttr("stayList", DictionaryUtil.getDicListByDicType(StaticData.STAY_DICTIONARYTYPE));//留守儿童
		this.setAttr("disabilityList", DictionaryUtil.getDicListByDicType(StaticData.DISABILITY_DICTIONARYTYPE));//残疾类型
		this.setAttr("relationList", DictionaryUtil.getDicListByDicType(StaticData.RELATION_DICTIONARYTYPE));//家属关系
		// 是和否
		setRadioYesOrNo();
		this.setAttr("manName", Sex.MAN.getName()); //性别
		this.setAttr("manVal", Sex.MAN.getValueStr());
		this.setAttr("woManName", Sex.WOMAN.getName());
		this.setAttr("woManVal", Sex.WOMAN.getValueStr());
		this.setAttr("goReadName", EnumAll.StudyWays.GoRead.getName()); //就读方式
		this.setAttr("goReadVal", EnumAll.StudyWays.GoRead.getValueStr());
		this.setAttr("ResidenceName", EnumAll.StudyWays.Residence.getName());
		this.setAttr("ResidenceVal", EnumAll.StudyWays.Residence.getValueStr());
		
		long schId = getParaToLong("schId");
		List<GradeModel> gradeList = StudentModel.dao.findGradeBySchId(schId);
		this.setAttr("gradeList", gradeList);
		long id = getParaToLong("id",0l);
		int status_id = 0;
		List<ClassModel> classList=new ArrayList<ClassModel>();
		if(id==0){
		   status_id= StaticData.StudentStatus.inUsed;
		   this.setAttr("account", AccountGenerator.getAccount());
		   this.setAttr("roleId", StaticData.roleType.studentRole);
		   this.setAttr("roleName", StaticData.roleType.studentRoleName);
		}else{
			StudentModel stu= StudentModel.dao.findStudentByStuId(id);
			status_id=stu.get("status");
			long gradeId=stu.get("gradeId");
			classList= StuParentModel.findClassByGrade(gradeId);
			
		}
		this.setAttr("classList", classList);
		this.setAttr("status_id", status_id);
		DictionaryModel dic= DictionaryUtil.getDicModel(StaticData.DISABILITY_STATUS, status_id);
		this.setAttr("status_name", dic.get("name"));
	    this.setAttr("id", id);
	    boolean isDetail=Boolean.valueOf(getPara("isDetail"));
	    if(isDetail){
	        renderJsp("student-detail.jsp");
	    }else{
	    	renderJsp("student-modify.jsp");
	    }
	}

	/**
    * 新增/修改学生信息
    */
	@Override
	@FuncActionAnnotation(noIdAction="/admin/student/add",idAction="/admin/student/modify",idName = "student.id")
    @Before(FuncActionInterceptor.class)
	public void add() {
		String account = getPara("account");
		long roleId = getParaToLong("roleId",0l);
		StudentModel studentModel = getModel(StudentModel.class, "student");
        StudentInfoModel stuInfoModel = getModel(StudentInfoModel.class,"stuInfo");
        studentModel.set("creator",getLoginUserId());
        boolean bool=studentModel.saveStudent(account,roleId,studentModel, stuInfoModel);
        if(bool){
			renderJson(AjaxRetPojo.newInstance());
		}else{
			pojo.setCode(AjaxRetPojo.CODE_FAIL);
			pojo.setMsg("系统异常，请稍后重试");
			renderJson(pojo);
		}
	}

	/**
	 * 删除学生信息
	 */
	@Override
	@Before(FuncActionInterceptor.class)
	public void del() {
	        String studentIds = getPara("ids");
	        String parentIds = getPara("parentIds","");
	        parentIds= parentIds.equals("null")? "" : parentIds;
	        Long schId = getParaToLong("schId",0l);
        	String accountIds="";
	        // 未关联家长的直接删除学生信息
	        if(DaYangCommonUtil.isEmptyString(parentIds) &&  DaYangCommonUtil.isNotEmptyString(studentIds)){
	        	 String[] id = studentIds.split(",");
	        	 accountIds=StudentModel.dao.findAccountIds(studentIds);
	             StudentModel.dao.insertAllStu(id);
	             StudentModel.dao.delAllStu(id);
	        }else{
	        	// 获取不是当前学校的家长ID
		        String ids= RstuAndParentModel.dao.findParentIds(StuParentController.NOT_EQUAL,schId, parentIds);
		        Boolean bool=StudentModel.dao.validateLastParent(schId, studentIds, parentIds, ids);
				if(bool){
					//该学生的家长只关联了这一个小孩
					pojo.setCode("-2");
					renderJson(pojo);
					return ;
				}else{
					 accountIds=StudentModel.dao.findAccountIds(studentIds);
					//删除关联关系和学生信息
					 StuParentModel.deleStudent(schId,parentIds,Long.valueOf(studentIds));
					 String[] id = studentIds.split(",");
		             StudentModel.dao.insertAllStu(id);
		             StudentModel.dao.delAllStu(id);
				}
	        }
	        //删除对应的账号
		    DaYangCommonUtil.deleteAccountById(accountIds);
			renderJson(AjaxRetPojo.newInstance());
	        
	}
	
	/**
     * 根据id获取该学生详细信息
     */
    public void queryById(){
        long studentId = getParaToLong("id");
        renderJson(StudentModel.dao.findStudentByStuId(studentId));
    }
	
    /**
     * 根据学校Id 获取该学校升学升学时间
     */
    
    public void getHigGradeTime(){
    	long schId = getParaToLong("schId");
    	SchUpgradeModel suModel = SchUpgradeModel.dao.getHigGradeTime(schId);
    	if(suModel != null){
    		Date jobRunTime = suModel.getDate("jobRunTime");
        	renderJson(jobRunTime);
        	return;
    	}
    	renderText(null);
    }

	/**
	 * 设置学生毕业升学
	 */
	public void  setGraduateStu(){
		long gradeId = getParaToLong("gradeId");
		long schId = getParaToInt("schId");
		String jobRunTime = getPara("hightTime");
		SchUpgradeModel schUpgradeModel = SchUpgradeModel.dao.findSchUpgradeBygradId(gradeId,schId);
		if(schUpgradeModel ==null){
			schUpgradeModel = new SchUpgradeModel();
			List<GradeModel> gradeList = GradeModel.dao.findGradeListBySchId(schId);
			String highGradeId = "";
			String highGradeIds="";
			if(gradeList.size()>0){
				for (int i=0;i<gradeList.size();i++){
					GradeModel gradeModel = gradeList.get(i);
					if(!gradeModel.get("id").equals(gradeId)){
						highGradeId += gradeModel.get("id")+",";
					}
				}
				highGradeIds = highGradeId.substring(0,highGradeId.length()-1);
			}
			schUpgradeModel.set("id", IDKeyUtil.getIDKey());
			schUpgradeModel.set("upGradeId",gradeId);
			schUpgradeModel.set("highGradeIds",highGradeIds);
			schUpgradeModel.set("jobRunTime",jobRunTime);
			schUpgradeModel.set("creator",getLoginUserId());
			schUpgradeModel.set("createTime",new Date());
			schUpgradeModel.set("schId",schId);
			schUpgradeModel.save();
		}
		else
		{
			schUpgradeModel.set("jobRunTime",jobRunTime);
			schUpgradeModel.update();
		}
		renderJson(AjaxRetPojo.newInstance());
	}
	
	/**
	 * radio yes 和  no 
	 */
	private void setRadioYesOrNo(){
		this.setAttr("radioYesName", EnumAll.IsYesOrNot.IsYes.getName());
		this.setAttr("radioYesVal", EnumAll.IsYesOrNot.IsYes.getValueStr());
		this.setAttr("radioNotName", EnumAll.IsYesOrNot.IsNot.getName());
		this.setAttr("radioNotVal", EnumAll.IsYesOrNot.IsNot.getValue());
		
	}

    /**
     * 学生升学查询
     */
	public void higGrade(){
		renderJsp("stuHigGrade.jsp");
	}

    /**
     * 根据条件查询学生升学信息
     */
	public void findHigGradeStu(){
		int pageNumber = getParaToInt("page");
		int pageSize = getParaToInt("rows", DaYangStaticData.PAGE_ROWS);
		long beforeGradeId = getParaToLong("gradeId",0l);
		long schId = getParaToLong("schId", 0l);
		long classId = getParaToLong("classId",0l);
		String name = getTrimParamValue("name");
		String telphone = getPara("tel");
		String startTime = getPara("startTime");
		String endTime = getPara("endTime");
		String studentNo = getPara("studentNo");
		Map<String,Object> map= new HashMap<String,Object>();
		map.put("name",name);
		map.put("telphone",telphone);
		map.put("beforeGradeId",beforeGradeId);
		map.put("schId",schId);
		map.put("startTime",startTime);
		map.put("endTime",endTime);
		map.put("classId",classId);
		map.put("studentNo",studentNo);
		Page<LogGradHigModel> p = LogGradHigModel.findHigGradeStuPage(pageNumber, pageSize, map);
		CommonUtil.setShowValue2List(p, EnumAndDicDefine.STUDENT_DEFINETABLE);
		renderJson(JQGridPagePojo.parsePageData(p));
	}
	
	
	//查询升学学生详情
	public void higGradeInfo(){
		int id = getParaToInt("id");
		this.setAttr("id", id);
		renderJsp("higGradeInfo.jsp");
	}
	public void findHigGradeInfoById(){
		int id = getParaToInt("id");
		LogGradHigModel logGradHigModel = LogGradHigModel.dao.findhigGradINfoById(id);
        renderJson(logGradHigModel);
	}
	/**
	 * 保存于学生与家长的关联关系
	 */
	@SuppressWarnings("unchecked")
	public void saveRelaStu(){
		Long studentId= getParaToLong("studentId");
		String students = getPara("relaStudents");
		List<JSONObject> list = new ArrayList<JSONObject>();
		if(CommonUtil.isNotEmptyString(students)){
			list = JSON.parseObject(students, ArrayList.class);
		}
		boolean bool = StudentModel.saveRelaStu(studentId, list);
		if(bool){
			renderJson(AjaxRetPojo.newInstance());
		}else{
			pojo.setCode(AjaxRetPojo.CODE_FAIL);
			pojo.setMsg("系统异常，请稍后重试");
			renderJson(pojo);
		}
	}
	
	/**
     * 导入学生信息
     */
	@FuncActionAnnotation(action="/admin/student/import")
    @Before(FuncActionInterceptor.class)
    public void importStuInfo(){
        long  schId = getParaToLong("schId");
        String fileUrl  = getPara("fileUrl");
        String excelName  = getPara("excelName");
        File file = FileUploadUtil.getServerPath(fileUrl);
        List<ImportPojo> list = ExcelUtil.parseXlsx(file, StudentImportPojo.class.getName());
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        List<String>  gradeNameList= StudentModel.dao.findGradeNameListBySchId(String.valueOf(schId));
        List<ExcelImportErrorPojo> errorMsgList = new ArrayList<>();
        List<StudentImportPojo> validationList = new ArrayList<>();
        String errorExcelUrl = null;
        for (ImportPojo importPojo : list){
            if (importPojo instanceof StudentImportPojo){
            	StudentImportPojo temp = (StudentImportPojo) importPojo;
            	temp.setGradeNames(gradeNameList);
            	temp.setSchId(schId);
                Set<ConstraintViolation<StudentImportPojo>> constraintViolations = validator.validate(temp);
                if(constraintViolations.size()!=0){
                    ExcelImportErrorPojo errorPojo = new ExcelImportErrorPojo();
                    errorPojo.setRowNumber(temp.getRowNumber());
                    for (ConstraintViolation<StudentImportPojo> constraintViolation : constraintViolations){
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
        loginfoModel.set("id", IDKeyUtil.getIDKey()).set("fileName",excelName)
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
	@FuncActionAnnotation(action="/admin/student/download")
    @Before(FuncActionInterceptor.class)
    public void downExcel(){
        renderFile(CommonUtil.getAppProperties("studentExcelPath"));
    }
    
    /**
     * 导出学生信息Excel数据
     */
    @FuncActionAnnotation(action="/admin/student/export")
    @Before(FuncActionInterceptor.class)
    public void downLoadExcel(){
        Map<String,Object> map=queryParams();
        List<StudentModel> dataSource = StudentModel.dao.findstudentList(map);
        CommonUtil.setShowValue2List(dataSource, EnumAndDicDefine.STUDENT_DEFINETABLE);
        String downExcel = CommonUtil.getAppProperties("downExcel");
        String file = PathKit.getWebRootPath()+downExcel+"/"+ CommonUtil.getUUID()+".xls";
        File targetFile = new File(file);
        StudentImportPojo stuImportPojo = new StudentImportPojo();
        ExcelUtil.exportExcel(dataSource, targetFile, stuImportPojo);
        renderFile(targetFile);
    }
}
