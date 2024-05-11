package com.bestrookie.springframework.springframework.beans.factory.config;

import com.bestrookie.springframework.beans.factory.BeanFactory;

/**
 * @Author bestrookie
 * @Date 2023/12/7 11:13
 * @Desc
 */
public interface AutowireCapableBeanFactory extends BeanFactory {
    /**
     * 执行 BeanPostProcessors 接口实现类 possProcessBeforeInitialization 方法
     * @param existingBean
     * @param beanName
     * @return
     * @throws Exception
     */
    Object applyBeanPostProcessorsBeForeInitialization(Object existingBean, String beanName) throws Exception;

    /**
     * 执行 BeanPostProcessors 接口实现类的 postProcessorsAfterInitialization 方法
     * @param existingBean
     * @param beanName
     * @return
     * @throws Exception
     */
    Object applyBeanPostProcessorsAfterInitialization(Object existingBean, String beanName) throws Exception;
}
