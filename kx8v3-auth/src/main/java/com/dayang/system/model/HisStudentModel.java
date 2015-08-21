package com.dayang.system.model;

import com.dayang.commons.pojo.QueryDefineParaPojo;
import com.dayang.commons.util.CommonStaticData;
import com.dayang.commons.util.CommonUtil;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;

import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 类描述：学生信息历史表
 * <pre>
 * -------------History------------------
 *   DATE                 AUTHOR         VERSION        DESCRIPTION
 *  2015年5月21日             何意            V01.00.001		      新增内容
 * </pre>
 *
 * @author <a href="mailto:heyi@dayang.com">何意 </a>
 */
public class HisStudentModel extends Model<HisStudentModel> {

    private static final long serialVersionUID = 1L;

    public static final HisStudentModel dao = new HisStudentModel();

    public static final Logger logger = Logger.getLogger(StudentModel.class);

    public static final Map<String, QueryDefineParaPojo> DefineMap= new HashMap<String,QueryDefineParaPojo>(){
        {
            put("name",new QueryDefineParaPojo("name","name,nameSpell","like"));
            put("ids",new QueryDefineParaPojo("ids","id","in"));
        }

    };

    public static final List<String> IngoreList= new ArrayList<String>(){
        /**
         *
         */
        private static final long serialVersionUID = 1L;

        {
            add("telphone");
            add("studentNo");
            add("startTime");
            add("endTime");
        }
    };
    
	public static Map<String,String>  getDiyDataMap(Map<String,Object> param){
		Map<String,String> dataTypeList= new HashMap<String,String>();
		dataTypeList.put(param.get("schId")==null?"0":param.get("schId").toString(), "s.schId");
		dataTypeList.put(param.get("gradeId")==null?"0":param.get("gradeId").toString(), "s.gradeId");
		dataTypeList.put(param.get("classId")==null?"0":param.get("classId").toString(), "s.classId");
		return dataTypeList;
	}
	
    /**
     * 查询已毕业的学生信息
     * @param pageNumber 页码
     * @param pageSize 页容量
     * @return
     */
    public static Page<HisStudentModel> findGraduateStuPage(int pageNumber,int pageSize,Map<String,Object> param){

        StringBuffer sql  = new StringBuffer(" SELECT sch.orgName AS schoolName, CASE concat(g. NAME, c. NAME) WHEN concat(g. NAME, c. NAME) is NULL THEN concat(g. NAME, c. NAME) ELSE concat(g. NAME, cl. NAME)  END as gradeNameAndSc,concat(g. NAME, cl. NAME) AS gradeNameAndSc1, s.*, " +
                "GROUP_CONCAT(p. NAME) AS parentName, GROUP_CONCAT(p.telphone) AS trelphone  ");
        StringBuilder select = new StringBuilder(" FROM his_student s LEFT JOIN his_class c ON c.id = s.classId    ");
        select.append("left join base_class cl on s.classId = cl.id ");
        select.append(" LEFT JOIN base_grade g ON g.id = s.gradeId");
        select.append(" LEFT JOIN base_organization sch ON sch.id = s.schId");
        select.append(" LEFT JOIN r_student_info rs ON rs.studentId = s.id");
        select.append(" LEFT JOIN base_parent p ON p.id = rs.parentId where 1=1  ");
        List<Object> list = new ArrayList<Object>();
        CommonUtil.setDefaultPara(param, select, list, "s", DefineMap, IngoreList, getDiyDataMap(param));
        String telphone = param.get("telphone")==null?"":param.get("telphone").toString();
        String startTime = param.get("startTime")==null?"":param.get("startTime").toString();
        String endTime = param.get("endTime")==null?"":param.get("endTime").toString();
		if(telphone != ""){
			select.append(" and p.telphone like ? ");
			list.add("%"+telphone+"%");
		}
		String studentNo = param.get("studentNo")==null?"":param.get("studentNo").toString();
		if(studentNo!=""){
			select.append(" and s.studentNo like ? ");
			list.add("%"+studentNo+"%");
		}
		
		if(!CommonUtil.isEmptyString(startTime)){
            select.append(" and s.graduateTime >= ?");
            list.add(startTime+"%");
        }
        if(!CommonUtil.isEmptyString(endTime)){
            select.append(" and s.graduateTime <= ?");
            list.add(endTime);
        }
		
        select.append("GROUP BY s.id  ORDER BY s.graduateTime DESC");
        logger.info("查询学生管理信息执行SQL："+sql.toString()+select.toString());
        return HisStudentModel.dao.paginate(pageNumber, pageSize, sql.toString(), select.toString(),list.toArray());
    }
}
