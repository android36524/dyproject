package com.dayang.commons.util;

import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.codec.digest.Md5Crypt;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.dayang.cas.pojo.AccountPojo;
import com.dayang.cas.util.LoginInfoUtil;
import com.dayang.commons.pojo.ImportPojo;
import com.dayang.commons.pojo.QueryDefineParaPojo;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

/**
 * 类描述：一般通用类
 * <pre>
 * -------------History------------------
 *   DATE                 AUTHOR         VERSION        DESCRIPTION
 *  2014-11-25               张才胜               V01.00.001		      新增内容   
 * </pre>
 * 
 * @author <a href="mailto:zcslsf@gmail.com">张才胜</a>
 */
public class CommonUtil {
	private static final Logger LOGGER = Logger.getLogger(CommonUtil.class);
	
	/**加载静态文件*/
	private static final Properties properties = new Properties();
	static {
		try {
			properties.load(CommonUtil.class.getClassLoader().getResourceAsStream("conf.properties")) ;
		} catch (IOException e) {
			LOGGER.error("系统加载app.properties失败...请检查包中是否存在conf.properties",e);
		}
	}

	/**
	 * 判断字符串是否在字符串List中存在
	 * @param list 字符串List
	 * @param str 字符串
	 * @return 存在返回true
	 */
	public static boolean strInList(List<String> list,String str){
		boolean result = false;
		if (list.size() == 0 || str == null){
			return result;
		}
		for (String strTemp : list){
			if (str.equals(strTemp)){
				result = true;
				break;
			}
		}
		return result;
	}
	
	/**
	 * 判断传入对象是否为空或值为空
	 * @param obj
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static boolean isEmpty(Object obj) {
		// 判断是否为空
		if (obj == null)
			return true;
		// ----------------根据各种对象类型判断是否值为空--------------
		if (obj instanceof String)
			return ((String) obj).trim().equals("");
		if (obj instanceof Collection) {
			Collection coll = (Collection) obj;
			return coll.size() == 0;
		}
		if (obj instanceof Map) {
			Map map = (Map) obj;
			return map.size() == 0;
		}
		if (obj.getClass().isArray())
			return Array.getLength(obj) == 0;
		else
			return false;
	}

	/**
	 * 组织html业务，显示值，供自定义标签用
	 * @param value
	 * @param len
	 * @return
	 */
	public static String showValue(String value, int len) {
		// 判断是否为空
		if (isEmpty(value))
			return "&nbsp;";
		// 根据长度取值，并构建html语句
		if (value.length() <= len) {
			return value;
		} else {
			StringBuffer buf = new StringBuffer((new StringBuilder(
					"<span title='")).append(value).append("'>").toString());
			buf.append(value.substring(0, len));
			buf.append("...</span>");
			return buf.toString();
		}
	}

	/**
	 * 产生时间形式的uuid方法+后4位随机数
	 * @return
	 */
	public static String getUUID() {
		// 定义uuid
		String uuId = "0";
		// 定义时间格式
		SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String tempId = sf.format(new Date());
		// 构造uuid
		if(Long.parseLong(uuId) >= Long.parseLong(tempId)){
			uuId = (new StringBuilder(String.valueOf(Long.parseLong(uuId) + 1L))).toString();
		}else{
			uuId = tempId;
		}
		// 返回
		return uuId + get4RandomNum();
	}
	/**
	 * 产生时间形式(MMdd)的uuid方法+后4位随机数
	 * @return
	 */
	public static String getBulletinNo() {
		// 定义uuid
		String uuId = "0";
		// 定义时间格式
		SimpleDateFormat sf = new SimpleDateFormat("yyMM");
		String tempId = sf.format(new Date());
		// 构造uuid
		if (Long.parseLong(uuId) >= Long.parseLong(tempId))
			uuId = (new StringBuilder(String.valueOf(Long.parseLong(uuId) + 1L)))
					.toString();
		else
			uuId = tempId;

		// 返回
		return uuId + get4RandomNum();
	}
	
	
	/**
	 * 
	* @Title: 获取当前时间戳+4位随机数
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	public static String getTradeNo(){
		return "TJ"+new Date().getTime()+get4RandomNum();
	}
	
	/**
	 * 检查传入值是否为null并处理方法，null则转为空值，否则为其身
	 * @param s 为字符串类
	 * @return
	 */
	public static String jugeAndFixNull(String s) {
		// 判断s是否为null，是则返回空串
		if (s == null) {
			return "";
		} else {// 否则返回其自身
			return s;
		}
	}

