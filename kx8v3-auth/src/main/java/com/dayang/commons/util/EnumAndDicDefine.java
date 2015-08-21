package com.dayang.commons.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.dayang.commons.enums.*;
import com.dayang.commons.pojo.DicDefinePojo;
import com.dayang.commons.pojo.EnumDefinePojo;

public class EnumAndDicDefine {
	public final static String ENUM_FIELD_DEFINE = "enum_field";
	public final static String DIC_FIELD_DEFINE = "dic_field";
	public final static String SEMESTER_DEFINETABLE = "SEMESTER";
	public final static String SCHOOL_DEFINETABLE = "SCHOOL";
	public final static String EMPE_DEFINETABLE = "EMPLOYEE";
	public final static String LOGCHANGE_DEFINETABLE = "LOGCHANGE";
	public final static String CLASS_DEFINETABLE = "CLASS";
	public final static String STUDENT_DEFINETABLE = "STUDENT";
	public final static String RELA_STUDENT = "RELASTUDENT";
	public final static String PARENT_DEFINETABLE = "PARENT";
	public final static String APPROVEFLOW_DEFINETABLE="APPROVEFLOW";
	
	@SuppressWarnings({ "rawtypes", "serial" })
	public final static Map<String,HashMap> map_all= new HashMap<String,HashMap>(){
		{
			put(SEMESTER_DEFINETABLE,new HashMap<String,ArrayList>(){				{
					put(ENUM_FIELD_DEFINE,new ArrayList<EnumDefinePojo>(){{
						add(new EnumDefinePojo("semester",EnumAll.SemesterFlag.FIRSTFLAG));
						add(new EnumDefinePojo("isCur",EnumAll.IsYesOrNot.IsYes));
					}});
					put(DIC_FIELD_DEFINE,new ArrayList<DicDefinePojo>(){{
						add(new DicDefinePojo("schYear",StaticData.SCHYEAR_dictionaryType));
					}});
				}				
			});
			
			put(SCHOOL_DEFINETABLE, new HashMap<String, ArrayList>(){
				{
					put(ENUM_FIELD_DEFINE,new ArrayList<EnumDefinePojo>(){{
						add(new EnumDefinePojo("status", Status.ENABLE));
					}});
				}
			});
			put(EMPE_DEFINETABLE,new HashMap<String,ArrayList>(){
				{
					put(ENUM_FIELD_DEFINE,new ArrayList<EnumDefinePojo>(){{
						add(new EnumDefinePojo("sex", Sex.MAN));						
						add(new EnumDefinePojo("maritalStatus", MarryStatus.ISMARRY));
						add(new EnumDefinePojo("empType", EmpeType.EDBEMPE));
					}});
					put(DIC_FIELD_DEFINE,new ArrayList<DicDefinePojo>(){{
						add(new DicDefinePojo("nation",StaticData.NATION_dictionaryType));
						add(new DicDefinePojo("political",StaticData.POLITICAL_dictionaryType));
						add(new DicDefinePojo("education",StaticData.EDUCATION_dictionaryType));
						add(new DicDefinePojo("health", StaticData.HEALTH_DICTIONARYTYPE));
						add(new DicDefinePojo("status", StaticData.TEASTATUS_INNERALTER));
						add(new DicDefinePojo("jobsCareers", StaticData.JOBSCAREERS_DICTIONARYTYPE));
					}});
				}
			});

			put(LOGCHANGE_DEFINETABLE,new HashMap<String,ArrayList>(){
				{
					put(ENUM_FIELD_DEFINE,new ArrayList<EnumDefinePojo>(){{
						add(new EnumDefinePojo("sex", Sex.MAN));
						add(new EnumDefinePojo("status", EnumAll.AlterStatus.ALTERFINISHSTATUS));
						add(new EnumDefinePojo("flag",EnumAll.AlterFlag.SCHOOLFLAG));
					}});
					put(DIC_FIELD_DEFINE,new ArrayList<DicDefinePojo>(){{
						add(new DicDefinePojo("reason",StaticData.ALTERREASON_dictionaryType));
						add(new DicDefinePojo("changeType",StaticData.ALTERTYPE_dictionaryType));
					}});
				}
			});

			put(CLASS_DEFINETABLE, new HashMap<String, ArrayList>(){
				{
					put(ENUM_FIELD_DEFINE,new ArrayList<EnumDefinePojo>(){{
						add(new EnumDefinePojo("sex", Sex.MAN));
					}});
				}
			});
			put(STUDENT_DEFINETABLE, new HashMap<String, ArrayList>(){
				{
					put(ENUM_FIELD_DEFINE,new ArrayList<EnumDefinePojo>(){{
						add(new EnumDefinePojo("sex", Sex.MAN));
						add(new EnumDefinePojo("isReside", EnumAll.StudyWays.GoRead));
						add(new EnumDefinePojo("onlySon", EnumAll.IsYesOrNot.IsYes));
						add(new EnumDefinePojo("migrant", EnumAll.IsYesOrNot.IsYes));
						add(new EnumDefinePojo("soldier", EnumAll.IsYesOrNot.IsYes));
						add(new EnumDefinePojo("teacher", EnumAll.IsYesOrNot.IsYes));
						add(new EnumDefinePojo("singleParent", EnumAll.IsYesOrNot.IsYes));
						add(new EnumDefinePojo("poor", EnumAll.IsYesOrNot.IsYes));
						add(new EnumDefinePojo("parentSex", Sex.MAN));
					}});
					put(DIC_FIELD_DEFINE,new ArrayList<DicDefinePojo>(){{
						add(new DicDefinePojo("nation",StaticData.NATION_dictionaryType));
						add(new DicDefinePojo("political",StaticData.POLITICAL_dictionaryType));
						add(new DicDefinePojo("health", StaticData.HEALTH_DICTIONARYTYPE));
						add(new DicDefinePojo("fromWhere", StaticData.FROMWHERE_DICTIONARYTYPE));
						add(new DicDefinePojo("stay", StaticData.STAY_DICTIONARYTYPE));
						add(new DicDefinePojo("householdNature", StaticData.HOUSEHOLDNATURE_DICTIONARYTYPE));
						add(new DicDefinePojo("disabilityType", StaticData.DISABILITY_DICTIONARYTYPE));
						add(new DicDefinePojo("status", StaticData.DISABILITY_STATUS));
					}});
				}
			});
			put(RELA_STUDENT,new HashMap<String,ArrayList>(){
				{
					put(ENUM_FIELD_DEFINE,new ArrayList<EnumDefinePojo>(){{
						add(new EnumDefinePojo("guardian", GuardianType.GUARDER));
					}});
					put(DIC_FIELD_DEFINE,new ArrayList<DicDefinePojo>(){{
						add(new DicDefinePojo("relationType",StaticData.RELATION_DICTIONARYTYPE));

					}});
				}
			}
			);
			
			put(PARENT_DEFINETABLE,new HashMap<String,ArrayList>(){
				{
					put(ENUM_FIELD_DEFINE,new ArrayList<EnumDefinePojo>(){{
						add(new EnumDefinePojo("sex", Sex.MAN));
					}});
					put(DIC_FIELD_DEFINE,new ArrayList<DicDefinePojo>(){{
						add(new DicDefinePojo("political",StaticData.POLITICAL_dictionaryType));
					}});
				}
			});
			
			put(APPROVEFLOW_DEFINETABLE,new HashMap<String,ArrayList>(){
				{
					
					put(DIC_FIELD_DEFINE,new ArrayList<DicDefinePojo>(){{
						add(new DicDefinePojo("status",StaticData.FLOWSTATUS_dictionaryType));
					}});
				}
			});

		}
	};
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static HashMap<String,ArrayList> getDefineEnumAndDic(String defineTable){
		if(map_all.containsKey(defineTable)){
			return map_all.get(defineTable);
		}
		return null;
	}
}


