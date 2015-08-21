package com.dayang.system.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.dayang.commons.annotation.FuncActionAnnotation;
import com.dayang.commons.controller.AdminBaseController;
import com.dayang.commons.enums.EnumAll;
import com.dayang.commons.pojo.AjaxRetPojo;
import com.dayang.commons.pojo.JQGridPagePojo;
import com.dayang.commons.util.CommonUtil;
import com.dayang.commons.util.DaYangStaticData;
import com.dayang.commons.util.DictionaryUtil;
import com.dayang.commons.util.EnumAndDicDefine;
import com.dayang.commons.util.IDKeyUtil;
import com.dayang.commons.util.StaticData;
import com.dayang.conf.interceptor.AuthInterceptor;
import com.dayang.conf.interceptor.FuncActionInterceptor;
import com.dayang.system.model.AlterModel;
import com.dayang.system.model.ApproveFlowModel;
import com.dayang.system.model.ApproveItemModle;
import com.dayang.system.model.DeptModel;
import com.dayang.system.model.DictionaryModel;
import com.dayang.system.model.GradeModel;
import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;

/**
 * 类描述：流程控制类
 * <pre>
 * -------------History------------------
 *   DATE                 AUTHOR         VERSION        DESCRIPTION
 *  2015年5月19日              温建军              V01.00.001		      新增内容   
 * </pre>
 * 
 * @author <a href="wenjj@dayanginfo.com">温建军</a>
 */
public class ApproveFlowController extends AdminBaseController {
	
	/**
	 * 工作流分页查询
	 */
	public void listPage(){
		int pageNumber = getParaToInt("page");
        int pageSize = getParaToInt("rows", DaYangStaticData.PAGE_ROWS);
        Map<String,Object> params= new HashMap<String,Object>();
        params.put("schName",getPara("schName"));
        params.put("teaName",getPara("teaName"));
        int status = getParaToInt("status",0);
        int flag = getParaToInt("flag",EnumAll.ApproveFlowFlag.Teacher.getValue());
        params.put("flag",flag+"");
        params.put("status",status);
        params.put("toSchId",getParaToLong("toSchId",0l));
        params.put("schId",getParaToLong("schId",0l));
        params.put("gradeId",getParaToLong("gradeId",0l));
        Page<ApproveFlowModel> afPage = ApproveFlowModel.dao.findApproveFlowPage(pageNumber, pageSize, params);
        CommonUtil.setShowValue2List(afPage, EnumAndDicDefine.APPROVEFLOW_DEFINETABLE);
        renderJson(JQGridPagePojo.parsePageData(afPage));
	}	
	
	@Before(AuthInterceptor.class)
	public void index(){
		this.setAttr("statusList", DictionaryUtil.getDicListByDicType(StaticData.FLOWSTATUS_dictionaryType));
		renderJsp("index.jsp");
	}
	
	@Before(AuthInterceptor.class)
	public void indexCancel(){
		this.setAttr("statusList", DictionaryUtil.getDicListByDicType(StaticData.FLOWSTATUS_dictionaryType));
		renderJsp("indexCancel.jsp");
	}	
	
	public void toApprove(){		
		long flowid = getParaToLong("flowid",0l);
		long schId = getParaToLong("schId",0l);
		Map<String,Object> params= new HashMap<String,Object>();
		params.put("flowid", flowid);
		params.put("flag", getPara("flag"));
		this.setAttr("deptList", DeptModel.dao.find("select * from base_department where orgId=? order by seq asc",schId));
		this.setAttr("jobList", DictionaryUtil.getDicListByDicType(StaticData.JOBSCAREERS_DICTIONARYTYPE));//职务
		Page<AlterModel> afPage = AlterModel.dao.findAlterPage(1, DaYangStaticData.PAGE_ROWS, params);
		this.setAttr("userList", afPage.getList());
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("schId", schId);
		this.setAttr("gradeList", GradeModel.dao.findGradeList(map));
		renderJsp("approve.jsp");
	}
	
