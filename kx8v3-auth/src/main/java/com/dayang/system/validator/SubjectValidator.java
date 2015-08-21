package com.dayang.system.validator;

import java.util.HashMap;
import java.util.Map;
import com.dayang.commons.enums.EnumAll;
import com.dayang.commons.pojo.AjaxRetPojo;
import com.dayang.commons.util.CommonUtil;
import com.dayang.system.model.SubjectModel;
import com.jfinal.core.Controller;
import com.jfinal.validate.Validator;

/**
 * 类描述：科目管理验证类
 * <pre>
 * -------------History------------------
 *   DATE                 AUTHOR         VERSION        DESCRIPTION
 *  2015年5月18日              温建军              V01.00.001		      新增内容   
 * </pre>
 * 
 * @author <a href="wenjj@dayanginfo.com">温建军</a>
 */
public class SubjectValidator extends Validator {
	public static final String Key_Speat = "nameSpeatMsg";
	public static final String Key_NotNull = "nameNotNullMsg";
	
	@Override
	protected void validate(Controller c) {
		c.removeAttr(Key_NotNull);
		c.removeAttr(Key_Speat);
		validateRequired("subject.name", Key_NotNull, "名称不能为空");		
		String name = c.getPara("subject.name");
		int flag = c.getParaToInt("subject.flag");
		long notid = c.getParaToLong("subject.id",0l);
		long schId = c.getParaToLong("subject.schId",0l);
		
		Map<String,Object> m = new HashMap<String,Object>();
		m.put("name", name);	
		m.put("notid", notid);
		
		if(flag==EnumAll.SubjectFlag.COMMONFLAG.getValue()){
			m.put("flag", flag);	
		}else{
			m.put("schId", schId);
			m.put("flag", flag);
		}
		SubjectModel sm = SubjectModel.dao.findSubject(m);
		if(!CommonUtil.isEmpty(sm) && sm.get("id")!=null){
			addError(Key_Speat, "名称不能重复");
		}
	}

	@Override
	protected void handleError(Controller c) {
		c.keepModel(SubjectModel.class, "subject");
		AjaxRetPojo ajaxRetPojo = AjaxRetPojo.newInstance();
		ajaxRetPojo.setCode(AjaxRetPojo.CODE_FAIL);
		if(c.getAttr(Key_NotNull)!=null){
			ajaxRetPojo.setMsg(c.getAttr(Key_NotNull).toString());
		}else if(c.getAttr(Key_Speat)!=null){
			ajaxRetPojo.setMsg(c.getAttr(Key_Speat).toString());
		}else{
			ajaxRetPojo.setMsg("提交失败");
		}		
		c.renderJson(ajaxRetPojo);
	}

}
