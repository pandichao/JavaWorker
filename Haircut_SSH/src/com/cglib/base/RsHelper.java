package com.cglib.base;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ��ݿ��������
 */
public class RsHelper {
	/**
     * ���ؽ���е�Ψһ���û���򷵻�null
     * @param rs ���
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
     * ��ʵ�������ת��Ϊmap������
     * @param rs ʵ�������
     * @return map������
     * @throws SQLException
     */
    public static List<Map<String,Object>> rSToList(ResultSet rs) throws SQLException {   
        if (rs == null)   
            return Collections.EMPTY_LIST;   
        ResultSetMetaData md = rs.getMetaData(); //�õ����(rs)�Ľṹ��Ϣ�������ֶ����ֶ����   
        int columnCount = md.getColumnCount(); //���ش� ResultSet �����е�����   
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
