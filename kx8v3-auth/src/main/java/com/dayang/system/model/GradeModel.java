package com.dayang.system.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dayang.commons.enums.EnumAll;
import com.dayang.commons.pojo.QueryDefineParaPojo;
import com.dayang.commons.util.CommonStaticData;
import com.dayang.commons.util.CommonUtil;
import com.dayang.commons.util.StaticData;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;


/**
 * 
 * 类描述：年级管理
 * <pre>
 * -------------History------------------
 *   DATE       AUTHOR       VERSION        DESCRIPTION
 *  2015-5-18      李中杰               V01.00.001		      新增内容   
 * </pre>
 * 
 * @author <a href="mailto:lizj@dayanginfo.com">李中杰</a>
 */
public class GradeModel extends Model<GradeModel>{

    private static final long serialVersionUID = 1L;
	
    public static final GradeModel dao = new GradeModel();
    
    private static int suffix;//编码后缀值
    public static final Map<String, QueryDefineParaPojo> DefineMap= new HashMap<String,QueryDefineParaPojo>(){
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		{
			put("gradeName",new QueryDefineParaPojo("gradeName","name","like"));
			put("notid",new QueryDefineParaPojo("notid","id","<>"));
		}
	};
	
	 public static final Map<String, QueryDefineParaPojo> filterMap= new HashMap<String,QueryDefineParaPojo>(){
	        /**
			 *
			 */
			private static final long serialVersionUID = 1L;
			
			{
			    put("name",new QueryDefineParaPojo("name","name","="));
			    put("schId",new QueryDefineParaPojo("schId","schId","="));
			    put("id",new QueryDefineParaPojo("id","id","!="));
			}
	 };
	 
	 /**
	 * 权限控制map
	 */
	public static final Map<String, String> DataMap= new HashMap<String,String>(){
		{
			put(CommonStaticData.OrgTreeNodeType.SCHFLAG,"g.schId");
			put(CommonStaticData.OrgTreeNodeType.GRADEFLAG,"g.id");
		 }
	 };
   	
	 /**
     * 分页查询年级
     * 
     * @param @param pageNumber
     * @param @param pageSize
     * @param @param stageName
     * @param @return    设定文件 
     * @return Page<StageModel>    返回类型 
     * @throws
     */ 
	public Page<GradeModel> findGradePage(int pageNumber,int pageSize,Map<String,Object> map){
		String selSql = " SELECT g.*,s.name as stageName, group_concat(CONCAT(su.`name`)) AS km  ,o.orgName schId_showname,b.name as creatorName";
		StringBuilder sql = new StringBuilder(" FROM base_grade g LEFT JOIN r_gradesubject gs ON g.id = gs.gradeId ");
		sql.append(" LEFT JOIN base_organization o ON o.id = g.schId");
		sql.append(" LEFT JOIN base_subject su ON su.id = gs.subjectId LEFT JOIN base_stage s on s.id = g.stageId LEFT JOIN sys_account b on g.creator=b.id where 1=1   ");
		final List<Object> parameters = new ArrayList<Object>();   
//	    CommonUtil.setDefaultPara(map,sql,parameters,"g",DefineMap,null,DataMap);
	    if(EnumAll.GradeFlag.SCHOOLFLAG.getValueStr().equals(String.valueOf(map.get("flag")))){
			CommonUtil.setDefaultPara(map,sql,parameters,"g",DefineMap,null,DataMap);
		}else{
			CommonUtil.setDefaultPara(map,sql,parameters,"g",DefineMap);
		}
	    sql.append(" group by g.id  ");
	    sql.append(" order by s.seq,g.seq asc");
	    return GradeModel.dao.paginate(pageNumber, pageSize, selSql, sql.toString(), parameters.toArray());
	}
	
