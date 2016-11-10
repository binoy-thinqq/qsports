package com.thinqq.qsports.persistence.validation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.validator.routines.EmailValidator;

import com.thinqq.qsports.persistence.dataobjects.User;

public class ValidationUtil {


 private static final String PRE_CHAR = "get";
private static Set<Class> validationRules = new HashSet<Class>();
 static {
	 validationRules.add(NotNull.class);
	 validationRules.add(ValidateEmail.class);
 }
 

public static List<String> validate(Object value) {
	List<String> failureFields = new ArrayList<String>();
	Class validateClass =  value.getClass();
	for(Field field : validateClass.getDeclaredFields()) {
		for(Annotation annotation : field.getDeclaredAnnotations()) {
			if (validationRules.contains(annotation.annotationType())) {
				try {
					
					String fieldName = field.getName();
					String methodName = PRE_CHAR + Character.toUpperCase(fieldName.charAt(0)) + fieldName.substring(1);
					Method method = validateClass.getDeclaredMethod(methodName);
					if (method != null) {
						if (!invokeValidate(method.invoke(value), annotation)) {
							failureFields.add(fieldName);
						}
					} else {
						failureFields.add("No Such MEthod");
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
	return failureFields;
}

private static boolean invokeValidate(Object value, Annotation annotation) {
	if (NotNull.class.equals(annotation.annotationType())) {
		return value != null;
	} else if (ValidateEmail.class.equals(annotation.annotationType())) {
		EmailValidator emailValidator = EmailValidator.getInstance();
		return emailValidator.isValid(value.toString());
		
	}
	return false;
}
public static void main(String[] args) {
	/*Session session = SessionManager.getSessionFactory().openSession();
	session.beginTransaction();
	User user = new User();
	user.setCity("Chennai");
	session.save(user);
	session.getTransaction().commit();*/
	User user  = new User();
	user.setFirstName("Binoy");
	ValidationUtil.validate(user);

	
	

}
}
