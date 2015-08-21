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
import com.dayang.commons.util.DayangDictionaryUtil;
import com.dayang.commons.util.DictionaryUtil;
import com.dayang.commons.util.EnumAndDicDefine;
import com.dayang.commons.util.EnumUtil;
import com.dayang.commons.util.StaticData;
import com.dayang.conf.interceptor.AuthInterceptor;
import com.dayang.conf.interceptor.FuncActionInterceptor;
import com.dayang.system.model.AlterModel;
import com.dayang.system.model.DeptModel;
import com.dayang.system.model.DictionaryModel;
import com.dayang.system.model.EmpeModel;
import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.Db;

/**
 * 类描述：异动控制类
 * <pre>
 * -------------History------------------
 *   DATE                 AUTHOR         VERSION        DESCRIPTION
 *  2015年5月19日              温建军              V01.00.001		      新增内容   
 * </pre>
 * 
 * @author <a href="wenjj@dayanginfo.com">温建军</a>
 */
public class AltenationController extends AdminBaseController {
	
	
	/**
	 * 异动管理首页
	 */
	@Before(AuthInterceptor.class)
	public void index(){
		this.setAttr("empStatus", DayangDictionaryUtil.getDicListByDicType(StaticData.ALTERSTATUS_dictionaryType));//状态
		renderJsp("index.jsp");
	}

	/**
	 * 异动员工列表查询
	 */
	public void toAlternation(){
		int flag=getParaToInt("flag");
		String ids = getPara("ids");
		long schId = getParaToLong("schId");
		String[] aids = ids.split(",");
		long[] lids = new long[aids.length];
		for(int i=0;i<aids.length;i++){
			lids[i]=Long.parseLong(aids[i]);
		}		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("ids", lids);
		List<EmpeModel> list = EmpeModel.dao.findEmpeModelPage(1,DaYangStaticData.PAGE_MAXROWS,map).getList();
		CommonUtil.setShowValue2List(list, EnumAndDicDefine.EMPE_DEFINETABLE);
		this.setAttr("empList", list);
		
		if(flag==StaticData.TeacherType.inner){			
			this.setAttr("deptList", DeptModel.dao.find("select * from base_department where orgId=? order by seq asc",schId));
			this.setAttr("alterList", DayangDictionaryUtil.findDicByParentId(StaticData.ALTERREASON_dictionaryType, StaticData.ALTERREASON_dictionaryType, StaticData.ALTERREASON_TEACHER));
			renderJsp("innerAltenation.jsp");
		}else if(flag==StaticData.TeacherType.otherAlter){		
			this.setAttr("alterList", DayangDictionaryUtil.findDicByParentId(StaticData.ALTERTYPE_dictionaryType,StaticData.ALTERTYPE_dictionaryType,StaticData.TeacherType.otherAlter));
			renderJsp("otherAltenation.jsp");
		}else if(flag==StaticData.TeacherType.repeat){
			this.setAttr("empStatus", DayangDictionaryUtil.getDicModel(StaticData.ALTERTYPE_dictionaryType,StaticData.TeacherType.repeat));
			this.setAttr("deptList", DeptModel.dao.find("select * from base_department where orgId=? order by seq asc",schId));
			this.setAttr("alterList", DayangDictionaryUtil.findDicByParentId(StaticData.ALTERTYPE_dictionaryType,StaticData.ALTERTYPE_dictionaryType,StaticData.TeacherType.otherAlter));
			this.setAttr("jobList", DayangDictionaryUtil.getDicListByDicType(StaticData.JOBSCAREERS_DICTIONARYTYPE));//职务
			renderJsp("repeatInfo.jsp");
		}else if(flag==StaticData.TeacherType.outerAlter){
			renderJsp("applyInfo.jsp");
		}
	}
	
	/**
	 * 校内异动
	 */	
	@FuncActionAnnotation(action="/admin/altenation/inner")
	@Before({FuncActionInterceptor.class})
	public void innerAlter(){		
		Long deptId= Long.parseLong(getPara("todept").toString());
		String ids =getPara("empIds").toString();
		String[] aids = ids.split(",");
		StringBuffer sql = new StringBuffer("update base_employee set deptId=? where id in (");
		final List<Object> parameters = new ArrayList<Object>();	
		parameters.add(deptId);
		for(int i=0;i<aids.length;i++){
			sql.append(i==aids.length-1?"?)":"?,");
			long id = Long.parseLong(aids[i]);
			parameters.add(id);
			AlterModel model =AlterModel.dao.setModelByEmpId(id);
		
			model.put("toDeptId", getPara("todept"));
			model.put("toDeptName",getPara("deptName"));
			model.put("toSchId", model.get("schId"));
			model.put("toSchName",  model.get("schName"));
			model.put("flag",EnumAll.AlterFlag.SCHOOLFLAG.getValueStr());
			model.put("status",EnumAll.AlterStatus.ALTERFINISHSTATUS.getValueStr());
			model.put("reason", getPara("alterReason"));
			model.put("changeType", StaticData.TeacherType.inner);
			model.put("remark",getPara("remark"));
			model.put("beginTime", new Date());
			model.put("endTime", new Date());
			model.put("creator", getLoginUserId());
			model.save();
		}		
		Db.update(sql.toString(),parameters.toArray());	
		renderJson(AjaxRetPojo.newInstance());
	}	
	
