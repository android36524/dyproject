package com.dayang.commons.controller;

import com.dayang.cas.pojo.AccountPojo;
import com.dayang.cas.util.LoginInfoUtil;
import com.dayang.conf.interceptor.AdminInterceptor;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;

/**
 * 类描述：管理后台的基类
 * <pre>
 * -------------History------------------
 *   DATE                 AUTHOR         VERSION        DESCRIPTION
 *  2015-7-9               张才胜               V01.00.001		      新增内容   
 * </pre>
 * 
 * @author <a href="mailto:zhangcs@dayanginfo.com">张才胜</a>
 */
@Before(AdminInterceptor.class)
public class AdminBaseController extends Controller{
	
	/**
	 * 获取用户登录信息
	 * @return
	 */
	public AccountPojo getLoginUser(){
		return LoginInfoUtil.getAccountInfo();
	}
	
	/**
	 * 获取用户登录编号
	 * @return
	 */
	public long getLoginUserId(){
		AccountPojo accountPojo = getLoginUser();
		return accountPojo==null? 0l : accountPojo.getId();
	}

	/**
	 * 获取清除空格后的参数值
	 * @param name 参数名
	 * @return str
	 */
	public String getTrimParamValue(String name){
		String result = getPara(name,"");
		return result.trim();
	}
}
