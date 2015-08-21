package com.dayang.system.model;

import java.util.ArrayList;
import java.util.List;

import com.dayang.commons.util.CommonUtil;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;


/**
 * 
 * 类描述：出版社管理
 * <pre>
 * -------------History------------------
 *   DATE       AUTHOR       VERSION        DESCRIPTION
 *  2015-5-19      李中杰               V01.00.001		      新增内容   
 * </pre>
 * 
 * @author <a href="mailto:lizj@dayanginfo.com">李中杰</a>
 */
public class SchoolBookModel extends Model<SchoolBookModel>{

    private static final long serialVersionUID = 1L;
	
    public static final SchoolBookModel dao = new SchoolBookModel();
    
    /**
     * 分页查询出版社
     * 
     * @param @param pageNumber
     * @param @param pageSize
     * @param @param stageName
     * @param @return    设定文件 
     * @return Page<SchoolBookModel>    返回类型 
     * @throws
     */
	public static Page<SchoolBookModel> findSchoolBook(int pageNumber,int pageSize,String schoolBookName,String stageId,String gradeId,String subject){
		 StringBuffer sql = new StringBuffer(" FROM base_schoolbook s LEFT JOIN base_schbookver sv ON s.schBookVerId = sv.id ");
		 sql.append(" LEFT JOIN base_grade g ON s.gradeId = g.id LEFT JOIN base_subject su on s.subjectId  = su.id ");
		 sql.append(" LEFT JOIN base_press p ON sv.pressId = p.id LEFT JOIN base_stage st ON s.stageId = st.id LEFT JOIN sys_account u on u.id=s.creator where 1 = 1  ");
		 
	        final List<Object> parameters = new ArrayList<Object>();
	        StringBuffer selSql = new StringBuffer(" SELECT s.*,sv.name as schbookverName,sv.pressTime as pressTime,g.`name` as gradeName,");
	        selSql.append(" su.`name` AS subjectName ,p.`name` as pressName ,st.name as stageName ,u.`name` as creatorName ");
	        if(!CommonUtil.isEmpty(schoolBookName)){
	            sql.append(" and sv.name like ? ");
	            parameters.add("%"+schoolBookName+"%");
	        }
	       
	        if(!CommonUtil.isEmpty(stageId)){
	        	sql.append(" and s.stageId=? ");
	        	parameters.add(stageId);
	        }
	        
	        if(!CommonUtil.isEmpty(gradeId)){
	        	sql.append(" and s.gradeId=? ");
	        	parameters.add(gradeId);
	        }
	        
	        if(!CommonUtil.isEmpty(subject)){
	        	sql.append(" and s.subjectId=? ");
	        	parameters.add(subject);
	        }
	        
	        sql.append(" order by s.createTime ");
	        return SchoolBookModel.dao.paginate(pageNumber, pageSize, selSql.toString(), sql.toString(), parameters.toArray());
	}
	
	
	/**
	 * 根据教材id查询教材版本   组合树用
	 * 
	 * @param @param id
	 * @param @return    设定文件 
	 * @return SchoolBookModel    返回类型 
	 * @throws
	 */
	public static SchoolBookModel findSchoolBookInfo(int id){
		String sql =" SELECT bs.id ,bv.`name` as name FROM base_schoolbook bs LEFT JOIN base_schbookver bv ON bs.schBookVerId = bv.id where bs.id = ? ";
		return SchoolBookModel.dao.findFirst(sql,id);
	}
	
}
