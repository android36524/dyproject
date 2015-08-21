package com.dayang.system.model;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;

import com.dayang.commons.enums.EmpeType;
import com.dayang.commons.enums.EmpeTypeSch;
import com.dayang.commons.pojo.EdbEmplImportPojo;
import com.dayang.commons.pojo.ExcelImportErrorPojo;
import com.dayang.commons.pojo.QueryDefineParaPojo;
import com.dayang.commons.thread.ExecuteSqlThread;
import com.dayang.commons.thread.ResThreadPoolUtil;
import com.dayang.commons.util.CommonStaticData;
import com.dayang.commons.util.CommonUtil;
import com.dayang.commons.util.DaYangCommonUtil;
import com.dayang.commons.util.ExcelUtil;
import com.dayang.commons.util.IDKeyUtil;
import com.dayang.commons.util.StaticData;
import com.github.stuxuhai.jpinyin.PinyinHelper;
import com.jfinal.kit.PathKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

/**
 * 类描述：教育局/学校员工管理模块
 * <pre>
 * -------------History------------------
 *   DATE                 AUTHOR         VERSION        DESCRIPTION
 *  2015年5月18日             何意            V01.00.001		      新增内容
 * </pre>
 *
 * @author <a href="mailto:heyi@dayang.com">何意 </a>
 */
public class EmpeModel extends Model<EmpeModel>  {
    private static final long serialVersionUID = 1L;
	public static final Map<String, QueryDefineParaPojo> DefineMap= new HashMap<String,QueryDefineParaPojo>(){
		/**
		 *
		 */
		private static final long serialVersionUID = 1L;

		{
			put("ids",new QueryDefineParaPojo("ids","id","in"));
			put("name",new QueryDefineParaPojo("name","name,nameSpell","like"));
			put("mobile",new QueryDefineParaPojo("mobile","mobile","like"));
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
			add("deptId");
			add("orgType");
			add("empTypeT");
			add("empTypeE");
		}
	};
	
	public Map<String,String> getDiyDataMap(Map<String,Object> param){
		Map<String,String> dataTypeList= new HashMap<String,String>();
		dataTypeList.put(CommonStaticData.OrgTreeNodeType.DEPTFLAG, "t.deptId");
		dataTypeList.put(param.get("orgType")==null?"0":param.get("orgType").toString(),"t.orgId");
		return dataTypeList;
	}


    public static final EmpeModel dao = new EmpeModel();

    private static Logger logger = Logger.getLogger(EmpeModel.class);
    
    public OrgModel getSchool(){
    	return OrgModel.dao.findById(get("orgId"));
    }
    
    public DeptModel getDept(){
    	return DeptModel.dao.findById(get("deptId"));
    }


	/**
	* 查询部门员工列表信息
	*/
	public Page<EmpeModel> findEmpeModelPage(int pageNumber,int pageSize,Map<String,Object> param){
		String sql = "select t.*,dept.deptName,org.orgName,a.name as creatorName,ac.account as account_id ";
		StringBuilder sb = new StringBuilder(" from base_employee t  ");
		sb.append(" left join base_organization org on t.orgId = org.id");
		sb.append(" left join base_department dept on t.deptId = dept.id  ");
		sb.append(" LEFT JOIN sys_account a on t.creator=a.id");
		sb.append(" LEFT JOIN sys_account ac on t.accountId=ac.id");
		sb.append(" where 1=1 ");

		List<Object> list = new ArrayList<Object>();
		CommonUtil.setDefaultPara(param, sb, list, "t", DefineMap, IngoreList,getDiyDataMap(param));
		long deptId = param.get("deptId")==null?0l:Long.parseLong(param.get("deptId").toString());
		
		if(param.get("empTypeT") != null){
			String empTypeT = param.get("empTypeT").toString();
			String empTypeE = param.get("empTypeE").toString();
				sb.append("and (t.empType = ? or t.empType = ? )");
				list.add(empTypeT);
				list.add(empTypeE);
		}
		if(deptId>0){
			sb.append("and (dept.id = ? or dept.pid = ? )");
			list.add(deptId);
			list.add(deptId);
		}
		
		String orgFlag = (String)param.get("orgType");
		if(CommonStaticData.OrgTreeNodeType.COMPANYFLAG.equals(orgFlag)){
			sb.append(" and org.orgFlag='"+orgFlag+"' ");
		}
		sb.append(" order by t.createTime desc ");
		return EmpeModel.dao.paginate(pageNumber, pageSize,sql,sb.toString(),list.toArray());
	}
	
