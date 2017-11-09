package com.cglib.utils.easyuiUtils;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * easyui�����ڵ��װ�Ĺ����ࣨһ��ȫ����ѯ�������ж�̬���أ�
 * @param <T>
 *
 */
public class TreeUtils<T> {
	/**
	 * ���ת���Ժ�Ľ��
	 */
	List<Map<String,Object>> comboTreeList  =new ArrayList<Map<String,Object>>();
	
	/** 
	 * ����ɫ��װ������ʼ 
	 * @param list 
	 * @param fid ��id 
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 * @throws IllegalAccessException 
	 * @throws IntrospectionException 
	 * @throws SecurityException 
	 * @throws NoSuchFieldException 
	 */  
	/**
	 * @param list
	 * @param attrName
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 * @throws IntrospectionException
	 * @throws NoSuchFieldException
	 * @throws SecurityException
	 */
	private void createComboTreeTree(List<T> list,String...attrName) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, IntrospectionException, NoSuchFieldException, SecurityException {  
	    for (int i = 0; i < list.size(); i++) {  
	            Map<String, Object> map = null;  
	            T one = (T) list.get(i);  
	            map = new HashMap<String, Object>();  
	            List<String> values = getValues(one,attrName);
	            map.put("id", values.get(0));         //id  
	            map.put("text",values.get(1));      //text(�ڵ���չʾ������)
	            map.put("children", createComboTreeChildren(list,map.get("id").toString(),attrName));  
	        if (map != null)  
	            comboTreeList.add(map);  
	    }  
	}  
	
	/**
	 * �����ȡ��ݵķ���
	 * @param one
	 * @param attrName
	 * @return
	 * @throws NoSuchFieldException
	 * @throws SecurityException
	 * @throws IntrospectionException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	public List<String> getValues(T one,String...attrName) throws NoSuchFieldException, SecurityException, IntrospectionException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		//�������Ҫ�������ɫ��id��nameת����ComboTree��ҳ�����ʾ��ʽid��text  
        //ComboTree,������ݱ��û����ҳ��ͨ��columnsת����ݵ�����  
        Class<?> clazz=one.getClass();
        //�Զ��������Ҫ�����������ֶη�װ��id��text
        Field idName = clazz.getDeclaredField(attrName[0]);
        Field textName = clazz.getDeclaredField(attrName[1]);
        //��ȡ���������Զ�Ӧ��ֵ�Ƕ���
        Field[] fields = {idName,textName};
        List<String> values = new ArrayList<String>();
        for (Field field : fields) {
        	PropertyDescriptor pd=new PropertyDescriptor(field.getName(),clazz);
            Method getMethod = pd.getReadMethod();
            //������Ҫע��������Ե�ֵ����Ϊ��
            String value = (String)getMethod.invoke(one);
            values.add(value);
		}
        return values;
	}
	  
	  
	/** 
	 * �ݹ��������ڵ���ӽڵ� 
	 * @param list 
	 * @param fid 
	 * @return 
	 * @throws SecurityException 
	 * @throws NoSuchFieldException 
	 * @throws IntrospectionException 
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 * @throws IllegalAccessException 
	 */  
	private List<Map<String, Object>> createComboTreeChildren(List<T> list,String fid,String...attrName) throws NoSuchFieldException, SecurityException, IntrospectionException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {  
	    List<Map<String, Object>> childList = new ArrayList<Map<String, Object>>();  
	    for (int j = 0; j < list.size(); j++) {  
	        Map<String, Object> map = null;  
	        T treeChild = (T) list.get(j);
	        //��ȡ�����ĵ�һ��id��name
	        Class<?> clazz=treeChild.getClass();
            //�Զ��������Ҫ�����������ֶη�װ��id��text
            Field idName = clazz.getDeclaredField(attrName[0]);
            PropertyDescriptor pd=new PropertyDescriptor(idName.getName(),clazz);
            Method getMethod = pd.getReadMethod();
            //������Ҫע��������Ե�ֵ����Ϊ��
            String id = (String)getMethod.invoke(treeChild);
	        if (id.equals(fid)) {  
	            map = new HashMap<String, Object>();  
	            //�������Ҫ�������ɫ��id��nameת����ComboTree��ҳ�����ʾ��ʽid��text  
	            //ComboTree,������ݱ��û����ҳ��ͨ��columnsת����ݵ�����  
	            List<String> values = getValues(treeChild, attrName);
	            map.put("id", values.get(0));  
	            map.put("text", values.get(1));  
	            map.put("children", createComboTreeChildren(list,map.get("id").toString(),attrName));  
	        }  
	          
	        if (map != null)  
	            childList.add(map);  
	    }  
	    return childList;  
	}

}
