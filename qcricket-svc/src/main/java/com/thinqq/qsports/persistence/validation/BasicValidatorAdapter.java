package com.thinqq.qsports.persistence.validation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.thinqq.qsports.server.error.ErrorInfo;

public class BasicValidatorAdapter implements ValidatorAdapter<Object> {

	private static final String PRE_CHAR = "get";
	private static Set<String> validationRules;
	private static Map<String, Validator> validationRuleExecutor = new HashMap<String, Validator>();

	public List<ErrorInfo> validate(Object value) {
		List<ErrorInfo> errors = new ArrayList<ErrorInfo>();
		Class validateClass = value.getClass();
		for (Field field : validateClass.getDeclaredFields()) {
			for (Annotation annotation : field.getDeclaredAnnotations()) {
				String annotationName = annotation.annotationType().getSimpleName();
				if (validationRules.contains(annotationName)) {
					try {

						String fieldName = field.getName();
						String methodName = PRE_CHAR
								+ Character.toUpperCase(fieldName.charAt(0))
								+ fieldName.substring(1);
						Method method = validateClass.getDeclaredMethod(methodName);
						if (method != null) {
							Validator validator = validationRuleExecutor.get(annotationName);
							List<ErrorInfo> validatorErrors = validator
									.validate(method.invoke(value), fieldName,
											annotation);
							if (validatorErrors != null
									&& !validatorErrors.isEmpty()) {
								errors.addAll(validatorErrors);
							}
						}
					} catch (IllegalArgumentException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
		return errors;
	}

	public static void setValidationRules(Set<String> validationRules) {
		BasicValidatorAdapter.validationRules = validationRules;
	}

	public static void setValidationRuleExecutor(
			Map<String, Validator> validationRuleExecutor) {
		BasicValidatorAdapter.validationRuleExecutor = validationRuleExecutor;
	}
}
