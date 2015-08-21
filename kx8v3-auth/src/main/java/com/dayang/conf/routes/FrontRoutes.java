package com.dayang.conf.routes;

import com.dayang.commons.controller.IndexController;
import com.jfinal.config.Routes;

/**
 * 类描述：前端路由配置
 * <pre>
 * -------------History------------------
 *   DATE                 AUTHOR         VERSION        DESCRIPTION
 *  2014年11月26日               张才胜               V01.00.001		      新增内容   
 * </pre>
 * 
 * @author <a href="mailto:zcslsf@gmail.com">张才胜</a>
 */

public class FrontRoutes extends Routes {

	@Override
	public void config() {
		this.add("/",IndexController.class);
	}
	

}
