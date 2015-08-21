package com.dayang.demo.dao.dto;

import com.dayang.commons.util.IDKeyUtil;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONObject;

/**
 * 类描述：地域的实体类
 * 
 * <pre>
 * -------------History------------------
 *   DATE                 AUTHOR         VERSION        DESCRIPTION
 *  2014年12月2日               张才胜               V01.00.001		      新增内容
 * </pre>
 * 
 * @author <a href="mailto:zcslsf@gmail.com">张才胜</a>
 */

public class AreaDto {

	/** 地域编码 */
	private int id;

	/** 地域名称 */
	private String name;

	/** 父级地域编号 */
	private int reid;

	/** 排序 */
	private int disorder;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getReid() {
		return reid;
	}

	public void setReid(int reid) {
		this.reid = reid;
	}

	public int getDisorder() {
		return disorder;
	}

	public void setDisorder(int disorder) {
		this.disorder = disorder;
	}

	public static void  main(String[]strs) throws UnirestException {

	}
}
