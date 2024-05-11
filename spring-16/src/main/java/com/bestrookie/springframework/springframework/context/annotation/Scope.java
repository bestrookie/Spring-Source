package com.bestrookie.springframework.springframework.context.annotation;

import java.lang.annotation.*;

/**
 * @Author bestrookie
 * @Date 2024/4/1 14:38
 * @Desc 用于配置作用域的自定义注解，方便配置 Bean 对象注解的时候，拿到 Bean 对象的作用域
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Scope {
    String value() default "singleton";
}
