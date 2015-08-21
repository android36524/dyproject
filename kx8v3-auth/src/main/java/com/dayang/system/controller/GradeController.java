package com.dayang.system.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dayang.commons.annotation.FuncActionAnnotation;
import com.dayang.commons.controller.AdminBaseController;
import com.dayang.commons.enums.EnumAll;
import com.dayang.commons.pojo.AjaxRetPojo;
import com.dayang.commons.pojo.JQGridPagePojo;
import com.dayang.commons.util.CommonUtil;
import com.dayang.commons.util.DaYangStaticData;
import com.dayang.commons.util.IDKeyUtil;
import com.dayang.conf.interceptor.AuthInterceptor;
import com.dayang.conf.interceptor.FuncActionInterceptor;
import com.dayang.system.model.GradeModel;
import com.dayang.system.model.GradeSubjectModel;
import com.dayang.system.model.StageModel;
import com.dayang.system.model.SubjectModel;
import com.dayang.system.validator.GradeDelValidator;
import com.dayang.system.validator.GradeValidator;
import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.Db;

/**
 * 
 * 类描述：年级管理
 * <pre>
 * -------------History------------------
 *   DATE       AUTHOR       VERSION        DESCRIPTION
 *  2015-5-18      李中杰               V01.00.001		      新增内容   
 * </pre>
 * 
 * @author <a href="mailto:lizj@dayanginfo.com">李中杰</a>
 */
public class GradeController extends AdminBaseController{

    /**
     * 年级管理
     */
	@Before(AuthInterceptor.class)
    public void index(){
        this.setAttr("fisrtMenu", "知识树管理");
        this.setAttr("secendMenu", "年级管理");
        this.setAttr("flag", EnumAll.GradeFlag.COMMONFLAG.getValue());
        renderJsp("index.jsp");
    }
    
    /**
     * 年级管理
     */
	@Before(AuthInterceptor.class)
    public void indexSch(){
        this.setAttr("fisrtMenu", "知识树管理");
        this.setAttr("secendMenu", "年级管理");
        this.setAttr("flag", EnumAll.GradeFlag.SCHOOLFLAG.getValue());
        renderJsp("indexSch.jsp");
    }
    
    /**
     * 分页查询年级列表
    * @param
    * @return void    返回类型 
    * @throws
     */
    public void listPage(){
    	int flag = getParaToInt("flag",EnumAll.GradeFlag.SCHOOLFLAG.getValue());//学校年级标识
    	int pageNumber = getParaToInt("page");
		int pageSize = getParaToInt("rows",DaYangStaticData.PAGE_ROWS);
		
		Map<String, Object> map =new HashMap<String,Object>();
		map.put("flag", flag);		
		map.put("gradeName", getPara("gradeName"));
		map.put("schId", getParaToLong("schId",0l));
        renderJson(JQGridPagePojo.parsePageData(GradeModel.dao.findGradePage(pageNumber, pageSize,map)));
    }
    
    /**
     * 查询所有的通用年级
     * 
     * @param
     * @return void    返回类型 
     * @throws
     */
    public void findGradeAll(){
    	 renderJson(GradeModel.dao.findGradeAll());
    }
    
    
    /**
     * 根据id获取年级详情
     * 
     * @param
     * @return void    返回类型 
     * @throws
     */
    public void findGradeById(){
    	GradeModel gradeModel= GradeModel.dao.findById(getParaToInt("id"));
        renderJson(gradeModel);
    }
    
    
    /**
     * 年级（新增、修改）
     * 
     * @param
     * @return void    返回类型 
     * @throws
     */
    @FuncActionAnnotation(noIdAction="/admin/grade/add",idAction="/admin/grade/modify",idName = "grade.id")
    @Before({GradeValidator.class, FuncActionInterceptor.class}) 
    public void save(){
    	GradeModel gradeModel = getModel(GradeModel.class,"grade");
    	String subjectIds = getPara("hd_subjectIds");
    	if(gradeModel.getLong("id") != null){
    		gradeModel.update();
    		Db.update("delete from r_gradesubject where gradeId=?", gradeModel.getLong("id"));
        }else {
        	gradeModel.set("id", IDKeyUtil.getIDKey());        	    
        	gradeModel.set("creator", getLoginUserId());
        	gradeModel.set("createTime", new Date());
        	gradeModel.save();
        }
    	if(!CommonUtil.isEmptyString(subjectIds)){
			String[] ids =subjectIds.split(",");
			Integer[] roleids = new Integer[ids.length];
			for(int i=0;i<ids.length;i++){
				roleids[i]=Integer.parseInt(ids[i]);
			}
			GradeSubjectModel.dao.updateSubjectByGrade(gradeModel.getLong("id"), roleids);			
		}
        renderJson(AjaxRetPojo.newInstance());
    }
    
