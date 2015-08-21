package com.dayang.commons.thread;

import com.dayang.commons.pojo.ImportPojo;
import com.dayang.commons.util.StaticData;
import com.jfinal.plugin.activerecord.Db;

import org.apache.log4j.Logger;

import java.util.*;

/**
 * 类描述：执行sql线程类
 * <pre>
 * -------------History------------------
 *   DATE                 AUTHOR         VERSION        DESCRIPTION
 *  2015年06月08日            刘建宇      V01.00.001		   新增内容
 * </pre>
 *
 * @author <a href="mailto:liujy@dayanginfo.com">刘建宇</a>
 */
public class ExecuteSqlThread extends Thread{

    private static final Logger log = Logger.getLogger(ExecuteSqlThread.class);

    private List<? extends ImportPojo> list;

    public ExecuteSqlThread(List<? extends ImportPojo> list)
    {
        this.list = list;
    }

    @Override
    public void run() {
        if (list == null || list.size() == 0){
            return;
        }
        Map<String,List<List<Object>>> result = new HashMap<>();
        for (ImportPojo aList : list) {
            Map<String, List<Object>> map = aList.getSql();
            for (Map.Entry<String, List<Object>> entry : map.entrySet()) {
                String sql = entry.getKey();
                if (result.get(sql) == null) {
                    List<List<Object>> valueList = new ArrayList<>();
                    result.put(sql, valueList);
                }
                List<Object> params = entry.getValue();
                result.get(sql).add(params);
            }
        }
        for (Map.Entry<String,List<List<Object>>> entry : result.entrySet()){
            String executeSql = entry.getKey();
            List<List<Object>> paramsList = entry.getValue();
            Object[][] paras = new Object[paramsList.size()][paramsList.get(0).size()];
            for (int x = 0; x < paramsList.size(); x++){
                paras[x] = paramsList.get(x).toArray();
            }
            log.debug(executeSql + " = " + Arrays.deepToString(paras));
            Db.batch(executeSql,paras,StaticData.Batch_Size);
        }
    }
}
