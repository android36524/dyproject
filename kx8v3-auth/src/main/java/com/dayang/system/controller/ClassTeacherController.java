package com.dayang.system.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.dayang.commons.annotation.FuncActionAnnotation;
import com.dayang.commons.controller.AdminBaseController;
import com.dayang.commons.controller.CURDController;
import com.dayang.commons.enums.EnumAll;
import com.dayang.commons.pojo.AjaxRetPojo;
import com.dayang.commons.pojo.JQGridPagePojo;
import com.dayang.commons.util.DaYangStaticData;
import com.dayang.commons.util.IDKeyUtil;
import com.dayang.conf.interceptor.AuthInterceptor;
import com.dayang.conf.interceptor.FuncActionInterceptor;
import com.dayang.system.model.ClassModel;
import com.dayang.system.model.ClassTeacherNameModel;
import com.dayang.system.model.GradeModel;
import com.dayang.system.model.SemesterModel;
import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.Page;

/**
 * 类描述：教师任课管理controller
 * <pre>
 * -------------History------------------
 *   DATE                 AUTHOR         VERSION        DESCRIPTION
 *  2015年06月13日            刘建宇      V01.00.001		   新增内容
 * </pre>
 *
 * @author <a href="mailto:liujy@dayanginfo.com">刘建宇</a>
 */
public class ClassTeacherController extends AdminBaseController implements CURDController{

    @Override
    @Before(AuthInterceptor.class)
    public void index() {
        renderJsp("index.jsp");
    }

    /**
     * 列表初始化
     */
    public void listPage(){
        int pageNumber = getParaToInt("page");
        int pageSize = getParaToInt("rows", DaYangStaticData.PAGE_ROWS);
        String orgId = getPara("orgId");
//        String semesterId = getPara("semesterId");
        String gradeId = getPara("gradeId");
        String classId = getPara("classId");
        Map<String,Object> params = new HashMap<>();
        params.put("schId",orgId);
        params.put("gradeId",gradeId);
        params.put("id",classId);
        Page<ClassModel> result  = ClassTeacherNameModel.dao.findByParams(pageNumber,pageSize,params);
        renderJson(JQGridPagePojo.parsePageData(result));
    }

    /**
     * 根据学校id查询当前学期
     */
    public void findCurSemester(){
        String schId =  getPara("schId");
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("isCur", EnumAll.IsYesOrNot.IsYes.getValueStr());
        paramMap.put("schId",schId);
        SemesterModel semesterModel = SemesterModel.dao.findSemester(paramMap);
        List<SemesterModel> result = new ArrayList<>();
        result.add(semesterModel);
        renderJson(result);
    }

    /**
     * 根据学校查询年级
     */
    public void findGrade(){
        long schId =  getParaToLong("schId");
        List<GradeModel> result = GradeModel.dao.findGradeByTerm(schId);
        renderJson(result);
    }

    /**
     * 根据年级id获取班级
     */
    public void findClassByGradeId(){
        String gradeId = getPara("gradeId");
        String schId = getPara("schId");
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("gradeId",gradeId);
        paramMap.put("schId",schId);
        List<ClassModel> result = ClassModel.dao.findClassModel(paramMap);
        renderJson(result);
    }

    /**
     *查询学期年级班级信息
     */
    public void initSelect(){
        String classId = getPara("classId");
        String semesterId = getPara("semesterId");
        Map<String,Object> result = ClassTeacherNameModel.dao.initSelect(classId,semesterId);
        renderJson(result);
    }

    /**
     * 根据班级id获取该班级科目任课老师
     */
    public void findClassTeacherByClassId(){
        String classId = getPara("classId");
        List<ClassTeacherNameModel> result = ClassTeacherNameModel.dao.findByClassId(classId);
        renderJson(result);
    }

    /**
     * 根据班级id查询任课老师详情
     */
    public void details(){
        String classId = getPara("classId");
        List<ClassTeacherNameModel> result = ClassTeacherNameModel.dao.findByClassId(classId);
        renderJson(result);
    }

    @Override
    public void toAdd() {

    }

    @Override
    @FuncActionAnnotation(action="/admin/classTeacher/set")
    @Before({FuncActionInterceptor.class}) 
    public void add() {
        String delIds = getPara("delIds");
        String [] delIdArray = delIds.split(",");
        for (int x = 0; x < delIdArray.length; x++){
            ClassTeacherNameModel.dao.deleteById(delIdArray[x]);
        }
        String dataStr = getPara("dataStr");
        List<? extends Map> dataList = JSON.parseArray(dataStr,Map.class);
        for (Map<String,Object> temp : dataList){
            new ClassTeacherNameModel().set("id", IDKeyUtil.getIDKey())
                    .set("classId",temp.get("classId"))
                    .set("teacherId",temp.get("teacherId"))
                    .set("semesterId",temp.get("semesterId"))
                    .set("subjectId",temp.get("subjectId"))
                    .save();
        }
        renderJson(AjaxRetPojo.newInstance());
    }

    @Override
    public void del() {

    }
}
