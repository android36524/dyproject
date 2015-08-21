package com.dayang.demo.intf.webservices.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dayang.demo.busi.IAreaBusi;
import com.dayang.demo.dao.dto.AreaDto;
import com.dayang.demo.intf.webservices.IFileMgrService;
import com.dayang.demo.model.Area;

/**
 * 类描述：WebService的测试类
 * <pre>
 * -------------History------------------
 *   DATE                 AUTHOR         VERSION        DESCRIPTION
 *  2014年12月28日               张才胜               V01.00.001		      新增内容   
 * </pre>
 * 
 * @author <a href="mailto:zhangcs@dayanginfo.com">张才胜</a>
 */

@Component(value="fileMgrService")
public class FileMgrService implements IFileMgrService {
	
	@Autowired
	private IAreaBusi areaBusi;
	
	@Override
	public String queryFile() {
		return "Rita,Glen";
	}
	
	/**
	 * 查询地域信息
	 * @return
	 */
	public List<Area> queryArea(){
		return areaBusi.queryAll();
	}
	
	/**
	 * 区域分页查询
	 * @param areaDto
	 * @param page
	 * @param rows
	 * @return
	 */
	public List<AreaDto> queryArea4Page(AreaDto areaDto,int page,int rows){
		return areaBusi.query(areaDto, page, rows);
	}
}
