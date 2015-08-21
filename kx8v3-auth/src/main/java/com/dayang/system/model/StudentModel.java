package com.dayang.system.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONObject;
import com.dayang.cas.pojo.AccountPojo;
import com.dayang.cas.util.LoginInfoUtil;
import com.dayang.commons.jfinal.kit.ConditionsKit;
import com.dayang.commons.pojo.QueryDefineParaPojo;
import com.dayang.commons.util.CommonStaticData;
import com.dayang.commons.util.CommonUtil;
import com.dayang.commons.util.DaYangCommonUtil;
import com.dayang.commons.util.IDKeyUtil;
import com.dayang.commons.util.StaticData;
import com.dayang.system.controller.StuParentController;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

/**
 * 类描述：学生管理MODEL
 * <pre>
 * -------------History------------------
 *   DATE                    AUTHOR         VERSION        DESCRIPTION
 *  2015年5月23日下午3:02:04         吴杰东              		 V01.00.001		 新增内容   
 * </pre>
 * 
 * @author <a href="wujd@dayanginfo.com">吴杰东</a>
 */
public class StudentModel extends Model<StudentModel> {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 1L;
	
	public static final Logger logger = Logger.getLogger(StudentModel.class);
	
	public static final StudentModel dao = new StudentModel();
	
	public static final Map<String, QueryDefineParaPojo> DefineMap= new HashMap<String,QueryDefineParaPojo>(){
		 /**
		 * 序列号
		 */
		private static final long serialVersionUID = 1L;

		{
	            put("name",new QueryDefineParaPojo("name","name,nameSpell","like"));
	            put("ids",new QueryDefineParaPojo("ids","id","in"));
	            put("telphone",new QueryDefineParaPojo("telphone","p.telphone","like"));
	     }
		 
	 };
	 /**
	  * 控制数据权限的Map
	  */
	public static final Map<String, String> DataMap= new HashMap<String,String>(){
		 /**
		 * 序列号
		 */
		private static final long serialVersionUID = 1L;

		{
			 put(CommonStaticData.OrgTreeNodeType.SCHFLAG,"s.schId");
			 put(CommonStaticData.OrgTreeNodeType.GRADEFLAG,"s.gradeId");
			 put(CommonStaticData.OrgTreeNodeType.CLASSFLAG,"s.classId");
		 }
	 };
	
	/**
	 * 学生管理根据条件分页查询学生信息
	 * @param pageNumber 页码
	 * @param pageSize 页容量
	 * @return
	 */
	public Page<StudentModel> findStudentPage(int pageNumber,int pageSize,Map<String,Object> param){
		StringBuffer sql  = new StringBuffer(" SELECT sch.orgName AS schoolName, concat(g. NAME, c. NAME) AS gradeNameAndSc, s.*, acc.account,GROUP_CONCAT(p. NAME) AS parentName, GROUP_CONCAT(p. ID) AS parentIds,GROUP_CONCAT(p.telphone) AS trelphone  ");
		StringBuilder select = new StringBuilder(" FROM base_student s LEFT JOIN base_class c ON c.id = s.classId LEFT JOIN base_grade g ON g.id = s.gradeId LEFT JOIN base_organization sch ON sch.id = s.schId LEFT JOIN r_student_info rs ON rs.studentId = s.id LEFT JOIN base_parent p ON p.id = rs.parentId LEFT JOIN sys_account acc on s.accountId=acc.id  where 1=1   ");
        List<Object> list = new ArrayList<Object>();
        CommonUtil.setDefaultPara(param, select, list, "s", DefineMap,null,DataMap);
        select.append("GROUP BY s.id");
		logger.info("查询学生管理信息执行SQL："+sql.toString()+select.toString());
		return StudentModel.dao.paginate(pageNumber, pageSize, sql.toString(), select.toString(),list.toArray());
	}
	
