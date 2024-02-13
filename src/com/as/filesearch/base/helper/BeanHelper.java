package com.as.filesearch.base.helper;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class BeanHelper {
	public static Map<String, Object> getFileds(Object object) throws SecurityException, IllegalArgumentException,
			NoSuchMethodException, IllegalAccessException, InvocationTargetException {
		Class clazz = object.getClass();
		Field[] fields = clazz.getDeclaredFields();
		Map<String, Object> map = new HashMap<String, Object>();

		for (int i = 0; i < fields.length; i++) {

			Object resultObject = invokeMethod(object, fields[i].getName(), null);
			map.put(fields[i].getName(),  resultObject);
		}

		return map;
	}

	public static Object invokeMethod(Object owner, String fieldname, Object[] args) throws SecurityException,
			NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		Class ownerClass = owner.getClass();

		Method method = null;
		method = ownerClass.getMethod(toGetter(fieldname));

		Object object = null;
		object = method.invoke(owner);

		return object;
	}

	public static String toGetter(String fieldname) {

		if (fieldname == null || fieldname.length() == 0) {
			return null;
		}

		/*
		 * If the second char is upper, make 'get' + field name as getter name.
		 * For example, eBlog -> geteBlog
		 */
		if (fieldname.length() > 2) {
			String second = fieldname.substring(1, 2);
			if (second.equals(second.toUpperCase())) {
				return new StringBuffer("get").append(fieldname).toString();
			}
		}

		/* Common situation */
		fieldname = new StringBuffer("get").append(fieldname.substring(0, 1).toUpperCase())
				.append(fieldname.substring(1)).toString();

		return fieldname;
	}
}
