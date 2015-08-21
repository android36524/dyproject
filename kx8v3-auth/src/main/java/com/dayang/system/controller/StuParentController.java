package com.dayang.system.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dayang.commons.annotation.FuncActionAnnotation;
import com.dayang.commons.controller.AdminBaseController;
import com.dayang.commons.enums.EnumAll;
import com.dayang.commons.enums.Sex;
import com.dayang.commons.pojo.AjaxRetPojo;
import com.dayang.commons.pojo.JQGridPagePojo;
import com.dayang.commons.pojo.StuParentExportPojo;
import com.dayang.commons.util.AccountGenerator;
import com.dayang.commons.util.CommonUtil;
import com.dayang.commons.util.DaYangCommonUtil;
import com.dayang.commons.util.DaYangStaticData;
import com.dayang.commons.util.DictionaryUtil;
import com.dayang.commons.util.EnumAndDicDefine;
import com.dayang.commons.util.ExcelUtil;
import com.dayang.commons.util.StaticData;
import com.dayang.conf.interceptor.AuthInterceptor;
import com.dayang.conf.interceptor.FuncActionInterceptor;
import com.dayang.system.model.RstuAndParentModel;
import com.dayang.system.model.StuParentModel;
import com.dayang.system.model.StudentModel;
import com.jfinal.aop.Before;
import com.jfinal.kit.PathKit;
import com.jfinal.plugin.activerecord.Page;

/**
 * 类描述：学生家长管理
 * <pre>
 * -------------History------------------
 *   DATE                 AUTHOR         VERSION        DESCRIPTION
 *  2015年5月22日下午2:43:40        吴杰东             		 V01.00.001		 新增内容   
 * </pre>
 * 
 * @author <a href="wujd@dayanginfo.com">吴杰东</a>
 */
public class StuParentController extends AdminBaseController {
	
	private static AjaxRetPojo pojo = new AjaxRetPojo();
	
	private static Boolean bool = false;
	
	public static final String NOT_EQUAL = "!=";  //不相等
	public static final String EQUAL = "="; //相等

	@Before(AuthInterceptor.class)
	public void index(){
		renderJsp("index.jsp");
	}
	
	/**
	 * 分页查询
	 */
	public void findParentByPage(){
		int pageNumber = getParaToInt("page");
		int pageSize = getParaToInt("rows",DaYangStaticData.PAGE_ROWS);
		Long schId = getParaToLong("schId");
		Long gradeId = getParaToLong("gradeId");
		Long classId = getParaToLong("classId");
		Long studentId = getParaToLong("studentId");
		String name = getTrimParamValue("name");
		String telphone = getTrimParamValue("tel");
		String parentIds = getPara("parentIds");
		boolean isGljz = Boolean.valueOf(getPara("isManageParent"));//管理家长
		Map<String,Object> map= new HashMap<String,Object>();
        map.put("schId",schId);
        map.put("gradeId",gradeId);
        map.put("classId",classId);
        map.put("studentId",studentId);
        map.put("name",name);
        map.put("telphone",telphone);
        map.put("parentIds",parentIds);
        Page<StuParentModel> page=null;
        if(isGljz){
        	page = StuParentModel.findParentBySchId(pageNumber, pageSize, map);
        }else{
        	page = StuParentModel.findParentByPage(pageNumber, pageSize, map);
        }
        CommonUtil.setShowValue2List(page, EnumAndDicDefine.STUDENT_DEFINETABLE);
        renderJson(JQGridPagePojo.parsePageData(page));
	}
	
	/**
	 * 加载修改或新增页面
	 */
	public void toModifyPage(){
		this.setAttr("politicalList", DictionaryUtil.getDicListByDicType(StaticData.POLITICAL_dictionaryType));//政治面貌
		this.setAttr("relationList", DictionaryUtil.getDicListByDicType(StaticData.RELATION_DICTIONARYTYPE));//家属关系
		this.setAttr("guarderName", EnumAll.IsYesOrNot.IsYes.getName());
		this.setAttr("guarderVal",EnumAll.IsYesOrNot.IsYes.getValueStr());
		this.setAttr("notGuarderName", EnumAll.IsYesOrNot.IsNot.getName());
		this.setAttr("notGuarderVal", EnumAll.IsYesOrNot.IsNot.getValue());
		this.setAttr("manName", Sex.MAN.getName()); //性别
		this.setAttr("manVal", Sex.MAN.getValueStr());
		this.setAttr("woManName", Sex.WOMAN.getName());
		this.setAttr("woManVal", Sex.WOMAN.getValueStr());
		long id = getParaToLong("id",0l);
		long schId = getParaToLong("schId");
	    this.setAttr("id", id);
	    this.setAttr("schId", schId);
	    if(id==0l){
		    this.setAttr("account", AccountGenerator.getAccount());
		    this.setAttr("roleId", StaticData.roleType.parentRole);
		    this.setAttr("roleName", StaticData.roleType.parentRoleName);
	    }
		renderJsp("parent-modify.jsp");
	}
	
