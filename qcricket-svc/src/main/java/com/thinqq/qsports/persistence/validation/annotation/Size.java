package com.thinqq.qsports.persistence.validation.annotation;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;


@Retention(value=RUNTIME)
public @interface Size {
	
	int length() default 0;
	int minLength() default 0;

}