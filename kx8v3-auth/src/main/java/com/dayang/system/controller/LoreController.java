package com.dayang.system.controller;

import com.dayang.commons.controller.AdminBaseController;
import com.dayang.commons.pojo.AjaxRetPojo;
import com.dayang.commons.util.CommonUtil;
import com.dayang.commons.util.StaticData;
import com.dayang.commons.util.UtilDate;
import com.dayang.system.model.LoreModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 类描述：知识点管理Controller
 * <pre>
 * -------------History------------------
 *   DATE                 AUTHOR         VERSION        DESCRIPTION
 *  2015年5月19日上午10:57:27        刘生慧              		 V01.00.001		 新增内容   
 * </pre>
 * 
 * @author <a href="liush@dayanginfo.com">刘生慧</a>
 */
public class LoreController extends AdminBaseController {

	private Boolean bool;

	public void index(){
		renderJsp("index.jsp");
	}
	
	/**
	 * 根据查询条件获取知识点
	 */
	public void searchLore(){
		Long subjectId = getParaToLong("subjectId");
		
		String stageId = getPara("stageId");
		String gradeId = getPara("gradeId");
		
		String sectionId = getPara("sectionId");//根据章节来查询章节与知识点的关联，并显示知识点树
		
		String name = getPara("name");
		Integer id = getParaToInt("id");
		Integer flag = getParaToInt("flag");
		List<LoreModel> loreInfoList = new ArrayList<LoreModel>();
		if( StaticData.SEARCH_FLAG.equals(flag) ){
			loreInfoList = LoreModel.searchLore(subjectId, name,id, stageId, gradeId,sectionId);
		}else{
			loreInfoList = LoreModel.asyncSearch(subjectId, name, id);
		}
		
	//	LoreModel model = new LoreModel();
		
		for(int i = 0;i < loreInfoList.size();i++){
			LoreModel model = loreInfoList.get(i);
			List<LoreModel> tempInfoList = LoreModel.searchLore(subjectId,name,model.getInt("id"),stageId, gradeId,sectionId);
			if(sectionId!=null){
				model.put("checked", true);
				model.put("selectLoreId", model.getInt("id"));
			}
			if(tempInfoList.size() > 0){
				model.put("isParent", true);
			}else{
				model.put("isParent", false);
			}
			//loreInfoList.add(model);
		}
//		if(loreInfoList.size()>0){
//			loreInfoList.add(model);
//		}
		renderJson(loreInfoList);
	}
	
	/**
	 * 点击章节时候，取到此章节已经关联的知识点
	 */
	public void searchSectionLoreID(){
		Long subjectId = getParaToLong("subjectId");
		
		String stageId = getPara("stageId");
		String gradeId = getPara("gradeId");
		
		String sectionId = getPara("sectionId");//根据章节来查询章节与知识点的关联，并显示知识点树
		
		String name = getPara("name");
		Integer id = getParaToInt("id");
		Integer flag = getParaToInt("flag");
		List<LoreModel> loreInfoList = new ArrayList<LoreModel>();
		if( StaticData.SEARCH_FLAG.equals(flag) ){
			loreInfoList = LoreModel.searchLore(subjectId, name,id, stageId, gradeId,sectionId);
		}else{
			loreInfoList = LoreModel.asyncSearch(subjectId, name, id);
		}
		
	//	LoreModel model = new LoreModel();
		List tempList = new ArrayList();
		for(int i = 0;i < loreInfoList.size();i++){
			LoreModel model = loreInfoList.get(i);
			List<LoreModel> tempInfoList = LoreModel.searchLore(subjectId,name,model.getInt("id"),stageId, gradeId,sectionId);
			if(sectionId!=null){
				model.put("checked", true);
				tempList.add(model.getInt("id"));
			}
			if(tempInfoList.size() > 0){
				model.put("isParent", true);
			}else{
				model.put("isParent", false);
			}
			//loreInfoList.add(model);
		}
//		if(loreInfoList.size()>0){
//			loreInfoList.add(model);
//		}
		renderJson(tempList);
	}
	
	/**
	 * 根据Id删除知识点
	 */
	public void deleLoreById(){
		AjaxRetPojo pojo = new AjaxRetPojo();
		Integer id = getParaToInt("id");
		
		String stageId = getPara("stageId");
		String gradeid = getPara("gradeId");
		String sectionId = getPara("sectionId");
		
		if(CommonUtil.isEmpty(id)){
			pojo.setCode(AjaxRetPojo.CODE_FAIL);
			pojo.setMsg("要删除的数据不存在");
			renderJson(pojo);
			bool=true;
		}else if(LoreModel.findLoreRela(id)){//根据id 查询是否有章节引用了该知识点 如果引用即不能删除
			pojo.setCode(AjaxRetPojo.CODE_FAIL);
			pojo.setMsg("存在相关联的章节");
			renderJson(pojo);
			bool=true;
		}else{
			List<LoreModel> loreInfo = LoreModel.searchLore(null, "",id,stageId, gradeid,sectionId);//查询是否有下级知识点
			if(loreInfo.size() > 0){
				pojo.setCode(AjaxRetPojo.CODE_FAIL);
				pojo.setMsg("该知识点存在下级数据");
				renderJson(pojo);
			}else{
				bool = LoreModel.deleLoreById(id);
			}
		}
		
		if(bool){
			renderJson(pojo);
		}else{
			pojo.setCode(AjaxRetPojo.CODE_FAIL);
			pojo.setMsg("系统异常");
			renderJson(pojo);
		}
	}
	
