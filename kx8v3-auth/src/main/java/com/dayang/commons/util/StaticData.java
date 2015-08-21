package com.dayang.commons.util;

import java.util.HashMap;
import java.util.Map;

public class StaticData {
	
	public final static int SCHYEAR_dictionaryType = 1;     // 学年
	public final static int SEMESTER_dictionaryType = 2;     // 学年
	public final static int NATION_dictionaryType = 3;     // 民族
	public final static int POLITICAL_dictionaryType = 4;     // 政治面貌
	public final static int EDUCATION_dictionaryType = 5;     // 学历
	public final static int JOB_dictionaryType = 6;     // 职务
	public final static int RELATION_DICTIONARYTYPE = 10;//家属关系
	public final static int ALTERREASON_dictionaryType = 9;     // 变动原因
	public final static int ALTERTYPE_dictionaryType = 12;     // 变动类型
	public final static int ALTERSTATUS_dictionaryType = 13;     // 变动状态
	public final static int STUSTATUS_dictionaryType = 20;     // 变动状态
	public final static int FLOWSTATUS_dictionaryType = 23;     // 工作流状态

	public static final int Batch_Size= 1000;
	
	
	public final static int TEASTATUS_INNERALTER = 13;     // 教师状态
	public final static int HEALTH_DICTIONARYTYPE = 15;     // 健康状态
	public final static int FROMWHERE_DICTIONARYTYPE= 17;   //学生来源
	public final static int HOUSEHOLDNATURE_DICTIONARYTYPE= 18;   // 户口性质
	public final static int STAY_DICTIONARYTYPE= 19;//留守儿童
	public final static int TEASTATUS_HEALTH = 15;     // 健康状况

	public final static int JOBSCAREERS_DICTIONARYTYPE = 16;     // 岗位职业
	public final static int DISABILITY_STATUS = 20 ; //学生在校状态
	public final static int DISABILITY_DICTIONARYTYPE = 21; //残疾类型	
	
	/**
	 * 学校类型数据字典
	 * */
	public final static int SCHOOL_TYPE = 7;
	/**
	 * 文理类型数据字典
	 * */
	public final static String SUBJECT_TYPE = "subjectType";
	
	//学阶编码初始值
	public static final String STAGECODE_INIT_VALUE = "XJ01";
	//年级编码初始值
	public static final String GRADECODE_INIT_VALUE = "NJ01";
	//出版社编码初始值
	public static final String PRESSCODE_INIT_VALUE = "CBS01";
	//民族类型编号
	public static final String NATION_INIT_VALUE = "nation ";
	//政治面貌类型编号
	public static final String POLITICAL_INIT_VALUE = "political ";
	//学历类型编号
	public static final String EDUCATION_INIT_VALUE = "education ";
	//职务类型编号
	public static final String JOB_INIT_VALUE = "job ";

	//员工类型:教育局员工
	public static final String EMPE_TYPE_EDB = "1";
	//员工类型:学校员工
	public static final String EMPE_TYPE_SCH = "2";
	
	//查询类型
	public static final Integer SEARCH_FLAG = 1;

	
	public final static int ALTERREASON_STUREPEAT=2;   // 学生转班异动原因
	public final static int ALTERREASON_TEACHER=1;   // 老师异动原因
	public final static int ALTERREASON_STUOTHER=3;   // 学生其他异动原因

	// 教师异动类型
	public static class TeacherType{
		public static final int Retired = 11 ;        // 退休
		public static final int ToRetired = 12;       // 离休 
		public static final int repeat  = 4;         // 返聘
		public static final int callOut  = 5;         // 调出
		public static final int resign = 6;           // 辞职
		public static final int leave = 7;            // 离职
		public static final int expel  = 8;            // 开除
		public static final int unpaidLeave = 16;      // 停薪留职
		public static final int other =99;             // 其他			
		public final static int inner = 1;     // 变动类型
		public final static int outerAlter = 2;     // 变动类型
		public final static int otherAlter = 3;     // 变动类型
	}
	
	// 教师异动状态
	public static class TeacherStatus{
		public static final int Retired = 11 ;        // 退休
		public static final int ToRetired = 12;       // 离休 
		public static final int callOut  = 5;         // 调出
		public static final int resign = 6;           // 辞职
		public static final int leave = 7;            // 离职
		public static final int expel  = 8;            // 开除
		public static final int unpaidLeave = 16;      // 停薪留职
		public static final int other =99;             // 其他	
		public static final int inUsed = 1;           // 在职
		public static final int waiting = 17;            // 待岗
	}
	
	// 学生异动类型
	public static class StudentType{
		public static final int dropOut = 111 ;        // 休学
		public static final int suspension = 113;       // 停学
		public static final int leave  = 131;         // 退学
		public static final int repeat  = 102;         // 复学
		public static final int other =199;             // 其他	
		public final static int inner=101;    // 转班		
		public final static int otherAlter=104;    // 其他
		public final static int outerAlter=103;    // 其他
	}
	
	// 学生在校状态
	public static class StudentStatus{
		public static final int inUsed = 1 ;        // 在读
		public static final int dropOut = 2;        // 休学
		public static final int leave  = 3;    // 退学
		public static final int suspension = 4;          // 停学
		public static final int gradution = 7;      // 毕业
		public static final int turnout  = 10;      // 转学（转出）
		public static final int turnin = 11;        // 转入
		public static final int transfer = 12;      // 转学中
		public static final int expel =14;          // 开除
		public static final int waiting =12;          // 开除
		public static final int other = 99;         // 其他		
	}
	
