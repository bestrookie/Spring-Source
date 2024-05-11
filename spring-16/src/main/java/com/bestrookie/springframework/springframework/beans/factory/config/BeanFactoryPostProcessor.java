package com.bestrookie.springframework.springframework.beans.factory.config;

import com.bestrookie.springframework.beans.factory.ConfigurableListableBeanFactory;

/**
 * @Author bestrookie
 * @Date 2024/1/24 15:40
 * @Desc
 */
public interface BeanFactoryPostProcessor {
    /**
     * 在所有的 BeanDefinition加载完成后，实例化 Bean对象之前，提供修改 BeanDefinition 属性的机制
     * @param beanFactory
     * @throws Exception
     */
    void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws Exception;
}
