package com.dayang.system.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dayang.commons.enums.EmpeTypeSch;
import com.dayang.commons.enums.Status;
import com.dayang.commons.enums.TeacherType;
import com.dayang.commons.pojo.QueryDefineParaPojo;
import com.dayang.commons.util.CommonStaticData;
import com.dayang.commons.util.CommonUtil;
import com.dayang.commons.util.StaticData;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;

/**
 * 类描述：学校班级信息
 * <pre>
 * -------------History------------------
 *   DATE                 AUTHOR         VERSION        DESCRIPTION
 *  2015年05月23日            张维      V01.00.001		   新增内容
 * </pre>
 *
 * @author <a href="mailto:zhangwei@dayanginfo.com">张维</a>
 */
public class ClassModel extends Model<ClassModel> {

	/**
	 * 
	 */
  private static final long serialVersionUID = 1L;
  private static final String SEARCHBY_TEACHERNAME="1";  // 1-教师姓名
  private static final String SEARCHBY_TEACHERNO="2";  //2-教师工号
  private static final String SEARCHBY_TEACHERTEL="3";  // 3-手机号码
  private static final String SEARCHBY_DEPTNAME = "4";
  public static final Map<String, QueryDefineParaPojo> DefineMap= new HashMap<String,QueryDefineParaPojo>(){

	  private static final long serialVersionUID = 1L;

		{
			put("gradeName",new QueryDefineParaPojo("gradeName","name","like"));
			put("notid",new QueryDefineParaPojo("notid","id","<>"));
			put("className",new QueryDefineParaPojo("className","name","like"));
		}
	};
	
	/**
	 * 权限控制map
	 */
	public static final Map<String, String> DataMap= new HashMap<String,String>(){
		 /**
		 * 序列号
		 */
		private static final long serialVersionUID = 1L;

		{
			 put(CommonStaticData.OrgTreeNodeType.SCHFLAG,"a.schId");
			 put(CommonStaticData.OrgTreeNodeType.GRADEFLAG,"a.gradeId");
			 put(CommonStaticData.OrgTreeNodeType.CLASSFLAG,"a.id");
		 }
	 };
	 
	public static final ClassModel dao = new ClassModel();

	/**
	 * 分页查询班级信息 
	 * @param pageNumber
	 * @param pageSize
	 * @param param
	 * @return
	 */
	public static  Page<ClassModel> findClassByPage(int pageNumber,int pageSize,Map<String,Object> param){

		StringBuffer select = new StringBuffer(" SELECT c.orgName schoolName, b. NAME gradeName, a. NAME className, a.*, e.id  headTeacherId, e.`name` headTeacher,su.account as accountName,bs.id scheduleId  ");
		StringBuilder sql = new StringBuilder(" FROM base_class a  ");
		sql.append(" LEFT JOIN base_grade b ON a.gradeId = b.id ");
		sql.append(" LEFT JOIN base_organization c ON a.schId = c.id ");
		sql.append(" LEFT JOIN base_employee e ON a.classHeadId = e.id ");
		sql.append(" LEFT JOIN sys_account su ON  a.creator = su.id ");		
		sql.append(" LEFT JOIN busi_schedule bs on bs.classId=a.id and bs.`status`=? ");		
		sql.append(" where 1=1 ");
		List<Object> list = new ArrayList<Object>();
		list.add(Status.ENABLE.getValueStr());	
        CommonUtil.setDefaultPara(param, sql, list, "a",DefineMap,null,DataMap);
        sql.append("ORDER BY b.seq,a.seq ");
		Page<ClassModel> page = ClassModel.dao.paginate(pageNumber, pageSize, select.toString(), sql.toString(),list.toArray());
		return page;
	}
	
	
	
	/**
	 * 根据老师类型获取Sql
	 * @param teacherType
	 * @return
	 */
	public String getTeacherSql(int teacherType){
		StringBuffer sql = new StringBuffer();
		
		if(TeacherType.HEADTEACHER.getValue() == teacherType){
			sql.append(" select a.id, c.name as headTeacher ");
		}else if(TeacherType.TEACHER.getValue() == teacherType){
			sql.append(" select a.id, group_concat(CONCAT(c.name)) as teacher ");
		}
		
		sql.append(" from base_class a ");		
		sql.append(" LEFT JOIN base_employee c on a.classHeadId = c.id ");
		sql.append(" where 1=1 ");		
		sql.append(" GROUP BY a.id ");
		return sql.toString();
	}
	
	
	/**
	 * 查询班级信息
	 * @param param
	 * @return
	 */
	public List<ClassModel> findClassModel(Map<String,Object> param) {
		StringBuilder sb = new StringBuilder("select * from base_class where 1=1 ");
		final List<Object> parameters = new ArrayList<Object>(); 
		CommonUtil.setDefaultPara(param,sb,parameters,"",DefineMap);	
		return ClassModel.dao.find(sb.toString(),parameters.toArray());
	}
	
	
	
