package com.dayang.commons.util;

import com.dayang.commons.pojo.ImportPojo;

/**
 * 类描述：模板pojo工厂类
 * <pre>
 * -------------History------------------
 *   DATE                 AUTHOR         VERSION        DESCRIPTION
 *  2015年06月02日            刘建宇      V01.00.001		   新增内容
 * </pre>
 *
 * @author <a href="mailto:liujy@dayanginfo.com">刘建宇</a>
 */
public class ImportPojoFactory {

    public static ImportPojo create(String clazzName){
        ImportPojo importPojo;
        try {
            importPojo = (ImportPojo) Class.forName(clazzName).newInstance();
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e ) {
            throw new RuntimeException("创建ImportPojo实例失败");
        }
        return importPojo;
    }
}
