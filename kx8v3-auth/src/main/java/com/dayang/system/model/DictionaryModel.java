package com.dayang.system.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.dayang.commons.util.CommonUtil;
import com.jfinal.plugin.activerecord.Model;

/**
 * 类描述：字典管理MODEL类
 * <pre>
 * -------------History------------------
 *   DATE                 AUTHOR         VERSION        DESCRIPTION
 *  2015年03月09日               温建军               V01.00.001		      新增内容   
 * </pre>
 * 
 * @author wjj
 *
 */
public class DictionaryModel extends Model<DictionaryModel> implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final DictionaryModel dao = new DictionaryModel();

	
	/**
	 * 
	* @Title: 查询所有的学阶
	* @param @return    设定文件 
	* @return List<DictionaryModel>    返回类型 
	* @throws
	 */
	public List<DictionaryModel> findStage(){
		return DictionaryModel.dao.find("select d.* from sys_dictionarytype t LEFT JOIN sys_dictionary d on t.id = d.dictionarytype where t.name like '学阶%'");
	}
	
	
	/**
	 * 
	* @Title: 查询年级
	* @param @param parentId 父类id
	* @param @return    设定文件 
	* @return List<DictionaryModel>    返回类型 
	* @throws
	 */
	public List<DictionaryModel> findDictionaryByParent(int dictionarytype,int parentId){
		return DictionaryModel.dao.find("select d.* from sys_dictionary d where d.dictionarytype = ? and d.pdicid = ? ",dictionarytype,parentId);
	}
	
	
	/**
	 * 根据科目 年级查询详情
	* @param @param gr
	* @param @param su
	* @param @return    设定文件 
	* @return GradeSubjectModel    返回类型 
	* @throws
	 */
	public GradeSubjectModel findGradeSubject(String gr,String su){
		return GradeSubjectModel.dao.findFirst("select s.* from s_grade_subject_inf s where s.subjectId = ? and s.gradeValue = ? ", gr, su);
	}
	
	
	/**
	 * 根据多个id获取model
	 * @param id
	 * @return model list
	 */
	public List<DictionaryModel> findByIdList(String[] id){
		List<DictionaryModel> list = new ArrayList<DictionaryModel>();
		for(String i : id){
			DictionaryModel model = DictionaryModel.dao.findById(i);
			if (model != null){
				list.add(model);
			}
		}
		return list;
	}
	
	
	/**
	 * 根据年级value查询年级详情
	* @param @param grValue
	* @param @return    设定文件 
	* @return DictionaryModel    返回类型 
	* @throws
	 */
	public DictionaryModel findDictiByValue(int dictionarytype,String grValue){
		return DictionaryModel.dao.findFirst("select d.* from sys_dictionary d where d.dictionarytype = ? and d.value = ? ",dictionarytype,grValue);
	}


	public List<DictionaryModel> findDictionaryByType(String[] param) {
		List<DictionaryModel> dictList;
		
		dictList = DictionaryModel.dao.find("select * from sys_dictionary a where a.dictionarytype=? order by a.seq asc",param[0]);
		
		return dictList;
	}
	
	
	/**
     * 根据岗位类型获取岗位职业
     */
	public List<DictionaryModel> getJobByEmpeType(int dictionarytype,String code){
		return DictionaryModel.dao.find("select * from sys_dictionary a  where a.dictionarytype = ? and a.`code` = ? ",dictionarytype,code);
	}

	/**
	 * 带条件查询字典
	 * @param params 参数列表
	 * @return list
	 */
	public List<DictionaryModel> findDictionaryByParams(Map<String,Object> params){
		StringBuilder sql = new StringBuilder();
		final List<Object> parameters = new ArrayList<Object>();
		sql.append("select a.* from sys_dictionary a where 1=1 ");
		CommonUtil.setDefaultPara(params, sql, parameters, "a", null);
		return DictionaryModel.dao.find(sql.toString(),parameters.toArray());
	}
}
