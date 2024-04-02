package com.bestrookie.springframework.context.support;

import com.bestrookie.springframework.beans.factory.ConfigurableListableBeanFactory;
import com.bestrookie.springframework.beans.factory.config.BeanFactoryPostProcessor;
import com.bestrookie.springframework.beans.factory.config.BeanPostProcessor;
import com.bestrookie.springframework.context.ApplicationEvent;
import com.bestrookie.springframework.context.ApplicationListener;
import com.bestrookie.springframework.context.ConfigurableApplicationContext;
import com.bestrookie.springframework.context.event.ApplicationEventMulticaster;
import com.bestrookie.springframework.context.event.ContextCloseEvent;
import com.bestrookie.springframework.context.event.ContextRefreshedEvent;
import com.bestrookie.springframework.context.event.SimpleApplicationEventMulticaster;
import com.bestrookie.springframework.core.io.DefaultResourceLoader;

import java.util.Collection;
import java.util.Map;

/**
 * @Author bestrookie
 * @Date 2024/1/24 16:01
 * @Desc 应用上下文抽象类实现
 */
public abstract class AbstractApplicationContext extends DefaultResourceLoader implements ConfigurableApplicationContext {
    public static final String APPLICATION_EVENT_MULTICASTER_BEAN_NAME = "applicationEventMulticaster";

    private ApplicationEventMulticaster applicationEventMulticaster;
    @Override
    public void refresh() throws Exception {
        //1.创建 BeanFactory，并创建 BeanDefinition
        refreshBeanFactory();

        //2.获取 BeanFactory
        ConfigurableListableBeanFactory beanFactory = getBeanFactory();

        //3.添加 ApplicationContextAwareProcessor,让继承自ApplicationContextAware的 Bean 对象都能感知所属的 ApplicationContext
        beanFactory.addBeanPostProcessor(new ApplicationContextAwareProcessor(this));
        //4.在 Bean实例化之前，执行 BeanFactoryPostProcessor
        invokeBeanFactoryPostProcessors(beanFactory);

        //5.BeanPostProcess 需要提前于其他 Bean 对想想实例化之前执行注册操作
        registerBeanPostProcessors(beanFactory);

        //6.初始化时间发布者
        initApplicationEventMulticaster();

        //7.注册时间监听器
        registerListener();

        //8.提前实例化单例bean
        beanFactory.preInstantiateSingletons();
        //9.发布容器刷新完成时间
        finishRefresh();

    }

    @Override
    public void registerShutdownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                this.close();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }));
    }

    @Override
    public void close() throws Exception {
        publishEvent(new ContextCloseEvent(this));
        getBeanFactory().destroySingletons();
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

    private void initApplicationEventMulticaster() throws Exception {
        ConfigurableListableBeanFactory beanFactory = getBeanFactory();
        applicationEventMulticaster = new SimpleApplicationEventMulticaster(beanFactory);
        beanFactory.registerSingleton(APPLICATION_EVENT_MULTICASTER_BEAN_NAME, applicationEventMulticaster);
    }
    private void registerListener() throws Exception {
        Collection<ApplicationListener> applicationListeners = getBeanOfType(ApplicationListener.class).values();
        for (ApplicationListener applicationListener : applicationListeners) {
            applicationEventMulticaster.addApplicationLister(applicationListener);
        }
    }

    private void finishRefresh() throws Exception {
        publishEvent(new ContextRefreshedEvent(this));
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
        return getBeanFactory().getBeanDefinitionNames();
    }

    @Override
    public void publishEvent(ApplicationEvent event) throws Exception {
        applicationEventMulticaster.multicastEvent(event);
    }
}
