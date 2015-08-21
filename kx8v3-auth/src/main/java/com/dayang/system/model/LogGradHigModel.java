package com.dayang.system.model;

import com.dayang.commons.pojo.QueryDefineParaPojo;
import com.dayang.commons.util.CommonUtil;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;

import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 类描述：学生升学日志
 * <pre>
 * -------------History------------------
 *   DATE                 AUTHOR         VERSION        DESCRIPTION
 *  2015年5月21日             何意            V01.00.001		      新增内容
 * </pre>
 *
 * @author <a href="mailto:heyi@dayang.com">何意 </a>
 */
public class LogGradHigModel extends Model<LogGradHigModel> {

    private static final long serialVersionUID = 1L;

    public static final LogGradHigModel dao = new LogGradHigModel();

    public static final Logger logger = Logger.getLogger(StudentModel.class);

    public static final Map<String, QueryDefineParaPojo> DefineMap= new HashMap<String,QueryDefineParaPojo>(){
        {
            put("name",new QueryDefineParaPojo("studentName","studentName","like"));
        }

    };

    public static final List<String> IngoreList= new ArrayList<String>(){
        /**
         *
         */
        private static final long serialVersionUID = 1L;

        {
            add("telphone");
            add("schId");
            add("studentNo");
            add("startTime");
            add("endTime");
        }
    };
    
	public static Map<String,String>  getDiyDataMap(Map<String,Object> param){
		Map<String,String> dataTypeList= new HashMap<String,String>();
		dataTypeList.put(param.get("schId")==null?"0":param.get("schId").toString(), "hig.schId");
		dataTypeList.put(param.get("gradeId")==null?"0":param.get("gradeId").toString(), "hig.afterGradeId");
		dataTypeList.put(param.get("classId")==null?"0":param.get("classId").toString(), "hig.classId");
		return dataTypeList;
	}
	
    /**
     * 查询升学的学生信息
     * @param pageNumber 页码
     * @param pageSize 页容量
     * @return
     */
    public static Page<LogGradHigModel> findHigGradeStuPage(int pageNumber,int pageSize,Map<String,Object> param){

        StringBuffer sql  = new StringBuffer(" select hig.id,"
        		+ "CASE  stu.studentNo WHEN stu.studentNo IS NULL THEN	stu.studentNo ELSE	hisStu.studentNo END AS studentNo"
        		+ ",CASE  stu.`name` WHEN stu.`name` IS NULL THEN	stu.`name` ELSE	hisStu.`name` END AS `name`"
        		+ ",CASE  stu.sex WHEN stu.sex IS NULL THEN stu.sex ELSE 	hisStu.sex END AS sex,hig.createTime,hig.creator,"
        		+ "CASE  concat(oldG. NAME, c. NAME) WHEN concat(oldG. NAME, c. NAME) IS NULL THEN 	concat(oldG. NAME, c. NAME) ELSE 	concat(oldG. NAME, cl. NAME) END AS gradeNameAndSc,"+
                " GROUP_CONCAT(p.telphone) AS trelphone,a.name as creatorName  ");
        StringBuilder select = new StringBuilder(" FROM log_highgrade hig  ");
        select.append(" LEFT JOIN base_student stu ON   hig.studentId = stu.id");
        select.append(" LEFT JOIN his_student hisStu on hig.studentId = hisStu.id ");
        select.append(" LEFT JOIN base_class c ON hig.classId = c.id");
        select.append(" LEFT JOIN his_class cl on hig.classId = cl.id");
        select.append(" LEFT JOIN base_organization sch ON sch.id = stu.schId");
        select.append(" LEFT JOIN base_grade nowG ON hig.afterGradeId = nowG.id");
        select.append(" LEFT JOIN base_grade oldG ON hig.beforeGradeId = oldG.id ");
        select.append(" LEFT JOIN r_student_info rs ON rs.studentId = stu.id ");
        select.append(" LEFT JOIN sys_account a on hig.creator=a.id");
        select.append(" LEFT JOIN base_parent p ON p.id = rs.parentId  where 1=1 ");
        List<Object> list = new ArrayList<Object>();
        CommonUtil.setDefaultPara(param, select, list, "hig", DefineMap, IngoreList, getDiyDataMap(param));
        String  telphone = param.get("telphone")==null?"":param.get("telphone").toString();
        String  studentNo = param.get("studentNo")==null?"":param.get("studentNo").toString();
        long schId = param.get("schId")==null?0l:Long.parseLong(param.get("schId").toString());
        String startTime = param.get("startTime")==null?"":param.get("startTime").toString();
        String endTime = param.get("endTime")==null?"":param.get("endTime").toString();
        if(!CommonUtil.isEmptyString(telphone)){
            select.append(" and p.telphone like ?");
            list.add("%"+telphone+"%");
        }
        if(schId>0){
            select.append("and hig.schId = ?");
            list.add(schId);
        }
        if(!CommonUtil.isEmptyString(studentNo)){
            select.append(" and stu.studentNo like ?");
            list.add("%"+studentNo+"%");
        }
        if(!CommonUtil.isEmptyString(startTime)){
            select.append(" and hig.createTime >= ?");
            list.add(startTime+"%");
        }
        if(!CommonUtil.isEmptyString(endTime)){
            select.append(" and hig.createTime <= ?");
            list.add(endTime);
        }
        select.append(" group by hig.id");
        logger.info("查询学生升学信息执行SQL："+sql.toString()+select.toString());
        return LogGradHigModel.dao.paginate(pageNumber, pageSize, sql.toString(), select.toString(),list.toArray());
    }
    
    /**
	 * 根据员工ID获取教育局员工信息
	 */
	public LogGradHigModel findhigGradINfoById(int hgId){
		String sql = "select t.*,stu.`name` as stuName,stu.studentNo as studentNo,stu.birthDate,stu.sex,ber.`name` as befGrade ,afr.`name` afeGrade,"
				+ "c.`name` as className from log_highgrade t "
				+ "LEFT JOIN base_student stu on t.studentId = stu.id "
				+ "LEFT JOIN base_grade ber on t.beforeGradeId = ber.id  "
				+ "LEFT JOIN base_grade afr  on t.afterGradeId = afr.id "
				+ "LEFT JOIN base_class c on t.classId = c.id where t.id = ?";
		return LogGradHigModel.dao.findFirst(sql,hgId);
	}
}
