package com.dayang.commons.pojo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

import com.dayang.commons.enums.EmpeTypeSch;
import com.dayang.commons.enums.MarryStatus;
import com.dayang.commons.enums.Sex;
import com.dayang.commons.util.CommonUtil;
import com.dayang.commons.util.DayangDictionaryUtil;
import com.dayang.commons.util.EnumUtil;
import com.dayang.commons.util.IDKeyUtil;
import com.dayang.commons.util.StaticData;
import com.dayang.system.annotation.EffectiveDictionaryName;
import com.dayang.system.annotation.EffectiveEnumName;
import com.dayang.system.annotation.EffectiveName;
import com.dayang.system.annotation.StringNotEmpty;
import com.dayang.system.controller.SchoolController;
import com.dayang.system.controller.SchoolEmpeController;
import com.dayang.system.model.DeptModel;
import com.dayang.system.model.DictionaryModel;
import com.dayang.system.model.EmpeModel;
import com.jfinal.kit.PathKit;

/**
 * 类描述：学校员工导入实体类
 * <pre>
 * -------------History------------------
 *   DATE                 AUTHOR         VERSION        DESCRIPTION
 *  2015年06月08日            何意      V01.00.001		   新增内容
 * </pre>
 *
 * @author <a href="mailto:liujy@dayanginfo.com">何意</a>
 */
@SuppressWarnings("unused")
@EffectiveName(nameField = "deptName",nameListField = "deptNames",message = "学校部门-部门名称不合法")
public class SchEmplImportPojo extends ImportPojo{

    //日期匹配正则
    private static final String datePattern = "([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})-(((0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)-(0[1-9]|[12][0-9]|30))|(02-(0[1-9]|[1][0-9]|2[0-8])))";
    //日期错误message
    private static final String dateErrorMessage = "日期格式不对";

    private static final String insertEmployeeSql = "insert into base_employee " +
            "(id,name,sex,empNo,hometown,nation,orgId,birthDate,political,maritalStatus,education,idCard,deptId,mobile," +
            "email,zipCode,homeAddr,addr,createTime,empType,jobsCareers,status) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
    private static  final String insertTeaSql="insert into base_teacherinfo(id,workAge,teacherWorkAge,jobName,health,toSchYear)" +
            " values(?,?,?,?,?,?)";


    //定义一个静态的机构ID
    public static long orgId;
    private static final Map<Integer,String> relation = new HashMap<>();
    static {
        relation.put(0,"deptName"); //对应部门
        relation.put(1,"empNo");//工号
        relation.put(2,"name");//名称
        relation.put(3,"sex");//性别
        relation.put(4,"mobile");//手机号码（非空）
        relation.put(5,"empType");//岗位类型
        relation.put(6,"jobsCareers");//岗位职业
        relation.put(7,"birthDate");//出生日期
        relation.put(8,"hometown"); //籍贯
        relation.put(9,"nation");//名族
        relation.put(10,"maritalStatus");//婚姻状况
        relation.put(11,"political");//政治面貌
        relation.put(12,"health");//健康状况
        relation.put(13,"education");//学历
        relation.put(14,"idCard");//身份证号码
        relation.put(15,"job");
        relation.put(16,"jobName");
        relation.put(17,"workAge");
        relation.put(18,"teacherWorkAge");
        relation.put(19,"toSchYear");
        relation.put(20,"homeAddr");
        relation.put(21,"addr");
        relation.put(22,"zipCode");
        relation.put(23,"email");
    }

    //合法部门名称List
    private List<String> deptNames;

    //姓名
    @StringNotEmpty(message = "姓名不能为空")
    @Length( max = 20,message = "姓名长度不能超过20")
    private String name;

    //工号
    @StringNotEmpty(message = "工号不能为空")
    @Length(max = 20,message = "工号长度不能超过20")
    private String empNo;

    //性别
    @StringNotEmpty(message = "性别不能为空")
    @EffectiveEnumName(clazz = Sex.class,message = "性别不合法")
    private String sex;

    //部门名称
    @StringNotEmpty(message = "部门不能为空")
    private String deptName;