	/**
	 * 将一个字符串数组转换为long数组，by weiqiang.yang
	 * 
	 * @param strArr
	 * @return
	 */
	public static Long[] strArrToLongArr(String[] strArr) {
		Long[] retArr = new Long[strArr.length];
		for (int i = 0; i < strArr.length; i++) {
			retArr[i] = Long.parseLong(strArr[i]);
		}
		return retArr;
	}

	/**
	 * 生成4位随机数
	 * @return
	 */
	public static String get4RandomNum() {
		return RandomStringUtils.randomNumeric(4);
	}
	
    /**
     * XML字符串转义
     * @param xml
     * @return
     */
    public static String revertXMLString(String xml){
        xml = xml.replace("%2B", "+");
        xml = xml.replace("%25", "%");
        xml = xml.replace("%26", "&");
        xml = xml.replace("!#92;", "\\");
        xml = xml.replace("!#60;", "<");
        xml = xml.replace("!#62;", ">");
        xml = xml.replace("!#34;", "\"");
        xml = xml.replace("!#35;", "'");
        return xml;
    }
    
    /**
	 * 判断字符串是否为空，如果为空返回true，否则返回false
	 * @param str
	 * @return
	 */
	public static boolean isEmptyString(String str) {
		return StringUtils.isEmpty(str);
	}

	/**
	 * 字符串非空判断：如果为空，则返回false，如果不为空返回true
	 * @param str
	 * @return
	 */
	public static boolean isNotEmptyString(String str) {
		return StringUtils.isNotEmpty(str);
	}

	/**
	 * 判断List是否为空，如果为空返回true，如果不为空返回false
	 * @param list
	 * @return
	 */
	public static boolean isEmptyCollection(List<?> list) {
		if (null != list && list.size() > 0) {
			return false;
		}
		return true;
	}

	/**
	 * List 非空判断，如果为空返回false，如果不为空返回true
	 * @param list
	 * @return
	 */
	public static boolean isNotEmptyCollection(List<?> list) {
		if (null != list && list.size() > 0) {
			return true;
		}
		return false;
	}

	/**
	 * 判断map是否为空，如果为空返回true，如果不为空返回false
	 * @param map
	 * @return
	 */
	public static boolean isEmptyMap(Map<?, ?> map) {
		if (null != map && map.size() > 0) {
			return false;
		}
		return true;
	}

	/**
	 * Map非空判断，如果map为空返回false，如果map不为空，返回true
	 * @param map
	 * @return boolean
	 */
	public static boolean isNotEmptyMap(Map<?, ?> map) {
		if (null != map && map.size() > 0) {
			return true;
		}
		return false;
	}
	
	/**
	 * 判断对象是否为空
	 * @param obj
	 * @return
	 */
	public static boolean isNotEmptyObject(Object obj){
		if(null != obj){
			return true;
		}
		return false; 
	}
	
	/**
	 * 返回一个UUID
	 * @return
	 */
	public static String randomUUID() {
		String uuid = java.util.UUID.randomUUID().toString().replaceAll("-", "");
		return uuid;
	}
	
	/**
	 * 
	 * @desc 根据ID获取app.properties文件里的值<br>
	 * @param Id
	 * @return ID在app.properties文件里的值
	 */
	public static final String getAppProperties(String Id) {
		String value = properties.getProperty(Id);
		return value;
	}

	/**
	 * 
	 * @desc 根据ID获取app.properties文件里的值<br>
	 * @param Id
	 * @param defaultValue
	 *            默认值
	 * @return
	 */
	public static String getAppProperties(String Id, String defaultValue) {
		String value = properties.getProperty(Id, defaultValue);
		return value;
	}

	/**
	 * 
	 * @desc 获取app.properties里的所有信息<br>
	 * @return Properties对象
	 */
	public static Properties getAllProperties() {
		return properties;
	}
	
	/**
	 * 获取一个字母加数字的随机字符串：
	 * 
	 * @param length
	 * @return
	 */
	public static String getCharacterAndNumber(int length) {
		return RandomStringUtils.randomAlphanumeric(length);
	}
	
