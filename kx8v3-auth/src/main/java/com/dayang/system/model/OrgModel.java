package com.dayang.system.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;

import com.dayang.commons.enums.EnumAll;
import com.dayang.commons.enums.OrgFlag;
import com.dayang.commons.enums.OrgLevelEnum;
import com.dayang.commons.enums.Status;
import com.dayang.commons.util.CommonStaticData;
import com.dayang.commons.util.CommonUtil;
import com.dayang.commons.util.IDKeyUtil;
import com.dayang.commons.util.StaticData;
import com.github.stuxuhai.jpinyin.PinyinHelper;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

/**
 * 类描述：教育局基础信息管理Model
 * <pre>
 * -------------History------------------
 *   DATE                 AUTHOR         VERSION        DESCRIPTION
 *  2015年05月18日            刘建宇      V01.00.001		   新增内容
 * </pre>
 *
 * @author <a href="mailto:liujy@dayanginfo.com">刘建宇</a>
 */
public class OrgModel extends Model<OrgModel>{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final OrgModel dao = new OrgModel();

	
	public Map<String,String> getDiyDataMap(Map<String,Object> param){
		Map<String,String> dataTypeList= new HashMap<String,String>();
		dataTypeList.put(CommonStaticData.OrgTreeNodeType.ORGFLAG, "a.id");
		//dataTypeList.put(param.get("orgType")==null?"0":param.get("orgType").toString(),"t.orgId");
		return dataTypeList;
	}
    /**
     * 分页查询教育局基础信息 
     * @param pageNumber 每页大小
     * @param pageSize 每页显示条数
     * @param orgId
     *@param paramsMap  @return EdbModel
     */
    public Page<OrgModel> findByPage(int pageNumber, int pageSize, long orgId, Map<String, Object> paramsMap) {
        List<Object> params = new ArrayList<>();
        params.add(OrgFlag.EDBFLAG.getValueStr());
        String selectSql = "select a.*,b.orgName as parentOrg,acc.name as creatorName,ac.account as account";
        StringBuilder sql = new StringBuilder("from base_organization a ")
                .append(" LEFT JOIN base_organization b ON a.orgId = b.id")
                .append(" LEFT JOIN sys_account acc on a.creator=acc.id")
                .append(" LEFT JOIN sys_account ac on a.accountId=ac.id")
                .append(" where a.orgFlag = ? ");
        CommonUtil.setDefaultPara(paramsMap,sql,params,"a", null, null, getDiyDataMap(paramsMap));
        sql.append(" and (a.orgId = ? or a.id = ?)");
        params.add(orgId);
        params.add(orgId);
        sql.append(" order by a.createTime desc ");
        return OrgModel.dao.paginate(pageNumber,pageSize,selectSql,sql.toString(),params.toArray());
    }

    /**
     * 根据教育局名称以及行政区划设置教育局编码
     */
    public void setEdbCode() {
		String orgLevel = this.getStr("level");
		String orgCode;
		String level;
		String areaCode = this.getStr("areaCode");
		String cityCode = this.getStr("cityCode");
		if (OrgLevelEnum.AREA.getValueStr().equals(orgLevel)){//区下级教育局
			level = OrgLevelEnum.CHILDAREA.getValueStr();
			orgCode = areaCode + System.currentTimeMillis();
		}else if (OrgLevelEnum.CITY.getValueStr().equals(orgLevel)){//市下级教育局
			level = OrgLevelEnum.AREA.getValueStr();
			orgCode = areaCode;
		}else if (OrgLevelEnum.PROVINCE.getValueStr().equals(orgLevel)){//省下级教育局
			level = OrgLevelEnum.CITY.getValueStr();
			orgCode = cityCode;
		}else {//省级教育局
			level = OrgLevelEnum.PROVINCE.getValueStr();
			orgCode = this.getStr("provinceCode");
		}
		this.set("level", level);
        this.set("orgCode", orgCode);
    }
    
