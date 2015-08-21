package com.dayang.commons.pojo;

import com.dayang.commons.enums.EnumAll;
import com.dayang.commons.enums.Sex;
import com.dayang.commons.util.*;
import com.dayang.system.annotation.EffectiveDictionaryName;
import com.dayang.system.annotation.EffectiveEnumName;
import com.dayang.system.annotation.EffectiveGradeAndClass;
import com.dayang.system.annotation.StringNotEmpty;
import com.dayang.system.model.ClassModel;
import com.dayang.system.model.GradeModel;
import com.dayang.system.model.StuParentModel;
import com.dayang.system.model.StudentModel;
import com.github.stuxuhai.jpinyin.PinyinHelper;
import com.jfinal.kit.PathKit;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Pattern;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 类描述：学生信息导入实体类
 * <pre>
 * -------------History------------------
 *   DATE                 AUTHOR         VERSION        DESCRIPTION
 *  2015年06月11日           吴杰东      V01.00.001		   新增内容
 * </pre>
 *
 * @author <a href="mailto:wujd@dayanginfo.com">吴杰东</a>
 */
@SuppressWarnings("unused")
@EffectiveGradeAndClass
public class StudentImportPojo extends ImportPojo{

    //日期匹配正则
    private static final String datePattern = "([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})-(((0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)-(0[1-9]|[12][0-9]|30))|(02-(0[1-9]|[1][0-9]|2[0-8])))";
    //日期错误message
    private static final String dateErrorMessage = "日期格式不对";

    private static final String insertStuSql = "insert into base_student " +
            "(id, name, nameSpell, schId, gradeId, classId, rollCode, studentNo, sex, birthDate, nation, hometown, birthPlace, enrolTime, telephone, originalSchool, status, isReside ," +
            " accountId, creator, createTime, fromWhere, idCard, homeAddr) values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    private  static final String insertStuExpandSql = " insert into base_student_expand" +
    "(id, political, householdLocation, householdNature, health, telphone, ziplCode, postalAddr, disabilityType, stay, migrant, soldier, teacher, singleParent, onlySon, poor) "+
    "values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";		
    
    private  static final String insertStuParentSql = "insert into base_parent"+
    " (ID, name, telphone, sex) VALUES (?, ?, ?, ?)";
    
    
    private  static final String insertLinkedSql = "insert into r_student_info"+
    	    " (id, studentId, parentId, guardian, relationType, schId)  VALUES (?, ?, ?, ?, ?, ?)";

    
    //定义一个静态的机构ID
    private long schId;
    private static final Map<Integer,String> relation = new HashMap<>();
    static {
        relation.put(0,"gradeName");
        relation.put(1,"className");
        relation.put(2,"studentNo");
        relation.put(3,"stuName");
        relation.put(4,"sex");
        relation.put(5,"birthDate");
        relation.put(6,"idCard");
        relation.put(7,"hometown");
        relation.put(8,"birthPlace");
        relation.put(9,"enrolTime");
        relation.put(10,"originalSchool");
        relation.put(11,"nation");
        relation.put(12,"political");
        relation.put(13,"health");
        relation.put(14,"fromWhere");
        relation.put(15,"isReside");
        relation.put(16,"telephone");
        relation.put(17,"ziplCode");
        relation.put(18,"homeAddr");
        relation.put(19,"postalAddr");
        relation.put(20,"householdLocation"); //户口所在地
        relation.put(21,"householdNature");
        relation.put(22,"disabilityType");
        relation.put(23,"stay");
        relation.put(24,"onlySon");
        relation.put(25,"migrant");
        relation.put(26,"soldier");
        relation.put(27,"teacher");
        relation.put(28,"singleParent");
        relation.put(29,"poor");
        relation.put(30,"rollCode");
        relation.put(31,"parentName"); //家长信息
        relation.put(32,"parentSex");
        relation.put(33,"relationType");
        relation.put(34,"guardian");
        relation.put(35,"parentTelphone");
    }

    //合法年级名称List
    private  List<String> gradeNames;
    //合法班级名称List
    private  List<String> classNames;