    //名族
    @EffectiveDictionaryName(dictType = StaticData.NATION_dictionaryType,message = "名族不合法")
    private String nation;

    //政治面貌
    @EffectiveDictionaryName(dictType = StaticData.POLITICAL_dictionaryType,message = "政治面貌不合法")
    private String political;

    //婚姻状况
    @EffectiveEnumName(clazz = MarryStatus.class,message = "婚姻状况不合法")
    private String maritalStatus;

    //学历
    @EffectiveDictionaryName(dictType = StaticData.EDUCATION_dictionaryType,message = "学历不合法")
    private String education;

    //手机号码
    @StringNotEmpty(message = "手机号码不能为空")
    @Pattern(regexp = "^((13[0-9])|(15[0-9])|(18[0-9])|(14[7])|(17[7]))\\d{8}$",message = "手机号不正确")
    private String mobile;

    //出生日期
    @Pattern(regexp = datePattern,message = dateErrorMessage)
    private String birthDate;

    //籍贯
    @Length(max = 30,message = "籍贯长度不能超过30")
    private String hometown;

    //身份证号码
    @Pattern(regexp = "^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}([0-9]|X)$",message = "身份证号码格式不对")
    private String idCard;

    //参加工作时间
    @Pattern(regexp = datePattern,message = dateErrorMessage)
    private String workAge;


    //职务
    @Length(max = 100,message = "职务列-职务长度不超过100")
    private String job;

    //职称
    @Length(max = 100,message = "职称列-职称长度不超过100")
    private String jobName;
    
    //岗位类型
    @StringNotEmpty(message = "岗位类型不能为空")
    @EffectiveEnumName(clazz = EmpeTypeSch.class,message = "岗位类型不合法")
    private String empType;

    //岗位职业
    @StringNotEmpty(message = "岗位职业不能为空")
    @EffectiveDictionaryName(dictType = StaticData.JOBSCAREERS_DICTIONARYTYPE,message = "岗位职业")
    private String jobsCareers;

    //从教年月
    @Pattern(regexp = datePattern,message = dateErrorMessage)
    private String teacherWorkAge;

    //来校年月
    @Pattern(regexp = datePattern,message = dateErrorMessage)
    private String toSchYear;

    //健康状况
    @EffectiveDictionaryName(dictType = StaticData.HEALTH_DICTIONARYTYPE,message = "健康状况")
    private String health;

    //电子邮箱
    @Pattern(regexp = "^((([a-z]|\\d|[!#\\$%&'\\*\\+\\-\\/=\\?\\^_`{\\|}~]|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])+(\\.([a-z]|\\d|[!#\\$%&'\\*\\+\\-\\/=\\?\\^_`{\\|}~]|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])+)*)|((\\x22)((((\\x20|\\x09)*(\\x0d\\x0a))?(\\x20|\\x09)+)?(([\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x7f]|\\x21|[\\x23-\\x5b]|[\\x5d-\\x7e]|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])|(\\\\([\\x01-\\x09\\x0b\\x0c\\x0d-\\x7f]|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF]))))*(((\\x20|\\x09)*(\\x0d\\x0a))?(\\x20|\\x09)+)?(\\x22)))@((([a-z]|\\d|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])|(([a-z]|\\d|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])([a-z]|\\d|-|\\.|_|~|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])*([a-z]|\\d|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])))\\.)+(([a-z]|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])|(([a-z]|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])([a-z]|\\d|-|\\.|_|~|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])*([a-z]|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])))\\.?$",message = "邮箱格式不对")
    private String email;

    //家庭住址
    @Length(max = 100,message = "家庭住址长度不能超过100")
    private String homeAddr;

    //现住址
    @Length(max = 100,message = "现住址长度不能超过100")
    private String addr;

    //邮政编码
    @Pattern(regexp = "^[\\d]{6}$",message = "邮政编码格式不对")
    private String zipCode;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getEmpNo() {
        return empNo;
    }