	/**
	 * 新增或修改知识点
	 */
	public void addLore(){
		LoreModel model = getModel(LoreModel.class,"lore");
		Integer parentId = model.getInt("parentId");
		Integer id = model.getInt("id");
		AjaxRetPojo pojo = new AjaxRetPojo();
		if(CommonUtil.isEmpty(id)){//添加操作
			bool = model.set("createTime",UtilDate.getDateFormatter()).set("creator",getLoginUserId()).save();
		}else{//修改操作
			bool = model.update();
		}
		if(bool){
			model.put("code",AjaxRetPojo.CODE_SUCCESS);
			renderJson(model);
		}else{
			pojo.setCode(AjaxRetPojo.CODE_FAIL);
			pojo.setMsg("系统异常");
			renderJson(pojo);
		}
	}
	
	/**
	 * 根据知识点名称查询数据
	 */
	public void findLoreByName(){
		String name = getPara("name");
		Integer id = getParaToInt("id");
		Long subjectId = getParaToLong("subjectId");
		AjaxRetPojo pojo = new AjaxRetPojo();
		Map<String,Object> map= new HashMap<String,Object>();
        map.put("id",id);
        map.put("name",name);
		map.put("subjectId", subjectId);
		List<LoreModel> list = LoreModel.findLoreByName(map);
		if(list.size() > 0){
				pojo.setCode(AjaxRetPojo.CODE_FAIL);
				renderJson(pojo);
	    }else{
				renderJson(AjaxRetPojo.newInstance());
		}
	}
	
	public static void main(String[] args) {
		List<LoreModel> loreInfo = new ArrayList<LoreModel>();
		String temp = loreInfo.get(0).get("");
		System.out.println(temp);
	}
	
	/**
	 * 移动知识点的排序
	 */
	public void moveLore(){
		Integer id = getParaToInt("nodeId");
		Integer moveId = getParaToInt("moveNodeId");
		bool = LoreModel.moveLore(id, moveId);
		AjaxRetPojo pojo = new AjaxRetPojo();
		if(bool){
			renderJson(AjaxRetPojo.newInstance());
		}else{
			pojo.setMsg("系统异常");
			pojo.setCode(AjaxRetPojo.CODE_FAIL);
			renderJson(pojo);
		}
		
	}
	
	/**
	 * 更换知识点（升、降级）
	 */
	public void loreChange(){
		Integer id = getParaToInt("id");
		Integer pId = getParaToInt("pId");
		bool = LoreModel.loreChange(id, pId);
		AjaxRetPojo pojo = new AjaxRetPojo();
		if(bool){
			renderJson(AjaxRetPojo.newInstance());
		}else{
			pojo.setMsg("系统异常");
			pojo.setCode(AjaxRetPojo.CODE_FAIL);
			renderJson(pojo);
		}
	}
	
	/**
	 * 根据ID查询知识点
	 */
	public void searchLoreById(){
		Integer id = getParaToInt("id");
		List<LoreModel> list = LoreModel.searchLoreById(id);
		LoreModel model = new LoreModel();
		if(list.size() > 0){
			model = list.get(0);
		}
		renderJson(model);
	}
	
	/**
	 * 根据章节ID来查询知识点，显示所有知识点，如果是对应的章节知识点就是选中状态
	 */
	public void searchLoreBySectionId(){
		Long subjectId = getParaToLong("subjectId");
		
		String stageId = getPara("stageId");
		String gradeId = getPara("gradeId");
		
		String sectionId = getPara("sectionId");//根据章节来查询章节与知识点的关联，并显示知识点树
		
		String name = getPara("name");
		Integer id = getParaToInt("id");
		Integer flag = getParaToInt("flag");
		List<LoreModel> loreInfoList = new ArrayList<LoreModel>();
		if( StaticData.SEARCH_FLAG.equals(flag) ){
			loreInfoList = LoreModel.searchLoreBySectionId(subjectId, name,id, stageId, gradeId,sectionId);
		}else{
			loreInfoList = LoreModel.asyncSearch(subjectId, name, id);
		}
		
	//	LoreModel model = new LoreModel();
		
		for(int i = 0;i < loreInfoList.size();i++){
			LoreModel model = loreInfoList.get(i);
			List<LoreModel> tempInfoList = LoreModel.searchLoreBySectionId(subjectId,name,model.getInt("id"),stageId, gradeId,sectionId);
			/*if(model.getInt("idid")!=null&&model.getInt("pId")==null){
				model.put("checked", true);
			}*/
			if(model.getInt("pId")==null){
				if(model.getInt("idid")!=null){
					model.put("checked", true);
				}else{
					model.put("checked", false);
				}
			}else if(model.getInt("pId")!=null){
				if(model.getInt("idid")!=null){
					model.put("checked", true);
				}else{
					model.put("checked", false);
				}
			}
			
			if(tempInfoList.size() > 0){
				model.put("isParent", true);
				//model.put("checked", true);
			}else{
				model.put("isParent", false);
				//model.put("checked", true);
			}
		}
		renderJson(loreInfoList);
	}
}
