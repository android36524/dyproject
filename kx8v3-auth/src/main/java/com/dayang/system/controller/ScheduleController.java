package com.dayang.system.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.dayang.commons.annotation.FuncActionAnnotation;
import com.dayang.commons.controller.AdminBaseController;
import com.dayang.commons.enums.EnumAll;
import com.dayang.commons.enums.Status;
import com.dayang.commons.pojo.AjaxRetPojo;
import com.dayang.commons.util.CommonUtil;
import com.dayang.commons.util.EnumUtil;
import com.dayang.commons.util.IDKeyUtil;
import com.dayang.conf.interceptor.AuthInterceptor;
import com.dayang.conf.interceptor.FuncActionInterceptor;
import com.dayang.system.model.CfgSectionModel;
import com.dayang.system.model.CfgWeekModel;
import com.dayang.system.model.ClassModel;
import com.dayang.system.model.ClassTeacherNameModel;
import com.dayang.system.model.ScheduleDetailModel;
import com.dayang.system.model.ScheduleModel;
import com.dayang.system.model.SemesterModel;
import com.dayang.system.validator.ScheduleArrangeValidator;
import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.Db;

/**
 * 类描述：排课管理
 * <pre>
 * -------------History------------------
 *   DATE                 AUTHOR         VERSION        DESCRIPTION
 *  2015年6月13日              温建军              V01.00.001		      新增内容   
 * </pre>
 * 
 * @author <a href="wenjj@dayanginfo.com">温建军</a>
 */
public class ScheduleController extends AdminBaseController  {
	
	/**
	 * 排课管理
	 */
	@Before(AuthInterceptor.class)
	public void index(){
		renderJsp("index.jsp");
	}
	
	/**
	 * 显示排课界面
	 */
	public void toArrange(){		
		long classId = getParaToLong("classId",0l);
		long schId = getParaToLong("schId",0l);
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("schId", schId);
		map.put("isCur", EnumAll.IsYesOrNot.IsYes.getValueStr());
		SemesterModel sm =  SemesterModel.dao.findSemester(map);
		if(CommonUtil.isEmpty(sm)){
			AjaxRetPojo p = AjaxRetPojo.newInstance();
			p.setCode(AjaxRetPojo.CODE_FAIL);
			p.setMsg("请先设置学校的当前学期");
			renderJson(p);	
			return;
		}
		this.setAttr("curSemester", sm);
		
		map = new HashMap<String,Object>();
		map.put("classId", classId);
		map.put("semesterId", sm.getLong("id"));
		map.put("status", Status.ENABLE.getValueStr());
		ScheduleModel scheduleModel = ScheduleModel.dao.findSchedule(map);
		long sheduleId = 0l;
		sheduleId =scheduleModel!=null?scheduleModel.getLong("id"):0l; 
		this.setAttr("weekList", CfgWeekModel.dao.findAll());
		this.setAttr("classInfo", ClassModel.dao.findClassByClassId(classId));
		this.setAttr("sectionList", CfgSectionModel.dao.findByScheduleId(sheduleId));
		this.setAttr("scheduleId", sheduleId);
		this.setAttr("timeList", EnumUtil.toEnumList(EnumAll.TimeNode.Afternoon));
		if(sheduleId>0){
			map = new HashMap<String,Object>();
			map.put("scheduleId", sheduleId);
			this.setAttr("detailList", ScheduleDetailModel.dao.findList(map));
		}
		int flag =getParaToInt("flag",0);
		if(flag>0){
			renderJsp("showArrange.jsp");
		}else{
			renderJsp("arrange.jsp");
		}		
	}	

