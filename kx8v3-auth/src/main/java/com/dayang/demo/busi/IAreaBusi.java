package com.dayang.demo.busi;

import java.util.List;

import com.dayang.demo.dao.dto.AreaDto;
import com.dayang.demo.model.Area;

/**
 * 类描述：Area相关的业务类
 * <pre>
 * -------------History------------------
 *   DATE                 AUTHOR         VERSION        DESCRIPTION
 *  2014年12月1日               张才胜               V01.00.001		      新增内容   
 * </pre>
 * 
 * @author <a href="mailto:zcslsf@gmail.com">张才胜</a>
 */

public interface IAreaBusi {
	
	/**
	 * 查询所有的区域
	 * @return
	 */
	public List<Area> queryAll();
	
	/**
	 * 以缓存的形式查询所有的区域
	 * @return
	 */
	public List<Area> queryAll4Cache();
	
	/**
	 * 区域分页查询
	 * @param areaDto
	 * @param page
	 * @param rows
	 * @return
	 */
	public List<AreaDto> query(AreaDto areaDto,int page,int rows);
}