	/**
	 * 学生关联家长,根据条件分页查询学生家长信息
	 * @param pageNumber 页码
	 * @param pageSize 页容量
	 * @param name 学生姓名
	 * @return
	 */
	public static Page<StuParentModel> findStuParentByPage(int pageNumber,int pageSize,String name,String telphone){
		StringBuffer select = new StringBuffer(" select id,name,telphone,sex   ");
		StringBuffer sql = new StringBuffer("  from base_parent where 1=1 ");
		ConditionsKit con = new ConditionsKit();
		if(!CommonUtil.isEmptyStringOrSpace(telphone)){
			con.setValueQuery(ConditionsKit.EQUAL, "telphone", telphone);
		}
		if(!CommonUtil.isEmptyStringOrSpace(name)){
			con.setValueQuery(ConditionsKit.FUZZY, "name", name);
		}
		con.modelToCondition(new StuParentModel());
		sql.append(con.getSql());
		return StuParentModel.dao.paginate(pageNumber, pageSize, select.toString(), sql.toString(),con.getParamList().toArray());
	}
	
	/**
	 * 保存学生信息
	 * @param studentModel 学生信息表model
	 * @param stuInfoModel 学生拓展表model
	 * @return
	 */
	public Boolean saveStudent(String account,long roleId,StudentModel studentModel,StudentInfoModel stuInfoModel){
		Boolean bool;
		 if(studentModel.getLong("id") != null){
	        	studentModel.update();
	        	stuInfoModel.set("id",studentModel.getLong("id"));
	        	bool=stuInfoModel.update();
	        }else {
	        	//增加账号信息
				long accountId = IDKeyUtil.getIDKey();
				AccountPojo accountPojo = new AccountPojo(accountId,account,
						CommonStaticData.AccountDicType.StudentType,studentModel.getStr("name"),
						studentModel.getLong("schId"),studentModel.getStr("telephone"));
				/**
		    	 * wangchong update by 2015-08-03 新增加入调用sns远程接口同步通讯录信息
		    	 */
		    	CommonUtil.saveAccountPoJo(accountPojo, roleId);
		    	
	        	studentModel.set("id", IDKeyUtil.getIDKey());
	        	studentModel.set("accountId", accountId);
	        	studentModel.set("creator",LoginInfoUtil.getAccountInfo().getId());
                studentModel.set("createTime",new Date());
	        	bool=studentModel.save();
	        	stuInfoModel.set("id",studentModel.get("id"));
	        	bool=stuInfoModel.save();
	        }
		 return bool;
	}
	
	/**
	 * 根据学生ID获取学生信息
	 */
	public StudentModel findStudentByStuId(long studentId){
		String sql = " select t1.*,t2.political,t2.householdLocation,t2.householdNature,t2.health,t2.telphone,t2.ziplCode, " +
	            "t2.postalAddr,t2.disabilityType,t2.stay,t2.migrant,t2.soldier, t2.teacher,t2.singleParent,t2.onlySon, t2.poor "+
				"from base_student t1 left join base_student_expand t2 on t1.id=t2.id where t1.id =? ";
		return StudentModel.dao.findFirst(sql,studentId);
	}
	/**
	 * 根据学生ID获取家长信息
	 * @param id
	 * @return
	 */
	public static List<StuParentModel> findParentByStuId(Long id){
		StringBuffer sql = new StringBuffer(" select rs.guardian, rs.relationType, rs.studentId, rs.parentId,rs.schId, p.* FROM r_student_info rs   ");
		sql.append(" LEFT JOIN base_parent p ON   p.id = rs.parentId ");
		sql.append(" WHERE  rs.studentId = ? ");
		return StuParentModel.dao.find(sql.toString(),id);
	}
	
	/**
	 * 根据学校Id查询年级
	 */
	public List<GradeModel>  findGradeBySchId(long schId){
		return GradeModel.dao.find("select id ,name  from base_grade a where 1=1 and a.schId = ? order by a.seq desc ", schId);
	}
	
