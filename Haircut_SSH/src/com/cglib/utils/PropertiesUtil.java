package com.cglib.utils;


import org.apache.log4j.Logger;

import java.io.*;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * �ṩһЩ���õ������ļ���صķ���
 */
public final class PropertiesUtil {
    public static Logger logger = Logger.getLogger(PropertiesUtil.class);

    /**
     * ��ϵͳ�����ļ��л�ȡ��Ӧ��ֵ
     *
     * @param key key
     * @return ����value
     */
    public final static String key(String key) {
        return System.getProperty(key);
    }

    /**
     * ���Key��ȡValue
     *
     * @param filePath �����ļ�
     * @param key      ��Ҫ��ȡ������
     */
    public final static String GetValueByKey(String filePath, String key) {
        Properties pps = new Properties();
        try (InputStream in = new BufferedInputStream(new FileInputStream(filePath))) {
            pps.load(in);
            return pps.getProperty(key);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public final static Map<String,String> properties(InputStream in){
        Map<String,String> map = new HashMap<>();
        Properties pps = new Properties();
        try {
            pps.load(in);
        } catch (IOException e) {
            logger.error("load properties error:"+e.getMessage());
        }
        Enumeration en = pps.propertyNames();
        while (en.hasMoreElements()) {
            String strKey = (String) en.nextElement();
            String strValue = pps.getProperty(strKey);
            map.put(strKey,strValue);
        }
        return map;
    }
    /**
     * ��ȡProperties��ȫ����Ϣ
     *
     * @param filePath ��ȡ�������ļ�
     * @return �������е����� key:value<>key:value
     */
    public final static Map<String,String> GetAllProperties(String filePath) throws IOException {
        Map<String,String> map = new HashMap<>();
        Properties pps = new Properties();
        try (InputStream in = new BufferedInputStream(new FileInputStream(filePath))) {
            return properties(in);
        }catch (IOException e){
            logger.error("load properties error");
        }
        return map;
    }

    /**
     * д��Properties��Ϣ
     *
     * @param filePath д��������ļ�
     * @param pKey     �������
     * @param pValue   ����ֵ
     */
    public final static void WriteProperties(String filePath, String pKey, String pValue) throws IOException {
        Properties props = new Properties();

        props.load(new FileInputStream(filePath));
        // ���� Hashtable �ķ��� put��ʹ�� getProperty �����ṩ�����ԡ�
        // ǿ��Ҫ��Ϊ���Եļ��ֵʹ���ַ�����ֵ�� Hashtable ���� put �Ľ��
        OutputStream fos = new FileOutputStream(filePath);
        props.setProperty(pKey, pValue);
        // ���ʺ�ʹ�� load �������ص� Properties ���еĸ�ʽ��
        // ���� Properties ���е������б?���Ԫ�ضԣ�д�������
        props.store(fos, "Update '" + pKey + "' value");

    }

}
