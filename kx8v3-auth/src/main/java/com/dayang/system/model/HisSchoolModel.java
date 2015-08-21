package com.dayang.system.model;

import com.jfinal.plugin.activerecord.Model;

/**
 * 类描述：学校历史基本信息
 * <pre>
 * -------------History------------------
 *   DATE                 AUTHOR         VERSION        DESCRIPTION
 *  2015年05月22日            张维      V01.00.001		   新增内容
 * </pre>
 *
 * @author <a href="mailto:zhangwei@dayanginfo.com">张维</a>
 */
public class HisSchoolModel extends Model<HisSchoolModel> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final HisSchoolModel dao = new HisSchoolModel();
}
