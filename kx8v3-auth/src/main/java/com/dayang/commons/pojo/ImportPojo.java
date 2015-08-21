package com.dayang.commons.pojo;

import java.util.List;
import java.util.Map;

/**
 * 类描述：excel导入pojo基类
 * <pre>
 * -------------History------------------
 *   DATE                 AUTHOR         VERSION        DESCRIPTION
 *  2015年06月04日            刘建宇      V01.00.001		   新增内容
 * </pre>
 *
 * @author <a href="mailto:liujy@dayanginfo.com">刘建宇</a>
 */
public abstract class ImportPojo {

    private int rowNumber;

    public int getRowNumber() {
        return rowNumber;
    }

    public void setRowNumber(int rowNumber) {
        this.rowNumber = rowNumber;
    }

    public abstract Map<Integer,String> getMap();
    public abstract Map<String,List<Object>> getSql();
    public abstract String getTemplatePath();
}
