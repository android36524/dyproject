package com.dayang.system.model;

import com.jfinal.plugin.activerecord.Model;

/**
 * 类描述：机构历史表Model
 * <pre>
 * -------------History------------------
 *   DATE                 AUTHOR         VERSION        DESCRIPTION
 *  2015年05月20日            刘建宇      V01.00.001		   新增内容
 * </pre>
 *
 * @author <a href="mailto:liujy@dayanginfo.com">刘建宇</a>
 */
public class HisorgModel extends Model<HisorgModel>{
    public static final HisorgModel dao = new HisorgModel();
}
