package com.chen.blog.common.cache;

import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Cache {

    // 过期时间
    long expire() default 1 * 60 * 1000;

    // 缓存标识的 key
    String name() default "";
}