	/**
	 * 批量新增学生信息历史表
	 */
	public boolean insertAllStu(String[] idArr){
		Map<String,Object> me = StudentModel.dao.getStuInsertMapById(Long.parseLong(idArr[0]));
		List<Object> paras = new ArrayList<Object>();
		String sql = CommonUtil.getInsertSqlString(me,"his_student",paras);
		Object[] arrValue = paras.toArray();
		Object[][] obj = new Object[idArr.length][arrValue.length];
		obj[0]=arrValue;
		for(int i=1;i<idArr.length;i++){
			me =StudentModel.dao.getStuInsertMapById(Long.parseLong(idArr[i]));
			paras = new ArrayList<Object>();
			sql = CommonUtil.getInsertSqlString(me,"his_student",paras);
			obj[i]=paras.toArray();
		}
		Db.batch(sql,obj, StaticData.Batch_Size);
		return true;
	}
	
		/**
		 * 学生毕业管理(将毕业学生信息存储到学生历史表)
		 * @param idArr 学生ID数组
		 * @return
		 */
		public boolean insertAllStuGraduate(String[] idArr){
			/**
			 * 批量新增学生信息历史表
			 */
			Map<String,Object> me = StudentModel.dao.getStuInsertMapById(Long.parseLong(idArr[0]));
			List<Object> paras = new ArrayList<Object>();
			String sql = CommonUtil.getInsertSqlString(me,"his_student",paras);
			Object[] arrValue = paras.toArray();
			Object[][] obj = new Object[idArr.length][arrValue.length];
			obj[0]=arrValue;
			for(int i=1;i<idArr.length;i++){
				me =StudentModel.dao.getStuInsertMapById(Long.parseLong(idArr[i]));
				paras = new ArrayList<Object>();
				sql = CommonUtil.getInsertSqlString(me,"his_student",paras);
				obj[i]=paras.toArray();
			}
			/**
			 * 批量修改学生历史表状态为“已经毕业”
			 */
			StringBuffer updateSql = new StringBuffer(" update his_student stu set stu.status = ? ,stu.graduateTime = ? where stu.id = ?");
			Object[][] objHisStuInfo = new Object[idArr.length][1];
			for(int j=0;j<idArr.length;j++){
				Object[] temp = new Object[3];
				temp[0] = StaticData.StudentStatus.gradution;
				temp[1] = new Date();
				temp[2] = idArr[j];
				objHisStuInfo[j] = temp;
			}

			Db.batch(sql, obj, StaticData.Batch_Size);
			Db.batch(updateSql.toString(),objHisStuInfo,StaticData.Batch_Size);
			return true;

		}
		/**
		 * 根据ID获取MODEL的MAP
		 * @param id
		 * @return
		 */
		public Map<String,Object> getStuInsertMapById(long id){
			StudentModel em = StudentModel.dao.findById(id);
			StudentExtModel stu = StudentExtModel.dao.findById(id);
			Map<String,Object> me = em.getAttrs();
			Map<String,Object> mei = stu.getAttrs();
			mei.remove("id");
			me.putAll(mei);
			return me;
		}

		/**
		 * 批量删除毕业学生信息
		 */
	public boolean delAllStu(String[] idArr){
		StringBuffer delStuSql = new StringBuffer(" delete from base_student where id = ?");
		Object[][] objstu = new Object[idArr.length][1];
		for(int i=0;i<idArr.length;i++){
			Object[] temp = new Object[1];
			temp[0] = idArr[i];
			objstu[i] = temp;
		}
		StringBuffer delStuExtSql = new StringBuffer(" delete from base_student_expand where id = ?");
		Object[][] objStuExt = new Object[idArr.length][1];
		for(int i=0;i<idArr.length;i++){
			Object[] temp = new Object[1];
			temp[0] = idArr[i];
			objStuExt[i] = temp;
		}

		Db.batch(delStuSql.toString(),objstu, StaticData.Batch_Size);
		Db.batch(delStuExtSql.toString(),objStuExt, StaticData.Batch_Size);
		return true;
	}
	
