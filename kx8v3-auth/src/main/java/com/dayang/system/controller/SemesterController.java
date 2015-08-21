package com.dayang.system.controller;


import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.dayang.commons.annotation.FuncActionAnnotation;
import com.dayang.commons.controller.AdminBaseController;
import com.dayang.commons.enums.EnumAll;
import com.dayang.commons.pojo.AjaxRetPojo;
import com.dayang.commons.pojo.JQGridPagePojo;
import com.dayang.commons.util.CommonUtil;
import com.dayang.commons.util.DaYangStaticData;
import com.dayang.commons.util.DictionaryUtil;
import com.dayang.commons.util.EnumAndDicDefine;
import com.dayang.commons.util.EnumUtil;
import com.dayang.commons.util.IDKeyUtil;
import com.dayang.commons.util.StaticData;
import com.dayang.conf.interceptor.AuthInterceptor;
import com.dayang.conf.interceptor.FuncActionInterceptor;
import com.dayang.system.model.SemesterModel;
import com.dayang.system.validator.SemesterValidator;
import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;

/**
 * 类描述：学期管理类
 * <pre>
 * -------------History------------------
 *   DATE                 AUTHOR         VERSION        DESCRIPTION
 *  2015年5月19日              温建军              V01.00.001		      新增内容   
 * </pre>
 * 
 * @author <a href="wenjj@dayanginfo.com">温建军</a>
 */
public class SemesterController extends AdminBaseController {
	
	/**
	 * 学期管理首页
	 */
	@Before(AuthInterceptor.class)
	public void index(){
		this.setAttr("fisrtMenu", "学校管理");
		this.setAttr("secendMenu", "学期管理");
		renderJsp("index.jsp");
	}
	
	/**
	 * 学期查询
	 */
	public void list(){
		String name=getPara("name");
		long schId = getParaToLong("schId",0l);
		int page =getParaToInt("page");
		int rows = getParaToInt("rows", DaYangStaticData.PAGE_ROWS);
		Map<String,Object> param = new HashMap<String,Object>();		
		if(!CommonUtil.isEmptyString(name)){
			param.put("name", name);
		}
		if(schId>0){
			param.put("schId", schId);
		}
		Page<SemesterModel> p = SemesterModel.dao.findSubjectPage(page, rows, param);
		CommonUtil.setShowValue2List(p,EnumAndDicDefine.SEMESTER_DEFINETABLE);
		renderJson(JQGridPagePojo.parsePageData(p));		
	}
	
	/**
	 * 新增修改学期初始化参数
	 */
	public void toAdd(){
		 this.setAttr("schLists", DictionaryUtil.getDicListByDicType(StaticData.SCHYEAR_dictionaryType));
         this.setAttr("semLists", EnumUtil.toEnumList(EnumAll.SemesterFlag.FIRSTFLAG));
         this.setAttr("notCurSem", EnumUtil.toEnumPojo(EnumAll.IsYesOrNot.IsNot));
         long id = getParaToLong("id",0l);
         this.setAttr("curId", id);
        renderJsp("semesterInfo.jsp");
	}
	/**
	 * 新增修改学期
	 */
	@FuncActionAnnotation(noIdAction="/admin/semester/add",idAction="/admin/semester/modify",idName="semester.id")
	@Before({SemesterValidator.class,FuncActionInterceptor.class})
	public void add(){
		SemesterModel m = getModel(SemesterModel.class,"semester");		
		if(m.getLong("id") != null){
			m.update();
		}else{
			m.set("createTime", new Date());
			m.set("creator", getLoginUserId());
			m.set("id", IDKeyUtil.getIDKey());
			m.save();
		}
		
		renderJson(AjaxRetPojo.newInstance());
	}
	
	/**
	 * 学期删除
	 */
	@Before(FuncActionInterceptor.class)
	public void del(){
		long id = getParaToLong("id");
	    if(id<=0){
	    	renderJson("result",false);
	    	return;
	    }
	    Db.update("delete from base_semester where id=?", id);
	    renderJson(AjaxRetPojo.newInstance());		
	}
	
	/**
	 * 设置学校的当前学期
	 */
	public void set(){
		long id = getParaToLong("id");
		long schId = getParaToLong("schId");
	    if(id<=0){
	    	renderJson("result",false);
	    	return;
	    }
	    String notCur = EnumAll.IsYesOrNot.IsNot.getValueStr();
	    String isCur = EnumAll.IsYesOrNot.IsYes.getValueStr();
	    Db.update("update base_semester set isCur=? where schId=?", notCur,schId);
	    Db.update("update base_semester set isCur=? where id=?", isCur,id);	   
	    renderJson(AjaxRetPojo.newInstance());		
	}
	/**
	 * 根据ID查询用户
	 */
	public void queryById(){
		renderJson(SemesterModel.dao.findById(getParaToLong("id")));
	}
}
