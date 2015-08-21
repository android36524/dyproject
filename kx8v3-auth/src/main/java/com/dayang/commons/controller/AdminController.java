package com.dayang.commons.controller;

/**
 * 类描述：管理后台首页的Controller
 * <pre>
 * -------------History------------------
 *   DATE                 AUTHOR         VERSION        DESCRIPTION
 *  2014年12月17日               张才胜               V01.00.001		      新增内容   
 * </pre>
 * 
 * @author <a href="mailto:zcslsf@gmail.com">张才胜</a>
 */
public class AdminController extends AdminBaseController {
	
	/**
	 * 登陆页面访问
	 */
	public void index(){
		redirect("/admin/main");
	}
	
	/**
	 * 首页访问
	 */
	public void main(){
		renderJsp("index.jsp");
	}
}
