package com.dayang.demo.validator;

import com.jfinal.core.Controller;
import com.jfinal.validate.Validator;

/**
 * 类描述：
 * <pre>
 * -------------History------------------
 *   DATE                 AUTHOR         VERSION        DESCRIPTION
 *  2015年1月1日              张才胜               V01.00.001		      新增内容   
 * </pre>
 * 
 * @author <a href="mailto:zcslsf@gmail.com">张才胜</a>
 */
public class LoginValidator extends Validator {

	@Override
	protected void validate(Controller c) {
		validateRequired("name", "nameMsg", "请输入用户名");
		validateRequired("pwd", "pwdMsg", "请输入密码");
	}

	@Override
	protected void handleError(Controller c) {
		c.keepPara("name");
		c.renderJsp("/demo/login.jsp");
	}

}
