package com.dayang.conf.interceptor;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.dayang.cas.pojo.AccountPojo;
import com.dayang.cas.util.LoginInfoUtil;
import com.dayang.commons.busi.impl.MenuBusiImpl;
import com.dayang.commons.pojo.InterfaceCfgPojo;
import com.dayang.commons.pojo.InterfaceRet;
import com.dayang.commons.util.CommonStaticData;
import com.dayang.commons.util.CommonUtil;
import com.dayang.commons.util.DYInterFaceKeyData;
import com.dayang.commons.util.DaYangCommonUtil;
import com.dayang.commons.util.DaYangStaticData;
import com.dayang.commons.util.StaticData;
import com.dayang.system.model.OrgModel;
import com.dayang.system.model.UserModel;
import com.jfinal.aop.Interceptor;
import com.jfinal.core.ActionInvocation;

/**
 * 类描述：后台菜单的拦截器
 * <pre>
 * -------------History------------------
 *   DATE                 AUTHOR         VERSION        DESCRIPTION
 *  2015-7-9               张才胜               V01.00.001		      新增内容   
 * </pre>
 * 
 * @author <a href="mailto:zhangcs@dayanginfo.com">张才胜</a>
 */

public class MenuInterceptor implements Interceptor {
	private static final Logger logger = Logger.getLogger(MenuInterceptor.class);
	
	private MenuBusiImpl menuBusi = new MenuBusiImpl();
	
	@Override
	public void intercept(ActionInvocation ai) {
		ai.invoke();
		logger.info("开始调用后台菜单拦截器");
		HttpSession session = ai.getController().getSession();
		Object menuStr = session.getAttribute(DaYangStaticData.SESSION_KEY_MENU);
		if(CommonUtil.isEmpty(menuStr)){
			AccountPojo accountPojo = LoginInfoUtil.getAccountInfo();
			InterfaceCfgPojo interfaceCfgPojo = DaYangCommonUtil.getInterFaceCfgFromRedis(DYInterFaceKeyData.MENUKEY);
			Map<String,Object> params = new HashMap<>();
			params.put("accountId",accountPojo.getId());
			params.put("systemId", CommonStaticData.SystemDefine.BaseDataSystem + "");
			String retString = DaYangCommonUtil.requestInterFace(interfaceCfgPojo, params);
			InterfaceRet interfaceRet = JSON.parseObject(retString, InterfaceRet.class);
			menuStr = menuBusi.generateMenuScript(interfaceRet);
			session.setAttribute(DaYangStaticData.SESSION_KEY_MENU,menuStr);
			
			session.setAttribute(DaYangStaticData.LOGIN_KEY__CURRENTUSER, accountPojo);
			session.setAttribute(DaYangStaticData.LOGIN_KEY__CURRENTUSERID, accountPojo.getId());
			
			UserModel userModel = new UserModel();
			userModel.set("orgId", accountPojo.getOrgId());
			OrgModel m =  userModel.getOrg();
			session.setAttribute(DaYangStaticData.LOGIN_KEY__CURRENTORG, m);
			session.setAttribute(DaYangStaticData.LOGIN_KEY__CURRENTORG_JSON, m.toJson());
		}
	}

}
