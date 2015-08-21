package com.dayang.system.model;
import java.util.ArrayList;
import java.util.List;
import com.dayang.commons.util.DaYangCommonUtil;
import com.dayang.system.controller.StuParentController;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Record;

/**
 * 类描述：学生家长关联model
 * <pre>
 * -------------History------------------
 *   DATE                 AUTHOR         VERSION        DESCRIPTION
 *  2015年5月25日上午11:41:37        吴杰东              		 V01.00.001		 新增内容   
 * </pre>
 * 
 * @author <a href="wujd@dayanginfo.com">吴杰东</a>
 */
public class RstuAndParentModel extends Model<RstuAndParentModel> {

	private static final long serialVersionUID = 1L;
	
	public static final RstuAndParentModel dao = new RstuAndParentModel();
	
	/**
	 * 根据学校ID和家长ID获取学生家长关联表信息
	 * @param schId
	 * @param parentId
	 * @return
	 */
	public List<RstuAndParentModel> findRstudent_info(final String queryType,Long schId,String idStr) {
	    StringBuilder sql = new StringBuilder("select r.* from r_student_info r where 1=1 ");
        List<Object> params = new ArrayList<>();
        if(schId != 0)
        {
        	if (StuParentController.EQUAL.equals(queryType)) {
        		sql.append(" and r.schId  = ?");
        	}else if(StuParentController.NOT_EQUAL.equals(queryType)){
        		sql.append(" and r.schId != ?");
        	}
            params.add(schId);
        }
        sql.append(" and FIND_IN_SET(r.parentId,?) ");
        params.add(idStr);
	   return RstuAndParentModel.dao.find(sql.toString(),params.toArray());
	 }
	
	/**
	 * 根据学校ID和家长ID获取关联表中学生家长ID
	 * @param queryType
	 * @param schId
	 * @param idStr
	 * @return
	 */
	public String findParentIds(final String queryType,Long schId,String idStr){
	    StringBuilder sql = new StringBuilder("select r.* from r_student_info r where 1=1 ");
        List<Object> params = new ArrayList<>();
        if(schId != 0)
        {
        	if (StuParentController.EQUAL.equals(queryType)) {
        		sql.append(" and r.schId  = ?");
        	}else if(StuParentController.NOT_EQUAL.equals(queryType)){
        		sql.append(" and r.schId != ?");
        	}
            params.add(schId);
        }
        sql.append(" and FIND_IN_SET(r.parentId,?) ");
        params.add(idStr);
		List<Record> reList = Db.find(sql.toString(),params.toArray());
		String idStrs = DaYangCommonUtil.column2Str(reList, "parentId");
		return idStrs;
	}
	
	/**
	 * 根据家长ID和学校ID删除学生家长关联表信息
	 * @param idStr
	 * @param schId
	 * @return
	 */
	public static Boolean deleRStuParent(final String idStr, final Long schId){
		StringBuffer insertRelaSql = new StringBuffer("Insert into his_student_info select * from r_student_info where schId = ? and FIND_IN_SET(parentId,?)");
		StringBuffer deleRelaStuSql = new StringBuffer("delete from r_student_info where schId = ? and FIND_IN_SET(parentId,?) ");
		Db.update(insertRelaSql.toString(),schId,idStr);
		int t = Db.update(deleRelaStuSql.toString(), schId,idStr);
		return t > 0;
	}
	
}
