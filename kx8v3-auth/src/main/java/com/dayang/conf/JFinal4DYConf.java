package com.dayang.conf;

import com.dayang.system.model.*;

import org.apache.log4j.Logger;
import org.beetl.core.GroupTemplate;
import org.beetl.ext.jfinal.BeetlRenderFactory;
import org.snaker.jfinal.plugin.SnakerPlugin;

import com.dayang.commons.beetl.DYBeetlRenderFactory;
import com.dayang.commons.controller.CommonController;
import com.dayang.commons.util.CommonUtil;
import com.dayang.commons.util.SpringInit;
import com.dayang.conf.handler.SessionHandler;
import com.dayang.conf.handler.WebServicesSkipHander;
import com.dayang.conf.routes.AdminRoutes;
import com.dayang.conf.routes.FrontRoutes;
import com.dayang.demo.controller.DemoController;
import com.dayang.demo.controller.FileUploadController;
import com.dayang.demo.interceptor.DemoInterceptor;
import com.dayang.demo.model.Area;
import com.dayang.system.model.DeptModel;
import com.dayang.system.model.DictionaryModel;
import com.dayang.system.model.DictionaryTypeModel;
import com.dayang.system.model.DivisionModel;
import com.dayang.system.model.OrgModel;
import com.dayang.system.model.EmpeModel;
import com.dayang.system.model.GradeModel;
import com.dayang.system.model.GradeSubjectModel;
import com.dayang.system.model.HisDeptModel;
import com.dayang.system.model.LoreModel;
import com.dayang.system.model.PressModel;
import com.dayang.system.model.SchoolBookModel;
import com.dayang.system.model.SchBookVerModel;
import com.dayang.system.model.SemesterModel;
import com.dayang.system.model.StageModel;
import com.dayang.system.model.SubjectModel;
import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.activerecord.cache.EhCache;
import com.jfinal.plugin.activerecord.tx.TxByRegex;
import com.jfinal.plugin.c3p0.C3p0Plugin;
import com.jfinal.plugin.ehcache.EhCachePlugin;
import com.jfinal.plugin.spring.SpringPlugin;
import com.jfinal.render.ViewType;

/**
 * 类描述：jFinal配置
 * <pre>
 * -------------History------------------
 *   DATE                 AUTHOR         VERSION        DESCRIPTION
 *  2014年11月25日               张才胜               V01.00.001		      新增内容   
 * </pre>
 * 
 * @author <a href="mailto:zcslsf@gmail.com">张才胜</a>
 */

public class JFinal4DYConf extends JFinalConfig{
	public final Logger logger = Logger.getLogger(JFinal4DYConf.class);
	
	@Override
	public void configConstant(Constants me) {
		loadPropertyFile("jfinal.properties");
		//配置beetl模板引擎
		me.setMainRenderFactory(new DYBeetlRenderFactory());
		GroupTemplate groupTemplate = BeetlRenderFactory.groupTemplate;

		//配置错误页面
		me.setError404View("/error/error-404.html");
		me.setError500View("/error/error-500.html");
		
		//设定开发者模式
		me.setDevMode(getPropertyToBoolean("jfinal.devModel"));
		//配置视图
		me.setViewType(ViewType.JSP);
		//配置编码
		me.setEncoding("UTF-8");
		
		//设定文件的上传目录
		me.setUploadedFileSaveDirectory(CommonUtil.getAppProperties("upload.path"));
	}

	@Override
	public void configRoute(Routes me) {
		me.add("/demo", DemoController.class);
		me.add("/demoFile",FileUploadController.class);
		
		//公共的Controller
		me.add("/common",CommonController.class);
		
		//前端路由
		me.add(new FrontRoutes());
		//后端路由
		me.add(new AdminRoutes());
	}

