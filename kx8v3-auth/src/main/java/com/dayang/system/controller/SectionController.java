package com.dayang.system.controller;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;

import com.dayang.commons.controller.AdminBaseController;
import com.dayang.commons.pojo.AjaxRetPojo;
import com.dayang.commons.util.IDKeyUtil;
import com.dayang.commons.util.StaticData;
import com.dayang.system.model.PressModel;
import com.dayang.system.model.SchoolBookModel;
import com.dayang.system.model.SectionLoreModel;
import com.dayang.system.model.SectionModel;
import com.jfinal.plugin.activerecord.Db;

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
public class SectionController extends AdminBaseController {

	/**
	 * 出版社管理
	 */
	public void index() {
		this.setAttr("fisrtMenu", "知识树管理");
		this.setAttr("secendMenu", "章节管理");
		renderJsp("index.jsp");
	}

	/**
	 * 分页查询出版社列表
	 * 
	 * @param 设定文件
	 * @return void 返回类型
	 * @throws
	 */
	public void findSectionList() {
		int shoolBookId = getParaToInt("schoolBookId");
		SchoolBookModel schoolBookModel = SchoolBookModel.findSchoolBookInfo(shoolBookId);//教材详情
		List<SectionModel> listSectionModel = SectionModel.findSectionList(shoolBookId);
		SectionModel model = new SectionModel();
		model.put("id", schoolBookModel.get("id"));
		model.put("name", schoolBookModel.get("name"));
		model.put("pId", "0");
		listSectionModel.add(model);
		renderJson(listSectionModel);
	}


	/**
	 * @throws UnsupportedEncodingException 
	 * 出版社（新增、修改）
	 * 
	 * @param 设定文件
	 * @return void 返回类型
	 * @throws
	 */
	public void save() throws UnsupportedEncodingException {
		Integer id = getParaToInt("id");
		String nameStr = getPara("name");
		//String name = new String(nameStr.getBytes("ISO-8859-1"),"UTF-8"); 
		AjaxRetPojo messagePojo = new AjaxRetPojo();
		if (id != null) {
			SectionModel model = SectionModel.dao.findById(id);
			model.set("name", nameStr);
			if(model.get("parentId")==null){
				model.set("pathName", nameStr);//如果parentId为null，那么pathName就是name
			}else{
				String parentName = model.get("pathName");
				int i = parentName.lastIndexOf(">");
				String realParentName = parentName.substring(0,i);
				model.set("pathName", realParentName+">"+nameStr);//如果parentId不为null，那么pathName就是pathName和name
			}
			boolean boole = model.update();
			if(boole){
				model.put("code", AjaxRetPojo.CODE_SUCCESS);
				renderJson(model);
			}else{
				messagePojo.setCode(AjaxRetPojo.CODE_FAIL);
				messagePojo.setMsg("系统异常");
				renderJson(messagePojo);
			}
		} else {
			int pId = getParaToInt("pId");
			SectionModel model = SectionModel.dao.findById(pId);//父级节点信息
			SectionModel sectionModel = getModel(SectionModel.class,"section");
			Long sectionId = IDKeyUtil.getIDKey();
			if(model==null||"".equals(model)){
				sectionModel.set("id", sectionId);
				sectionModel.set("schoolBookId", pId);
				sectionModel.set("name", nameStr);
				sectionModel.set("parentId", id);
				sectionModel.set("path", sectionId);
				sectionModel.set("pathName", nameStr);
				sectionModel.set("createTime", new Date());
				sectionModel.set("creator", getLoginUserId());
			}else{
				sectionModel.set("id", sectionId);
				sectionModel.set("schoolBookId", model.get("schoolBookId"));
				sectionModel.set("name", nameStr);
				sectionModel.set("parentId", pId);
				sectionModel.set("path", model.get("path")+">"+sectionId);
				sectionModel.set("pathName", model.get("pathName")+">"+nameStr);
				sectionModel.set("createTime", new Date());
				sectionModel.set("creator", getLoginUserId());
			}
			boolean boole =sectionModel.save();
			if(boole){
				sectionModel.put("code", AjaxRetPojo.CODE_SUCCESS);
				renderJson(sectionModel);
			}else{
				messagePojo.setCode(AjaxRetPojo.CODE_FAIL);
				messagePojo.setMsg("系统异常");
				renderJson(messagePojo);
			}
		}
	}
	
	
	
	/**
	 * 删除出版社
	 * 
	 * @param     设定文件 
	 * @return void    返回类型 
	 * @throws
	 */
	public void del() {
		AjaxRetPojo ajaxRetPojo = AjaxRetPojo.newInstance();
		int id = getParaToInt("id");
		Long count = SectionLoreModel.findSectionLoreBySectionId(id);
		if (count > 0) {
			ajaxRetPojo.setCode(AjaxRetPojo.CODE_FAIL);
			ajaxRetPojo.setMsg("当前章节在知识点中有引用，不允许删除！");
		} else {
			SectionModel.dao.deleteById(getPara("id"));
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
	 * 保存章节与知识点的关系
	 */
	public void saveSectionAndLore(){
		String sectionId = getPara("sectionId");
		String loreId = getPara("loreId");//这些增加  43,44,68,48,67,45,47,61,46,66,59,65,
		String selectLoreId = getPara("selectLoreId");//这些删除  43,66,68,47,61,45,59,46,44,48,71,65,67
		
		String deleteSql = " delete from r_section_lore  where loreId in(?) and sectionId in("+sectionId+") ";
		String insertSql = " insert into r_section_lore (sectionId,loreId) values (?,?) ";
		
		if(loreId.length()>0){
			int index = loreId.lastIndexOf(",");
			String loreIdGroup = loreId.substring(0, index);
			
			
			//先删除此章节关联的知识点
			String[] deleteSelectLoreId = selectLoreId.split(",");
			
			Object[][] objLoreSectionEmpe = new Object[deleteSelectLoreId.length][1];
			for(int i=0;i<deleteSelectLoreId.length;i++){
				Object[] temp = new Object[1];
				temp[0] = deleteSelectLoreId[i];
				objLoreSectionEmpe[i] = temp;
			}
			Db.batch(deleteSql,objLoreSectionEmpe,StaticData.Batch_Size);
			
			String[] arrayLore=loreIdGroup.split(",");//增加此章节勾选的知识的数据
			for(int i =0;i<arrayLore.length;i++){
				String param = arrayLore[i];
				Db.update(insertSql,sectionId,param);
			}
		}else{//如果知识点空，说明只删除
			String[] deleteSelectLoreId = selectLoreId.split(",");
			
			Object[][] objLoreSectionEmpe = new Object[deleteSelectLoreId.length][1];
			for(int i=0;i<deleteSelectLoreId.length;i++){
				Object[] temp = new Object[1];
				temp[0] = deleteSelectLoreId[i];
				objLoreSectionEmpe[i] = temp;
			}
			Db.batch(deleteSql,objLoreSectionEmpe,StaticData.Batch_Size);
		}
		
		
		renderJson(AjaxRetPojo.newInstance());
	}

}
