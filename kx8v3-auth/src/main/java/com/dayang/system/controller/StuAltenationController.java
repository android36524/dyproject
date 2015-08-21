package com.dayang.system.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dayang.commons.annotation.FuncActionAnnotation;
import com.dayang.commons.controller.AdminBaseController;
import com.dayang.commons.enums.EnumAll;
import com.dayang.commons.pojo.AjaxRetPojo;
import com.dayang.commons.pojo.FieldDefinePojo;
import com.dayang.commons.pojo.JQGridPagePojo;
import com.dayang.commons.util.CommonUtil;
import com.dayang.commons.util.DaYangStaticData;
import com.dayang.commons.util.DictionaryUtil;
import com.dayang.commons.util.EnumAndDicDefine;
import com.dayang.commons.util.EnumUtil;
import com.dayang.commons.util.StaticData;
import com.dayang.conf.interceptor.AuthInterceptor;
import com.dayang.conf.interceptor.FuncActionInterceptor;
import com.dayang.system.model.AlterModel;
import com.dayang.system.model.DictionaryModel;
import com.dayang.system.model.StudentModel;
import com.dayang.system.validator.SubjectValidator;
import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.Db;


/**
 * 类描述：异动控制类
 * <pre>
 * -------------History------------------
 *   DATE                 AUTHOR         VERSION        DESCRIPTION
 *  2015年5月19日              温建军              V01.00.001		      新增内容   
 * </pre>
 * 
 * @author <a href="wenjj@dayanginfo.com">温建军</a>
 */
public class StuAltenationController extends AdminBaseController {
	
	private final static List<FieldDefinePojo> ShowField_Repeat = new ArrayList<FieldDefinePojo>(){
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		{
			add(new FieldDefinePojo("姓名","userName"));
			add(new FieldDefinePojo("性别","sex_showname"));
			add(new FieldDefinePojo("学号","studentNo"));
			add(new FieldDefinePojo("原班级","className"));
			add(new FieldDefinePojo("现班级","toClassName"));
			add(new FieldDefinePojo("异动类别","changeType_showname"));
			add(new FieldDefinePojo("异动时间","beginTime",StaticData.FieldDataType.date));
			add(new FieldDefinePojo("处理状态","status_showname"));
			add(new FieldDefinePojo("处理时间","endTime",StaticData.FieldDataType.date));
			add(new FieldDefinePojo("处理人","creatorName"));
		}
	};
	
	private final static List<FieldDefinePojo> ShowField_Outer = new ArrayList<FieldDefinePojo>(){
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		{
			add(new FieldDefinePojo("姓名","userName"));
			add(new FieldDefinePojo("性别","sex_showname"));
			add(new FieldDefinePojo("学号","studentNo"));
			add(new FieldDefinePojo("原学校","schName"));
			add(new FieldDefinePojo("原班级","className"));
			add(new FieldDefinePojo("现学校","toSchName"));
			add(new FieldDefinePojo("现班级","toClassName"));
			add(new FieldDefinePojo("异动类别","changeType_showname"));
			add(new FieldDefinePojo("异动时间","beginTime",StaticData.FieldDataType.date));
			add(new FieldDefinePojo("申请人","creatorName"));
			add(new FieldDefinePojo("处理状态","status_showname"));
			add(new FieldDefinePojo("处理时间","endTime",StaticData.FieldDataType.date));
			add(new FieldDefinePojo("处理人","editorName"));
		}
	};
	
