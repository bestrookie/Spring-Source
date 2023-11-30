package com.bestrookie.springframework.factory.support;

import com.bestrookie.springframework.factory.config.BeanDefinition;

/**
 * @Author bestrookie
 * @Date 2023/11/24 10:30
 * @Desc
 */
public interface BeanDefinitionRegistry {
    /**
     * 想注册表中注册BeanDefinition
     * @param beanName beanName
     * @param beanDefinition beanDefinition
     */
    void registerBeanDefinition(String beanName, BeanDefinition beanDefinition);
}