    /**
     * 根据教育局省市区来确定层级
     */
    public void setEdbLevel() {
        String provinceCode = this.getStr("provinceCode");
        String areaCode = this.getStr("areaCode");
        String cityCode = this.getStr("cityCode");
        String temp = null;
        if (CommonUtil.isNotEmptyString(areaCode)){
        	temp = OrgLevelEnum.AREA.getValueStr();
        }else{
        	if (CommonUtil.isNotEmptyString(cityCode)) {
            	temp = OrgLevelEnum.CITY.getValueStr();
            }else {
            	if(CommonUtil.isNotEmptyString(provinceCode)){
                	temp = OrgLevelEnum.PROVINCE.getValueStr();
                }
            }
        }
        this.set("level", temp);
    }

    /**
     * 教育教育局名称是否唯一 
     * @param edbName 教育局名称
     * @param edbId 教育局id
     * @return EdbModel
     */
    public OrgModel validateEdbName(String edbName, long edbId) {
        StringBuilder sql = new StringBuilder("select * from base_organization a where a.orgName = ?");
        List<Object> params = new ArrayList<>();
        params.add(edbName);
        if (edbId != 0){
            sql.append(" and a.id != ?");
            params.add(edbId);
        }
        return OrgModel.dao.findFirst(sql.toString(),params.toArray());
    }


    /**
     * 根据条件行政区划查询教育局 
     * @param pageNumber 页码
     * @param pageSize 每页大小
     * @param flag 省市区标记
     * @param code 行政编码
     * @return page
     */
    public Page<OrgModel> findByPage(int pageNumber, int pageSize, String flag, String code,String itSelf) {
        String selectSql = "select * ";
        StringBuilder sql = new StringBuilder("from base_organization t where 1=1 and t.id<>? and t.orgFlag=? and t.id not in(select b.id from base_organization b where b.orgId=?)");
        List<Object> params = new ArrayList<>();
        params.add(itSelf);
        params.add(OrgFlag.EDBFLAG.getValueStr());
        params.add(itSelf);
        if("province".equals(flag)){//查中央教育局
            sql.append(" and t.orgId is null");
        }else if ("city".equals(flag)){//查省级教育局
            sql.append(" and t.provinceCode = ? and t.cityCode is null and t.areaCode is null");
            params.add(code);
        }else {//查市级教育局
            sql.append(" and t.cityCode = ? and t.areaCode is null");
            params.add(code);
        }
        return OrgModel.dao.paginate(pageNumber, pageSize, selectSql, sql.toString(), params.toArray());
    }

    /**
     * 根据id查询教育局
     * @param edbId 教育局id
     * @return EdbModel
     */
    public OrgModel findByEdbId(String edbId) {
        String sql = "select a.*,b.orgName as parentOrg " +
                " from base_organization a" +
                " LEFT JOIN base_organization b ON a.orgId = b.id" +
                " where a.id = ?";
        return OrgModel.dao.findFirst(sql, edbId);
    }

	/**
	 * 查询机构树
	 * @param provinceId 省id
	 * @param cityId 市id
	 * @param areaId 区id
	 * @param orgName 机构名称
	 * @param treeFlag 机构树标识
	 * @param isDataAuth 是否需要数据权限
	 * @return
	 */
    public List<OrgModel> findOrgTree(String provinceId, String cityId, String areaId, String orgName,
									  String treeFlag,boolean isDataAuth) {
        List<Object> params = new ArrayList<>();
		StringBuilder selectSql = new StringBuilder("select o.id,o.orgId as pId,o.orgName as name,o.orgFlag as flag,o.level as orgLevel")
				.append(" from base_organization o where o.status = ? ");
		params.add(Status.ENABLE.getValueStr());
		if (CommonUtil.isNotEmptyString(areaId)){
			selectSql.append(" and o.areaCode = ?");
			params.add(areaId);
		}else if(CommonUtil.isNotEmptyString(cityId)){
			selectSql.append(" and o.cityCode = ?");
			params.add(cityId);
		}else if(CommonUtil.isNotEmptyString(provinceId)){
			selectSql.append(" and o.provinceCode = ?");
			params.add(provinceId);
		}
		if (CommonUtil.isNotEmptyString(orgName)){
			selectSql.append(" and (o.orgName like ?");
			selectSql.append(" or o.orgCode like ?)");
			params.add("%" + orgName + "%");
			params.add("%" + orgName + "%");
		}
		if (isDataAuth){
			CommonUtil.appendDataAuthSql(treeFlag, "o.id", selectSql, params);
		}
		return OrgModel.dao.find(selectSql.toString(), params.toArray());
    }
    