	private final static List<FieldDefinePojo> ShowField_Other = new ArrayList<FieldDefinePojo>(){
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		{
			add(new FieldDefinePojo("姓名","userName"));
			add(new FieldDefinePojo("性别","sex_showname"));
			add(new FieldDefinePojo("学号","studentNo"));
			add(new FieldDefinePojo("原学校","schName"));
			add(new FieldDefinePojo("原班级","className"));
			add(new FieldDefinePojo("异动类别","changeType_showname"));
			add(new FieldDefinePojo("异动时间","beginTime",StaticData.FieldDataType.date));
			add(new FieldDefinePojo("申请人","creatorName"));
			add(new FieldDefinePojo("处理状态","status_showname"));
			add(new FieldDefinePojo("处理时间","endTime",StaticData.FieldDataType.date));
			add(new FieldDefinePojo("处理人","creatorName"));
		}
	};
	
	
	/**
	 * 异动管理首页
	 */
	@Before(AuthInterceptor.class)
	public void index(){
		this.setAttr("empStatus", DictionaryUtil.getDicListByDicType(StaticData.STUSTATUS_dictionaryType));//状态
		renderJsp("index.jsp");
	}
	
	/**
	 * 学生异动跳转页面
	 */
	public void toAltenation(){
		int flag=getParaToInt("flag");
		String ids = getPara("ids");
		
		String[] aids = ids.split(",");
		long[] lids = new long[aids.length];
		for(int i=0;i<aids.length;i++){
			lids[i]=Long.parseLong(aids[i]);
		}
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("ids", lids);	
	
		List<StudentModel> list = StudentModel.dao.findStudentPage(1,DaYangStaticData.PAGE_MAXROWS,map).getList();
		CommonUtil.setShowValue2List(list, EnumAndDicDefine.STUDENT_DEFINETABLE);
		this.setAttr("stuList", list);		
		if(flag==StaticData.StudentType.inner){			
			this.setAttr("alterList", DictionaryUtil.findDicByParentId(StaticData.ALTERREASON_dictionaryType, StaticData.ALTERREASON_dictionaryType, StaticData.ALTERREASON_STUREPEAT));
			renderJsp("innerAltenation.jsp");
		}else if(flag==StaticData.StudentType.otherAlter){
			this.setAttr("alterList", DictionaryUtil.findDicByParentId(StaticData.ALTERTYPE_dictionaryType,StaticData.ALTERTYPE_dictionaryType,StaticData.StudentType.otherAlter));
			renderJsp("otherAltenation.jsp");
		}else if(flag==StaticData.StudentType.outerAlter){
			renderJsp("applyInfo.jsp");
		}else{			
			renderJsp("repeatInfo.jsp");
		}	
	}
	
	/**
	 * 转班
	 */
	@FuncActionAnnotation(action="/admin/stuAltenation/inner")
	@Before({FuncActionInterceptor.class})
	public void innerAlter(){		
		Long classId= getParaToLong("toClass",0l);
		Long gradeId= getParaToLong("toGrade",0l);
		String ids =getPara("stuIds").toString();
		String[] aids = ids.split(",");
		StringBuffer sql = new StringBuffer("update base_student set gradeId=?,classId=? where id in (");
		final List<Object> parameters = new ArrayList<Object>();	
		parameters.add(gradeId);
		parameters.add(classId);
		for(int i=0;i<aids.length;i++){
			sql.append(i==aids.length-1?"?)":"?,");
			long id = Long.parseLong(aids[i]);
			parameters.add(id);
			AlterModel model =AlterModel.dao.setStuModelById(id);
			model.put("toClassId", getPara("toClass"));
			model.put("toClassName",getPara("className"));
			model.put("toSchId", model.get("schId"));
			model.put("toSchName",  model.get("schName"));
			model.put("flag",EnumAll.AlterFlag.STUDENTFLAG.getValueStr());
			model.put("status",EnumAll.AlterStatus.ALTERFINISHSTATUS.getValueStr());
			model.put("reason", getPara("alterReason"));
			model.put("changeType", StaticData.StudentType.inner);
			model.put("remark",getPara("remark"));
			model.put("beginTime", new Date());
			model.put("endTime", new Date());
			model.put("creator", getLoginUserId());
			model.save();
		}		
		Db.update(sql.toString(),parameters.toArray());	
		renderJson(AjaxRetPojo.newInstance());
	}
	