	 /**
     * 分页查询年级
     * 
     * @param @param pageNumber
     * @param @param pageSize
     * @param @param stageName
     * @param @return    设定文件 
     * @return Page<StageModel>    返回类型 
     * @throws
     */ 
	public List<GradeModel> findGradeList(Map<String,Object> map){
		String selSql = " SELECT g.*,s.name as stageName, group_concat(CONCAT(su.`name`)) AS km  ,o.orgName schId_showname";
		StringBuilder sql = new StringBuilder(" FROM base_grade g LEFT JOIN r_gradesubject gs ON g.id = gs.gradeId ");
		sql.append(" LEFT JOIN base_organization o ON o.id = g.schId");
		sql.append(" LEFT JOIN base_subject su ON su.id = gs.subjectId LEFT JOIN base_stage s on s.id = g.stageId where 1=1 ");
		final List<Object> parameters = new ArrayList<Object>();   
		if(EnumAll.GradeFlag.SCHOOLFLAG.getValueStr().equals(String.valueOf(map.get("flag")))){
			CommonUtil.setDefaultPara(map,sql,parameters,"g",DefineMap,null,DataMap);
		}else{
			CommonUtil.setDefaultPara(map,sql,parameters,"g",DefineMap);
		}
	    sql.append(" group by g.id  ");
	    sql.append(" order by  g.code desc");
	    return GradeModel.dao.find(selSql+sql.toString(), parameters.toArray());
	}
	
	
	/**
	 * 年级编号
	 * 
	 * @param @return    设定文件 
	 * @return String    返回类型 
	 * @throws
	 */
	public static String getMaxCode(){
		String sql = "SELECT code FROM base_grade ORDER BY code desc";
		GradeModel gradeModel = GradeModel.dao.findFirst(sql);
		String code ="";
		if(gradeModel!=null){
			code = gradeModel.getStr("code");
		}
		String codePrefix = "";
		String codeSuffix = "";
		if(CommonUtil.isEmpty(code)){
			codePrefix = StaticData.GRADECODE_INIT_VALUE;
		}else{
			codePrefix = code.substring(0,2);
			codeSuffix = code.substring(2,code.length());
			suffix = Integer.parseInt(codeSuffix)+1;
			if(suffix <= 9){
				codeSuffix = "0"+suffix;
			}else{
				codeSuffix = suffix+"";
			}
			codePrefix = codePrefix + codeSuffix;
		}
		return codePrefix;
	}
	
	/**
	 * 查询所有的通用年级
	 * 
	 * @param @return    设定文件 
	 * @return List<GradeModel>    返回类型 
	 * @throws
	 */
	public List<GradeModel> findGradeAll(){
		return findGradeAll(EnumAll.GradeFlag.COMMONFLAG.getValue(),0l);
	}
	
	/**
	 * 根据学阶Id获取年级 通用年级
	 * @param stageId
	 * @return
	 */
	public List<GradeModel> findGradeByStage(Integer stageId){
		return findGradeByStage(stageId,EnumAll.GradeFlag.COMMONFLAG.getValue());
	}
	
	/**
	 * 查询所有的通用年级
	 * 
	 * @param @return    设定文件 
	 * @return List<GradeModel>    返回类型 
	 * @throws
	 */
	public List<GradeModel> findGradeAll(int flag,long schId){
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("flag", flag);
		param.put("schId", schId);
		return GradeModel.dao.findGradeList(param);
	}
	
	/**
	 * 根据学阶查询年级
	 * 
	 * @param @return    设定文件 
	 * @return List<GradeModel>    返回类型 
	 * @throws
	 */
	public List<GradeModel> findGradeByStage(int stageId,int flag){
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("flag", flag);
		param.put("stageId", stageId);
		return GradeModel.dao.findGradeList(param);
	}
	
	/**
	 * 查询所有年级
	 */
	public List<GradeModel> findGrade(int flag){
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("flag", flag);
		return GradeModel.dao.findGradeList(param);
	}
	
	/**
	 * 根据条件查询年级详情
	 * @param param
	 * @return
	 */
	public GradeModel findGradeModel(Map<String,Object> param) {
		StringBuilder sb = new StringBuilder("select * from base_grade where 1=1 ");
		final List<Object> parameters = new ArrayList<Object>(); 
		CommonUtil.setDefaultPara(param,sb,parameters,"",DefineMap);	
		return GradeModel.dao.findFirst(sb.toString(),parameters.toArray());
	}
	
	/**
	 * 根据学校ID查询年级
	 * @param schId
	 * @return
	 */
	public List<GradeModel> findGradeByTerm(Long schId){
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("flag", EnumAll.GradeFlag.SCHOOLFLAG.getValue());
		param.put("schId", schId);		
		return GradeModel.dao.findGradeList(param);
	}

	/**
	 * 根据学校Id查询该学校下的所有年级
	 */
	public List<GradeModel> findGradeListBySchId(long schId){
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("schId", schId);
		param.put("flag", EnumAll.GradeFlag.SCHOOLFLAG.getValue());
		return GradeModel.dao.findGradeList(param);
	}
	
	/**
	 * 根据名称与ID查询在同一个学校下是否存在同名的年级
	 * @param param
	 * @return
	 */
	public List<GradeModel> findGradeByName(Map<String,Object> param){
		StringBuilder select = new StringBuilder(" select bg.* from base_grade bg where 1=1 ");
		List<Object> list = new ArrayList<Object>();
	    CommonUtil.setDefaultPara(param,select,list,"bg",filterMap);
	    return GradeModel.dao.find(select.toString(),list.toArray());
	}
	
}
