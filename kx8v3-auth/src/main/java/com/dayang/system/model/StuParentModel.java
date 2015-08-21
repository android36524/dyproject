package com.dayang.system.model;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONObject;
import com.dayang.cas.pojo.AccountPojo;
import com.dayang.cas.util.LoginInfoUtil;
import com.dayang.commons.pojo.QueryDefineParaPojo;
import com.dayang.commons.util.CommonStaticData;
import com.dayang.commons.util.CommonUtil;
import com.dayang.commons.util.DaYangCommonUtil;
import com.dayang.commons.util.EnumAndDicDefine;
import com.dayang.commons.util.IDKeyUtil;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.IAtom;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

/**
 * 类描述：学生家长管理MODEL
 * <pre>
 * -------------History------------------
 *   DATE                 AUTHOR         VERSION        DESCRIPTION
 *  2015年5月22日下午2:48:30        吴杰东            		 V01.00.001		 新增内容   
 * </pre>
 * 
 * @author <a href="wujd@dayanginfo.com">吴杰东  </a>
 */
public class StuParentModel extends Model<StuParentModel> {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 1L;

	public static final StuParentModel dao = new StuParentModel();
	
	public static final Logger logger = Logger.getLogger(StuParentModel.class);
	
	 @SuppressWarnings("serial")
	public static final Map<String, QueryDefineParaPojo> DefineMap= new HashMap<String,QueryDefineParaPojo>(){
		 {
	            put("name",new QueryDefineParaPojo("name","bp.name","like"));
	            put("telphone",new QueryDefineParaPojo("telphone","bp.telphone","like"));
	            put("gradeId",new QueryDefineParaPojo("gradeId","bs.gradeId","="));
	            put("classId",new QueryDefineParaPojo("classId","bs.classId","="));
	            put("schId",new QueryDefineParaPojo("schId","rsi.schId","="));
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
			 put(CommonStaticData.OrgTreeNodeType.SCHFLAG,"rsi.schId");
			 put(CommonStaticData.OrgTreeNodeType.GRADEFLAG,"bs.gradeId");
			 put(CommonStaticData.OrgTreeNodeType.CLASSFLAG,"bs.classId");
		 }
	 };
	 
	 public static final List<String> IngoreList= new ArrayList<String>(){
			/**
			 *序列号
			 */
			private static final long serialVersionUID = 1L;

			{
				add("studentId"); 
				add("parentIds");
			}
	};
	
	public static final List<String> StuIngoreList= new ArrayList<String>(){
		/**
		 *
		 */
		private static final long serialVersionUID = 1L;

		{
			add("studentId"); 
			add("parentIds");
		}
};


	/**
	 * 根据条件查询学生家长
	 * @param pageNumber
	 * @param pageSize
	 * @param param
	 * @return
	 */
	public static  Page<StuParentModel> findParentByPage(int pageNumber,int pageSize,Map<String,Object> param){
		StringBuffer select = new StringBuffer(" SELECT *  ");
		StringBuilder sql = new StringBuilder("  FROM ( select DISTINCT bp.* ,acc.account  from base_parent bp LEFT JOIN r_student_info rsi on rsi.parentId = bp.ID LEFT JOIN base_student bs ON rsi.studentId = bs.id LEFT JOIN sys_account acc on bp.accountId=acc.id  where 1=1 ");
		List<Object> list = new ArrayList<Object>();
        CommonUtil.setDefaultPara(param, sql, list, "bp", DefineMap,IngoreList,DataMap);
        sql.append(" ) temp ");
		logger.info("查询学生家长信息执行SQL："+sql.toString());
		Page<StuParentModel> page = StuParentModel.dao.paginate(pageNumber, pageSize, select.toString(), sql.toString(),list.toArray());
		List<StuParentModel> parentlist = page.getList();
		for(int i = 0;i < parentlist.size();i++){
			StuParentModel tempModel = parentlist.get(i);
			Long gradeId=(Long) param.get("gradeId")==null?0l:(Long) param.get("gradeId");
			Long classId=(Long) param.get("classId")==null?0l:(Long) param.get("classId");
			Long schId=(Long) param.get("schId")==null?0l:(Long) param.get("schId");
			List<StudentModel> stuList = findStuByParentId(tempModel.getLong("ID"),schId,gradeId, classId);
			CommonUtil.setShowValue2List(stuList, EnumAndDicDefine.RELA_STUDENT);
			tempModel.put("stuList", stuList);
		}
		return new Page<StuParentModel>(parentlist,pageNumber,pageSize,page.getTotalPage(),page.getTotalRow());
	}
	/**
	 * 家长选择中，家长信息查询。
	 * @param pageNumber
	 * @param pageSize
	 * @param param
	 * @return
	 */
	public static  Page<StuParentModel> findParentBySchId(int pageNumber,int pageSize,Map<String,Object> param){
		StringBuffer select = new StringBuffer(" select DISTINCT bp.*,rsi.schId ");
		StringBuilder sql = new StringBuilder("  from base_parent bp LEFT JOIN r_student_info rsi on rsi.parentId = bp.ID  where 1=1 ");
		List<Object> list = new ArrayList<Object>();
		 Map<String, String> atuhDataMap= new HashMap<String,String>();
	     atuhDataMap.put(CommonStaticData.OrgTreeNodeType.SCHFLAG,"rsi.schId");
        CommonUtil.setDefaultPara(param, sql, list, "bp", DefineMap,IngoreList,atuhDataMap);
        String parentIds=(String) param.get("parentIds");
        if(!CommonUtil.isEmptyString(parentIds)){
        	sql.append(" and bp.ID not in ( "+param.get("parentIds")+")   ");
        }
		logger.info("查询学生家长信息执行SQL："+sql.toString());
		Page<StuParentModel> page = StuParentModel.dao.paginate(pageNumber, pageSize, select.toString(), sql.toString(),list.toArray());
		return page;
	}
	
