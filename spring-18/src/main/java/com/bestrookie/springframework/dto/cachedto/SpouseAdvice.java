package com.bestrookie.springframework.dto.cachedto;

import com.bestrookie.springframework.aop.MethodBeforeAdvice;

import java.lang.reflect.Method;

/**
 * @Author bestrookie
 * @Date 2024/6/5 15:42
 * @Desc
 */
public class SpouseAdvice implements MethodBeforeAdvice {
    @Override
    public void before(Method method, Object[] args, Object target) throws Exception {
        System.out.println("关怀小两口（切面）" + method);
    }
}
