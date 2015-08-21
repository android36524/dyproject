package com.dayang.system.validator;

import java.util.List;
import com.dayang.commons.pojo.AjaxRetPojo;
import com.dayang.commons.util.CommonUtil;
import com.dayang.system.model.SubjectModel;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.validate.Validator;

/**
 * 类描述：年级删除管理验证类
 * <pre>
 * -------------History------------------
 *   DATE                 AUTHOR         VERSION        DESCRIPTION
 *  2015年5月20日              温建军              V01.00.001		      新增内容   
 * </pre>
 * 
 * @author <a href="wenjj@dayanginfo.com">温建军</a>
 */
public class GradeDelValidator extends Validator{
	public static final String Key_used = "gradeUsed";
	@Override
	protected void validate(Controller c) {
		c.removeAttr(Key_used);	
		long id = c.getParaToLong("id");			
		List<?> list = Db.query("select * from base_grade where gradeId=?",id);
		if(!CommonUtil.isEmptyCollection(list)){
			addError(Key_used, "该年级在学校中有引用禁止删除！");
		}		
		list = Db.query("select * from base_class where gradeId=?",id);
		if(!CommonUtil.isEmptyCollection(list)){
			addError(Key_used, "该年级在班级中有引用禁止删除！");
		}		
		list = Db.query("select * from base_student where gradeId=?",id);
		if(!CommonUtil.isEmptyCollection(list)){
			addError(Key_used, "该年级在学生中有引用禁止删除！");
		}		
	}
	
	@Override
	protected void handleError(Controller c) {
		c.keepModel(SubjectModel.class, "subject");
		AjaxRetPojo ajaxRetPojo = AjaxRetPojo.newInstance();
		ajaxRetPojo.setCode(AjaxRetPojo.CODE_FAIL);
		if(c.getAttr(Key_used)!=null){
			ajaxRetPojo.setMsg(c.getAttr(Key_used).toString());
		}	
		c.renderJson(ajaxRetPojo);
	}
}
