package com.dayang.system.model;

import java.util.List;

import com.jfinal.plugin.activerecord.Model;

/**
 * 类描述：节配置MODEL
 * <pre>
 * -------------History------------------
 *   DATE                 AUTHOR         VERSION        DESCRIPTION
 *  2015年6月13日              温建军              V01.00.001		      新增内容   
 * </pre>
 * 
 * @author <a href="wenjj@dayanginfo.com">温建军</a>
 */
public class CfgSectionModel extends Model<CfgSectionModel> {
	 private static final long serialVersionUID = 1L;
	 public static final CfgSectionModel dao = new CfgSectionModel(); 
	 
	 public List<CfgSectionModel> findByScheduleId(long scheduleId){
		 List<CfgSectionModel> list = CfgSectionModel.dao.find("select cs.*,csc.flag flag_class from cfg_section cs LEFT JOIN cfg_sectionclass csc on csc.sectionId=cs.id and csc.scheduleId=? order by cs.seq asc",scheduleId);
		 return list;
	 }
}