	/**
	 * 检查是否全是数字或者全是字母<br>
	 * @param length
	 * @param password
	 * password
	 */
	public static String checkPassword(int length,String password) {
		Pattern pattern = Pattern.compile("^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,20}$");   
		Matcher isNum = pattern.matcher(password);
		if(!isNum.matches()){
			return getCharacterAndNumber(length);
		}
		return password;
	}
	
	/**
	 * 判断一个字符串是否包含数字
	 * 
	 * @param content
	 * @return
	 */
	public static boolean hasDigit(String content) {
		boolean flag = false;
		Pattern p = Pattern.compile(".*\\d+.*");
		Matcher m = p.matcher(content);
		if (m.matches())
			flag = true;
		return flag;
	}
	
	/**
	 * 校验是不是数字
	 * @desc	<br>
	 * @create 	Jul 5, 2012 11:38:36 AM by fangqi<br>
	 * @param str
	 * @return
	 */
	public static boolean isNumeric(String str)
	{ 
		if (str.matches("\\d*")) {
			return true;
		} else {
			return false;
		}
	}	
	
	/**
	 * 字符串非空判断<br>
	 * 如果为空，空字符，空格则返回true<br>
	 * 否则返回false
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isEmptyStringOrSpace(String str) {
		if (null == str || "".equals(str.trim())) {
			return true;
		}
		return false;
	}
	
	/**
	 * 按设置的长度用空格补齐字符串
	 * @param targetStr
	 * @param strLength
	 * @return
	 */
	public static String FormatString(String targetStr,int strLength){
	   int curLength = targetStr.getBytes().length;
	   if(targetStr!=null && curLength>strLength){
	     targetStr = targetStr.substring(0,strLength);
	   }   
	   String newString = "";
	   int cutLength = strLength-targetStr.getBytes().length;
	   for(int i=0;i<cutLength;i++)
		   newString +=" ";
	   return targetStr+newString;  
	}
	
	/**
	 * 按设置长度用0补齐数字
	 * @param seqs
	 * @param length
	 * @return
	 */
	public static String getSeq(String seqs,int length){
		String formatString = "%0"+length+"d";
		if(seqs.length()>length){
			seqs = seqs.substring(seqs.length()-length, seqs.length());
		}
		String str = String.format(formatString, Long.parseLong(seqs));
		return str;
	}
	
	public static String getYYYYMMddHmmss(){
		String fmt = "yyyyMMddHmmss";
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat(fmt);
		return sdf.format(date);
	}
	
	/**
	 * 将字符串转化为Long类型的数组
	 * @param str
	 * @return
	 * @throws Exception
	 */
	public static Long[] str2LongArrays(String str) throws Exception {
		if(StringUtils.isNotBlank(str)){
			String[] strs = str.split(",");
			Long[] ary = null;
			if(strs != null && strs.length > 0){
				ary = new Long[strs.length];
				for(int i = 0; i < strs.length; i++){
					ary[i] = Long.valueOf(StringUtils.isBlank(strs[i]) ? "0" : strs[i]);
				}
			}
			return ary;
		} else {
			return null;
		}
	}
	
	/**
	 * MD5算法加密
	 * @param passWord
	 */
	public static String getMD5(String passWord){
		return Md5Crypt.apr1Crypt(passWord);
	}
	
	/**
	 * Int类型的数据为0时需要设定的默认值
	 * @param num
	 * @param defaultVal
	 * @return
	 */
	public static int defaultIntIfZero(int num,int defaultVal){
		return num == 0 ? defaultVal : num;
	}
	
	/**
	 * 给List设置字典和ENUM的显示值
	 * 在显示值后加_value 
	 * 如 base_semester 中的
	 * schYear 为字典，value值为1，后面加 _value schYear_value显示值为 2015-2016.
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static <M extends Model<?>> void setShowValue2List(List<M> list,String defineTable){
		Map<String, ArrayList> m = EnumAndDicDefine.getDefineEnumAndDic(defineTable);
		if(m==null){
			return ;
		}
		DaYangCommonUtil.setShowValue2List(list,m);
	}

	/**
	  * 给Page设置字典和ENUM的显示值
	 * 在显示值后加_value 
	 * 如 base_semester 中的
	 * schYear 为字典，value值为1，后面加 _value schYear_value显示值为 2015-2016.
	 * @param p
	 * @param defineTable
	 */
	public static <M extends Model<?>> void setShowValue2List(Page<M> p,String defineTable){
		setShowValue2List(p.getList(),defineTable);
	}
	