	/**
	 * 验证是否为家长的最后一个关联孩子
	 * @param schId 学校Id
	 * @param studentId 学生Id
	 * @param allIds 所有需要删除的家长ID
	 * @param otherIds  其他学校的家长ID
	 * @return
	 */
	public boolean validateLastParent(Long schId,String studentId,String allIds,String otherIds){
		String [] pidArr= allIds.split(","); //所有需要删除的家长ID
		String [] otherArr= otherIds.split(","); //不是当前学校的家长ID
		//取当前学校的家长ID
		List<String> list=CommonUtil.compare(otherArr,pidArr);
		if(list.size()<=1){
			return true;
		}else{
			//循环处理每个家长是否绑定的是最后一个孩子
			for(String pid: list){
				//该家长当前学校是否关联的是最后一个孩子
				List<RstuAndParentModel> rstuList = RstuAndParentModel.dao.findRstudent_info(StuParentController.EQUAL,schId, pid);
				 if(rstuList.size()<=1){
					 return true;
				 }
			}
		}
		return false;
	}
	
	/**
	 * 取回学校MODEL
	 * @return
	 */
	public OrgModel getSchool(){
    	return OrgModel.dao.findById(get("schId"));
    }
    
	/**
	 * 取回年级MODEL
	 * @return
	 */
	public GradeModel getGrade(){
		return GradeModel.dao.findById(get("gradeId"));
	}
    
	/**
	 * 取回班级MODEL
	 * @return
	 */
	public ClassModel getClassModel(){
		return ClassModel.dao.findById(get("classId"));
	}
	
	/**
     * 根据学校id获取年级字符串数组(id=gradeName)
     * @param schId 学校id
     * @return id=gradeName组成的字符串数组
     */
    public List<String> findGradeNameListBySchId(String schId){
        List<GradeModel> gradeModels = GradeModel.dao.find("select g.name from base_grade g " +
                "where g.schId = ?",schId);
        List<String> result = new ArrayList<>();
        for (int i = 0; i < gradeModels.size(); i++){
        	GradeModel gradeModel = gradeModels.get(i);
            String temp = gradeModel.get("name");
            result.add(temp);
        }
        return result;
    }
    
    /**
     * 根据学校id和年级名称获取班级名称列表
     * @param schId 班级Id
     * @param gradName 年级名称
     * @return
     */
    public List<String> findClassNameListBySchId(long schId,String gradName){
    	 StringBuilder sql = new StringBuilder("select a.name from base_class a LEFT JOIN base_grade b on b.id = a.gradeId where 1=1 ");
    	 List<Object> params = new ArrayList<>(); 
    	 if(schId != 0)
         {
             sql.append(" and b.schId  = ?");
             params.add(schId);
         }
         if(!CommonUtil.isEmptyString(gradName))
         {
             sql.append(" and b.name = ?");
             params.add(gradName);
         }
        List<ClassModel> classModels = ClassModel.dao.find(sql.toString(),params.toArray());
        List<String> result = new ArrayList<>();
        for (int i = 0; i < classModels.size(); i++){
        	ClassModel classModel = classModels.get(i);
            String temp = classModel.get("name");
            result.add(temp);
        }
        return result;
    }
    
    /**
     * 根据年级名称和学校ID获取年级信息
     * @param Name 年级名称
     * @param schId  学校ID
     * @param Id  年级    
     * @return
     */
    public GradeModel findGardeBySchId(String Name,long schId,long Id) {
        StringBuilder sql = new StringBuilder("select g.* from base_grade g where 1=1 ");
        List<Object> params = new ArrayList<>();
        if(schId != 0)
        {
            sql.append(" and g.schId  = ?");
            params.add(schId);
        }
        if(!CommonUtil.isEmptyString(Name))
        {
            sql.append(" and g.name = ?");
            params.add(Name);
        }
        if (Id != 0){
            sql.append(" and g.id != ?");
            params.add(Id);
        }
        return GradeModel.dao.findFirst(sql.toString(),params.toArray());
    }
    
