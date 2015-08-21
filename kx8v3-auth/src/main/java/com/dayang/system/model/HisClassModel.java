package com.dayang.system.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dayang.commons.pojo.QueryDefineParaPojo;
import com.dayang.commons.util.CommonUtil;
import com.jfinal.plugin.activerecord.Model;

/**
 * 类描述：学校班级历史信息
 * <pre>
 * -------------History------------------
 *   DATE                 AUTHOR         VERSION        DESCRIPTION
 *  2015年05月26日            张维      V01.00.001		   新增内容
 * </pre>
 *
 * @author <a href="mailto:zhangwei@dayanginfo.com">张维</a>
 */
public class HisClassModel extends Model<HisClassModel>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	 public static final Map<String, QueryDefineParaPojo> DefineMap= new HashMap<String,QueryDefineParaPojo>(){

		  private static final long serialVersionUID = 1L;

			{
				
			}
		};
	public static final HisClassModel dao = new HisClassModel();
	
	
	/**
	 * 查询历史班级信息
	 * @param param
	 * @return
	 */
	public List<HisClassModel> findHisClassModel(Map<String,Object> param) {
		StringBuilder sb = new StringBuilder("select * from his_class where 1=1 ");
		final List<Object> parameters = new ArrayList<Object>(); 
		CommonUtil.setDefaultPara(param,sb,parameters,"",DefineMap);	
		return HisClassModel.dao.find(sb.toString(),parameters.toArray());
	}

}