	/**
	 * 返聘
	 */
	@FuncActionAnnotation(action="/admin/altenation/repeat")
	@Before({FuncActionInterceptor.class})
	public void repeatAlter(){		
		Long deptId= Long.parseLong(getPara("todept").toString());
		String ids =getPara("empIds").toString();
		String[] aids = ids.split(",");
		int jobsCareers = getParaToInt("jobsCareers",0);
		StringBuffer sql = new StringBuffer("update base_employee set deptId=?,status=?,jobsCareers=? where id in (");
		final List<Object> parameters = new ArrayList<Object>();	
		parameters.add(deptId);
		parameters.add(StaticData.TeacherStatus.inUsed);
		parameters.add(jobsCareers);
		for(int i=0;i<aids.length;i++){
			sql.append(i==aids.length-1?"?)":"?,");
			long id = Long.parseLong(aids[i]);
			parameters.add(id);
			AlterModel model =AlterModel.dao.setModelByEmpId(id);		
			model.put("toDeptId", getPara("todept"));
			model.put("toDeptName",getPara("deptName"));
			model.put("toSchId", model.get("schId"));
			model.put("toSchName",  model.get("schName"));
			model.put("flag",EnumAll.AlterFlag.SCHOOLFLAG.getValueStr());
			model.put("status",EnumAll.AlterStatus.ALTERFINISHSTATUS.getValueStr());			
			model.put("changeType", StaticData.TeacherType.repeat);
			model.put("remark",getPara("remark"));
			model.put("beginTime", new Date());
			model.put("endTime", new Date());
			model.put("creator", getLoginUserId());
			model.save();
		}		
		Db.update(sql.toString(),parameters.toArray());	
		renderJson(AjaxRetPojo.newInstance());
	}
	
	/**
	 * 其他异动
	 */
	@FuncActionAnnotation(action="/admin/altenation/otherAlter")
	@Before({FuncActionInterceptor.class})
	public void otherAlter(){		
		String ids =getPara("empIds").toString();
		String[] aids = ids.split(",");		
		final List<Object> parameters = new ArrayList<Object>();	
		int type =  getParaToInt("alterOtherType",0);		
		int _status= CommonUtil.getTeaType2Status(type);
		StringBuffer sql = new StringBuffer("update base_employee set status=? where id in (");
		parameters.add(_status);
		for(int i=0;i<aids.length;i++){			
			sql.append(i==aids.length-1?"?)":"?,");
			long id = Long.parseLong(aids[i]);			
			parameters.add(id);
			AlterModel model =AlterModel.dao.setModelByEmpId(id);			;
			model.put("flag",EnumAll.AlterFlag.SCHOOLFLAG.getValueStr());
			model.put("status",EnumAll.AlterStatus.ALTERFINISHSTATUS.getValueStr());			
			model.put("remark",getPara("remark"));
			model.put("changeType",getPara("alterOtherType"));
			model.put("beginTime", new Date());
			model.put("endTime", new Date());
			model.put("creator",getLoginUserId());
			model.save();
		}		
		Db.update(sql.toString(),parameters.toArray());		
		renderJson(AjaxRetPojo.newInstance());
	}
	
	/**
	 * 异动列表查询
	 */
	public void toAltenationList(){
		List<DictionaryModel> list = DictionaryUtil.findDicByParentId(StaticData.ALTERTYPE_dictionaryType,StaticData.ALTERTYPE_dictionaryType,StaticData.TeacherType.otherAlter);
		list.add(DictionaryUtil.getDicModel(StaticData.ALTERTYPE_dictionaryType, StaticData.TeacherType.inner));
		list.add(DictionaryUtil.getDicModel(StaticData.ALTERTYPE_dictionaryType, StaticData.TeacherType.outerAlter));
		list.add(DictionaryUtil.getDicModel(StaticData.ALTERTYPE_dictionaryType, StaticData.TeacherType.repeat));
		this.setAttr("alterList", list);
		this.setAttr("alterStatus",EnumUtil.toEnumList(EnumAll.AlterStatus.ALTERFINISHSTATUS));
		renderJsp("altenationInfo.jsp");		
	}
	
	/**
	 * 异动列表查询
	 */
	public void listAltenation(){
		int pageNumber = getParaToInt("page");
        int pageSize = getParaToInt("rows", DaYangStaticData.PAGE_ROWS);   
        Map<String,Object> m = new HashMap<String,Object>();
        long schId = getParaToLong("schId",0l);
        long deptId = getParaToLong("deptId",0l);
        int changeType = getParaToInt("changeType",0);
        String userName= getPara("userName");
        String status = getPara("status");
        String beginTime= getPara("beginTime");
        String endTime= getPara("endTime");
        m.put("schId",schId);
        m.put("deptId",deptId);
        m.put("changeType",changeType);
        m.put("userName",userName);
        m.put("status",status);
        m.put("beginTime",beginTime);
        m.put("endTime",endTime);
        m.put("flag", EnumAll.AlterFlag.SCHOOLFLAG.getValueStr());
		com.jfinal.plugin.activerecord.Page<AlterModel> p = AlterModel.dao.findAlterPage(pageNumber,pageSize,m);		
		CommonUtil.setShowValue2List(p,EnumAndDicDefine.LOGCHANGE_DEFINETABLE);
		renderJson(JQGridPagePojo.parsePageData(p));
	}	
	
	/**
	 * 判断是否能做变动
	 */
	public void isCanAlter(){
		String alterStatus = getPara("alterStatus");
		int alterType = getParaToInt("alterType",0);
		boolean bl = true;
		if(CommonUtil.isEmptyString(alterStatus) || alterType==0){
			bl=false;
		}else{
			String[] statuses = alterStatus.split(",");		
			for(int i=0;i<statuses.length;i++){				
				bl = CommonUtil.isCanAlter(alterType, Integer.parseInt(statuses[i]));
				if(!bl) break;
			}
		}
		AjaxRetPojo p = AjaxRetPojo.newInstance();
		p.setCode(bl?AjaxRetPojo.CODE_SUCCESS:AjaxRetPojo.CODE_FAIL);
		renderJson(p);
	}

}
