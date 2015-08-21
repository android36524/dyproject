package com.dayang.system.model;

import com.jfinal.plugin.activerecord.Model;

/**
 * 类描述：菜单MODEL
 * <pre>
 * -------------History------------------
 *   DATE                 AUTHOR         VERSION        DESCRIPTION
 *  2015年5月18日              温建军              V01.00.001		      新增内容   
 * </pre>
 * 
 * @author <a href="wenjj@dayanginfo.com">温建军</a>
 */
public class MenuModel extends Model<MenuModel>{
	private static final long serialVersionUID = 1L;
	
	public static final MenuModel dao = new MenuModel();
}
