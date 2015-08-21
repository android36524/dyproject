package com.dayang.commons.enums;

/**
 * 类描述：权限标识枚举
 * <pre>
 * -------------History------------------
 *   DATE                 AUTHOR         VERSION        DESCRIPTION
 *  2015年05月18日            刘建宇      V01.00.001		   新增内容
 * </pre>
 *
 * @author <a href="mailto:liujy@dayanginfo.com">刘建宇</a>
 */
public enum AuthType {
    MENUTYPE("菜单权限",1),BUTTONTYPE("按钮权限",2);
    private String name;
    private int value;
    private AuthType(String name, int value){
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
