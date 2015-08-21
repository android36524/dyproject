package com.dayang.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import redis.clients.jedis.Jedis;

import com.alibaba.fastjson.JSONObject;
import com.dayang.cas.util.LoginInfoUtil;
import com.dayang.commons.ibatis.plugin.util.Page;
import com.dayang.commons.pojo.DataGridReturn;
import com.dayang.commons.util.CommonUtil;
import com.dayang.commons.util.JedisUtil;
import com.dayang.demo.busi.IAreaBusi;
import com.dayang.demo.dao.dto.AreaDto;
import com.dayang.demo.model.Area;
import com.dayang.demo.pojo.AreaPojo;
import com.dayang.demo.validator.LoginValidator;
import com.jfinal.aop.Before;
import com.jfinal.core.ActionKey;
import com.jfinal.core.Controller;
import com.jfinal.ext.plugin.redis.JedisKit;
import com.jfinal.plugin.spring.Inject.BY_TYPE;
import com.jfinal.plugin.spring.IocInterceptor;

/**
 * 类描述：jfinal demo 的例子
 * <pre>
 * -------------History------------------
 *   DATE                 AUTHOR         VERSION        DESCRIPTION
 *  2014年11月25日               张才胜               V01.00.001		      新增内容   
 * </pre>
 * 
 * @author <a href="mailto:zcslsf@gmail.com">张才胜</a>
 */

@Before(IocInterceptor.class)
public class DemoController extends Controller {
	public final Logger logger = Logger.getLogger(DemoController.class);
	@BY_TYPE
	private IAreaBusi areaBusi;
	
	@Before(LoginValidator.class)
	public void login(){
		renderJsp("index.jsp");
	}
	
	/**
	 * Demo命名空间的首页
	 * 可以通过demo/index和demo访问
	 */
	public void index(){
		renderText("JFinal 成功启动");
	}

	/**
	 * 测试的Action，自定义路径
	 */
	@ActionKey("/demo/test1")
	public void test(){
		Area area = Area.dao.findFirst("select * from demo_area where id=105");
		AreaPojo areaObj = new AreaPojo();
		
		renderJson(areaObj);
	}
	
	/**
	 * 异常测试
	 */
	public void test3()throws Exception{
		Thread.sleep(10000);
		throw new Exception();
	}
	
	public void test2(){
		logger.info("/demo/test2的Action方法开始执行...");
		renderText("您请求的参数a的值为："+getPara(0) + ",当前登录的用户信息为："+LoginInfoUtil.getUserInfoStr());
	}
	
	public void areaList(){
		renderJson(areaBusi.queryAll());
	}
	
	public void areaCacheList(){
		renderJson(areaBusi.queryAll4Cache());
	}
	

	/**
	 * Redis 缓存查询
	 */
	public void rdemo1(){
		List<Area> areaList = null;
		String areaStr = JedisUtil.get("areaList");
		if(CommonUtil.isEmpty(areaStr)){
			areaList = areaBusi.queryAll();

			JedisUtil.set("areaList", JSONObject.toJSONString(areaList));
		}else{
			areaList = JSONObject.parseObject(areaStr, new ArrayList<Area>().getClass());
		}
		renderJson(areaList);
	}
	
	/**
	 * 分页查询地域
	 * 请求DEMO:dyBase/demo/queryArea?page=1&area.id=202
	 */
	public void queryArea(){
		int page = getParaToInt("page",1);
		int rows = getParaToInt("rows",20);
		String a = getPara("a");
		AreaDto areaDto = getModel(AreaDto.class,"area");
		
		List<AreaDto> areaList = areaBusi.query(areaDto, page, rows);
		renderJson(new DataGridReturn(((Page<AreaDto>)areaList).getTotal(), areaList));
	}
	
	/**
	 * 分页查询地域,使用了模板引擎
	 * 请求DEMO:dyBase/demo/queryArea4Beetl?page=1&area.id=202
	 */
	public void queryArea4Beetl(){
		int page = getParaToInt("page",1);
		int rows = getParaToInt("rows",20);
		String a = getPara("a");
		AreaDto areaDto = getModel(AreaDto.class,"area");
		
		List<AreaDto> areaList = areaBusi.query(areaDto, page, rows);
		setAttr("areaList", areaList);
		render("/demo/user.html");
	}
	
	
	/**
	 * 跨域2.0请求的DEMO
	 * 请求地址：http://api.crossdomain.com.cn/oauth/authorize?grant_type=token&response_type=code&client_id=4567858441&redirect_url=http%3A%2F%2F10.0.0.80%3A8081%2FdyBase%2Fdemo%2Fauth2Demo
	 * 
	 */
	public void auth2Demo(){
		renderJson(getParaMap());
	}

}
