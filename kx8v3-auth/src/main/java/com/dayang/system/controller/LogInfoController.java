package com.dayang.system.controller;

import java.io.File;

import com.dayang.commons.controller.AdminBaseController;
import com.dayang.commons.pojo.JQGridPagePojo;
import com.dayang.commons.util.DaYangStaticData;
import com.dayang.system.model.LoginfoModel;

/**
 * 类描述：Excel日志文件管理类
 * <pre>
 * -------------History------------------
 *   DATE                 AUTHOR         VERSION        DESCRIPTION
 *  2015年5月18日              何意              V01.00.001		      新增内容
 * </pre>
 * 
 * @author <a href="heyi@dayanginfo.com">何意</a>
 */
public class LogInfoController extends AdminBaseController{
	/**
	 * Excel日志文件管理首页
	 */
	public void index(){
		renderJsp("index.jsp");
	}

	/**
	 * 分页查询
	 */
	public void logInfoList(){
		int pageNumber = getParaToInt("page");
		int pageSize = getParaToInt("rows", DaYangStaticData.PAGE_ROWS);
		String fileName = getPara("fileName");
		renderJson(JQGridPagePojo.parsePageData(LoginfoModel.dao.findByStageAll(pageNumber, pageSize, fileName)));
	}
	//excel下载
	public void downExcel(){
		String downLoadUrl = getPara("downLoadUrl");
		File downFile = new File(downLoadUrl);
		renderFile(downFile);
	}
}
