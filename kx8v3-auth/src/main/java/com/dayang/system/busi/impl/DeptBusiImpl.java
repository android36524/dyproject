package com.dayang.system.busi.impl;

import com.dayang.system.busi.IDeptBusi;
import com.dayang.system.model.DeptModel;
import org.springframework.stereotype.Controller;

/**
 * 类描述：教育部门业务接口
 * <pre>
 * -------------History------------------
 *   DATE                 AUTHOR         VERSION        DESCRIPTION
 *  2015年5月189日              heyi              V01.00.001		      新增内容
 * </pre>
 *
 * @author <a href="heyi@dayanginfo.com">何意</a>
 */
@Controller
public class DeptBusiImpl  implements IDeptBusi {
    /**
     * 获取menu所属的层级
     * @param level 层级 默认为0
     * @param deptId
     * @return
     */
    public int getDeptLevel(int level,long deptId){
        if(deptId <= 0){
            return 0;
        }else if(level == 0 && deptId > 0){
            level = 1;
        }
        DeptModel deptModel = DeptModel.dao.findFirst("select pid from base_department where id=?",deptId);
        if(deptModel != null){
            long pid = deptModel.get("pid")!=null?deptModel.getLong("pid"):0l;
            if(pid != 0){
                level += 1;
                level = getDeptLevel(level, pid);
            }
        }

        return level;
    }

    /**
     * 获取部门的子节点数量
     * @param deptId
     * @return
     */
    public int getDeptLeftCount(long deptId){
        int count = 0;
        DeptModel deptModel = DeptModel.dao.findFirst("select count(1) leafcount from base_department where pid=?",deptId);
        if(deptModel != null){
            count = deptModel.getLong("leafcount").intValue();
        }
        return count;
    }

}