	@Override
	public void configPlugin(Plugins me) {
		//配置jfinal与Spring整合
		logger.info("开始配置spring和jfinal的集成");
		me.add(new SpringPlugin(SpringInit.getApplicationContext()));
		
		//配置Ehcache缓存插件
		me.add(new EhCachePlugin());
		
		//配置Redis缓存插件
//		me.add(new JedisPlugin("10.0.0.22",6379));
		
		//配置数据源插件
		String url = CommonUtil.getAppProperties("url").trim();
		String user = CommonUtil.getAppProperties("user").trim();
		String pwd = CommonUtil.getAppProperties("pwd").trim();
		String driver = CommonUtil.getAppProperties("driver").trim();
		
		C3p0Plugin c3p0Plugin = new C3p0Plugin(url,user, pwd, driver);
		me.add(c3p0Plugin);
		
		//配置ActiveRecord
		ActiveRecordPlugin arp = new ActiveRecordPlugin(c3p0Plugin);
		arp.setCache(new EhCache());
		arp.setShowSql(true);
		me.add(arp);
		
		//配置snaker工作流
		SnakerPlugin snakerPlugin = new SnakerPlugin(c3p0Plugin,null);
		me.add(snakerPlugin);
		
		//配置实体类
		arp.addMapping("demo_area", Area.class);
		arp.addMapping("base_department", "id",DeptModel.class);
		arp.addMapping("base_stage", "id",StageModel.class);
		arp.addMapping("base_subject", "id",SubjectModel.class);
		arp.addMapping("base_organization", OrgModel.class);
		arp.addMapping("base_grade","id", GradeModel.class);
		arp.addMapping("base_semester", SemesterModel.class);
		arp.addMapping("base_press","id", PressModel.class);
		arp.addMapping("base_schoolbook","id", SchoolBookModel.class);
		arp.addMapping("base_schBookVer","id", SchBookVerModel.class);
		arp.addMapping("base_section","id", SectionModel.class);
		arp.addMapping("base_division", DivisionModel.class);
		arp.addMapping("base_employee", EmpeModel.class);
		arp.addMapping("base_lore", LoreModel.class);
		arp.addMapping("base_educationinfo", EduInfoModel.class);
		arp.addMapping("base_schoolinfo", SchoolExtModel.class);
		arp.addMapping("base_parent","ID", StuParentModel.class);
		arp.addMapping("base_student", StudentModel.class);
		arp.addMapping("base_student_expand", StudentInfoModel.class);
		arp.addMapping("base_class", ClassModel.class);
		arp.addMapping("base_teacherinfo", TeachInfoModel.class);
		arp.addMapping("his_organization", HisorgModel.class);
		arp.addMapping("his_department", HisDeptModel.class);
		arp.addMapping("his_employee", HisEmpeModel.class);
		arp.addMapping("his_organization", HisSchoolModel.class);
		arp.addMapping("log_change", AlterModel.class);		
		arp.addMapping("log_change", AlterModel.class);
		arp.addMapping("sys_dictionary", "id",DictionaryModel.class);
		arp.addMapping("sys_dictionarytype", "id",DictionaryTypeModel.class);
		arp.addMapping("r_section_lore", SectionLoreModel.class);		
		arp.addMapping("r_gradesubject","id", GradeSubjectModel.class);
		arp.addMapping("r_student_info", RstuAndParentModel.class);
		arp.addMapping("his_class", HisClassModel.class);
		arp.addMapping("sys_account", "id",UserModel.class);

		arp.addMapping("his_student_info", "id",HisStudentInfoModel.class);
		arp.addMapping("his_student", "id",HisStudentModel.class);
		arp.addMapping("base_student_expand", "id",StudentExtModel.class);
		arp.addMapping("cfg_schupgrade", "id",SchUpgradeModel.class);
		arp.addMapping("wfl_approveitem", "id",ApproveItemModle.class);
		arp.addMapping("wfl_approveflow", "id",ApproveFlowModel.class);
		arp.addMapping("imp_loginfo", LoginfoModel.class);
		arp.addMapping("log_highgrade", LogGradHigModel.class);
		arp.addMapping("cfg_sectionclass", CfgSectionModel.class);
		arp.addMapping("cfg_week", CfgWeekModel.class);
		arp.addMapping("r_classteacherinfo",ClassTeacherNameModel.class);
		arp.addMapping("busi_schedule",ScheduleModel.class);
		arp.addMapping("busi_scheduledetail",ScheduleDetailModel.class);
	}

	@Override
	public void configInterceptor(Interceptors me) {
		//配置拦截器
		//配置demo拦截器
		me.add(new DemoInterceptor());
		
		//配置事务拦截器
		me.add(new TxByRegex(".*(save|update|add|del|insert|modify|remove).*"));
	}
	
	@Override
	public void configHandler(Handlers me) {
		logger.info("开始配置Jfinal的Handler....");
		//配置webservice skip
		me.add(new WebServicesSkipHander());
		me.add(new SessionHandler());
	}
	
	@Override
	public void afterJFinalStart() {
		super.afterJFinalStart();
		//web启动后执行的事项...
		logger.info("Web工程启动完成，开始初始化相应的数据....");
	}
	
	@Override
	public void beforeJFinalStop() {
		super.beforeJFinalStop();
		logger.info("Web工程已经停止，开始执行停止后的相关事项....");
	}

}