	/**
	 * 加载管理孩子界面
	 */
	public void toManageStuPage(){
		this.setAttr("relationList", DictionaryUtil.getDicListByDicType(StaticData.RELATION_DICTIONARYTYPE));//家属关系
		long id = getParaToLong("id",0l);
	    this.setAttr("id", id);
		renderJsp("manageStudent.jsp");
	}
	
	/**
	 * 保存学生家长信息
	 */
	@SuppressWarnings("unchecked")
	@FuncActionAnnotation(noIdAction="/admin/stuParent/add",idAction="/admin/stuParent/modify",idName = "stuParent.ID")
	@Before(FuncActionInterceptor.class) 
	public void saveStuParent(){
		String account = getPara("account");
		long roleId = getParaToLong("roleId",0l);
		String students = getPara("relaStudents");
		Long schId = getParaToLong("schId",0l);
		String isExist = getPara("isExist"); // 已有相同记录，是否直接沿用的数据；
		List<JSONObject> list = new ArrayList<JSONObject>();
		if(CommonUtil.isNotEmptyString(students)){
			list = JSON.parseObject(students, ArrayList.class);   
		}
		StuParentModel model = getModel(StuParentModel.class,"stuParent");
		RstuAndParentModel rstuModel = getModel(RstuAndParentModel.class,"relaStu");
		bool = StuParentModel.saveStuParent(account,roleId,Boolean.valueOf(isExist),schId,model, rstuModel, list);
		if(bool){
			renderJson(AjaxRetPojo.newInstance());
		}else{
			pojo.setCode(AjaxRetPojo.CODE_FAIL);
			pojo.setMsg("系统异常，请稍后重试");
			renderJson(pojo);
		}
	}
	
	/**
	 * 保存于家长与学生的关联关系
	 */
	@SuppressWarnings("unchecked")
	@FuncActionAnnotation(action="/admin/stuParent/manage")
	@Before(FuncActionInterceptor.class) 
	public void saveRelaStu(){
		Long parentId = getParaToLong("id",0l);
		Long schId = getParaToLong("schId",0l);
		String students = getPara("relaStudents");
		List<JSONObject> list = new ArrayList<JSONObject>();
		if(CommonUtil.isNotEmptyString(students)){
			list = JSON.parseObject(students, ArrayList.class);   
		}
		bool = StuParentModel.saveRelaStu(schId,parentId, list);
		if(bool){
			renderJson(AjaxRetPojo.newInstance());
		}else{
			pojo.setCode(AjaxRetPojo.CODE_FAIL);
			pojo.setMsg("系统异常，请稍后重试");
			renderJson(pojo);
		}
	}
	
	/**
	 * 根据家长ID查询家长信息
	 */
	public void findStuParentById(){
		Long id = getParaToLong("id",0l);
		Long schId = getParaToLong("schId",0l);
		StuParentModel model = StuParentModel.findStuParentById(id);
		List<StudentModel> list = StuParentModel.findStuByParentId(id,schId,0l,0l);
		List<StuParentModel> parList = new ArrayList<StuParentModel>();
		parList.add(model);
		CommonUtil.setShowValue2List(parList, EnumAndDicDefine.PARENT_DEFINETABLE);
		CommonUtil.setShowValue2List(list, EnumAndDicDefine.RELA_STUDENT);
		model.put("studentList", list);
		renderJson(model);
	}
	
	/**
	 * 根据年级ID查询班级
	 */
	public void findClassByGrade(){
		Long gradeId = getParaToLong("gradeId");
		renderJson(StuParentModel.findClassByGrade(gradeId));
	}
	
	/**
	 * 删除家长信息
	 */
	@FuncActionAnnotation(action="/admin/stuParent/del")
	@Before(FuncActionInterceptor.class) 
	public void deleStuParentInfo() {
		Long schId = getParaToLong("schId", 0l);
		String idStr = getPara("id");
		String accountIds="";
		try {
			String ids= RstuAndParentModel.dao.findParentIds(NOT_EQUAL,schId, idStr);
			accountIds=StuParentModel.dao.findAccountIds(idStr);
			// 不是当前学校的家长ID
			if (!CommonUtil.isEmptyString(ids)) {
				//删除关联关系
				bool = RstuAndParentModel.deleRStuParent(ids, schId);
				String [] pidArr= idStr.split(","); //所有需要删除的家长ID
				String [] idsArr= ids.split(","); //不是当前学校的家长ID
				//取当前学校的ID
				List<String> list=CommonUtil.compare(idsArr,pidArr);
				String delIds=DaYangCommonUtil.list2Str(list);
				if(!CommonUtil.isEmptyString(delIds)){
					//删除当前学校的家长信息及关联信息
					RstuAndParentModel.deleRStuParent(delIds, schId);
					bool = StuParentModel.deleStuParent(delIds);
				}
			} else {
				//删除当前学校的家长信息及关联信息
				RstuAndParentModel.deleRStuParent(idStr, schId);
				bool = StuParentModel.deleStuParent(idStr);
			}
			bool = DaYangCommonUtil.deleteAccountById(accountIds);
			if (bool) {
				renderJson(AjaxRetPojo.newInstance());
			} else {
				pojo.setCode(AjaxRetPojo.CODE_FAIL);
				pojo.setMsg("系统异常，请稍后重试");
				renderJson(pojo);
			}
		} catch (Exception e) {
			pojo.setCode(AjaxRetPojo.CODE_FAIL);
			pojo.setMsg("系统异常，请稍后重试");
			renderJson(pojo);
			e.printStackTrace();
		}
	}
	