	/**
	 * 根据班级ID更改班主任
	 * @param class_classHeadId 班主任ID
	 * @param id 班级ID
	 * @return
	 */
	public static Boolean updateClassHead(Long classHeadId,Long id) {
		String sql = " update base_class set classHeadId = ? where id = ?  ";
		int count=Db.update(sql,classHeadId,id);
		return count>0;
	}
	
	public ClassModel findClassById(long classId){
		String sql = " SELECT a.* from base_class a  where a.id = ?  ";
	   return ClassModel.dao.findFirst(sql,classId);
	}
	
	/**
	 * 根据班级ID获取班级信息和班主任名称 
	 */
	public ClassModel findClassByClassId(long classId){
		String sql = " SELECT a.*, e.id  headTeacherId, e.`name` headTeacher  " +
	            " FROM base_class a LEFT JOIN base_grade b ON a.gradeId = b.id LEFT JOIN base_organization c ON a.schId = c.id  LEFT JOIN base_employee e ON a.classHeadId = e.id where a.id = ?  ";
	   return ClassModel.dao.findFirst(sql,classId);
	}
	
	/**
	 * 根据班级ID查找学生信息
	 * @param _classId
	 * @return
	 */
	public List<StudentModel>  findStuByClassId(String _classId){
		List<StudentModel> stuList = 
				StudentModel.dao.find("select * from base_student where 1=1 and classId = ?", _classId);
		return stuList;
	}
	
	/**
	 * 班级管理中分页查询班主任列表
	 * @param pageNumber
	 * @param pageSize
	 * @param searchBy 查询的字段
	 * @param searchText 查询的值
	 * @param deptId 部门ID
	 * @return
	 */
	public Page<EmpeModel> findTeacherList(int pageNumber,int pageSize,String searchBy,String searchText,String deptId,long schId){
		StringBuffer sql = new StringBuffer();
		sql.append(" from (select (@rownum :=0) ) r , base_employee a LEFT JOIN base_department b ");
		sql.append(" on a.deptId = b.id ");
		sql.append(" where 1=1 ");
		sql.append(" and a.empType='" + EmpeTypeSch.TEACHEREMP.getValue()+"'");
//		sql.append(" and a.status=" + Status.ENABLE.getValue()+"");
		sql.append(" and a.status=" + StaticData.TeacherStatus.inUsed+"");
		//获取页面查询条件 1-教师姓名 2-教师工号 3-手机号码
		List<Object> parameters = new ArrayList<Object>();
		if (CommonUtil.isNotEmptyString(searchText)){
			if(SEARCHBY_TEACHERNAME.equals(searchBy) || !CommonUtil.isNotEmptyString(searchBy)){
				searchText = "%" +searchText+ "%";
				sql.append(" and a.name like ? ");
			}else if(SEARCHBY_TEACHERNO.equals(searchBy)){
				sql.append(" and a.empNo = ? ");
			}else if(SEARCHBY_TEACHERTEL.equals(searchBy)){
				searchText  = "%" + searchText + "%";
				sql.append(" and a.mobile like ? ");
			}else if(SEARCHBY_DEPTNAME.equals(searchBy)){
				searchText  = "%" + searchText + "%";
				sql.append(" and b.deptName like ? ");
			}
			parameters.add(searchText);
		}
        if(schId!=0){
        	sql.append(" and b.orgId = ? ");
        	parameters.add(schId);
        }
		sql.append(" group by a.id ");
		String selSql = " select @rownum:=@rownum+1  AS rowno,  a.id,a.name,a.sex,a.empNo,a.mobile,b.id deptId, b.deptName ";
		return EmpeModel.dao.paginate(pageNumber, pageSize,selSql,sql.toString(),parameters.toArray());
	}

}
