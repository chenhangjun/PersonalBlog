package com.chen.blog.common.aop;

import java.lang.annotation.*;

// TYPE 代表该注解可以加在类上 METHOD 代表可以加在方法上
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LogAnnotation {

    String module() default "";
    String operation() default "";
}
