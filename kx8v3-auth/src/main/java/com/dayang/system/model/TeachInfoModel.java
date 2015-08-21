package com.dayang.system.model;

import com.jfinal.plugin.activerecord.Model;
import org.apache.log4j.Logger;

import java.util.Map;

/**
 * 类描述：学校员工信息扩展
 * <pre>
 * -------------History------------------
 *   DATE                 AUTHOR         VERSION        DESCRIPTION
 *  2015年5月25日             何意            V01.00.001		      新增内容
 * </pre>
 *
 * @author <a href="mailto:heyi@dayang.com">何意 </a>
 */
public class TeachInfoModel extends Model<TeachInfoModel> {
    private static final long serialVersionUID = 1L;

    public static final TeachInfoModel dao = new TeachInfoModel();

    private static Logger logger = Logger.getLogger(TeachInfoModel.class);

    public Map<String, Object> getAttrs() {
        return super.getAttrs();
    }

}
