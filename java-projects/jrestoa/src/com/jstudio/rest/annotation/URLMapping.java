package com.jstudio.rest.annotation;

import java.lang.annotation.*;

@Documented  
@Retention(RetentionPolicy.RUNTIME)  
@Target(ElementType.TYPE) 
public @interface URLMapping
{
	String[] value();
}
