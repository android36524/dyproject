package com.dayang.system.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;

import com.dayang.commons.annotation.FuncActionAnnotation;
import com.dayang.commons.controller.AdminBaseController;
import com.dayang.commons.controller.CURDController;
import com.dayang.commons.enums.OrgFlag;
import com.dayang.commons.enums.Status;
import com.dayang.commons.pojo.AjaxRetPojo;
import com.dayang.commons.pojo.JQGridPagePojo;
import com.dayang.commons.util.CommonStaticData.OrgTreeNodeType;
import com.dayang.commons.util.DaYangCommonUtil;
import com.dayang.commons.util.DaYangStaticData;
import com.dayang.commons.util.IDKeyUtil;
import com.dayang.commons.util.SpringInit;
import com.dayang.conf.interceptor.AuthInterceptor;
import com.dayang.conf.interceptor.FuncActionInterceptor;
import com.dayang.system.busi.IDeptBusi;
import com.dayang.system.busi.impl.DeptBusiImpl;
import com.dayang.system.model.DeptModel;
import com.dayang.system.model.HisDeptModel;
import com.dayang.system.model.OrgModel;
import com.dayang.system.validator.DeptDelValidator;
import com.jfinal.aop.Before;
import com.jfinal.render.ContentType;

/**
 * 类描述：教育局/学校部门管理模块
 * <pre>
 * -------------History------------------
 *   DATE                 AUTHOR         VERSION        DESCRIPTION
 *  2015年5月18日             何意            V01.00.001		      新增内容
 * </pre>
 *
 * @author <a href="mailto:heyi@dayang.com">何意 </a>
 */
public class DeptController extends AdminBaseController implements CURDController {

    private IDeptBusi deptBusi = (IDeptBusi) SpringInit.getBean(DeptBusiImpl.class);

    /**
     * 教育局部门管理
     */
    @Before(AuthInterceptor.class)
    public void index(){
        this.setAttr("title","教育局");
        this.setAttr("orgFlag", OrgTreeNodeType.ORGFLAG);
        renderJsp("index.jsp");
    }
    
    public void loadPublic(){
    	long orgId = getParaToLong("orgId",0l);
        String title  = getPara("title");
        OrgModel orgModel = OrgModel.dao.findById(orgId);
        this.setAttr("orgName", orgModel.get("orgName"));
        this.setAttr("orgId",orgId);
        this.setAttr("title",title);
    }

    /**
     * 部门新增页面
     */
    @Override
    public void toAdd() {
    	String title = getPara("title");
    	String name = getPara("name");
    	if("学校".equals(title)||"教育局".equals(title)){
    		loadPublic();
    	}
    	this.setAttr("title", title);
    	this.setAttr("name", name);
        renderJsp("dept-modify.jsp");
    }
    /**
     * 跳转到部门详情界面
     */
    public void showdeptInfo(){
    	loadPublic();
    	renderJsp("showdeptInfo.jsp");
    }

    /**
     * 新增或者修改教育局部门
     */
    @FuncActionAnnotation(noIdAction="/admin/dept/add",idAction="/admin/dept/modify",idName = "dept.id")
    @Before({FuncActionInterceptor.class}) 
    @Override
    public void add() {
        DeptModel deptModel = getModel(DeptModel.class,"dept");
        if(deptModel.getLong("id") != null){
            deptModel.update();
        }else {
            deptModel.set("id", IDKeyUtil.getIDKey());
            deptModel.set("createTime",new Date());
            deptModel.set("creator",getLoginUserId());
            deptModel.save();
        }
        renderJson(AjaxRetPojo.newInstance());
    }

    /**
     * 上级部门（有下级部门不允许删除）
     */
    @Override
    @Before({DeptDelValidator.class,FuncActionInterceptor.class})
    public void del() {
        AjaxRetPojo ajaxRetPojo = AjaxRetPojo.newInstance();
        String deptId = getPara("id");
        DeptModel dept = DeptModel.dao.findById(getParaToLong("id"));
        String accountId = String.valueOf(dept.get("accountId"));
        HisDeptModel hisDeptModel = new HisDeptModel();
        if (dept != null){
            Set<Map.Entry<String,Object>> set = dept.getAttrsEntrySet();
            for (Map.Entry<String,Object> obj : set){
                hisDeptModel.set(obj.getKey(),obj.getValue());
            }
            hisDeptModel.save();
            DeptModel.dao.deleteById(deptId);
            DaYangCommonUtil.deleteAccountById(accountId);
        }
        renderJson(ajaxRetPojo.newInstance());
    }

    @Before(AuthInterceptor.class)
    public void schoolIndex(){
        this.setAttr("title","学校");
        this.setAttr("orgFlag", OrgTreeNodeType.SCHFLAG);
        renderJsp("index.jsp");
    }
    
