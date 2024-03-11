package com.bestrookie.springframework.beans.factory.support;

import com.bestrookie.springframework.beans.factory.config.BeanDefinition;

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

    /**
     * 使用beanName查询BeanDefinition
     * @param beanName beanName
     * @return beanDefinition
     * @throws Exception exception
     */
    BeanDefinition getBeanDefinition(String beanName) throws Exception;

    /**
     * 判断是否包含指定名称的BeanDefinition
     * @param beanName beanName
     * @return boolean
     */
    boolean containsBeanDefinition(String beanName);

    /**
     * 返回注册表中所有Bean名称
     * @return return
     */
    String[] getBeanDefinitionNames();
}
