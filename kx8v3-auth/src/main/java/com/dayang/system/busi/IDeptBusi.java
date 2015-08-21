package com.dayang.system.busi;

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
public interface IDeptBusi {


    /**
     * 获取部门所属的层级
     * @param level 层级 默认为0
     * @param deptId
     * @return
     */
    public int getDeptLevel(int level,long deptId);

    /**
     * 获取部门的子节点数量
     * @param
     * deptId
     * @return
     */
    public int getDeptLeftCount(long deptId);
}
