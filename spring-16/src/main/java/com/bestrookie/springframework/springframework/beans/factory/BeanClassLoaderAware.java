package com.bestrookie.springframework.springframework.beans.factory;

/**
 * @Author bestrookie
 * @Date 2024/3/11 14:59
 * @Desc
 */
public interface BeanClassLoaderAware extends Aware {
    void setBeanClassLoader(ClassLoader classLoader) throws Exception;
}