	/**
	 * 根据条件查询公司员工
	 */
	public Page<EmpeModel> queryByConditions(int pageNumber, int pageSize, String nameOrPy,String phoneNo,String accountId,String company_id,Map<String,Object> param){
		String sql = "select t.*,dept.deptName,org.orgName ,ac.account as account_id ";
		StringBuilder sb = new StringBuilder(" from base_employee t  ");
		sb.append(" left join base_organization org on t.orgId = org.id");
		sb.append(" left join base_department dept on t.deptId = dept.id  ");
		sb.append(" LEFT JOIN sys_account a on t.creator=a.id");
		sb.append(" LEFT JOIN sys_account ac on t.accountId=ac.id");
		sb.append(" where 1=1 ");

		List<Object> list = new ArrayList<Object>();
		CommonUtil.setDefaultPara(param, sb, list, "t", DefineMap, IngoreList,getDiyDataMap(param));
		

		if(phoneNo!=""){
			sb.append(" and t.mobile like'%"+phoneNo+"%'");
		}
		
		if(nameOrPy!=""){
			sb.append(" and( t.name like'%"+nameOrPy+"%' or t.nameSpell like'%"+nameOrPy+"%') ");
		}
		
		if(accountId!=""){
			sb.append(" and ac.account="+accountId+"");
		}
		if(company_id!=""){
			sb.append(" and t.orgId="+company_id);
		}
		String orgFlag = (String)param.get("orgType");
		if("2".equals(orgFlag)){
			sb.append(" and org.orgFlag='"+orgFlag+"' ");
		}
		sb.append(" order by t.createTime desc ");
		return EmpeModel.dao.paginate(pageNumber, pageSize,sql,sb.toString(),list.toArray());
	}
	

	/**
	 * 根据员工ID获取教育局员工信息
	 */
	public EmpeModel findEdbByEmpeId(long empeId){
		String sql = " select t.*,edu.entryDate,edu.startWorkDate,edu.dutyInfo,dept.deptName from base_employee t  " +
				"left join base_department dept on t.deptId = dept.id " +
				"left join base_educationinfo edu on t.id = edu.id " +
				"left join base_organization org on t.orgId = org.id where t.id =?  ";
		return EmpeModel.dao.findFirst(sql,empeId);
	}

	/**
	 * 批量删除教育局员工，删除教育局员工之前需要将教育局员工信息记录到员工历史表中
	 * 批量新增教育局员工历史表
	 */
	public boolean insertAllEdb(String[] idArr){
		Map<String,Object> me = EmpeModel.dao.getInsertMapById(Long.parseLong(idArr[0]));
		List<Object> paras = new ArrayList<Object>();
		String sql = CommonUtil.getInsertSqlString(me, "his_employee", paras);
		Object[] arrValue = paras.toArray();
		Object[][] obj = new Object[idArr.length][arrValue.length];
		obj[0]=arrValue;
		for(int i=1;i<idArr.length;i++){			
			me = EmpeModel.dao.getInsertMapById(Long.parseLong(idArr[i]));
			paras = new ArrayList<Object>();
			sql = CommonUtil.getInsertSqlString(me, "his_employee", paras);
			obj[i]=paras.toArray();
		}
		Db.batch(sql,obj, StaticData.Batch_Size);
		return true;
	}
	
	/**
	 * 根据ID获取MODEL的MAP
	 * @param id
	 * @return
	 */
	public Map<String,Object> getInsertMapById(long id){
		EmpeModel em = EmpeModel.dao.findById(id);
		EduInfoModel eim = EduInfoModel.dao.findById(id);
		Map<String,Object> me = em.getAttrs();
		Map<String,Object> mei = eim.getAttrs();
		mei.remove("id");
		me.putAll(mei);
		return me;
	}

	/**
	 * 批量删除教育局员工
	 */
	public boolean delAllEdb(String[] idArr){
		StringBuffer delEmpeSql = new StringBuffer(" delete from base_employee where id = ?");
		Object[][] objEdbEmpe = new Object[idArr.length][1];
		for(int i=0;i<idArr.length;i++){
			Object[] temp = new Object[1];
			temp[0] = idArr[i];
			objEdbEmpe[i] = temp;
		}
		StringBuffer delEduSql = new StringBuffer(" delete from base_educationinfo where id = ?");
		Object[][] objEdbEdu = new Object[idArr.length][1];
		for(int i=0;i<idArr.length;i++){
			Object[] temp = new Object[1];
			temp[0] = idArr[i];
			objEdbEdu[i] = temp;
		}
		Db.batch(delEmpeSql.toString(),objEdbEmpe, StaticData.Batch_Size);
		Db.batch(delEduSql.toString(),objEdbEdu, StaticData.Batch_Size);
		return true;
	}

