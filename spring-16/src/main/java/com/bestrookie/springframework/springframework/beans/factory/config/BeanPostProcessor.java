package com.bestrookie.springframework.springframework.beans.factory.config;

/**
 * @Author bestrookie
 * @Date 2024/1/24 15:43
 * @Desc
 */
public interface BeanPostProcessor {
    /**
     * 在 Bean 对象执行初始化方法之前，执行此方法
     * @param bean bean
     * @param beanName beanName
     * @return
     * @throws Exception exception
     */
    Object postProcessBeforeInitialization(Object bean, String beanName) throws Exception;

    /**
     * 在 Bean 对象执行初始化方法之后执行此方法
     * @param bean bean
     * @param beanName beanName
     * @return
     * @throws Exception exception
     */
    Object postProcessAfterInitialization(Object bean, String beanName) throws Exception;
}
