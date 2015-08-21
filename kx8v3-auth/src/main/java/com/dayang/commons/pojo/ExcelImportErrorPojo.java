package com.dayang.commons.pojo;

import java.util.ArrayList;
import java.util.List;

/**
 * 类描述：excel导入行错误信息pojo类
 * <pre>
 * -------------History------------------
 *   DATE                 AUTHOR         VERSION        DESCRIPTION
 *  2015年06月02日            刘建宇      V01.00.001		   新增内容
 * </pre>
 *
 * @author <a href="mailto:liujy@dayanginfo.com">刘建宇</a>
 */
public class ExcelImportErrorPojo {

    private int rowNumber;
    private List<String> errorMsg = new ArrayList<>();

    public int getRowNumber() {
        return rowNumber;
    }

    public void setRowNumber(int rowNumber) {
        this.rowNumber = rowNumber;
    }

    public List<String> getErrorMsg() {
        return errorMsg;
    }

    public String getErrorMessage(){
        StringBuilder errorMsg = new StringBuilder();
        for (String str : this.errorMsg){
            errorMsg.append("第").append(rowNumber).append("行:")
                    .append(str).append("\n");
        }
        return errorMsg.toString();
    }
}
