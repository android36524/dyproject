package com.dayang.demo.busi.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.dayang.demo.busi.IAreaBusi;
import com.dayang.demo.dao.IAreaDao;
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
@Controller
public class AreaBusi implements IAreaBusi{
	@Autowired
	private IAreaDao areaDao;
	
	public List<Area> queryAll(){
		return Area.dao.find("select * from demo_area");
	}
	
	public List<Area> queryAll4Cache(){
		return Area.dao.findByCache("dyBaseCache","key","select * from demo_area");
	}
	
	/**
	 * 区域分页查询
	 * @param areaDto
	 * @param page
	 * @param rows
	 * @return
	 */
	public List<AreaDto> query(AreaDto areaDto,int page,int rows){
		return areaDao.queryArea(areaDto, page, rows);
	}
}
