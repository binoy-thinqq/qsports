package com.thinqq.qsports.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.thinqq.qsports.persistence.validation.annotation.IgnoreTransform;

public class ObjectTransformUtil {

	public static String PRE_CHAR_VALUE_FROM = "get";
	public static String PRE_CHAR_VALUE_TO = "set";
	
	public static void transform(Object valueFrom, Object valueTo) {
		transform(valueFrom, valueTo, true);
	}
	public static void transform(Object valueFrom, Object valueTo, boolean ignoreNull) {
		Class classFrom =  valueFrom.getClass();
		Class classTo =  valueTo.getClass();
		for(Field field : classFrom.getDeclaredFields()) {
			try {
						String fieldName = field.getName();
						String methodName = Character.toUpperCase(fieldName.charAt(0)) + fieldName.substring(1);
						String methodNameGet = PRE_CHAR_VALUE_FROM + methodName;
						String methodNameSet = PRE_CHAR_VALUE_TO + methodName;
						Method methodFrom = classFrom.getDeclaredMethod(methodNameGet);
						if (methodFrom != null) {
							Method methodTo = classTo.getDeclaredMethod(methodNameSet, methodFrom.getReturnType());
							if (methodFrom != null && methodTo != null && methodTo.getAnnotation(IgnoreTransform.class) == null) {
								Object object = methodFrom.invoke(valueFrom);
								if (!(ignoreNull && object == null)) {
									methodTo.invoke(valueTo, object);
								}
							} 
						}
			} catch (Exception e) {
				//chew and eat
			}
		}
	}
	
	public static void transformGivenFields(Object valueFrom, Object valueTo, boolean ignoreNull, Field[] fields) {
		Class classFrom =  valueFrom.getClass();
		Class classTo =  valueTo.getClass();
		for(Field field : fields) {
			try {
						String fieldName = field.getName();
						String methodName = Character.toUpperCase(fieldName.charAt(0)) + fieldName.substring(1);
						String methodNameGet = PRE_CHAR_VALUE_FROM + methodName;
						String methodNameSet = PRE_CHAR_VALUE_TO + methodName;
						Method methodFrom = classFrom.getDeclaredMethod(methodNameGet);
						if (methodFrom != null) {
							Method methodTo = classTo.getDeclaredMethod(methodNameSet, methodFrom.getReturnType());
							if (methodFrom != null && methodTo != null && methodTo.getAnnotation(IgnoreTransform.class) == null) {
								Object object = methodFrom.invoke(valueFrom);
								if (!(ignoreNull && object == null)) {
									methodTo.invoke(valueTo, object);
								}
							} 
						}
			} catch (Exception e) {
				//chew and eat
			}
		}
	}

}