	/**
	 * 其他异动
	 */
	@FuncActionAnnotation(action="/admin/stuAltenation/otherAlter")
	@Before({FuncActionInterceptor.class})
	public void otherAlter(){		
		String ids =getPara("stuIds").toString();
		String[] aids = ids.split(",");		
		final List<Object> parameters = new ArrayList<Object>();	
		int type =  getParaToInt("alterOtherType");
		int _status= CommonUtil.getStuType2Status(type);
		StringBuffer sql = new StringBuffer("update base_student set status=? where id in (");
		parameters.add(_status);
		for(int i=0;i<aids.length;i++){			
			sql.append(i==aids.length-1?"?)":"?,");
			long id = Long.parseLong(aids[i]);			
			parameters.add(id);
			AlterModel model =AlterModel.dao.setStuModelById(id);			;
			model.put("flag",EnumAll.AlterFlag.STUDENTFLAG.getValueStr());
			model.put("status",EnumAll.AlterStatus.ALTERFINISHSTATUS.getValueStr());			
			model.put("remark",getPara("remark"));
			model.put("changeType",getPara("alterOtherType"));
			model.put("beginTime", new Date());
			model.put("endTime", new Date());
			model.put("creator", getLoginUserId());
			model.save();
		}		
		Db.update(sql.toString(),parameters.toArray());		
		renderJson(AjaxRetPojo.newInstance());
	}
	

	
	/**
	 * 异动列表查询
	 */
	public void list(){
		List<DictionaryModel> list = DictionaryUtil.findDicByParentId(StaticData.ALTERTYPE_dictionaryType,StaticData.ALTERTYPE_dictionaryType,StaticData.StudentType.otherAlter);
		list.add(DictionaryUtil.getDicModel(StaticData.ALTERTYPE_dictionaryType, StaticData.StudentType.inner));
		list.add(DictionaryUtil.getDicModel(StaticData.ALTERTYPE_dictionaryType, StaticData.StudentType.outerAlter));
		this.setAttr("alterList", list);
		this.setAttr("alterStatus",EnumUtil.toEnumList(EnumAll.AlterStatus.ALTERFINISHSTATUS));
		renderJsp("altenationInfo.jsp");		
	}
	
	/**
	 * 异动列表查询
	 */
	public void listAltenation(){
		int pageNumber = getParaToInt("page");
        int pageSize = getParaToInt("rows", DaYangStaticData.PAGE_ROWS);   
        Map<String,Object> m = new HashMap<String,Object>();
        long schId = getParaToLong("schId",0l);
        int changeType = getParaToInt("changeType",0);
        String userName= getPara("userName");
        String status = getPara("status");
        String beginTime= getPara("beginTime");
        String endTime= getPara("endTime");
        m.put("schId",schId);
        m.put("changeType",changeType);
        m.put("userName",userName);
        m.put("status",status);
        m.put("beginTime",beginTime);
        m.put("endTime",endTime);
        m.put("flag", EnumAll.AlterFlag.STUDENTFLAG.getValueStr());
		com.jfinal.plugin.activerecord.Page<AlterModel> p = AlterModel.dao.findAlterPage(pageNumber,pageSize,m);		
		CommonUtil.setShowValue2List(p,EnumAndDicDefine.LOGCHANGE_DEFINETABLE);
		renderJson(JQGridPagePojo.parsePageData(p));
	}
	
	/**
	 * 判断是否能做变动
	 */
	public void isCanAlter(){
		String alterStatus = getPara("alterStatus");
		int alterType = getParaToInt("alterType",0);
		String[] statuses = alterStatus.split(",");
		boolean bl = true;
		for(int i=0;i<statuses.length;i++){
			bl = CommonUtil.isCanStuAlter(alterType, Integer.parseInt(statuses[i]));
			if(!bl) break;
		}		
		AjaxRetPojo p = AjaxRetPojo.newInstance();
		p.setCode(bl?AjaxRetPojo.CODE_SUCCESS:AjaxRetPojo.CODE_FAIL);
		renderJson(p);
	}
	
