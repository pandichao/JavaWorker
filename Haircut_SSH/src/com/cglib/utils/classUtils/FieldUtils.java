package com.cglib.utils.classUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * ������ط����������װ
 * @author pdc
 *
 */
public class FieldUtils {
	/** 
	 * ����������ȡ����ֵ 
	 * */  
	public static Object getFieldValueByName(String fieldName, Object o) {  
		try {    
			String firstLetter = fieldName.substring(0, 1).toUpperCase();    
			String getter = "get" + firstLetter + fieldName.substring(1);    
			Method method = o.getClass().getMethod(getter, new Class[] {});    
			Object value = method.invoke(o, new Object[] {});    
			return value;    
		} catch (Exception e) {    
			e.printStackTrace();  
			return null;    
		}    
	}

	/**
	 * ָ�����������ö�Ӧ��ֵ(���뱣֤��ǰ�������û�����������)
	 * @param <T>
	 * @param fieldName
	 * @param o
	 */
	public static <T> T setFieldValueByName(String fieldName,String value,T o){
		try {
            //���Ե�һ����ĸת�ɴ�д set��������������
			String firstLetter = fieldName.substring(0, 1).toUpperCase();
			String setter = "set" + firstLetter + fieldName.substring(1);
			Field[] fields = o.getClass().getDeclaredFields();
			for (Field field : fields) {
				if(field.getName().equalsIgnoreCase(fieldName)){
					//�������һ���ȡ����
					Method method = o.getClass().getMethod(setter,new Class[]{field.getType()});
					 String fieldType = field.getType().getSimpleName();  
	                    if ("String".equals(fieldType)) {  
	                    	method.invoke(o, value);  
	                    } else if ("Date".equals(fieldType)) {  
	                        Date temp = parseDate(value);  
	                        method.invoke(o, temp);  
	                    } else if ("Integer".equals(fieldType)  
	                            || "int".equals(fieldType)) {  
	                        Integer intval = Integer.parseInt(value);  
	                        method.invoke(o, intval);  
	                    } else if ("Long".equalsIgnoreCase(fieldType)) {  
	                        Long temp = Long.parseLong(value);  
	                        method.invoke(o, temp);  
	                    } else if ("Double".equalsIgnoreCase(fieldType)) {  
	                        Double temp = Double.parseDouble(value);  
	                        method.invoke(o, temp);  
	                    } else if ("Boolean".equalsIgnoreCase(fieldType)) {  
	                        Boolean temp = Boolean.parseBoolean(value);  
	                        method.invoke(o, temp);  
	                    }
				}else{
					//
					System.out.println("��ȡ������ֵΪ��"+field.getName()+",fieldName:"+fieldName);
				}
			}
			//����ֵ
			//��������Ժ󷵻��������
			return o;
		} catch (Exception e) {
			e.printStackTrace();
			return o;
		}
	}
	
	/** 
     * ȡBean�в�Ϊ�յ����ԺͶ�Ӧ��value�ֱ���ɼ��ϣ�����map��ż��Ϸ��س���     map��key��fields��values
	 * @param <T>
     *  
     * @param bean 
     * @return Map 
     */  
    public static Map<String,List> getNotnullFieldsAndValueMap(Object bean) {  
        Class<?> cls = bean.getClass();
        Map<String, List> valueMap = new HashMap<String, List>();  
        Method[] methods = cls.getDeclaredMethods();  
        Field[] fields = cls.getDeclaredFields();
        //������ļ��Ϻͻ�ȡ��ֵ�ļ���
        List<String> fieldList = new ArrayList<String>();
        List<Object> values = new ArrayList<Object>();
        for (Field field : fields) {  
            try {  
                String fieldType = field.getType().getSimpleName();  
                String fieldGetName = parGetName(field.getName());  
                if (!checkGetMet(methods, fieldGetName)) {  
                    continue;  
                }  
                Method fieldGetMet = cls.getMethod(fieldGetName, new Class[] {});  
                Object fieldVal = fieldGetMet.invoke(bean, new Object[] {});  
                String result = null;  
                if ("Date".equals(fieldType)) {  
                    result = fmtDate((Date) fieldVal);  
                } else {  
                    if (null != fieldVal) {  
                        result = String.valueOf(fieldVal);  
                        fieldList.add(field.getName());
                        values.add(result);
                    }  
                }
            } catch (Exception e) {  
                continue;  
            }  
        }
        //�����Ժ��map��װһ�·ų�ȥ
        valueMap.put("fields", fieldList);
        valueMap.put("values",values);
        return valueMap;  
    }
	