    /**
	 * 分页查询学校信息 (orgFlag = 1)
	 * @param pageNumber
	 * @param pageSize
	 * @param schoolName
	 * @param status
	 * @param orgId
	 * @return
	 */
	public Page<OrgModel> findSchoolByPage(int pageNumber, int pageSize,
			String schoolName, String status, long orgId) {
		
		StringBuilder sql = new StringBuilder();
		sql.append(" from base_organization a left join base_schoolinfo b on a.id = b.id  ");
		sql.append(" LEFT JOIN base_organization c on a.orgId = c.id ");
		sql.append(" left join sys_account d on a.creator = d.id ");
		sql.append(" LEFT JOIN sys_account ac on a.accountId=ac.id");
		sql.append(" where 1=1 ");
		
		List<Object> parameters = new ArrayList<Object>();
		if(!CommonUtil.isEmpty(schoolName)){
			sql.append(" and a.orgName like ? ");
			parameters.add("%" +schoolName.trim()+ "%");
		}
		if(!CommonUtil.isEmpty(status)){
			sql.append(" and a.status = ? ");
			parameters.add(status);
		}
		
		sql.append(" and a.orgFlag= ? ");
		parameters.add(OrgFlag.SCHOOLFLAG.getValueStr());
		
		sql.append(" and c.orgFlag= ? ");
		parameters.add(OrgFlag.EDBFLAG.getValueStr());
		
		sql.append(" and c.id = ? ");
		parameters.add(orgId);
//		String selSql = "select a.*,b.*,c.orgName edbName, ac.account ";
		String selSql = "select a.*,b.*,c.orgName edbName, ac.account,d.name as createName ";
		
		Map<String,String> dataAuthMap = new HashMap<>();
		dataAuthMap.put(CommonStaticData.OrgTreeNodeType.SCHFLAG,"a.id");
		CommonUtil.appendDataAuthSql(dataAuthMap,sql,parameters);
		
		sql.append(" order by a.createTime desc ");
		
		return OrgModel.dao.paginate(pageNumber, pageSize, selSql,
				sql.toString(), parameters.toArray());
	}
	
	/**
     * 检验学校名称是否唯一 
     * @param schoolName 学校名称
     * @param schoolId 学校id
     * @return EdbModel
     */
    public OrgModel validateSchoolName(String schoolName, long schoolId) {
        StringBuilder sql = new StringBuilder("select * from base_organization a where a.orgName = ?");
        List<Object> params = new ArrayList<>();
        params.add(schoolName);
        if (schoolId != 0){
            sql.append(" and a.id != ?");
            params.add(schoolId);
        }
        sql.append(" and orgFlag = ? ");
        params.add(OrgFlag.SCHOOLFLAG.getValueStr());
        return OrgModel.dao.findFirst(sql.toString(),params.toArray());
    }
    
    /**
	 * 初始化年级科目关系信息
	 * @param schId
	 * @param subRecodMap 科目信息Map
	 */
	public void initRGradeSub(long schId, Map<String, List<Long>> subRecodMap) {
		
		StringBuffer gradeBySchSql = new StringBuffer();
		gradeBySchSql.append(" select * from base_grade where 1=1 ");
		gradeBySchSql.append(" and schId = "+schId+"");
		List<GradeModel> gradeBySchList = GradeModel.dao.find(gradeBySchSql.toString());
		
		if(!subRecodMap.isEmpty() 
				&& CollectionUtils.isNotEmpty(gradeBySchList)){
			
			StringBuffer insertGradeSubSql = new StringBuffer();
			insertGradeSubSql.append("insert into r_gradesubject(id,gradeId,subjectId) ");
			insertGradeSubSql.append(" values(?,?,?) ");
			
			List<Object> gradeSubList = new ArrayList<Object>();
			for (GradeModel gradeModel : gradeBySchList) {
				String gradeCode = gradeModel.get("code");
				
				if(subRecodMap.containsKey(gradeCode)){
					List<Long> subjectIds = subRecodMap.get(gradeCode);
					
					for (Long subjectId : subjectIds) {
						Object[] gradeSubTemp = new Object[3];
						gradeSubTemp[0] = IDKeyUtil.getIDKey();
						gradeSubTemp[1] = gradeModel.get("id");
						gradeSubTemp[2] = subjectId;
						gradeSubList.add(gradeSubTemp);
					}
				}
			}
			
			Object[][] gardeSubParas = new Object[gradeSubList.size()][2];
			for (int i=0; i<gradeSubList.size(); i++) {
				Object[] obj =  (Object[])gradeSubList.get(i);
				gardeSubParas[i] = obj;
			}
			Db.batch(insertGradeSubSql.toString(), gardeSubParas, StaticData.Batch_Size);
		}
	}
	