    /**
     * 根据班级名称和年级ID及学校ID获取班级信息
     * @param Name 年级名称
     * @param schId  学校ID
     * @param gradeId 年级ID
     * @param Id  年级id
     * @return
     */
    public ClassModel findClassBySchId(String Name,long schId,long gradeId,long Id) {
        StringBuilder sql = new StringBuilder("select c.* from base_class c where 1=1 ");
        List<Object> params = new ArrayList<>();
        if(schId != 0)
        {
            sql.append(" and c.schId  = ?");
            params.add(schId);
        }
        if(gradeId != 0)
        {
            sql.append(" and c.gradeId  = ?");
            params.add(gradeId);
        }
        if(!CommonUtil.isEmptyString(Name))
        {
            sql.append(" and c.name = ?");
            params.add(Name);
        }
        if (Id != 0){
            sql.append(" and c.id != ?");
            params.add(Id);
        }
        return ClassModel.dao.findFirst(sql.toString(),params.toArray());
    }
    
    
    /**
	 * 更改学生关联的家长
	 * @param studentId  学生ID
	 * @param stuList
	 * @return
	 */
	public static Boolean saveRelaStu(Long studentId,List<JSONObject> stuList){
		Boolean bool = updateRelationById(new RstuAndParentModel(), studentId, stuList);
		return bool;
	}
	
	/**
	 * 更改学生家长关联关系表
	 * @param rstuModel 学生家长关联表对象
	 * @param id 学生ID
	 * @param list
	 * @return
	 */
	private static Boolean updateRelationById(RstuAndParentModel rstuModel,
			Long id, List<JSONObject> list) {
		Boolean bool = false;
		String sql = " delete from r_student_info where studentId = ?  ";
		Db.update(sql,id);
		//先删除其关联的家长 再重新新增
		for(int i = 0;i<list.size();i++){
			JSONObject tempObj = (JSONObject) list.get(i);
			Long parentId = tempObj.getLong("parentId");
			Integer guardian = tempObj.getInteger("guardian");
			Integer relationType = tempObj.getInteger("relationType");
			Long schId= tempObj.getLong("schId");
			bool = rstuModel.set("id",IDKeyUtil.getIDKey()).set("studentId", id).set("parentId", parentId).
			set("guardian", guardian).set("relationType", relationType).set("schId", schId).save();
		}
		return bool;
	}
	/**
	 * 导出学生信息
	 * @param param
	 * @return
	 */
	public List<StudentModel> findstudentList(Map<String,Object> param){
		StringBuffer sql  = new StringBuffer(" SELECT g. NAME gradeName, c. NAME className, s.*, s. NAME stuName, epd.*   ");
		StringBuilder select = new StringBuilder("  FROM base_student s LEFT JOIN base_student_expand epd ON s.id = epd.id LEFT JOIN base_class c ON c.id = s.classId LEFT JOIN base_grade g ON g.id = s.gradeId LEFT JOIN base_organization sch ON sch.id = s.schId  WHERE 1 = 1   ");
        List<Object> list = new ArrayList<Object>();
        CommonUtil.setDefaultPara(param, select, list, "s", DefineMap);
        select.append("GROUP BY s.id");
		logger.info("导出学生信息执行SQL："+sql.toString()+select.toString());
		List<StudentModel> dataSource = StudentModel.dao.find(sql.toString()+select.toString(),list.toArray());
		return dataSource;
	}
	
	/**
	 * 根据学生ID获取账号ID
	 * @param idStr
	 * @return
	 */
	public String findAccountIds(String idStr){
	    StringBuilder sql = new StringBuilder("select t.* from base_student t where 1=1 and FIND_IN_SET(t.id,?) ");
		List<Record> reList = Db.find(sql.toString(),idStr);
		String idStrs = DaYangCommonUtil.column2Str(reList, "accountId");
		return idStrs;
	}



}
