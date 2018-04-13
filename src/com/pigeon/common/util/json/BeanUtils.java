package com.pigeon.common.util.json;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class BeanUtils {
	
	/**
	 * 将实体对象转换为Map集合
	 * @param obj Object
	 * @return Map<String, Object>
	 * @throws IntrospectionException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	public static Map<String, Object> beanToMap(Object obj) throws IntrospectionException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		if(obj == null)
			return null;
		Map<String, Object> map = new HashMap<String, Object>();
		BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
		PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
		for (PropertyDescriptor property : propertyDescriptors) {
			String key = property.getName();
			// 过滤class属性  
            if (!key.equals("class")) {
            	// 得到property对应的getter方法  
                Method getter = property.getReadMethod();  
                Object value = getter.invoke(obj);
                map.put(key, value);
            }
		}
		return map;
	}
	
	@SuppressWarnings("unchecked")
	public static List<Map<String, Object>> listToMap(List list, Class clz) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, IntrospectionException {
		if(list == null && list.size() <= 0)
			return null;
		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			Object object = (Object) iterator.next();
			if(object == null)
				return null;
			BeanInfo beanInfo = Introspector.getBeanInfo(clz);
			PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
			Map<String, Object> map = new HashMap<String, Object>();
			for (PropertyDescriptor property : propertyDescriptors) {
				String key = property.getName();
				// 过滤class属性  
	            if (!key.equals("class")) {
	            	// 得到property对应的getter方法  
	                Method getter = property.getReadMethod();  
	                Object value = getter.invoke(object);
	                map.put(key, value);
	            }
			}
			resultList.add(map);
		}
		return resultList;
	}
	
}
