package com.dayang.commons.enums;
/**
 * 类描述：家属关系类型
 * <pre>
 * -------------History------------------
 *   DATE                 AUTHOR         VERSION        DESCRIPTION
 *  2015年5月25日下午5:43:40        刘生慧              		 V01.00.001		 新增内容   
 * </pre>
 * 
 * @author <a href="liush@dayanginfo.com">刘生慧</a>
 */
public enum GuardianType {
		GUARDER("是",1),NOTGUARDER("否",2);
	    private String name;
	    private int value;
	    private GuardianType(String name, int value){
	        this.name = name;
	        this.value = value;
	    }
	    public String getName(){
	        return this.name;
	    }
	    public int getValue(){
	        return this.value;
	    }
	    public String getValueStr(){
	        return this.value + "";
	    }
}
