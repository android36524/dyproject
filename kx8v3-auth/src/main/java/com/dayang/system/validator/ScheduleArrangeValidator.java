package com.dayang.system.validator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.dayang.commons.pojo.AjaxRetPojo;
import com.dayang.commons.util.CommonUtil;
import com.dayang.system.model.CfgSectionModel;
import com.dayang.system.model.CfgWeekModel;
import com.dayang.system.model.ClassModel;
import com.dayang.system.model.ScheduleDetailModel;
import com.dayang.system.model.SubjectModel;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.validate.Validator;

/**
 * 排课验证类
 * <pre>
 * -------------History------------------
 *   DATE                 AUTHOR         VERSION        DESCRIPTION
 *  2015年6月16日              温建军              V01.00.001		      新增内容   
 * </pre>
 * 
 * @author <a href="wenjj@dayanginfo.com">温建军</a>
 */
public class ScheduleArrangeValidator extends Validator{
	
	public static final String Key_used = "teacherUsed";

	@Override
	protected void validate(Controller c) {
		c.removeAttr(Key_used);	
		long scheduleId = c.getParaToLong("scheduleId",0l);	
		String subjectList = c.getPara("subjectList");
		long classId = c.getParaToLong("classId",0l);
		Map<String,Object> param = new HashMap<String,Object>();
		if(classId>0){
			param.put("relaClassId", classId);
		}
		if(scheduleId>0){
			param.put("notScheduleId", scheduleId);
		}
		List<CfgWeekModel> weekList =CfgWeekModel.dao.findAll();
		List<CfgSectionModel> sectionList =CfgSectionModel.dao.findByScheduleId(scheduleId);
	
		List<ScheduleDetailModel> listModel = ScheduleDetailModel.dao.findList(param);
		List<? extends Map> list = JSON.parseArray(subjectList, Map.class);
		for(Map m :list){
			int weekId = Integer.parseInt(m.get("weekId").toString())	;
			int sectionId = Integer.parseInt(m.get("sectionId").toString())	;
			long teacherId = Long.parseLong(m.get("teacherId").toString());
			for(ScheduleDetailModel model:listModel){
				if(weekId==model.getInt("weekId") && sectionId==model.getInt("sectionId") && teacherId==model.getLong("teacherId")){
					String weekName = "";
					String sectionName = "";
					for(CfgWeekModel week:weekList){
						if(week.getInt("id")==weekId){
							weekName = week.getStr("name");
							break;
						}
					}
					for(CfgSectionModel section:sectionList){
						if(section.getInt("id")==weekId){
							sectionName = section.getStr("name");
							break;
						}
					}
					addError(Key_used, weekName+" "+sectionName+" 老师："+model.get("teaName")+" 已经排课，请重新排课");
					break;
				}
			}
		}
	}
	
	@Override
	protected void handleError(Controller c) {
		c.keepModel(ScheduleDetailModel.class, "scheduleDetail");
		AjaxRetPojo ajaxRetPojo = AjaxRetPojo.newInstance();
		ajaxRetPojo.setCode(AjaxRetPojo.CODE_FAIL);
		if(c.getAttr(Key_used)!=null){
			ajaxRetPojo.setMsg(c.getAttr(Key_used).toString());
		}
		c.renderJson(ajaxRetPojo);
	}
}
