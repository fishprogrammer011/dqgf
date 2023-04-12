package com.yipin_server.yihuo.config;

import java.lang.annotation.*;


@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AuthAccess {
    boolean required() default true;
}