	/**
	 * 根据类型取到老师变动类型
	 * @param type
	 * @return
	 */
	public static int getTeaType2Status(int type){
		return StaticData.TeaTypeToStatusMap.get(type);
	}
	
	/**
	 * 根据类型取到老师变动类型
	 * @param type
	 * @return
	 */
	public static int getStuType2Status(int type){
		return StaticData.StuTypeToStatusMap.get(type);
	}
	
	/**
	 * 是否能做此变动
	 * @param type
	 * @param status
	 * @return
	 */
	public static boolean isCanAlter(int type,int status){
		if(CommonUtil.isEmpty(StaticData.TeaIsCanAlter.get(status))) return false;
		int[] statuses = StaticData.TeaIsCanAlter.get(status);
		for(int i=0;i<statuses.length;i++){
			if(statuses[i]==type){return true;}
		}
		return false;
	}
	
	/**
	 * 学生是否能做此变动
	 * @param type
	 * @param status
	 * @return
	 */
	public static boolean isCanStuAlter(int type,int status){
		if(CommonUtil.isEmpty(StaticData.StuIsCanAlter.get(status))) return false;
		int[] statuses = StaticData.StuIsCanAlter.get(status);
		for(int i=0;i<statuses.length;i++){
			if(statuses[i]==type){return true;}
		}
		return false;
	}
	
	/**
	 * 根据Map值设置sql
	 * @param paraMap    MAP参数
	 * @param sql        拼接的sql 
	 * @param parmList   拼接的参数列表
	 */
	public static void setDefaultPara(Map<String,Object> paraMap,StringBuilder sql ,List<Object> parmList){
		setDefaultPara(paraMap,sql,parmList,null,null);
	}
	
	/**
	 * 根据Map值设置sql
	 * @param paraMap    MAP参数
	 * @param sql        拼接的sql 
	 * @param parmList   拼接的参数列表
	 * @param priTable   表前缀
	 */
	public static void setDefaultPara(Map<String,Object> paraMap,StringBuilder sql ,List<Object> parmList,String priTable){
		setDefaultPara(paraMap,sql,parmList,priTable,null);
	}

	/**
	 * 根据Map值设置sql
	 * @param paraMap    MAP参数
	 * @param sql        拼接的sql 
	 * @param parmList   拼接的参数列表
	 * @param priTable   表前缀
	 * @param mapField   有特殊要求的字段
	 */
	public static void setDefaultPara(Map<String,Object> paraMap,StringBuilder sql ,List<Object> parmList,String priTable,Map<String,QueryDefineParaPojo> mapField){
		setDefaultPara(paraMap, sql, parmList, priTable, mapField, null);
	}
	
	/**
	 * 根据Map值设置sql
	 * @param paraMap    MAP参数
	 * @param sql        拼接的sql 
	 * @param parmList   拼接的参数列表
	 * @param priTable   表前缀
	 * @param mapField   有特殊要求的字段
	 */
	public static void setDefaultPara(Map<String,Object> paraMap,StringBuilder sql ,List<Object> parmList,String priTable,Map<String,QueryDefineParaPojo> mapField,List<String> ignoreFieldList){
		setDefaultPara(paraMap, sql, parmList, priTable, mapField, ignoreFieldList, null);
	}
	
