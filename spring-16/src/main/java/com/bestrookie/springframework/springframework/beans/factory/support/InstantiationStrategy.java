package com.bestrookie.springframework.springframework.beans.factory.support;

import com.bestrookie.springframework.beans.factory.config.BeanDefinition;

import java.lang.reflect.Constructor;

/**
 * @Author bestrookie
 * @Date 2023/11/24 14:49
 * @Desc bean实例化策略
 */
public interface InstantiationStrategy {
    Object instantiate(BeanDefinition beanDefinition, String beanName, Constructor ctor, Object[] args) throws Exception;
}
