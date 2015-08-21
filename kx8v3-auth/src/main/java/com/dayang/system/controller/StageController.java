package com.dayang.system.controller;

import java.util.Date;
import java.util.List;

import com.dayang.commons.annotation.FuncActionAnnotation;
import com.dayang.commons.controller.AdminBaseController;
import com.dayang.commons.pojo.AjaxRetPojo;
import com.dayang.commons.pojo.JQGridPagePojo;
import com.dayang.commons.util.CommonUtil;
import com.dayang.commons.util.DaYangStaticData;
import com.dayang.commons.util.StaticData;
import com.dayang.conf.interceptor.AuthInterceptor;
import com.dayang.conf.interceptor.FuncActionInterceptor;
import com.dayang.system.model.GradeModel;
import com.dayang.system.model.StageModel;
import com.jfinal.aop.Before;

/**
 * 类描述：学阶管理控制层
 * <pre>
 * -------------History------------------
 *   DATE                 AUTHOR         VERSION        DESCRIPTION
 *  2015年5月18日上午11:16:45        刘生慧              		 V01.00.001		 新增内容   
 * </pre>
 * 
 * @author <a href="liush@dayanginfo.com">刘生慧</a>
 */
public class StageController extends AdminBaseController {

	private boolean bool;
	
	private static AjaxRetPojo pojo = new AjaxRetPojo();

	private int suffix;

	@Before(AuthInterceptor.class)
	public void index(){
		renderJsp("index.jsp");
	}
	
	/**
	 * 分页查询学阶信息
	 */
	public void findStageByPage(){
		int pageNumber = getParaToInt("page");
		int pageSize = getParaToInt("rows",DaYangStaticData.PAGE_ROWS);
		String stageName = getPara("name");
		renderJson(JQGridPagePojo.parsePageData(StageModel.findByStageAll(pageNumber, pageSize, stageName)));
	}
	
	/**
	 * 查询学阶信息
	 */
	public void findStageInfo(){
		int id = getParaToInt("id");
		renderJson(StageModel.findStageInfo(id));
	}
	
	/**
	 * 根据学阶名称查询学阶是否存在
	 */
	public void findStageByName(){
		String name = getPara("name");
		Integer id = getParaToInt("id");
		if(CommonUtil.isEmpty(name)){
			renderJson(AjaxRetPojo.newInstance());
		}else{
			List<StageModel> stageInfo = StageModel.findStageByName(name,id);
			if(stageInfo.size() > 0){
				pojo.setCode(AjaxRetPojo.CODE_FAIL);
				renderJson(pojo);
			}else{
				renderJson(AjaxRetPojo.newInstance());
			}
		}
	}
	
	/**
	 * 新增或修改学阶数据
	 */
	@FuncActionAnnotation(noIdAction="/admin/stage/add",idAction="/admin/stage/modify",idName="stage.id")
	@Before(FuncActionInterceptor.class)
	public void addStage(){
		StageModel model = getModel(StageModel.class,"stage");
		Integer id = model.getInt("id");
		model.set("name",model.getStr("name").trim());
		if(CommonUtil.isEmpty(id)){
			model.set("creator", getLoginUserId());
			model.set("createTime", new Date());
			bool = model.save();
		}else{
			bool = model.update();
		}
		if(bool){
			renderJson(AjaxRetPojo.newInstance());
		}else{
			pojo.setCode(AjaxRetPojo.CODE_FAIL);
			renderJson(pojo);
		}
	}
	
	
	/**
	 * 查询所有的学阶  不含分页
	 * 
	 * @param     设定文件 
	 * @return void    返回类型 
	 * @throws
	 */
	public void findStageAll(){
		renderJson(StageModel.dao.findStageAll());
	}
	
	/**
	 * 获取学阶编码
	 */
	public void getMaxCode(){
		String code = StageModel.getMaxCode();
		String codePrefix = "";
		String codeSuffix = "";
		if(CommonUtil.isEmpty(code)){
			codePrefix = StaticData.STAGECODE_INIT_VALUE;
		}else{
			codePrefix = code.substring(0,2);
			codeSuffix = code.substring(2,code.length());
			suffix = Integer.parseInt(codeSuffix)+1;
			if(suffix <= 9){
				codeSuffix = "0"+suffix;
				codePrefix = codePrefix + codeSuffix;
			}else{
				codePrefix = codePrefix + suffix;
			}
		}
		this.setAttr("code", codePrefix);
		renderJson();
	}
	
	/**
	 * 删除学阶信息
	 */
	@FuncActionAnnotation(action="/admin/stage/del")
	@Before(FuncActionInterceptor.class)
	public void deleStageInfo(){
		Integer id = getParaToInt("id");
		if(CommonUtil.isEmpty(id)){
			pojo.setCode(AjaxRetPojo.CODE_FAIL);
			pojo.setMsg("要删除的数据不存在");
			renderJson(pojo);
		}else{
			List<GradeModel> gradeList = GradeModel.dao.findGradeByStage(id);
			if(gradeList.size() > 0){
				pojo.setCode(AjaxRetPojo.CODE_FAIL);
				pojo.setMsg("该学阶下存在年级数据");
				renderJson(pojo);
			}else{
				bool = StageModel.deleStageInfo(id);
				if(bool){
					renderJson(AjaxRetPojo.newInstance());
				}else{
					pojo.setCode(AjaxRetPojo.CODE_FAIL);
					pojo.setMsg("系统异常");
					renderJson(pojo);
				}
			}
		}
		
	}
}
