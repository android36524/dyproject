package com.dayang.system.model;

import com.dayang.commons.pojo.QueryDefineParaPojo;
import com.dayang.commons.util.CommonStaticData;
import com.dayang.commons.util.CommonUtil;
import com.dayang.commons.util.UtilDate;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;

import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 类描述：教育局部门管模型
 * <pre>
 * -------------History------------------
 *   DATE                 AUTHOR         VERSION        DESCRIPTION
 *  2015年5月18日             何意            V01.00.001		      新增内容
 * </pre>
 *
 * @author <a href="mailto:heyi@dayang.com">何意 </a>
 */
public class DeptModel extends Model<DeptModel>{

    private static final long serialVersionUID = 1L;

    public static final DeptModel dao = new DeptModel();

    private static Logger logger = Logger.getLogger(DeptModel.class);
    public static final Map<String, QueryDefineParaPojo> DefineMap= new HashMap<String,QueryDefineParaPojo>(){
        /**
         *
         */
        private static final long serialVersionUID = 1L;

        {
            put("deptName",new QueryDefineParaPojo("deptName","deptName","like"));
            put("id",new QueryDefineParaPojo("id","id","!="));
            put("deptId",new QueryDefineParaPojo("deptId","pid","="));
        }
    };
    /**
	 * 忽略参数定义
	 */
	public static final List<String> IngoreList= new ArrayList<String>(){
		/**
		 *
		 */
		private static final long serialVersionUID = 1L;

		{
			add("orgType");
		}
	};

	public Map<String,String> getDiyDataMap(Map<String,Object> param){
		Map<String,String> dataTypeList= new HashMap<String,String>();
		dataTypeList.put(CommonStaticData.OrgTreeNodeType.DEPTFLAG, "t.id");
		dataTypeList.put(param.get("orgType")==null?"0":param.get("orgType").toString(),"t.orgId");
		return dataTypeList;
	}
	
    /**
     * 查询部门分页列表
     * @param pageNumber
     * @param pageSize
     * @param isleaf
     * @param level
     * @param param
     * @return
     */
    public  Page<DeptModel> findDeptPage(int pageNumber, int pageSize,int isleaf,int level,Map<String,Object> param){
        String sql = " select t.*,"+level+" level,"+isleaf+" isleaf,org.orgName,CASE dept.deptName WHEN dept.deptName is NULL THEN dept.deptName ELSE org.orgName END as parentName,a.name as creatorName ";
        StringBuilder select = new StringBuilder(" from base_department t left join base_organization org on t.orgId = org.id");
        select.append(" LEFT JOIN sys_account a on t.creator=a.id");
        select.append(" left join base_department dept on dept.id = t.pid where 1 = 1");
        List<Object> list = new ArrayList<Object>();
        CommonUtil.setDefaultPara(param, select, list, "t", DefineMap,IngoreList,getDiyDataMap(param));
        long deptId = param.get("deptId")==null?0l:Long.parseLong(param.get("deptId").toString());
        String deptName =param.get("deptName")==null?"":String.valueOf(param.get("deptName"));
        if( deptId<=0){
        	if(!"".equals(deptName)){
        		select.append(" and 1=1 ");
        	}else{
        		select.append(" and (t.pid is null or t.pid =0) ");
        	}
        }
        String orgType = (String)param.get("orgType");
        if("2".equals(orgType)){
        	select.append(" and org.orgFlag='"+orgType+"'");
        	select.append(" order by t.seq desc");
            return DeptModel.dao.paginate(pageNumber, pageSize,sql,select.toString(),list.toArray());
        }else{
        	select.append(" order by t.seq desc");
            return DeptModel.dao.paginate(pageNumber, pageSize,sql,select.toString(),list.toArray());
        }
    }

    /**
     * 查询部门列表
     * @param isleaf
     * @param level
     * @param param
     * @return
     */
    public  List<DeptModel> findDeptList(int isleaf,int level,Map<String,Object> param){
        StringBuilder sb = new StringBuilder("select t.*,"+level+ " level,"+isleaf+" isleaf,org.orgName,org.orgFlag ");
        sb.append(" from base_department t  left join base_organization org on t.orgId = org.id");
        sb.append(" where 1=1 ");
        List<Object> list = new ArrayList<Object>();
        Map<String, String> atuhDataMap= new HashMap<String,String>();
        atuhDataMap.put(CommonStaticData.OrgTreeNodeType.DEPTFLAG,"t.id");
        CommonUtil.setDefaultPara(param,sb,list,"t",DefineMap,IngoreList,atuhDataMap);
        long deptId = param.get("deptId")==null?0l:Long.parseLong(param.get("deptId").toString());
        if( deptId<=0){
            sb.append(" and (t.pid is null or t.pid =0) ");
        }
        long id = param.get("id")==null?0l:Long.parseLong(param.get("id").toString());
        if( id > 0){
            sb.append(" and t.id != ? ");
            list.add(id);
        }
        sb.append(" order by t.seq desc ");
        return DeptModel.dao.find(sb.toString(),list.toArray());
    }

    /**
     * 根据Id获取教育局/学校部门
     * @param deptId 部门id
     * @return DeptModel
     */
    public DeptModel findByDeptId(long deptId){
        String sql = "SELECT t.*, dept.deptName AS parentName FROM base_department t " +
                "LEFT JOIN base_department dept ON dept.id = t.pid " +
                "LEFT JOIN base_organization org ON t.orgId = org.id WHERE t.id = ? ";
        return DeptModel.dao.findFirst(sql,deptId);
    }

    /**
     * 根据机构id获取部门字符串数组(id=deptName)
     * @param orgId 机构id
     * @return id=deptName组成的字符串数组
     */
    public List<String> findDeptNameListByOrgId(String orgId){
        List<DeptModel> deptModels = DeptModel.dao.find("select t.deptName from base_department t " +
                "where t.orgId = ?",orgId);
        List<String> result = new ArrayList<>();
        for (int i = 0; i < deptModels.size(); i++){
            DeptModel deptModel = deptModels.get(i);
            String temp = deptModel.get("deptName");
            result.add(temp);
        }
        return result;
    }




    /**
     * 当前教育局下部门名称是否唯一
     * @param deptName 部门名称
     * @param orgId 教育局id
     * @return DeptModel
     */
    public DeptModel validateDeptName(String deptName,long orgId,long deptId) {
        StringBuilder sql = new StringBuilder("select t.* from base_department t where 1=1 ");
        List<Object> params = new ArrayList<>();
        if(orgId != 0)
        {
            sql.append(" and t.orgId = ?");
            params.add(orgId);
        }
        if(!CommonUtil.isEmptyString(deptName))
        {
            sql.append(" and t.deptName = ?");
            params.add(deptName);
        }
        if (deptId != 0){
            sql.append(" and t.id != ?");
            params.add(deptId);
        }
        return DeptModel.dao.findFirst(sql.toString(),params.toArray());
    }
    
    /**
     * 根据学校ID获取学校信息
     * @param schId
     * @return
     */
    public  List<DeptModel> findDeptNameBySchId(long schId){
    	return DeptModel.dao.find("select a.id, a.deptName from base_department a where 1=1 and a.orgId = ?", schId);
    }
}
