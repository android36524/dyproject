package com.dayang.commons.enums;

/**
 * 类描述：
 * <pre>
 * -------------History------------------
 *   DATE                 AUTHOR         VERSION        DESCRIPTION
 *  2015年05月22日            何意      V01.00.001		   新增内容
 * </pre>
 *
 * @author <a href="mailto:heyi@dayanginfo.com">何意</a>
 */
public enum EmpeTypeSch {
    SCHEMP("学校员工",2),TEACHEREMP("学校教师",4);
    private String name;
    private int value;
    private EmpeTypeSch(String name, int value){
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
