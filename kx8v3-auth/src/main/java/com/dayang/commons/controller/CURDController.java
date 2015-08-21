package com.dayang.commons.controller;

/**
 * 类描述：curdController接口
 * <pre>
 * -------------History------------------
 *   DATE                 AUTHOR         VERSION        DESCRIPTION
 *  2015年05月29日            刘建宇      V01.00.001		   新增内容
 * </pre>
 *
 * @author <a href="mailto:liujy@dayanginfo.com">刘建宇</a>
 */
public interface CURDController {

    /**
     * 首页列表
     */
    public void index();

    /**
     * 跳转新增修改页面
     */
    public void toAdd();

    /**
     * 新增方法
     */
    public void add();

    /**
     * 删除方法
     */
    public void del();
}
