package com.dayang.commons.pojo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dayang.commons.util.CommonUtil;
import com.jfinal.kit.PathKit;

/**
 * 类描述：学生信息导出实体类
 * <pre>
 * -------------History------------------
 *   DATE                 AUTHOR         VERSION        DESCRIPTION
 *  2015年7月1日               吴杰东                           V01.00.001		   
 * </pre>
 *
 * @author <a href="mailto:wujd@dayanginfo.com">吴杰东</a>
 */
public class StuParentExportPojo extends ImportPojo {

	 private static final Map<Integer,String> relation = new HashMap<>();
	    static {
	        relation.put(0,"name");
	        relation.put(1,"sex");
	        relation.put(2,"telphone");
	        relation.put(3,"company");
	        relation.put(4,"officeAddr");
	        relation.put(5,"officePhone");
	        relation.put(6,"duties");
	        relation.put(7,"political");
	        relation.put(8,"email");
	        relation.put(9,"zipCode");
	        relation.put(10,"addr");
	        relation.put(11,"orgName");
	        relation.put(12,"gradeName");
	        relation.put(13,"className");
	        relation.put(14,"stuName");
	    //    relation.put(15,"stuSex");
	        relation.put(15,"relationType");
	        relation.put(16,"guardian");
	    }
	@Override
	public Map<Integer, String> getMap() {
		// TODO Auto-generated method stub
		return relation;
	}

	@Override
	public Map<String, List<Object>> getSql() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getTemplatePath() {
		// TODO Auto-generated method stub
		return PathKit.getWebRootPath()+ CommonUtil.getAppProperties("stuParentExcelPath");
	}

	
}
