package com.bestrookie.springframework.context.annotation;

import java.lang.annotation.*;

/**
 * @Author bestrookie
 * @Date 2024/4/1 14:41
 * @Desc
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Component {
    String value() default "";
}