	/**
	 * 显示排课
	 */
	public void toSelectSubject(){
		long classId = getParaToLong("classId",0l);
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("classId", classId);
		List<ClassTeacherNameModel> list = ClassTeacherNameModel.dao.findList(param);
		this.setAttr("subList",list);
		if(CommonUtil.isEmptyCollection(list)){
			AjaxRetPojo p = AjaxRetPojo.newInstance();
			p.setCode(AjaxRetPojo.CODE_FAIL);
			p.setMsg("请给该班设置任课老师");
			renderJson(p);	
			return;
		}
		renderJsp("selectSubject.jsp");
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@FuncActionAnnotation(action="/admin/schedule/arrange")
    @Before({ScheduleArrangeValidator.class,FuncActionInterceptor.class}) 
	public void saveArrange(){		
		String subjectList = getPara("subjectList");
		String hdSection = getPara("hdSection");
		String showSection = getPara("showSection");
		
		long classId = getParaToLong("classId",0l);
		long semesterId = getParaToLong("semesterId",0l);
		long scheduleId = getParaToLong("scheduleId",0l);
		
		// 保存排课表
		if(scheduleId<=0){
			Db.update("update busi_schedule set status=? where classId=?",Status.DISABLED.getValueStr(),classId);
			ScheduleModel sm = new ScheduleModel();
			scheduleId = IDKeyUtil.getIDKey();
			sm.put("id", scheduleId);
			sm.put("classId", classId);
			sm.put("semesterId", semesterId);
			sm.put("status", Status.ENABLE.getValueStr());
			sm.put("creator", getLoginUserId());
			sm.put("createTime",  new Date());
			sm.save();
		}
		Map<String,Object> map = new HashMap<String,Object>();
		List<? extends Map> list = JSON.parseArray(subjectList, map.getClass());
		Iterator it =list.iterator();
		Db.update("delete from busi_scheduledetail where scheduleId=?",scheduleId);
		while(it.hasNext()){
			Map<String,Object> m = (Map<String,Object>)it.next();
			ScheduleDetailModel model = new ScheduleDetailModel();
			model.put("id",  IDKeyUtil.getIDKey());
			model.set("weekId", m.get("weekId"));
			model.set("scheduleId", scheduleId);
			model.set("sectionId", m.get("sectionId"));
			model.set("subjectId", m.get("subjectId"));
			model.set("teacherId", m.get("teacherId"));
			model.save();
		}
		//保存隐藏的节
		List<Long> listHd = JSON.parseArray(hdSection,Long.class);
		it =listHd.iterator();
		Db.update("delete from cfg_sectionclass where scheduleId=?",scheduleId);
		while(it.hasNext()){
			long sectionId = (long)it.next();
			CfgSectionModel model = new CfgSectionModel();
			model.put("id",  IDKeyUtil.getIDKey());
			model.set("scheduleId", scheduleId);
			model.set("sectionId", sectionId);
			model.set("flag", EnumAll.IsShowOrHidden.IsHidden.getValueStr());			
			model.save();
		}
		// 保存显示的节
		List<Long> listShow = JSON.parseArray(showSection,Long.class);
		it =listShow.iterator();
		while(it.hasNext()){
			long sectionId = (long)it.next();
			CfgSectionModel model = new CfgSectionModel();
			model.put("id",  IDKeyUtil.getIDKey());
			model.set("scheduleId", scheduleId);
			model.set("sectionId", sectionId);
			model.set("flag", EnumAll.IsShowOrHidden.IsShow.getValueStr());			
			model.save();
		}
		renderJson(AjaxRetPojo.newInstance());
	}
	
	/**
	 * 验证老师是否在某节已经排课
	 */
	public void validatorTeaIsAssigned(){
		long teaId = getParaToLong("teaId",0l);
		long scheduleId= getParaToLong("scheduleId",0l);
		int weekId =  getParaToInt("weekId",0);
		int sectionId =  getParaToInt("sectionId",0);
		if(teaId==0l || weekId==0 || sectionId==0){
			AjaxRetPojo p = AjaxRetPojo.newInstance();
			p.setCode(AjaxRetPojo.CODE_FAIL);
			p.setMsg("传入的参数不对");
			renderJson(p);	
		}
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("teacherId", teaId);
		map.put("weekId", weekId);
		map.put("sectionId", sectionId);
		map.put("notScheduleId", scheduleId);
		List<ScheduleDetailModel> list = ScheduleDetailModel.dao.findList(map);
		if(CommonUtil.isEmptyCollection(list)){
			renderJson(AjaxRetPojo.newInstance());
		}else{
			AjaxRetPojo p = AjaxRetPojo.newInstance();
			p.setCode(AjaxRetPojo.CODE_FAIL);
			p.setMsg("对不起，该教师已在当前节次排过课");
			renderJson(p);	
		}
	}
}
