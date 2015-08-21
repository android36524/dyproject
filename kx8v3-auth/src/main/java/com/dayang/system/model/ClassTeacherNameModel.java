package com.dayang.system.model;

import com.dayang.commons.util.CommonStaticData;
import com.dayang.commons.util.CommonUtil;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * 类描述：教师任课管理model
 * <pre>
 * -------------History------------------
 *   DATE                 AUTHOR         VERSION        DESCRIPTION
 *  2015年06月13日            刘建宇      V01.00.001		   新增内容
 * </pre>
 *
 * @author <a href="mailto:liujy@dayanginfo.com">刘建宇</a>
 */
public class ClassTeacherNameModel extends Model<ClassTeacherNameModel>{
    /**
	 *
	 */
	private static final long serialVersionUID = 1L;	
	public static final ClassTeacherNameModel dao = new ClassTeacherNameModel();
	
	/**
	 * 权限控制map
	 */
	
	public static final Map<String, String> DataMap= new HashMap<String,String>(){
		 {
			 put(CommonStaticData.OrgTreeNodeType.SCHFLAG,"a.schId");
			 put(CommonStaticData.OrgTreeNodeType.GRADEFLAG,"a.gradeId");
			 put(CommonStaticData.OrgTreeNodeType.CLASSFLAG,"a.id");
		 }
	 };

    public List<ClassTeacherNameModel> findList(Map<String,Object> param){
    	 StringBuilder sb = new StringBuilder("SELECT	rct.*, s.`name` subjectName,	e.`name` teaName,c.`name` className FROM	r_classteacherinfo rct ");
    	 sb.append(" LEFT JOIN base_subject s ON s.id = rct.subjectId ");
    	 sb.append(" LEFT JOIN base_employee e ON e.id = rct.teacherId ");
    	 sb.append(" LEFT JOIN base_class c ON c.id = rct.classId ");
         sb.append(" where 1=1 ");
         List<Object> list = new ArrayList<Object>();
         Map<String, String> atuhDataMap= new HashMap<String,String>();
         atuhDataMap.put(CommonStaticData.OrgTreeNodeType.CLASSFLAG,"rct.classId");
       //  CommonUtil.appendDataAuthSql(atuhDataMap,sql,params);
         
         CommonUtil.setDefaultPara(param,sb,list,"",null,null,atuhDataMap);
         return ClassTeacherNameModel.dao.find(sb.toString(),list.toArray());
    }

    /**
     * 列表方法
     * @param pageNumber 页码
     * @param pageSize 页数
     * @param paramMap 参数列表
     * @return page
     */
    public Page<ClassModel> findByParams(int pageNumber, int pageSize, Map<String, Object> paramMap) {
        String selectField = "select a.id,a.`name` as className,a.gradeId,b.name as gradeName,c.`name` as classHeadName";
        StringBuilder sql = new StringBuilder();
        sql.append("from base_class a")
                .append(" LEFT JOIN base_grade b on b.id = a.gradeId")
                .append(" LEFT JOIN base_employee c on c.id = a.classHeadId where 1=1");
        List<Object> paramList = new ArrayList<>();
        CommonUtil.setDefaultPara(paramMap,sql,paramList,"a",null,null,DataMap);
        return ClassModel.dao.paginate(pageNumber,pageSize,selectField,sql.toString(),paramList.toArray());
    }

    /**
     * 查询年级学期班级信息
     * @param classId 班级id
     * @param semesterId 学期id
     * @return map
     */
    public Map<String, Object> initSelect(String classId, String semesterId) {
        Map<String,Object> result = new HashMap<>();
        SemesterModel semesterModel = SemesterModel.dao.findById(semesterId);
        result.put("semesterObj", semesterModel);
        ClassModel classModel = ClassModel.dao.findFirst("select a.id as classId,a.name as className,a.schId," +
                "b.id as gradeId,b.name as gradeName from base_class a" +
                " LEFT JOIN base_grade b on a.gradeId = b.id" +
                " where a.id = ?",classId);
        result.put("classObj", classModel);
        long gradeId = classModel.getLong("gradeId");
        List<SubjectModel> subjectModelList = SubjectModel.dao.findSubByGrade((int)gradeId);
        result.put("subjectList",subjectModelList);
        long schId = classModel.getLong("schId");
        result.put("schId",schId);
        return result;
    }

    /**
     * 根据班级id获取班级科目任课老师信息
     * @param classId
     * @return
     */
    public List<ClassTeacherNameModel> findByClassId(String classId) {
        StringBuilder sql = new  StringBuilder("SELECT a.*,b.`name` subjectName,c.`name` teacherName FROM r_classteacherinfo a" );
        sql.append(" LEFT JOIN base_subject b ON b.id = a.subjectId" );
        sql.append(" LEFT JOIN base_employee c ON c.id = a.teacherId  where 1=1  " );
        List<Object> params = new ArrayList<>();
        if(!CommonUtil.isEmptyString(classId) ){
        	sql.append(" and a.classId = ? "); 
            params.add(classId);
        }
        return ClassTeacherNameModel.dao.find(sql.toString(),params.toArray());
    }
}
