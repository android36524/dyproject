package com.dayang.system.controller;

import java.util.Date;
import java.util.List;

import com.dayang.commons.annotation.FuncActionAnnotation;
import com.dayang.commons.controller.AdminBaseController;
import com.dayang.commons.pojo.AjaxRetPojo;
import com.dayang.commons.pojo.JQGridPagePojo;
import com.dayang.commons.util.CommonUtil;
import com.dayang.commons.util.DaYangStaticData;
import com.dayang.commons.util.IDKeyUtil;
import com.dayang.conf.interceptor.AuthInterceptor;
import com.dayang.conf.interceptor.FuncActionInterceptor;
import com.dayang.system.model.SchBookVerModel;
import com.dayang.system.model.SchoolBookModel;
import com.dayang.system.validator.GradeDelValidator;
import com.jfinal.aop.Before;

/**
 * 
 * 类描述：教材版本管理   //人教版2011
 * <pre>
 * -------------History------------------
 *   DATE       AUTHOR       VERSION        DESCRIPTION
 *  2015-5-19      李中杰               V01.00.001		      新增内容   
 * </pre>
 * 
 * @author <a href="mailto:lizj@dayanginfo.com">李中杰</a>
 */
public class SchBookVerController extends AdminBaseController {

	/**
	 * 出版社管理
	 */
	@Before(AuthInterceptor.class)
	public void index() {
		this.setAttr("fisrtMenu", "知识树管理");
		this.setAttr("secendMenu", "教材版本管理");
		renderJsp("index.jsp");
	}

	/**
	 * 分页查询教程版本列表
	 * 
	 * @param 设定文件
	 * @return void 返回类型
	 * @throws
	 */
	public void listPage() {
		int pageNumber = getParaToInt("page");
		int pageSize = getParaToInt("rows", DaYangStaticData.PAGE_ROWS);
		String schBookVerName = getPara("schBookVerName");
		renderJson(JQGridPagePojo.parsePageData(SchBookVerModel.findSchBookVer(pageNumber, pageSize, schBookVerName)));
	}

	/**
	 * 根据id获取教程版本详情
	 * 
	 * @param 设定文件
	 * @return void 返回类型
	 * @throws
	 */
	public void findSchBookVerById() {
		renderJson(SchBookVerModel.dao.findById(getParaToInt("id")));
	}

	/**
	 * 出版教程版本（新增、修改）
	 * 
	 * @param 设定文件
	 * @return void 返回类型
	 * @throws
	 */
	@FuncActionAnnotation(noIdAction="/admin/schBookVer/add",idAction="/admin/schBookVer/modify",idName = "schBookVer.id")
    @Before({FuncActionInterceptor.class}) 
	public void save() {
		SchBookVerModel schBookVerModel = getModel(SchBookVerModel.class, "schBookVer");
		if (schBookVerModel.getLong("id") != null) {
			schBookVerModel.update();
		} else {
			schBookVerModel.set("id", IDKeyUtil.getIDKey());
			schBookVerModel.set("createTime", new Date());
			schBookVerModel.set("creator", getLoginUserId());
			schBookVerModel.save();
		}
		renderJson(AjaxRetPojo.newInstance());
	}

	
	/**
	 * 删除教程版本
	 * 
	 * @param     设定文件 
	 * @return void    返回类型 
	 * @throws
	 */
	@Before({FuncActionInterceptor.class})
	public void del() {
		AjaxRetPojo ajaxRetPojo = AjaxRetPojo.newInstance();
		SchoolBookModel schoolBookVersionModel = SchoolBookModel.dao.findFirst("select count(1) as schBookVer from base_schoolbook where schBookVerId = ?",getParaToInt("id"));
		if (schoolBookVersionModel != null && schoolBookVersionModel.getLong("schBookVer") > 0) {
			ajaxRetPojo.setCode(AjaxRetPojo.CODE_FAIL);
			ajaxRetPojo.setMsg("当前教程版本在教材中有引用，不允许删除！");
		} else {
			SchBookVerModel.dao.deleteById(getParaToInt("id"));
		}
		renderJson(ajaxRetPojo);
	}
	
	/**
	 * 查询所有的教材版本
	 * 
	 * @param     设定文件 
	 * @return void    返回类型 
	 * @throws
	 */
	public void findSchBookVerAll(){
		renderJson(SchBookVerModel.findSchBookVerAll());
	}
	
	/**
	 * 根据教材版本名称查询数据
	 */
	public void findSchoolBookVerByName(){
		String name = getPara("name");
		Integer id = getParaToInt("id");
		AjaxRetPojo pojo = new AjaxRetPojo();
		if(CommonUtil.isEmpty(name)){
			renderJson(AjaxRetPojo.newInstance());
		}else{
			List<SchBookVerModel> schoolBookVerInfo = SchBookVerModel.findSchoolBookVerByName(name,id);
			if(schoolBookVerInfo.size() > 0){
				pojo.setCode(AjaxRetPojo.CODE_FAIL);
				renderJson(pojo);
			}else{
				renderJson(AjaxRetPojo.newInstance());
			}
		}
	}

}
