package com.dayang.demo.dao.impl;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.dayang.demo.dao.IAreaDao;
import com.dayang.demo.dao.dto.AreaDto;

/**
 * 类描述：地域管理的实现类
 * <pre>
 * -------------History------------------
 *   DATE                 AUTHOR         VERSION        DESCRIPTION
 *  2014年12月2日               张才胜               V01.00.001		      新增内容   
 * </pre>
 * 
 * @author <a href="mailto:zcslsf@gmail.com">张才胜</a>
 */

@Repository
public class AreaDao implements IAreaDao {
	@Autowired
	private SqlSession sqlSession;

	@Override
	public List<AreaDto> queryArea(AreaDto areaDto,int page,int rows) {
		return sqlSession.selectList("DEMO_AREA.QUERY", areaDto,new RowBounds(page, rows));
	}

}