    public void setEmpNo(String empNo) {
        this.empNo = empNo;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public String getHometown() {
        return hometown;
    }

    public void setHometown(String hometown) {
        this.hometown = hometown;
    }

    public String getPolitical() {
        return political;
    }

    public void setPolitical(String political) {
        this.political = political;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }


    public String getWorkAge() {
        return workAge;
    }

    public void setWorkAge(String workAge) {
        this.workAge = workAge;
    }

    public String getTeacherWorkAge() {
        return teacherWorkAge;
    }

    public void setTeacherWorkAge(String teacherWorkAge) {
        this.teacherWorkAge = teacherWorkAge;
    }

    public String getToSchYear() {
        return toSchYear;
    }

    public void setToSchYear(String toSchYear) {
        this.toSchYear = toSchYear;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getJobsCareers() {
        return jobsCareers;
    }

    public void setJobsCareers(String jobsCareers) {
        this.jobsCareers = jobsCareers;
    }

    public String getHealth() {
        return health;
    }

    public void setHealth(String health) {
        this.health = health;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }



    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHomeAddr() {
        return homeAddr;
    }

    public void setHomeAddr(String homeAddr) {
        this.homeAddr = homeAddr;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {

        this.zipCode = zipCode;
    }

    public void setDeptNames(List<String> list){
        if (!CommonUtil.isEmptyCollection(list)){
            deptNames = list;
        }
    }

    public List<String> getDeptNames() {
        return deptNames;
    }

    @Override
    public Map<Integer, String> getMap() {
        return relation;
    }

    public Map<String,List<Object>> getSql(){
        Map<String,List<Object>> map = new HashMap<>();
        List<Object> empelist = new ArrayList<>();
        List<Object> tealist = new ArrayList<>();
        String sexValue = EnumUtil.getValueByName(Sex.class,this.sex);
        String maritalStatusValue = EnumUtil.getValueByName(MarryStatus.class, this.maritalStatus);
        long id = IDKeyUtil.getIDKey();
        int nationValue = DayangDictionaryUtil.getDicValueByName(StaticData.NATION_dictionaryType,this.nation);
        long politicalValue = DayangDictionaryUtil.getDicValueByName(StaticData.POLITICAL_dictionaryType,this.political);
        long educationValue = DayangDictionaryUtil.getDicValueByName(StaticData.EDUCATION_dictionaryType,this.education);
        long healthValue = DayangDictionaryUtil.getDicValueByName(StaticData.HEALTH_DICTIONARYTYPE,this.health);
        DeptModel deptModel = DeptModel.dao.validateDeptName(this.deptName,orgId,0l);
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        DictionaryModel dictionaryModel = DictionaryModel.dao.findDictiByValue(StaticData.TEASTATUS_INNERALTER,String.valueOf(StaticData.TeacherStatus.inUsed));
        
        empType = EnumUtil.getValueByName(EmpeTypeSch.class,this.empType);
        int jobsCareers = DayangDictionaryUtil.getDicValueByName(StaticData.JOBSCAREERS_DICTIONARYTYPE,this.jobsCareers);
        
        empelist.add(id);empelist.add(this.name);
        empelist.add(sexValue);empelist.add(this.empNo);empelist.add(this.hometown);
        empelist.add(nationValue);empelist.add(orgId);empelist.add(this.birthDate);
        empelist.add(politicalValue);empelist.add(maritalStatusValue);empelist.add(educationValue);
        empelist.add(this.idCard);empelist.add(deptModel.get("id"));empelist.add(this.mobile);
        empelist.add(this.email);empelist.add(this.zipCode);empelist.add(this.homeAddr);
        empelist.add(this.addr);empelist.add(sf.format(new Date()));
        empelist.add(empType);empelist.add(jobsCareers);empelist.add(dictionaryModel.get("value"));

        
        tealist.add(id);tealist.add(this.workAge);tealist.add(this.teacherWorkAge);
        tealist.add(this.jobName);tealist.add(healthValue);tealist.add(this.toSchYear);
        map.put(insertEmployeeSql,empelist);
        map.put(insertTeaSql,tealist);
        return map;
    }

    @Override
    public String getTemplatePath() {
        return PathKit.getWebRootPath()+ CommonUtil.getAppProperties("teaEmplExcelPath");
    }
}
