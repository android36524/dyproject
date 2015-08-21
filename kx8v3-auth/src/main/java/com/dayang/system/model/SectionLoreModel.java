package com.dayang.system.model;

import com.jfinal.plugin.activerecord.Model;


/**
 * 
 * 类描述：章节知识点管理
 * <pre>
 * -------------History------------------
 *   DATE       AUTHOR       VERSION        DESCRIPTION
 *  2015-5-19      李中杰               V01.00.001		      新增内容   
 * </pre>
 * 
 * @author <a href="mailto:lizj@dayanginfo.com">李中杰</a>
 */
public class SectionLoreModel extends Model<SectionLoreModel>{

    private static final long serialVersionUID = 1L;
	
    public static final SectionLoreModel dao = new SectionLoreModel();
    
    
    /**
     * 根据章节id查询关联总条数
     * 
     * @param @param sectionId
     * @param @return    设定文件 
     * @return int    返回类型 
     * @throws
     */
    public static Long findSectionLoreBySectionId(int sectionId){
    	String sql = " SELECT count(*) as sectionCount FROM `r_section_lore` where sectionId = ? ";
    	return SectionLoreModel.dao.findFirst(sql,sectionId).getLong("sectionCount");
    	
    }
	
}
