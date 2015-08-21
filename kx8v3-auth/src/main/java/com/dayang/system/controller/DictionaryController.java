package com.dayang.system.controller;


import com.dayang.commons.controller.AdminBaseController;
import com.dayang.commons.pojo.AjaxRetPojo;
import com.dayang.commons.pojo.JQGridPagePojo;
import com.dayang.commons.util.DaYangStaticData;
import com.dayang.system.model.DictionaryModel;
import com.dayang.system.model.DictionaryTypeModel;
/**
 * 类描述：字典管理的Controller
 * <pre>
 * -------------History------------------
 *   DATE                 AUTHOR         VERSION        DESCRIPTION
 *  2015年4月15日               温建军           V01.00.001		      新增内容   
 * </pre>
 * 
 * @author <a href="mailto:wenjj@dayang.com"> 温建军  </a>
 */
public class DictionaryController extends AdminBaseController {
	/**
	 * 字典管理首页
	 */
	public void index(){
		this.setAttr("fisrtMenu", "系统管理");
		this.setAttr("secendMenu", "字典管理");
		
		renderJsp("index.jsp");
	}
	
	/**
	 * 查询字典列表
	 */
	public void query(){
		int typeid = getParaToInt("typeid",0);
		renderJson(DictionaryModel.dao.find("select * from sys_dictionary where dictionarytype=? order by code asc",typeid));
	}
	
	/**
	 * 新增字典
	 */
	public void add(){
		DictionaryModel dictionaryModel = getModel(DictionaryModel.class,"dictionary");
		if(dictionaryModel.getInt("id") != null){
			dictionaryModel.update();
		}else{
			dictionaryModel.save();
		}
		
		renderJson(AjaxRetPojo.newInstance());
	}
	
	  /**
     * 删除字典
     */	
    public void del(){
        int id = getParaToInt("id");
        if(id<=0){
        	 renderJson("result",false);
    		 return;
        }
        DictionaryModel.dao.deleteById(id);
        renderJson(AjaxRetPojo.newInstance());
    }
	
	/**
	 *  根据ID查询字典
	 */
	public void queryById(){
		int id = getParaToInt("id",0);	
		if(id>0){
			renderJson(DictionaryModel.dao.findById(id));
		}
	}
	
	/**
	 *  根据ID查询字典
	 */
	public void queryByPtypeAndValue(){
		int id = getParaToInt("value",0);	
		int typeid = getParaToInt("typeid",0);	
		if(id>0){
			renderJson(DictionaryModel.dao.findDictiByValue(typeid,id+""));
		}
	}
	
	/**
	 * 根据类型查询字典
	 */
	public void queryDictionaryTypeById(){
		int id = getParaToInt("id",0);		
		renderJson(DictionaryTypeModel.dao.findById(id));
	}
	
	/**
	 * 字典类型分页查询
	 */
	public void listDictionaryType(){
		renderJson(JQGridPagePojo.parsePageData(DictionaryTypeModel.dao.paginate(getParaToInt("page"), getParaToInt("rows", DaYangStaticData.PAGE_ROWS), "select *", "from sys_dictionarytype")));
	}
	
	/**
	 * 字典类型查询
	 */
	public void listDicType(){
		renderJson(DictionaryTypeModel.dao.find("select * from sys_dictionarytype order by id asc"));
	}
	
	/**
	 * 新增字典类型
	 */
	public void addDictionaryType(){
		DictionaryTypeModel dictionaryModel = getModel(DictionaryTypeModel.class,"dictionarytype");
		if(dictionaryModel.getInt("id") != null){
			dictionaryModel.update();
		}else{
			dictionaryModel.save();
		}
		
		renderJson(AjaxRetPojo.newInstance());
	}
}