	/**
	 * 初始化年级信息
	 * @param schId
	 * @param codeArray
	 * @param userId
	 */
	public void initGrade(long schId, String codeArray, long userId) {
		
		StringBuffer gradeSql = new StringBuffer();
		gradeSql.append(" select * from base_grade ");
		gradeSql.append(" where 1=1 ");
		gradeSql.append(" and flag = '"+EnumAll.GradeFlag.COMMONFLAG.getValueStr()+"' ");
		gradeSql.append(" and code in ("+codeArray+")");
		List<GradeModel> gradeModelList = GradeModel.dao.find(gradeSql.toString());
		
		if(CollectionUtils.isNotEmpty(gradeModelList)){
			
			StringBuffer insertGradeSql = new StringBuffer();
			insertGradeSql.append("insert into base_grade(id,name,code,stageId,gradeId,schId,flag,remark,seq,creator,createTime) ");
			insertGradeSql.append(" values(?,?,?,?,?,?,?,?,?,?,?) ");
			
			Object[][] gardeParas = new Object[gradeModelList.size()][10];
			for (GradeModel gradeModel : gradeModelList) {
				Object[] gradeTemp = new Object[11];
				long gradeId = IDKeyUtil.getIDKey();
				gradeTemp[0] = gradeId;
				gradeTemp[1] = gradeModel.get("name");
				gradeTemp[2] = gradeModel.get("code");
				gradeTemp[3] = gradeModel.get("stageId");
				gradeTemp[4] = gradeModel.get("id");
				gradeTemp[5] = schId;
				gradeTemp[6] = EnumAll.GradeFlag.SCHOOLFLAG.getValueStr();
				gradeTemp[7] = gradeModel.get("remark");
				gradeTemp[8] = gradeModel.get("seq");
				gradeTemp[9] = userId;
				gradeTemp[10] = new Date();
				int gradeIndex = gradeModelList.indexOf(gradeModel);
				gardeParas[gradeIndex] = gradeTemp;
			}
			Db.batch(insertGradeSql.toString(), gardeParas, StaticData.Batch_Size);
		}
	}
	
