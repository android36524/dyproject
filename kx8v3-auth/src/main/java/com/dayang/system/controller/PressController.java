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
import com.dayang.system.model.PressModel;
import com.jfinal.aop.Before;

/**
 * 
 * 类描述：出版社管理
 * 
 * <pre>
 * -------------History------------------
 *   DATE       AUTHOR       VERSION        DESCRIPTION
 *  2015-5-19      李中杰               V01.00.001		      新增内容
 * </pre>
 * 
 * @author <a href="mailto:lizj@dayanginfo.com">李中杰</a>
 */
public class PressController extends AdminBaseController {
	
	private static AjaxRetPojo pojo = new AjaxRetPojo();

	/**
	 * 出版社管理
	 */
	@Before(AuthInterceptor.class)
	public void index() {
		this.setAttr("fisrtMenu", "知识树管理");
		this.setAttr("secendMenu", "出版社管理");
		renderJsp("index.jsp");
	}

	/**
	 * 分页查询出版社列表
	 * 
	 * @param 设定文件
	 * @return void 返回类型
	 * @throws
	 */
	public void listPage() {
		int pageNumber = getParaToInt("page");
		int pageSize = getParaToInt("rows", DaYangStaticData.PAGE_ROWS);
		String pressName = getPara("pressName");
		renderJson(JQGridPagePojo.parsePageData(PressModel.findPress(
				pageNumber, pageSize, pressName)));
	}

	/**
	 * 根据id获取出版社详情
	 * 
	 * @param 设定文件
	 * @return void 返回类型
	 * @throws
	 */
	public void findPressById() {
		renderJson(PressModel.dao.findById(getParaToInt("id")));
	}

	/**
	 * 出版社（新增、修改）
	 * 
	 * @param 设定文件
	 * @return void 返回类型
	 * @throws
	 */
	@FuncActionAnnotation(noIdAction="/admin/press/add",idAction="/admin/press/modify",idName = "press.id")
    @Before({FuncActionInterceptor.class}) 
	public void save() {
		PressModel pressModel = getModel(PressModel.class, "press");
		if (pressModel.getLong("id") != null) {
			pressModel.update();
		} else {
			pressModel.set("id", IDKeyUtil.getIDKey());
			pressModel.set("code", PressModel.getMaxCode());
			pressModel.set("createTime", new Date());
			pressModel.set("creator", getLoginUserId());
			pressModel.save();
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
	@Before({FuncActionInterceptor.class})
	public void del() {
		AjaxRetPojo ajaxRetPojo = AjaxRetPojo.newInstance();
		PressModel pressModel = PressModel.dao.findFirst(" select count(*) as presscount from base_schbookver a,base_press b where a.pressId=b.id and b.id = ?",getParaToInt("id"));
		if (pressModel != null && pressModel.getLong("presscount") > 0) {
			ajaxRetPojo.setCode(AjaxRetPojo.CODE_FAIL);
			ajaxRetPojo.setMsg("当前出版社在教材版本中有引用，不允许删除！");
		} else {
			PressModel.dao.deleteById(getParaToInt("id"));
		}
		renderJson(ajaxRetPojo);
	}
	
	
	/**
	 * 查询所有的出版社 不分页
	 * 
	 * @param     设定文件 
	 * @return void    返回类型 
	 * @throws
	 */
	public void findPressAll(){
		renderJson(PressModel.findPressAll());
	}
	
	/**
	 * 根据出版社名称查询出版社是否存在
	 */
	public void findPressByName(){
		String name = getPara("name");
		Integer id = getParaToInt("id");
		if(CommonUtil.isEmpty(name)){
			renderJson(AjaxRetPojo.newInstance());
		}else{
			List<PressModel> pressInfo = PressModel.findPressByName(name,id);
			if(pressInfo.size() > 0){
				pojo.setCode(AjaxRetPojo.CODE_FAIL);
				renderJson(pojo);
			}else{
				renderJson(AjaxRetPojo.newInstance());
			}
		}
	}

}
