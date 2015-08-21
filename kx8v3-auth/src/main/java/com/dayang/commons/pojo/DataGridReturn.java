package com.dayang.commons.pojo;

import java.util.List;

import org.apache.commons.lang3.math.NumberUtils;


/**
 * 类描述：后台向前台返回JSON，用于easyui的datagrid
 * <pre>
 * -------------History------------------
 *   DATE                 AUTHOR         VERSION        DESCRIPTION
 *  2014年12月2日               张才胜               V01.00.001		      新增内容   
 * </pre>
 * 
 * @author <a href="mailto:zcslsf@gmail.com">张才胜</a>
 */
public class DataGridReturn {

	public DataGridReturn(Integer total, List rows) {
		this.total = total;
		this.rows = rows;
	}
	
	public DataGridReturn(Long total, List rows) {
		this.total = total.intValue();
		this.rows = rows;
	}
	
	public DataGridReturn(String total, List rows) {
		this.total = NumberUtils.toInt(total,0);
		this.rows = rows;
	}
	
	public DataGridReturn(Object total, List rows) {
		this.total = NumberUtils.toInt(total.toString(),0);
		this.rows = rows;
	}

	private Integer total;// 总记录数
	private List rows;// 每行记录
	private List footer;

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public List getRows() {
		return rows;
	}

	public void setRows(List rows) {
		this.rows = rows;
	}

	public List getFooter() {
		return footer;
	}

	public void setFooter(List footer) {
		this.footer = footer;
	}

}