    @Before(AuthInterceptor.class)
    public void companyDeptIndex(){
        this.setAttr("title","公司");
        this.setAttr("orgFlag", OrgTreeNodeType.COMPANYFLAG);
        renderJsp("index.jsp");
    }

    /**
     * 查询部门列表信息
     */
    public void deptList(){
        int pageNumber = getParaToInt("page");
        int pageSize = getParaToInt("rows", DaYangStaticData.PAGE_ROWS);
        String deptName = getPara("deptName");
        long orgId = getParaToLong("orgId",0l);
        long deptId = getParaToLong("nodeid", 0l);
        int isleaf = deptBusi.getDeptLeftCount(deptId) > 0 ? 0 : 1;
        int level = deptBusi.getDeptLevel(0, deptId);
        Map<String,Object> map= new HashMap<String,Object>();
        map.put("deptName",deptName);
        map.put("deptId",deptId);
        map.put("orgId",orgId);
        map.put("orgType",getPara("orgFlag"));
        renderJson(JQGridPagePojo.parsePageData(DeptModel.dao.findDeptPage(pageNumber, pageSize,  isleaf, level,map)));
    }
    /**
     * 根据部门Id获该部门信息
     */
    public void queryById(){
        long deptId = getParaToLong("id");
        DeptModel deptModel = DeptModel.dao.findByDeptId(deptId);
        renderJson(deptModel);
    }  
    
    /**
     * 所属部门树
     */
    public void getDeptTree(){
        long deptId = getParaToLong("nodeid", 0l);
        long id = getParaToLong("deptId", 0l);
        int isleaf = deptBusi.getDeptLeftCount(deptId) > 0 ? 0 : 1;
        int level = deptBusi.getDeptLevel(0, deptId);
        long orgId = getParaToLong("orgId", 0l);
        Map<String,Object> map= new HashMap<String,Object>();
        map.put("deptId", deptId);
        map.put("id", id);
        map.put("orgId", orgId);
        renderJson(DeptModel.dao.findDeptList(isleaf, level, map));
    }



    /**
     * 教育局部门编码=教育局编码+两位流水号
     */
    public void genDeptCode(){
        long orgId = getParaToLong("orgId");

        //先获取当前教育局的编码
        String edbFlag = OrgFlag.EDBFLAG.getValueStr();
        String schFlag = OrgFlag.SCHOOLFLAG.getValueStr();
        String status = Status.ENABLE.getValueStr();
        OrgModel orgModel = OrgModel.dao.findById(orgId);
        OrgModel model= null;
        String orgFlag = orgModel.get("orgFlag");
        if (orgFlag.equals(edbFlag)){
            model = OrgModel.dao.findFirst("select * from base_organization where 1=1 and orgFlag = ? and status =? and id = ? ", edbFlag,status,orgId);
        }
        else if(orgFlag.equals(schFlag)){
            model = OrgModel.dao.findFirst("select * from base_organization where 1=1 and orgFlag = ? and status =? and id = ? ", schFlag,status,orgId);
        }else{
        	model = OrgModel.dao.findFirst("select * from base_organization where 1=1 and orgFlag = ? and status =? and id = ? ", orgFlag,status,orgId);
        }
        String deptCode = model.getStr("orgCode");
        //根据教育局ID，查找当前教育局下的部门
        //String deptFlag = OrgFlag.EDBFLAG.getValue() + "";
        List<DeptModel> deptList =
                DeptModel.dao.find("select * from base_department where 1=1  and orgId = ?", orgId);
        if(CollectionUtils.isNotEmpty(deptList)){
            Integer max = 1;
            for (DeptModel dept : deptList) {
                String orgCode = dept.getStr("deptCode");
                String curIndex = orgCode.substring(orgCode.length()-2, orgCode.length());
                if(Integer.valueOf(curIndex).compareTo(max) > 0){
                    max = Integer.valueOf(curIndex);
                }
            }
            String maxStr = String.valueOf(max + 1);
            if(maxStr.length() == 1){
                maxStr = "0" + maxStr;
            }
            deptCode += maxStr;
        }else{
            //当前是第一个教育局部门
            deptCode += "01";
        }
        renderText(deptCode, ContentType.TEXT);
    }
    /**
     * 校验当前教育局下部门名称是否唯一
     */
    public void validateDeptName(){
        boolean flag = true;
        String deptName = getPara("deptName");
        long orgId = getParaToLong("orgId",0l);
        long deptId = getParaToLong("deptId",0l);
        DeptModel deptModel = DeptModel.dao.validateDeptName(deptName, orgId, deptId);
        if (deptModel != null){
            flag = false;
        }
        renderJson(flag);
    }

}
