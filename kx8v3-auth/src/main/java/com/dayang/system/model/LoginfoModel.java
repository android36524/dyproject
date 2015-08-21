package com.dayang.system.model;

import com.dayang.commons.jfinal.kit.ConditionsKit;
import com.dayang.commons.util.CommonUtil;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;
import org.apache.log4j.Logger;


/**
 * 类描述：错误Excel文件下载地址Model
 * <pre>
 * -------------History------------------
 *   DATE                 AUTHOR         VERSION        DESCRIPTION
 *  2015年6月8日上午10:57:46       何意            		 V01.00.001		 新增内容
 * </pre>
 *
 * @author <a href="heyi@dayanginfo.com">何意</a>
 */
public class LoginfoModel extends Model<LoginfoModel> {
    private static final long serialVersionUID = 1L;

    public static final LoginfoModel dao = new LoginfoModel();

    public static final Logger logger = Logger.getLogger(LoginfoModel.class);

    /**
     * 分页查询学阶信息
     * @param pageNumber 页码
     * @param pageSize  页面大小
     * @param fileName 文件名称
     * @return
     */
    public static Page<LoginfoModel> findByStageAll(int pageNumber,int pageSize,String fileName){
        ConditionsKit con = new ConditionsKit();
        String select = " select a.*   ";
        String sqlSelect = " from imp_loginfo a where 1=1  ";
        if(CommonUtil.isNotEmptyString(fileName)){
            con.setValueQuery(ConditionsKit.FUZZY, "a.fileName", fileName);
        }
        con.modelToCondition(new StageModel());
        sqlSelect += con.getSql();
        sqlSelect += " order by importTime desc ";
        logger.info(select+sqlSelect);
        return LoginfoModel.dao.paginate(pageNumber, pageSize, select, sqlSelect,con.getParamList().toArray());
    }
}
