package com.dayang.commons.enums;

public enum OrgLevelEnum {
	NATIONAL("全国",86),PROVINCE("省",8601),CITY("市",8602),AREA("区",8603),SCHOOL("学校",8604),COMPANY("公司",8600),CHILDAREA("区下级",8605);
    private String name;
    private int value;
    private OrgLevelEnum(String name,int value){
        this.name = name;
        this.value = value;
    }
    public String getName(){
         return this.name;
    }

    public int getValue() {
        return value;
    }
    public String getValueStr(){
        return this.value + "";
    }
}