    //年级
    @StringNotEmpty(message = "年级不能为空")
    private String gradeName;
    
    //班级
    @StringNotEmpty(message = "班级不能为空")
    private String className;
    
    //学号
    @StringNotEmpty(message = "学号不能为空")
    private String studentNo;
    
    //姓名
    @StringNotEmpty(message = "姓名不能为空")
    @Length( max = 20,message = "姓名长度不能超过20")
    @Pattern(regexp = "^[\u0391-\uFFE5]+$",message = "姓名只能为中文")
    private String stuName;
    
    //性别
    @StringNotEmpty(message = "性别不能为空")
    @EffectiveEnumName(clazz = Sex.class,message = "性别不合法")
    private String sex;
    
    //出生日期
    @StringNotEmpty(message = "出生日期不能为空")
    @Pattern(regexp = datePattern,message = dateErrorMessage)
    private String birthDate;
    
    //身份证号码
    @Pattern(regexp = "^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}([0-9]|X)$",message = "身份证号码格式不对")
    private String idCard;
    
    //籍贯
    @Length(max = 30,message = "籍贯长度不能超过30")
    private String hometown;
    
    //出生地
    @Length(max = 100,message = "出生地长度不能超过100")
    private String birthPlace;
    
    // 入校日期
    @Pattern(regexp = datePattern,message = dateErrorMessage)
    private String enrolTime;
    
    // 来源校
    @Length(max = 100,message = "来源校长度不能超过100")
    private String originalSchool;
    
    //民族
    @EffectiveDictionaryName(dictType = StaticData.NATION_dictionaryType,message = "民族不合法")
    private String nation;

    //政治面貌
    @EffectiveDictionaryName(dictType = StaticData.POLITICAL_dictionaryType,message = "政治面貌不合法")
    private String political;
    
    //健康状况
    @EffectiveDictionaryName(dictType = StaticData.HEALTH_DICTIONARYTYPE,message = "健康状况不合法")
    private String health;
    
    //学生来源
    @EffectiveDictionaryName(dictType = StaticData.FROMWHERE_DICTIONARYTYPE,message = "学生来源不合法")
    private String fromWhere;
    
    //就读方式
    @StringNotEmpty(message = "就读方式不能为空")
    @EffectiveEnumName(clazz = EnumAll.StudyWays.class,message = "就读方式不合法")
    private String isReside;

    //联系方式
    @Pattern(regexp = "^((\\(\\d{2,3}\\))|(\\d{3}\\-))?(\\(0\\d{2,3}\\)|0\\d{2,3}-)?[1-9]\\d{6,7}(\\-\\d{1,4})?",message = "联系电话格式不对")
    private String telephone;
    
    //邮政编码
    @Pattern(regexp = "^[\\d]{6}$",message = "邮政编码格式不对")
    private String ziplCode;
    
    //家庭住址
    @Length(max = 100,message = "家庭住址长度不能超过100")
    private String homeAddr;
    
    //通信地址
    @Length(max = 100,message = "通信地址长度不能超过100")
    private String postalAddr;
    
    //户口所在地
    @Length(max = 100,message = "户口所在地长度不能超过100")
    private String householdLocation;

    //户口性质
    @EffectiveDictionaryName(dictType = StaticData.HOUSEHOLDNATURE_DICTIONARYTYPE,message = "户口性质不合法")
    private String householdNature;

    //残疾类型
    @EffectiveDictionaryName(dictType = StaticData.DISABILITY_DICTIONARYTYPE,message = "残疾类型不合法")
    private String disabilityType;
  
    //留守儿童
    @EffectiveDictionaryName(dictType = StaticData.STAY_DICTIONARYTYPE,message = "留守儿童不合法")
    private String stay;
    
    //独生子女
    @EffectiveEnumName(clazz = EnumAll.IsYesOrNot.class,message = "独生子女不合法")
    private String onlySon;
    
    //农民工子女
    @EffectiveEnumName(clazz = EnumAll.IsYesOrNot.class,message = "农民工子女不合法")
    private String migrant;
 
    //军人子女
    @EffectiveEnumName(clazz = EnumAll.IsYesOrNot.class,message = "军人子女不合法")
    private String soldier;
    