	/**
	 * 根据员工ID获取学校员工信息
	 */
	public EmpeModel findSchByEmpeId(long empeId){
		String sql = " select t.*,tea.workAge,tea.teacherWorkAge,tea.jobName," +
				"tea.isWork,tea.societyNexus,tea.workExperience,tea.studyExperrience,tea.remark,dept.deptName,tea.health,tea.toSchYear " +
				"from base_employee t  " +
				"left join base_department dept on t.deptId = dept.id " +
				"left join base_teacherinfo tea on t.id = tea.id " +
				"left join base_organization org on t.orgId = org.id where t.id =?  ";
		return EmpeModel.dao.findFirst(sql,empeId);
	}

	/**
	 * 批量新增学校员工历史表
	 */
	public boolean insertAllSch(String[] idArr){
		Map<String,Object> me = EmpeModel.dao.getSchInsertMapById(Long.parseLong(idArr[0]));
		List<Object> paras = new ArrayList<Object>();
		String sql = CommonUtil.getInsertSqlString(me, "his_employee", paras);
		Object[] arrValue = paras.toArray();
		Object[][] obj = new Object[idArr.length][arrValue.length];
		obj[0]=arrValue;
		for(int i=1;i<idArr.length;i++){
			me = EmpeModel.dao.getSchInsertMapById(Long.parseLong(idArr[i]));
			paras = new ArrayList<Object>();
			sql = CommonUtil.getInsertSqlString(me, "his_employee", paras);
			obj[i]=paras.toArray();
		}
		Db.batch(sql,obj, StaticData.Batch_Size);
		return true;
	}

	/**
	 * 根据ID获取MODEL的MAP
	 * @param id
	 * @return
	 */
	public Map<String,Object> getSchInsertMapById(long id){
		EmpeModel em = EmpeModel.dao.findById(id);
		TeachInfoModel tea = TeachInfoModel.dao.findById(id);
		Map<String,Object> me = em.getAttrs();
		Map<String,Object> mei = tea.getAttrs();
		mei.remove("id");
		me.putAll(mei);
		return me;
	}
	/**
	 * 批量删除学校员工
	 */
	public boolean delAllSch(String[] idArr){
		StringBuffer delEmpeSql = new StringBuffer(" delete from base_employee where id = ?");
		Object[][] objEdbEmpe = new Object[idArr.length][1];
		for(int i=0;i<idArr.length;i++){
			Object[] temp = new Object[1];
			temp[0] = idArr[i];
			objEdbEmpe[i] = temp;
		}
		StringBuffer delTeaSql = new StringBuffer(" delete from base_teacherinfo where id = ?");
		Object[][] objTeaEdu = new Object[idArr.length][1];
		for(int i=0;i<idArr.length;i++){
			Object[] temp = new Object[1];
			temp[0] = idArr[i];
			objTeaEdu[i] = temp;
		}
		Db.batch(delEmpeSql.toString(),objEdbEmpe, StaticData.Batch_Size);
		Db.batch(delTeaSql.toString(),objTeaEdu, StaticData.Batch_Size);
		return true;
	}

	/**
	 * 根据教育局/学校员工姓名生成拼音
	 */
	public String pinYinByName(String empeName){
		String nameSpell ="";
		String name = PinyinHelper.getShortPinyin(empeName);
		nameSpell = name.toUpperCase();
		return nameSpell;
	}
	/**
	 * 将验证不通过的数据写入Excel文件
	 * 将验证通过的数据存储到数据库
	 * 插Excel导入日志表
	 */
	public void executionSql(List<ExcelImportErrorPojo> errorMsgList,String errorExcelUrl,String fileUrl,List<EdbEmplImportPojo> validationList){
		if(errorMsgList.size()>0){
			//错误信息写入文件
			String errorPath = CommonUtil.getAppProperties("errorExcelPath");
			errorExcelUrl = PathKit.getWebRootPath() + errorPath+"/"+ CommonUtil.getUUID()+".xls";
			File errorFile = new File(errorExcelUrl);
			ExcelUtil.writeError2Excel(errorFile, errorMsgList);
		}

		//将生成的错误Excel写入到imp_loginfo表中
		LoginfoModel loginfoModel = new LoginfoModel();
		loginfoModel.set("id", IDKeyUtil.getIDKey()).set("fileName",fileUrl)
				.set("downLoadUrl",errorExcelUrl)
				.set("importTime",new Date()).set("succesCount",validationList.size())
				.set("errorCount",errorMsgList.size()).save();

		//执行验证通过后的pojo插入数据库
		ResThreadPoolUtil.threadPool.execute(new ExecuteSqlThread(validationList));
	}