	/**
	 *  根据Map值设置sql
	 * @param paraMap    MAP参数
	 * @param sql        拼接的sql 
	 * @param parmList   拼接的参数列表
	 * @param priTable   表前缀
	 * @param mapField   有特殊要求的字段
	 * @param ignoreFieldList 忽略字段
	 */
	public static void setDefaultPara(Map<String,Object> paraMap,StringBuilder sql ,List<Object> parmList,String priTable,Map<String,QueryDefineParaPojo> mapField,List<String> ignoreFieldList,Map<String,String> dataAuth){
		if(CommonUtil.isEmptyMap(paraMap) || sql==null || parmList==null){
			return ;
		}
		Iterator<String> ite = paraMap.keySet().iterator();
		while (ite.hasNext()) {
			String key = (String) ite.next();
			if(key!=null && !CommonUtil.isEmpty(paraMap.get(key))){
				String op = "=";
				String field = key;
				Object value = paraMap.get(key);
				String[] arrField = null;
				// 忽略定义中的字段直接忽略
				if(!CommonUtil.isEmptyCollection(ignoreFieldList)){
					if(ignoreFieldList.contains(key)){
						continue;
					}
				}
				// int型<=0不拼
				if(value instanceof Integer ){
					if(Integer.parseInt(value.toString())<=0) continue;
				}
				// long型<=0不拼
				if( value instanceof Long){
					if(Long.parseLong(value.toString())<=0) continue;
				}
				// 取定义中的字段
				if(!CommonUtil.isEmptyMap(mapField)){
					QueryDefineParaPojo dp = mapField.get(key);
					if(!CommonUtil.isEmpty(dp)){
						op = dp.getOperator();
						field = dp.getField();
						arrField = field.split(",");
						if(op.equals("like")){
							value="%"+value+"%";
						}
					}
				}
				// 拼sql和传递参数
				if(!CommonUtil.isEmpty(arrField) && arrField.length>1){
					sql.append( " and (");
					for(int i=0;i<arrField.length;i++){						
						sql.append(getWherOp(arrField[i],op,priTable,value,i,parmList));
					}
					sql.append(" ) ");
				}else{
					sql.append( " and ");
					sql.append(getWherOp(field,op,priTable,value,0,parmList));
				}
			}
		}
		
		appendDataAuthSql(dataAuth, sql, parmList);
	}

	/**
	 * 拼接数据权限sql
	 * @param dataAuthTypeMap 数据权限类型map
	 * @param sql sql语句
	 * @param paramList 参数list
	 */
	public static void appendDataAuthSql(Map<String,String> dataAuthTypeMap,StringBuilder sql,List<Object> paramList){
		if(!LoginInfoUtil.isSuperAdmin() && !CommonUtil.isEmptyMap(dataAuthTypeMap)){
			for(Map.Entry<String, String> entry:dataAuthTypeMap.entrySet()){
				String field = entry.getValue();
				String type = entry.getKey();
				if(!CommonUtil.isEmpty(type)){
					if (type.equals(CommonStaticData.OrgTreeNodeType.SCHFLAG) || type.equals(CommonStaticData.OrgTreeNodeType.ORGFLAG)){
						String ids = DaYangCommonUtil.getOrgDataAuth(LoginInfoUtil.getAccountInfo().getId() + "", type);
						sql.append(" and FIND_IN_SET(" + field + ",?) ");
						paramList.add(ids);
					}else {
						Map<String,String> dataMap=DaYangCommonUtil.getBusinessDataAuthFromRedis(LoginInfoUtil.getAccountInfo().getId()+"",type);
						if(!CommonUtil.isEmpty(dataMap.get("notContainsData"))){
							sql.append(" and "+field+" not in "+ DaYangCommonUtil.appendInSqlByIdStr(dataMap.get("notContainsData")).toString());
						}else if(!CommonUtil.isEmpty(dataMap.get("containsData"))){
							sql.append(" and FIND_IN_SET("+field+",?) ");
							paramList.add(dataMap.get("containsData"));
						}
					}
				}
			}
		}
	}

	/**
	 * 机构树拼接数据权限
	 * @param type 机构树类型
	 * @param field 数据权限字段
	 * @param sql sql语句
	 * @param paramList 参数列表
	 */
	public static void appendDataAuthSql(String type,String field,StringBuilder sql,List<Object> paramList){
		//1.超级管理员 2.普通用户
		if (LoginInfoUtil.isSuperAdmin()){
			String orgFlag = CommonStaticData.OrgTreeNodeType.ORGFLAG;
			if (CommonStaticData.OrgTreeNodeType.SCHFLAG.equals(type)){
				orgFlag += "," + CommonStaticData.OrgTreeNodeType.SCHFLAG;
			}
			sql.append(" and o.orgFlag in ").append(DaYangCommonUtil.appendInSqlPlaceholderByIdStr(orgFlag,paramList));
		}else {
			String ids = DaYangCommonUtil.getOrgDataAuth(LoginInfoUtil.getAccountInfo().getId() + "", type);
			sql.append(" and FIND_IN_SET(" + field + ",?) ");
			paramList.add(ids);
		}
	}

