package com.dayang.commons.pojo;

import com.dayang.commons.enums.EmpeType;
import com.dayang.commons.enums.MarryStatus;
import com.dayang.commons.enums.Sex;
import com.dayang.commons.util.*;
import com.dayang.system.annotation.EffectiveDictionaryName;
import com.dayang.system.annotation.EffectiveEnumName;
import com.dayang.system.annotation.EffectiveName;
import com.dayang.system.annotation.StringNotEmpty;
import com.dayang.system.model.DeptModel;
import com.jfinal.kit.PathKit;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Pattern;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.SimpleFormatter;

/**
 * 类描述：教育局员工导入实体类
 * <pre>
 * -------------History------------------
 *   DATE                 AUTHOR         VERSION        DESCRIPTION
 *  2015年06月04日            刘建宇      V01.00.001		   新增内容
 * </pre>
 *
 * @author <a href="mailto:liujy@dayanginfo.com">刘建宇</a>
 */
@SuppressWarnings("unused")
@EffectiveName(nameField = "deptName",nameListField = "deptNames",message = "教育局部门-部门名称不合法")
public class EdbEmplImportPojo extends ImportPojo{

    //日期匹配正则
    private static final String datePattern = "([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})-(((0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)-(0[1-9]|[12][0-9]|30))|(02-(0[1-9]|[1][0-9]|2[0-8])))";
    //日期错误message
    private static final String dateErrorMessage = "日期格式不对";

    private static final String insertEmployeeSql = "insert into base_employee " +
            "(id,name,sex,empNo,hometown,nation,orgId,birthDate,political,maritalStatus,education,idCard,deptId,mobile,telphone," +
            "email,zipCode,homeAddr,addr,createTime,empType) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

    private  static final String insertEduSql = "insert into base_educationinfo(id,entryDate,startWorkDate) values(?,?,?)";

    //定义一个静态的机构ID
    public static long orgId;
    private static final Map<Integer,String> relation = new HashMap<>();
    static {
        relation.put(0,"deptName");
        relation.put(1,"empNo");
        relation.put(2,"name");
        relation.put(3,"sex");
        relation.put(4,"birthDate");
        relation.put(5,"mobile");
        relation.put(6,"telphone");
        relation.put(7,"hometown");
        relation.put(8,"nation");
        relation.put(9,"maritalStatus");
        relation.put(10,"political");
        relation.put(11,"education");
        relation.put(12,"idCard");
        relation.put(13,"job");
        relation.put(14,"entryDate");
        relation.put(15,"startWorkDate");
        relation.put(16,"homeAddr");
        relation.put(17,"addr");
        relation.put(18,"zipCode");
        relation.put(19,"email");
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
    private String startWorkDate;


    //职务
    @Length(max = 100,message = "职务列-职务长度不超过100")
    private String job;



    //进入单位时间
    @Pattern(regexp = datePattern,message = dateErrorMessage)
    private String entryDate;

    //办公电话
    @Pattern(regexp = "^((\\(\\d{2,3}\\))|(\\d{3}\\-))?(\\(0\\d{2,3}\\)|0\\d{2,3}-)?[1-9]\\d{6,7}(\\-\\d{1,4})?",message = "电话号码格式不对")
    private String telphone;

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

    public String getStartWorkDate() {
        return startWorkDate;
    }

    public void setStartWorkDate(String startWorkDate) {
        this.startWorkDate = startWorkDate;
    }

    public String getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(String entryDate) {
        this.entryDate = entryDate;
    }

    public String getTelphone() {
        return telphone;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public void setTelphone(String telphone) {
        this.telphone = telphone;
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
        List<Object> edulist = new ArrayList<>();
        String sexValue = EnumUtil.getValueByName(Sex.class,this.sex);
        String maritalStatusValue = EnumUtil.getValueByName(MarryStatus.class,this.maritalStatus);
        long id = IDKeyUtil.getIDKey();
        int nationValue = DayangDictionaryUtil.getDicValueByName(StaticData.NATION_dictionaryType,this.nation);
        long politicalValue = DayangDictionaryUtil.getDicValueByName(StaticData.POLITICAL_dictionaryType,this.political);
        long educationValue = DayangDictionaryUtil.getDicValueByName(StaticData.EDUCATION_dictionaryType,this.education);
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        DeptModel deptModel = DeptModel.dao.validateDeptName(this.deptName,orgId,0l);
        //给教育局员工表设值
        empelist.add(id);empelist.add(this.name);
        empelist.add(sexValue);empelist.add(this.empNo);empelist.add(this.hometown);empelist.add(nationValue);empelist.add(orgId);empelist.add(this.birthDate);
        empelist.add(politicalValue);empelist.add(maritalStatusValue);empelist.add(educationValue);empelist.add(this.idCard);empelist.add(deptModel.get("id"));empelist.add(this.mobile);
        empelist.add(this.telphone);empelist.add(this.email);empelist.add(this.zipCode);empelist.add(this.homeAddr);empelist.add(this.addr);empelist.add(sf.format(new Date()));
        empelist.add(EmpeType.EDBEMPE.getValue());
        //给教育局员工扩展表设值
        edulist.add(id);edulist.add(this.entryDate);edulist.add(this.startWorkDate);
        map.put(insertEmployeeSql,empelist);
        map.put(insertEduSql,edulist);
        return map;
    }

    @Override
    public String getTemplatePath() {
        return PathKit.getWebRootPath()+ CommonUtil.getAppProperties("eudEmplExcelPath");
    }

}
