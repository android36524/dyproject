package com.dayang.system.model;

import java.util.Map;

import com.jfinal.plugin.activerecord.Model;
import org.apache.log4j.Logger;

/**
 * 类描述：教育局员工信息扩展
 * <pre>
 * -------------History------------------
 *   DATE                 AUTHOR         VERSION        DESCRIPTION
 *  2015年5月18日             何意            V01.00.001		      新增内容
 * </pre>
 *
 * @author <a href="mailto:heyi@dayang.com">何意 </a>
 */
public class EduInfoModel extends Model<EduInfoModel> {
    private static final long serialVersionUID = 1L;

    public static final EduInfoModel dao = new EduInfoModel();

    private static Logger logger = Logger.getLogger(EduInfoModel.class);
    
    public Map<String, Object> getAttrs() {
		return super.getAttrs();
	}

}
