package com.dayang.demo.interceptor;

import org.apache.log4j.Logger;

import com.jfinal.aop.Interceptor;
import com.jfinal.core.ActionInvocation;

/**
 * 类描述：拦截器用例
 * <pre>
 * -------------History------------------
 *   DATE                 AUTHOR         VERSION        DESCRIPTION
 *  2014年11月26日               张才胜               V01.00.001		      新增内容   
 * </pre>
 * 
 * @author <a href="mailto:zcslsf@gmail.com">张才胜</a>
 */

public class DemoInterceptor implements Interceptor{
	public final Logger logger = Logger.getLogger(DemoInterceptor.class);
	@Override
	public void intercept(ActionInvocation ai) {
		logger.info("Action调用之前....，启动Demo拦截器");
		ai.invoke();
		logger.info("Action调用之后...");
	}

}
