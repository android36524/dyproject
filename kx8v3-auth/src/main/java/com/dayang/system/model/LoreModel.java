package com.dayang.system.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.dayang.commons.jfinal.kit.ConditionsKit;
import com.dayang.commons.pojo.QueryDefineParaPojo;
import com.dayang.commons.util.CommonUtil;
import com.dayang.commons.util.StaticData;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Record;

/**
 * 类描述：知识点管理Model
 * <pre>
 * -------------History------------------
 *   DATE                 AUTHOR         VERSION        DESCRIPTION
 *  2015年5月19日上午10:57:46        刘生慧              		 V01.00.001		 新增内容   
 * </pre>
 * 
 * @author <a href="liush@dayanginfo.com">刘生慧</a>
 */
public class LoreModel extends Model<LoreModel> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final LoreModel dao = new LoreModel();
	
	public static final Logger logger = Logger.getLogger(LoreModel.class);
	
	public static final Map<String, QueryDefineParaPojo> filterMap= new HashMap<String,QueryDefineParaPojo>(){
        /**
		 *
		 */
		private static final long serialVersionUID = 1L;
		
		{
		    put("name",new QueryDefineParaPojo("name","name","="));
		    put("subjectId",new QueryDefineParaPojo("subjectId","subjectId","="));
		    put("id",new QueryDefineParaPojo("id","id","!="));
		}
	 };

	/**
	 * 根据条件获取知识点
	 * @param subjectId
	 * @param name
	 * @param objs
	 * @return
	 */
	public static List<LoreModel> searchLore(Long subjectId,String name,Integer id,String stageId,String gradeId,String sectionId){
		ConditionsKit con = new ConditionsKit();
		String sql = "select t.id,t.seq,t.stageId,t.gradeId,t.subjectId,t.name,t.remark,t.parentId as pId,(select name from base_lore where id = t.parentId) as parentName  ";
//			   sql += " from base_lore bl where 1=1  ";
		String filterSql = " FROM " +
						   "  base_lore t " +
						" WHERE " + 
							" FIND_IN_SET( "+
								" t.id, "+
								" ( "+
									" SELECT "+
										" GROUP_CONCAT(a.ids) "+
									" FROM "+
										" ( "+
											" SELECT "+
												" getLoreParentList (o.id) AS ids, "+
												" 1 AS gid "+
											" FROM "+
												" base_lore o where 1=1 ";
		sql = sql + filterSql;
		if(CommonUtil.isNotEmptyObject(subjectId)){
			con.setValueQuery(ConditionsKit.EQUAL, "o.subjectId", subjectId);
		}
		if(!CommonUtil.isEmpty(name)){
			con.setValueQuery(ConditionsKit.FUZZY, "o.name", name);
		}
		if(!CommonUtil.isEmpty(id)){
			con.setValueQuery(ConditionsKit.EQUAL, "o.parentId", id);
		}
		if(CommonUtil.isNotEmptyObject(stageId)){
			con.setValueQuery(ConditionsKit.EQUAL, "o.stageId", stageId);
		}
		if(CommonUtil.isNotEmptyObject(stageId)){
			con.setValueQuery(ConditionsKit.EQUAL, "o.gradeId", gradeId);
		}
//		if(CommonUtil.isEmpty(objs[0])){
//			sql += " and o.parentId is null ";
//		}
		con.modelToCondition(new LoreModel());
		sql += con.getSql();
		sql += " ) as a group by a.gid ) ) ";
		if(CommonUtil.isNotEmptyObject(sectionId)){
			sql+=" and t.id in(select aa.loreId from r_section_lore aa where aa.sectionId="+sectionId+" ) ";
		}
		sql += " order by seq";
		logger.info("根据条件查询知识点执行SQL："+sql);
		return LoreModel.dao.find(sql,con.getParamList().toArray());
	}
	
	/**
	 * 根据章节ID来查询知识点，先查询出全部信息，如果是此章节的知识点，就是选中状态
	 * @param subjectId
	 * @param name
	 * @param id
	 * @param stageId
	 * @param gradeId
	 * @param sectionId
	 * @return
	 */
	public static List<LoreModel>  searchLoreBySectionId(Long subjectId,String name,Integer id,String stageId,String gradeId,String sectionId){
		ConditionsKit con = new ConditionsKit();
		String sql = "select t.id,t.stageId,t.gradeId,t.subjectId,t.name,t.remark,t.parentId as pId,(select name from base_lore where id = t.parentId) as parentName, (select aa.loreId from r_section_lore aa where aa.sectionId="+sectionId+" and aa.loreId=t.id ) as idid  ";
//			   sql += " from base_lore bl where 1=1  ";
		String filterSql = " FROM " +
						   "  base_lore t " +
						" WHERE " + 
							" FIND_IN_SET( "+
								" t.id, "+
								" ( "+
									" SELECT "+
										" GROUP_CONCAT(a.ids) "+
									" FROM "+
										" ( "+
											" SELECT "+
												" getLoreParentList (o.id) AS ids, "+
												" 1 AS gid "+
											" FROM "+
												" base_lore o where 1=1 ";
		sql = sql + filterSql;
		if(CommonUtil.isNotEmptyObject(subjectId)){
			con.setValueQuery(ConditionsKit.EQUAL, "o.subjectId", subjectId);
		}
		if(!CommonUtil.isEmpty(name)){
			con.setValueQuery(ConditionsKit.FUZZY, "o.name", name);
		}
		if(!CommonUtil.isEmpty(id)){
			con.setValueQuery(ConditionsKit.EQUAL, "o.parentId", id);
		}
		if(CommonUtil.isNotEmptyObject(stageId)){
			con.setValueQuery(ConditionsKit.EQUAL, "o.stageId", stageId);
		}
		if(CommonUtil.isNotEmptyObject(stageId)){
			con.setValueQuery(ConditionsKit.EQUAL, "o.gradeId", gradeId);
		}
//		if(CommonUtil.isEmpty(objs[0])){
//			sql += " and o.parentId is null ";
//		}
		con.modelToCondition(new LoreModel());
		sql += con.getSql();
		sql += " ) as a group by a.gid ) ) ";
		/*if(CommonUtil.isNotEmptyObject(sectionId)){
			sql+=" and t.id in(select aa.loreId from r_section_lore aa where aa.sectionId="+sectionId+" ) ";
		}*/
		sql += " order by seq";
		logger.info("根据条件查询知识点执行SQL："+sql);
		return LoreModel.dao.find(sql,con.getParamList().toArray());
	}
	
	/**
	 * 异步加载子节点
	 * @param subjectId
	 * @param name
	 * @param objs
	 * @return
	 */
	public static List<LoreModel> asyncSearch(Long subjectId,String name,Object...objs){
		ConditionsKit con = new ConditionsKit();
		String sql = "select bl.id,bl.stageId,bl.gradeId,bl.subjectId,bl.name,bl.remark,bl.parentId as pId,(select name from base_lore where id = bl.parentId) as parentName  ";
			   sql += " from base_lore bl where 1=1  ";
		if(CommonUtil.isNotEmptyObject(subjectId)){
			con.setValueQuery(ConditionsKit.EQUAL, "bl.subjectId", subjectId);
		}
		if(!CommonUtil.isEmpty(name)){
			con.setValueQuery(ConditionsKit.FUZZY, "bl.name", name);
		}
		if(!CommonUtil.isEmpty(objs[0])){
			con.setValueQuery(ConditionsKit.EQUAL, "bl.parentId", objs[0]);
		}
		if(CommonUtil.isEmpty(objs[0])){
			sql += " and bl.parentId is null ";
		}
		con.modelToCondition(new LoreModel());
		sql += con.getSql();
		sql += " order by seq";
		logger.info("异步加载知识点执行SQL："+sql);
		return LoreModel.dao.find(sql,con.getParamList().toArray());
	}
	
	/**
	 * 根据ID删除知识点
	 * @param id
	 * @return
	 */
	public static Boolean deleLoreById(Integer id){
		return LoreModel.dao.deleteById(id);
	} 
	
	/**
	 *  根据名称查询是否存在该名称
	 * @param name
	 * @return
	 */
	public static List<LoreModel> findLoreByName(Map<String,Object> param){
		StringBuilder select = new StringBuilder(" select bl.* from base_lore bl where 1=1 ");
		List<Object> list = new ArrayList<Object>();
	    CommonUtil.setDefaultPara(param,select,list,"bl",filterMap);
	    return LoreModel.dao.find(select.toString(),list.toArray());
	}
	
	/**
	 * 交换排序值
	 * @param id
	 * @param moveId
	 * @return
	 */
	public static Boolean moveLore(Integer id,Integer moveId){
		StringBuffer sql = new StringBuffer(" update base_lore a ,base_lore b set a.seq=b.seq,b.seq=a.seq where a.id=? and b.id=? ");
		int count = Db.update(sql.toString(),id,moveId);
		return count ==2 ;
	}
	
	/**
	 * 更换知识点（升、降级）
	 * @param id
	 * @param pId
	 * @return
	 */
	public static Boolean loreChange(Integer id,Integer pId){
		StringBuffer sql = new StringBuffer(" update base_lore a set a.parentId = ? where id = ? ");
		int count = Db.update(sql.toString(),pId,id);
		return count == 1;
	}
	
	/**
	 * 根据ID查询知识点
	 * @param id
	 * @return
	 */
	public static List<LoreModel> searchLoreById(Integer id){
		StringBuffer sql = new StringBuffer(" select bl.*,( select temp.name from base_lore temp where temp.id = bl.parentId  ) as  parentName from base_lore bl where bl.id = ? ");
		return LoreModel.dao.find(sql.toString(),id);
	}
	
	/**
	 * 通过知识点查询章节关联信息
	 * @param id
	 * @return
	 */
	public static Boolean findLoreRela(Integer id){
		StringBuffer sql = new StringBuffer(" select * from r_section_lore where loreId=? ");
		List<Record> list = Db.find(sql.toString(),id);
		return list.size()>0? true : false;
	}
}
