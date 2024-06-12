package com.bestrookie.springframework.beans.factory;

import com.bestrookie.springframework.beans.factory.config.AutowireCapableBeanFactory;
import com.bestrookie.springframework.beans.factory.config.BeanDefinition;
import com.bestrookie.springframework.beans.factory.config.ConfigurableBeanFactory;
import com.bestrookie.springframework.core.convert.converter.ConversionService;
import com.sun.istack.internal.Nullable;

/**
 * @Author bestrookie
 * @Date 2023/12/7 11:25
 * @Desc 提供分析和修改Bean以及预先实例化的操作接口
 */
public interface ConfigurableListableBeanFactory extends ListableBeanFactory, AutowireCapableBeanFactory, ConfigurableBeanFactory {
    /**
     * 获取实例对象
     * @param beanName 对象名称
     * @return 实例对象
     * @throws Exception exception
     */
    BeanDefinition getBeanDefinition(String beanName) throws Exception;

    /**
     * 实例化单例 Bean 对象
     * @throws Exception exception
     */
    void preInstantiateSingletons() throws Exception;
    /**
     * Specify a Spring 3.0 ConversionService to use for converting
     * property values, as an alternative to JavaBeans PropertyEditors.
     * @since 3.0
     */
    void setConversionService(ConversionService conversionService);

    /**
     * Return the associated ConversionService, if any.
     * @since 3.0
     */
    @Nullable
    ConversionService getConversionService();
}
