package com.dayang.system.model;

import com.jfinal.plugin.activerecord.Model;
import org.apache.log4j.Logger;

/**
 * 类描述：学生信息扩展
 * <pre>
 * -------------History------------------
 *   DATE                 AUTHOR         VERSION        DESCRIPTION
 *  2015年5月31日             吴杰东            V01.00.001		      新增内容
 * </pre>
 *
 * @author <a href="mailto:wujd@dayanginfo.com">何意 </a>
 */
public class StudentInfoModel extends Model<StudentInfoModel> {
    private static final long serialVersionUID = 1L;

    public static final StudentInfoModel dao = new StudentInfoModel();

    private static Logger logger = Logger.getLogger(StudentInfoModel.class);

}