	/**
	 * 保存家长信息
	 * @param model
	 * @param rstuModel
	 * @param objs
	 * @return
	 */
	public static Boolean saveStuParent(String account,long roleId,boolean isExist,Long schId,StuParentModel model,RstuAndParentModel rstuModel,Object...objs){
		Long id = model.get("ID");
		Boolean bool = false;
		@SuppressWarnings("unchecked")
		List<JSONObject> list = (List<JSONObject>) objs[0];
		Long priId = IDKeyUtil.getIDKey();
		if(CommonUtil.isEmpty(id)){//新增
			if(!isExist){ // 不存在相同记录，新增家长账号和家长信息.
				//增加账号信息
				long accountId = IDKeyUtil.getIDKey();
				AccountPojo accountPojo = new AccountPojo(accountId,account,
						CommonStaticData.AccountDicType.ParentType,model.getStr("name"),0l,model.getStr("telphone"));
				/**
		    	 * wangchong update by 2015-08-03 新增加入调用sns远程接口同步通讯录信息
		    	 */
		    	CommonUtil.saveAccountPoJo(accountPojo, roleId);
		    	
				bool = model.set("ID",priId).set("accountId", accountId).set("creator",LoginInfoUtil.getAccountInfo().getId()).set("createTime",new Date()).save();//新增家长基本信息
			}
			for(int i = 0;i<list.size();i++){
				JSONObject tempObj = (JSONObject) list.get(i);
				Long studentId = tempObj.getLong("studentId");
				Integer guardian = tempObj.getInteger("guardian");
				Integer relationType = tempObj.getInteger("relationType");
				bool=rstuModel.set("id",IDKeyUtil.getIDKey()).set("studentId", studentId).set("parentId", priId).
				set("guardian", guardian).set("relationType", relationType).set("schId", schId).save();
			}
		}else{//修改
			if(!isExist){
				bool = model.update();
			}
			bool=updateRelationById(schId,rstuModel, id, list);
		}
		return bool;
	}

	/**
	 * 更改关联信息表
	 * @param rstuModel
	 * @param id
	 * @param list
	 * @return
	 */
	private static Boolean updateRelationById(Long schId,RstuAndParentModel rstuModel,
			Long id, List<JSONObject> list) {
		Boolean bool = false;
		String sql = " delete from r_student_info where parentId = ? and schId = ?  ";
		Db.update(sql,id,schId);
		//先删除其关联的学生 再重新新增
		for(int i = 0;i<list.size();i++){
			JSONObject tempObj = (JSONObject) list.get(i);
			Long studentId = tempObj.getLong("studentId");
			Integer guardian = tempObj.getInteger("guardian");
			Integer relationType = tempObj.getInteger("relationType");
			bool = rstuModel.set("id",IDKeyUtil.getIDKey()).set("studentId", studentId).set("parentId", id).
			set("guardian", guardian).set("relationType", relationType).set("schId", schId).save();
		}
		return bool;
	}
	
	/**
	 * 更改家长关联的学生
	 * @param parentId
	 * @param stuList
	 * @return
	 */
	public static Boolean saveRelaStu(Long schId,Long parentId,List<JSONObject> stuList){
		Boolean bool = updateRelationById(schId,new RstuAndParentModel(), parentId, stuList);
		return bool;
	}
	
