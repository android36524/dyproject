package com.dayang.system.model;

import com.jfinal.plugin.activerecord.Model;

/**
 * 类描述：教育局部门历史表
 * <pre>
 * -------------History------------------
 *   DATE                 AUTHOR         VERSION        DESCRIPTION
 *  2015年5月21日             何意            V01.00.001		      新增内容
 * </pre>
 *
 * @author <a href="mailto:heyi@dayang.com">何意 </a>
 */
public class HisDeptModel extends Model<HisDeptModel> {

    private static final long serialVersionUID = 1L;

    public static final HisDeptModel dao = new HisDeptModel();
}
