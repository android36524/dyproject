package com.dayang.commons.enums;

/**
 * 类描述：教材上下册枚举类
 * <pre>
 * -------------History------------------
 *   DATE                 AUTHOR         VERSION        DESCRIPTION
 *  2015年06月08日            李勇      V01.00.001		   新增内容
 * </pre>
 *
 * @author <a href="mailto:liyong@dayanginfo.com">李勇</a>
 */
public enum SchoolBookVolume {
    NOVOLUME("不分册",100),FIRESTVOLUME("上册",101),SECONDVOLUME("下册",102);
    private String name;
    private int value;
    private SchoolBookVolume(String name,int value){
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
