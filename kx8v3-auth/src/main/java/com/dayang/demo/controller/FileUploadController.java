package com.dayang.demo.controller;

import org.apache.log4j.Logger;

import com.dayang.commons.util.BaseUtil;
import com.jfinal.core.Controller;
import com.jfinal.upload.UploadFile;

/**
 * 类描述：jfinal文件上传的demo
 * <pre>
 * -------------History------------------
 *   DATE                 AUTHOR         VERSION        DESCRIPTION
 *  2015年1月15日              张才胜               V01.00.001		      新增内容   
 * </pre>
 * 
 * @author <a href="mailto:zcslsf@gmail.com">张才胜</a>
 */
public class FileUploadController extends Controller {
	private static final Logger LOGGER = Logger.getLogger(FileUploadController.class);
	/**
	 * 单文件上传
	 */
	public void upload(){
		UploadFile uploadFile = getFile("uploadify");
		renderJson(BaseUtil.uploadWebFile2FTP(uploadFile));
	}
}
