package com.dayang.demo.intf.webservices;

import java.util.List;

import com.dayang.demo.dao.dto.AreaDto;
import com.dayang.demo.model.Area;

/**
 * 类描述：WebService的测试类 接口
 * <pre>
 * -------------History------------------
 *   DATE                 AUTHOR         VERSION        DESCRIPTION
 *  2014年12月28日               张才胜               V01.00.001		      新增内容   
 * </pre>
 * 
 * @author <a href="mailto:zhangcs@dayanginfo.com">张才胜</a>
 */

public interface IFileMgrService {
	
	/**
	 * 查询文件
	 * @return
	 */
	public String queryFile();
	
	/**
	 * 查询地域信息
	 * @return
	 */
	public List<Area> queryArea();
	
	/**
	 * 区域分页查询
	 * @param areaDto
	 * @param page
	 * @param rows
	 * @return
	 */
	public List<AreaDto> queryArea4Page(AreaDto areaDto,int page,int rows);
}
