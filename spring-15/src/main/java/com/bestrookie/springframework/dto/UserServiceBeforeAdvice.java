package com.bestrookie.springframework.dto;

import com.bestrookie.springframework.aop.MethodBeforeAdvice;

import java.lang.reflect.Method;

/**
 * @Author bestrookie
 * @Date 2024/3/19 17:00
 * @Desc
 */
public class UserServiceBeforeAdvice implements MethodBeforeAdvice {
    @Override
    public void before(Method method, Object[] args, Object target) throws Exception {
        System.out.println("拦截方法："+method.getName());
    }
}
