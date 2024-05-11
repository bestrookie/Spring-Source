package com.bestrookie.springframework.springframework.context.support;

import com.bestrookie.springframework.beans.factory.ConfigurableListableBeanFactory;
import com.bestrookie.springframework.beans.factory.support.DefaultListableBeanFactory;

/**
 * @Author bestrookie
 * @Date 2024/1/29 09:33
 * @Desc 获取工厂和加载资源
 */
public abstract class AbstractRefreshableApplicationContext extends AbstractApplicationContext {
    private DefaultListableBeanFactory beanFactory;

    @Override
    protected void refreshBeanFactory() throws Exception {
        DefaultListableBeanFactory beanFactory = createBeanFactory();
        loadBeanDefinitions(beanFactory);
        this.beanFactory = beanFactory;
    }

    @Override
    protected ConfigurableListableBeanFactory getBeanFactory() {
        return beanFactory;
    }


    private DefaultListableBeanFactory createBeanFactory(){
        return new DefaultListableBeanFactory();
    }

    /**
     * 资源配置加载
     * @param beanFactory bean 工厂
     */
    protected abstract void loadBeanDefinitions(DefaultListableBeanFactory beanFactory) throws Exception;
}
