package com.dayang.demo.dao;

import java.util.List;

import com.dayang.demo.dao.dto.AreaDto;

/**
 * 类描述：地域处理的DAO
 * <pre>
 * -------------History------------------
 *   DATE                 AUTHOR         VERSION        DESCRIPTION
 *  2014年12月2日               张才胜               V01.00.001		      新增内容   
 * </pre>
 * 
 * @author <a href="mailto:zcslsf@gmail.com">张才胜</a>
 */

public interface IAreaDao {
	
	/**
	 * 查询区域
	 * @param areaDto
	 * @param page
	 * @param rows
	 * @return
	 */
	public List<AreaDto> queryArea(AreaDto areaDto,int page,int rows);
}
