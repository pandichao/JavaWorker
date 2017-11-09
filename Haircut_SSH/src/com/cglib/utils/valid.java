package com.cglib.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Map;

/**
 * �ṩһЩ������Ч��У��ķ���
 */
@SuppressWarnings("rawtypes")
public final class valid {

    /**
     * �ж��ַ��Ƿ��Ƿ��ָ����ʽ��ʱ��
     * @param date ʱ���ַ�
     * @param format ʱ���ʽ
     * @return �Ƿ���
     */
    public final static boolean isDate(String date,String format){
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            sdf.parse(date);
            return true;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * �ж��ַ���Ч��
     */
    public final static boolean valid(String src) {
        return !(src == null || "".equals(src.trim()));
    }

    /**
     * �ж�һ���ַ��Ƿ���Ч
     * @param src
     * @return
     */
    public final static boolean valid(String... src) {
        for (String s : src) {
            if (!valid(s)) {
                return false;
            }
        }
        return true;
    }


    /**
     * �ж�һ�������Ƿ�Ϊ��
     */
    public final static boolean valid(Object obj) {
        return !(null == obj);
    }

    /**
     * �ж�һ������Ƿ���Ч
     * @param objs
     * @return
     */
    public final static boolean valid(Object... objs) {
        if (objs != null && objs.length != 0) {
            return true;
        }
        return false;
    }

    /**
     * �жϼ��ϵ���Ч��
     */
    public final static boolean valid(Collection col) {
        return !(col == null || col.isEmpty());
    }

    /**
     * �ж�һ�鼯���Ƿ���Ч
     * @param cols
     * @return
     */
    public final static boolean valid(Collection... cols) {
        for (Collection c : cols) {
            if (!valid(c)) {
                return false;
            }
        }
        return true;
    }

    /**
     * �ж�map�Ƿ���Ч
     * @param map
     * @return
     */
    public final static boolean valid(Map map) {
        return !(map == null || map.isEmpty());
    }

    /**
     * �ж�һ��map�Ƿ���Ч
     * @param maps ��Ҫ�ж�map
     * @return �Ƿ�ȫ����Ч
     */
    public final static boolean valid(Map... maps) {
        for (Map m : maps) {
            if (!valid(m)) {
                return false;
            }
        }
        return true;
    }
}
