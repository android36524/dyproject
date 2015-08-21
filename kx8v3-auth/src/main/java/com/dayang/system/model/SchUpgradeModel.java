package com.dayang.system.model;

import com.jfinal.plugin.activerecord.Model;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

/**
 * 类描述：学生毕业升学时间设定表
 * <pre>
 * -------------History------------------
 *   DATE                 AUTHOR         VERSION        DESCRIPTION
 *  2015年6月2日             何意            V01.00.001		      新增内容
 * </pre>
 *
 * @author <a href="mailto:heyi@dayang.com">何意 </a>
 */
public class SchUpgradeModel extends Model<SchUpgradeModel> {

    public static final SchUpgradeModel dao = new SchUpgradeModel();

    public static final Logger logger = Logger.getLogger(SchUpgradeModel.class);

    public SchUpgradeModel findSchUpgradeBygradId(long gradId,long schId){
        return SchUpgradeModel.dao.findFirst(" select * from cfg_schupgrade where upGradeId = ? and schId = ?",gradId,schId);
    }
    public SchUpgradeModel getHigGradeTime(long schId){
        return SchUpgradeModel.dao.findFirst(" select * from cfg_schupgrade where  schId = ?",schId);
    }
}
