package com.dayang.conf.routes;

import com.dayang.commons.controller.AdminController;
import com.dayang.commons.controller.CommonController;
import com.dayang.commons.controller.OrgTreeController;
import com.dayang.system.controller.*;
import com.jfinal.config.Routes;

/**
 * 类描述：后端路由配置
 * <pre>
 * -------------History------------------
 *   DATE                 AUTHOR         VERSION        DESCRIPTION
 *  2014年11月26日               张才胜               V01.00.001		      新增内容   
 * </pre>
 * 
 * @author <a href="mailto:zcslsf@gmail.com">张才胜</a>
 */

public class AdminRoutes extends Routes {

	@Override
	public void config() {
		this.add("/admin",AdminController.class);
		
		//学阶管理
		this.add("/admin/stage",StageController.class);
		this.add("/admin/dept",DeptController.class);
		this.add("/admin/subject",SubjectController.class);
		this.add("/admin/edb", EdbController.class);
		this.add("/admin/grade", GradeController.class);
		this.add("/admin/division", DivisionController.class);
		
		//知识点管理
		this.add("/admin/lore",LoreController.class);
		this.add("/admin/section",SectionController.class);
		this.add("/admin/press", PressController.class);
		this.add("/admin/dictionary",DictionaryController.class);
		this.add("/admin/empe",EmpeEdbController.class);
		this.add("/admin/semester",SemesterController.class);
		this.add("/admin/schBookVer",SchBookVerController.class);
		this.add("/admin/schoolBook",SchoolBookController.class);
		this.add("/admin/school",SchoolController.class);
		this.add("/admin/stuParent",StuParentController.class);
		this.add("/admin/class",ClassController.class);
		this.add("/admin/orgTree", OrgTreeController.class);
		this.add("/admin/altenation",AltenationController.class);
		this.add("/admin/stuAltenation",StuAltenationController.class);
		this.add("/admin/student",StudentController.class);
		this.add("/admin/schoolEmpe",SchoolEmpeController.class);
		this.add("/admin/approveFlow",ApproveFlowController.class);
		this.add("/admin/approveFlowStu",ApproveFlowController.class);
		this.add("/admin/logInfo",LogInfoController.class);
		this.add("/admin/schedule",ScheduleController.class);
		this.add("/admin/classTeacher",ClassTeacherController.class);
		this.add("/common/", CommonController.class);
		
		//公司管理
		this.add("/admin/companyDetail",CompanyController.class);
		//公司员工管理
		this.add("/admin/companyEmp",companyEmpController.class);
	}

}
