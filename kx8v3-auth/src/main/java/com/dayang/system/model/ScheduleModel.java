package com.dayang.system.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.dayang.commons.util.CommonUtil;
import com.jfinal.plugin.activerecord.Model;

/**
 * 类描述：排课MODEL
 * <pre>
 * -------------History------------------
 *   DATE                 AUTHOR         VERSION        DESCRIPTION
 *  2015年6月13日              温建军              V01.00.001		      新增内容   
 * </pre>
 * 
 * @author <a href="wenjj@dayanginfo.com">温建军</a>
 */
public class ScheduleModel extends Model<ScheduleModel>{
	 private static final long serialVersionUID = 1L;
	 public static final ScheduleModel dao = new ScheduleModel(); 
	 
	 /**
		 * 根据条件查询user详情
		 * @param param
		 * @return
		 */
	public ScheduleModel findSchedule(Map<String,Object> param) {
		StringBuilder sb = new StringBuilder("select * from busi_schedule where 1=1 ");
		List<Object> list = new ArrayList<Object>();
		CommonUtil.setDefaultPara(param,sb,list,"",null);		
		return ScheduleModel.dao.findFirst(sb.toString(),list.toArray());
	}
}
