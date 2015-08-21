package com.dayang.system.validator;

import com.dayang.commons.enums.EnumAll;
import com.dayang.commons.pojo.AjaxRetPojo;
import com.dayang.commons.util.CommonUtil;
import com.dayang.system.model.SubjectModel;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.validate.Validator;

import java.util.List;

/**
 * 类描述：科目删除管理验证类
 * <pre>
 * -------------History------------------
 *   DATE                 AUTHOR         VERSION        DESCRIPTION
 *  2015年5月20日              温建军              V01.00.001		      新增内容   
 * </pre>
 * 
 * @author <a href="wenjj@dayanginfo.com">温建军</a>
 */
public class SubjectDelValidator extends Validator {
	public static final String Key_used = "subjectUsed";
	public static final String Key_other = "otherCannotDel";
	@Override
	protected void validate(Controller c) {
		c.removeAttr("Key_used");	
		c.removeAttr("Key_other");
		long id = c.getParaToLong("id");		
		int flag = c.getParaToInt("flag");
		String code = c.getPara("code");
		if(flag==EnumAll.SubjectFlag.SCHOOLFLAG.getValue()){
			if(!code.equals("KMQT")){
				addError("Key_other", "学校科目只能删除科目类别为其他的科目");
			}
		}		
		List<?> list = Db.query("select * from base_subject where subjectId=?",id);
		if(!CommonUtil.isEmptyCollection(list)){
			addError("Key_used", "该科目无法删除");
		}
		list = Db.query("select * from r_gradesubject where subjectId=?",id);
		if(!CommonUtil.isEmptyCollection(list)){
			addError("Key_used", "该科目无法删除");
		}
		list = Db.query("select * from base_schoolbook where subjectId=?",id);
		if(!CommonUtil.isEmptyCollection(list)){
			addError("Key_used", "该科目无法删除");
		}		
	}
	
	@Override
	protected void handleError(Controller c) {
		c.keepModel(SubjectModel.class, "subject");
		AjaxRetPojo ajaxRetPojo = AjaxRetPojo.newInstance();
		ajaxRetPojo.setCode(AjaxRetPojo.CODE_FAIL);
		if(c.getAttr("Key_used")!=null){
			ajaxRetPojo.setMsg(c.getAttr("Key_used").toString());
		}else if(c.getAttr("Key_other")!=null){
			ajaxRetPojo.setMsg(c.getAttr("Key_other").toString());
		}		
		c.renderJson(ajaxRetPojo);
	}
}
