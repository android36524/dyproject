package com.dayang.system.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dayang.commons.pojo.QueryDefineParaPojo;
import com.dayang.commons.util.CommonUtil;
import com.jfinal.plugin.activerecord.Model;

/**
 * 类描述：排课详情MODEL
 * <pre>
 * -------------History------------------
 *   DATE                 AUTHOR         VERSION        DESCRIPTION
 *  2015年6月13日              温建军              V01.00.001		      新增内容   
 * </pre>
 * 
 * @author <a href="wenjj@dayanginfo.com">温建军</a>
 */
public class ScheduleDetailModel extends Model<ScheduleDetailModel> {
	 private static final long serialVersionUID = 1L;
	 public static final ScheduleDetailModel dao = new ScheduleDetailModel();  	 
	 public static final Map<String, QueryDefineParaPojo> DefineMap= new HashMap<String,QueryDefineParaPojo>(){
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		{
			put("notScheduleId",new QueryDefineParaPojo("notScheduleId","scheduleId","<>"));			
		}
	};
	
	public static final List<String> ingoreMap= new ArrayList<String>(){
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		{
			add("relaClassId");			
		}
	};
	
    public List<ScheduleDetailModel> findList(Map<String,Object> param){
    	StringBuilder sb = new StringBuilder("SELECT	rct.*, s.`name` subjectName,	e.`name` teaName FROM	busi_scheduledetail rct ");
	   	sb.append(" LEFT JOIN base_subject s ON s.id = rct.subjectId ");
	   	sb.append(" LEFT JOIN base_employee e ON e.id = rct.teacherId ");    
	   	sb.append(" where 1=1 ");
	   	List<Object> list = new ArrayList<Object>();
	   	CommonUtil.setDefaultPara(param,sb,list,"",DefineMap,ingoreMap);
	   	if(!CommonUtil.isEmpty(param.get("relaClassId"))){
	   		sb.append(" AND rct.teacherId in (select r.teacherId from r_classteacherinfo r where r.classId=?) ");
	   		list.add(param.get("relaClassId"));
	   	}
	   	return ScheduleDetailModel.dao.find(sb.toString(),list.toArray());
   }
}