	/** 
     * ȡBean�����Ժ�ֵ��Ӧ��ϵ��MAP 
	 * @param <T>
     *  
     * @param bean 
     * @return Map 
     */  
    public static <T> Map<String, Object> getFieldValueMap(T bean) {  
        Class<?> cls = bean.getClass();  
        Map<String, Object> valueMap = new HashMap<String, Object>();  
        Method[] methods = cls.getDeclaredMethods();  
        Field[] fields = cls.getDeclaredFields();  
        for (Field field : fields) {  
            try {  
                String fieldType = field.getType().getSimpleName();  
                String fieldGetName = parGetName(field.getName());  
                if (!checkGetMet(methods, fieldGetName)) {  
                    continue;  
                }  
                Method fieldGetMet = cls.getMethod(fieldGetName, new Class[] {});  
                Object fieldVal = fieldGetMet.invoke(bean, new Object[] {});  
                String result = null;  
                if ("Date".equals(fieldType)) {  
                    result = fmtDate((Date) fieldVal);  
                }else if("String".equals(fieldType)){
                	if (null != fieldVal) {  
                        result = String.valueOf(fieldVal).replaceAll("'", "\\\\'");  
                    } 
                }
                else {  
                    if (null != fieldVal) {  
                        result = String.valueOf(fieldVal);  
                    }  
                }  
                valueMap.put(field.getName(), result);  
            } catch (Exception e) {  
                continue;  
            }  
        }  
        return valueMap;  
    }
	
	/** 
     * set���Ե�ֵ��Bean 
     *  
     * @param bean 
     * @param valMap 
     */  
    public static void setFieldValue(Object bean, Map<String, String> valMap) {  
        Class<?> cls = bean.getClass();  
        // ȡ��bean������з���  
        Method[] methods = cls.getDeclaredMethods();  
        Field[] fields = cls.getDeclaredFields();  
  
        for (Field field : fields) {  
            try {  
                String fieldSetName = parSetName(field.getName());  
                if (!checkSetMet(methods, fieldSetName)) {  
                    continue;  
                }  
                Method fieldSetMet = cls.getMethod(fieldSetName,  
                        field.getType());  
//              String fieldKeyName = parKeyName(field.getName());  
                String  fieldKeyName = field.getName();  
                String value = valMap.get(fieldKeyName);  
                if (null != value && !"".equals(value)) {  
                    String fieldType = field.getType().getSimpleName();  
                    if ("String".equals(fieldType)) {  
                        fieldSetMet.invoke(bean, value);  
                    } else if ("Date".equals(fieldType)) {  
                        Date temp = parseDate(value);  
                        fieldSetMet.invoke(bean, temp);  
                    } else if ("Integer".equals(fieldType)  
                            || "int".equals(fieldType)) {  
                        Integer intval = Integer.parseInt(value);  
                        fieldSetMet.invoke(bean, intval);  
                    } else if ("Long".equalsIgnoreCase(fieldType)) {  
                        Long temp = Long.parseLong(value);  
                        fieldSetMet.invoke(bean, temp);  
                    } else if ("Double".equalsIgnoreCase(fieldType)) {  
                        Double temp = Double.parseDouble(value);  
                        fieldSetMet.invoke(bean, temp);  
                    } else if ("Boolean".equalsIgnoreCase(fieldType)) {  
                        Boolean temp = Boolean.parseBoolean(value);  
                        fieldSetMet.invoke(bean, temp);  
                    } else {  
                        System.out.println("not supper type" + fieldType);  
                    }  
                }  
            } catch (Exception e) {  
                continue;  
            }  
        }  
    } 
    
    /** 
     * ��ʽ��stringΪDate 
     *  
     * @param datestr 
     * @return date 
     */  
    public static Date parseDate(String datestr) {  
        if (null == datestr || "".equals(datestr)) {  
            return null;  
        }  
        try {  
            String fmtstr = null;  
            if (datestr.indexOf(':') > 0) {  
                fmtstr = "yyyy-MM-dd HH:mm:ss";  
            } else {  
                fmtstr = "yyyy-MM-dd";  
            }  
            SimpleDateFormat sdf = new SimpleDateFormat(fmtstr, Locale.UK);  
            return sdf.parse(datestr);  
        } catch (Exception e) {  
            return null;  
        }  
    }
    