	/**
	 * 移除关联学生
	 */
	public void deleStudent(){
		Long schId = getParaToLong("schId",0l);
		Long studentId = getParaToLong("studentId");
		Long parentId = getParaToLong("parentId");
		List<RstuAndParentModel> rstu = RstuAndParentModel.dao.findRstudent_info(NOT_EQUAL,schId, String.valueOf(parentId));
		// 判断该家长在其他学校是否关联信息。有关联就只删除关联信息。为空(无关联),则删除关联关系及家长信息
		if (!rstu.isEmpty()) {
			bool = StuParentModel.deleStudent(schId,String.valueOf(parentId),studentId);
		} else {
			List<RstuAndParentModel> rstuList = RstuAndParentModel.dao.findRstudent_info(EQUAL,schId, String.valueOf(parentId));
			if(rstuList.size()<=1){
				pojo.setCode("-2");
				renderJson(pojo);
				return ;
			}else{
				bool = StuParentModel.deleStudent(schId,String.valueOf(parentId),studentId);
			}
		}
		if(bool){
			renderJson(AjaxRetPojo.newInstance());
		}else{
			pojo.setCode(AjaxRetPojo.CODE_FAIL);
			pojo.setMsg("系统异常，请稍后重试");
			renderJson(pojo);
		}
	}
	
	/**
	 * 根据学生ID查询学生信息
	 */
	public void findStudentById(){
		Long id = getParaToLong("studentId");
		List<StudentModel> list = StuParentModel.findStudentByStuId(id);
		renderJson(list);
	}
	
	/**
	 * 查询家长姓名和手机号码是否存在
	 */
	public void findNameAndTel(){
		long id = getParaToLong("id",0l);
	//	long schId = getParaToLong("schId");
		String name = getTrimParamValue("name");
		String tel = getPara("tel");
		if(CommonUtil.isEmpty(tel)){
			renderJson(AjaxRetPojo.newInstance());
		}else{
			StuParentModel  stuParentInfo = StuParentModel.dao.findStuParent(name,tel,id);
			if(stuParentInfo!=null){
				renderJson(stuParentInfo);
			}else{
				pojo.setCode(AjaxRetPojo.CODE_FAIL);
				renderJson(pojo);
			}
		}
	}
	
	 /**
     * 导出学生家长信息Excel数据
     */
	@FuncActionAnnotation(action="/admin/stuParent/export")
	@Before(FuncActionInterceptor.class) 
    public void downLoadExcel(){
    	Map<String,Object> map= new HashMap<String,Object>();
    	Long schId = getParaToLong("schId");
		Long gradeId = getParaToLong("gradeId");
		Long classId = getParaToLong("classId");
		String name = getTrimParamValue("name");
		String telphone = getTrimParamValue("tel");
	    map.put("schId",schId);
        map.put("gradeId",gradeId);
        map.put("classId",classId);
        map.put("name",name);
        map.put("telphone",telphone);
        List<StuParentModel> dataSource = StuParentModel.dao.findstuParentList(map);
        CommonUtil.setShowValue2List(dataSource, EnumAndDicDefine.PARENT_DEFINETABLE);
		CommonUtil.setShowValue2List(dataSource, EnumAndDicDefine.RELA_STUDENT);
        String downExcel = CommonUtil.getAppProperties("downExcel");
        String file = PathKit.getWebRootPath()+downExcel+"/"+ CommonUtil.getUUID()+".xls";
        File targetFile = new File(file);
        StuParentExportPojo stuImportPojo = new StuParentExportPojo();
        ExcelUtil.exportExcel(dataSource, targetFile, stuImportPojo);
        renderFile(targetFile);
    }
    
    /**
     * 根据家长姓名和手机号码获取学生家长信息
     */
    public void findStuParent(){
		long id = getParaToLong("id",0l);
		String name = getTrimParamValue("name");
		String tel = getPara("tel");
		
		StuParentModel  stuParentInfo = StuParentModel.dao.findStuParent(name,tel,id);
		if(stuParentInfo!=null){
			renderJson(stuParentInfo);
		}
    }
    
    /**
     * 校验该家长在该学校是否存在
     */
    public void validateParent(){
    	String name = getTrimParamValue("name");
		String tel = getPara("tel");
    	Long schId = getParaToLong("schId",0l);
    	StuParentModel  stuParentInfo = StuParentModel.dao.findRstuParent(name,tel,schId);
    	if(stuParentInfo!=null){
			pojo.setCode(AjaxRetPojo.CODE_SUCCESS);
			renderJson(pojo);
		}else{
			pojo.setCode(AjaxRetPojo.CODE_FAIL);
			renderJson(pojo);
		}
    }
	
}
