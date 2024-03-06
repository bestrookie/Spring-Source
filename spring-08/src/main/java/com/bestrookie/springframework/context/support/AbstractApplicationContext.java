package com.bestrookie.springframework.context.support;

import com.bestrookie.springframework.context.ConfigurableApplicationContext;
import com.bestrookie.springframework.beans.factory.ConfigurableListableBeanFactory;
import com.bestrookie.springframework.beans.factory.config.BeanFactoryPostProcessor;
import com.bestrookie.springframework.beans.factory.config.BeanPostProcessor;
import com.bestrookie.springframework.core.io.DefaultResourceLoader;

import java.util.Map;

/**
 * @Author bestrookie
 * @Date 2024/1/24 16:01
 * @Desc 应用上下文抽象类实现
 */
public abstract class AbstractApplicationContext extends DefaultResourceLoader implements ConfigurableApplicationContext {
    @Override
    public void refresh() throws Exception {
        //1.创建 BeanFactory，并创建 BeanDefinition
        refreshBeanFactory();

        //2.获取 BeanFactory
        ConfigurableListableBeanFactory beanFactory = getBeanFactory();

        //3.在 Bean实例化之前，执行 BeanFactoryPostProcessor
        invokeBeanFactoryPostProcessors(beanFactory);

        //4.BeanPostProcess 需要提前于其他 Bean 对想想实例化之前执行注册操作
        registerBeanPostProcessors(beanFactory);

        //5.提前实例化单例 Bean 对象
        beanFactory.preInstantiateSingletons();

    }

    /**
     * 刷新工厂
     */
    protected abstract void refreshBeanFactory() throws Exception;

    /**
     * 获取 bean 工程
     * @return factory
     */
    protected abstract ConfigurableListableBeanFactory getBeanFactory();


    private void invokeBeanFactoryPostProcessors(ConfigurableListableBeanFactory beanFactory) throws Exception {
        Map<String, BeanFactoryPostProcessor> beanFactoryPostProcessorMap = beanFactory.getBeanOfType(BeanFactoryPostProcessor.class);
        for (BeanFactoryPostProcessor value : beanFactoryPostProcessorMap.values()) {
            value.postProcessBeanFactory(beanFactory);
        }
    }

    private void registerBeanPostProcessors(ConfigurableListableBeanFactory beanFactory) throws Exception {
        Map<String, BeanPostProcessor> beanPostProcessorMap = beanFactory.getBeanOfType(BeanPostProcessor.class);
        for (BeanPostProcessor value : beanPostProcessorMap.values()) {
            beanFactory.addBeanPostProcessor(value);
        }
    }

    @Override
    public Object getBean(String beanName) throws Exception {
        return getBeanFactory().getBean(beanName);
    }

    @Override
    public Object getBean(String name, Object... args) throws Exception {
        return getBeanFactory().getBean(name, args);
    }

    @Override
    public <T> T getBean(String name, Class<T> requireType) throws Exception {
        return getBeanFactory().getBean(name, requireType);
    }

    @Override
    public <T> Map<String, T> getBeanOfType(Class<T> type) throws Exception {
        return getBeanFactory().getBeanOfType(type);
    }

    @Override
    public String[] getBeanDefinitionNames() {
        return new String[0];
    }
}
