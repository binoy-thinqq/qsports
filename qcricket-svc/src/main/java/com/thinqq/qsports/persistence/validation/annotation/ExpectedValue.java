package com.thinqq.qsports.persistence.validation.annotation;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;

@Retention(value=RUNTIME)
public @interface ExpectedValue {
	
	String[] values();
	
}