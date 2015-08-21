package com.dayang.system.model;

import java.util.List;

import com.dayang.commons.util.IDKeyUtil;
import com.dayang.commons.util.StaticData;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;

/**
 * 
 * 类描述：年级科目中间表
 * 
 * <pre>
 * -------------History------------------
 *   DATE       AUTHOR       VERSION        DESCRIPTION
 *  2015-5-18      李中杰               V01.00.001		      新增内容
 * </pre>
 * 
 * @author <a href="mailto:lizj@dayanginfo.com">李中杰</a>
 */
public class GradeSubjectModel extends Model<GradeSubjectModel> {

	private static final long serialVersionUID = 1L;

	public static final GradeSubjectModel dao = new GradeSubjectModel();

	/**
	 * 年级科目中间表  现根据年级删除所有  再更新
	 * 
	 * @param @param gradeId
	 * @param @param subjectIds
	 * @param @return    设定文件 
	 * @return Boolean    返回类型 
	 * @throws
	 */
	public Boolean updateSubjectByGrade(Long gradeId, Integer[] subjectIds) {
		if (gradeId < 1 || subjectIds == null || subjectIds.length < 1) {
			return false;
		}
		String sql = "insert into r_gradesubject (id,gradeId,subjectId) values (?,?,?)";
		Object[][] obj = new Object[subjectIds.length][2];
		for (int i = 0; i < subjectIds.length; i++) {
			Object[] temp = new Object[3];
			temp[0] = IDKeyUtil.getIDKey();
			temp[1] = gradeId;
			temp[2] = subjectIds[i];
			obj[i] = temp;
		}
		Db.batch(sql, obj, StaticData.Batch_Size);
		return true;
	}
	
	/**
	 * 根据年级查询科目
	 * 
	 * @param @param gradeId
	 * @param @return    设定文件 
	 * @return List<SubjectModel>    返回类型 
	 * @throws
	 */
	public List<GradeSubjectModel> findSubjectByGrade(Long gradeId){
		String sql = " SELECT s.* FROM r_gradesubject g LEFT JOIN base_subject s ON g.subjectId = s.id where g.gradeId = ? ";
		return GradeSubjectModel.dao.find(sql,gradeId);
	}

}