    //教师子女
    @EffectiveEnumName(clazz = EnumAll.IsYesOrNot.class,message = "教师子女不合法")
    private String teacher;
    
    //单亲家庭
    @EffectiveEnumName(clazz = EnumAll.IsYesOrNot.class,message = "单亲家庭不合法")
    private String singleParent;
    
    //贫困生
    @EffectiveEnumName(clazz = EnumAll.IsYesOrNot.class,message = "贫困生不合法")
    private String poor;

    //学籍号
    private String rollCode;
 
    //家长姓名
    @StringNotEmpty(message = "家长姓名不能为空")
    @Length( max = 20,message = "家长姓名长度不能超过20")
    private String parentName;

    //性别
    @StringNotEmpty(message = "家长性别不能为空")
    @EffectiveEnumName(clazz = Sex.class,message = "家长性别不合法")
    private String parentSex;

    //关系
    @StringNotEmpty(message = "关系不能为空")
    private String relationType;
    
    //家长是否监护人
    @StringNotEmpty(message = "家长监护人不能为空")
    private String guardian;
    
    //家长手机号码
    @StringNotEmpty(message = "家长手机号码不能为空")
    @Pattern(regexp = "^((13[0-9])|(15[0-9])|(18[0-9])|(14[7])|(17[7]))\\d{8}$",message = "家长手机号码不正确")
    private String parentTelphone;
 

    public String getGradeName() {
		return gradeName;
	}

