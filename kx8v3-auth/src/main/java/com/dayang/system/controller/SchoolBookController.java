package com.dayang.system.controller;

import java.util.Date;

import com.dayang.commons.annotation.FuncActionAnnotation;
import com.dayang.commons.controller.AdminBaseController;
import com.dayang.commons.pojo.AjaxRetPojo;
import com.dayang.commons.pojo.JQGridPagePojo;
import com.dayang.commons.util.DaYangStaticData;
import com.dayang.commons.util.IDKeyUtil;
import com.dayang.conf.interceptor.AuthInterceptor;
import com.dayang.conf.interceptor.FuncActionInterceptor;
import com.dayang.system.model.SchoolBookModel;
import com.dayang.system.model.SectionModel;
import com.jfinal.aop.Before;

/**
 * 
 * 类描述：教材管理管理
 * 
 * <pre>
 * -------------History------------------
 *   DATE       AUTHOR       VERSION        DESCRIPTION
 *  2015-5-19      李中杰               V01.00.001		      新增内容
 * </pre>
 * 
 * @author <a href="mailto:lizj@dayanginfo.com">李中杰</a>
 */
public class SchoolBookController extends AdminBaseController {

	/**
	 * 教材管理
	 */
	@Before(AuthInterceptor.class)
	public void index() {
		this.setAttr("fisrtMenu", "知识树管理");
		this.setAttr("secendMenu", "教材管理");
		renderJsp("index.jsp");
	}

	/**
	 * 分页查询教材列表
	 * 
	 * @param 设定文件
	 * @return void 返回类型
	 * @throws
	 */
	public void listPage() {
		int pageNumber = getParaToInt("page");
		int pageSize = getParaToInt("rows", DaYangStaticData.PAGE_ROWS);
		String schoolBookName = getPara("schoolBookName");
		String stageId=getPara("stageId");
		String gradeId=getPara("gradeId");
		String subject=getPara("subject");
		renderJson(JQGridPagePojo.parsePageData(SchoolBookModel.findSchoolBook(
				pageNumber, pageSize, schoolBookName,stageId,gradeId,subject)));
	}

	/**
	 * 根据id获取教材详情
	 * 
	 * @param 设定文件
	 * @return void 返回类型
	 * @throws
	 */
	public void findSchoolBookById() {
		renderJson(SchoolBookModel.dao.findById(getParaToInt("id")));
	}

	/**
	 * 出版社（新增、修改）
	 * 
	 * @param 设定文件
	 * @return void 返回类型
	 * @throws
	 */
	@FuncActionAnnotation(noIdAction="/admin/schoolBook/add",idAction="/admin/schoolBook/modify",idName="schoolBook.id")
	@Before(FuncActionInterceptor.class)
	public void save() {
		SchoolBookModel schoolBookModel = getModel(SchoolBookModel.class, "schoolBook");
		if (schoolBookModel.getLong("id") != null) {
			schoolBookModel.update();
		} else {
			schoolBookModel.set("id", IDKeyUtil.getIDKey());
			schoolBookModel.set("createTime", new Date());
			schoolBookModel.set("creator", getLoginUserId());
			//schoolBookModel.set("volume", SchoolBookVolume.NOVOLUME.getValue());
			schoolBookModel.save();
		}
		renderJson(AjaxRetPojo.newInstance());
	}

	
	/**
	 * 删除出版社
	 * 
	 * @param     设定文件 
	 * @return void    返回类型 
	 * @throws
	 */
	@Before(FuncActionInterceptor.class)
	public void del() {
		AjaxRetPojo ajaxRetPojo = AjaxRetPojo.newInstance();
		SectionModel sectionModel = SectionModel.dao.findFirst(" select count(1) as schoolBookcount from base_section where schoolBookId = ? ",getParaToInt("id"));
		if (sectionModel != null && sectionModel.getLong("schoolBookcount") > 0) {
			ajaxRetPojo.setCode(AjaxRetPojo.CODE_FAIL);
			ajaxRetPojo.setMsg("当前教材在教材章节中有引用，不允许删除！");
		} else {
			SchoolBookModel.dao.deleteById(getParaToInt("id"));
		}
		renderJson(ajaxRetPojo);
	}
	
	
	
	

}