	/**
	 * 复学
	 */	
	@FuncActionAnnotation(action="/admin/stuAltenation/repeat")
	@Before({FuncActionInterceptor.class})
	public void repeatAlter(){		
		Long classId= getParaToLong("toClass",0l);
		Long gradeId= getParaToLong("toGrade",0l);
		String ids =getPara("stuIds").toString();
		String[] aids = ids.split(",");
		StringBuffer sql = new StringBuffer("update base_student set gradeId=?,classId=?,status=? where id in (");
		final List<Object> parameters = new ArrayList<Object>();	
		parameters.add(gradeId);
		parameters.add(classId);
		parameters.add(StaticData.StudentStatus.inUsed);
		for(int i=0;i<aids.length;i++){
			sql.append(i==aids.length-1?"?)":"?,");
			long id = Long.parseLong(aids[i]);
			parameters.add(id);
			AlterModel model =AlterModel.dao.setStuModelById(id);
			model.put("toClassId", getPara("toClass"));
			model.put("toClassName",getPara("className"));
			model.put("toSchId", model.get("schId"));
			model.put("toSchName",  model.get("schName"));
			model.put("flag",EnumAll.AlterFlag.STUDENTFLAG.getValueStr());
			model.put("status",EnumAll.AlterStatus.ALTERFINISHSTATUS.getValueStr());			
			model.put("changeType", StaticData.StudentType.repeat);
			model.put("remark",getPara("remark"));
			model.put("beginTime", new Date());
			model.put("endTime", new Date());
			model.put("creator", getLoginUserId());
			model.save();
		}		
		Db.update(sql.toString(),parameters.toArray());	
		renderJson(AjaxRetPojo.newInstance());
	}
	
	/**
	 * 异动列表查询
	 */
	public void toAltenationList(){
		List<DictionaryModel> list = DictionaryUtil.findDicByParentId(StaticData.ALTERTYPE_dictionaryType,StaticData.ALTERTYPE_dictionaryType,StaticData.StudentType.otherAlter);
		list.add(DictionaryUtil.getDicModel(StaticData.ALTERTYPE_dictionaryType, StaticData.StudentType.inner));
		list.add(DictionaryUtil.getDicModel(StaticData.ALTERTYPE_dictionaryType, StaticData.StudentType.outerAlter));
		list.add(DictionaryUtil.getDicModel(StaticData.ALTERTYPE_dictionaryType, StaticData.StudentType.repeat));
		this.setAttr("alterList", list);
		this.setAttr("alterStatus",EnumUtil.toEnumList(EnumAll.AlterStatus.ALTERFINISHSTATUS));
		renderJsp("altenationInfo.jsp");		
	}
	
	/**
	 * 显示学生详情
	 */
	public void toShowDetail(){
		long id = getParaToLong("id",0l);
		Map<String,Object> m = new HashMap<String,Object>();
		m.put("id",id);
		m.put("flag",EnumAll.AlterFlag.STUDENTFLAG.getValueStr());
		com.jfinal.plugin.activerecord.Page<AlterModel> p = AlterModel.dao.findAlterPage(1,DaYangStaticData.PAGE_ROWS,m);		
		CommonUtil.setShowValue2List(p,EnumAndDicDefine.LOGCHANGE_DEFINETABLE);
		List<AlterModel> list  = p.getList();
		AlterModel model = list.get(0);
		StudentModel stuModel = model.getStudent();
		model.put("rollCode", stuModel.get("rollCode"));
		model.put("studentNo", stuModel.get("studentNo"));
		model.put("birthDate", stuModel.get("birthDate"));
		model.put("hometown", stuModel.get("hometown"));
		model.put("homeAddr", stuModel.get("homeAddr"));
		this.setAttr("changeModel",model);
		int changeType = model.getInt("changeType");
		if(changeType==StaticData.StudentType.inner || changeType==StaticData.StudentType.repeat){
			this.setAttr("showField",ShowField_Repeat);
		}else if(changeType==StaticData.StudentType.outerAlter){
			this.setAttr("showField",ShowField_Outer);
		}else{
			this.setAttr("showField",ShowField_Other);
		}
		renderJsp("showDetail.jsp");
	}
}

