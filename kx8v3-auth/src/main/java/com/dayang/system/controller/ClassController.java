package com.dayang.system.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;

import com.dayang.commons.annotation.FuncActionAnnotation;
import com.dayang.commons.controller.AdminBaseController;
import com.dayang.commons.enums.Status;
import com.dayang.commons.pojo.AjaxRetPojo;
import com.dayang.commons.pojo.JQGridPagePojo;
import com.dayang.commons.util.CommonUtil;
import com.dayang.commons.util.DaYangStaticData;
import com.dayang.commons.util.DictionaryUtil;
import com.dayang.commons.util.EnumAndDicDefine;
import com.dayang.commons.util.IDKeyUtil;
import com.dayang.commons.util.StaticData;
import com.dayang.conf.interceptor.AuthInterceptor;
import com.dayang.conf.interceptor.FuncActionInterceptor;
import com.dayang.system.model.ClassModel;
import com.dayang.system.model.DeptModel;
import com.dayang.system.model.EmpeModel;
import com.dayang.system.model.GradeModel;
import com.dayang.system.model.HisClassModel;
import com.dayang.system.model.StudentModel;
import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.Page;

/**
 * 类描述：学校班级信息管理Controller
 * <pre>
 * -------------History------------------
 *   DATE                 AUTHOR         VERSION        DESCRIPTION
 *  2015年05月23日           张维      V01.00.001		   新增内容
 * </pre>
 *
 * @author <a href="mailto:zhangwei@dayanginfo.com">张维</a>
 */
public class ClassController extends AdminBaseController {

	@Before(AuthInterceptor.class)
	public void index(){
		this.setAttr("fisrtMenu", "学校管理");
        this.setAttr("secendMenu", "学校班级管理");
        renderJsp("index.jsp");
	}
	
	/**
	 * 分页显示班级列表
	 */
	public void listClassByPage(){
		int pageNumber = getParaToInt("page");
        int pageSize = getParaToInt("rows", DaYangStaticData.PAGE_ROWS);
        
        //获取页面查询条件
        String schId = getPara("schId");
        String gradeId = getPara("gradeId");
        String className = getTrimParamValue("className");
        long classId = getParaToLong("classId",0l);
        Map<String,Object> map= new HashMap<String,Object>();
        map.put("gradeId",gradeId);
        map.put("className",className);
        map.put("schId",schId);
        map.put("id",classId);
		com.jfinal.plugin.activerecord.Page<ClassModel> classPage = 
				ClassModel.findClassByPage(pageNumber,
				pageSize, map);
		
		renderJson(JQGridPagePojo.parsePageData(classPage));
	}
	
	/**
	 * 初始化新增或者编辑页面
	 * */
	public void goAdd(){
		this.initPage();
		renderJsp("class-modify.jsp");
	}
	
	/**
	 * 初始化页面需要的数据
	 */
	private void initPage(){
		long schId=getParaToLong("schId");
		// 初始化年级列表
		 List<GradeModel> gradeList = StudentModel.dao.findGradeBySchId(schId);
	     this.setAttr("gradeList", gradeList);
	     //学校ID 保存到表单隐藏域
	     this.setAttr("schId", schId);
	     //初始化文理类型列表
	     this.setAttr("subjectTypeList", DictionaryUtil.getDicListByCode(StaticData.SUBJECT_TYPE));
	     
	   //如果不为空则是更新操作，传入到前台
    	long _classId = getParaToLong("_classId", 0l);
    	if(_classId != 0l){
    		this.setAttr("_classId", _classId);
    	}
	}
	
	/**
	 * 查询老师列表
	 */
	public void getTeacherList(){
		int pageNumber = getParaToInt("page");
        int pageSize = getParaToInt("rows", DaYangStaticData.PAGE_ROWS);
        String searchBy = getPara("searchBy");
        String searchText = getPara("searchText");
        String deptId = getPara("deptId");
        long schId=getParaToLong("schId");
        Page<EmpeModel> empPage=ClassModel.dao.findTeacherList(pageNumber, pageSize, searchBy,searchText,deptId,schId);
		CommonUtil.setShowValue2List(empPage, EnumAndDicDefine.CLASS_DEFINETABLE);
		renderJson(JQGridPagePojo.parsePageData(empPage));
	}
	
	/**
	 * 查询老师时候，初始化部门下拉列表
	 */
	public void initDept(){
		long schId=getParaToLong("schId");
		List<DeptModel>	deptList =  
				DeptModel.dao.findDeptNameBySchId(schId);
		renderJson(deptList);
	}
	
	/**
	 * 增加班级信息
	 */
	@FuncActionAnnotation(noIdAction="/admin/class/add",idAction="/admin/class/modify",idName = "class.id")
    @Before(FuncActionInterceptor.class)
	public void add(){
		ClassModel m = getModel(ClassModel.class, "class");
		
		if(m.getLong("id") != null){
			m.update();
			
		}else{
			long idKey = IDKeyUtil.getIDKey();
			m.set("id", idKey);
			m.set("status", Status.ENABLE.getValue());
			m.set("creator",getLoginUserId());
			m.set("createTime", new Date());
			m.save();
		}
		renderJson(AjaxRetPojo.newInstance());
	}
	
	/**
	 * 删除当前班级
	 */
	@Before(FuncActionInterceptor.class)
	public void del(){
		String _classId = getPara("_classId");
		//TODO 如果该班级下面关联了学生数据，则不允许删除
		List<StudentModel> stuList = ClassModel.dao.findStuByClassId(_classId);
		if(CollectionUtils.isNotEmpty(stuList)){
			AjaxRetPojo ret = AjaxRetPojo.newInstance();
			ret.setCode(AjaxRetPojo.CODE_FAIL);
			ret.setMsg("请删除该班级下所有学生信息再重复此操作!");
			renderJson(ret);
			return ;
		}
		//TODO 删除动作，copy记录到历史表，删除原表的数据
		ClassModel model = ClassModel.dao.findClassById(Long.valueOf(_classId));
		if(null != model){
			HisClassModel hisModel = new HisClassModel();
			Set<Entry<String, Object>> set = model.getAttrsEntrySet();
			for (Entry<String, Object> entry : set) {
				hisModel.set(entry.getKey(), entry.getValue());
			}
			hisModel.save();
			
			model.deleteById(_classId);
		}
		renderJson(AjaxRetPojo.newInstance());
	}
	
	/**
	 * 设置班主任
	 */
	@FuncActionAnnotation(action="/admin/class/set")
	@Before(FuncActionInterceptor.class)
	public void setHteacher(){
		//更改班主任,一个班级只能有一个班主任
		long classId = getParaToLong("_classId");
		long classHeadId = getParaToLong("classHeadId");
		
		if(!CommonUtil.isEmpty(classHeadId)){
			ClassModel.updateClassHead(classHeadId,classId);
		}
		renderJson(AjaxRetPojo.newInstance());
	}
	
	/**
	 * 修改班级前初始化页面数据
	 */
	public void queryClassById(){
		long classId=getParaToLong("_classId");
		renderJson(ClassModel.dao.findClassByClassId(classId));
	}
	
	/**
	 * 根据年级查询班级
	 */
	public void findClassByGradeId(){
		Long gradeId = getParaToLong("gradeId");
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("gradeId", gradeId);		
		renderJson(ClassModel.dao.findClassModel(param));
	}
	
	/**
	 * 根据年级查询历史班级
	 */
	public void findHisClassByGradeId(){
		Long gradeId = getParaToLong("gradeId");
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("gradeId", gradeId);		
		renderJson(HisClassModel.dao.findHisClassModel(param));
	}
	
	
	
}
