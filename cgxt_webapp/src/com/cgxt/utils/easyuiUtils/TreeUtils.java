package com.cgxt.utils.easyuiUtils;

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
 * easyui的树节点封装的工具类（一次全部查询出来进行动态加载）
 * @param <T>
 *
 */
public class TreeUtils<T> {
	/**
	 * 存放转换以后的结果
	 */
	List<Map<String,Object>> comboTreeList  =new ArrayList<Map<String,Object>>();
	
	/** 
	 * 将角色封装成树开始 
	 * @param list 
	 * @param fid 父id 
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
	            map.put("text",values.get(1));      //text(节点树展示的内容)
	            map.put("children", createComboTreeChildren(list,map.get("id").toString(),attrName));  
	        if (map != null)  
	            comboTreeList.add(map);  
	    }  
	}  
	
	/**
	 * 反射获取数据的方法
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
		//这里必须要将对象角色的id、name转换成ComboTree在页面的显示形式id、text  
        //ComboTree,不是数据表格，没有在页面通过columns转换数据的属性  
        Class<?> clazz=one.getClass();
        //自动根据你想要把哪两个个字段封装成id，text
        Field idName = clazz.getDeclaredField(attrName[0]);
        Field textName = clazz.getDeclaredField(attrName[1]);
        //获取这两个属性对应的值是多少
        Field[] fields = {idName,textName};
        List<String> values = new ArrayList<String>();
        for (Field field : fields) {
        	PropertyDescriptor pd=new PropertyDescriptor(field.getName(),clazz);
            Method getMethod = pd.getReadMethod();
            //这里需要注意这个属性的值不能为空
            String value = (String)getMethod.invoke(one);
            values.add(value);
		}
        return values;
	}
	  
	  
	/** 
	 * 递归设置树节点的子节点 
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
	        //获取传来的第一个id的name
	        Class<?> clazz=treeChild.getClass();
            //自动根据你想要把哪两个个字段封装成id，text
            Field idName = clazz.getDeclaredField(attrName[0]);
            PropertyDescriptor pd=new PropertyDescriptor(idName.getName(),clazz);
            Method getMethod = pd.getReadMethod();
            //这里需要注意这个属性的值不能为空
            String id = (String)getMethod.invoke(treeChild);
	        if (id.equals(fid)) {  
	            map = new HashMap<String, Object>();  
	            //这里必须要将对象角色的id、name转换成ComboTree在页面的显示形式id、text  
	            //ComboTree,不是数据表格，没有在页面通过columns转换数据的属性  
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
