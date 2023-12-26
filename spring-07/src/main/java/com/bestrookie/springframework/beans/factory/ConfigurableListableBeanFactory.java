package com.bestrookie.springframework.beans.factory;

import com.bestrookie.springframework.beans.factory.config.AutowireCapableBeanFactory;
import com.bestrookie.springframework.beans.factory.config.BeanDefinition;

/**
 * @Author bestrookie
 * @Date 2023/12/7 11:25
 * @Desc 提供分析和修改Bean以及预先实例化的操作接口
 */
public interface ConfigurableListableBeanFactory extends ListableBeanFactory, AutowireCapableBeanFactory {
    BeanDefinition getBeanDefinition(String beanName) throws Exception;
}
