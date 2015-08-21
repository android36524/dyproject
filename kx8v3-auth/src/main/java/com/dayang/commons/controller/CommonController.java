package com.dayang.commons.controller;

import org.apache.log4j.Logger;

import com.dayang.commons.util.AccountGenerator;
import com.dayang.commons.util.CommonUtil;
import com.dayang.commons.util.FileUploadUtil;
import com.github.stuxuhai.jpinyin.PinyinHelper;
import com.jfinal.core.Controller;
import com.jfinal.upload.UploadFile;

/**
 * 类描述：公共的action
 * <pre>
 * -------------History------------------
 *   DATE                 AUTHOR         VERSION        DESCRIPTION
 *  2015年3月15日              张才胜               V01.00.001		      新增内容   
 * </pre>
 * 
 * @author <a href="mailto:zcslsf@gmail.com">张才胜</a>
 */
public class CommonController extends Controller {
	private static final Logger LOGGER = Logger.getLogger(CommonController.class);
	/**
	 * 通用的文件上传
	 */
	public void upload(){
		UploadFile uploadFile = getFile("uploadify");
		LOGGER.info("开始上传文件....");
		renderJson(FileUploadUtil.upload2FTP(uploadFile)); 
	}
	
	/**
	 * 取得拼音首字
	 */
	public void getPinyin(){		
		String str = getPara("name");
		if(!CommonUtil.isEmptyString(str)){
			renderText(PinyinHelper.getShortPinyin(str).toUpperCase());
		}else{
			renderText("");
		}
	}

	/**
	 * 获取自动生成账号
	 */
	public void getAaccount(){
		renderText(AccountGenerator.getAccount() + "");
	}
}
