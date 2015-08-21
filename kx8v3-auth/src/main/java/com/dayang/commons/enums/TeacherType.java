package com.dayang.commons.enums;

/**
 * 类描述：老师类型枚举类
 * <pre>
 * -------------History------------------
 *   DATE                 AUTHOR         VERSION        DESCRIPTION
 *  2015年05月25日           张维      V01.00.001		   新增内容
 * </pre>
 *
 * @author <a href="mailto:zhangwei@dayanginfo.com">张维</a>
 */
public enum TeacherType {
	  HEADTEACHER("班主任",1),TEACHER("任课老师",2);
	    private String name;
	    private int value;
	    private TeacherType(String name, int value){
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
