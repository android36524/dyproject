package com.dayang.system.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dayang.commons.enums.EnumAll;
import com.dayang.commons.pojo.QueryDefineParaPojo;
import com.dayang.commons.util.CommonStaticData;
import com.dayang.commons.util.CommonUtil;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;

/**
 * 类描述 ：工作流MODEL
 * <pre>
 * -------------History------------------
 *   DATE                 AUTHOR         VERSION        DESCRIPTION
 *  2015年6月3日              温建军              V01.00.001		      新增内容   
 * </pre>
 * 
 * @author <a href="wenjj@dayanginfo.com">温建军</a>
 */
public class ApproveFlowModel extends Model<ApproveFlowModel>  {
	 private static final long serialVersionUID = 1L;
	 public static final ApproveFlowModel dao = new ApproveFlowModel(); 
	 public static final Map<String, QueryDefineParaPojo> DefineMap= new HashMap<String,QueryDefineParaPojo>(){
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		{
			put("schName",new QueryDefineParaPojo("schName","schName","like"));
			put("teaName",new QueryDefineParaPojo("teaName","lc.userName","like"));
			put("gradeId",new QueryDefineParaPojo("gradeId","lc.gradeId","="));
		}
	};
	
	 /**
	  * 控制数据权限的Map
	  */
	 public static final Map<String, String> TEACHERMAP= new HashMap<String,String>(){
		 {
			 put(CommonStaticData.OrgTreeNodeType.SCHFLAG,"lc.schId");
			 put(CommonStaticData.OrgTreeNodeType.DEPTFLAG,"lc.deptId");
		 }
	 };
	 
	 /**
	  * 控制数据权限的Map
	  */
	 public static final Map<String, String> STUDENTAUTHMAP= new HashMap<String,String>(){
		 {
			 put(CommonStaticData.OrgTreeNodeType.SCHFLAG,"lc.schId");
			 put(CommonStaticData.OrgTreeNodeType.GRADEFLAG,"lc.gradeId");
			 put(CommonStaticData.OrgTreeNodeType.CLASSFLAG,"lc.classId");
		 }
	 };
	 
	public OrgModel getSchool(){
		return OrgModel.dao.findById(get("schId"));
	}
	 
	public OrgModel getToSchool(){
		return OrgModel.dao.findById(get("toSchId"));
	}
	 
	 /**
	  * 查询工作流Page
	  * @param pageNumber
	  * @param pageSize
	  * @param params
	  * @return
	  */
	 public Page<ApproveFlowModel> findApproveFlowPage(int pageNumber,int pageSize,Map<String,Object> params){
		 String selSql = "select wa.*,(select GROUP_CONCAT(lc1.userName) from log_change lc1 where lc1.flowId=wa.id  group by flowId) namelist, (select GROUP_CONCAT(lc1.userName,'【',lc1.className,'】') from log_change lc1 where lc1.flowId=wa.id  group by flowId) nameAndClassList,ca.name creatorName,aa.name approveName";
		 StringBuilder sb = new StringBuilder("from wfl_approveflow wa left join log_change lc on lc.flowId =  wa.id LEFT JOIN sys_account ca on wa.creator=ca.id LEFT JOIN sys_account aa on wa.approver=aa.id where 1=1 ");
		 List<Object> paralist = new ArrayList<Object>();
		 if(EnumAll.AlterFlag.SCHOOLFLAG.getValueStr().equals(params.get("flag"))){//教师
			 CommonUtil.setDefaultPara(params,sb,paralist,"wa",DefineMap,null,TEACHERMAP);
		 }else if(EnumAll.AlterFlag.STUDENTFLAG.getValueStr().equals(params.get("flag"))){//学生
			 CommonUtil.setDefaultPara(params,sb,paralist,"wa",DefineMap,null,STUDENTAUTHMAP);
		 }
		 sb.append("GROUP BY wa.id");
		 return ApproveFlowModel.dao.paginate(pageNumber, pageSize, selSql,
					sb.toString(), paralist.toArray());
	 }
}
