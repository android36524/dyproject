package com.dayang.system.model;

import java.util.List;

import org.apache.log4j.Logger;

import com.dayang.commons.jfinal.kit.ConditionsKit;
import com.dayang.commons.util.CommonUtil;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;

/**
 * 类描述：学阶管理Model
 * <pre>
 * -------------History------------------
 *   DATE                 AUTHOR         VERSION        DESCRIPTION
 *  2015年5月18日上午11:27:18        刘生慧              		 V01.00.001		 新增内容   
 * </pre>
 * 
 * @author <a href="liush@dayanginfo.com">刘生慧</a>
 */
public class StageModel extends Model<StageModel> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static Logger logger = Logger.getLogger(StageModel.class);
	
	public static final  StageModel dao = new StageModel();

	/**
	 * 分页查询学阶信息
	 * @param pageNumber 页码
	 * @param pageSize  页面大小
	 * @param stageName 学阶名称
	 * @return
	 */
	public static Page<StageModel> findByStageAll(int pageNumber,int pageSize,String stageName){
		ConditionsKit con = new ConditionsKit();
		String select = " select @rownum:=@rownum+1 AS rownum,bb.* ";
		String sqlSelect = " from (select  s.id,s.name,s.code,s.memo,s.seq,b.name as createName,s.createTime createTime  from  base_stage s,sys_account b where  s.creator=b.id  order by s.seq ) bb,(select @rownum:=0) r where 1=1";
		if(CommonUtil.isNotEmptyString(stageName)){
			con.setValueQuery(ConditionsKit.FUZZY, "bb.name", stageName);
		}
		con.modelToCondition(new StageModel());
		sqlSelect += con.getSql();
		sqlSelect += " order by bb.seq ";
		logger.info(select+sqlSelect);
		return StageModel.dao.paginate(pageNumber, pageSize, select, sqlSelect,con.getParamList().toArray());
	}
	
	/**
	 * 根据id查询学阶
	 * @param id
	 * @return
	 */
	public static StageModel findStageInfo(Integer id){
		String sql = " select * from base_stage where 1=1 ";
		ConditionsKit con = new ConditionsKit();
		if(CommonUtil.isNotEmptyObject(id)){
			con.setValueQuery(ConditionsKit.EQUAL, "id",id);
		}
		con.modelToCondition(new StageModel());
		sql += con.getSql();
		logger.info("根据id查询学阶SQL："+sql);
		return StageModel.dao.findFirst(sql,con.getParamList().toArray());
	}
	
	/**
	 * 查询所有的学阶列表   不带分页
	 * 
	 * @param @return    设定文件 
	 * @return List<StageModel>    返回类型 
	 * @throws
	 */
	public List<StageModel> findStageAll(){
		return StageModel.dao.find("SELECT * FROM base_stage ORDER BY SEQ");
	}
	
	/**
	 * 根据名称查询学阶
	 * @param name
	 * @return
	 */
	public static List<StageModel> findStageByName(String name,Integer id){
		ConditionsKit con = new ConditionsKit();
		String sql = " select * from base_stage where  1=1 ";
		if(CommonUtil.isNotEmptyString(name)){
			con.setValueQuery(ConditionsKit.EQUAL, "name", name);
		}
		if(CommonUtil.isNotEmptyObject(id)){
			con.setValueQuery(ConditionsKit.NOT_EQUAL, "id", id);
		}
		con.modelToCondition(new StageModel());
		sql += con.getSql();
		return StageModel.dao.find(sql,con.getParamList().toArray());
	}
	
	/**
	 * 获取学阶中编码的最大值
	 * @return
	 */
	public static String getMaxCode(){
		String sql = " select code from base_stage where id = (select  max(id) from base_stage )  ";
		StageModel model = StageModel.dao.findFirst(sql);
		if(CommonUtil.isEmpty(model)){
			return "";
		}else{
			return StageModel.dao.findFirst(sql).getStr("code");
		}
	}
	
	/**
	 * 根据ID删除学阶
	 * @param id
	 * @return
	 */
	public static boolean deleStageInfo(Integer id){
		return StageModel.dao.deleteById(id);
	}
	
}
