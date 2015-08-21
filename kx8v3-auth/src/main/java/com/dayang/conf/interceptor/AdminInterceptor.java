package com.dayang.conf.interceptor;

import com.jfinal.aop.InterceptorStack;

/**
 * 类描述：管理后台的拦截器
 * <pre>
 * -------------History------------------
 *   DATE                 AUTHOR         VERSION        DESCRIPTION
 *  2015-7-9               张才胜               V01.00.001		      新增内容   
 * </pre>
 * 
 * @author <a href="mailto:zhangcs@dayanginfo.com">张才胜</a>
 */

public class AdminInterceptor extends InterceptorStack {

	@Override
	public void config() {
		//后台管理菜单拦截器
		addInterceptors(new MenuInterceptor());
	}

}
