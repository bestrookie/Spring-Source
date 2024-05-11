package com.bestrookie.springframework.springframework.aop;

import java.lang.reflect.Method;

/**
 * @Author bestrookie
 * @Date 2024/3/18 14:25
 * @Desc
 */
public interface MethodMatcher {

    /**
     * 判断给定的方法是否与指定的类匹配。
     *
     * @param method 要匹配的方法对象。
     * @param targetClass 目标类，用于匹配的方法必须是此类或其子类中的方法。
     * @return 如果给定的方法是目标类或其子类中的方法，则返回true；否则返回false。
     */
    boolean matches(Method method, Class<?> targetClass);
}
