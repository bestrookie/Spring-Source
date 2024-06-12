package com.bestrookie.springframework.beans.factory.annotation;

import java.lang.annotation.*;

/**
 * @Author bestrookie
 * @Date 2024/4/3 16:05
 * @Desc
 */
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Value {
    String value();
}