	/**
	 * 根据ID查询家长信息
	 * @param id
	 * @return
	 */
	public static StuParentModel findStuParentById(Long id){
		return StuParentModel.dao.findById(id);
	}
	
	/**
	 * 根据家长ID查询学生信息
	 * @param id
	 * @return
	 */
	public static List<StudentModel> findStuByParentId(Long id,Long schId,Long gradeId,Long classId){
		StringBuilder sql = new StringBuilder(" select rsi.guardian,rsi.relationType,rsi.studentId,rsi.parentId,rsi.schId,bs.*,bc.name as className ");
		sql.append(" from r_student_info rsi right join base_student bs on rsi.studentId = bs.id  ");
		sql.append(" left join base_class bc on bs.classId = bc.id where 1=1 ");
		List<Object> params = new ArrayList<>();
	 	if(id != 0 ){
        	sql.append(" and rsi.parentId = ?"); //验证手机号码,判断不为该家长的手机号码
            params.add(id);
        }
        if(schId != 0 ){
        	sql.append(" and rsi.schId = ?"); 
            params.add(schId);
        }
        if( gradeId != 0)
        {	
            sql.append(" and bs.gradeId  = ?");
            params.add(gradeId);
        }
        if( classId != 0)
        {
            sql.append(" and bs.classId  = ?");
            params.add(classId);
        }
		CommonUtil.appendDataAuthSql(DataMap,sql,params);
		return StudentModel.dao.find(sql.toString(),params.toArray() );
	}
	
	/**
	 * 根据年级ID查询班级
	 * @param gradeId
	 * @return
	 */
	public static List<ClassModel> findClassByGrade(Long gradeId){
		StringBuffer sql = new StringBuffer(" select id,name from base_class where gradeId = ? ");
		return ClassModel.dao.find(sql.toString(),gradeId);
	}
	
	/**
	 * 删除家长信息
	 * @param idStr
	 * @return
	 */
	public static Boolean deleStuParent(final String idStr){
			StringBuffer tempinsertSql = new StringBuffer(" Insert into his_parent select * from base_parent where  FIND_IN_SET(ID,?)   ");
			StringBuffer deleParentSql = new StringBuffer(" delete from base_parent where  FIND_IN_SET(ID,?)  ");
			Db.update(tempinsertSql.toString(),idStr);
			int count = Db.update(deleParentSql.toString(),idStr);
			return count > 0;
	}
	
	/**
	 * 删除家长信息
	 * @param idArr
	 * @return
	 */
	public static Boolean deleStuParentInfo( final Object [] idArr,final Long schId){
		Boolean count = Db.tx(new IAtom() {
			public boolean run() throws SQLException {
				StringBuffer tempRemoveSql = new StringBuffer(" Insert into his_parent select * from base_parent where ID  ");
				StringBuffer removeRelaSql = new StringBuffer(" Insert into his_student_info select * from r_student_info where parentId ");
				StringBuffer deleParentSql = new StringBuffer(" delete from base_parent where ID ");
				StringBuffer deleRelaStuSql = new StringBuffer(" delete from r_student_info where parentId ");
				StringBuffer inSql = new StringBuffer(" in (");
				for(int i=0;i<idArr.length;i++){
					if(i == idArr.length-1){
						inSql.append("?");
					}else{
						inSql.append("?,");
					}
				}
				inSql.append(") ");
				tempRemoveSql.append(inSql);
				removeRelaSql.append(inSql);
				deleParentSql.append(inSql);
				deleRelaStuSql.append(inSql);
				logger.info("备份家长与学生关系执行SQL："+removeRelaSql.toString());
				Db.update(tempRemoveSql.toString(), idArr);
				Db.update(removeRelaSql.toString(), idArr);
				Db.update(deleRelaStuSql.toString(), idArr);
				int count = Db.update(deleParentSql.toString(),idArr);
				return count > 0;
			}
		});
		return count;
	}
	
	/**
	 * 根据学生ID,家长ID删除学生与家长的关联信息
	 * @param id
	 * @return
	 */
	public static Boolean deleStudent(Long schId,String parentId,Long studentId){
		StringBuffer removeSql = new StringBuffer(" Insert into his_student_info select * from r_student_info where  studentId = ?  and schId = ? and FIND_IN_SET(parentId,?)");
		StringBuffer deleSql = new StringBuffer(" delete from r_student_info where  studentId = ? and schId = ?  and FIND_IN_SET(parentId,?)");
		int removeCount = Db.update(removeSql.toString(),studentId,schId,parentId);
		int deleCount = Db.update(deleSql.toString(),studentId,schId,parentId);
		return removeCount > 0 && deleCount > 0;
	}
	
