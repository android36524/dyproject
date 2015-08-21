package com.dayang.system.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dayang.commons.annotation.FuncActionAnnotation;
import com.dayang.commons.controller.AdminBaseController;
import com.dayang.commons.enums.EnumAll;
import com.dayang.commons.pojo.AjaxRetPojo;
import com.dayang.commons.pojo.EnumPojo;
import com.dayang.commons.pojo.JQGridPagePojo;
import com.dayang.commons.util.CommonUtil;
import com.dayang.commons.util.DaYangStaticData;
import com.dayang.commons.util.EnumUtil;
import com.dayang.commons.util.IDKeyUtil;
import com.dayang.conf.interceptor.AuthInterceptor;
import com.dayang.conf.interceptor.FuncActionInterceptor;
import com.dayang.system.model.GradeSubjectModel;
import com.dayang.system.model.SubjectModel;
import com.dayang.system.validator.SubjectDelValidator;
import com.dayang.system.validator.SubjectValidator;
import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.Db;

/**
 * 类描述：科目管理类
 * <pre>
 * -------------History------------------
 *   DATE                 AUTHOR         VERSION        DESCRIPTION
 *  2015年5月18日              温建军              V01.00.001		      新增内容   
 * </pre>
 * 
 * @author <a href="wenjj@dayanginfo.com">温建军</a>
 */
public class SubjectController extends AdminBaseController {
	
	private static AjaxRetPojo pojo = new AjaxRetPojo();

	/**
	 * 菜单管理首页
	 */
	@Before(AuthInterceptor.class)
	public void index(){
		this.setAttr("flag", EnumAll.SubjectFlag.COMMONFLAG.getValue());
		renderJsp("index.jsp");
	}
	
	/**
	 * 菜单管理首页
	 */
	@Before(AuthInterceptor.class)
	public void indexSch(){
		this.setAttr("flag", EnumAll.SubjectFlag.SCHOOLFLAG.getValue());
		renderJsp("indexSch.jsp");
	}
	
	/**
	 * 新增修改科目界面初始化
	 * @throws Exception
	 */
	public void toAdd(){
		long id = getParaToLong("id",0l);
		int flag = getParaToInt("flag");   // 是否通用
		long schId = getParaToLong("schId",0l);
		if(flag==EnumAll.SubjectFlag.SCHOOLFLAG.getValue()){
			if(id>0){
				Map<String,Object> map= new HashMap<String,Object>();
				map.put("flag", EnumAll.SubjectFlag.COMMONFLAG.getValue());
				List<SubjectModel> list = SubjectModel.dao.findSubjectList(map);
				if(!CommonUtil.isEmptyCollection(list)){
					this.setAttr("otherModels", list);
				}
			}else{
				List<SubjectModel> list = SubjectModel.dao.findSubjectNoContains(schId);			
				if(!CommonUtil.isEmptyCollection(list)){
					this.setAttr("otherModels", list);
				}else {
					AjaxRetPojo p = AjaxRetPojo.newInstance();
					p.setCode(AjaxRetPojo.CODE_FAIL);
					p.setMsg("当前不支持新增科目");
					renderJson(p);	
					return;
				}
			}
		}				
		EnumPojo schEnum = EnumUtil.toEnumPojo(EnumAll.SubjectFlag.SCHOOLFLAG);
		EnumPojo commonEnum = EnumUtil.toEnumPojo(EnumAll.SubjectFlag.COMMONFLAG);
		this.setAttr("schEnum", schEnum);
		this.setAttr("commonEnum", commonEnum);
		this.setAttr("flag", flag);
		this.setAttr("curId", id);
		if(flag==schEnum.getValue()){
			renderJsp("subjectInfo.jsp");		
		}else{
			renderJsp("commSubInfo.jsp");
		}
	}
	
	/**
	 * 新增修改科目
	 */
	@FuncActionAnnotation(noIdAction="/admin/subject/add",idAction="/admin/subject/modify",idName="subject.id")
	@Before({SubjectValidator.class,FuncActionInterceptor.class})
	public void add(){
		SubjectModel subjectModel = getModel(SubjectModel.class,"subject");		
		if(subjectModel.getLong("id") != null){
			subjectModel.update();
		}else{
			subjectModel.set("createTime", new Date());
			subjectModel.set("creator", getLoginUserId());
			subjectModel.set("id", IDKeyUtil.getIDKey());
			subjectModel.save();
		}		
		renderJson(AjaxRetPojo.newInstance());
	}
	
	/**
	 * 科目列表查询
	 */
	public void list(){
		String name=getPara("name");	
		int flag = getParaToInt("flag");
		int page =getParaToInt("page");
		long schId = getParaToLong("schId",0l);
		int rows = getParaToInt("rows", DaYangStaticData.PAGE_ROWS);		
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("flag", flag);
		if(!CommonUtil.isEmptyString(name)){
			param.put("name", name);
		}
		if(schId>0){
			param.put("schId", schId);
		}
		renderJson(JQGridPagePojo.parsePageData(SubjectModel.dao.findSubjectPage(page, rows, param)));		
	}
	
	/**
	 * 通过年级ID获取科目
	 */
	public void findSubByGrade(){
    	Integer gradeId = getParaToInt("gradeId");
    	renderJson(SubjectModel.findSubByGrade(gradeId));
	}
	
	/**
	 * 学科删除
	 */
	@Before({SubjectDelValidator.class,FuncActionInterceptor.class})
	public void del(){
		long id = getParaToLong("id");
	    if(id<=0){
	    	renderJson("result",false);
	    	return;
	    }
	    Db.update("delete from base_subject where id=?", id);
	    SubjectModel.dao.deleteById(id);
	    renderJson(AjaxRetPojo.newInstance());		
	}
	
	/**
	 * 根据ID查询用户
	 */
	public void queryById(){
		renderJson(SubjectModel.dao.findById(getParaToLong("id")));
	}
	
	/**
	 * 查询所有的通用科目   不分页 
	 * 
	 * @param     设定文件 
	 * @return void    返回类型 
	 * @throws
	 */
	public void findSubjectAll(){
		renderJson(SubjectModel.dao.findSubjectCommon());
	}
	
	
	/**
	 * 根据年级查询科目
	 * 
	 * @param     设定文件 
	 * @return void    返回类型 
	 * @throws
	 */
	public void findSubjectByGrade(){
		renderJson(GradeSubjectModel.dao.findSubjectByGrade(getParaToLong("gradeId")));
	}
	
	/**
	 * 根据名称与ID查询 是否已经存在此数据
	 */
	public void findSubByCondition(){
		String name = getPara("name");
		Long id = getParaToLong("id");
        Map<String,Object> map= new HashMap<String,Object>();
        map.put("id",id);
        map.put("name",name);
        List<SubjectModel>  list = SubjectModel.dao.findSubByCondition(map);
        if(list.size() > 0){
			pojo.setCode(AjaxRetPojo.CODE_FAIL);
			renderJson(pojo);
		}else{
			renderJson(AjaxRetPojo.newInstance());
		}
	}
}
