package com.dayang.system.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dayang.commons.enums.EnumAll;
import com.dayang.commons.pojo.QueryDefineParaPojo;
import com.dayang.commons.util.CommonStaticData;
import com.dayang.commons.util.CommonUtil;
import com.dayang.commons.util.IDKeyUtil;
import com.dayang.commons.util.StaticData;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;

/**
 * 类描述：异动Model
 * <pre>
 * -------------History------------------
 *   DATE                 AUTHOR         VERSION        DESCRIPTION
 *  2015年5月26日              温建军              V01.00.001		      新增内容   
 * </pre>
 * 
 * @author <a href="wenjj@dayanginfo.com">温建军</a>
 */
public class AlterModel extends Model<AlterModel> {
	 private static final long serialVersionUID = 1L;
	 public static final Map<String, QueryDefineParaPojo> DefineMap= new HashMap<String,QueryDefineParaPojo>(){
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		{
			put("userName",new QueryDefineParaPojo("userName","userName","like"));
			put("notid",new QueryDefineParaPojo("notid","id","<>"));
			put("beginTime",new QueryDefineParaPojo("beginTime","beginTime",">"));
			put("endTime",new QueryDefineParaPojo("endTime","endTime","<"));
		}
	};
	
	 /**
	  * 控制数据权限的Map
	  */
	 public static final Map<String, String> TEACHERAUTHMAP= new HashMap<String,String>(){
		 {
			 put(CommonStaticData.OrgTreeNodeType.SCHFLAG,"l.schId");
			 put(CommonStaticData.OrgTreeNodeType.DEPTFLAG,"l.deptId");
		 }
	 };
	 
	 /**
	  * 控制数据权限的Map
	  */
	 public static final Map<String, String> STUDENTAUTHMAP= new HashMap<String,String>(){
		 {
			 put(CommonStaticData.OrgTreeNodeType.SCHFLAG,"l.schId");
			 put(CommonStaticData.OrgTreeNodeType.GRADEFLAG,"l.gradeId");
			 put(CommonStaticData.OrgTreeNodeType.CLASSFLAG,"l.classId");
		 }
	 };

	public static final AlterModel dao = new AlterModel(); 	
	 
	 /**
	  * 给Model设置值
	  * @param id
	  * @return
	  */
	public AlterModel setModelByEmpId(long id){
		EmpeModel em = EmpeModel.dao.findById(id);
		OrgModel sm = em.getSchool();
		
		DeptModel dp = em.getDept();
		AlterModel model = new AlterModel();
		model.put("id", IDKeyUtil.getIDKey());			
		model.put("userId",em.get("id"));
		model.put("userName", em.get("name"));
		model.put("sex", em.get("sex"));
		model.put("telphone", em.get("telphone"));
		model.put("schId", em.get("orgId"));
		model.put("deptId", em.get("deptId"));
		model.put("schName", sm==null?"":sm.get("orgName"));
		model.put("deptName",  dp==null?"":dp.get("deptName"));			
		return model;
	}
	 
	/**
	 * 给学生MODEL设值 
	 * @param id
	 * @return
	 */
	public AlterModel setStuModelById(long id){
		StudentModel em = StudentModel.dao.findById(id);
		OrgModel sm = em.getSchool();		
		ClassModel cm = em.getClassModel();
		AlterModel model = new AlterModel();
		model.put("id", IDKeyUtil.getIDKey());			
		model.put("userId",em.get("id"));
		model.put("userName", em.get("name"));
		model.put("sex", em.get("sex"));
		model.put("telphone", em.get("telphone"));
		model.put("schId", em.get("schId"));
		model.put("classId", em.get("classId"));
		model.put("gradeId", em.get("gradeId"));
		model.put("schName", sm==null?"":sm.get("orgName"));
		model.put("className",  cm==null?"":cm.get("name"));			
		return model;
	}
	 /**
	  * 查询列表
	  * @return
	  */
	 public Page<AlterModel> findAlterPage(int pageNumber,
				int pageSize,Map<String,Object> m){
		String selSql = "select l.*,(select concat(g.name,l.className) from base_grade g where g.id = l.gradeId) AS gradeNameAndSc,ca.name creatorName,ca.account account,aa.name editorName ";
		StringBuilder sb = new StringBuilder("from log_change l LEFT JOIN sys_account ca on l.creator=ca.id LEFT JOIN sys_account aa on l.editor=aa.id where 1=1");
		List<Object> paralist = new ArrayList<Object>();
		if(EnumAll.AlterFlag.SCHOOLFLAG.getValueStr().equals(m.get("flag"))){//教师
			CommonUtil.setDefaultPara(m,sb,paralist,"l",DefineMap,null,TEACHERAUTHMAP);	
		}else if(EnumAll.AlterFlag.STUDENTFLAG.getValueStr().equals(m.get("flag"))){//学生
			CommonUtil.setDefaultPara(m,sb,paralist,"l",DefineMap,null,STUDENTAUTHMAP);	
		}
		sb.append(" order by l.beginTime desc");
		return AlterModel.dao.paginate(pageNumber, pageSize, selSql,
				sb.toString(), paralist.toArray());
	 }
	 
	 /**
	  * 取得学生对象
	  * @return
	  */
	 public StudentModel getStudent(){
		 return StudentModel.dao.findById(get("userId"));
	 }
	 
}
