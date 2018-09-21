package com.hotent.core.annotion;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @company
 * @description 类的方法描述注解,用于aop拦截以获取正确方法其对应的描述
 * @author csx
 * @create 2010-02-03
 */
@Target(ElementType.METHOD)   
@Retention(RetentionPolicy.RUNTIME)   
@Documented  
@Inherited 
public @interface Action {
	
	/**
	 * 操作类型
	 * @return
	 */
	public String operateType() default "";
	/**
	 * 方法描述
	 * @return
	 */
	public String description() default "no description"; 
}