	/**
	 * 初始化科目信息
	 * @param schId
	 * @param codeArray
	 * @param subRecodMap
	 * @param userId
	 */
	public void initSubject(long schId, String codeArray,
			Map<String, List<Long>> subRecodMap, long userId) {
		
		StringBuffer subjectSql = new StringBuffer();
		subjectSql.append("select a.*,c.code gradecode from r_gradesubject b ");
		subjectSql.append(" left join base_subject a on a.id = b.subjectId ");
		subjectSql.append(" left join base_grade c on b.gradeId = c.id ");
		subjectSql.append(" where 1=1 ");
		subjectSql.append(" and c.flag = '"+EnumAll.SubjectFlag.COMMONFLAG.getValueStr()+"' ");
		subjectSql.append(" and c.code in ("+codeArray+") ");
		
		Map<Long, Long> tempIdMap = new HashMap<Long, Long>();
		
		List<SubjectModel> subjectList = SubjectModel.dao.find(subjectSql.toString());
		if(CollectionUtils.isNotEmpty(subjectList)){
			
			StringBuffer insertSubjectSql = new StringBuffer();
			insertSubjectSql.append("insert into base_subject(id,name,code,schId,subjectId,seq,flag,creator,createTime) ");
			insertSubjectSql.append(" values(?,?,?,?,?,?,?,?,?) ");
			
			List<Object> list = new ArrayList<Object>();
			
			for (SubjectModel subjectModel : subjectList) {
				Object[] subjectTemp = new Object[9];
				
				long oldId = subjectModel.get("id");
				if(!tempIdMap.containsKey(oldId)){
					long newId = IDKeyUtil.getIDKey();
					subjectTemp[0] = newId;
					tempIdMap.put(oldId, newId);
					
					String gradeCode = subjectModel.get("gradecode");
					if(subRecodMap.containsKey(gradeCode)){
						subRecodMap.get(gradeCode).add(newId);
					}else{
						List<Long> IdList = new ArrayList<Long>();
						IdList.add(newId);
						subRecodMap.put(gradeCode, IdList);
					}
				}else{
					long newId = tempIdMap.get(oldId);
					
					String gradeCode = subjectModel.get("gradecode");
					if(subRecodMap.containsKey(gradeCode)){
						subRecodMap.get(gradeCode).add(newId);
					}else{
						List<Long> IdList = new ArrayList<Long>();
						IdList.add(newId);
						subRecodMap.put(gradeCode, IdList);
					}
					
					continue;
				}
				
				subjectTemp[1] = subjectModel.get("name");
				subjectTemp[2] = subjectModel.get("code");
				subjectTemp[3] = schId;
				subjectTemp[4] = subjectModel.get("id");
				subjectTemp[5] = subjectModel.get("seq");
				subjectTemp[6] = EnumAll.SubjectFlag.SCHOOLFLAG.getValueStr();
				subjectTemp[7] = userId;
				subjectTemp[8] = new Date();
				
				list.add(subjectTemp);
			}
			
			Object[][] subjectParas = new Object[list.size()][8];
			for (int i = 0; i<list.size(); i++) {
				Object[] obj = (Object[])list.get(i);
				subjectParas[i] = obj;
			}
			
			Db.batch(insertSubjectSql.toString(), subjectParas, StaticData.Batch_Size);
		}
	}
	
	/**
	 * 删除学校，copy记录到历史表，删除原表的数据
	 * @param _schoolId
	 */
	public void delSchool(String _schoolId) {
		OrgModel model = 
				OrgModel.dao.findFirst("select * from base_organization where 1=1 and status = '"+Status.ENABLE.getValueStr()+"' and id = ? ", _schoolId);
		if(null != model){
			HisSchoolModel hisModel = new HisSchoolModel();
			Set<Entry<String, Object>> set = model.getAttrsEntrySet();
			for (Entry<String, Object> entry : set) {
				hisModel.set(entry.getKey(), entry.getValue());
			}
			hisModel.save();
			
			model.deleteById(_schoolId);
		}
	}
	
	/**
	 * 变更学校状态
	 * @param _schoolId
	 */
	public void changeSchStatus(String _schoolId) {
		OrgModel model = OrgModel.dao.findFirst("select * from base_organization where 1=1 and id = ? ", _schoolId);
		String status = "";
		if((Status.DISABLED.getValueStr()).equals(model.get("status"))){
			status = Status.ENABLE.getValueStr();
		}else{
			status = Status.DISABLED.getValueStr();
		}
		this.upSchRAccount(_schoolId, status);
		model.set("status", status);
		model.update();
	}

	/**
	 * 当设置为禁用时，归属该学校的所有账户都不能登录系统，设置账户状态为不可用,反之则设置为可用
	 * */
	private void upSchRAccount(String _schoolId, String status) {
		String sql = "update sys_account set status = ? where orgId = ?";
		Db.update(sql, status, _schoolId);
	}
	