    /**
     * 异步验证编码是否重复
     */
    public void findGradeByCode(){
    	AjaxRetPojo pojo = new AjaxRetPojo();
		String code = getPara("code");
		Long id = getParaToLong("id");
		if(CommonUtil.isEmpty(code)){
			renderJson(AjaxRetPojo.newInstance());
		}else{
			Map<String,Object> m = new HashMap<String,Object>();
			m.put("code",code);
			m.put("notid",id);
			GradeModel sm = GradeModel.dao.findGradeModel(m);
			if(!CommonUtil.isEmpty(sm) && sm.get("id")!=null){
				pojo.setCode(AjaxRetPojo.CODE_FAIL);
				renderJson(pojo);
			}else{
				renderJson(AjaxRetPojo.newInstance());
			}
		}
	}
    
    /**
     * to年级Action 
     */
    public void toAdd(){
    	int flag = getParaToInt("flag");//学校年级标识
    	
    	if(flag==EnumAll.GradeFlag.SCHOOLFLAG.getValue()){
    		long schId = getParaToLong("schId");
    		this.setAttr("gradeList", GradeModel.dao.findGradeAll());
    		this.setAttr("subjectList", SubjectModel.dao.findSubjectAll(flag,schId));
    	}else{	
    		this.setAttr("subjectList", SubjectModel.dao.findSubjectCommon());   
    	}
    	this.setAttr("stageList", StageModel.dao.findStageAll());
    	
    	if(!CommonUtil.isEmpty(getParaToInt("id"))){
    		this.setAttr("gradeSubject",GradeSubjectModel.dao.find("select r.* from r_gradesubject r where r.gradeId = ? ",getParaToInt("id")));
    	}
    	renderJsp("grade-modify.jsp");
    }
    
    /**
     * 根据学阶查询年级列表
     * 
     * @param
     * @return void    返回类型 
     * @throws
     */
    public void findGradeByStage(){
    	Integer stageId = getParaToInt("stageId");
    	if(stageId==null){
    		renderJson(GradeModel.dao.findGrade(EnumAll.GradeFlag.COMMONFLAG.getValue()));
    	}else{
    		renderJson(GradeModel.dao.findGradeByStage(getParaToInt("stageId"), EnumAll.GradeFlag.COMMONFLAG.getValue()));
    	}
    }
    
   
	/**
	 * 年级删除
	 */
	@Before({GradeDelValidator.class, FuncActionInterceptor.class})
	public void del(){
		long id = getParaToLong("id");
	    if(id<=0){
	    	renderJson("result",false);
	    	return;
	    }
	   
	    Db.update("delete from r_gradesubject where gradeId=?", id);
	    GradeModel.dao.deleteById(id);
	    renderJson(AjaxRetPojo.newInstance());		
	}
	
	/**
	 * 根据学校ID查询年级
	 */
	public void findGradeByTerm(){
		Long schId = getParaToLong("schId");
		renderJson(GradeModel.dao.findGradeByTerm(schId));
	}

	/**
	 * 根据学校ID设置该学校年级毕业和升学时间
	 */
	public void setGradeHigerById(){
		Long schId =  getParaToLong("schId");
		List<String> listCode = new ArrayList<String>(){
			/**
			 * 
			 */
			private static final long serialVersionUID = -7813190831501637668L;

			{
				add("NJ06");
				add("NJ09");
				add("NJ12");
			}
		};
		List<GradeModel> gradeList = GradeModel.dao.findGradeByTerm(schId);
		List<GradeModel> gradeGraduation = new ArrayList<GradeModel>();
		List<GradeModel> higGrade = new ArrayList<GradeModel>();
		for(int i=0;i<gradeList.size();i++){
			String code = gradeList.get(i).getStr("code");
			if(listCode.contains(code) ){
				gradeGraduation.add(gradeList.get(i));
			}
		}
		Map<String,List<GradeModel>> map = new HashMap<String,List<GradeModel>>();
		
		if(gradeGraduation.size()>0){
			higGrade.add(gradeGraduation.get(0));
		}
		map.put("gradeList",gradeList);
		map.put("gradeGraduation", higGrade);
		renderJson(map);
	}
	
	/**
	 * 根据id与名称检验在同一个学校下面是否存在同名的年级
	 */
	public void findGradeByName(){
		AjaxRetPojo pojo = new AjaxRetPojo();
		String name = getPara("name");
		Long id = getParaToLong("id");
		Long schId = getParaToLong("schId");
        Map<String,Object> map= new HashMap<String,Object>();
        map.put("id",id);
        map.put("name",name);
        map.put("schId",schId);
        List<GradeModel> list = GradeModel.dao.findGradeByName(map);
        if(list.size() > 0){
			pojo.setCode(AjaxRetPojo.CODE_FAIL);
			renderJson(pojo);
		}else{
			renderJson(AjaxRetPojo.newInstance());
		}
	}
}
