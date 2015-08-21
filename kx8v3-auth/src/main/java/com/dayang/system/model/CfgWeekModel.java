package com.dayang.system.model;

import java.util.List;

import com.jfinal.plugin.activerecord.Model;

/**
 * 类描述：周配置MODEL
 * <pre>
 * -------------History------------------
 *   DATE                 AUTHOR         VERSION        DESCRIPTION
 *  2015年6月13日              温建军              V01.00.001		      新增内容   
 * </pre>
 * 
 * @author <a href="wenjj@dayanginfo.com">温建军</a>
 */
public class CfgWeekModel extends Model<CfgWeekModel> {
	private static final long serialVersionUID = 1L;
	public static final CfgWeekModel dao = new CfgWeekModel(); 
	
	public List<CfgWeekModel> findAll(){
		List<CfgWeekModel> list = CfgWeekModel.dao.find("select * from cfg_week order by seq asc");
		 return list;
	}
}
