package com.dayang.system.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dayang.commons.enums.EnumAll;
import com.dayang.commons.pojo.QueryDefineParaPojo;
import com.dayang.commons.util.CommonStaticData;
import com.dayang.commons.util.CommonUtil;
import com.dayang.commons.util.StaticData;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;

/**
 * 类描述：科目Model
 * 
 * <pre>
 * -------------History------------------
 *   DATE                 AUTHOR         VERSION        DESCRIPTION
 *  2015年5月26日              温建军              V01.00.001		      新增内容
 * </pre>
 * 
 * @author <a href="wenjj@dayanginfo.com">温建军</a>
 */
public class SubjectModel extends Model<SubjectModel> {
	private static final long serialVersionUID = 1L;
	public static final String OtherSubCode = "KMQT";
	public static final SubjectModel dao = new SubjectModel();
	
	public static final Map<String, QueryDefineParaPojo> DefineMap = new HashMap<String, QueryDefineParaPojo>() {
		private static final long serialVersionUID = 1L;

		{
			put("name", new QueryDefineParaPojo("name", "name", "like"));
			put("notid", new QueryDefineParaPojo("notid", "id", "<>"));
		}
	};

	public static final Map<String, QueryDefineParaPojo> filterMap = new HashMap<String, QueryDefineParaPojo>() {
		private static final long serialVersionUID = 1L;

		{
			put("name", new QueryDefineParaPojo("name", "name", "="));
			put("id", new QueryDefineParaPojo("id", "id", "!="));
		}
	};
	
	/**
	 * 权限控制map
	 */
	public static final Map<String, String> DataMap= new HashMap<String,String>(){
		 {
			 put(CommonStaticData.OrgTreeNodeType.SCHFLAG,"s.schId");
		 }
	 };

	/**
	 * 通过年级ID获取科目
	 * 
	 * @param gradeId
	 *            年级id
	 * @return
	 */
	public static List<SubjectModel> findSubByGrade(Integer gradeId) {

		String sql = " select * from base_subject bs where bs.id in ( ";
		sql += " select rg.subjectId from r_gradesubject rg where rg.gradeId = ? )  order by bs.seq asc";
		return SubjectModel.dao.find(sql, gradeId);
	}

	/**
	 * 根据条件查询科目详情
	 * 
	 * @param param
	 * @return
	 */
	public SubjectModel findSubject(Map<String, Object> param) {
		StringBuilder sb = new StringBuilder(
				"select s.*,o.orgName schId_showname,b.name creatorName from base_subject s LEFT JOIN base_organization o on o.id=s.schId LEFT JOIN sys_account b on s.creator=b.id where 1=1 ");
		List<Object> list = new ArrayList<Object>();
		CommonUtil.setDefaultPara(param, sb, list, "s", DefineMap);
		sb.append(" order by s.seq asc");
		return SubjectModel.dao.findFirst(sb.toString(), list.toArray());
	}

	/**
	 * 根据条件查询科目分页列表
	 * 
	 * @param pageNumber
	 * @param pageSize
	 * @param param
	 * @return
	 */
	public Page<SubjectModel> findSubjectPage(int pageNumber, int pageSize,
			Map<String, Object> param) {
		String sql = "select s.*,o.orgName schId_showname,b.name creatorName  ";
		StringBuilder sb = new StringBuilder(
				" from base_subject s LEFT JOIN base_organization o on o.id=s.schId LEFT JOIN sys_account b on s.creator=b.id  where 1=1 ");
		List<Object> list = new ArrayList<Object>();
		CommonUtil.setDefaultPara(param,sb,list,"s",DefineMap,null,DataMap);
		sb.append(" order by s.seq asc");
		return SubjectModel.dao.paginate(pageNumber, pageSize, sql,
				sb.toString(), list.toArray());
	}

	/**
	 * 根据条件查询科目列表
	 * 
	 * @param param
	 * @return
	 */
	public List<SubjectModel> findSubjectList(Map<String, Object> param) {
		StringBuilder sb = new StringBuilder(
				"select s.*,o.orgName schId_showname from base_subject s LEFT JOIN base_organization o on o.id=s.schId where 1=1  ");
		List<Object> list = new ArrayList<Object>();
		CommonUtil.setDefaultPara(param, sb, list, "s", DefineMap);
		sb.append(" order by s.seq asc");
		return SubjectModel.dao.find(sb.toString(), list.toArray());
	}

	/**
	 * 查询所有的科目 不分页
	 * 
	 * @param @return 设定文件
	 * @return List<SubjectModel> 返回类型
	 * @throws
	 */
	public List<SubjectModel> findSubjectCommon() {
		String sql = "SELECT * FROM base_subject where flag = ? ORDER BY seq";
		return SubjectModel.dao.find(sql,
				EnumAll.SubjectFlag.COMMONFLAG.getValue());
	}

	/**
	 * 查询所有的科目 不分页
	 * 
	 * @param @return 设定文件
	 * @return List<SubjectModel> 返回类型
	 * @throws
	 */
	public List<SubjectModel> findSubjectAll(int flag, long schId) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (flag > 0) {
			map.put("flag", flag);
		}
		if (schId > 0) {
			map.put("schId", schId);
		}
		return findSubjectList(map);
	}

	/**
	 * 查询学校中未添加的通用科目，其他科目每次都查询
	 * 
	 * @param schId
	 * @return
	 */
	public List<SubjectModel> findSubjectNoContains(long schId) {
		List<SubjectModel> list = SubjectModel.dao
				.find("select * from base_subject where flag=? and (code not in (select code from base_subject where schId =?) or code=?) order by seq asc",
						EnumAll.SubjectFlag.COMMONFLAG.getValue(), schId,
						OtherSubCode);
		return list;
	}

	/**
	 * 根据条件查询科目
	 * 
	 * @param param
	 * @return
	 */
	public List<SubjectModel> findSubByCondition(Map<String, Object> param) {
		StringBuilder select = new StringBuilder(
				" select bs.* from base_subject bs where bs.flag =  "
						+ StaticData.SEARCH_FLAG);
		List<Object> list = new ArrayList<Object>();
		CommonUtil.setDefaultPara(param, select, list, "bs", filterMap);
		return SubjectModel.dao.find(select.toString(), list.toArray());
	}
}
