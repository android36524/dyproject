package com.dayang.system.model;

import com.dayang.commons.jfinal.kit.ConditionsKit;
import com.dayang.commons.util.CommonUtil;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;
import org.apache.log4j.Logger;

import java.util.Map;

/**
 * 类描述：学生信息扩展model
 * <pre>
 * -------------History------------------
 *   DATE                 AUTHOR         VERSION        DESCRIPTION
 *  2015年5月28日下午3:02:04        何意        		 V01.00.001		 新增内容
 * </pre>
 * 
 * @author <a href="liush@dayanginfo.com">何意  </a>
 */
public class StudentExtModel extends Model<StudentExtModel> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final Logger logger = Logger.getLogger(StudentExtModel.class);
	
	public static final StudentExtModel dao = new StudentExtModel();

	public Map<String, Object> getAttrs() {
		return super.getAttrs();
	}
	

}
