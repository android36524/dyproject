package com.dayang.commons.pojo;

/**
 * 
 * 类描述：查询字段定义
 * <pre>
 * -------------History------------------
 *   DATE       AUTHOR       VERSION        DESCRIPTION
 *  2015-5-29      温建军             V01.00.001		      新增内容   
 * </pre>
 * 
 * @author <a href="mailto:wenjj@dayanginfo.com">温建军</a>
 */
public class QueryDefineParaPojo {
	private String key;                                // Map中的key
	private String field;                              // Model 中的字段
	private String operator;                           // 操作符号，如 "="，"like"，">"   
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public String getField() {
		return field;
	}
	public void setField(String field) {
		this.field = field;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	
	public QueryDefineParaPojo(String key,String field,String operator){
		this.key =key;
		this.field = field;
		this.operator=operator;
	}
}
