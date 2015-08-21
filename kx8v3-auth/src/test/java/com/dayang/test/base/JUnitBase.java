package com.dayang.test.base;

import org.apache.log4j.Logger;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.dayang.conf.JFinal4DYConf;
import com.jfinal.ext.test.ControllerTestCase;

/**
 * 类描述：Junit测试的基类
 * <pre>
 * -------------History------------------
 *   DATE                 AUTHOR         VERSION        DESCRIPTION
 *  2014年8月18日               张才胜               V01.00.001		      新增内容   
 * </pre>
 * 
 * @author <a href="mailto:glen.zhang@mail.netide.net">张才胜</a>
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"file:src/main/webapp/WEB-INF/spring-*.xml"})
public class JUnitBase extends ControllerTestCase<JFinal4DYConf>{
	protected Logger logger = Logger.getLogger(JUnitBase.class);
	
}