	public static String getWherOp(String field,String op,String priTable,Object value,int index,List<Object> parmList){
		String fieldop =field + " " + op + " ? ";
		String t = index==0?"":" or ";
		if(op.equals("in")){
			String s = CommonUtil.isEmptyString(priTable)||fieldop.contains(".") ? (t + field) : t + priTable + "." + field;
			s+=" in (";
			if(value instanceof   long[] ){
				long[] ov = (long[])value;
				for(int i=0;i<ov.length;i++){
					s+=(i==ov.length-1?"?":"?,");  
					parmList.add(ov[i]);
				}
				s+=")";
			}else if(value instanceof   int[] ){
				int[] ov = (int[])value;
				for(int i=0;i<ov.length;i++){
					s+=(i==ov.length-1?"?)":"?,");  
					parmList.add(ov[i]);
				}
				s+=")";
			}else if(value instanceof List){
				List<Object> list = extracted(value);			
				Iterator<Object> it = list.iterator();
				while(it.hasNext()){
					s+="?,";  
					parmList.add(it.next());
				}
				s=s.substring(0,s.length()-1);
				s+=")";
			}else{
				s+="?) ";
				parmList.add(value);
			}
			return s;
		}else{
			parmList.add(value);
			return CommonUtil.isEmptyString(priTable)||fieldop.contains(".") ? (t + fieldop) : t + priTable + "." + fieldop;
		}
	}

	@SuppressWarnings("unchecked")
	private static List<Object> extracted(Object value) {
		return (List<Object>)value;
	}
	
	/**
	 * 根据表名获取插入的sql
	 * @param map
	 * @param tableName
	 * @param paras
	 * @return
	 */
	public static String getInsertSqlString(Map<String,Object> map,String tableName,List<Object> paras){
		StringBuffer sql = new StringBuffer();
		sql.append("insert into `").append(tableName).append("`(");
		StringBuilder temp = new StringBuilder(") values(");
		for (Entry<String, Object> e: map.entrySet()) {
			String colName = e.getKey();
			if (paras.size() > 0) {
				sql.append(", ");
				temp.append(", ");
			}
			sql.append("`").append(colName).append("`");
			temp.append("?");
			paras.add(e.getValue());
		}
		sql.append(temp.toString()).append(")");
		return sql.toString();
	}

	/**
	 * 设置指定pojo属性值
	 * @param importPojo 指定pojo
	 * @param valueList 值list
	 * @return pojo
	 */
	public static ImportPojo setBeanPropertyValue(ImportPojo importPojo, List<String> valueList) {
		Map<Integer,String> relation = importPojo.getMap();
		Class clazz = importPojo.getClass();
		for (int i = 0; i < valueList.size(); i++){
			String fieldName = relation.get(i);
			try {
				Field field = clazz.getDeclaredField(fieldName);
				field.setAccessible(true);
				field.set(importPojo,valueList.get(i));
			} catch (NoSuchFieldException | IllegalAccessException e) {
				throw new RuntimeException("设置属性值失败");
			}
		}
		return importPojo;
	}

	/**
	 * 数据库的查询列去重翻译成字符串
	 * @param records Record集合
	 * @param columnName 列名
	 * @return 去重后的字符串
	 */
	public static String column2Str(List<Record> records,String columnName){
		StringBuilder result = new StringBuilder();
		Set<String> notRepeat = new HashSet<>();
		for (Record temp : records){
			String columnValue = temp.getStr(columnName);
			if (CommonUtil.isNotEmptyString(columnValue)){
				String[] idArray = columnValue.split(",");
				for (String id : idArray){
					notRepeat.add(id);
				}
			}
		}
		for (String str : notRepeat){
			result.append(str).append(",");
		}
		if (result.length() > 0){
			result.deleteCharAt(result.length() - 1);
		}
		return result.toString();
	}
	
	/**
	 * 取两个数组中不相同的数据
	 * @param t1
	 * @param t2
	 * @return
	 */
	public static <T> List<T> compare(T[] t1, T[] t2) {
		List<T> list1 = Arrays.asList(t1);
		List<T> list2 = new ArrayList<T>();
		for (T t : t2) {
			if (!list1.contains(t)) {
				list2.add(t);
			}
		}
		return list2;
	}
	
	/**
	 * 保存账号并建立与角色关系，原调用环信接口
	 * 调用基础数据平台本地util，然后整合调用同步sns系统通讯录信息接口  wangchong
	 * @param accountPojo 账号pojo
	 * @param roelId 角色id
	 */
	public static void saveAccountPoJo(AccountPojo accountPojo,long roelId){
		DaYangCommonUtil.saveAccountPoJo(accountPojo, roelId);
	}
}