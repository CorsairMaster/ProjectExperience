package com.quickly.devploment.gp.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @ClassName GpRequestParam
 * @Description
 * @Author LiDengJin
 * @Date 2020/1/14 10:51
 * @Version V-1.0
 **/
@Target({ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface GpRequestParam {
	String value() default "";
}