	/**
	 * 拼接教育局员工导出sql
	 */
	public  List<EmpeModel> eduEmplSql(long orgId,long deptId,String name,String mobile){
		List<Object> params = new ArrayList<>();
		StringBuffer sql = new StringBuffer(" select t.*,edu.entryDate,edu.startWorkDate,dept.deptName from base_employee t");
		sql.append(" left join base_organization org on t.orgId = org.id");
		sql.append(" left join base_educationinfo edu on t.id = edu.id");
		sql.append(" left join base_department dept on t.deptId = dept.id where 1=1 and t.orgId = ?  and t.empType = ?");
		params.add(orgId);
		params.add(EmpeType.EDBEMPE.getValueStr());
		if(deptId>0){
			sql.append(" and (dept.id = ? or dept.pid = ? )");
			params.add(deptId);
			params.add(deptId);
		}
		if(!CommonUtil.isEmptyString(name)){
			sql.append(" and t.name like ?");
			params.add("%"+name+"%");
		}if(!CommonUtil.isEmptyString(mobile)){
			sql.append(" and t.mobile = ?");
			params.add(mobile);
		}
		sql.append(" order by t.createTime desc ");
		List<EmpeModel> dataSource = EmpeModel.dao.find(sql.toString(),params.toArray());
		return dataSource;
	}



	/**
	 * 拼接学校员工导出sql
	 */
	public  List<EmpeModel> teaEmplSql(long orgId,long deptId,String name,String mobile,int status){
		List<Object> params = new ArrayList<>();
		StringBuffer sql = new StringBuffer(" select t.*,tea.teacherWorkAge,tea.workAge,tea.health,tea.toSchYear,tea.jobName,dept.deptName from base_employee t");
		sql.append(" left join base_organization org on t.orgId = org.id");
		sql.append(" left join base_teacherinfo tea on t.id = tea.id");
		sql.append(" left join base_department dept on t.deptId = dept.id where 1=1 and t.orgId = ?  and t.empType = ?  ");
		params.add(orgId);
		params.add(EmpeTypeSch.SCHEMP.getValueStr());
		if(deptId>0){
			sql.append(" and (dept.id = ? or dept.pid = ? )");
			params.add(deptId);
			params.add(deptId);
		}
		if(!CommonUtil.isEmptyString(name)){
			sql.append(" and t.name like ?");
			params.add("%"+name+"%");
		}if(!CommonUtil.isEmptyString(mobile)){
			sql.append(" and t.mobile = ?");
			params.add(mobile);
		}
		if(status > 0){
			sql.append(" and t.status = ? ");
			params.add(status);
		}
		sql.append(" order by t.createTime desc ");
		List<EmpeModel> dataSource = EmpeModel.dao.find(sql.toString(),params.toArray());
		return dataSource;
	}
	
	
	/**
     * 验证手机号码是否唯一
     * @param mobile 手机号码
     * @param orgId 组织机构Id
     */
    public EmpeModel validateEmpeMobile(String mobile,long orgId,long empeId) {
        StringBuilder sql = new StringBuilder("select t.* from base_employee t where 1=1 ");
        List<Object> params = new ArrayList<>();
        if(orgId != 0)
        {
            sql.append(" and t.orgId = ?");
            params.add(orgId);
        }
        if(!CommonUtil.isEmptyString(mobile))
        {
            sql.append(" and t.mobile = ?");
            params.add(mobile);
        }
        if (empeId != 0){
            sql.append(" and t.id != ?");
            params.add(empeId);
        }
        return EmpeModel.dao.findFirst(sql.toString(),params.toArray());
    }
    
