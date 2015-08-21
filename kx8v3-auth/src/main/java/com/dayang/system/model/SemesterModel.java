package com.dayang.system.model;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.dayang.commons.pojo.QueryDefineParaPojo;
import com.dayang.commons.util.CommonStaticData;
import com.dayang.commons.util.CommonUtil;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;

public class SemesterModel extends Model<SemesterModel>{
	private static final long serialVersionUID = 1L;
	
	public static final SemesterModel dao = new SemesterModel();
	public static final Map<String, QueryDefineParaPojo> DefineMap= new HashMap<String,QueryDefineParaPojo>(){
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		{
			put("name",new QueryDefineParaPojo("name","name","like"));
			put("notid",new QueryDefineParaPojo("notid","id","<>"));
		}
	};
	
	/**
	 * 权限控制map
	 */
	
	public static final Map<String, String> DataMap= new HashMap<String,String>(){
		 {
			 put(CommonStaticData.OrgTreeNodeType.SCHFLAG,"s.schId");
		 }
	 };
	
	/**
	 * 根据条件查询user详情
	 * @param param
	 * @return
	 */
	public SemesterModel findSemester(Map<String,Object> param) {
		StringBuilder sb = new StringBuilder("select * from base_semester where 1=1 ");
		List<Object> list = new ArrayList<Object>();
		CommonUtil.setDefaultPara(param,sb,list,"",DefineMap);		
		return SemesterModel.dao.findFirst(sb.toString(),list.toArray());
	}
	
	/**
	 * 查询学期的分页信息
	 * @param pageNumber
	 * @param pageSize
	 * @param param
	 * @return
	 */
	public Page<SemesterModel> findSubjectPage(int pageNumber,int pageSize,Map<String,Object> param) {
		StringBuilder sql = new StringBuilder(" select s.*,o.orgName schId_showname,u.account ");
		StringBuilder sb = new StringBuilder(" from base_semester s LEFT JOIN base_organization o on o.id=s.schId LEFT JOIN sys_account u ON s.creator = u.id where 1=1 ");
		List<Object> list = new ArrayList<Object>();
		CommonUtil.setDefaultPara(param,sb,list,"s",DefineMap,null,DataMap);
		sb.append(" order by s.createTime desc ");
		return SemesterModel.dao.paginate(pageNumber, pageSize,sql.toString(),sb.toString(),list.toArray());
	}
}
