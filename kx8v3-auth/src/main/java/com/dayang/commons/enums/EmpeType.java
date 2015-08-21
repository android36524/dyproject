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
public enum EmpeType {
    EDBEMPE("教育局员工",1),COMPANYEMP("公司员工",3),AGENTEMP("代理商",5);
    private String name;
    private int value;
    private EmpeType(String name, int value){
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
