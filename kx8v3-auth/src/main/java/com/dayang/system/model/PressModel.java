package com.dayang.system.model;

import java.util.ArrayList;
import java.util.List;

import com.dayang.commons.jfinal.kit.ConditionsKit;
import com.dayang.commons.util.CommonUtil;
import com.dayang.commons.util.StaticData;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;


/**
 * 
 * 类描述：教科书管理
 * <pre>
 * -------------History------------------
 *   DATE       AUTHOR       VERSION        DESCRIPTION
 *  2015-5-19      李中杰               V01.00.001		      新增内容   
 * </pre>
 * 
 * @author <a href="mailto:lizj@dayanginfo.com">李中杰</a>
 */
public class PressModel extends Model<PressModel>{

    private static final long serialVersionUID = 1L;
	
    public static final PressModel dao = new PressModel();
    
    private static int suffix;//编码后缀值
    
    /**
     * 分页查询出版社
     * 
     * @param @param pageNumber
     * @param @param pageSize
     * @param @param stageName
     * @param @return    设定文件 
     * @return Page<PressModel>    返回类型 
     * @throws
     */
	public static Page<PressModel> findPress(int pageNumber,int pageSize,String pressName){
		 StringBuffer sql = new StringBuffer(" from (SELECT @rownum:=0) r, base_press s LEFT JOIN sys_account b on s.creator=b.id where 1=1 ");
	        final List<Object> parameters = new ArrayList<Object>();
	        String selSql = "select @rownum:=@rownum+1 AS rownum, s.*,b.name as creatorName ";
	        if(!CommonUtil.isEmpty(pressName)){
	            sql.append(" and s.name like ? ");
	            parameters.add("%"+pressName+"%");
	        }
	        sql.append(" order by code ");
	        return PressModel.dao.paginate(pageNumber, pageSize, selSql, sql.toString(), parameters.toArray());
	}
	
	
	/**
	 * 出版社编号
	 * 
	 * @param @return    设定文件 
	 * @return String    返回类型 
	 * @throws
	 */
	public static String getMaxCode(){
		String sql = "SELECT code FROM base_press ORDER BY code desc";
		String code = PressModel.dao.findFirst(sql).getStr("code");
		String codePrefix = "";
		String codeSuffix = "";
		if(CommonUtil.isEmpty(code)){
			code = StaticData.PRESSCODE_INIT_VALUE;
		}else{
			codePrefix = code.substring(0,3);
			codeSuffix = code.substring(3,code.length());
			suffix = Integer.parseInt(codeSuffix)+1;
			if(suffix <= 9){
				codeSuffix = "0"+suffix;
				codePrefix = codePrefix + codeSuffix;
			}else{
				codePrefix = codePrefix + suffix;
			}
		}
		return codePrefix;
	}
	
	
	public static List<PressModel> findPressAll(){
		String sql = "select * from base_press order by convert(name using gbk) asc";
		return PressModel.dao.find(sql);
	}
	
	
	/**
	 * 根据名称查询出版社
	 * @param name
	 * @return
	 */
	public static List<PressModel> findPressByName(String name,Integer id){
		ConditionsKit con = new ConditionsKit();
		String sql = " select * from base_press where  1=1 ";
		if(CommonUtil.isNotEmptyString(name)){
			con.setValueQuery(ConditionsKit.EQUAL, "name", name);
		}
		if(CommonUtil.isNotEmptyObject(id)){
			con.setValueQuery(ConditionsKit.NOT_EQUAL, "id", id);
		}
		con.modelToCondition(new PressModel());
		sql += con.getSql();
		return PressModel.dao.find(sql,con.getParamList().toArray());
	}
	
}
