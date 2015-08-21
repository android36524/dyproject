package com.dayang.commons.pojo;

import com.dayang.commons.util.StaticData;

public class FieldDefinePojo {
	private String name;
	private String fieldName;
	private int dataType;
	public FieldDefinePojo(String name,String fieldName,int dataType){
		this.name = name;
		this.fieldName=fieldName;
		this.dataType=dataType;
	}
	public FieldDefinePojo(String name,String fieldName){
		this(name,fieldName,StaticData.FieldDataType.normal);
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	public int getDataType() {
		return dataType;
	}
	public void setDataType(int dataType) {
		this.dataType = dataType;
	}	
}
