package com.dayang.commons.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * 类描述：学校类型对应年级CODE定义
 * <pre>
 * -------------History------------------
 *   DATE                 AUTHOR         VERSION        DESCRIPTION
 *  2015年06月17日           张维      V01.00.001		   新增内容
 * </pre>
 *
 * @author <a href="mailto:zhangwei@dayanginfo.com">张维</a>
 */
public class SchoolType {

	
	// 小学
	public static final Map<String, String> XX = new HashMap<String, String>();
	// 初中
	public static final Map<String, String> CZ = new HashMap<String, String>();
	// 九年一贯制
	public static final Map<String, String> JNYGZ = new HashMap<String, String>();
	// 完全中学
	public static final Map<String, String> WQZX = new HashMap<String, String>();
	// 高中
	public static final Map<String, String> GZ = new HashMap<String, String>();
	// 十二年一贯制
	public static final Map<String, String> SEYGZ = new HashMap<String, String>();
	//学校类型字典值及年级范围的映射
	public static final Map<String, Map<String, String>> SCHTYPE_DICT =  new HashMap<String, Map<String, String>>();

	static {
		XX.put("NJ01", "NJ01");
		XX.put("NJ02", "NJ02");
		XX.put("NJ03", "NJ03");
		XX.put("NJ04", "NJ04");
		XX.put("NJ05", "NJ05");
		XX.put("NJ06", "NJ06");
		
		CZ.put("NJ07", "NJ07");
		CZ.put("NJ08", "NJ08");
		CZ.put("NJ09", "NJ09");
		
		JNYGZ.put("NJ01", "NJ01");
		JNYGZ.put("NJ02", "NJ02");
		JNYGZ.put("NJ03", "NJ03");
		JNYGZ.put("NJ04", "NJ04");
		JNYGZ.put("NJ05", "NJ05");
		JNYGZ.put("NJ06", "NJ06");
		JNYGZ.put("NJ07", "NJ07");
		JNYGZ.put("NJ08", "NJ08");
		JNYGZ.put("NJ09", "NJ09");
		
		WQZX.put("NJ07", "NJ07");
		WQZX.put("NJ08", "NJ08");
		WQZX.put("NJ09", "NJ09");
		WQZX.put("NJ10", "NJ10");
		WQZX.put("NJ11", "NJ11");
		WQZX.put("NJ12", "NJ12");
		
		GZ.put("NJ10", "NJ10");
		GZ.put("NJ11", "NJ11");
		GZ.put("NJ12", "NJ12");
		
		SEYGZ.put("NJ01", "NJ01");
		SEYGZ.put("NJ02", "NJ02");
		SEYGZ.put("NJ03", "NJ03");
		SEYGZ.put("NJ04", "NJ04");
		SEYGZ.put("NJ05", "NJ05");
		SEYGZ.put("NJ06", "NJ06");
		SEYGZ.put("NJ07", "NJ07");
		SEYGZ.put("NJ08", "NJ08");
		SEYGZ.put("NJ09", "NJ09");
		SEYGZ.put("NJ10", "NJ10");
		SEYGZ.put("NJ11", "NJ11");
		SEYGZ.put("NJ12", "NJ12");
		
		SCHTYPE_DICT.put("211", XX);
		SCHTYPE_DICT.put("218", XX);
		SCHTYPE_DICT.put("219", XX);
		SCHTYPE_DICT.put("221", XX);
		SCHTYPE_DICT.put("222", XX);
		SCHTYPE_DICT.put("228", XX);
		SCHTYPE_DICT.put("229", XX);
		
		SCHTYPE_DICT.put("311", CZ);
		SCHTYPE_DICT.put("312", JNYGZ);
		SCHTYPE_DICT.put("319", CZ);
		SCHTYPE_DICT.put("321", CZ);
		SCHTYPE_DICT.put("329", CZ);
		SCHTYPE_DICT.put("331", CZ);
		SCHTYPE_DICT.put("332", CZ);
		
		SCHTYPE_DICT.put("341", WQZX);
		SCHTYPE_DICT.put("342", GZ);
		SCHTYPE_DICT.put("345", SEYGZ);
		SCHTYPE_DICT.put("349", GZ);
		SCHTYPE_DICT.put("351", GZ);
		SCHTYPE_DICT.put("352", GZ);
	}
	
}
