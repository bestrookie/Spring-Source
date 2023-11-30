package com.bestrookie.springframework.factory;

import com.bestrookie.springframework.factory.config.BeanDefinition;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author bestrookie
 * @Date 2023/11/20 14:01
 * @Desc
 */
public interface BeanFactory {
    /**
     * 获取bean
     * @param beanName beanName
     * @return bean
     */
    Object getBean(String beanName) throws Exception;

    /**
     * 获取bean 有参构造
     * @param name name
     * @param args 参数
     * @return bean
     */
    Object getBean(String name, Object... args) throws Exception;
}