	public void setGradeName(String gradeName) {
		this.gradeName = gradeName;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getStudentNo() {
		return studentNo;
	}

	public void setStudentNo(String studentNo) {
		this.studentNo = studentNo;
	}

	public String getStuName() {
		return stuName;
	}

	public void setStuName(String stuName) {
		this.stuName = stuName;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public String getHometown() {
		return hometown;
	}

	public void setHometown(String hometown) {
		this.hometown = hometown;
	}

	public String getBirthPlace() {
		return birthPlace;
	}

	public void setBirthPlace(String birthPlace) {
		this.birthPlace = birthPlace;
	}
	public String getEnrolTime() {
		return enrolTime;
	}

	public void setEnrolTime(String enrolTime) {
		this.enrolTime = enrolTime;
	}

	public String getOriginalSchool() {
		return originalSchool;
	}

	public void setOriginalSchool(String originalSchool) {
		this.originalSchool = originalSchool;
	}

	public String getNation() {
		return nation;
	}

	public void setNation(String nation) {
		this.nation = nation;
	}

	public String getPolitical() {
		return political;
	}

	public void setPolitical(String political) {
		this.political = political;
	}

	public String getHealth() {
		return health;
	}

	public void setHealth(String health) {
		this.health = health;
	}

	public String getFromWhere() {
		return fromWhere;
	}

	public void setFromWhere(String fromWhere) {
		this.fromWhere = fromWhere;
	}

	public String getIsReside() {
		return isReside;
	}

	public void setIsReside(String isReside) {
		this.isReside = isReside;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getZiplCode() {
		return ziplCode;
	}

	public void setZiplCode(String ziplCode) {
		this.ziplCode = ziplCode;
	}

	public String getHomeAddr() {
		return homeAddr;
	}

	public void setHomeAddr(String homeAddr) {
		this.homeAddr = homeAddr;
	}

	public String getPostalAddr() {
		return postalAddr;
	}

	public void setPostalAddr(String postalAddr) {
		this.postalAddr = postalAddr;
	}

	public String getHouseholdLocation() {
		return householdLocation;
	}

	public void setHouseholdLocation(String householdLocation) {
		this.householdLocation = householdLocation;
	}

	public String getHouseholdNature() {
		return householdNature;
	}

	public void setHouseholdNature(String householdNature) {
		this.householdNature = householdNature;
	}

	public String getDisabilityType() {
		return disabilityType;
	}

	public void setDisabilityType(String disabilityType) {
		this.disabilityType = disabilityType;
	}

	public String getStay() {
		return stay;
	}

	public void setStay(String stay) {
		this.stay = stay;
	}

	public String getOnlySon() {
		return onlySon;
	}

	public void setOnlySon(String onlySon) {
		this.onlySon = onlySon;
	}

	public String getMigrant() {
		return migrant;
	}

	public void setMigrant(String migrant) {
		this.migrant = migrant;
	}

	public String getSoldier() {
		return soldier;
	}

	public void setSoldier(String soldier) {
		this.soldier = soldier;
	}

	public String getTeacher() {
		return teacher;
	}

	public void setTeacher(String teacher) {
		this.teacher = teacher;
	}

	public String getSingleParent() {
		return singleParent;
	}

	public void setSingleParent(String singleParent) {
		this.singleParent = singleParent;
	}

	public String getPoor() {
		return poor;
	}

	public void setPoor(String poor) {
		this.poor = poor;
	}

	public String getRollCode() {
		return rollCode;
	}

	public void setRollCode(String rollCode) {
		this.rollCode = rollCode;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public String getParentSex() {
		return parentSex;
	}

	public void setParentSex(String parentSex) {
		this.parentSex = parentSex;
	}

	public String getRelationType() {
		return relationType;
	}

	public void setRelationType(String relationType) {
		this.relationType = relationType;
	}

	public String getGuardian() {
		return guardian;
	}

	public void setGuardian(String guardian) {
		this.guardian = guardian;
	}

	public String getParentTelphone() {
		return parentTelphone;
	}

	public void setParentTelphone(String parentTelphone) {
		this.parentTelphone = parentTelphone;
	}

	public static Map<Integer, String> getRelation() {
		return relation;
	}

	public List<String> getGradeNames() {
		return gradeNames;
	}

	public long getSchId() {
		return schId;
	}

	public void setSchId(long schId) {
		this.schId = schId;
	}

	public void setGradeNames(List<String> gradeNames) {
		if(!CommonUtil.isEmptyCollection(gradeNames)){
			this.gradeNames = gradeNames;
		}
	}

	public List<String> getClassNames() {
		return classNames;
	}

	public void setClassNames(List<String> classNames) {
		if(!CommonUtil.isEmptyCollection(classNames)){
			this.classNames = classNames;
		}
	}

	@Override
    public Map<Integer, String> getMap() {
        return relation;
    }

    public Map<String,List<Object>> getSql(){
        Map<String,List<Object>> map = new HashMap<>();
        List<Object> stuList = new ArrayList<>();
        List<Object> stuExpandlist = new ArrayList<>();
        List<Object> parentlist = new ArrayList<>();
        List<Object> linkedlist = new ArrayList<>();
        String sexValue = EnumUtil.getValueByName(Sex.class,this.sex);
        String isResideValue = EnumUtil.getValueByName(EnumAll.StudyWays.class,this.isReside);
        String onlySonValue = EnumUtil.getValueByName(EnumAll.IsYesOrNot.class,this.onlySon);
        String migrantValue = EnumUtil.getValueByName(EnumAll.IsYesOrNot.class,this.migrant);
        String soldierValue = EnumUtil.getValueByName(EnumAll.IsYesOrNot.class,this.soldier);
        String teacherValue = EnumUtil.getValueByName(EnumAll.IsYesOrNot.class,this.teacher);
        String singleParentValue = EnumUtil.getValueByName(EnumAll.IsYesOrNot.class,this.singleParent);
        String poorValue = EnumUtil.getValueByName(EnumAll.IsYesOrNot.class,this.poor);
        String parentSexValue = EnumUtil.getValueByName(Sex.class,this.parentSex);
        String guardianValue = EnumUtil.getValueByName(EnumAll.IsYesOrNot.class,this.guardian);
        
        long id = IDKeyUtil.getIDKey();
        int nationValue = DayangDictionaryUtil.getDicValueByName(StaticData.NATION_dictionaryType,this.nation);
        long politicalValue = DayangDictionaryUtil.getDicValueByName(StaticData.POLITICAL_dictionaryType,this.political);
        long healthValue = DayangDictionaryUtil.getDicValueByName(StaticData.HEALTH_DICTIONARYTYPE,this.health);
        long fromWhereValue = DayangDictionaryUtil.getDicValueByName(StaticData.FROMWHERE_DICTIONARYTYPE,this.fromWhere);
        long householdValue = DayangDictionaryUtil.getDicValueByName(StaticData.HOUSEHOLDNATURE_DICTIONARYTYPE,this.householdNature);
        long disabilityTypeValue = DayangDictionaryUtil.getDicValueByName(StaticData.DISABILITY_DICTIONARYTYPE,this.disabilityType);
        long stayValue = DayangDictionaryUtil.getDicValueByName(StaticData.STAY_DICTIONARYTYPE,this.stay);
        long relationTypeValue = DayangDictionaryUtil.getDicValueByName(StaticData.RELATION_DICTIONARYTYPE,this.relationType);
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        GradeModel gradeModel = StudentModel.dao.findGardeBySchId(this.gradeName, schId, 0l);
        long gradeId=gradeModel.get("id");
        ClassModel classModel = StudentModel.dao.findClassBySchId(this.className, schId, gradeId, 0l);
        String nameSpell=PinyinHelper.getShortPinyin(this.stuName).toUpperCase();
        //给学生基本信息表设值
        stuList.add(id);stuList.add(this.stuName);stuList.add(nameSpell);stuList.add(schId);stuList.add(gradeModel.get("id"));stuList.add(classModel.get("id"));
        stuList.add(this.rollCode);stuList.add(this.studentNo);stuList.add(sexValue);stuList.add(this.birthDate);
        stuList.add(nationValue);stuList.add(this.hometown);stuList.add(this.birthPlace);stuList.add(this.enrolTime);stuList.add(this.telephone);stuList.add(this.originalSchool);
        stuList.add(StaticData.StudentStatus.inUsed);//默认在读状态
        stuList.add(isResideValue); 
         stuList.add("00001"); // accountId
        stuList.add("1");stuList.add(sf.format(new Date()));stuList.add(fromWhereValue);stuList.add(this.idCard);stuList.add(this.homeAddr);
        
        //给学生信息扩展表设值
        stuExpandlist.add(id);stuExpandlist.add(politicalValue);stuExpandlist.add(this.householdLocation);
        stuExpandlist.add(householdValue);stuExpandlist.add(healthValue);stuExpandlist.add(this.telephone);stuExpandlist.add(this.ziplCode);
        stuExpandlist.add(this.postalAddr);stuExpandlist.add(disabilityTypeValue);stuExpandlist.add(stayValue);stuExpandlist.add(migrantValue);
        stuExpandlist.add(soldierValue);stuExpandlist.add(teacherValue);stuExpandlist.add(singleParentValue);stuExpandlist.add(onlySonValue);stuExpandlist.add(poorValue);
       
        map.put(insertStuSql,stuList);
        map.put(insertStuExpandSql,stuExpandlist);
        //根据家长姓名和电话号码查询家长是否存在。为空则不存在
        StuParentModel stuParentModel = StuParentModel.dao.findStuParent(this.parentName,this.parentTelphone,0);
        if(stuParentModel==null){
        	long parent_id = IDKeyUtil.getIDKey();
        	//给家长信息表设值
        	parentlist.add(parent_id);parentlist.add(this.parentName);parentlist.add(this.parentTelphone); parentlist.add(parentSexValue); 
           //给学生家长关联表设值
        	linkedlist.add(IDKeyUtil.getIDKey());linkedlist.add(id);linkedlist.add(parent_id);linkedlist.add(guardianValue);linkedlist.add(relationTypeValue);linkedlist.add(schId);  
        	map.put(insertStuParentSql,parentlist);
        }else{
        	linkedlist.add(IDKeyUtil.getIDKey());linkedlist.add(id);linkedlist.add(stuParentModel.get("ID"));linkedlist.add(guardianValue);linkedlist.add(relationTypeValue);linkedlist.add(schId);  
        }
        map.put(insertLinkedSql,linkedlist);
        return map;
    }

    @Override
    public String getTemplatePath() {
        return PathKit.getWebRootPath()+ CommonUtil.getAppProperties("studentExcelPath");
    }

}
