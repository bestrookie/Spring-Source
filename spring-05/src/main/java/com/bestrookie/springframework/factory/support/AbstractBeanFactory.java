package com.bestrookie.springframework.factory.support;

import com.bestrookie.springframework.factory.BeanFactory;
import com.bestrookie.springframework.factory.config.BeanDefinition;

/**
 * @Author bestrookie
 * @Date 2023/11/24 09:51
 * @Desc 抽象定义模板方法
 */
public abstract class AbstractBeanFactory extends DefaultSingletonBeanRegistry implements BeanFactory {
    @Override
    public Object getBean(String beanName) throws Exception {
        return doGetBean(beanName, null);
    }

    @Override
    public Object getBean(String beanName, Object... args) throws Exception {
        return doGetBean(beanName, args);
    }

    protected <T> T doGetBean(final String name, final Object[] args) throws Exception {
        Object bean = getSingleton(name);
        if (bean != null){
            return (T) bean;
        }
        BeanDefinition beanDefinition = getBeanDefinition(name);
        return (T) crateBean(name, beanDefinition, args);
    }

    protected abstract BeanDefinition getBeanDefinition(String beanName) throws Exception;

    protected abstract Object crateBean(String beanName, BeanDefinition beanDefinition, Object[] args) throws Exception;
}