	/**
	 * 处理审批结果
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@FuncActionAnnotation(diyAction="status^2:/admin/approveFlow/approve,3:/admin/approveFlow/approve,4:/admin/approveFlow/revoke")
    @Before(FuncActionInterceptor.class)
	public void approve(){
		long flowid = getParaToLong("flowid",0l);
		long shcId = getParaToLong("shcId",0l);		
		String userlist = getPara("userlist");
		int statuses = getParaToInt("status",0);		
		Map<String,Object> map = new HashMap<String,Object>();
		
	    // 处理工作流
	    ApproveFlowModel fm = ApproveFlowModel.dao.findById(flowid);	    
	    fm.set("approver", getLoginUserId());
		fm.set("approveTime", new Date());
		fm.set("status",statuses);
		fm.set("approveRemark",getPara("remark"));
		fm.update();
		// 创建审批单条目
		ApproveItemModle im = new ApproveItemModle();
		im.put("id", IDKeyUtil.getIDKey());
		im.put("flowId", fm.get("id"));		
		im.put("remark", getPara("remark"));
		im.put("status", statuses+"");
		if(statuses==StaticData.ApproveFLowStatus.pass||statuses==StaticData.ApproveFLowStatus.nopass){
			im.put("flag", EnumAll.ApproveItemFlag.approve.getValueStr());
			im.put("name", fm.get("schName")+"-审批单");
		}else{
			im.put("flag", EnumAll.ApproveItemFlag.cancel.getValueStr());
			im.put("name", fm.get("schName")+"-撤销单");
		}		
		im.put("operatorTime", new Date());
		im.put("seq", 2);
		im.put("operator", getLoginUserId());
		im.save();
		final List<Object> parameters = new ArrayList<Object>();
		String table = "base_employee";
		String flag = fm.get("flag");
		if(flag.equals(EnumAll.ApproveFlowFlag.Student.getValueStr())){
			table="base_student";
		}
		StringBuffer sql = new StringBuffer("update "+table+" set status=? ");
		Object[][] obj = null;		
		
		Iterator it = null;
		int num = 0;
		
		// 如果没有传userlist，则从数据库中查询
		if(CommonUtil.isEmptyString(userlist)){
			Map<String,Object> params= new HashMap<String,Object>();
			params.put("flowid", flowid);
			params.put("flag", flag);
			Page<AlterModel> afPage = AlterModel.dao.findAlterPage(1, DaYangStaticData.PAGE_ROWS, params);
			List<AlterModel> listModel = afPage.getList();
			List<Map> listtemp = new ArrayList();
			for(int i=0;i<listModel.size();i++){
				Map<String,Object> mt = new HashMap();
				mt.put("id", listModel.get(i).get("id"));
				mt.put("userId", listModel.get(i).get("userId"));
				listtemp.add(mt);
			}
			it = listtemp.iterator();
			num =listtemp.size();
		}else{
			List<? extends Map> list = JSON.parseArray(userlist, map.getClass());
			num = list.size();
			it = list.iterator();
		}
		if(statuses==StaticData.ApproveFLowStatus.pass){
			if(flag.equals(EnumAll.ApproveFlowFlag.Teacher.getValueStr())){
				sql.append(",orgId=?,deptId=?,jobsCareers=?");
				obj = new Object[num][5];
			}else{
				sql.append(",schId=?,gradeId=?,classId=?");
				obj = new Object[num][5];
			}
		}else{
			obj = new Object[num][2];
		}
		sql.append( " where id = ?");
		int i=0;
		while(it.hasNext())	{	
			Map<String, Object> m = (Map)it.next();
			long id = Long.parseLong(m.get("userId").toString());
			Object[] temp = null;
			long alterid = Long.parseLong(m.get("id").toString());
			AlterModel model =AlterModel.dao.findById(alterid);
			if(statuses==StaticData.ApproveFLowStatus.pass){
				if(flag.equals(EnumAll.ApproveFlowFlag.Teacher.getValueStr())){
					temp = new Object[5];
					temp[0] = StaticData.TeacherStatus.inUsed;
					temp[1] =shcId;
					temp[2] =m.get("deptId");
					temp[3] =m.get("jobs");
					temp[4] =id;
					model.set("toDeptId", m.get("deptId"));		
					model.set("toDeptName", m.get("toDeptName"));
				}else{
					temp = new Object[5];
					temp[0] = StaticData.StudentStatus.inUsed;
					temp[1] =shcId;
					temp[2] =m.get("gradeId");
					temp[3] =m.get("classId");
					temp[4] =id;
					model.set("toClassId", m.get("classId"));		
					model.set("toClassName", m.get("toClassName"));
					model.set("toGradeId", m.get("gradeId"));	
				}
			}else{
				temp = new Object[2];
				temp[0] =  StaticData.TeacherStatus.inUsed;
				temp[1] = id;
			}			
			obj[i] = temp;
			parameters.add(id);			
			model.set("status",EnumAll.AlterStatus.ALTERFINISHSTATUS.getValueStr());	
			model.set("endTime", new Date());			
			model.set("editor", getLoginUserId());
			model.update();
			i++;
		}
		Db.batch(sql.toString(), obj, StaticData.Batch_Size);		
		renderJson(AjaxRetPojo.newInstance());
	}
	
	/**
	 * 校外调岗申请
	 */
	@FuncActionAnnotation(diyAction="flag^1:/admin/approveFlow/apply,2:/admin/stuAltenation/outerAlter")
	@Before({FuncActionInterceptor.class})
	public void apply(){		
		String ids =getPara("empIds").toString();
		int flag = getParaToInt("flag",EnumAll.ApproveFlowFlag.Teacher.getValue());
		int changeType = getParaToInt("changeType",StaticData.TeacherType.outerAlter);
		String talbe = "base_employee";
		int _status= StaticData.TeacherStatus.waiting;
		if(flag==EnumAll.ApproveFlowFlag.Student.getValue()){
			talbe="base_student";
			_status = StaticData.StudentStatus.waiting;
		}
		
		String[] aids = ids.split(",");		
		final List<Object> parameters = new ArrayList<Object>();	
		
		StringBuffer sql = new StringBuffer("update "+talbe+" set status=? where id in (");
		parameters.add(_status);
		// 创建申请单
		ApproveFlowModel fm = new ApproveFlowModel();
		long flowId = IDKeyUtil.getIDKey();
		fm.put("id", flowId);
		fm.put("flag",flag+"");
		DictionaryModel dm = DictionaryUtil.getDicModel(StaticData.FLOWSTATUS_dictionaryType, StaticData.ApproveFLowStatus.apply);
		fm.put("status",CommonUtil.isEmpty(dm)?0:dm.getInt("value"));
		fm.put("schId", getParaToLong("schId",0l));
		fm.put("schName", fm.getSchool()==null?"":fm.getSchool().get("orgName"));
		fm.put("toSchId", getParaToLong("o_school",0l));
		fm.put("toSchName", getPara("toSchName"));
		fm.put("remark", getPara("remark"));
		fm.put("name", fm.get("schName"));
		fm.put("creator", getLoginUserId());
		fm.put("createTime", new Date());
		fm.save();
		// 创建申请单条目
		ApproveItemModle im = new ApproveItemModle();
		im.put("id", IDKeyUtil.getIDKey());
		im.put("flowId", flowId);
		im.put("name", fm.get("schName")+"-申请单");
		im.put("remark", getPara("remark"));
		im.put("status", EnumAll.ApproveItemStatus.create.getValueStr());
		im.put("flag", EnumAll.ApproveItemFlag.apply.getValueStr());
		im.put("operatorTime", new Date());
		im.put("seq", 1);
		im.put("operator", getLoginUserId());
		im.save();
		for(int i=0;i<aids.length;i++){			
			sql.append(i==aids.length-1?"?)":"?,");
			long id = Long.parseLong(aids[i]);			
			parameters.add(id);
			AlterModel model = null;
			if(flag==EnumAll.ApproveFlowFlag.Teacher.getValue()){
				model =AlterModel.dao.setModelByEmpId(id);
			}else{
				model =AlterModel.dao.setStuModelById(id);
			}
			model.put("flag",flag+"");
			model.put("status",EnumAll.AlterStatus.ALTERTINGSTATUS.getValueStr());			
			model.put("remark",getPara("remark"));
			model.put("changeType",changeType);
			model.put("beginTime", new Date());		
			model.put("creator", getLoginUserId());
			model.put("toSchId", getParaToLong("o_school",0l));
			model.put("toSchName", getPara("toSchName"));
			model.put("flowId", flowId);
			model.save();
		}		
		Db.update(sql.toString(),parameters.toArray());		
		renderJson(AjaxRetPojo.newInstance());
	}
}
