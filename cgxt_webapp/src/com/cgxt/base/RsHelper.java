package com.cgxt.base;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 数据库结果集帮助器
 */
public class RsHelper {
	/**
     * 返回结果集中的唯一结果，没有则返回null
     * @param rs 结果集
     * @return
     * @throws SQLException
     */
    public static Object getUniqueResult(ResultSet rs) throws SQLException{ 
        if(rs.next()) {
            return rs.getObject(1);
        }
        return null;
    }
    
    /**
     * 将实体结果集对象转换为map对象结果集
     * @param rs 实体结果集对象
     * @return map对象结果集
     * @throws SQLException
     */
    public static List<Map<String,Object>> rSToList(ResultSet rs) throws SQLException {   
        if (rs == null)   
            return Collections.EMPTY_LIST;   
        ResultSetMetaData md = rs.getMetaData(); //得到结果集(rs)的结构信息，比如字段数、字段名等   
        int columnCount = md.getColumnCount(); //返回此 ResultSet 对象中的列数   
        List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();   
        Map<String,Object> rowData = new HashMap<String,Object>();   
        while (rs.next()) {   
            rowData = new HashMap<String,Object>(columnCount);   
            for (int i = 1; i <= columnCount; i++) {   
                rowData.put(md.getColumnLabel(i), rs.getObject(i));   
            }   
            list.add(rowData);   
        }   
        return list;   
    }
}
