package com.dayang.system.model;

import com.jfinal.plugin.activerecord.Model;

/**
 * 类描述：学生信息扩展历史表
 * <pre>
 * -------------History------------------
 *   DATE                 AUTHOR         VERSION        DESCRIPTION
 *  2015年5月21日             何意            V01.00.001		      新增内容
 * </pre>
 *
 * @author <a href="mailto:heyi@dayang.com">何意 </a>
 */
public class HisStudentInfoModel extends Model<HisStudentInfoModel> {

    private static final long serialVersionUID = 1L;

    public static final HisStudentInfoModel dao = new HisStudentInfoModel();

}