	/**
	 * 根据学生ID学生信息
	 * @param id
	 * @return
	 */
	public static List<StudentModel> findStudentByStuId(Long id){
		StringBuffer sql = new StringBuffer(" select rs.guardian, rs.relationType, rs.studentId, rs.parentId, s.* FROM r_student_info rs   ");
		sql.append(" LEFT JOIN base_student s on s.id = rs.studentId ");
		sql.append(" WHERE  rs.studentId = ? ");
		return StudentModel.dao.find(sql.toString(),id);
	}
	
	/**
	 * 根据家长姓名和学校ID获取家长信息
	 * @param Name  家长姓名
	 * @param schId  学校ID
	 * @param Id 
	 * @return
	 */
	 public StuParentModel findStuParent(String Name,String telphone,long id) {
	        StringBuilder sql = new StringBuilder("select p.* from base_parent p where 1=1 ");
	        List<Object> params = new ArrayList<>();
	        if(id != 0 ){
	        	sql.append(" and p.id  != ?"); //验证手机号码,判断不为该家长的手机号码
	            params.add(id);
	        }
	        sql.append(" and p.name = ?");
	        params.add(Name);
	        sql.append(" and p.telphone = ?");
	        params.add(telphone);
	        return StuParentModel.dao.findFirst(sql.toString(),params.toArray());
	}
	 
	 /**
	  * 根据条件查询所有学生家长信息
	  * @param param
	  * @return
	  */
	 public List<StuParentModel> findstuParentList(Map<String,Object> param){
			StringBuffer sql  = new StringBuffer(" SELECT rsi.guardian, rsi.relationType, rsi.studentId, rsi.parentId, bp.*, GROUP_CONCAT(bs. NAME) AS stuName, GROUP_CONCAT(bs.sex) AS stuSex,org.orgName, GROUP_CONCAT(g. NAME) AS gradeName, GROUP_CONCAT(c. NAME) AS className    ");
			StringBuilder select = new StringBuilder(" FROM base_parent bp LEFT JOIN r_student_info rsi ON bp.id = rsi.parentId LEFT JOIN base_student bs ON rsi.studentId = bs.id LEFT JOIN base_class c ON bs.classId = c.id LEFT JOIN base_grade g ON g.id = bs.gradeId left join base_organization org on org.id=rsi.schId  WHERE 1 = 1  ");
	        List<Object> list = new ArrayList<Object>();
	        CommonUtil.setDefaultPara(param, select, list, "bp", DefineMap,null,DataMap);
	        select.append("GROUP BY bp.id");
			logger.info("导出学生家长信息执行SQL："+sql.toString()+select.toString());
			List<StuParentModel> dataSource = StuParentModel.dao.find(sql.toString()+select.toString(),list.toArray());
			return dataSource;
	}
	 
	/**
	 * 根据学校ID，家长姓名和手机号码获取家长信息
	 * @param Name
	 * @param telphone
	 * @param schId
	 * @return
	 */
	public StuParentModel findRstuParent(String Name,String telphone,long schId) {
	        StringBuilder sql = new StringBuilder("select p.* from base_parent p left join r_student_info r on p.id=r.parentId  where 1=1 ");
	        List<Object> params = new ArrayList<>();
	        if(schId != 0 ){
	        	sql.append(" and r.schId  = ?"); 
	            params.add(schId);
	        }
	        sql.append(" and p.name = ?");
	        params.add(Name);
	        sql.append(" and p.telphone = ?");
	        params.add(telphone);
	        Map<String, String> atuhDataMap= new HashMap<String,String>();
	        atuhDataMap.put(CommonStaticData.OrgTreeNodeType.SCHFLAG,"r.schId");
	        CommonUtil.appendDataAuthSql(atuhDataMap,sql,params);
	        return StuParentModel.dao.findFirst(sql.toString(),params.toArray());
	}
	
	/**
	 * 根据家长ID获取账号ID
	 * @param idStr
	 * @return
	 */
	public String findAccountIds(String idStr){
	    StringBuilder sql = new StringBuilder("select t.* from base_parent t where 1=1 and FIND_IN_SET(t.ID,?) ");
		List<Record> reList = Db.find(sql.toString(),idStr);
		String idStrs = DaYangCommonUtil.column2Str(reList, "accountId");
		return idStrs;
	}
	
	
}