	// 教师异动状态
	public static class ApproveFLowStatus{
		public static final int apply = 1 ;        // 待审批
		public static final int pass = 2;          // 审批通过
		public static final int nopass  = 3;       // 审批不通过
		public static final int cancel = 4;    // 已撤回
	}
	
	// 字段类型
	public static class FieldDataType{
		public static final int normal = 1 ;        // 普通
		public static final int date  = 2;       // 时间
	}
	
	// 固定角色类型
	public static class roleType{
		public static final int edbAdmin = 1; //教育局管理员ID
		public static final String  edbAdminName ="教育局管理员"; //教育局管理员名称
		public static final int schAdmin = 2; //学校局管理员ID
		public static final String  schAdminName ="学校管理员"; //学校管理员名称
		public static final int studentRole = 3; //学生角色ID
		public static final String  studentRoleName ="学生角色"; //学生角色名称
		public static final int parentRole = 4; //家长角色ID
		public static final String  parentRoleName ="家长角色"; //家长角色名称
		public static final int companyAdmin = 5; //教育局管理员ID
		public static final String companyAdminName = "公司管理员"; //公司管理员名称
		public static final int teacherRole = 6; //教师角色ID
		public static final String teacherName = "教师角色"; //教师角色
		
	}
	// 教师状态可以做的异动
	public static final Map<Integer,int[]> TeaIsCanAlter=new HashMap<Integer,int[]>(){
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		{
			put(StaticData.TeacherStatus.inUsed,new int[]{StaticData.TeacherType.Retired,StaticData.TeacherType.ToRetired,StaticData.TeacherType.callOut,StaticData.TeacherType.resign,StaticData.TeacherType.leave,StaticData.TeacherType.expel,StaticData.TeacherType.unpaidLeave,StaticData.TeacherType.other,StaticData.TeacherType.inner,StaticData.TeacherType.outerAlter,StaticData.TeacherType.otherAlter});
			put(StaticData.TeacherStatus.Retired,new int[]{StaticData.TeacherType.repeat});
			put(StaticData.TeacherStatus.ToRetired,new int[]{StaticData.TeacherType.repeat});
			put(StaticData.TeacherStatus.resign,new int[]{StaticData.TeacherType.repeat});
			put(StaticData.TeacherStatus.leave,new int[]{StaticData.TeacherType.repeat});
			put(StaticData.TeacherStatus.callOut,new int[]{StaticData.TeacherType.repeat});
			put(StaticData.TeacherStatus.expel,new int[]{StaticData.TeacherType.repeat});
			put(StaticData.TeacherStatus.other,new int[]{StaticData.TeacherType.repeat});
		};
	};
	
	// 学生状态可以做的异动
	public static final Map<Integer,int[]> StuIsCanAlter=new HashMap<Integer,int[]>(){
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		{
			put(StaticData.StudentStatus.inUsed,new int[]{StaticData.StudentType.dropOut,StaticData.StudentType.suspension,StaticData.StudentType.leave,StaticData.StudentType.other,StaticData.StudentType.inner,StaticData.StudentType.outerAlter,StaticData.StudentType.otherAlter});
			put(StaticData.StudentStatus.dropOut,new int[]{StaticData.StudentType.repeat});
			put(StaticData.StudentStatus.leave,new int[]{StaticData.StudentType.repeat});
			put(StaticData.StudentStatus.suspension,new int[]{StaticData.StudentType.repeat});
			put(StaticData.StudentStatus.expel,new int[]{StaticData.StudentType.repeat});
			put(StaticData.StudentStatus.other,new int[]{StaticData.StudentType.repeat});
		};
	};
	
	/**
	 * 老师状态转换
	 */
	public static final Map<Integer,Integer> TeaTypeToStatusMap=new HashMap<Integer,Integer>(){
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		{
			put(StaticData.TeacherType.Retired,TeacherStatus.Retired);
			put(StaticData.TeacherType.ToRetired,TeacherStatus.ToRetired);
			put(StaticData.TeacherType.callOut,TeacherStatus.callOut);
			put(StaticData.TeacherType.resign,TeacherStatus.resign);
			put(StaticData.TeacherType.leave,TeacherStatus.leave);
			put(StaticData.TeacherType.expel,TeacherStatus.expel);
			put(StaticData.TeacherType.unpaidLeave,TeacherStatus.unpaidLeave);
			put(StaticData.TeacherType.other,TeacherStatus.other);
			put(StaticData.TeacherType.repeat,TeacherStatus.inUsed);
		};
	};
	
	/**
	 * 学生状态转换
	 */
	public static final Map<Integer,Integer> StuTypeToStatusMap=new HashMap<Integer,Integer>(){
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		{
			put(StaticData.StudentType.dropOut,StudentStatus.dropOut);
			put(StaticData.StudentType.suspension,StudentStatus.suspension);
			put(StaticData.StudentType.leave,StudentStatus.leave);
			put(StaticData.StudentType.repeat,StudentStatus.inUsed);
			put(StaticData.StudentType.other,StudentStatus.other);
			put(StaticData.StudentType.outerAlter,StudentStatus.inUsed);
		};
	};

}
