package com.dayang.commons.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.dayang.commons.pojo.DictionaryPojo;
import com.dayang.system.model.DictionaryModel;
import com.dayang.system.model.DictionaryTypeModel;
import com.jfinal.plugin.activerecord.Model;

/**
 * 类描述：字典调用UTIl类
 * <pre>
 * -------------History------------------
 *   DATE                 AUTHOR         VERSION        DESCRIPTION
 *  2015年7月14日              温建军              V01.00.001		      新增内容   
 * </pre>
 * 
 * @author <a href="wenjj@dayanginfo.com">温建军</a>
 */
public class DictionaryUtil {
	
	/**
	 * 将字典POJO转换成MODEL
	 * @param p
	 * @return
	 */
	public static DictionaryModel convertDicPojoToModel(DictionaryPojo p){
		DictionaryModel model = new DictionaryModel();
		model.put("id", p.getId());
		model.put("dictionaryType", p.getDictionaryType());
		model.put("name", p.getName());
		model.put("code", p.getCode());
		model.put("value", p.getValue());
		model.put("desciption", p.getDesciption());
		model.put("pdictypeId", p.getPdictypeId());
		model.put("pdicValue", p.getPdicValue());
		model.put("seq", p.getSeq());
		return model;
	}
	
	/**
	 * 将字典MODEL转换成POJO
	 * @param model
	 * @return
	 */
	public static DictionaryPojo convertDicModelToDic(DictionaryModel m){
		DictionaryPojo p = new DictionaryPojo();
		p.setId(m.getInt("id"));
		p.setName(m.get("name")==null?"":m.getStr("name"));
		p.setCode(m.get("code")==null?"":m.getStr("code"));
		p.setDictionaryType(m.get("dictionaryType")==null?0:m.getInt("dictionaryType"));
		p.setValue(m.get("value")==null?0:m.getInt("value"));
		p.setPdictypeId(m.get("pdictypeId")==null?0:m.getInt("pdictypeId"));
		p.setPdicValue(m.get("pdicValue")==null?0:m.getInt("pdicValue"));
		p.setSeq(m.get("seq")==null?0:m.getInt("seq"));
		p.setCode(m.get("desciption")==null?"":m.getStr("desciption"));	
		return p;
	}
	/**
	 * 根据字典类型取字典列表
	 * @param dictype
	 * @return
	 */
	public static List<DictionaryModel> getDicListByDicType(int dictype){		
		List<DictionaryPojo> dictionaryList = DayangDictionaryUtil.getDicList();
		List<DictionaryModel> list = new ArrayList<>();
		if(dictionaryList!=null && dictionaryList.size()>0){
			for(int i=0;i<dictionaryList.size();i++){
				DictionaryPojo m = dictionaryList.get(i);
				if(m.getDictionaryType()==dictype){
					list.add(convertDicPojoToModel(m));
				}
			}
		}
		return list;
	}

	/**
	 * 根据字典类型取字典Name属性列表
	 * @param dictype
	 * @return
	 */
	public static List<String> getDicNameListByDicType(int dictype){
		List<DictionaryModel> list = getDicListByDicType(dictype);
		List<String> result = new ArrayList<>();
		for (DictionaryModel dictionaryModel : list){
			String dicName = dictionaryModel.getStr("name");
			result.add(dicName);
		}
		return result;
	}
	
	/**
	 * 根据字典编码，获取字典列表
	 * @param code
	 * @return
	 */
	public static List<DictionaryModel> getDicListByCode(String code){
		List<DictionaryPojo> dictionaryList = DayangDictionaryUtil.getDicList();	
		DictionaryTypeModel dictionaryType = DictionaryTypeModel.dao.findFirst("select * from sys_dictionarytype where code=? ", code);
		if(null == dictionaryType){
			return null;
		}
		int dicType = dictionaryType.getInt("id");
		
		List<DictionaryModel> list = new ArrayList<DictionaryModel>();
		if(dictionaryList!=null && dictionaryList.size()>0){
			for(int i=0;i<dictionaryList.size();i++){
				DictionaryPojo m = (DictionaryPojo)dictionaryList.get(i);
				if(m.getDictionaryType()==dicType){
					list.add(convertDicPojoToModel(m));
				}
			}
		}
		return list;
	}
	
	/**
	 * 根据父类id和字典类型获取列表
	* @param @param dictype 字典类型
	* @param @param parentId 父类id
	* @param @return    设定文件 
	* @return List<DictionaryModel>    返回类型 
	* @throws
	 */
	public static List<DictionaryModel> findDicByParentId(int dictype,int ptypeId,int parentId){
		List<DictionaryPojo> dictionaryList = DayangDictionaryUtil.getDicList();;
		List<DictionaryModel> list = new ArrayList<DictionaryModel>();
		if(dictionaryList!=null && dictionaryList.size()>0){
			for(int i=0;i<dictionaryList.size();i++){
				DictionaryPojo m = (DictionaryPojo)dictionaryList.get(i);
				if(m.getDictionaryType()== dictype && ptypeId == m.getPdictypeId() && parentId== m.getPdicValue() ){
					list.add(convertDicPojoToModel(m));
				}
			}
		}
		return list;
	}	
	
	/**
	 * 根据字典类型和字典值获取字典
	 * @param dictype
	 * @param dicvalue
	 * @return
	 */
	public static DictionaryModel getDicModel(int dictype,int dicvalue){
		List<DictionaryModel> list = getDicListByDicType(dictype);
		if(!CommonUtil.isEmptyCollection(list)){
			for(int i=0;i<list.size();i++){
				DictionaryModel d = list.get(i);
				if(d.getInt("value")==dicvalue){
					return d;
				}
			}
		}
		return null;
	}
	
	/**
	 * 给list设置字典值
	 * @param dicPara
	 */
	public static <M extends Model<?>>  void setDicNameToList(String[][] dicPara, List<M> list){
		if(!CommonUtil.isEmptyCollection(list)){
			for(int i=0;i<list.size();i++){
				Model<?> m = list.get(i);
				for(int k=0;k<dicPara.length;k++){
					String key = dicPara[k][0];
					try{
						int value = m.getInt(key);					
						String name = DayangDictionaryUtil.getDicName(Integer.parseInt(dicPara[k][1]),value);
						m.put(key+"_name", name);
					}catch(Exception e){
						m.put(key+"_name", m.get(key));
					}
				}			
			}
		}
	}
	
	/**
	 * 给list设置字典值
	 * @param dicPara
	 */
	public static void setDicNameMapToList(String[][] dicPara, @SuppressWarnings("rawtypes") List<Map> list){
		if(!CommonUtil.isEmptyCollection(list)){
			for(int i=0;i<list.size();i++){
				@SuppressWarnings({ "unchecked" })
				Map<String, Object> m = list.get(i);
				for(int k=0;k<dicPara.length;k++){
					String key = dicPara[k][0];
					try{
						int value = Integer.parseInt(m.get(key).toString());					
						String name = DayangDictionaryUtil.getDicName(Integer.parseInt(dicPara[k][1]),value);
						m.put(key+"_name", name);
					}catch(Exception e){
						m.put(key+"_name", m.get(key));
					}
				}
			}
		}
	}	
}
