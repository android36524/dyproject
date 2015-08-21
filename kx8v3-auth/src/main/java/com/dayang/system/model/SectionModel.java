package com.dayang.system.model;

import java.util.List;

import com.jfinal.plugin.activerecord.Model;


/**
 * 
 * 类描述：章节管理
 * <pre>
 * -------------History------------------
 *   DATE       AUTHOR       VERSION        DESCRIPTION
 *  2015-5-19      李中杰               V01.00.001		      新增内容   
 * </pre>
 * 
 * @author <a href="mailto:lizj@dayanginfo.com">李中杰</a>
 */
public class SectionModel extends Model<SectionModel>{

    private static final long serialVersionUID = 1L;
	
    public static final SectionModel dao = new SectionModel();
    
    /**
     * 
     * 
     * @param @param shhoolBookId
     * @param @return    设定文件 
     * @return List<SectionModel>    返回类型 
     * @throws
     */
    public static List<SectionModel> findSectionBySchoolBook(int shoolBookId ){
    	StringBuffer sql = new StringBuffer(" SELECT s.*,ver.name as schBookVerName FROM ");
    	sql.append(" base_section s LEFT JOIN base_schoolbook sb ON s.schoolBookId = sb.id ");
    	sql.append(" LEFT JOIN base_schbookver ver ON sb.schBookVerId = ver.id ");
    	sql.append(" where 1 = 1 and s.parentId = 0 and ver.id = ? ");
    	return SectionModel.dao.find(sql.toString(),shoolBookId);
    }
    
    /**
     * 根据课本id查询章节
     * 
     * @param @param shoolBookId
     * @param @return    设定文件 
     * @return List<SectionModel>    返回类型 
     * @throws
     */
    public static List<SectionModel> findSectionList(int shoolBookId ){
    	String sql = "SELECT bs.name , coalesce(bs.parentId,bs.schoolBookId) as pId , bs.id as id FROM base_section bs where bs.schoolBookId = ? ";
    	return SectionModel.dao.find(sql,shoolBookId);
    }
    
    
    /**
     * 根据父类id查询子节点
     * 
     * @param @param parentId
     * @param @return    设定文件 
     * @return List<SectionModel>    返回类型 
     * @throws
     */
    public static List<SectionModel> findSectionAllByParentId(int parentId){
    	String sql = "select * from base_section  where parentId = ? ";
    	return SectionModel.dao.find(sql.toString(),parentId);
    } 
}
