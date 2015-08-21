package com.dayang.system.validator;

import java.util.HashMap;
import java.util.Map;

import com.dayang.commons.enums.EnumAll;
import com.dayang.commons.pojo.AjaxRetPojo;
import com.dayang.commons.util.CommonUtil;
import com.dayang.system.model.GradeModel;
import com.jfinal.core.Controller;
import com.jfinal.validate.Validator;

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
public class GradeValidator extends Validator {
	public static final String Key_Speat = "nameSpeatMsg";
	public static final String Key_NotNull = "nameNotNullMsg";
	public static final String KeyCode_Speat = "codeNotNullMsg";
	@Override
	protected void validate(Controller c) {
		c.removeAttr(Key_NotNull);
		c.removeAttr(Key_Speat);
		c.removeAttr(KeyCode_Speat);
		validateRequired("grade.name", Key_NotNull, "名称不能为空");		
		String name = c.getPara("grade.name");
		int flag = c.getParaToInt("grade.flag");
		long notid = c.getParaToLong("grade.id",0l);
		long schId = c.getParaToLong("grade.schId",0l);		
		Map<String,Object> m = new HashMap<String,Object>();
		m.put("name", name);	
		m.put("notid", notid);		
		if(flag==EnumAll.GradeFlag.COMMONFLAG.getValue()){
			m.put("flag", flag);	
		}else{
			m.put("schId", schId);
		}
		GradeModel sm = GradeModel.dao.findGradeModel(m);
		if(!CommonUtil.isEmpty(sm) && sm.get("id")!=null){
			addError(Key_Speat, "名称不能重复");
		}
		m.remove("name");
		m.put("code",c.getPara("grade.code"));
		sm = GradeModel.dao.findGradeModel(m);
		if(!CommonUtil.isEmpty(sm) && sm.get("id")!=null){
			addError(KeyCode_Speat, "添加失败，年级编码重复");
		}
	}

	@Override
	protected void handleError(Controller c) {
		c.keepModel(GradeModel.class, "grade");
		AjaxRetPojo ajaxRetPojo = AjaxRetPojo.newInstance();
		ajaxRetPojo.setCode(AjaxRetPojo.CODE_FAIL);
		if(c.getAttr(Key_NotNull)!=null){
			ajaxRetPojo.setMsg(c.getAttr(Key_NotNull).toString());
		}else if(c.getAttr(Key_Speat)!=null){
			ajaxRetPojo.setMsg(c.getAttr(Key_Speat).toString());
		}else if(c.getAttr(KeyCode_Speat)!=null){
			ajaxRetPojo.setMsg(c.getAttr(KeyCode_Speat).toString());
		}else{
			ajaxRetPojo.setMsg("提交失败");
		}		
		c.renderJson(ajaxRetPojo);
	}
}
