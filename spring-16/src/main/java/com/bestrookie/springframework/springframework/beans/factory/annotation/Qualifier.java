package com.bestrookie.springframework.springframework.beans.factory.annotation;

import java.lang.annotation.*;

/**
 * @Author bestrookie
 * @Date 2024/4/3 16:00
 * @Desc
 */
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface Qualifier {
    String value() default "";
}