	/**
	 * 生成学校编码
	 * @param orgId
	 * @return
	 */
	public String genSchCode(String orgId) {
		//先获取当前教育局的编码
    	String edbFlag = OrgFlag.EDBFLAG.getValueStr();
    	OrgModel edbModel = 
    			OrgModel.dao.findFirst("select * from base_organization where 1=1 and orgFlag = '"+edbFlag+"' and id = ? ", orgId);
    	String schoolCode = edbModel.getStr("orgCode");
    	
    	//根据教育局ID，查找当前教育局下的学校
    	String schoolFlag = OrgFlag.SCHOOLFLAG.getValueStr();
    	List<OrgModel> schoolList = 
    			OrgModel.dao.find("select * from base_organization where 1=1 and orgFlag = '"+schoolFlag+"' and orgId = ?", orgId);
    	if(CollectionUtils.isNotEmpty(schoolList)){
    		Integer max = 1;
    		for (OrgModel school : schoolList) {
    			String orgCode = school.getStr("orgCode");
    			String curIndex = orgCode.substring(orgCode.length()-2, orgCode.length());
    			if(Integer.valueOf(curIndex).compareTo(max) > 0){
    				max = Integer.valueOf(curIndex);
    			}
			}
    		
    		String maxStr = String.valueOf(max + 1);
    		if(maxStr.length() == 1){
    			maxStr = "_00" + maxStr;
    		}else if(maxStr.length() == 2){
    			maxStr = "_0" + maxStr;
    		}
    		schoolCode += maxStr;
    		
    	}else{
    		//当前是第一个学校
    		schoolCode += "_001";
    	}
		return schoolCode;
	}
	
	/**
	 * 如果该学校下存在子记录，则不允许删除(学校机构部门 /教职工/年级/科目/学生/家长 )
	 * @param _schoolId
	 */
	public String checkBeforeDel(String _schoolId) {
		List<DeptModel> deptList = 
				DeptModel.dao.find("select * from base_department where orgId = ? ", _schoolId);
		if(CollectionUtils.isNotEmpty(deptList)){
			return "该学校下面存在下级机构部门,不允许删除!";
		}
		
		List<EmpeModel> empList = 
				EmpeModel.dao.find("select * from base_employee where 1=1 and status = '"+Status.ENABLE.getValueStr()+"' and  orgId = ? ", _schoolId);
		if(CollectionUtils.isNotEmpty(empList)){
			return "该学校下面存在教职员工,不允许删除!";
		}
		
		List<GradeModel> gradeList = 
				GradeModel.dao.find("select * from base_grade where schId = ? ", _schoolId);
		if(CollectionUtils.isNotEmpty(gradeList)){
			return "该学校下面存在年级信息,不允许删除!";
		}
		
		List<SubjectModel> subjectList = 
				SubjectModel.dao.find("select * from base_subject where schId = ? ", _schoolId);
		if(CollectionUtils.isNotEmpty(subjectList)){
			return "该学校下面存在科目信息,不允许删除!";
		}
		
		List<StudentModel> stuList = 
				StudentModel.dao.find("select * from base_student where 1=1 and status = '"+Status.ENABLE.getValueStr()+"' and schId = ? ", _schoolId);
		if(CollectionUtils.isNotEmpty(stuList)){
			return "该学校下面存在学生信息,不允许删除!";
		}
		
		List<RstuAndParentModel> parentList = 
				RstuAndParentModel.dao.find("select * from r_student_info where schId = ? ", _schoolId);
		if(CollectionUtils.isNotEmpty(parentList)){
			return "该学校下面存在家长信息,不允许删除!";
		}
		
		return "";
	}
    
    /**
     * 分页查询公司基础信息 
     * @param pageNumber 每页大小
     * @param pageSize 每页显示条数
     *@param paramsMap  @return
     */
    public Page<OrgModel> findCompanyByPage(int pageNumber, int pageSize, String orgFlag, Map<String, Object> paramsMap) {
        List<Object> params = new ArrayList<>();
        //params.add(OrgFlag.EDBFLAG.getValueStr());
        String selectSql = "select a.*,b.orgName as parentOrg,c.name as createName,d.account as account_Id ";
        StringBuilder sql = new StringBuilder(" from base_organization a LEFT JOIN base_organization b ON a.orgId = b.id ")
        		.append(" LEFT JOIN sys_account c on a.creator=c.id  ")
        		.append(" LEFT JOIN sys_account d on a.accountId = d.id ")
                .append(" where a.orgFlag = ? ");
        params.add(orgFlag);
        //sql.append(" and (a.orgId = ? or a.id = ?)");
       // params.add(orgId);
        //params.add(orgId);
        Map<String,String> dataAuthMap = new HashMap<>();
        dataAuthMap.put(CommonStaticData.OrgTreeNodeType.COMPANYFLAG, "a.id");
		CommonUtil.appendDataAuthSql(dataAuthMap, sql, params);

        sql.append(" order by a.createTime desc ");
        return OrgModel.dao.paginate(pageNumber,pageSize,selectSql,sql.toString(),params.toArray());
    }
    
