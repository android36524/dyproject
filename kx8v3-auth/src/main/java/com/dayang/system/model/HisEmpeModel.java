package com.dayang.system.model;

import com.jfinal.plugin.activerecord.Model;

/**
 * 类描述：教育局员工历史表
 * <pre>
 * -------------History------------------
 *   DATE                 AUTHOR         VERSION        DESCRIPTION
 *  2015年5月21日             何意            V01.00.001		      新增内容
 * </pre>
 *
 * @author <a href="mailto:heyi@dayang.com">何意 </a>
 */
public class HisEmpeModel extends Model<HisEmpeModel> {

    public static final HisEmpeModel dao = new HisEmpeModel();
}