    /** 
     * ����ת��ΪString 
     *  
     * @param date 
     * @return date string 
     */  
    public static String fmtDate(Date date) {  
        if (null == date) {  
            return null;  
        }  
        try {  
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",  
                    Locale.US);  
            return sdf.format(date);  
        } catch (Exception e) {  
            return null;  
        }  
    }
    
    /** 
     * �ж��Ƿ����ĳ���Ե� set���� 
     *  
     * @param methods 
     * @param fieldSetMet 
     * @return boolean 
     */  
    public static boolean checkSetMet(Method[] methods, String fieldSetMet) {  
        for (Method met : methods) {  
            if (fieldSetMet.equals(met.getName())) {  
                return true;  
            }  
        }  
        return false;  
    }  
  
    /** 
     * �ж��Ƿ����ĳ���Ե� get���� 
     *  
     * @param methods 
     * @param fieldGetMet 
     * @return boolean 
     */  
    public static boolean checkGetMet(Method[] methods, String fieldGetMet) {  
        for (Method met : methods) {  
            if (fieldGetMet.equals(met.getName())) {  
                return true;  
            }  
        }  
        return false;  
    }
    
    /** 
     * ƴ��ĳ���Ե� get���� 
     *  
     * @param fieldName 
     * @return String 
     */  
    public static String parGetName(String fieldName) {  
        if (null == fieldName || "".equals(fieldName)) {  
            return null;  
        }  
        int startIndex = 0;  
        if (fieldName.charAt(0) == '_')  
            startIndex = 1;  
        return "get"  
                + fieldName.substring(startIndex, startIndex + 1).toUpperCase()  
                + fieldName.substring(startIndex + 1);  
    }  
  
    /** 
     * ƴ����ĳ���Ե� set���� 
     *  
     * @param fieldName 
     * @return String 
     */  
    public static String parSetName(String fieldName) {  
        if (null == fieldName || "".equals(fieldName)) {  
            return null;  
        }  
        int startIndex = 0;  
        if (fieldName.charAt(0) == '_')  
            startIndex = 1;  
        return "set"  
                + fieldName.substring(startIndex, startIndex + 1).toUpperCase()  
                + fieldName.substring(startIndex + 1);  
    }  
  
    /** 
     * ��ȡ�洢�ļ���ƣ�����parGetName�� 
     *  
     * @param fieldName 
     * @return ȥ����ͷ��get 
     */  
    public static String parKeyName(String fieldName) {  
        String fieldGetName = parGetName(fieldName);  
        if (fieldGetName != null && fieldGetName.trim() != ""  
                && fieldGetName.length() > 3) {  
            return fieldGetName.substring(3);  
        }  
        return fieldGetName;  
    }  

	/** 
	 * ��ȡ���������� 
	 * */  
	private static String[] getFiledName(Object o){  
		Field[] fields=o.getClass().getDeclaredFields();  
		String[] fieldNames=new String[fields.length];  
		for(int i=0;i<fields.length;i++){  
			System.out.println(fields[i].getType());  
			fieldNames[i]=fields[i].getName();  
		}  
		return fieldNames;  
	}  

	/** 
	 * ��ȡ��������(type)��������(name)������ֵ(value)��map��ɵ�list 
	 * */  
	private static List getFiledsInfo(Object o){  
		Field[] fields=o.getClass().getDeclaredFields();  
		String[] fieldNames=new String[fields.length];  
		List list = new ArrayList();  
		Map infoMap=null;  
		for(int i=0;i<fields.length;i++){  
			infoMap = new HashMap();  
			infoMap.put("type", fields[i].getType().toString());  
			infoMap.put("name", fields[i].getName());  
			infoMap.put("value", getFieldValueByName(fields[i].getName(), o));  
			list.add(infoMap);  
		}  
		return list;  
	}  

	/** 
	 * ��ȡ�������������ֵ������һ���������� 
	 * */  
	public static Object[] getFiledValues(Object o){  
		String[] fieldNames=getFiledName(o);  
		Object[] value=new Object[fieldNames.length];  
		for(int i=0;i<fieldNames.length;i++){  
			value[i]=getFieldValueByName(fieldNames[i], o);  
		}  
		return value;  
	}
}