    /**
     * 根据公司名称模糊查询出公司信息
     */
    public Page<OrgModel> queryCompanyByName(int pageNumber, int pageSize,String companyName, String orgFlag){
    	List<Object> params = new ArrayList<>();
    	String selectSql = "select a.*,b.orgName as parentOrg ,c.name as createName,d.account as account_Id";
    	StringBuilder sql = new StringBuilder(" from base_organization a LEFT JOIN base_organization b ON a.orgId = b.id ")
    	.append(" LEFT JOIN sys_account c on a.creator=c.id  ")
		.append(" LEFT JOIN sys_account d on a.accountId = d.id ")
        .append(" where a.orgFlag = ? ");
    	params.add(orgFlag);
        sql.append(" and (a.orgName like '%"+companyName+"%') ");
        sql.append(" order by a.createTime desc ");
        return OrgModel.dao.paginate(pageNumber,pageSize,selectSql,sql.toString(),params.toArray());
    }
    
    /**
     * 检验公司名称是否存在
     */
    public OrgModel validateCompanyName(String companyName,Long companyId,String comFlag){
    	StringBuilder sql = new StringBuilder("select * from base_organization a where a.orgFlag=? and a.orgName = ?");
        List<Object> params = new ArrayList<>();
        params.add(comFlag);
        params.add(companyName);
        if (companyId != 0){
            sql.append(" and a.id != ?");
            params.add(companyId);
        }
        return OrgModel.dao.findFirst(sql.toString(),params.toArray());
    }
    
    /**
     * 根据当前公司查询以上所有父节点
     * @param pageNumber
     * @param pageSize
     * @param itSelf
     * @return
     */
    public Page<OrgModel> findFNodeByItSelfPage(int pageNumber, int pageSize,String comFlag, String itSelf,Long companyOrgId,String provinceId) {
    	String sql = " SELECT getOrgParentList (o.id) AS ids FROM base_organization o where o.orgFlag = ? and o.id = ? ";
        List<Object> params = new ArrayList<>();
        params.add(comFlag);
        params.add(itSelf);
        List<Record> orgIdRecord = Db.find(sql.toString(), params.toArray());
        String ids = "";
		for (Record temp : orgIdRecord){
			ids = temp.getStr("ids");
		}
		List<Object> param = new ArrayList<>();
		String selectSql = " select DISTINCT *  ";
		StringBuilder selectSql1 = new StringBuilder(" from ( select a.* from base_organization a where a.id in("+ids+")  and a.id<>?  ");
		param.add(itSelf);
		selectSql1.append(" union all ");
		selectSql1.append(" select c.* from base_organization c where ");
		selectSql1.append(" c.provinceCode=? and c.id<>? and c.orgFlag=? ) aa"); 
		param.add(provinceId);
		param.add(itSelf);
		param.add(comFlag);
        return OrgModel.dao.paginate(pageNumber,pageSize,selectSql,selectSql1.toString(),param.toArray());
    }
    
    /**
     * 根据省市 区查询公司下拉框的值
     */
    public List<OrgModel> findCompanyByProvinceOrCityOrArea(String bmId,String cityCode,String areaCode){
    	return OrgModel.dao.find("  select * from base_organization t where t.provinceCode = ? and cityCode = ? and areaCode = ?  and t.orgFlag=?",bmId,cityCode,areaCode,OrgFlag.COMPANYFLAG.getValueStr());
    }
}