    /**
	 * 根据公司员工ID查询出员工信息
	 */
	public EmpeModel findCompanyByEmpeId(long empeId){
		String sql = " select t.*,dept.deptName as deptName,org.orgName as orgName from base_employee t  left join base_department dept on t.deptId = dept.id  left join base_organization org on t.orgId = org.id where t.id =? ";
		return EmpeModel.dao.findFirst(sql,empeId);
	}
	
	/**
	 * 生成员工编号
	 */
	public EmpeModel valdateEmpNo(String empNo,long orgId,long empeId,String empType){
		StringBuilder sql = new StringBuilder("select t.* from base_employee t where 1=1 ");
        List<Object> params = new ArrayList<>();
        if(orgId > 0)
        {
            sql.append(" and t.orgId = ?");
            params.add(orgId);
        }
        if(!CommonUtil.isEmptyString(empNo))
        {
            sql.append(" and t.empNo = ?");
            params.add(empNo);
        }
        if (empeId > 0){
            sql.append(" and t.id != ?");
            params.add(empeId);
        }
        if(empType.equals(EmpeTypeSch.SCHEMP.getValueStr())){
        	sql.append(" and (t.empType = ? or t.empType = ?)" );
            params.add(EmpeTypeSch.SCHEMP.getValueStr());	
            params.add(EmpeTypeSch.TEACHEREMP.getValueStr());	
        }
        else if(empType.equals(EmpeType.COMPANYEMP.getValueStr())){
        	sql.append(" and (t.empType = ? or t.empType = ?)" );
            params.add(EmpeType.COMPANYEMP.getValueStr());	
            params.add(EmpeType.AGENTEMP.getValueStr());
        }
        else{
        	sql.append(" and t.empType =  ?" );
        	params.add(empType);
        }
        return EmpeModel.dao.findFirst(sql.toString(),params.toArray());	
	}
	/**
	 * 生成教育局员工或者公司员工
	 */
	public String genEdbCompNo(String empFlag){
		String empeNo="";
        if("3".equals(empFlag)){
        	List<EmpeModel> empeList =
	                EmpeModel.dao.find("select * from base_employee  t where t.empType='"+EmpeType.COMPANYEMP.getValueStr()+"' or t.empType='"+EmpeType.AGENTEMP.getValueStr()+"'");
	        if(CollectionUtils.isNotEmpty(empeList)){
	            Integer max = 0001;
	            for (EmpeModel empe : empeList) {
	                String dbempeNo = empe.getStr("empNo");
	                String curIndex = dbempeNo.substring(2,dbempeNo.length());
	                if(Integer.valueOf(curIndex).compareTo(max) > 0){
	                    max = Integer.valueOf(curIndex);
	                }
	            }
	            String maxStr = String.valueOf(max + 1);
	            if(maxStr.length() == 1){
	                maxStr = "000" + maxStr;
	            }else if(maxStr.length() == 2){
	            	maxStr = "00" + maxStr;
	            }else if(maxStr.length() == 3){
	            	maxStr = "0" + maxStr;
	            }
	            empeNo += "DY"+maxStr;
	
	        }else{
	            empeNo += "DY0001";
	        }
	        return empeNo;
        }else {
	        List<EmpeModel> empeList =
	                EmpeModel.dao.find("select * from base_employee  t where t.empType='"+empFlag+"'");
	        if(CollectionUtils.isNotEmpty(empeList)){
	            Integer max = 1001;
	            for (EmpeModel empe : empeList) {
	                String dbempeNo = empe.getStr("empNo");
	                String curIndex = dbempeNo;
	                if(Integer.valueOf(curIndex).compareTo(max) > 0){
	                    max = Integer.valueOf(curIndex);
	                }
	            }
	            String maxStr = String.valueOf(max + 1);
	            if(maxStr.length() == 1){
	                maxStr = "0" + maxStr;
	            }
	            empeNo += maxStr;
	
	        }else{
	            empeNo += "1001";
	        }
	        return empeNo;
        }
	}
	
		/**
		 * 根据员工ID获取账号ID
		 * @param idStr
		 * @return
		 */
		public String findAccountIds(String idStr){
		    StringBuilder sql = new StringBuilder("select t.* from base_employee t where 1=1 and FIND_IN_SET(t.id,?) ");
			List<Record> reList = Db.find(sql.toString(),idStr);
			String idStrs = DaYangCommonUtil.column2Str(reList, "accountId");
			return idStrs;
		}
}
