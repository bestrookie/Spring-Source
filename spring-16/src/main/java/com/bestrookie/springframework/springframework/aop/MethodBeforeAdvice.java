package com.bestrookie.springframework.springframework.aop;

import java.lang.reflect.Method;

/**
 * @Author bestrookie
 * @Date 2024/3/19 15:14
 * @Desc
 */
public interface MethodBeforeAdvice extends BeforeAdvice{


    /**
     * 在方法执行前执行的拦截器方法。
     *
     * @param method 将要执行的方法，是一个Method对象。
     * @param args 将要传递给方法的参数数组。
     * @param target 方法所属的对象实例。
     * @throws Exception 如果在执行过程中发生异常，则抛出Exception。
     */
    void before(Method method, Object[] args, Object target) throws Exception;
}
