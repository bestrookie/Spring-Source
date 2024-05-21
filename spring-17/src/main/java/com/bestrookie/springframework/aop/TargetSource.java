package com.bestrookie.springframework.aop;

import com.bestrookie.springframework.util.ClassUtils;

/**
 * @Author bestrookie
 * @Date 2024/3/18 15:13
 * @Desc
 */
public class TargetSource {
    private final Object target;
    public TargetSource(Object object){
        this.target = object;
    }

    public Class<?>[] getTargetClass(){
        Class<?> clazz = this.target.getClass();
        clazz = ClassUtils.isCglibProxyClass(clazz) ? clazz.getSuperclass() : clazz;
        return clazz.getInterfaces();
    }

    public Object getTarget() {
        return this.target;
    }
}
