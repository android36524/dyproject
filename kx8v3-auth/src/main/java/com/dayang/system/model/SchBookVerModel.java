package com.dayang.system.model;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.dayang.commons.jfinal.kit.ConditionsKit;
import com.dayang.commons.util.CommonUtil;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;


/**
 * 
 * 类描述：教材版本管理   //人教版2011
 * <pre>
 * -------------History------------------
 *   DATE       AUTHOR       VERSION        DESCRIPTION
 *  2015-5-19      李中杰               V01.00.001		      新增内容   
 * </pre>
 * 
 * @author <a href="mailto:lizj@dayanginfo.com">李中杰</a>
 */
public class SchBookVerModel extends Model<SchBookVerModel>{

    private static final long serialVersionUID = 1L;
	
    public static final SchBookVerModel dao = new SchBookVerModel();
    
    public static final Logger logger = Logger.getLogger(SchBookVerModel.class);
    
    
    /**
     * 分页查询教材版本
     * 
     * @param @param pageNumber
     * @param @param pageSize
     * @param @param stageName
     * @param @return    设定文件 
     * @return Page<SchoolBookVersionModel>    返回类型 
     * @throws
     */
	public static Page<SchBookVerModel> findSchBookVer(int pageNumber,int pageSize,String schBookVerName){
		 StringBuffer sql = new StringBuffer(" FROM base_schBookVer s LEFT JOIN base_press p on s.pressId = p.id left join sys_account f on s.creator=f.id where 1=1  ");
	        final List<Object> parameters = new ArrayList<Object>();
	        String selSql = "SELECT s.* ,p.name as pressName,f.name as creatorName ";
	        if(!CommonUtil.isEmpty(schBookVerName)){
	            sql.append(" and s.name like ? ");
	            parameters.add("%"+schBookVerName+"%");
	        }
	        sql.append(" order by s.createTime ");
	        return SchBookVerModel.dao.paginate(pageNumber, pageSize, selSql, sql.toString(), parameters.toArray());
	}
	
	/**
	 * 查询所有的教材版本  不分页
	 * 
	 * @param @return    设定文件 
	 * @return List<SchBookVerModel>    返回类型 
	 * @throws
	 */
	public static List<SchBookVerModel> findSchBookVerAll(){
		String sql =" select * from base_schbookver ORDER BY createTime desc ";
		return SchBookVerModel.dao.find(sql);
	}
	
	/**
	 *  根据名称查询是否存在该名称
	 * @param name
	 * @return
	 */
	public static List<SchBookVerModel> findSchoolBookVerByName(String name,Integer id){
		ConditionsKit con = new ConditionsKit();
		String sql = " select * from base_schbookver where 1=1 ";
		if(CommonUtil.isNotEmptyString(name)){
			con.setValueQuery(ConditionsKit.EQUAL, "name", name);
		}
		if(CommonUtil.isNotEmptyObject(id)){
			con.setValueQuery(ConditionsKit.NOT_EQUAL, "id", id);
		}
		con.modelToCondition(new LoreModel());
		sql += con.getSql();
		logger.info("根据名称与ID查询数据执行SQL："+sql);
		return SchBookVerModel.dao.find(sql,con.getParamList().toArray());
	}
	
}
