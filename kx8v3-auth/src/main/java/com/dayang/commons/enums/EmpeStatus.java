package com.dayang.commons.enums;

/**
 * 类描述：婚姻状况枚举类
 * <pre>
 * -------------History------------------
 *   DATE                 AUTHOR         VERSION        DESCRIPTION
 *  2015年05月22日            何意      V01.00.001		   新增内容
 * </pre>
 *
 * @author <a href="mailto:heyi@dayanginfo.com">何意</a>
 */
public enum EmpeStatus {

    ENABLE("在用",1),TOBEALLOCATED("待分配",8),DISABLED("禁用",9);
    private String name;
    private int value;
    private EmpeStatus(String name, int value){
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
